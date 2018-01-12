package com.ccg.banana.ccg.Fragments;

import android.os.Parcel;
import android.os.Parcelable;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MessageBoardList implements Parcelable {


    String Content_Id;

    public String getContent_Id() {
        return Content_Id;
    }

    public void setContent_Id(String content_Id) {
        Content_Id = content_Id;
    }

    public String getContent_Type() {
        return Content_Type;
    }

    public void setContent_Type(String content_Type) {
        Content_Type = content_Type;
    }

    public String getMesage_Website_Link() {
        return Mesage_Website_Link;
    }

    public void setMesage_Website_Link(String mesage_Website_Link) {
        Mesage_Website_Link = mesage_Website_Link;
    }

    public String getMessage_Id() {
        return Message_Id;
    }

    public void setMessage_Id(String message_Id) {
        Message_Id = message_Id;
    }

    public String getMessage_Post_DateTime() {
        return Message_Post_DateTime;
    }

    public void setMessage_Post_DateTime(String message_Post_DateTime) {
        Message_Post_DateTime = message_Post_DateTime;
    }

    public String getMessage_Post_DateTime_Format() {
        return Message_Post_DateTime_Format;
    }

    public void setMessage_Post_DateTime_Format(String message_Post_DateTime_Format) {
        Message_Post_DateTime_Format = message_Post_DateTime_Format;
    }

    public String getMessage_Title() {
        return Message_Title;
    }

    public void setMessage_Title(String message_Title) {
        Message_Title = message_Title;
    }

    public String getMessage_Type() {
        return Message_Type;
    }

    public void setMessage_Type(String message_Type) {
        Message_Type = message_Type;
    }

    public String getMessage_URL() {
        return Message_URL;
    }

    public void setMessage_URL(String message_URL) {
        Message_URL = message_URL;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    String Content_Type;
    String Mesage_Website_Link;
    String Message_Id;
    String Message_Post_DateTime;
    String Message_Post_DateTime_Format;
    String Message_Title;
    String Message_Type;
    String Message_URL;
    String Status;

    public String getMessage_Description() {
        return Message_Description;
    }

    public void setMessage_Description(String message_Description) {
        Message_Description = message_Description;
    }

    String Message_Description;

    public String getTrainee_Name() {
        return Trainee_Name;
    }

    public void setTrainee_Name(String trainee_Name) {
        Trainee_Name = trainee_Name;
    }

    String Trainee_Name;


    @SerializedName("MessageComments")
    @Expose
    private ArrayList<MessageComments> messageComments = new ArrayList<>();




    protected MessageBoardList(Parcel in) {
        Content_Id = in.readString();
        Content_Type = in.readString();
        Mesage_Website_Link = in.readString();
        Message_Id = in.readString();
        Message_Post_DateTime = in.readString();
        Message_Post_DateTime_Format = in.readString();
        Message_Title = in.readString();
        Message_Type = in.readString();
        Message_URL = in.readString();
        Status = in.readString();
        Trainee_Name = in.readString();
        Message_Description = in.readString();
        messageComments = in.createTypedArrayList(MessageComments.CREATOR);

   //     shopAddress = in.createTypedArrayList(ShopAddress.CREATOR);
      //  mid = in.readInt();
    //    address=in.createTypedArrayList(ShopAddress.CREATOR);
       }

   // protected DoctorList(Double in, String CategoryList,ArrayList<ShopAddress> shopAddress,Boolean chainbusiness,int mid,String mname,String shop_image,String shop_name, int sid,int sstatus,String website) {
 //  protected MessageBoardList(String Content_Id,String Content_Type,String Mesage_Website_Link,String Message_Id,String Message_Post_DateTime,String Message_Post_DateTime_Format,String Message_Title,String Message_Type,String Message_URL,String Status,String Message_Description,String Trainee_Name)
    protected MessageBoardList(String Content_Id,String Content_Type,String Mesage_Website_Link,String Message_Id,String Message_Post_DateTime,String Message_Post_DateTime_Format,String Message_Title,String Message_Type,String Message_URL,String Status,String Message_Description,String Trainee_Name,ArrayList<MessageComments> messageComments)
  // protected HosDocList(String hdid)
   {
       this.Content_Id = Content_Id;
       this.Content_Type = Content_Type;
       this.Mesage_Website_Link = Mesage_Website_Link;
       this.Message_Id = Message_Id;
       this.Message_Post_DateTime = Message_Post_DateTime;
       this.Message_Post_DateTime_Format = Message_Post_DateTime_Format;
       this.Message_Title = Message_Title;
       this.Message_Type = Message_Type;
       this.Message_URL = Message_URL;
       this.Status = Status;
       this.Trainee_Name = Trainee_Name;
       this.Message_Description = Message_Description;
       this.messageComments=messageComments;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Content_Id);
        dest.writeString(Content_Type);
        dest.writeString(Mesage_Website_Link);
        dest.writeString(Message_Id);
        dest.writeString(Message_Post_DateTime);
        dest.writeString(Message_Post_DateTime_Format);
        dest.writeString(Message_Title);
        dest.writeString(Message_Type);
        dest.writeString(Message_URL);
        dest.writeString(Status);
        dest.writeString(Message_Description);
        dest.writeString(Trainee_Name);
        dest.writeTypedList(messageComments);
    }



    /**
     * @return The doctorList
     */
/*    public ArrayList<ShopAddress> getShopAddress() {
        return shopAddress;
    }*/

    /**
     * @param // shopAddress The MovieImages
     */
/*    public void setShopAddress(ArrayList<ShopAddress> shopAddress) {
        this.shopAddress = shopAddress;
    }*/


    /**
     * @return The messageBoardList
     */
    public ArrayList<MessageComments> getTrainingPhotoLists() {
        return messageComments;
    }

    /**
     * @param messageComments The MovieImages
     */
    public void setTrainingPhotoLists(ArrayList<MessageComments> messageComments) {
        this.messageComments = messageComments;
    }


    public static final Creator<MessageBoardList> CREATOR = new Creator<MessageBoardList>() {
        @Override
        public MessageBoardList createFromParcel(Parcel in) {
            return new MessageBoardList(in);
        }

        @Override
        public MessageBoardList[] newArray(int size) {
            return new MessageBoardList[size];
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
