package com.example.test.Model;

//added by Lalit kumar on 20.march.2020 at 1:00 PM
public class UserDetails {
    private String username;
    private String email;
    private String mobileNumber;
    private boolean isLogin;
    private String id;
    private String gender;
    private String dob;
    private String pincode;
    private String remeber_token;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getRemeber_token() {
        return remeber_token;
    }

    public void setRemeber_token(String remeber_token) {
        this.remeber_token = remeber_token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", isLogin=" + isLogin +
                ", id='" + id + '\'' +
                ", gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", pincode='" + pincode + '\'' +
                ", remeber_token='" + remeber_token + '\'' +
                '}';
    }
}
