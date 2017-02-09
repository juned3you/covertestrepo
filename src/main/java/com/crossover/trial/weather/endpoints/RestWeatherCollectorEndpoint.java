package com.crossover.trial.weather.endpoints;

import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.crossover.trial.weather.model.AirportData;
import com.crossover.trial.weather.service.AirportWeatherService;
import com.crossover.trial.weather.service.AirportWeatherServiceImpl;
import com.crossover.trial.weather.utils.DataPointType;
import com.crossover.trial.weather.utils.WeatherException;

/**
 * A REST implementation of the WeatherCollector API. Accessible only to airport
 * weather collection sites via secure VPN.
 *
 * @author code test administrator
 * @version 1.2.0
 * @since 2016-12-24
 */
@Path("/collect")
public class RestWeatherCollectorEndpoint implements WeatherCollectorEndpoint {

	/**
	 * Logger
	 */
	public final static Logger LOGGER = Logger
			.getLogger(RestWeatherCollectorEndpoint.class.getName());

	/**
	 * To be changed with Injection
	 */
	private AirportWeatherService airportWeatherService = AirportWeatherServiceImpl
			.getInstance();

	/**
	 * A liveliness check for the collection endpoint.
	 *
	 * @return 1 if the endpoint is alive functioning, 0 otherwise
	 */
	@Override
	public Response ping() {
		return Response.status(Response.Status.OK).entity("ready").build();
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
	 */
	@Override
	public Response updateWeather(String iataCode, String pointType,
			String datapointJson) {
		try {
			airportWeatherService.updateWeather(iataCode, pointType,
					datapointJson);
		} catch (WeatherException e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(e.getMessage()).build();
		}
		return Response.status(Response.Status.OK).build();
	}

	/**
	 * Return a list of known airports as a json formatted list
	 *
	 * @return HTTP Response code and a json formatted list of IATA codes
	 */
	@Override
	public Response getAirports() {
		try {
			Set<String> retval = airportWeatherService.getAirports();
			return Response.status(Response.Status.OK).entity(retval).build();
		} catch (WeatherException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(e.getMessage()).build();
		}

	}

	/**
	 * Retrieve airport data, including latitude and longitude for a particular
	 * airport
	 *
	 * @param iata
	 *            the 3 letter airport code
	 * @return an HTTP Response with a json representation of
	 *         {@link AirportData}
	 */
	@Override
	public Response getAirport(String iata) {
		try {
			AirportData ad = airportWeatherService.getAirport(iata);
			return Response.status(Response.Status.OK).entity(ad).build();
		} catch (WeatherException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(e.getMessage()).build();
		}
	}

	/**
	 * Add a new airport to the known airport list.
	 *
	 * @param iata
	 *            the 3 letter airport code of the new airport
	 * @param latString
	 *            the airport's latitude in degrees as a string [-90, 90]
	 * @param longString
	 *            the airport's longitude in degrees as a string [-180, 180]
	 * @return HTTP Response code for the add operation
	 */
	@Override
	public Response addAirport(String iata, String latString, String longString) {
		try {
			airportWeatherService.addAirport(iata, Double.valueOf(latString),
					Double.valueOf(longString));
			return Response.status(Response.Status.OK).build();
		} catch (NumberFormatException | WeatherException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(e.getMessage()).build();
		}

	}

	/**
	 * Remove an airport from the known airport list
	 *
	 * @param iata
	 *            the 3 letter airport code
	 * @return HTTP Repsonse code for the delete operation
	 */
	@Override
	public Response deleteAirport(String iata) {
		try {
			airportWeatherService.deleteAirport(iata);
			return Response.status(Response.Status.OK).build();
		} catch (WeatherException e) {			
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(e.getMessage()).build();
		}
	}

	/**
	 * Terminate Jvm.
	 */
	@Override
	public Response exit() {
		System.exit(0);
		return Response.noContent().build();
	}
}