package com.example.bryan.corfoga.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bryan.corfoga.Class.Animal;
import com.example.bryan.corfoga.Class.Farm;
import com.example.bryan.corfoga.Class.Global;
import com.example.bryan.corfoga.Class.InspectedAnimal;
import com.example.bryan.corfoga.Class.Inspection;
import com.example.bryan.corfoga.Class.Region;
import com.example.bryan.corfoga.Class.User;
import com.example.bryan.corfoga.Class.VisitNumber;
import com.example.bryan.corfoga.Database.DataBaseHelper;
import com.example.bryan.corfoga.InternetConection.Conection;
import com.example.bryan.corfoga.InternetConection.Ip;
import com.example.bryan.corfoga.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegionActivity extends AppCompatActivity {
    private Button todas, pacificoCentral, central, huetarAtlantica, brunca, huetarNorte, chorotega;
    private Intent intent;
    private ArrayList<Farm> farmsList;
    private Global global;
    private Boolean isDatabaseEmpty, succed, failed;
    private Retrofit query;
    private Conection conection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regions);
        setButtonsFromView();
        setConnections();
        createRegions();
        setInformationToBegin();
        if (isDatabaseEmpty) {
            showAlert("Base de datos vacía, sincronice la aplicación.", false);
        }
        buttonsOnClicks();
    }

    private void buttonsOnClicks() {
        todas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkDataBase(0)) {
                    showAlert("No hay fincas disponibles en ninguna región, sincronice la aplicación.",false);
                }
                else if (!global.getFarmsList().isEmpty()) {
                    intent.putExtra("selectedRegion", "all");
                    startActivity(intent);
                }
                else {
                    showAlert("No hay fincas disponibles en ninguna región, sincronice la aplicación.", false);
                }
            }
        });
        pacificoCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkDataBase(3)) {
                    showAlert("No hay fincas disponibles en esta región.", false);
                }
                else if (!global.getFarmsList().isEmpty()) {
                    intent.putExtra("selectedRegion", "pacificoCentral");
                    startActivity(intent);
                }
                else {
                    showAlert("No hay fincas disponibles en esta región.", false);
                }
            }
        });
        central.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkDataBase(1)) {
                    showAlert("No hay fincas disponibles en esta región.", false);
                }
                else if (!global.getFarmsList().isEmpty()) {
                    intent.putExtra("selectedRegion", "central");
                    startActivity(intent);
                }
                else {
                    showAlert("No hay fincas disponibles en esta región.", false);
                }
            }
        });
        huetarAtlantica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkDataBase(5)) {
                    showAlert("No hay fincas disponibles en esta región.", false);
                }
                else if (!global.getFarmsList().isEmpty()) {
                    intent.putExtra("selectedRegion", "huetarAtlantica");
                    startActivity(intent);
                }
                else {
                    showAlert("No hay fincas disponibles en esta región.", false);
                }
            }
        });
        brunca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkDataBase(4)) {
                    showAlert("No hay fincas disponibles en esta región.", false);
                }
                else if (!global.getFarmsList().isEmpty()) {
                    intent.putExtra("selectedRegion", "brunca");
                    startActivity(intent);
                }
                else {
                    showAlert("No hay fincas disponibles en esta región.", false);
                }
            }
        });
        huetarNorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkDataBase(6)) {
                    showAlert("No hay fincas disponibles en esta región.", false);
                }
                else if (!global.getFarmsList().isEmpty()) {
                    intent.putExtra("selectedRegion", "huetarNorte");
                    startActivity(intent);
                }
                else {
                    showAlert("No hay fincas disponibles en esta región.", false);
                }
            }
        });
        chorotega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkDataBase(2)) {
                    showAlert("No hay fincas disponibles en esta región.", false);
                }
                else if (!global.getFarmsList().isEmpty()) {
                    intent.putExtra("selectedRegion", "chorotega");
                    startActivity(intent);
                }
                else {
                    showAlert("No hay fincas disponibles en esta región.", false);
                }
            }
        });
    }

    private void setButtonsFromView() {
        todas = findViewById(R.id.todas);
        pacificoCentral = findViewById(R.id.pacificoCentral);
        central = findViewById(R.id.central);
        huetarAtlantica = findViewById(R.id.huetarAtlantica);
        brunca = findViewById(R.id.brunca);
        huetarNorte = findViewById(R.id.huetarNorte);
        chorotega = findViewById(R.id.chorotega);
    }

    private void setConnections() {
        intent = new Intent(getBaseContext(), FarmActivity.class);
        global = Global.getInstance();
        query = new Retrofit.Builder()
                .baseUrl(Ip.getIpAddress())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        conection = query.create(Conection.class);
    }

    private void showAlert(String message, boolean state) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        if (state) {
            alert.setTitle("¡Listo!");
            alert.setIcon(R.drawable.success);
        }
        else {
            alert.setTitle("¡Error!");
            alert.setIcon(R.drawable.failed);
        }
        alert.setMessage(message);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    private void chageForMessage(boolean option) {
        if (option) {
            succed = true;
        }
        else {
            failed = true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.importar:
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
                dataBaseHelper.deleteDB(getApplicationContext());
                if (importInformationFromWebServer()) {
                    showAlert("Aplicación sincronizada correctamente", true);
                }
                else {
                    showAlert("Conexión débil, inténtelo nuevamente.", false);
                }
                return true;
            case R.id.exportar:
                succed = false;
                failed = false;
                try {
                    if (global.getExportListOfInspections(getApplicationContext()).isEmpty()) {
                        showAlert("No hay datos para exportar.", false);
                    }
                    else {
                        for (Inspection inspection : global.getExportListOfInspections(getApplicationContext())) {
                            Call<Inspection> result;
                            result = conection.addInspection(inspection);
                            result.enqueue(new Callback<Inspection>() {
                                @Override
                                public void onResponse(Call<Inspection> call, Response<Inspection> response) {
                                    chageForMessage(false);
                                }

                                @Override
                                public void onFailure(Call<Inspection> call, Throwable t) {
                                    chageForMessage(true);
                                }
                            });
                        }
                        return true;
                    }
                }
                catch (Exception e) {
                    failed = true;
                    showAlert("No hay datos para exportar.", false);
                }
                if (failed) {
                    Toast.makeText(getApplicationContext(), "¡Dasdf!", Toast.LENGTH_LONG).show();
                    showAlert("Conexión débil, inténtelo nuevamente.", false);
                }
                else {
                    Toast.makeText(getApplicationContext(), "¡!", Toast.LENGTH_LONG).show();
                    showAlert("Datos exportados.", true);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    private void setInformationToBegin() {
        farmsList = global.getFarmsList();
        isDatabaseEmpty = false;
        try {

            for (Region region : global.getRegionsList()) {
                if (!region.getFarmListDB(getApplicationContext()).isEmpty()) {
                    isDatabaseEmpty = false;
                }
            }
        }
        catch (Exception e) {
            isDatabaseEmpty = true;
        }
    }

    private void createRegions() {
        global.resetRegionList();
        String[] regionsName = {
                "Pacífico Central",
                "Central",
                "Huetar Atlántica",
                "Brunca",
                "Huetar Norte",
                "Chorotega"
        };
        int position = 1;
        while (position <= regionsName.length) {
            Region region = new Region(position, regionsName[position-1]);
            global.addRegion(region);
            position ++;
        }
    }

    private void getInspectedAnimals(final Farm farm) {
        int anno = Integer.parseInt(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        Call<List<InspectedAnimal>> result;
        result = conection.getAnimalsVisit(farm.getVisitNumber(), farm.getAsocebuID(), anno);
        result.enqueue(new Callback<List<InspectedAnimal>>() {
            @Override
            public void onResponse(Call<List<InspectedAnimal>> call, Response<List<InspectedAnimal>> response) {
                if (!response.body().isEmpty()) {
                    for (final InspectedAnimal inspectedAnimal: response.body()) {
                        farm.addInspectedAnimalDB(getApplicationContext(), inspectedAnimal);
                        Toast.makeText(getApplicationContext(), String.valueOf(inspectedAnimal.getObservations()), Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<InspectedAnimal>> call, Throwable t) {
            }
        });
    }

    private void getVisitNumber(final Region region, final Farm farm) {
        int anno = Integer.parseInt(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        int idfinca = farm.getAsocebuID();
        Call<List<VisitNumber>> result;
        result = conection.getInspectionNumber(anno, idfinca);
        result.enqueue(new Callback<List<VisitNumber>>() {
            @Override
            public void onResponse(Call<List<VisitNumber>> call, Response<List<VisitNumber>> response) {
                if (!response.body().isEmpty()) {
                    for (final VisitNumber visitNumber: response.body()) {
                        farm.setVisitNumber(visitNumber.getVisitNumber());
                        //Toast.makeText(getApplicationContext(), String.valueOf(farm.getVisitNumber()), Toast.LENGTH_LONG).show();
                        getInspectedAnimals(farm);
                    }
                }
                region.addFarmDB(getApplicationContext(), farm);
            }
            @Override
            public void onFailure(Call<List<VisitNumber>> call, Throwable t) {
                failed = true;
                region.addFarmDB(getApplicationContext(), farm);
            }
        });
    }

    private Boolean importInformationFromWebServer() {
        Call<List<Farm>> result;
        succed = false;
        failed = false;
        for (final Region region: global.getRegionsList()) {
            result = conection.getFarmsFromRegion(region.getId());
            result.enqueue(new Callback<List<Farm>>() {
                @Override
                public void onResponse(Call<List<Farm>> call, Response<List<Farm>> response) {
                    if (!response.body().isEmpty()) {
                        for (final Farm farm: response.body()) {
                            getVisitNumber(region, farm);
                            getAnimalsFromFarm(farm);
                            succed = true;
                        }
                    }
                }
                @Override
                public void onFailure(Call<List<Farm>> call, Throwable t) {
                    failed = true;
                }
            });
        }
        if (!succed && failed) {
            return false;
        }
        else {
            return true;
        }
    }

    private Boolean getAnimalsFromFarm(final Farm farm) {
        Call<List<Animal>> result;
        result = conection.getAnimalsFromFarm(farm.getAsocebuID());
        result.enqueue(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                if (!response.body().isEmpty()) {
                    for (final Animal animal: response.body()) {
                        farm.addAnimalDB(getApplicationContext(), animal);
                        //getInspectionsFromAnimal(animal);
                        //Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                showAlert("Conexión débil, inténtelo nuevamente.", false);
            }
        });
        return false;
    }
    private void getInspectionsFromAnimal(final Animal animal) {
        Call<List<Inspection>> result;
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        result = conection.getInspectionsFromAnimal(year, animal.getId());
        result.enqueue(new Callback<List<Inspection>>() {
            @Override
            public void onResponse(Call<List<Inspection>> call, Response<List<Inspection>> response) {
                if (!response.body().isEmpty()) {
                    for (final Inspection inspection: response.body()) {
                        animal.addInspectionDB(getApplicationContext(), inspection);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Inspection>> call, Throwable t) {
                showAlert("Conexión débil, inténtelo nuevamente.", false);
            }
        });
    }

    private Boolean checkDataBase(int id) {
        if (id == 0) {
            try {
                global.setFarmsList(global.getAllFarms(getApplicationContext()));
                return true;
            }
            catch (Exception e) {
                return false;
            }
        }
        else {
            for (Region region : global.getRegionsList()) {
                if (region.getId() == id) {
                    try {
                        global.setFarmsList(region.getFarmListDB(getApplicationContext()));
                        return true;
                    }
                    catch (Exception e) {
                        return false;
                    }
                }
            }
        }
        return false;
    }
}
