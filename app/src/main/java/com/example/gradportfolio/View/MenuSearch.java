package com.example.gradportfolio.View;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gradportfolio.Model.MenuList;
import com.example.gradportfolio.Presenter.RecyclerAdapter;
import com.example.gradportfolio.Presenter.SearchRecyclerAdapter;
import com.example.gradportfolio.Model.SearchData;
import com.example.gradportfolio.Presenter.SearchRecyclerAdapter2;
import com.example.gradportfolio.R;

import java.sql.Array;
import java.util.ArrayList;


public class MenuSearch extends Fragment {
    ArrayList<SearchData> productItemArrayList, filteredList;
    SearchRecyclerAdapter searchRecyclerAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    EditText searchET;

    RecyclerView recyclerView2;
    ArrayList<MenuList> mList;
    SearchRecyclerAdapter2 searchRecyclerAdapter2;
    LinearLayoutManager linearLayoutManager2;
    SwipeRefreshLayout swipeRefreshLayout;

    public static Context ct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_menu_search1, container, false);

        intiProduct(rootView);

        secondList(rootView);

        ct = getContext();

        // Inflate the layout for this fragment
        return rootView;
    }

    private void secondList(ViewGroup rootView)
    {
        recyclerView2 = rootView.findViewById(R.id.recyclerview2);

        mList = new ArrayList<>();
        searchRecyclerAdapter2 = new SearchRecyclerAdapter2(mList, this);
        //linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false); //가로
        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView2.setAdapter(searchRecyclerAdapter2);

        mList.add(new MenuList("욕조", R.drawable.bath));
        mList.add(new MenuList("침대", R.drawable.bed));
        mList.add(new MenuList("의자", R.drawable.chair));
        mList.add(new MenuList("책상", R.drawable.desktop));
        mList.add(new MenuList("책장",R.drawable.bookcase));


        searchRecyclerAdapter2.notifyDataSetChanged();

    }



    private void intiProduct(ViewGroup rootView){
        recyclerView = rootView.findViewById(R.id.recyclerview);
        searchET = rootView.findViewById(R.id.edit_text);

        filteredList=new ArrayList<>();
        productItemArrayList = new ArrayList<>();

        searchRecyclerAdapter = new SearchRecyclerAdapter(productItemArrayList, this);

        //linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false); //가로
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(searchRecyclerAdapter);

        if(MainActivity.productList.size() != 0){
            try {
                for(int i=0; i < MainActivity.productList.size(); i++){
                    productItemArrayList.add(new SearchData(MainActivity.productList.get(i).getProduct_name(), MainActivity.productList.get(i).getBrand_name(), MainActivity.productList.get(i).getPrice(), MainActivity.productList.get(i).getUrl(),
                            MainActivity.productList.get(i).getDetails(), MainActivity.productList.get(i).getUrl2(),
                            MainActivity.productList.get(i).getUrl3(), MainActivity.productList.get(i).getUrl4(), MainActivity.productList.get(i).getPurchaseUrl()));
                }

            }
            catch(Exception e){
                Log.v("error", "로딩 오류");
            }
        }
        else{
            Toast.makeText(getContext(), "데이터 연결상태를 확인하세요.", Toast.LENGTH_SHORT).show();
        }
        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.swipe_layout_menu_search);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    if(MainActivity.productList.isEmpty()){
                        MainActivity.makeRequest();
                    }
                    for(int i=0; i < MainActivity.productList.size(); i++){
                        productItemArrayList.add(new SearchData(MainActivity.productList.get(i).getProduct_name(), MainActivity.productList.get(i).getBrand_name(), MainActivity.productList.get(i).getPrice(), MainActivity.productList.get(i).getUrl(),
                                MainActivity.productList.get(i).getDetails(), MainActivity.productList.get(i).getUrl2(),
                                MainActivity.productList.get(i).getUrl3(), MainActivity.productList.get(i).getUrl4(),MainActivity.productList.get(i).getPurchaseUrl()));
                    }
                    swipeRefreshLayout.setRefreshing(false);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "데이터 연결상태를 확인하세요.", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });


        searchRecyclerAdapter.notifyDataSetChanged();

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String searchText = searchET.getText().toString();
                searchFilter(searchText);

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void searchFilter(String searchText) {
        filteredList.clear();

        for (int i = 0; i < productItemArrayList.size(); i++) {
            if (productItemArrayList.get(i).getProductName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(productItemArrayList.get(i));
            }
        }

        searchRecyclerAdapter.filterList(filteredList);
    }




}
