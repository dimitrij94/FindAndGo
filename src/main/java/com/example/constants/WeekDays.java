package com.example.constants;

/**
 * Created by Dmitrij on 20.01.2016.
 */
public enum WeekDays {

    Monday("mon", "mo", "monday",1),
    Tuesday("tue", "tu", "tuesday",2),
    Wednesday("wed", "we", "wednesday",3),
    Thursday("thu", "th", "thursday",4),
    Friday("fri", "fr", "friday",5),
    Saturday("sat", "sa", "saturday",6),
    Sunday("sun", "su", "sunday",7);

    private String threeLetters;
    private String twoLetters;
    private String fullName;
    private int dayNum;

    WeekDays(String threeLetters, String twoLetters, String fullName, int dayNum) {
        this.threeLetters = threeLetters;
        this.twoLetters = twoLetters;
        this.fullName = fullName;
        this.dayNum = dayNum;
    }

    public static WeekDays getInstace(int dayNum){
        for (WeekDays w : WeekDays.values())
            if (w.getDayNum()==dayNum)
                return w;
        throw new IllegalArgumentException();
    }

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
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
