package com.crossover.trial.weather.exception;

/**
 * An internal exception marker
 */
public class WeatherException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1774730667736256084L;

	public WeatherException() {
	}

	public WeatherException(String message) {
		super(message);
	}

	public WeatherException(Exception message) {
		super(message);
	}
}
