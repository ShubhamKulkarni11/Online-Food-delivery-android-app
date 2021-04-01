package com.example111.foodshop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class customerCart extends Fragment {

    RecyclerView recyclerView;

    DatabaseReference reference;


    private cartAdapter adapter;
    Myadapter myadapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_customer_cart, container, false);

        recyclerView=view.findViewById(R.id.customerCart);
        //search items


        //GridLayoutManager gridLayoutManager=new GridLayoutManager(this.getContext(),2);
        //recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        ArrayList<cartItems> myFoodList = new ArrayList<>();
        FirebaseUser user;
        user= FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();


        FirebaseRecyclerOptions<cartItems> options =
                new FirebaseRecyclerOptions.Builder<cartItems>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("CustomerCart").child(uid), cartItems.class)
                        .build();
        adapter=new cartAdapter(options);
        recyclerView.setAdapter(adapter);
        return view;




    }
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

}