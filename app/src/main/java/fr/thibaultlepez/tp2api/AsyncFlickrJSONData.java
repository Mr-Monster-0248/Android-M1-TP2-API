package fr.thibaultlepez.tp2api;

import android.os.AsyncTask;
import android.util.Log;

import androidx.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncFlickrJSONData extends AsyncTask<String, Void, JSONObject> {

    private static JSONObject readStream(InputStream in) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = in.read();
            while (i != -1) {
                bo.write(i);
                i = in.read();
            }
            String result = bo.toString();

            Log.d("LEPEZ", result);


            result = result.substring(14);
            result = result.substring(0, result.length() - 1);

            return new JSONObject(result);

        } catch (IOException | JSONException e) {
            Log.e("ERROR", "fail to read stream");
            return new JSONObject();
        }
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        try {


            URL url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                 return readStream(in);
            } catch (IOException e) {
                e.printStackTrace();
                return new JSONObject();
            }


        } catch (IOException e) {
            e.printStackTrace();
            return new JSONObject();
        }

    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        Log.i("LEPEZ", jsonObject.toString());
    }
}
