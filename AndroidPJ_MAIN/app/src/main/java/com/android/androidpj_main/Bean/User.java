package com.android.androidpj_main.Bean;

public class User {
    //Field (필드) = Bean 에선 이렇게 한줄 씩 쓰는 것이 좋다.
    String userEmail;
    String userPw;
    String userName;
    String userTel;
    String userFilename;
    String userGender;
    String userColor;

    // Constructor (생성자)
    public User(String userEmail, String userPw, String userName, String userTel, String userFilename, String userGender, String userColor) {
        this.userEmail = userEmail;
        this.userPw = userPw;
        this.userName = userName;
        this.userTel = userTel;
        this.userFilename = userFilename;
        this.userGender = userGender;
        this.userColor = userColor;
    }

    public User(String userColor) {
        this.userColor = userColor;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserFilename() {
        return userFilename;
    }

    public void setUserFilename(String userFilename) {
        this.userFilename = userFilename;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserColor() {
        return userColor;
    }

    public void setUserColor(String userColor) {
        this.userColor = userColor;
    }
}

