package com.liuwang.myok;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button okhttp_bt;
    private Button native_json;
    private Button gson_bt;
    private Button fastjson_bt;
    private Button hello_chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        okhttp_bt=findViewById(R.id.okhttp_bt);
        native_json=findViewById(R.id.native_json);
        gson_bt=findViewById(R.id.gson_bt);
        fastjson_bt=findViewById(R.id.fastjson_bt);
        hello_chart=findViewById(R.id.hello_chart);

        okhttp_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,OKHttpActivity.class);
                startActivity(intent);
            }
        });
        native_json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,NativeJsonPraseActivity.class);
                startActivity(intent);
            }
        });
        gson_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,GSONActivity.class);
                startActivity(intent);
            }
        });
        fastjson_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,FastJsonActivity.class);
                startActivity(intent);
            }
        });
        hello_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,HelloChart.class);
                startActivity(intent);
            }
        });
    }
}
