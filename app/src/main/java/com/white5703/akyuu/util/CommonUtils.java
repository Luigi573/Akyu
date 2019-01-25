package com.white5703.akyuu.util;

import android.content.Context;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import com.white5703.akyuu.R;
import com.white5703.akyuu.entity.Note;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.white5703.akyuu.util.ResourceUtils.getColor;

public class CommonUtils {
    /**
     * 获取范围内的随机整数
     * @param min 最小值
     * @param max 最大值
     * @return 得到的随机数
     */
    public static long genRandomNumber(long min,long max) {
        Random random = new Random();
        long num = random.nextInt((int)max) % (max - min + 1) + min;
        return num;
    }

    /**
     * 获取所传入List中的最小权值
     * @param list
     * @return 1~9
     */
    public static int getMinimumPriority(List<Note> list) {
        int cnt = list.size();
        int res = 9;
        for (int i = 0; i < cnt; i++) {
            if (list.get(i).getPriority() < res) {
                res = list.get(i).getPriority();
            }
        }
        return res;
    }

    /**
     * 将Date格式化为 yyyy-MM-dd HH:mm:ss
     */
    public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return formatter.format(date);
    }

    /**
     * list转array
     */
    public static String[] listToArray(List<String> list) {
        return list.toArray(new String[0]);
    }

    /**
     * 获取默认的Note模板
     * id:传空自增
     * brief:This is a default brief
     * detail:This is a default detail
     * tag:Default
     * briefAlignment:Gravity.CENTER
     * briefFontSize:SUPER_LARGE
     * detailAlignment:Gravity.START
     * detailFontSize:MEDIUM
     * images:null
     */
    public static Note getDefaultNote() {
        return new Note(null, "This is a default brief", "This is a default detail",
            "Default", 3, new Date(), "", Gravity.CENTER, 24, Gravity.START, 18, null);
    }

    public static String getLatextUrl(String latex) {
        String str = "";
        try {
            str = "http://latex.codecogs.com/gif.latex?" + URLEncoder.encode(latex, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Pattern pattern = Pattern.compile("\\+");
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll("%20");
    }

    public static float dip2px(Context context, int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
            dp, context.getResources().getDisplayMetrics());
    }

    public static float sp2px(Context context, int spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }

    public static int getPriorityColor(int priority) {
        switch (priority) {
            case 1:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return getColor(R.color.colorPriority1);
                }
                break;
            case 2:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return getColor(R.color.colorPriority2);
                }
                break;
            case 3:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return getColor(R.color.colorPriority3);
                }
                break;
            case 4:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return getColor(R.color.colorPriority4);
                }
                break;
            case 5:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return getColor(R.color.colorPriority5);
                }
                break;
            default:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return getColor(R.color.colorBlack);
                }
                break;
        }

        return 0;
    }

    public static boolean equalsList(List<String> list1, List<String> list2) {
        if (list1 == null) {
            return list2 == null;
        }

        if (list1.size() != list2.size()) {
            return false;
        }
        String[] arr1 = list1.toArray(new String[] {});
        String[] arr2 = list2.toArray(new String[] {});
        Arrays.sort(arr1);
        Arrays.sort(arr1);
        return Arrays.equals(arr1, arr2);
    }
}
