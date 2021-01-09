package com.android.androidpj_main.Bean;

// Bean 이라서 상속(extends) 할 필요성이 없다.
public class Youtube {

    //Field (필드) = Bean 에선 이렇게 한줄 씩 쓰는 것이 좋다.
    String ytNo;
    String ytCtg;
    String ytName;
    String ytContent;
    String ytUrl;
    String ytImg;

    // Constructor (생성자)


    public Youtube(String ytNo, String ytCtg, String ytName, String ytContent, String ytUrl, String ytImg) {
        this.ytNo = ytNo;
        this.ytCtg = ytCtg;
        this.ytName = ytName;
        this.ytContent = ytContent;
        this.ytUrl = ytUrl;
        this.ytImg = ytImg;
    }

    public String getYtNo() {
        return ytNo;
    }

    public void setYtNo(String ytNo) {
        this.ytNo = ytNo;
    }

    public String getYtCtg() {
        return ytCtg;
    }

    public void setYtCtg(String ytCtg) {
        this.ytCtg = ytCtg;
    }

    public String getYtContent() {
        return ytContent;
    }

    public void setYtContent(String ytContent) {
        this.ytContent = ytContent;
    }

    public String getYtName() {
        return ytName;
    }

    public void setYtName(String ytName) {
        this.ytName = ytName;
    }

    public String getYtUrl() {
        return ytUrl;
    }

    public void setYtUrl(String ytUrl) {
        this.ytUrl = ytUrl;
    }

    public String getYtImg() {
        return ytImg;
    }

    public void setYtImg(String ytImg) {
        this.ytImg = ytImg;
    }
}
