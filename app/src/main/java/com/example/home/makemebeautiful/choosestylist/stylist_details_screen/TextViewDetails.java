package com.example.home.makemebeautiful.choosestylist.stylist_details_screen;

/**
 * Created by home on 8/16/2016.
 */
public class TextViewDetails {
    private int rIndexNumber;
    private String text;

    public TextViewDetails(int rIndexNumber, String text) {
        this.rIndexNumber = rIndexNumber;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public int getRIndexNumber() {
        return rIndexNumber;
    }
}
