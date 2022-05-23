package com.example.gradportfolio.View;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
    private String title, product_name, product_price, image, details, image2, image3, image4;
    private String sql;
    private Cursor c;

    private Button buttonAdd;
    private ImageView imageView1, imageView2, imageView3;
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

        imageView1 = findViewById(R.id.item_detail_image1);
        imageView2 = findViewById(R.id.item_detail_image2);
        imageView3 = findViewById(R.id.item_detail_image3);

        buttonAdd = findViewById(R.id.basket_add_button);

        textView1 = findViewById(R.id.item_detail_text1);
        textView2 = findViewById(R.id.item_detail_text2);
        textView3 = findViewById(R.id.item_detail_text3);
        textView4 = findViewById(R.id.item_detail_text4);
        textView5 = findViewById(R.id.details);


                Glide.with(MenuSearch.ct).load(image).into(imageView1);//클릭 시 가져오는 이미지
                textView1.setText(title); // 클릭 시 가져오는 타이틀
                textView2.setText(product_name); // 클릭 시 가져오는 제품 이름
                textView3.setText(product_price);
                textView4.setText(Currency.getInstance(Locale.KOREA).getSymbol());
                textView5.setText(details);
                Glide.with(MenuSearch.ct).load(image2).into(imageView2);
                Glide.with(MenuSearch.ct).load(image3).into(imageView3);


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer temp = 0;

                ContentValues values = new ContentValues();
                values.put("url",image);
                values.put("brand_name",title);
                values.put("product_name",product_name);
                values.put("price", product_price);



                sql = "select url, brand_name, product_name, price from ShoppingBasket";
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
    }




}
