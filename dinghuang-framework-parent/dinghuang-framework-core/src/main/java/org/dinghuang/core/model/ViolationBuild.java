package org.dinghuang.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/5
 */
@Data
@AllArgsConstructor
public class ViolationBuild {
    private Set<Violation> violations;

    public String getMessage() {
        List<String> list = new ArrayList<>(violations.size());
        for (Violation violation : violations) {
            list.add(violation.getMessage());
        }
        return !list.isEmpty() ? list.get(0) : "";
    }

    static <T> ViolationBuild build(Set<ConstraintViolation<T>> cvs) {
        Set<Violation> result = new HashSet<>(cvs.size());
        if (CollectionUtils.isNotEmpty(cvs)) {
            for (ConstraintViolation cv : cvs) {
                result.add(new Violation(cv.getMessage(), cv.getRootBean() == null ? null : cv.getRootBean().toString(),
                        cv.getPropertyPath() == null ? null : cv.getPropertyPath().toString(),
                        cv.getInvalidValue()));
            }
        }
        return new ViolationBuild(result);
    }
}
