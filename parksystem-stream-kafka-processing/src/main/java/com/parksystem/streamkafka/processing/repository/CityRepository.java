package com.parksystem.streamkafka.processing.repository;

import com.parksystem.streamkafka.processing.model.City;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CityRepository extends MongoRepository<City,Integer>  {
	
	public City findByContractName(String contractName);
	

}
