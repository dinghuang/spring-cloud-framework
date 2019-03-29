package org.dinghuang.core.mapper;

import org.dinghuang.core.dto.PageableDTO;
import org.dinghuang.core.dto.PageableSearchDTO;
import org.dinghuang.core.mybatis.model.Pageable;
import org.dinghuang.core.utils.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/6
 */
@Mapper
public interface PageableMapper {

    Logger LOGGER = LoggerFactory.getLogger(PageableMapper.class);

    PageableMapper INSTANCE = Mappers.getMapper(PageableMapper.class);

    PageableDTO doToDto(Pageable pageable);

    /**
     * 对查询参数进行处理
     *
     * @param pageableSearchDTO pageableSearchDTO
     * @return Pageable
     */
    default Pageable searchDtoToDo(PageableSearchDTO pageableSearchDTO) {
        Boolean flag = pageableSearchDTO.getKey() != null && pageableSearchDTO.getValue() != null;
        if (flag && pageableSearchDTO.getKey().length != pageableSearchDTO.getValue().length) {
            LOGGER.error("key value length error");
            return new Pageable();
        }
        Map<Object, Object> condition = new HashMap<>(flag ? pageableSearchDTO.getKey().length : 0);
        if (flag) {
            for (int i = 0; i < pageableSearchDTO.getKey().length; i++) {
                condition.put(StringUtils.HumpToUnderline(pageableSearchDTO.getKey()[i]), pageableSearchDTO.getValue()[i]);
            }
        }
        if (pageableSearchDTO.getDescs() != null) {
            List<String> strings = new ArrayList<>(pageableSearchDTO.getDescs().length);
            for (int i = 0; i < pageableSearchDTO.getDescs().length; i++) {
                strings.add(StringUtils.HumpToUnderline(pageableSearchDTO.getDescs()[i]));
            }
            pageableSearchDTO.setDescs(strings.toArray(new String[0]));
        }
        if (pageableSearchDTO.getAscs() != null) {
            List<String> strings = new ArrayList<>(pageableSearchDTO.getAscs().length);
            for (int i = 0; i < pageableSearchDTO.getAscs().length; i++) {
                strings.add(StringUtils.HumpToUnderline(pageableSearchDTO.getAscs()[i]));
            }
            pageableSearchDTO.setAscs(strings.toArray(new String[0]));
        }
        return new Pageable(pageableSearchDTO.getPageNumber(), pageableSearchDTO.getPageSize(), pageableSearchDTO.getSearchCount(),
                pageableSearchDTO.getOptimizeCountSql(), condition, pageableSearchDTO.getAscs(), pageableSearchDTO.getDescs());

    }


}
