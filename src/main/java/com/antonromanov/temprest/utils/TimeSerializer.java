package com.antonromanov.temprest.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeSerializer implements JsonSerializer {
	@Override
	public JsonElement serialize(Object src, Type type, JsonSerializationContext jsonSerializationContext) {

		DateFormat format = new SimpleDateFormat("hh:mm:ss a");
		String result = LocalTime.parse(format.format((java.sql.Time)src) , DateTimeFormatter.ofPattern("hh:mm:ss a" , Locale.US))
				.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

		return new JsonPrimitive(result);
	}
}
