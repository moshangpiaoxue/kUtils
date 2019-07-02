package com.libs.view.switcherView;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.libs.k;

import java.util.List;



/**
 * @ author：mo
 * @ data：2019/1/10：11:20
 * @ 功能：上下滚动textview-->TextSwitcher;
 * 控件还有ImageSwitcher和ViewSwitcher。TextSwitcher和ImageSwitcher都是继承于ViewSwitcher的，所以使用方法都一样只不过对象不一样
 */
public class TextSwitcherAnimation {

    private static final int DURATION = 1000;

    private TextSwitcher textSwitcher;
    private List<String> texts;
    private int marker;
    private AnimationSet InAnimationSet;
    private AnimationSet OutAnimationSet;

    private int delayTime = 2000;
    private Handler handler = new Handler();
    private Runnable task = new Runnable() {
        @Override
        public void run() {
            nextView();
            handler.postDelayed(task, delayTime * 2);
        }
    };
/**
*
*/
    public TextSwitcherAnimation(TextSwitcher textSwitcher, List<String> texts) {
        this.textSwitcher = textSwitcher;
        this.texts = texts;
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView t = new TextView(k.app());
                return t;
            }
        });
    }

    public void start() {
        stop();
        handler.postDelayed(task, delayTime);
    }

    public void stop(){
        handler.removeCallbacks(task);
    }

    public int getMarker() {
        return marker;
    }

    public TextSwitcherAnimation setTexts(List<String> texts) {
        this.texts = texts;
        return this;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    public void create() {
        marker = 0;
        if (texts == null){
            Log.w("TextSwitcherAnimation", "texts is null");
            return;
        }
        if (textSwitcher == null) {
            Log.w("TextSwitcherAnimation", "textSwitcher is null");
            return;
        }
        textSwitcher.setText(texts.get(0));
        createAnimation();
        textSwitcher.setInAnimation(InAnimationSet);
        textSwitcher.setOutAnimation(OutAnimationSet);
        start();
    }

    private void createAnimation() {
        AlphaAnimation alphaAnimation;
        TranslateAnimation translateAnimation;

        int h = textSwitcher.getHeight();
        if (h <= 0) {
            textSwitcher.measure(0,0);
            h = textSwitcher.getMeasuredHeight();
        }

        InAnimationSet = new AnimationSet(true);
        OutAnimationSet = new AnimationSet(true);

        alphaAnimation = new AlphaAnimation(0,1);
        translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, h, Animation.ABSOLUTE, 0);
        InAnimationSet.addAnimation(alphaAnimation);
        InAnimationSet.addAnimation(translateAnimation);
        InAnimationSet.setDuration(DURATION);

        alphaAnimation = new AlphaAnimation(1,0);
        translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, 0, Animation.ABSOLUTE, -h);
        OutAnimationSet.addAnimation(alphaAnimation);
        OutAnimationSet.addAnimation(translateAnimation);
        OutAnimationSet.setDuration(DURATION);
    }

    private void nextView() {
        marker = ++marker % texts.size();
        textSwitcher.setText(texts.get(marker));
    }
}
