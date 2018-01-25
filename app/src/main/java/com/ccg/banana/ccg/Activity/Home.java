package com.ccg.banana.ccg.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.ccg.banana.ccg.Fragments.DisplayMessageBoard;
import com.ccg.banana.ccg.Fragments.EventApply;
import com.ccg.banana.ccg.Fragments.EventBoard;
import com.ccg.banana.ccg.Fragments.EventCalender;
import com.ccg.banana.ccg.Fragments.FragmentA;
import com.ccg.banana.ccg.Fragments.FragmentB;
import com.ccg.banana.ccg.Fragments.JobApply;
import com.ccg.banana.ccg.Fragments.JobsBoard;
import com.ccg.banana.ccg.Fragments.MessageBoard;
import com.ccg.banana.ccg.Fragments.More;
import com.ccg.banana.ccg.Fragments.MyBookingRegister;
import com.ccg.banana.ccg.Fragments.Notification;
import com.ccg.banana.ccg.Fragments.SafeGuardNhs;
import com.ccg.banana.ccg.Fragments.SafeGuardNhsAdult;
import com.ccg.banana.ccg.Fragments.TrainingBoard;
import com.ccg.banana.ccg.Fragments.TrainingRegister;
import com.ccg.banana.ccg.Login;
import com.ccg.banana.ccg.R;
import com.ccg.banana.ccg.ServiceClass.ConnectionDetector;
import com.ccg.banana.ccg.session.Cache;
import com.ccg.banana.ccg.session.CatchValue;

import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by Banana on 11-Dec-17.
 */

public class Home extends AppCompatActivity {
ImageView backArrow,drProfileImg;
    private TextView mTextMessage,heading;

