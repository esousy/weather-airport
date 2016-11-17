package com.crossover.trial.weather.dao.impl;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import com.crossover.trial.weather.dao.AirportDataDao;
import com.crossover.trial.weather.dao.model.AirportData;
import com.crossover.trial.weather.dao.model.AtmosphericInformation;

/**
 * General implementation of the DAO that can be used for any key-value storage.
 *
 */
public class AirportDataDaoImpl implements AirportDataDao {

    private static final Logger LOGGER = Logger.getLogger(AirportDataDaoImpl.class.getName());

    @Override
    public AirportData lookup(String iataCode) {
        if (iataCode == null) {
            LOGGER.severe("iataCode is null");
            return null;
        }
        return DataStore.AIRPORT_STORE.get(iataCode);
    }
    
    /**
     * Haversine distance between two airports.
     *
     * @param ad1 airport 1
     * @param ad2 airport 2
     * @return the distance in KM
     */
    private double calculateDistance(AirportData ad1, AirportData ad2) {
        double deltaLat = Math.toRadians(ad2.getLatitude() - ad1.getLatitude());
        double deltaLon = Math.toRadians(ad2.getLongitude() - ad1.getLongitude());
        double a = Math.pow(Math.sin(deltaLat / 2), 2) + Math.pow(Math.sin(deltaLon / 2), 2)
                * Math.cos(ad1.getLatitude()) * Math.cos(ad2.getLatitude());
        double c = 2 * Math.asin(Math.sqrt(a));

        return 6372.8 * c;
    }	

    @Override
    public Set<AirportData> find(String iataCode, double radius) {
        Set<AirportData> result = new HashSet<>();
        if (iataCode == null) {
            LOGGER.severe("iataCode is null");
            return result;
        }

        AirportData ad = DataStore.AIRPORT_STORE.get(iataCode);
        if (ad == null) {
            LOGGER.severe("Cannot find airport iataCode = " + iataCode);
            return result;
        }

        for (AirportData a : DataStore.AIRPORT_STORE.values()) {
            if (calculateDistance(ad, a) <= radius) {
                result.add(a);
            }
        }

        return result;
    }

    @Override
    public Set<String> findAllCodes() {
        return DataStore.AIRPORT_STORE.keySet();
    }

    @Override
    public Collection<AirportData> findAll() {
        return DataStore.AIRPORT_STORE.values();
    }

    @Override
    public void save(AirportData ad) {
        if (ad == null || ad.getIata() == null) {
            LOGGER.severe("Cannot save airport");
            return;
        }
        DataStore.AIRPORT_STORE.put(ad.getIata(), ad);
        AtmosphericInformation ai = new AtmosphericInformation();
        DataStore.ATMOSPHERIC_INFORMATION_STORE.put(ad.getIata(), ai);
    }

    @Override
    public void delete(String iataCode) {
        if (iataCode == null) {
            LOGGER.severe("Cannot delete airport");
            return;
        }
        DataStore.AIRPORT_STORE.remove(iataCode);
        DataStore.ATMOSPHERIC_INFORMATION_STORE.remove(iataCode);
    }

}