package org.dinghuang.core.mybatis.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.Data;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/5
 */
@Data
public class Pageable<T extends BaseModel> implements IPage<T> {

    /**
     * 当前页数, 从1开始
     */
    private long pageNumber = 1;
    /**
     * 每页显示的记录数
     */
    private long pageSize = 10;
    /**
     * 总记录数
     */
    private long totalCount = 0;
    /**
     * 总页数
     */
    private long totalPage = 1;
    /**
     * 是否进行 count 查询
     */
    private boolean searchCount = true;
    /**
     * 自动优化 COUNT SQL
     */
    private boolean optimizeCountSql = true;
    /**
     * 查询数据列表
     */
    private transient List<T> list = Collections.emptyList();
    /**
     * SQL 排序 ASC 数组
     */
    private String[] ascs;
    /**
     * SQL 排序 DESC 数组
     */
    private String[] descs;

    private String searchData;

    private transient Map<String, Object> condition;

    public Pageable(Long pageNumber, Long pageSize, Boolean searchCount, Boolean optimizeCountSql, Map<String, Object> condition, String[] ascs, String[] descs) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.searchCount = searchCount;
        this.optimizeCountSql = optimizeCountSql;
        this.condition = condition;
        this.ascs = ascs;
        this.descs = descs;
    }

    public Pageable() {
    }

    /**
     * 是否存在上一页
     *
     * @return true / false
     */
    public boolean hasPrevious() {
        return this.pageNumber > 1;
    }

    /**
     * 是否存在下一页
     *
     * @return true / false
     */
    public boolean hasNext() {
        return this.pageNumber < this.getPages();
    }

    public Pageable<T> setDescs(List<String> descs) {
        if (CollectionUtils.isNotEmpty(descs)) {
            this.descs = descs.toArray(new String[0]);
        }
        return this;
    }

    /**
     * 降序
     *
     * @param descs 多个降序字段
     */
    public Pageable<T> setDesc(String... descs) {
        this.descs = descs;
        return this;
    }

    public Pageable<T> setAscs(List<String> ascs) {
        if (CollectionUtils.isNotEmpty(ascs)) {
            this.ascs = ascs.toArray(new String[0]);
        }
        return this;
    }

    /**
     * 升序
     *
     * @param ascs 多个升序字段
     */
    public Pageable<T> setAsc(String... ascs) {
        this.ascs = ascs;
        return this;
    }

    @Override
    public String[] descs() {
        return descs;
    }

    @Override
    public String[] ascs() {
        return ascs;
    }


    @Override
    public boolean optimizeCountSql() {
        return optimizeCountSql;
    }

    @Override
    public boolean isSearchCount() {
        if (totalCount < 0) {
            return false;
        }
        return searchCount;
    }

    @Override
    public long getPages() {
        return this.totalPage;
    }

    @Override
    public IPage<T> setPages(long pages) {
        this.totalPage = pages;
        return this;
    }

    @Override
    public List<T> getRecords() {
        return this.list;
    }

    @Override
    public IPage<T> setRecords(List<T> list) {
        this.list = list;
        return this;
    }


    @Override
    public long getTotal() {
        return this.totalCount;
    }

    @Override
    public IPage<T> setTotal(long total) {
        this.totalCount = total;
        return this;
    }

    @Override
    public long getSize() {
        return this.pageSize;
    }

    @Override
    public IPage<T> setSize(long size) {
        this.pageSize = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return this.pageNumber;
    }

    @Override
    public IPage<T> setCurrent(long current) {
        this.pageNumber = current;
        return this;
    }

    @Override
    public <R> IPage<R> convert(Function<? super T, ? extends R> mapper) {
        return null;
    }

    @Override
    public Map<Object, Object> condition() {
        Map<Object, Object> objectObjectMap = new HashMap<>(this.condition.size());
        this.condition.forEach(objectObjectMap::put);
        return objectObjectMap;
    }

    /**
     * 条件
     *
     * @param condition condition
     */
    public Pageable<T> setCondition(Map<String, Object> condition) {
        this.condition = condition;
        return this;
    }
}
