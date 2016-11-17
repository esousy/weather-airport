package com.crossover.trial.weather.dao;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.crossover.trial.weather.dao.model.AtmosphericInformation;

/**
 * DAO for business objects
 */
public interface AtmosphericInformationDao {
	/**
	 * Given an iataCode find atmospheric information around the airport
	 *
	 * @param iataCode
	 *            as a string
	 * @return atmospheric information or null if not found
	 */
	AtmosphericInformation lookup(String iataCode);

	/**
	 * Given an iataCode and radius find atmospheric information around the
	 * airports within the radius
	 *
	 * @param iataCode
	 *            as a string
	 * @param radius
	 *            as a double
	 * @return list of objects or empty collection if not found
	 */
	//List<AtmosphericInformation> find(String iataCode, double radius);

	/**
	 * 
	 * @return list of objects or empty collection if not found
	 */
	Collection<AtmosphericInformation> findAll();

	/**
	 * Update atmospheric information with the given data point for the given
	 * point type
	 *
	 * @param iataCode
	 *            airport iata code
	 * @param pointType
	 *            the data point type as a string
	 * @param dp
	 *            the actual data point
	 */
	void update(String iataCode, AtmosphericInformation newValue);

	/**
	 * Delete atmospheric information of particular airport from the database
	 *
	 * @param iataCode
	 *            as a string
	 */
	void delete(String iataCode);
	
	Set<String> findAllCodes();

}