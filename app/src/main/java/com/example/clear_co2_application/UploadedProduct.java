package com.example.clear_co2_application;

import java.util.Comparator;

public class UploadedProduct
{
    private String mName;
    private String mImageUrl;
    private int mVerified;
    private String mCarbonValue;
    private String mInfo;

    private String deleteKey;
    private String stepDeleteKey;
    private String prodDeleteKey;



    public UploadedProduct()
    {

    }

    public UploadedProduct(String name , String image, int verified, String carbonValue, String info)
    {
        mName = name;
        mImageUrl = image;
        mVerified = verified;
        mCarbonValue = carbonValue;
        mInfo = info;
    }

    // Sorting list items. But  it is not working properly.
/*
    public static Comparator<UploadedProduct> AZComparator = new Comparator<UploadedProduct>() {
        @Override
        public int compare(UploadedProduct p1, UploadedProduct p2) {
            return p1.mName.compareTo(p2.mName);
        }
    };

    public static Comparator<UploadedProduct> ZAComparator = new Comparator<UploadedProduct>() {
        @Override
        public int compare(UploadedProduct p1, UploadedProduct p2) {
            return p2.getName().compareTo(p1.getName());
        }
    };

    public static Comparator<UploadedProduct> DateAscComparator = new Comparator<UploadedProduct>() {
        @Override
        public int compare(UploadedProduct p1, UploadedProduct p2) {
            return p1.getName().compareToIgnoreCase(p2.getName());
        }
    };
     */


    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getDeleteKey() {
        return deleteKey;
    }

    public void setDeleteKey(String deleteKey) {
        this.deleteKey = deleteKey;
    }

    public String getStepDeleteKey() {
        return stepDeleteKey;
    }

    public void setStepDeleteKey(String stepDeleteKey) {
        this.stepDeleteKey = stepDeleteKey;
    }

    public int getVerified() { return mVerified; }

    public void setVerified(int mVerified) { this.mVerified = mVerified; }

    public String getCarbonValue() {
        return mCarbonValue;
    }

    public void setCarbonValue(String mCarbonValue) {
        this.mCarbonValue = mCarbonValue;
    }

    public String getProdDeleteKey() {
        return prodDeleteKey;
    }

    public void setProdDeleteKey(String prodDeleteKey) {
        this.prodDeleteKey = prodDeleteKey;
    }

    public String getmInfo() {
        return mInfo;
    }

    public void setmInfo(String mInfo) {
        this.mInfo = mInfo;
    }
}
