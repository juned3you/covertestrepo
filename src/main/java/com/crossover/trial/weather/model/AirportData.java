package com.crossover.trial.weather.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * <h3>Basic airport information.</h3>
 * 
 * @author code test administrator
 * @version 1.2.0
 * @since 2016-12-24
 */
public class AirportData {

	/**
	 * the three letter IATA code
	 **/
	private String iata;

	/**
	 * latitude value in degrees
	 */
	private double latitude;

	/**
	 * longitude value in degrees
	 */
	private double longitude;

	/**
	 * atmospheric information for each airport.
	 */
	private AtmosphericInformation atmosphericInformation;

	/**
	 * Airport city
	 */
	private String city;

	/**
	 * Airport country
	 */
	private String country;

	/**
	 * 4-letter ICAO code
	 */
	private String icao;

	/**
	 * Altitude in Feet
	 */
	private double altitude;

	/**
	 * Hours offset from UTC. Fractional hours are expressed as decimals.
	 */
	private double timezoneHoursOffset;

	private char daylightSavingTime;

	public AirportData() {
	}

	/**
	 * Get iata
	 * 
	 * @return String iata
	 */
	public String getIata() {
		return iata;
	}

	/**
	 * Set iata string
	 * 
	 * @param iata
	 */
	public void setIata(String iata) {
		this.iata = iata;
	}

	/**
	 * Get Latitude
	 * 
	 * @return double latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Set Latitude
	 * 
	 * @param latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Get longitude
	 * 
	 * @return double longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Set longitude
	 * 
	 * @param longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Override default.
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.NO_CLASS_NAME_STYLE);
	}

	/**
	 * Override default.
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof AirportData) {
			return ((AirportData) other).getIata().equals(this.getIata());
		}

		return false;
	}

	/**
	 * Override default.
	 */
	@Override
	public int hashCode() {
		return this.getIata().hashCode();
	}

	/**
	 * Set atmosphericInformation
	 * 
	 * @return AtmosphericInformation
	 */
	public AtmosphericInformation getAtmosphericInformation() {
		return atmosphericInformation;
	}

	/**
	 * Get AtmosphericInformation
	 * 
	 * @param atmosphericInformation
	 *            AtmosphericInformation
	 */
	public void setAtmosphericInformation(
			AtmosphericInformation atmosphericInformation) {
		this.atmosphericInformation = atmosphericInformation;
	}

	/**
	 * Get Airport city.
	 * 
	 * @return String city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Set Airport city.
	 * 
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Get Airport country.
	 * 
	 * @return String country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Set Airport country.
	 * 
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Get Airport icao.
	 * 
	 * @return String icao
	 */
	public String getIcao() {
		return icao;
	}

	/**
	 * Set Airport icao.
	 * 
	 * @param icao
	 */
	public void setIcao(String icao) {
		this.icao = icao;
	}

	/**
	 * Get Airport altitude.
	 * 
	 * @return double altitude
	 */
	public double getAltitude() {
		return altitude;
	}

	/**
	 * Set Airport altitude.
	 * 
	 * @param altitude
	 */
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	/**
	 * Get Airport timezoneHoursOffset.
	 * 
	 * @return double timezoneHoursOffset
	 */
	public double getTimezoneHoursOffset() {
		return timezoneHoursOffset;
	}

	/**
	 * Set Airport timezoneHoursOffset.
	 * 
	 * @param timezoneHoursOffset
	 */
	public void setTimezoneHoursOffset(double timezoneHoursOffset) {
		this.timezoneHoursOffset = timezoneHoursOffset;
	}

	/**
	 * Get Airport daylightSavingTime.
	 * 
	 * @return char daylightSavingTime
	 */
	public char getDaylightSavingTime() {
		return daylightSavingTime;
	}

	/**
	 * Set Airport daylightSavingTime.
	 * 
	 * @param daylightSavingTime
	 */
	public void setDaylightSavingTime(char daylightSavingTime) {
		this.daylightSavingTime = daylightSavingTime;
	}
}