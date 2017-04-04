package com.example.home.makemebeautiful.choosestylist.stylist_details_screen;

import com.example.home.makemebeautiful.utils.handlers.FontsManager.FontLoader;

class TextViewDetails {
    private int rIndexNumber;
    private FontLoader font;
    private String text;

    TextViewDetails(int rIndexNumber, FontLoader font, String text) {
        this.rIndexNumber = rIndexNumber;
        this.font = font;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    int getRIndexNumber() {
        return rIndexNumber;
    }

    FontLoader getFont() {
        return font;
    }
}
