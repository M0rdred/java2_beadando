package hu.tutor.service;

import java.util.Collection;

import com.vaadin.tapio.googlemaps.client.LatLon;

import hu.tutor.model.Address;

public interface GoogleMapService {

	public LatLon getGeoCode(String address);

	public LatLon getGeoCode(Address address);

	public LatLon getGeoCode(Integer zip, String country, String city, String street, String houseNumber);

	public LatLon[] getBounds(Collection<LatLon> latlons);

}
