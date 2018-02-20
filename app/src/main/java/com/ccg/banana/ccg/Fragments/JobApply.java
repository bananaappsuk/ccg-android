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
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
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
public class JobApply extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView description,date,msgBoardTitle,traineName;
    ImageView msgImage,call,register_button,reg_success,fb;
    VideoView videoView;
    private static final int REQUEST_CALL = 1;
    Button send,cancel;
    String d,day,loc,start_time;
    EditText commentText;
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
    private ShareDialog shareDialog;
    public JobApply() {
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
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        // Create a callbackManager to handle the login responses.

        shareDialog = new ShareDialog(this);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.job_board, container, false);
        // Inflate the layout for this fragment
        Cache.putData(CatchValue.BackArrow, getContext(), "JobApply", Cache.CACHE_LOCATION_DISK);
        ((Home)getActivity()).BackArrowMethod();
        description = (TextView)v.findViewById(R.id.description);
        date = (TextView)v.findViewById(R.id.date);
        msgBoardTitle = (TextView)v.findViewById(R.id.msgBoardTitle);
        traineName = (TextView)v.findViewById(R.id.traineName);
        msgImage = (ImageView)v.findViewById(R.id.msgImage);
        register_button = (ImageView)v.findViewById(R.id.register_button);
        reg_success =(ImageView)v.findViewById(R.id.reg_success);
        fb = (ImageView)v.findViewById(R.id.fb);
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
                callIntent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ph));
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
                }else {
                    startActivity(callIntent);
                }
            }
        });


      //  SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
      //  String s = sharedPrefs.getString("Message_Description", "");
        final String ppic = sharedPrefs.getString("Message_URL", "");
        String Message_Type = sharedPrefs.getString("Message_Type","");
        Traning_Id  = sharedPrefs.getString("Traning_Id","");
