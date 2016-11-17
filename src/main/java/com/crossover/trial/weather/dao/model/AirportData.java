package com.crossover.trial.weather.dao.model;

import java.io.Serializable;

/**
 * Basic airport information.
 *
 * @author code test administrator
 */
public class AirportData implements Serializable {

	private static final long serialVersionUID = -2533695717816373790L;

	/** the three letter IATA code */
	private String iata;

	/** latitude value in degrees */
	private double latitude;

	/** longitude value in degrees */
	private double longitude;

	
	/**
	 * @param iata
	 * @param latitude
	 * @param longitude
	 */
	public AirportData(String iata, double latitude, double longitude) {
		this.iata = iata;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * @return the iata
	 */
	public final String getIata() {
		return iata;
	}

	/**
	 * @param iata
	 *            the iata to set
	 */
	public final void setIata(String iata) {
		this.iata = iata;
	}

	/**
	 * @return the latitude
	 */
	public final double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public final void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public final double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public final void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iata == null) ? 0 : iata.hashCode());
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		AirportData other = (AirportData) obj;
		if (iata == null) {
			if (other.iata != null)
				return false;
		} else if (!iata.equals(other.iata))
			return false;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AirportData [iata=" + iata + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

}
