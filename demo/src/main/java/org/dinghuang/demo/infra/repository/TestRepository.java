package org.dinghuang.demo.infra.repository;

import org.dinghuang.demo.infra.dataobject.TestDO;
import org.dinghuang.core.annotation.Repository;
import org.dinghuang.core.mybatis.BaseRepository;

/**
 * 测试对象Repository
 *
 * @author dinghuang123@gmail.com
 * @since 2020/01/16
 */
@Repository
public interface TestRepository extends BaseRepository<TestDO> {

}