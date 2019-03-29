package org.dinghuang.core.model;

import lombok.Data;

/**
 * 数据库字段封装类
 *
 * @author dinghuang123@gmail.com
 * @since 2019/3/20
 */
@Data
public class ColumnClass {

    /**
     * 数据库字段名称
     **/
    private String columnName;

    /**
     * 数据库字段类型
     **/
    private String columnType;

    /**
     * 数据库字段首字母小写且去掉下划线字符串
     **/
    private String changeColumnName;

    /**
     * 数据库字段大写
     **/
    private String changeColumnNameUp;

    /**
     * 数据库字段注释
     **/
    private String columnComment;

    /**
     * 数据库字段长度
     **/
    private String columnLength;

    /**
     * 数据库字段长度
     **/
    private Boolean isNullAble;

    /**
     * 是否主键
     **/
    private Boolean isPrimary;

}
