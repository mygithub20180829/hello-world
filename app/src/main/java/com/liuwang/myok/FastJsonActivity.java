package com.liuwang.myok;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.liuwang.myok.bean.ShopInfo;

import java.util.ArrayList;
import java.util.List;

public class FastJsonActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_title;
    private Button bt_fastjson_tojavaobject;
    private Button bt_fastjson_tojavalist;
    private Button bt_fastjson_tojsonobject;
    private Button bt_fastjson_tojsonarray;

    private TextView tv_fastjson_original;
    private TextView tv_fastjson_last;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_json);

        initView();
        initListener();
    }

    private void initListener() {
        bt_fastjson_tojavaobject.setOnClickListener(this);
        bt_fastjson_tojavalist.setOnClickListener(this);
        bt_fastjson_tojsonobject.setOnClickListener(this);
        bt_fastjson_tojsonarray.setOnClickListener(this);
    }

    private void initView() {
        tv_title=findViewById(R.id.tv_title);
        tv_title.setText("FastJson解析");

        bt_fastjson_tojavaobject=findViewById(R.id.bt_fastjson_tojavaobject);
        bt_fastjson_tojavalist=findViewById(R.id.bt_fastjson_tojavalist);
        bt_fastjson_tojsonobject=findViewById(R.id.bt_fastjson_tojsonobject);
        bt_fastjson_tojsonarray=findViewById(R.id.bt_fastjson_tojsonarray);

        tv_fastjson_original=findViewById(R.id.tv_fastjson_original);
        tv_fastjson_last=findViewById(R.id.tv_fastjson_last);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_fastjson_tojavaobject:
                jsonToJavaObjectByFastJson();
                break;
            case R.id.bt_fastjson_tojavalist:
                jsonToJavaListByFastJson();
                break;
            case R.id.bt_fastjson_tojsonobject:
                javaToJsonObjectByFastJson();
                break;
            case R.id.bt_fastjson_tojsonarray:
                javaToJsonArrayByFastJson();
                break;
        }
    }

    private void javaToJsonArrayByFastJson() {
        //获取或创建java对象
        List<ShopInfo> shops=new ArrayList<>();
        ShopInfo baoyu = new ShopInfo(1, "鲍鱼", 250.5, "baoyu");
        ShopInfo longxia = new ShopInfo(2, "龙虾", 251, "longxia");

        shops.add(baoyu);
        shops.add(longxia);
        //生成json数据
        String json = JSON.toJSONString(shops);
        //显示Json数据
        tv_fastjson_original.setText(shops.toString());
        tv_fastjson_last.setText(json);
    }

    private void javaToJsonObjectByFastJson() {
        //获取或创建java对象
        ShopInfo shopInfo = new ShopInfo(1, "鲍鱼", 250.5, "");
        //生成json数据
        String json = JSON.toJSONString(shopInfo);
        //显示Json数据
        tv_fastjson_original.setText(shopInfo.toString());
        tv_fastjson_last.setText(json);
    }

    private void jsonToJavaListByFastJson() {
        //获取或创建json对象
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
        List<ShopInfo> shopInfos = JSON.parseArray(json, ShopInfo.class);
        //显示Json数据
        tv_fastjson_original.setText(json);
        tv_fastjson_last.setText(shopInfos.toString());
    }

    private void jsonToJavaObjectByFastJson() {
        //获取或创建json对象
        String json="{\n"+"\t\"id\":2,\"name\":\"大虾\",\n"+
                "\t\"price\":12.3,\n"+
                "\t\"imagePath\":\"http://192.168.10.165:8080/L05_Server/images/f1.jpg\"\n"+
                "}\n";
        //解析json数据
        ShopInfo shopInfo = JSON.parseObject(json, ShopInfo.class);
        //显示Json数据
        tv_fastjson_original.setText(json);
        tv_fastjson_last.setText(shopInfo.toString());
    }
}
