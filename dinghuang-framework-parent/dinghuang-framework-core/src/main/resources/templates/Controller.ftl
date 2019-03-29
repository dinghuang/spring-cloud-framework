package ${package_name}.controller;

import ${package_name}.dto.${table_name}CreateDTO;
import ${package_name}.dto.${table_name}DTO;
import ${package_name}.dto.${table_name}UpdateDTO;
import ${package_name}.dto.${table_name}CreateOrUpdateDTO;
import ${package_name}.service.${table_name}Service;
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
 * ${table_annotation}Controller
 *
 * @author ${author}
 * @since ${date}
 */
@Api(value = "${table_annotation}", tags = {"${table_annotation}"})
@RestController
@RequestMapping(value = "/v1/${table_name_small}s")
public class ${table_name}Controller {

    @Autowired
    private ${table_name}Service ${table_name_first_low}Service;


    @PostMapping
    @ApiOperation(value = "创建${table_annotation}")
    public ResponseEntity<${table_name}DTO> create(@ApiParam(value = "创建${table_annotation}", required = true)
                                                   @Valid @RequestBody ${table_name}CreateDTO ${table_name_first_low}CreateDTO) {
        return Optional.ofNullable(${table_name_first_low}Service.create(${table_name_first_low}CreateDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.${table_name}.create"));
    }

    @PutMapping
    @ApiOperation(value = "更新${table_annotation}")
    public ResponseEntity<${table_name}DTO> update(@ApiParam(value = "更新${table_annotation}", required = true)
                                                   @Valid @RequestBody ${table_name}UpdateDTO ${table_name_first_low}UpdateDTO) {
        return Optional.ofNullable(${table_name_first_low}Service.update(${table_name_first_low}UpdateDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.${table_name}.update"));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除${table_annotation}")
    public void deleteById(@ApiParam(value = "主键", required = true)
                           @PathVariable(name = "id") Long id) {
        ${table_name_first_low}Service.deleteById(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询${table_annotation}")
    public ResponseEntity<${table_name}DTO> queryById(@ApiParam(value = "id", required = true)
                                                      @PathVariable(name = "id") Long id) {
        return Optional.ofNullable(${table_name_first_low}Service.queryById(id))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.${table_name}.queryById"));
    }

    @GetMapping(value = "/query_by_page")
    @ApiOperation(value = "分页查询${table_annotation}")
    public ResponseEntity<PageableDTO<${table_name}DTO>> queryByPage(@Valid PageableSearchDTO pageableSearchDTO) {
        return Optional.ofNullable(${table_name_first_low}Service.queryByPage(pageableSearchDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.${table_name}.queryByPage"));
    }

    @PostMapping(value = "/batch_create_or_update")
    @ApiOperation(value = "批量创建、更新${table_annotation}")
    public ResponseEntity<List<${table_name}DTO>> batchCreateOrUpdate(@ApiParam(value = "创建、更新${table_annotation}列表", required = true)
                                                                        @Valid @RequestBody ValidList<${table_name}CreateOrUpdateDTO> ${table_name_first_low}CreateOrUpdateDTOS) {
        return Optional.ofNullable(${table_name_first_low}Service.batchCreateOrUpdate(${table_name_first_low}CreateOrUpdateDTOS))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.${table_name}.batchCreateOrUpdate"));
    }

    @DeleteMapping(value = "/batch_deleted")
    @ApiOperation(value = "批量删除${table_annotation}")
    public void batchDeleted(@ApiParam(value = "批量删除${table_annotation}id列表", required = true)
                             @Valid @RequestBody List<Long> ids) {
        ${table_name_first_low}Service.batchDeleted(ids);
    }

}
