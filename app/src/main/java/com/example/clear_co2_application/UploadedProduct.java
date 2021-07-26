package com.example.clear_co2_application;

public class UploadedProduct
{
    private String mName;
    private String mImageUrl;

    public UploadedProduct()
    {

    }

    public UploadedProduct(String name , String image)
    {
        mName = name;
        mImageUrl = image;
    }

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
}
