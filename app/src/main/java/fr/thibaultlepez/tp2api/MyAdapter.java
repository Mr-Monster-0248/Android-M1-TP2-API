package fr.thibaultlepez.tp2api;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.Vector;

public class MyAdapter extends BaseAdapter {

    private Activity _ctx;
    private Vector<String> vector;

    public MyAdapter(Activity context) {
        this.vector = new Vector<>();
        this._ctx = context;
    }

    public void add(String url) {
        this.vector.add(url);
    }

    @Override
    public int getCount() {
        return this.vector.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RequestQueue queue = MySingleton.getInstance(parent.getContext()).getRequestQueue();

        if (convertView == null)
            convertView = _ctx.getLayoutInflater().inflate(R.layout.bitmap_layout, parent, false);

        ImageView image = (ImageView) convertView.findViewById(R.id.bitmap);
        ImageRequest request = new ImageRequest(
                this.vector.get(position),
                response -> image.setImageBitmap(response),
                0,
                0,
                ImageView.ScaleType.CENTER_CROP,
                Bitmap.Config.RGB_565,
                error -> error.printStackTrace()
        );

        queue.add(request);

//        =====  text layout =====
//
//        if (convertView == null)
//            convertView = _ctx.getLayoutInflater().inflate(R.layout.text_view_layout, parent, false);
//
//        TextView imageUrl = (TextView) convertView.findViewById(R.id.imageUrl);
//        imageUrl.setText(this.vector.get(position));

        return convertView;
    }
}
