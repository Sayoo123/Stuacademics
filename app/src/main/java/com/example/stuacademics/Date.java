package com.example.stuacademics;

import java.text.SimpleDateFormat;

public class Date {
    java.util.Date date = new java.util.Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    String strDate= formatter.format(date);
}
