package org.dinghuang.core.mybatis.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;
import com.baomidou.mybatisplus.core.injector.methods.*;
import org.dinghuang.core.mybatis.injector.LockRecord;
import org.dinghuang.core.mybatis.injector.QueryLock;
import org.dinghuang.core.mybatis.injector.Unlock;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * 自定义mapper中的方法实现
 *
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
@Configuration
public class SqlInjectorConfiguration extends AbstractSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList() {
        return Stream.of(
                new Insert(),
                new Delete(),
                new DeleteByMap(),
                new DeleteById(),
                new DeleteBatchByIds(),
                new Update(),
                new UpdateById(),
                new SelectById(),
                new SelectBatchByIds(),
                new SelectByMap(),
                new SelectOne(),
                new SelectCount(),
                new SelectMaps(),
                new SelectMapsPage(),
                new SelectObjs(),
                new SelectList(),
                new SelectPage(),
                new LockRecord(),
                new QueryLock(),
                new Unlock()
        ).collect(toList());
    }
}
