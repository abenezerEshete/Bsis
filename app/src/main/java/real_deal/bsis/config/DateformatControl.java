package real_deal.bsis.config;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateformatControl {

    SimpleDateFormat longDateFormat; //= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ a");
    SimpleDateFormat shortDateFormat;// = new SimpleDateFormat("yyyy-MM-dd");

    public DateformatControl () {
        SimpleDateFormat longDateFormat = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss.SSS");
        SimpleDateFormat shortDateFormat = new SimpleDateFormat ("yyyy-MM-dd");

    }

    public String convertToShortDate (String date) {
        try {
            SimpleDateFormat longDateFormat = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss.SSS");
            SimpleDateFormat shortDateFormat = new SimpleDateFormat ("yyyy-MM-dd");

            Date d = shortDateFormat.parse (date);
            String shortDate = shortDateFormat.format (d);


            return shortDate;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }

        return "";
    }

    public Date convertStringToDate (String date) {
        try {
            SimpleDateFormat longDateFormat = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss.SSS");

            Date d = longDateFormat.parse (date);


            return d;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }

        return new Date ();
    }

    public Date convertdbStringToDate (String date) {
        try {
            SimpleDateFormat longDateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");

            Date d = longDateFormat.parse (date);


            return d;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }

        return new Date ();
    }

    public Date convertshortStringToDate (String date) {
        try {
            SimpleDateFormat shortDateFormat = new SimpleDateFormat ("yyyy-MM-dd");

            Date d = shortDateFormat.parse (date);


            return d;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }

        return new Date ();
    }


    public String convertToLongDate (String date) {
        try {
            SimpleDateFormat longDateFormat = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss.SSS");
            SimpleDateFormat shortDateFormat = new SimpleDateFormat ("yyyy-MM-dd");

            Date d = shortDateFormat.parse (date);
            String longDate = longDateFormat.format (d);


            return longDate;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }

        return "";
    }

    public String convertToLongDate (Date date) {
        try {
            SimpleDateFormat longDateFormat = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss.SSS");

            String d = longDateFormat.format (date);


            return d;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }

        return "";
    }

    public String convertToshortDate (Date date) {
        try {
            SimpleDateFormat shortDateFormat = new SimpleDateFormat ("yyyy-MM-dd");

            String d = shortDateFormat.format (date);


            return d;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }

        return "";
    }

    public String convertDateToString (Date date) {
        try {
            SimpleDateFormat longDateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");

            String d = longDateFormat.format (date);


            return d;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }

        return "";
    }

    public String convertDateToShortString (Date date) {
        try {
            SimpleDateFormat longDateFormat = new SimpleDateFormat ("yyyy-MM-dd");

            String d = longDateFormat.format (date);


            return d;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }

        return "";
    }

}
