package com.liuwang.myok;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liuwang.myok.bean.ShopInfo;

import java.util.ArrayList;
import java.util.List;

public class GSONActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_title;
    private Button bt_gson_tojavaobject;
    private Button bt_gson_tojavalist;
    private Button bt_gson_togsonobject;
    private Button bt_gson_togsonlist;

    private TextView tv_gson_original;
    private TextView tv_gson_last;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);

        initView();
        initListener();
    }

    private void initListener() {
        bt_gson_tojavaobject.setOnClickListener(this);
        bt_gson_tojavalist.setOnClickListener(this);
        bt_gson_togsonobject.setOnClickListener(this);
        bt_gson_togsonlist.setOnClickListener(this);
    }

    private void initView() {
        tv_title=findViewById(R.id.tv_title);
        tv_title.setText("GSON解析");
        bt_gson_tojavaobject=findViewById(R.id.bt_gson_tojavaobject);
        bt_gson_tojavalist=findViewById(R.id.bt_gson_tojavalist);
        bt_gson_togsonobject=findViewById(R.id.bt_gson_togsonobject);
        bt_gson_togsonlist=findViewById(R.id.bt_gson_togsonlist);

        tv_gson_original=findViewById(R.id.tv_gson_original);
        tv_gson_last=findViewById(R.id.tv_gson_last);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_gson_tojavaobject:
                jsonToJavaObjectByGson();
                break;
            case R.id.bt_gson_tojavalist:
                jsonToJavaListByGson();
                break;
            case R.id.bt_gson_togsonobject:
                javaToJsonObjectByGson();
                break;
            case R.id.bt_gson_togsonlist:
                javaToJsonListByGson();
                break;
        }
    }

    private void javaToJsonListByGson() {
        //获取或创建Java对象
        List<ShopInfo> shopInfo=new ArrayList<>();
        ShopInfo baoyu = new ShopInfo(1, "鲍鱼", 250.5, "baoyu");
        ShopInfo longxia = new ShopInfo(2, "龙虾", 251, "longxia");
        shopInfo.add(baoyu);
        shopInfo.add(longxia);
        //生成json数据
        Gson gson = new Gson();
        String json = gson.toJson(shopInfo);
        //显示Json数据
        tv_gson_original.setText(shopInfo.toString());
        tv_gson_last.setText(json);
    }

    private void javaToJsonObjectByGson() {
        //获取或创建Java对象
        ShopInfo shopInfo = new ShopInfo(1, "鲍鱼", 250.5, "");
        //生成json数据
        Gson gson = new Gson();
        String json = gson.toJson(shopInfo);
        //显示Json数据
        tv_gson_original.setText(shopInfo.toString());
        tv_gson_last.setText(json);
    }

    private void jsonToJavaListByGson() {
        //获取或创建json数据
        String json="[\n"+
                "   {\n"+
                "       \"id\":1,\n"+
                "       \"imagePath\":\"http://192.168.10.165:8080/f1.jpg\",\n"+
                "       \"name\":\"大虾1\",\n"+
                "       \"price\":12.3\n"+
                "       },\n"+
                "       {\n"+
                "           \"id\":2,\n"+
                "       \"imagePath\":\"http://192.168.10.165:8080/f2.jpg\",\n"+
                "       \"name\":\"大虾2\",\n"+
                "       \"price\":12.5\n"+
                "       }\n"+
                "]";
        //解析json数据
        Gson gson = new Gson();
        List<ShopInfo> shops = gson.fromJson(json, new TypeToken<List<ShopInfo>>() {}.getType());
        //显示json数据
        tv_gson_original.setText(json);
        tv_gson_last.setText(shops.toString());
    }

    private void jsonToJavaObjectByGson() {
        //获取或创建json数据
        String json="{\n"+"\t\"id\":2,\"name\":\"大虾\",\n"+
                "\t\"price\":12.3,\n"+
                "\t\"imagePath\":\"http://192.168.10.165:8080/L05_Server/images/f1.jpg\"\n"+
                "}\n";
        //解析json数据
        Gson gson = new Gson();
        ShopInfo shopInfo = gson.fromJson(json, ShopInfo.class);
        //显示json数据
        tv_gson_original.setText(json);
        tv_gson_last.setText(shopInfo.toString());
    }
}
