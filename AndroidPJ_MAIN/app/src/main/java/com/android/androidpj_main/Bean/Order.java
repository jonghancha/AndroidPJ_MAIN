package com.android.androidpj_main.Bean;

public class Order {
    ///////////////////////////////////////////////////////////////////////////
//
// 21.01.14 세미 생성
//
///////////////////////////////////////////////////////////////////////////

    //Field (필드) = Bean 에선 이렇게 한줄 씩 쓰는 것이 좋다.

    int ordNo;
    String ordDate;
    String ordDelivery;
    String prdName;
    int prdPrice;
    String prdFilename;

    // Constructor (생성자)


    public Order(int ordNo, String ordDate, String ordDelivery, String prdName, int prdPrice, String prdFilename) {
        this.ordNo = ordNo;
        this.ordDate = ordDate;
        this.ordDelivery = ordDelivery;
        this.prdName = prdName;
        this.prdPrice = prdPrice;
        this.prdFilename = prdFilename;
    }

    public int getOrdNo() {
        return ordNo;
    }

    public void setOrdNo(int ordNo) {
        this.ordNo = ordNo;
    }

    public String getOrdDate() {
        return ordDate;
    }

    public void setOrdDate(String ordDate) {
        this.ordDate = ordDate;
    }

    public String getOrdDelivery() {
        return ordDelivery;
    }

    public void setOrdDelivery(String ordDelivery) {
        this.ordDelivery = ordDelivery;
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

    public String getPrdFilename() {
        return prdFilename;
    }

    public void setPrdFilename(String prdFilename) {
        this.prdFilename = prdFilename;
    }
}
