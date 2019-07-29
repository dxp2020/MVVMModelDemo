package com.shangtao.base.model.permission;

import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.mula.base.dialog.MessageDialog;
import com.mula.base.tools.jump.JumpUtils;
import com.shangtao.base.dialog.MessageDialog;

import java.util.ArrayList;
import java.util.List;


public class PermissionHelper {

    private FragmentActivity activity;
    private String[] permissions;
    private OnResultListener listener;
    private int requestCode;
    private MessageDialog mFailedDialog ;
    private boolean isClickedFailedDialog;
    List<String> grantedList = new ArrayList<>();// 有权限
    List<String> deniedList = new ArrayList<>();// 无权限

    public PermissionHelper(FragmentActivity activity) {
        this.activity = activity;
    }

    public PermissionHelper(FragmentActivity activity, int requestCode, String[] permissions, OnResultListener listener) {
        this.activity = activity;
        this.requestCode = requestCode;
        this.permissions = permissions;
        this.listener = listener;
    }

    /**
     * 请求权限，只会请求permissions中定义，但没有开启的权限
     */
    public void request() {
        checkPermissions();
        if (deniedList.size() > 0) {
            ActivityCompat.requestPermissions(activity, deniedList.toArray(new String[deniedList.size()]), requestCode);
        } else {
            listener.onResult(grantedList, deniedList);
        }
    }

    /**
     * 获取权限结果，在onRequestPermissionsResult中调用
     */
    public void result() {
        checkPermissions();
        listener.onResult(grantedList, deniedList);
    }


    /**
     * 显示获取权限失败的弹窗提示
     */
    public void showFailedDialog(String message) {
        if(mFailedDialog != null){
            mFailedDialog.setMessageContent(message);
        }else{
            mFailedDialog = new MessageDialog(activity)
                    .setMessageContent(message)
                    .setBottonClickListener(new MessageDialog.OnClickListener<Boolean>() {
                        @Override
                        public void onClick(Boolean content) {
                            isClickedFailedDialog = true;
                            if (content) {
                                JumpUtils.openSettingActivity(activity);
                            }
                        }
                    });
        }
        mFailedDialog.show();
    }

    public void showFailedDialog(MessageDialog failedDialog) {
        mFailedDialog = failedDialog;
        mFailedDialog.show();
    }

    public void dismissFailedDialog() {
        if (mFailedDialog!=null&&mFailedDialog.isShowing()) {
            mFailedDialog.dismiss();
        }
    }

    public boolean isGrantedPermission(){
        checkPermissions();
        if(grantedList.size()==0||grantedList.size()!=permissions.length){
            return false;
        }
        dismissFailedDialog();
        return true;
    }

    public void showGrantFailureDialog(){
        if (mFailedDialog!=null&&!mFailedDialog.isShowing()) {
            mFailedDialog.show();
        }
    }

    private void checkPermissions() {
        grantedList.clear();
        deniedList.clear();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
                grantedList.add(permission);
            } else {
                deniedList.add(permission);
            }
        }
    }

    public interface OnResultListener {
        void onResult(List<String> grantedList, List<String> deniedList);
    }

}
