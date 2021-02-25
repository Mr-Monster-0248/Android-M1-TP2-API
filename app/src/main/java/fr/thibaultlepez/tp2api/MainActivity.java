package fr.thibaultlepez.tp2api;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public ImageView flickrImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.flickrImg = (ImageView) findViewById(R.id.flickrImg);
    }

    public void getImageOnClickListener(View view) throws ExecutionException, InterruptedException, JSONException {
        AsyncFlickrJSONData flickr = new AsyncFlickrJSONData();
        flickr.execute();
        JSONObject flickrFeed = flickr.get();

        String imageURL = flickrFeed
                .getJSONArray("items")
                .getJSONObject(0)
                .getJSONObject("media")
                .getString("m");

        AsyncBitmapDownloader bitmapDownloader = new AsyncBitmapDownloader();
        bitmapDownloader.execute(imageURL);
        Bitmap image = bitmapDownloader.get();

        this.flickrImg.setImageBitmap(image);
    }
}