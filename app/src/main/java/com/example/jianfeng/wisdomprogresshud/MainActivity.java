package com.example.jianfeng.wisdomprogresshud;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.jianfeng.wisdomprogresshud.WisdomProgressHUD.CurrentHUDStyleState;
import static com.example.jianfeng.wisdomprogresshud.WisdomProgressHUD.CurrentHUDTextSize;


public class MainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//WisdomHUDStatus

        context = this;

        //CurrentHUDStyleState = WisdomHUDStyleStatus.WisdomHUDStyle_White;

        final RelativeLayout rootLayout = (RelativeLayout)findViewById(R.id.activity_main_rootLayout);

        Button one_button = (Button)findViewById(R.id.activity_main_one_button);
        one_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WisdomProgressHUD.start_onApplication(WisdomHUDStatus.Loading, context,"Loading...");

                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        WisdomProgressHUD.start_onApplication(WisdomHUDStatus.Warning, context,"警告,请重试！");
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 3000);
            }
        });


        Button two_button = (Button)findViewById(R.id.activity_main_two_button);
        two_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WisdomProgressHUD.start_onApplication(WisdomHUDStatus.Text, context,"将要重新加载！将要重新加载！将要重新加载！");

                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        WisdomProgressHUD.start_onApplication(WisdomHUDStatus.Warning, context,"加载失败", new WisdomProgressHUD.WisdomHUDFinishHandler(){
                            @Override
                            public void finish() {
                                WisdomProgressHUD.start_onApplication(WisdomHUDStatus.Text, context,"结束");
                            }
                        });
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 2000);
            }
        });


        Button three_button = (Button)findViewById(R.id.activity_main_three_button);
        three_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WisdomProgressHUD.start(WisdomHUDStatus.Loading, context,rootLayout,"Loading...");

                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        WisdomProgressHUD.start(WisdomHUDStatus.Error, context, rootLayout, "加载失败");
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 2000);
            }
        });


        Button four_button = (Button)findViewById(R.id.activity_main_four_button);
        four_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WisdomProgressHUD.start(WisdomHUDStatus.Loading, context,rootLayout,"Loading...");

                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        WisdomProgressHUD.start_onApplication(WisdomHUDStatus.Succee, context,"加载成功");
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 2000);
            }
        });


        Button five_button = (Button)findViewById(R.id.activity_main_five_button);
        five_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WisdomProgressHUD.start(WisdomHUDStatus.Loading_Rotate, context,rootLayout,"Loading...");

                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        WisdomProgressHUD.start_onApplication(WisdomHUDStatus.Succee, context,"加载成功");
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 2000);
            }
        });


    }
}
