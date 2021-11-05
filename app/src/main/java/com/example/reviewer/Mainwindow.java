package com.example.reviewer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
    ArrayList<ProductsModel> userArrayList,userArrayList2,userArrayList3;
   FRecyclerViewAdapter myAdapter,myAdapter2,myAdapter3;
    RecyclerView recyclerView,recyclerView2,recyclerView3;
    ImageSlider imageSlider;
    ImageView v1;
    ShimmerFrameLayout shimmer1,shimmer2;
    Dialog dialog,dialog2;
    BottomNavigationView bottomNavigationView;


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

        recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        recyclerView3 = findViewById(R.id.recyclerView3);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        db = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<ProductsModel>();
        myAdapter = new FRecyclerViewAdapter(Mainwindow.this,userArrayList);
        recyclerView.setAdapter(myAdapter);

        userArrayList2 = new ArrayList<ProductsModel>();
        myAdapter2 = new FRecyclerViewAdapter(Mainwindow.this,userArrayList2);
        recyclerView2.setAdapter(myAdapter2);

        userArrayList3 = new ArrayList<ProductsModel>();
        myAdapter3 = new FRecyclerViewAdapter(Mainwindow.this,userArrayList3);
        recyclerView3.setAdapter(myAdapter3);

        EventChangeListner();
        EventChangeListner2();
        EventChangeListner3();
        dialog = new Dialog (this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.homenav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.searchnav:
                        Intent intent = new Intent(Mainwindow.this, search.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });




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
                opendialog();
              /*  SharedPreferences sharedPreferences = getSharedPreferences(emailogin.PREFS_NAME,0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("hasloggedin",false);
                editor.commit();
                Intent intent = new Intent(Mainwindow.this,mainmenu.class);
                startActivity(intent);
                finish();*/
            }


        });
       /* home = (Button) findViewById(R.id.home);
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
        });*/
}

    private void opendialog() {
        dialog.setContentView(R.layout.exit);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
        Button btnok = dialog.findViewById(R.id.logoute);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                SharedPreferences sharedPreferences = getSharedPreferences(emailogin.PREFS_NAME,0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("hasloggedin",false);
                editor.commit();
                Intent intent = new Intent(Mainwindow.this,mainmenu.class);
                startActivity(intent);
                finish();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });
        Button btncancel = dialog.findViewById(R.id.cancele);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
    }

    private void EventChangeListner() {
        db.collection("RecommendedTvShows").orderBy("mNames", Query.Direction.DESCENDING)
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
    private void EventChangeListner2() {
        db.collection("Recently Added")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("Firestore Error",error.getMessage());
                            return;
                        }
                        for(DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == (DocumentChange.Type.ADDED)){
                                userArrayList2.add(dc.getDocument().toObject(ProductsModel.class));
                            }
                            myAdapter2.notifyDataSetChanged();
                        }
                        shimmer2.setVisibility(View.GONE);

                    }
                });
    }
    private void EventChangeListner3() {
        db.collection("BestMovies")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("Firestore Error",error.getMessage());
                            return;
                        }
                        for(DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == (DocumentChange.Type.ADDED)){
                                userArrayList3.add(dc.getDocument().toObject(ProductsModel.class));
                            }
                            myAdapter3.notifyDataSetChanged();
                        }
                        shimmer2.setVisibility(View.GONE);

                    }
                });
    }

}