package com.avoinovan.drawlot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * Created by avoinovan on 3/10/17
 */
@SpringBootApplication
public class Application {


    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
        /*YoutubeAuthService youtubeAuthService = new YoutubeAuthService(JSON_FACTORY, HTTP_TRANSPORT);
        System.out.println(youtubeAuthService.generateUserRedirectUrl(UUID.randomUUID().toString()));
        System.out.println(youtubeAuthService.getRefreshToken("4/mNVFF7zJb38xSjwxpuYl5TbCzejxE-iC9cX7pRGrbyU#"));

        System.out.println(new YouTubeApiClient(HTTP_TRANSPORT, JSON_FACTORY).countSubscribers("UC9Y0KMzELO85uNeJZxEdtYQ", "ya29.GlsKBGcjOPvqBZofRJwjT85bBSlsenntleE-zAX7RYmMuVy2yep65dMcZRWQ_xh8K7z5q7tXQ3uVNONuzDr1rzQ5dKxchLyEVvO4Ob_Z36FYow_jqAwMjetvfHO1"));*/

    }
}
