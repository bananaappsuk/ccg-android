package com.ccg.banana.ccg.Fragments;

import android.os.Parcel;
import android.os.Parcelable;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TrainingPhotoList implements Parcelable {

    @SerializedName("ID")
    @Expose
    private String ID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTraning_Id() {
        return Traning_Id;
    }

    public void setTraning_Id(String traning_Id) {
        Traning_Id = traning_Id;
    }

    public String getTraning_Photo() {
        return Traning_Photo;
    }

    public void setTraning_Photo(String traning_Photo) {
        Traning_Photo = traning_Photo;
    }

    @SerializedName("Traning_Id")
    @Expose
    private String Traning_Id;

    @SerializedName("Traning_Photo")
    @Expose
    private String Traning_Photo;



    protected TrainingPhotoList(Parcel in) {
        ID = in.readString();
        Traning_Id = in.readString();
        Traning_Photo = in.readString();
    }

    protected TrainingPhotoList(String ID,String Traning_Id,String Traning_Photo) {
        this.ID=ID;
        this.Traning_Id=Traning_Id;
        this.Traning_Photo=Traning_Photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID);
        dest.writeString(Traning_Id);
        dest.writeString(Traning_Photo);
    }

    public static final Creator<TrainingPhotoList> CREATOR = new Creator<TrainingPhotoList>() {
        @Override
        public TrainingPhotoList createFromParcel(Parcel in) {
            return new TrainingPhotoList(in);
        }

        @Override
        public TrainingPhotoList[] newArray(int size) {
            return new TrainingPhotoList[size];
        }
    };



}
