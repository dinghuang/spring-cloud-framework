package org.dinghuang.core.model;


/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
public class Sequence {
    protected String id;
    protected String service;
    protected String object;
    protected String field;
    protected Long current;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }
}
