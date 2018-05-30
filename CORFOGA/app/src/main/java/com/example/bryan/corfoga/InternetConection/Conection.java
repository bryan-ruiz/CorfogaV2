package com.example.bryan.corfoga.InternetConection;

import com.example.bryan.corfoga.Class.Animal;
import com.example.bryan.corfoga.Class.Farm;
import com.example.bryan.corfoga.Class.InspectedAnimal;
import com.example.bryan.corfoga.Class.Inspection;
import com.example.bryan.corfoga.Class.User;

import com.example.bryan.corfoga.Class.User;
import com.example.bryan.corfoga.Class.VisitNumber;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface Conection {

    @GET("/api/login/{user}/{pass}")
    Call<User> getUser(@Path("user") String user, @Path("pass") String pass);

    @GET("/api/fincas/get/{region}")
    Call<List<Farm>> getFarmsFromRegion(@Path("region") int region);

    @GET("/api/animales/get/{farm}")
    Call<List<Animal>> getAnimalsFromFarm(@Path("farm") int farm);

    @GET("/api/inspecciones/visit/{anno}/{idfinca}")
    Call<List<VisitNumber>> getInspectionNumber(@Path("anno") int anno, @Path("idfinca") int idfinca);


    @GET("/api/inspecciones/getAnimalesvisita/{visita}/{idfinca}/{anno}")
    Call<List<InspectedAnimal>> getAnimalsVisit(@Path("visita") int visita, @Path("idfinca") int idfinca, @Path("anno") int anno);

    @GET("/api/inspecciones/get/{anno}/{animal}")
    Call<List<Inspection>> getInspectionsFromAnimal(@Path("anno") String anno, @Path("animal") int animal);

    //revisar ese String en el call.. podria ser mejor un Inspection
    @POST("/api/inspecciones/create")
    Call<Inspection>addInspection(@Body Inspection inspection);

}
