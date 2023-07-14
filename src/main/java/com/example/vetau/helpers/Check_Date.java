package com.example.vetau.helpers;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Check_Date {
    public static boolean Is_Between(Date startTime, String HourStart , Date endTime, String HourEnd,Date CheckTime, String HourCheck)
    {
        boolean isBetween;
        try {
            SimpleDateFormat dateFormat_date = new SimpleDateFormat("dd-MM-yyyy");

            SimpleDateFormat dateFormat_date_full = new SimpleDateFormat("dd-MM-yyyy HH");
            String startTime_day = dateFormat_date.format(startTime);

            Date startTime_full = dateFormat_date_full.parse(startTime_day +" "+ HourStart);

            String endTime_day = dateFormat_date.format(endTime);

            Date endTime_full = dateFormat_date_full.parse(endTime_day +" "+ HourEnd);

            String checkTime_day = dateFormat_date.format(CheckTime);

            Date checkTime_full = dateFormat_date_full.parse(checkTime_day +" "+ HourCheck);

            isBetween = checkTime_full.after(startTime_full) && checkTime_full.before(endTime_full);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return isBetween ;
    }
//    public static void main(String[] args) {
//
//
//            Date a = new Date(1);
//            Date b = new Date(2);
//            Date c = new Date(3);
//            boolean isBetween = Check_Date.Is_Between(a,"9",b,"12",c,"10");
//
//            if (isBetween) {
//                System.out.println("trong");
//            } else {
//                System.out.println("ngoài");
//            }
//
//    }
}

