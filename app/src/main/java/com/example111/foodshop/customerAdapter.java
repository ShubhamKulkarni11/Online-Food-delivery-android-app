package com.example111.foodshop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class customerAdapter extends FirebaseRecyclerAdapter<model,customerAdapter.myViewHolder> {



    public customerAdapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull model model) {

       /* holder.name.setText(model.getShopName(position));
        holder.email.setText(model.getEmail(position));
        holder.contact.setText(model.getContact(position));*/



    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myViewHolder(view);

    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name,contact,email;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
           /* img=itemView.findViewById(R.id.FoodImage);
            name=itemView.findViewById(R.id.Name);
            contact=itemView.findViewById(R.id.Contact);
            email=itemView.findViewById(R.id.Email);*/

        }
    }
}
