package com.shangtao.base.model.utils;


import android.os.Environment;
import android.util.Log;


import com.shangtao.base.model.setting.AppSettings;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class L {
    public static String TAG = "MULA";
    private static Boolean MYLOG_WRITE_TO_FILE=false;// 日志写入文件开关
    private static char MYLOG_TYPE='v';// 输入日志类型，w代表只输出告警信息等，v代表输出所有信息
    private static String MYLOG_PATH_SDCARD_DIR = Environment.getExternalStorageDirectory().getPath();// 日志文件在sdcard中的路径
    private static String MYLOGFILEName = "Log.txt";// 本类输出的日志文件名称
    private static SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());// 日志的输出格式
    private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());// 日志文件格式

    public static void v(String content){
        log(TAG, content,'v');
    }

    public static void i(String content){
        log(TAG, content,'i');
    }

    public static void d(String content){
        log(TAG, content,'d');
    }

    public static void w(String content){
        log(TAG, content,'w');
    }

    public static void w(String content, Exception ex){
        w(content);
        log(TAG, getExceptionTrace(ex),'w');
    }

    public static void e(String content){
        log(TAG, content,'e');
    }

    public static void e(Throwable ex){
        log(TAG, getThrowableTrace(ex),'e');
    }

    public static void e(Exception ex){
        log(TAG, getThrowableTrace(ex),'e');
    }

    public static void e(Error ex){
        log(TAG, getErrorTrace(ex),'e');
    }

    public static void e(String content, Exception ex){
        log(TAG, content,'e');
        log(TAG, getExceptionTrace(ex),'e');
    }

    public static void e(String content, Throwable ex){
        log(TAG, content,'e');
        log(TAG, getThrowableTrace(ex),'w');
    }

    public static void e(String content, Error ex){
        log(TAG, content,'e');
        log(TAG, getErrorTrace(ex),'e');
    }

    public static String getIllegalStateExceptionTrace(IllegalStateException e) {
        String content = null;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        content = sw.toString();
        try {
            if(content==null){
                ByteArrayOutputStream buf = new ByteArrayOutputStream();
                e.printStackTrace(new PrintWriter(buf, true));
                content = buf.toString();
                buf.close();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return content;
    }

    public static String getExceptionTrace(Exception e) {
        String content = null;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        content = sw.toString();
        try {
            if(content==null){
                ByteArrayOutputStream buf = new ByteArrayOutputStream();
                e.printStackTrace(new PrintWriter(buf, true));
                content = buf.toString();
                buf.close();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return content;
    }

    public static String getErrorTrace(Error e) {
        String content = null;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        content = sw.toString();
        try {
            if(content==null){
                ByteArrayOutputStream buf = new ByteArrayOutputStream();
                e.printStackTrace(new PrintWriter(buf, true));
                content = buf.toString();
                buf.close();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return content;
    }

    public static String getThrowableTrace(Throwable e) {
        String content = null;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        content = sw.toString();
        try {
            if(content==null){
                ByteArrayOutputStream buf = new ByteArrayOutputStream();
                e.printStackTrace(new PrintWriter(buf, true));
                content = buf.toString();
                buf.close();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return content;
    }

    /**
     * 根据tag, msg和等级，输出日志
     *
     * @param tag
     * @param msg
     * @param level
     */
    private synchronized static void log(String tag, String msg, char level) {
        if (AppSettings.isDebug) {
            if ('e' == level && ('e' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) { // 输出错误信息
                Log.e(tag, msg);
            } else if ('w' == level && ('w' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
                Log.w(tag, msg);
            } else if ('d' == level && ('d' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
                Log.d(tag, msg);
            } else if ('i' == level && ('d' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
                Log.i(tag, msg);
            } else {
                Log.v(tag, msg);
            }
            if (MYLOG_WRITE_TO_FILE)
                writeLogtoFile(String.valueOf(level), tag, msg);
        }
    }

    /**
     * 打开日志文件并写入日志
     *
     * @return
     * **/
    private synchronized static void writeLogtoFile(String mylogtype, String tag, String text) {// 新建或打开日志文件
        Date nowtime = new Date();
        String needWriteFiel = logfile.format(nowtime);
        String needWriteMessage = myLogSdf.format(nowtime) + "    " + mylogtype+ "    " + tag + "    " + text;
        File file = new File(MYLOG_PATH_SDCARD_DIR, needWriteFiel+ MYLOGFILEName);
        try {
            FileWriter filerWriter = new FileWriter(file, true);//后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
