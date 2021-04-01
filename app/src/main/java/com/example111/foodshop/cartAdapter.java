package com.example111.foodshop;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Objects;

public class cartAdapter extends FirebaseRecyclerAdapter<cartItems,cartAdapter.myViewHolder> {



    public cartAdapter(@NonNull FirebaseRecyclerOptions<cartItems> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull final myViewHolder holder, final int position, @NonNull cartItems model) {

        holder.foodName.setText("Food Name : " + model.getFoodName());
        holder.totalPrice.setText("Rs "+ model.getTotal());
        holder.quantity.setText(model.getQuantity());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user;
                user= FirebaseAuth.getInstance().getCurrentUser();
                final String uid = user.getUid();


                AlertDialog.Builder builder= new AlertDialog.Builder(holder.foodName.getContext());
                builder.setTitle("Delete");
                builder.setMessage("Delete Food Item.....??");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {


                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        FirebaseDatabase.getInstance().getReference().child("CustomerCart").child(uid)
                                .child(Objects.requireNonNull(getRef(position).getKey())).removeValue();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });




    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow3,parent,false);
        return new myViewHolder(view);

    }

    static class myViewHolder extends RecyclerView.ViewHolder {

        TextView foodName,totalPrice,quantity,delete;

        public myViewHolder(View itemView) {
            super(itemView);



            foodName=itemView.findViewById(R.id.cartFoodName);
            totalPrice=itemView.findViewById(R.id.cartTotalPrice);
            quantity=itemView.findViewById(R.id.cartQuantity);
            delete=itemView.findViewById(R.id.cartdeleteFoodItems);



        }

    }


}
