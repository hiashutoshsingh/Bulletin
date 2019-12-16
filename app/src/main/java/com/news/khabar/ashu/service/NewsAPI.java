package com.news.khabar.ashu.service;

import com.news.khabar.ashu.model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPI {

    @GET("top-headlines")
    Call<News> getNewsList(@Query("country") String country, @Query("apiKey") String apiKey);
}
