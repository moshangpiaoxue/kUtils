package com.libs.utils.systemUtils.contactsUtil;


/**
 * @ author：mo
 * @ data：2018/1/29：16:25
 * @ 功能：手机联系人实体类
 */
public class ContactsListBean {
    private String[] phone;
    private String name;

    public ContactsListBean(String[] phone, String name) {
        this.phone = phone;
        this.name = name;
    }

    @Override
    public String toString() {
        return "ContactsListBean{" +
                "phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String[] getPhone() {
        return phone;
    }

    public void setPhone(String[] phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
