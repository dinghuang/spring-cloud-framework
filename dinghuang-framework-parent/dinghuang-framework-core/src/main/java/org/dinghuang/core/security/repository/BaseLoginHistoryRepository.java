package org.dinghuang.core.security.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.dinghuang.core.security.model.BaseLoginHistory;
import org.springframework.stereotype.Repository;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/1
 */
@Repository
@Mapper
public interface BaseLoginHistoryRepository extends BaseMapper<BaseLoginHistory> {
    @Select("select * from oauth_login_history where user_id = #{userId}")
    BaseLoginHistory queryByUserId(@Param("userId") Long userId);
}
