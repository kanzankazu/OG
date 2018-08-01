package com.gandsoft.openguide.activity.infomenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.infomenu.adapter.InboxRecViewAdapter;
import com.gandsoft.openguide.activity.infomenu.adapter.InboxRecViewPojo;
import com.gandsoft.openguide.activity.main.adapter.PostRecViewAdapter;
import com.gandsoft.openguide.activity.main.adapter.PostRecViewPojo;

import java.security.AccessController;
import java.util.ArrayList;

public class cInboxActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    InboxRecViewAdapter adapter;
    private ArrayList<InboxRecViewPojo> listContentArr= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_inbox);

        recyclerView=(RecyclerView)findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter=new InboxRecViewAdapter((this));
        //Method call for populating the view
        populateRecyclerViewValues();
    }

    private void populateRecyclerViewValues() {
        /** This is where we pass the data to the adpater using POJO class.
         *  The for loop here is optional. I've just populated same data for 50 times.
         *  You can use a JSON object request to gather the required values and populate in the
         *  RecyclerView.
         * */
        for(int iter=0;iter<=50;iter++) {
            //Creating POJO class object
            InboxRecViewPojo pojoObject = new InboxRecViewPojo();
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
