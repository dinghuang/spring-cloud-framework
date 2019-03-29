package org.dinghuang.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
@Data
public abstract class BaseModelDTO {

    @ApiModelProperty(notes = "创建人id")
    protected String createdBy;

    @ApiModelProperty(notes = "创建人名称")
    protected String createdByName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(notes = "创建日期")
    protected Date creationDate;

    @ApiModelProperty(notes = "最后更新人id")
    protected String lastUpdatedBy;

    @ApiModelProperty(notes = "最后更新人名称")
    protected String lastUpdatedByName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(notes = "最后更新日期")
    protected Date lastUpdatedDate;

    @ApiModelProperty(notes = "乐观锁版本号")
    @NotNull(message = "objectVersionNumber:乐观锁不能为空")
    protected Long objectVersionNumber;

}
