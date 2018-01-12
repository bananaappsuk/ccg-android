package com.ccg.banana.ccg.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by BananaApps on 5/29/2017.
 */

public class BaseActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    String EMAIL_REGEX = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
    String EMAIL_REGEX_UK = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}+\\.[A-Za-z]{2,4}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void showToast(String txt) {
        Toast toast = Toast.makeText(this, txt, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    public  void okMessage(String title, String msg)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(msg);
        dlgAlert.setTitle(title);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    public  boolean IsEmpty(String msg)
    {
       if(msg.length()==0 || msg==null)
           return false;
        else
           return true;
    }
    public  boolean IsLength(String ph)
    {
        if(ph.length()!=11)
            return false;
        else
            return true;
    }

    public  boolean IsValidEmail(String msg)
    {
        if(msg.matches(EMAIL_REGEX) || msg.matches(EMAIL_REGEX_UK))
            return true;
        else
            return false;
    }


public void showYesNo()
{
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                   moveTaskToBack(true);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
    builder.setMessage("Are you sure want to exit?").setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show();
}

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }
    public void dismissProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

    }
}
