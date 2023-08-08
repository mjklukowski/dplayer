package com.github.mjklukowski.dplayer;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DPlayerConfiguration implements WebMvcConfigurer {

    @Value("${discord.token}")
    private String token;

    @Bean
    public GatewayDiscordClient createDiscordClient() {
        return DiscordClientBuilder.create(token)
                .build()
                .login()
                .block();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
