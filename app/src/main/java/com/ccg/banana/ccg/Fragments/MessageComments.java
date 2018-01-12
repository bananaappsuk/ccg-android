package com.ccg.banana.ccg.Fragments;

import android.os.Parcel;
import android.os.Parcelable;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MessageComments implements Parcelable {

    @SerializedName("Comment_By")
    @Expose
    private String Comment_By;

    public String getComment_By() {
        return Comment_By;
    }

    public void setComment_By(String comment_By) {
        Comment_By = comment_By;
    }

    public String getComment_Posted_Date_Format() {
        return Comment_Posted_Date_Format;
    }

    public void setComment_Posted_Date_Format(String comment_Posted_Date_Format) {
        Comment_Posted_Date_Format = comment_Posted_Date_Format;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    @SerializedName("Comment_Posted_Date_Format")
    @Expose
    private String Comment_Posted_Date_Format;

    @SerializedName("Comment")
    @Expose
    private String Comment;



    protected MessageComments(Parcel in) {
        Comment_By = in.readString();
        Comment_Posted_Date_Format = in.readString();
        Comment = in.readString();
    }

    protected MessageComments(String Comment_By,String Comment_Posted_Date_Format,String Comment) {
        this.Comment_By=Comment_By;
        this.Comment_Posted_Date_Format=Comment_Posted_Date_Format;
        this.Comment=Comment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Comment_By);
        dest.writeString(Comment_Posted_Date_Format);
        dest.writeString(Comment);
    }

    public static final Creator<MessageComments> CREATOR = new Creator<MessageComments>() {
        @Override
        public MessageComments createFromParcel(Parcel in) {
            return new MessageComments(in);
        }

        @Override
        public MessageComments[] newArray(int size) {
            return new MessageComments[size];
        }
    };



}
