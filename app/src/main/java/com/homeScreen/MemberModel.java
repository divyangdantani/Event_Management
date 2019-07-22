package com.homeScreen;

/**
 * Created by Divyang Dantani on 3/8/2018.
 */

public class MemberModel {



    private String mName,mEmail,mFlatno,mMobileno;
    private String mImage;

    public MemberModel(String mName, String mEmail, String mFlatno, String mMobileno) {
        this.mName = mName;
        this.mEmail = mEmail;
        this.mFlatno = mFlatno;
        //this.mImage = mImage;
        this.mMobileno = mMobileno;
    }

    public String getmName() {
        return mName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmFlatno() {
        return mFlatno;
    }

   /* public String getmImage() {
        return mImage;
    }*/

    public String getmMobileno() {
        return mMobileno;
    }
}
