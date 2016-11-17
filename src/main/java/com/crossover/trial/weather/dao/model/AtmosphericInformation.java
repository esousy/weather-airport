package com.crossover.trial.weather.dao.model;

import java.io.Serializable;

/**
 * encapsulates sensor information for a particular location
 */
public class AtmosphericInformation implements Serializable {

	private static final long serialVersionUID = -5317972341273507458L;

	/** temperature in degrees celsius */
	private DataPoint temperature;

	/** wind speed in km/h */
	private DataPoint wind;

	/** humidity in percent */
	private DataPoint humidity;

	/** precipitation in cm */
	private DataPoint precipitation;

	/** pressure in mmHg */
	private DataPoint pressure;

	/** cloud cover percent from 0 - 100 (integer) */
	private DataPoint cloudCover;

	/** the last time this data was updated, in milliseconds since UTC epoch */
	private long lastUpdateTime;

	/**
	 * 
	 */
	public AtmosphericInformation() {

	}

	/**
	 * @param temperature
	 * @param wind
	 * @param humidity
	 * @param percipitation
	 * @param pressure
	 * @param cloudCover
	 */
	public AtmosphericInformation(DataPoint temperature, DataPoint wind, DataPoint humidity, DataPoint percipitation,
			DataPoint pressure, DataPoint cloudCover) {
		this.temperature = temperature;
		this.wind = wind;
		this.humidity = humidity;
		this.precipitation = percipitation;
		this.pressure = pressure;
		this.cloudCover = cloudCover;
		this.lastUpdateTime = System.currentTimeMillis();
	}

	/**
	 * @return the temperature
	 */
	public final DataPoint getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public final void setTemperature(DataPoint temperature) {
		this.temperature = temperature;
	}

	/**
	 * @return the wind
	 */
	public final DataPoint getWind() {
		return wind;
	}

	/**
	 * @param wind the wind to set
	 */
	public final void setWind(DataPoint wind) {
		this.wind = wind;
	}

	/**
	 * @return the humidity
	 */
	public final DataPoint getHumidity() {
		return humidity;
	}

	/**
	 * @param humidity the humidity to set
	 */
	public final void setHumidity(DataPoint humidity) {
		this.humidity = humidity;
	}

	/**
	 * @return the precipitation
	 */
	public final DataPoint getPrecipitation() {
		return precipitation;
	}

	/**
	 * @param precipitation the precipitation to set
	 */
	public final void setPrecipitation(DataPoint precipitation) {
		this.precipitation = precipitation;
	}

	/**
	 * @return the pressure
	 */
	public final DataPoint getPressure() {
		return pressure;
	}

	/**
	 * @param pressure the pressure to set
	 */
	public final void setPressure(DataPoint pressure) {
		this.pressure = pressure;
	}

	/**
	 * @return the cloudCover
	 */
	public final DataPoint getCloudCover() {
		return cloudCover;
	}

	/**
	 * @param cloudCover the cloudCover to set
	 */
	public final void setCloudCover(DataPoint cloudCover) {
		this.cloudCover = cloudCover;
	}

	/**
	 * @return the lastUpdateTime
	 */
	public final long getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public final void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cloudCover == null) ? 0 : cloudCover.hashCode());
		result = prime * result + ((humidity == null) ? 0 : humidity.hashCode());
		result = prime * result + (int) (lastUpdateTime ^ (lastUpdateTime >>> 32));
		result = prime * result + ((precipitation == null) ? 0 : precipitation.hashCode());
		result = prime * result + ((pressure == null) ? 0 : pressure.hashCode());
		result = prime * result + ((temperature == null) ? 0 : temperature.hashCode());
		result = prime * result + ((wind == null) ? 0 : wind.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AtmosphericInformation other = (AtmosphericInformation) obj;
		if (cloudCover == null) {
			if (other.cloudCover != null)
				return false;
		} else if (!cloudCover.equals(other.cloudCover))
			return false;
		if (humidity == null) {
			if (other.humidity != null)
				return false;
		} else if (!humidity.equals(other.humidity))
			return false;
		if (lastUpdateTime != other.lastUpdateTime)
			return false;
		if (precipitation == null) {
			if (other.precipitation != null)
				return false;
		} else if (!precipitation.equals(other.precipitation))
			return false;
		if (pressure == null) {
			if (other.pressure != null)
				return false;
		} else if (!pressure.equals(other.pressure))
			return false;
		if (temperature == null) {
			if (other.temperature != null)
				return false;
		} else if (!temperature.equals(other.temperature))
			return false;
		if (wind == null) {
			if (other.wind != null)
				return false;
		} else if (!wind.equals(other.wind))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AtmosphericInformation [temperature=" + temperature + ", wind=" + wind + ", humidity=" + humidity
				+ ", precipitation=" + precipitation + ", pressure=" + pressure + ", cloudCover=" + cloudCover
				+ ", lastUpdateTime=" + lastUpdateTime + "]";
	}
	
	
	
}
