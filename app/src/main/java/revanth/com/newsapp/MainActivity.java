package revanth.com.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import revanth.com.newsapp.Models.Articles;
import revanth.com.newsapp.Models.Headlines;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText searchQuery;
    Button searchBtn;
    Adapter adapter;
    List<Articles>  articles = new ArrayList<>();
    final String API_KEY = "YOUR api KEY GOES HERE";  //API key to newsapi website


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Mainly Required IDs
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchQuery = findViewById(R.id.searchQuery);
        searchBtn = findViewById(R.id.searchBtn);

        final String country = getCountry();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    retrieveJson("",country,API_KEY);
            }
        }); //Swipe to refresh layout

        retrieveJson("",country,API_KEY);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (searchQuery.getText().toString().equals("")){

                        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                retrieveJson("",country,API_KEY);
                            }
                        }); //Swipe to refresh layout

                        retrieveJson("",country,API_KEY);

                    }
                    else
                    {
                        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                retrieveJson(searchQuery.getText().toString(),country,API_KEY);
                            }
                        }); //Swipe to refresh layout

                        retrieveJson(searchQuery.getText().toString(),country,API_KEY);

                    }
            }
        });
    }

    public void retrieveJson(String query,String country, String apiKey){

        Call<Headlines> call;

        if (!searchQuery.getText().toString().equals("")){
            call = ApiClient.getInstance().getApi().getSpecificData(query,apiKey);
        }
        else
        {
            call = ApiClient.getInstance().getApi().getHeadlines(country,apiKey);
        }


        swipeRefreshLayout.setRefreshing(true); //So that refresh can be done when data is retrived.

        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if (response.isSuccessful() && response.body().getArticles() != null){
                    articles.clear();
                    articles = response.body().getArticles();
                    adapter = new Adapter(MainActivity.this,articles);
                    recyclerView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false); //So that refresh can't be done when data is retrived.
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false); //So that refresh can't be done when data is retrived.
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }


}
