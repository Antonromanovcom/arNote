package com.antonromanov.temprest.utils;

import java.util.Arrays;
import java.util.List;
import com.antonromanov.temprest.model.Temperature;
import com.google.gson.*;

public final class JSONTemplate {
	private static Gson gson = new Gson();
	private JsonObject json = new JsonObject();

	private JSONTemplate() {}

	public static JSONTemplate create() {
		return new JSONTemplate();
	}

	public JSONTemplate add(String propertyName, String value) {
		json.addProperty(propertyName, value);
		return this;
	}

	public JSONTemplate add(String propertyName, Number value) {
		json.addProperty(propertyName, value);
		return this;
	}

	public JSONTemplate add(String propertyName, Boolean value) {
		json.addProperty(propertyName, value);
		return this;
	}

	public JSONTemplate add(String propertyName, Character value) {
		json.addProperty(propertyName, value);
		return this;
	}

	public JSONTemplate add(String propertyName, String... list) {
		JsonArray arr = new JsonArray();
		Arrays.asList(list).stream().forEach(l -> arr.add(new JsonPrimitive(l)));
		json.add(propertyName, arr);
		return this;
	}

	public JsonObject getJson() {
		return json;
	}

	public Gson getGson() {
		return gson;
	}

	public static JsonObject fromString(String json) {
		return gson.fromJson(json, JsonObject.class);
	}

	@Override
	public String toString() {
		return getJson().toString();
	}

	public String parseListOfObjects(List<Temperature> list) {

		return gson.toJson(list);

	}
}