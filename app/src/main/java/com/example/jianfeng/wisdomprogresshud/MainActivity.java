package com.example.jianfeng.wisdomprogresshud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout rootLayout = (RelativeLayout)findViewById(R.id.rootLayout);

        WisdomProgressHUD.startOnCreate(WisdomProgressHUD.Loading, this,"Loading", rootLayout);

        WisdomProgressHUD.after(WisdomProgressHUD.Succee, this,"欢迎来到：WisdomProgressHUD",3000);

        WisdomProgressHUD.after(WisdomProgressHUD.Default, this,"WisdomProgressHUD开始体验了！",6000);

        WisdomProgressHUD.after(WisdomProgressHUD.Warning, this,"我是Warning图标！",9000);

        WisdomProgressHUD.after(WisdomProgressHUD.Error, this,"我是Error图标！",12000);
    }
}
