package com.libs.utils.systemUtils.contactsUtil;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.libs.k;

import java.util.ArrayList;
import java.util.List;



/**
 * @ author：mo
 * @ data：2018/12/13：16:46
 * @ 功能：手机联系人工具类
 */
public class ContactsUtil {

    /**
     * 查询数据库中手机联系人信息的方法
     */
    public static List<ContactsListBean> getContacts() {
        List<ContactsListBean> list = new ArrayList<>();

        String[] colums = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.CONTACT_ID};
        Cursor cursor = k.app().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, colums, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                //获取手机号码
                String phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //当手机号为空或者没有该字段，跳过循环
                if (TextUtils.isEmpty(phoneNum)) {
                    continue;
                }
                //移除手机号+86
                if (phoneNum.contains("+")) {
                    phoneNum = phoneNum.substring(3, phoneNum.length());
                }
                //移除手机号码中的空格
                phoneNum = phoneNum.replace(" ", "");
                //获取联系人姓名：
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                //获取ID
                int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                String[] phones = new String[1];
                phones[0] = phoneNum;
                list.add(new ContactsListBean(phones, name + ""));
            }
            cursor.close();
        }
        return list;
    }
}
