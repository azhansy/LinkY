package com.azhansy.linky.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;

/**
 * 搜索需要高亮的数据
 * Created by terryyhl on 15/5/27.
 */
public class SearchHighlightModel {

    private SpannableString spannableString;

    public SpannableString getSpannableString() {
        return spannableString;
    }

    public void setSpannableString(String name, String queryString, int colorResource) {
        spannableString = getSpannableString(name, queryString, colorResource);
    }

    public void setSpannableString(Context context, String name, String queryString, int res) {
        spannableString = getSpannableString(context, name, queryString, res);
    }


    protected SpannableString getSpannableString(String text, String queryString, int colorResource) {
        SpannableString spanText = new SpannableString(text);
        if (TextUtils.isEmpty(queryString)) return spanText;
        int startIndex = text.toLowerCase().indexOf(queryString.toLowerCase());
        int endIndex = startIndex + queryString.length();
        if (startIndex > -1) {

            spanText.setSpan(new ForegroundColorSpan(colorResource), startIndex,
                    endIndex, 0);
        }

        return spanText;
    }


    protected SpannableString getSpannableString(Context context, String text, String queryString, int res) {
        SpannableString spanText = new SpannableString(text);
        if (TextUtils.isEmpty(queryString)) return spanText;
        int startIndex = text.toLowerCase().indexOf(queryString.toLowerCase());
        int endIndex = startIndex + queryString.length();
        TextAppearanceSpan textStyle = new TextAppearanceSpan(context, res);
        if (startIndex > -1) {
            spanText.setSpan(textStyle, startIndex, endIndex, 0);
        }

        return spanText;
    }

}
