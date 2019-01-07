package com.example.adamp.bookapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=getIntent();
        String id=intent.getExtras().getString("CategoryID");


        new AsyncTask<String, Void, List<Book>>(){

            @Override
            protected List<Book> doInBackground(String... strings) {
                String categoryID=strings[0];
                return Book.ListBooksbyCategory(Integer.parseInt(categoryID));
            }

            @Override
            protected void onPostExecute(List<Book> result){
                MyAdapter adapter = new MyAdapter(getApplicationContext(), result);
                ListView list = (ListView) findViewById(R.id.listView1);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Book selected = (Book) parent.getAdapter().getItem(position);
                        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                        intent.putExtra("BookID", selected.get("BookID"));
                        startActivityForResult(intent, 123);
                    }
                });
            }
        }.execute(id);

        //StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
        //List<Book> data = Book.ReadBooks();
        //MyAdapter adapter = new MyAdapter(this, data);
/*      SimpleAdapter adapter = new SimpleAdapter(this, data,
                R.layout.row2,
                new String[]{"BookID", "Title", "CategoryID", "ISBN", "Author", "Stock", "Price"},
                new int[]{R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4,
                        R.id.textView5, R.id.textView6, R.id.textView7});*/
        //ListView list = (ListView) findViewById(R.id.listView1);
        //list.setAdapter(adapter);

        //list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //    @Override
        //    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //        Book selected = (Book) parent.getAdapter().getItem(position);
        //        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        //        intent.putExtra("BookID", selected.get("BookID"));
        //        startActivityForResult(intent, 123);
        //    }
        //});

/*      List<String> data = Book.ReadPlainTitles();
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
        ListView list = (ListView)findViewById(R.id.listView1);
        list.setAdapter(adapter);*/
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 123) {
            if (data.hasExtra("edited")) {
                int result = data.getExtras().getInt("edited");
                if (result == 1) {
                    recreate();
                }
            }
        }
    }
}
