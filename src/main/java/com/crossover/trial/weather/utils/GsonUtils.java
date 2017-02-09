package com.crossover.trial.weather.utils;

import com.google.gson.Gson;

/**
 * Gson utility.
 * 
 * @author code test administrator
 * @version 1.2.0
 * @since 2016-12-24
 *
 */
public class GsonUtils {

	/**
	 * shared gson json to object factory
	 **/
	public static final Gson gson = new Gson();

	/**
	 * Convert object to json
	 * 
	 * @param src
	 * @return String jsonString
	 */
	public final static String toJson(final Object src) {
		return gson.toJson(src);
	}

	/**
	 * Convert json string to class type.
	 * @param jsonString
	 * @param clazz
	 * @return T class of T
	 */
	public static <T> T fromJson(final String jsonString, final Class<T> clazz) {
		return gson.fromJson(jsonString, clazz);
	}

}