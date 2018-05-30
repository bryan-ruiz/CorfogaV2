package com.example.bryan.corfoga.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bryan.corfoga.Class.Animal;
import com.example.bryan.corfoga.Adarter.AnimalAdapter;
import com.example.bryan.corfoga.Class.Global;
import com.example.bryan.corfoga.R;

import java.util.ArrayList;

public class AnimalActivity extends AppCompatActivity {
    private ListView listView;
    private AnimalAdapter animalAdapter;
    private ArrayList<Animal> listItems;
    private Animal animal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);
        llenar();
        listView = (ListView) findViewById(R.id.listAnimals);
        fillAdapter();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Animal selectedAnimal = (Animal) listView.getItemAtPosition(i);
                Global.getInstance().setAnimal(selectedAnimal);
                Intent intent = new Intent(getBaseContext(), InspectionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fillAdapter() {
        animalAdapter = new AnimalAdapter(listItems,this);
        listView.setAdapter(animalAdapter);
    }

    private void llenar() {
        listItems = Global.getInstance().getAnimalsList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_buscador_animal, menu);
        return true;
    }
}
