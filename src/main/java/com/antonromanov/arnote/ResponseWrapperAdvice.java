package com.antonromanov.arnote;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import lombok.NonNull;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@AllArgsConstructor
@ControllerAdvice(annotations = EnableResponseWrapper.class)
public class ResponseWrapperAdvice implements ResponseBodyAdvice<Object> {

    private final IWrapperService wrapperService;


    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        /*for (Annotation a : returnType.getMethodAnnotations()) {
            if (a.annotationType() == DisableResponseWrapper.class) {
                return false;
            }
        }*/

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
        }

        // получаем wrapperClass из аннотации
        Class<? extends IWrapperModel> wrapperClass = null;
        for (Annotation annotation : returnType.getContainingClass().getAnnotations()) {
            if (annotation.annotationType() == EnableResponseWrapper.class) {
                wrapperClass = ((EnableResponseWrapper) annotation).wrapperClass();
                break;
            }
        }

        if (wrapperClass == null) {
            return body;
        }

        // проверяем, был ли передан Collection или наследник Collection
        if (Collection.class.isAssignableFrom(body.getClass())) {
            try {
                Collection<?> bodyCollection = (Collection<?>) body;

                // проверяем, что collection не пустой
                if (bodyCollection.isEmpty()) {
                    return body;
                }
                // оборачиваем каждый элемент коллекции
                return generateListOfResponseWrapper(bodyCollection, wrapperClass);
            } catch (Exception e) {
                return body;
            }
        }

        // если не collection
        return generateResponseWrapper(body, wrapperClass);

    }

    /**
     * Генерируем список оберток для коллекции (те информация добавляется внутрь списка)
     *
     * @param bodyCollection список объектов, которые необходимо обернуть
     * @param wrapperClass   объект обертки
     * @return список оберток
     */
    private List<Object> generateListOfResponseWrapper(Collection<?> bodyCollection, Class<? extends IWrapperModel> wrapperClass) {
        return bodyCollection.stream()
                .map((t) -> t == null ?
                        null :
                        generateResponseWrapper(t, wrapperClass)
                )
                .collect(Collectors.toList());
    }

    /**
     * Генерируем обертку вокруг объекта
     *
     * @param body         объект который необходимо поместить в обертку
     * @param wrapperClass объект обертки
     * @return обертка
     */
    @SneakyThrows
    private IWrapperModel generateResponseWrapper(Object body, Class<? extends IWrapperModel> wrapperClass) {
        // wrapperClass должен иметь конструктор без параметров - получаем объект IWrapperModel
        IWrapperModel wrapper = wrapperClass.getDeclaredConstructor().newInstance();
        wrapper.setBody(body);
        wrapper.setData(wrapperService.getData(body));
        return wrapper;
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<IWrapperModel> handleAllExceptions(RuntimeException e) {

       /* if (e.getClass().isAssignableFrom(UserNotFoundException.class)) {
            UserNotFoundException exceptionClass = (UserNotFoundException) e.getClass().cast(UserNotFoundException.class);
            return new ResponseEntity<>(gb, HttpStatus.BAD_REQUEST);
        }*/
        /*return new ResponseEntity<>(GlobalResponse.builder()
                .status(ResponseStatus.builder()
                        .success(false)
                        .description(e.getMessage())
                        .build())
                .build(), HttpStatus.BAD_REQUEST);*/


        // получаем wrapperClass из аннотации
        Class<? extends IWrapperModel> wrapperClass = Wrapper.class;
        IWrapperModel i =  generateResponseWrapper("body", wrapperClass);
        return new ResponseEntity<>(i, HttpStatus.BAD_REQUEST);
        // IWrapperModel i =  generateResponseWrapper(body, wrapperClass);
    }
}
