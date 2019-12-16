package com.news.khabar.ashu.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.news.khabar.ashu.model.News;
import com.news.khabar.ashu.repository.NewsRepository;
import com.news.khabar.ashu.utils.CommonMethods;
import com.news.khabar.ashu.utils.Constants;

public class NewsViewModel extends ViewModel {

    private MutableLiveData<News> mutableLiveData;
    private NewsRepository newsRepository;

    public void init() {
        if (mutableLiveData != null)
            return;

        newsRepository = NewsRepository.getInstance();
        mutableLiveData = newsRepository.getNews(CommonMethods.getCountry(), Constants.API_KEY);
    }

    public LiveData<News> getNewsRepository() {
        return mutableLiveData;
    }

}
