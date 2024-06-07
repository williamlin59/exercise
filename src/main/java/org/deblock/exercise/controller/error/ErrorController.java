package org.deblock.exercise.controller.error;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.deblock.generated.api.model.ErrorResponse;
import org.springframework.beans.PropertyAccessException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler({IllegalStateException.class, ServletRequestBindingException.class, PropertyAccessException.class})
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ResponseBody
    public HttpEntity<org.deblock.generated.api.model.ErrorResponse> handleIllegalStateException(Exception ex){

        ErrorResponse error = new ErrorResponse().code("CP400").description(ex.getMessage());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler({Exception.class})
    @Order()
    @ResponseBody
    public HttpEntity<org.deblock.generated.api.model.ErrorResponse> handleOtherException(Exception ex){

        ErrorResponse error = new ErrorResponse().code("CP500").description(ex.getMessage());

        return ResponseEntity.internalServerError().body(error);
    }
}
