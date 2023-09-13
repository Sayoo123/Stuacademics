package com.example.stuacademics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;

import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.text.SimpleDateFormat;

public  class AttendanceFragment extends Fragment {
    private CaptureManager capture;
    public CompoundBarcodeView barcodeScanner;
    DatabaseReference db;
    Button reset;
    public AttendanceFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);

        db=FirebaseDatabase.getInstance().getReference(new Date().strDate);
        barcodeScanner=view.findViewById(R.id.barcode_scanner);
        reset=view.findViewById(R.id.retry);
        barcodeScanner.setStatusText("");
        barcodeScanner.decodeSingle(callback);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barcodeScanner.decodeSingle(callback);
                barcodeScanner.setStatusText("");
            }
        });
        return view;
    }
    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() != null) {
                barcodeScanner.setStatusText("Attendance updated");
                db.push().setValue("hellolll");
            }
        }

    };
    @Override
    public void onResume() {
        super.onResume();
        barcodeScanner.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        barcodeScanner.pause();
    }

}



