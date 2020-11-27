package api;

import model.NewsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPI {
    @GET("v2/top-headlines")
    Call<NewsResponse> getTrendingNews(@Query("country") String countryCode, @Query("page") int pageNumber, @Query("apiKey") String apiKey);

    @GET("v2/everything")
    Call<NewsResponse> getSearchedNews(@Query("q") String searchQuery, @Query("page") int pageNumber, @Query("apiKey") String apiKey);
}
