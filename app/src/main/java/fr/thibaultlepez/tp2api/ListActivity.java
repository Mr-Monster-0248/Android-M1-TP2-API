package fr.thibaultlepez.tp2api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class ListActivity extends AppCompatActivity {

    private MyAdapter _myAdapter = new MyAdapter(this);
    private ListView _pictureList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        this._pictureList = findViewById(R.id.imageList);
        this._pictureList.setAdapter(_myAdapter);

        AsyncFlickrJSONDataForList flickrLoader = new AsyncFlickrJSONDataForList(_myAdapter);
        flickrLoader.execute();
    }
}