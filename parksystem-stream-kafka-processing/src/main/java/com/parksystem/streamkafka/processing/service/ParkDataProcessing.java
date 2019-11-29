package com.parksystem.streamkafka.processing.service;

import com.parksystem.streamkafka.processing.stream.ParkDataStreams;
import com.parksystem.streamkafka.processing.service.ParkDataService;
import com.parksystem.streamkafka.processing.repository.CityRepository;
import com.parksystem.streamkafka.processing.repository.CityStationRepository;
import com.parksystem.streamkafka.processing.model.City;
import com.parksystem.streamkafka.processing.model.CityStation;
import com.parksystem.streamkafka.processing.model.ParkData;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ParkDataProcessing {

	@Autowired
	CityRepository repositoryCity;

	@Autowired
	CityStationRepository repositoryCityStation;
	
	@Autowired
	private ParkDataService parkDataService;
	
	@Autowired
	private SimpMessagingTemplate template;

	@StreamListener(ParkDataStreams.INPUT)
	public void transform(@Payload ParkData parkData) {

		// Get data from kafka topic "parkdata"
		final String contents = parkData.getContents();
		JSONObject obj = new JSONObject(contents);
		String contractName = obj.getString("contract_name");
		int stationNumber = obj.getInt("number");
		int availableBikeStands = obj.getInt("available_bike_stands");
		String address = obj.getString("address");
		Double longitude = obj.getJSONObject("position").getDouble("lng");
		Double latitude = obj.getJSONObject("position").getDouble("lat");

		// Check if the db is empty 
		if (repositoryCity.count() == 0) {
			
            // Add the first city item in db
			City newCity = new City();
			newCity.setContractName(contractName);

			CityStation newCityStation = new CityStation();
			newCityStation.setStationNumber(stationNumber);
			newCityStation.setAddress(address);
			newCityStation.setAvailableBikeStands(availableBikeStands);
			newCityStation.setLongitude(longitude);
			newCityStation.setLatitude(latitude);

			List<CityStation> newCityStations = newCity.getCityStations();
			newCityStations.add(newCityStation);
			newCity.setCityStations(newCityStations);
			repositoryCityStation.save(newCityStation);
			
			// Send to websocket topic
			newCity=  repositoryCity.save(newCity); 
            template.convertAndSend("/topic/cities/add", newCity);
			
			// Send the first city to kafka topic "parkdataprocessed"    
	        ParkData parkdata = ParkData.builder()
					.message(newCity.toString())
					.timestamp(System.currentTimeMillis())
					.build();
	  	    parkDataService.sendParkData(parkdata);

		} else {

			City cityExist = repositoryCity.findByContractName(contractName);
			
			// Check if the city exist in db 
			if (cityExist != null) {
				// Get the station list
				List<CityStation> cityStations = cityExist.getCityStations();
				
				boolean stationExist = false;
				for (CityStation cityStat : cityStations) {
					// Check if station number exist
					if (cityStat.getStationNumber() == stationNumber) {
						// Update available bike stands
						if (cityStat.getAvailableBikeStands() != availableBikeStands) {
							
							cityStat.setAvailableBikeStands(availableBikeStands);
							cityStations.set(cityStations.indexOf(cityStat), cityStat);
							cityExist.setCityStations(cityStations);

							repositoryCityStation.save(cityStat);
							repositoryCity.save(cityExist);
						}
						stationExist = true;
					}
				}
				if (stationExist == false) {
					// Add new station in db
					CityStation newCityStation = new CityStation();
					newCityStation.setStationNumber(stationNumber);
					newCityStation.setAddress(address);
					newCityStation.setAvailableBikeStands(availableBikeStands);
					newCityStation.setLongitude(longitude);
					newCityStation.setLatitude(latitude);

					cityStations.add(newCityStation);
					cityExist.setCityStations(cityStations);
					repositoryCityStation.save(newCityStation);
					repositoryCity.save(cityExist);
				}
			} else {
				// Add new city in db 
				City newCity = new City();
				newCity.setContractName(contractName);

				CityStation newCityStation = new CityStation();
				newCityStation.setStationNumber(stationNumber);
				newCityStation.setAddress(address);
				newCityStation.setAvailableBikeStands(availableBikeStands);
				newCityStation.setLongitude(longitude);
				newCityStation.setLatitude(latitude);

				List<CityStation> newCityStations = newCity.getCityStations();
				newCityStations.add(newCityStation);
				newCity.setCityStations(newCityStations);
				repositoryCityStation.save(newCityStation);
				
				// Send to websocket topic
				newCity=  repositoryCity.save(newCity); 
	            template.convertAndSend("/topic/cities/add", newCity);
				
				// Send new city to kafka topic "parkdataprocessed"    
		        ParkData parkdata = ParkData.builder()
						.message(newCity.toString())
						.timestamp(System.currentTimeMillis())
						.build();
		  	    parkDataService.sendParkData(parkdata);

			}
		}
	}
}
