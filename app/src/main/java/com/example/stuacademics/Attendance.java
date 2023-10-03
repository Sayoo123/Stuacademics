package com.example.stuacademics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

public class Attendance extends AppCompatActivity {
    GmsBarcodeScannerOptions options;
    Intent i;
    Double latitude,longitude;
    public double d=0;
    GmsBarcodeScanner scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        i=new Intent(Attendance.this,GoogleService.class);
        startService(i);
        setContentView(R.layout.activity_attendance);

    }
        private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            latitude = Double.valueOf(intent.getStringExtra("latutide"));
            longitude = Double.valueOf(intent.getStringExtra("longitude"));
            stopService(i);
            d= Distance.distance(latitude,longitude);
            Log.d("distance",String.valueOf(d));
            if(d<0.300){
            options = new GmsBarcodeScannerOptions.Builder()
                    .setBarcodeFormats(
                            Barcode.FORMAT_QR_CODE,
                            Barcode.FORMAT_AZTEC)
                    .enableAutoZoom()
                    .build();
            scanner = GmsBarcodeScanning.getClient(getApplicationContext(), options);
            scanner.startScan()
                    .addOnSuccessListener(
                            barcode -> {
                                Toast.makeText(getApplicationContext(),
                                        "SUCCESS: " + barcode.getRawValue(), Toast.LENGTH_LONG).show();
                                i = new Intent(Attendance.this, MainActivity.class);
                                startActivity(i);
                            })
                    .addOnCanceledListener(
                            () -> {
                                i = new Intent(Attendance.this, MainActivity.class);
                                startActivity(i);
                            })
                    .addOnFailureListener(
                            e -> {
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG);
                            });

                    }
            else {
                Toast.makeText(getApplicationContext(),"Not Within College premises",Toast.LENGTH_LONG).show();
                i = new Intent(Attendance.this, MainActivity.class);
                startActivity(i);
            }
            }

    };
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(GoogleService.str_receiver), RECEIVER_NOT_EXPORTED);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

}