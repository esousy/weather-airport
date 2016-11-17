package com.crossover.trial.weather.dao;

import java.util.Collection;
import java.util.Set;

import com.crossover.trial.weather.dao.model.AirportData;

/**
 * DAO for business objects
 */
public interface AirportDataDao {

	/**
	 * Given an iataCode find the airport data
	 *
	 * @param iataCode
	 *            as a string
	 * @return airport data or null if not found
	 */
	AirportData lookup(String iataCode);

	/**
	 * Given an iataCode and radius find airports within the radius
	 *
	 * @param iataCode
	 *            as a string
	 * @param radius
	 *            as a double
	 * @return set of airports or empty collection if not found
	 */
	Set<AirportData> find(String iataCode, double radius);

	/**
	 * Get all airports stored in the database
	 *
	 * @return set of airport IATA codes
	 */
	Collection<AirportData> findAll();

	/**
	 * Get all IATA codes of the airports stored in the database
	 *
	 * @return set of airport IATA codes
	 */
	Set<String> findAllCodes();

	/**
	 * Save airport data to the database
	 *
	 * @param ad
	 *            as an AirportData
	 */
	void save(AirportData ad);

	/**
	 * Delete airport data and it's atmospheric information from the database
	 *
	 * @param iataCode
	 *            as a string
	 */
	void delete(String iataCode);

}