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
 * {@link MessageBoard.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageBoard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageBoard extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView msgBoardTitle,avlMsg,msgCount;
    RecyclerView messagBoardRCV;
    ArrayList<MessageComments> messageComments;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    String sid;
    ArrayList<MessageBoardList> messageBoardLists;
    ArrayList<MessageBoardList> list;

    ProgressDialog progressDialog;
    JSONObject resultJsonObject,resultData,resultData2;
    String responseMessage;
    MessageBoardAdapter messageBoardAdapter;

    String Content_Id,Content_Type,Mesage_Website_Link,Message_Id,Message_Post_DateTime,Message_Post_DateTime_Format,Message_Title,Message_Type,Message_URL,Status,Trainee_Name,Message_Description;
    private OnFragmentInteractionListener mListener;

    public MessageBoard() {
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

        View v = inflater.inflate(R.layout.fragment_message_board, container, false);

        msgBoardTitle = (TextView) v.findViewById(R.id.msgBoardTitle);
        avlMsg = (TextView) v.findViewById(R.id.avlMsg);
        msgCount = (TextView) v.findViewById(R.id.msgCount);
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "AbrilFatface-Regular.ttf");
        msgBoardTitle.setTypeface(custom_font);
        messagBoardRCV = (RecyclerView) v.findViewById(R.id.messagBoardRCV);
        messageBoardLists = new ArrayList<>();
        sid = ((String) Cache.getData(CatchValue.ID, getContext()));
        cd = new ConnectionDetector(getContext());
        Cache.putData(CatchValue.BackArrow, getContext(), "MessageBoard", Cache.CACHE_LOCATION_DISK);
        ((Home) getActivity()).BackArrowMethod();
        String BackArrowRecall = ((String) Cache.getData(CatchValue.BackArrowRecall, getContext()));
        if(BackArrowRecall.equalsIgnoreCase("BackArrowRecall"))
        {
            Cache.putData(CatchValue.BackArrowRecall, getContext(), "", Cache.CACHE_LOCATION_DISK);
            ShowData();
        }
else {
            isInternetPresent = cd.isConnectionAvailable();
            if (isInternetPresent) {
                new MessageBoardData().execute(sid);
            } else {
                ShowNoInternetDialog();
            }

        }
        return v;
    }

    private class MessageBoardData extends AsyncTask<String,Void,Report> {

        Report response = new Report();

        @Override
        protected void onPreExecute() {
            showProgressDialog();
        }

        @Override
        protected Report doInBackground(String... params) {
            try {
                ServiceClass utility = new ServiceClass();
               // response = utility.getJsonObjectResponse("http://ccg.bananaappscenter.com/api/Message/GetMessages");
                response = utility.getJsonObjectResponse("http://ccg.bananaappscenter.com/api/Message/GetMessagesbyUserID?UserID="+params[0]);
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
                showToast("Server couldn't respond,Please try again 222");
            }
        }

    }

    private void gerLoginResponse(Report response) {
        try {
         //   Log.e("Response "," "+response);

            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = sharedPrefs.edit();
            Gson gson = new Gson();
            String json2 = gson.toJson(""+messageBoardLists);
            editor.putString("AllMessageBoardLists", json2);
            editor.commit();

            //resultJsonObject = response.getJsonObject();
           // Log.e("Response "," "+resultJsonObject);
            if (response.getStatus().equalsIgnoreCase("true")) {
                resultJsonObject = response.getJsonObject();
                if(resultJsonObject.length()>0&&resultJsonObject!=null) {
                    JSONObject resultJson=resultJsonObject.getJSONObject("Msg");
                    if(resultJson.length()>0&& resultJson!=null){
                        if (resultJson.getString("StatusCode").equalsIgnoreCase("200")) {
                            responseMessage = resultJson.getString("Message");
                        //    resultJsonObject.getString("Email");
                            // showToast(responseMessage);
                            //  Log.e("12345  "," "+ (String) Cache.getData(CatchValue.USER_NAME,Login.this));

                            /*
                            Intent intent = new Intent(getContext(), Home.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);*/

                            if(resultJsonObject.length()!=0 && !resultJsonObject.equals("[]")) {
                                JSONArray resultJsonArray = resultJsonObject.getJSONArray("Messages");
                                if (resultJsonArray.length() != 0 && !resultJsonArray.equals("[]")) {
                                    for (int i = 0; i < resultJsonArray.length(); i++) {

                                        resultData = resultJsonArray.getJSONObject(i);
                                    //    Toast.makeText(getContext(),"  Message_Title "+resultData.getString("Message_Title"), Toast.LENGTH_SHORT).show();

                                        Content_Id = resultData.getString("Content_Id");
                                        Content_Type = resultData.getString("Content_Type").toString().trim();
                                        Mesage_Website_Link = "";
                                        Message_Id = resultData.getString("Message_Id");
                                        Message_Post_DateTime = resultData.getString("Message_Post_DateTime");
                                        Message_Post_DateTime_Format = resultData.getString("Message_Post_DateTime_Format");
                                        Message_Title = resultData.getString("Message_Title");
                                        Message_Type = resultData.getString("Message_Type");
                                        Message_URL = resultData.getString("Message_URL");
                                        Status = resultData.getString("Status");
                                        Message_Description = resultData.getString("Message_Description");
                                        Trainee_Name = resultData.getString("Trainee_Name");

                                        //messageComments.clear();
                                        messageComments = new ArrayList<>();
                                        JSONArray resultJsonArray2 = resultData.getJSONArray("MessageComments");
                                        MessageComments t = null;
                                      //  Log.e("020118 "," started ");
                                      if (resultJsonArray2.length() != 0 && !resultJsonArray2.equals("[]")) {
                                            for (int i2 = 0; i2 < resultJsonArray2.length(); i2++) {
                                                resultData2 = resultJsonArray2.getJSONObject(i2);

                                                t = new MessageComments(resultData2.getString("Comment_By"), resultData2.getString("Comment_Posted_Date_Format"), resultData2.getString("Comment"));
                                                messageComments.add(t);
                                      //
                                            }
                                    //      Log.e("020118 ",i+" "+resultData2.getJSONArray("MessageComments"));
                                        }

                                    //    Log.e("obj "," "+resultData2);
                                        MessageBoardList d = new MessageBoardList(Content_Id,Content_Type,Mesage_Website_Link,Message_Id,Message_Post_DateTime,Message_Post_DateTime_Format,Message_Title,Message_Type,Message_URL,Status,Message_Description,Trainee_Name,messageComments);
                                        //MessageBoardList d = new MessageBoardList(Content_Id);
                                        messageBoardLists.add(d);
                                /*        if(i==2)
                                            Log.e("020118 "," messageBoardLists  "+i+" "+messageBoardLists.get(2));*/

                                        //messageBoardLists.add(resultJsonObject.get(i));
                                    }
                                }


                             //   Log.e("020118 ",messageBoardLists.get(2).getMessage_Post_DateTime_Format()+" messageCommentsList "+messageBoardLists.get(2).getTrainingPhotoLists().get(0).getComment());
                          //      Log.e("020118 "," messageCommentsList "+messageBoardLists.get(2).getTrainingPhotoLists().toString());
                                sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                                editor = sharedPrefs.edit();
                                gson = new Gson();

                                json2 = gson.toJson(messageBoardLists);
                                editor.putString("AllMessageBoardLists", json2);
                                editor.commit();

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

                }
                else {


                    showToast("Some thing went wrong please check once");
                }
            }

            else {
                showToast("Some thing went wrong please check once ");
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
        String json = sharedPrefs.getString("AllMessageBoardLists", null);

        if(json.equals("\"[]\"")) {
        //     Toast.makeText(getContext(), "empty", Toast.LENGTH_SHORT).show();
            avlMsg.setVisibility(View.VISIBLE);
            avlMsg.setText("No Messages is available");
            messagBoardRCV.setAdapter(null);

        }
        else {
            avlMsg.setVisibility(View.GONE);
            // Toast.makeText(getContext(), "Not empty " + json, Toast.LENGTH_SHORT).show();
          //  avlHosp.setText("Available hospitals");
            Type type = new TypeToken<ArrayList<MessageBoardList>>() {
            }.getType();
            list = gson.fromJson(json, type);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            messagBoardRCV.setLayoutManager(linearLayoutManager);
            msgCount.setText("You have "+list.size()+" messages in total.");
            messageBoardAdapter = new MessageBoardAdapter(getContext(), list);
            messagBoardRCV.setAdapter(messageBoardAdapter);
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
                    new MessageBoardData().execute(sid);
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
