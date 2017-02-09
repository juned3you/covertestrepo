package com.crossover.trial.weather.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.crossover.trial.weather.dal.AirportWeatherDal;
import com.crossover.trial.weather.dal.AirportWeatherDalImpl;
import com.crossover.trial.weather.model.AirportData;
import com.crossover.trial.weather.model.AtmosphericInformation;
import com.crossover.trial.weather.model.DataPoint;
import com.crossover.trial.weather.utils.Constants;
import com.crossover.trial.weather.utils.DataPointType;
import com.crossover.trial.weather.utils.GsonUtils;
import com.crossover.trial.weather.utils.WeatherException;

/**
 * Airport service
 * 
 * @author code test administrator
 * @version 1.2.0
 * @since 2016-12-24
 *
 */
public class AirportWeatherServiceImpl implements AirportWeatherService {

	private static AirportWeatherService airportWeatherService = null;

	/**
	 * To be changed with Injection
	 */
	private AirportWeatherDal airportWeatherDal = null;

	/**
	 * Private constructor for singlton.
	 */
	private AirportWeatherServiceImpl() {
		airportWeatherDal = AirportWeatherDalImpl.getInstance();
	}

	/**
	 * Singleton object. No need of synchronized keyword.
	 * 
	 * @return AirportWeatherService
	 */
	public static AirportWeatherService getInstance() {
		if (airportWeatherService == null)
			airportWeatherService = new AirportWeatherServiceImpl();
		return airportWeatherService;
	}

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
	@Override
	public void updateWeather(String iataCode, String pointType,
			String datapointJson) throws WeatherException {
		try {
			DataPoint dp = GsonUtils.fromJson(datapointJson, DataPoint.class);
			AirportData ad = airportWeatherDal.findAirportData(iataCode);

			if (ad == null)
				ad = new AirportData();

			if (ad.getAtmosphericInformation() == null)
				ad.setAtmosphericInformation(new AtmosphericInformation());

			AtmosphericInformation ai = ad.getAtmosphericInformation();
			updateAtmosphericInformation(ai, pointType, dp);
		} catch (Throwable t) {
			throw new WeatherException(t);
		}
	}

	/**
	 * Return a list of known airports.
	 *
	 * @return Set<String>
	 */
	@Override
	public Set<String> getAirports() throws WeatherException {
		try {
			Set<String> retval = new HashSet<>();
			for (AirportData ad : airportWeatherDal.getAirports()) {
				retval.add(ad.getIata());
			}
			return retval;
		} catch (Throwable t) {
			throw new WeatherException(t);
		}
	}

	@Override
	public AirportData getAirport(String iata) throws WeatherException {
		try {
			return airportWeatherDal.findAirportData(iata);
		} catch (Throwable t) {
			throw new WeatherException(t);
		}
	}

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
	@Override
	public AirportData addAirport(String iataCode, double latitude,
			double longitude) throws WeatherException {
		try {
			// AirportData ad = GsonUtils.fromJson(airportDataJson,
			// AirportData.class);
			return airportWeatherDal.addAirport(iataCode, latitude, longitude);
		} catch (Throwable t) {
			throw new WeatherException(t);
		}
	}

	@Override
	public boolean deleteAirport(String iata) throws WeatherException {
		try {
			return airportWeatherDal.deleteAirport(iata);
		} catch (Throwable t) {
			throw new WeatherException("No iata found!!.");
		}
	}

	/**
	 * update atmospheric information with the given data point for the given
	 * point type
	 *
	 * @param ai
	 *            the atmospheric information object to update
	 * @param pointType
	 *            the data point type as a string
	 * @param dp
	 *            the actual data point
	 */
	private void updateAtmosphericInformation(AtmosphericInformation ai,
			String pointType, DataPoint dp) throws WeatherException {

		if (pointType.equalsIgnoreCase(DataPointType.WIND.name())) {
			if (dp.getMean() >= 0) {
				ai.setWind(dp);
				ai.setLastUpdateTime(System.currentTimeMillis());
				return;
			}
		}

		if (pointType.equalsIgnoreCase(DataPointType.TEMPERATURE.name())) {
			if (dp.getMean() >= -50 && dp.getMean() < 100) {
				ai.setTemperature(dp);
				ai.setLastUpdateTime(System.currentTimeMillis());
				return;
			}
		}

		if (pointType.equalsIgnoreCase(DataPointType.HUMIDTY.name())) {
			if (dp.getMean() >= 0 && dp.getMean() < 100) {
				ai.setHumidity(dp);
				ai.setLastUpdateTime(System.currentTimeMillis());
				return;
			}
		}

		if (pointType.equalsIgnoreCase(DataPointType.PRESSURE.name())) {
			if (dp.getMean() >= 650 && dp.getMean() < 800) {
				ai.setPressure(dp);
				ai.setLastUpdateTime(System.currentTimeMillis());
				return;
			}
		}

		if (pointType.equalsIgnoreCase(DataPointType.CLOUDCOVER.name())) {
			if (dp.getMean() >= 0 && dp.getMean() < 100) {
				ai.setCloudCover(dp);
				ai.setLastUpdateTime(System.currentTimeMillis());
				return;
			}
		}

		if (pointType.equalsIgnoreCase(DataPointType.PRECIPITATION.name())) {
			if (dp.getMean() >= 0 && dp.getMean() < 100) {
				ai.setPrecipitation(dp);
				ai.setLastUpdateTime(System.currentTimeMillis());
				return;
			}
		}

		throw new IllegalStateException("couldn't update atmospheric data");
	}

