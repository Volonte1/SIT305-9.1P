package com.example.lostandfoundapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class createadvertActivity extends AppCompatActivity {
    //declare variables
    private PlacesClient Client;
    EditText name, phone, description, date, location;
    Button save, locate;
    Double number1;
    Double number2;
    RadioButton lost, found;
    String item;
    DbHelper DB;
    LocationManager locationManager;
    FusedLocationProviderClient fusedLocationProviderClient;
    private LatLng mLatLng = null;
    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result != null && result.getResultCode() == RESULT_OK) {
                    Toast.makeText(createadvertActivity.this, "Test 1", Toast.LENGTH_SHORT).show();
                if (result.getData() != null) {
                    Toast.makeText(createadvertActivity.this, "Test 2", Toast.LENGTH_SHORT).show();
                        Place place = Autocomplete.getPlaceFromIntent(result.getData());
                            location.setText(place.getAddress());
                    if(null != place.getLatLng()) {
                        number1 = place.getLatLng().latitude;
                            number2 = place.getLatLng().longitude;
                    }

                }
            }
        }
    });

    //declare variables again
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DB = new DbHelper(this);

        setContentView(R.layout.activity_createadvert);

        name = findViewById(R.id.name);

        phone = findViewById(R.id.phone);

        description = findViewById(R.id.description);

        locate = findViewById(R.id.getlocation);

        date = findViewById(R.id.date);

        location = findViewById(R.id.location);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //my API key (pain to get)
        Places.initialize(getApplicationContext(), "AIzaSyBTAbveokhmL0YwXgbB8lBwdL41y_EAhdk");
        Client = Places.createClient(this);

        location.setFocusable(false);
        //functiong for on click autocomplete
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
                    Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(createadvertActivity.this);
                        startForResult.launch(intent);

            }
        });
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(createadvertActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(createadvertActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(createadvertActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                Getlocation();
                } else {
                    ActivityCompat.requestPermissions(createadvertActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 44);

                }

            }
        });

        //more declaring
        save = findViewById(R.id.save);
        lost = findViewById(R.id.radioButton);
        found = findViewById(R.id.radioButton2);
        //onclicks for different radio buttons
        lost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item = lost.getText().toString();
            }
        });
        found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item = found.getText().toString();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            //set DB with inputted data, if its not correct data it will come back that it has not been saved with a toast
            @Override
            public void onClick(View view) {
                boolean insert = DB.insertData(item, name.getText().toString(), phone.getText().toString(), description.getText().toString(), date.getText().toString(), location.getText().toString(), number1, number2);
                if (insert == true) {
                    Toast.makeText(createadvertActivity.this, "Data has been saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(createadvertActivity.this, "Data has not been saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void Getlocation() {
        ProgressDialog progressDialog = ProgressDialog.show(createadvertActivity.this,"","",true);
        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
                Place.Field.LAT_LNG);
        FindCurrentPlaceRequest request =
                FindCurrentPlaceRequest.newInstance(placeFields);
        @SuppressWarnings("MissingPermission") final Task<FindCurrentPlaceResponse> placeResult =
                Client.findCurrentPlace(request);
        placeResult.addOnCompleteListener(new OnCompleteListener<FindCurrentPlaceResponse>() {
            //function for getplace for maps
            @Override
            public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    FindCurrentPlaceResponse likelyPlaces = task.getResult();

                    if (!likelyPlaces.getPlaceLikelihoods().isEmpty()) {

                        Place place = null;
                        for (PlaceLikelihood placeLikelihood : likelyPlaces.getPlaceLikelihoods()) {
                            place = placeLikelihood.getPlace();
                        }
                        if (null == place) {
                            Toast.makeText(getApplicationContext(), "test 3", Toast.LENGTH_SHORT).show();
                            return;
                        }
                //latlng functions, found in week 9 of the tutorial videos
                        mLatLng = place.getLatLng();
                        location.setText(place.getAddress());
                        if(null != place.getLatLng()) {
                            number1 = place.getLatLng().latitude;
                            number2 = place.getLatLng().longitude;
                        }

                        Toast.makeText(getApplicationContext(), "test 4", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "test 5", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "test 6", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }

}
