package com.sty.gmap.navigation.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sty.gmap.navigation.R;

import java.util.ArrayList;

/**
 * Created by Shi Tianyi on 2020/2/4/0004.
 */
public class ActionSheet extends Dialog {
    private Context context;

    private LinearLayout parentLayout;
    private TextView titleTextView;
    private ArrayList<Button> sheetList;
    private Button cancelButton;

    // 标题
    private String title;

    // 取消按钮文字
    private String cancel;

    // 选择项文字列表
    private ArrayList<String> sheetTextList;

    // 标题颜色
    private int titleTextColor;

    // 取消按钮文字颜色
    private int cancelTextColor;

    // 选择项文字颜色
    private int sheetTextColor;

    // 标题大小
    private int titleTextSize;

    // 取消按钮文字大小
    private int cancelTextSize;

    // 选择项文字大小
    private int sheetTextSize;

    // 标题栏高度
    private int titleHeight;

    // 取消按钮高度
    private int cancelHeight;

    // 选择项高度
    private int sheetHeight;

    // 弹出框距离底部的高度
    private int marginBottom;

    // 取消按钮点击回调
    private View.OnClickListener cancelListener;

    // 选择项点击回调列表
    private ArrayList<View.OnClickListener> sheetListenerList;

    public ActionSheet(Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        init(context);
    }

