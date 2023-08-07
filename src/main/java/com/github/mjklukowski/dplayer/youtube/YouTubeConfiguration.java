package com.github.mjklukowski.dplayer.youtube;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class YouTubeConfiguration {

    @Bean
    public WebClient createWebClient() {
        return WebClient.create("https://youtube.googleapis.com/youtube/v3");
    }

}
