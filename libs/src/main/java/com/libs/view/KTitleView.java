package com.libs.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.libs.R;
import com.libs.modle.listener.clickListener.KNoDoubleClickListener;
import com.libs.utils.dataUtil.dealUtil.DensityUtil;


/**
 * @ author：mo
 * @ data：2018/12/18
 * @ 功能：
 */
public class KTitleView extends FrameLayout {
    private TitleBarClickListener listener;
    private RelativeLayout title;
    private LinearLayout left;
    private ImageView iv_title_left;
    private TextView midle;
    private TextView right;
    private ImageView iv_title_right;
    private boolean isShow = true;

    public void setListener(TitleBarClickListener listener) {
        this.listener = listener;
    }

    public KTitleView(@NonNull Context context) {
        super(context, null);
    }

    public KTitleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title, this, true);
        title = (RelativeLayout) findViewById(R.id.rl_title);
        left = (LinearLayout) findViewById(R.id.ll_title_left);
        iv_title_left = findViewById(R.id.iv_title_left);
        midle = (TextView) findViewById(R.id.tv_title_midle);
        right = (TextView) findViewById(R.id.tv_title_right);
        iv_title_right = findViewById(R.id.iv_title_right);

        //        //左侧监听
        left.setOnClickListener(new KNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (listener != null) {
                    listener.leftClick(v);
                }

            }
        });
        //中间监听
        midle.setOnClickListener(new KNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (listener != null) {
                    listener.midleClick(v);
                }

            }
        });
        //右侧监听
        right.setOnClickListener(new KNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (listener != null) {
                    listener.rightClick(v);
                }

            }
        });
    }


    /**
     * 整体隐藏
     */
    public void setHindView() {
        if (isShow) {
            title.setVisibility(GONE);
            isShow = false;
        }
    }

    /**
     * 整体显示
     */
    public void setShowView() {
        if (!isShow) {
            title.setVisibility(VISIBLE);
            isShow = true;
        }
    }

    /**
     * 拿到title整体
     */
    public RelativeLayout getTitleView() {
        return title;
    }

    public TextView getMidleTextView() {
        return midle;
    }

    public TextView getRightTextView() {
        return right;
    }

    /**
     * 左侧隐藏
     */
    public void setLeftHind() {
        left.setVisibility(GONE);
    }

    /**
     * 设置左侧图标
     */
    public void setLeftSrc(int leftSrc) {
        iv_title_left.setImageResource(leftSrc);
    }

    /**
     * 设置中间文字 String
     *
     * @param midleText
     */
    public void setMidleText(String midleText) {
        midle.setText(midleText);
    }

    public void setMidleTextColor(int color) {
        midle.setTextColor(color);
    }

    /**
     * 设置中间文字 String
     *
     * @param midleText
     */
    public void setMidleText(String midleText, int color) {
        midle.setTextColor(color);
        midle.setText(midleText);
    }

    /**
     * 设置右侧文字
     */
    public void setRightText(CharSequence text) {
        //        if (StringUtil.isEmpty(text)) {
        //            right.setText("");
        //            right.setVisibility(INVISIBLE);
        //        } else {
        right.setText(text);
        right.setVisibility(VISIBLE);
        //        }
    }

    public void setRightText(String text, int color) {
        setRightText(text);
        midle.setTextColor(color);
    }

    /**
     * 设置右侧图标
     */
    public void setRightSrc(int srcId) {
        iv_title_right.setImageResource(srcId);
        iv_title_right.setVisibility(VISIBLE);
        right.setVisibility(GONE);
        //右侧监听
        iv_title_right.setOnClickListener(new KNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (listener != null) {
                    listener.rightClick(v);
                }

            }
        });
    }
    public void setRightSrc(int srcId,int width,int height) {
        iv_title_right.setImageResource(srcId);
        ViewGroup.LayoutParams params = iv_title_right.getLayoutParams();
        params.width = DensityUtil.dp2px(width);
        params.height = DensityUtil.dp2px(height);
        iv_title_right.setLayoutParams(params);
        iv_title_right.setVisibility(VISIBLE);
        right.setVisibility(GONE);
        //右侧监听
        iv_title_right.setOnClickListener(new KNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (listener != null) {
                    listener.rightClick(v);
                }

            }
        });
    }

    /**
     * 监听接口
     */
    private interface TitleBarClickListener {
        void leftClick(View v);

        void midleClick(View v);

        void rightClick(View v);
    }

    /**
     * 抽象监听方法
     */
    public static abstract class KTitleBarClickListenerImpl implements TitleBarClickListener {
        @Override
        public void leftClick(View v) {

        }

        @Override
        public void midleClick(View v) {

        }

        @Override
        public void rightClick(View v) {

        }
    }
}
