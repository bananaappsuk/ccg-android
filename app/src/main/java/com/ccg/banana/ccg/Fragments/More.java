package com.ccg.banana.ccg.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ccg.banana.ccg.Activity.Home;
import com.ccg.banana.ccg.Login;
import com.ccg.banana.ccg.R;
import com.ccg.banana.ccg.session.Cache;
import com.ccg.banana.ccg.session.CatchValue;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link More.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link More#newInstance} factory method to
 * create an instance of this fragment.
 */
public class More extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
LinearLayout aboutUs,contacUs,logout,feedback;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public More() {
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
        View v =  inflater.inflate(R.layout.fragment_more, container, false);

        aboutUs = (LinearLayout)v.findViewById(R.id.aboutUs);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, new AboutUs());
                transaction.commit();
            }
        });

        contacUs = (LinearLayout)v.findViewById(R.id.contacUs);
        contacUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, new ContactUs());
                transaction.commit();
            }
        });

        feedback = (LinearLayout)v.findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, new FeedBack());
                transaction.commit();
            }
        });


        logout =  (LinearLayout)v.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                //Uncomment the below code to Set the message and title from the strings.xml file
                //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);


                //Setting message manually and performing action on button click
                builder.setMessage("Are you sure you want logout ?")
                        .setCancelable(false)
                        .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Cache.putData(CatchValue.Email, getContext(), "", Cache.CACHE_LOCATION_DISK);
                                Cache.putData(CatchValue.Mobile, getContext(), "", Cache.CACHE_LOCATION_DISK);
                                Cache.putData(CatchValue.Name, getContext(), "", Cache.CACHE_LOCATION_DISK);
                                Cache.putData(CatchValue.Title, getContext(), "", Cache.CACHE_LOCATION_DISK);
                                Cache.putData(CatchValue.User_Pic, getContext(), "", Cache.CACHE_LOCATION_DISK);
                                Cache.putData(CatchValue.ID, getContext(), "", Cache.CACHE_LOCATION_DISK);
                                Cache.putData(CatchValue.BackArrow, getContext(), "", Cache.CACHE_LOCATION_DISK);

                                Cache.putData(CatchValue.USER_NAME, getContext(), "", Cache.CACHE_LOCATION_DISK);
                                Cache.putData(CatchValue.password, getContext(), "", Cache.CACHE_LOCATION_DISK);
                                Cache.putData(CatchValue.Safe, getContext(), "", Cache.CACHE_LOCATION_DISK);

                                Intent intent = new Intent(getContext(), Login.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                                // Action for 'NO' Button
                         /* Intent i2 = new Intent(getApplicationContext(), Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i2);
                            */
                            }
                        });

                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("CCG");

                alert.show();



            }
        });

        // Inflate the layout for this fragment
        Cache.putData(CatchValue.BackArrow, getContext(), "More", Cache.CACHE_LOCATION_DISK);
        ((Home)getActivity()).BackArrowMethod();
        return v;
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
