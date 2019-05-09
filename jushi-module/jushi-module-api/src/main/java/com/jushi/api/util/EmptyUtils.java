package com.jushi.api.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

public class EmptyUtils {
    public static boolean isEmpty(@Nullable String str) {
        if (str == null) {
            return true;
        }
        return StringUtils.isEmpty(str.trim());
    }
}
