package org.dinghuang.core.exception;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/12
 */
public class CommonValidateException extends RuntimeException {
    private final transient Object[] parameters;

    /**
     * 构造器
     *
     * @param parameters parameters
     */
    public CommonValidateException(Object... parameters) {
        this.parameters = parameters;
    }

    public CommonValidateException(String code, Throwable cause, Object... parameters) {
        super(code, cause);
        this.parameters = parameters;
    }

    public CommonValidateException(String code, Throwable cause) {
        super(code, cause);
        this.parameters = new Object[]{};
    }


    public CommonValidateException(Throwable cause, Object... parameters) {
        super(cause);
        this.parameters = parameters;
    }

    public Object[] getParameters() {
        return parameters;
    }


    public String getTrace() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        this.printStackTrace(ps);
        ps.flush();
        return new String(baos.toByteArray());
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new LinkedHashMap<>();
        map.put("messages", super.getMessage());
        return map;
    }
}
