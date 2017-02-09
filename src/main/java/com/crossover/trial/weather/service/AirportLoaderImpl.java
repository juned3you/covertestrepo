package com.crossover.trial.weather.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.stream.Stream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.crossover.trial.weather.model.AirportData;
import com.crossover.trial.weather.model.DataPoint;
import com.crossover.trial.weather.utils.AirportHeaderEnum;
import com.crossover.trial.weather.utils.Constants;
import com.crossover.trial.weather.utils.ProjectUtils;

/**
 * Airport loader implementation.
 * 
 * @author code test administrator
 * @version 1.2.0
 * @since 2016-12-24
 *
 */
public class AirportLoaderImpl implements AirportLoader {

	/**
	 * Logger
	 */
	public final Logger LOGGER = Logger.getLogger(AirportLoaderImpl.class
			.getName());

	/** end point for read queries */
	private WebTarget query;

	/** end point to supply updates */
	private WebTarget collect;

	public AirportLoaderImpl() {
		Client client = ClientBuilder.newClient();
		collect = client.target(Constants.COLLECT_ENDPOINT);
		query = client.target(Constants.QUERY_ENDPOINT);
	}

	/**
	 * Upload dat file to server.
	 * 
	 * @param airportDataStream
	 * @throws IOException
	 */
	@Override
	public void upload(String fileName) throws IOException {

		try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
			lines.forEach(i -> {
				final String[] fields = i.split(Constants.WORD_SEPARATOR);
				if (fields.length == Constants.TOTAL_FIELD_LENGTH)
					post(fields);
				else
					System.err.println("Column mismatched: " + i);
			});

		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}

	}

	/**
	 * Post data to airport endpoint.
	 * 
	 * @param fields
	 *            String[]
	 */
	private void post(String[] fields) {
		final String iata = ProjectUtils
				.replaceQuotes(fields[AirportHeaderEnum.IATA.getIndex()].trim());
		final String lati = fields[AirportHeaderEnum.LATITUDE.getIndex()]
				.trim();
		final String longi = fields[AirportHeaderEnum.LONGITUDE.getIndex()]
				.trim();

		final String endpoint = String.format(
				Constants.AIRPORT_COLLECT_ENDPOINT, iata, lati, longi);

		WebTarget path = collect.path(endpoint);
		AirportData airportData = ProjectUtils.toAirportData(fields);
		Response post = path.request().post(
				Entity.entity(airportData, "application/json"));
		LOGGER.info("Response: " + post.getStatus());
	}

	public void populate(String pointType, int first, int last, int mean,
			int median, int count) {
		WebTarget path = collect.path("/weather/BOS/" + pointType);
		DataPoint dp = new DataPoint.Builder().withFirst(first).withLast(last)
				.withMean(mean).withMedian(median).withCount(count).build();
		path.request().post(Entity.entity(dp, "application/json"));
	}

	/**
	 * Query api for test.
	 * 
	 * @param iata
	 */
	public void query(String iata) {
		WebTarget path = query.path("/weather/" + iata + "/0");
		Response response = path.request().get();
		LOGGER.info("query." + iata + ".0: "
				+ response.readEntity(String.class));
	}

	/**
	 * Call exit.
	 */
	public void exit() {
		try {
			collect.path("/exit").request().get();
		} catch (Throwable t) {
			// swallow
		}
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {
		String fileName = args[0];// "./src/main/resources/airports.dat";
		File airportDataFile = new File(fileName);
		if (!airportDataFile.exists() || airportDataFile.length() == 0) {
			System.err.println(airportDataFile + " is not a valid input");
			System.exit(1);
		}

		AirportLoaderImpl ap = new AirportLoaderImpl();
		ap.upload(fileName);

		// Populate for BOS iata.
		ap.populate("wind", 6, 20, 8, 9, 50);
		ap.query("BOS");
		ap.query("LPL");
		ap.query("STN");

		ap.exit();

		System.exit(0);
	}

}
