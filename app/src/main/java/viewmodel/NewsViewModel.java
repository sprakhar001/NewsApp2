package viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import api.RetrofitInstance;
import model.Article;
import model.NewsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

public class NewsViewModel extends ViewModel {
    private NewsRepository newsRepository;
    private MutableLiveData<NewsResponse> _news = new MutableLiveData<NewsResponse>();
    public LiveData<NewsResponse> news = _news;


    private int newsPageNumber = 1;

    private MutableLiveData<NewsResponse> _searchNews = new MutableLiveData<NewsResponse>();
    public LiveData<NewsResponse> searchNews = new MutableLiveData<NewsResponse>();
    private int searchPageNumber = 1;

    public NewsViewModel(NewsRepository newsRepository) {
        super();
        this.newsRepository = newsRepository;
    }

    public void getTrendingNews(){
        Call<NewsResponse> call = newsRepository.getTrendingNews("us", newsPageNumber, Constants.API_KEY);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                _news.postValue(response.body());
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                t.printStackTrace();
                // TODO: handle errors
            }
        });
    }

    public void getSearchedNews(String searchQuery){
        Call<NewsResponse> call = newsRepository.getSearchedNews(searchQuery, searchPageNumber, Constants.API_KEY);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                _searchNews.postValue(response.body());
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                t.printStackTrace();
                // TODO: handle errors
            }
        });
    }

    public void insert(Article article){
        newsRepository.insert(article);
    }
    public void delete(Article article){
        newsRepository.delete(article);
    }
    public LiveData<List<Article>> getAllArticle(){
        return newsRepository.getAllArticle();
    }
}
