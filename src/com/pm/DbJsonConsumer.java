package com.pm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

/**
 * Created by Preshoth on 2015-06-05.
 */
public class DbJsonConsumer {

    private ArrayList<URL> dbUrls_;
    private String json_;

    public DbJsonConsumer(String url) throws MalformedURLException, IOException, Exception {
        dbUrls_ = new ArrayList<URL>();
        System.out.println("\nGathering Dropbox Assets \n");
        readUrl(url);

        Gson gson = new Gson();
        Page page = gson.fromJson(json_, Page.class);

        for(Item item : page.items){
            System.out.println(" Dropbox Chooser Link: " + item.link);
            dbUrls_.add(new URL(item.link));
        }

    }

    public void readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            json_ =  buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    public ArrayList<URL> getDbUrls_() {
        return dbUrls_;
    }

    static class Item {
        String title;
        String link;
        String description;
    }

    static class Page {
        List<Item> items;
    }
}
