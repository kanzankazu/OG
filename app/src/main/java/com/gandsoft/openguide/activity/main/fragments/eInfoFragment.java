package com.gandsoft.openguide.activity.main.fragments;

import android.accessibilityservice.AccessibilityService;
import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.gandsoft.openguide.MapsActivity;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.AccountActivity;
import com.gandsoft.openguide.activity.ChangeEventActivity;
import com.gandsoft.openguide.activity.infomenu.aMapActivity;
import com.gandsoft.openguide.activity.infomenu.bGalleryActivity;
import com.gandsoft.openguide.activity.infomenu.cInboxActivity;
import com.gandsoft.openguide.activity.infomenu.dComitteContactActivity;
import com.gandsoft.openguide.activity.infomenu.eEmergenciesActivity;
import com.gandsoft.openguide.activity.infomenu.fPracticalInfoActivity;
import com.gandsoft.openguide.activity.infomenu.gSurroundingAreaActivity;
import com.gandsoft.openguide.activity.infomenu.hFeedbackActivity;
import com.gandsoft.openguide.activity.main.BaseHomeActivity;


/**
 * Example about replacing fragments inside a ViewPager. I'm using
 * android-support-v7 to maximize the compatibility.
 * 
 * @author Dani Lao (@dani_lao)
 * 
 */
public class eInfoFragment extends Fragment {
	ListView listView;
	Button bMyPro;
	ArrayAdapter<String> adapter;
	String infomenu[]={
		"Map",
		"Gallery",
		"Inbox",
		"Comitee Contact",
		"Emergencies",
		"Practical Information",
		"Surrounding Area",
		"Feedback",
		"Change Event"
	};


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater
				.inflate(R.layout.fragment_e_info, container, false);

        final Button button = (Button) view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(),AccountActivity.class);
            startActivity(intent);
        }
    });
    listView = (ListView) view.findViewById(R.id.lvMenu);
    adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
				android.R.layout.simple_list_item_1, infomenu);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
				String infomenu2 = (String) listView.getAdapter().getItem(position);

                if (infomenu2.equals("Map")) {
                    Intent intent = new Intent(listView.getContext(), MapsActivity.class);
                    listView.getContext().startActivity(intent);
                }
                else if(infomenu2.equals("Gallery")) {
                    Intent intent = new Intent(listView.getContext(), bGalleryActivity.class);
                    listView.getContext().startActivity(intent);
                }
                else if(infomenu2.equals("Inbox")) {
                    Intent intent = new Intent(listView.getContext(), cInboxActivity.class);
                    listView.getContext().startActivity(intent);
                }
                else if(infomenu2.equals("Comitte Contact")) {
                    Intent intent = new Intent(listView.getContext(), dComitteContactActivity.class);
                    listView.getContext().startActivity(intent);
                }
                else if(infomenu2.equals("Emergencies")) {
                    Intent intent = new Intent(listView.getContext(), eEmergenciesActivity.class);
                    listView.getContext().startActivity(intent);
                }
                else if(infomenu2.equals("Practical Information")) {
                    Intent intent = new Intent(listView.getContext(),fPracticalInfoActivity.class);
                    listView.getContext().startActivity(intent);
                }
                else if(infomenu2.equals("Surrounding Area")) {
                    Intent intent = new Intent(listView.getContext(),gSurroundingAreaActivity.class);
                    listView.getContext().startActivity(intent);
                }
                else if(infomenu2.equals("Feedback")) {
                    Intent intent = new Intent(listView.getContext(),hFeedbackActivity.class);
                    listView.getContext().startActivity(intent);
                }
                else if(infomenu2.equals("Change Event")) {
                    Intent intent = new Intent(listView.getContext(),ChangeEventActivity.class);
                    listView.getContext().startActivity(intent);
                }
			}
		});
		return view;
	}
}

