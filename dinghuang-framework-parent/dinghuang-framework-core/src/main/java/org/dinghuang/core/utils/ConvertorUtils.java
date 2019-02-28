package org.dinghuang.core.utils;

import org.dinghuang.core.exception.ConvertorException;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 转换工具类
 *
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
public class ConvertorUtils {

    private ConvertorUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static final String ERROR_CONVERT = "error.convert";

    /**
     * 转换到目标类
     *
     * @param source source
     * @return target
     */
    @SuppressWarnings("unchecked")
    public static <T, V> V toTarget(T source, Class<V> tClass) {
        if (source == null) {
            return null;
        } else {
            try {
                V target = tClass.newInstance();
                if (target != null) {
                    BeanUtils.copyProperties(source, target);
                }
                return target;
            } catch (InstantiationException | IllegalAccessException e) {
                throw new ConvertorException(ERROR_CONVERT, e);
            }
        }
    }

    /**
     * List转换到目标类
     *
     * @param source source
     * @return target
     */
    @SuppressWarnings("unchecked")
    public static <T extends List, V> List<V> toTargetList(T source, Class<V> tClass) {
        if (source == null) {
            return new ArrayList<>();
        } else {
            List<V> targetList = new ArrayList<>(source.size());
            if (!source.isEmpty()) {
                source.forEach(s -> {
                    V target = toTarget(s, tClass);
                    targetList.add(target);
                });
            }
            return targetList;
        }
    }
}
