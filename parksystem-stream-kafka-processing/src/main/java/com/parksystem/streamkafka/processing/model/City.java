package com.parksystem.streamkafka.processing.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "city")
public class City {
	
	@Id
	private long cityId = System.nanoTime();
	
	private String contractName;
	
	@DBRef(db="cityStation")
	private List<CityStation> cityStations= new ArrayList<CityStation>();

	public City() {
		
	}
	
	@PersistenceConstructor
	public City(long cityId, String contractName, List<CityStation> cityStations) {
		
		this.cityId = cityId;
		this.contractName = contractName;
		this.cityStations = cityStations;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	

	public List<CityStation> getCityStations() {
		return cityStations;
	}

	public void setCityStations(List<CityStation> cityStations) {
		this.cityStations = cityStations;
	}


	@Override
	public String toString() {
		return "City [cityId=" + cityId + ", contractName=" + contractName + ", cityStations=" + cityStations + "]";
	}

	


}
