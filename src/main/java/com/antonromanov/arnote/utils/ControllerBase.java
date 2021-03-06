package com.antonromanov.arnote.utils;

import com.antonromanov.arnote.exceptions.JsonNullException;
import com.antonromanov.arnote.exceptions.JsonParseException;
import com.antonromanov.arnote.exceptions.SaveNewWishException;
import com.antonromanov.arnote.exceptions.enums.ErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.server.ServletServerHttpResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;

@Slf4j
public class ControllerBase {

    protected <T, E> T $do(SomeProcess<T, E> process, E s, Principal user, ArNoteUtils.OperationType operationType, HttpServletResponse response) {
        try {
            return process.aplly(s);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            prepareError(ex, response);
            return null;
        }
    }

    protected ResponseEntity<String> $prepareResponse(String responseBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setCacheControl("no-cache");
        ResponseEntity<String> responseEntity = new ResponseEntity<String>(responseBody, headers, HttpStatus.OK);
        return responseEntity;
    }

    protected ResponseEntity<String> $prepareBadResponse(String responseBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setCacheControl("no-cache");

        ResponseEntity<String> responseEntity = new ResponseEntity<String>(responseBody, headers, HttpStatus.BAD_REQUEST);
        log.error("RESPONSE: " + responseEntity.toString());
        return responseEntity;
    }

    protected ResponseEntity<String> $prepareNoDataYetErrorResponse(ErrorCodes errorCode) { //todo: данную лютую хуету конечно же надо переделать!
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setCacheControl("no-cache");
        return new ResponseEntity<>(errorCode.getUiCode(), headers, HttpStatus.BAD_REQUEST);
    }

    public static void prepareError(Exception ex, HttpOutputMessage outputMessage) {
        prepareError(ex, ((ServletServerHttpResponse) outputMessage).getServletResponse());
    }

    private static void prepareError(Exception ex, HttpServletResponse response) {
        response.setStatus(520);
        if (ex instanceof SaveNewWishException)
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        if (ex instanceof JsonParseException || ex instanceof JsonNullException)
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        response.setHeader("Content-Type", "application/json;charset=UTF-8");

        try (PrintWriter pw = response.getWriter()) {
            pw.write(ex.getMessage() == null ? "Ошибка сохранения нового желания! " : ex.getMessage());
        } catch (IOException ioe) {
        }
    }


    protected interface SomeProcess<T, E> {
        T aplly(E req) throws Exception;
    }
}
