package com.white5703.akyuu.util;

import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.StringRes;
import com.white5703.akyuu.app.AkyuuApplication;

public class ResourceUtils {
    public static String getString(@StringRes int id) {
        return AkyuuApplication.getInstance().getString(id);
    }

    public static int getDimensionPixelSize(@DimenRes int id) {
        return AkyuuApplication.getInstance().getResources().getDimensionPixelSize(id);
    }

    public static int getColor(@ColorRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return AkyuuApplication.getInstance().getColor(id);
        }
        return 0;
    }
}
