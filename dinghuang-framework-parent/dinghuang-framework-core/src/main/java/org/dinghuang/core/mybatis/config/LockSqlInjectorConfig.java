package org.dinghuang.core.mybatis.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;
import org.dinghuang.core.mybatis.injector.LockData;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
public class LockSqlInjectorConfig extends AbstractSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList() {
        //todo 可以添加自定义的方法，集成baseMapper就可以拥有该方法
        return Stream.of(
                new LockData()
        ).collect(toList());
    }
}
