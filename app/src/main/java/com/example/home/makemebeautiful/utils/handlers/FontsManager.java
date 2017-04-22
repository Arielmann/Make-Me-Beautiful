package com.example.home.makemebeautiful.utils.handlers;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.TextView;

public class FontsManager {

    public enum FontLoader {

        MONTSERRAT_BOLD("fonts/Montserrat-Bold.ttf"),
        MONTSERRAT_REGULAR("fonts/Montserrat-Regular.ttf"),
        QUIRLYCUES("fonts/Quirlycues.ttf"),
        MYRIAD_PRO_REGULAR("fonts/Myriad_Pro-Regular.ttf"),
        LATO_REGULAR("fonts/Lato-Regular.ttf");

        final String fontName;

        FontLoader(final String name) {
            this.fontName = name;
        }
    }


    public static void setUpFontOnTV(AssetManager assets, FontLoader fontLoader, TextView tv) {
        Typeface headlineFont = Typeface.createFromAsset(assets, fontLoader.fontName);
        tv.setTypeface(headlineFont);
    }
}
