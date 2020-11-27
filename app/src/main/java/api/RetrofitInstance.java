package api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.Constants;

public class RetrofitInstance {
    public static NewsAPI newsAPI = null;
    public static NewsAPI getService(){
        if(newsAPI == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            newsAPI = retrofit.create(NewsAPI.class);
        }
        return newsAPI;
    }
}
