package com.example.constants;

/**
 * Created by Dmitrij on 20.01.2016.
 */
public enum WeekDays {

    Monday("mon", "mo", "monday"),
    Tuesday("tue", "tu", "tuesday"),
    Wednesday("wed", "we", "wednesday"),
    Thursday("thu", "th", "thursday"),
    Friday("fri", "fr", "friday"),
    Saturday("sat", "sa", "saturday"),
    Sunday("sun", "su", "sunday");

    String threeLetters;
    String twoLetters;
    String fullName;

    WeekDays(String threeLetters, String twoLetters, String fullName) {
        this.threeLetters = threeLetters;
        this.twoLetters = twoLetters;
        this.fullName = fullName;
    }

    public String getThreeLetters() {
        return threeLetters;
    }

    public void setThreeLetters(String threeLetters) {
        this.threeLetters = threeLetters;
    }

    public String getTwoLetters() {
        return twoLetters;
    }

    public void setTwoLetters(String twoLetters) {
        this.twoLetters = twoLetters;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
