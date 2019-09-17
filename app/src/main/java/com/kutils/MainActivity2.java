package com.kutils;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.libs.k;
import com.libs.modle.annotation.methods.KInjectCheckNet;
import com.libs.modle.annotation.methods.KInjectEvent;
import com.libs.modle.annotation.methods.KInjectView;
import com.libs.broadcastreceivers.SmsBroadcastReceiver;
import com.libs.ui.activity.KMediaActivity;
import com.libs.utils.ResUtil;
import com.libs.utils.dataUtil.SpannableStringUtil;

import static android.text.style.DynamicDrawableSpan.ALIGN_BASELINE;

//@KInjectContentView(R.layout.activity_main)
public class MainActivity2 extends KMediaActivity {
    @KInjectView(R.id.iv_1)
    private ImageView imageView;
    @KInjectView(R.id.tv_1)
    private TextView tv_1;
    @KInjectView(R.id.et_input_charge_number)
    private EditText et_input_charge_number;


    @KInjectCheckNet(toast = "sssssssssss")
    @KInjectEvent({R.id.tv_1, R.id.iv_1})
    public void onClick1(View view) {
        switch (view.getId()) {
            case R.id.tv_1:

                break;
            case R.id.iv_1:
                actionMediaTakePic();
                break;
            default:
                break;

        }

    }

    SmsBroadcastReceiver smsBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        k.view().inject(this);
//        addString("图片").setResourceId(R.mipmap.aa)
        SpannableStringUtil.getBuilder("").addString("   ").setResourceId(R.mipmap.aa).setVerticalAlignment(ALIGN_BASELINE)
                .addString("分享").setBackgroundColor(ResUtil.getColor(R.color.color_097fdc)).addToTextView(tv_1);

//        if (ActivityCompat.checkSelfPermission(MainActivity2.this,
//                Manifest.permission.READ_SMS)!= PackageManager.PERMISSION_GRANTED||
//                ActivityCompat.checkSelfPermission(MainActivity2.this,Manifest.permission.RECEIVE_SMS)
//                        !=PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(MainActivity2.this,
//                    new String[]{Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS},
//                    1);
//        }//动态申请权限
//        smsBroadcastReceiver=new SmsBroadcastReceiver(new KOnSmsReceive() {
//            @Override
//            public void onSmsReceive(Context context, final String from, final String message) {
//                tv_1.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        LogUtil.i(from+"==\n"+message);
//                        tv_1.setText(from);
//                        et_input_charge_number.setText(message);
//                    }
//                });
//
//            }
//        });
    }


}
