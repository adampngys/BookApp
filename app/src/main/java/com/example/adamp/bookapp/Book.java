package com.example.adamp.bookapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Book extends HashMap<String, String> {
    static String host = "192.168.0.109";
    static String baseURL;
    static String imageURL;

    static {
        baseURL = String.format("http://%s/MyBookStore/api/BookList", host);
        /*baseURL = String.format("http://%s/MyBookStore/api/Book", host);*/
        imageURL = String.format("http://%s/MyBookStore/Images", host);
    }

    public Book(String bookid, String title, String categoryid, String isbn, String author, String stock, String price) {
        put("BookID", bookid);
        put("Title", title);
        put("CategoryID", categoryid);
        put("ISBN", isbn);
        put("Author", author);
        put("Stock", stock);
        put("Price", price);
    }

/*    public static List<String> ReadPlainTitles(){
        List<String> list = new ArrayList<String>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL);
            for (int i = 0; i < a.length(); i++) {
                String b = a.getString(i);
                list.add(b);
            }
        } catch(Exception e){
            Log.e("PlainTitles", "JSONArray error");
        }
        return list;
    }*/

    public static List<Book> ReadBooks() {
        List<Book> list = new ArrayList<Book>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL);
        try {
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new Book(b.getString("BookID"),
                        b.getString("Title"),
                        b.getString("CategoryID"),
                        b.getString("ISBN"),
                        b.getString("Author"),
                        b.getString("Stock"),
                        b.getString("Price")));
            }
        } catch (Exception e) {
            Log.e("BookList", "JSONArray error");
        }
        return list;
    }

    public static Book ReadBookID(String id) {
        try {
            JSONObject a = JSONParser.getJSONFromUrl(String.format("%s/%s", baseURL, id));
            Book b = new Book(a.getString("BookID"),
                    a.getString("Title"),
                    a.getString("CategoryID"),
                    a.getString("ISBN"),
                    a.getString("Author"),
                    a.getString("Stock"),
                    a.getString("Price"));
            return b;
        } catch (Exception e) {
            Log.e("Book", "JSONArray error");
        }
        return null;
    }

    public static Bitmap getPhoto(String isbn) {
        try {
            URL url = new URL(String.format("%s/%s.jpg", imageURL, isbn));
            URLConnection conn = url.openConnection();
            InputStream ins = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(ins);
            ins.close();
            return bitmap;
        } catch (Exception e) {
            Log.e("Book.getPhoto()", "Bitmap error");
        }
        return null;
    }

    public static void saveBook(Book b, boolean isNew) {
        JSONObject jemp = new JSONObject();
        String theID = b.get("BookID");
        try {
            jemp.put("BookID", b.get("BookID"));
            jemp.put("Title", b.get("Title"));
            jemp.put("CategoryID", b.get("CategoryID"));
            jemp.put("ISBN", b.get("ISBN"));
            jemp.put("Author", b.get("Author"));
            jemp.put("Stock", b.get("Stock"));
            jemp.put("Price", b.get("Price"));
        } catch (Exception e) {
        }
        if (isNew) JSONParser.postStream(baseURL + "/add", jemp.toString());
        else JSONParser.postStream(baseURL + "/" + theID, jemp.toString());
    }

    public static List<Category> ListCategories(){
        List<Category> list = new ArrayList<Category>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL + "/Category");
        try {
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new Category(b.getString("CategoryID"),
                        b.getString("Name")));

            }
        } catch (Exception e) {
            Log.e("CategoryList", "JSONArray error");
        }
        return list;
    }


    public static List<Book> ListBooksbyCategory(int categoryID){
            List<Book> list = new ArrayList<Book>();
            JSONArray a = JSONParser.getJSONArrayFromUrl(String.format("%s/%s", baseURL + "/Category", categoryID));
            try {
                for (int i = 0; i < a.length(); i++) {
                    JSONObject b = a.getJSONObject(i);
                    list.add(new Book(b.getString("BookID"),
                            b.getString("Title"),
                            b.getString("CategoryID"),
                            b.getString("ISBN"),
                            b.getString("Author"),
                            b.getString("Stock"),
                            b.getString("Price")));
                }
            } catch (Exception e) {
                Log.e("ListBooksbyCategory", "JSONArray error");
            }
            return list;
    }
}
