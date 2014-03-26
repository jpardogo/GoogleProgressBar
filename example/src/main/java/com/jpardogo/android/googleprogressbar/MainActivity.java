package com.jpardogo.android.googleprogressbar;

import android.app.ListActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ListActivity {

    private int LIST_ITEM_COUNT=40;
    @InjectView(R.id.google_progress)
    GoogleProgressBar mProgressBar;
    private boolean isRefreshing=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        refresh();

    }

    private void refresh() {
        isRefreshing=true;
        getListView().setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        getListView().postDelayed(new Runnable() {
            @Override
            public void run() {
                isRefreshing =false;
                getListView().setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                setListAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.item_list, getListItem()));

            }
        },4000);
    }

    private ArrayList<String> getListItem() {
        ArrayList<String> list = new ArrayList<String>();
        for(int i=0;i<LIST_ITEM_COUNT;i++){
            list.add("Position: "+i);
        }
        return list;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            if(!isRefreshing)refresh();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
