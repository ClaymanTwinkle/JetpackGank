package com.kesar.jetpackgank.util;

import java.util.Collection;
import java.util.Map;

/**
 * EmptyUtils
 *
 * @author andy <br/>
 * create time: 2019/2/21 10:03
 */
public final class EmptyUtils {
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static <T> T ifNull(T c, T d) {
        return c == null ? d : c;
    }
}
