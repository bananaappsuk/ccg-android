package com.ccg.banana.ccg.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ccg.banana.ccg.Activity.Home;
import com.ccg.banana.ccg.Adapter.CalendarAdapter;
import com.ccg.banana.ccg.Adapter.EventBoardAdapter;
import com.ccg.banana.ccg.R;
import com.ccg.banana.ccg.ServiceClass.ConnectionDetector;
import com.ccg.banana.ccg.ServiceClass.Report;
import com.ccg.banana.ccg.ServiceClass.ServiceClass;
import com.ccg.banana.ccg.model.DList;
import com.ccg.banana.ccg.session.Cache;
import com.ccg.banana.ccg.session.CatchValue;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

//import java.text.DateFormat;
// import doctorsapp.com.doctorsapp.activity.MainActivity;
// import doctorsapp.com.doctorsapp.utils.UIUtils;

public class EventCalender extends Fragment {



//    private LinearLayout layoutHealth;

    private int selectedPos = 0;
    private static final int color = Color.parseColor("#228BC34A");
//    private final Calendar calendar = Calendar.getInstance();
   // private final Calendar calendar = Calendar.getInstance();
//    private MaterialCalendarView calendarView;
//    private CompactCalendarView compactCalendarView;
//    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());

    GridView calendar_gridView;
    private Locale locale;
    private GregorianCalendar calendar;
    private int cur_month, cur_year;

    private CalendarAdapter calendarAdapter;
    private TextView tv_month;

    ArrayList<DList> caDLists;
    private String selectedDate;
    ArrayList<DList> calendarDates;
    ArrayList<String> calendarDates2;
    ArrayList<JobPhotoList> trainingPhotoLists;
    TextView msgBoardTitle,avlMsg,msgCount;
    RecyclerView trainingBoardRCV;
    LinearLayout calenderLayout;
    String newDateString,current_Month_Day;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;

    ArrayList<EventBoardList> trainingBoardLists;
    ArrayList<EventBoardList> list;
    String sid;
    ProgressDialog progressDialog;
    JSONObject resultJsonObject,resultData,resultData2;
    String responseMessage;
    EventBoardAdapter trainingBoardAdapter;




    // String Content_Id,Content_Type,Mesage_Website_Link,Message_Id,Message_Post_DateTime,Message_Post_DateTime_Format,Message_Title,Message_Type,Message_URL,Status,Trainee_Name,Message_Description;

