package com.example.w0143446.anotherfragmenttest;

//import android.support.v4.app.Fragment;
//import android.support.v4.app.ListFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

    private Animation myAnimation;

    //image currently being displayed
    int imgIndex = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // determine whether we are in single or dual pane
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        if (width > 1000) {
            isDualPane = true;
            setContentView(R.layout.activity_main);
            myimgFragment = (ImageFragment) getSupportFragmentManager().findFragmentById(
                    R.id.imageLayout); //tvNote
        }
        else {
            setContentView(R.layout.activity_main_single);
        }

        Toast.makeText(MainActivity.this, "Your screen width = " + width +
                ", your height is " + height, Toast.LENGTH_SHORT).show();
        //find our fragments
        mylistFragment = (myListFragment) getSupportFragmentManager().findFragmentById(
                R.id.list);


        //register ourselves as the listener for list fragment
        mylistFragment.setOnImageSelectedListener(this);
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


        if (isDualPane) {
            mylistFragment.adapter.setItemEnabled(index, false);

            System.out.println("Here is the numbers");
            for (int i=0;i< 8; i++){
                if (mylistFragment.adapter.isEnabled(i)){
                    System.out.println(i);
                }
            }
            ImageView imageView = myimgFragment.ivImage;
            //imageView.setImageResource(R.drawable.my_image);
            Drawable img = ContextCompat.getDrawable(getApplicationContext(),mylistFragment.imageId[index]);
            imageView.setImageDrawable(img);
            animateImage(imageView);

            TextView tvNote = (TextView) findViewById(R.id.tvNote);
            tvNote.setText("This is a view of image " + index);

        }
        else {
            //create a new intent, pass image, send intent
            Intent myIntent = new Intent(MainActivity.this, FullscreenActivity.class);

            myIntent.putExtra("web", mylistFragment.web[index]);
            myIntent.putExtra("image", (Integer) mylistFragment.imageId[index]);
            //System.out.println(getResources(imageId[+ position]));
            //myIntent.putExtra("image", (Integer) imageId[position]);
            startActivity(myIntent); //no result expected back
            }
    }

    public void animateImage(ImageView imageView) {
        myAnimation = AnimationUtils.loadAnimation(this, R.anim.fade);
        imageView.startAnimation(myAnimation);
    }//end wave flag function

}
