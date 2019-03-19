package org.dinghuang.core.service;

import org.apache.commons.lang3.StringUtils;
import org.dinghuang.core.exception.CommonException;
import org.dinghuang.core.model.ValidatorProvider;
import org.dinghuang.core.model.ViolationBuild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Validator;

/**
 * 验证器的使用方法，service继承该service
 *
 *
 * <p>
 * ValidatorProvider validatorProvider = getValidatorProvider();
 * ViolationBuild validateFlaw = validatorProvider.validate(submitVO);
 * System.out.println(validateFlaw.getMessage());
 * </p>
 *
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
public abstract class BaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseService.class);

    @Autowired
    protected Validator validator;

    private ValidatorProvider validatorProvider;

    protected ValidatorProvider getValidatorProvider() {
        if (validatorProvider == null) {
            validatorProvider = new ValidatorProvider(validator);
        }
        return validatorProvider;
    }

    /**
     * 对象验证
     *
     * @param object object
     */
    protected void ValidatorData(Object object) {
        ValidatorProvider validatorProvider = getValidatorProvider();
        ViolationBuild validateFlaw = validatorProvider.validate(object);
        if (StringUtils.isNotEmpty(validateFlaw.getMessage()) || StringUtils.isNoneBlank(validateFlaw.getMessage())) {
            throw new CommonException("data validator error", validateFlaw.getMessage());
        }
    }


}
