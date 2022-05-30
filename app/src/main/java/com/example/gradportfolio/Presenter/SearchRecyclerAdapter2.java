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

import com.example.gradportfolio.View.Category_1;
import com.example.gradportfolio.Model.MenuList;
import com.example.gradportfolio.R;
import com.example.gradportfolio.View.MenuSearch;

import java.util.ArrayList;

public class SearchRecyclerAdapter2 extends RecyclerView.Adapter<SearchRecyclerAdapter2.ViewHolder> { //카테고리 부분 리사이클러뷰
   ArrayList<MenuList> mList;
   MenuSearch activity2;
   private Intent intent;
   public SearchRecyclerAdapter2(ArrayList<MenuList> mList, MenuSearch activity2){
      this.mList = mList;
      this.activity2 = activity2;
   }
   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
      View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_item_search,null);
      ViewHolder viewHolder = new ViewHolder(view);
      return viewHolder;
   }


   @Override
   public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView")final int position){
      holder.menu_Name.setText(mList.get(position).getImgName());
      holder.menu_Img.setImageResource(mList.get(position).getImgId2());

      holder.menu_Img.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            intent = new Intent(v.getContext(), Category_1.class);
            intent.putExtra("number" ,position);
            intent.putExtra("img_name", mList.get(position).getImgName());

            v.getContext().startActivity(intent);
         }
      });

   }
   @Override
   public int getItemCount(){return mList.size();}

   public class ViewHolder extends RecyclerView.ViewHolder{
      TextView menu_Name;
      ImageView menu_Img;
      public ViewHolder(@NonNull View itemView){
         super(itemView);
         this.menu_Name = itemView.findViewById(R.id.text_menu);
         this.menu_Img = itemView.findViewById(R.id.imgView_menu);
      }
   }



}

