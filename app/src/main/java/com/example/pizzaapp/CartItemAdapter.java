package com.example.pizzaapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>{
    private static double DouPrice = 0;
    private Context mCtx;
    private List<CartItemClass> cartItemClassList = new ArrayList<>();

    public CartItemAdapter(Context mCtx, List<CartItemClass> cartItemClassList) {
        this.mCtx = mCtx;
        this.cartItemClassList = cartItemClassList;
    }

    @Override
    public CartItemAdapter.CartItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.hot_product_list, null);
        return new CartItemAdapter.CartItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final CartItemAdapter.CartItemViewHolder cartItemViewHolder, int position) {

        CartItemClass cartItemClass = cartItemClassList.get(position);

        /*cartItemViewHolder.textViewDesc.setText("description");
        cartItemViewHolder.textViewTitle.setText("Title");
        cartItemViewHolder.textViewPrice.setText(CartItemClass.getItemLocation());
        cartItemViewHolder.textViewRating.setText(CartItemClass.getItemStatus());*/
        //cartItemViewHolder.textViewstatus.setText(CartItemClass.getStatus());


        /*cartItemViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, SingleViewActivity.class);
                intent.putExtra("id",CartItemClass.getId());
                intent.putExtra("title", CartItemClass.getTitle());
                intent.putExtra("description", CartItemClass.getShortdesc());
                intent.putExtra("rating", String.valueOf(CartItemClass.getRating()));
                intent.putExtra("status", CartItemClass.getStatus());
                intent.putExtra("image", CartItemClass.getImage());
                intent.putExtra("price", String.valueOf(CartItemClass.getPrice()));
                intent.putExtra("location","hotProducts");
                DouPrice = CartItemClass.getPrice();
                intent.putExtra("DouPrice", DouPrice);
                mCtx.startActivity(intent);
            }
        });*/


        /*Glide.with(mCtx)
                .load(CartItemClass.getImage())
                .into(CartItemViewHolder.imageView);*/

    }

    @Override
    public int getItemCount() {
        return cartItemClassList.size();
    }

    class CartItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewTitle, textViewDesc, textViewRating, textViewPrice;
        RelativeLayout relativeLayout;

        public CartItemViewHolder(View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.relative);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);

        }
    }
}
