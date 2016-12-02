package com.weather.city;

public class City {
	private final long id;
	private final String name;
	private final String zipCode;
	private final String countryCode;
	private final double latitude;
	private final double longitude;
	
	public City(long id, String name, String zipCode, String countryCode, double lat, double lon) {
		this.id = id;
		this.name = name;
		this.zipCode = zipCode;
		this.countryCode = countryCode;
		this.latitude = lat;
		this.longitude = lon;
	}
	
	public long getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getZipCode() {
		return this.zipCode;
	}
	
	public String getCountry() {
		return this.countryCode;
	}
	
	public double getLatitude() {
		return this.latitude;
	}
	
	public double getLongitude(){
		return this.longitude;
	}
}
