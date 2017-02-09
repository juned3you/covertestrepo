package com.crossover.trial.weather;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import com.crossover.trial.weather.dal.AirportWeatherDalImpl;
import com.crossover.trial.weather.endpoints.RestWeatherCollectorEndpoint;
import com.crossover.trial.weather.endpoints.RestWeatherQueryEndpoint;
import com.crossover.trial.weather.endpoints.WeatherCollectorEndpoint;
import com.crossover.trial.weather.endpoints.WeatherQueryEndpoint;
import com.crossover.trial.weather.model.AirportData;
import com.crossover.trial.weather.model.AtmosphericInformation;
import com.crossover.trial.weather.model.DataPoint;
import com.crossover.trial.weather.utils.Constants;
import com.crossover.trial.weather.utils.GsonUtils;
import com.crossover.trial.weather.utils.ProjectUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@SuppressWarnings("unchecked")
public class WeatherEndpointTest {

	private WeatherQueryEndpoint _query = new RestWeatherQueryEndpoint();

	private WeatherCollectorEndpoint _update = new RestWeatherCollectorEndpoint();

	private DataPoint _dp;

	@Before
	public void setUp() throws Exception {
		AirportWeatherDalImpl.init();
		_dp = new DataPoint.Builder().withCount(10).withFirst(10)
				.withMedian(20).withLast(30).withMean(22).build();
		_update.updateWeather("BOS", "wind", GsonUtils.toJson(_dp));
		_query.weather("BOS", "0").getEntity();
	}

	@Test
	public void testPing() throws Exception {
		String ping = _query.ping();
		JsonElement pingResult = new JsonParser().parse(ping);
		assertEquals(1, pingResult.getAsJsonObject().get("datasize").getAsInt());
		assertEquals(5, pingResult.getAsJsonObject().get("iata_freq")
				.getAsJsonObject().entrySet().size());
	}

	@Test
	public void testGet() throws Exception {
		List<AtmosphericInformation> ais = (List<AtmosphericInformation>) _query
				.weather("BOS", "0").getEntity();
		assertEquals(ais.get(0).getWind(), _dp);
	}

	@Test
	public void testGetNearby() throws Exception {
		// check datasize response
		_update.updateWeather("JFK", "wind", GsonUtils.toJson(_dp));
		_dp.setMean(40);
		_update.updateWeather("EWR", "wind", GsonUtils.toJson(_dp));
		_dp.setMean(30);
		_update.updateWeather("LGA", "wind", GsonUtils.toJson(_dp));

		List<AtmosphericInformation> ais = (List<AtmosphericInformation>) _query
				.weather("JFK", "200").getEntity();
		assertEquals(3, ais.size());
	}

	@Test
	public void testUpdate() throws Exception {

		DataPoint windDp = new DataPoint.Builder().withCount(10).withFirst(10)
				.withMedian(20).withLast(30).withMean(22).build();
		_update.updateWeather("BOS", "wind", GsonUtils.toJson(windDp));
		_query.weather("BOS", "0").getEntity();

		String ping = _query.ping();
		JsonElement pingResult = new JsonParser().parse(ping);
		assertEquals(1, pingResult.getAsJsonObject().get("datasize").getAsInt());

		DataPoint cloudCoverDp = new DataPoint.Builder().withCount(4)
				.withFirst(10).withMedian(60).withLast(100).withMean(50)
				.build();
		_update.updateWeather("BOS", "cloudcover",
				GsonUtils.toJson(cloudCoverDp));

		List<AtmosphericInformation> ais = (List<AtmosphericInformation>) _query
				.weather("BOS", "0").getEntity();
		assertEquals(ais.get(0).getWind(), windDp);
		assertEquals(ais.get(0).getCloudCover(), cloudCoverDp);
	}

	/**
	 * Delete test case
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDelete() throws Exception {

		// Before delete.
		String ping = _query.ping();
		JsonElement pingResult = new JsonParser().parse(ping);

		// Should be 1
		assertEquals(1, pingResult.getAsJsonObject().get("datasize").getAsInt());

		// should be 5
		assertEquals(5, pingResult.getAsJsonObject().get("iata_freq")
				.getAsJsonObject().entrySet().size());

		// Delete BOS
		_update.deleteAirport("BOS");

		// After delete
		ping = _query.ping();
		pingResult = new JsonParser().parse(ping);

		// Should be 0
		assertEquals(0, pingResult.getAsJsonObject().get("datasize").getAsInt());

		// Should be 4
		assertEquals(4, pingResult.getAsJsonObject().get("iata_freq")
				.getAsJsonObject().entrySet().size());
	}

	/**
	 * Delete non existing element, results in Bad request.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDeleteNegative() throws Exception {

		// Delete unknown iata.
		Response res = _update.deleteAirport("dsds");
		assertEquals(400, res.getStatus());
	}

	/**
	 * Add new airport test case.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddAirport() throws Exception {

		// Before Add.
		String ping = _query.ping();
		JsonElement pingResult = new JsonParser().parse(ping);

		// Should be 1
		assertEquals(1, pingResult.getAsJsonObject().get("datasize").getAsInt());

		// should be 5
		assertEquals(5, pingResult.getAsJsonObject().get("iata_freq")
				.getAsJsonObject().entrySet().size());

		// Record from dat file.
		final String row = "10,\"Stansted\",\"London\",\"United Kingdom\",\"STN\",\"EGSS\",51.885,0.235,348,0,\"E\"";
		final String[] fields = row.split(Constants.WORD_SEPARATOR);
		AirportData airportData = ProjectUtils.toAirportData(fields);

		// Add new row.
		_update.addAirport(airportData.getIata(),
				String.valueOf(airportData.getLatitude()),
				String.valueOf(airportData.getLongitude()));

		// After add
		ping = _query.ping();
		pingResult = new JsonParser().parse(ping);

		// Should be 1
		assertEquals(1, pingResult.getAsJsonObject().get("datasize").getAsInt());

		// Should be 6
		assertEquals(6, pingResult.getAsJsonObject().get("iata_freq")
				.getAsJsonObject().entrySet().size());

		// query
		List<AtmosphericInformation> ais = (List<AtmosphericInformation>) _query
				.weather("STN", "0").getEntity();
		assertEquals(ais.get(0).getWind(), null);
		assertEquals(ais.get(0).getCloudCover(), null);
	}

}