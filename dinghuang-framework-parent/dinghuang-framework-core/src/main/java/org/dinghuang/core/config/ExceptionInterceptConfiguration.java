package org.dinghuang.core.config;

import org.dinghuang.core.constant.ReposeEntityConstant;
import org.dinghuang.core.exception.*;
import org.dinghuang.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.Set;

/**
 * Controller异常拦截器，后续可以继续添加进来一些异常
 *
 * @author dinghuang123@gmail.com
 * @since 2019/3/7
 */
@ControllerAdvice
@ResponseBody
@ConditionalOnProperty(prefix = "dinghuang.exception-intercept", name = "enabled", havingValue = "true")
public class ExceptionInterceptConfiguration {

    @Value("${dinghuang.exception-intercept.logging:true}")
    private boolean logging;

    private static Logger LOGGER = LoggerFactory.getLogger(ExceptionInterceptConfiguration.class);

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        if (logging) {
            LOGGER.error("required parameter is not present{}", e);
        }
        return new ResponseEntity<>(ReposeEntityConstant.MISSING_REQUEST_PARAMETER, HttpStatus.BAD_REQUEST);
    }

    /**
     * 404
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseEntity handlePageNotFoundException(NoHandlerFoundException e) {
        if (logging) {
            LOGGER.error("interface migration...{}", e);
        }
        return new ResponseEntity<>(ReposeEntityConstant.INTERFACE_MIGRATION, HttpStatus.NOT_FOUND);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        if (logging) {
            LOGGER.error("required parameter is not present{}", e);
        }
        return new ResponseEntity<>(ReposeEntityConstant.MISSING_REQUEST_PARAMETER, HttpStatus.BAD_REQUEST);
    }

    /**
     * 参数验证异常
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        if (logging) {
            LOGGER.error("parameter verification failed{}", e);
        }
        return new ResponseEntity<>(String.format("%s%s",
                ReposeEntityConstant.PARAMETER_VERIFICATION_FAILED,
                e.getBindingResult().getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity handleBindException(BindException e) {
        if (logging) {
            LOGGER.error("parameter binding failed{}", e);
        }
        return new ResponseEntity<>(String.format("%s %s %s:%s",
                ReposeEntityConstant.PARAMETER_BINDING_FAILED,
                e.getBindingResult().getObjectName(),
                e.getBindingResult().getFieldError().getField(),
                e.getBindingResult().getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleServiceException(ConstraintViolationException e) {
        if (logging) {
            LOGGER.error("parameter verification failed{}", e);
        }
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        return new ResponseEntity<>(ReposeEntityConstant.PARAMETER_VERIFICATION_FAILED + message, HttpStatus.BAD_REQUEST);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handleValidationException(ValidationException e) {
        if (logging) {
            LOGGER.error("parameter verification failed{}", e);
        }
        return new ResponseEntity<>(ReposeEntityConstant.PARAMETER_VERIFICATION_FAILED, HttpStatus.BAD_REQUEST);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InsertException.class)
    public ResponseEntity handleInsertException(InsertException e) {
        if (logging) {
            LOGGER.error("insert data failed{}", e);
        }
        return new ResponseEntity<>(ReposeEntityConstant.INSERT_EXCEPTION + e, HttpStatus.BAD_REQUEST);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DeleteException.class)
    public ResponseEntity handleDeleteException(DeleteException e) {
        if (logging) {
            LOGGER.error("delete data failed{}", e);
        }
        return new ResponseEntity<>(ReposeEntityConstant.DELETE_EXCEPTION, HttpStatus.BAD_REQUEST);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UpdateException.class)
    public ResponseEntity handleUpdateException(UpdateException e) {
        if (logging) {
            LOGGER.error("delete data failed{}", e);
        }
        return new ResponseEntity<>(ReposeEntityConstant.UPDATE_EXCEPTION, HttpStatus.BAD_REQUEST);
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        if (logging) {
            LOGGER.error("request method not supported{}", e);
        }
        return new ResponseEntity<>(ReposeEntityConstant.REQUEST_METHOD_NOT_SUPPORT, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        if (logging) {
            LOGGER.error("content type not supported{}", e);
        }
        return new ResponseEntity<>(ReposeEntityConstant.MEDIA_TYPE_NOT_SUPPORT, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CommonException.class)
    public ResponseEntity handleServiceException(CommonException e) {
        if (logging) {
            LOGGER.error("business logic exception{}", e);
        }
        return new ResponseEntity<>(ReposeEntityConstant.BUSINESS_LOGIC_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CommonValidateException.class)
    public ResponseEntity handleValidateException(CommonValidateException e) {
        if (logging) {
            LOGGER.error("data validation exception{}", e);
        }
        return new ResponseEntity<>(ReposeEntityConstant.VALIDATE_EXCEPTION + Arrays.toString(e.getParameters()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        if (logging) {
            LOGGER.error("general exception{}", e);
        }
        return new ResponseEntity<>(ReposeEntityConstant.GENERAL_EXCEPTION + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    /**
     * 500 - 数据库异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseEntity handleDatabaseException(BadSqlGrammarException e) {
        if (logging) {
            LOGGER.error("execute database exception{}", e);
        }
        return new ResponseEntity<>(ReposeEntityConstant.EXECUTE_DATABASE_EXCEPTION + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 500 - 数据库异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UncategorizedSQLException.class)
    public ResponseEntity handleDatabaseException(UncategorizedSQLException e) {
        if (logging) {
            LOGGER.error("execute database exception{}", e);
        }
        return new ResponseEntity<>(ReposeEntityConstant.EXECUTE_DATABASE_EXCEPTION + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 500 - 数据库异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleDatabaseException(DataIntegrityViolationException e) {
        String string = StringUtils.getDuplicateMessage(e.getMessage());
        if (string != null) {
            return new ResponseEntity<>(string, HttpStatus.BAD_REQUEST);
        } else {
            if (logging) {
                LOGGER.error("execute database exception{}", e);
            }
            return new ResponseEntity<>(ReposeEntityConstant.EXECUTE_DATABASE_EXCEPTION + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Feign异常
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FeignException.class)
    public ResponseEntity handleFeignException(FeignException e) {
        if (logging) {
            LOGGER.error("invoking an external service exception{}", e);
        }
        return new ResponseEntity<>(ReposeEntityConstant.INVOKING_EXTERNAL_SERVICE_EXCEPTION + Arrays.toString(e.getParameters()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}