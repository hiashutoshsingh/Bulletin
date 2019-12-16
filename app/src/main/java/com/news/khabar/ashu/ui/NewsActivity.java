package com.news.khabar.ashu.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.news.khabar.ashu.viewmodel.NewsViewModel;
import com.news.khabar.ashu.R;
import com.news.khabar.ashu.model.Article;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    //    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<Article> articles = new ArrayList<>();
    private RelativeLayout errorLayout;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;
    private LinearLayoutManager linearLayoutManager;
    private SearchView searchView;
    //    private ShimmerFrameLayout shimContainerLayout;
    private FrameLayout frame;
    NewsViewModel newsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

//        shimContainerLayout = findViewById(R.id.shimContainerLayout);
        frame = findViewById(R.id.frame);
        errorLayout = findViewById(R.id.errorLayout);
        errorTitle = findViewById(R.id.errorTitle);
        errorMessage = findViewById(R.id.errorMessage);
        btnRetry = findViewById(R.id.btnRetry);

//        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
//        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        recyclerView = findViewById(R.id.recyclerView);

        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        newsViewModel.init();
        newsViewModel.getNewsRepository().observe(this, newsResponse -> {
            articles.addAll(newsResponse.getArticles());
            adapter.notifyDataSetChanged();
        });
        showPopularNews();

//        onLoadingSwipeRefresh();


    }

//    private void onLoadingSwipeRefresh() {
//        swipeRefreshLayout.post(() -> showPopularNews());
//    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void showPopularNews() {

        errorLayout.setVisibility(View.GONE);
//        frame.setVisibility(View.GONE);
//        shimContainerLayout.setVisibility(View.VISIBLE);
//        shimContainerLayout.startShimmer();
//        swipeRefreshLayout.setRefreshing(true);

        if (adapter == null) {
//            shimContainerLayout.stopShimmer();
//            shimContainerLayout.setVisibility(View.GONE);
//            frame.setVisibility(View.VISIBLE);
//            swipeRefreshLayout.setRefreshing(false);

            adapter = new NewsAdapter(articles, NewsActivity.this);
            linearLayoutManager = new LinearLayoutManager(NewsActivity.this);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(adapter);
            initListener();
        } else adapter.notifyDataSetChanged();

    }


    private void initListener() {

        adapter.setOnItemClickListener((view, position) -> {
            ImageView imageView = view.findViewById(R.id.img);
            Intent intent = new Intent(NewsActivity.this, NewsInfoActivity.class);

            Article article = articles.get(position);
            intent.putExtra("url", article.getUrl());
            intent.putExtra("title", article.getTitle());
            intent.putExtra("author", article.getAuthor());
            intent.putExtra("img", article.getUrlToImage());
            intent.putExtra("date", article.getPublishedAt());
            intent.putExtra("description", article.getDescription());
            intent.putExtra("source", article.getSource().getName());

            Pair<View, String> pair = Pair.create((View) imageView, ViewCompat.getTransitionName(imageView));
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(NewsActivity.this, pair);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                startActivity(intent, optionsCompat.toBundle());
            } else {
                startActivity(intent);
            }
        });
    }


    private void showErrorMessage(String title, String message) {

        if (errorLayout.getVisibility() == View.GONE)
            errorLayout.setVisibility(View.VISIBLE);

        errorTitle.setText(title);
        errorMessage.setText(message);
//        btnRetry.setOnClickListener(v -> onLoadingSwipeRefresh());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

}
