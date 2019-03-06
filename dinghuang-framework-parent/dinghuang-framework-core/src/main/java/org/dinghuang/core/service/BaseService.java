package org.dinghuang.core.service;

import javax.validation.Validator;

import org.dinghuang.core.model.ValidatorProvider;
import org.dinghuang.core.model.ViolationBuild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
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
        LOGGER.info(validateFlaw.getMessage());
        System.out.println();
    }


}
