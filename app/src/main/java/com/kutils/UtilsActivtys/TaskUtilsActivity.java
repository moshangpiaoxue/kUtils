package com.kutils.UtilsActivtys;

import android.view.View;
import android.widget.TextView;

import com.kutils.R;
import com.libs.modle.viewHolder.ViewHolder;
import com.libs.ui.activity.KBaseActivity;
import com.libs.utils.task.handlers.PollingUtil;

/**
 * @ author：mo
 * @ data：2019/8/5:15:52
 * @ 功能：
 */
public class TaskUtilsActivity extends KBaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.act_utils_task;
    }

    int tag = 1;
    PollingUtil pollingUtil;
    @Override
    protected void initView(ViewHolder mViewHolder, View rootView) {
        final TextView tv_task_down = findViewById(R.id.tv_task_down);

        pollingUtil = new PollingUtil();
        pollingUtil.startPolling(new Runnable() {
            @Override
            public void run() {
                tv_task_down.setText("倒计时" + tag++);
            }
        }, 3000, true);
        tv_task_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pollingUtil.endPolling();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pollingUtil.endPolling();
    }
}
