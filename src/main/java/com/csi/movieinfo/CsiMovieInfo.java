/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.csi.movieinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author ratnesh
 */
@SpringBootApplication
@EnableEurekaClient
public class CsiMovieInfo {

    public static void main(String[] args) {
        SpringApplication.run(CsiMovieInfo.class, args);
    }
    
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
