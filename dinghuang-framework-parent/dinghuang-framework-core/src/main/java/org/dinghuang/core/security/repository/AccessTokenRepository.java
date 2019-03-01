package org.dinghuang.core.security.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.dinghuang.core.security.model.AccessToken;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/1
 */
@Repository
@Mapper
public interface AccessTokenRepository extends BaseMapper<AccessToken> {

    List<AccessToken> selectTokens(@Param("name") String username, @Param("client") String client,
                                   @Param("id") String authenticationId);


    void deleteTokens(@Param("name") String username, @Param("client") String client,
                      @Param("id") String authenticationId);

    void deleteUsersToken(@Param("name") String userName);
}
