package com.euromonitor.storecheck.model;
import android.renderscript.Sampler;

/**
 * Created by Shashwat.Bajpai on 19/04/2016.
 */
public class ProductDetail
{
    double price;
    double pack_size;
    public void setPrice(Double price)
    {
        this.price=price;
    }
    public String getPrice()
    {
        return String.valueOf(price);
    }
    public void setPack_size(Double pack_size)
    {
        this.pack_size=pack_size;
    }
    public String getPack_size()
    {
        return String.valueOf(pack_size);
    }
}
