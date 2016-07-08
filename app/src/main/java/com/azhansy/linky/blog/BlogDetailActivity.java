package com.azhansy.linky.blog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseActivity;
import com.azhansy.linky.blog.fragment.BlogDetailFragment;


/**
 * Created by SHU on 2016/7/8.
 */
public class BlogDetailActivity extends BaseActivity {
    private static String BLOGDETAILACTIVITY_URL = "StringURL";
    @Override
    public int getLayoutResource() {
        return R.layout.activity_base_fragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getStringExtra(BLOGDETAILACTIVITY_URL);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, BlogDetailFragment.getInstance(url)).commit();
    }

    public static void launch(Context context, String url) {
        Intent intent = new Intent(context, BlogDetailActivity.class);
        intent.putExtra(BLOGDETAILACTIVITY_URL, url);
        context.startActivity(intent);
    }
}
