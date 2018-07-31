package com.gandsoft.openguide.activity.main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gandsoft.openguides2.R;
import com.gandsoft.openguides2.adapter.PostRecViewAdapter;
import com.gandsoft.openguides2.adapter.PostRecViewPojo;

import java.util.ArrayList;

public class aHomeFragment extends Fragment {
    public aHomeFragment(){}
    RecyclerView recyclerView;
    PostRecViewAdapter adapter;
    private ArrayList<PostRecViewPojo> listContentArr= new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_home, container, false);
        Button bCamera = (Button) view.findViewById(R.id.bHomeWritePostCamera);
        bCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
            }
        });

        recyclerView=(RecyclerView)view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        adapter=new PostRecViewAdapter(container.getContext());
        //Method call for populating the view
        populateRecyclerViewValues();
        return view;
    }

    private void populateRecyclerViewValues() {
        /** This is where we pass the data to the adpater using POJO class.
         *  The for loop here is optional. I've just populated same data for 50 times.
         *  You can use a JSON object request to gather the required values and populate in the
         *  RecyclerView.
         * */
        for(int iter=0;iter<=50;iter++) {
            //Creating POJO class object
            PostRecViewPojo pojoObject = new PostRecViewPojo();
            //Values are binded using set method of the POJO class
            pojoObject.setName("User Name");
            pojoObject.setContent("Content, number: "+iter);
            pojoObject.setTime("Time");
            //After setting the values, we add all the Objects to the array
            //Hence, listConentArr is a collection of Array of POJO objects
            listContentArr.add(pojoObject);
        }
        //We set the array to the adapter
        adapter.setListContent(listContentArr);
        //We in turn set the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);
    }
}
