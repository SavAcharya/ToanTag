package com.mphasis.toantag;

/**
 * Created by Sourav.Bhattacharya on 6/29/2016.
 */
public class ProductBean {

    /**
     * [ { "Product_Discount": "10", "Product_price": "36500", "Product_name": "Samsung Mobile S4 Model" },
     *   { "Product_Discount": "5", "Product_price": "1500", "Product_name": "LED Bulb 3 Pairs" } ]
     *
     * Product_Discount : 10
     * Product_price : 36500
     * Product_name : Samsung Mobile S4 Model
     */

    private String Product_Discount;
    private String Product_price;
    private String Product_name;

    public String getProduct_Discount() {
        return Product_Discount;
    }

    public void setProduct_Discount(String Product_Discount) {
        this.Product_Discount = Product_Discount;
    }

    public String getProduct_price() {
        return Product_price;
    }

    public void setProduct_price(String Product_price) {
        this.Product_price = Product_price;
    }

    public String getProduct_name() {
        return Product_name;
    }

    public void setProduct_name(String Product_name) {
        this.Product_name = Product_name;
    }
}
