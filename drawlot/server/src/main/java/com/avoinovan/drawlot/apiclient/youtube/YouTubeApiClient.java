package com.avoinovan.drawlot.apiclient.youtube;

import com.avoinovan.drawlot.model.entity.AccessToken;
import com.avoinovan.drawlot.model.entity.Platform;
import com.avoinovan.drawlot.model.repository.AccessTokenRepository;
import com.avoinovan.drawlot.model.repository.UserRepository;
import com.avoinovan.drawlot.service.auth.YoutubeAuthService;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SubscriptionListResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;

/**
 * Created by avoinovan on 3/10/17
 */
@Service
public class YouTubeApiClient {

    private static final Logger LOGGER = LogManager.getLogger(YouTubeApiClient.class);

    //Maximum supported by YouTube results per page
    private static final Long RESULTS_PER_PAGE = 50l;

    @Value("${google.oauth2.project_id}")
    private String projectId;

    private final YouTube youTube;
    private final UserRepository userRepository;
    private final AccessTokenRepository accessTokenRepository;
    private final YoutubeAuthService youtubeAuthService;

    public YouTubeApiClient(HttpTransport transport, JsonFactory jsonFactory,
                            UserRepository userRepository, AccessTokenRepository accessTokenRepository,
                            YoutubeAuthService youtubeAuthService) {

        this.youTube = new YouTube.Builder(transport, jsonFactory, httpRequest -> {})
                .setApplicationName(projectId)
                .build();

        this.userRepository = userRepository;
        this.accessTokenRepository = accessTokenRepository;
        this.youtubeAuthService = youtubeAuthService;
    }

    public Integer countSubscribers(final String userId) throws IOException {

        final String requestToken = getUserAccessToken(userId);

        SubscriptionListResponse subscriptionListResponse = youTube
                .subscriptions()
                .list("snippet")
                .setOauthToken(requestToken)
                .setMySubscribers(true)
                .setForChannelId("UC9Y0KMzELO85uNeJZxEdtYQ")
                .execute();

        final Integer result = subscriptionListResponse.getPageInfo().getTotalResults();
        //websocket.convertAndSend("/topic/subsCount", result);
        return result;
    }

    public YoutubeSubscriber getRandomSubscriber(final String userId) throws IOException {

        final String requestToken = getUserAccessToken(userId);

        final Integer totalSubscribers = countSubscribers(userId);

        final Integer randomSubscriber = new Random().nextInt(totalSubscribers);

        System.out.println("Random subscriber index " + randomSubscriber);

        Integer subscriberIndex = randomSubscriber % RESULTS_PER_PAGE.intValue();

        final Integer pagesToSkip = randomSubscriber / RESULTS_PER_PAGE.intValue() - (subscriberIndex == 0 ? 1 : 0);

        LOGGER.debug(
                String.format(
                "TotalSubscribers[%s] Results per page [%s] Pages[%s] index at %sth page[%s]",
                totalSubscribers,
                RESULTS_PER_PAGE,
                pagesToSkip,
                pagesToSkip + 1,
                subscriberIndex)
        );


        SubscriptionListResponse result = getSubscribersPage(userId, pagesToSkip, requestToken);



        YoutubeSubscriber subscriber = new YoutubeSubscriber();

        // special condition, sometimes page could contain less subscribers
        // than it should due to inconsistency on google side - number of total subscribers
        //returned by API doesn't match summ of subscribers polled page-by-page
        // in that case re-rolling the subscriber index to get the subscriber this page
        if (result.getItems().size() < subscriberIndex ) {
            LOGGER.warn(String.format("Expected subscriber index %s, found subscribers on page %s, re-electing the winner.",
                    subscriberIndex, result.getItems().size()));
            subscriberIndex = new Random().nextInt(result.getItems().size() -1);
            LOGGER.warn(String.format("Re-elected winner index %s of %s on the page",
                    subscriberIndex, result.getItems().size()));
        }

        subscriber.setName(result.getItems().get(subscriberIndex).getSubscriberSnippet().getTitle());
        subscriber.setChannelId(result.getItems().get(subscriberIndex).getSubscriberSnippet().getChannelId());
        subscriber.setThumbnail(result.getItems().get(subscriberIndex).getSubscriberSnippet().getThumbnails().getHigh().getUrl());
        subscriber.setDescription(result.getItems().get(subscriberIndex).getSubscriberSnippet().getDescription());


        return subscriber;
    }

    private SubscriptionListResponse getSubscribersPage(final String userId, final Integer pagesToSkip, final String token) throws IOException {
        SubscriptionListResponse result = null;

        Integer pagesSkipped = 0;

        do {

            YouTube.Subscriptions.List subsPageCall =
            youTube.subscriptions()
                    .list(pagesSkipped++ < pagesToSkip - 1 ? "id" : "subscriberSnippet")
                    .setPageToken(result == null ? null : result.getNextPageToken())
                    .setOauthToken(token)
                    .setMySubscribers(true)
                    .setForChannelId("UC9Y0KMzELO85uNeJZxEdtYQ")
                    .setOrder("alphabetical")
                    .setMaxResults(RESULTS_PER_PAGE);

            result = subsPageCall.execute();
            System.out.println(pagesSkipped + ":" + result.getItems().size());

            //websocket.convertAndSend("/topic/progress", new PageInfo(pagesToSkip, pagesSkipped));

        } while(pagesSkipped <= pagesToSkip);

        return result;

    }

    private String getUserAccessToken(final String userId) throws IOException {

        if (userRepository.exists(userId)) {

            AccessToken token = accessTokenRepository.findByUserIdAndPlatform(userId, Platform.YOUTUBE);

            if (token != null) {
                return youtubeAuthService.refreshAccessToken(token.getToken());
            }
        }

        return null;
    }

    private class PageInfo {
        private Integer totalPages;
        private Integer pagesSkipped;

        public PageInfo(final Integer totalPages, final Integer pagesSkipped) {
            this.totalPages = totalPages;
            this.pagesSkipped = pagesSkipped;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(final Integer totalPages) {
            this.totalPages = totalPages;
        }

        public Integer getPagesSkipped() {
            return pagesSkipped;
        }

        public void setPagesSkipped(final Integer pagesSkipped) {
            this.pagesSkipped = pagesSkipped;
        }
    }


}
