package com.example.w0143446.pictureviewer;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.HashSet;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyListFragment extends ListFragment implements OnItemClickListener {

    //shared preferences stuff
    public static final String IMAGE_VIEWED_PREFS = "ImagesViewed";
    public  SharedPreferences settings;
    private Set<String> viewedImgSet;

    protected CustomListAdapter adapter;
    public Integer[] imageId = new Integer[7];
    String[] web = new String[7];

    //listener to notify when image selected
    OnImageSelectedListener imgSelectedListener = null;

    public interface OnImageSelectedListener {
        //called when an image is selected
        public void onImageSelected(int index);
    }

    public MyListFragment() {
        super();
        // Required empty public constructor
    }

    //not bothering with start override
    //not bothering with onCreate override
    public void setOnImageSelectedListener(OnImageSelectedListener listener){
        //tell fragment about activity listener
        imgSelectedListener = listener;
    }

    /*
    Did not implement loadData
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //deal with loading non-graphical stuff
        super.onCreate(savedInstanceState);


        //imagesList.add("Image 1 - baddass");
        //imagesList.add("Image 2 - wickedawesone");
        //imagesList.add("Image 3 - whatdatwah");

        imageId[0] = R.drawable.pic1;
        imageId[1] = R.drawable.pic2;
        imageId[2] = R.drawable.pic3;
        imageId[3] = R.drawable.pic4;
        imageId[4] = R.drawable.pic5;
        imageId[5] = R.drawable.pic6;
        imageId[6] = R.drawable.pic7;

        Resources res = getResources();
        web = res.getStringArray(R.array.image_name_arrays);

        // Restore preferences
        settings = this.getActivity().getPreferences(Context.MODE_PRIVATE); //0 = private
        //populate viewedImgSet with existing values from shared prefs
        viewedImgSet = settings.getStringSet(IMAGE_VIEWED_PREFS, new HashSet<String>()); //if there isn't already a property set, leave null?
        System.out.println("Loaded Shared Settings. there are: " + viewedImgSet.size() + " viewd imgs");
        adapter = new CustomListAdapter(this.getActivity(), web, imageId, (HashSet<String>) viewedImgSet); //convert string set to hashset
        //list = (ListView) findViewById(R.id.list);
        //list.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //somewhere here we need to override the simple listview object to allow an image text hybrid

        return inflater.inflate(R.layout.fragment_my_list, container, false);
    }

    /*
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }
    */

    /*
    Use the onAttach method to bind
    the fragment to the activity and add listener to activity
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            imgSelectedListener = (OnImageSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*
        if (imgSelectedListener != null) {
            imgSelectedListener.onImageSelected(position);
        }
        */
    }

    @Override
    public void onStart() {
        super.onStart();
        setListAdapter(adapter);
    }


    @Override
    /*
    Handle List Item Click...
     */
    public void onListItemClick(ListView l, View v, int position, long id) {
        imgSelectedListener.onImageSelected(position);
    }


    @Override
    public void onStop(){
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        //https://developer.android.com/guide/topics/data/data-storage.html#pref
        //settings = getPreferences(IMAGE_VIEWED_PREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(IMAGE_VIEWED_PREFS, adapter.getImageSet());
        editor.apply(); //switched to apply as per recommendations

        System.out.println("Stop Called. Saved");
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

    private void disableItems(ListView list, Set<String> itemSet) {
        //a function to loop through the Set from
        //shared settings and disable children from the listview

        for (String s : itemSet) {
            System.out.println("the item in the set is: " +s);
            list.getChildAt(Integer.parseInt(s)).setEnabled(false);
        }

    }

}
