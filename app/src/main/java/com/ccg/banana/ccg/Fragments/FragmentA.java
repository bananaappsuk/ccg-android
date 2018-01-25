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
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ccg.banana.ccg.Activity.Home;
import com.ccg.banana.ccg.Adapter.HomeAdapter;
import com.ccg.banana.ccg.Adapter.HomeOrderObject;
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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentA.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentA#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentA extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView name,homeDesc,msgBoardTxt,msgTrainingTxt,jobTxt,eventTxt,myBooking, TV_EventCount,MessageCount,TraningCount,JobCount;
    Button safeGuarding,childProtection;
    LinearLayout msgBoard,training,jobs,events;
    private GridLayoutManager lLayout2;
    ProgressDialog progressDialog;
    private OnFragmentInteractionListener mListener;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    ArrayList<MyBookingList> trainingBoardLists;
    RecyclerView rView2;
    JSONObject resultJsonObject,resultData,resultData2;
    String responseMessage;
    MybookingAdapter trainingBoardAdapter;
    ArrayList<MyBookingList> list;
    String id,EventCount;
    public FragmentA() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentA.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentA newInstance(String param1, String param2) {
        FragmentA fragment = new FragmentA();
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
        View v =   inflater.inflate(R.layout.fragment_, container, false);
        name = (TextView)v.findViewById(R.id.name);
        homeDesc = (TextView)v.findViewById(R.id.homeDesc);
        msgBoardTxt = (TextView)v.findViewById(R.id.msgBoardTxt);
        msgTrainingTxt = (TextView)v.findViewById(R.id.msgTrainingTxt);
        jobTxt = (TextView)v.findViewById(R.id.jobTxt);
        eventTxt = (TextView)v.findViewById(R.id.eventTxt);
        TV_EventCount = (TextView)v.findViewById(R.id.EventCount);
        MessageCount  = (TextView)v.findViewById(R.id.MessageCount);
        TraningCount  = (TextView)v.findViewById(R.id.TraningCount);
        JobCount  = (TextView)v.findViewById(R.id.JobCount);

        myBooking = (TextView)v.findViewById(R.id.myBooking);
        safeGuarding = (Button) v.findViewById(R.id.safeGuarding);
        childProtection = (Button) v.findViewById(R.id.childProtection);
        Cache.putData(CatchValue.onBackPressed, getContext(), "backHome", Cache.CACHE_LOCATION_DISK);
        name.setText("Hi "+((String) Cache.getData(CatchValue.Name,getContext())));
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "hel_bold.ttf");
        name.setTypeface(custom_font);
        myBooking.setTypeface(custom_font);
        trainingBoardLists = new ArrayList<>();
        custom_font = Typeface.createFromAsset(getContext().getAssets(), "hel_medium.ttf");
        homeDesc.setTypeface(custom_font);
        msgBoardTxt.setTypeface(custom_font);
        msgTrainingTxt.setTypeface(custom_font);
        jobTxt.setTypeface(custom_font);
        eventTxt.setTypeface(custom_font);
        rView2 = (RecyclerView)v.findViewById(R.id.recycler_view_govt);

   /*     List<HomeOrderObject> rowListItem2 = getAllItemList2();
        lLayout2 = new GridLayoutManager(getContext(), 1);

        RecyclerView rView2 = (RecyclerView)v.findViewById(R.id.recycler_view_govt);
        HomeAdapter rcAdapter = new HomeAdapter(getContext(), rowListItem2);

        rView2.setHasFixedSize(true);
        rView2.setLayoutManager(lLayout2);
        rView2.setAdapter(rcAdapter);*/

        cd = new ConnectionDetector(getContext());
        isInternetPresent = cd.isConnectionAvailable();
        id = ((String) Cache.getData(CatchValue.ID,getContext()));


        safeGuarding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, new SafeGuardNhs());
                transaction.commit();
            }
        });

        childProtection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, new ChildProtection());
                transaction.commit();*/
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, new SafeGuardNhs());
                transaction.commit();
            }
        });


        msgBoard = (LinearLayout)v.findViewById(R.id.msgBoard);

        msgBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, new MessageBoard());
                transaction.commit();
            }
        });

        training = (LinearLayout)v.findViewById(R.id.training);

        training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, new TrainingBoard());
                transaction.commit();
            }
        });

        jobs = (LinearLayout)v.findViewById(R.id.jobs);
        jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, new JobsBoard());
                transaction.commit();
            }
        });

        events = (LinearLayout)v.findViewById(R.id.events);
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, new EventBoard());
                transaction.commit();
            }
        });

        Cache.putData(CatchValue.BackArrow, getContext(), "Hide", Cache.CACHE_LOCATION_DISK);
        ((Home)getActivity()).BackArrowMethod();

        // Inflate the layout for this fragment
        String mcall =   ((String) Cache.getData(CatchValue.MyBooking,getContext()));
        if(mcall.equalsIgnoreCase("MyBookingCall")) {
            MyBooking();
            Cache.putData(CatchValue.MyBooking, getContext(), "MyBookingNotCall", Cache.CACHE_LOCATION_DISK);
        }
        return v;
    }

    private void MyBooking() {

        isInternetPresent = cd.isConnectionAvailable();
        if (isInternetPresent) {

            new MyBookingTask().execute(id);
        } else {
            ShowNoInternetDialog();
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
                    new MyBookingTask().execute(id);
                } else {
                    ShowNoInternetDialog();
                }

            }
        });
        alertDialog.show();
        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(16);
    }

    private class MyBookingTask extends AsyncTask<String,Void,Report> {

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
                response = new ServiceClass().getJsonObjectResponse("http://ccg.bananaappscenter.com/api/User/Bookings?UserID="+params[0]);
            } catch (JSONException e) {
                showToast("Server couldn't respond,Please try again");
            }
            return response;
        }

        @Override
        protected void onPostExecute(Report response) {
            dismissProgressDialog();
            if (response!=null){
                BookingResponse(response);
            }
            else {
                showToast("Server couldn't respond,Please try again 222");
            }
        }

    }

    private void BookingResponse(Report response) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json2 = gson.toJson(""+trainingBoardLists);
        editor.putString("AllMyBookingLists", json2);
        editor.commit();

        String Module_Address1,Module_Address2,Module_Category,Module_City,Module_End_Date,Module_End_Day,Module_End_Time,Module_ID,Module_Start_Date,Module_Start_Day,Module_Start_Time,Module_Title,Module_Type;
        try {
        /*    Log.e("Response "," "+response);
            resultJsonObject = response.getJsonObject();
            Log.e("Response "," "+resultJsonObject);*/
            if (response.getStatus().equalsIgnoreCase("true")) {
                resultJsonObject = response.getJsonObject();

                if(resultJsonObject.length()>0&&resultJsonObject!=null) {
                   // EventCount = resultJsonObject.getString("EventCount");
                    TV_EventCount.setText(resultJsonObject.getString("EventCount"));
                    MessageCount.setText(resultJsonObject.getString("MessageCount"));
                    TraningCount.setText(resultJsonObject.getString("TraningCount"));
                    JobCount.setText(resultJsonObject.getString("JobCount"));
                            JSONObject resultJson=resultJsonObject.getJSONObject("Msg");
                    if(resultJson.length()>0&& resultJson!=null){
                        if (resultJson.getString("StatusCode").equalsIgnoreCase("200")) {

                            responseMessage = resultJson.getString("Message");
                         //   resultJsonObject.getString("Email");
                           //  showToast(responseMessage);
                            if(resultJsonObject.length()!=0 && !resultJsonObject.equals("[]")) {
                                JSONArray resultJsonArray = resultJsonObject.getJSONArray("MyBookings");
                                for (int i2 = 0; i2 < resultJsonArray.length(); i2++) {
                                resultData = resultJsonArray.getJSONObject(i2);

                                JSONArray resultJsonArray2 = resultData.getJSONArray("MyBookings");

                                    resultData2 = resultJsonArray2.getJSONObject(0);
                                //    Log.e("obj "," "+resultData2.getString("Module_Address1"));

                                    Module_Address1 = resultData2.getString("Module_Address1");
                                    Module_Address2 = resultData2.getString("Module_Address2");
                                    Module_Category = resultData2.getString("Module_Category");
                                    Module_City = resultData2.getString("Module_City");
                                    Module_End_Date = resultData2.getString("Module_End_Date");
                                    Module_End_Day = resultData2.getString("Module_End_Day");
                                    Module_End_Time = resultData2.getString("Module_End_Time");
                                    Module_ID = resultData2.getString("Module_ID");
                                    Module_Start_Date = resultData2.getString("Module_Start_Date");
                                    Module_Start_Day = resultData2.getString("Module_Start_Day");
                                    Module_Start_Time = resultData2.getString("Module_Start_Time");
                                    Module_Title = resultData2.getString("Module_Title");
                                    Module_Type = resultData2.getString("Module_Type");
                                    MyBookingList d = new MyBookingList(Module_Address1,Module_Address2,Module_Category,Module_City,Module_End_Date,Module_End_Day,Module_End_Time,Module_ID,Module_Start_Date,Module_Start_Day,Module_Start_Time,Module_Title,Module_Type);

                                    //TrainingBoardList d = new TrainingBoardList(Capacity,Register_Status,Status,Traning_Address1,Traning_Address2,Traning_Category_Id,Traning_Category_Name,Traning_City,Traning_ContactPersonname,Traning_ContactPersonnumber,Traning_Day,Traning_Description,Traning_EndDate,Traning_EndDate_Foramt,Traning_EndTime,Traning_EndTime_Format,Traning_Id,Traning_Image,Traning_Postcode,Traning_StartDate,Traning_StartDate_Format,Traning_StartTime,Traning_StartTime_Format,Traning_Title);
                                    trainingBoardLists.add(d);

                                    // Log.e("obj  ",Traning_Image+"   "+trainingPhotoLists.get(0).getTraning_Photo());
                                }


                            }
                           // Log.e("12345  "," "+ trainingBoardLists.size());
                            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                            editor = sharedPrefs.edit();
                            gson = new Gson();
                            json2 = gson.toJson(trainingBoardLists);
                            editor.putString("AllMyBookingLists", json2);
                            editor.commit();
                            ShowData();
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


                showToast("Server couldn't respond,Please try again");

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
        String json = sharedPrefs.getString("AllMyBookingLists", null);

        if(json.equals("\"[]\"")) {
            //     Toast.makeText(getContext(), "empty", Toast.LENGTH_SHORT).show();
           /* avlMsg.setVisibility(View.VISIBLE);
            avlMsg.setText("No data is available");*/
            rView2.setAdapter(null);
        }
        else {
       //     avlMsg.setVisibility(View.GONE);
         //   Log.e("obj m ",json);
          //  Toast.makeText(getContext(), "Not empty " + json, Toast.LENGTH_SHORT).show();
            //  avlHosp.setText("Available hospitals");
            Type type = new TypeToken<ArrayList<MyBookingList>>() {
            }.getType();
            list = gson.fromJson(json, type);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            rView2.setLayoutManager(linearLayoutManager);

            trainingBoardAdapter = new MybookingAdapter(getContext(), list);
            rView2.setAdapter(trainingBoardAdapter);
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

    public void showToast(String txt) {
        Toast toast = Toast.makeText(getContext(), txt, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }


    private List<HomeOrderObject> getAllItemList2() {
        List<HomeOrderObject> allItems = new ArrayList<HomeOrderObject>();
        allItems.add(new HomeOrderObject("09","Sat","TEDx London","Event category","08 AM - 09 Pm","Newcastle, London"));
        allItems.add(new HomeOrderObject("15","Tue","Training Session","Event category","08 AM - 09 Pm","Newcastle, London"));
        allItems.add(new HomeOrderObject("22","Fei","Tech summit 2018","Event category","08 AM - 09 Pm","Newcastle, London"));
        allItems.add(new HomeOrderObject("29","Mon","TEDx London","Event category","08 AM - 09 Pm","Newcastle, London"));


       /* allItems.add(new HomeOrderObject("3",R.mipmap.back,"2 Li Water bottile case", "20 bottles per case","20"));
        allItems.add(new HomeOrderObject("4",R.mipmap.app_icon,"1 Li Water bottile case","20 bottles per case","10"));
        allItems.add(new HomeOrderObject("5",R.mipmap.back,"500 ML Water bottile case", "20 bottles per case","5"));
        allItems.add(new HomeOrderObject("6",R.mipmap.app_icon,"250 ML Water bottile case", "20 bottles per case","5"));*/
        return allItems;
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
}
