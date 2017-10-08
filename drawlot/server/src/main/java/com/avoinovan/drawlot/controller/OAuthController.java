package com.avoinovan.drawlot.controller;

import com.avoinovan.drawlot.model.entity.AccessToken;
import com.avoinovan.drawlot.model.entity.Platform;
import com.avoinovan.drawlot.model.entity.User;
import com.avoinovan.drawlot.model.repository.AccessTokenRepository;
import com.avoinovan.drawlot.model.repository.UserRepository;
import com.avoinovan.drawlot.service.auth.YoutubeAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * @author by avoinovan
 */
@Controller
@RequestMapping("oauth")
public class OAuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Autowired
    private YoutubeAuthService youtubeAuthService;

    @RequestMapping(value = "youtube", method = RequestMethod.GET)
    public ModelAndView completeYoutubeOAuth(@RequestParam("state") String userId, @RequestParam("code") String code) throws IOException {

            User user = userRepository.findOne(userId);
            if (user == null) {
                //TODO: throw exception user doesn't exist
                user = new User();
                user.setId(userId);
            }

            AccessToken tokenEntity = accessTokenRepository.findByUserIdAndPlatform(userId, Platform.YOUTUBE);
            String accessToken = youtubeAuthService.getRefreshToken(code);

            if (tokenEntity == null) {
                tokenEntity = new AccessToken(Platform.YOUTUBE, accessToken, user.getId());
            } else {
                tokenEntity.setToken(accessToken);
            }

            accessTokenRepository.save(tokenEntity);

            userRepository.save(user);


        ModelAndView response = new ModelAndView("regcomplete");
        response.addObject("platform", "Youtube");

        return response;
    }

    @RequestMapping(value = "/youtube/{userId}/redirect", method = RequestMethod.GET)
    public ModelAndView redirectToYoutube(@PathVariable("userId") String userId) {
        return new ModelAndView("redirect:" + youtubeAuthService.generateUserRedirectUrl(userId));
    }
}
