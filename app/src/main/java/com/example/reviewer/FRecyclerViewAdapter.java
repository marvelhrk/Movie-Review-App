package com.example.reviewer;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FRecyclerViewAdapter extends RecyclerView.Adapter<FRecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<ProductsModel> userArrayList;

    public FRecyclerViewAdapter(Context context, ArrayList<ProductsModel> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public FRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FRecyclerViewAdapter.MyViewHolder holder, int position) {
        ProductsModel user = userArrayList.get(position);

        Glide.with(context)
                .asBitmap()
                .load(user.mImageUrls)
                .into(holder.image);
        holder.name.setText(user.mNames);
        holder.desc.setText(user.mDesc);
        holder.vidid.setText(user.mVidId);
        holder.imdb.setText(user.mImdb);
        holder.tomato.setText(user.mTomato);
        holder.storyline.setText(user.mStoryline);
        holder.genre.setText(user.mGenre);
        holder.cast1.setText(user.mCast1);
        holder.cast2.setText(user.mCast2);
        holder.cast3.setText(user.mCast3);
        holder.cast4.setText(user.mCast4);
        holder.cast5.setText(user.mCast5);
        holder.cast6.setText(user.mCast6);
        holder.director.setText(user.mDirector);
        holder.writers.setText(user.mWriter);
        holder.reldate.setText(user.mReleaseDate);
        holder.countryorg.setText(user.mCountryOrigin);
        holder.language.setText(user.mLanguage);
        holder.criticname.setText(user.mCname);
        holder.criticimage.setText(user.mCimageurls);
        holder.criticrating.setText(user.mCrating);
        holder.criticreview.setText(user.mCreview);


        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, Displaycontent.class);
                intent.putExtra("image_url",user.mImageUrls );
                intent.putExtra("image_name", user.mNames);
                intent.putExtra("image_desc",user.mDesc );
                intent.putExtra("image_vidid", user.mVidId);
                intent.putExtra("image_imdb", user.mImdb);
                intent.putExtra("image_tomato", user.mTomato);
                intent.putExtra("image_storyline",user.mStoryline );
                intent.putExtra("image_genre",user.mGenre );
                intent.putExtra("image_cast1",user.mCast1 );
                intent.putExtra("image_cast2", user.mCast2);
                intent.putExtra("image_cast3", user.mCast3);
                intent.putExtra("image_cast4", user.mCast4);
                intent.putExtra("image_cast5", user.mCast5);
                intent.putExtra("image_cast6", user.mCast6);
                intent.putExtra("image_director", user.mDirector);
                intent.putExtra("image_writer", user.mWriter);
                intent.putExtra("image_reldate", user.mReleaseDate);
                intent.putExtra("image_countryorigin", user.mCountryOrigin);
                intent.putExtra("image_language", user.mLanguage);
                intent.putExtra("image_cname", user.mCname);
                intent.putExtra("image_cimage", user.mCimageurls);
                intent.putExtra("image_crating", user.mCrating);
                intent.putExtra("image_creview", user.mCreview);


                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name,desc,vidid,imdb,tomato,storyline,genre,cast1,cast2,cast3,cast4,cast5,cast6,director,writers,reldate,countryorg,language,criticimage,criticname,criticreview,criticrating;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view);
            name = itemView.findViewById(R.id.name);
            desc = itemView.findViewById(R.id.desc);
            vidid = itemView.findViewById(R.id.vidid);
            imdb = itemView.findViewById(R.id.imdb);
            tomato = itemView.findViewById(R.id.tomato);
            storyline = itemView.findViewById(R.id.storyline);
            genre = itemView.findViewById(R.id.genres);
            cast1 = itemView.findViewById(R.id.cast1);
            cast2 = itemView.findViewById(R.id.cast2);
            cast3 = itemView.findViewById(R.id.cast3);
            cast4 = itemView.findViewById(R.id.cast4);
            cast5 = itemView.findViewById(R.id.cast5);
            cast6 = itemView.findViewById(R.id.cast6);
            director = itemView.findViewById(R.id.director);
            writers = itemView.findViewById(R.id.writer);
            reldate = itemView.findViewById(R.id.release);
            countryorg = itemView.findViewById(R.id.country);
            language = itemView.findViewById(R.id.language);
            criticname = itemView.findViewById(R.id.criticname);
            criticimage = itemView.findViewById(R.id.criticimage);
            criticreview = itemView.findViewById(R.id.review);
            criticrating = itemView.findViewById(R.id.criticrating);
        }
    }
}
