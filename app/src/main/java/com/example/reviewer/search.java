package com.example.reviewer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class search extends AppCompatActivity {
Button home,search;
    FirebaseFirestore db;
    ArrayList<ProductsModel> userArrayList,userArrayList2;
    FRecyclerViewAdapter myAdapter,myAdapter2;
    RecyclerView recyclerView,recyclerView2;
    EditText searchbar;
    BottomNavigationView bottomNavigationView;
    ImageView nores;
    TextView notxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        recyclerView = findViewById(R.id.recyclerViews);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        searchbar = findViewById(R.id.searchbart);
        nores = findViewById(R.id.noresult);
        notxt = findViewById(R.id.notext);

        db = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<ProductsModel>();
        myAdapter = new FRecyclerViewAdapter(search.this,userArrayList);
        recyclerView.setAdapter(myAdapter);

        EventChangeListner();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.searchnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.homenav:
                        Intent intent = new Intent(search.this, Mainwindow.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
    }
    private void EventChangeListner() {
        db.collection("RecommendedTvShows").orderBy("mNames", Query.Direction.ASCENDING)
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
                       // shimmer2.setVisibility(View.GONE);

                    }
                });
        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
            filter(s.toString());
            }
        });
    }

    private void filter(String Text) {
        ArrayList<ProductsModel> filterList = new ArrayList<>();
        for(ProductsModel items :userArrayList){
            if(items.getmNames().toLowerCase().contains(Text.toLowerCase()))
            {
                filterList.add(items);
                nores.setVisibility(View.GONE);
                notxt.setVisibility(View.GONE);
            }
            else if(filterList.isEmpty()){
                nores.setVisibility(View.VISIBLE);
                notxt.setVisibility(View.VISIBLE);
            }

        }
        myAdapter.filterList(filterList);
    }

}