try {
    Gson gson = new Gson();
    String json = sharedPrefs.getString("MyObject", "");
    TrainingBoardList obj = gson.fromJson(json, TrainingBoardList.class);
    LinearLayout layout = (LinearLayout) v.findViewById(R.id.linear);
// Log.e("obj "," "+obj);
for(int k=0;k<obj.getTrainingPhotoLists().size();k++)
{
   // Toast.makeText(getContext(), " " + obj.getTrainingPhotoLists().get(k).getTraning_Photo(), Toast.LENGTH_SHORT).show();
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

/*    ArrayList<TrainingPhotoList> list;
    list = getArguments().getParcelableArrayList("LIST");

    Toast.makeText(getContext(), "photo " + list.size(), Toast.LENGTH_SHORT).show();
    if (list != null || !list.isEmpty()) {
       for(int ii=0;ii<list.size();ii++)
       {
           Toast.makeText(getContext(), "photo " + list.get(ii).getTraning_Photo(), Toast.LENGTH_SHORT).show();
       }
    }*/

   /* Gson gson = new Gson();
    String json = sharedPrefs.getString("All", null);
    Type type = new TypeToken<ArrayList<TrainingPhotoList>>() {
    }.getType();
    list2 = gson.fromJson(json, type);
    Toast.makeText(getContext(), " " + list2.get(0).getTraning_Photo(), Toast.LENGTH_SHORT).show();
    list2.get(0).getTraning_Photo();*/


}catch (Exception e)
{

}
if(Register_Status.equalsIgnoreCase("1"))
{
    /*register_button.setVisibility(View.GONE);
    reg_success.setVisibility(View.VISIBLE);
    login.setVisibility(View.GONE);*/
    login.setText("Cancel");
}
else
{
   /* register_button.setVisibility(View.VISIBLE);
    reg_success.setVisibility(View.GONE);
    login.setVisibility(View.VISIBLE);*/

    login.setText("Register");
}

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login.getText().toString().trim().equalsIgnoreCase("Register")) {


                ImageView descImage;
                Button reg;
                TextView sorryText,price,Title;
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.alert_description);
                cancel = (Button) dialog.findViewById(R.id.cancel);
                reg = (Button) dialog.findViewById(R.id.reg);

                descImage= (ImageView)dialog.findViewById(R.id.descImage);
                //      descImage.setImageBitmap(new DownloadImageTask(holder.title).execute(slots.getfImageurl()));

                if (!TextUtils.isEmpty(ppic)) {

                    new DownLoadImageTask(descImage).execute(ppic);
                } else {
                   // holder.title.setImageResource(R.drawable.ic_edit_button);
                }

          /*      sorryText = (TextView)dialog.findViewById(R.id.storeClosed);
                Title = (TextView)dialog.findViewById(R.id.sorryText);
                Title.setText(holder.fItemName.getText());
                price = (TextView)dialog.findViewById(R.id.price);
                String s = slots.getPrice();
                sorryText.setText(holder.fItemNameDesc.getText());*/

          reg.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  isInternetPresent = cd.isConnectionAvailable();
                  if (isInternetPresent) {
                   //   Log.e("11111 Traning_Id "," "+Traning_Id);
                   //   Log.e("11111 Traning_Id "," "+((String) Cache.getData(CatchValue.ID,getContext())));
                      dialog.dismiss();
                      new RegisterTask().execute(Traning_Id, ((String) Cache.getData(CatchValue.ID,getContext())),""+2);
                  } else {
                    //  ShowNoInternetDialog();
                  }

              }
          });


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

                }
                else
                {
                    // showToast("Unregister");
                    isInternetPresent = cd.isConnectionAvailable();
                    if (isInternetPresent) {
                        //   showToast("success");
                        //   Log.e("11111 Traning_Id "," "+((String) Cache.getData(CatchValue.ID,getContext())));

                        new UnRegisterTask().execute(Traning_Id, ((String) Cache.getData(CatchValue.ID, getContext())));
                    } else {
                        //  ShowNoInternetDialog();
                    }
                }
            }
        });


  /*      try {
            JSONArray jsonArray2 = new JSONArray(sharedPrefs.getString("All", "[]"));
            for (int i = 0; i < jsonArray2.length(); i++) {
                Log.d("your JSON Array", jsonArray2.getJSONObject(0).getString("Traning_Photo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        d = sharedPrefs.getString("Message_Post_DateTime_Format", "");
        day = sharedPrefs.getString("Traning_Day", "");
        String temp;
        temp = d;
        if(!TextUtils.isEmpty(temp) && !temp.equalsIgnoreCase("null"))
        {

        }
        else
            d ="";

        temp = day;
        if(!TextUtils.isEmpty(temp) && !temp.equalsIgnoreCase("null"))
        {

        }
        else
            day ="";

        date.setText(d+" "+day);

        temp = sharedPrefs.getString("Message_Title", "");

        if(!TextUtils.isEmpty(temp) && !temp.equalsIgnoreCase("null"))
            msgBoardTitle.setText(temp);
        else
            msgBoardTitle.setText("");

        temp = sharedPrefs.getString("Traning_ContactPersonname", "");

        if(!TextUtils.isEmpty(temp) && !temp.equalsIgnoreCase("null"))
            traineName2.setText(temp);
        else
            traineName2.setText("");

        temp = sharedPrefs.getString("Traning_ContactPersonnumber","");

        if(!TextUtils.isEmpty(temp) && !temp.equalsIgnoreCase("null"))
            contact.setText(temp);
        else
            contact.setText("");

        loc= sharedPrefs.getString("Trainee_Name", "");
       // start_time= sharedPrefs.getString("Traning_StartTime_Format", "");
       // traineName.setText(loc);

        temp = loc;
        if(!TextUtils.isEmpty(temp) && !temp.equalsIgnoreCase("null"))
        {
            traineName.setText(loc);
        }
        else

            traineName.setText("");

        temp = sharedPrefs.getString("Traning_Address1","");
        if(!TextUtils.isEmpty(temp) && !temp.equalsIgnoreCase("null"))
        {
            addressText.setText(sharedPrefs.getString("Traning_Address1",""));
        }
        else
            addressText.setText("");


        temp = sharedPrefs.getString("Traning_Description","");
        if(!TextUtils.isEmpty(temp) && !temp.equalsIgnoreCase("null"))
        {
            descriptionText.setText(sharedPrefs.getString("Traning_Description",""));
        }
        else
            descriptionText.setText("");


        if (!TextUtils.isEmpty(ppic)) {
            new DownLoadImageTask(msgImage).execute(ppic);
        } else {
            msgImage.setImageResource(R.drawable.no_image);
        }

        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "hel_bold.ttf");
        msgBoardTitle.setTypeface(custom_font);

        custom_font = Typeface.createFromAsset(getContext().getAssets(), "hel_medium.ttf");
        //   txtItemName.setTypeface(custom_font);
        date.setTypeface(custom_font);
        traineName.setTypeface(custom_font);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Androidlift")
                            .setContentDescription("Androidlift blog")
                            .setContentUrl(Uri.parse(ppic))
                            .build();
                    shareDialog.show(linkContent);
                }
            }
        });

        msgImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubsamplingScaleImageView descImage;


                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.zoom_image);
                Button cancel = (Button) dialog.findViewById(R.id.cancel);
                descImage = (SubsamplingScaleImageView)dialog.findViewById(R.id.descImage);

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

    private class UnRegisterTask extends AsyncTask<String,Void,Report> {

        Report response = new Report();

        @Override
        protected void onPreExecute() {
            showProgressDialog();
        }

        @Override
        protected Report doInBackground(String... params) {
            try {
                JSONObject jObject = new JSONObject();
                jObject.put("ModuleID", params[0]);
                jObject.put("UserID", params[1]);
                //  jObject.put("Module_Type", params[2]);

                response = new ServiceClass().getJsonObjectResponsePost("http://bananatech.co.uk/api/User/Unregistred?ModuleID="+params[0]+"&UserID="+params[1]);
            } catch (JSONException e) {
                showToast("Server couldn't respond,Please try again");
            }
            return response;
        }

        @Override
        protected void onPostExecute(Report response) {
            dismissProgressDialog();
            if (response!=null){
                getUnRegisterResponse(response);
            }
            else {
                showToast("Server couldn't respond,Please try again");
            }
        }

    }

    private void getUnRegisterResponse(Report response) {

        try {
            //  Log.e("1452 Response "," "+response);

            resultJsonObject = response.getJsonObject();
            //      Log.e("1452 resultJsonObject "," "+resultJsonObject);
            if(resultJsonObject!=null) {
                //       Log.e("Response ", " " + resultJsonObject.getString("Message"));
                if (resultJsonObject.getString("StatusCode").equalsIgnoreCase("200")) {
                    //showToast(resultJsonObject.getString("Message"));
                    showToast(resultJsonObject.getString("Message"));
                    Cache.putData(CatchValue.BackArrowRecall, getContext(), "", Cache.CACHE_LOCATION_DISK);
                    /*Intent intent = new Intent(getContext(), Home.class);
                    Cache.putData(CatchValue.Operation, getContext(), "TrainingRegister", Cache.CACHE_LOCATION_DISK);
                    startActivity(intent);*/
                    FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction2.replace(R.id.content, new JobsBoard());
                    transaction2.commit();

                }
            }
            else {


                showToast("Registration Failed please try again");

            }
        } catch (JSONException ex) {

            showToast("Registration Failed please try again");
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
                jObject.put("EventID", params[0]);
                jObject.put("UserID", params[1]);
                jObject.put("Module_Type", params[2]);
             //   Log.e("1141 Response "," "+params[0] +"   "+ params[1]+"  "+params[2]);
           //     response = new ServiceClass().getJsonObjectResponsePost("http://ccg.bananaappscenter.com/api/User/ModuleRegister?EventID="+params[0]+"&UserID="+params[1]+"&Module_Type="+params[2]);
                response = new ServiceClass().getJsonObjectResponsePost("http://bananatech.co.uk/api/User/ModuleRegister?EventID="+params[0]+"&UserID="+params[1]+"&Module_Type="+params[2]);
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
           // Log.e("1141 Response "," "+response);

            resultJsonObject = response.getJsonObject();

         //   Log.e("1141 Response "," "+resultJsonObject);
            if(resultJsonObject!=null) {
              //  Log.e("Response ", " " + resultJsonObject.getString("Message"));
                if (resultJsonObject.getString("StatusCode").equalsIgnoreCase("200")) {
                    //showToast(resultJsonObject.getString("Message"));
                    showToast(resultJsonObject.getString("Message"));
                    Cache.putData(CatchValue.BackArrowRecall, getContext(), "", Cache.CACHE_LOCATION_DISK);
                  /*  Intent intent = new Intent(getContext(), Home.class);
                    Cache.putData(CatchValue.Operation, getContext(), "TrainingRegister", Cache.CACHE_LOCATION_DISK);
                    startActivity(intent);*/
                    FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction2.replace(R.id.content, new JobsBoard());
                    transaction2.commit();

                }
            }
            else {


                showToast("Registration Failed please try again");

            }
        } catch (JSONException ex) {

            showToast("Registration Failed please try again");
        }

    }
}
