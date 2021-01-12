package com.android.androidpj_main.Bean;

public class Cart {

///////////////////////////////////////////////////////////////////////////
//
// 21.01.12 종한 생성
//
///////////////////////////////////////////////////////////////////////////


    //Field (필드) = Bean 에선 이렇게 한줄 씩 쓰는 것이 좋다.
    String prdBrand;
    String prdName;
    int prdPrice;
    int cartQty;
    String prdFilename;


    // Constructor (생성자)


    public Cart(String prdBrand, String prdName, int prdPrice, int cartQty, String prdFilename) {
        this.prdBrand = prdBrand;
        this.prdName = prdName;
        this.prdPrice = prdPrice;
        this.cartQty = cartQty;
        this.prdFilename = prdFilename;
    }

    public String getPrdBrand() {
        return prdBrand;
    }

    public void setPrdBrand(String prdBrand) {
        this.prdBrand = prdBrand;
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public int getPrdPrice() {
        return prdPrice;
    }

    public void setPrdPrice(int prdPrice) {
        this.prdPrice = prdPrice;
    }

    public int getCartQty() {
        return cartQty;
    }

    public void setCartQty(int cartQty) {
        this.cartQty = cartQty;
    }

    public String getPrdFilename() {
        return prdFilename;
    }

    public void setPrdFilename(String prdFilename) {
        this.prdFilename = prdFilename;
    }
}// ----
