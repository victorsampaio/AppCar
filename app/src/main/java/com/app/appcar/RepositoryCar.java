package com.app.appcar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.sql.SQLException;
import java.sql.SQLException.*;
import java.util.ArrayList;
import java.util.List;
import com.app.appcar.Car.Cars;


/**
 * Created by VictorSampaio on 17/09/2014.
 */
public class RepositoryCar {
    private static final String CATEGORY = "repositoryCar";

    // Database Name
    private static final String DATABASE_NAME = "livro";

    // Table Name
    public static final String TABLE_NAME = "carro";
    protected SQLiteDatabase db;

    public RepositoryCar(Context ctx) {
        // Open existent Database
        db = ctx.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    protected RepositoryCar() {
        // create the subclass
    }

    // Save the car, insert a new car or update
    public long save(Car car) {
        long id = car.id;

        if (id != 0) {
            update(car);
        } else {
            // Insert a new car
            id = insert(car);
        }
        return id;
    }

    // Insert a new Car
    public long insert(Car car) {
        ContentValues values = new ContentValues();
        values.put(Cars.NAME, car.name);
        values.put(Cars.PLAQUE, car.plaque);
        values.put(Cars.YEAR, car.year);
        long id = insert(values);
        return id;
    }

    // Insert a new Car
    public long insert(ContentValues contentValues) {
        long id = db.insert(TABLE_NAME, "", contentValues);
        return id;
    }

    // Update the car in the database. The id of the Car it's used
    public int update(Car car) {
        ContentValues values = new ContentValues();
        values.put(Cars.NAME, car.name);
        values.put(Cars.PLAQUE, car.plaque);
        values.put(Cars.YEAR, car.year);

        String _id = String.valueOf(car.id);
        String where = Cars._ID + "=?";
        String[] whereArgs = new String[]{_id};
        int count = update(values, where, whereArgs);
        return count;
    }

    // Update the car with the values
    // the clause Where it's used for identify the car for update
    public int update(ContentValues values, String where, String[] whereArgs) {
        int count = db.update(TABLE_NAME, values, where, whereArgs);
        Log.i(CATEGORY, "UPDATE [ " + count + " ] records");
        return count;
    }

    //Delete the car using id
    public int delete(long id) {
        String where = Cars._ID + "=?";
        String _id = String.valueOf(id);
        String[] whereArgs = new String[]{_id};
        int count = delete(where, whereArgs);
        return count;
    }

    // Delete the car with arguments provided
    public int delete(String where, String[] whereArgs) {
        int count = db.delete(TABLE_NAME, where, whereArgs);
        Log.i(CATEGORY, "Delete [" + count + "] records");
        return count;
    }

    // Search the car by id
    public Car searchCar(long id) {
        // select + from car where _id=?
        Cursor c = db.query(true, TABLE_NAME, Car.column, Cars._ID + "=" + id, null, null, null, null, null);
        if (c.getCount() > 0) {
            // Position in the first element of cursor
            c.moveToFirst();
            Car car = new Car();
            // Read data
            car.id = c.getLong(0);
            car.name = c.getString(1);
            car.plaque = c.getString(2);
            car.year = c.getInt(3);

            return car;
        }
        return null;
    }

    // Return cursor with all the cars
    public Cursor getCursor() {
        try {
            // select * from car
            return db.query(TABLE_NAME, Car.column, null, null, null, null, null, null);

        } catch (SQLException e) {
            Log.e(CATEGORY, "Erros to seach the car: " + e.toString());
            return null;
        }
    }

    // Return the list with all the cars
    public List<Car> listCar() {

        Cursor c = getCursor();
        List<Car> cars = new ArrayList<Car>();

        if (c.moveToFirst()) {
            // Recover the indices of the column
            int idxId = c.getColumnIndex(Cars._ID);
            int idxName = c.getColumnIndex(Cars.NAME);
            int idxPlaque = c.getColumnIndex(Cars.PLAQUE);
            int idxYear = c.getColumnIndex(Cars.YEAR);

            // Loop to the end
            do {
                Car car = new Car();
                cars.add(car);
                // Recover the attributes to the car
                car.id = c.getLong(idxId);
                car.name = c.getString(idxName);
                car.plaque = c.getString(idxPlaque);
                car.year = c.getInt(idxYear);
            } while (c.moveToNext());

        }
        return cars;
    }

    // Search the car by the Name "select * from car where name=?"
    public Car searchCarByName(String name) {
        Car car = null;

        try {
            // SELECT _id, name, plaque,year from Car where name=?
            Cursor c = db.query(TABLE_NAME, Car.column, Cars.NAME + "='" + name + "'", null, null, null, null);

            //if meet
            if (c.moveToNext()) {
                car = new Car();

                // Using the method getLong(), getString(), getInt() etc. for recover the velues
                car.id = c.getLong(0);
                car.name = c.getString(1);
                car.plaque = c.getString(2);
                car.year = c.getInt(3);
            }
        } catch (SQLException e) {
            Log.e(CATEGORY, "Erro to search by the name: " + e.toString());
            return null;
        }
        return car;
    }

    // Search the car using the configurations in SQLiteQueryBuilder
    // Used by Content Provider of the car
    public Cursor query(SQLiteQueryBuilder queryBuilder, String[] projection, String selection,
                        String[] selectionArgs, String groupBy, String having, String orderBy){

        Cursor c = queryBuilder.query(this.db, projection, selection, selectionArgs, groupBy, having, orderBy);
        return c;
    }

    // Close Database
    public void close(){
        // close database
        if (db != null){
            db.close();
        }
    }
}