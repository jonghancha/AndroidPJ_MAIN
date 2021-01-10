package com.android.androidpj_main.Bean;

public class User {
    //Field (필드) = Bean 에선 이렇게 한줄 씩 쓰는 것이 좋다.
    String userId;
    String userPw;
    String userName;
    String userPhone;
    String userEmail;

    // Constructor (생성자)


    public User(String userId, String userPw, String userName, String userPhone, String userEmail) {

        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}

