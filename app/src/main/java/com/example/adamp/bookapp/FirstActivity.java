package com.example.adamp.bookapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;

public class FirstActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        new AsyncTask<Void, Void, List<Category>>() {
            @Override
            protected List<Category> doInBackground(Void... voids) {
                List<Category> categories = Book.ListCategories();
                return categories;
            }

            @Override
            protected void onPostExecute(List<Category> categories) {
                setListAdapter(
                        new SimpleAdapter(getApplicationContext(),
                                categories,
                                R.layout.row3,
                                new String[]{"CategoryID", "Name"},
                                new int[]{R.id.textView1, R.id.textView2}));
            }
        }.execute();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Category c = (Category) getListAdapter().getItem(position);
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("CategoryID", c.get("CategoryID"));
        startActivity(i);
    }
}
