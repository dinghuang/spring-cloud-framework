package org.dinghuang.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/26
 */
@Data
@ApiModel(description = "分页查询对象")
public class PageableSearchDTO {

    @ApiModelProperty(notes = "页数")
    private Long pageNumber = 1L;

    @ApiModelProperty(notes = "每页显示的记录数")
    private Long pageSize = 10L;

    @ApiModelProperty(notes = "是否统计查询")
    private Boolean searchCount = false;

    @ApiModelProperty(notes = "是否自动优化统计查询sql")
    private Boolean optimizeCountSql = false;

    @ApiModelProperty(notes = "SQL 排序 ASC 数组")
    private String[] ascs;

    @ApiModelProperty(notes = "SQL 排序 DESC 数组")
    private String[] descs;

    @ApiModelProperty(notes = "SQL查询字段名(驼峰命名)")
    private String[] key;

    @ApiModelProperty(notes = "SQL查询字段值")
    private Object[] value;

    @ApiModelProperty(notes = "自定义查询的json数据")
    private String message;

    public PageableDTO initEmptyPageableDto() {
        PageableDTO pageableDTO = new PageableDTO<>();
        pageableDTO.setPageNumber(this.pageNumber);
        pageableDTO.setPageSize(this.pageSize);
        pageableDTO.setTotalPage(1L);
        pageableDTO.setTotalCount(0L);
        return pageableDTO;
    }
}
