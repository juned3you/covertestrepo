package com.crossover.trial.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * encapsulates sensor information for a particular location
 * 
 * @author code test administrator
 * @version 1.2.0
 * @since 2016-12-24
 */
public class AtmosphericInformation {

	/**
	 * temperature in degrees celsius
	 **/
	private DataPoint temperature;

	/**
	 * wind speed in km/h
	 * **/
	private DataPoint wind;

	/** humidity in percent */
	private DataPoint humidity;

	/**
	 * precipitation in cm
	 * */
	private DataPoint precipitation;

	/**
	 * pressure in mmHg
	 * */
	private DataPoint pressure;

	/**
	 * cloud cover percent from 0 - 100 (integer)
	 * */
	private DataPoint cloudCover;

	/**
	 * the last time this data was updated, in milliseconds since UTC epoch
	 * */
	@JsonIgnore
	private long lastUpdateTime;

	/**
	 * Default constructor.
	 */
	public AtmosphericInformation() {

	}

	/**
	 * Parameterized constructor.
	 * 
	 * @param temperature
	 * @param wind
	 * @param humidity
	 * @param percipitation
	 * @param pressure
	 * @param cloudCover
	 */
	protected AtmosphericInformation(DataPoint temperature, DataPoint wind,
			DataPoint humidity, DataPoint percipitation, DataPoint pressure,
			DataPoint cloudCover) {
		this.temperature = temperature;
		this.wind = wind;
		this.humidity = humidity;
		this.precipitation = percipitation;
		this.pressure = pressure;
		this.cloudCover = cloudCover;
		this.lastUpdateTime = System.currentTimeMillis();
	}

	/**
	 * Get temperature.
	 * 
	 * @return DataPoint temperature
	 */
	public DataPoint getTemperature() {
		return temperature;
	}

	/**
	 * Set Temperature.
	 * 
	 * @param temperature
	 */
	public void setTemperature(DataPoint temperature) {
		this.temperature = temperature;
	}

	/**
	 * Get wind.
	 * 
	 * @return DataPoint wind
	 */
	public DataPoint getWind() {
		return wind;
	}

	/**
	 * Set wind.
	 * 
	 * @param wind
	 */
	public void setWind(DataPoint wind) {
		this.wind = wind;
	}

	/**
	 * Get humidity
	 * 
	 * @return DataPoint humidity
	 */
	public DataPoint getHumidity() {
		return humidity;
	}

	/**
	 * Set humidity
	 * 
	 * @param humidity
	 */
	public void setHumidity(DataPoint humidity) {
		this.humidity = humidity;
	}

	/**
	 * Get precipitation
	 * 
	 * @return DataPoint precipitation
	 */
	public DataPoint getPrecipitation() {
		return precipitation;
	}

	/**
	 * Set precipitation
	 * 
	 * @param precipitation
	 */
	public void setPrecipitation(DataPoint precipitation) {
		this.precipitation = precipitation;
	}

	/**
	 * Get Pressure
	 * 
	 * @return DataPoint pressure
	 */
	public DataPoint getPressure() {
		return pressure;
	}

	/**
	 * Set Pressure
	 * 
	 * @param pressure
	 */
	public void setPressure(DataPoint pressure) {
		this.pressure = pressure;
	}

	/**
	 * Get cloudCover
	 * 
	 * @return DataPoint cloudCover
	 */
	public DataPoint getCloudCover() {
		return cloudCover;
	}

	/**
	 * Set cloudCover
	 * 
	 * @param cloudCover
	 */
	public void setCloudCover(DataPoint cloudCover) {
		this.cloudCover = cloudCover;
	}

	/**
	 * Get last update time.
	 * 
	 * @return long lastUpdateTime
	 */
	public long getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	/**
	 * Set last update time.
	 * 
	 * @param lastUpdateTime
	 */
	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
}