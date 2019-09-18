package com.liuwang.myok;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.liuwang.myok.adpter.OKHttpListAdapter;
import com.liuwang.myok.domain.DateBean;
import com.liuwang.myok.utils.CacheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;
import okhttp3.Request;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 在列表中请求图片
 */
public class OKHttpListActivity extends AppCompatActivity {

    private static final String TAG=OKHttpListActivity.class.getSimpleName();
    private ListView listView;
    private ProgressBar progressBar;
    private TextView tv_nodate;
    private OKHttpListAdapter adapter;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_list);
        findById();
        getDateFromNet();//请求网络
    }

    private void findById(){
        listView=findViewById(R.id.listview);
        progressBar=findViewById(R.id.progressBar);
        tv_nodate=findViewById(R.id.tv_nodate);
    }

    /**
     * 从网络中请求数据
     */
    private void getDateFromNet(){
        url ="http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        //得到缓存的数据
        String saveJson=CacheUtils.getString(this,url);
        if (!TextUtils.isEmpty(saveJson)){
            processedData(saveJson);
        }
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallBack());
    }

    private class MyStringCallBack extends StringCallback{

        @Override
        public void onBefore(Request request, int id) {
            super.onBefore(request, id);
            setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            super.onAfter(id);
            setTitle("sample-okhttp");
        }

        @Override
        public void onError(Call call, Exception e, int i) {
            e.printStackTrace();
            tv_nodate.setVisibility(View.VISIBLE);//显示
        }

        //成功后回调
        @Override
        public void onResponse(String s, int i) {
            //显示文本消息
            tv_nodate.setVisibility(View.GONE);//隐藏
            switch (i){
                case 100:
                    Toast.makeText(OKHttpListActivity.this,"http",Toast.LENGTH_LONG).show();
                    break;
                case 101:
                    Toast.makeText(OKHttpListActivity.this,"https",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
            //如果response不为空解释数据和显示数据
            if (s!=null){
                //缓存数据
                //第二个参数是连网的路径
                CacheUtils.putString(OKHttpListActivity.this,url,s);
                //请求数据
                processedData(s);
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress: "+progress);
        }
    }

    /**
     * 解析和显示数据
     * @param json
     */
    private void processedData(String json){
        //解析数据
        DateBean dateBean=parsedJson(json);
        //获取列表
        List<DateBean.ItemData> dates=dateBean.getTrailers();
        //如果列表数据不为空并且大于零
        if (dates!=null&&dates.size()>0){
            //有数据，显示适配器
            tv_nodate.setVisibility(View.GONE);//隐藏
            adapter=new OKHttpListAdapter(OKHttpListActivity.this,dates);
            listView.setAdapter(adapter);
        } else{
            tv_nodate.setVisibility(View.VISIBLE);//显示
        }
        //有数据没有数据都要隐藏，因为都在加载数据
        progressBar.setVisibility(View.GONE);
    }

    /**
     * 解析json数据
     * @param response
     * @return
     */
    private DateBean parsedJson(String response){//response为json数据
        DateBean dateBean=new DateBean();
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("trailers");//获取与key关联的JSONArray值
            if (jsonArray!=null && jsonArray.length()>0){
                List<DateBean.ItemData> trailers=new ArrayList<DateBean.ItemData>();
                dateBean.setTrailers(trailers);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObjectItem= (JSONObject) jsonArray.get(i);//获取与索引(i)关联的对象值
                    if (jsonObjectItem!=null){
                        DateBean.ItemData mediaItem=new DateBean.ItemData();
                        String movieName=jsonObjectItem.optString("movieName");
                        mediaItem.setMovieName(movieName);
                        String videoTitle = jsonObjectItem.optString("videoTitle");
                        mediaItem.setVideoTitle(videoTitle);
                        String imageUrl=jsonObjectItem.optString("coverImg");
                        mediaItem.setCoverImg(imageUrl);
                        String highUrl=jsonObjectItem.optString("highUrl");
                        mediaItem.setHightUrl(highUrl);
                        //把数据添加到集合
                        trailers.add(mediaItem);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dateBean;
    }
}
