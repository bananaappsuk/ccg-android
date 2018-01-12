package com.ccg.banana.ccg.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link More#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedBack extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
LinearLayout aboutUs;
TextView Submit;
    ProgressDialog progressDialog;
EditText message;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    JSONObject resultJsonObject;
    String responseMessage;
    String uid;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FeedBack() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment More.
     */
    // TODO: Rename and change types and number of parameters
    public static More newInstance(String param1, String param2) {
        More fragment = new More();
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
        View v =  inflater.inflate(R.layout.feedback, container, false);
        uid =(String) Cache.getData(CatchValue.ID,getContext());
        Submit = (TextView)v.findViewById(R.id.Submit);
        message = (EditText) v.findViewById(R.id.message);
        // Inflate the layout for this fragment
        Cache.putData(CatchValue.BackArrow, getContext(), "ContactUs", Cache.CACHE_LOCATION_DISK);
        ((Home)getActivity()).BackArrowMethod();

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cd = new ConnectionDetector(getContext());
                isInternetPresent = cd.isConnectionAvailable();

                if(TextUtils.isEmpty(message.getText().toString()))
                {
                    message.setError("Please enter feedback");
                    message.requestFocus();
                }
                else
                {
                    isInternetPresent = cd.isConnectionAvailable();
                    if (isInternetPresent) {

                        new ChangePasswordTask().execute(uid,message.getText().toString().trim());
                    } else {
                        ShowNoInternetDialog();
                    }
                }
            }
        });
        return v;
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
                jObject.put("UserID", params[0]);
                jObject.put("FeedBack", params[1]);
                String urlString = params[1].replaceAll(" ", "%20");
                String url = urlString.replaceAll("\n","%20");
              /*  Log.e("1245  params[0] "," "+ params[0]);
                Log.e("1245 "," "+ params[1]);*/
                response = new ServiceClass().getJsonObjectResponsePost("http://ccg.bananaappscenter.com/api/User/FeedbackSubmit?UserID="+params[0]+"&FeedBack="+url);
              //  response = new ServiceClass().getJsonObjectResponsePost("http://ccg.bananaappscenter.com/api/User/FeedbackUserID="+params[0]+"&FeedBack="+params[1]);
             //   response = new ServiceClass().getJsonObjectResponse(jObject,"http://ccg.bananaappscenter.com/api/User/Feedback?UserID=4&FeedBack=test");
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

    public void ShowNoInternetDialog() {
        showAlertDialog(getContext(), "No Internet Connection", "Please check your network.", false);
    }
    public void showAlertDialog(Context context, String title, String message2, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message2);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setIcon((status) ? R.mipmap.ic_action_checked : R.mipmap.ic_action_warning);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                isInternetPresent = cd.isConnectionAvailable();
                if (isInternetPresent) {
                    new ChangePasswordTask().execute(uid,message.getText().toString().trim());
                } else {
                    ShowNoInternetDialog();
                }

            }
        });
        alertDialog.show();
        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(16);
    }

    private void gerLoginResponse(Report response) {
        try {

            Log.e("1245 "," "+response.getJsonObject());
            if (response.getStatus().equalsIgnoreCase("true")) {
                resultJsonObject = response.getJsonObject();
                if(resultJsonObject.length()>0&&resultJsonObject!=null) {

                    if (resultJsonObject.getString("StatusCode").equalsIgnoreCase("200")) {

                        //  responseMessage = resultJson.getString("Message");

                        Toast.makeText(getContext(), resultJsonObject.getString("Message"),Toast.LENGTH_SHORT).show();
                        Cache.putData(CatchValue.MyBooking, getContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
                        // showToast(resultJsonObject.getString("Email"));
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
}
