package com.example.gradportfolio.View;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.gradportfolio.Presenter.RecyclerAdapter;
import com.example.gradportfolio.R;

import java.util.Currency;
import java.util.Locale;

public class ProductDetail extends AppCompatActivity {

    private  Intent intent;
    private int number;
    private String title, product_name, product_price, image, details, image2, image3, image4, name, purchase;
    private String sql;
    private Cursor c;

    private Button buttonAdd,buttonPurchase;
    private ImageView imageView1, imageView2, imageView3, imageView4;
    private TextView textView1, textView2, textView3, textView4,textView5;

    private Context ct;

    protected  void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productdetail1);

        intent = getIntent();
        number = intent.getIntExtra("number", -1);
        title = intent.getStringExtra("title");
        image = intent.getStringExtra("image");
        product_name = intent.getStringExtra("product_name");
        product_price = intent.getStringExtra("price");
        details = intent.getStringExtra("details");
        image2 = intent.getStringExtra("image2");
        image3 = intent.getStringExtra("image3");
        image4 = intent.getStringExtra("image4");
        name = intent.getStringExtra("name");
        purchase = intent.getStringExtra("purchase");

        imageView1 = findViewById(R.id.item_detail_image1);
        imageView2 = findViewById(R.id.item_detail_image2);
        imageView3 = findViewById(R.id.item_detail_image3);
        imageView4 = findViewById(R.id.item_detail_image4);

        buttonAdd = findViewById(R.id.basket_add_button);
        buttonPurchase = findViewById(R.id.purchase_button);

        textView1 = findViewById(R.id.item_detail_text1);
        textView2 = findViewById(R.id.item_detail_text2);
        textView3 = findViewById(R.id.item_detail_text3);
        textView4 = findViewById(R.id.item_detail_text4);
        textView5 = findViewById(R.id.details);


        if(name.equals("basket")) {
            Glide.with(ShoppingBasket.ct).load(image).into(imageView1);//클릭 시 가져오는 이미지
            textView1.setText(title); // 클릭 시 가져오는 타이틀
            textView2.setText(product_name); // 클릭 시 가져오는 제품 이름
            textView3.setText(product_price);
            textView4.setText(Currency.getInstance(Locale.KOREA).getSymbol());
            textView5.setText(details);
            Glide.with(ShoppingBasket.ct).load(image2).into(imageView2);
            Glide.with(ShoppingBasket.ct).load(image3).into(imageView3);
            Glide.with(ShoppingBasket.ct).load(image4).into(imageView4);
        }
        else if(name.equals("search")){
            Glide.with(MenuSearch.ct).load(image).into(imageView1);//클릭 시 가져오는 이미지
            textView1.setText(title); // 클릭 시 가져오는 타이틀
            textView2.setText(product_name); // 클릭 시 가져오는 제품 이름
            textView3.setText(product_price);
            textView4.setText(Currency.getInstance(Locale.KOREA).getSymbol());
            textView5.setText(details);
            Glide.with(MenuSearch.ct).load(image2).into(imageView2);
            Glide.with(MenuSearch.ct).load(image3).into(imageView3);
            Glide.with(MenuSearch.ct).load(image4).into(imageView4);
        }
        else{
            Glide.with(Home.context).load(image).into(imageView1);//클릭 시 가져오는 이미지
            textView1.setText(title); // 클릭 시 가져오는 타이틀
            textView2.setText(product_name); // 클릭 시 가져오는 제품 이름
            textView3.setText(product_price);
            textView4.setText(Currency.getInstance(Locale.KOREA).getSymbol());
            textView5.setText(details);
            Glide.with(Home.context).load(image2).into(imageView2);
            Glide.with(Home.context).load(image3).into(imageView3);
            Glide.with(Home.context).load(image4).into(imageView4);
        }

        //미리보기
        Button preview = findViewById(R.id.preview_button);

       /* preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetail.this, UnityPlayerActivity.class);
                startActivity(intent);
            }
        }); */

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer temp = 0;

                ContentValues values = new ContentValues();
                values.put("url",image);
                values.put("brand_name",title);
                values.put("product_name",product_name);
                values.put("price", product_price);
                values.put("purchase", purchase);


                sql = "select url, brand_name, product_name, price, purchase from ShoppingBasket";
                c = MainActivity.db.rawQuery(sql, null);

                while(c.moveToNext()){
                    if(c.getString(c.getColumnIndex("product_name")).equals(product_name)){
                        temp = 1;
                        Toast.makeText(getApplicationContext(), "장바구니에 이미 존재합니다.", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                if(temp != 1) {
                    MainActivity.db.insert("ShoppingBasket", null, values);
                    Toast.makeText(getApplicationContext(), "장바구니에 추가 되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse(purchase));
                    view.getContext().startActivity(urlintent);
                }
                catch (Exception e){
                    Toast.makeText(view.getContext(), "데이터 연결상태를 확인하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }




}
