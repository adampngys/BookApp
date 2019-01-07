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

public class MyAdapter2 extends ArrayAdapter<Category> {

    private List<Category> items;
    int resource;

    public MyAdapter2(Context context, List<Category>items) {
        super(context, R.layout.row, items);
        this.resource = R.layout.row3;
        this.items = items;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(resource, null);
        Category category = items.get(position);
        if (category != null) {
            int[] dest = new int[]{R.id.textView1, R.id.textView2};
            String[] src = new String[]{"CategoryID", "Name"};
            for (int n = 0; n < dest.length; n++) {
                TextView txt = v.findViewById(dest[n]);
                txt.setText(category.get(src[n]));
                txt.setTextColor(Color.BLACK);
            }
        }

        /*new AsyncTask<String, Void, Bitmap>(){

            @Override
            protected Bitmap doInBackground(String...params) {
                return Book.getPhoto(params[0]);
            }

            @Override
            protected void onPostExecute(Bitmap result){
                ImageView image = (ImageView) v.findViewById(R.id.imageView);
                image.setImageBitmap(result);
            }
        }.execute(book.get("ISBN"));*/

        /*Bitmap bitmap = Book.getPhoto(book.get("ISBN"));
        ImageView image = (ImageView) v.findViewById(R.id.imageView);
        image.setImageBitmap(bitmap);*/

        int[] dest1 = new int[]{R.id.textViewLabel1, R.id.textViewLabel2};

        for(int i = 0; i < 2; i++){

            TextView txt = v.findViewById(dest1[i]);
            txt.setTextColor(Color.RED);
        }
        return v;
    }
}
