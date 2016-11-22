package com.example.w0143446.anotherfragmenttest;

//import android.support.v4.app.Fragment;
//import android.support.v4.app.ListFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements myListFragment.OnImageSelectedListener {

    //shared preferences stuff
    //public static final String IMAGE_VIEWED_PREFS = "ImagesViewed";
    //public SharedPreferences settings;

    myListFragment mylistFragment;
    ImageFragment myimgFragment;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    // Whether or not we are in dual-pane mode
    boolean isDualPane = false;

    //image currently being displayed
    int imgIndex = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Restore preferences
        //settings = getPreferences(MODE_PRIVATE); //0 = private
        //populate viewedImgSet with existing values from shared prefs
        //Set<String> viewedImgSet = settings.getStringSet(IMAGE_VIEWED_PREFS, new HashSet<String>()); //if there isn't already a property set, leave null?
        //System.out.println("Loaded Shared Settings. there are: " + viewedImgSet.size() + " viewd imgs");

        //find our fragments
        mylistFragment = (myListFragment) getSupportFragmentManager().findFragmentById(
                R.id.list);
        myimgFragment = (ImageFragment) getSupportFragmentManager().findFragmentById(
                R.id.tvNote);
        // determine whether we are in single or dual pane
        View imageView = findViewById(R.id.image);
        isDualPane = imageView != null && imageView.getVisibility() == View.VISIBLE;

        //register ourselves as the listener for list fragment
        mylistFragment.setOnImageSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }

    //called when an image is selected
    @Override
    public void onImageSelected(int index) {

        if (!mylistFragment.adapter.isEnabled(index)) { //ignore disabled buttons
            return;
        }
        Toast.makeText(MainActivity.this, "You Clicked at " + index, Toast.LENGTH_SHORT).show();
        //create a new intent, pass image, send intent
        //Intent myIntent = new Intent(MainActivity.this, FullscreenActivity.class);

        //myIntent.putExtra("web", web[+ position]);
        //System.out.println(getResources(imageId[+ position]));
        //myIntent.putExtra("image", (Integer) imageId[position]);

        mylistFragment.adapter.setItemEnabled(index, false);

        System.out.println("Here is the numbers");
        for (int i=0;i< 8; i++){
            if (mylistFragment.adapter.isEnabled(i)){
                System.out.println(i);
            }
        }
        //startActivity(myIntent); //no result expected back
        myimgFragment.setText("Changed " + index);

    }

}
