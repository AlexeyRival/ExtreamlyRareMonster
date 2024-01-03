package com.rivalsoftware.extreamlyraremonster;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public enum BaseMonsterData {
    ODDISH(R.drawable.oddish,"Оддиш"),
    RATTATA(R.drawable.rattata,"Раттата"),
    SANDSHREW(R.drawable.sandshrew,"Сендшрю"),

    ;
    public int resource;
    public String name;

    BaseMonsterData(int resource, String name) {
        this.resource=resource;
        this.name=name;
    }
}
