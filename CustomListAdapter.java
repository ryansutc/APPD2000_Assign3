package com.example.w0143446.pictureviewer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class CustomListAdapter extends ArrayAdapter<String> implements ListAdapter{
    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;
    private Set<String> viewedImgSet;

    public CustomListAdapter(Activity context, String[] web, Integer[] imageId, HashSet<String> viewedImg) {
        super(context, R.layout.customlist_single, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.viewedImgSet = viewedImg;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.customlist_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web[position]);
        imageView.setImageResource(imageId[position]);
        return rowView;
    }

    @Override
    public boolean isEnabled(int position) {
        //System.out.println("my custom isEnabled button was triggered");
        if (viewedImgSet.contains(String.valueOf(position))){
            return false;
        }
       else {
            return true;
        }
    }

    public void setItemEnabled(int position, boolean enabled) {
        /*a custom function to toggle a particular
        list item to be enabled or disabled
        */
        String strPosition = String.valueOf(position);
        if (!enabled){
            //set should take care of duplicates
            this.viewedImgSet.add(strPosition);
        }
        else {
            this.viewedImgSet.remove(strPosition);
        }
    }
    public Set<String> getImageSet(){
        /* Getter: Return the set of the images as a hashset
         used to put the values back in the settings
         */

        System.out.println("Returned the Set");
        return this.viewedImgSet;
    }
}

