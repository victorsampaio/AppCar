package com.app.appcar;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by VictorSampaio on 17/09/2014.
 */
public class Car {

    public static String[] column = new String[] { Cars._ID, Cars.NAME, Cars.PLAQUE,Cars.YEAR};

    /**
     * Package of Content Provider. Must be unique
     */
    public static final String AUTHORITY = "com.app.appcar.provider.car";
    public long id;
    public String name;
    public String plaque;
    public int year;

    public Car(){

    }

    public Car(String name, String plaque, int year){
        super();
        this.name = name;
        this.plaque = plaque;
        this.year = year;
    }

    public Car (long id, String name, String plaque, int year){
        super();
        this.id = id;
        this.name = name;
        this.plaque = plaque;
        this.year = year;
    }

    /**
     * The internal class for represent the columns for used for a Content Provider
     *
     * Daughter to BaseColumn defined (_id and _count), for follow the default Android
     */
    public static final class Cars implements BaseColumns{
        // Don't instantiate this class
        private Cars(){

        }

    //content:
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/cars");
    //Mime Type for all the cars
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.cars";
    // Mime Type for a unique car
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.cars";
    // Ordination default for insert in the order by
    public static final String DEFAULT_SORT_ORDER = "_id ASC";

        public static final String NAME = "name";
        public static final String PLAQUE = "plaque";
        public static final String YEAR = "year";

        //Method to create a Uri for a specific car, with your id
        public static Uri getUriId(long id){
        Uri uriCar = ContentUris.withAppendedId(Cars.CONTENT_URI, id);
        return uriCar;
         }
    }

    @Override
    public String toString(){
        return "Name: " + name + "Plaque: " + plaque + "Year: " + year;
    }
}
