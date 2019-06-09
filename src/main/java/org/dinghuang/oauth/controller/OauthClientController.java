package org.dinghuang.oauth.controller;

import org.dinghuang.oauth.dto.OauthClientCreateDTO;
import org.dinghuang.oauth.dto.OauthClientDTO;
import org.dinghuang.oauth.dto.OauthClientUpdateDTO;
import org.dinghuang.oauth.dto.OauthClientCreateOrUpdateDTO;
import org.dinghuang.oauth.service.OauthClientService;
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
 * 认证客户端Controller
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/09
 */
@Api(value = "认证客户端", tags = {"认证客户端"})
@RestController
@RequestMapping(value = "/v1/oauth_clients")
public class OauthClientController {

    @Autowired
    private OauthClientService oauthClientService;


    @PostMapping
    @ApiOperation(value = "创建认证客户端")
    public ResponseEntity<OauthClientDTO> create(@ApiParam(value = "创建认证客户端", required = true)
                                                   @Valid @RequestBody OauthClientCreateDTO oauthClientCreateDTO) {
        return Optional.ofNullable(oauthClientService.create(oauthClientCreateDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.OauthClient.create"));
    }

    @PutMapping
    @ApiOperation(value = "更新认证客户端")
    public ResponseEntity<OauthClientDTO> update(@ApiParam(value = "更新认证客户端", required = true)
                                                   @Valid @RequestBody OauthClientUpdateDTO oauthClientUpdateDTO) {
        return Optional.ofNullable(oauthClientService.update(oauthClientUpdateDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.OauthClient.update"));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除认证客户端")
    public void deleteById(@ApiParam(value = "主键", required = true)
                           @PathVariable(name = "id") Long id) {
        oauthClientService.deleteById(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询认证客户端")
    public ResponseEntity<OauthClientDTO> queryById(@ApiParam(value = "id", required = true)
                                                      @PathVariable(name = "id") Long id) {
        return Optional.ofNullable(oauthClientService.queryById(id))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.OauthClient.queryById"));
    }

    @GetMapping(value = "/query_by_page")
    @ApiOperation(value = "分页查询认证客户端")
    public ResponseEntity<PageableDTO<OauthClientDTO>> queryByPage(@Valid PageableSearchDTO pageableSearchDTO) {
        return Optional.ofNullable(oauthClientService.queryByPage(pageableSearchDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.OauthClient.queryByPage"));
    }

    @PostMapping(value = "/batch_create_or_update")
    @ApiOperation(value = "批量创建、更新认证客户端")
    public ResponseEntity<List<OauthClientDTO>> batchCreateOrUpdate(@ApiParam(value = "创建、更新认证客户端列表", required = true)
                                                                        @Valid @RequestBody ValidList<OauthClientCreateOrUpdateDTO> oauthClientCreateOrUpdateDTOS) {
        return Optional.ofNullable(oauthClientService.batchCreateOrUpdate(oauthClientCreateOrUpdateDTOS))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.OauthClient.batchCreateOrUpdate"));
    }

    @DeleteMapping(value = "/batch_deleted")
    @ApiOperation(value = "批量删除认证客户端")
    public void batchDeleted(@ApiParam(value = "批量删除认证客户端id列表", required = true)
                             @Valid @RequestBody List<Long> ids) {
        oauthClientService.batchDeleted(ids);
    }

}
