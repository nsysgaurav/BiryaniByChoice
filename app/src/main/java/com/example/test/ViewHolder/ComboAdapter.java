package com.example.test.ViewHolder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Model.FoodModel;
import com.example.test.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ComboAdapter extends RecyclerView.Adapter<ComboAdapter.ViewHolder> {
ArrayList<FoodModel> combos;

    public ComboAdapter(ArrayList<FoodModel> combolist) {
        combos=combolist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.combocards,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.comboname.setText(combos.get(position).getFoodName());
        holder.description.setText(combos.get(position).getFoodCat());
        holder.price.setText(String.valueOf(combos.get(position).getFoodPrice())+" \u20B9");
        try {
            holder.comboimage.setImageBitmap(BitmapFactory.decodeByteArray(combos.get(position).getImage(),0,combos.get(position).getImage().length));
        }catch (Exception e){
        }


        //new DownlordImage(holder.comboimage).execute(combos.get(position).getFood_imag_url());
    }

    @Override
    public int getItemCount() {
        return combos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView comboname , price,description;
        ImageView comboimage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comboname =itemView.findViewById(R.id.comboname);
            comboimage = itemView.findViewById(R.id.comboimage);
            price= itemView.findViewById(R.id.Cprice);
            description =itemView.findViewById(R.id.Cdescription);
        }
    }
    public class DownlordImage extends AsyncTask<String,Void, Bitmap> {
        ImageView img;

        public DownlordImage(ImageView img) {
            this.img = img;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urlLoad= urls[0];
            Bitmap bitmap =null;
            try {
                URL url=new URL(urlLoad);
                InputStream stream=url.openStream();
                bitmap= BitmapFactory.decodeStream(stream);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            img.setImageBitmap(bitmap);
        }
    }
}
