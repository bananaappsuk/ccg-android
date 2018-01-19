package com.ccg.banana.ccg.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ccg.banana.ccg.Activity.Home;
import com.ccg.banana.ccg.Login;
import com.ccg.banana.ccg.R;
import com.ccg.banana.ccg.ServiceClass.ConnectionDetector;
import com.ccg.banana.ccg.ServiceClass.Report;
import com.ccg.banana.ccg.ServiceClass.ServiceClass;
import com.ccg.banana.ccg.session.Cache;
import com.ccg.banana.ccg.session.CatchValue;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentB.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentB#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentB extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView profile,email,phone,Submit,cancel,homeDesc;
    LinearLayout beforeChangePwdLoutout,changePwdLoutout,editLayout;
    TextView changePassword;
    EditText curPwd,newPwd,cnfPwd,name,titleType;
    ProgressDialog progressDialog;
    ImageView profileImg,show,showNew,showConfirm;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    private String userChooseTask;
    JSONObject resultJsonObject;
    String responseMessage;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private OnFragmentInteractionListener mListener;
    static final Integer CAMERA = 0x1;
    Boolean showFlag = false;
    Boolean showFlagNew = false;
    Boolean showFlagConfirm = false;
    public FragmentB() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentB.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentB newInstance(String param1, String param2) {
        FragmentB fragment = new FragmentB();
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
       /* Home h = new Home();
        h.BackArrowMethod();*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_b, container, false);
        profile = (TextView)v.findViewById(R.id.profile);
        name = (EditText) v.findViewById(R.id.name);
        titleType = (EditText) v.findViewById(R.id.titleType);
        email = (TextView)v.findViewById(R.id.email);
        phone = (TextView)v.findViewById(R.id.phone);
        beforeChangePwdLoutout = (LinearLayout)v.findViewById(R.id.beforeChangePwdLoutout);
        changePwdLoutout = (LinearLayout)v.findViewById(R.id.changePwdLoutout);
        Submit = (TextView) v.findViewById(R.id.Submit);
        cancel = (TextView) v.findViewById(R.id.cancel);
        homeDesc = (TextView) v.findViewById(R.id.homeDesc);
        changePassword = (TextView) v.findViewById(R.id.changePassword);

        curPwd = (EditText) v.findViewById(R.id.curPwd);
        newPwd = (EditText) v.findViewById(R.id.newPwd);
        cnfPwd = (EditText) v.findViewById(R.id.cnfPwd);



        Cache.putData(CatchValue.BackArrow, getContext(), "FragmentB", Cache.CACHE_LOCATION_DISK);
        ((Home)getActivity()).BackArrowMethod();


        editLayout = (LinearLayout)v.findViewById(R.id.editLayout);

        profileImg = (ImageView)v.findViewById(R.id.profileImg);
        showConfirm = (ImageView)v.findViewById(R.id.showConfirm);
        showNew = (ImageView)v.findViewById(R.id.showNew);
        show = (ImageView)v.findViewById(R.id.show);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showFlag.equals(false)) {
                    curPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showFlag = true;
                }
                else
                {
                    showFlag = false;
                    curPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

            }
        });

        showNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showFlagNew.equals(false)) {
                    newPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showFlagNew = true;
                }
                else
                {
                    showFlagNew = false;
                    newPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

            }
        });

        showConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showFlagConfirm.equals(false)) {
                    cnfPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showFlagConfirm = true;
                }
                else
                {
                    showFlagConfirm = false;
                    cnfPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

            }
        });


        name.setEnabled(false);
        titleType.setEnabled(false);
        String pImag = ((String) Cache.getData(CatchValue.User_Pic,getContext()));
       // Log.e("pimg","  "+pImag.length());
        if(pImag!=null && pImag.length()!=4) {
            byte[] decodedString = Base64.decode(pImag, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            profileImg.setImageBitmap(decodedByte);
        }
        else
            profileImg.setImageDrawable(getResources().getDrawable(R.mipmap.user));

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);*/
                if(!homeDesc.getText().toString().equals("Edit profile")) {


                    final CharSequence[] items = {getResources().getString(R.string.text_take_photo), getResources().getString(R.string.text_choose_from_gallery)};
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                    builder.setTitle(getResources().getString(R.string.text_add_photo));
                    // Log.e("Camera "," 00");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            boolean result = CameraCapture.checkPermission(getActivity());
                            Log.e("Camera ", " result " + result);
                            if (items[item].equals(getResources().getString(R.string.text_take_photo))) {
                                Log.e("Camera ", " 11");
                                userChooseTask = getResources().getString(R.string.text_take_photo);
                                if (result) {
                                    Log.e("Camera ", " 22");
                                    cameraIntent();

                                }
                            } else if (items[item].equals(getResources().getString(R.string.text_choose_from_gallery))) {
                                userChooseTask = getResources().getString(R.string.text_choose_from_gallery);
                                if (result) {
                                    Log.e("Camera ", " 33");
                                    galleryIntent();
                                }
                            }
                        }
                    });
                    builder.show();

                }
            }
        });

        askForPermission(Manifest.permission.CAMERA, CAMERA);
        name.setText(((String) Cache.getData(CatchValue.Name,getContext())));
        titleType.setText(((String) Cache.getData(CatchValue.Title,getContext())));
        email.setText(((String) Cache.getData(CatchValue.Email,getContext())));
        phone.setText(((String) Cache.getData(CatchValue.Mobile,getContext())));


        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "AbrilFatface-Regular.ttf");
        profile.setTypeface(custom_font);
        name.setTypeface(custom_font);

        custom_font = Typeface.createFromAsset(getContext().getAssets(), "Quicksand-Regular.ttf");
        titleType.setTypeface(custom_font);
        email.setTypeface(custom_font);
        phone.setTypeface(custom_font);

        cd = new ConnectionDetector(getContext());
        isInternetPresent = cd.isConnectionAvailable();

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePwdLoutout.setVisibility(View.VISIBLE);
                beforeChangePwdLoutout.setVisibility(View.GONE);
            }
        });

        changePwdLoutout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePwdLoutout.setVisibility(View.GONE);
                beforeChangePwdLoutout.setVisibility(View.VISIBLE);
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(curPwd.getText().toString().trim()))
                {
                    curPwd.setError("Please enter current password");
                    curPwd.requestFocus();
                }
                else
                if(TextUtils.isEmpty(newPwd.getText().toString().trim()))
                {
                    newPwd.setError("Please enter new password");
                    newPwd.requestFocus();
                }
                else
                if(TextUtils.isEmpty(cnfPwd.getText().toString().trim()))
                {
                    cnfPwd.setError("Please enter confirm password");
                    cnfPwd.requestFocus();
                }
                else
                if(!newPwd.getText().toString().trim().equals(cnfPwd.getText().toString().trim()))
                {
                    Toast.makeText(getContext(),"Password did not match \nPlease try again",Toast.LENGTH_SHORT).show();
                    newPwd.setText("");
                    cnfPwd.setText("");
                }
                else
                {
                    isInternetPresent = cd.isConnectionAvailable();
                    if (isInternetPresent) {

                        new ChangePasswordTask().execute(((String) Cache.getData(CatchValue.ID,getContext())), newPwd.getText().toString().trim());
                    } else {
                        ShowNoInternetDialog();
                    }
                }

            }
        });
        editLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(homeDesc.getText().toString().equals("Edit profile")){
                    homeDesc.setText("Save");
                    name.setEnabled(true);
                    titleType.setEnabled(true);
                }
                else
                {
                    if(TextUtils.isEmpty(name.getText().toString().trim()))
                    {
                        name.setError("Please enter Name");
                        name.requestFocus();
                    }
                    else
                    if(TextUtils.isEmpty(titleType.getText().toString().trim()))
                    {
                        titleType.setError("Please enter Title");
                        titleType.requestFocus();
                    }
                    else {
                        homeDesc.setText("Edit profile");
                        name.setEnabled(false);
                        titleType.setEnabled(false);

                        isInternetPresent = cd.isConnectionAvailable();
                        if (isInternetPresent) {
                            Cache.putData(CatchValue.Name, getContext(), name.getText().toString().trim(), Cache.CACHE_LOCATION_DISK);
                            Cache.putData(CatchValue.Title, getContext(), titleType.getText().toString().trim(), Cache.CACHE_LOCATION_DISK);
                            new ProfileUpdate().execute(((String) Cache.getData(CatchValue.ID,getContext())),name.getText().toString().trim(), titleType.getText().toString().trim(),((String) Cache.getData(CatchValue.User_Pic,getContext())));
                        } else {
                            ShowNoInternetDialog2();
                        }

                    }

                }
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.text_select_file)), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    public void ShowNoInternetDialog() {
        showAlertDialog(getContext(), "No Internet Connection", "Please check your network.", false);
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
                    new ChangePasswordTask().execute(((String) Cache.getData(CatchValue.ID,getContext())), newPwd.getText().toString().trim());
                } else {
                    ShowNoInternetDialog();
                }

            }
        });
        alertDialog.show();
        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(16);
    }


    public void ShowNoInternetDialog2() {
        showAlertDialog2(getContext(), "No Internet Connection", "Please check your network.", false);
    }
    public void showAlertDialog2(Context context, String title, String message, Boolean status) {
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
                    new ProfileUpdate().execute(((String) Cache.getData(CatchValue.ID,getContext())),name.getText().toString().trim(), titleType.getText().toString().trim(),((String) Cache.getData(CatchValue.User_Pic,getContext())));
                } else {
                    ShowNoInternetDialog();
                }

            }
        });
        alertDialog.show();
        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(16);
    }

    private class ChangePasswordTask extends AsyncTask<String,Void,Report> {

        Report response = new Report();

        @Override
        protected void onPreExecute() {
            showProgressDialog();
        }

        @Override
        protected Report doInBackground(String... params) {
            try {
                JSONObject jObject = new JSONObject();
                jObject.put("ID", params[0]);
                jObject.put("Password", params[1]);
                response = new ServiceClass().getJsonObjectResponse(jObject,"http://ccg.bananaappscenter.com/api/User/ChangePassword");
            } catch (JSONException e) {
                Toast.makeText(getContext(),"Server couldn't respond,Please try again",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(),"Server couldn't respond,Please try again",Toast.LENGTH_SHORT).show();
               // showToast("Server couldn't respond,Please try again");
            }
        }
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

    private void gerLoginResponse(Report response) {
        try {
            if (response.getStatus().equalsIgnoreCase("true")) {
                resultJsonObject = response.getJsonObject();
                if(resultJsonObject.length()>0&&resultJsonObject!=null) {
                 //   JSONObject resultJson=resultJsonObject.getJSONObject("Msg");
                    //if(resultJson.length()>0&& resultJson!=null){
                        if (resultJsonObject.getString("StatusCode").equalsIgnoreCase("200")) {

                          //  responseMessage = resultJson.getString("Message");

                            Toast.makeText(getContext(), resultJsonObject.getString("Message"),Toast.LENGTH_SHORT).show();
                            // showToast(resultJsonObject.getString("Email"));

                            Cache.putData(CatchValue.Email, getContext(), "", Cache.CACHE_LOCATION_DISK);
                            Cache.putData(CatchValue.Mobile, getContext(), "", Cache.CACHE_LOCATION_DISK);
                            Cache.putData(CatchValue.Name, getContext(), "", Cache.CACHE_LOCATION_DISK);
                            Cache.putData(CatchValue.Title, getContext(),"", Cache.CACHE_LOCATION_DISK);
                            Cache.putData(CatchValue.User_Pic, getContext(), "", Cache.CACHE_LOCATION_DISK);
                            Cache.putData(CatchValue.ID, getContext(), "", Cache.CACHE_LOCATION_DISK);

                            //  Log.e("12345  "," "+ (String) Cache.getData(CatchValue.USER_NAME,Login.this));

                            Intent intent = new Intent(getContext(), Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else
                        {
                          //  responseMessage = resultJson.getString("Message");
                            Toast.makeText(getContext(), resultJsonObject.getString("Message"),Toast.LENGTH_SHORT).show();
                            return;
                        }

             /*       }
                    else if(resultJson.getString("isSuccess").equalsIgnoreCase("false")){
                        if (resultJsonObject.getString("StatusCode").equalsIgnoreCase("400")){
                            responseMessage = resultJsonObject.getString("Message");
                            Toast.makeText(getContext(),responseMessage,Toast.LENGTH_SHORT).show();
                        }
                        else if (resultJsonObject.getString("StatusCode").equalsIgnoreCase("401")){
                            responseMessage = resultJsonObject.getString("Message");
                            Toast.makeText(getContext(),responseMessage,Toast.LENGTH_SHORT).show();
                        }
                        else if (resultJsonObject.getString("StatusCode").equalsIgnoreCase("405")){
                            responseMessage = resultJsonObject.getString("Message");
                            Toast.makeText(getContext(),responseMessage,Toast.LENGTH_SHORT).show();
                        }
                    }*/

                }
                else {
                    Toast.makeText(getContext(),responseMessage,Toast.LENGTH_SHORT).show();
                }
            }

            else {
                if ((response.getStatus().equalsIgnoreCase("false"))) {
                    responseMessage = response.getMessage();
                    if (response.getErr_code() == 400) {
                        Toast.makeText(getContext(),responseMessage,Toast.LENGTH_SHORT).show();
                    }
                    else if (response.getErr_code() == 401) {
                        Toast.makeText(getContext(),responseMessage,Toast.LENGTH_SHORT).show();
                    } else if (response.getErr_code() == 500) {
                        Toast.makeText(getContext(),responseMessage,Toast.LENGTH_SHORT).show();
                    }
                    else if (response.getErr_code() == 405) {
                        Toast.makeText(getContext(),responseMessage,Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(),responseMessage,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (JSONException ex) {
            Toast.makeText(getContext(),responseMessage,Toast.LENGTH_SHORT).show();
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

/*    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

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

    private class ProfileUpdate extends AsyncTask<String,Void,Report> {

        Report response = new Report();

        @Override
        protected void onPreExecute() {
            showProgressDialog();
        }

        @Override
        protected Report doInBackground(String... params) {
            try {
                JSONObject jObject = new JSONObject();
                jObject.put("ID", params[0]);
                jObject.put("Name", params[1]);
                jObject.put("Title", params[2]);
                jObject.put("User_Pic", params[3]);
               // Log.e("1234ccg"," params[3] "+params[3]);
                response = new ServiceClass().getJsonObjectResponse(jObject,"http://ccg.bananaappscenter.com/api/User/ProfileUpdate");
            } catch (JSONException e) {
                Toast.makeText(getContext(),"Server couldn't respond,Please try again",Toast.LENGTH_SHORT).show();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Report response) {
            dismissProgressDialog();
            if (response!=null){
                gerProfileResponse(response);
            }
            else {
                Toast.makeText(getContext(),"Server couldn't respond,Please try again",Toast.LENGTH_SHORT).show();
                // showToast("Server couldn't respond,Please try again");
            }
        }
    }

    private void gerProfileResponse(Report response) {
        try {
            if (response.getStatus().equalsIgnoreCase("true")) {
                resultJsonObject = response.getJsonObject();
                if(resultJsonObject.length()>0&&resultJsonObject!=null) {
                    //   JSONObject resultJson=resultJsonObject.getJSONObject("Msg");
                    //if(resultJson.length()>0&& resultJson!=null){
                    if (resultJsonObject.getString("StatusCode").equalsIgnoreCase("200")) {

                        //  responseMessage = resultJson.getString("Message");

                        Toast.makeText(getContext(), resultJsonObject.getString("Message"),Toast.LENGTH_SHORT).show();
                        Cache.putData(CatchValue.MyBooking, getContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
                        // showToast(resultJsonObject.getString("Email"));

                       /* Cache.putData(CatchValue.Email, getContext(), "", Cache.CACHE_LOCATION_DISK);
                        Cache.putData(CatchValue.Mobile, getContext(), "", Cache.CACHE_LOCATION_DISK);
                        Cache.putData(CatchValue.Name, getContext(), "", Cache.CACHE_LOCATION_DISK);
                        Cache.putData(CatchValue.Title, getContext(),"", Cache.CACHE_LOCATION_DISK);
                        Cache.putData(CatchValue.User_Pic, getContext(), "", Cache.CACHE_LOCATION_DISK);
                        Cache.putData(CatchValue.ID, getContext(), "", Cache.CACHE_LOCATION_DISK);
*/

                        Intent intent = new Intent(getContext(), Home.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else
                    {
                        //  responseMessage = resultJson.getString("Message");
                        Toast.makeText(getContext(), resultJsonObject.getString("Message"),Toast.LENGTH_SHORT).show();
                        return;
                    }



                }
                else {
                    Toast.makeText(getContext(),responseMessage,Toast.LENGTH_SHORT).show();
                }
            }

            else {
                if ((response.getStatus().equalsIgnoreCase("false"))) {
                    responseMessage = response.getMessage();
                    if (response.getErr_code() == 400) {
                        Toast.makeText(getContext(),responseMessage,Toast.LENGTH_SHORT).show();
                    }
                    else if (response.getErr_code() == 401) {
                        Toast.makeText(getContext(),responseMessage,Toast.LENGTH_SHORT).show();
                    } else if (response.getErr_code() == 500) {
                        Toast.makeText(getContext(),responseMessage,Toast.LENGTH_SHORT).show();
                    }
                    else if (response.getErr_code() == 405) {
                        Toast.makeText(getContext(),responseMessage,Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(),responseMessage,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (JSONException ex) {
            Toast.makeText(getContext(),responseMessage,Toast.LENGTH_SHORT).show();
        }

    }
/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean read = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean write = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (read && write)
                        Toast.makeText(getContext(), "Permission: granted Successfully", Toast.LENGTH_SHORT);
                    else {
                        Toast.makeText(getContext(), "Permission Denied, Please allow permission.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

/*
    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".PNG");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        profileImg.setImageBitmap(thumbnail);
       */
/* capturedata.setVisibility(View.INVISIBLE);
        capturedataResult.setVisibility(View.VISIBLE);*//*

    }
*/

    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
              //  Log.e("1234ccg","data "+data.getData());
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
             //   Log.e("1234ccg","bm - "+bm);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                byte[] b = bytes.toByteArray();
                String encImage = Base64.encodeToString(b, Base64.DEFAULT);
                Cache.putData(CatchValue.User_Pic, getActivity(), encImage, Cache.CACHE_LOCATION_DISK);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        profileImg.setImageBitmap(bm);
       /* capturedata.setVisibility(View.INVISIBLE);
        capturedataResult.setVisibility(View.VISIBLE);*/
    }
    private void askForPermission(String permission, Integer requestCode) {

        if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
            }
        } else {

            // Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
            //  mScannerView.setResultHandler(this);
            //  mScannerView.startCamera();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                //    showToastMessage("SELECT_FILE");
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                // showToastMessage("REQUEST_CAMERA");
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

     //   Cache.putData(CatchValue.User_Pic, getContext(), thumbnail, Cache.CACHE_LOCATION_DISK);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        byte[] b = bytes.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        Cache.putData(CatchValue.User_Pic, getActivity(), encImage, Cache.CACHE_LOCATION_DISK);
    //    Log.e("1234ccg"," encImage "+encImage);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".PNG");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        profileImg.setImageBitmap(thumbnail);

    }
}
