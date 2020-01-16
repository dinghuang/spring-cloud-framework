package org.dinghuang.demo.mapper;

import org.dinghuang.demo.infra.dataobject.TestDO;
import org.dinghuang.demo.dto.test.TestDTO;
import org.dinghuang.demo.dto.test.TestCreateDTO;
import org.dinghuang.demo.dto.test.TestUpdateDTO;
import org.dinghuang.demo.dto.test.TestCreateOrUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.dinghuang.core.dto.PageableDTO;
import org.dinghuang.core.mybatis.model.Pageable;

import java.util.List;

/**
 * 测试对象Mapper
 *
 * @author dinghuang123@gmail.com
 * @since 2020/01/16
 */
@Mapper
public interface TestMapper {

    TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);

    /**
     * createDto到do的转换
     *
     * @param testCreateDTO testCreateDTO
     * @return TestDO
     */
    TestDO createDtoToDo(TestCreateDTO testCreateDTO);

    /**
     * updateDto到do的转换
     *
     * @param testUpdateDTO testUpdateDTO
     * @return TestDO
     */
    TestDO updateDtoToDo(TestUpdateDTO testUpdateDTO);

    /**
     * createOrUpdateDto到dto的转换
     *
     * @param testCreateOrUpdateDTO testCreateOrUpdateDTO
     * @return TestDO
     */
    TestDO createOrUpdateDtoToDo(TestCreateOrUpdateDTO testCreateOrUpdateDTO);

    /**
     * do到dto的转换
     *
     * @param testDO testDO
     * @return TestDTO
     */
    TestDTO doToDto(TestDO testDO);

    /**
     * do到dto列表转换
     *
     * @param testDOS testDOS
     * @return TestDTO
     */
    List<TestDTO> doToDtos(List<TestDO> testDOS);

    /**
     * do到dto分页转换
     *
     * @param testDOPageable testDOPageable
     * @return TestDTO
     */
    PageableDTO<TestDTO> pageDoToDto(Pageable<TestDO> testDOPageable);
}