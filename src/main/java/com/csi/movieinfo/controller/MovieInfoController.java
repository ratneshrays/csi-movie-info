/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csi.movieinfo.controller;

import com.csi.movieinfo.modal.MovieInfo;
import com.csi.movieinfo.modal.MovieSummary;
import com.csi.movieinfo.props.DataSource;
import com.csi.movieinfo.props.Greeting;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author ratnesh
 */
@RestController
@RefreshScope
@RequestMapping("/api/v1")
public class MovieInfoController {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${application.apikey}")
    private String apiKey;
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private Greeting greeting;
      
    @GetMapping("/props/greeting")
    public List<String> getGreetingProps() {
        List<String> dataList = new ArrayList<>();
        dataList.add(greeting.getId());
        dataList.add(greeting.getName());
        dataList.add(greeting.getDescription());
        dataList.add(greeting.getMessage());
        return dataList;
    }
    
    @GetMapping("/props/datasource")
    public List<String> getDataSourceProps() {
        List<String> dataList = new ArrayList<>();
        dataList.add(dataSource.getUrl());
        dataList.add(dataSource.getUsername());
        dataList.add(dataSource.getPassword());
        return dataList;
    }
    
    @RequestMapping("/info/{movieId}")
    public MovieInfo getMovieInfo(@PathVariable("movieId") String movieId) {
        return new MovieInfo(movieId, "PK", "Alien Movie","s-1");
    }
    
    @RequestMapping("/info/db/{movieId}")
    public MovieInfo getMovieInfoFromDB(@PathVariable("movieId") String movieId) throws JsonProcessingException {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" +  apiKey;
        String response = restTemplate.getForObject(url, String.class);
        System.out.println("Response  : "+response);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MovieSummary movieSummary = mapper.readValue(response, MovieSummary.class);
        return new MovieInfo(movieId, movieSummary.getTitle(), movieSummary.getOverview(),
                movieSummary.getTagline());
    }
    
}
