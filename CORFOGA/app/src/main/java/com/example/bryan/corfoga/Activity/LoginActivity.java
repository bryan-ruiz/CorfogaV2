package com.example.bryan.corfoga.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

import com.example.bryan.corfoga.Class.Global;
import com.example.bryan.corfoga.Class.Login;
import com.example.bryan.corfoga.Class.User;
import com.example.bryan.corfoga.Database.DataBaseHelper;
import com.example.bryan.corfoga.InternetConection.Conection;
import com.example.bryan.corfoga.InternetConection.Ip;
import com.example.bryan.corfoga.R;

public class LoginActivity extends AppCompatActivity {
    private LinearLayout progressBar;
    private Button login;
    private String userName, password;
    private Login loginDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = (LinearLayout) findViewById(R.id.progressBarLayout);
        login = (Button) findViewById(R.id.login);
        loginDB = new Login();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setEnabled(false);
                try {
                    userName = ((EditText) findViewById(R.id.userName)).getText().toString();
                    password = ((EditText) findViewById(R.id.password)).getText().toString();
                    if (!userName.equals("") && !password.equals("")) {
                        progressBar.setVisibility(View.VISIBLE);
                        Retrofit query = new Retrofit.Builder()
                                .baseUrl(Ip.getIpAddress())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        Conection conection = query.create(Conection.class);
                        Call<User> result = conection.getUser(userName, password);
                        result.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.body() == null) {
                                    checkDatabase();
                                } else {
                                    loginDB.addUserDB(getApplicationContext(), response.body(), userName, password);
                                    setToRegionsView(response.body().getIdUsuario());
                                }
                            }
                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                checkDatabase();
                            }
                        });
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"¡No pueden haber espacios vacíos, inténtelo nuevamente!",Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                    login.setEnabled(true);
                }
                catch (Exception ex){
                    Toast.makeText(getApplicationContext(),"No se puede acceder al sistema, inténtelo más tarde",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkDatabase() {
        User existInBD = loginDB.getUserFromDB(getApplicationContext(),userName, password);
        if (existInBD != null) {
            setToRegionsView(existInBD.getIdUsuario());
        }
        else {
            Toast.makeText(getApplicationContext(), "¡Usuario o Contraseña incorrectos. Tambien es posible que los datos no estén sincronizados, por favor, busca internet! ", Toast.LENGTH_LONG).show();
        }
    }

    private void setToRegionsView(int id) {
        Global global = Global.getInstance();
        User user = new User(id);
        global.setUser(user);
        progressBar.setVisibility(View.GONE);
        login.setEnabled(true);
        toRegionsView();
    }

    private void toRegionsView() {
        Intent intent = new Intent(LoginActivity.this, RegionActivity.class);
        startActivity(intent);
    }
}