    String Capacity,Register_Status,Status,Traning_Address1,Traning_Address2,Traning_Category_Id,Traning_Category_Name,Traning_City,Traning_ContactPersonname,Traning_ContactPersonnumber,Traning_Day,Traning_Description,Traning_EndDate,Traning_EndDate_Foramt,Traning_EndTime,Traning_EndTime_Format,Traning_Id,Traning_Image;
    String ID,Traning_Id2,Traning_Photo,Traning_Postcode,Traning_StartDate,Traning_StartDate_Format,Traning_StartTime,Traning_StartTime_Format,Traning_Title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.event_calender, container, false);




        msgBoardTitle = (TextView)rootView.findViewById(R.id.msgBoardTitle);
        avlMsg = (TextView)rootView.findViewById(R.id.avlMsg);
        msgCount = (TextView)rootView.findViewById(R.id.msgCount);
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "AbrilFatface-Regular.ttf");
        msgBoardTitle.setTypeface(custom_font);
        calenderLayout = (LinearLayout)rootView.findViewById(R.id.calenderLayout);
        trainingBoardRCV= (RecyclerView)rootView.findViewById(R.id.trainingBoardRCV);
        trainingPhotoLists = new ArrayList<>();
        trainingBoardLists = new ArrayList<>();
        sid = ((String) Cache.getData(CatchValue.ID,getContext()));
        cd = new ConnectionDetector(getContext());
        //isInternetPresent = cd.isConnectionAvailable();

        isInternetPresent = cd.isConnectionAvailable();



        // Inflate the layout for this fragment
        Cache.putData(CatchValue.BackArrow, getContext(), "EventBoard", Cache.CACHE_LOCATION_DISK);
        ((Home)getActivity()).BackArrowMethod();

        calenderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Home.class);
                Cache.putData(CatchValue.Operation, getContext(),"EventCalender", Cache.CACHE_LOCATION_DISK);
                startActivity(intent);
            }
        });


        locale = getResources().getConfiguration().locale;
        Cache.putData(CatchValue.BackArrow, getContext(), "EventCalender", Cache.CACHE_LOCATION_DISK);
        ((Home)getActivity()).BackArrowMethod();
        calendar = (GregorianCalendar) GregorianCalendar.getInstance();
        cur_month = calendar.get(Calendar.MONTH) + 1;
        cur_year = calendar.get(Calendar.YEAR);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        Date d = new Date();
        int dd = d.getDate();
        int startDate = dd;
        current_Month_Day = year + "-" + month + "-" + startDate;

        tv_month = (TextView) rootView.findViewById(R.id.seva_text_month);
        trainingBoardRCV= (RecyclerView)rootView.findViewById(R.id.trainingBoardRCV);
        tv_month.setText(calendarMonth(String.valueOf(DateFormat.format("MMM yyyy", calendar)), locale));
        calenderLayout = (LinearLayout)rootView.findViewById(R.id.calenderLayout);
        calendarAdapter = new CalendarAdapter(getActivity(), calendar,  new ArrayList<DList>());
        calendar_gridView = (GridView) rootView.findViewById(R.id.seva_calendar);
        loadCalendarDates();

        if (isInternetPresent) {
            new TrainingBoardData().execute();
        } else {
            ShowNoInternetDialog();
        }

        calendar_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                ((CalendarAdapter) parent.getAdapter()).setSelected(view, position);
                selectedDate = CalendarAdapter.day_string.get(position);
                Log.e("selectedDate "," "+selectedDate);
               // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    Date date = sdf.parse(selectedDate);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                /*    System.out.println(date);
                    System.out.println(formatter.format(date));*/
                    newDateString= formatter.format(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date date1 = null;
                Date date2=null;

                try {
                    date1 = sdf.parse(selectedDate);
                  //  showToast("date1 "+date1);
                  //  Log.e("DATE  ",""+date1);
                    for(int i=0;i<calendarDates.size();i++)
                    {
                        date2 =  sdf.parse(calendarDates.get(i).getDate());
                     //   Log.e("DATE  ",""+date2);
                        if (date1.equals(date2)) {
                          //  showToast("Equal "+calendarDates2.get(i));
                           // showToast("Equal "+calendarDates.get(i).getDate());
                            //      new DateService().execute(newDateString,doctorSid); dd-mm-yyyy

                            isInternetPresent = cd.isConnectionAvailable();
                            if (isInternetPresent) {
                                // new TrainingBoardData().execute();
                                new EventCalenderSelected().execute(calendarDates2.get(i),sid);
                            } else {
                              //  ShowNoInternetDialog();
                            }

                        }
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }


              /*  if (date1.before(date2)) {
                    Toast.makeText(getContext(),"Selection date Should be Current date or After Current Date",Toast.LENGTH_SHORT).show();
                }*/

            }
        });



        calenderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Home.class);
                Cache.putData(CatchValue.Operation, getContext(),"EventCalenderBack", Cache.CACHE_LOCATION_DISK);
                startActivity(intent);
            }
        });


        ImageButton previous = (ImageButton) rootView.findViewById(R.id.seva_calendar_month_previous);
        previous.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });


        ImageButton next = (ImageButton) rootView.findViewById(R.id.seva_calendar_month_next);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();
            }
        });


/*
        Log.e("color hp ",""+ UIUtils.getHospitalCode(getContext()));
        Log.e("color st ",""+ UIUtils.getStudentId(getContext()));
        Log.e("color tk ",""+ UIUtils.getToken(getContext()));
        Log.e("color pd ",""+ UIUtils.getPassword(getContext()));
*/
        return rootView;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Reset the selected position when the fragment is  recreated
    }

    @Override
    public void onResume() {
        super.onResume();


    }


/*
    public class EventDecorator implements DayViewDecorator {

        private final int color;
        private final HashSet<CalendarDay> dates;

        public EventDecorator(int color, Collection<CalendarDay> dates) {
            this.color = color;
            this.dates = new HashSet<>(dates);
        }



        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(5, color));
        }
    }
*/



