package com.parksystem.streamkafka.processing.model;
	
import lombok.Builder;	
import lombok.Getter;	
import lombok.Setter;	
import lombok.ToString;
	
@Getter @Setter @ToString @Builder
public class ParkData {	
	  private long timestamp;
	  private String message;
	  
	  public ParkData() {

	    }

	    public ParkData(long timestamp, String message) {
	        this.timestamp = timestamp;
	        this.message = message;
	    }

	    public long getTime() {
	        return timestamp;
	    }

	    public String getContents() {
	        return message;
	    }
	}
	

	
	