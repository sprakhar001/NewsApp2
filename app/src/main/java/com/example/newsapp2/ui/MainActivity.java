package com.example.newsapp2.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import android.os.Bundle;

import com.example.newsapp2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import db.ArticleDao;
import db.ArticleDatabase;
import viewmodel.NewsRepository;
import viewmodel.NewsViewModel;
import viewmodel.NewsViewModelProviderFactory;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    public NewsViewModel newsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NewsRepository newsRepository = new NewsRepository(ArticleDatabase.getDataBaseInstance(this));
        NewsViewModelProviderFactory viewModelProviderFactory = new NewsViewModelProviderFactory(newsRepository);
        newsViewModel = new ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel.class);
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        setUI();
    }

    private void setUI() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        navController = Navigation.findNavController(this, R.id.news_nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}