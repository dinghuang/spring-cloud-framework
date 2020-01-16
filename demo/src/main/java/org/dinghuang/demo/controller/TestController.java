package org.dinghuang.demo.controller;

import org.dinghuang.demo.dto.test.TestCreateDTO;
import org.dinghuang.demo.dto.test.TestDTO;
import org.dinghuang.demo.dto.test.TestUpdateDTO;
import org.dinghuang.demo.dto.test.TestCreateOrUpdateDTO;
import org.dinghuang.demo.service.TestService;
import org.dinghuang.core.dto.ValidList;
import org.dinghuang.core.dto.PageableDTO;
import org.dinghuang.core.dto.PageableSearchDTO;
import org.dinghuang.core.exception.CommonException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * 测试对象Controller
 *
 * @author dinghuang123@gmail.com
 * @since 2020/01/16
 */
@Api(value = "测试对象", tags = {"测试对象"})
@RestController
@RequestMapping(value = "/v1/tests")
public class TestController {

    @Autowired
    private TestService testService;


    @PostMapping
    @ApiOperation(value = "创建测试对象")
    public ResponseEntity<TestDTO> create(@ApiParam(value = "创建测试对象", required = true)
                                                   @Valid @RequestBody TestCreateDTO testCreateDTO) {
        return Optional.ofNullable(testService.create(testCreateDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.Test.create"));
    }

    @PutMapping
    @ApiOperation(value = "更新测试对象")
    public ResponseEntity<TestDTO> update(@ApiParam(value = "更新测试对象", required = true)
                                                   @Valid @RequestBody TestUpdateDTO testUpdateDTO) {
        return Optional.ofNullable(testService.update(testUpdateDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.Test.update"));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除测试对象")
    public void deleteById(@ApiParam(value = "主键", required = true)
                           @PathVariable(name = "id") Long id) {
        testService.deleteById(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询测试对象")
    public ResponseEntity<TestDTO> queryById(@ApiParam(value = "id", required = true)
                                                      @PathVariable(name = "id") Long id) {
        return Optional.ofNullable(testService.queryById(id))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.Test.queryById"));
    }

    @GetMapping(value = "/query_by_page")
    @ApiOperation(value = "分页查询测试对象")
    public ResponseEntity<PageableDTO<TestDTO>> queryByPage(@Valid PageableSearchDTO pageableSearchDTO) {
        return Optional.ofNullable(testService.queryByPage(pageableSearchDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.Test.queryByPage"));
    }

    @PostMapping(value = "/batch_create_or_update")
    @ApiOperation(value = "批量创建、更新测试对象")
    public ResponseEntity<List<TestDTO>> batchCreateOrUpdate(@ApiParam(value = "创建、更新测试对象列表", required = true)
                                                                        @Valid @RequestBody ValidList<TestCreateOrUpdateDTO> testCreateOrUpdateDTOS) {
        return Optional.ofNullable(testService.batchCreateOrUpdate(testCreateOrUpdateDTOS))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.Test.batchCreateOrUpdate"));
    }

    @DeleteMapping(value = "/batch_deleted")
    @ApiOperation(value = "批量删除测试对象")
    public void batchDeleted(@ApiParam(value = "批量删除测试对象id列表", required = true)
                             @Valid @RequestBody List<Long> ids) {
        testService.batchDeleted(ids);
    }

}
