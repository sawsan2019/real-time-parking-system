package com.parksystem.streamkafka.processing.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = "com.parksystem.streamkafka.processing.repository")
@PropertySource("classpath:application.yaml")
public class MongoConfig extends AbstractMongoConfiguration {
 
 @Autowired
 private Environment env;

 @Override
 protected String getDatabaseName() {
 return env.getProperty("mongo.database");
 }

 @Override
 public Mongo mongo() throws Exception {
 return new MongoClient(env.getProperty("mongo.host"), Integer.parseInt(env.getProperty("mongo.port")));
 }
 
 @Override
 protected String getMappingBasePackage() {
 return "com.parksystem.streamkafka.processing.model";
 }

}