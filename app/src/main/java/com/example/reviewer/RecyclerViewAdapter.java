package com.example.reviewer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sdsmdg.tastytoast.TastyToast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private  ArrayList<String> mImageUrls = new ArrayList<>();
    private  ArrayList<String> mName = new ArrayList<>();
    private  ArrayList<String> mDesc = new ArrayList<>();
    private  ArrayList<String> mVidId = new ArrayList<>();
    private  ArrayList<String> mImdb = new ArrayList<>();
    private  ArrayList<String> mTomato = new ArrayList<>();
    private  ArrayList<String> mStoryline = new ArrayList<>();
    private  ArrayList<String> mGenre = new ArrayList<>();
    private  ArrayList<String> mCast1 = new ArrayList<>();
    private  ArrayList<String> mCast2 = new ArrayList<>();
    private  ArrayList<String> mCast3 = new ArrayList<>();
    private  ArrayList<String> mCast4 = new ArrayList<>();
    private  ArrayList<String> mCast5 = new ArrayList<>();
    private  ArrayList<String> mCast6 = new ArrayList<>();
    private  ArrayList<String> mDirector = new ArrayList<>();
    private  ArrayList<String> mWriter = new ArrayList<>();
    private  ArrayList<String> mReleaseDate = new ArrayList<>();
    private  ArrayList<String> mCountryOrigin = new ArrayList<>();
    private  ArrayList<String> mLanguage = new ArrayList<>();
    private  ArrayList<String> mCname = new ArrayList<>();
    private  ArrayList<String> mCimageurls = new ArrayList<>();
    private  ArrayList<String> mCrating = new ArrayList<>();
    private  ArrayList<String> mCreview = new ArrayList<>();
    private  Context mContext;

    public RecyclerViewAdapter(ArrayList<String> mImageUrls, ArrayList<String> mName, ArrayList<String> mDesc, ArrayList<String> mVidId, ArrayList<String> mImdb, ArrayList<String> mTomato, ArrayList<String> mStoryline, ArrayList<String> mGenre, ArrayList<String> mCast1, ArrayList<String> mCast2, ArrayList<String> mCast3, ArrayList<String> mCast4, ArrayList<String> mCast5, ArrayList<String> mCast6, ArrayList<String> mDirector, ArrayList<String> mWriter, ArrayList<String> mReleaseDate, ArrayList<String> mCountryOrigin, ArrayList<String> mLanguage, ArrayList<String> mCname, ArrayList<String> mCimageurls, ArrayList<String> mCrating, ArrayList<String> mCreview, Context mContext) {
        this.mImageUrls = mImageUrls;
        this.mName = mName;
        this.mDesc = mDesc;
        this.mVidId = mVidId;
        this.mImdb = mImdb;
        this.mTomato = mTomato;
        this.mStoryline = mStoryline;
        this.mGenre = mGenre;
        this.mCast1 = mCast1;
        this.mCast2 = mCast2;
        this.mCast3 = mCast3;
        this.mCast4 = mCast4;
        this.mCast5 = mCast5;
        this.mCast6 = mCast6;
        this.mDirector = mDirector;
        this.mWriter = mWriter;
        this.mReleaseDate = mReleaseDate;
        this.mCountryOrigin = mCountryOrigin;
        this.mLanguage = mLanguage;
        this.mCname = mCname;
        this.mCimageurls = mCimageurls;
        this.mCrating = mCrating;
        this.mCreview = mCreview;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        Glide.with(mContext)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(holder.image);
        holder.name.setText(mName.get(position));
        holder.desc.setText(mDesc.get(position));
        holder.vidid.setText(mVidId.get(position));
        holder.imdb.setText(mImdb.get(position));
        holder.tomato.setText(mTomato.get(position));
        holder.storyline.setText(mStoryline.get(position));
        holder.genre.setText(mGenre.get(position));
        holder.cast1.setText(mCast1.get(position));
        holder.cast2.setText(mCast2.get(position));
        holder.cast3.setText(mCast3.get(position));
        holder.cast4.setText(mCast4.get(position));
        holder.cast5.setText(mCast5.get(position));
        holder.cast6.setText(mCast6.get(position));
        holder.director.setText(mDirector.get(position));
        holder.writers.setText(mWriter.get(position));
        holder.reldate.setText(mReleaseDate.get(position));
        holder.countryorg.setText(mCountryOrigin.get(position));
        holder.language.setText(mLanguage.get(position));
        holder.criticname.setText(mCname.get(position));
        holder.criticimage.setText(mCimageurls.get(position));
        holder.criticrating.setText(mCrating.get(position));
        holder.criticreview.setText(mCreview.get(position));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, Displaycontent.class);
                intent.putExtra("image_url", mImageUrls.get(position));
                intent.putExtra("image_name", mName.get(position));
                intent.putExtra("image_desc", mDesc.get(position));
                intent.putExtra("image_vidid", mVidId.get(position));
                intent.putExtra("image_imdb", mImdb.get(position));
                intent.putExtra("image_tomato", mTomato.get(position));
                intent.putExtra("image_storyline", mStoryline.get(position));
                intent.putExtra("image_genre", mGenre.get(position));
                intent.putExtra("image_cast1", mCast1.get(position));
                intent.putExtra("image_cast2", mCast2.get(position));
                intent.putExtra("image_cast3", mCast3.get(position));
                intent.putExtra("image_cast4", mCast4.get(position));
                intent.putExtra("image_cast5", mCast5.get(position));
                intent.putExtra("image_cast6", mCast6.get(position));
                intent.putExtra("image_director", mDirector.get(position));
                intent.putExtra("image_writer", mWriter.get(position));
                intent.putExtra("image_reldate", mReleaseDate.get(position));
                intent.putExtra("image_countryorigin", mCountryOrigin.get(position));
                intent.putExtra("image_language", mLanguage.get(position));
                intent.putExtra("image_cname", mCname.get(position));
                intent.putExtra("image_cimage", mCimageurls.get(position));
                intent.putExtra("image_crating", mCrating.get(position));
                intent.putExtra("image_creview", mCreview.get(position));


                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name,desc,vidid,imdb,tomato,storyline,genre,cast1,cast2,cast3,cast4,cast5,cast6,director,writers,reldate,countryorg,language,criticimage,criticname,criticreview,criticrating;



        public ViewHolder(@NonNull View itemView) {
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
