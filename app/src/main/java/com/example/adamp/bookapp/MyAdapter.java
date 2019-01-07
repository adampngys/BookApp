package com.example.adamp.bookapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends ArrayAdapter<Book> {

    private List<Book> items;
    int resource;

    public MyAdapter(Context context, List<Book> items) {
        super(context, R.layout.row, items);
        this.resource = R.layout.row2;
        this.items = items;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(resource, null);
        Book book = items.get(position);
        if (book != null) {
            int[] dest = new int[]{R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4,
                    R.id.textView5, R.id.textView6, R.id.textView7};
            String[] src = new String[]{"BookID", "Title", "CategoryID", "ISBN", "Author", "Stock", "Price"};
            for (int n = 0; n < dest.length; n++) {
                TextView txt = v.findViewById(dest[n]);
                txt.setText(book.get(src[n]));
                txt.setTextColor(Color.BLACK);

                if (n == dest.length - 1) {
                    Locale locale = Locale.US;
                    NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                    double bookPrice = Double.parseDouble(book.get(src[n]));
                    txt.setText(fmt.format(bookPrice));
                }
            }
        }

        new AsyncTask<String, Void, Bitmap>(){

            @Override
            protected Bitmap doInBackground(String...params) {
                return Book.getPhoto(params[0]);
            }

            @Override
            protected void onPostExecute(Bitmap result){
                ImageView image = (ImageView) v.findViewById(R.id.imageView);
                image.setImageBitmap(result);
            }
        }.execute(book.get("ISBN"));

        /*Bitmap bitmap = Book.getPhoto(book.get("ISBN"));
        ImageView image = (ImageView) v.findViewById(R.id.imageView);
        image.setImageBitmap(bitmap);*/

        int[] dest1 = new int[]{R.id.textViewLabel1, R.id.textViewLabel2, R.id.textViewLabel3, R.id.textViewLabel4,
                R.id.textViewLabel5, R.id.textViewLabel6, R.id.textViewLabel7};

        for(int i = 0; i < 7; i++){

            TextView txt = v.findViewById(dest1[i]);
            txt.setTextColor(Color.RED);
        }
        return v;
    }
}
