package com.libs.modle.datum.design.factory.factorySimple;


/**
 * @author：mo
 * @data：2018/5/31 0031
 * @功能：
 */

public class TeacherTest {

    public static void main(String[] aa) {
        KView kView = KFactory.getInstance().getWhoAnswer(KView.Who.li);
        kView.answer("你叫啥");

    }
}
