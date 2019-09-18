package com.liuwang.myok;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.*;


import java.io.File;
import java.io.IOException;

public class OKHttpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    protected static final int GET = 1;//get请求
    protected static final int POST = 2;//POST请求
    private Button btn_get_post;//get和post请求
    private TextView tv_result;//显示请求数据
    private OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");//编码配置
    private Button btn_get_okhttputils;//使用okhttp-utils的get和post请求数据
    private Button btn_downloadfile;//使用okhttp-utils的下载大文件
    private ProgressBar mProgressBar;//进度条
    private Button btn_uploadfile;//使用okhttp-utils上传文件
    private ImageView iv_icon;//存放请求的图片
    private Button btn_image;//请求单张图片
    private Button btn_image_list;//请求列表图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        btn_get_post=findViewById(R.id.btn_get_post);
        tv_result=findViewById(R.id.tv_result);
        btn_get_post.setOnClickListener(this);
        btn_get_okhttputils=findViewById(R.id.btn_get_okhttputils);
        btn_get_okhttputils.setOnClickListener( this);//设置点击事件
        btn_downloadfile= findViewById(R.id.btn_downloadfile);
        btn_downloadfile.setOnClickListener( this);//设置点击事件
        mProgressBar= findViewById(R.id.progressBar);
        btn_uploadfile= findViewById(R.id.btn_uploadfile);
        btn_uploadfile.setOnClickListener( this);//设置点击事件
        iv_icon= findViewById(R.id.iv_icon);
        btn_image= findViewById(R.id.btn_image);
        btn_image.setOnClickListener( this);//设置点击事件
        btn_image_list= findViewById(R.id.btn_image_list);
        btn_image_list.setOnClickListener( this);//设置点击事件
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //使用原生态的okhttp请求网络数据
            case R.id.btn_get_post:
                tv_result.setText(" ");
                getDateFromPost();
                break;
            case R.id.btn_get_okhttputils:
                postDateByOkhttpUtils();
                break;
            case R.id.btn_downloadfile:
                downloadFile();
                break;
            case R.id.btn_uploadfile:
                multiFileUpload();
                break;
            case R.id.btn_image:
                getImage();
                break;
            case R.id.btn_image_list:
                Intent intent=new Intent();
                intent.setClass(OKHttpActivity.this,OKHttpListActivity.class);
                startActivity(intent);
        }
    }

    /**
     * 使用get网络请求数据
     * 创建一个子线程
     */
    private void getDateFromGet(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    String result=get("http://api.m.mtime.cn/PageSubArea/TrailerList.api");
                    Log.i("",result);
                    Message msg=Message.obtain();
                    msg.what=GET;
                    msg.obj=result;
                    handler.sendMessage(msg);
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void getDateFromPost(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    String result=post("http://api.m.mtime.cn/PageSubArea/TrailerList.api","");
                    Log.i("", result);
                    Message msg=Message.obtain();
                    msg.what=POST;
                    msg.obj=result;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 在handler中显示数据
     */
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GET:
                    tv_result.setText((CharSequence) msg.obj);
                    break;
                case POST:
                    tv_result.setText((CharSequence) msg.obj);
                    break;
            }
        }
    };

    /**
     * get请求
     * 注意：只能在子线程里执行
     *
     * @param url 网络连接
     * @return
     * @throws IOException
     */
    private String get(String url) throws IOException{
        Request request=new Request.Builder()
                .url(url)
                .build();
        //把request传进client
        //execute()执行线程
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * okhttp3的post请求
     *
     * 也是像get请求一样，要在子线程请求
     * @param url       网络连接
     * @param json      要请求的数据
     * @return
     * @throws IOException
     */
    private String post(String url,String json)throws IOException{
        RequestBody body=RequestBody.create(JSON,json);
        Request request=new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response=client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 使用okhttp-utils的get请求网络文本数据
     */
    private void getDateByOkhttpUtils(){
        String url ="http://api.m.mtime.cn/PageSubArea/TrailerList.api";

        OkHttpUtils.get().url(url)
                .id(100)
                .build()
                .execute(new MyStringCallBack());
    }

    private void postDateByOkhttpUtils(){
        String url="http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        OkHttpUtils.post().url(url)
                .id(100)//请求的id
                .build()
                .execute(new MyStringCallBack());
    }

    private class MyStringCallBack extends StringCallback {

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
            tv_result.setText("onError:"+e.getMessage());
        }

        @Override
        public void onResponse(String s, int i) {
            tv_result.setText("onResponse:"+s);
            switch (i){
                case 100:
                    Toast.makeText(OKHttpActivity.this,"http",Toast.LENGTH_LONG).show();
                    break;
                case 101:
                    Toast.makeText(OKHttpActivity.this,"https",Toast.LENGTH_LONG).show();
                    break;
                default :
                    break;
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress: "+progress );
            mProgressBar.setProgress((int) (100*progress));
        }
    }

    /**
     * 使用okhttp-utils下载大文件，也支持下载小文件
     */
    public void downloadFile(){
        //图片的url        视频的url://http://vfx.mtime.cn/Video/2016/07/24/mp4/160724055620533327_480.mp4
        String url="http://img5.mtime.cn/mg/2018/05/09/014321.49355104_120X90X4.jpg";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(),"tupian.jpg") {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        Log.e(TAG, "onError: " +e.getMessage() );
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        mProgressBar.setProgress((int) (100*progress));
                        Log.e(TAG, "inProgress: " +(int) (100*progress));
                    }

                    //成功
                    @Override
                    public void onResponse(File file, int i) {
                        //打印路径
                        Log.e(TAG, "onResponse: " +file.getAbsolutePath());
                    }
                });
    }

    /**
     * 使用okhttp-utils上传多个或者单个文件
     */
    public void multiFileUpload(){
        String mBaseUrl = "http://192.168.3.27:8080/FileUpload/FileUploadServlet";
        File file=new File(Environment.getExternalStorageDirectory(),"tupian.jpg");
        File file2=new File(Environment.getExternalStorageDirectory(),"zanghao.jpg");
        if (!file.exists()||!file2.exists()){
            Toast.makeText(OKHttpActivity.this,"文件不存在,请修改文件路径",Toast.LENGTH_SHORT).show();
            return;
        }

        String url=mBaseUrl;
        OkHttpUtils.post()
                .addFile("mFile","server_tupian.jpg",file)
                .addFile("mFile","server_zanghao.jpg",file2)
                .url(url)
                .build()
                .execute(new MyStringCallBack());
    }

    /**
     * 使用okhttp-utils请求单张图片
     */
    public void getImage(){
        tv_result.setText("");
        String url = "http://images.csdn.net/20150817/1.jpg";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .connTimeOut(20000)//链接超时
                .readTimeOut(20000)//读取超时
                .writeTimeOut(20000)//写入超时
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        tv_result.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int i) {
                        Log.e(TAG, "onResponse: complete");
                        iv_icon.setImageBitmap(bitmap);
                    }
    });

}}
