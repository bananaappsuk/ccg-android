package com.ccg.banana.ccg;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.ccg.banana.ccg.Activity.BaseActivity;
import com.ccg.banana.ccg.Activity.Home;
import com.ccg.banana.ccg.Fragments.TrainingRegister;
import com.ccg.banana.ccg.ServiceClass.ConnectionDetector;
import com.ccg.banana.ccg.ServiceClass.Report;
import com.ccg.banana.ccg.ServiceClass.ServiceClass;
import com.ccg.banana.ccg.session.Cache;
import com.ccg.banana.ccg.session.CatchValue;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by BananaApps on 5/29/2017.
 */

public class Login extends BaseActivity  {
    EditText username,password;
    TextView agree,login,welcome,see,forgot;
    ImageView agreeImg,loginImg;
    Boolean tick_flag=false;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    String un,pd;


    JSONObject resultJsonObject;
    String responseMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        welcome = (TextView) findViewById(R.id.welcome);
        see = (TextView) findViewById(R.id.see);
        agree = (TextView) findViewById(R.id.agree);



        login = (TextView) findViewById(R.id.login);
        forgot = (TextView) findViewById(R.id.forgot);

        agreeImg = (ImageView) findViewById(R.id.agreeImg);
        loginImg = (ImageView) findViewById(R.id.loginImg);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "hel_medium.ttf");
        username.setTypeface(custom_font);
        password.setTypeface(custom_font);

        String userN = ((String) Cache.getData(CatchValue.USER_NAME,this));
      //  showToast("username "+userN);
        if(!TextUtils.isEmpty(userN))
        {
           // showToast("username "+userN);
            username.setText(userN);
            password.setText((String) Cache.getData(CatchValue.password,this));
        }

        custom_font = Typeface.createFromAsset(getAssets(), "hel_bold.ttf");
        agree.setTypeface(custom_font);
        login.setTypeface(custom_font);
        forgot.setTypeface(custom_font);

        custom_font = Typeface.createFromAsset(getAssets(), "hel_medium.ttf");
        welcome.setTypeface(custom_font);
        see.setTypeface(custom_font);

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

        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(Login.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.login_terms);

                Button cancel = (Button) dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

              /*  AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);

                builder.setMessage("By downloading or using the app, these terms will automatically apply to you – you should make sure therefore that you read them carefully before using the app. We are offering you this app to use for your own personal use without cost, but you should be aware that you cannot send it on to anyone else, and you’re not allowed to copy, or modify the app, any part of the app, or our trademarks in any way. You’re not allowed to attempt to extract the source code of the app, and you also shouldn’t try to translate the app into other languages, or make derivative versions. The app itself, and all the trade marks, copyright, database rights and other intellectual property rights related to it, still belong to Chosen Care Group.\n" +
                        "\n" + "Chosen care Group is committed to ensuring that the app is as useful and efficient as possible. For that reason, we reserve the right to make changes to the app or to charge for its services, at any time and for any reason. We will never charge you for the app or its services.\n" +
                        "\n" +   "The Chosen Care Group app stores and processes personal data that you have provided to us so that you can book trainings or events. It’s your responsibility to keep your phone and access to the app secure. We therefore recommend that you do not jailbreak or root your phone, which is the process of removing software restrictions and limitations imposed by the official operating system of your device. It could make your phone vulnerable to malware/viruses/malicious programs, compromise your phone’s security features and it could mean that the Chosen Care Group app won’t work properly or at all. \n" +
                        "\n" +
                        "The connection can be Wi-Fi, or provided by your mobile network provider, but Chosen Care Group cannot take responsibility for the app not working at full functionality if you don’t have access to Wi-Fi, and you don’t have any of your data allowance left.\n" +
                        "\n" +
                        "If you’re using the app outside of an area with Wi-Fi, you should remember that your terms of agreement with your mobile network provider will still apply. As a result, you may be charged by your mobile provider for the cost of data for the duration of the connection while accessing the app, or other third-party charges. In using the app, you’re accepting responsibility for any such charges, including roaming data charges if you use the app outside of your home territory (i.e. region or country) without turning off data roaming. If you are not the bill payer for the device on which you’re using the app, please be aware that we assume that you have received permission from the bill payer for using the app.\n" +
                        "\n" +
                        "Along the same lines, Chosen Care Group cannot always take responsibility for the way you use the app. At some point we may wish to update the app. The app is currently available on Android and iOS – the requirements for both systems (and for any additional systems we decide to extend the availability of the app to) may change, and you’ll need to download the updates if you want to keep using the app. \n" +
                        "Chosen Care Group does not promise that it will always update the app so that it is relevant to you and/or works with the iOS/Android version that you have installed on your device. However, you promise to always accept updates to the application when offered to you, we may also wish to stop providing the app, and may terminate use of it at any time without giving notice of termination to you. Unless we tell you otherwise, upon any termination, (a) the rights and licenses granted to you in these terms will end; (b) you must stop using the app, and (if needed) delete it from your device.\n")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                AlertDialog alert = builder.create();

                alert.setTitle("Terms and Conditions");

                alert.show();

                alert.getWindow().setLayout(600, 800);*/

            }
        });

        agreeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tick_flag == false) {
                    agreeImg.setImageResource(R.mipmap.check);
                    tick_flag = true;
                } else {
                    agreeImg.setImageResource(R.mipmap.uncheck);
                    tick_flag = false;
                }

            }
        });

        loginImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                un = username.getText().toString().trim();
                pd = password.getText().toString().trim();

                if (TextUtils.isEmpty(un)) {
                    username.setError("Please enter username");
                    username.requestFocus();
                } else if (TextUtils.isEmpty(pd)) {
                    password.setError("Please enter password");
                    password.requestFocus();
                } else if (tick_flag == false) {
                    //  AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(Login.this, R.style.AlertDialogCustom));
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    builder.setMessage(Html.fromHtml("<font color='#FFFFFF'>Please agree terms and conditions.</font>"))
                            .setCancelable(false)

                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog alert = builder.create();

                    alert.setTitle(Html.fromHtml("<font color='#669E5D'>Terms and Conditions</font>"));
                    alert.show();
                } else {
                   isInternetPresent = cd.isConnectionAvailable();
                    if (isInternetPresent) {
                        Cache.putData(CatchValue.USER_NAME, getApplicationContext(), un, Cache.CACHE_LOCATION_DISK);
                        Cache.putData(CatchValue.password, getApplicationContext(), pd, Cache.CACHE_LOCATION_DISK);
                        new LoginTask().execute(un, pd);
                    } else {
                        ShowNoInternetDialog();
                    }
                }
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgotPwd.class);
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
                jObject.put("Password", params[1]);
                response = new ServiceClass().getJsonObjectResponseTest(jObject,"http://bananatech.co.uk/api/User/Login");
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
                showToast("Server couldn't respond,Please try again");
            }
        }

    }


    private void gerLoginResponse(Report response) {
        try {
           // Log.e("Response "," "+response);

            resultJsonObject = response.getJsonObject();
          //  Log.e("Response "," "+resultJsonObject);
            if (response.getStatus().equalsIgnoreCase("true")) {
                resultJsonObject = response.getJsonObject();
                if(resultJsonObject.length()>0&&resultJsonObject!=null) {
                    JSONObject resultJson=resultJsonObject.getJSONObject("Msg");
                    if(resultJson.length()>0&& resultJson!=null){
                        if (resultJson.getString("StatusCode").equalsIgnoreCase("200")) {

                            responseMessage = resultJson.getString("Message");
                            resultJsonObject.getString("Email");
                           // showToast(resultJsonObject.getString("Email"));

                            Cache.putData(CatchValue.Email, getApplicationContext(), resultJsonObject.getString("Email"), Cache.CACHE_LOCATION_DISK);
                            Cache.putData(CatchValue.Mobile, getApplicationContext(), resultJsonObject.getString("Mobile"), Cache.CACHE_LOCATION_DISK);
                            Cache.putData(CatchValue.Name, getApplicationContext(), resultJsonObject.getString("Name"), Cache.CACHE_LOCATION_DISK);
                            Cache.putData(CatchValue.Title, getApplicationContext(), resultJsonObject.getString("Title"), Cache.CACHE_LOCATION_DISK);
                            Cache.putData(CatchValue.LastName, getApplicationContext(), resultJsonObject.getString("LastName"), Cache.CACHE_LOCATION_DISK);
                            Cache.putData(CatchValue.Username, getApplicationContext(), resultJsonObject.getString("Username"), Cache.CACHE_LOCATION_DISK);
                            Cache.putData(CatchValue.User_Pic, getApplicationContext(), resultJsonObject.getString("User_Pic"), Cache.CACHE_LOCATION_DISK);
                            Cache.putData(CatchValue.ID, getApplicationContext(), resultJsonObject.getString("ID"), Cache.CACHE_LOCATION_DISK);
                            Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                            Cache.putData(CatchValue.MyBooking, getApplicationContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
                            Cache.putData(CatchValue.BackArrowRecall, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);
                            Cache.putData(CatchValue.onBackPressed, getApplicationContext(), "backTrue", Cache.CACHE_LOCATION_DISK);

                          //  Log.e("12345  "," "+ (String) Cache.getData(CatchValue.USER_NAME,Login.this));

                            Intent intent = new Intent(this, Home.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else
                        {
                            responseMessage = resultJson.getString("Message");
                            Toast.makeText(Login.this,responseMessage,Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }
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
                    }

                }
                else {


                    showToast("Some thing went wrong please check once");
                }
            }

            else {

                /*resultJsonObject = response.getJsonObject();
                Log.e("250118",""+resultJsonObject);
                resultJsonObject.getString("Message");*/
                showToast("Invalid credentials. ");
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
            try{
                resultJsonObject = response.getJsonObject();
                //Log.e("250118",""+resultJsonObject);
                //String s = resultJsonObject.getString("Message");
                showToast(resultJsonObject.getString("Message"));
            }catch (Exception e)
            {
                showToast("Server couldn't respond,Please try again");
            }
        }
    }

   }



