package com.ccg.banana.ccg.model;

import java.io.Serializable;

/**
 * Created by Banana on 29-Nov-17.
 */

public class DList implements Serializable {

    private int ID, colorValue;
    private String date, colorCode;

    public DList(int ID, String date, String colorCode, int colorValue) {
        this.ID = ID;
        this.date = date;
        this.colorCode = colorCode;
        this.colorValue = colorValue;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public int getColorValue() {
        return colorValue;
    }

    public void setColorValue(int colorValue) {
        this.colorValue = colorValue;
    }
}
