package revanth.com.newsapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import revanth.com.newsapp.Models.Headlines;

public interface ApiInterface {

    @GET("top-headlines")
    Call<Headlines> getHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey

    );

    @GET("everything")
    Call<Headlines> getSpecificData(
            @Query("q") String query,
            @Query("apiKey") String apiKey

    );
}
