package com.example.stuacademics;

import android.util.Log;

public class Distance {
    static double lat1=12.336398337552524;
    static double lon1=75.10020878835685;
    public static double distance( double lat2, double lon2) {
        Log.i("lat at distance",String.valueOf(lat2));
        Log.i("lon at distance",String.valueOf(lon2));
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist/0.62137); }
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0); }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI); }
}