    @Override
    public void onBackPressed() {
        String s = ((String) Cache.getData(CatchValue.BackArrow,getApplicationContext()));
        if(s.equalsIgnoreCase("MessageBoard")) {
            Cache.putData(CatchValue.Operation, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);
            Cache.putData(CatchValue.MyBooking, getApplicationContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
            //    Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
            //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, new FragmentA());
            transaction.commit();
        }
        else
                /*if(s.equalsIgnoreCase("FragmentC"))
                    Toast.makeText(Home.this," FragmentC ",Toast.LENGTH_SHORT).show();*/
            if(s.equalsIgnoreCase("DisplayMessageBoard")) {
                Cache.putData(CatchValue.BackArrowRecall, getApplicationContext(), "BackArrowRecall", Cache.CACHE_LOCATION_DISK);
                //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, new MessageBoard());
                transaction.commit();
            }
            else
            if(s.equalsIgnoreCase("TrainingBoard")) {
                Cache.putData(CatchValue.MyBooking, getApplicationContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
                //    Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, new FragmentA());
                transaction.commit();
            }
            else
            if(s.equalsIgnoreCase("TrainingRegister")) {
                Cache.putData(CatchValue.BackArrowRecall, getApplicationContext(), "BackArrowRecall", Cache.CACHE_LOCATION_DISK);
                //    Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, new TrainingBoard());
                transaction.commit();
            }
            else
            if(s.equalsIgnoreCase("JobsBoard")) {
                Cache.putData(CatchValue.MyBooking, getApplicationContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
                //    Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, new FragmentA());
                transaction.commit();
            }
            else
            if(s.equalsIgnoreCase("JobApply")) {
                Cache.putData(CatchValue.BackArrowRecall, getApplicationContext(), "BackArrowRecall", Cache.CACHE_LOCATION_DISK);
                //    Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, new JobsBoard());
                transaction.commit();


            }
            else
            if(s.equalsIgnoreCase("EventApply")) {
                Cache.putData(CatchValue.BackArrowRecall, getApplicationContext(), "BackArrowRecall", Cache.CACHE_LOCATION_DISK);
                //    Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, new EventBoard());
                transaction.commit();


            }
            else
            if(s.equalsIgnoreCase("EventBoard")) {
                Cache.putData(CatchValue.MyBooking, getApplicationContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
                //    Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, new FragmentA());
                transaction.commit();


            }
            else
                /*if(s.equalsIgnoreCase("FragmentC"))
                    Toast.makeText(Home.this," FragmentC ",Toast.LENGTH_SHORT).show();*/
                if(s.equalsIgnoreCase("SafeGauardNhs")) {
                    Cache.putData(CatchValue.MyBooking, getApplicationContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
                    Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                    //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, new FragmentA());
                    transaction.commit();
                }
                else
                /*if(s.equalsIgnoreCase("FragmentC"))
                    Toast.makeText(Home.this," FragmentC ",Toast.LENGTH_SHORT).show();*/
                    if(s.equalsIgnoreCase("SafeGauardNhsAdult")) {
                        Cache.putData(CatchValue.MyBooking, getApplicationContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
                        //  Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                        //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.content, new SafeGuardNhs());
                        transaction.commit();
                    }
                    else
                /*if(s.equalsIgnoreCase("FragmentC"))
                    Toast.makeText(Home.this," FragmentC ",Toast.LENGTH_SHORT).show();*/
                        if(s.equalsIgnoreCase("SafeGauardNhsContact")) {
                            //  Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                            //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.content, new SafeGuardNhsAdult());
                            transaction.commit();
                        }

                        else
                /*if(s.equalsIgnoreCase("FragmentC"))
                    Toast.makeText(Home.this," FragmentC ",Toast.LENGTH_SHORT).show();*/
                            if(s.equalsIgnoreCase("More")) {
                                Cache.putData(CatchValue.MyBooking, getApplicationContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
                                //  Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                                //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.content, new FragmentA());
                                transaction.commit();
                            }
                            else
                /*if(s.equalsIgnoreCase("FragmentC"))
                    Toast.makeText(Home.this," FragmentC ",Toast.LENGTH_SHORT).show();*/
                                if(s.equalsIgnoreCase("AboutUs")) {
                                    //  Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                                    //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.content, new More());
                                    transaction.commit();
                                }
                                else
                /*if(s.equalsIgnoreCase("FragmentC"))
                    Toast.makeText(Home.this," FragmentC ",Toast.LENGTH_SHORT).show();*/
                                    if(s.equalsIgnoreCase("ContactUs")) {
                                        //  Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                                        //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.content, new More());
                                        transaction.commit();
                                    }
                                    else

                                    if(s.equalsIgnoreCase("FragmentB")) {
                                        Cache.putData(CatchValue.MyBooking, getApplicationContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
                                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.content, new FragmentA());
                                        transaction.commit();
                                    }
                                    else

                                    if(s.equalsIgnoreCase("MyBookingRegister")) {
                                        Cache.putData(CatchValue.MyBooking, getApplicationContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
                                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.content, new FragmentA());
                                        transaction.commit();
                                    }
                                    else
                                    if(s.equalsIgnoreCase("EventCalender")) {
                                        //    Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                                        //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.content, new EventBoard());
                                        transaction.commit();


                                    }
                                    else
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(this);

                                        builder.setMessage("Are you sure you want logout ?")
                                                .setCancelable(false)
                                                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {

                                                    }
                                                })
                                                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        //  Action for 'NO' Button
                                                        Cache.putData(CatchValue.Email, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);
                                                        Cache.putData(CatchValue.Mobile, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);
                                                        Cache.putData(CatchValue.Name, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);
                                                        Cache.putData(CatchValue.Title, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);
                                                        Cache.putData(CatchValue.User_Pic, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);
                                                        Cache.putData(CatchValue.ID, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);
                                                        Cache.putData(CatchValue.BackArrow, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);

                                                     //   Cache.putData(CatchValue.USER_NAME, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);
                                                      //  Cache.putData(CatchValue.password, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);
                                                        Cache.putData(CatchValue.Safe, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);
                                                        Cache.putData(CatchValue.onBackPressed, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);

                                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        startActivity(intent);
                                                    }
                                                });

                                        //Creating dialog box
                                        AlertDialog alert = builder.create();
                                        //Setting the title manually
                                        alert.setTitle("CCG");

                                        alert.show();
                                    }





    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        backArrow = (ImageView) findViewById(R.id.backArrow);
        drProfileImg = (ImageView) findViewById(R.id.drProfileImg);
        backArrow.setVisibility(View.GONE);
        Cache.putData(CatchValue.onBackPressed, getApplicationContext(), "backFalse", Cache.CACHE_LOCATION_DISK);
        //   backArrow.setVisibility(View.GONE);

        String pImag = ((String) Cache.getData(CatchValue.User_Pic,Home.this));
        // Log.e("pimg","  "+pImag.length());
        if(pImag!=null && pImag.length()!=4) {
            byte[] decodedString = Base64.decode(pImag, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            drProfileImg.setImageBitmap(decodedByte);
        }
        else
            drProfileImg.setImageDrawable(getResources().getDrawable(R.mipmap.user));

        drProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cache.putData(CatchValue.BackArrowRecall, getApplicationContext(), "BackArrowRecall", Cache.CACHE_LOCATION_DISK);
                //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, new FragmentB());
                transaction.commit();
            }
        });


