package com.shuvenduoffline.easycake;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CakeRveAdapter extends RecyclerView.Adapter<CakeRveAdapter.MyViewHolder> {

    private List<Cake> cakeList;
    private Context
            ctx;
    public interface MyAdapterListener {
        void buttonAddCart(View v, int position);
    }

    public MyAdapterListener onClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, desc;
        public Button btn_addCart;
        public ImageView cakeimg;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.cake_name);
            desc = (TextView) view.findViewById(R.id.cake_description);
            btn_addCart = (Button) view.findViewById(R.id.btn_add_cart);
            cakeimg = view.findViewById(R.id.cakeimg);
        }
    }


    public CakeRveAdapter(List<Cake> cakes,Context c,  MyAdapterListener onClickListener) {
        this.cakeList = cakes;
        this.ctx = c;
        this.onClickListener = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cake, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Cake cake = cakeList.get(position);
        holder.name.setText(cake.getName());
        holder.desc.setText("" + cake.getWeight() + " \u00B7 ₹" + cake.getPrice());
        Glide.with(ctx)
                .load(cake.getImgurl())
                .placeholder(R.drawable.cake_logo)
                .into(holder.cakeimg);
        holder.btn_addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onClickListener.buttonAddCart(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cakeList.size();
    }
}
