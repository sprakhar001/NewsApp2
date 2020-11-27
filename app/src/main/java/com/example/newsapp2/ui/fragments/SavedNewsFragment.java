package com.example.newsapp2.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.newsapp2.R;
import com.example.newsapp2.ui.MainActivity;

import java.util.List;

import adapter.NewsRecyclerViewAdapter;
import model.Article;
import viewmodel.NewsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavedNewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavedNewsFragment extends Fragment implements NewsRecyclerViewAdapter.ArticleClickListener {
    private NewsViewModel viewModel;
    private RecyclerView recyclerView;
    private NavController navController;

    public SavedNewsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_saved_news, container, false);
        setUpRecyclerView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        viewModel = ((MainActivity) getActivity()).newsViewModel;
        viewModel.getAllArticle().observe(getViewLifecycleOwner(), new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                ((NewsRecyclerViewAdapter)recyclerView.getAdapter()).listDiffer.submitList(articles);
            }
        });
//        new ItemTouchHelper(callback).attachToRecyclerView(recyclerView);
    }

    private void setUpRecyclerView(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.saved_news_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new NewsRecyclerViewAdapter(this));
    }

    @Override
    public void articleTapped(Article article) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("article", article);
        bundle.putBoolean("willDelete", true);
        navController.navigate(R.id.action_savedNewsFragment_to_articleFragment, bundle);
    }

    /*ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.DOWN | ItemTouchHelper.UP,
            ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT
    ){

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Article article = ((NewsRecyclerViewAdapter)recyclerView.getAdapter()).listDiffer.getCurrentList().get(viewHolder.getAdapterPosition());
            viewModel.delete(article);
            Toast.makeText(getView().getContext(), "Article Deleted", Toast.LENGTH_SHORT).show();
        }
    };*/
}