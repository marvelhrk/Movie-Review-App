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

public class RecyclerViewAdapterMovies extends RecyclerView.Adapter<RecyclerViewAdapterMovies.MoviesViewHolder> {
    private static final String TAG = "RecyclerViewAdapterMovies";

    private ArrayList<String> movImageUrls = new ArrayList<>();
    private  ArrayList<String> movName = new ArrayList<>();
    private  ArrayList<String> movDesc = new ArrayList<>();
    private  ArrayList<String> movVidId = new ArrayList<>();
    private  ArrayList<String> movImdb = new ArrayList<>();
    private  ArrayList<String> movTomato = new ArrayList<>();
    private  ArrayList<String> movStoryline = new ArrayList<>();
    private  ArrayList<String> movGenre = new ArrayList<>();
    private  ArrayList<String> movCast1 = new ArrayList<>();
    private  ArrayList<String> movCast2 = new ArrayList<>();
    private  ArrayList<String> movCast3 = new ArrayList<>();
    private  ArrayList<String> movCast4 = new ArrayList<>();
    private  ArrayList<String> movCast5 = new ArrayList<>();
    private  ArrayList<String> movCast6 = new ArrayList<>();
    private  ArrayList<String> movDirector = new ArrayList<>();
    private  ArrayList<String> movWriter = new ArrayList<>();
    private  ArrayList<String> movReleaseDate = new ArrayList<>();
    private  ArrayList<String> movCountryOrigin = new ArrayList<>();
    private  ArrayList<String> movLanguage = new ArrayList<>();
    private Context movContext;

    public RecyclerViewAdapterMovies(ArrayList<String> movImageUrls, ArrayList<String> movName, ArrayList<String> movDesc, ArrayList<String> movVidId, ArrayList<String> movImdb, ArrayList<String> movTomato, ArrayList<String> movStoryline, ArrayList<String> movGenre, ArrayList<String> movCast1, ArrayList<String> movCast2, ArrayList<String> movCast3, ArrayList<String> movCast4, ArrayList<String> movCast5, ArrayList<String> movCast6, ArrayList<String> movDirector, ArrayList<String> movWriter, ArrayList<String> movReleaseDate, ArrayList<String> movCountryOrigin, ArrayList<String> movLanguage, Context movContext) {
        this.movImageUrls = movImageUrls;
        this.movName = movName;
        this.movDesc = movDesc;
        this.movVidId = movVidId;
        this.movImdb = movImdb;
        this.movTomato = movTomato;
        this.movStoryline = movStoryline;
        this.movGenre = movGenre;
        this.movCast1 = movCast1;
        this.movCast2 = movCast2;
        this.movCast3 = movCast3;
        this.movCast4 = movCast4;
        this.movCast5 = movCast5;
        this.movCast6 = movCast6;
        this.movDirector = movDirector;
        this.movWriter = movWriter;
        this.movReleaseDate = movReleaseDate;
        this.movCountryOrigin = movCountryOrigin;
        this.movLanguage = movLanguage;
        this.movContext = movContext;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        Glide.with(movContext)
                .asBitmap()
                .load(movImageUrls.get(position))
                .into(holder.image);
        holder.name.setText(movName.get(position));
        holder.desc.setText(movDesc.get(position));
        holder.vidid.setText(movVidId.get(position));
        holder.imdb.setText(movImdb.get(position));
        holder.tomato.setText(movTomato.get(position));
        holder.storyline.setText(movStoryline.get(position));
        holder.genre.setText(movGenre.get(position));
        holder.cast1.setText(movCast1.get(position));
        holder.cast2.setText(movCast2.get(position));
        holder.cast3.setText(movCast3.get(position));
        holder.cast4.setText(movCast4.get(position));
        holder.cast5.setText(movCast5.get(position));
        holder.cast6.setText(movCast6.get(position));
        holder.director.setText(movDirector.get(position));
        holder.writers.setText(movWriter.get(position));
        holder.reldate.setText(movReleaseDate.get(position));
        holder.countryorg.setText(movCountryOrigin.get(position));
        holder.language.setText(movLanguage.get(position));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(movContext, Displaycontent.class);
                intent.putExtra("image_url", movImageUrls.get(position));
                intent.putExtra("image_name", movName.get(position));
                intent.putExtra("image_desc", movDesc.get(position));
                intent.putExtra("image_vidid", movVidId.get(position));
                intent.putExtra("image_imdb", movImdb.get(position));
                intent.putExtra("image_tomato", movTomato.get(position));
                intent.putExtra("image_storyline", movStoryline.get(position));
                intent.putExtra("image_genre", movGenre.get(position));
                intent.putExtra("image_cast1", movCast1.get(position));
                intent.putExtra("image_cast2", movCast2.get(position));
                intent.putExtra("image_cast3", movCast3.get(position));
                intent.putExtra("image_cast4", movCast4.get(position));
                intent.putExtra("image_cast5", movCast5.get(position));
                intent.putExtra("image_cast6", movCast6.get(position));
                intent.putExtra("image_director", movDirector.get(position));
                intent.putExtra("image_writer", movWriter.get(position));
                intent.putExtra("image_reldate", movReleaseDate.get(position));
                intent.putExtra("image_countryorigin", movCountryOrigin.get(position));
                intent.putExtra("image_language", movLanguage.get(position));


                movContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name,desc,vidid,imdb,tomato,storyline,genre,cast1,cast2,cast3,cast4,cast5,cast6,director,writers,reldate,countryorg,language;

        public MoviesViewHolder(@NonNull View itemView) {
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
        }
    }
}
