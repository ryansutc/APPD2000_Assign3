package com.example.w0143446.pictureviewer;


import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MyListFragment.OnImageSelectedListener {

    //Fragment Parts
    MyListFragment mylistFragment;
    ImageFragment myimgFragment;

    // Whether or not we are in dual-pane mode
    boolean isDualPane = false;

    private Animation myAnimation;
    //image currently being displayed
    int imgIndex = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // if screen width is more than 1000 pixels, use dual pane
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int densityDpi = metrics.densityDpi;


        System.out.println("Your screen width = " + width/ densityDpi + ", your height is " + height/ densityDpi);
        if (width / densityDpi > 2) {
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
        mylistFragment = (MyListFragment) getSupportFragmentManager().findFragmentById(
                R.id.list);

        //register ourselves as the listener for list fragment
        mylistFragment.setOnImageSelectedListener(this);
    }

    //called when an image is selected in myFragmentList
    @Override
    public void onImageSelected(int index) {

        if (!mylistFragment.adapter.isEnabled(index)) { //ignore disabled buttons
            return;
        }
        Toast.makeText(MainActivity.this, "You Clicked at " + index, Toast.LENGTH_SHORT).show();
        //remember item in list
        mylistFragment.adapter.setItemEnabled(index, false);
        //if dual view, we have two fragments to set up directly
        if (isDualPane) {

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
        //if single pane view, we have to pass intent info to new activity
        else {
            //create a new intent, pass image, send intent
            Intent myIntent = new Intent(MainActivity.this, FullscreenActivity.class);

            myIntent.putExtra("web", mylistFragment.web[index]);
            myIntent.putExtra("image", (Integer) mylistFragment.imageId[index]);
            startActivity(myIntent); //no result expected back
        }
    }

    //do some animation on loading the image
    public void animateImage(ImageView imageView) {
        myAnimation = AnimationUtils.loadAnimation(this, R.anim.fade);
        imageView.startAnimation(myAnimation);
    }//end wave flag function

}
