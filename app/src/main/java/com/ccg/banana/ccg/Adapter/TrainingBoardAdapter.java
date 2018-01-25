package com.ccg.banana.ccg.Adapter;

/**
 * Created by BananaApps on 6/1/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ccg.banana.ccg.Activity.Home;
import com.ccg.banana.ccg.Fragments.TrainingBoardList;
import com.ccg.banana.ccg.Fragments.TrainingPhotoList;
import com.ccg.banana.ccg.R;
import com.ccg.banana.ccg.session.Cache;
import com.ccg.banana.ccg.session.CatchValue;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;


public class TrainingBoardAdapter extends RecyclerView.Adapter<TrainingBoardAdapter.ShopListViewHolder> {
    private ArrayList<TrainingBoardList> hosDocLists;
    private Context context;
    private LayoutInflater inflater;
    private OnShopClickListener mOnShopClickListener;
    ArrayList<TrainingPhotoList> t;
    public TrainingBoardAdapter(Context context, ArrayList<TrainingBoardList> hosDocLists) {
        this.context = context;
        this.hosDocLists = hosDocLists;
        inflater = ((Activity) context).getLayoutInflater();
        mOnShopClickListener = new OnShopClickListener(context);
    }

    @Override
    public ShopListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
       // View view = inflater.inflate(R.layout.training_board_adapter, viewGroup, false);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.training_board_adapter, null);
        return new ShopListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopListViewHolder holder, final int position) {
        final TrainingBoardList hosDocList = hosDocLists.get(position);
        ShopListViewHolder shopListViewHolder = (ShopListViewHolder) holder;
        View view = shopListViewHolder.getItemView();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("Message_URL",hosDocList.getTraning_Image());
                editor.putString("Message_Title",hosDocList.getTraning_Title());
                editor.putString("Message_Post_DateTime_Format",hosDocList.getTraning_StartDate_Format());
                editor.putString("Traning_Day",hosDocList.getTraning_Day());
                editor.putString("Trainee_Name",hosDocList.getTraning_City());
                editor.putString("Traning_StartTime_Format",hosDocList.getTraning_StartTime_Format());
                editor.putString("Traning_ContactPersonname",hosDocList.getTraning_ContactPersonname());
                editor.putString("Traning_ContactPersonnumber",hosDocList.getTraning_ContactPersonnumber());
                editor.putString("Traning_Address1",hosDocList.getTraning_Address1());
                editor.putString("Traning_Description",hosDocList.getTraning_Description());
                editor.putString("Traning_Id",hosDocList.getTraning_Id());
                editor.putString("Register_Status",hosDocList.getRegister_Status());
                editor.putString("position",""+position);
                Gson gson = new Gson();
                String json = gson.toJson(hosDocLists); // myObject - instance of MyObject
                editor.putString("MyObject", json);
                editor.commit();

                Intent intent = new Intent(context, Home.class);
                Cache.putData(CatchValue.Operation, context,"TrainingBoard", Cache.CACHE_LOCATION_DISK);
                context.startActivity(intent);
            }
        });


        ImageView registerStatus = shopListViewHolder.getRegisterStatus();
        TextView txtItemName = shopListViewHolder.getTxtItemName();
        TextView txtRating = shopListViewHolder.getTxtRating();
        TextView txtBook = shopListViewHolder.getDate();
        TextView txtDescription = shopListViewHolder.getDescription();
        TextView txtTime = shopListViewHolder.getTime();
        TextView txtStartDate = shopListViewHolder.getStartDate();
        ImageView imgItem = shopListViewHolder.getImgItem();
   /*   Collections.sort(hosDocLists, new CustomComparator());
        Collections.reverse(hosDocLists);*/

        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "hel_bold.ttf");
        txtItemName.setTypeface(custom_font);

        custom_font = Typeface.createFromAsset(context.getAssets(), "hel_medium.ttf");
     //   txtItemName.setTypeface(custom_font);
        txtRating.setTypeface(custom_font);
        txtBook.setTypeface(custom_font);
        txtDescription.setTypeface(custom_font);
        txtTime.setTypeface(custom_font);
        txtStartDate.setTypeface(custom_font);

        if (txtBook != null) {
            if (!TextUtils.isEmpty(hosDocList.getTraning_Address1()) && !hosDocList.getTraning_Address1().equals("null")) {
                txtBook.setVisibility(View.VISIBLE);
                txtBook.setText(hosDocList.getTraning_Address1());
            } else {
                txtBook.setVisibility(View.INVISIBLE);
            }
        }

        if(txtTime != null)
        {
            if (!TextUtils.isEmpty(hosDocList.getTraning_StartTime_Format()) && !hosDocList.getTraning_StartTime_Format().equals("null")) {
                txtTime.setVisibility(View.VISIBLE);
                txtTime.setText(hosDocList.getTraning_StartTime_Format()+" - "+hosDocList.getTraning_EndTime_Format());
            } else {
                txtTime.setVisibility(View.GONE);
            }
        }

        if(txtStartDate != null)
        {
            if (!TextUtils.isEmpty(hosDocList.getTraning_StartDate_Format())) {
                txtStartDate.setVisibility(View.VISIBLE);
                txtStartDate.setText(hosDocList.getTraning_StartDate_Format());
            } else {
                txtStartDate.setVisibility(View.GONE);
            }
        }

        if (txtDescription != null) {
            if (!TextUtils.isEmpty(hosDocList.getTraning_Day()) && !hosDocList.getTraning_Day().equals("null")) {
                txtDescription.setVisibility(View.VISIBLE);
                txtDescription.setText(hosDocList.getTraning_Day());
            } else {
                txtDescription.setVisibility(View.GONE);
            }
        }
       if (txtItemName != null) {
            if (!TextUtils.isEmpty(hosDocList.getTraning_Title())&& !hosDocList.getTraning_Title().equals("null")) {
                txtItemName.setVisibility(View.VISIBLE);
                txtItemName.setText(hosDocList.getTraning_Title());
            } else {
                txtItemName.setVisibility(View.GONE);
            }
        }
       if (txtRating != null) {

            if (!TextUtils.isEmpty(hosDocList.getTraning_Category_Name())  && !hosDocList.getTraning_Category_Name().equals("null")) {
                txtRating.setVisibility(View.VISIBLE);
                //txtRating.setText(shopList.getDistance()+" miles");
                txtRating.setText(hosDocList.getTraning_Category_Name());
            } else {
                txtRating.setVisibility(View.GONE);
            }
        }
        if (imgItem != null) {
            String t = hosDocList.getTraning_Image();
            if (!TextUtils.isEmpty(t) && !t.equalsIgnoreCase("null")) {
                   new DownLoadImageTask(imgItem).execute(hosDocList.getTraning_Image());
            }
            else
                imgItem.setImageResource(R.mipmap.pic);
        }

        if (registerStatus != null) {
            String t = hosDocList.getRegister_Status();

            if (t.equalsIgnoreCase("1")) {
                registerStatus.setImageResource(R.mipmap.register_success);
                registerStatus.setVisibility(View.VISIBLE);
              }
            else{
                imgItem.setImageResource(R.mipmap.pic);
                registerStatus.setVisibility(View.GONE);
            }

        }

    }

    private class DownLoadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownLoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }
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
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return logo;
        }

        protected void onPostExecute(Bitmap result) {
            if(result!=null){
                imageView.setImageBitmap(result);
            }
            else{
               // imageView.setImageResource(R.drawable.noimage);
            }
        }
    }

    private static class OnShopClickListener implements View.OnClickListener {
        private Context context;

        public OnShopClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {

        }
    }

    public static class ShopListViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem,registerStatus;
        private TextView txtItemName;
        private TextView txtRating;
        private TextView description;
        private TextView book,fromToTimes,dateDisplay;


        private TextView Message_Type;


        public ShopListViewHolder(View itemView) {
            super(itemView);

        }

        public View getItemView() {
            return this.itemView;
        }

        public TextView getTxtRating() {
            if (txtRating == null) {
                txtRating = (TextView) itemView.findViewById(R.id.Content_Type);
            }
            return txtRating;
        }

        public TextView getTxtItemName() {
            if (txtItemName == null) {
                txtItemName = (TextView) itemView.findViewById(R.id.msgTitle);
            }
            return txtItemName;
        }

        public ImageView getRegisterStatus()
        {
            if(registerStatus == null)
                registerStatus = (ImageView)itemView.findViewById(R.id.registerStatus);
            return registerStatus;
        }


        public ImageView getImgItem() {
            if (imgItem == null) {
                imgItem = (ImageView) itemView.findViewById(R.id.n_logo);
            }
            return imgItem;
        }

        public TextView getDate()
        {
            if(book == null)
                book = (TextView)itemView.findViewById(R.id.date);
        return book;
        }



        public TextView getTime()
        {
            if(fromToTimes == null)
                fromToTimes = (TextView)itemView.findViewById(R.id.fromToTimes);
            return fromToTimes;
        }

        public TextView getStartDate()
        {
            if(dateDisplay == null)
                dateDisplay = (TextView)itemView.findViewById(R.id.dateDisplay);
            return dateDisplay;
        }

        public TextView getDescription()
        {
            if(description == null)
                description = (TextView)itemView.findViewById(R.id.description);
            return description;
        }
    }

    @Override
    public int getItemCount() {
            return hosDocLists.size();
    }

    private Bitmap bitmapConvert(String Image) {
        byte[] decodedString = Base64.decode(Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
    public class CustomComparator implements Comparator<TrainingBoardList> {
        @Override
        public int compare(TrainingBoardList shop, TrainingBoardList t1) {
            return shop.getTraning_StartDate_Format().compareTo(t1.getTraning_StartDate_Format());
        }
    }
}