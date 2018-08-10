package com.gandsoft.openguide.activity.infomenu;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.util.Log;
import android.widget.Toast;

import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.LocalBaseActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class aMapActivity extends LocalBaseActivity implements OnMapReadyCallback {

    private static final int ALL_PERMISSION = 1;
    private GoogleMap mMap;
    private double mylat;
    private double mylng;
    private LatLng mylatlng;
    private Toolbar toolbar;
    private ActionBar ab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_a_map);
        initMaps();
        initComponent();
        initContent();
        initListener();
    }

    private void initMaps() {
        setRequestPermissionGPS();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }
    private void initContent() {
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("Maps");
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

    private void initListener() {

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
        getMyLocation();

        /*mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                CameraUpdate center=CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
                CameraUpdate zoom=CameraUpdateFactory.zoomTo(11);
                mMap.moveCamera(center);
                mMap.animateCamera(zoom);
            }
        });*/
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
                Toast.makeText(this, "Access dibutuhkan untuk menentukan lokasi anda", Toast.LENGTH_LONG).show();
                String[] perm = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
                ActivityCompat.requestPermissions(this, perm, ALL_PERMISSION);
            } else {
                // request permission untuk access fine location
                String[] perm = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
                ActivityCompat.requestPermissions(this, perm, ALL_PERMISSION);
            }
        } else {
            // permission access fine location didapat
            // Toast.makeText(MainMapsActivity.this, "Yay, has permission", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("Lihat onRequestPermissionsResult aMapActivity", String.valueOf(permissions));
        Log.d("Lihat onRequestPermissionsResult aMapActivity", String.valueOf(requestCode));
        Log.d("Lihat onRequestPermissionsResult aMapActivity", String.valueOf(grantResults));
        switch (requestCode) {
            case ALL_PERMISSION: //private final int = 1
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do location thing
                    // access location didapatkan
                    Toast.makeText(this, "Akses di berikan", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                } else {
                    // access location ditolak user
                    Toast.makeText(this, "Akses di tolak", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }
}
