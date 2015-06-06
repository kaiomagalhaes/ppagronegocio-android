package com.kty.kaio.ppagronegocio_android;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchDataTask extends AsyncTask<String, Void, String[]> {

    private MainActivity mainActivity;
    private String BASE_URL = "http://ppagronegocio.herokuapp.com/json/graph-index-price.json";

    public FetchDataTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        handleProgressBar();
    }

    private void handleProgressBar() {
        ProgressBar progressBar = getProgressBar();
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();
    }

    protected String[] doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String json = null;
        try {
            json = getData(urlConnection, reader);
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }

        try {
            return getDataFromJson(json);
        } catch (JSONException e) {
            System.out.println("Error parsing json");
        }

        return null;
    }

    private String getData(HttpURLConnection urlConnection, BufferedReader reader) throws MalformedURLException, IOException {
        URL url = new URL(BASE_URL);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        InputStream inputStream = urlConnection.getInputStream();
        StringBuffer buffer = new StringBuffer();
        if (inputStream == null) {
            return null;
        }
        reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
        }

        if (buffer.length() == 0) {
            return null;
        }
        return buffer.toString();
    }

    protected void onPostExecute(String[] result) {
        if (result != null) {
            this.mainActivity.setReclyclerViewData(result);
        }
        getProgressBar().setVisibility(View.GONE);
    }

    private String[] getDataFromJson(String json)
            throws JSONException {

        JSONObject dataJson = new JSONObject(json);
        JSONArray months = dataJson.getJSONArray("months");

        int index = this.mainActivity.getPreferedMonth();
        int max = months.length() - 1;
        if (index > max) {
            index = max;
            this.mainActivity.setPreferedMonth(index);
        }
        JSONObject lastMonth = months.getJSONObject(index);


        JSONArray products = lastMonth.getJSONArray("products");
        String month = lastMonth.getString("month");

        String[] resultStrs = new String[products.length() + 2];
        resultStrs[0] = "Month: " + month;
        for (int i = 0; i < products.length(); i++) {
            JSONObject product = products.getJSONObject(i);
            String name = product.getString("name");
            String priceIndex = product.getString("priceIndex");
            resultStrs[i + 1] = name + " - " + priceIndex;
        }

        return resultStrs;
    }

    private ProgressBar getProgressBar() {
        return ((ProgressBar) this.mainActivity.findViewById(R.id.progressBar1));
    }

}