/*
    private static class NumberPickerTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_MOVE && v.getParent() != null) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                v.performClick();
            }
            v.onTouchEvent(event);
            return true;
        }
    }*/

/*    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }*/

    private void loadCalendarDates(){
//        if (isInternetPresent) {
            new GetSevaCalenderTask().execute();
//        } else {
//            ShowNoInternetDialog();
//        }
    }

    private class GetSevaCalenderTask extends AsyncTask<String, Void, JSONObject> {


        JSONObject jsonObject1;

        @Override
        protected void onPreExecute() {
            // Show Progress Dialog
//            progressDialog = new ProgressDialog(SevaActivity.this);
//            progressDialog.setMessage(getResources().getString(R.string.text_processing));
//            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            try {
//                utility = new Utility(SevaActivity.this);
              //  jsonObject1 = getCalendarDates("http://www.doctorsfriend.uk/AppGetDiaryView.ashx?HospitalCode=DFTEST&StudyID=DFTEST1&Token=BB4C6A75-2BE5-4504-80A0-1E069EF65ED7&UserPassword=selenite");
               // jsonObject1 = getCalendarDates("http://www.doctorsfriend.uk/AppGetDiaryView.ashx?HospitalCode="+ UIUtils.getHospitalCode(getContext())+"&StudyID="+UIUtils.getStudentId(getContext())+"&Token="+UIUtils.getToken(getContext())+"&UserPassword="+UIUtils.getPassword(getContext()));
                jsonObject1 = getCalendarDates("http://bananatech.co.uk/api/Events/GeteventsbyUserID?UserID="+sid);
                                              //http://ccg.bananaappscenter.com/api/Events/GeteventsbyUserID?UserID="+sid

            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObject1;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            // Dismiss Progress Dialog
//            progressDialog.dismiss();
            if (result != null) {
                getSevaCalenderInfoResult(result);
            } else {
//                Toast.makeText(SevaActivity.this, getResources().getString(R.string.text_something_went_wrong_pls_try_again), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getSevaCalenderInfoResult(JSONObject result) {

        try {


          //  Log.e("1234 "," 11 "+result);
            calendarDates = new ArrayList<>();
            calendarDates2 = new ArrayList<>();
          //  Log.e("1234 "," 111 "+result);

          //      Log.e("1234 "," 1111 "+result);
                JSONArray jsonElements = result.getJSONArray("EventList");
          //      Log.e("1234 "," 22 "+jsonElements);
                DList dList = null;
                int colcrValue=0;
                String calendarDate="";
                if (jsonElements != null && jsonElements.length() > 0) {
                    for (int x = 0; x < jsonElements.length(); x++) {
                        JSONObject jsonObject1 = jsonElements.getJSONObject(x);
                        for (int z = 0; z < jsonObject1.length(); z++) {
                            calendarDate = jsonObject1.getString("Event_StartDate_Format");
                  //          Log.e("1234 "," 33 "+calendarDate);
                  //          calendarDate = calendarDate.replace("-", "/");
                            String[] str_array = calendarDate.split("-");
                            String stringa = str_array[0];
                            String stringb = str_array[1];
                            String stringc = str_array[2];
                            String s=(new StringBuilder()).append(stringc).append("-").append(stringb).append("-").append(stringa).toString();
                           /* Log.e("030118  ","s "+s);
                            Log.e("030118  ","calendarDate "+calendarDate);*/
                      /*      switch (jsonObject1.getString("HeadacheSeverityStatus")) {
                                case "SE":
                                    colcrValue = Color.parseColor("#CC494E");
                                    break;
                                case "PF":
                                    colcrValue = Color.parseColor("#A7F6A1");
                                    break;
                                case "MO":
                                    colcrValue = Color.parseColor("#FE9A01");
                                    break;
                                case "MI":
                                    colcrValue = Color.parseColor("#67FEFF");
                                    break;
                            }*/
                            colcrValue = Color.parseColor("#5F2862");
                        //    dList = new DList(jsonObject1.getInt("ID"),s , jsonObject1.getString("HeadacheSeverityStatus"), colcrValue);
                            dList = new DList(jsonObject1.getInt("Category_Id"),s , jsonObject1.getString("Event_Day"), colcrValue);
                         //   dList = new DList(jsonObject1.getInt("ID"),calendarDate , jsonObject1.getString("HeadacheSeverityStatus"), colcrValue);
                        }
                        calendarDates.add(dList);
                        calendarDates2.add(jsonObject1.getString("Event_StartDate_Format"));

                    }
                }

                GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
                refreshCalendar();
                calendarAdapter = new CalendarAdapter(getActivity(), calendar, calendarDates);
                calendar_gridView.setAdapter(calendarAdapter);


        } catch (JSONException ex) {
            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    public JSONObject getCalendarDates(String cURL) {

        JSONObject report = new JSONObject();
        HttpURLConnection client = null;
        JSONObject response = null;
        StringBuilder result;
        OutputStreamWriter writer;
        InputStream input;
        String output, line;
        BufferedReader reader;

        try {
            URL url = new URL(cURL);

            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("GET");
            client.connect();
            // Read the input stream into temples_list_row String
            InputStream inputStream = client.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            output = buffer.toString();
            response = new JSONObject(output);

        } catch (ConnectException e) {

        } catch (Exception e) {

        } finally {
            try {
                client.disconnect();
            } catch (NullPointerException ex) {

            }
        }
        return response;
    }

    private String calendarMonth(String date, Locale locale) {

        try {
            SimpleDateFormat format = new SimpleDateFormat("MMM yyyy");
            Date d = format.parse(date);
            SimpleDateFormat serverFormat = new SimpleDateFormat("MMMM yyyy", locale);
            return serverFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    protected void setPreviousMonth() {
            int nav_year = calendar.get(Calendar.YEAR);
            int nav_month = calendar.get(Calendar.MONTH);
       /* Log.e("12345","nav_year   "+nav_year);

        Log.e("12345","cur_year   "+cur_year);
        Log.e("12345","nav_month  "+nav_month);
        Log.e("12345","cur_month  "+cur_month);*/
            Date pre_date = new Date();
            int pre_date_value = pre_date.getDate();

            if (nav_month == cur_month && nav_year == cur_year) {
                calendar.set(GregorianCalendar.MONTH, nav_month - 1);
                calendar.set(GregorianCalendar.YEAR, nav_year);
                calendarAdapter = new CalendarAdapter(getActivity(), calendar,  calendarDates);
                calendar_gridView.setAdapter(calendarAdapter);
                //new GetSevaCalenderTask().execute();
          //      Log.e("12345","nav_year   1");

            } else if (nav_month > cur_month && nav_year == cur_year) {
                calendar.set(GregorianCalendar.MONTH, nav_month - 1);
                calendar.set(GregorianCalendar.YEAR, nav_year);
                calendarAdapter = new CalendarAdapter(getActivity(), calendar,  calendarDates);
                calendar_gridView.setAdapter(calendarAdapter);

           //     Log.e("12345","nav_year   2");

            } else if (nav_month == 1 && nav_year > cur_year) {
                nav_month = 1;
                calendar.set(GregorianCalendar.MONTH, nav_month - 1);
                calendar.set(GregorianCalendar.YEAR, nav_year);
                calendarAdapter = new CalendarAdapter(getActivity(), calendar,  calendarDates);
                calendar_gridView.setAdapter(calendarAdapter);
            //    Log.e("12345","nav_year   3");

            } else if (nav_month < cur_month && nav_year > cur_year) {
                if(nav_month==0) {
                    nav_month = 12;
                    nav_year--;
                }
               /* Log.e("12345","nav_month   "+nav_month);
                Log.e("12345","nav_year    "+nav_year);
                Log.e("12345","cur_month    "+cur_month);
                Log.e("12345","cur_year    "+cur_year);*/

                calendar.set(GregorianCalendar.MONTH, nav_month - 1);
                calendar.set(GregorianCalendar.YEAR, nav_year);
                calendarAdapter = new CalendarAdapter(getActivity(), calendar,  calendarDates);
                calendar_gridView.setAdapter(calendarAdapter);
                if(nav_month==cur_month && nav_year == cur_year)
                new GetSevaCalenderTask().execute();
            //    Log.e("12345","nav_year   4");

            } else if (nav_month >= cur_month && nav_year > cur_year) {
                calendar.set(GregorianCalendar.MONTH, nav_month - 1);
                calendar.set(GregorianCalendar.YEAR, nav_year);
                calendarAdapter = new CalendarAdapter(getActivity(), calendar, calendarDates);
                calendar_gridView.setAdapter(calendarAdapter);

          //      Log.e("12345","nav_year   5");
            }
            else if (nav_month < cur_month && nav_year == cur_year) {
            calendar.set(GregorianCalendar.MONTH, nav_month - 1);
            calendar.set(GregorianCalendar.YEAR, nav_year);
             calendarAdapter = new CalendarAdapter(getActivity(), calendar, calendarDates);
                calendar_gridView.setAdapter(calendarAdapter);

             //   Log.e("12345","nav_year   6");
        }
    }

    protected void setNextMonth() {

            int nav_year = calendar.get(Calendar.YEAR);
            int nav_month = calendar.get(Calendar.MONTH) + 1;
      /*  Log.e("030118 nav_month "," "+nav_month);
        Log.e("030118 cur_month "," "+cur_month);
        Log.e("030118 nav_year "," "+nav_year);
        Log.e("030118 cur_year "," "+cur_year);*/

            if (nav_month == cur_month && nav_year == cur_year) {
                nav_month++;
       //      Log.e("030118 month "," 1 ");
                calendar.set(GregorianCalendar.MONTH, nav_month - 1);
                calendar.set(GregorianCalendar.YEAR, nav_year);
             //   calendarAdapter = new CalendarAdapter(getActivity(), calendar,  new ArrayList<DList>());
                calendarAdapter = new CalendarAdapter(getActivity(), calendar, calendarDates);
                calendar_gridView.setAdapter(calendarAdapter);

            //    refreshCalendar();


           //     new GetSevaCalenderTask().execute();
//                isInternetPresent = cd.isConnectionAvailable();
//                if (isInternetPresent) {
//                new GetSevaCalenderTask().execute();
//                } else {
//                    ShowNoInternetDialog();
//                }
//            }
            } else if (nav_month == 12 && nav_year >= cur_year) {
                nav_month = 1;
                nav_year++;
             //   Log.e("030118 month "," 2");
                calendar.set(GregorianCalendar.MONTH, nav_month - 1);
                calendar.set(GregorianCalendar.YEAR, nav_year);
                calendarAdapter = new CalendarAdapter(getActivity(), calendar,  calendarDates);
                calendar_gridView.setAdapter(calendarAdapter);
//                isInternetPresent = cd.isConnectionAvailable();
//                if (isInternetPresent) {
//                new GetSevaCalenderTask().execute();
//                } else {
//                    ShowNoInternetDialog();
//                }
//            }
            } else if (nav_month <= cur_month && nav_year > cur_year) {
                nav_month++;
            //    Log.e("030118 month "," 3");
                calendar.set(GregorianCalendar.MONTH, nav_month - 1);
                calendar.set(GregorianCalendar.YEAR, nav_year);
                calendarAdapter = new CalendarAdapter(getActivity(), calendar,  calendarDates);
                calendar_gridView.setAdapter(calendarAdapter);
//                isInternetPresent = cd.isConnectionAvailable();
//                if (isInternetPresent) {
//                new GetSevaCalenderTask().execute();
//                } else {
//                    ShowNoInternetDialog();
//                }
//            }
            } else if (nav_month > cur_month && nav_year >= cur_year) {
                nav_month++;
           //     Log.e("030118 month "," 4");
                calendar.set(GregorianCalendar.MONTH, nav_month - 1);
                calendar.set(GregorianCalendar.YEAR, nav_year);
                calendarAdapter = new CalendarAdapter(getActivity(), calendar,  calendarDates);
                calendar_gridView.setAdapter(calendarAdapter);
//                isInternetPresent = cd.isConnectionAvailable();
//                if (isInternetPresent) {
//                new GetSevaCalenderTask().execute();
//                } else {
//                    ShowNoInternetDialog();
//                }
//            }
            }else if (nav_month < cur_month && nav_year == cur_year) {
                nav_month++;
                if(nav_month == cur_month && nav_year == cur_year){
                    calendar.set(GregorianCalendar.MONTH, nav_month - 1);
                    calendar.set(GregorianCalendar.YEAR, nav_year);
                 //   Log.e("030118 month "," 5");
                    new GetSevaCalenderTask().execute();
                }else {
                //    Log.e("030118 month "," 6");
                    calendar.set(GregorianCalendar.MONTH, nav_month - 1);
                    calendar.set(GregorianCalendar.YEAR, nav_year);
                    calendarAdapter = new CalendarAdapter(getActivity(), calendar,calendarDates);
                    calendar_gridView.setAdapter(calendarAdapter);
                }

//                isInternetPresent = cd.isConnectionAvailable();
//                if (isInternetPresent) {
//                new GetSevaCalenderTask().execute();
//                } else {
//                    ShowNoInternetDialog();
//                }
//            }
            }


    }


    public void refreshCalendar() {
        calendarAdapter.refreshDays();
        calendarAdapter.notifyDataSetChanged();
        tv_month.setText(calendarMonth(String.valueOf(DateFormat.format("MMM yyyy", calendar)), locale));
    }

    private class EventCalenderSelected extends AsyncTask<String,Void,Report> {

        Report response = new Report();

        @Override
        protected void onPreExecute() {
          //  showProgressDialog();
        }

        @Override
        protected Report doInBackground(String... params) {
            try {
                ServiceClass utility = new ServiceClass();
                // response = utility.getJsonObjectResponse("http://ccg.bananaappscenter.com/api/Events/GetTraningsbyUserID?UserID="+sid);
                response = utility.getJsonObjectResponse("http://bananatech.co.uk/api/Events/GetEventsbyDate?Date="+params[0]+"&UserID="+params[1]);
            } catch (Exception e) {
                showToast("Server couldn't respond,Please try again");
            }
            return response;
        }

        @Override
        protected void onPostExecute(Report response) {
            dismissProgressDialog();
            if (response!=null){
                CalenderResponce(response);
            }
            else {
                showToast("Server couldn't respond,Please try again");
            }
        }

    }

    private void CalenderResponce(Report response) {
        try {
            trainingBoardLists.clear();

            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = sharedPrefs.edit();
            Gson gson = new Gson();
            String json2 = gson.toJson(""+trainingBoardLists);
            editor.putString("AllEventBoardLists", json2);
            editor.commit();


            if (response.getStatus().equalsIgnoreCase("true")) {
                resultJsonObject = response.getJsonObject();
                if(resultJsonObject.length()>0&&resultJsonObject!=null) {
                    JSONObject resultJson=resultJsonObject.getJSONObject("Msg");
                    if(resultJson.length()>0&& resultJson!=null){
                        if (resultJson.getString("StatusCode").equalsIgnoreCase("200")) {
                            responseMessage = resultJson.getString("Message");
                            //  Toast.makeText(getContext(),"  Message_Title "+responseMessage, Toast.LENGTH_SHORT).show();
                            if(resultJsonObject.length()!=0 && !resultJsonObject.equals("[]")) {
                                JSONArray resultJsonArray = resultJsonObject.getJSONArray("EventList");
                                if (resultJsonArray.length() != 0 && !resultJsonArray.equals("[]")) {
                                    for (int i = 0; i < resultJsonArray.length(); i++) {

                                        resultData = resultJsonArray.getJSONObject(i);
                                        //     showToast("Job_Category_Name  "+resultData.getString("Job_Category_Name"));
                                        Capacity = resultData.getString("Capacity");
                                        Register_Status = resultData.getString("Register_Status");
                                        Status = resultData.getString("Status");
                                        Traning_Address1 = resultData.getString("Event_Address1");
                                        Traning_Address2 = resultData.getString("Event_Address2");

                                        Traning_Category_Id = resultData.getString("Category_Id");
                                        Traning_Category_Name = resultData.getString("Category_Name");

                                        Traning_City = resultData.getString("Event_City");
                                        Traning_ContactPersonname = resultData.getString("Event_ContactPersonname");
                                        Traning_ContactPersonnumber = resultData.getString("Event_ContactPersonnumber");
                                        Traning_Day = resultData.getString("Event_Day");
                                        Traning_Description = resultData.getString("Event_Description");
                                        Traning_EndDate = resultData.getString("Event_EndDate");
                                        Traning_EndDate_Foramt = resultData.getString("Event_EndDate_Format");
                                        Traning_EndTime = resultData.getString("Event_EndTime");
                                        Traning_EndTime_Format = resultData.getString("Event_EndTime_Fromat");
                                        Traning_Id = resultData.getString("Event_Id");
                                        Traning_Image = resultData.getString("Event_Image");
                                        Traning_Postcode = resultData.getString("Event_Postcode");
                                        Traning_StartDate = resultData.getString("Event_StartDate");
                                        Traning_StartDate_Format = resultData.getString("Event_StartDate_Format");
                                        Traning_StartTime = resultData.getString("Event_StartTime");
                                        Traning_StartTime_Format = resultData.getString("Event_StartTime_Format");
                                        Traning_Title = resultData.getString("Event_Title");


                                        trainingPhotoLists.clear();
                                        resultData = resultJsonArray.getJSONObject(i);
                                        JSONArray resultJsonArray2 = resultData.getJSONArray("Event_Photos");
                                        JobPhotoList t = null;
                                        for (int i2 = 0; i2 < resultJsonArray2.length(); i2++) {
                                            resultData2 = resultJsonArray2.getJSONObject(i2);
                                            t = new JobPhotoList(resultData2.getString("ID"),resultData2.getString("Event_Id"),resultData2.getString("Event_Photo"));
                                            trainingPhotoLists.add(t);
                                        }


                                        //TrainingBoardList d = new TrainingBoardList(Capacity,Register_Status,Status,Traning_Address1,Traning_Address2,Traning_Category_Id,Traning_Category_Name,Traning_City,Traning_ContactPersonname,Traning_ContactPersonnumber,Traning_Day,Traning_Description,Traning_EndDate,Traning_EndDate_Foramt,Traning_EndTime,Traning_EndTime_Format,Traning_Id,Traning_Image,Traning_Postcode,Traning_StartDate,Traning_StartDate_Format,Traning_StartTime,Traning_StartTime_Format,Traning_Title,trainingPhotoLists);
                                        EventBoardList d = new EventBoardList(Capacity,Register_Status,Status,Traning_Address1,Traning_Address2,Traning_Category_Id,Traning_Category_Name,Traning_City,Traning_ContactPersonname,Traning_ContactPersonnumber,Traning_Day,Traning_Description,Traning_EndDate,Traning_EndDate_Foramt,Traning_EndTime,Traning_EndTime_Format,Traning_Id,Traning_Image,Traning_Postcode,Traning_StartDate,Traning_StartDate_Format,Traning_StartTime,Traning_StartTime_Format,Traning_Title,trainingPhotoLists);
                                        trainingBoardLists.add(d);
                                    }
                                }
                                // showToast("Traning_Photo  "+trainingPhotoLists.get(0).getTraning_Photo());
                                sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                                editor = sharedPrefs.edit();
                                gson = new Gson();

                                json2 = gson.toJson(trainingBoardLists);
                                editor.putString("AllEventBoardLists", json2);
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
             // response = utility.getJsonObjectResponse("http://ccg.bananaappscenter.com/api/Events/GetTraningsbyUserID?UserID="+sid);
                response = utility.getJsonObjectResponse("http://bananatech.co.uk/api/Events/GeteventsbyUserID?UserID="+sid);
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


        /*    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = sharedPrefs.edit();
            Gson gson = new Gson();
            String json2 = gson.toJson(""+trainingBoardLists);
            editor.putString("AllEventBoardLists", json2);
            editor.commit();*/


            if (response.getStatus().equalsIgnoreCase("true")) {
                resultJsonObject = response.getJsonObject();
                if(resultJsonObject.length()>0&&resultJsonObject!=null) {
                    JSONObject resultJson=resultJsonObject.getJSONObject("Msg");
                    if(resultJson.length()>0&& resultJson!=null){
                        if (resultJson.getString("StatusCode").equalsIgnoreCase("200")) {
                            responseMessage = resultJson.getString("Message");
                           /* if(resultJsonObject.length()!=0 && !resultJsonObject.equals("[]")) {
                                JSONArray resultJsonArray = resultJsonObject.getJSONArray("EventList");
                                if (resultJsonArray.length() != 0 && !resultJsonArray.equals("[]")) {
                                    for (int i = 0; i < resultJsonArray.length(); i++) {

                                        resultData = resultJsonArray.getJSONObject(i);
                                        Capacity = resultData.getString("Capacity");
                                        Register_Status = resultData.getString("Register_Status");
                                        Status = resultData.getString("Status");
                                        Traning_Address1 = resultData.getString("Event_Address1");
                                        Traning_Address2 = resultData.getString("Event_Address2");
                                        Traning_Category_Id = resultData.getString("Category_Id");
                                        Traning_Category_Name = resultData.getString("Category_Name");
                                        Traning_City = resultData.getString("Event_City");
                                        Traning_ContactPersonname = resultData.getString("Event_ContactPersonname");
                                        Traning_ContactPersonnumber = resultData.getString("Event_ContactPersonnumber");
                                        Traning_Day = resultData.getString("Event_Day");
                                        Traning_Description = resultData.getString("Event_Description");
                                        Traning_EndDate = resultData.getString("Event_EndDate");
                                        Traning_EndDate_Foramt = resultData.getString("Event_EndDate_Format");
                                        Traning_EndTime = resultData.getString("Event_EndTime");
                                        Traning_EndTime_Format = resultData.getString("Event_EndTime_Fromat");
                                        Traning_Id = resultData.getString("Event_Id");
                                        Traning_Image = resultData.getString("Event_Image");
                                        Traning_Postcode = resultData.getString("Event_Postcode");
                                        Traning_StartDate = resultData.getString("Event_StartDate");
                                        Traning_StartDate_Format = resultData.getString("Event_StartDate_Format");
                                        Traning_StartTime = resultData.getString("Event_StartTime");
                                        Traning_StartTime_Format = resultData.getString("Event_StartTime_Format");
                                        Traning_Title = resultData.getString("Event_Title");


                                        trainingPhotoLists.clear();
                                        resultData = resultJsonArray.getJSONObject(i);
                                        JSONArray resultJsonArray2 = resultData.getJSONArray("Event_Photos");
                                        JobPhotoList t = null;
                                        for (int i2 = 0; i2 < resultJsonArray2.length(); i2++) {
                                            resultData2 = resultJsonArray2.getJSONObject(i2);
                                            t = new JobPhotoList(resultData2.getString("ID"),resultData2.getString("Event_Id"),resultData2.getString("Event_Photo"));
                                            trainingPhotoLists.add(t);
                                        }
                                        EventBoardList d = new EventBoardList(Capacity,Register_Status,Status,Traning_Address1,Traning_Address2,Traning_Category_Id,Traning_Category_Name,Traning_City,Traning_ContactPersonname,Traning_ContactPersonnumber,Traning_Day,Traning_Description,Traning_EndDate,Traning_EndDate_Foramt,Traning_EndTime,Traning_EndTime_Format,Traning_Id,Traning_Image,Traning_Postcode,Traning_StartDate,Traning_StartDate_Format,Traning_StartTime,Traning_StartTime_Format,Traning_Title,trainingPhotoLists);
                                        trainingBoardLists.add(d);
                                    }
                                }
                                sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                                editor = sharedPrefs.edit();
                                gson = new Gson();

                                json2 = gson.toJson(trainingBoardLists);
                                editor.putString("AllEventBoardLists", json2);
                                editor.commit();
 ShowData();

                            }
*/
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


                showToast("Some thing went wrong please check once");

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
        String json = sharedPrefs.getString("AllEventBoardLists", null);

        if(json.equals("\"[]\"")) {
            //     Toast.makeText(getContext(), "empty", Toast.LENGTH_SHORT).show();
       /*     avlMsg.setVisibility(View.VISIBLE);
            avlMsg.setText("No data is available");*/
            trainingBoardRCV.setAdapter(null);

        }
        else {
        //    avlMsg.setVisibility(View.GONE);
            //   Toast.makeText(getContext(), "Not empty " + json, Toast.LENGTH_SHORT).show();
            //  avlHosp.setText("Available hospitals");
            dismissProgressDialog();
            Type type = new TypeToken<ArrayList<EventBoardList>>() {
            }.getType();
            list = gson.fromJson(json, type);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            trainingBoardRCV.setLayoutManager(linearLayoutManager);
            msgCount.setText("You have "+list.size()+" messages in total.");
            trainingBoardAdapter = new EventBoardAdapter(getContext(), list);
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

    public void showToast(String txt) {
        Toast toast = Toast.makeText(getContext(), txt, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

}
