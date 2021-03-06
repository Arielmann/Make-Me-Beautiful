package com.example.home.makemebeautiful.profile.profilemodels;

import org.parceler.Parcel;

@Parcel
public class Stylist extends User {
    private String company;
    private String website;

    public Stylist(int id, String name, String company, String location, String profileImageUrl, String description, String website, String token) {
        super(id, name, location, profileImageUrl, description, token);
        this.company = company;
        this.website = website;
    }

    //For Parcelable
    public Stylist() {
        super();
    }

    public java.lang.String getCompany() {
        return company;
    }

    public void setCompany(java.lang.String company) {
        this.company = company;
    }

    public java.lang.String getWebsite() {
        return website;
    }

    public void setWebsite(java.lang.String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        String stylistsObjectDetails = "Name: " + getName() +
                ", Location: " + getLocation() +
                ", Url: " + getProfileImageUrl();
        return stylistsObjectDetails;
    }
}

