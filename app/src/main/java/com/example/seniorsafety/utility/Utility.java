package com.example.seniorsafety.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    public static String getCurrentData() {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-yyyy");
            String currentDateTime = dateFormat.format(new Date());

            return currentDateTime;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getMonthFromNumber(String monthNumber) {
        switch (monthNumber) {
            case "01": {
                return "January";
            }
            case "02": {
                return "February";
            }
            case "03": {
                return "March";
            }
            case "04": {
                return "April";
            }
            case "05": {
                return "May";
            }
            case "06": {
                return "June";
            }
            case "07": {
                return "July";
            }
            case "08": {
                return "August";
            }
            case "09": {
                return "September";
            }
            case "10": {
                return "October";
            }
            case "11": {
                return "November";
            }
            case "12": {
                return "December";
            }
            default: {
                return "Unknown";
            }
        }
    }
}
