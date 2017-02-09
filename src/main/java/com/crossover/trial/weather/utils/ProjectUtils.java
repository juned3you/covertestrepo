package com.crossover.trial.weather.utils;

import com.crossover.trial.weather.model.AirportData;

/**
 * Project utility
 * 
 * @author code test administrator
 * @version 1.2.0
 * @since 2016-12-24
 */
public class ProjectUtils {

	/**
	 * Replace all quotes occurrence.
	 * 
	 * @param text
	 * @return String
	 */
	public static String replaceQuotes(String text) {
		return text.replaceAll("\"", "");
	}

	/**
	 * Convert string[] to AirportData object, Pass filename to load.
	 * 
	 * @param fields
	 * @return AirportData
	 */
	public static AirportData toAirportData(String[] fields) {
		final AirportData airportData = new AirportData();
		airportData.setAltitude(Double
				.parseDouble(fields[AirportHeaderEnum.ALTITUDE.getIndex()]
						.trim()));
		airportData
				.setCity(ProjectUtils
						.replaceQuotes(fields[AirportHeaderEnum.CITY.getIndex()]
								.trim()));
		airportData.setCountry(ProjectUtils
				.replaceQuotes(fields[AirportHeaderEnum.COUNTRY.getIndex()]
						.trim()));
		airportData
				.setDaylightSavingTime(ProjectUtils.replaceQuotes(
						fields[AirportHeaderEnum.LATITUDE.getIndex()].trim())
						.charAt(0));
		airportData
				.setIata(ProjectUtils
						.replaceQuotes(fields[AirportHeaderEnum.IATA.getIndex()]
								.trim()));
		airportData
				.setIcao(ProjectUtils
						.replaceQuotes(fields[AirportHeaderEnum.ICAO.getIndex()]
								.trim()));
		airportData.setLatitude(Double
				.parseDouble(fields[AirportHeaderEnum.LATITUDE.getIndex()]
						.trim()));
		airportData.setLongitude(Double
				.parseDouble(fields[AirportHeaderEnum.LONGITUDE.getIndex()]
						.trim()));
		airportData.setTimezoneHoursOffset(Double
				.parseDouble(fields[AirportHeaderEnum.TIMEZONE_OFFSET_HOUR
						.getIndex()].trim()));
		return airportData;
	}
}
