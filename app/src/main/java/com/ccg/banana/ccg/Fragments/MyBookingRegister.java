package com.ccg.banana.ccg.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.ccg.banana.ccg.Activity.Home;
import com.ccg.banana.ccg.Login;
import com.ccg.banana.ccg.R;
import com.ccg.banana.ccg.ServiceClass.ConnectionDetector;
import com.ccg.banana.ccg.ServiceClass.Report;
import com.ccg.banana.ccg.ServiceClass.ServiceClass;
import com.ccg.banana.ccg.session.Cache;
import com.ccg.banana.ccg.session.CatchValue;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DisplayMessageBoard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyBookingRegister extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView description,date,msgBoardTitle,traineName;
    ImageView msgImage,call,register_button,reg_success;
    VideoView videoView;
    private static final int REQUEST_CALL = 1;
    Button send,cancel;
    String d,day,loc,start_time;
    EditText commentText;
    String ppic;
    ProgressDialog pDialog;
    TextView traineName2,contact,addressText,descriptionText,login;
    Intent callIntent;
    String ph,Traning_Id;
    private OnFragmentInteractionListener mListener;
    ArrayList<MessageBoardList> list;
    ArrayList<TrainingPhotoList> list2;
    ProgressDialog progressDialog;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    JSONObject resultJsonObject;
    String responseMessage,Register_Status;
    String ModuleType,ModuleString,ModuleId;

    public MyBookingRegister() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DisplayMessageBoard.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayMessageBoard newInstance(String param1, String param2) {
        DisplayMessageBoard fragment = new DisplayMessageBoard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.mybooking_board, container, false);
        // Inflate the layout for this fragment
        Cache.putData(CatchValue.BackArrow, getContext(), "MyBookingRegister", Cache.CACHE_LOCATION_DISK);
        ((Home)getActivity()).BackArrowMethod();
        description = (TextView)v.findViewById(R.id.description);
        date = (TextView)v.findViewById(R.id.date);
        msgBoardTitle = (TextView)v.findViewById(R.id.msgBoardTitle);
        traineName = (TextView)v.findViewById(R.id.traineName);
        msgImage = (ImageView)v.findViewById(R.id.msgImage);
        register_button = (ImageView)v.findViewById(R.id.register_button);
        reg_success =(ImageView)v.findViewById(R.id.reg_success);

        videoView = (VideoView)v.findViewById(R.id.videoView);
        send = (Button)v.findViewById(R.id.send);
        commentText = (EditText) v.findViewById(R.id.commentText);
        traineName2 = (TextView)v.findViewById(R.id.traineName2);
        contact = (TextView)v.findViewById(R.id.contact);
        login = (TextView)v.findViewById(R.id.login);
        call = (ImageView)v.findViewById(R.id.call);
        addressText = (TextView)v.findViewById(R.id.addressText);
        descriptionText =  (TextView)v.findViewById(R.id.descriptionText);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        ph = sharedPrefs.getString("Traning_ContactPersonnumber","");
        Register_Status = sharedPrefs.getString("Register_Status","");
        cd = new ConnectionDetector(getContext());
        isInternetPresent = cd.isConnectionAvailable();



        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callIntent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+contact.getText().toString()));
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
                }else {
                    startActivity(callIntent);
                }
            }
        });


      //  SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
      //  String s = sharedPrefs.getString("Message_Description", "");

        String Message_Type = sharedPrefs.getString("Message_Type","");
        Traning_Id  = sharedPrefs.getString("Traning_Id","");
