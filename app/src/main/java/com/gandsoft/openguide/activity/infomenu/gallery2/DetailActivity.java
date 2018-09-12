package com.gandsoft.openguide.activity.infomenu.gallery2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gandsoft.openguide.R;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    public ArrayList<ImageModel> data = new ArrayList<>();
    int pos;

    Toolbar toolbar;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        data = getIntent().getParcelableArrayListExtra("data");
        pos = getIntent().getIntExtra("pos", 0);

        setTitle(data.get(pos).getName());


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), data);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setCurrentItem(pos);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitle(data.get(position).getName());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public ArrayList<ImageModel> data = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm, ArrayList<ImageModel> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position,
                    data.get(position).getName(),
                    data.get(position).getUrl(),
                    data.get(position).getCaption(),
                    data.get(position).getLike(),
                    data.get(position).getStatlike(),
                    data.get(position).getTotcom());
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return data.get(position).getName();
        }
    }

    public static class PlaceholderFragment extends Fragment {

        String name, url, caption,statlike,like,totcom;
        int pos;
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_IMG_TITLE = "image_title";
        private static final String ARG_IMG_URL = "image_url";
        private static final String ARG_IMG_LIKE = "image_like";
        private static final String ARG_IMG_STATLIKE = "image_statlike";
        private static final String ARG_IMG_CAPTION = "image_caption";
        private static final String ARG_IMG_TOTCOM = "image_totcom";


        @Override
        public void setArguments(Bundle args) {
            super.setArguments(args);
            this.pos = args.getInt(ARG_SECTION_NUMBER);
            this.name = args.getString(ARG_IMG_TITLE);
            this.url = args.getString(ARG_IMG_URL);
            this.caption = args.getString(ARG_IMG_CAPTION);
            this.like = args.getString(ARG_IMG_LIKE);
            this.statlike = args.getString(ARG_IMG_STATLIKE);
            this.totcom = args.getString(ARG_IMG_TOTCOM);

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, String name, String url,String caption,String like, String statlike,String totcom) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(ARG_IMG_TITLE, name);
            args.putString(ARG_IMG_URL, url);
            args.putString(ARG_IMG_CAPTION, caption);
            args.putString(ARG_IMG_LIKE, like);
            args.putString(ARG_IMG_STATLIKE, statlike);
            args.putString(ARG_IMG_TOTCOM, totcom);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public void onStart() {
            super.onStart();

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            final TextView mUsername = rootView.findViewById(R.id.username);
            final TextView mCaption = rootView.findViewById(R.id.caption);
            final TextView mLike = rootView.findViewById(R.id.likee);
            final TextView mStatusLike = rootView.findViewById(R.id.statuslikee);
            final TextView mTotCom = rootView.findViewById(R.id.totcom);
            final ImageView imageView = (ImageView) rootView.findViewById(R.id.detail_image);

            mUsername.setText(name);
            mCaption.setText(caption);
            mLike.setText(like);
            mStatusLike.setText(statlike);
            mTotCom.setText(totcom);

            Glide.with(getActivity())
                    .load(url)
                    .thumbnail(0.1f)
                    .into(imageView);

            return rootView;
        }

    }
}
