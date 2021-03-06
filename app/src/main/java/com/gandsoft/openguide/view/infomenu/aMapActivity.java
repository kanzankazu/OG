package com.gandsoft.openguide.view.infomenu;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.API.APIresponse.Event.EventPlaceList;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelperMethod;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.PictureUtil;
import com.gandsoft.openguide.support.SessionUtil;
import com.gandsoft.openguide.support.SystemUtil;
import com.gandsoft.openguide.view.LocalBaseActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class aMapActivity extends LocalBaseActivity implements OnMapReadyCallback {
    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;
    private static final int ALL_PERMISSION = 1;
    SQLiteHelperMethod db = new SQLiteHelperMethod(this);
    private CardView cvMapsMarkerTitlefvbi, cvMapsFocusEventfvbi;
    private TextView tvMapsMarkerTitlefvbi;
    private TextView tvMapsFocusEventfvbi;
    private RelativeLayout rlMapsOnlinefvbi;
    private RelativeLayout rlMapsOfflinefvbi;
    private ImageView ivMapsOfflinefvbi;
    private GoogleMap mMap;
    private double mylat;
    private double mylng;
    private LatLng mylatlng;
    private Toolbar toolbar;
    private ActionBar ab;
    private String accountId, eventId;
    private ArrayList<EventPlaceList> placeLists = new ArrayList<>();
    private LatLngBounds.Builder builder;
    private LatLngBounds bounds;
    private MarkerOptions marker;
    private CameraUpdate cameraUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_a_map);

        accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);

        Nammu.init(this);

        initPermission();
        initMaps();
        initComponent();
        initContent();
        initListener();
        //initStaticMaps();
    }

    private void initMaps() {
        setRequestPermissionGPS();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        cvMapsFocusEventfvbi = (CardView) findViewById(R.id.cvMapsFocusEvent);
        tvMapsFocusEventfvbi = (TextView) findViewById(R.id.tvMapsFocusEvent);
        cvMapsMarkerTitlefvbi = (CardView) findViewById(R.id.cvMapsMarkerTitle);
        tvMapsMarkerTitlefvbi = (TextView) findViewById(R.id.tvMapsMarkerTitle);

        rlMapsOnlinefvbi = (RelativeLayout) findViewById(R.id.rlMapsOnline);
        rlMapsOfflinefvbi = (RelativeLayout) findViewById(R.id.rlMapsOffline);
        ivMapsOfflinefvbi = (ImageView) findViewById(R.id.ivMapsOffline);
    }

    private void initContent() {
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("Maps");

        String staticMaps = db.getPlaceList(eventId).get(0).getStaticMaps();

        if (NetworkUtil.isConnected(getApplicationContext())) {
            rlMapsOnlinefvbi.setVisibility(View.VISIBLE);
            rlMapsOfflinefvbi.setVisibility(View.GONE);
        } else {
            if (TextUtils.isEmpty(staticMaps)) {
                SystemUtil.showToast(getApplicationContext(), "no internet access", Toast.LENGTH_SHORT, Gravity.TOP);
            } else {
                Glide.with(getApplicationContext())
                        .load(new File(staticMaps))
                        .asBitmap()
                        .placeholder(R.drawable.template_account_og)
                        .error(R.drawable.template_account_og)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                ivMapsOfflinefvbi.setImageBitmap(resource);
                            }
                        });
            }
            rlMapsOnlinefvbi.setVisibility(View.GONE);
            rlMapsOfflinefvbi.setVisibility(View.VISIBLE);

        }
    }

    private void initListener() {
        cvMapsFocusEventfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cvMapsMarkerTitlefvbi.setVisibility(View.GONE);
                int width = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels;
                int padding = (int) (width * 0.12); // offset from edges of the map 12% of screen
                cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                mMap.animateCamera(cameraUpdate, new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        if (mMap.getCameraPosition().zoom > 15) {
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        //getMyLocation();

        getPlaceLocation();

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                tvMapsFocusEventfvbi.setText("" + cameraPosition.zoom);
            }
        });

    }

    private void getPlaceLocation() {
        placeLists = db.getPlaceList(eventId);
        builder = new LatLngBounds.Builder();

        for (int i = 0; i < placeLists.size(); i++) {
            EventPlaceList placeList = placeLists.get(i);

            if (Double.parseDouble(placeList.getLatitude()) != 0 && Double.parseDouble(placeList.getLongitude()) != 0) {
                builder.include(new LatLng(Double.parseDouble(placeList.getLatitude()), Double.parseDouble(placeList.getLongitude())));
                marker = new MarkerOptions();
                marker.title(placeList.getTitle());
                marker.snippet(placeList.getTitle());
                marker.position(new LatLng(Double.parseDouble(placeList.getLatitude()), Double.parseDouble(placeList.getLongitude())));
                //loadMarkerIcon(marker, placeList.getIcon());
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mMap.addMarker(marker);
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        cvMapsMarkerTitlefvbi.setVisibility(View.VISIBLE);
                        tvMapsMarkerTitlefvbi.setText(marker.getTitle());
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
                        return true;
                    }
                });

            }
        }

        bounds = builder.build();

        cvMapsFocusEventfvbi.setVisibility(View.VISIBLE);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.12); // offset from edges of the map 12% of screen
        cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
        mMap.animateCamera(cameraUpdate, new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                if (mMap.getCameraPosition().zoom > 15) {
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                }
            }

            @Override
            public void onCancel() {

            }
        });
    }

    private void getMyLocation() {
        //spMapsMarkerfvbi.setEnabled(false);
        if (mylat == 0.00 && mylng == 0.00) {
            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    mylat = location.getLatitude();
                    mylng = location.getLongitude();
                    mylatlng = new LatLng(mylat, mylng);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylatlng, 15));
                    mMap.setOnMyLocationChangeListener(null);
                    //spMapsMarkerfvbi.setEnabled(true);
                }
            });
        } else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            mylat = location.getLatitude();
            mylng = location.getLongitude();
            mylatlng = new LatLng(mylat, mylng);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylatlng, 15));
            //spMapsMarkerfvbi.setEnabled(true);
        }
    }

    private void setRequestPermissionGPS() {
        // cek apakah sudah memiliki permission untuk access fine location
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                SystemUtil.showToast(this, "Access dibutuhkan untuk menentukan lokasi anda", Toast.LENGTH_LONG, Gravity.TOP);
                String[] perm = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
                ActivityCompat.requestPermissions(this, perm, ALL_PERMISSION);
            } else {
                // request permission untuk access fine location
                String[] perm = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
                ActivityCompat.requestPermissions(this, perm, ALL_PERMISSION);
            }
        } else {
            // permission access fine location didapat
            // SystemUtil.showToast(MainMapsActivity.this, "Yay, has permission", Toast.LENGTH_SHORT,Gravity.TOP);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ALL_PERMISSION: //private final int = 1
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do location thing
                    // access location didapatkan
                    SystemUtil.showToast(this, "Akses di berikan", Toast.LENGTH_SHORT, Gravity.TOP);
                    finish();
                    startActivity(getIntent());
                } else {
                    // access location ditolak user
                    SystemUtil.showToast(this, "Akses di tolak", Toast.LENGTH_SHORT, Gravity.TOP);
                    finish();
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void loadMarkerIcon(MarkerOptions marker, String s) {
        Glide.with(getApplicationContext())
                .load(s)
                .asBitmap()
                .fitCenter()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        Bitmap resizeImageBitmap = PictureUtil.resizeImageBitmap(bitmap, 1080);
                        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(resizeImageBitmap);
                        marker.icon(icon);
                    }
                });
    }

    private void initPermission() {
        if (!Nammu.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            Nammu.askForPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, new PermissionCallback() {
                @Override
                public void permissionGranted() {
                    SystemUtil.showToast(getApplicationContext(), "Diberikan Ada akses", Toast.LENGTH_LONG, Gravity.TOP);
                }

                @Override
                public void permissionRefused() {
                    SystemUtil.showToast(getApplicationContext(), "Tidak Diberikan Ada akses", Toast.LENGTH_LONG, Gravity.TOP);
                }
            });
        } else {
            SystemUtil.showToast(getApplicationContext(), "Sudah Ada akses", Toast.LENGTH_LONG, Gravity.TOP);
        }
    }
}
