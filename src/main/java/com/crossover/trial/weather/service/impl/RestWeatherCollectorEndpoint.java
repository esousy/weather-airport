package com.crossover.trial.weather.service.impl;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;

import com.crossover.trial.weather.dao.AirportDataDao;
import com.crossover.trial.weather.dao.AtmosphericInformationDao;
import com.crossover.trial.weather.dao.impl.AirportDataDaoImpl;
import com.crossover.trial.weather.dao.impl.AtmosphericInformationDaoImpl;
import com.crossover.trial.weather.dao.model.AirportData;
import com.crossover.trial.weather.dao.model.AtmosphericInformation;
import com.crossover.trial.weather.dao.model.DataPoint;
import com.crossover.trial.weather.dao.model.DataPointType;
import com.crossover.trial.weather.exception.WeatherException;
import com.crossover.trial.weather.service.WeatherCollectorEndpoint;
import com.google.gson.Gson;

/**
 * A REST implementation of the WeatherCollector API. Accessible only to airport
 * weather collection sites via secure VPN.
 *
 * @author code test administrator
 */

public class RestWeatherCollectorEndpoint implements WeatherCollectorEndpoint {
	public final static Logger LOGGER = Logger.getLogger(RestWeatherCollectorEndpoint.class.getName());

	private AirportDataDao airportDataDao = new AirportDataDaoImpl();
	
	private AtmosphericInformationDao atmosphericInformationDao = new AtmosphericInformationDaoImpl();

	@Override
	public Response ping() {
		return Response.status(Response.Status.OK).entity("ready").build();
	}

	@Override
	public Response updateWeather(String iataCode, String pointType, String datapointJson) {
		if (iataCode == null || iataCode.length() != 3) {
			LOGGER.severe("Bad parameters: iata = " + iataCode);
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		try {
			Gson gson = new Gson();
			addDataPoint(iataCode, pointType, gson.fromJson(datapointJson, DataPoint.class));
		} catch (WeatherException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			return Response.serverError().build();
		}
		return Response.status(Response.Status.OK).build();
	}

	@Override
	public Response getAirports() {
		Set<String> retval = airportDataDao.findAllCodes();
		return Response.status(Response.Status.OK).entity(retval).build();
	}

	@Override
	public Response getAirport(String iata) {
		AirportData ad = airportDataDao.lookup(iata);
		return Response.status(Response.Status.OK).entity(ad).build();
	}

	@Override
	public Response addAirport(String iata, String latString, String longString) {
		System.out.println("addAirport: " + iata);
		if (iata == null || iata.length() != 3 || latString == null || longString == null) {
			LOGGER.log(Level.SEVERE,
					"Bad parameters: iata = " + iata + ", latString = " + latString + ", longString = " + longString);
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		AirportData ad = new AirportData(iata, Double.valueOf(latString), Double.valueOf(longString));
		airportDataDao.save(ad);
		return Response.status(Response.Status.OK).build();
	}

	@Override
	public Response deleteAirport(String iata) {
		if (iata == null) {
			LOGGER.log(Level.SEVERE, "Bad parameters: iata = " + iata);
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		airportDataDao.delete(iata);
		return Response.status(Response.Status.NOT_IMPLEMENTED).build();
	}

	@Override
	public Response exit() {
		System.exit(0);
		return Response.noContent().build();
	}
	//
	// Internal support methods
	//

	/**
	 * Update the airports weather data with the collected data.
	 *
	 * @param iataCode
	 *            the 3 letter IATA code
	 * @param pointType
	 *            the point type {@link DataPointType}
	 * @param dp
	 *            a datapoint object holding pointType data
	 *
	 * @throws WeatherException
	 *             if the update can not be completed
	 */
	public void addDataPoint(String iataCode, String pointType, DataPoint dp) throws WeatherException {
		AtmosphericInformation ai = atmosphericInformationDao.lookup(iataCode);
		AtmosphericInformation newValue = new AtmosphericInformation(ai.getTemperature(), ai.getHumidity(), ai.getWind(), 
				ai.getPrecipitation(), ai.getPressure(), ai.getCloudCover());
        
		updateAtmosphericInformation(newValue, pointType, dp);
		atmosphericInformationDao.update(iataCode, newValue);
	}

	/**
	 * update atmospheric information with the given data point for the given
	 * point type
	 *
	 * @param ai
	 *            the atmospheric information object to update
	 * @param pointType
	 *            the data point type as a string
	 * @param dp
	 *            the actual data point
	 */
	public void updateAtmosphericInformation(AtmosphericInformation ai, String pointType, DataPoint dp)
			throws WeatherException {
		if (ai == null) {
			throw new WeatherException();
		}
		DataPointType dptype = null;
		try {
			dptype = DataPointType.valueOf(pointType.toUpperCase());
		} catch (IllegalArgumentException ex) {
			throw new WeatherException();
		}

		switch (dptype) {
		case WIND:
			if (dp.getMean() >= 0) {
				ai.setWind(dp);
				ai.setLastUpdateTime(System.currentTimeMillis());
			}
			break;

		case TEMPERATURE:
			if (dp.getMean() >= -50 && dp.getMean() < 100) {
				ai.setTemperature(dp);
				ai.setLastUpdateTime(System.currentTimeMillis());
			}
			break;

		case HUMIDTY:
			if (dp.getMean() >= 0 && dp.getMean() < 100) {
				ai.setHumidity(dp);
				ai.setLastUpdateTime(System.currentTimeMillis());
			}
			break;

		case PRESSURE:
			if (dp.getMean() >= 650 && dp.getMean() < 800) {
				ai.setPressure(dp);
				ai.setLastUpdateTime(System.currentTimeMillis());
			}
			break;

		case CLOUDCOVER:
			if (dp.getMean() >= 0 && dp.getMean() < 100) {
				ai.setCloudCover(dp);
				ai.setLastUpdateTime(System.currentTimeMillis());
			}
			break;

		case PRECIPITATION:
			if (dp.getMean() >= 0 && dp.getMean() < 100) {
				ai.setPrecipitation(dp);
				ai.setLastUpdateTime(System.currentTimeMillis());
			}
			break;
		}
	}
}
