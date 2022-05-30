package com.example.gradportfolio.Presenter;

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
import com.example.gradportfolio.Model.SearchData;
import com.example.gradportfolio.R;
import com.example.gradportfolio.View.MenuSearch;
import com.example.gradportfolio.View.ProductDetail;

import java.util.ArrayList;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> { //하단 모든 제품 리사이클러뷰
    ArrayList<SearchData> productItemArrayList;
    MenuSearch activity;
    private Intent intent;
    public SearchRecyclerAdapter(ArrayList<SearchData> productItemArrayList, MenuSearch activity) {
        this.productItemArrayList = productItemArrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyccler_search,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.productName.setText(productItemArrayList.get(position).getProductName());
        holder.productPrice.setText(productItemArrayList.get(position).getProduct_price());
        holder.productTitle.setText(productItemArrayList.get(position).getBrand_title());
        Glide.with(MenuSearch.ct).load(productItemArrayList.get(position).getImageUrl()).into(holder.image1);

        holder.image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), ProductDetail.class);
                intent.putExtra("number" ,position);
                intent.putExtra("title", productItemArrayList.get(position).getBrand_title());
                intent.putExtra("image",productItemArrayList.get(position).getImageUrl());
                intent.putExtra("product_name", productItemArrayList.get(position).getProductName());
                intent.putExtra("price",productItemArrayList.get(position).getProduct_price());
                intent.putExtra("details",productItemArrayList.get(position).getDetails());
                intent.putExtra("image2",productItemArrayList.get(position).getImageUrl2());
                intent.putExtra("image3",productItemArrayList.get(position).getImageUrl3());
                intent.putExtra("image4",productItemArrayList.get(position).getImageUrl4());
                intent.putExtra("purchase",productItemArrayList.get(position).getPurchaseUrl());
                intent.putExtra("name","search");
                v.getContext().startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return productItemArrayList.size();
    }

    public void  filterList(ArrayList<SearchData> filteredList) {
        productItemArrayList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView productName;
        TextView productTitle;
        TextView productPrice;
        ImageView image1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.productName=itemView.findViewById(R.id.recycle_product_name);
            this.productTitle = itemView.findViewById(R.id.recycle_brand_title);
            this.productPrice = itemView.findViewById(R.id.recycle_product_price);
            this.image1 = itemView.findViewById(R.id.recycle_product_image);


        }
    }
}
