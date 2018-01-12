package com.ccg.banana.ccg.Fragments;

import android.os.Parcel;
import android.os.Parcelable;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EventBoardList implements Parcelable {

    String Capacity,Register_Status,Status,Traning_Address1,Traning_Address2,Traning_Category_Id,Traning_Category_Name,Traning_City,Traning_ContactPersonname,Traning_ContactPersonnumber,Traning_Day,Traning_Description;

    @SerializedName("trainingPhotoLists")
    @Expose
    private ArrayList<JobPhotoList> trainingPhotoLists = new ArrayList<>();

    public String getCapacity() {
        return Capacity;
    }

    public void setCapacity(String capacity) {
        Capacity = capacity;
    }

    public String getRegister_Status() {
        return Register_Status;
    }

    public void setRegister_Status(String register_Status) {
        Register_Status = register_Status;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTraning_Address1() {
        return Traning_Address1;
    }

    public void setTraning_Address1(String traning_Address1) {
        Traning_Address1 = traning_Address1;
    }

    public String getTraning_Address2() {
        return Traning_Address2;
    }

    public void setTraning_Address2(String traning_Address2) {
        Traning_Address2 = traning_Address2;
    }

    public String getTraning_Category_Id() {
        return Traning_Category_Id;
    }

    public void setTraning_Category_Id(String traning_Category_Id) {
        Traning_Category_Id = traning_Category_Id;
    }

    public String getTraning_Category_Name() {
        return Traning_Category_Name;
    }

    public void setTraning_Category_Name(String traning_Category_Name) {
        Traning_Category_Name = traning_Category_Name;
    }

    public String getTraning_City() {
        return Traning_City;
    }

    public void setTraning_City(String traning_City) {
        Traning_City = traning_City;
    }

    public String getTraning_ContactPersonname() {
        return Traning_ContactPersonname;
    }

    public void setTraning_ContactPersonname(String traning_ContactPersonname) {
        Traning_ContactPersonname = traning_ContactPersonname;
    }

    public String getTraning_ContactPersonnumber() {
        return Traning_ContactPersonnumber;
    }

    public void setTraning_ContactPersonnumber(String traning_ContactPersonnumber) {
        Traning_ContactPersonnumber = traning_ContactPersonnumber;
    }

    public String getTraning_Day() {
        return Traning_Day;
    }

    public void setTraning_Day(String traning_Day) {
        Traning_Day = traning_Day;
    }

    public String getTraning_Description() {
        return Traning_Description;
    }

    public void setTraning_Description(String traning_Description) {
        Traning_Description = traning_Description;
    }

    public String getTraning_EndDate() {
        return Traning_EndDate;
    }

    public void setTraning_EndDate(String traning_EndDate) {
        Traning_EndDate = traning_EndDate;
    }

    public String getTraning_EndDate_Foramt() {
        return Traning_EndDate_Foramt;
    }

    public void setTraning_EndDate_Foramt(String traning_EndDate_Foramt) {
        Traning_EndDate_Foramt = traning_EndDate_Foramt;
    }

    public String getTraning_EndTime() {
        return Traning_EndTime;
    }

    public void setTraning_EndTime(String traning_EndTime) {
        Traning_EndTime = traning_EndTime;
    }

    public String getTraning_EndTime_Format() {
        return Traning_EndTime_Format;
    }

    public void setTraning_EndTime_Format(String traning_EndTime_Format) {
        Traning_EndTime_Format = traning_EndTime_Format;
    }

    public String getTraning_Id() {
        return Traning_Id;
    }

    public void setTraning_Id(String traning_Id) {
        Traning_Id = traning_Id;
    }

    public String getTraning_Image() {
        return Traning_Image;
    }

    public void setTraning_Image(String traning_Image) {
        Traning_Image = traning_Image;
    }

    public String getTraning_Postcode() {
        return Traning_Postcode;
    }

    public void setTraning_Postcode(String traning_Postcode) {
        Traning_Postcode = traning_Postcode;
    }

    public String getTraning_StartDate() {
        return Traning_StartDate;
    }

    public void setTraning_StartDate(String traning_StartDate) {
        Traning_StartDate = traning_StartDate;
    }

    public String getTraning_StartDate_Format() {
        return Traning_StartDate_Format;
    }

    public void setTraning_StartDate_Format(String traning_StartDate_Format) {
        Traning_StartDate_Format = traning_StartDate_Format;
    }

    public String getTraning_StartTime() {
        return Traning_StartTime;
    }

    public void setTraning_StartTime(String traning_StartTime) {
        Traning_StartTime = traning_StartTime;
    }

    public String getTraning_StartTime_Format() {
        return Traning_StartTime_Format;
    }

    public void setTraning_StartTime_Format(String traning_StartTime_Format) {
        Traning_StartTime_Format = traning_StartTime_Format;
    }

    public String getTraning_Title() {
        return Traning_Title;
    }

    public void setTraning_Title(String traning_Title) {
        Traning_Title = traning_Title;
    }

    String Traning_EndDate,Traning_EndDate_Foramt,Traning_EndTime,Traning_EndTime_Format,Traning_Id,Traning_Image;
    String Traning_Postcode,Traning_StartDate,Traning_StartDate_Format,Traning_StartTime,Traning_StartTime_Format,Traning_Title;

    private ArrayList<JobPhotoList> shopList = new ArrayList<>();

    protected EventBoardList(Parcel in) {
        Capacity = in.readString();
        Register_Status = in.readString();
        Status = in.readString();
        Traning_Address1 = in.readString();
        Traning_Address2 = in.readString();
        Traning_Category_Id = in.readString();
        Traning_Category_Name = in.readString();
        Traning_City = in.readString();
        Traning_ContactPersonname = in.readString();
        Traning_ContactPersonnumber = in.readString();
        Traning_Day = in.readString();
        Traning_Description = in.readString();
        Traning_EndDate = in.readString();
        Traning_EndDate_Foramt = in.readString();
        Traning_EndTime = in.readString();
        Traning_EndTime_Format = in.readString();
        Traning_Id = in.readString();
        Traning_Image = in.readString();
        Traning_Postcode = in.readString();
        Traning_StartDate = in.readString();
        Traning_StartDate_Format = in.readString();
        Traning_StartTime = in.readString();
        Traning_StartTime_Format = in.readString();
        Traning_Title = in.readString();
        trainingPhotoLists = in.createTypedArrayList(JobPhotoList.CREATOR);
   //     shopAddress = in.createTypedArrayList(ShopAddress.CREATOR);
      //  mid = in.readInt();
    //    address=in.createTypedArrayList(ShopAddress.CREATOR);
       }

 //  protected TrainingBoardList(String Capacity,String Register_Status,String Status,String Traning_Address1,String Traning_Address2,String Traning_Category_Id,String Traning_Category_Name,String Traning_City,String Traning_ContactPersonname,String Traning_ContactPersonnumber,String Traning_Day,String Traning_Description)
   protected EventBoardList(String Capacity,String Register_Status,String Status,String Traning_Address1,String Traning_Address2,String Traning_Category_Id,String Traning_Category_Name,String Traning_City,String Traning_ContactPersonname,String Traning_ContactPersonnumber,String Traning_Day,String Traning_Description,String Traning_EndDate,String Traning_EndDate_Foramt,String Traning_EndTime,String Traning_EndTime_Format,String Traning_Id,String Traning_Image,String Traning_Postcode,String Traning_StartDate,String Traning_StartDate_Format,String Traning_StartTime,String Traning_StartTime_Format,String Traning_Title,ArrayList<JobPhotoList> trainingPhotoLists)
   {
       this.Capacity = Capacity;
       this.Register_Status = Register_Status;
       this.Status = Status;
       this.Traning_Address1 = Traning_Address1;
       this.Traning_Address2 = Traning_Address2;
       this.Traning_Category_Id = Traning_Category_Id;
       this.Traning_Category_Name = Traning_Category_Name;
       this.Traning_City = Traning_City;
       this.Traning_ContactPersonname = Traning_ContactPersonname;
       this.Traning_ContactPersonnumber = Traning_ContactPersonnumber;
       this.Traning_Day = Traning_Day;
       this.Traning_Description = Traning_Description;
       this.Traning_EndDate = Traning_EndDate;
       this.Traning_EndDate_Foramt = Traning_EndDate_Foramt;
       this.Traning_EndTime = Traning_EndTime;
       this.Traning_EndTime_Format = Traning_EndTime_Format;
       this.Traning_Id = Traning_Id;
       this.Traning_Image = Traning_Image;
       this.Traning_Postcode = Traning_Postcode;
       this.Traning_StartDate = Traning_StartDate;
       this.Traning_StartDate_Format = Traning_StartDate_Format;
       this.Traning_StartTime = Traning_StartTime;
       this.Traning_StartTime_Format = Traning_StartTime_Format;
       this.Traning_Title = Traning_Title;
       this.trainingPhotoLists=trainingPhotoLists;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Capacity);
        dest.writeString(Register_Status);
        dest.writeString(Status);
        dest.writeString(Traning_Address1);
        dest.writeString(Traning_Address2);
        dest.writeString(Traning_Category_Id);
        dest.writeString(Traning_Category_Name);
        dest.writeString(Traning_City);
        dest.writeString(Traning_ContactPersonname);
        dest.writeString(Traning_ContactPersonnumber);
        dest.writeString(Traning_Day);
        dest.writeString(Traning_Description);
        dest.writeString(Traning_EndDate);
        dest.writeString(Traning_EndDate_Foramt);
        dest.writeString(Traning_EndTime);
        dest.writeString(Traning_EndTime_Format);
        dest.writeString(Traning_Id);
        dest.writeString(Traning_Image);
        dest.writeString(Traning_Postcode);
        dest.writeString(Traning_StartDate);
        dest.writeString(Traning_StartDate_Format);
        dest.writeString(Traning_StartTime);
        dest.writeString(Traning_StartTime_Format);
        dest.writeString(Traning_Title);
        dest.writeTypedList(trainingPhotoLists);
    }



    /**
     * @return The trainingPhotoLists
     */
   public ArrayList<JobPhotoList> getTrainingPhotoLists() {
        return trainingPhotoLists;
    }

    /**
     * @param // shopAddress The MovieImages
     */
    public void setTrainingPhotoLists(ArrayList<JobPhotoList> trainingPhotoLists) {
        this.trainingPhotoLists = trainingPhotoLists;
    }



    public static final Creator<JobsBoardList> CREATOR = new Creator<JobsBoardList>() {
        @Override
        public JobsBoardList createFromParcel(Parcel in) {
            return new JobsBoardList(in);
        }

        @Override
        public JobsBoardList[] newArray(int size) {
            return new JobsBoardList[size];
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
