package com.example111.foodshop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import static com.example111.foodshop.R.layout.singlerow2;


class ImageAdapter extends  FirebaseRecyclerAdapter<model,ImageAdapter.myViewHolder> {
    //public ArrayList<model> foodList;

   /* public Myadapter(ArrayList<model> foodList) {
        this.foodList = foodList;
        RecyclerView.Adapter<Myadapter.myViewHolder>

    }*/

    public ImageAdapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull final myViewHolder holder, final int position, @NonNull final model model) {
        holder.FoodName.setText("Food Name: "+ model.getFoodName());
        holder.FoodDescription.setText("Rs: "+model.getFoodPrice());
        holder.FoodPrice.setText(model.getFoodDesci());
        Glide.with(holder.FoodimageView.getContext()).load(model.getUrlImage()).into(holder.FoodimageView);

        // setOnClickListener on cardview
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.FoodName.getContext(),foodItemDescription.class);
                intent.putExtra("Image",model.getUrlImage());
               // intent.putExtra("UrlImage",model.getUrlImage());
                intent.putExtra("FoodName",model.getFoodName());
                intent.putExtra("FoodDesci",model.getFoodDesci());
                intent.putExtra("FoodPrice",model.getFoodPrice());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.FoodName.getContext().startActivity(intent);
            }
        });




    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(singlerow2,parent,false);
        return new myViewHolder(view);
    }

  /*  @Override
    public int getItemCount() {
        return foodList.size();
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(singlerow,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        model model=foodList.get(position);
        holder.FoodName.setText(model.getFoodName());
        holder.FoodDescription.setText("Rs: "+model.getFoodPrice());
        holder.FoodPrice.setText(model.getFoodDesci());
        Glide.with(holder.FoodimageView.getContext()).load(model.getUrlImage()).into(holder.FoodimageView);

    }*/




    static class myViewHolder extends RecyclerView.ViewHolder {
        public TextView FoodName;
        public TextView FoodPrice;
        public TextView FoodDescription;
        public ImageView FoodimageView;

        public myViewHolder(View itemView) {
            super(itemView);
            FoodName = itemView.findViewById(R.id.FoodName);
            FoodDescription = itemView.findViewById(R.id.cartTotalPrice);
            FoodPrice = itemView.findViewById(R.id.cEdit);
            FoodimageView = itemView.findViewById(R.id.FoodImage);


        }

    }
    /*public void filterList(ArrayList<model> filterdata) {
        foodList=filterdata;

    }*/


}