package fr.thibaultlepez.tp2api.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import fr.thibaultlepez.tp2api.AsyncFlickrJSONDataForList;
import fr.thibaultlepez.tp2api.MyAdapter;
import fr.thibaultlepez.tp2api.R;

public class ListActivity extends AppCompatActivity {

    private MyAdapter _myAdapter = new MyAdapter(this);
    private ListView _pictureList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        this._pictureList = findViewById(R.id.imageList);
        this._pictureList.setAdapter(_myAdapter);

        SharedPreferences preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);
        String keyword = preferenceManager.getString("searchKeyword", "cat");

        AsyncFlickrJSONDataForList flickrLoader = new AsyncFlickrJSONDataForList(_myAdapter);
        flickrLoader.execute(keyword);
    }
}