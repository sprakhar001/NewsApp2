package viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;

import java.util.List;

import api.RetrofitInstance;
import db.ArticleDatabase;
import model.Article;
import model.NewsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

public class NewsRepository {
    public ArticleDatabase db;

    public NewsRepository(ArticleDatabase db) {
        this.db = db;
    }

    public Call<NewsResponse> getTrendingNews(String countryCode, int pageNumber, String apiKey){
        return  RetrofitInstance.getService().getTrendingNews(countryCode, pageNumber, apiKey);
    }

    public Call<NewsResponse> getSearchedNews(String query, int pageNumber, String apiKey){
        return RetrofitInstance.getService().getSearchedNews(query, pageNumber, apiKey);
    }

    public void insert(Article article){
        new InsertAsyncTask(db).execute(article);
    }

    public LiveData<List<Article>> getAllArticle(){
        return db.articleDao().getAllArticles();
    }

    public void delete(Article article){
        new DeleteAsyncTask(db).execute(article);
    }

    public static class InsertAsyncTask extends AsyncTask<Article, Void, Void> {
        ArticleDatabase db;

        public InsertAsyncTask(ArticleDatabase db) {
            this.db = db;
        }

        @Override
        protected Void doInBackground(Article... articles) {
            db.articleDao().insert(articles[0]);
            return null;
        }
    }
    public static class DeleteAsyncTask extends AsyncTask<Article, Void, Void> {
        ArticleDatabase db;

        public DeleteAsyncTask(ArticleDatabase db) {
            this.db = db;
        }

        @Override
        protected Void doInBackground(Article... articles) {
            db.articleDao().delete(articles[0]);
            return null;
        }
    }

    //TODO: RxJava or Kotlin with couroutine

}
