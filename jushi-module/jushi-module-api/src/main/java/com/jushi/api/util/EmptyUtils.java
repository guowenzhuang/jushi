package com.jushi.api.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

public class EmptyUtils {
    public static boolean isEmpty(@Nullable Object str) {
        return StringUtils.isEmpty(str);
    }
}
