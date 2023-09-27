package com.example.stuacademics;

import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.maps.model.LatLng;

public class GeofenceHelper extends ContextWrapper {
    private static final String TAG="GeofenceHelper";
    PendingIntent pendingIntent;
    public GeofenceHelper(Context base) {
        super(base);
    }
    public GeofencingRequest getGeofencingRequest(Geofence geofence){
        return new GeofencingRequest.Builder()
                .addGeofence(geofence)
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .build();
    }
    public Geofence getGeofence(String ID, LatLng latlng,float radius,int transitionTrypes)
    {
        return new Geofence.Builder().setCircularRegion(latlng.latitude, latlng.longitude,radius)
                .setRequestId(ID).setTransitionTypes(transitionTrypes).setLoiteringDelay(5000).setExpirationDuration(Geofence.NEVER_EXPIRE)
                .build();
    }
    public PendingIntent getPendingIntent(){
        if(pendingIntent!=null)
            return pendingIntent;
        Intent intent=new Intent(this,GeofenceBroadcastReceiver.class);
        if (Build.VERSION.SDK_INT >= 23) {
            pendingIntent=PendingIntent.getBroadcast(this,3901,intent,PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent=PendingIntent.getBroadcast(this,3901,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        }


        return pendingIntent;
    }
    public String getErrorString(Exception e){
        if(e instanceof ApiException){
            ApiException apiException=(ApiException)e;
            switch (apiException.getStatusCode()){
                case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                    return "GEOFENCE NOT AVAILABLE";
                case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                    return "GEOFENCE_TOO_MANY_GEOFENCES";
                case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                    return "GEOFENCE TOO MANY PENDING INTENTS";

            }
        }
        return e.getLocalizedMessage();
    }
}
