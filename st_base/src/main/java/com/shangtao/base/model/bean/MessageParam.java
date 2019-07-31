package com.shangtao.base.model.bean;

import android.text.SpannableStringBuilder;
import android.view.Gravity;

import com.blankj.utilcode.util.SizeUtils;
import com.shangtao.base.R;

import java.io.Serializable;

public class MessageParam implements Serializable {
    private boolean isHiddenTitle = true;
    private boolean isAllowDismissDialog = true;
    private boolean isHiddenCancelButton = false;
    private boolean isCanceledOnTouchOutside = false;

    private int titleTextSize = 16;
    private int contentTextSize = 14;
    private int titleColor = R.color.color_333333;
    private int contentColor = R.color.color_333333;
    private String title = "标题";
    private String content = "内容";

    private int cancelTextSize = 15;
    private int confirmTextSize = 15;
    private int cancelColor = R.color.color_333333;
    private int confirmColor = R.color.color_00adef;
    private String cancel = "取消";
    private String confirm = "确定";

    private SpannableStringBuilder contentSpannableStringBuilder;

    private int gravity = Gravity.CENTER;
    private int width = SizeUtils.dp2px(280);
    private int height;

    public MessageParam() {
    }

    private MessageParam(Builder builder) {
        setHiddenTitle(builder.isHiddenTitle);
        setAllowDismissDialog(builder.isAllowDismissDialog);
        setHiddenCancelButton(builder.isHiddenCancelButton);
        setCanceledOnTouchOutside(builder.isCanceledOnTouchOutside);
        setTitleTextSize(builder.titleTextSize);
        setContentTextSize(builder.contentTextSize);
        setTitleColor(builder.titleColor);
        setContentColor(builder.contentColor);
        setTitle(builder.title);
        setContent(builder.content);
        setCancelTextSize(builder.cancelTextSize);
        setConfirmTextSize(builder.confirmTextSize);
        setCancelColor(builder.cancelColor);
        setConfirmColor(builder.confirmColor);
        setCancel(builder.cancel);
        setConfirm(builder.confirm);
        setContentSpannableStringBuilder(builder.contentSpannableStringBuilder);
        setGravity(builder.gravity);
        setWidth(builder.width);
        setHeight(builder.height);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean isCanceledOnTouchOutside() {
        return isCanceledOnTouchOutside;
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        isCanceledOnTouchOutside = canceledOnTouchOutside;
    }

    public boolean isHiddenTitle() {
        return isHiddenTitle;
    }

    public void setHiddenTitle(boolean hiddenTitle) {
        isHiddenTitle = hiddenTitle;
    }

    public boolean isAllowDismissDialog() {
        return isAllowDismissDialog;
    }

    public void setAllowDismissDialog(boolean allowDismissDialog) {
        isAllowDismissDialog = allowDismissDialog;
    }

    public boolean isHiddenCancelButton() {
        return isHiddenCancelButton;
    }

    public void setHiddenCancelButton(boolean hiddenCancelButton) {
        isHiddenCancelButton = hiddenCancelButton;
    }

    public int getTitleTextSize() {
        return titleTextSize;
    }

    public void setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
    }

    public int getContentTextSize() {
        return contentTextSize;
    }

    public void setContentTextSize(int contentTextSize) {
        this.contentTextSize = contentTextSize;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getContentColor() {
        return contentColor;
    }

    public void setContentColor(int contentColor) {
        this.contentColor = contentColor;
    }

    public int getCancelTextSize() {
        return cancelTextSize;
    }

    public void setCancelTextSize(int cancelTextSize) {
        this.cancelTextSize = cancelTextSize;
    }

    public int getConfirmTextSize() {
        return confirmTextSize;
    }

    public void setConfirmTextSize(int confirmTextSize) {
        this.confirmTextSize = confirmTextSize;
    }

    public int getCancelColor() {
        return cancelColor;
    }

    public void setCancelColor(int cancelColor) {
        this.cancelColor = cancelColor;
    }

    public int getConfirmColor() {
        return confirmColor;
    }

    public void setConfirmColor(int confirmColor) {
        this.confirmColor = confirmColor;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public SpannableStringBuilder getContentSpannableStringBuilder() {
        return contentSpannableStringBuilder;
    }

    public void setContentSpannableStringBuilder(SpannableStringBuilder contentSpannableStringBuilder) {
        this.contentSpannableStringBuilder = contentSpannableStringBuilder;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public static final class Builder {
        private boolean isHiddenTitle = true;
        private boolean isAllowDismissDialog = true;
        private boolean isHiddenCancelButton = false;
        private boolean isCanceledOnTouchOutside = false;

        private int titleTextSize = 16;
        private int contentTextSize = 14;
        private int titleColor = R.color.color_333333;
        private int contentColor = R.color.color_333333;
        private String title = "标题";
        private String content = "内容";

        private int cancelTextSize = 15;
        private int confirmTextSize = 15;
        private int cancelColor = R.color.color_333333;
        private int confirmColor = R.color.color_00adef;
        private String cancel = "取消";
        private String confirm = "确定";

        private SpannableStringBuilder contentSpannableStringBuilder;

        private int gravity = Gravity.CENTER;
        private int width = SizeUtils.dp2px(280);
        private int height;

        private Builder() {
        }

        public Builder hiddenTitle() {
            isHiddenTitle = true;
            return this;
        }

        public Builder hiddenCancel() {
            isHiddenCancelButton = true;
            return this;
        }

        public Builder setIsHiddenTitle(boolean val) {
            isHiddenTitle = val;
            return this;
        }

        public Builder setIsAllowDismissDialog(boolean val) {
            isAllowDismissDialog = val;
            return this;
        }

        public Builder setIsHiddenCancelButton(boolean val) {
            isHiddenCancelButton = val;
            return this;
        }

        public Builder setIsCanceledOnTouchOutside(boolean val) {
            isCanceledOnTouchOutside = val;
            return this;
        }

        public Builder setTitleTextSize(int val) {
            titleTextSize = val;
            return this;
        }

        public Builder setContentTextSize(int val) {
            contentTextSize = val;
            return this;
        }

        public Builder setTitleColor(int val) {
            titleColor = val;
            return this;
        }

        public Builder setContentColor(int val) {
            contentColor = val;
            return this;
        }

        public Builder setTitle(String val) {
            title = val;
            return this;
        }

        public Builder setContent(String val) {
            content = val;
            return this;
        }

        public Builder setCancelTextSize(int val) {
            cancelTextSize = val;
            return this;
        }

        public Builder setConfirmTextSize(int val) {
            confirmTextSize = val;
            return this;
        }

        public Builder setCancelColor(int val) {
            cancelColor = val;
            return this;
        }

        public Builder setConfirmColor(int val) {
            confirmColor = val;
            return this;
        }

        public Builder setCancel(String val) {
            cancel = val;
            return this;
        }

        public Builder setConfirm(String val) {
            confirm = val;
            return this;
        }

        public Builder setContentSpannableStringBuilder(SpannableStringBuilder val) {
            contentSpannableStringBuilder = val;
            return this;
        }

        public Builder setGravity(int val) {
            gravity = val;
            return this;
        }

        public Builder setWidth(int val) {
            width = val;
            return this;
        }

        public Builder setHeight(int val) {
            height = val;
            return this;
        }

        public MessageParam build() {
            return new MessageParam(this);
        }
    }
}
