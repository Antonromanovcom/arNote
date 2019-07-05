package com.antonromanov.arnote.utils;

import com.antonromanov.arnote.Exceptions.JsonNullException;
import com.antonromanov.arnote.Exceptions.JsonParseException;
import com.antonromanov.arnote.Exceptions.SaveNewWishException;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.server.ServletServerHttpResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ControllerBase {

	private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger("console_logger");

	protected <T,E>T $do(SomeProcess<T, E> process, E s, HttpServletResponse response){
		try {
			return process.aplly(s);
		} catch (Exception ex) {
			prepareError(ex, response);
			return null;
		}
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
		T aplly(E req) throws JsonNullException;
	}


}
