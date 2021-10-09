
package com.example.reviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class Displaycontent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.blackish));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaycontent);
        getIncomingIntent();
    }
    private void getIncomingIntent(){

        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("image_name")){


            String imageUrl = getIntent().getStringExtra("image_url");
            String imageName = getIntent().getStringExtra("image_name");
            String imageDesc = getIntent().getStringExtra(("image_desc"));
            String imageVidid = getIntent().getStringExtra(("image_vidid"));
            String imageImdb = getIntent().getStringExtra(("image_imdb"));
            String imageTomato = getIntent().getStringExtra(("image_tomato"));
            String imageStoryline = getIntent().getStringExtra(("image_storyline"));
            String imagegenre = getIntent().getStringExtra(("image_genre"));
            String imagecast1 = getIntent().getStringExtra(("image_cast1"));
            String imagecast2 = getIntent().getStringExtra(("image_cast2"));
            String imagecast3 = getIntent().getStringExtra(("image_cast3"));
            String imagecast4 = getIntent().getStringExtra(("image_cast4"));
            String imagecast5 = getIntent().getStringExtra(("image_cast5"));
            String imagecast6 = getIntent().getStringExtra(("image_cast6"));
            String imagedirector = getIntent().getStringExtra(("image_director"));
            String imagewriter = getIntent().getStringExtra(("image_writer"));
            String imagereldate = getIntent().getStringExtra(("image_reldate"));
            String imagecountry = getIntent().getStringExtra(("image_countryorigin"));
            String imagelanguage = getIntent().getStringExtra(("image_language"));
            String imagecname = getIntent().getStringExtra(("image_cname"));
            String imagecimage = getIntent().getStringExtra(("image_cimage"));
            String imagecrating = getIntent().getStringExtra(("image_crating"));
            String imagecreview = getIntent().getStringExtra(("image_creview"));

            setImage(imageUrl, imageName ,imageDesc,imageVidid,imageImdb,imageTomato,imageStoryline,imagegenre,imagecast1,imagecast2,imagecast3,imagecast4,imagecast5,imagecast6,imagedirector,imagewriter,imagereldate,imagecountry,imagelanguage,imagecname,imagecimage,imagecrating,imagecreview);
        }
    }

    private void setImage(String imageUrl, String imageName,String imageDesc,String imageVidid,String imageImdb,String imageTomato,String imageStoryline,String imagegenre,String imagecast1,String imagecast2,String imagecast3,String imagecast4,String imagecast5,String imagecast6,String imagedirector,String imagewriter,String imagereldate,String imagecountry,String imagelanguage,String imagecname,String imagecimage,String imagecrating,String imagecreview){

        ImageView image = findViewById(R.id.dimageView);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);

        TextView name = findViewById(R.id.dname);
        name.setText(imageName);

        TextView desc = findViewById(R.id.ddesc);
        desc.setText(imageDesc);

        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = imageVidid;
                youTubePlayer.loadVideo(videoId, 0);
            }
        });

        TextView star = findViewById(R.id.star);
        star.setText(imageImdb);

        TextView tomato = findViewById(R.id.tomato);
        tomato.setText(imageTomato);

        TextView story = findViewById(R.id.dstoryline);
        story.setText(imageStoryline);

        TextView genre = findViewById(R.id.dgenre);
        genre.setText(imagegenre);

        ImageView cast1 = findViewById(R.id.dcast1);
        Glide.with(this)
                .asBitmap()
                .load(imagecast1)
                .into(cast1);

        ImageView cast2 = findViewById(R.id.dcast2);
        Glide.with(this)
                .asBitmap()
                .load(imagecast2)
                .into(cast2);

        ImageView cast3 = findViewById(R.id.dcast3);
        Glide.with(this)
                .asBitmap()
                .load(imagecast3)
                .into(cast3);

        ImageView cast4 = findViewById(R.id.dcast4);
        Glide.with(this)
                .asBitmap()
                .load(imagecast4)
                .into(cast4);

        ImageView cast5 = findViewById(R.id.dcast5);
        Glide.with(this)
                .asBitmap()
                .load(imagecast5)
                .into(cast5);

        ImageView cast6 = findViewById(R.id.dcast6);
        Glide.with(this)
                .asBitmap()
                .load(imagecast6)
                .into(cast6);

        ImageView cimage = findViewById(R.id.dcrticimage);
        Glide.with(this)
                .asBitmap()
                .load(imagecimage)
                .into(cimage);

        TextView dir = findViewById(R.id.ddirectors);
        dir.setText(imagedirector);

        TextView writer = findViewById(R.id.dwriters);
        writer.setText(imagewriter);

        TextView reldate = findViewById(R.id.drelease);
        reldate.setText(imagereldate);

        TextView country = findViewById(R.id.dcountryorigin);
        country.setText(imagecountry);

        TextView language = findViewById(R.id.dlanguage);
        language.setText(imagelanguage);

        TextView cname = findViewById(R.id.dcriticname);
        cname.setText(imagecname);

        TextView crat = findViewById(R.id.dcritcrating);
        crat.setText(imagecrating);

        TextView creview = findViewById(R.id.dreview);
        creview.setText(imagecreview);











    }
}