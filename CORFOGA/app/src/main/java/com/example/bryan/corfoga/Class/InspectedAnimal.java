package com.example.bryan.corfoga.Class;

/**
 * Created by Bryan on 30/05/2018.
 */

public class InspectedAnimal {
    private int animalID;
    private String observations;

    public InspectedAnimal(int animalID, String observations) {
        this.animalID = animalID;
        this.observations = observations;
    }

    public int getAnimalID() {
        return animalID;
    }

    public void setAnimalID(int animalID) {
        this.animalID = animalID;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
