package com.kty.kaio.ppagronegocio_android;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;


public class MainActivity extends ActionBarActivity {

    private ProgressBar spinner;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchData();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void setPreferedMonth(int preferedMonth){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().putInt("month", preferedMonth).apply();
    }

    public Integer getPreferedMonth() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return  sp.getInt("month", 0);
    }

    private void previousPreferedMonth() {
        Integer actual = getPreferedMonth();
        if (actual != 0) {
            setPreferedMonth(actual - 1);
        }
    }

    private void nextPreferedMonth() {
        setPreferedMonth(getPreferedMonth() + 1);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.previous_month) {
            previousPreferedMonth();
            fetchData();
            return true;
        }
        if (id == R.id.next_month) {
            nextPreferedMonth();
            fetchData();
            return true;
        }
        if (id == R.id.action_refresh) {
            fetchData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setReclyclerViewData(String[] products) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout_data_container, RecyclerViewFragment.newInstance(products))
                .commit();
    }

    private void fetchData() {
        FetchDataTask task = new FetchDataTask(this);
        task.execute();
    }

}