try {
    Gson gson = new Gson();
    String json = sharedPrefs.getString("MyObject", "");
    TrainingBoardList obj = gson.fromJson(json, TrainingBoardList.class);
    LinearLayout layout = (LinearLayout) v.findViewById(R.id.linear);
 //  Log.e("position "," "+obj.getTrainingPhotoLists().size());
for(int k=0;k<=obj.getTrainingPhotoLists().size();k++)
{
   // Log.e("position "," "+sharedPrefs.getString("position",""));
    Toast.makeText(getContext(), " " + obj.getTrainingPhotoLists().get(k).getTraning_Photo(), Toast.LENGTH_SHORT).show();
    ImageView imageView = new ImageView(getContext());
    imageView.setId(k);

   // imageView.getLayoutParams().height = 20;
    imageView.setImageBitmap(BitmapFactory.decodeResource(
            getResources(), R.mipmap.user));
    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    int width = 360;
    int height = 260;
    LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
    imageView.setLayoutParams(parms);
    layout.addView(imageView);

    new DownLoadImageTask(imageView).execute(obj.getTrainingPhotoLists().get(k).getTraning_Photo());
}




}catch (Exception e)
{

}

    register_button.setVisibility(View.GONE);
    reg_success.setVisibility(View.VISIBLE);
    login.setVisibility(View.GONE);
        ModuleType = ((String) Cache.getData(CatchValue.ModuleType,getContext()));

        if(ModuleType.equalsIgnoreCase("3"))
            ModuleString = "3";
        else
        if(ModuleType.equalsIgnoreCase("2"))
            ModuleString = "2";
          //  ModuleString = "Job_ID=2";
        else
            ModuleString = "1";
          //  ModuleString = "Event_ID=1";

                  isInternetPresent = cd.isConnectionAvailable();
                  if (isInternetPresent) {

                      new RegisterTask().execute(((String) Cache.getData(CatchValue.ID,getContext())),ModuleString);
                  } else {
                    //  ShowNoInternetDialog();
                  }

        d = sharedPrefs.getString("Message_Post_DateTime_Format", "");
        day = sharedPrefs.getString("Traning_Day", "");

        loc= sharedPrefs.getString("Trainee_Name", "");
        start_time= sharedPrefs.getString("Traning_StartTime_Format", "");

        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "hel_bold.ttf");
        msgBoardTitle.setTypeface(custom_font);

        custom_font = Typeface.createFromAsset(getContext().getAssets(), "hel_medium.ttf");
        //   txtItemName.setTypeface(custom_font);
        date.setTypeface(custom_font);
        traineName.setTypeface(custom_font);

        msgImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubsamplingScaleImageView descImage;

                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.zoom_image);
                Button cancel = (Button) dialog.findViewById(R.id.cancel);
                descImage = (SubsamplingScaleImageView)dialog.findViewById(R.id.descImage);
                // Log.e("ppic  "," "+ppic);
                if (!TextUtils.isEmpty(ppic)) {
                    new DownLoadImageTaskZoom(descImage).execute(ppic);
                } else {
                    msgImage.setImageResource(R.drawable.no_image);
                }


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

      //  description.setText(s);
        return v;
    }

    class DownLoadImageTaskZoom extends AsyncTask<String, Void, Bitmap> {
        SubsamplingScaleImageView imageView;

        public DownLoadImageTaskZoom(SubsamplingScaleImageView imageView) {
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String... urls) {
            String urlOfImage = urls[0];
            Bitmap logo = null;

            try {
                if (!urlOfImage.contains("data:image/jpeg;base64")) {
                    InputStream in = new java.net.URL(urlOfImage).openStream();
                    logo = BitmapFactory.decodeStream(in);
                } else {
                    String actualBitmap = urlOfImage.substring(0, urlOfImage.indexOf(",") + 1);
                    urlOfImage = urlOfImage.replace(actualBitmap, "");
                    logo = bitmapConvert(urlOfImage);
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
            return logo;
        }

        protected void onPostExecute(Bitmap result) {
            if(result!=null){
                // imageView.setImageBitmap(result);
                imageView.setImage(ImageSource.bitmap(result));
            }
            else {
                // imageView.setImageResource(R.mipmap.pic);
                imageView.setImage(ImageSource.resource(R.mipmap.pic));
            }
        }
    }

    class DownLoadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String... urls) {
            String urlOfImage = urls[0];
            Bitmap logo = null;

            try {
                if (!urlOfImage.contains("data:image/jpeg;base64")) {
                    InputStream in = new java.net.URL(urlOfImage).openStream();
                    logo = BitmapFactory.decodeStream(in);
                } else {
                    String actualBitmap = urlOfImage.substring(0, urlOfImage.indexOf(",") + 1);
                    urlOfImage = urlOfImage.replace(actualBitmap, "");
                    logo = bitmapConvert(urlOfImage);
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
            return logo;
        }

        protected void onPostExecute(Bitmap result) {
            if(result!=null){
                imageView.setImageBitmap(result);
            }
            else {
                imageView.setImageResource(R.mipmap.pic);
            }
        }
    }

    private Bitmap bitmapConvert(String Image) {
        byte[] decodedString = Base64.decode(Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CALL:
            {
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    startActivity(callIntent);
                }else{
                    ////
                }
            }
        }
    }

    private class RegisterTask extends AsyncTask<String,Void,Report> {

        Report response = new Report();

        @Override
        protected void onPreExecute() {
            showProgressDialog();
        }

        @Override
        protected Report doInBackground(String... params) {
            try {
                JSONObject jObject = new JSONObject();

                jObject.put("UserID", params[0]);
                jObject.put("EventID", params[1]);
               /* Log.e("1234 UserID  "," "+params[0]);
                Log.e("1234 EventID  "," "+params[1]);*/
               ModuleId = ((String) Cache.getData(CatchValue.ModuleId,getContext()));
                if(ModuleType.equalsIgnoreCase("3"))
                    response = new ServiceClass().getJsonObjectResponse("http://ccg.bananaappscenter.com/api/Events/GetTraningbyUserID?UserID="+params[0]+"&Traning_ID="+ModuleId);
                                                                      // http://ccg.bananaappscenter.com/api/Events/GetTraningbyUserID?UserID=4&Traning_ID=3
                else
                    if(ModuleType.equalsIgnoreCase("2"))
                    response = new ServiceClass().getJsonObjectResponse("http://ccg.bananaappscenter.com/api/Events/GetJobbyUserID?UserID="+params[0]+"&Job_ID="+ModuleId);
                    //  ModuleString = "Job_ID=2";
                else
                    response = new ServiceClass().getJsonObjectResponse("http://ccg.bananaappscenter.com/api/Events/GetEventbyUserID?UserID="+params[0]+"&Event_ID="+ModuleId);


            } catch (JSONException e) {
                showToast("Server couldn't respond,Please try again");
            }
            return response;
        }

        @Override
        protected void onPostExecute(Report response) {
            dismissProgressDialog();
            if (response!=null){
                gerRegisterResponse(response);
            }
            else {
                showToast("Server couldn't respond,Please try again");
            }
        }

    }
    public void showToast(String txt) {
        Toast toast = Toast.makeText(getContext(), txt, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
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

    private void gerRegisterResponse(Report response) {

        try {

            if (response.getStatus().equalsIgnoreCase("true")) {
                resultJsonObject = response.getJsonObject();
                if(resultJsonObject.length()>0&&resultJsonObject!=null) {
                    JSONObject resultJson=resultJsonObject.getJSONObject("Msg");
                    if(resultJson.length()>0&& resultJson!=null){
                        if (resultJson.getString("StatusCode").equalsIgnoreCase("200")) {
                            ModuleType = ((String) Cache.getData(CatchValue.ModuleType,getContext()));
                            if(ModuleType.equalsIgnoreCase("3"))
                            {
                                responseMessage = resultJson.getString("Message");
                                msgBoardTitle.setText(resultJsonObject.getString("Traning_Title"));
                                date.setText(resultJsonObject.getString("Traning_StartDate_Format")+" "+resultJsonObject.getString("Traning_Day"));

                                String ecity = resultJsonObject.getString("Traning_City");
                                if(ecity.equalsIgnoreCase("null"))
                                    ecity="";
                                traineName.setText(ecity+" "+resultJsonObject.getString("Traning_StartTime_Format"));

                                //traineName.setText(resultJsonObject.getString("Traning_City")+" "+resultJsonObject.getString("Traning_StartTime_Format"));
                                traineName2.setText(resultJsonObject.getString("Traning_ContactPersonname"));
                                contact.setText(resultJsonObject.getString("Traning_ContactPersonnumber"));


                                //addressText.setText(resultJsonObject.getString("Traning_Address1")+ "\n"+resultJsonObject.getString("Traning_Address2"));

                                if(!resultJsonObject.getString("Traning_Address1").equalsIgnoreCase("null") || !resultJsonObject.getString("Traning_Address2").equalsIgnoreCase("null"))
                                addressText.setText(resultJsonObject.getString("Traning_Address1")+ "\n"+resultJsonObject.getString("Traning_Address2"));
                                else
                                    addressText.setText("");

                                descriptionText.setText(resultJsonObject.getString("Traning_Description"));

                                ppic = resultJsonObject.getString("Traning_Image");
                                if (!TextUtils.isEmpty(ppic)) {
                                    new DownLoadImageTask(msgImage).execute(ppic);
                                } else {
                                    msgImage.setImageResource(R.drawable.no_image);
                                }
                            }
                            else
                            if(ModuleType.equalsIgnoreCase("2"))
                            {
                                responseMessage = resultJson.getString("Message");
                                msgBoardTitle.setText(resultJsonObject.getString("Job_Title"));
                                date.setText(resultJsonObject.getString("Job_StartDate_Format")+" "+resultJsonObject.getString("Job_Day"));

                                String ecity = resultJsonObject.getString("Job_City");
                                if(ecity.equalsIgnoreCase("null"))
                                    ecity="";
                                traineName.setText(ecity);

                              //  traineName.setText(resultJsonObject.getString("Job_City"));
                                traineName2.setText(resultJsonObject.getString("Job_ContactPersonname"));
                                contact.setText(resultJsonObject.getString("Job_ContactPersonnumber"));

                              //  addressText.setText(resultJsonObject.getString("Job_Address1")+ "\n"+resultJsonObject.getString("Job_Address2"));

                                if(!resultJsonObject.getString("Job_Address1").equalsIgnoreCase("null") || !resultJsonObject.getString("Job_Address2").equalsIgnoreCase("null"))
                                    addressText.setText(resultJsonObject.getString("Job_Address1")+ "\n"+resultJsonObject.getString("Job_Address2"));
                                else
                                    addressText.setText("");

                                descriptionText.setText(resultJsonObject.getString("Job_Description"));

                                ppic = resultJsonObject.getString("Job_Image");
                                if (!TextUtils.isEmpty(ppic)) {
                                    new DownLoadImageTask(msgImage).execute(ppic);
                                } else {
                                    msgImage.setImageResource(R.drawable.no_image);
                                }
                            }
                            else
                            {
                                responseMessage = resultJson.getString("Message");
                                msgBoardTitle.setText(resultJsonObject.getString("Event_Title"));
                                date.setText(resultJsonObject.getString("Event_StartDate_Format")+" "+resultJsonObject.getString("Event_Day"));
                                String ecity = resultJsonObject.getString("Event_City");
                                if(ecity.equalsIgnoreCase("null"))
                                    ecity="";
                                traineName.setText(ecity+" "+resultJsonObject.getString("Event_StartTime_Format"));
                                traineName2.setText(resultJsonObject.getString("Event_ContactPersonname"));
                                contact.setText(resultJsonObject.getString("Event_ContactPersonnumber"));

                               // addressText.setText(resultJsonObject.getString("Event_Address1")+ "\n"+resultJsonObject.getString("Event_Address2"));

                               /* Log.e("Event_Address2 "," "+resultJsonObject.getString("Event_Address1"));
                                Log.e("Event_Address2 "," "+resultJsonObject.getString("Event_Address1").length());
                                Log.e("Event_Address2 "," "+resultJsonObject.getString("Event_Address2"));
                                Log.e("Event_Address2 "," "+resultJsonObject.getString("Event_Address2").length());*/


                                if(!resultJsonObject.getString("Event_Address1").equalsIgnoreCase("null") || !resultJsonObject.getString("Event_Address2").equalsIgnoreCase("null"))
                                addressText.setText(resultJsonObject.getString("Event_Address1")+ "\n"+resultJsonObject.getString("Event_Address2"));
                                else
                                    addressText.setText("");
                              //  descriptionText.setText(resultJsonObject.getString("Traning_Description"));


                                descriptionText.setText(resultJsonObject.getString("Event_Description"));

                                ppic = resultJsonObject.getString("Event_Image");
                                if (!TextUtils.isEmpty(ppic)) {
                                    new DownLoadImageTask(msgImage).execute(ppic);
                                } else {
                                    msgImage.setImageResource(R.drawable.no_image);
                                }
                            }


                        }
                        else
                        {
                            responseMessage = resultJson.getString("Message");
                            Toast.makeText(getContext(),responseMessage,Toast.LENGTH_SHORT).show();
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

                resultJsonObject = response.getJsonObject();
                resultJsonObject.getString("Message");
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

            showToast("Server couldn't respond,Please try again");
        }

    }
}
