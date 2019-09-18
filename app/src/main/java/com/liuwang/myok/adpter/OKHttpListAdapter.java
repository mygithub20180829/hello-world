package com.liuwang.myok.adpter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.liuwang.myok.R;
import com.liuwang.myok.domain.DateBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import okhttp3.Call;

import java.util.List;

import static android.content.ContentValues.TAG;

public class OKHttpListAdapter extends BaseAdapter {

    private Context context;
    private List<DateBean.ItemData> dates;

    public OKHttpListAdapter(Context context, List<DateBean.ItemData> dates) {
        this.context = context;
        this.dates = dates;
    }

    @Override
    public int getCount() {
        return dates.size();
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        final viewHolder viewHolder;
        if (view==null){
            //inflate方法的作用是将一个xml布局文件变成一个view对象
            view=View.inflate(context, R.layout.item_okhttp_list_image,null);
            viewHolder=new viewHolder();
            viewHolder.iv_icon=view.findViewById(R.id.iv_icon);
            viewHolder.tv_name=view.findViewById(R.id.tv_name);
            viewHolder.tv_desc=view.findViewById(R.id.tv_desc);
            /**
             * 1、setTag(object)表示给View添加一个格外的数据，以后可以用getTag()将这个数据取出来
             * 2、setTag(object)可以用在多个Button添加一个监听器，
             * 每个Button都设置不同的setTag,这个监听器就通过getTag来分辨是哪个Button 被按下
             */
            view.setTag(viewHolder);
        } else {
            viewHolder= (OKHttpListAdapter.viewHolder) view.getTag();
        }
        //根据位置得到数据
        DateBean.ItemData itemData=dates.get(position);
        viewHolder.tv_name.setText(itemData.getMovieName());
        viewHolder.tv_desc.setText(itemData.getVideoTitle());
        //在列表中使用okhttp-utils请求图片
        OkHttpUtils
                .get()
                .url(itemData.getCoverImg())
                .tag(this)
                .build()
                .connTimeOut(20000)//链接超时
                .readTimeOut(20000)//读取超时
                .writeTimeOut(20000)//写入超时
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {

                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int i) {
                        Log.e(TAG, "onResponse: complete" );
                        viewHolder.iv_icon.setImageBitmap(bitmap);
                    }
                });
        return view;
    }

    static class viewHolder{
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_desc;
    }
}
