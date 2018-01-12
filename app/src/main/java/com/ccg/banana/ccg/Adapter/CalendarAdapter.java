package com.ccg.banana.ccg.Adapter;

/**
 * Created by govt on 22-11-2016.
 */

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import com.ccg.banana.ccg.R;
import com.ccg.banana.ccg.model.DList;

public class CalendarAdapter extends BaseAdapter {
    public static List<String> day_string;
    private GregorianCalendar pmonth;
    /**
     * calendar instance for previous month for getting complete view
     */
    private GregorianCalendar pmonthmaxset;
    private int firstDay;
    private int maxWeeknumber;
    private int maxP;
    private int calMaxP;
    private int mnthlength;
    private String itemvalue, curentDateString;
    private DateFormat df;
    private ArrayList<DList> AvailableDates, QuotaFullDates, QuotaNotReleasedDates, SevaNotPerformedDates;
    private Context context;
    private java.util.Calendar month;
    private GregorianCalendar selectedDate;
    private ArrayList<String> items;
    private View previousView;

    public CalendarAdapter(Context context, GregorianCalendar monthCalendar, ArrayList<DList> AvailableDates) {

        Log.e("030118 AvailableDates "," "+AvailableDates);

        this.AvailableDates = AvailableDates;

        CalendarAdapter.day_string = new ArrayList<String>();
        Locale.setDefault(Locale.US);
        month = monthCalendar;
        selectedDate = (GregorianCalendar) monthCalendar.clone();
        this.context = context;
        month.set(GregorianCalendar.DAY_OF_MONTH, 1);

        this.items = new ArrayList<String>();
        df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
      //  df = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        curentDateString = df.format(selectedDate.getTime());
        refreshDays();

    }

    public int getCount() {
        return day_string.size();
    }

    public Object getItem(int position) {
        return day_string.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new view for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView dayView;
        if (convertView == null) { // if it's not recycled, initialize some
            // attributes
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.layout_calendar_item, null);
            v.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT));
        }

        dayView = (TextView) v.findViewById(R.id.calendar_date_cell);
        String[] separatedTime = day_string.get(position).split("-");
        String gridvalue = separatedTime[2].replaceFirst("^0*", "");
        if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
            dayView.setTextColor(Color.GRAY);
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
            dayView.setTextColor(Color.GRAY);
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else {
            // setting curent month's days in blue color.
            dayView.setBackgroundResource(R.drawable.ic_circle_gray);
            dayView.setTextColor(Color.WHITE);
        }


        if (day_string.get(position).equals(curentDateString)) {
            // current date
            dayView.setBackgroundResource(R.drawable.ic_circle_gray);
          //  dayView.setBackgroundResource(R.color.green);
            dayView.setTextColor(Color.WHITE);
        } else {

            dayView.setBackgroundResource(R.drawable.ic_circle_gray);
            dayView.setTextColor(Color.WHITE);
            //v.setBackgroundColor(Color.WHITE);
        }


        dayView.setText(gridvalue);

        // create date string for comparison
        String date = day_string.get(position);

        if (date.length() == 1) {
            date = "0" + date;
        }
        String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }

        // show icon if date is not empty and it exists in the items array
        /*ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
        if (date.length() > 0 && items != null && items.contains(date)) {
            iw.setVisibility(View.VISIBLE);
        } else {
            iw.setVisibility(View.GONE);
        }
        */

/*        String[] separatedTime = day_string.get(position).split("-");
        String gridvalue = separatedTime[2].replaceFirst("^0*", "");
        if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
            dayView.setBackgroundResource(R.mipmap.ic_circle_gray);
            dayView.setTextColor(Color.WHITE);
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
            dayView.setBackgroundResource(R.mipmap.ic_circle_gray);
            dayView.setTextColor(Color.WHITE);
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else {
            // setting curent month's days in blue color.
            dayView.setBackgroundResource(R.mipmap.ic_circle_gray);
            dayView.setTextColor(Color.WHITE);
        }
        if (day_string.get(position).equals(curentDateString)) {

            v.setBackgroundColor(Color.CYAN);
        } else {
            v.setBackgroundColor(Color.parseColor("#343434"));
        }
        dayView.setText(gridvalue);
        // create date string for comparison
        String date = day_string.get(position);

        if (date.length() == 1) {
            date = "0" + date;
        }
        String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }
        // show icon if date is not empty and it exists in the items array
        *//*ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
        if (date.length() > 0 && items != null && items.contains(date)) {
            iw.setVisibility(View.VISIBLE);
        } else {
            iw.setVisibility(View.GONE);
        }
        */
        setEventView(v, position, dayView);
        return v;
    }

    public View setSelected(View view, int pos) {

        int len = day_string.size();
        if (len > pos) {
            if (day_string.get(pos).equals(curentDateString)) {
            } else {
                previousView = view;
            }
        }
        return view;
    }

    public void refreshDays() {
        // clear items
        items.clear();
        day_string.clear();
        Locale.setDefault(Locale.US);
        pmonth = (GregorianCalendar) month.clone();
        // month start day. ie; sun, mon, etc
        firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
        // finding number of weeks in current month.
        maxWeeknumber = month.getActualMaximum(Calendar.WEEK_OF_MONTH);
        // allocating maximum row number for the gridview.

//        mnthlength = (maxWeeknumber * 7);
        if (maxWeeknumber == 5) {
            mnthlength = (maxWeeknumber * 6) + 6;
        } else {
            mnthlength = (maxWeeknumber * 6) + 1;
        }
        maxP = getMaxP(); // previous month maximum day 31,30....
        calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
        /**
         * Calendar instance for getting a complete gridview including the three
         * month's (previous,current,next) dates.
         */
        pmonthmaxset = (GregorianCalendar) pmonth.clone();
        /**
         * setting the start date as previous month's required date.
         */
        pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

        /**
         * filling calendar gridview.
         */
        for (int n = 0; n < mnthlength; n++) {
            itemvalue = df.format(pmonthmaxset.getTime());
            pmonthmaxset.add(GregorianCalendar.DATE, 1);
            day_string.add(itemvalue);

        }
    }

    private int getMaxP() {
        int maxP;
        if (month.get(GregorianCalendar.MONTH) == month.getActualMinimum(GregorianCalendar.MONTH)) {
            pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            pmonth.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }
        maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        return maxP;
    }

    public void setEventView(View v, int pos, TextView txt) {
        try {
            if (AvailableDates.size() != 0) {
                for (int i = 0; i < AvailableDates.size(); i++) {
                    String avail_date = AvailableDates.get(i).getDate();
                    int len1 = day_string.size();
                    if (len1 > pos) {
                        if (day_string.get(pos).equals(avail_date)) {
                            // v.setBackgroundColor(Color.WHITE);
                            txt.setBackgroundColor(AvailableDates.get(i).getColorValue());
//                            txt.setBackgroundResource(R.mipmap.ic_circle_green);
                            txt.setTextColor(Color.WHITE);
                        }
                    }
                }
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

    }
}