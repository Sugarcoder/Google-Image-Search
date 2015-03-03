package com.sugarcoder.googleimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sugarcoder.googleimagesearch.R;

import com.sugarcoder.googleimagesearch.models.ImageResult;

import java.util.List;


// Adapter = Bridges the data and turn the data into views, then rendered into the View.

public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {
    private View view;

    public ImageResultsAdapter(Context context, List<ImageResult> images) {
        super(context, R.layout.image_result, images);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        ImageResult imageInfo = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_result, parent, false);

        }


        // Look up the data

        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);       // This will give us access to the ImageView within the template.
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);


        // Clear out the image from last time
        ivImage.setImageResource(0);


        // Popular the title and remote image Url
        // This is the Java version => tvTitle.setText(imageInfo.title);

        tvTitle.setText(Html.fromHtml(imageInfo.title));


        // Remotely download the image data in the background (with Picasso)
        Picasso.with(getContext()).load(imageInfo.getThumbUrl()).into(ivImage);


        // Return the completed view to be displayed
        return convertView;
    }


}

