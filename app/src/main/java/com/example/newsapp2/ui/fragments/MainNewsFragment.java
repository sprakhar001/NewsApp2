package com.example.newsapp2.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.newsapp2.R;
import com.example.newsapp2.ui.MainActivity;

import adapter.NewsRecyclerViewAdapter;
import model.Article;
import model.NewsResponse;
import viewmodel.NewsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainNewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainNewsFragment extends Fragment implements NewsRecyclerViewAdapter.ArticleClickListener {

    private NewsViewModel viewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private NavController navController;
    private static final String TAG = MainNewsFragment.class.getSimpleName();

    public MainNewsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_main_news, container, false);
        setUpRecyclerView(view);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        Log.d(TAG, "onCreateView");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        viewModel = ((MainActivity) getActivity()).newsViewModel;
        viewModel.news.observe(getViewLifecycleOwner(), new Observer<NewsResponse>() {
            @Override
            public void onChanged(NewsResponse newsResponse) {
                ((NewsRecyclerViewAdapter)recyclerView.getAdapter()).listDiffer.submitList(newsResponse.getArticles());
            }
        });
        viewModel.getTrendingNews();
        Log.d(TAG, "onViewCreated");
    }

    private void setUpRecyclerView(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.news_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new NewsRecyclerViewAdapter(this));
    }

    @Override
    public void articleTapped(Article article) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("article", article);
        bundle.putBoolean("willDelete", false);
        navController.navigate(R.id.action_mainNewsFragment_to_articleFragment, bundle);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int scrollPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        outState.putInt("scrollPosition", scrollPosition);
        outState.putParcelable("scrollPosition", recyclerView.getLayoutManager().onSaveInstanceState());
        Log.d(TAG, "onsaveInstance");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            int scrollPosition = savedInstanceState.getInt("scrollPosition", 0);
            recyclerView.scrollToPosition(scrollPosition);
            Parcelable savedPosition = savedInstanceState.getParcelable("scrollPosition");
            if(savedPosition != null) recyclerView.getLayoutManager().onRestoreInstanceState(savedPosition);
        }
        Log.d(TAG, "onRestore");
    }

}