package com.crossover.trial.weather.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.crossover.trial.weather.dao.AtmosphericInformationDao;
import com.crossover.trial.weather.dao.model.AirportData;
import com.crossover.trial.weather.dao.model.AtmosphericInformation;
import com.crossover.trial.weather.dao.model.DataPoint;
import com.crossover.trial.weather.dao.model.DataPointType;


/**
 * General implementation of the DAO that can be used for any key-value storage.
 *
 */
public class AtmosphericInformationDaoImpl implements AtmosphericInformationDao {

    private static final Logger LOGGER = Logger.getLogger(AtmosphericInformationDaoImpl.class.getName());
    
    
    @Override
    public AtmosphericInformation lookup(String iataCode) {
        return DataStore.ATMOSPHERIC_INFORMATION_STORE.get(iataCode);
    }

    @Override
    public Collection<AtmosphericInformation> findAll() {
        return DataStore.ATMOSPHERIC_INFORMATION_STORE.values();
    }

    @Override
    public void update(String iataCode, AtmosphericInformation newValue) {

        AtmosphericInformation oldValue = DataStore.ATMOSPHERIC_INFORMATION_STORE.get(iataCode);
        if (oldValue == null) {
            oldValue = new AtmosphericInformation();
            DataStore.ATMOSPHERIC_INFORMATION_STORE.putIfAbsent(iataCode, oldValue);
        }

        DataStore.ATMOSPHERIC_INFORMATION_STORE.replace(iataCode, oldValue, newValue);
    }

    @Override
    public void delete(String iataCode) {
        if (iataCode == null) {
            LOGGER.severe("Cannot delete atmospheric information");
            return;
        }
        DataStore.ATMOSPHERIC_INFORMATION_STORE.remove(iataCode);
    }
    
    @Override
    public Set<String> findAllCodes() {
        return DataStore.ATMOSPHERIC_INFORMATION_STORE.keySet();
    }

}