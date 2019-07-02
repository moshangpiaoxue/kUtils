package com.libs.modle.datum.design.factory.factorySimple;


/**
 * @author：mo
 * @data：2018/5/31 0031
 * @功能：
 */

public class KFactory {
    private static KFactory instance;

    public KFactory() {
    }

    public static KFactory getInstance() {
        if (instance == null) {
            instance = new KFactory();
        }
        return instance;
    }

    public KView getWhoAnswer(KView.Who who) {
        KView kView = null;
        switch (who) {
            case zhang:
                kView = new zhangsan();
                break;
            case li:
                kView = new lisi();
                break;
            default:
                break;
        }
        return kView;
    }
}
