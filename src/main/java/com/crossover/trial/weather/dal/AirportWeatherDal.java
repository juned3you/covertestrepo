package com.crossover.trial.weather.dal;

import java.util.Map;
import java.util.Set;

import com.crossover.trial.weather.model.AirportData;

/**
 * Data access layer for Airport.
 * 
 * @author code test administrator
 * @version 1.2.0
 * @since 2016-12-24
 *
 */
public interface AirportWeatherDal {

	/**
	 * Given an iataCode find the airport data
	 *
	 * @param iataCode
	 *            as a string
	 * @return airport data or null if not found
	 */
	public AirportData findAirportData(String iataCode);
	

	/**
	 * Add airport
	 * 
	 * @param iataCode
	 * @param latitude
	 * @param longitude
	 * @param airportData
	 * @return AirportData
	 */
	public AirportData addAirport(String iataCode, double latitude,
			double longitude);

	/**
	 * Return a list of known airports.
	 *
	 * @return Set<AirportData>
	 */
	public Set<AirportData> getAirports();	

	/**
	 * Return a list of Request frequencies.
	 *
	 * @return Map<AirportData, Integer>
	 */
	public Map<AirportData, Integer> getFrequencies();

	/**
	 * Return a list of radius frequency.
	 *
	 * @return Map<Double, Integer>
	 */
	public Map<Double, Integer> getRadiusFrequencies();

	/**
	 * Records information about how often requests are made
	 *
	 * @param iata
	 *            an iata code
	 * @param radius
	 *            query radius
	 */
	public void updateRequestFrequency(String iata, Double radius);

	/**
	 * Delete Airport
	 * 
	 * @param iata
	 * @return boolean
	 */
	public boolean deleteAirport(String iata);
}
