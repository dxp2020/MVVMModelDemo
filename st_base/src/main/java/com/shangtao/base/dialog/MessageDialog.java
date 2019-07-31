package com.shangtao.base.dialog;

import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.SizeUtils;
import com.shangtao.base.BR;
import com.shangtao.base.R;
import com.shangtao.base.databinding.DialogMessageBinding;
import com.shangtao.base.model.bean.MessageParam;
import com.shangtao.base.model.impl.OnClickListener;
import com.shangtao.base.viewModel.MessageDialogViewModel;

public class MessageDialog extends BaseDialog<DialogMessageBinding, MessageDialogViewModel> {

    private MessageParam messageParam;
    private OnClickListener onClickListener;

    @Override
    protected void initParam() {
        assert getArguments() != null;
        messageParam = (MessageParam) getArguments().getSerializable("MessageParam");
    }

    @Override
    protected void initData() {
        getDialog().setCanceledOnTouchOutside(messageParam.isCanceledOnTouchOutside());

        if(messageParam.isHiddenTitle()){
            binding.tvTitle.setVisibility(View.GONE);
        }else{
            binding.tvTitle.setVisibility(View.VISIBLE);
            binding.tvTitle.setPadding(0,0,0, SizeUtils.dp2px(10));
        }

        if(messageParam.isHiddenCancelButton()){
            binding.tvCancel.setVisibility(View.GONE);
            binding.tvCenterLine.setVisibility(View.GONE);
            binding.tvConfirm.setBackgroundResource(R.drawable.selector_button);
        }

        binding.tvTitle.setTextSize(messageParam.getTitleTextSize());
        binding.tvTitle.setTextColor(ContextCompat.getColor(getBaseActivity(),messageParam.getTitleColor()));
        binding.tvTitle.setText(messageParam.getTitle());
        binding.tvContent.setTextSize(messageParam.getContentTextSize());
        binding.tvContent.setTextColor(ContextCompat.getColor(getBaseActivity(),messageParam.getContentColor()));
        binding.tvContent.setText(messageParam.getContent());

        binding.tvCancel.setTextSize(messageParam.getCancelTextSize());
        binding.tvCancel.setTextColor(ContextCompat.getColor(getBaseActivity(),messageParam.getCancelColor()));
        binding.tvCancel.setText(messageParam.getCancel());
        binding.tvConfirm.setTextSize(messageParam.getConfirmTextSize());
        binding.tvConfirm.setTextColor(ContextCompat.getColor(getBaseActivity(),messageParam.getConfirmColor()));
        binding.tvConfirm.setText(messageParam.getConfirm());

        if(messageParam.getContentSpannableStringBuilder()!=null){
            binding.tvContent.setText(messageParam.getContentSpannableStringBuilder());
        }

        if (getDialog().getWindow() != null) {
//            getDialog().getWindow().setWindowAnimations(R.style.dialogWindowAnim);
            getDialog().getWindow().getAttributes().gravity = messageParam.getGravity();
            getDialog().getWindow().getAttributes().width = messageParam.getWidth();
        }
    }

    public void registerLiveDataCallBack() {
        viewModel.getLiveData().getClickEvent().observe(this, view -> {
            if(view==null){
                return;
            }
            int i = view.getId();
            if (i == R.id.tv_cancel) {
                if (messageParam.isAllowDismissDialog()) {
                    dismissDialog();
                }
                if(onClickListener!=null){
                    onClickListener.onClick(false);
                    onClickListener = null;
                }
            } else if (i == R.id.tv_confirm) {
                if (messageParam.isAllowDismissDialog()) {
                    dismissDialog();
                }
                if(onClickListener!=null){
                    onClickListener.onClick(true);
                    onClickListener = null;
                }
            }
        });
    }

    public void show(FragmentManager fragmentManager,OnClickListener onClickListener) {
        super.show(fragmentManager);
        this.onClickListener = onClickListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_message;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    public static MessageDialog newInstance() {
        return  MessageDialog.newInstance(new MessageParam());
    }

    public static MessageDialog newInstance(MessageParam param) {
        MessageDialog dialog= new MessageDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("MessageParam",param);
        dialog.setArguments(bundle);
        return dialog;
    }
}
