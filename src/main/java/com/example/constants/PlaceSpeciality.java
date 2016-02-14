package com.example.constants;

/**
 * Created by Dmitrij on 23.11.2015.
 */
public enum PlaceSpeciality {
    PrivateClinic("Private clinic"), PublicClinic("Public clinic"), Dentist("Dentist"),
    MassageSalon("Massage salon"), FitnessCenter("Fitness salon");

    String name;

    PlaceSpeciality(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static PlaceSpeciality getInstance(String name) {
        for (PlaceSpeciality speciality : PlaceSpeciality.values()) {
            if (speciality.getName().equals(name))
                return speciality;
        }
        throw new IllegalArgumentException();
    }
}
