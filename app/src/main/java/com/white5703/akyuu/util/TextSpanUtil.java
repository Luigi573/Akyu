package com.white5703.akyuu.util;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;
import com.white5703.akyuu.R;
import com.white5703.akyuu.entity.Note;
import com.white5703.akyuu.ui.widget.CenterAlignImageSpan;
import com.white5703.akyuu.ui.widget.UrlImageParser;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.white5703.akyuu.util.CommonUtils.getPriorityColor;
import static com.white5703.akyuu.util.ResourceUtils.getColor;

public class TextSpanUtil {

    public static SpannableString buildDetailText(Context context, Note note, TextView tv) {
        SpannableString str = new SpannableString(doReplacement(note.getDetail()));

        str.setSpan(new AbsoluteSizeSpan(note.getDetailfontsize(), true),
            0, str.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        float imageSize = CommonUtils.sp2px(context, note.getDetailfontsize());

        Pattern pattern = Pattern.compile("★");
        Matcher matcher = pattern.matcher(str);

        int index = 0;

        while (matcher.find()) {
            if (note.getLatexs().get(index) == null) {
                return str;
            }
            CenterAlignImageSpan imageSpan;
            String url = CommonUtils.getLatextUrl(note.getLatexs().get(index));
            Drawable drawableFromNet = new UrlImageParser(tv, context, (int) imageSize)
                .getDrawable(url);
            imageSpan = new CenterAlignImageSpan(drawableFromNet);
            str.setSpan(imageSpan, matcher.start(), matcher.end(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            index++;
        }

        return str;
    }

    public static SpannableStringBuilder buildBriefText(Note note) {
        SpannableString str = new SpannableString(note.getBrief());
        str.setSpan(new AbsoluteSizeSpan(note.getBrieffontsize(), true),
            0, str.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        //ImageSpan imageSpan = new ImageSpan();
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(str);
        return builder;
    }

    public static SpannableString buildLatexImageText(Context context, TextView tv, String latex) {
        float imageSize = tv.getTextSize();
        SpannableString str = new SpannableString("★\0");
        String url = CommonUtils.getLatextUrl(latex);

        Pattern pattern = Pattern.compile("★");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            CenterAlignImageSpan imageSpan;
            Drawable drawableFromNet = new UrlImageParser(tv, context, (int) imageSize)
                .getDrawable(url);
            imageSpan = new CenterAlignImageSpan(drawableFromNet);
            str.setSpan(imageSpan, matcher.start(), matcher.end(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        return str;
    }

    public static SpannableStringBuilder buildNavFooterText(Note note) {
        SpannableString idStr =
            new SpannableString(String.format(Locale.CHINA, "No.%d", note.getId()));
        idStr.setSpan(
            new AbsoluteSizeSpan(ResourceUtils.getDimensionPixelSize(R.dimen.text_medium)),
            3, idStr.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        SpannableString priorityStr =
            new SpannableString(String.format(Locale.CHINA, "Priority:%d", note.getPriority()));
        priorityStr.setSpan(
            new AbsoluteSizeSpan(ResourceUtils.getDimensionPixelSize(R.dimen.text_medium)),
            9, priorityStr.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        priorityStr.setSpan(new ForegroundColorSpan(getPriorityColor(note.getPriority())),
            9, priorityStr.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        SpannableString tagStr = new SpannableString(note.getTag());
        tagStr.setSpan(new UnderlineSpan(), 0, tagStr.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tagStr.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, tagStr.length(),
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        SpannableString refStr = new SpannableString("");
        boolean flag = false;
        if (!(note.getReference() == null || note.getReference().equals("")
            || note.getReference().equals("null"))) {
            flag = true;
            refStr = new SpannableString(note.getReference());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                refStr.setSpan(new ForegroundColorSpan(getColor(R.color.colorSecondaryLight)),
                    0, refStr.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }

        SpannableString timeStr = new SpannableString(CommonUtils.formatDate(note.getUpdatetime()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timeStr.setSpan(new ForegroundColorSpan(getColor(R.color.colorGrey)),
                0, timeStr.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        SpannableStringBuilder rtn = new SpannableStringBuilder();
        if (flag) {
            rtn.append(idStr)
                .append("  ")
                .append(priorityStr)
                .append("  ")
                .append(tagStr)
                .append("\n")
                .append(refStr)
                .append("\n")
                .append(timeStr);
        } else {
            rtn.append(idStr)
                .append("  ")
                .append(priorityStr)
                .append("  ")
                .append(tagStr)
                .append("\n")
                .append(timeStr);
        }

        return rtn;
    }

    public static String doReplacement(String str) {
        Pattern pattern = Pattern.compile("★");
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll("★\0");
    }
}
