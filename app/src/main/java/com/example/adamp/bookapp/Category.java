package com.example.adamp.bookapp;

import java.lang.reflect.Array;
import java.util.HashMap;

public class Category extends HashMap<String, String> {

    static final String[] categorynames = new String[]{"Children", "Finance", "Non Fiction", "Technical"};

    public Category(String categoryID, String name) {
        put("CategoryID", categoryID);
        put("Name", name);
    }
}
