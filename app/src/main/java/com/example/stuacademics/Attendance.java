package com.example.stuacademics;

import androidx.appcompat.app.AppCompatActivity;

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
    Intent j;
    Double latitude,longitude;
    public double d=0;
    GmsBarcodeScanner scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
//        j=new Intent(Attendance.this,GoogleService.class);
//        startService(j);
            setContentView(R.layout.activity_attendance);
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
                                j = new Intent(Attendance.this, MainActivity.class);
                                startActivity(j);
                            })
                    .addOnCanceledListener(
                            () -> {
                                j = new Intent(Attendance.this, MainActivity.class);
                                startActivity(j);
                            })
                    .addOnFailureListener(
                            e -> {
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
                            });


        }
//        private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            latitude = Double.valueOf(intent.getStringExtra("latutide"));
//            longitude = Double.valueOf(intent.getStringExtra("longitude"));
//             stopService(j);
//            d= Distance.distance(latitude,longitude);
//            Log.d("distance",String.valueOf(d));
//            if(d<0.300){
//
//
//                    }
//            else {
//                Toast.makeText(getApplicationContext(),"Not Within College premises",Toast.LENGTH_LONG).show();
//                Intent i = new Intent(Attendance.this, MainActivity.class);
//                startActivity(i);
//                stopService(j);
//            }
//            }
//
//    };


}