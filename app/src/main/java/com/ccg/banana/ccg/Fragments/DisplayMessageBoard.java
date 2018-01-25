package com.ccg.banana.ccg.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.ccg.banana.ccg.Adapter.CommentMessageAdapter;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DisplayMessageBoard.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DisplayMessageBoard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayMessageBoard extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView description,date,msgBoardTitle,traineName,avlMsg;
    ImageView msgImage,fb;
    VideoView videoView;
    Button send;
    private ShareDialog shareDialog;
    ProgressDialog progressDialog;
    EditText commentText;
    LinearLayout llComment;
    RecyclerView messagBoardRCV;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    CommentMessageAdapter commentMessageAdapter;
    ProgressDialog pDialog;
    String Message_Id="",sid,cmt;
    private OnFragmentInteractionListener mListener;
    ArrayList<MessageBoardList> list;
    JSONObject resultJsonObject;
    Button showAllComments;
    String responseMessage;
    String ppic;
    public DisplayMessageBoard() {
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
        View v = inflater.inflate(R.layout.fragment_display_message_board, container, false);
        // Inflate the layout for this fragment
        Cache.putData(CatchValue.BackArrow, getContext(), "DisplayMessageBoard", Cache.CACHE_LOCATION_DISK);
        ((Home)getActivity()).BackArrowMethod();
        description = (TextView)v.findViewById(R.id.description);
        date = (TextView)v.findViewById(R.id.date);
        msgBoardTitle = (TextView)v.findViewById(R.id.msgBoardTitle);
        traineName = (TextView)v.findViewById(R.id.traineName);
        msgImage = (ImageView)v.findViewById(R.id.msgImage);
        fb = (ImageView)v.findViewById(R.id.fb);
        videoView = (VideoView)v.findViewById(R.id.videoView);
        send = (Button)v.findViewById(R.id.send);
        llComment  = (LinearLayout)v.findViewById(R.id.llComment);
        showAllComments = (Button)v.findViewById(R.id.showAllComments);
        messagBoardRCV = (RecyclerView)v.findViewById(R.id.messagBoardRCV);
        commentText = (EditText) v.findViewById(R.id.commentText);
        avlMsg = (TextView)v.findViewById(R.id.avlMsg);

        cd = new ConnectionDetector(getContext());

        sid = ((String) Cache.getData(CatchValue.ID,getContext()));

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String s = sharedPrefs.getString("Message_Description", "");
        ppic = sharedPrefs.getString("Message_URL", "");
        Message_Id = sharedPrefs.getString("Message_Id","");
        String Message_Type = sharedPrefs.getString("Message_Type","");

        String dt = sharedPrefs.getString("Message_Post_DateTime_Format", "");

        if(TextUtils.isEmpty(dt) || dt.equalsIgnoreCase("null"))
        date.setText("");
        else
        date.setText(dt);

        dt = sharedPrefs.getString("Message_Title", "");
        if(TextUtils.isEmpty(dt) || dt.equalsIgnoreCase("null"))
            msgBoardTitle.setText("");
        else
            msgBoardTitle.setText(dt);

        dt = sharedPrefs.getString("Trainee_Name", "");
        if(TextUtils.isEmpty(dt) || dt.equalsIgnoreCase("null"))
            traineName.setText("");
        else
            traineName.setText(dt);




        showAllComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(showAllComments.getText().toString().equalsIgnoreCase("Show all comments")) {
                    showAllComments.setText("Hide all comments");
                    llComment.setVisibility(View.VISIBLE);
                }
                else {


                    showAllComments.setText("Show all comments");
                    llComment.setVisibility(View.GONE);
                }
            }
        });





        if (Message_Type.equalsIgnoreCase("1")) {
            videoView.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(ppic)) {
                new DownLoadImageTask(msgImage).execute(ppic);
            } else {
                msgImage.setImageResource(R.drawable.no_image);
            }
        }
        else
        if (Message_Type.equalsIgnoreCase("2")) {
            msgImage.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            pDialog = new ProgressDialog(getContext());
            // Set progressbar title
            pDialog.setTitle("Video Streaming...");
            // Set progressbar message
            pDialog.setMessage("Buffering...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            // Show progressbar
            pDialog.show();

            try {
                // Start the MediaController
                MediaController mediacontroller = new MediaController(
                        getContext());
                mediacontroller.setAnchorView(videoView);
                // Get the URL from String VideoURL
                Uri video = Uri.parse(ppic);
                videoView.setMediaController(mediacontroller);
                videoView.setVideoURI(video);

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                // Close the progress bar and play the video
                public void onPrepared(MediaPlayer mp) {
                    pDialog.dismiss();
                    videoView.start();
                }
            });
        }

      //  Log.e("020118 "," start ");
        try {
            Gson gson = new Gson();
         //   Log.e("020118 "," start 1");
            String json = sharedPrefs.getString("CommentsListDisplay", "");
        //    Log.e("020118 "," start 2 ");
            // MessageComments obj = gson.fromJson(json, MessageComments.class);

            if(json.equals("\"[]\"")) {
             //   Log.e("020118 "," empty ");
             //   Toast.makeText(getContext(), "empty", Toast.LENGTH_SHORT).show();
                avlMsg.setVisibility(View.VISIBLE);
                avlMsg.setText("No Messages is available");
                messagBoardRCV.setAdapter(null);

            }
            else {
             //   Log.e("020118 "," not empty ");
                ArrayList<MessageComments> listc;
             //   avlMsg.setVisibility(View.GONE);
             //    Toast.makeText(getContext(), "Not empty " + json, Toast.LENGTH_SHORT).show();

                Type type = new TypeToken<ArrayList<MessageComments>>() {
                }.getType();
                listc = gson.fromJson(json, type);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
              //  Log.e("020118 size ","  "+listc.size());

                if(listc.size()==0) {
                    avlMsg.setText("No message is available");
                    avlMsg.setVisibility(View.VISIBLE);
                }
                else
                    avlMsg.setVisibility(View.GONE);
             /*   else
                    avlMsg.setText("No message is available");*/

              /* for(int l = 0;l<listc.size();l++)
                {
                    Toast.makeText(getContext(), "Not empty " + listc.size(), Toast.LENGTH_SHORT).show();
                }*/
                messagBoardRCV.setLayoutManager(linearLayoutManager);
                commentMessageAdapter = new CommentMessageAdapter(getContext(), listc);
                messagBoardRCV.setAdapter(commentMessageAdapter);
            }


        }catch (Exception e)
        {

        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = send.getText().toString().trim();
                if(s.equalsIgnoreCase("Comment"))
                {
                    send.setText("Send");
                    commentText.setVisibility(View.VISIBLE);
                }
                else
                {
                    cmt = commentText.getText().toString();
                    commentText.setVisibility(View.INVISIBLE);
                    commentText.setText("");
                    send.setText("Comment");
                    if(!TextUtils.isEmpty(cmt)) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                        builder.setMessage(cmt)
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        isInternetPresent = cd.isConnectionAvailable();
                                        if (isInternetPresent) {

                                            new CommentTask().execute(sid, Message_Id, cmt);
                                        } else {
                                            ShowNoInternetDialog();
                                        }
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Action for 'NO' Button
                                /*Intent i2 = new Intent(getApplicationContext(), Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i2);*/
                                    }
                                });

                        //Creating dialog box
                        AlertDialog alert = builder.create();
                        //Setting the title manually
                        alert.setTitle("Confirmation");

                        alert.show();

                    }

                }

            }
        });

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

        description.setText(s);

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
        return v;
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
                    new CommentTask().execute(sid, Message_Id,cmt);
                } else {
                    ShowNoInternetDialog();
                }

            }
        });
        alertDialog.show();
        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(16);
    }


    private class CommentTask extends AsyncTask<String,Void,Report> {

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
                jObject.put("MessageID", params[1]);
                jObject.put("Comments",params[2]);
                response = new ServiceClass().getJsonObjectResponsePost("http://ccg.bananaappscenter.com/api/User/MessageComments?UserID="+params[0]+"&MessageID="+params[1]+"&Comments="+params[2]);
            } catch (JSONException e) {
                showToast("Server couldn't respond,Please try again");
            }
            return response;
        }

        @Override
        protected void onPostExecute(Report response) {
            dismissProgressDialog();
            if (response!=null){
                gerCommentResponse(response);
            }
            else {
                showToast("Server couldn't respond,Please try again 222");
            }
        }

    }

    private void gerCommentResponse(Report response) {
        try {
        //   Log.e("Response "," "+response);

            resultJsonObject = response.getJsonObject();
       //     Log.e("Response "," "+resultJsonObject);
            responseMessage = resultJsonObject.getString("Message");
            showToast(responseMessage);
         //   Log.e("Response "," "+responseMessage);
/*            if (response.getStatus().equalsIgnoreCase("true")) {
                resultJsonObject = response.getJsonObject();
                if(resultJsonObject.length()>0&&resultJsonObject!=null) {
                    JSONObject resultJson=resultJsonObject.getJSONObject("Msg");
                    if(resultJson.length()>0&& resultJson!=null){
                        if (resultJson.getString("StatusCode").equalsIgnoreCase("200")) {

                            responseMessage = resultJson.getString("Message");
                            resultJsonObject.getString("Email");
                            // showToast(resultJsonObject.getString("Email"));


                            //  Log.e("12345  "," "+ (String) Cache.getData(CatchValue.USER_NAME,Login.this));

                        *//*    Intent intent = new Intent(this, Home.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);*//*
                        }
                        else
                        {
                            responseMessage = resultJson.getString("Message");
                            Toast.makeText(,responseMessage,Toast.LENGTH_SHORT).show();
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


                showToast("Invalid credentials. ");

            }*/
        } catch (Exception ex) {

          //  showToast("Server couldn't respond,Please try again");
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


    public void showToast(String txt) {
        Toast toast = Toast.makeText(getContext(), txt, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
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
}
