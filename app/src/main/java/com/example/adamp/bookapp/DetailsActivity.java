package com.example.adamp.bookapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        String id = intent.getStringExtra("BookID");

        new AsyncTask<String, Void, Book>() {

            @Override
            protected Book doInBackground(String... params) {
                return Book.ReadBookID(params[0]);
            }

            @Override
            protected void onPostExecute(Book result) {
                show(result);
            }
        }.execute(id);

/*      super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        String id = intent.getStringExtra("BookID");

        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
        Book b = Book.ReadBookID(id);
        show(b);*/
    }

    void show(Book b) {
        int[] dest = new int[]{R.id.editText1, R.id.editText2, R.id.editText3, R.id.editText4,
                R.id.editText5, R.id.editText6, R.id.editText7};
        String[] src = new String[]{"BookID", "Title", "CategoryID", "ISBN", "Author", "Stock", "Price"};

        for (int n = 0; n < dest.length; n++) {
            TextView txt = findViewById(dest[n]);
            txt.setText(b.get(src[n]));

/*            if (n == dest.length - 1) {
                Locale locale = Locale.US;
                NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                double bookPrice = Double.parseDouble(b.get(src[n]));
                txt.setText(fmt.format(bookPrice));
            }*/
        }

        new AsyncTask<String, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(String... params) {
                return Book.getPhoto(params[0]);
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                ImageView image = (ImageView) findViewById(R.id.imageViewc);
                image.setImageBitmap(result);
            }
        }.execute(b.get("ISBN"));

/*        Bitmap bitmap = Book.getPhoto(b.get("ISBN"));
        ImageView image = (ImageView) findViewById(R.id.imageViewc);
        image.setImageBitmap(bitmap);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save: {
                saveEdit();
                Intent data = new Intent();
                data.putExtra("edited", 1);
                setResult(RESULT_OK, data); // we have finished this Activity
                finish();
                return true;
            }
            case R.id.cancel:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void saveEdit() {
        int[] src = new int[]{R.id.editText1, R.id.editText2, R.id.editText3, R.id.editText4,
                R.id.editText5, R.id.editText6, R.id.editText7};

        String[] dest = new String[7];

        for (int n = 0; n < dest.length; n++) {

            if (n < 5) {
                TextView txt = findViewById(src[n]);
                dest[n] = txt.getText().toString();
            } else {
                EditText txt = findViewById(src[n]);
                dest[n] = txt.getText().toString();
            }
        }
        Book b = new Book(dest[0], dest[1], dest[2], dest[3], dest[4], dest[5], dest[6]);
        //Book.saveBook(b, false);
        new AsyncTask<Book, Void, Void>() {

            @Override
            protected Void doInBackground(Book... params) {
                Book.saveBook(params[0], false);
                return null;
            }

        }.execute(b);
    }
}
