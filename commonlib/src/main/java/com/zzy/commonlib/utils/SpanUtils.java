package com.zzy.commonlib.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.CharacterStyle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by lwj on 2017/12/14.
 * lwjfork@gmail.com
 * <p>
 * tv_span1.setText(SpanUtils.newInstance()
 * .append("蓝色", new ForegroundColorSpan(ResUtil.getColor(android.R.color.holo_blue_dark)))
 * .append("红色", new ForegroundColorSpan(ResUtil.getColor(android.R.color.holo_red_dark)))
 * .append("hah")
 * .buildSpan());
 * <p>
 * <p>
 * tv_span2.setText(SpanUtils.newInstance("蓝色，红色，绿色 base")
 * .setSpan("蓝色", new ForegroundColorSpan(ResUtil.getColor(android.R.color.holo_blue_dark)))
 * .setSpan("红色", new ForegroundColorSpan(ResUtil.getColor(android.R.color.holo_red_dark)))
 * .setSpan("绿色", new ForegroundColorSpan(ResUtil.getColor(android.R.color.holo_green_dark)))
 * .buildSpan());
 */

public final class SpanUtils {


    private StringBuilder allStr = new StringBuilder();

    private ArrayList<SpanModel> spanModels = new ArrayList<>();

    public static SpanUtils newInstance() {
        return new SpanUtils();
    }

    public static SpanUtils newInstance(String allStr) {
        return new SpanUtils(allStr);
    }


    private SpanUtils() {

    }

    private SpanUtils(String allStr) {
        this.allStr = new StringBuilder(allStr);

    }


    private class SpanModel {
        public int start; // 开始位置
        public int end;// 结束位置
        int flags = Integer.MIN_VALUE;
        public Object span;
    }


    public SpanUtils setSpan(String string, Object... spans) {
        return setSpan(string, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE, spans);
    }

    public SpanUtils setSpan(String string, int flags, Object... spans) {
        int start = allStr.indexOf(string);
        if (start < 0) {
            return this;
        }
        int end = start + string.length();
        addSpanModel(start, end, flags, spans);
        return this;
    }

    // 正则匹配
    public SpanUtils setRegexSpan(String regex, Object... spans) {
        return setSpan(regex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE, spans);
    }

    // 正则匹配
    public SpanUtils setRegexSpan(String regex, int flags, Object... spans) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(allStr);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end() - 1;
            addSpanModel(start, end, flags, spans);
        }
        return this;
    }


    public SpanUtils setSpan(String[] strings, Object... spans) {

        return setSpan(strings, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE, spans);
    }


    public SpanUtils setSpan(String[] strings, int flags, Object... spans) {
        for (String s : strings) {
            setSpan(s, flags, spans);
        }
        return this;
    }


    public SpanUtils append(String string, Object... spans) {
        return append(string, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE, spans);
    }


    public SpanUtils append(String string, int flags, Object... spans) {
        int start = allStr.length();
        allStr.append(string);
        int end = allStr.length();
        addSpanModel(start, end, flags, spans);
        return this;
    }

    public SpanUtils append(String[] strings, Object... spans) {
        return append(strings, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE, spans);
    }


    public SpanUtils append(String[] strings, int flags, Object... spans) {
        for (String string : strings) {
            append(string, flags, spans);
        }
        return this;
    }

    public SpanUtils append(String string) {
        allStr.append(string);
        return this;
    }


    public SpanUtils append(String[] string) {
        for (String s : string) {
            append(s);
        }
        return this;
    }


    private void addSpanModel(int start, int end, int flags, Object... spans) {
        for (Object span : spans) {
            SpanModel spanModel = new SpanModel();
            spanModel.start = start;
            spanModel.end = end;
            if (span instanceof CharacterStyle) { // fix 每个span 只能用一次的bug
                spanModel.span = CharacterStyle.wrap((CharacterStyle) span);
            } else {
                spanModel.span = span;
            }

            spanModel.flags = flags;
            spanModels.add(spanModel);
        }
    }


    public SpannableString buildSpan() {
        SpannableString spannableString = new SpannableString(allStr.toString());
        for (SpanModel spanModel : spanModels) {
            spannableString.setSpan(spanModel.span, spanModel.start, spanModel.end, spanModel.flags);
        }
        return spannableString;
    }

    public <T extends TextView> void apply(T view) {
        view.setText(buildSpan());
    }

}
