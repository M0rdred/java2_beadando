package hu.tutor.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.tapio.googlemaps.client.LatLon;

import hu.tutor.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GoogleMapServiceImpl implements GoogleMapService {

	private static final String GOOGLE_OK = "OK";
	private static final String GOOGLE_MAP_URL = "https://maps.googleapis.com/maps/api/geocode/";
	private static final String GOOGLE_DISTANCE_MATRIX_URL = "https://maps.googleapis.com/maps/api/distancematrix/";
	@Value("${google.maps.api-key}")
	private String API_KEY;

	@Override
	public LatLon getGeoCode(String address) {
		String googleAddress = this.replaceSpaces(address);

		if (googleAddress.isEmpty()) {
			return null;
		}

		StringBuilder builder = new StringBuilder();
		builder.append(GOOGLE_MAP_URL);
		builder.append(GoogleMapResultFormat.JSON.getValue());
		builder.append("?");
		builder.append("address=");
		builder.append(googleAddress);
		builder.append("&");
		builder.append("key=");
		builder.append(this.API_KEY);

		try {
			URL url = new URL(builder.toString());

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(reader);
			String status = root.get("status").textValue();

			if (GOOGLE_OK.equals(status)) {
				JsonNode results = root.withArray("results");
				JsonNode geometry = results.get(0).get("geometry");
				JsonNode location = geometry.get("location");

				double lat = location.get("lat").asDouble();
				double lon = location.get("lng").asDouble();

				return new LatLon(lat, lon);
			} else {
				log.error("Google Status:  " + status);
			}

			reader.close();
		} catch (

		IOException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	public LatLon getGeoCode(Integer zip, String country, String city, String street, String houseNumber) {
		StringBuilder builder = new StringBuilder();
		boolean isValidAddress = false;

		if (zip != null) {
			builder.append(zip.toString());
			isValidAddress = true;
		}

		if (country != null) {
			if (isValidAddress) {
				builder.append(" ");
			}
			builder.append(country.toString());
			isValidAddress = true;
		}
		if (isValidAddress) {
			builder.append(" ");
		}
		if (city != null) {
			builder.append(city.toString());
			isValidAddress = true;
		}

		if (street != null) {
			if (isValidAddress) {
				builder.append(" ");
			}
			builder.append(street.toString());
			isValidAddress = true;
		}

		if (houseNumber != null) {
			if (isValidAddress) {
				builder.append(" ");
			}
			builder.append(houseNumber.toString());
		}

		return this.getGeoCode(builder.toString());
	}

	@Override
	public LatLon getGeoCode(Address address) {
		return this.getGeoCode(address.getFullAddress());
	}

	@Override
	public Integer getDistanceBetweenCities(String cityFrom, String cityTo) {
		if (StringUtils.isAnyEmpty(cityFrom, cityTo)) {
			return null;
		}

		StringBuilder builder = new StringBuilder();
		builder.append(GOOGLE_DISTANCE_MATRIX_URL);
		builder.append(GoogleMapResultFormat.JSON.getValue());
		builder.append("?");
		builder.append("units=metric");
		builder.append("&");
		builder.append("origins=");
		builder.append(cityFrom);
		builder.append("&");
		builder.append("destinations=");
		builder.append(cityTo);
		builder.append("&");
		builder.append("key=");
		builder.append(this.API_KEY);

		try {
			URL url = new URL(builder.toString());

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(reader);
			reader.close();

			String status = root.get("status").textValue();

			if (GOOGLE_OK.equals(status)) {
				JsonNode rows = root.withArray("rows");
				JsonNode elements = rows.get(0).withArray("elements");
				JsonNode firstElement = elements.get(0);
				String elementsStatus = firstElement.get("status").textValue();

				if (GOOGLE_OK.equals(elementsStatus)) {
					JsonNode distance = firstElement.get("distance");
					return distance.get("value").asInt();
				} else {
					log.error("Google Elements Status(for " + cityFrom + ", " + cityTo + "):  " + status);
				}
			} else {
				log.error("Google Status(for " + cityFrom + ", " + cityTo + "):  " + status);
			}

		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	public LatLon[] getBounds(Collection<LatLon> latlons) {
		if (latlons != null && latlons.size() > 0) {

			double north = latlons.stream().filter(l -> l != null).mapToDouble(LatLon::getLat).max().getAsDouble();
			double east = latlons.stream().filter(l -> l != null).mapToDouble(LatLon::getLon).max().getAsDouble();
			double south = latlons.stream().filter(l -> l != null).mapToDouble(LatLon::getLat).min().getAsDouble();
			double west = latlons.stream().filter(l -> l != null).mapToDouble(LatLon::getLon).min().getAsDouble();

			LatLon northEast = new LatLon(north, east);
			LatLon southWest = new LatLon(south, west);

			return new LatLon[] { northEast, southWest };
		} else {
			return null;
		}
	}

	private String replaceSpaces(String address) {
		return address.replace(' ', '+');
	}

	@AllArgsConstructor
	private enum GoogleMapResultFormat {
		XML("xml"), JSON("json");

		@Getter
		private String value;
	}

}
