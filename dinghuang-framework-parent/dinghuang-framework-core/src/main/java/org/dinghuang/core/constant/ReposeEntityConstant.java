package org.dinghuang.core.constant;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/19
 */
public class ReposeEntityConstant {

    private ReposeEntityConstant() {
        throw new IllegalStateException("Utility class");
    }

    public static final String MISSING_REQUEST_PARAMETER = "缺少请求参数";
    public static final String PARAMETER_VERIFICATION_FAILED = "参数验证失败:";
    public static final String PARAMETER_BINDING_FAILED = "参数绑定失败";
    public static final String REQUEST_METHOD_NOT_SUPPORT = "不支持当前请求方法";
    public static final String MEDIA_TYPE_NOT_SUPPORT = "不支持当前媒体类型";
    public static final String BUSINESS_LOGIC_EXCEPTION = "业务逻辑异常";
    public static final String VALIDATE_EXCEPTION = "数据验证异常:";
    public static final String GENERAL_EXCEPTION = "通用异常:";
    public static final String EXECUTE_DATABASE_EXCEPTION = "执行数据库异常:";
    public static final String INVOKING_EXTERNAL_SERVICE_EXCEPTION = "调用外部服务异常";
    public static final String INTERFACE_MIGRATION = "接口迁移中";
    public static final String INSERT_EXCEPTION = "插入数据失败:";
    public static final String DELETE_EXCEPTION = "删除数据失败:id对应的记录未找到";
    public static final String UPDATE_EXCEPTION = "更新数据失败:id对应的记录未找到或乐观锁版本不一致";

}
