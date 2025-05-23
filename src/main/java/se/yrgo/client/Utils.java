package se.yrgo.client;

import org.hsqldb.lib.StringUtil;

public class Utils {
    public static boolean isNullOrEmpty(String string) {
        return string == null || StringUtil.isEmpty(string);
    }
}
