package com.antonromanov.arnote.common.wrapper;

import com.antonromanov.arnote.common.wrapper.GlobalWrapper;
import com.antonromanov.arnote.common.wrapper.ResponseStatus;
import com.antonromanov.arnote.common.wrapper.WrapperModel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@ControllerAdvice
public class ResponseWrapperAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(@Nullable Object body,
                                  @NonNull MethodParameter returnType,
                                  @NonNull MediaType selectedContentType,
                                  @NonNull Class selectedConverterType,
                                  @NonNull ServerHttpRequest request,
                                  @NonNull ServerHttpResponse response) {

        if (body == null) {
            return null;
        } else if (checkBody(body)){
            return body;
        }

        return generateResponseWrapper(body, GlobalWrapper.class, ResponseStatus.builder()
                .success(true)
                .build());
    }

    private Boolean checkBody(Object body){
        try {
           return  ((GlobalWrapper) body).getStatus() != null;
        } catch (ClassCastException e) {
            return false;
        }
    }

    /**
     * Генерируем обертку вокруг объекта
     *
     * @param body         объект который необходимо поместить в обертку
     * @param wrapperClass объект обертки
     * @return обертка
     */
    @SneakyThrows
    private WrapperModel generateResponseWrapper(Object body, Class<? extends WrapperModel> wrapperClass, ResponseStatus status) {
        WrapperModel wrapper = wrapperClass.getDeclaredConstructor().newInstance();
        wrapper.setBody(body);
        wrapper.setStatus(status);

        return wrapper;
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<WrapperModel> handleAllExceptions(RuntimeException e) {

        WrapperModel exceptionResponse =  generateResponseWrapper(null, GlobalWrapper.class,
                ResponseStatus.builder()
                        .success(false)
                        .description(e.getMessage())
                        .build());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WrapperModel> handleSpringValidatorException(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        WrapperModel exceptionResponse =  generateResponseWrapper(errors, GlobalWrapper.class,
                ResponseStatus.builder()
                        .success(false)
                        .description(e.getMessage())
                        .build());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
