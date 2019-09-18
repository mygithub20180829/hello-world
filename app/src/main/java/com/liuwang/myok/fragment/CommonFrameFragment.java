package com.liuwang.myok.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.liuwang.myok.GSONActivity;
import com.liuwang.myok.NativeJsonPraseActivity;
import com.liuwang.myok.R;
import com.liuwang.myok.adpter.CommonFrameFragmentAdapter;
import com.liuwang.myok.base.BaseFragment;

public class CommonFrameFragment extends BaseFragment {

    private ListView mListView;
    private String[] datas;
    private CommonFrameFragmentAdapter adapter;
    private static final String TAG=CommonFrameFragment.class.getSimpleName();

    @Override
    protected View initView() {
        Log.e(TAG, "常用框架Fragment页面被初始化了.....");
        View view=View.inflate(mContext, R.layout.fragment_common_frame,null);
        mListView=view.findViewById(R.id.listview);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String data=datas[i];
                if (data.toLowerCase().equals("nativejsonprase")){
                    Intent intent=new Intent(mContext,NativeJsonPraseActivity.class);
                    mContext.startActivity(intent);
                } else if (data.toLowerCase().equals("gson")){
                    Intent intent = new Intent(mContext, GSONActivity.class);
                    mContext.startActivity(intent);
                }
                Toast.makeText(mContext,"data=="+data,Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        Log.e(TAG, "常用框架Fragment数据被初始化了....");
        //准备数据
        datas=new String[]{"OKHttp", "xUtils3","Retrofit2","Fresco","Glide","greenDao","RxJava","volley","Gson",
                "FastJson","picasso","evenBus","jcvideoplayer","pulltorefresh","Expandablelistview",
                "UniversalVideoView","....."};
        //设置适配器
        adapter=new CommonFrameFragmentAdapter(mContext,datas);
        mListView.setAdapter(adapter);
    }
}
