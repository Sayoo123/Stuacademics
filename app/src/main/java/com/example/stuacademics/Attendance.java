package com.example.stuacademics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.widget.Toast;

import com.google.android.gms.common.moduleinstall.ModuleInstall;
import com.google.android.gms.common.moduleinstall.ModuleInstallClient;
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

public class Attendance extends AppCompatActivity {
    GmsBarcodeScannerOptions options;
    Intent j;
    public double d=0;
    GmsBarcodeScanner scanner;
    ModuleInstallClient moduleInstall;
    ModuleInstallRequest moduleInstallRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
         moduleInstall = ModuleInstall.getClient(this);
         moduleInstallRequest = ModuleInstallRequest.newBuilder()
                .addApi(GmsBarcodeScanning.getClient(this))
                .build();
        moduleInstall.installModules(moduleInstallRequest)
                .addOnSuccessListener(
                        response->
                        {
                            if(response.areModulesAlreadyInstalled())
                            {
                                gotoscan();
                            }
                        }
                )
        .addOnFailureListener(e->{
            // Handle failure…
        }
        );
    }
    private void gotoscan()
    {
        setContentView(R.layout.activity_attendance);
        options = new GmsBarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                        Barcode.FORMAT_QR_CODE,
                        Barcode.FORMAT_AZTEC)
                .enableAutoZoom()
                .build();
        scanner = GmsBarcodeScanning.getClient(this, options);
        scanner.startScan()
                .addOnSuccessListener(
                        barcode -> {
                            Toast.makeText(getApplicationContext(),
                                    "SUCCESS: " + barcode.getRawValue(), Toast.LENGTH_LONG).show();
//                                j = new Intent(Attendance.this, MainActivity.class);
//                                startActivity(j);
                        })
                .addOnCanceledListener(
                        () -> {
                            j = new Intent(Attendance.this, MainActivity.class);
                            startActivity(j);
                        })
                .addOnFailureListener(
                        e -> {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        });
    }

}