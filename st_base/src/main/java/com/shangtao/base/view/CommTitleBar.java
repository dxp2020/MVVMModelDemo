package com.shangtao.base.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.BarUtils;
import com.shangtao.base.R;


public class CommTitleBar extends RelativeLayout {

    private ImageView btn_right;
    private ImageView iv_back;
    private ImageView iv_logo;
    private ImageView iv_close;
    private TextView tv_title;
    private TextView tv_right;
    private View v_status_bar;
    private View v_bottom_line;
    private int bottomLineColor;
    private int textColor;
    private int bgColor;

    public CommTitleBar(Context context) {
        this(context, null);
    }

    public CommTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_bar, this, true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.comm_title_bar);

        String rightText = a.getString(R.styleable.comm_title_bar_right_text);
        String titleText = a.getString(R.styleable.comm_title_bar_title_text);
        int backBtnSrc = a.getResourceId(R.styleable.comm_title_bar_back_btn_src, 0);
        int rightBtnSrc = a.getResourceId(R.styleable.comm_title_bar_right_btn_src, 0);
        int logoImgSrc = a.getResourceId(R.styleable.comm_title_bar_logo_img_src, 0);
        bgColor = ContextCompat.getColor(context,a.getResourceId(R.styleable.comm_title_bar_bg_color, R.color.color_ffffff));
        textColor = ContextCompat.getColor(context,a.getResourceId(R.styleable.comm_title_bar_text_color, R.color.color_333333));
        bottomLineColor = ContextCompat.getColor(context,a.getResourceId(R.styleable.comm_title_bar_bottom_line_color, R.color.color_f3f3f3));
        a.recycle();

        initView();
        setBackSrc(backBtnSrc);
        setRightSrc(rightBtnSrc);
        setLogoSrc(logoImgSrc);
        setRightText(rightText);
        setTitle(titleText);

        setBackgroundColor(bgColor);
        v_bottom_line.setBackgroundColor(bottomLineColor);
        tv_title.setTextColor(textColor);
        tv_right.setTextColor(textColor);
    }

    private void initView() {
        tv_title = findViewById(android.R.id.title);
        tv_right = findViewById(R.id.tv_right);
        iv_logo = findViewById(R.id.iv_logo);
        iv_close = findViewById(R.id.iv_close);
        iv_back = findViewById(R.id.iv_back);
        btn_right = findViewById(R.id.btn_right);
        v_status_bar = findViewById(R.id.v_status_bar);
        v_bottom_line = findViewById(R.id.v_bottom_line);

        ViewGroup.LayoutParams params = v_status_bar.getLayoutParams();
        params.height = BarUtils.getStatusBarHeight();
        v_status_bar.setLayoutParams(params);
    }

    public void setTitleAlpha(int alpha) {
        setBackgroundColor(changeAlpha(bgColor, alpha));
        v_bottom_line.setBackgroundColor(changeAlpha(bottomLineColor, alpha));
        tv_title.setTextColor(changeAlpha(textColor, alpha));
    }

    /**
     * 修改颜色透明度
     */
    public static int changeAlpha(int color, int alpha) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    public void setTitle(String title) {
        if (title == null || "".equals(title)) {
            return;
        }
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(title);
    }

    public void setRightText(String rightText) {
        if (rightText == null || "".equals(rightText)) {
            return;
        }
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(rightText);
    }

    public void setLogoSrc(int resId) {
        if (resId == 0) {
            return;
        }
        iv_logo.setVisibility(View.VISIBLE);
        iv_logo.setImageResource(resId);
    }

    public void setBackSrc(int resId) {
        if (resId == 0) {
            return;
        }
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setImageResource(resId);
    }

    public void setRightSrc(int resId) {
        if (resId == 0) {
            return;
        }
        btn_right.setVisibility(View.VISIBLE);
        btn_right.setImageResource(resId);
    }

    public void hiddenRightBtn() {
        btn_right.setVisibility(View.GONE);
    }

    public void setRightBtnMarginRight(int marginRight) {
        LayoutParams lp = new LayoutParams(btn_right.getLayoutParams());
        lp.setMargins(0, 0, marginRight, 0);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        btn_right.setLayoutParams(lp);
    }

    public void setOnBackClickListener(OnClickListener onClickListener) {
        iv_back.setOnClickListener(onClickListener);
    }

    public void setOnRightBtnClickListener(OnClickListener onClickListener) {
        btn_right.setOnClickListener(onClickListener);
    }

    public void setOnRightTxtClickListener(OnClickListener onClickListener) {
        tv_right.setOnClickListener(onClickListener);
    }

    public TextView getRightText() {
        return tv_right;
    }

    public ImageView getRightImage() {
        return btn_right;
    }

    public View getBackImage() {
        return iv_back;
    }

    public TextView getTitleText() {
        return tv_title;
    }

    public View getBottomLine() {
        return v_bottom_line;
    }

    public ImageView getIvClose() {
        return iv_close;
    }

    public void setIvClose(ImageView iv_close) {
        this.iv_close = iv_close;
    }

    public void setMargin(View view, int left, int top, int right, int bottom) {
        LayoutParams params = (LayoutParams) view.getLayoutParams();
        params.setMargins(left, top, right, bottom);
        view.setLayoutParams(params);
    }

}
