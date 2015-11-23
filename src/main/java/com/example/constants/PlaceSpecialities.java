package com.example.constants;

/**
 * Created by Dmitrij on 23.11.2015.
 */
public enum PlaceSpecialities {
    NightClub("NightClub"), Sport("Sport"), Cafe("Cafe");
    String name;
    PlaceSpecialities(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
