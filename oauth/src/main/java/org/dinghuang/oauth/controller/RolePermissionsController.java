package org.dinghuang.oauth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.dinghuang.core.dto.PageableDTO;
import org.dinghuang.core.dto.PageableSearchDTO;
import org.dinghuang.core.dto.ValidList;
import org.dinghuang.core.exception.CommonException;
import org.dinghuang.oauth.dto.RolePermissionsCreateDTO;
import org.dinghuang.oauth.dto.RolePermissionsCreateOrUpdateDTO;
import org.dinghuang.oauth.dto.RolePermissionsDTO;
import org.dinghuang.oauth.dto.RolePermissionsUpdateDTO;
import org.dinghuang.oauth.service.RolePermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * 角色权限Controller
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/08
 */
@Api(value = "角色权限", tags = {"角色权限"})
@RestController
@RequestMapping(value = "/v1/role_permissionss")
public class RolePermissionsController {

    @Autowired
    private RolePermissionsService rolePermissionsService;


    @PostMapping
    @ApiOperation(value = "创建角色权限")
    public ResponseEntity<RolePermissionsDTO> create(@ApiParam(value = "创建角色权限", required = true)
                                                     @Valid @RequestBody RolePermissionsCreateDTO rolePermissionsCreateDTO) {
        return Optional.ofNullable(rolePermissionsService.create(rolePermissionsCreateDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.RolePermissions.create"));
    }

    @PutMapping
    @ApiOperation(value = "更新角色权限")
    public ResponseEntity<RolePermissionsDTO> update(@ApiParam(value = "更新角色权限", required = true)
                                                     @Valid @RequestBody RolePermissionsUpdateDTO rolePermissionsUpdateDTO) {
        return Optional.ofNullable(rolePermissionsService.update(rolePermissionsUpdateDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.RolePermissions.update"));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除角色权限")
    public void deleteById(@ApiParam(value = "主键", required = true)
                           @PathVariable(name = "id") Long id) {
        rolePermissionsService.deleteById(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询角色权限")
    public ResponseEntity<RolePermissionsDTO> queryById(@ApiParam(value = "id", required = true)
                                                        @PathVariable(name = "id") Long id) {
        return Optional.ofNullable(rolePermissionsService.queryById(id))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.RolePermissions.queryById"));
    }

    @GetMapping(value = "/query_by_page")
    @ApiOperation(value = "分页查询角色权限")
    public ResponseEntity<PageableDTO<RolePermissionsDTO>> queryByPage(@Valid PageableSearchDTO pageableSearchDTO) {
        return Optional.ofNullable(rolePermissionsService.queryByPage(pageableSearchDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.RolePermissions.queryByPage"));
    }

    @PostMapping(value = "/batch_create_or_update")
    @ApiOperation(value = "批量创建、更新角色权限")
    public ResponseEntity<List<RolePermissionsDTO>> batchCreateOrUpdate(@ApiParam(value = "创建、更新角色权限列表", required = true)
                                                                        @Valid @RequestBody ValidList<RolePermissionsCreateOrUpdateDTO> rolePermissionsCreateOrUpdateDTOS) {
        return Optional.ofNullable(rolePermissionsService.batchCreateOrUpdate(rolePermissionsCreateOrUpdateDTOS))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.RolePermissions.batchCreateOrUpdate"));
    }

    @DeleteMapping(value = "/batch_deleted")
    @ApiOperation(value = "批量删除角色权限")
    public void batchDeleted(@ApiParam(value = "批量删除角色权限id列表", required = true)
                             @Valid @RequestBody List<Long> ids) {
        rolePermissionsService.batchDeleted(ids);
    }

}
