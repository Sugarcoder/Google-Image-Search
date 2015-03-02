package com.sugarcoder.googleimagesearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;


public class ImageResult implements Serializable {            // Serializable makes the object capable of being encoded into the Intent extra system.
    public String fullUrl;
    private String thumbUrl;
    public String title;


    // This is a very common pattern that will be used often (model that parses the original items and array list of items).


    // new imageResult(...passing in the raw item JSON...)

    public ImageResult(JSONObject json) {
        try {
            this.fullUrl = json.getString("url");
            this.thumbUrl = json.getString("tbUrl");
            this.title = json.getString("title");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    /* Creating a method that returns the array list of image result.
       Name is called 'fromJsonArray' and will accept from 'JsonArray. */



    // Take an array of json images and return an arraylist of image results

    // imageResult.fromJSONArray([..., ..., ...] <= This passes in the array of JSON responses
    public static ArrayList<ImageResult> fromJSONArray(JSONArray array) {
        ArrayList<ImageResult> results = new ArrayList<>();        // Create a new array list



        /*  If the results are not invalid, we will add an item to the array list
            that is constructed using our new Json object constructor,
            grabbing the Json item out of the array of Json items. */


        for(int i = 0; i < array.length(); i++) {
            try {
                 results.add(new ImageResult(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }


    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }
}
