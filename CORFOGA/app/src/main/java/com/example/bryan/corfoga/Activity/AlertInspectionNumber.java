package com.example.bryan.corfoga.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bryan.corfoga.Class.Global;
import com.example.bryan.corfoga.InternetConection.Conection;
import com.example.bryan.corfoga.InternetConection.Ip;
import com.example.bryan.corfoga.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlertInspectionNumber extends AppCompatActivity{
    private Button inspectionOne, inspectionTwo, inspectionThree,inspectionFour;
    private Retrofit query;
    private Conection conection;
    private Intent intent;
    private String visitNumber;
    private ColorStateList backgroundTintList;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_inspection_number_view);
        loadData();
        setColor();
        buttonsOnClicks();
    }

    private void buttonsOnClicks() {
        inspectionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visitNumber.equals("1")) {
                    Global.getInstance().setVisitNumber(1);
                    startActivity(intent);
                }
                else {
                    showAlert();
                }
            }
        });
        inspectionTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visitNumber.equals("2")) {
                    Global.getInstance().setVisitNumber(2);
                    startActivity(intent);
                }
                else {
                    showAlert();
                }
            }
        });
        inspectionThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visitNumber.equals("3")) {
                    Global.getInstance().setVisitNumber(3);
                    startActivity(intent);
                }
                else {
                    showAlert();
                }
            }
        });
        inspectionFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visitNumber.equals("4")) {
                    Global.getInstance().setVisitNumber(4);
                    startActivity(intent);
                }
                else {
                    showAlert();
                }
            }
        });
    }

    private void setColor() {
        switch (visitNumber) {
            case "1":
                ViewCompat.setBackgroundTintList(inspectionOne, backgroundTintList);
                break;
            case "2":
                ViewCompat.setBackgroundTintList(inspectionTwo, backgroundTintList);
                break;
            case "3":
                ViewCompat.setBackgroundTintList(inspectionThree, backgroundTintList);
                break;
            default:
                ViewCompat.setBackgroundTintList(inspectionFour, backgroundTintList);
                break;
        }
    }

    private void loadData() {
        inspectionOne = (Button) findViewById(R.id.inspectionOne);
        inspectionTwo = (Button) findViewById(R.id.inspectionTwo);
        inspectionThree = (Button) findViewById(R.id.inspectionThree);
        inspectionFour = (Button) findViewById(R.id.inspectionFour);
        intent = new Intent(getBaseContext(), AnimalActivity.class);
        extras = getIntent().getExtras();
        visitNumber =  String.valueOf(extras.getInt("visit"));
        backgroundTintList = AppCompatResources.getColorStateList(getApplicationContext(), R.color.colorWords);
    }

    private void showAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("¡Error!");
        alert.setIcon(R.drawable.failed);
        alert.setMessage("Inspección no disponible, seleccione la correcta (color verde).");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
    }
}
