package com.example111.foodshop;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example111.foodshop.R.layout.singlerow;


class Myadapter extends  FirebaseRecyclerAdapter<model,Myadapter.myViewHolder> {
    private ProgressDialog pd;

   //public ArrayList<model> foodList;

   /* public Myadapter(ArrayList<model> foodList) {
        this.foodList = foodList;
        RecyclerView.Adapter<Myadapter.myViewHolder>

    }*/

    public Myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull final myViewHolder holder, final int position, @NonNull final model model) {
        holder.FoodName.setText("Food Name: "+ model.getFoodName());
        holder.FoodDescription.setText("Rs: "+model.getFoodPrice());
        holder.FoodPrice.setText(model.getFoodDesci());
        Glide.with(holder.FoodimageView.getContext()).load(model.getUrlImage()).into(holder.FoodimageView);

        // editt Food Items
                holder.edit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //pd.setMessage("Upadating");
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.FoodimageView.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_dilogbox))
                        .setExpanded(true,1000)
                        .create();

                View myView=dialogPlus.getHolderView();
                final EditText dname=myView.findViewById(R.id.dfoodName);
                final EditText dprice=myView.findViewById(R.id.dfoodPrice);
                final EditText dDescri=myView.findViewById(R.id.dfooddescri);
                final Button dUpade=myView.findViewById(R.id.dUpdate);

                dname.setText(model.getFoodName());
                dprice.setText(model.getFoodPrice());
                dDescri.setText(model.getFoodDesci());
                dialogPlus.show();


                //updating data
                dUpade.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View v) {
                        FirebaseUser user;
                        user= FirebaseAuth.getInstance().getCurrentUser();
                        final String uid = user.getUid();
                        //pd.show();
                       Map<String,Object> map= new HashMap<>();
                        map.put("foodName",dname.getText().toString());
                        map.put("foodPrice",dprice.getText().toString());
                        map.put("foodDesci",dDescri.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("FoodItems").child(uid)
                                .child(Objects.requireNonNull(getRef(position).getKey())).updateChildren(map);


                        FirebaseDatabase.getInstance().getReference().child("FoodItems")
                                .child(Objects.requireNonNull(getRef(position).getKey())).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                       // pd.dismiss();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialogPlus.dismiss();
                               // pd.dismiss();


                            }
                        });
                    }
                });




                    }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user;
                user= FirebaseAuth.getInstance().getCurrentUser();
                final String uid = user.getUid();


                AlertDialog.Builder builder= new AlertDialog.Builder(holder.FoodimageView.getContext());
                builder.setTitle("Delete");
                builder.setMessage("Delete Food Item.....??");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    private DialogInterface dialog;
                    private int which;

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("FoodItems")
                                .child(Objects.requireNonNull(getRef(position).getKey())).removeValue();

                        FirebaseDatabase.getInstance().getReference().child("FoodItems").child(uid)
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
        View view= LayoutInflater.from(parent.getContext()).inflate(singlerow,parent,false);
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
        private TextView edit;
        private  TextView delete;

        public myViewHolder(View itemView) {
            super(itemView);
            FoodName = itemView.findViewById(R.id.FoodName);
            FoodDescription = itemView.findViewById(R.id.cartTotalPrice);
            FoodPrice = itemView.findViewById(R.id.cEdit);
            FoodimageView = itemView.findViewById(R.id.FoodImage);
            edit=itemView.findViewById(R.id.editFooditems);
            delete=itemView.findViewById(R.id.cartdeleteFoodItems);


        }

    }
    /*public void filterList(ArrayList<model> filterdata) {
        foodList=filterdata;

    }*/


}
