package com.example.newsapp2.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.newsapp2.R;
import com.example.newsapp2.ui.MainActivity;

import adapter.NewsRecyclerViewAdapter;
import model.Article;
import model.NewsResponse;
import viewmodel.NewsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchNewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchNewsFragment extends Fragment implements NewsRecyclerViewAdapter.ArticleClickListener {

    private NewsViewModel viewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private EditText query;
    private NavController navController;

    public SearchNewsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_search_news, container, false);
        setUpRecyclerView(view);
        progressBar = (ProgressBar) view.findViewById(R.id.search_fragment_progress_bar);
        query = (EditText) view.findViewById(R.id.search_fragment_query);
        query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String queryText = query.getText().toString();
                if(queryText != null && queryText.length() > 0) viewModel.getSearchedNews(query.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        viewModel = ((MainActivity) getActivity()).newsViewModel;
        viewModel.searchNews.observe(getViewLifecycleOwner(), new Observer<NewsResponse>() {
            @Override
            public void onChanged(NewsResponse newsResponse) {
                ((NewsRecyclerViewAdapter)recyclerView.getAdapter()).listDiffer.submitList(newsResponse.getArticles());
            }
        });
    }

    private void setUpRecyclerView(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.search_fragment_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new NewsRecyclerViewAdapter(this));
    }

    @Override
    public void articleTapped(Article article) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("article", article);
        navController.navigate(R.id.action_searchNewsFragment_to_articleFragment, bundle);
    }
}