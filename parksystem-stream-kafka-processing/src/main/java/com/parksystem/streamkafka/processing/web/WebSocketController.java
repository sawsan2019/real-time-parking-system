package com.parksystem.streamkafka.processing.web;

import com.parksystem.streamkafka.processing.repository.CityRepository;
import com.parksystem.streamkafka.processing.model.City;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.InitializingBean;

@RestController
public class WebSocketController implements InitializingBean {

			@Autowired
			private SimpMessagingTemplate template;
			
			@Autowired
			private CityRepository repository;

			@MessageMapping("/cities/list")
			@SendTo("/topic/cities/list")
			public List<City> list() throws Exception {
				return repository.findAll();
			}

			@MessageMapping("/cities/{destination}")
			public void webSocketHandler(@DestinationVariable String destination, City city) throws Exception {

				if (destination == null) {
					return;
				}
				
				if ("add".equals(destination)) {
					city = repository.save(city);
				} else
					
					if ("remove".equals(destination)) {
					System.out.println("REMOVING: " + city.getCityId());
					repository.delete((int) city.getCityId());
				}		
				template.convertAndSend("/topic/cities/" + destination, city);
			}

			@RequestMapping(value = "/api/cities/{destination}", method = RequestMethod.POST)
			public void apiHandler(@PathVariable("destination") String destination, @ModelAttribute City city) throws Exception {
				webSocketHandler(destination, city);
			}
			
			@Override
			public void afterPropertiesSet() throws Exception {
				Assert.notNull(template, "Template is null!");
				Assert.notNull(repository, "Repo is null!");
			}
		}