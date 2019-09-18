package com.liuwang.myok.adpter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommonFrameFragmentAdapter extends BaseAdapter {

    private final Context mContext;
    private final String[] mDatas;

    public CommonFrameFragmentAdapter(Context mContext,String[] mDatas) {
        this.mContext=mContext;
        this.mDatas=mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView=new TextView(mContext);
        textView.setPadding(10,10,0,10);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(20);
        textView.setText(mDatas[i]);
        return textView;
    }
}
