package com.xudong.core.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;

public class CollectionUtil extends CollectionUtils {

    public static <T> List<T> arrayToListNotNull(Object... objs) {
        List list = CollectionUtils.arrayToList(objs);
        list.removeAll(Collections.singleton(null));
        return list;
    }

    public static String join(Collection<? extends Serializable> collection, String separator) {
        if (CollectionUtils.isEmpty(collection)) {
            return StringUtils.EMPTY;
        }
        return StringUtils.join(collection.toArray(), ",");
    }

    /**
     * 数组转set
     * @param array
     * @param <T>
     * @return
     */
    public static <T> Set<T> arrayToSet(Object[] array) {
        if(ArrayUtils.isEmpty(array)){
            return new HashSet<>();
        }
        return new HashSet<>(CollectionUtils.arrayToList(array));
    }
}
