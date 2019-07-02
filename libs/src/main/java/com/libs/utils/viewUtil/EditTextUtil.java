package com.libs.utils.viewUtil;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;

import com.libs.modle.listener.textListener.KOnTextChangedListener;
import com.libs.utils.tipsUtil.ToastUtil;


/**
 * @ author：mo
 * @ data：2017/1124：11:10
 * @ 功能：EditText工具类
 */
public class EditTextUtil {
    public interface InputListener {
        void onInputAfter(Boolean allComplete);
    }

    /**
     * 将 EditText 的光标移动至所显示文字的末尾
     *
     * @param editText EditText
     */
    public static void setCursorToLast(EditText editText) {
        editText.setSelection(editText.getText().length());
    }

    /**
     * 打开/关闭 EditText 的输入 & 编辑功能
     * @param editText 载体
     * @param isOpen    是否开启输入/编辑
     * @param focusNeeded   是否移动光标到最后
     */
    public static void setEditAble(EditText editText, boolean isOpen, boolean focusNeeded) {
        if (isOpen) {
            editText.setFocusableInTouchMode(true);
            editText.setFocusable(true);
            if (focusNeeded) {
                editText.requestFocus();
                setCursorToLast(editText);
            }
        } else {
            editText.clearFocus();
            editText.setFocusable(false);
            editText.setFocusableInTouchMode(false);
        }

    }

    /**
     * 密码模式切换
     *
     * @param mEditText
     * @param isShow
     */
    public static void setPwAble(EditText mEditText, Boolean isShow) {
        //第一种方法
        if (isShow) {
            //选择状态 显示明文--设置为可见的密码
            mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
            mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        //第二种方法
        if (isShow) {
            mEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            mEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    /**
     * 监听若干个输入框，是否全部输入了数据
     *
     * @param inputListener 监听接口回调
     * @param mEditTexts    edittext数组
     */
    public static void setInpListener(final InputListener inputListener, final EditText... mEditTexts) {
        for (int i = 0; i < mEditTexts.length; i++) {
            mEditTexts[i].addTextChangedListener(new KOnTextChangedListener() {
                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 0) {
                        inputListener.onInputAfter(false);
                        return;
                    }
                    boolean tag = false;
                    for (int i = 0; i < mEditTexts.length; i++) {
                        if (mEditTexts[i].getText().length() != 0) {
                            tag = true;
                        } else {
                            tag = false;
                            break;
                        }
                    }
                    inputListener.onInputAfter(tag);
                }
            });
        }

    }

    /**
     * Edittext 输入时保留小数点后位数
     *
     * @param editText
     * @param count    位数 count=0表示不输入小数类型
     */
    public static void setDecimal(EditText editText, int count) {
        if (count < 1) {
            //整数型
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            //小数点型、整数型
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);

            //设置字符过滤
            final int finalCount = count + 1;
            editText.setFilters(new InputFilter[]{new InputFilter() {
                @Override
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    if (source.equals(".") && dest.toString().length() == 0) {
                        return "0.";
                    }
                    if (dest.toString().contains(".")) {
                        int index = dest.toString().indexOf(".");
                        int mlength = dest.toString().substring(index).length();
                        if (mlength == finalCount) {
                            return "";
                        }
                    }
                    return null;
                }
            }});
        }
    }

    public static void initEditNumberPrefix(final EditText edSerialNumber, final int number) {
        edSerialNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String s = edSerialNumber.getText().toString();
                    String temp = "";
                    for (int i = s.length(); i < number; i++) {
                        s = "0" + s;
                    }

                    for (int i = 0; i < number; i++) {
                        temp += "0";
                    }
                    if (s.equals(temp)) {
                        s = temp.substring(1) + "1";
                    }
                    edSerialNumber.setText(s);
                }
            }
        });
    }
    /**
     * 设置禁止表情
     *
     * @param et
     */
    public static void setProhibitEmoji(EditText et) {
        InputFilter[] filters = {getInputFilterProhibitEmoji()};
        et.setFilters(filters);
    }

    public static InputFilter getInputFilterProhibitEmoji() {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuffer buffer = new StringBuffer();
                for (int i = start; i < end; i++) {
                    char codePoint = source.charAt(i);
                    if (!getIsEmoji(codePoint)) {
                        buffer.append(codePoint);
                    } else {
                        ToastUtil.showToast("昵称地址不能含有第三方表情");
                        i++;
                        continue;
                    }
                }
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(buffer);
                    TextUtils.copySpansFrom((Spanned) source, start, end, null,
                            sp, 0);
                    return sp;
                } else {
                    return buffer;
                }
            }
        };
        return filter;
    }

    public static boolean getIsEmoji(char codePoint) {
        if ((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)))
            return false;
        return true;
    }
}
