package org.dinghuang.security.repository;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.dinghuang.security.model.User;
import org.springframework.stereotype.Repository;


/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
@Repository
@Mapper
public interface UserRepository extends BaseMapper<User> {
}
