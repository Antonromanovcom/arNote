package com.antonromanov.arnote.utils;

import com.antonromanov.arnote.exceptions.JsonNullException;
import com.antonromanov.arnote.exceptions.JsonParseException;
import com.antonromanov.arnote.exceptions.SaveNewWishException;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.server.ServletServerHttpResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;

public class ControllerBase {

	private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger("console_logger");

	protected <T,E>T $do(SomeProcess<T, E> process, E s, Principal user, Utils.OperationType operationType, HttpServletResponse response){
		try {
			return process.aplly(s);
		} catch (Exception ex) {
			prepareError(ex, response);
			return null;
		}
	}


	protected ResponseEntity<String> $prepareResponse(String responseBody){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setCacheControl("no-cache");
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(responseBody, headers, HttpStatus.OK);
		return responseEntity;
	}

	protected ResponseEntity<String> $prepareBadResponse(String responseBody){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setCacheControl("no-cache");

		ResponseEntity<String> responseEntity = new ResponseEntity<String>(responseBody, headers, HttpStatus.BAD_REQUEST);
		LOGGER.info("RESPONSE: " + responseEntity.toString());

		return responseEntity;
	}

	protected ResponseEntity<String> $prepareNoDataYetErrorResponse(Boolean switcher){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setCacheControl("no-cache");

		ResponseEntity<String> responseEntity;

		if (switcher) {
			responseEntity = new ResponseEntity<String>("ERR-01", headers, HttpStatus.BAD_REQUEST);
		} else {
			responseEntity = new ResponseEntity<String>("ERR-02", headers, HttpStatus.BAD_REQUEST);
		}
		LOGGER.info("RESPONSE: " + responseEntity.toString());
		return responseEntity;
	}


	public static void prepareError(Exception ex, HttpOutputMessage outputMessage) {
		prepareError(ex, ((ServletServerHttpResponse)outputMessage).getServletResponse());
	}

	private static void prepareError(Exception ex, HttpServletResponse response) {
		LOGGER.error(ex.getMessage(),ex);
		response.setStatus(520);

		if(ex instanceof SaveNewWishException)
			response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
		if(ex instanceof JsonParseException || ex instanceof JsonNullException)
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

		response.setHeader("Content-Type","application/json;charset=UTF-8");

		try (PrintWriter pw = response.getWriter()) {
			pw.write(ex.getMessage() == null ? "Ошибка сохранения нового желания! ":ex.getMessage() );
		}catch (IOException ioe) {
			LOGGER.error("Не могу записать ошибку в Response", ex);
		}
	}


	protected interface SomeProcess<T, E> {
		T aplly(E req) throws Exception;
	}
}
