package com.crossover.trial.weather.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;

import com.crossover.trial.weather.dao.AirportDataDao;
import com.crossover.trial.weather.dao.AtmosphericInformationDao;
import com.crossover.trial.weather.dao.impl.AirportDataDaoImpl;
import com.crossover.trial.weather.dao.impl.AtmosphericInformationDaoImpl;
import com.crossover.trial.weather.dao.model.AirportData;
import com.crossover.trial.weather.dao.model.AtmosphericInformation;
import com.crossover.trial.weather.service.WeatherQueryEndpoint;
import com.google.gson.Gson;

/**
 * The Weather App REST endpoint allows clients to query, update and check
 * health stats. Currently, all data is held in memory. The end point deploys to
 * a single container
 *
 * @author code test administrator
 */

public class RestWeatherQueryEndpoint implements WeatherQueryEndpoint {

	public final static Logger LOGGER = Logger.getLogger(RestWeatherQueryEndpoint.class.getName());

	/** earth radius in KM */
	public static final double R = 6372.8;

	private AirportDataDao airportDataDao = new AirportDataDaoImpl();
	
	private AtmosphericInformationDao atmosphericInformationDao = new AtmosphericInformationDaoImpl();

	/**
	 * Internal performance counter to better understand most requested
	 * information, this map can be improved but for now provides the basis for
	 * future performance optimizations. Due to the stateless deployment
	 * architecture we don't want to write this to disk, but will pull it off
	 * using a REST request and aggregate with other performance metrics
	 * {@link #ping()}
	 */
	public static Map<AirportData, Integer> requestFrequency = new HashMap<AirportData, Integer>();

	public static Map<Double, Integer> radiusFreq = new HashMap<Double, Integer>();

	/**
	 * Retrieve service health including total size of valid data points and
	 * request frequency information.
	 *
	 * @return health stats for the service as a string
	 */
	@Override
	public String ping() {
		Map<String, Object> retval = new HashMap<>();
		Collection<AtmosphericInformation> atmosphericInformation = atmosphericInformationDao.findAll();
		int datasize = 0;
		for (AtmosphericInformation ai : atmosphericInformation) {
			// we only count recent readings
			if (ai.getCloudCover() != null || ai.getHumidity() != null || ai.getPressure() != null
					|| ai.getPrecipitation() != null || ai.getTemperature() != null || ai.getWind() != null) {
				// updated in the last day
				if (ai.getLastUpdateTime() > System.currentTimeMillis() - 86400000) {
					datasize++;
				}
			}
		}
		retval.put("datasize", datasize);

		Map<String, Double> freq = new HashMap<>();
		// fraction of queries
		Collection<AirportData> airportData = airportDataDao.findAll();
		for (AirportData data : airportData) {
			double frac = (double) requestFrequency.getOrDefault(data, 0) / requestFrequency.size();
			freq.put(data.getIata(), frac);
		}
		retval.put("iata_freq", freq);

		int m = radiusFreq.keySet().stream().max(Double::compare).orElse(1000.0).intValue() + 1;

		int[] hist = new int[m];
		for (Map.Entry<Double, Integer> e : radiusFreq.entrySet()) {
			int i = e.getKey().intValue() % 10;
			hist[i] += e.getValue();
		}
		retval.put("radius_freq", hist);
		Gson gson = new Gson();

		return gson.toJson(retval);
	}

	/**
	 * Given a query in json format {'iata': CODE, 'radius': km} extracts the
	 * requested airport information and return a list of matching atmosphere
	 * information.
	 *
	 * @param iata
	 *            the iataCode
	 * @param radiusString
	 *            the radius in km
	 *
	 * @return a list of atmospheric information
	 */
	@Override
	public Response weather(String iata, String radiusString) {
		double radius = radiusString == null || radiusString.trim().isEmpty() ? 0 : Double.valueOf(radiusString);
		updateRequestFrequency(iata, radius);

		List<AtmosphericInformation> retval = new ArrayList<>();
		if (radius == 0) {
			AtmosphericInformation atmosphericInformation = atmosphericInformationDao.lookup(iata);
			retval.add(atmosphericInformation);
		} else {
			AirportData ad = airportDataDao.lookup(iata);
			Collection<AirportData> airportData = airportDataDao.findAll();
			for (AirportData data : airportData) {
				if (calculateDistance(ad, data) <= radius) {
					AtmosphericInformation ai = atmosphericInformationDao.lookup(data.getIata());
					if (ai == null) {
						continue;
					}
					if (ai.getCloudCover() != null || ai.getHumidity() != null || ai.getPrecipitation() != null
							|| ai.getPressure() != null || ai.getTemperature() != null || ai.getWind() != null) {
						retval.add(ai);
					}
				}
			}
		}
		return Response.status(Response.Status.OK).entity(retval).build();
	}

	/**
	 * Records information about how often requests are made
	 *
	 * @param iata
	 *            an iata code
	 * @param radius
	 *            query radius
	 */
	public void updateRequestFrequency(String iata, Double radius) {
		AirportData airportData = airportDataDao.lookup(iata);
		requestFrequency.put(airportData, requestFrequency.getOrDefault(airportData, 0) + 1);
		radiusFreq.put(radius, radiusFreq.getOrDefault(radius, 0));
	}

	/**
	 * Haversine distance between two airports.
	 *
	 * @param ad1
	 *            airport 1
	 * @param ad2
	 *            airport 2
	 * @return the distance in KM
	 */
	public double calculateDistance(AirportData ad1, AirportData ad2) {
		double deltaLat = Math.toRadians(ad2.getLatitude() - ad1.getLatitude());
		double deltaLon = Math.toRadians(ad2.getLongitude() - ad1.getLongitude());
		double a = Math.pow(Math.sin(deltaLat / 2), 2)
				+ Math.pow(Math.sin(deltaLon / 2), 2) * Math.cos(ad1.getLatitude()) * Math.cos(ad2.getLatitude());
		double c = 2 * Math.asin(Math.sqrt(a));
		return R * c;
	}

}
