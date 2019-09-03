package com.itaccess.interphone.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by linxi on 2019/01/15.
 */

public class StringUtil {

    /**
     * バイナリを16進数文字列に変換.
     *
     * @param checkChar 文字列
     * @return 文字列チェック結果
     *          true：文字列がnullもしくは空欄
     *          false：文字列がnullでも空欄でもない
     */
    public static boolean isNull(final String checkChar) {
        if (null == checkChar || "".equals(checkChar)) {
            return true;
        }
        return false;
    }

    /**
     * 配列をArrayListに変換.
     *
     * @param value
     * @return List<String>
     */
    public static List<String> asList(final String[] value) {
        List<String> list= Arrays.asList(value);
        return list;
    }

    /**
     * ArrayListを配列に変換.
     *
     * @param list
     * @return List<String>
     */
    public static String[] toArray(final List<String> list) {
        final int size =  list.size();
        String[] value = (String[])list.toArray(new String[size]);
        return value;
    }

    /**
     * 文字列に含まれたURLを抽出
     *
     * @param content
     * @return List<String>
     */
    public static List<String> getUrlFromString(final String content) {
        List<String> mUrlList = new ArrayList<>();
        final Pattern urlPattern = Pattern.compile("(http://|https://){1}[\\w\\.\\-/:\\#\\?\\=\\&\\;\\%\\~\\+]+"
                , Pattern.CASE_INSENSITIVE);
        final Matcher matcher = urlPattern.matcher(content);
        while (matcher.find()) {
            mUrlList.add(matcher.group());
        }
        return mUrlList;
    }


}
