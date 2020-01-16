package org.dinghuang.demo.service;

import org.dinghuang.demo.dto.test.TestCreateDTO;
import org.dinghuang.demo.dto.test.TestDTO;
import org.dinghuang.demo.dto.test.TestUpdateDTO;
import org.dinghuang.demo.dto.test.TestCreateOrUpdateDTO;
import org.dinghuang.demo.infra.dataobject.TestDO;
import org.dinghuang.demo.infra.repository.TestRepository;
import org.dinghuang.demo.mapper.TestMapper;
import org.dinghuang.core.dto.PageableDTO;
import org.dinghuang.core.dto.PageableSearchDTO;
import org.dinghuang.core.exception.DeleteException;
import org.dinghuang.core.exception.InsertException;
import org.dinghuang.core.exception.UpdateException;
import org.dinghuang.core.mapper.PageableMapper;
import org.dinghuang.core.mybatis.model.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 测试对象Service
 *
 * @author dinghuang123@gmail.com
 * @since 2020/01/16
 */
@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    @Transactional(rollbackFor = Exception.class)
    public TestDTO create(TestCreateDTO testCreateDTO) {
        TestDO testDO = TestMapper.INSTANCE.createDtoToDo(testCreateDTO);
        if (testRepository.insert(testDO) == 1) {
            return queryById(testDO.getId());
        } else {
            throw new InsertException("error.Test.create");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public TestDTO update(TestUpdateDTO testUpdateDTO) {
        TestDO testDO = TestMapper.INSTANCE.updateDtoToDo(testUpdateDTO);
        if (testRepository.updateById(testDO) == 1) {
            return queryById(testDO.getId());
        } else {
            throw new UpdateException("error.Test.update");
        }
    }

    public TestDTO queryById(Long id) {
        return TestMapper.INSTANCE.doToDto(testRepository.selectById(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        if (testRepository.deleteById(id) != 1) {
            throw new DeleteException("error.Test.deleteById");
        }
    }

    @SuppressWarnings("unchecked")
    public PageableDTO<TestDTO> queryByPage(PageableSearchDTO pageableSearchDTO) {
        Pageable<TestDO> testDOPageable = testRepository.selectPageCondition(PageableMapper.INSTANCE.searchDtoToDo(pageableSearchDTO), null);
        return TestMapper.INSTANCE.pageDoToDto(testDOPageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<TestDTO> batchCreateOrUpdate(List<TestCreateOrUpdateDTO> testCreateOrUpdateDTOS) {
        List<TestDTO> testDTOS = Collections.synchronizedList(new ArrayList<>(testCreateOrUpdateDTOS.size()));
        testCreateOrUpdateDTOS.forEach(testUpdateDTO -> {
            TestDO testDO = TestMapper.INSTANCE.createOrUpdateDtoToDo(testUpdateDTO);
            testRepository.insertOrUpdate(testDO);
            testDTOS.add(queryById(testDO.getId()));
        });
        return testDTOS;
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchDeleted(List<Long> ids) {
        ids.forEach(this::deleteById);
    }
}
