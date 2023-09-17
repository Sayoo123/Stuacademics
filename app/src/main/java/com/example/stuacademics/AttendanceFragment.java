package com.example.stuacademics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.text.SimpleDateFormat;

public  class AttendanceFragment extends Fragment {
    private static final String ARG_PARAM1 = "edttext";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    public CompoundBarcodeView barcodeScanner;
    DatabaseReference db;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    Button reset;
    public AttendanceFragment() {
    }
    public static AttendanceFragment newInstance(String param1) {
        AttendanceFragment fragment = new AttendanceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);
        db=FirebaseDatabase.getInstance().getReference();
        barcodeScanner=view.findViewById(R.id.barcode_scanner);
        reset=view.findViewById(R.id.retry);
        barcodeScanner.setStatusText(mParam1);
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
                String name=currentFirebaseUser.getEmail().split("@")[0];

                FirebaseDatabase.getInstance().getReference().child("Registration").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        String roll="";
                        for(DataSnapshot ds : snapshot.getChildren()) {
                             roll = ds.getValue(String.class);
                        }
                        db.child("Attendance").child(new Date().strDate).push().setValue(roll);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });

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



