package com.zzy.acommonlib;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zzy.commonlib.util.FileUtils;

public class MainActivity extends Activity {

    private Button btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGo = findViewById(R.id.btnGo);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestHttp.doTestPost();
//                TestHttp.doTestGet();
            }
        });

        String s = FileUtils.readFileFromAssets(this,"mockServer.json");
        Log.e("zzy",s);
    }
}
