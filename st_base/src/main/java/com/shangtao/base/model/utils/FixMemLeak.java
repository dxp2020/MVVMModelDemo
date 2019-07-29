package com.shangtao.base.model.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 解决键盘内存泄露
 */
public class FixMemLeak {

    private static Map<String,Boolean> filedMap = new HashMap<>();

    static {
        filedMap.put("mCurRootView",null);
        filedMap.put("mServedView",null);
        filedMap.put("mNextServedView",null);
        filedMap.put("mLastSrvView",null);
    }

    public static void fixLeak(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        for (Map.Entry<String, Boolean> entry : filedMap.entrySet()) {
            String key = entry.getKey();
            if (entry.getValue() != null && !(entry.getValue())) {
                continue;
            }
            try {
                Field field = imm.getClass().getDeclaredField(key);
                filedMap.put(key, true);
                field.setAccessible(true);
                field.set(imm, null);
            } catch (Throwable t) {
                filedMap.put(key, false);
            }
        }
    }
}