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

import com.ccg.banana.ccg.Activity.Home;
import com.ccg.banana.ccg.Fragments.MessageBoardList;
import com.ccg.banana.ccg.R;
import com.ccg.banana.ccg.session.Cache;
import com.ccg.banana.ccg.session.CatchValue;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MessageBoardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<MessageBoardList> hosDocLists;
    private Context context;
    private LayoutInflater inflater;
    private OnShopClickListener mOnShopClickListener;

    public MessageBoardAdapter(Context context, ArrayList<MessageBoardList> hosDocLists) {
        this.context = context;
        this.hosDocLists = hosDocLists;
        inflater = ((Activity) context).getLayoutInflater();
        mOnShopClickListener = new OnShopClickListener(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.message_board_adapter, viewGroup, false);
        return new ShopListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MessageBoardList hosDocList = hosDocLists.get(position);
        ShopListViewHolder shopListViewHolder = (ShopListViewHolder) holder;
        View view = shopListViewHolder.getItemView();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, "TEST  ", Toast.LENGTH_LONG).show();
                String t = hosDocList.getMessage_Type();
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("Content_Id",hosDocList.getContent_Id());
                editor.putString("Content_Type",hosDocList.getContent_Type());
                editor.putString("Mesage_Website_Link",hosDocList.getMesage_Website_Link());
                editor.putString("Message_Description",hosDocList.getMessage_Description());
                editor.putString("Message_Id",hosDocList.getMessage_Id());
                editor.putString("Message_Post_DateTime",hosDocList.getMessage_Post_DateTime());
                editor.putString("Message_Post_DateTime_Format",hosDocList.getMessage_Post_DateTime_Format());
                editor.putString("Message_Title",hosDocList.getMessage_Title());
                editor.putString("Message_Type",hosDocList.getMessage_Type());
                editor.putString("Message_URL",hosDocList.getMessage_URL());
                editor.putString("Status",hosDocList.getStatus());
                editor.putString("Trainee_Name",hosDocList.getTrainee_Name());
                editor.commit();

                sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                editor = sharedPrefs.edit();
                Gson gson = new Gson();

                String json2 = gson.toJson(hosDocList.getTrainingPhotoLists());
                editor.putString("CommentsListDisplay", json2);
                editor.commit();

//Log.e("URL ",""+hosDocList.getMessage_URL());
                if (t.equalsIgnoreCase("1")) {
                    Intent intent = new Intent(context, Home.class);
                    Cache.putData(CatchValue.Operation, context,"ShowImage", Cache.CACHE_LOCATION_DISK);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);

                } else
                if (t.equalsIgnoreCase("2")) {
                    Intent intent = new Intent(context, Home.class);
                    Cache.putData(CatchValue.Operation, context,"ShowImage", Cache.CACHE_LOCATION_DISK);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(context, Home.class);
                    Cache.putData(CatchValue.Operation, context,"ShowImage", Cache.CACHE_LOCATION_DISK);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
            }
        });

        ImageView imgItem = shopListViewHolder.getImgItem();
        TextView txtItemName = shopListViewHolder.getTxtItemName();
        TextView txtRating = shopListViewHolder.getTxtRating();
        TextView txtBook = shopListViewHolder.getDate();
        TextView txtDescription = shopListViewHolder.getDescription();
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "Quicksand-Bold.ttf");
        txtItemName.setTypeface(custom_font);
        custom_font = Typeface.createFromAsset(context.getAssets(), "Quicksand-Regular.ttf");
        txtRating.setTypeface(custom_font);
        txtBook.setTypeface(custom_font);
        txtDescription.setTypeface(custom_font);

     // Collections.sort(hosDocLists, new CustomComparator());
    /*    Collections.reverse(hosDocLists);*/

        txtBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if (txtBook != null) {

            if (!TextUtils.isEmpty(hosDocList.getMessage_Post_DateTime_Format()) && !hosDocList.getMessage_Post_DateTime_Format().equals("null")) {
                txtBook.setVisibility(View.VISIBLE);
                txtBook.setText(hosDocList.getMessage_Post_DateTime_Format());
            } else {
                txtBook.setVisibility(View.GONE);
            }
        }

        if (txtDescription != null) {
            if (!TextUtils.isEmpty(hosDocList.getMessage_Description()) && !hosDocList.getMessage_Description().equals("null") ) {
                String s = hosDocList.getStatus();
                if(!s.equalsIgnoreCase("true"))
                    txtDescription.setTypeface(null, Typeface.BOLD);
                /* else
                    txtDescription.setTypeface(null, Typeface.NORMAL);*/

                txtDescription.setVisibility(View.VISIBLE);
                txtDescription.setText(hosDocList.getMessage_Description());
            } else {
                txtDescription.setVisibility(View.GONE);
            }
        }
       if (txtItemName != null) {
            if (!TextUtils.isEmpty(hosDocList.getMessage_Id()) && !hosDocList.getMessage_Title().equals("null")) {
                txtItemName.setVisibility(View.VISIBLE);
                txtItemName.setText(hosDocList.getMessage_Title());
            } else {
                txtItemName.setVisibility(View.GONE);
            }
        }
       if (txtRating != null ) {
            if (!TextUtils.isEmpty(hosDocList.getMessage_Title()) && !hosDocList.getContent_Type().equals("null")) {
                txtRating.setVisibility(View.VISIBLE);
                txtRating.setText(hosDocList.getContent_Type());
            } else {
                txtRating.setVisibility(View.GONE);
            }
        }
        if (imgItem != null) {
            String t = hosDocList.getMessage_Type().toString().trim();
            if (t.equalsIgnoreCase("1")) {
                imgItem.setImageResource(R.mipmap.pic);
              //  new DownLoadImageTask(imgItem).execute(hosDocList.getHosp_Image());
            } else
            if (t.equalsIgnoreCase("2")) {
                imgItem.setImageResource(R.mipmap.video);
            }
            else
                imgItem.setImageResource(R.mipmap.text);
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

    private static class ShopListViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem;
        private TextView txtItemName;
        private TextView txtRating;
        private TextView description;
        private TextView book;

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

    public class CustomComparator implements Comparator<MessageBoardList> {
        @Override
        public int compare(MessageBoardList shop, MessageBoardList t1) {
            return shop.getMessage_Post_DateTime_Format().compareTo(t1.getMessage_Post_DateTime_Format());
        }
    }

}