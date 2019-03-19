package org.dinghuang.core.mybatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/5
 */
@Data
@AllArgsConstructor
public class ValidatorProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorProvider.class);

    private final Validator validator;

    public <T> ViolationBuild validate(T object) {
        Set<ConstraintViolation<T>> violations;
        try {
            violations = validator.validate(object);
        } catch (IllegalArgumentException | ValidationException e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
        return ViolationBuild.build(violations);
    }

    public <T> ViolationBuild validate(T object, Class<?>... groups) {
        Set<ConstraintViolation<T>> violations;
        try {
            violations = validator.validate(object, groups);
        } catch (IllegalArgumentException | ValidationException e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
        return ViolationBuild.build(violations);
    }

    public <T> ViolationBuild validateProperty(T object, String propertyName, Class<?>... groups) {
        Set<ConstraintViolation<T>> violations;
        try {
            violations = validator.validateProperty(object, propertyName, groups);
        } catch (IllegalArgumentException | ValidationException e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
        return ViolationBuild.build(violations);
    }

    public <T> ViolationBuild validateValue(Class<T> beanType, String propertyName, Object value, Class<?>... groups) {
        Set<ConstraintViolation<T>> violations;
        try {
            violations = validator.validateValue(beanType, propertyName, value, groups);
        } catch (IllegalArgumentException | ValidationException e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
        return ViolationBuild.build(violations);
    }
}
