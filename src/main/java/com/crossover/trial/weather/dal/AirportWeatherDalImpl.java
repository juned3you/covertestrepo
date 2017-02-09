package com.crossover.trial.weather.dal;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.crossover.trial.weather.model.AirportData;
import com.crossover.trial.weather.model.AtmosphericInformation;

/**
 * Data access layer for Airport.
 * 
 * @author code test administrator
 * @version 1.2.0
 * @since 2016-12-24
 *
 */
public class AirportWeatherDalImpl implements AirportWeatherDal {

	/**
	 * Return a list of known airports.
	 *
	 * @return Set<AirportData>
	 */
	@Override
	public Set<AirportData> getAirports() {
		return airportData;
	}

	/** all known airports */
	private static Set<AirportData> airportData = Collections
			.newSetFromMap(new ConcurrentHashMap<AirportData, Boolean>());

	/**
	 * Internal performance counter to better understand most requested
	 * information, this map can be improved but for now provides the basis for
	 * future performance optimizations. Due to the stateless deployment
	 * architecture we don't want to write this to disk, but will pull it off
	 * using a REST request and aggregate with other performance metrics
	 * {@link #ping()}
	 */
	private static Map<AirportData, Integer> requestFrequency = new ConcurrentHashMap<AirportData, Integer>();

	public static Map<Double, Integer> radiusFreq = new ConcurrentHashMap<Double, Integer>();

	private static AirportWeatherDal airportWeatherDao = null;

	/**
	 * A dummy init method that loads hard coded data for testing only.
	 */
	public static void init() {
		airportData.clear();
		requestFrequency.clear();

		AirportWeatherDal airportWeatherDao = AirportWeatherDalImpl
				.getInstance();
		airportWeatherDao.addAirport("BOS", 42.364347, -71.005181);
		airportWeatherDao.addAirport("EWR", 40.6925, -74.168667);
		airportWeatherDao.addAirport("JFK", 40.639751, -73.778925);
		airportWeatherDao.addAirport("LGA", 40.777245, -73.872608);
		airportWeatherDao.addAirport("MMU", 40.79935, -74.4148747);
	}

	/**
	 * Private constructor for singleton.
	 */
	private AirportWeatherDalImpl() {
	}

	/**
	 * Singleton object. No need of synchronized keyword.Later can be
	 * implemented using Jersey singleton feature.
	 * 
	 * @return
	 */
	public static AirportWeatherDal getInstance() {
		if (airportWeatherDao == null)
			airportWeatherDao = new AirportWeatherDalImpl();

		return airportWeatherDao;
	}

	/**
	 * Given an iataCode find the airport data
	 *
	 * @param iataCode
	 *            as a string
	 * @return airport data or null if not found
	 */
	public AirportData findAirportData(String iataCode) {
		return airportData.stream().filter(ap -> ap.getIata().equals(iataCode))
				.findFirst().orElse(null);
	}

	/**
	 * Add airport
	 * 
	 * @param iataCode
	 * @param latitude
	 * @param longitude
	 * @return AirportData
	 */
	@Override
	public AirportData addAirport(String iataCode, double latitude,
			double longitude) {
		AirportData ad = new AirportData();
		AtmosphericInformation ai = new AtmosphericInformation();
		ad.setIata(iataCode);
		ad.setLatitude(latitude);
		ad.setLongitude(longitude);
		ad.setAtmosphericInformation(ai);
		airportData.add(ad);
		return ad;
	}

	/**
	 * Return a list of Request frequencies.
	 *
	 * @return Map<AirportData, Integer>
	 */
	@Override
	public Map<AirportData, Integer> getFrequencies() {
		return requestFrequency;
	}

	/**
	 * Return a list of radius frequency.
	 *
	 * @return Map<Double, Integer>
	 */
	@Override
	public Map<Double, Integer> getRadiusFrequencies() {
		return radiusFreq;
	}

	/**
	 * Records information about how often requests are made
	 *
	 * @param iata
	 *            an iata code
	 * @param radius
	 *            query radius
	 */
	@Override
	public void updateRequestFrequency(String iata, Double radius) {
		AirportData airportData = findAirportData(iata);
		requestFrequency.put(airportData,
				requestFrequency.getOrDefault(airportData, 0) + 1);
		radiusFreq.put(radius, radiusFreq.getOrDefault(radius, 0));
	}

	/**
	 * Delete Airport
	 * 
	 * @param iata
	 * @return AirportData deleted ariport data.
	 */
	@Override
	public boolean deleteAirport(String iata) {
		AirportData ad = findAirportData(iata);
		return airportData.remove(ad);
	}
}
