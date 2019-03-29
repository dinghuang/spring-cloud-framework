package org.dinghuang.core.dto;

import lombok.Data;

import java.util.List;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/6
 */
@Data
public class PageableDTO<T>{
    /**
     * 当前页数, 从1开始
     */
    private Long pageNumber;
    /**
     * 每页显示的记录数
     */
    private Long pageSize;
    /**
     * 总记录数
     */
    private Long totalCount;
    /**
     * 总页数
     */
    private Long totalPage;

    /**
     * 对象数组
     */
    private List<T> list;

}
