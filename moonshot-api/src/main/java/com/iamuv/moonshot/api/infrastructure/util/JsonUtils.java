package com.iamuv.moonshot.api.infrastructure.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonUtils {

    /**
     * 去除JSON字符串中的多余空格。
     *
     * @param json 原始JSON字符串
     * @return 去除多余空格后的JSON字符串
     */
    public static String removeExtraSpaces(String json) {
        if (json == null || json.trim().isEmpty()) {
            return json;
        }

        StringBuilder result = new StringBuilder();
        boolean inQuotes = false;
        for (char c : json.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            }

            if (c == ' ' && !inQuotes) {
                // 如果当前字符是空格且不在引号内，则跳过
                continue;
            }

            // 如果当前字符不是空格或者在引号内，则添加到结果中
            result.append(c);
        }

        return result.toString();
    }


    /**
     * 从JSON字符串中提取指定键的值，仅限于字符串类型。
     *
     * @param json 原始JSON字符串
     * @param key  要提取的键
     * @return 对应键的值，如果键不存在或者不是字符串类型，则返回null
     */
    public static String getStringValueFromJson(String json, String key) {
        String regex = "\"" + key + "\":\"(.*?)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(removeExtraSpaces(json));
        if (matcher.find()) {
            // 如果找到匹配项，返回匹配的值
            return matcher.group(1);
        }
        return null;

    }

    /**
     * 从JSON字符串中提取指定键的值，仅限于int类型。
     *
     * @param json 原始JSON字符串
     * @param key  要提取的键
     * @return 对应键的值，如果键不存在或者不是字符串类型，则返回null
     */
    public static Integer getIntValueFromJson(String json, String key) {
        String regex = "\"" + key + "\":(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(removeExtraSpaces(json));
        if (matcher.find()) {
            // 如果找到匹配项，返回匹配的值
            return Integer.valueOf(matcher.group(1));
        }
        return null;

    }

    /**
     * 从JSON字符串中提取指定键的值，仅限于boolean类型。
     *
     * @param json 原始JSON字符串
     * @param key  要提取的键
     * @return 对应键的值，如果键不存在或者不是字符串类型，则返回null
     */
    public static Boolean getBooleanValueFromJson(String json, String key) {
        String regex = "\"" + key + "\":(true|false)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(removeExtraSpaces(json));
        if (matcher.find()) {
            // 如果找到匹配项，返回匹配的值
            return Boolean.valueOf(matcher.group(1));
        }
        return null;

    }

}