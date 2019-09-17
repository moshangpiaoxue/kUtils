package com.libs.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.libs.k;
import com.libs.modle.listener.receiverListener.KOnSmsReceive;
import com.libs.utils.logUtils.LogUtil;


/**
 * @ author：mo
 * @ data：2019/5/27:15:57
 * @ 功能：短信广播接收器
 */
public class SmsBroadcastReceiver {
    private Receiver receiver;
    private KOnSmsReceive listener;

    public SmsBroadcastReceiver(KOnSmsReceive listener) {
        this.listener = listener;
        onCreate();
    }

    private void onCreate() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        // 设置优先级 不然监听不到短信
        filter.setPriority(1000);
        k.app().registerReceiver(receiver, filter);
    }

    /**
     * 释放资源
     */
    public void onDestroy() {
        if (receiver != null) {
            k.app().unregisterReceiver(receiver);
        }
    }


    public class Receiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            // 如果是接收到短信
            if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
                LogUtil.i("有短信");
                StringBuilder sb = new StringBuilder();
                // 接收由SMS传过来的数据
                Bundle bundle = intent.getExtras();
                // 判断是否有数据
                if (bundle != null) {
                    // 通过pdus可以获得接收到的所有短信消息
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    // 构建短信对象array,并依据收到的对象长度来创建array的大小
                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < pdus.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }
                    // 将送来的短信合并自定义信息于StringBuilder当中
                    for (SmsMessage message : messages) {
                        sb.append("短信来源:");
                        // 获得接收短信的电话号码
                        sb.append(message.getDisplayOriginatingAddress());
                        sb.append("\n------短信内容------\n");
                        // 获得短信的内容
                        sb.append(message.getDisplayMessageBody());
                    }
                    LogUtil.i(sb.toString());
                }

//                Toast.makeText(context, sb.toString(), 5000).show();
//                Object[] objs = (Object[]) intent.getExtras().get("pdus");
//                for (Object obj : objs) {
//                    byte[] pdu = (byte[]) obj;
//                    SmsMessage sms = SmsMessage.createFromPdu(pdu);
//                    // 短信的内容
//                    String message = sms.getMessageBody();
//                    // 短息的手机号，如果你们公司发送验证码的号码是固定的这里可以进行一个号码的校验
//                    String from = sms.getOriginatingAddress();
//                    if (listener != null) {
//                        listener.onSmsReceive(context, from, message);
//                    }
//                }
            }
        }

    }
}
