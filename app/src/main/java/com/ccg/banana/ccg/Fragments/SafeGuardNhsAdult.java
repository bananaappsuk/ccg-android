package com.ccg.banana.ccg.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.ccg.banana.ccg.Activity.Home;
import com.ccg.banana.ccg.R;
import com.ccg.banana.ccg.session.Cache;
import com.ccg.banana.ccg.session.CatchValue;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentB#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SafeGuardNhsAdult extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_CALL = 1;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private WebView mywebview;
Button safeGuarding,childProtection;
    LinearLayout contactPerson;
    Intent callIntent;
  //  private WebView mywebview;

TextView msgBoardTxt;
    private OnFragmentInteractionListener mListener;

    public SafeGuardNhsAdult() {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.safe_guard_nhs_adult, container, false);
        msgBoardTxt = (TextView)v.findViewById(R.id.msgBoardTxt);

        safeGuarding = (Button) v.findViewById(R.id.safeGuarding);
        childProtection = (Button) v.findViewById(R.id.childProtection);
        mywebview = (WebView) v.findViewById(R.id.webView1);
        contactPerson = (LinearLayout)v.findViewById(R.id.contactPerson);

        String safe = ((String) Cache.getData(CatchValue.Safe,getContext()));
        if(safe.equalsIgnoreCase("safe")) {
            safeGuarding.setTextColor(getResources().getColor(R.color.dark_pink));
            childProtection.setTextColor(getResources().getColor(R.color.cement));
            mywebview.getSettings().setJavaScriptEnabled(true);
            mywebview.loadUrl("http://www.myguideapps.com/nhs_safeguarding/default/safeguarding_adults/?nocache=0.4674478393244097");
        }
        else
        {
            childProtection.setTextColor(getResources().getColor(R.color.dark_pink));
            safeGuarding.setTextColor(getResources().getColor(R.color.cement));
            mywebview.getSettings().setJavaScriptEnabled(true);
            mywebview.loadUrl("http://www.myguideapps.com/nhs_safeguarding/default/safeguarding_children/?nocache=0.5346279246361767/");
        }
        safeGuarding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                safeGuarding.setTextColor(getResources().getColor(R.color.dark_pink));
                childProtection.setTextColor(getResources().getColor(R.color.cement));
                mywebview.getSettings().setJavaScriptEnabled(true);
                mywebview.loadUrl("http://www.myguideapps.com/nhs_safeguarding/default/safeguarding_adults/?nocache=0.4674478393244097");
            }
        });

        childProtection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childProtection.setTextColor(getResources().getColor(R.color.dark_pink));
                safeGuarding.setTextColor(getResources().getColor(R.color.cement));
                mywebview.getSettings().setJavaScriptEnabled(true);
                mywebview.loadUrl("http://www.myguideapps.com/nhs_safeguarding/default/safeguarding_children/?nocache=0.5346279246361767/");
            }
        });
        //mywebview.loadUrl("http://www.bananaapps.co.in/");
        Cache.putData(CatchValue.BackArrow, getContext(), "SafeGauardNhsAdult", Cache.CACHE_LOCATION_DISK);
        ((Home)getActivity()).BackArrowMethod();

     //   mywebview = (WebView) v.findViewById(R.id.webView1);
     //   mywebview.loadUrl("http://www.myguideapps.com/nhs_safeguarding/default/safeguarding_adults/?nocache=0.4674478393244097");
        init();
        //mywebview.loadUrl("http://www.myguideapps.com/");
contactPerson.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, new SafeGuardNhsContact());
        transaction.commit();
    }
});

        return v;
    }

    private void init() {
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "Quicksand-Regular.ttf");
        msgBoardTxt.setTypeface(custom_font);
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
