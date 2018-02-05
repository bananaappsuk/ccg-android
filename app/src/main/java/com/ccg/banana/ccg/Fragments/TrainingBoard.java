package com.ccg.banana.ccg.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ccg.banana.ccg.Activity.Home;
import com.ccg.banana.ccg.Adapter.MessageBoardAdapter;
import com.ccg.banana.ccg.Adapter.TrainingBoardAdapter;
import com.ccg.banana.ccg.Login;
import com.ccg.banana.ccg.R;
import com.ccg.banana.ccg.ServiceClass.ConnectionDetector;
import com.ccg.banana.ccg.ServiceClass.Report;
import com.ccg.banana.ccg.ServiceClass.ServiceClass;
import com.ccg.banana.ccg.session.Cache;
import com.ccg.banana.ccg.session.CatchValue;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageBoard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrainingBoard extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<TrainingPhotoList> trainingPhotoLists;
    TextView msgBoardTitle,avlMsg,msgCount;
    RecyclerView trainingBoardRCV;

    Boolean isInternetPresent = false;
    ConnectionDetector cd;

    ArrayList<TrainingBoardList> trainingBoardLists;
    ArrayList<TrainingBoardList> list;
String sid;
    ProgressDialog progressDialog;
    JSONObject resultJsonObject,resultData,resultData2;
    String responseMessage;
    TrainingBoardAdapter trainingBoardAdapter;

   // String Content_Id,Content_Type,Mesage_Website_Link,Message_Id,Message_Post_DateTime,Message_Post_DateTime_Format,Message_Title,Message_Type,Message_URL,Status,Trainee_Name,Message_Description;

    String Capacity,Register_Status,Status,Traning_Address1,Traning_Address2,Traning_Category_Id,Traning_Category_Name,Traning_City,Traning_ContactPersonname,Traning_ContactPersonnumber,Traning_Day,Traning_Description,Traning_EndDate,Traning_EndDate_Foramt,Traning_EndTime,Traning_EndTime_Format,Traning_Id,Traning_Image;
    String ID,Traning_Id2,Traning_Photo,Traning_Postcode,Traning_StartDate,Traning_StartDate_Format,Traning_StartTime,Traning_StartTime_Format,Traning_Title;
    private OnFragmentInteractionListener mListener;

    public TrainingBoard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageBoard.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageBoard newInstance(String param1, String param2) {
        MessageBoard fragment = new MessageBoard();
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

        View v = inflater.inflate(R.layout.fragment_training_board, container, false);

        msgBoardTitle = (TextView)v.findViewById(R.id.msgBoardTitle);
        avlMsg = (TextView)v.findViewById(R.id.avlMsg);
        msgCount = (TextView)v.findViewById(R.id.msgCount);
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "AbrilFatface-Regular.ttf");
        msgBoardTitle.setTypeface(custom_font);

        trainingBoardRCV= (RecyclerView)v.findViewById(R.id.trainingBoardRCV);

        trainingBoardLists = new ArrayList<>();
        sid = ((String) Cache.getData(CatchValue.ID,getContext()));
        cd = new ConnectionDetector(getContext());
        isInternetPresent = cd.isConnectionAvailable();
        Cache.putData(CatchValue.BackArrow, getContext(), "TrainingBoard", Cache.CACHE_LOCATION_DISK);
        ((Home)getActivity()).BackArrowMethod();
        String BackArrowRecall = ((String) Cache.getData(CatchValue.BackArrowRecall, getContext()));
        if(BackArrowRecall.equalsIgnoreCase("BackArrowRecall"))
        {
            Cache.putData(CatchValue.BackArrowRecall, getContext(), "", Cache.CACHE_LOCATION_DISK);
            ShowData();
        }
        else {

            //   isInternetPresent = cd.isConnectionAvailable();
            if (isInternetPresent) {
                new TrainingBoardData().execute();
            } else {
                ShowNoInternetDialog();
            }

        }
        // Inflate the layout for this fragment


        return v;
    }

    private class TrainingBoardData extends AsyncTask<String,Void,Report> {

        Report response = new Report();

        @Override
        protected void onPreExecute() {
            showProgressDialog();
        }

        @Override
        protected Report doInBackground(String... params) {
            try {
                ServiceClass utility = new ServiceClass();
                response = utility.getJsonObjectResponse("http://ccg.bananaappscenter.com/api/Events/GetTraningsbyUserID?UserID="+sid);
            } catch (Exception e) {
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
         //   Log.e("Response "," "+response);

            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = sharedPrefs.edit();
            Gson gson = new Gson();
            String json2 = gson.toJson(""+trainingBoardLists);
            editor.putString("AllTrainingBoardLists", json2);
            editor.commit();

            //resultJsonObject = response.getJsonObject();
           // Log.e("Response "," "+resultJsonObject);
            if (response.getStatus().equalsIgnoreCase("true")) {
                int m=0;
                resultJsonObject = response.getJsonObject();
                if(resultJsonObject.length()>0&&resultJsonObject!=null) {
                    try {
                    JSONObject resultJson=resultJsonObject.getJSONObject("Msg");
                    if(resultJson.length()>0&& resultJson!=null){
                        if (resultJson.getString("StatusCode").equalsIgnoreCase("200")) {
                            responseMessage = resultJson.getString("Message");
                           // Toast.makeText(getContext(),"  Message_Title "+responseMessage, Toast.LENGTH_SHORT).show();
                            if(resultJsonObject.length()!=0 && !resultJsonObject.equals("[]")) {
                                JSONArray resultJsonArray = resultJsonObject.getJSONArray("TraningList");
                                if (resultJsonArray.length() != 0 && !resultJsonArray.equals("[]")) {
                                    for (int i = 0; i < resultJsonArray.length(); i++) {
                                        resultData = resultJsonArray.getJSONObject(i);

                                        Capacity = resultData.getString("Capacity");
                                        Register_Status = resultData.getString("Register_Status");
                                        Status = resultData.getString("Status");
                                        Traning_Address1 = resultData.getString("Traning_Address1");
                                        Traning_Address2 = resultData.getString("Traning_Address2");
                                        Traning_Category_Id = resultData.getString("Traning_Category_Id");
                                        Traning_Category_Name = resultData.getString("Traning_Category_Name");
                                        Traning_City = resultData.getString("Traning_City");
                                        Traning_ContactPersonname = resultData.getString("Traning_ContactPersonname");
                                        Traning_ContactPersonnumber = resultData.getString("Traning_ContactPersonnumber");
                                        Traning_Day = resultData.getString("Traning_Day");
                                        Traning_Description = resultData.getString("Traning_Description");
                                        Traning_EndDate = resultData.getString("Traning_EndDate");
                                        Traning_EndDate_Foramt = resultData.getString("Traning_EndDate_Foramt");
                                        Traning_EndTime = resultData.getString("Traning_EndTime");
                                        Traning_EndTime_Format = resultData.getString("Traning_EndTime_Format");
                                        Traning_Id = resultData.getString("Traning_Id");
                                        Traning_Image = resultData.getString("Traning_Image");
                                        Traning_Postcode = resultData.getString("Traning_Postcode");
                                        Traning_StartDate = resultData.getString("Traning_StartDate");
                                        Traning_StartDate_Format = resultData.getString("Traning_StartDate_Format");
                                        Traning_StartTime = resultData.getString("Traning_StartTime");
                                        Traning_StartTime_Format = resultData.getString("Traning_StartTime_Format");
                                        Traning_Title = resultData.getString("Traning_Title");


                                     //   trainingPhotoLists.clear();
                                        trainingPhotoLists = new ArrayList<>();
                                     //   resultData = resultJsonArray.getJSONObject(i);
                                        JSONArray resultJsonArray2 = resultData.getJSONArray("Traning_Photos");
                                        TrainingPhotoList t = null;
                                   //    for (int i2 = 0; i2 < resultJsonArray2.length(); i2++) {
                                        if (resultJsonArray2.length() != 0 && !resultJsonArray2.equals("[]")) {
                                            for (int i2 = 0; i2 < resultJsonArray2.length(); i2++) {
                                                resultData2 = resultJsonArray2.getJSONObject(i2);
                                                // Log.e("obj "," "+resultData2);
                                                t = new TrainingPhotoList(resultData2.getString("ID"), resultData2.getString("Traning_Id"), resultData2.getString("Traning_Photo"));
                                                trainingPhotoLists.add(t);
                                                // Log.e("obj  ",Traning_Image+"   "+trainingPhotoLists.get(0).getTraning_Photo());
                                            }
                                        }
/*                                        int s = trainingPhotoLists.size();
                                        for(int s1 = 0;s1<s;s1++) {
                                            Log.e("obj ", " end " + trainingPhotoLists.get(s1).getTraning_Photo());
                                        }*/

                                       // showToast("Traning_Photo  "+trainingPhotoLists.get(0).getTraning_Photo());



                                        TrainingBoardList d = new TrainingBoardList(Capacity,Register_Status,Status,Traning_Address1,Traning_Address2,Traning_Category_Id,Traning_Category_Name,Traning_City,Traning_ContactPersonname,Traning_ContactPersonnumber,Traning_Day,Traning_Description,Traning_EndDate,Traning_EndDate_Foramt,Traning_EndTime,Traning_EndTime_Format,Traning_Id,Traning_Image,Traning_Postcode,Traning_StartDate,Traning_StartDate_Format,Traning_StartTime,Traning_StartTime_Format,Traning_Title,trainingPhotoLists);


                                        //TrainingBoardList d = new TrainingBoardList(Capacity,Register_Status,Status,Traning_Address1,Traning_Address2,Traning_Category_Id,Traning_Category_Name,Traning_City,Traning_ContactPersonname,Traning_ContactPersonnumber,Traning_Day,Traning_Description,Traning_EndDate,Traning_EndDate_Foramt,Traning_EndTime,Traning_EndTime_Format,Traning_Id,Traning_Image,Traning_Postcode,Traning_StartDate,Traning_StartDate_Format,Traning_StartTime,Traning_StartTime_Format,Traning_Title);
                                        trainingBoardLists.add(d);
                                  //      Log.e("obj before ", i+"  "+trainingBoardLists.get(i).getTraning_Image()+" end " + trainingBoardLists.get(i).getTrainingPhotoLists().size());
                                      //  Log.e("obj m ",trainingBoardLists.get(m).getTraning_Address1()+" "+d.getTrainingPhotoLists().get(m).getTraning_Photo());

                                        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                                        editor = sharedPrefs.edit();
                                        gson = new Gson();

                                        json2 = gson.toJson(trainingBoardLists);
                                        editor.putString("AllTrainingBoardLists", json2);
                                        editor.commit();

                                    }
                           /*         int s = trainingBoardLists.size();
                                    for(int s1 = 0;s1<s;s1++) {
                                        Log.e("obj after ", s1+"  "+trainingBoardLists.get(s1).getTraning_Image()+" end " + trainingBoardLists.get(s1).getTrainingPhotoLists().size());
                                    }*/
                                }



                               // Log.e("mm  ",0+" "+trainingBoardLists.get(0).getTraning_Photo());
                             //   Log.e("mm  ",1+" "+trainingPhotoLists.get(1).getTraning_Photo());

                            /*    Log.e("mm  ",0+" "+trainingBoardLists.get(0).getTrainingPhotoLists().get(0).getTraning_Photo());
                                Log.e("mm  ",1+" "+trainingBoardLists.get(1).getTrainingPhotoLists().get(0).getTraning_Photo());*/


                                ShowData();
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
                }catch (Exception e)
                {
                    responseMessage = resultJsonObject.getString("Message");
                    showToast(responseMessage);
                    Cache.putData(CatchValue.MyBooking, getContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
                    Intent intent = new Intent(getContext(), Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                }
                else {


                    showToast("Some thing went wrong please check once");
                }
            }

            else {

                responseMessage = resultJsonObject.getString("Message");
                showToast(responseMessage);

            }
        } catch (JSONException ex) {

            showToast("Server couldn't respond,Please try again");
        }

    }

    public void ShowData()
    {
        //  Toast.makeText(getContext(), "Fee method fff " , Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("AllTrainingBoardLists", null);

        if(json.equals("\"[]\"")) {
        //     Toast.makeText(getContext(), "empty", Toast.LENGTH_SHORT).show();
            avlMsg.setVisibility(View.VISIBLE);
            avlMsg.setText("No data is available");
            trainingBoardRCV.setAdapter(null);
        }
        else {
            avlMsg.setVisibility(View.GONE);
         //   Log.e("obj m ",json);
           // Toast.makeText(getContext(), "Not empty " + json, Toast.LENGTH_SHORT).show();
          //  avlHosp.setText("Available hospitals");
            Type type = new TypeToken<ArrayList<TrainingBoardList>>() {
            }.getType();
            list = gson.fromJson(json, type);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            trainingBoardRCV.setLayoutManager(linearLayoutManager);
            msgCount.setText("You have "+list.size()+" messages in total.");
            trainingBoardAdapter = new TrainingBoardAdapter(getContext(), list);
            trainingBoardRCV.setAdapter(trainingBoardAdapter);
        }

        //    Toast.makeText(getContext(), "Fee method " + list.get(0).getHdid(), Toast.LENGTH_SHORT).show();
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
                    new TrainingBoardData().execute();
                } else {
                    ShowNoInternetDialog();
                }

            }
        });
        alertDialog.show();
        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(16);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void showToast(String txt) {
        Toast toast = Toast.makeText(getContext(), txt, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
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
}
