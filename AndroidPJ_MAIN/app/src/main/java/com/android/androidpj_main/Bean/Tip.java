package com.android.androidpj_main.Bean;

// 21.01.13 지은 추가  ***************************************
public class Tip {

    //Field (필드) = Bean 에선 이렇게 한줄 씩 쓰는 것이 좋다.
    int tipNo;
    String tipTitle;
    String tipContent;
    String tipImg;


    // Constructor (생성자)


    public Tip(int tipNo, String tipTitle, String tipContent, String tipImg) {
        this.tipNo = tipNo;
        this.tipTitle = tipTitle;
        this.tipContent = tipContent;
        this.tipImg = tipImg;
    }

    public int getTipNo() {
        return tipNo;
    }

    public void setTipNo(int tipNo) {
        this.tipNo = tipNo;
    }

    public String getTipTitle() {
        return tipTitle;
    }

    public void setTipTitle(String tipTitle) {
        this.tipTitle = tipTitle;
    }

    public String getTipContent() {
        return tipContent;
    }

    public void setTipContent(String tipContent) {
        this.tipContent = tipContent;
    }

    public String getTipImg() {
        return tipImg;
    }

    public void setTipImg(String tipImg) {
        this.tipImg = tipImg;
    }
}// ----
