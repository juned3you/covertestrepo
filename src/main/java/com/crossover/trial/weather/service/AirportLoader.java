package com.crossover.trial.weather.service;

import java.io.IOException;

/**
 * Airport loader interface.
 * 
 * @author code test administrator
 * @version 1.2.0
 * @since 2016-12-24
 *
 */
public interface AirportLoader {

	/**
	 * Upload dat file to server.
	 * 
	 * @param airportDataStream
	 * @throws IOException
	 */
	public void upload(String fileName) throws IOException;	
}