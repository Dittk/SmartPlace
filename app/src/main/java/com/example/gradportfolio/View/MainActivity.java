package com.example.gradportfolio.View;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gradportfolio.Model.ProductData;
import com.example.gradportfolio.Model.SqlLiteHelper;
import com.example.gradportfolio.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    static RequestQueue requestQueue; // 주로 1개만 선언
    public static ArrayList<ProductData> productList = new ArrayList<>();
    SqlLiteHelper helper;
    public static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (requestQueue == null) {
            // requestQueue 초기화
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        makeRequest(); //DB접근 후 모든 DB를 객체리스트에 담아옴
        startSqlLite();

        bottomNavigationView = findViewById(R.id.bottom_menu);

        getSupportFragmentManager().beginTransaction().add(R.id.main_frame, new Home()).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.first_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Home()).commit();
                        break;
                    case R.id.second_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new MenuSearch()).commit();
                        break;
                    case R.id.third_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new ShoppingBasket()).commit();
                        break;
//                    case R.id.fourth_tab:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new MypageLogin()).commit();
//                        break;
                }
                return true;
            }
        });
    }

    public static void makeRequest() {
        String url = "http://121.150.127.163:3000/data";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // 응답 성공
                Document doc = Jsoup.parse(response);
                String text1 = doc.text();
                String text2 = text1.substring(9);
                try {
                    JSONObject jsonObject = new JSONObject(text2);
                    JSONArray jsonArray = jsonObject.getJSONArray("product");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject output = jsonArray.getJSONObject(i);
                        ProductData product = new ProductData();
                        product.setId(output.getString("id"));
                        product.setUrl(output.getString("url"));
                        product.setBrand_name(output.getString("brand_name"));
                        product.setProduct_name(output.getString("product_name"));
                        product.setPrice(output.getString("price"));
                        product.setDetails(output.getString("details"));
                        product.setUrl2(output.getString("url2"));
                        product.setUrl3(output.getString("url3"));
                        product.setUrl4(output.getString("url4"));
                        product.setPurchaseUrl(output.getString("purchase"));
                        productList.add(product);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // POST 방식을 사용할 때 요청 파라미터를 전달하고자 할 때 사용한다.
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        request.setShouldCache(false);  // 이전 응답 결과를 사용하지 않는다.
        requestQueue.add(request);
    }

    void startSqlLite() {
        helper = new SqlLiteHelper(MainActivity.this, "InternalDb2.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        WebView webView = (WebView)dialog.findViewById(R.id.webView2);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            }
        });

        String html = "<div style=\"height: 50px\">\n" +
                "\t\t\t\t<script src=\"https://ads-partners.coupang.com/g.js\"></script>\n" +
                "\t\t\t\t<script>\n" +
                "\t\t\t\t\tnew PartnersCoupang.G({\n" +
                "\t\t\t\t\t\t\"id\": 1,\n" +
                "\t\t\t\t\t\t\"height\": 240,\n" +
                "\t\t\t\t\t\t\"width\": 270,\n" +
                "\t\t\t\t\t\t\"bordered\": true\n" +
                "\t\t\t\t\t});\n" +
                "\t\t\t\t</script>\t\t\n" +
                "\t\t</div>";
        webView.loadData(html, "text/html", "UTF8");


        dialog.show();
        Button button = (Button)dialog.findViewById(R.id.backbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button button1 = (Button)dialog.findViewById(R.id.exitbtn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productList.clear();
                finish();

            }
        });
    }
}