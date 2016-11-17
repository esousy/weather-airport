package com.crossover.trial.weather.loader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.crossover.trial.weather.exception.WeatherException;

/**
 * A simple airport loader which reads a file from disk and sends entries to the
 * webservice
 *
 * TODO: Implement the Airport Loader
 * 
 * @author code test administrator
 */
public class AirportLoader {

	/** end point to supply updates */
	private WebTarget collect;

	private String airportDataFile;

	private String weatherServerURL;

	public AirportLoader() {
		Client client = ClientBuilder.newClient();
		this.collect = client.target("http://localhost:9090" + "/collect");
	}

	/**
	 * @return the airportDataFile
	 */
	public final String getAirportDataFile() {
		return airportDataFile;
	}

	/**
	 * @param airportDataFile
	 *            the airportDataFile to set
	 */
	public final void setAirportDataFile(String airportDataFile) {
		this.airportDataFile = airportDataFile;
	}

	/**
	 * @return the weatherServerURL
	 */
	public final String getWeatherServerURL() {
		return weatherServerURL;
	}

	/**
	 * @param weatherServerURL
	 *            the weatherServerURL to set
	 */
	public final void setWeatherServerURL(String weatherServerURL) {
		this.weatherServerURL = weatherServerURL;
	}

	public void upload() throws WeatherException {
		try {
			InputStream airportDataStream = new FileInputStream("src/main/resources/airports.dat");
			BufferedReader reader = new BufferedReader(new InputStreamReader(airportDataStream));
			String l = null;
			int index = 1;
			while ((l = reader.readLine()) != null) {
				if (postLine(l)) {
					System.out.println("Line [" + index + "] successed: '" + l);
				} else {
					System.out.println("Line [" + index + "] failed: '" + l);
				}
				index++;
			}
			if (reader != null) {
				reader.close();
			}
		} catch (Exception e) {
			throw new WeatherException(e);
		} 
	}

	private boolean postLine(String line) {
		boolean result = false;
		String[] records = line != null ? line.split(",") : null;
		if (records == null || records.length <= 7) {
			return false;
		}

		String iataCode = records[4];
		String latitude = records[6];
		String longitude = records[7];
		

		Form form =new Form();
		form.param("iata", iataCode.replace("\"", ""));
		form.param("lat", latitude);
		form.param("long", longitude);

		Response post = collect.request().post(Entity.entity(form,
				MediaType.APPLICATION_FORM_URLENCODED),Response.class);

		switch (post.getStatus()) {
		case 200:
			result = true;
			break;

		case 403:
			System.out.println("Warning: airport entry '" + iataCode + "' already exists");
			break;

		default:
			System.out.println(
					"ERROR when adding airport '" + iataCode + "': " + post.getStatus() + " " + post.getStatusInfo());
		}
		return result;
	}

	public static void main(String args[]) {
		try {
			AirportLoader al = new AirportLoader();
			al.upload();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
}
