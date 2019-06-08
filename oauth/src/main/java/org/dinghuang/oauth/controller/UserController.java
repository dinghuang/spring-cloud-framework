package org.dinghuang.oauth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.dinghuang.core.dto.PageableDTO;
import org.dinghuang.core.dto.PageableSearchDTO;
import org.dinghuang.core.dto.ValidList;
import org.dinghuang.core.exception.CommonException;
import org.dinghuang.oauth.dto.UserCreateDTO;
import org.dinghuang.oauth.dto.UserCreateOrUpdateDTO;
import org.dinghuang.oauth.dto.UserDTO;
import org.dinghuang.oauth.dto.UserUpdateDTO;
import org.dinghuang.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * 用户信息Controller
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/07
 */
@Api(value = "用户信息", tags = {"用户信息"})
@RestController
@RequestMapping(value = "/v1/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping
    @ApiOperation(value = "创建用户信息")
    public ResponseEntity<UserDTO> create(@ApiParam(value = "创建用户信息", required = true)
                                          @Valid @RequestBody UserCreateDTO userCreateDTO) {
        return Optional.ofNullable(userService.create(userCreateDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.User.create"));
    }

    @PutMapping
    @ApiOperation(value = "更新用户信息")
    public ResponseEntity<UserDTO> update(@ApiParam(value = "更新用户信息", required = true)
                                          @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        return Optional.ofNullable(userService.update(userUpdateDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.User.update"));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除用户信息")
    public void deleteById(@ApiParam(value = "主键", required = true)
                           @PathVariable(name = "id") Long id) {
        userService.deleteById(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询用户信息")
    public ResponseEntity<UserDTO> queryById(@ApiParam(value = "id", required = true)
                                             @PathVariable(name = "id") Long id) {
        return Optional.ofNullable(userService.queryById(id))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.User.queryById"));
    }

    @GetMapping(value = "/query_by_page")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "分页查询用户信息")
    public ResponseEntity<PageableDTO<UserDTO>> queryByPage(@Valid PageableSearchDTO pageableSearchDTO) {
        return Optional.ofNullable(userService.queryByPage(pageableSearchDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.User.queryByPage"));
    }

    @PostMapping(value = "/batch_create_or_update")
    @ApiOperation(value = "批量创建、更新用户信息")
    public ResponseEntity<List<UserDTO>> batchCreateOrUpdate(@ApiParam(value = "创建、更新用户信息列表", required = true)
                                                             @Valid @RequestBody ValidList<UserCreateOrUpdateDTO> userCreateOrUpdateDTOS) {
        return Optional.ofNullable(userService.batchCreateOrUpdate(userCreateOrUpdateDTOS))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.User.batchCreateOrUpdate"));
    }

    @DeleteMapping(value = "/batch_deleted")
    @ApiOperation(value = "批量删除用户信息")
    public void batchDeleted(@ApiParam(value = "批量删除用户信息id列表", required = true)
                             @Valid @RequestBody List<Long> ids) {
        userService.batchDeleted(ids);
    }

}
