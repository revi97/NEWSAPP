package revanth.com.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Detailed extends AppCompatActivity {

    TextView tvTitle, tvSource,tvTime,tvDesc;
     ImageView imageViews;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        tvTitle = findViewById(R.id.tvId);
        tvDesc = findViewById(R.id.tvDesc);
        tvTime = findViewById(R.id.tvDate);
        tvSource = findViewById(R.id.tvSource);
        imageViews = findViewById(R.id.imageView);
        webView = findViewById(R.id.webView);


        Intent intent =getIntent();
        String title = intent.getStringExtra("title");
        String source = intent.getStringExtra("source");
        String time= intent.getStringExtra("time");
        String desc = intent.getStringExtra("desc");
        String imageUrl= intent.getStringExtra("imageUrl");
        String url = intent.getStringExtra("url");


        tvTitle.setText(title);
        tvSource.setText(source);
        tvTime.setText(time);
        tvDesc.setText(desc);

        Picasso.get().load(imageUrl).into(imageViews); //getting image from the web to display

        //For webView purposes of getting everything from the site
        webView.getSettings().getDomStorageEnabled();
        webView.getSettings().getJavaScriptEnabled();  //JS for the proper look of the website
        webView.getSettings().setLoadsImagesAutomatically(true); //to get the image from the site automatically


        //setting scroll option for the whole web page.
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);  //This loads the url from with the help of API access









    }
}
