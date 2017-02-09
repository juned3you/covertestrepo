package com.crossover.trial.weather.utils;

/**
 * Index of field in airports.dat text file.
 * @author root
 *
 */
public enum AirportHeaderEnum {
	CITY(2), COUNTRY(3), IATA(4), ICAO(5), LATITUDE(6), LONGITUDE(7), ALTITUDE(
			8), TIMEZONE_OFFSET_HOUR(9), DST(10);
	private final int index;

	private AirportHeaderEnum(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}