package com.example.gradportfolio.Model;

public class CategoryData {
    private String productName;
    private String brand_title;
    private String product_price;
    private String imageUrl;
    private String details;
    private String imageUrl2;
    private String imageUrl3;
    private String imageUrl4;
    private String purchase_url;

    public CategoryData(String productName, String brand_title, String product_price, String imageUrl,
                      String details, String imageUrl2, String imageUrl3, String imageUrl4, String purchase_url)
    {
        this.productName = productName;
        this.brand_title = brand_title;
        this.product_price = product_price;
        this.imageUrl = imageUrl;
        this.details = details;
        this.imageUrl2 = imageUrl2;
        this.imageUrl3 = imageUrl3;
        this.imageUrl4 = imageUrl4;
        this.purchase_url = purchase_url;
    }

    public String getProductName(){
        return productName;
    }
    public String getBrand_title(){
        return brand_title;
    }
    public String getProduct_price(){
        return product_price;
    }
    public String getImageUrl(){
        return imageUrl;
    }
    public String getDetails(){
        return details;
    }
    public String getImageUrl2(){
        return imageUrl2;
    }
    public String getImageUrl3(){
        return imageUrl3;
    }
    public String getImageUrl4(){
        return imageUrl4;
    }
    public String getPurchaseUrl() {
        return purchase_url;
    }




    public void setProductName(String productName){
        this.productName = productName;
    }

    public void setBrand_title(String brand_title){
        this.brand_title = brand_title;
    }

    public void setProduct_price(String product_price){
        this.product_price = product_price;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public void setDetails(String details){
        this.details = details;
    }

    public void setImageUrl2(String imageUrl2){
        this.imageUrl2 = imageUrl2;
    }

    public void setImageUrl3(String imageUrl3){
        this.imageUrl3 = imageUrl3;
    }

    public void setImageUrl4(String imageUrl4){
        this.imageUrl4 = imageUrl4;
    }

    public void setPurchaseUrl(String purchase_url) {
        this.purchase_url = purchase_url;
    }





}