        heading = (TextView) findViewById(R.id.heading);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "hel_bold.ttf");
        heading.setTypeface(custom_font);

        // mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//backArrow.setVisibility(View.GONE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, new FragmentA());
        transaction.commit();
        //  BackArrowMethod();
        //  backArrow.setVisibility(View.GONE);

        String operation = ((String) Cache.getData(CatchValue.Operation, getApplicationContext()));
        if (operation != null)
        {
            if (operation.equalsIgnoreCase("ShowImage")) {
                backArrow.setVisibility(View.VISIBLE);
                //  Toast.makeText(Home.this, "ShowImage", Toast.LENGTH_SHORT).show();
                Cache.putData(CatchValue.Operation, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                transaction2.replace(R.id.content, new DisplayMessageBoard());
                transaction2.commit();
            }
            else
            if (operation.equalsIgnoreCase("TrainingBoard")) {
                backArrow.setVisibility(View.VISIBLE);
                //  Toast.makeText(Home.this, "ShowImage", Toast.LENGTH_SHORT).show();
                Cache.putData(CatchValue.Operation, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                transaction2.replace(R.id.content, new TrainingRegister());
                transaction2.commit();
            }
            else
            if (operation.equalsIgnoreCase("JobApply")) {
                backArrow.setVisibility(View.VISIBLE);
                //  Toast.makeText(Home.this, "ShowImage", Toast.LENGTH_SHORT).show();
                Cache.putData(CatchValue.Operation, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                transaction2.replace(R.id.content, new JobApply());
                transaction2.commit();
            }
            else
            if (operation.equalsIgnoreCase("EventApply")) {
                backArrow.setVisibility(View.VISIBLE);
                //  Toast.makeText(Home.this, "ShowImage", Toast.LENGTH_SHORT).show();
                Cache.putData(CatchValue.Operation, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                transaction2.replace(R.id.content, new EventApply());
                transaction2.commit();
            }
            else
            if (operation.equalsIgnoreCase("MyBookingBoard")) {
                backArrow.setVisibility(View.VISIBLE);
                //  Toast.makeText(Home.this, "ShowImage", Toast.LENGTH_SHORT).show();
                Cache.putData(CatchValue.Operation, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                transaction2.replace(R.id.content, new MyBookingRegister());
                transaction2.commit();
            }
            else
            if (operation.equalsIgnoreCase("EventCalender")) {
                backArrow.setVisibility(View.GONE);
                Cache.putData(CatchValue.Operation, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);
                Cache.putData(CatchValue.BackArrow, getApplicationContext(), "EventCalender", Cache.CACHE_LOCATION_DISK);
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                transaction2.replace(R.id.content, new EventCalender());
                transaction2.commit();
            }
            else
            if(operation.equalsIgnoreCase("EventCalenderBack")) {
                //    Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                transaction2.replace(R.id.content, new EventBoard());
                transaction2.commit();


            }
    }
       /* else
        {
            Toast.makeText(Home.this, " ShowImage ", Toast.LENGTH_SHORT).show();
            Cache.putData(CatchValue.Operation, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);
        }*/

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = ((String) Cache.getData(CatchValue.BackArrow,getApplicationContext()));
                if(s.equalsIgnoreCase("MessageBoard")) {
                    Cache.putData(CatchValue.Operation, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);
                    Cache.putData(CatchValue.MyBooking, getApplicationContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
                //    Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                 //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, new FragmentA());
                    transaction.commit();
                }
                else
                /*if(s.equalsIgnoreCase("FragmentC"))
                    Toast.makeText(Home.this," FragmentC ",Toast.LENGTH_SHORT).show();*/
                if(s.equalsIgnoreCase("DisplayMessageBoard")) {
                    Cache.putData(CatchValue.BackArrowRecall, getApplicationContext(), "BackArrowRecall", Cache.CACHE_LOCATION_DISK);
                    //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, new MessageBoard());
                    transaction.commit();
                }
                else
                if(s.equalsIgnoreCase("TrainingBoard")) {
                    Cache.putData(CatchValue.MyBooking, getApplicationContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
                    //    Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                    //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, new FragmentA());
                    transaction.commit();
                }
                else
                if(s.equalsIgnoreCase("TrainingRegister")) {
                    Cache.putData(CatchValue.BackArrowRecall, getApplicationContext(), "BackArrowRecall", Cache.CACHE_LOCATION_DISK);
                    //    Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                    //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, new TrainingBoard());
                    transaction.commit();
                }
                else
                if(s.equalsIgnoreCase("JobsBoard")) {
                    Cache.putData(CatchValue.MyBooking, getApplicationContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
                    //    Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                    //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, new FragmentA());
                    transaction.commit();
                }
                else
                if(s.equalsIgnoreCase("JobApply")) {
                    Cache.putData(CatchValue.BackArrowRecall, getApplicationContext(), "BackArrowRecall", Cache.CACHE_LOCATION_DISK);
                    //    Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                    //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, new JobsBoard());
                    transaction.commit();


                }
                else
                if(s.equalsIgnoreCase("EventApply")) {
                    Cache.putData(CatchValue.BackArrowRecall, getApplicationContext(), "BackArrowRecall", Cache.CACHE_LOCATION_DISK);
                    //    Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                    //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, new EventBoard());
                    transaction.commit();


                }
                else
                if(s.equalsIgnoreCase("EventBoard")) {
                    Cache.putData(CatchValue.MyBooking, getApplicationContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
                    //    Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                    //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, new FragmentA());
                    transaction.commit();


                }
                else
                /*if(s.equalsIgnoreCase("FragmentC"))
                    Toast.makeText(Home.this," FragmentC ",Toast.LENGTH_SHORT).show();*/
                    if(s.equalsIgnoreCase("SafeGauardNhs")) {
                        Cache.putData(CatchValue.MyBooking, getApplicationContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
                           Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                        //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.content, new FragmentA());
                        transaction.commit();
                    }
                    else
                /*if(s.equalsIgnoreCase("FragmentC"))
                    Toast.makeText(Home.this," FragmentC ",Toast.LENGTH_SHORT).show();*/
                        if(s.equalsIgnoreCase("SafeGauardNhsAdult")) {
                            Cache.putData(CatchValue.MyBooking, getApplicationContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
                          //  Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                            //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.content, new SafeGuardNhs());
                            transaction.commit();
                        }
                        else
                /*if(s.equalsIgnoreCase("FragmentC"))
                    Toast.makeText(Home.this," FragmentC ",Toast.LENGTH_SHORT).show();*/
                            if(s.equalsIgnoreCase("SafeGauardNhsContact")) {
                                //  Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                                //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.content, new SafeGuardNhsAdult());
                                transaction.commit();
                            }

                            else
                /*if(s.equalsIgnoreCase("FragmentC"))
                    Toast.makeText(Home.this," FragmentC ",Toast.LENGTH_SHORT).show();*/
                                if(s.equalsIgnoreCase("More")) {
                                    Cache.putData(CatchValue.MyBooking, getApplicationContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
                                    //  Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                                    //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.content, new FragmentA());
                                    transaction.commit();
                                }
                                else
                /*if(s.equalsIgnoreCase("FragmentC"))
                    Toast.makeText(Home.this," FragmentC ",Toast.LENGTH_SHORT).show();*/
                                    if(s.equalsIgnoreCase("AboutUs")) {
                                        //  Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                                        //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.content, new More());
                                        transaction.commit();
                                    }
                                    else
                /*if(s.equalsIgnoreCase("FragmentC"))
                    Toast.makeText(Home.this," FragmentC ",Toast.LENGTH_SHORT).show();*/
                                        if(s.equalsIgnoreCase("ContactUs")) {
                                            //  Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                                            //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                            transaction.replace(R.id.content, new More());
                                            transaction.commit();
                                        }
                                        else

                                            if(s.equalsIgnoreCase("FragmentB")) {
                                                Cache.putData(CatchValue.MyBooking, getApplicationContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
                                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                                transaction.replace(R.id.content, new FragmentA());
                                                transaction.commit();
                                            }
                                            else

                                            if(s.equalsIgnoreCase("MyBookingRegister")) {
                                                Cache.putData(CatchValue.MyBooking, getApplicationContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
                                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                                transaction.replace(R.id.content, new FragmentA());
                                                transaction.commit();
                                            }
                                            else
                                            if(s.equalsIgnoreCase("EventCalender")) {
                                                //    Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                                                //   Toast.makeText(Home.this, " HOme ", Toast.LENGTH_SHORT).show();
                                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                                transaction.replace(R.id.content, new EventBoard());
                                                transaction.commit();


                                            }



            }
        });
    }

    public void BackArrowMethod() {
        String s = ((String) Cache.getData(CatchValue.BackArrow,getApplicationContext()));

        if(s.equalsIgnoreCase("FragmentB"))
        {
          //  Log.e("1234"," "+s);
            backArrow.setVisibility(View.VISIBLE);
        }
        else
        if(s.equalsIgnoreCase("Hide"))
        {
          //  Log.e("1234"," "+s);
            backArrow.setVisibility(View.GONE);
        }
        else
            backArrow.setVisibility(View.VISIBLE);
      //  Toast.makeText(Home.this," FragmentB ",Toast.LENGTH_SHORT).show();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                 //   mTextMessage.setText(R.string.title_home);
                   // fragment = new FragmentB();
                    Cache.putData(CatchValue.BackArrow, getApplicationContext(), "Hide", Cache.CACHE_LOCATION_DISK);
                    Cache.putData(CatchValue.MyBooking, getApplicationContext(), "MyBookingCall", Cache.CACHE_LOCATION_DISK);
                    Cache.putData(CatchValue.BackArrowRecall, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);
                    BackArrowMethod();
                   // backArrow.setVisibility(View.VISIBLE);
                    transaction.replace(R.id.content, new FragmentA());
                    transaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                //    mTextMessage.setText(R.string.title_dashboard);
              //      fragment = new FragmentB();
                   // backArrow.setVisibility(View.VISIBLE);
                    Cache.putData(CatchValue.BackArrowRecall, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);
                    transaction.replace(R.id.content, new FragmentB());
                    transaction.commit();
                    return true;
      /*          case R.id.navigation_notifications:
                 //   mTextMessage.setText(R.string.title_notifications);
                    transaction.replace(R.id.content, new Notification());
                    transaction.commit();
                    return true;*/
                case R.id.navigation_more:
                //    mTextMessage.setText(R.string.title_more);
                    Cache.putData(CatchValue.BackArrowRecall, getApplicationContext(), "", Cache.CACHE_LOCATION_DISK);
                    transaction.replace(R.id.content, new More());
                    transaction.commit();

                    return true;
            }

         //   getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, "TAG").commit();

            return false;
        }

    };



}
