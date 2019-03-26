package com.zzy.acommonlib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.utils.ToastUtils;

public class MainActivity extends Activity{
    private static final String TAG = "MainActivity";

    private Button btnGo,btnRest;
    private TextView tvServer,tvText;

    private EditText etServer;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        tvText = findViewById(R.id.tvText);
        tvText.setText("如果有异常这里显示");

        etServer = findViewById(R.id.etServer);

        btnGo = findViewById(R.id.btnGo);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for(int i=0;i<2;i++){
                        String url = etServer.getText().toString();
                        if(!url.isEmpty()){
                            HttpProxy.testPing(url,new HInterface.DataCallback() {
                                @Override
                                public void requestCallback(int result, Object o, Object o1) {
                                    if (result == HConstant.SUCCESS) {
                                        ToastUtils.showShort("成功");
                                    } else {
                                        tvText.setText("失败: "+(String) o);
                                        ToastUtils.showShort((String) o);
                                    }
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    tvText.setText("失败: "+e.toString());
                    ToastUtils.showShort(e.toString());
                }
            }
        });

        btnRest = findViewById(R.id.btnRest);
        btnRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvText.setText("");
            }
        });

    }


}
