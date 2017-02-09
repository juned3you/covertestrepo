package com.crossover.trial.weather.utils;

/**
 * An internal exception marker
 */
public class WeatherException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WeatherException(Throwable cause) {
		super(cause);
	}
	
	public WeatherException(String cause) {
		super(cause);
	}
}
