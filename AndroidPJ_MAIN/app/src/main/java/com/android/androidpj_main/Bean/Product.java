package com.android.androidpj_main.Bean;

public class Product {

///////////////////////////////////////////////////////////////////////////
//
// 21.01.08 세미 생성
//
///////////////////////////////////////////////////////////////////////////


    //Field (필드) = Bean 에선 이렇게 한줄 씩 쓰는 것이 좋다.
    int prdNo;
    String prdName;
    String prdColor;
    String ctgType;
    String prdBrand;
    int prdPrice;
    String prdFilename;
    String prdDFilename;
    String prdNFilename;


    // Constructor (생성자)

    public Product(int prdNo, String prdName, String prdColor, String ctgType, String prdBrand, int prdPrice, String prdFilename, String prdDFilename, String prdNFilename) {
        this.prdNo = prdNo;
        this.prdName = prdName;
        this.prdColor = prdColor;
        this.ctgType = ctgType;
        this.prdBrand = prdBrand;
        this.prdPrice = prdPrice;
        this.prdFilename = prdFilename;
        this.prdDFilename = prdDFilename;
        this.prdNFilename = prdNFilename;
    }

    public int getPrdNo() {
        return prdNo;
    }

    public void setPrdNo(int prdNo) {
        this.prdNo = prdNo;
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public String getPrdColor() {
        return prdColor;
    }

    public void setPrdColor(String prdColor) {
        this.prdColor = prdColor;
    }

    public String getCtgType() {
        return ctgType;
    }

    public void setCtgType(String ctgType) {
        this.ctgType = ctgType;
    }

    public String getPrdBrand() {
        return prdBrand;
    }

    public void setPrdBrand(String prdBrand) {
        this.prdBrand = prdBrand;
    }

    public int getPrdPrice() {
        return prdPrice;
    }

    public void setPrdPrice(int prdPrice) {
        this.prdPrice = prdPrice;
    }

    public String getPrdFilename() {
        return prdFilename;
    }

    public void setPrdFilename(String prdFilename) {
        this.prdFilename = prdFilename;
    }

    public String getPrdDFilename() {
        return prdDFilename;
    }

    public void setPrdDFilename(String prdDFilename) {
        this.prdDFilename = prdDFilename;
    }

    public String getPrdNFilename() {
        return prdNFilename;
    }

    public void setPrdNFilename(String prdNFilename) {
        this.prdNFilename = prdNFilename;
    }
}
