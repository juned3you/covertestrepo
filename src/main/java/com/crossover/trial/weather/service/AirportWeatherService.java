package com.crossover.trial.weather.service;

import java.util.List;
import java.util.Set;

import com.crossover.trial.weather.model.AirportData;
import com.crossover.trial.weather.model.AtmosphericInformation;
import com.crossover.trial.weather.utils.DataPointType;
import com.crossover.trial.weather.utils.WeatherException;

/**
 * Airport service
 * 
 * @author code test administrator
 * @version 1.2.0
 * @since 2016-12-24
 *
 */
public interface AirportWeatherService {

	/**
	 * Update the airports atmospheric information for a particular pointType
	 * with json formatted data point information.
	 *
	 * @param iataCode
	 *            the 3 letter airport code
	 * @param pointType
	 *            the point type, {@link DataPointType} for a complete list
	 * @param datapointJson
	 *            a json dict containing mean, first, second, thrid and count
	 *            keys
	 *
	 * @return HTTP Response code
	 * @throws WeatherException
	 */
	public void updateWeather(String iataCode, String pointType,
			String datapointJson) throws WeatherException;

	/**
	 * Return a list of known airports.
	 *
	 * @return Set<String>
	 */
	public Set<String> getAirports() throws WeatherException;

	/**
	 * Retrieve airport data, including latitude and longitude for a particular
	 * airport
	 *
	 * @param iata
	 *            the 3 letter airport code
	 * @return {@link AirportData}
	 */
	public AirportData getAirport(String iata) throws WeatherException;

	/**
	 * Add a new airport to the known airport list.
	 *
	 * @param iata
	 *            the 3 letter airport code of the new airport
	 * @param latitude
	 *            the airport's latitude in degrees as a string [-90, 90]
	 * @param longitude
	 *            the airport's longitude in degrees as a string [-180, 180]
	 * @return {@link AirportData}
	 */
	public AirportData addAirport(String iata, double latitude,
			double longitude) throws WeatherException;

	/**
	 * Remove an airport from the known airport list
	 *
	 * @param iata
	 *            the 3 letter airport code
	 * @return boolean
	 */
	public boolean deleteAirport(String iata) throws WeatherException;

	/**
	 * Retrieve service health including total size of valid data points and
	 * request frequency information.
	 *
	 * @return health stats for the service as a string
	 */
	public String queryPing() throws WeatherException;

	/**
	 * Given a query in json format {'iata': CODE, 'radius': km} extracts the
	 * requested airport information and return a list of matching atmosphere
	 * information.
	 *
	 * @param iata
	 *            the iataCode
	 * @param radiusString
	 *            the radius in km
	 *
	 * @return a list of atmospheric information
	 */
	public List<AtmosphericInformation> getWeather(String iata,
			String radiusString);

	/**
	 * Records information about how often requests are made
	 *
	 * @param iata
	 *            an iata code
	 * @param radius
	 *            query radius
	 */
	public void updateRequestFrequency(String iata, Double radius);
}