package com.parksystem.streamkafka.producer.web;

import com.parksystem.streamkafka.producer.model.ParkData;
import com.parksystem.streamkafka.producer.service.ParkDataService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ParkDataController {

	@Autowired
	private ParkDataService parkdataService;

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(ParkDataController.class);

	// Scheduling 10s
	@Scheduled(fixedRate = 10000)
	public void reportCurrentTime() throws JSONException {
		{
			HttpURLConnection connection = null;
			String inputLine;
			StringBuffer response = new StringBuffer();

			try {
				// Get apiKey from https://developer.jcdecaux.com/#/signup
				URL url = new URL(
						"https://api.jcdecaux.com/vls/v1/stations?apiKey=XXXX");
				// Configure Proxy
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 3128));
				// Make connection
				connection = (HttpURLConnection) url.openConnection(proxy);
				// Set request type as HTTP GET
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setUseCaches(false);
				connection.setDoOutput(true);
				if (connection.getResponseCode() == 200) {
					// Get response stream
					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					// Feed response into the StringBuilder
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					in.close();
					// Start parsing
					JSONArray obj = new JSONArray(response.toString());
					// Get Array type
					for (int i = 0; i < obj.length(); i++) {

						JSONObject results = obj.getJSONObject(i);
						// Under results, get string type
						String message = results.toString();
						// Under results, get double type

						// System.out.println(message);

						ParkData parkData = ParkData.builder().message(message).timestamp(System.currentTimeMillis())
								.build();

						parkdataService.sendParkData(parkData);
						log.info("the data stored{}", parkData);

					}
				} else {
					System.out.println("Can't get data");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
