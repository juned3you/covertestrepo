package com.crossover.trial.weather.utils;

/**
 * Project Constants
 * 
 * @author code test administrator
 * @version 1.2.0
 * @since 2016-12-24
 *
 */
public interface Constants {

	/**
	 * Constants for endpoint.
	 */
	public static final String ENDPOINT = "http://localhost:9090";

	/**
	 * Constants for collect endpoint.
	 */
	public static final String COLLECT_ENDPOINT = ENDPOINT + "/collect";

	/**
	 * Constants for query endpoint.
	 */
	public static final String QUERY_ENDPOINT = ENDPOINT + "/query";

	/**
	 * Constants for Collect airports. params as /airport/{iata}/{lat}/{long}
	 */
	public static final String AIRPORT_COLLECT_ENDPOINT = "/airport/%s/%s/%s";

	/** earth radius in KM */
	public static final double R = 6372.8;

	/**
	 * airport file word separator.
	 */
	public static final String WORD_SEPARATOR = ",";

	/**
	 * Total field length in dat file.
	 */
	public static final short TOTAL_FIELD_LENGTH = 11;
}
