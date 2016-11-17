package com.crossover.trial.weather.dao.impl;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.crossover.trial.weather.dao.model.AirportData;
import com.crossover.trial.weather.dao.model.AtmosphericInformation;

/**
 * General implementation of the DAO that can be used for any key-value storage.
 *
 */
class DataStore {

	/**
    * Airports
    */
   protected static Map<String, AirportData> AIRPORT_STORE = new ConcurrentHashMap<>();

   /**
    * Atmospheric information
    */
   protected static Map<String, AtmosphericInformation> ATMOSPHERIC_INFORMATION_STORE = new ConcurrentHashMap<>();
   

}