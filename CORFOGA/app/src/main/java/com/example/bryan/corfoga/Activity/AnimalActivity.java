package com.example.bryan.corfoga.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bryan.corfoga.Class.Animal;
import com.example.bryan.corfoga.Adarter.AnimalAdapter;
import com.example.bryan.corfoga.Class.Global;
import com.example.bryan.corfoga.R;

import java.util.ArrayList;
import java.util.List;

public class AnimalActivity extends AppCompatActivity {
    private ListView listView;
    private AnimalAdapter animalAdapter;
    private ArrayList<Animal> listItems;
    private Animal animal;
    private EditText buscar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);
        listView = (ListView) findViewById(R.id.listAnimals);
        fillAdapter();
        buscar = (EditText) findViewById(R.id.buscar);
        llenar();
        mostrarLista();
        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    llenar();
                    mostrarLista();
                }
                else{
                    //Hacer la busqueda
                    buscarAnimal(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void fillAdapter() {
        animalAdapter = new AnimalAdapter(listItems,this);
        listView.setAdapter(animalAdapter);
    }
    private void buscarAnimal(String busqueda){

        for(Animal ani : listItems ){
            if(!(ani.getId()+"").contains(busqueda)){
                listItems.remove(ani);
            }
        }
        animalAdapter.notifyDataSetChanged();

    }
    private void llenar() {
        listItems = cloneList(Global.getInstance().getAnimalsList());
    }


    private void mostrarLista(){
        animalAdapter = new AnimalAdapter(listItems,this);
        listView.setAdapter(animalAdapter);

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

    public static ArrayList<Animal> cloneList(List<Animal> listaAnim) {
        ArrayList<Animal> clonedList = new ArrayList<Animal>(listaAnim.size());
        for (Animal ani : listaAnim) {
            clonedList.add(new Animal(ani));
        }
        return clonedList;
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_buscador_animal, menu);
        return true;
    }
*/
}
