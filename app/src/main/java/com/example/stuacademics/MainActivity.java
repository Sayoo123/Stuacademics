package com.example.stuacademics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;

import android.widget.Toast;

import com.example.stuacademics.databinding.ActivityMainBinding;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    LocationManager locationManager;
    double longitude, latitude;
    Thread mythread;
    public static int REQUEST_CHECK_SETTINGS = 100;
    public int d=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.Attendance) {
                if((locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled( LocationManager.NETWORK_PROVIDER))&&ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    mythread= new Thread() {
                        @Override
                        public void run() {
                            startService(new Intent(MainActivity.this,GoogleService.class));
                        }
                    };
                    mythread.start();
                }

                else if(ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    CheckGps.CheckGpsOnOorNot(this);
                    Log.d("at attt","at else");
                }
                else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                    android.Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CHECK_SETTINGS);
                }

            }
            return true;
        });

    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent!=null) {
                latitude = Double.parseDouble(intent.getStringExtra("latutide"));
                longitude = Double.parseDouble(intent.getStringExtra("longitude"));
                stopService(new Intent(MainActivity.this,GoogleService.class));
                double d = Distance.distance(latitude, longitude);
                if (d < 0.300) {
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,Attendance.class));
                }
                else{
                    startActivity(new Intent(MainActivity.this,NotCollegePremises.class));
                }

            }
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(GoogleService.str_receiver));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "GPS on", Toast.LENGTH_SHORT).show();
                if(d!=0)
                    mythread.interrupt();
                d+=1;
            }
        }
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_CANCELED)
                Toast.makeText(getApplicationContext(), "Turn On GPS ", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
              CheckGps.CheckGpsOnOorNot(this);
            } else {
                Toast.makeText(this, "Allow Location permission from Settings", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }



}
