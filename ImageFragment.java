package com.example.w0143446.pictureviewer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {
    // The image we are to display
    String image = null;
    TextView tvNote;
    ImageView ivImage;

    public ImageFragment() {
        // Required empty public constructor
    }

    //take arguments in instance
    public static ImageFragment newInstance(int index) {
        ImageFragment f = new ImageFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //tvNote = (TextView) getView().findViewById(R.id.tvNote);
        System.out.println("onCreateView called");

        return inflater.inflate(R.layout.fragment_image, container, false);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("onActivityCreated called");
        tvNote = (TextView) getView().findViewById(R.id.tvNote); //throws cannot cast LinearLayout to TextView exception
        ivImage = (ImageView) getView().findViewById(R.id.image);

    }

    public void setText(String text) {
        tvNote.setText(text);
    }
    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

}
