package com.azhansy.linky.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhansy.linky.R;


/**
 * 带X的Dialog,点击x清除输入框内容，自动弹出输入法，自动隐藏输入法
 * Created by SHU on 2016/6/23
 */
public class InputDialogWithX extends Dialog {
    private TextView tv_title;
    private TextView tv_ok;
    private ImageView clear_txt;
    private ImageView close_dialog;
    private EditText et_inputText;
    OnInputButtonClickListener mListener;
    private String mHint;

    private String mTitle;
    private String mInputText;

    public InputDialogWithX(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected InputDialogWithX(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input_with_x);
        tv_title = (TextView) findViewById(R.id.tv_title);
        close_dialog = (ImageView) findViewById(R.id.close_dialog);
        clear_txt = (ImageView) findViewById(R.id.iv_clear_txt);
        et_inputText = (EditText) findViewById(R.id.et_inputText);
        tv_ok = (TextView) findViewById(R.id.tv_ok);
        init();
    }

    private void init() {
        showSoftInput(et_inputText);
        close_dialog.setOnClickListener(v -> {
            hideSoftInput(et_inputText);
            this.dismiss();
        });
        clear_txt.setOnClickListener(v -> {
            showSoftInput(et_inputText);
            et_inputText.setText("");
        });
        tv_ok.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onClick(et_inputText.getText().toString());
            }
        });

        if (!mTitle.isEmpty()) {
            tv_title.setText(mTitle);
        }
        et_inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null) return;
                if (s.length() == 0) {
                    clear_txt.setVisibility(View.GONE);
                }else {
                    clear_txt.setVisibility(View.VISIBLE);
                }
            }
        });
        if (mHint != null) {
            et_inputText.setHint(mHint);
        }
        if (mInputText != null) {
            et_inputText.setText(mInputText);
            et_inputText.setSelection(mInputText.length());
        }
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setInputText(String text) {
        if (text == null) {
            return;
        }
        mInputText = text;
    }
    public void setHintText(String hintText) {
        if (hintText == null) {
            return;
        }
        mHint = hintText;
    }
    private void setPositiveButton(OnInputButtonClickListener listener) {
//        hideSoftInput(et_inputText);
        this.mListener = listener;
    }


    public interface OnInputButtonClickListener {
        void onClick(String input);
    }

    /**
     * //         * 隐藏软键盘
     * //
     */
    public void hideSoftInput(View view) {
        view.clearFocus();
        InputMethodManager imm = (InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public void showSoftInput(View view) {
        view.setFocusable(true);
        view.requestFocus();
        view.postDelayed(() -> {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.showSoftInput(view, 0);
            }
        }, 200);
    }

    public static class Builder {
        private Context context;
        public String title;
        private String inputText;
        private String hintText;
        private int inputType = EditorInfo.TYPE_CLASS_TEXT;

        private OnInputButtonClickListener negativeListener;
        private OnInputButtonClickListener positiveListener;
        private OnDismissListener dismissListener;

        private boolean cancelable = true;
        private boolean canceledOnTouchOutside = true;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder title(int resId) {
            return title(context.getString(resId));
        }

        public Builder positive(OnInputButtonClickListener listener) {
            positiveListener = listener;
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder canceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        /**
         * 输入框中的文字
         */
        public Builder inputText(String text) {
            inputText = text;
            return this;
        }

        public Builder hintText(int res) {
            return hintText(context.getString(res));
        }

        public Builder hintText(String hint) {
            this.hintText = hint;
            return this;
        }

        public Builder inputType(int type) {
            this.inputType = type;
            return this;
        }

        public Builder dismissListener(OnDismissListener listener) {
            this.dismissListener = listener;
            return this;
        }

        public InputDialogWithX create() {

            InputDialogWithX dialog = new InputDialogWithX(context,R.style.dialog);
            if (!TextUtils.isEmpty(title)) {
                dialog.setTitle(title);
            }
            if (!TextUtils.isEmpty(inputText)) {
                dialog.setInputText(inputText);
            }
            if (!TextUtils.isEmpty(hintText)) {
                dialog.setHintText(hintText);
            }
            if (positiveListener != null) {
                dialog.setPositiveButton(positiveListener);
            }
            dialog.setCancelable(cancelable);
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
            dialog.setOnDismissListener(dialog1 -> {
                if (dismissListener != null) {
                    dismissListener.onDismiss(dialog1);
                }
            });

            return dialog;
        }
    }

}