    public ActionSheet(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    private void init(Context context) {
        this.context = context;

        cancel = "取消";
        titleTextColor = Color.parseColor("#aaaaaa");
        cancelTextColor = Color.parseColor("#666666");
        sheetTextColor = Color.parseColor("#666666");
        titleTextSize = 14;
        cancelTextSize = 16;
        sheetTextSize = 16;
        titleHeight = dp2px(40);
        cancelHeight = dp2px(40);
        sheetHeight = dp2px(40);
        marginBottom = dp2px(10);

        sheetList = new ArrayList<>();
        sheetTextList = new ArrayList<>();
        sheetListenerList = new ArrayList<>();
    }

    private ActionSheet createDialog() {
        parentLayout = new LinearLayout(context);
        parentLayout.setBackgroundColor(Color.parseColor("#00000000"));
        parentLayout.setOrientation(LinearLayout.VERTICAL);

        if (title != null) {
            titleTextView = new TextView(context);
            titleTextView.setGravity(Gravity.CENTER);
            titleTextView.setText(title);
            titleTextView.setTextColor(titleTextColor);
            titleTextView.setTextSize(titleTextSize);
            titleTextView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_dialog_top_up));
            LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, titleHeight);
            parentLayout.addView(titleTextView, titleLayoutParams);
        }
        for (int i = 0; i < sheetTextList.size(); i++) {
            if (i == 0 && title != null) {
                View topDividerLine = new View(context);
                topDividerLine.setBackgroundColor(Color.parseColor("#eeeeee"));
                parentLayout.addView(topDividerLine, new LinearLayout
                        .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(1)));
            }

            Button button = new Button(context);
            button.setGravity(Gravity.CENTER);
            button.setText(sheetTextList.get(i));
            button.setTextColor(sheetTextColor);
            button.setTextSize(sheetTextSize);
            if (title != null) {
                if (i == sheetTextList.size() - 1) {
                    button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dialog_bottom_selector));
                } else {
                    button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.layout_white_selector));
                }
            } else {
                if (sheetTextList.size() == 1) {
                    button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dialog_white_selector));
                } else {
                    if (i == 0) {
                        button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dialog_top_selector));
                    } else if (i == sheetTextList.size() - 1) {
                        button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dialog_bottom_selector));
                    } else {
                        button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.layout_white_selector));
                    }
                }
            }
            button.setOnClickListener(sheetListenerList.get(i));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, sheetHeight);
            parentLayout.addView(button, layoutParams);
            sheetList.add(button);

            if (i != sheetTextList.size() - 1) {
                View bottomDividerLine = new View(context);
                bottomDividerLine.setBackgroundColor(Color.parseColor("#eeeeee"));
                parentLayout.addView(bottomDividerLine, new LinearLayout
                        .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(1)));
            }
        }

        cancelButton = new Button(context);
        cancelButton.setGravity(Gravity.CENTER);
        cancelButton.setText(cancel);
        cancelButton.setTextColor(cancelTextColor);
        cancelButton.setTextSize(cancelTextSize);
        cancelButton.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dialog_white_selector));
        cancelButton.setOnClickListener(cancelListener);
        LinearLayout.LayoutParams cancelParams = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, cancelHeight);
        cancelParams.setMargins(0, dp2px(10), 0, 0);
        parentLayout.addView(cancelButton, cancelParams);

        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().getAttributes().y = marginBottom;
        show();
        setContentView(parentLayout);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        return this;
    }

    private void addSheet(String text, View.OnClickListener listener) {
        sheetTextList.add(text);
        sheetListenerList.add(listener);
    }

    public void setCancel(String text) {
        this.cancel = text;
    }

    public void setCancelHeight(int height) {
        this.cancelHeight = dp2px(height);
    }

    public void setCancelTextColor(int color) {
        this.cancelTextColor = color;
    }

    public void setCancelTextSize(int textSize) {
        this.cancelTextSize = textSize;
    }

    public void setSheetHeight(int height) {
        this.sheetHeight = dp2px(height);
    }

    public void setSheetTextColor(int color) {
        this.sheetTextColor = color;
    }

    public void setSheetTextSize(int textSize) {
        this.sheetTextSize = textSize;
    }

    public void setTitle(String text) {
        this.title = text;
    }

    public void setTitleHeight(int height) {
        this.titleHeight = height;
    }

    public void setTitleTextColor(int color) {
        this.titleTextColor = color;
    }

    public void setTitleTextSize(int textSize) {
        this.titleTextSize = textSize;
    }

    public void setMargin(int bottom) {
        this.marginBottom = dp2px(bottom);
    }

    public void addCancelListener(View.OnClickListener listener) {
        this.cancelListener = listener;
    }

    private int dp2px(float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static class DialogBuilder {
        ActionSheet dialog;

        public DialogBuilder(Context context) {
            dialog = new ActionSheet(context);
        }

        /**
         * 添加一个选择项
         * @param text 选择项文字
         * @param listener 选择项点击回调监听
         * @return 当前DialogBuilder
         */
        public DialogBuilder addSheet(String text, View.OnClickListener listener) {
            dialog.addSheet(text, listener);
            return this;
        }

        /**
         * 设置取消按钮文字
         * @param text 文字内容
         * @return 当前DialogBuilder
         */
        public DialogBuilder setCancel(String text) {
            dialog.setCancel(text);
            return this;
        }

        /**
         * 设置取消按钮高度
         * @param height 高度值，单位dp
         * @return 当前DialogBuilder
         */
        public DialogBuilder setCancelHeight(int height) {
            dialog.setCancelHeight(height);
            return this;
        }

        /**
         * 设置取消按钮文字颜色
         * @param color 颜色值
         * @return 当前DialogBuilder
         */
        public DialogBuilder setCancelTextColor(int color) {
            dialog.setCancelTextColor(color);
            return this;
        }

        /**
         * 设置取消按钮文字大小
         * @param textSize 大小值，单位sp
         * @return 当前DialogBuilder
         */
        public DialogBuilder setCancelTextSize(int textSize) {
            dialog.setCancelTextSize(textSize);
            return this;
        }

        /**
         * 设置选择项高度
         * @param height 高度值，单位dp
         * @return 当前DialogBuilder
         */
        public DialogBuilder setSheetHeight(int height) {
            dialog.setSheetHeight(height);
            return this;
        }

        /**
         * 设置选择项文字颜色
         * @param color 颜色值
         * @return 当前DialogBuilder
         */
        public DialogBuilder setSheetTextColor(int color) {
            dialog.setSheetTextColor(color);
            return this;
        }

        /**
         * 设置选择项文字大小
         * @param textSize 大小值，单位sp
         * @return 当前DialogBuilder
         */
        public DialogBuilder setSheetTextSize(int textSize) {
            dialog.setSheetTextSize(textSize);
            return this;
        }

        /**
         * 设置标题
         * @param text 文字内容
         * @return 当前DialogBuilder
         */
        public DialogBuilder setTitle(String text) {
            dialog.setTitle(text);
            return this;
        }

        /**
         * 设置标题栏高度
         * @param height 高度值，单位dp
         * @return 当前DialogBuilder
         */
        public DialogBuilder setTitleHeight(int height) {
            dialog.setTitleHeight(height);
            return this;
        }

        /**
         * 设置标题颜色
         * @param color 颜色值
         * @return 当前DialogBuilder
         */
        public DialogBuilder setTitleTextColor(int color) {
            dialog.setTitleTextColor(color);
            return this;
        }

        /**
         * 设置标题大小
         * @param textSize 大小值，单位sp
         * @return 当前DialogBuilder
         */
        public DialogBuilder setTitleTextSize(int textSize) {
            dialog.setTitleTextSize(textSize);
            return this;
        }

        /**
         * 设置弹出框距离底部的高度
         * @param bottom 距离值，单位dp
         * @return 当前DialogBuilder
         */
        public DialogBuilder setMargin(int bottom) {
            dialog.setMargin(bottom);
            return this;
        }

        /**
         * 设置取消按钮的点击回调
         * @param listener 回调监听
         * @return
         */
        public DialogBuilder addCancelListener(View.OnClickListener listener) {
            dialog.addCancelListener(listener);
            return this;
        }

        /**
         * 创建弹出框，放在最后执行
         * @return 创建的 ActionSheet 实体
         */
        public ActionSheet create() {
            return dialog.createDialog();
        }
    }
}

