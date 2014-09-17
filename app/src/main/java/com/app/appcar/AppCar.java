package com.app.appcar;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.app.appcar.Car.Cars;

import java.util.List;

// REGISTER CARS
public class AppCar extends ListActivity {

    protected static final int INSERT_EDIT = 1;
    protected static final int SEARCH = 2;

    public static RepositoryCar repository;
    public List<Car> cars;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_app_car);

        repository = new RepositoryCar(this);

        updateList();
    }

    protected void updateList() {
        //List car in the screen
        cars = repository.listCar();
        // Adapter List customize line a car
        setListAdapter(new CarListAdapter(this, cars));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, INSERT_EDIT, 0, "Insert New").setIcon(R.drawable.novo);
        menu.add(0, SEARCH, 0, "Search").setIcon(R.drawable.search);

        // Inflate the menu; this adds items to the action bar if it is present.
        // -> getMenuInflater().inflate(R.menu.app_car, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item){
        // Clicked the Menu
        switch (item.getItemId()) {
            case INSERT_EDIT:
                // Open the screen for add a new Form
                startActivityForResult(new Intent(this, EditCar.class), INSERT_EDIT);
                break;
            case SEARCH:
                startActivity(new Intent(this, SearchCar.class));
                break;
        }
        return true;
    }

    /**
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
         if (id == R.id.action_settings) {
         return true;
         }
         return super.onOptionsItemSelected(item);
    }
     */

    @Override
    protected void onListItemClick(ListView l, View v,int position, long id){
        super.onListItemClick(l, v, position, id);
        editCar(position);
    }

    // Recover id to the Car and open edit screen
    protected void editCar(int position){
        // The user click in the car of the list
        // Recover the selected car
        Car car = cars.get(position);
        // Create the Intent for open the edit screen
        Intent it = new Intent(this, EditCar.class);
        // Send id of the car as parameter
        it.putExtra(Cars._ID, car.id);
        // Open Screen for edit
        startActivityForResult(it, INSERT_EDIT);
    }

    @Override
    protected void onActivityResult(int cod, int responseCode, Intent it){
        super.onActivityResult(cod,responseCode, it);

        // Update the list
        if (responseCode == RESULT_OK){
            // update the list in the screen
            updateList();
        }

    }

    @Override
    protected void onDestroy(){
        repository.close();
    }
}

