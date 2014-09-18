package com.app.appcar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SearchCar extends Activity implements OnClickListener{

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_search_car);

        ImageButton btSearch = (ImageButton)findViewById(R.id.btSearch);
        btSearch.setOnClickListener(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        // Cancel and clean screen
        setResult(RESULT_CANCELED);
        // Close screen
        finish();
    }

    /**
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v){

        EditText name = (EditText)findViewById(R.id.fieldName);
        EditText plaque = (EditText)findViewById(R.id.fieldPlaque);
        EditText year = (EditText)findViewById(R.id.fieldYear);

        // Send values again
        String nameCar = name.getText().toString();
        Car car = searchCar(nameCar);
        if (car != null) {
            // Update fields with the result
            plaque.setText(car.plaque);
            year.setText(String.valueOf(car.year));
        } else {
            // Clean Fields
            plaque.setText("");
            year.setText("");
            Toast.makeText(SearchCar.this, "Not Found - car", Toast.LENGTH_SHORT).show();
        }
    }

    // Search by name
    protected Car searchCar(String nameCar){
        Car car = AppCar.repository.searchCarByName(nameCar);
        return car;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_car, menu);
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
