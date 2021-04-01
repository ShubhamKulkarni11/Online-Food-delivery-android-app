package com.example111.foodshop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Home_fragment extends Fragment {
    RecyclerView recyclerView;
    Myadapter adapter;
    DatabaseReference reference;
  private TextView searchItems;
  private ArrayList<model> myFoodList;
  Myadapter myadapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.fragment_home_fragment, container, false);

        recyclerView=view.findViewById(R.id.shopRecView);
        //search items
        searchItems=view.findViewById(R.id.sreachFoodItems);

        //GridLayoutManager gridLayoutManager=new GridLayoutManager(this.getContext(),2);
        //recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        myFoodList=new ArrayList<>();

        //optional
       /* reference=FirebaseDatabase.getInstance().getReference().child("FoodItems");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    model p=dataSnapshot.getValue(model.class);
                    myFoodList.add(p);
                }
                adapter=new Myadapter(myFoodList);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });*/
    // getting user id
        FirebaseUser user;
        user=FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();


        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                       .setQuery(FirebaseDatabase.getInstance().getReference().child("FoodItems").child(uid), model.class)
                        .build();
        adapter=new Myadapter(options);
        recyclerView.setAdapter(adapter);



        searchItems.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                proccessSearch(s);


            }
        });



        return view;


    }

   ;
    private void searchByPrice(Editable s) {
        // taking user id
        FirebaseUser user;
        user=FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();


        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("FoodItems").orderByChild("foodPrice").startAt(String.valueOf(s)).endAt(s+"\uf8ff"), model.class)
                        .build();
        adapter=new Myadapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }

    private boolean proccessSearch(Editable s) {
        FirebaseUser user;
        user=FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("FoodItems").child(uid).orderByChild("foodName").startAt(String.valueOf(s)).endAt(s+"\uf8ff"), model.class)
                        .build();
        adapter=new Myadapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
        return false;
    }


    // search food Items
  /*  private void filter(String toString) {

        ArrayList<model> filterdata=new ArrayList<>();

        for(model item: myFoodList){

            if(item.getFoodName().toLowerCase().contains(toString.toLowerCase()));
            {
                filterdata.add(item);
            }

        }

        myadapter.filterList(filterdata);


    }*/

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }

}