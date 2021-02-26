package fr.thibaultlepez.tp2api;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
        if (convertView == null)
            convertView = _ctx.getLayoutInflater().inflate(R.layout.text_view_layout, parent, false);

        TextView imageUrl = (TextView) convertView.findViewById(R.id.imageUrl);
        imageUrl.setText(this.vector.get(position));

        return convertView;
    }
}
