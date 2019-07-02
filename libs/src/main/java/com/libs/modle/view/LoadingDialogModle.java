package com.libs.modle.view;

import android.app.Dialog;
import android.content.Context;


/**
 * author：mo
 * data：2017/11/14 0014
 * 功能：加载进度条dialog
 */

public class LoadingDialogModle {
    private static Dialog loadingDialog;

    /**
     * Dialog消失类型
     */
    public enum DismissType {
        //从不取消
        NEVER,
        //系统返回键取消
        BACK,
        //默认，点击空白处或者返回键都取消
        DEFUT
    }

    public interface DismissCallBack {
        void onDismiss();
    }

    /**
     * 开启加载Dialog
     *
     * @param context
     * @return
     */
    public static Dialog showDialog(Context context) {
        return showDialog(context, "", DismissType.DEFUT, null);
    }

    public static Dialog showDialog(Context context, String loadingString) {
        return showDialog(context, loadingString, DismissType.DEFUT, null);
    }

    /**
     * 开启加载Dialog
     *
     * @param context
     * @param dismissType false：点外面不消失，可以用“返回键”取消 true：点外面消失
     * @return
     */
    public static Dialog showDialog(Context context, DismissType dismissType) {
        return showDialog(context, "", dismissType, null);
    }

    public static Dialog showDialog(Context context, String loadingString, DismissType dismissType) {
        return showDialog(context, loadingString, dismissType, null);
    }

    public static Dialog showDialog(Context context, DismissType dismissType, DismissCallBack callBack) {
        return showDialog(context, "", dismissType, callBack);
    }

    /**
     * 开启加载Dialog
     *
     * @param context
     * @param loadingString 加载中等语句
     * @param dismissType   false：点外面不消失，可以用“返回键”取消 true：点外面消失
     * @return
     */
    public static Dialog showDialog(Context context, String loadingString, DismissType dismissType, DismissCallBack callBack) {
        if (context != null && loadingDialog == null) {
            if (callBack == null) {
                createDialog(context, loadingString, dismissType, null);
            } else {
                createDialog(context, loadingString, dismissType, callBack);
            }

        }
        if (context != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        return loadingDialog;
    }

    /**
     * 关闭加载Dialog
     */
    public static void hintDialog() {
        if (loadingDialog == null) {
            return;
        }
        loadingDialog.dismiss();
    }


    /**
     * 创建加载Dialog
     *
     * @param context       上下文
     * @param loadingString 加载文字
     * @param dismissType   取消类型
     * @param callBack      消失回调
     * @return
     */
    private static Dialog createDialog(Context context, String loadingString, DismissType dismissType, final DismissCallBack callBack) {
//        // 得到加载view
//        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
//        ImageView spaceshipImage = (ImageView) view.findViewById(R.id.iv_dialog_loading);
//        TextView tv_dialog_loading = (TextView) view.findViewById(R.id.tv_dialog_loading);
//        if (TextUtils.isEmpty(loadingString)) {
//            tv_dialog_loading.setText("   加载中...");
//        } else {
//            tv_dialog_loading.setText(loadingString);
//        }
//
//
//        spaceshipImage.setBackgroundResource(R.drawable.dialog_rotate);
//        AnimationDrawable drawable = (AnimationDrawable) spaceshipImage.getBackground();
//        drawable.start();
//        // 创建自定义样式dialog
//        loadingDialog = new Dialog(context, R.style.loading_dialog);
//        // 设置布局
//        loadingDialog.setContentView(view, new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//
//        //loading消失监听
//        loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                loadingDialog = null;
//                if (callBack!=null){
//                    callBack.onDismiss();
//                }
//
//            }
//        });
//        // 可以用“返回键”取消,点外面不消失
//        if (dismissType == BACK) {
//            loadingDialog.setCanceledOnTouchOutside(false);
//            //从不取消
//        } else if (dismissType == NEVER) {
//            loadingDialog.setCancelable(false);
//            //默认，点击空白处或者返回键都取消
//        } else if (dismissType == DEFUT) {
//            loadingDialog.setCanceledOnTouchOutside(true);
//            loadingDialog.setCancelable(true);
//        }
        return loadingDialog;
    }
}
