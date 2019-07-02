package com.libs.modle.listener.textListener;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

import com.libs.utils.tipsUtil.ToastUtil;


/**
 * @ author：mo
 * @ data：2018/10/12
 * @ 功能：自定义文本监听
 */
public class KOnTextChangedListener implements TextWatcher {
    /**
     * 输入字符长度，默认1000
     */
    private int mMaxLen = 1000;
    /**
     * 载体view
     */
    private EditText mEditText = null;
    /**
     * 是否检查空格
     */
    private Boolean isCheckSpace = false;


    public KOnTextChangedListener() {
    }

    public KOnTextChangedListener(EditText mEditText) {
        isCheckSpace = true;
        this.mEditText = mEditText;
    }

    public KOnTextChangedListener(int mMaxLen, EditText mEditText) {
        isCheckSpace = true;
        this.mMaxLen = mMaxLen;
        this.mEditText = mEditText;
    }

    public KOnTextChangedListener(int mMaxLen, EditText mEditText, Boolean isCheckSpace) {
        this.mMaxLen = mMaxLen;
        this.mEditText = mEditText;
        this.isCheckSpace = isCheckSpace;
    }

    /**
     * @param s     当前TextView内部的mText成员变量，实际上就是当前显示的文本
     * @param start 需要改变的文字区域的起点，即选中的文本区域的起始点
     * @param count 需要改变的文字的字符数目，即选中的文本区域的字符的数目
     * @param after 替换的文字的字符数目
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    /**
     * @param s      当前TextView内部的mText成员变量，此时的mText已经被修改过了，但此时mText所表示的文本还没有被显示到UI组件上
     * @param start  改变的文字区域的起点;
     * @param before 改变的文字区域在改变前的旧的文本长度，即选中文字区域的文本长度
     * @param count  改变的文字区域在修改后的新的文本长度
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mEditText != null) {
//            监听输入内容是否超出最大长度，并设置光标位置
            Editable editable = mEditText.getText();
            if (editable.length() > mMaxLen) {
                int selEndIndex = Selection.getSelectionEnd(editable);
                String str = editable.toString();
                //截取新字符串
                String newStr = str.substring(0, mMaxLen);
                mEditText.setText(newStr);
                editable = mEditText.getText();

                //新字符串的长度
                int newLen = editable.length();
                //旧光标位置超过字符串长度
                if (selEndIndex > newLen) {
                    selEndIndex = editable.length();
                }
                //设置新光标所在的位置
                Selection.setSelection(editable, selEndIndex);
                ToastUtil.showToast("最大字符长度为" + mMaxLen + "位");
            }

            //输入空格
            if (isCheckSpace && s.toString().contains(" ")) {
                String[] str = s.toString().split(" ");
                String str1 = "";
                for (int i = 0; i < str.length; i++) {
                    str1 += str[i];
                }
                mEditText.setText(str1);
//                mEditText.setSelection(start);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (isCheckSpace) {
            if (s.toString().contains(" ")) {
                ToastUtil.showToast("输入字符中不能包含空格");
            }
        }
    }
}
