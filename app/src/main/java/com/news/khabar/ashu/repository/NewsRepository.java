package com.news.khabar.ashu.repository;


import androidx.lifecycle.MutableLiveData;

import com.news.khabar.ashu.model.News;
import com.news.khabar.ashu.service.NewsAPI;
import com.news.khabar.ashu.service.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {


    private static NewsRepository newsRepository;
    public static NewsRepository getInstance() {
        if (newsRepository == null) {
            newsRepository = new NewsRepository();
        }
        return newsRepository;
    }
    private NewsAPI newsApi;


    public NewsRepository() {
        newsApi = ServiceGenerator.createService(NewsAPI.class);
    }

    public MutableLiveData<News> getNews(String source, String key){
        MutableLiveData<News> newsMutableLiveData = new MutableLiveData<>();
        newsApi.getNewsList(source,key).enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                    if(response.isSuccessful())
                        newsMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                    newsMutableLiveData.setValue(null);
            }
        });
        return newsMutableLiveData;
    }

}
