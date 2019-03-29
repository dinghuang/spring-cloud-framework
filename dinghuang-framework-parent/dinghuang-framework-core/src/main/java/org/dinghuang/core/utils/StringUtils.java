package org.dinghuang.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/27
 */
public class StringUtils {

    private static final Pattern HUMP_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern DUPLICATE_ONE = Pattern.compile("Duplicate entry '");
    private static final Pattern DUPLICATE_TWO = Pattern.compile("' for key '");
    private static final Pattern DUPLICATE_THREE = Pattern.compile("'\n###");

    /**
     * 驼峰转下划线
     *
     * @param string string
     * @return
     */
    public static String HumpToUnderline(String string) {
        Matcher matcher = HUMP_PATTERN.matcher(string);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String getDuplicateMessage(String string) {
        // 现在创建 matcher 对象
        Matcher ms = DUPLICATE_ONE.matcher(string);
        Matcher me = DUPLICATE_TWO.matcher(string);
        Matcher meTwo = DUPLICATE_THREE.matcher(string);
        if (ms.find() && me.find() && meTwo.find()) {
            return "字段" + string.substring(me.end(), meTwo.start()).replaceAll("uk_", "") + "不允许重复，重复值为" + string.substring(ms.end(), me.start());
        } else {
            return null;
        }
    }
}
