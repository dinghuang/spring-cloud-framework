package org.dinghuang.core.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
public class ModelValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelValidator.class);

    private static final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();

    public static <T> Map<String, Object> validate(T t) {
        Map<String, Object> map = new HashMap<>();
        if (t == null) {
            map.put("object", "要验证的对象为空");
            return map;
        }
        Validator validator = VALIDATOR_FACTORY.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        List<String> messageList = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        messageList.forEach(message -> {
            String[] attr = message.split(":");
            if (attr.length != 2) {
                LOGGER.error("验证消息格式不合法: {}", message);
                return;
            }
            map.put(attr[0], attr[1]);
        });
        return map;
    }
}
