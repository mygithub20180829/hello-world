package com.liuwang.myok;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.liuwang.myok.bean.DataInfo;
import com.liuwang.myok.bean.FilmInfo;
import com.liuwang.myok.bean.ShopInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NativeJsonPraseActivity extends Activity implements View.OnClickListener {

    private TextView tv_title;
    private Button bt_native_tojavaobject;
    private Button bt_native_tojavalist;
    private Button bt_native_complex;
    private Button bt_native_special;
    private TextView tv_native_original;
    private TextView tv_native_last;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_json_prase);
        
        initView();
        initListenesr();
    }

    private void initView() {
        tv_title=findViewById(R.id.tv_title);
        //修改头布局显示
        tv_title.setText("手动JSON解析");
        bt_native_tojavaobject=findViewById(R.id.bt_native_tojavaobject);
        bt_native_tojavalist=findViewById(R.id.bt_native_tojavalist);
        bt_native_complex=findViewById(R.id.bt_native_complex);
        bt_native_special=findViewById(R.id.bt_native_special);
        tv_native_original=findViewById(R.id.tv_native_original);
        tv_native_last=findViewById(R.id.tv_native_last);
    }

    private void initListenesr() {
        bt_native_tojavaobject.setOnClickListener(this);
        bt_native_tojavalist.setOnClickListener(this);
        bt_native_complex.setOnClickListener(this);
        bt_native_special.setOnClickListener(this);
        tv_native_original.setOnClickListener(this);
        tv_native_last.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //将JSON对象转化为JAVA对象
            case R.id.bt_native_tojavaobject:
                jsonToJavaObjectByNative();
                break;
            //将JSON数组转化为JAVA集合
            case R.id.bt_native_tojavalist:
                jsonToJavaArrayListByNative();
                break;
            //复杂JSON数据解析
            case R.id.bt_native_complex:
                jsonToJavaOfComplex();
                break;
            //特殊JSON数据解析
            case R.id.bt_native_special:
                jsonToJavaOfSpecial();
                break;
        }
    }

    private void jsonToJavaObjectByNative() {
        //获取或者创建JSON
        String json="{\n"+"\t\"id\":2,\"name\":\"大虾\",\n"+
                "\t\"price\":12.3,\n"+
                "\t\"imagePath\":\"http://192.168.10.165:8080/L05_Server/images/f1.jpg\"\n"+
                "}\n";
        ShopInfo shopInfo=null;
        //解析JSON
        try {
            JSONObject jsonObject = new JSONObject(json);
            int id = jsonObject.getInt("id");
            int opt_id=jsonObject.optInt("id");
            String name = jsonObject.optString("name");
            double price = jsonObject.optDouble("price");
            String imagePath = jsonObject.optString("imagePath");
            shopInfo = new ShopInfo(id, name, price, imagePath);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //显示JSON数据
        tv_native_original.setText(json);
        tv_native_last.setText(shopInfo.toString());
    }

    private void jsonToJavaArrayListByNative(){
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
        List<ShopInfo> shops=new ArrayList<>();
        //解析json
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject!=null){
                    int id = jsonObject.optInt("id");
                    String imagePah = jsonObject.optString("imagePah");
                    String name = jsonObject.optString("name");
                    double price = jsonObject.optDouble("price");
                    ShopInfo shopInfo=new ShopInfo(id,name,price,imagePah);
                    shops.add(shopInfo);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //显示json数据
        tv_native_original.setText(json);
        tv_native_last.setText(shops.toString());
    }

    private void jsonToJavaOfComplex() {
        //获取或创建json数据
        String json="{\n" +
                "\t\"data\":{\n" +
                "\t\t\"count\":5,\n" +
                "\t\t\"items\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"id\":45,\n" +
                "\t\t\t\t\"title\":\"坚果\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"id\":132,\n" +
                "\t\t\t\t\"title\":\"炒货\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"id\":166,\n" +
                "\t\t\t\t\"title\":\"蜜饯\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"id\":195,\n" +
                "\t\t\t\t\"title\":\"果脯\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"id\":196,\n" +
                "\t\t\t\t\"title\":\"礼盒\"\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t},\n" +
                "\t\"rs_code\":\"1000\",\n" +
                "\t\"rs_msg\":\"success\"\n" +
                "}\t\t\t";
        //封装Java对象
        DataInfo dataInfo = new DataInfo();
        //解析json
        try {
            JSONObject jsonObject = new JSONObject(json);
            //第一层解析
            JSONObject data = jsonObject.optJSONObject("data");
            String rs_code = jsonObject.optString("rs_code");
            String rs_msg = jsonObject.optString("rs_msg");
            //第一层封装
            dataInfo.setRs_code(rs_code);
            dataInfo.setRs_msg(rs_msg);
            DataInfo.DataBean dataBean=new DataInfo.DataBean();
            dataInfo.setData(dataBean);

            //第二层解析
            int count = data.optInt("count");
            JSONArray items = data.optJSONArray("items");
            //第二层封装
            dataBean.setCount(count);
            List<DataInfo.DataBean.ItemsBean> itemsBean=new ArrayList<>();
            dataBean.setItems(itemsBean);

            //第三层解析
            for (int i=0;i<items.length();i++){
                JSONObject jsonObject1 = items.optJSONObject(i);
                if (jsonObject1!=null){
                    int id = jsonObject1.optInt("id");
                    String title = jsonObject1.optString("title");
                    //第三层封装
                    DataInfo.DataBean.ItemsBean bean = new DataInfo.DataBean.ItemsBean();
                    bean.setId(id);
                    bean.setTitle(title);
                    itemsBean.add(bean);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //显示json数据
        tv_native_original.setText(json);
        tv_native_last.setText(dataInfo.toString());
    }


    private void jsonToJavaOfSpecial() {
        //获取或创建json数据
        String json="{\n" +
                "\t\"code\":0,\n" +
                "\t\"list\":{\n" +
                "\t\t\"0\":{\n" +
                "\t\t\t\"aid\":\"6008965\",\n" +
                "\t\t\t\"author\":\"哔哩哔哩番剧\",\n" +
                "\t\t\t\"coins\":170,\n" +
                "\t\t\t\"copyright\":\"Copy\",\n" +
                "\t\t\t\"create\":\"2016-08-25 21:34\"\n" +
                "\t\t},\n" +
                "\t\t\"1\":{\n" +
                "\t\t\t\"aid\":\"6008938\",\n" +
                "\t\t\t\"author\":\"哔哩哔哩番剧\",\n" +
                "\t\t\t\"coins\":404,\n" +
                "\t\t\t\"copyright\":\"Copy\",\n" +
                "\t\t\t\"create\":\"2016-08-25 21:33\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
        //封装Java对象
        FilmInfo filmInfo=new FilmInfo();
        //解析json
        try {
            JSONObject jsonObject = new JSONObject(json);
            //第一层解析
            int code = jsonObject.optInt("code");
            //第一层封装
            filmInfo.setCode(code);
            List<FilmInfo.FilmBean> lists=new ArrayList<>();
            filmInfo.setList(lists);

            //第二层解析
            JSONObject list = jsonObject.optJSONObject("list");
            for (int i=0;i<list.length();i++){
                JSONObject jsonObject1 = list.optJSONObject(i + "");
                if (jsonObject1!=null){
                    String aid = jsonObject1.optString("aid");
                    String author = jsonObject1.optString("author");
                    int coins = jsonObject1.optInt("coins");
                    String copyright = jsonObject1.optString("copyright");
                    String create = jsonObject1.optString("create");

                    //第二层封装
                    FilmInfo.FilmBean filmBean = new FilmInfo.FilmBean();
                    filmBean.setAid(aid);
                    filmBean.setAuthor(author);
                    filmBean.setCoins(coins);
                    filmBean.setCopyright(copyright);
                    filmBean.setCreate(create);
                    lists.add(filmBean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //显示json数据
        tv_native_original.setText(json);
        tv_native_last.setText(filmInfo.toString());
    }

}
