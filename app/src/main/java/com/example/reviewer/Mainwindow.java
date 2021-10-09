package com.example.reviewer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

public class Mainwindow extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button log,home,search;
    SharedPreferences sp;
    FirebaseFirestore db;
    ArrayList<ProductsModel> userArrayList;
   FRecyclerViewAdapter myAdapter;
    RecyclerView recyclerView;
    ImageSlider imageSlider;
    ImageView v1;
    ShimmerFrameLayout shimmer1,shimmer2;


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        TastyToast.makeText(getApplicationContext(), "Press BACK again to Exit", TastyToast.LENGTH_SHORT, TastyToast.WARNING);


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainwindow);

        imageSlider = findViewById(R.id.image_slider);
        v1 = findViewById(R.id.image_slider_view1);

        imageSlider.setVisibility(View.GONE);
        shimmer1 = findViewById(R.id.shimmer1);
        shimmer1.setVisibility(View.VISIBLE);

        shimmer2 = findViewById(R.id.shimmer2);
        shimmer2.setVisibility(View.VISIBLE);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        db = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<ProductsModel>();
        myAdapter = new FRecyclerViewAdapter(Mainwindow.this,userArrayList);
        recyclerView.setAdapter(myAdapter);

        EventChangeListner();




       final List<SlideModel> slideModels = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("Slider")
        .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot data: snapshot.getChildren())
                {
            slideModels.add(new SlideModel(data.child("url").getValue().toString(),data.child("title").getValue().toString(),ScaleTypes.CENTER_CROP));
                }

                imageSlider.setImageList(slideModels,ScaleTypes.CENTER_CROP);
                imageSlider.setVisibility(View.VISIBLE);
                shimmer1.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


       //getImages();
       //getImagesmovie();



        log = (Button) findViewById(R.id.logout);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(emailogin.PREFS_NAME,0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("hasloggedin",false);
                editor.commit();
                Intent intent = new Intent(Mainwindow.this,mainmenu.class);
                startActivity(intent);
                finish();
            }
    });
        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());

            }
        });
        search = (Button) findViewById(R.id.search1);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mainwindow.this, search.class);
                startActivity(intent);
            }
        });
}

    private void EventChangeListner() {
        db.collection("RecommendedTv").orderBy("mNames", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                  if(error != null){
                      Log.e("Firestore Error",error.getMessage());
                      return;
                  }
                  for(DocumentChange dc : value.getDocumentChanges()){
                      if(dc.getType() == (DocumentChange.Type.ADDED)){
                      userArrayList.add(dc.getDocument().toObject(ProductsModel.class));
                      }
                      myAdapter.notifyDataSetChanged();
                  }
                        shimmer2.setVisibility(View.GONE);

                    }
                });
    }


 /*   private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mImageUrls,mNames,mDesc,mVidId,mImdb,mTomato,mStoryline,mGenre,mCast1,mCast2,mCast3,mCast4,mCast5,mCast6,mDirector,mWriter,mReleaseDate,mCountryOrigin,mLanguage,mCname,mCimageurls,mCrating,mCreview,this);
        recyclerView.setAdapter(adapter);
    }

    private void initRecyclerViewMov(){
        Log.d(TAG, "initRecyclerView: init recyclerview2");

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(layoutManager2);
        RecyclerViewAdapter adapter2 = new RecyclerViewAdapter(movImageUrls,movNames,movDesc,movVidId,movImdb,movTomato,movStoryline,movGenre,movCast1,movCast2,movCast3,movCast4,movCast5,movCast6,movDirector,movWriter,movReleaseDate,movCountryOrigin,movLanguage,movCname,movCimageurls,movCrating,movCreview,this);
        recyclerView.setAdapter(adapter2);
    }

    private void getImages(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrls.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/tvshows%2Fmoneyhiest.jpg?alt=media&token=8575e488-8ce8-482e-b1a0-ff4723aad3d2");
        mNames.add("Money Heist");
        mDesc.add("Eight thieves take hostages and lock themselves in the Royal Mint of Spain as a criminal mastermind manipulates the police to carry out his plan.");
        mVidId.add("ZAXA1DV4dtI");
        mImdb.add("8.2 / 10");
        mTomato.add("98 %");
        mStoryline.add("To carry out the biggest heist in history, a mysterious man called The Professor recruits a band of eight robbers who have a single characteristic: none of them has anything to lose. Five months of seclusion memorizing every step, every detail, every probability culminate in eleven days locked up in the National Coinage and Stamp Factory of Spain, surrounded by police forces and with dozens of hostages in their power, to find out whether their suicide wager will lead to everything or nothing.");
        mGenre.add("Action    Crime    Mystery    Thriller");
        mCast1.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Falvaro%20morte.jpg?alt=media&token=d4a82ee7-d89a-4c9a-81e0-4e48501507e0");
        mCast2.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fursula%20corbero.jpg?alt=media&token=85bcd03f-48a2-451a-8599-70e9287ebebe");
        mCast3.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fpedroalosnso.jfif?alt=media&token=35484b93-fff5-4981-a73b-a7d9f2b1c9ba");
        mCast4.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fmiguel%20herran.jpg?alt=media&token=20e7fd93-b3df-4520-ba29-5dd894964fc3");
        mCast5.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fitziar%20ituno.jpg?alt=media&token=3219b41b-f0b4-4068-8fb5-7d39f358fb0d");
        mCast6.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fjaime%20lorente.jpg?alt=media&token=30f5c4aa-f574-45a3-960e-4717aeaaa382");
        mDirector.add("Jesus Colmenar , Alex Rodrigo");
        mWriter.add("Alex Pina");
        mReleaseDate.add("December 20 , 2017");
        mCountryOrigin.add("Spain");
        mLanguage.add("Spanish , Russian , Serbian , English");
        mCname.add("Vamos Ramos");
        mCimageurls.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/crtics%2Fcrtic1.png?alt=media&token=4e3049c2-47e5-46b0-8a0c-e54b5294bc33");
        mCrating.add("8.9 / 10");
        mCreview.add("Spain’s original underdog of a show is back to blow your mind, and it does – in every way that the previous seasons did and in more ways than you would expect it to. New characters weave into the story like peas in a pod. And the older favourite ones keep you hooked, all over again.\n" +
                "\n" +
                "Fans of the show have waited a long time and the gratification is handed over to viewers on a platter. The show begins right in the midst of the action. While the gang works their way through the heist from within the Bank of Spain, with Lisbon at the reins, the Professor is in a situation of sorts with Alicia playing his villain who has gone solo in her attempt to bring to book the betrayal by the army and therefore her own soiled reputation of being the best negotiator to walk the planet.\n" +
                "\n" +
                "Cooler bits about the first half of season five? The villains. The women. The Professor, of course, and the way tables turn – quite literally.");

        mImageUrls.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/tvshows%2Fbreakingbad.jpg?alt=media&token=a42ac4a2-02fc-4c9d-b37d-bc617ebef4b7");
        mNames.add("Breaking Bad");
        mDesc.add("A high school chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine in order to secure his family's future.");
        mVidId.add("lrcqbavlbyQ");
        mImdb.add("9.4 / 10");
        mTomato.add("99 %");
        mStoryline.add("When chemistry teacher Walter White is diagnosed with Stage III cancer and given only two years to live, he decides he has nothing to lose. He lives with his teenage son, who has cerebral palsy, and his wife, in New Mexico. Determined to ensure that his family will have a secure future, Walt embarks on a career of drugs and crime. He proves to be remarkably proficient in this new world as he begins manufacturing and selling methamphetamine with one of his former students. The series tracks the impacts of a fatal diagnosis on a regular, hard working man, and explores how a fatal diagnosis affects his morality and transforms him into a major player of the drug trade.");
        mGenre.add("Action Crime Mystery Thriller");
        mCast1.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Falvaro%20morte.jpg?alt=media&token=d4a82ee7-d89a-4c9a-81e0-4e48501507e0");
        mCast2.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fursula%20corbero.jpg?alt=media&token=85bcd03f-48a2-451a-8599-70e9287ebebe");
        mCast3.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fpedroalosnso.jfif?alt=media&token=35484b93-fff5-4981-a73b-a7d9f2b1c9ba");
        mCast4.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fmiguel%20herran.jpg?alt=media&token=20e7fd93-b3df-4520-ba29-5dd894964fc3");
        mCast5.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fmiguel%20herran.jpg?alt=media&token=20e7fd93-b3df-4520-ba29-5dd894964fc3");
        mCast6.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fjaime%20lorente.jpg?alt=media&token=30f5c4aa-f574-45a3-960e-4717aeaaa382");
        mDirector.add("Jesus Colmenar , Alex Rodrigo");
        mWriter.add("Alex Pina");
        mReleaseDate.add("December 20 , 2017");
        mCountryOrigin.add("Spain");
        mLanguage.add("Spanish , Russian , Serbian , English");
        mCname.add("Vamos Ramos");
        mCimageurls.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/crtics%2Fcritic.jpg?alt=media&token=42a75629-339e-4cc9-8458-10071664d2f2");
        mCrating.add("8.9 / 10");
        mCreview.add("Spain’s original underdog of a show is back to blow your mind, and it does – in every way that the previous seasons did and in more ways than you would expect it to. New characters weave into the story like peas in a pod. And the older favourite ones keep you hooked, all over again.\n" +
                "\n" +
                "Fans of the show have waited a long time and the gratification is handed over to viewers on a platter. The show begins right in the midst of the action. While the gang works their way through the heist from within the Bank of Spain, with Lisbon at the reins, the Professor is in a situation of sorts with Alicia playing his villain who has gone solo in her attempt to bring to book the betrayal by the army and therefore her own soiled reputation of being the best negotiator to walk the planet.\n" +
                "\n" +
                "Cooler bits about the first half of season five? The villains. The women. The Professor, of course, and the way tables turn – quite literally.");

        mImageUrls.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/tvshows%2Fhimym.jpg?alt=media&token=d904457b-f72c-4ce9-a096-b591929af3d9");
        mNames.add("How I Met Your Mother");
        mDesc.add("A father recounts to his children - through a series of flashbacks - the journey he and his four best friends took leading up to him meeting their mother.");
        mVidId.add("C8-4jMTOUJI");
        mImdb.add("8.3 / 10");
        mTomato.add("99 %");
        mStoryline.add("Ted Mosby sits down with his kids, to tell them the story of how he met their mother. The story is told through memories of his friends Marshall, Lily, Robin, and Barney Stinson. All legendary 9 seasons lead up to the moment of Ted's final encounter with \"the one.\"");
        mGenre.add("Action Crime Mystery Thriller");
        mCast1.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Falvaro%20morte.jpg?alt=media&token=d4a82ee7-d89a-4c9a-81e0-4e48501507e0");
        mCast2.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fursula%20corbero.jpg?alt=media&token=85bcd03f-48a2-451a-8599-70e9287ebebe");
        mCast3.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fpedroalosnso.jfif?alt=media&token=35484b93-fff5-4981-a73b-a7d9f2b1c9ba");
        mCast4.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fmiguel%20herran.jpg?alt=media&token=20e7fd93-b3df-4520-ba29-5dd894964fc3");
        mCast5.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fmiguel%20herran.jpg?alt=media&token=20e7fd93-b3df-4520-ba29-5dd894964fc3");
        mCast6.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fjaime%20lorente.jpg?alt=media&token=30f5c4aa-f574-45a3-960e-4717aeaaa382");
        mDirector.add("Jesus Colmenar , Alex Rodrigo");
        mWriter.add("Alex Pina");
        mReleaseDate.add("December 20 , 2017");
        mCountryOrigin.add("Spain");
        mLanguage.add("Spanish , Russian , Serbian , English");
        mCname.add("Vamos Ramos");
        mCimageurls.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/crtics%2Fcritic.jpg?alt=media&token=42a75629-339e-4cc9-8458-10071664d2f2");
        mCrating.add("8.9 / 10");
        mCreview.add("Spain’s original underdog of a show is back to blow your mind, and it does – in every way that the previous seasons did and in more ways than you would expect it to. New characters weave into the story like peas in a pod. And the older favourite ones keep you hooked, all over again.\n" +
                "\n" +
                "Fans of the show have waited a long time and the gratification is handed over to viewers on a platter. The show begins right in the midst of the action. While the gang works their way through the heist from within the Bank of Spain, with Lisbon at the reins, the Professor is in a situation of sorts with Alicia playing his villain who has gone solo in her attempt to bring to book the betrayal by the army and therefore her own soiled reputation of being the best negotiator to walk the planet.\n" +
                "\n" +
                "Cooler bits about the first half of season five? The villains. The women. The Professor, of course, and the way tables turn – quite literally.");

        mImageUrls.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/tvshows%2Fbrooklyn99.jpg?alt=media&token=3a3842aa-417f-435e-8928-58b28a64c70d");
        mNames.add("Brooklyn99");
        mDesc.add("Comedy series following the exploits of Det. Jake Peralta and his diverse, lovable colleagues as they police the NYPD's 99th Precinct.");
        mVidId.add("sEOuJ4z5aTc");
        mImdb.add("8.4 / 10");
        mTomato.add("99 %");
        mStoryline.add("Captain Ray Holt takes over Brooklyn's 99th precinct, which includes Detective Jake Peralta, a talented but carefree detective who's used to doing whatever he wants. The other employees of the 99th precinct include Detective Amy Santiago, Jake's over achieving and competitive partner; Detective Rosa Diaz, a tough and kept to herself coworker; Detective Charles Boyle, Jake's best friend who also has crush on Rosa; Detective Sergeant Terry Jeffords, who was recently taken off the field after the birth of his twin girls; and Gina Linetti, the precinct's sarcastic administrator.");
        mGenre.add("Action Crime Mystery Thriller");
        mCast1.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Falvaro%20morte.jpg?alt=media&token=d4a82ee7-d89a-4c9a-81e0-4e48501507e0");
        mCast2.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fursula%20corbero.jpg?alt=media&token=85bcd03f-48a2-451a-8599-70e9287ebebe");
        mCast3.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fpedroalosnso.jfif?alt=media&token=35484b93-fff5-4981-a73b-a7d9f2b1c9ba");
        mCast4.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fmiguel%20herran.jpg?alt=media&token=20e7fd93-b3df-4520-ba29-5dd894964fc3");
        mCast5.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fmiguel%20herran.jpg?alt=media&token=20e7fd93-b3df-4520-ba29-5dd894964fc3");
        mCast6.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fjaime%20lorente.jpg?alt=media&token=30f5c4aa-f574-45a3-960e-4717aeaaa382");
        mDirector.add("Jesus Colmenar , Alex Rodrigo");
        mWriter.add("Alex Pina");
        mReleaseDate.add("December 20 , 2017");
        mCountryOrigin.add("Spain");
        mLanguage.add("Spanish , Russian , Serbian , English");
        mCname.add("Vamos Ramos");
        mCimageurls.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/crtics%2Fcritic.jpg?alt=media&token=42a75629-339e-4cc9-8458-10071664d2f2");
        mCrating.add("8.9 / 10");
        mCreview.add("Spain’s original underdog of a show is back to blow your mind, and it does – in every way that the previous seasons did and in more ways than you would expect it to. New characters weave into the story like peas in a pod. And the older favourite ones keep you hooked, all over again.\n" +
                "\n" +
                "Fans of the show have waited a long time and the gratification is handed over to viewers on a platter. The show begins right in the midst of the action. While the gang works their way through the heist from within the Bank of Spain, with Lisbon at the reins, the Professor is in a situation of sorts with Alicia playing his villain who has gone solo in her attempt to bring to book the betrayal by the army and therefore her own soiled reputation of being the best negotiator to walk the planet.\n" +
                "\n" +
                "Cooler bits about the first half of season five? The villains. The women. The Professor, of course, and the way tables turn – quite literally.");

        mImageUrls.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/tvshows%2Flucifer.jpg?alt=media&token=49ee03da-47ed-4e7c-be1c-22f18b169df8");
        mNames.add("Lucifer");
        mDesc.add("yoyoyo");
        mVidId.add("X4bF_quwNtw");
        mImdb.add("9.2 / 10");
        mTomato.add("99 %");
        mStoryline.add("Godammit");
        mGenre.add("Action Crime Mystery Thriller");
        mCast1.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Falvaro%20morte.jpg?alt=media&token=d4a82ee7-d89a-4c9a-81e0-4e48501507e0");
        mCast2.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fursula%20corbero.jpg?alt=media&token=85bcd03f-48a2-451a-8599-70e9287ebebe");
        mCast3.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fpedroalosnso.jfif?alt=media&token=35484b93-fff5-4981-a73b-a7d9f2b1c9ba");
        mCast4.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fmiguel%20herran.jpg?alt=media&token=20e7fd93-b3df-4520-ba29-5dd894964fc3");
        mCast5.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fitziar%20ituno.jpg?alt=media&token=abc8688d-b1c5-45f9-9593-2ab5b6a5e3aa");
        mCast6.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fjaime%20lorente.jpg?alt=media&token=30f5c4aa-f574-45a3-960e-4717aeaaa382");
        mDirector.add("Jesus Colmenar , Alex Rodrigo");
        mWriter.add("Alex Pina");
        mReleaseDate.add("December 20 , 2017");
        mCountryOrigin.add("Spain");
        mLanguage.add("Spanish , Russian , Serbian , English");
        mCname.add("Vamos Ramos");
        mCimageurls.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/crtics%2Fcritic.jpg?alt=media&token=42a75629-339e-4cc9-8458-10071664d2f2");
        mCrating.add("8.9 / 10");
        mCreview.add("Spain’s original underdog of a show is back to blow your mind, and it does – in every way that the previous seasons did and in more ways than you would expect it to. New characters weave into the story like peas in a pod. And the older favourite ones keep you hooked, all over again.\n" +
                "\n" +
                "Fans of the show have waited a long time and the gratification is handed over to viewers on a platter. The show begins right in the midst of the action. While the gang works their way through the heist from within the Bank of Spain, with Lisbon at the reins, the Professor is in a situation of sorts with Alicia playing his villain who has gone solo in her attempt to bring to book the betrayal by the army and therefore her own soiled reputation of being the best negotiator to walk the planet.\n" +
                "\n" +
                "Cooler bits about the first half of season five? The villains. The women. The Professor, of course, and the way tables turn – quite literally.");

        initRecyclerView();

    }
    private void getImagesmovie(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps movies.");

        movImageUrls.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/moneyhiest.jpg?alt=media&token=0180946f-fc32-48cc-bf53-93efadb67af8");
        movNames.add("Money Heist");
        movDesc.add("Eight thieves take hostages and lock themselves in the Royal Mint of Spain as a criminal mastermind manipulates the police to carry out his plan.");
        movVidId.add("ZAXA1DV4dtI");
        movImdb.add("8.2 / 10");
        movTomato.add("98 %");
        movStoryline.add("To carry out the biggest heist in history, a mysterious man called The Professor recruits a band of eight robbers who have a single characteristic: none of them has anything to lose. Five months of seclusion memorizing every step, every detail, every probability culminate in eleven days locked up in the National Coinage and Stamp Factory of Spain, surrounded by police forces and with dozens of hostages in their power, to find out whether their suicide wager will lead to everything or nothing.");
        movGenre.add("Action    Crime    Mystery    Thriller");
        movCast1.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Falvaro%20morte.jpg?alt=media&token=d4a82ee7-d89a-4c9a-81e0-4e48501507e0");
        movCast2.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fursula%20corbero.jpg?alt=media&token=85bcd03f-48a2-451a-8599-70e9287ebebe");
        movCast3.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fmiguel%20herran.jpg?alt=media&token=20e7fd93-b3df-4520-ba29-5dd894964fc3");
        movCast4.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fmiguel%20herran.jpg?alt=media&token=20e7fd93-b3df-4520-ba29-5dd894964fc3");
        movCast5.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fitziar%20ituno.jpg?alt=media&token=3219b41b-f0b4-4068-8fb5-7d39f358fb0d");
        movCast6.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/Top%20Cast%2Fjaime%20lorente.jpg?alt=media&token=30f5c4aa-f574-45a3-960e-4717aeaaa382");
        movDirector.add("Jesus Colmenar , Alex Rodrigo");
        movWriter.add("Alex Pina");
        movReleaseDate.add("December 20 , 2017");
        movCountryOrigin.add("Spain");
        movLanguage.add("Spanish , Russian , Serbian , English");
        movCname.add("Vamos Ramos");
        movCimageurls.add("https://firebasestorage.googleapis.com/v0/b/reviewer-1a33b.appspot.com/o/crtics%2Fcritic.jpg?alt=media&token=42a75629-339e-4cc9-8458-10071664d2f2");
        movCrating.add("8.9 / 10");
        movCreview.add("Spain’s original underdog of a show is back to blow your mind, and it does – in every way that the previous seasons did and in more ways than you would expect it to. New characters weave into the story like peas in a pod. And the older favourite ones keep you hooked, all over again.\n" +
                "\n" +
                "Fans of the show have waited a long time and the gratification is handed over to viewers on a platter. The show begins right in the midst of the action. While the gang works their way through the heist from within the Bank of Spain, with Lisbon at the reins, the Professor is in a situation of sorts with Alicia playing his villain who has gone solo in her attempt to bring to book the betrayal by the army and therefore her own soiled reputation of being the best negotiator to walk the planet.\n" +
                "\n" +
                "Cooler bits about the first half of season five? The villains. The women. The Professor, of course, and the way tables turn – quite literally.");


        initRecyclerViewMov();

    }

*/

}