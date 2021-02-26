package fr.thibaultlepez.tp2api.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.thibaultlepez.tp2api.AsyncBitmapDownloader;
import fr.thibaultlepez.tp2api.AsyncFlickrJSONData;
import fr.thibaultlepez.tp2api.R;

public class MainActivity extends AppCompatActivity {

    public ImageView flickrImg;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.flickrImg = (ImageView) findViewById(R.id.flickrImg);

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    1
            );

        }
        this.location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.i("LEPEZ", "location: " + location.toString());

    }

    public void getImageOnClickListener(View view) throws ExecutionException, InterruptedException, JSONException {

        SharedPreferences preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);
        String keyword = preferenceManager.getString("searchKeyword", "cat");

        Log.d("LEPEZ", keyword);

        String stringUrl = "https://api.flickr.com/services/rest/?" +
                "method=flickr.photos.search" +
                "&license=4" +
                "&api_key=" + PreferenceManager.getDefaultSharedPreferences(this).getString("APIkey", "e69ac742344c7aea5ac8c126b8cdc72f") +
                "&has_geo=1&lat=" +  this.location.getLatitude()+
                "&lon=" + this.location.getLongitude() +
                "&tags=" + keyword +
                "&per_page=1&format=json";

        AsyncFlickrJSONData flickr = new AsyncFlickrJSONData();
        flickr.execute(stringUrl);
        JSONObject flickrFeed = flickr.get();

        JSONObject flickImage = flickrFeed
                .getJSONObject("photos")
                .getJSONArray("photo")
                .getJSONObject(0);
        String imageURL = "https://live.staticflickr.com/" +
                flickImage.getString("server") +
                "/" + flickImage.getString("id") +
                "_" + flickImage.getString("secret") +
                ".jpg";

        AsyncBitmapDownloader bitmapDownloader = new AsyncBitmapDownloader();
        bitmapDownloader.execute(imageURL);
        Bitmap image = bitmapDownloader.get();

        this.flickrImg.setImageBitmap(image);
    }

    public void goToList(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void goToSetting(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}