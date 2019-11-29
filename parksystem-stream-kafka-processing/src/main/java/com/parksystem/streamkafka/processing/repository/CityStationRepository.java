package com.parksystem.streamkafka.processing.repository;

import com.parksystem.streamkafka.processing.model.CityStation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CityStationRepository extends MongoRepository<CityStation,Integer>  {
	
	public CityStation findByStationNumber(int stationNumber);

}
