package com.example.bryan.corfoga.Class;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bryan.corfoga.Database.DataBaseHelper;
import com.example.bryan.corfoga.Database.DataBaseContract;
import java.util.ArrayList;

/**
 * Created by Bryan on 26/02/2018.
 */

public class Animal {
    private int id;
    private int asocebuFarmID;
    private String register;
    private String code;
    private String sex;
    private String birthdate;
    private String fatherRegister;
    private String fatherCode;
    private String motherRegister;
    private String motherCode;
    private String state;
    private ArrayList<Inspection> inspectionsList;

    public Animal(int id, int asocebuFarmID, String register, String code, String sex, String birthdate, String fatherRegister, String fatherCode, String motherRegister, String motherCode) {
        this.id = id;
        this.asocebuFarmID = asocebuFarmID;
        this.register = register;
        this.code = code;
        this.sex = sex;
        this.birthdate = birthdate;
        this.fatherRegister = fatherRegister;
        this.fatherCode = fatherCode;
        this.motherRegister = motherRegister;
        this.motherCode = motherCode;
        this.state = "1.Normal";
        this.inspectionsList = new ArrayList<Inspection>();
    }

    public Animal(Animal a) {
        this.id = a.getId();
        this.asocebuFarmID = a.getAsocebuFarmID();
        this.register = a.getRegister();
        this.code = a.getCode();
        this.sex = a.getSex();
        this.birthdate = a.getBirthdate();
        this.fatherRegister = a.getFatherRegister();
        this.fatherCode = a.getFatherCode();
        this.motherRegister = a.getMotherRegister();
        this.motherCode = a.getMotherCode();
        this.inspectionsList = new ArrayList<Inspection>();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAsocebuFarmID() {
        return asocebuFarmID;
    }

    public void setAsocebuFarmID(int asocebuFarmID) {
        this.asocebuFarmID = asocebuFarmID;
    }
    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getFatherRegister() {
        return fatherRegister;
    }

    public void setFatherRegister(String fatherRegister) {
        this.fatherRegister = fatherRegister;
    }

    public String getFatherCode() {
        return fatherCode;
    }

    public void setFatherCode(String fatherCode) {
        this.fatherCode = fatherCode;
    }

    public String getMotherRegister() {
        return motherRegister;
    }

    public void setMotherRegister(String motherRegister) {
        this.motherRegister = motherRegister;
    }

    public String getMotherCode() {
        return motherCode;
    }

    public void setMotherCode(String motherCode) {
        this.motherCode = motherCode;
    }

    public ArrayList<Inspection> getInspectionsList() {
        return inspectionsList;
    }

    public void setInspectionsList(ArrayList<Inspection> inspectionsList) {
        this.inspectionsList = inspectionsList;
    }

    public long addInspectionDB(Context context, Inspection inspection) {
        // usar la clase DataBaseHelper para realizar la operacion de insertar
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos en modo escritura
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        // Crear un mapa de valores donde las columnas son las llaves
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_ID, inspection.getId());
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_ASOCEBU_FARM_ID, inspection.getAsocebuFarmID());
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_USER_ID, inspection.getUserID());
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_DATETIME, inspection.getDatetime());
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_VISIT_NUMBER, inspection.getVisitNumber());
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_ANIMAL_ID, this.getId());
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_FEED_SYSTEM, inspection.getFeedingMethodID());
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_WEIGHT, inspection.getWeight());
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_SCROTAL_CIRCUMFERENCE, inspection.getScrotalCircumference());
        values.put(DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_COMMENT, inspection.getObservations());
        // Insertar la nueva fila
        return db.insert(DataBaseContract.DataBaseEntry.TABLE_NAME_INSPECTION, null, values);
    }

    public ArrayList<Inspection> getInspectionListDB (Context context){
        this.inspectionsList = new ArrayList<Inspection>();
        Inspection inspection;
        String state, comment, scrotalCircumference, weight, datetime;
        int animalId, inspectionId, asocebuId, userId, visitNumber, feedSystem;
        // usar la clase DataBaseHelper para realizar la operacion de leer
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos en modo lectura
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        // Define cuales columnas quiere solicitar // en este caso todas las de la clase
        String[] projection = {
                DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_ID,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_ASOCEBU_FARM_ID,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_USER_ID,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_DATETIME,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_VISIT_NUMBER,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_ANIMAL_ID,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_FEED_SYSTEM,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_WEIGHT,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_SCROTAL_CIRCUMFERENCE,
                DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_COMMENT
        };
        // Resultados en el cursor
        Cursor cursor = db.query(
                DataBaseContract.DataBaseEntry.TABLE_NAME_INSPECTION, // tabla
                projection, // columnas
                null, // where
                null, // valores del where
                null, // agrupamiento
                null, // filtros por grupo
                null // orden
        );
        cursor.moveToFirst();
        do
        {
            animalId = cursor.getInt(cursor.getColumnIndex(DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_ANIMAL_ID));
            if (animalId == this.getId()) {
                inspectionId = cursor.getInt(cursor.getColumnIndex(DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_ID));
                asocebuId = cursor.getInt(cursor.getColumnIndex(DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_ASOCEBU_FARM_ID));
                userId = cursor.getInt(cursor.getColumnIndex(DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_USER_ID));
                datetime = cursor.getString(cursor.getColumnIndex(DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_DATETIME));
                visitNumber = cursor.getInt(cursor.getColumnIndex(DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_VISIT_NUMBER));
                feedSystem = cursor.getInt(cursor.getColumnIndex(DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_FEED_SYSTEM));
                weight = cursor.getString(cursor.getColumnIndex(DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_WEIGHT));
                scrotalCircumference = cursor.getString(cursor.getColumnIndex(DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_SCROTAL_CIRCUMFERENCE));
                comment = cursor.getString(cursor.getColumnIndex(DataBaseContract.DataBaseEntry.COLUMN_NAME_INSPECTION_COMMENT));
                inspection = new Inspection(inspectionId, asocebuId, userId, datetime, visitNumber, animalId, feedSystem, weight, scrotalCircumference, comment);
                this.inspectionsList.add(inspection);
            }
        }
        while (cursor.moveToNext());
        return this.inspectionsList;
    }
}
