package org.dinghuang.oauth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.dinghuang.core.dto.PageableDTO;
import org.dinghuang.core.dto.PageableSearchDTO;
import org.dinghuang.core.dto.ValidList;
import org.dinghuang.core.exception.CommonException;
import org.dinghuang.oauth.dto.RoleCreateDTO;
import org.dinghuang.oauth.dto.RoleCreateOrUpdateDTO;
import org.dinghuang.oauth.dto.RoleDTO;
import org.dinghuang.oauth.dto.RoleUpdateDTO;
import org.dinghuang.oauth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * 角色Controller
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/08
 */
@Api(value = "角色", tags = {"角色"})
@RestController
@RequestMapping(value = "/v1/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @PostMapping
    @ApiOperation(value = "创建角色")
    public ResponseEntity<RoleDTO> create(@ApiParam(value = "创建角色", required = true)
                                          @Valid @RequestBody RoleCreateDTO roleCreateDTO) {
        return Optional.ofNullable(roleService.create(roleCreateDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.Role.create"));
    }

    @PutMapping
    @ApiOperation(value = "更新角色")
    public ResponseEntity<RoleDTO> update(@ApiParam(value = "更新角色", required = true)
                                          @Valid @RequestBody RoleUpdateDTO roleUpdateDTO) {
        return Optional.ofNullable(roleService.update(roleUpdateDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.Role.update"));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除角色")
    public void deleteById(@ApiParam(value = "主键", required = true)
                           @PathVariable(name = "id") Long id) {
        roleService.deleteById(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询角色")
    public ResponseEntity<RoleDTO> queryById(@ApiParam(value = "id", required = true)
                                             @PathVariable(name = "id") Long id) {
        return Optional.ofNullable(roleService.queryById(id))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.Role.queryById"));
    }

    @GetMapping(value = "/query_by_page")
    @ApiOperation(value = "分页查询角色")
    public ResponseEntity<PageableDTO<RoleDTO>> queryByPage(@Valid PageableSearchDTO pageableSearchDTO) {
        return Optional.ofNullable(roleService.queryByPage(pageableSearchDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.Role.queryByPage"));
    }

    @PostMapping(value = "/batch_create_or_update")
    @ApiOperation(value = "批量创建、更新角色")
    public ResponseEntity<List<RoleDTO>> batchCreateOrUpdate(@ApiParam(value = "创建、更新角色列表", required = true)
                                                             @Valid @RequestBody ValidList<RoleCreateOrUpdateDTO> roleCreateOrUpdateDTOS) {
        return Optional.ofNullable(roleService.batchCreateOrUpdate(roleCreateOrUpdateDTOS))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.Role.batchCreateOrUpdate"));
    }

    @DeleteMapping(value = "/batch_deleted")
    @ApiOperation(value = "批量删除角色")
    public void batchDeleted(@ApiParam(value = "批量删除角色id列表", required = true)
                             @Valid @RequestBody List<Long> ids) {
        roleService.batchDeleted(ids);
    }

}
