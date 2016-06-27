package com.azhansy.linky.column;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseActivity;
import com.azhansy.linky.column.helper.ItemDragHelperCallback;
import com.azhansy.linky.utils.Config;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by SHU on 2016/6/23.
 * 更改栏目
 */
public class ChangeChannelActivity extends BaseActivity implements IChangeChannel{
    @Bind(R.id.rv_channel)
    RecyclerView mRv;
    private ArrayList<Config.Channel> savedChannel = new ArrayList<>();
    private ArrayList<Config.Channel> otherChannel = new ArrayList<>();
    private IChangeChannelPresenter mIChangeChannelPresenter;
    private ChannelAdapter mChannelAdapter;


    @Override
    public int getLayoutResource() {
        return R.layout.activity_change_channel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIChangeChannelPresenter = new ChangeChannelPresenterImpl(this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRv.setLayoutManager(linearLayoutManager);
        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRv);
        mRv.setHasFixedSize(true);
        mRv.setNestedScrollingEnabled(false);
        mChannelAdapter = new ChannelAdapter(this, helper, savedChannel, otherChannel);
        mRv.setAdapter(mChannelAdapter);
        mIChangeChannelPresenter.getChannel();
    }

    public static void launch(Context context){
        Intent intent = new Intent(context, ChangeChannelActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void showChannel(ArrayList<Config.Channel> savedChannel, ArrayList<Config.Channel> otherChannel) {
        this.savedChannel.addAll(savedChannel);
        this.otherChannel.addAll(otherChannel);
        mChannelAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        mIChangeChannelPresenter.saveChannel(savedChannel);
        super.onBackPressed();
    }
}
