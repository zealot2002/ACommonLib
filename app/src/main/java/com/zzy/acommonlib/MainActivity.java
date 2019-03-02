package com.zzy.acommonlib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity{
    private static final String TAG = "MainActivity";

    private Button btnGo;
    private TextView tvText;

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        tvText = findViewById(R.id.tvText);
        tvText.setText(getClass().getSimpleName());

        btnGo = findViewById(R.id.btnGo);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(context,Main2Activity.class);
                startActivity(it);
            }
        });
    }


}
