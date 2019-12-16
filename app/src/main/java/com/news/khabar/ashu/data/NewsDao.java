package com.news.khabar.ashu.data;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.news.khabar.ashu.model.Article;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.DELETE;

public interface NewsDao {

    @Query("SELECT * FROM article")
    Single<List<Article>> getAllNews();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Article article);

    @DELETE
    void remove(Article article);

    @Query("SELECT count(*) FROM article where id LIKE :id")
    int isFavouriteShow(int id);
}

