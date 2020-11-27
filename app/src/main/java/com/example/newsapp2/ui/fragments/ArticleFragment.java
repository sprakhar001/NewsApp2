package com.example.newsapp2.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.newsapp2.R;
import com.example.newsapp2.ui.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import model.Article;
import viewmodel.NewsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleFragment extends Fragment {

    private NewsViewModel viewModel;
    private WebView webView;
    private FloatingActionButton button, deleteButton;
    private Article article;
    private Boolean toBeDeleted;

    public ArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        webView = (WebView) view.findViewById(R.id.web_view);
        button = (FloatingActionButton) view.findViewById(R.id.favourite);
        deleteButton = (FloatingActionButton) view.findViewById(R.id.delete);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.insert(article);
                Toast.makeText(view.getContext(), "Article Saved", Toast.LENGTH_SHORT).show();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.delete(article);
                Toast.makeText(view.getContext(), "Article Deleted", Toast.LENGTH_SHORT).show();
//                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ((MainActivity) getActivity()).newsViewModel;
        ArticleFragmentArgs args = ArticleFragmentArgs.fromBundle(getArguments());
        article = args.getArticle();
        toBeDeleted = getArguments().getBoolean("willDelete");
        if(toBeDeleted != null && toBeDeleted){
            button.setVisibility(View.INVISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
        }
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(article.getUrl());
    }
}