package com.parksystem.streamkafka.processing.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cityStation")
public class CityStation {
	
	@Id
	private long cityStationId = System.nanoTime();;
	
	private int stationNumber;
	private String address;
	private int availableBikeStands;
	private Double longitude;
	private Double latitude;

	
	public CityStation() {
		
	}

	@PersistenceConstructor
	public CityStation(long cityStationId, int stationNumber, String address, int availableBikeStands, Double longitude, Double latitude) {
		super();
		this.cityStationId = cityStationId;
		this.stationNumber = stationNumber;
		this.address = address;
		this.availableBikeStands = availableBikeStands;
		this.longitude = longitude;
		this.latitude = latitude;
		
	}


	public long getCityStationId() {
		return cityStationId;
	}

	public void setCityStationId(long cityStationId) {
		this.cityStationId = cityStationId;
	}

	public int getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(int stationNumber) {
		this.stationNumber = stationNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAvailableBikeStands() {
		return availableBikeStands;
	}

	public void setAvailableBikeStands(int availableBikeStands) {
		this.availableBikeStands = availableBikeStands;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "CityStation [cityStationId=" + cityStationId + ", stationNumber=" + stationNumber + ", address="
				+ address + ", availableBikeStands=" + availableBikeStands + ", longitude=" + longitude + ", latitude="
				+ latitude + "]";
	}

}
