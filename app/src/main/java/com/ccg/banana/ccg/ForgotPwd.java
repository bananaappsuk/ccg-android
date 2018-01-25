package com.ccg.banana.ccg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.ccg.banana.ccg.Activity.BaseActivity;
import com.ccg.banana.ccg.Activity.Home;
import com.ccg.banana.ccg.ServiceClass.ConnectionDetector;
import com.ccg.banana.ccg.ServiceClass.Report;
import com.ccg.banana.ccg.ServiceClass.ServiceClass;
import com.ccg.banana.ccg.session.Cache;
import com.ccg.banana.ccg.session.CatchValue;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by BananaApps on 5/29/2017.
 * Created by BananaApps on 5/29/2017.
 */

public class ForgotPwd extends BaseActivity  {
    EditText username;
    TextView login,welcome,forgot;
    ImageView agreeImg,loginImg;

    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    String un,pd;


    JSONObject resultJsonObject;
    String responseMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pwd);
        username = (EditText) findViewById(R.id.username);

        welcome = (TextView) findViewById(R.id.welcome);


        login = (TextView) findViewById(R.id.login);
        forgot = (TextView) findViewById(R.id.forgot);

        agreeImg = (ImageView) findViewById(R.id.agreeImg);
        loginImg = (ImageView) findViewById(R.id.loginImg);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "hel_medium.ttf");
        username.setTypeface(custom_font);


        custom_font = Typeface.createFromAsset(getAssets(), "hel_bold.ttf");

        login.setTypeface(custom_font);
        forgot.setTypeface(custom_font);

        custom_font = Typeface.createFromAsset(getAssets(), "hel_bold.ttf");
        welcome.setTypeface(custom_font);


        cd = new ConnectionDetector(this);
        isInternetPresent = cd.isConnectionAvailable();

        String id = ((String) Cache.getData(CatchValue.ID,this));
        if(!TextUtils.isEmpty(id))
        {
            Cache.putData(CatchValue.MyBooking, getApplicationContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
            Intent intent = new Intent(this, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }


        loginImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                un = username.getText().toString().trim();

                if (TextUtils.isEmpty(un)) {
                    username.setError("Please enter Email ID");
                    username.requestFocus();
                }
                else
                if(!IsValidEmail(un))
                {
                    okMessage("Error","Please enter Valid Email ID");
                    username.requestFocus();
                }else {
                   isInternetPresent = cd.isConnectionAvailable();
                    if (isInternetPresent) {

                     /*   showToast("Password send to your registered Email ID.");

                        Intent intent = new Intent(ForgotPwd.this, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);*/
                        new LoginTask().execute(un);
                    } else {
                        ShowNoInternetDialog();
                    }
                }
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPwd.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    public void ShowNoInternetDialog() {
        showAlertDialog(this, "No Internet Connection", "Please check your network.", false);
    }
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setIcon((status) ? R.mipmap.ic_action_checked : R.mipmap.ic_action_warning);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                isInternetPresent = cd.isConnectionAvailable();
                if (isInternetPresent) {
                    new LoginTask().execute(un,pd);
                } else {
                    ShowNoInternetDialog();
                }

            }
        });
        alertDialog.show();
        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(16);
    }

    private class LoginTask extends AsyncTask<String,Void,Report> {

        Report response = new Report();

        @Override
        protected void onPreExecute() {
            showProgressDialog();
        }

        @Override
        protected Report doInBackground(String... params) {
            try {
                JSONObject jObject = new JSONObject();
                jObject.put("Username", params[0]);
                response = new ServiceClass().getJsonObjectResponse(jObject,"http://ccg.bananaappscenter.com/api/User/ForgotPassword?User_Email="+params[0]);
            } catch (JSONException e) {
                showToast("Server couldn't respond,Please try again");
            }
            return response;
        }

        @Override
        protected void onPostExecute(Report response) {
            dismissProgressDialog();
            if (response!=null){
                gerLoginResponse(response);
            }
            else {
                showToast("Server couldn't respond,Please try again 222");
            }
        }

    }


    private void gerLoginResponse(Report response) {
        try {
        /*    Log.e("Response "," "+response);

            resultJsonObject = response.getJsonObject();
            Log.e("Response "," "+resultJsonObject);*/
            if (response.getStatus().equalsIgnoreCase("true")) {
                resultJsonObject = response.getJsonObject();
                if(resultJsonObject.length()>0&&resultJsonObject!=null) {
                   // JSONObject resultJson=resultJsonObject.getJSONObject("Msg");
               //     if(resultJson.length()>0&& resultJson!=null){
                        if (resultJsonObject.getString("StatusCode").equalsIgnoreCase("200")) {

                            responseMessage = resultJsonObject.getString("Message");
                            showToast(responseMessage);
                          //  Log.e("12345  "," "+ (String) Cache.getData(CatchValue.USER_NAME,Login.this));

                            Intent intent = new Intent(this, Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else
                        {
                            responseMessage = resultJsonObject.getString("Message");
                            Toast.makeText(ForgotPwd.this,responseMessage,Toast.LENGTH_SHORT).show();
                            return;
                        }

                   /* }
                    else if(resultJson.getString("isSuccess").equalsIgnoreCase("false")){
                        if (resultJsonObject.getString("StatusCode").equalsIgnoreCase("400")){
                            responseMessage = resultJsonObject.getString("Message");
                            showToast(responseMessage);
                        }
                        else if (resultJsonObject.getString("StatusCode").equalsIgnoreCase("401")){
                            responseMessage = resultJsonObject.getString("Message");
                            showToast(responseMessage);
                        }
                        else if (resultJsonObject.getString("StatusCode").equalsIgnoreCase("405")){
                            responseMessage = resultJsonObject.getString("Message");
                            showToast(responseMessage);
                        }
                    }*/

                }
                else {


                    showToast("Some thing went wrong please check once");
                }
            }

            else {

               // resultJsonObject = response.getJsonObject();
              //  resultJsonObject.getString("Message");
                showToast(""+"Invalid Email_ID");
           /*     if ((response.getStatus().equalsIgnoreCase("false"))) {
                    responseMessage = response.getMessage();
                    if (response.getErr_code() == 400) {
                        showToast(responseMessage);
                    }
                    else if (response.getErr_code() == 401) {
                        showToast(responseMessage);
                    } else if (response.getErr_code() == 500) {
                        showToast(responseMessage);
                    }
                    else if (response.getErr_code() == 405) {
                        showToast(responseMessage);
                    } else {
                        showToast(responseMessage);
                    }
                }*/
            }
        } catch (JSONException ex) {

            showToast("Server couldn't respond,Please try again");
        }

    }

   }



