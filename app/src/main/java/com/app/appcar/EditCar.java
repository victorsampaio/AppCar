package com.app.appcar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.app.appcar.Car.Cars;


public class EditCar extends Activity {

    static final int RESULT_SAVE = 1;
    static final int RESULT_DELETE = 2;

    //Field text
    private EditText fielName;
    private EditText fieldPlaque;
    private EditText fieldYear;
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);

        fielName = (EditText)findViewById(R.id.fieldName);
        fieldPlaque = (EditText)findViewById(R.id.fieldPlaque);
        fieldYear = (EditText)findViewById(R.id.fieldYear);
        id = null;

        Bundle extras =  getIntent().getExtras();
        // if to edit, recover the values
        if (extras != null){
            id = extras.getLong(Cars._ID);
            if (id != null){
                // Edition, search the car
                Car c = searchCar(id);
                fielName.setText(c.name);
                fieldPlaque.setText(c.plaque);
                fieldYear.setText(String.valueOf(c.year));
            }
        }

        // Button Cancel
        ImageButton btCancel = (ImageButton)findViewById(R.id.btCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                //Finish Screen
                finish();
            }
        });

        // Button Save
        ImageButton btSave = (ImageButton)findViewById(R.id.btSave);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        // Button Delete
        ImageButton btDelete = (ImageButton)findViewById(R.id.btDelete);
        if (id == null){
            // if id is null, don't delete
            btDelete.setVisibility(View.INVISIBLE);
        } else {
            // Listener for delete the car
            btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete();
                }
            });
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        // Cancel for clean the screen
        setResult(RESULT_CANCELED);
        // Close screen
        finish();
    }

    //Save
    public void save(){
        int year = 0;
        try {
            year = Integer.parseInt(fieldYear.getText().toString());

        }catch (NumberFormatException e){

        }

        Car car = new Car();
        if (id != null){
            // It's Update
            car.id = id;
        }

        car.name = fielName.getText().toString();
        car.plaque = fieldPlaque.getText().toString();
        car.year = year;

        //Save
        saveCar(car);

        //Ok
        setResult(RESULT_OK, new Intent());
        // close screen
        finish();
    }

    //DELETE
    public void delete(){
        if (id != null){
            deleteCar(id);
        }
        //OK
        setResult(RESULT_OK, new Intent());
        // Close screen
        finish();
    }

    //Search the car by id
    protected Car searchCar(long id){
        return AppCar.repository.searchCar(id);
    }

    // Save the Car
    protected void saveCar (Car car){
        AppCar.repository.save(car);
    }

    // Delete the car
    protected void deleteCar(long id){
        AppCar.repository.delete(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_car, menu);
        return true;
    }

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
}
