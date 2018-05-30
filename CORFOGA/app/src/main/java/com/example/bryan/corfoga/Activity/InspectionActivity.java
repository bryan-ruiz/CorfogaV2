package com.example.bryan.corfoga.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bryan.corfoga.Class.Animal;
import com.example.bryan.corfoga.Class.Global;
import com.example.bryan.corfoga.Class.Inspection;
import com.example.bryan.corfoga.R;

import java.util.Calendar;

public class InspectionActivity extends AppCompatActivity {
    Animal animal;
    Spinner spinnerFeed, spinnerSituation;
    String spinnerItemSelected;
    TextView register, code, gender, birthdate, weight, scrotalCircumference, comments;
    String selectedItemText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);
        register = (TextView) findViewById(R.id.register);
        code = (TextView) findViewById(R.id.code);
        gender = (TextView) findViewById(R.id.gender);
        birthdate = (TextView) findViewById(R.id.birthdate);
        weight = (TextView) findViewById(R.id.txtPeso);
        scrotalCircumference = (TextView) findViewById(R.id.txtCE);
        comments = (MultiAutoCompleteTextView) findViewById(R.id.txtObservaciones);
        animal = Global.getInstance().getAnimal();
        register.setText(String.valueOf(animal.getRegister()));
        code.setText(String.valueOf(animal.getCode()));
        gender.setText(String.valueOf(animal.getSex()));
        birthdate.setText(String.valueOf(animal.getBirthdate()));
        spinnerFeed = (Spinner) findViewById(R.id.spinAlimentacion);
        spinnerSituation = (Spinner) findViewById(R.id.spinSituacion);
        spinnerSituation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemText = (String) parent.getItemAtPosition(position);
                switch (selectedItemText) {
                    case "1.Normal":
                        comments.setEnabled(true);
                        break;
                    default:
                        comments.setEnabled(false);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedItemText = "1.Normal";
                Toast.makeText
                        (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);
        return true;
    }

    private String getDatetime() {
        Calendar calendar = Calendar.getInstance();
        int seconds = calendar.get(Calendar.SECOND);
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // IF YOU USE HOUR IT WILL GIVE 12 HOUR USE HOUR_OF_DAY TO GET 24 HOUR FORMAT
        int minutes = calendar.get(Calendar.MINUTE);
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1; // in java month starts from 0 not from 1 so for december 11+1 = 12
        int year = calendar.get(Calendar.YEAR);
        String datetime = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(date) + " " + String.valueOf(hour) + ":" + String.valueOf(minutes) + ":" + String.valueOf(seconds);
        return datetime;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_item_save:
                String weightTotal = weight.getText().toString();
                if (Integer.parseInt(weightTotal) >= 150 && Integer.parseInt(weightTotal) <= 999) {
                    int id = Global.getInstance().getInspectionId() -1;
                    Global.getInstance().setInspectionId(id);
                    int asocebuFarmID = animal.getAsocebuFarmID();
                    int userID = Global.getInstance().getUser().getIdUsuario();
                    String datetime = getDatetime();
                    int visitNumber = Global.getInstance().getVisitNumber();
                    int animalID = animal.getId();
                    spinnerItemSelected = spinnerFeed.getSelectedItem().toString();
                    int feedingMethodID;
                    switch (spinnerItemSelected) {
                        case "1.Pastoreo":
                            feedingMethodID = 1;
                            break;
                        case "2.Semi Estabulación":
                            feedingMethodID = 2;
                            break;
                        case "3.Estabulación":
                            feedingMethodID = 3;
                            break;
                        default:
                            feedingMethodID = 4;
                            break;
                    }
                    String scrotalC = scrotalCircumference.getText().toString();
                    String observations;
                    if (!selectedItemText.equals("1.Normal")) {
                        observations = selectedItemText;
                    }
                    else {
                        observations = comments.getText().toString();
                    }
                    //int id, int asocebuFarmID, int userID, String datetime, int visitNumber, int animalID, int feedingMethodID, String weight, String scrotalCircumference, String observations) {
                    Inspection inspection = new Inspection(id, asocebuFarmID, userID, datetime, visitNumber, animalID, feedingMethodID, weightTotal, scrotalC, observations);
                    Global.getInstance().getAnimal().addInspectionDB(getApplicationContext(), inspection);
                    Toast.makeText(getApplicationContext(), "¡Datos guardados correctamente, no olvide exportar los datos (vista regiones)!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getBaseContext(), RegionActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "¡Peso no válido!", Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

