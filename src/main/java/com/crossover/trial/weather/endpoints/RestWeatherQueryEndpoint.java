package com.crossover.trial.weather.endpoints;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.crossover.trial.weather.model.AtmosphericInformation;
import com.crossover.trial.weather.service.AirportWeatherService;
import com.crossover.trial.weather.service.AirportWeatherServiceImpl;
import com.crossover.trial.weather.utils.WeatherException;

/**
 * The Weather App REST endpoint allows clients to query, update and check
 * health stats. Currently, all data is held in memory. The end point deploys to
 * a single container
 *
 * @author code test administrator
 */
@Path("/query")
public class RestWeatherQueryEndpoint implements WeatherQueryEndpoint {

	public final static Logger LOGGER = Logger.getLogger("WeatherQuery");

	/**
	 * To be changed with Injection
	 */
	private AirportWeatherService airportWeatherService = AirportWeatherServiceImpl
			.getInstance();

	/**
	 * Retrieve service health including total size of valid data points and
	 * request frequency information.
	 *
	 * @return health stats for the service as a string
	 */
	@Override
	public String ping() {
		try {
			return airportWeatherService.queryPing();
		} catch (WeatherException e) {
			return e.getMessage();
		}
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
	public Response weather(String iata, String radiusString) {
		try {
			List<AtmosphericInformation> retval = airportWeatherService
					.getWeather(iata, radiusString);
			return Response.status(Response.Status.OK).entity(retval).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(e.getMessage()).build();
		}
	}
}
