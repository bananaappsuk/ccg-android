package com.ccg.banana.ccg.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;



import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import com.ccg.banana.ccg.R;

/**
 * Created by Dell1 on 08-Sep-17.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.BooldSampleHolders> {

    private List<HomeOrderObject> itemList;
    private Context context;
    List<HomeOrderObject> addConsumables;
    RelativeLayout relative;


    private List<HomeOrderObject> savedQtyList;
    public static List<String> savedItemSNoList;
    public HomeAdapter(Context context, List<HomeOrderObject> itemList) {
        this.itemList = itemList;
        this.context = context;
        savedQtyList=new ArrayList<>();
        savedItemSNoList=new ArrayList<>();
        addConsumables=new ArrayList<>();
    }

    @Override
    public BooldSampleHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_row, null);
        return new HomeAdapter.BooldSampleHolders(layoutView);
    }

    @Override
    public void onBindViewHolder(final BooldSampleHolders holder, final int position) {
//        holder.countryName.setText(itemList.get(position).getName());
        holder.test.setText(itemList.get(position).getTest());
        holder.countryName.setText(itemList.get(position).getCity());
        holder.quantity.setText(""+itemList.get(position).getQuantity());
        holder.date.setText(""+itemList.get(position).getDate());
        holder.day.setText(""+itemList.get(position).getDay());
        holder.loation.setText("  "+itemList.get(position).getLocation());

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
    }
}
