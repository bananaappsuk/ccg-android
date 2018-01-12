package com.ccg.banana.ccg.Fragments;

import android.os.Parcel;
import android.os.Parcelable;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyBookingList implements Parcelable {

    String Module_Address1;
    String Module_Address2;
    String Module_Category;
    String Module_City;
    String Module_End_Date;
    String Module_End_Day;
    String Module_End_Time;
    String Module_ID;
    String Module_Start_Date;
    String Module_Start_Day;

    public String getModule_Address1() {
        return Module_Address1;
    }

    public void setModule_Address1(String module_Address1) {
        Module_Address1 = module_Address1;
    }

    public String getModule_Address2() {
        return Module_Address2;
    }

    public void setModule_Address2(String module_Address2) {
        Module_Address2 = module_Address2;
    }

    public String getModule_Category() {
        return Module_Category;
    }

    public void setModule_Category(String module_Category) {
        Module_Category = module_Category;
    }

    public String getModule_City() {
        return Module_City;
    }

    public void setModule_City(String module_City) {
        Module_City = module_City;
    }

    public String getModule_End_Date() {
        return Module_End_Date;
    }

    public void setModule_End_Date(String module_End_Date) {
        Module_End_Date = module_End_Date;
    }

    public String getModule_End_Day() {
        return Module_End_Day;
    }

    public void setModule_End_Day(String module_End_Day) {
        Module_End_Day = module_End_Day;
    }

    public String getModule_End_Time() {
        return Module_End_Time;
    }

    public void setModule_End_Time(String module_End_Time) {
        Module_End_Time = module_End_Time;
    }

    public String getModule_ID() {
        return Module_ID;
    }

    public void setModule_ID(String module_ID) {
        Module_ID = module_ID;
    }

    public String getModule_Start_Date() {
        return Module_Start_Date;
    }

    public void setModule_Start_Date(String module_Start_Date) {
        Module_Start_Date = module_Start_Date;
    }

    public String getModule_Start_Day() {
        return Module_Start_Day;
    }

    public void setModule_Start_Day(String module_Start_Day) {
        Module_Start_Day = module_Start_Day;
    }

    public String getModule_Start_Time() {
        return Module_Start_Time;
    }

    public void setModule_Start_Time(String module_Start_Time) {
        Module_Start_Time = module_Start_Time;
    }

    public String getModule_Title() {
        return Module_Title;
    }

    public void setModule_Title(String module_Title) {
        Module_Title = module_Title;
    }

    public String getModule_Type() {
        return Module_Type;
    }

    public void setModule_Type(String module_Type) {
        Module_Type = module_Type;
    }

    String Module_Start_Time;
    String Module_Title;
    String Module_Type;

   /* private ArrayList<TrainingPhotoList> shopList = new ArrayList<>();*/

    protected MyBookingList(Parcel in) {
        Module_Address1 = in.readString();
        Module_Address2 = in.readString();
        Module_Category = in.readString();
        Module_City = in.readString();
        Module_End_Date = in.readString();
        Module_End_Day = in.readString();
        Module_End_Time = in.readString();
        Module_ID = in.readString();
        Module_Start_Date = in.readString();
        Module_Start_Day = in.readString();
        Module_Start_Time = in.readString();
        Module_Title = in.readString();
        Module_Type = in.readString();

   //     shopAddress = in.createTypedArrayList(ShopAddress.CREATOR);
      //  mid = in.readInt();
    //    address=in.createTypedArrayList(ShopAddress.CREATOR);
       }

 //  protected TrainingBoardList(String Capacity,String Register_Status,String Status,String Traning_Address1,String Traning_Address2,String Traning_Category_Id,String Traning_Category_Name,String Traning_City,String Traning_ContactPersonname,String Traning_ContactPersonnumber,String Traning_Day,String Traning_Description)
   protected MyBookingList(String Module_Address1,String Module_Address2,String Module_Category,String Module_City,String Module_End_Date,String Module_End_Day,String Module_End_Time,String Module_ID,String Module_Start_Date,String Module_Start_Day,String Module_Start_Time,String Module_Title,String Module_Type)
   {
       this.Module_Address1 = Module_Address1;
       this.Module_Address2 = Module_Address2;
       this.Module_Category = Module_Category;
       this.Module_City = Module_City;
       this.Module_End_Date = Module_End_Date;
       this.Module_End_Day = Module_End_Day;
       this.Module_End_Time = Module_End_Time;
       this.Module_ID = Module_ID;
       this.Module_Start_Date = Module_Start_Date;
       this.Module_Start_Day = Module_Start_Day;
       this.Module_Start_Time = Module_Start_Time;
       this.Module_Title = Module_Title;
       this.Module_Type = Module_Type;

    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Module_Address1);
        dest.writeString(Module_Address2);
        dest.writeString(Module_Category);
        dest.writeString(Module_City);
        dest.writeString(Module_End_Date);
        dest.writeString(Module_End_Day);
        dest.writeString(Module_End_Time);
        dest.writeString(Module_ID);
        dest.writeString(Module_Start_Date);
        dest.writeString(Module_Start_Day);
        dest.writeString(Module_Start_Time);
        dest.writeString(Module_Title);
        dest.writeString(Module_Type);

    }







    public static final Creator<TrainingBoardList> CREATOR = new Creator<TrainingBoardList>() {
        @Override
        public TrainingBoardList createFromParcel(Parcel in) {
            return new TrainingBoardList(in);
        }

        @Override
        public TrainingBoardList[] newArray(int size) {
            return new TrainingBoardList[size];
        }
    };

  /*  public ArrayList<ShopAddress> getAddress() {
        return address;
    }*/

/*      public void setAddress(String diatsance) {
        this.address = address;
    }
*/
    @Override
    public int describeContents() {
        return 0;
    }
}