	/**
	 * Retrieve service health including total size of valid data points and
	 * request frequency information.
	 *
	 * @return health stats for the service as a string
	 */
	@Override
	public String queryPing() throws WeatherException {

		final Map<AirportData, Integer> requestFrequency = airportWeatherDal
				.getFrequencies();
		final Map<Double, Integer> radiusFreq = airportWeatherDal
				.getRadiusFrequencies();
		final Set<AirportData> airportData = airportWeatherDal.getAirports();

		final Map<String, Object> retval = new HashMap<>();

		int datasize = 0;
		for (AirportData ad : airportData) {
			AtmosphericInformation ai = ad.getAtmosphericInformation();

			// we only count recent readings
			if (ai.getCloudCover() != null || ai.getHumidity() != null
					|| ai.getPressure() != null
					|| ai.getPrecipitation() != null
					|| ai.getTemperature() != null || ai.getWind() != null) {
				// updated in the last day
				if (ai.getLastUpdateTime() > System.currentTimeMillis() - 86400000) {
					datasize++;
				}
			}
		}
		retval.put("datasize", datasize);

		Map<String, Double> freq = new HashMap<>();
		// fraction of queries
		for (AirportData data : airportData) {
			double frac = (double) requestFrequency.getOrDefault(data, 0)
					/ requestFrequency.size();
			freq.put(data.getIata(), frac);
		}
		retval.put("iata_freq", freq);

		int m = radiusFreq.keySet().stream().max(Double::compare)
				.orElse(1000.0).intValue() + 1;

		int[] hist = new int[m];
		for (Map.Entry<Double, Integer> e : radiusFreq.entrySet()) {
			int i = e.getKey().intValue() % 10;
			hist[i] += e.getValue();
		}
		retval.put("radius_freq", hist);
		return GsonUtils.toJson(retval);
	}

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
	@Override
	public List<AtmosphericInformation> getWeather(String iata,
			String radiusString) {

		final Set<AirportData> airportData = airportWeatherDal.getAirports();

		double radius = radiusString == null || radiusString.trim().isEmpty() ? 0
				: Double.valueOf(radiusString);

		updateRequestFrequency(iata, radius);

		List<AtmosphericInformation> retval = new ArrayList<>();
		if (radius == 0) {
			AirportData ad = airportWeatherDal.findAirportData(iata);
			if (ad != null)
				retval.add(ad.getAtmosphericInformation());
		} else {
			AirportData ad = airportWeatherDal.findAirportData(iata);

			for (AirportData lad : airportData) {
				if (calculateDistance(ad, lad) <= radius) {
					AtmosphericInformation ai = lad.getAtmosphericInformation();
					if (ai.getCloudCover() != null || ai.getHumidity() != null
							|| ai.getPrecipitation() != null
							|| ai.getPressure() != null
							|| ai.getTemperature() != null
							|| ai.getWind() != null) {
						retval.add(ai);
					}
				}
			}
		}
		return retval;
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
		airportWeatherDal.updateRequestFrequency(iata, radius);
	}

	/**
	 * Haversine distance between two airports.
	 *
	 * @param ad1
	 *            airport 1
	 * @param ad2
	 *            airport 2
	 * @return the distance in KM
	 */
	private double calculateDistance(AirportData ad1, AirportData ad2) {
		double deltaLat = Math.toRadians(ad2.getLatitude() - ad1.getLatitude());
		double deltaLon = Math.toRadians(ad2.getLongitude()
				- ad1.getLongitude());
		double a = Math.pow(Math.sin(deltaLat / 2), 2)
				+ Math.pow(Math.sin(deltaLon / 2), 2)
				* Math.cos(ad1.getLatitude()) * Math.cos(ad2.getLatitude());
		double c = 2 * Math.asin(Math.sqrt(a));
		return Constants.R * c;
	}
}