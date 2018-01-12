package com.ccg.banana.ccg.Fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccg.banana.ccg.Activity.Home;
import com.ccg.banana.ccg.Adapter.HomeOrderObject;
import com.ccg.banana.ccg.R;
import com.ccg.banana.ccg.session.Cache;
import com.ccg.banana.ccg.session.CatchValue;

import java.util.List;

/**
 * Created by Dell1 on 08-Sep-17.
 */

public class MybookingAdapter extends RecyclerView.Adapter<MybookingAdapter.BooldSampleHolders> {

    private List<MyBookingList> itemList;
    private Context context;
    List<MyBookingList> addConsumables;
    RelativeLayout relative;


    private List<HomeOrderObject> savedQtyList;
    public static List<String> savedItemSNoList;
    public MybookingAdapter(Context context, List<MyBookingList> itemList) {
        this.itemList = itemList;
        this.context = context;

    }

    @Override
    public BooldSampleHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mybooking_row, null);
        return new BooldSampleHolders(layoutView);
    }

    @Override
    public void onBindViewHolder(final BooldSampleHolders holder, final int position) {
        final MyBookingList hospitalList = itemList.get(position);
        BooldSampleHolders shopListViewHolder = (BooldSampleHolders) holder;
        //  Toast.makeText(context,"view holder",Toast.LENGTH_LONG).show();
        View view = shopListViewHolder.getItemView();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //   Toast.makeText(context, "Item clicked "+hospitalList.getModule_Type(), Toast.LENGTH_LONG).show();
                Cache.putData(CatchValue.ModuleType,context,""+hospitalList.getModule_Type(),Cache.CACHE_LOCATION_DISK);
                Cache.putData(CatchValue.ModuleId,context,""+hospitalList.getModule_ID(),Cache.CACHE_LOCATION_DISK);
             /*   Intent i = new Intent(context, LandingPage.class);
                //Cache.putData(CatchValue.USER_NAME, getApplicationContext(), un, Cache.CACHE_LOCATION_DISK);
                Cache.putData(CatchValue.Operation,context,"HospitalAvailbleDoctors",Cache.CACHE_LOCATION_DISK);
                Cache.putData(CatchValue.Hosp_Name,context,hospitalList.getHospital_Name(),Cache.CACHE_LOCATION_DISK);
                Cache.putData(CatchValue.Hosp_Image,context,hospitalList.getHosp_Image(),Cache.CACHE_LOCATION_DISK);
                Cache.putData(CatchValue.Hospital_Type_Name,context,hospitalList.getHospital_Type_Name(),Cache.CACHE_LOCATION_DISK);
                Cache.putData(CatchValue.Hosp_Branch_Id,context,hospitalList.getHosp_Bran_id(),Cache.CACHE_LOCATION_DISK);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);*/


                Intent intent = new Intent(context, Home.class);
                Cache.putData(CatchValue.Operation, context,"MyBookingBoard", Cache.CACHE_LOCATION_DISK);
                context.startActivity(intent);


            }
        });

        if(TextUtils.isEmpty(itemList.get(position).getModule_Title()) || itemList.get(position).getModule_Title().equalsIgnoreCase("null"))
            holder.countryName.setText("");
           else
            holder.countryName.setText(itemList.get(position).getModule_Title());

        String s = itemList.get(position).getModule_Start_Date();
        s= s.substring(0,2);

        if(TextUtils.isEmpty(s) || s.equalsIgnoreCase("null"))
            holder.date.setText("");
        else
            holder.date.setText(""+s);

        if(TextUtils.isEmpty(itemList.get(position).getModule_Start_Day()) || itemList.get(position).getModule_Start_Day().equalsIgnoreCase("null"))
            holder.day.setText("");
        else
            holder.day.setText(""+itemList.get(position).getModule_Start_Day());

        s = itemList.get(position).getModule_Type();
        if(s.equalsIgnoreCase("1"))
        holder.test.setText("Event Module");
        else
        if(s.equalsIgnoreCase("2"))
            holder.test.setText("Job Module");
        else
            holder.test.setText("Traning Module");

/*        Log.e("Start_Time","   "+itemList.get(position).getModule_Start_Time());
        Log.e("End_Time","   "+itemList.get(position).getModule_End_Time());
        Log.e("l_Time","   "+itemList.get(position).getModule_End_Time().length());*/
        if(!TextUtils.isEmpty(itemList.get(position).getModule_Start_Time()) && !TextUtils.isEmpty(itemList.get(position).getModule_End_Time()) && itemList.get(position).getModule_Start_Time().length() !=4)
           holder.quantity.setText(itemList.get(position).getModule_Start_Time()+" - "+itemList.get(position).getModule_End_Time());
        else
            holder.quantity.setText("");

     //   holder.loation.setText("  "+itemList.get(position).getModule_City());

        if(itemList.get(position).getModule_City().equalsIgnoreCase("null"))
        {
            holder.loation.setVisibility(View.GONE);

        }
        else
            holder.loation.setText("  "+itemList.get(position).getModule_City());
    /*    holder.countryName.setText(itemList.get(position).getCity());

        */

      //  holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());


    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class BooldSampleHolders extends RecyclerView.ViewHolder{
        public TextView countryName;
        public TextView test;

        public TextView quantity,date,day,loation;


        String value;
        public BooldSampleHolders(View itemView) {
            super(itemView);

            countryName = (TextView) itemView.findViewById(R.id.city);
            test = (TextView)itemView.findViewById(R.id.test);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            date = (TextView) itemView.findViewById(R.id.date);
            day = (TextView) itemView.findViewById(R.id.day);
            loation = (TextView) itemView.findViewById(R.id.loation);
          //  countryPhoto = (ImageView) itemView.findViewById(R.id.photo);

          //  relative= (RelativeLayout)itemView.findViewById(R.id.relative);
     /*       countryPhoto = (CheckedTextView) itemView.findViewById(R.id.simpleCheckedTextView);

            countryPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
*/
        }

        public View getItemView() {
            return this.itemView;
        }

        public TextView getCountryName() {
            if (countryName == null) {
                countryName = (TextView) itemView.findViewById(R.id.city);
            }
            return countryName;
        }

    }
}
