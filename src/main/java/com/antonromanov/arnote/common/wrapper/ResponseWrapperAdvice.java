package com.antonromanov.arnote.common.wrapper;

import com.antonromanov.arnote.common.exceptions.enums.ErrorCodes;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Arrays;

@AllArgsConstructor
@ControllerAdvice
public class ResponseWrapperAdvice implements ResponseBodyAdvice<Object> {

    private final String SUCCESS_STATUS = "SUCCESS";

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
        } else if (checkBody(body)) {
            return body;
        }

        return generateResponseWrapper(body, GlobalWrapper.class, ResponseStatus.builder()
                .success(true)
                .description(SUCCESS_STATUS)
                .build());
    }

    private Boolean checkBody(Object body) {
        try {
            return ((GlobalWrapper) body).getStatus() != null;
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

        ResponseStatus status;
        if (StringUtils.isNotBlank(e.getMessage())) {
            status = Arrays.stream(ErrorCodes.values()).filter(v -> v.getUiCode().equals(e.getMessage()))
                    .findFirst()
                    .map(w -> ResponseStatus.builder()
                            .success(false)
                            .errorCode(w.getUiCode())
                            .description(w.getDescription())
                            .build())
                    .orElse(ResponseStatus.builder()
                            .success(false)
                            .description(e.getMessage())
                            .build());
        } else {
            status = ResponseStatus.builder()
                    .success(false)
                    .description("Неизвестная ошибка!")
                    .build();
        }

        WrapperModel exceptionResponse = generateResponseWrapper(null, GlobalWrapper.class, status);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WrapperModel> handleSpringValidatorException(MethodArgumentNotValidException e) {

        WrapperModel exceptionResponse = generateResponseWrapper(null, GlobalWrapper.class,
                ResponseStatus.builder()
                        .success(false)
                        .description(e.getMessage())
                        .errorCode(ErrorCodes.ERR_10.getUiCode())
                        .build());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
