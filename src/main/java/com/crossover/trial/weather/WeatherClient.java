package com.crossover.trial.weather;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.crossover.trial.weather.model.DataPoint;
import com.crossover.trial.weather.service.AirportLoader;
import com.crossover.trial.weather.service.AirportLoaderImpl;
import com.crossover.trial.weather.utils.Constants;

/**
 * A reference implementation for the weather client. Consumers of the REST API
 * can look at WeatherClient to understand API semantics. This existing client
 * populates the REST endpoint with dummy data useful for testing.
 *
 * @author code test administrator
 */
public class WeatherClient {

	/** end point for read queries */
	private WebTarget query;

	/** end point to supply updates */
	private WebTarget collect;

	public WeatherClient() {
		Client client = ClientBuilder.newClient();
		query = client.target(Constants.QUERY_ENDPOINT);
		collect = client.target(Constants.COLLECT_ENDPOINT);
	}

	public void pingCollect() {
		WebTarget path = collect.path("/ping");
		Response response = path.request().get();
		System.out.print("collect.ping: " + response.readEntity(String.class)
				+ "\n");
	}

	public void query(String iata) {
		WebTarget path = query.path("/weather/" + iata + "/0");
		Response response = path.request().get();
		System.out.println("query." + iata + ".0: "
				+ response.readEntity(String.class));
	}

	public void pingQuery() {
		WebTarget path = query.path("/ping");
		Response response = path.request().get();
		System.out.println("query.ping: " + response.readEntity(String.class));
	}

	public void populate(String pointType, int first, int last, int mean,
			int median, int count) {
		WebTarget path = collect.path("/weather/BOS/" + pointType);
		DataPoint dp = new DataPoint.Builder().withFirst(first).withLast(last)
				.withMean(mean).withMedian(median).withCount(count).build();
		path.request().post(Entity.entity(dp, "application/json"));
	}

	public void exit() {
		try {
			collect.path("/exit").request().get();
		} catch (Throwable t) {
			// swallow
		}
	}

	/**
	 * Delete iata.
	 */
	public void delete() {
		WebTarget path = collect.path("/airport/BOS");
		Response response = path.request().delete();
		System.out.println("deleted.result: " + response.getStatus());
	}

	public static void main(String[] args) throws IOException {

		/**
		 * Load airports.dat file first.
		 */
		AirportLoader ap = new AirportLoaderImpl();
		ap.upload("./src/main/resources/airports.dat");

		WeatherClient wc = new WeatherClient();
		wc.pingCollect();
		wc.populate("wind", 0, 10, 6, 4, 20);

		wc.query("BOS");
		wc.query("JFK");
		wc.query("EWR");
		wc.query("LGA");
		wc.query("MMU");

		// From airports.dat
		wc.query("LPL");

		// delete BOS
		wc.delete();

		wc.pingQuery();
		wc.exit();
		System.out.println("complete");
		System.exit(0);
	}
}
