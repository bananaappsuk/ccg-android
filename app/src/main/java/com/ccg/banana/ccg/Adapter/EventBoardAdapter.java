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
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ccg.banana.ccg.Activity.Home;
import com.ccg.banana.ccg.Fragments.EventBoardList;
import com.ccg.banana.ccg.Fragments.FragmentB;
import com.ccg.banana.ccg.Fragments.JobsBoardList;
import com.ccg.banana.ccg.Fragments.MessageBoard;
import com.ccg.banana.ccg.Fragments.MessageBoardList;
import com.ccg.banana.ccg.Fragments.SafeGuard;
import com.ccg.banana.ccg.Fragments.TrainingBoardList;
import com.ccg.banana.ccg.session.Cache;
import com.ccg.banana.ccg.session.CatchValue;

import com.ccg.banana.ccg.R;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class EventBoardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<EventBoardList> hosDocLists;
    private Context context;
    private LayoutInflater inflater;
    private OnShopClickListener mOnShopClickListener;

    public EventBoardAdapter(Context context, ArrayList<EventBoardList> hosDocLists) {
        this.context = context;
        this.hosDocLists = hosDocLists;
        inflater = ((Activity) context).getLayoutInflater();
        mOnShopClickListener = new OnShopClickListener(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.event_board_adapter, viewGroup, false);
        return new ShopListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final EventBoardList hosDocList = hosDocLists.get(position);
        ShopListViewHolder shopListViewHolder = (ShopListViewHolder) holder;
        //  Toast.makeText(context,"view holder",Toast.LENGTH_LONG).show();
        View view = shopListViewHolder.getItemView();
        // view.setTag(R.id.movie, shop);
        // view.setTag(R.id.list, shop);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   String t = hosDocList.getMessage_Type();
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
              //  Toast.makeText(context, "TEST  "+hosDocList.getTrainingPhotoLists().get(0).getTraning_Photo(), Toast.LENGTH_LONG).show();
                Gson gson = new Gson();
            //    String json2 = gson.toJson(""+hosDocList.getTrainingPhotoLists());
                //json2 = gson.toJson(hosDocList.getTrainingPhotoLists());
            //    editor.putString("All", json2);
             //   Gson gson = new Gson();
             //   Toast.makeText(context, "TEST  "+hosDocList.getTrainingPhotoLists().get(position).getTraning_Photo(), Toast.LENGTH_LONG).show();
                String json = gson.toJson(hosDocLists.get(position)); // myObject - instance of MyObject
                editor.putString("MyObject", json);

  /*              Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("LIST", hosDocList.getTrainingPhotoLists());*/
                //prefsEditor.commit();
       /*         JSONArray jsonArray = new JSONArray();
                jsonArray.put(1);
                jsonArray.put(2);
                editor.putString("key", jsonArray.toString());*/

          /*      editor.putString("Content_Id",hosDocList.getContent_Id());
                editor.putString("Content_Type",hosDocList.getContent_Type());
                editor.putString("Mesage_Website_Link",hosDocList.getMesage_Website_Link());
                editor.putString("Message_Description",hosDocList.getMessage_Description());
                editor.putString("Message_Id",hosDocList.getMessage_Id());
                editor.putString("Message_Post_DateTime",hosDocList.getMessage_Post_DateTime());

                editor.putString("Message_Type",hosDocList.getMessage_Type());
                editor.putString("Status",hosDocList.getStatus());
                */
                editor.commit();
                Intent intent = new Intent(context, Home.class);
                Cache.putData(CatchValue.Operation, context,"EventApply", Cache.CACHE_LOCATION_DISK);
                context.startActivity(intent);


        /*        if (t.equalsIgnoreCase("1")) {
                    Intent intent = new Intent(context, Home.class);
                    Cache.putData(CatchValue.Operation, context,"ShowImage", Cache.CACHE_LOCATION_DISK);
                    context.startActivity(intent);
                } else
                if (t.equalsIgnoreCase("2")) {
                    Intent intent = new Intent(context, Home.class);
                    Cache.putData(CatchValue.Operation, context,"ShowImage", Cache.CACHE_LOCATION_DISK);
                    context.startActivity(intent);
                }
                else
                    Toast.makeText(context, "TEST  ", Toast.LENGTH_LONG).show();*/




            }
        });

        ImageView imgItem = shopListViewHolder.getImgItem();
        ImageView registerStatus = shopListViewHolder.getRegisterStatus();
        TextView txtItemName = shopListViewHolder.getTxtItemName();
        TextView txtRating = shopListViewHolder.getTxtRating();
        TextView txtBook = shopListViewHolder.getDate();
        TextView txtDescription = shopListViewHolder.getDescription();
        TextView txtTime = shopListViewHolder.getTime();
        TextView txtStartDate = shopListViewHolder.getStartDate();

        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "hel_bold.ttf");
        txtItemName.setTypeface(custom_font);

        custom_font = Typeface.createFromAsset(context.getAssets(), "hel_medium.ttf");
     //   txtItemName.setTypeface(custom_font);
        txtRating.setTypeface(custom_font);
        txtBook.setTypeface(custom_font);
        txtDescription.setTypeface(custom_font);
        txtTime.setTypeface(custom_font);
        txtStartDate.setTypeface(custom_font);

        txtBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(context, "hosDocLists.get(position).getHosp_name())  "+hosDocList.getFee(), Toast.LENGTH_LONG).show();
           /*     Intent i = new Intent(context, LandingPage.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);*/
            }
        });
        if (txtBook != null) {
            if (!TextUtils.isEmpty(hosDocList.getTraning_Address1())&& !hosDocList.getTraning_City().equals("null")) {
                txtBook.setVisibility(View.VISIBLE);
                txtBook.setText(hosDocList.getTraning_City());
            } else {
                txtBook.setVisibility(View.INVISIBLE);
            }
        }

        if(txtTime != null)
        {
           // txtTime.setText("End date");
            if (!TextUtils.isEmpty(hosDocList.getTraning_StartTime_Format()) && !hosDocList.getTraning_StartTime_Format().equals("null") && !hosDocList.getTraning_EndTime_Format().equals("null") ) {
                txtTime.setVisibility(View.VISIBLE);
                txtTime.setText(hosDocList.getTraning_StartTime_Format()+" - "+hosDocList.getTraning_EndTime_Format());
            } else {
                txtTime.setVisibility(View.GONE);
            }
        }

        if(txtStartDate != null)
        {
            if (!TextUtils.isEmpty(hosDocList.getTraning_StartDate_Format()) && !hosDocList.getTraning_StartDate_Format().equals("null")) {
                txtStartDate.setVisibility(View.VISIBLE);
                txtStartDate.setText(hosDocList.getTraning_StartDate_Format());
            } else {
                txtStartDate.setVisibility(View.GONE);
            }
        }

        if (txtDescription != null) {
            if (!TextUtils.isEmpty(hosDocList.getTraning_Day())&& !hosDocList.getTraning_Day().equals("null")) {
                txtDescription.setVisibility(View.VISIBLE);
                txtDescription.setText(hosDocList.getTraning_Day());
            } else {
                txtDescription.setVisibility(View.INVISIBLE);
            }
        }

     //   Toast.makeText(context, "hosDocLists.get(position).getHosp_name())  "+hosDocList.getFee(), Toast.LENGTH_LONG).show();
       if (txtItemName != null) {
            if (!TextUtils.isEmpty(hosDocList.getTraning_Title()) && !hosDocList.getTraning_Title().equals("null")) {
                txtItemName.setVisibility(View.VISIBLE);
                txtItemName.setText(hosDocList.getTraning_Title());
            } else {
                txtItemName.setVisibility(View.GONE);
            }
        }
       if (txtRating != null) {

            if (!TextUtils.isEmpty(hosDocList.getTraning_Category_Name()) && !hosDocList.getTraning_Category_Name().equals("null")) {
                txtRating.setVisibility(View.VISIBLE);
                //txtRating.setText(shopList.getDistance()+" miles");
                txtRating.setText(hosDocList.getTraning_Category_Name());
            } else {
                txtRating.setVisibility(View.GONE);
            }
        }
        if (imgItem != null) {
            String t = hosDocList.getTraning_Image();

            if (!t.equalsIgnoreCase(null)) {
                   new DownLoadImageTask(imgItem).execute(hosDocList.getTraning_Image());
              /*  imgItem.setImageResource(R.mipmap.pic);

            } else
            if (t.equalsIgnoreCase("2")) {
                imgItem.setImageResource(R.mipmap.video);*/
            }
            else
                imgItem.setImageResource(R.mipmap.pic);
        }

        if (registerStatus != null) {
            String t = hosDocList.getRegister_Status();

            if (t.equalsIgnoreCase("1")) {
                registerStatus.setImageResource(R.mipmap.applied);
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

//                    ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(myUri, "r");
//                    FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
//                    logo = BitmapFactory.decodeFileDescriptor(fileDescriptor);
//                    parcelFileDescriptor.close();


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

        //    Toast.makeText(context, "Item clicked ", Toast.LENGTH_LONG).show();
            /*if (context == null) return;
            Shop shop = (Shop) v.getTag(R.id.movie);
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra("ItemName", shop.getName());
            context.startActivity(intent);*/
        }
    }

    private static class ShopListViewHolder extends RecyclerView.ViewHolder {
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
      /*  Log.e("hosDocLists","   "+hosDocLists);
       if(!hosDocLists.equals(null))
        {*/
            return hosDocLists.size();
 /*       }
        else
            return 0;*/
    }

    private Bitmap bitmapConvert(String Image) {
        byte[] decodedString = Base64.decode(Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

}