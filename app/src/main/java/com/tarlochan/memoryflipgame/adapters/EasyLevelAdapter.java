package com.tarlochan.memoryflipgame.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.tarlochan.memoryflipgame.DataStorage.ProductImg;
import com.tarlochan.memoryflipgame.DataStorage.Products;
import com.tarlochan.memoryflipgame.MainActivity;
import com.tarlochan.memoryflipgame.R;

import java.util.ArrayList;

public class EasyLevelAdapter extends RecyclerView.Adapter<EasyLevelAdapter.ViewHolder> {

    private ArrayList<Integer> cardFront;
    private  ArrayList<Products> mProductList = MainActivity.Companion.getMProductsList();

    //public EasyLevelAdapter(ArrayList<Integer> cardFront) {
        //this.cardFront = cardFront;
    //}

    public EasyLevelAdapter(ArrayList<Products> mProductList) {
        this.mProductList = mProductList;
    }

    @Override
    public EasyLevelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        //view.setMinimumWidth(parent.getMeasuredWidth() / 3);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Products mProducts = mProductList.get(position);
        String imgURL = mProducts.getProductImg().getImagelink();
        Glide.with(holder.cardFr)  //2
                .load(imgURL) //3
                .centerCrop() //4
                .placeholder(R.drawable.img_placeholder) //5
                .error(R.drawable.img_notload) //6
                .fallback(R.drawable.img_placeholder) //7
                .into(holder.cardFr);
        //holder.cardFr.setImageResource(cardFront.get(position));
    }

    @Override
    public int getItemCount() {
        //return cardFront.size();
        return mProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView cardFr;

        public ViewHolder(View itemView) {
            super(itemView);
            cardFr = itemView.findViewById(R.id.cardfront);
            cardFr.setAdjustViewBounds(true);
        }
    }
}
