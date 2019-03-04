package org.dinghuang.core.annotation;

import org.apache.ibatis.annotations.Mapper;

import java.lang.annotation.*;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/4
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Mapper
public @interface Repository {
}
