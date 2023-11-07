package com.mken.itinerary.api;

import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mken.itinerary.entity.Destination;
import com.mken.itinerary.entity.ExplorationType;
import com.mken.itinerary.model.DestinationTreeObject;
import com.mken.itinerary.service.DestinationService;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("destination")
public class DestinationController {

	@Autowired
	private DestinationService destinationService;
	
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Destination> getAllDestinations() {
		GenericResponse<Destination> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setDataList(destinationService.getAllDestinations()).build();
	}
	
	@GetMapping(value = "/fetch", produces = org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Destination> getAllDestinationsReactive() {
		return (Flux<Destination>) destinationService.getAllDestinationsReactive();
	}
	
	@GetMapping(value = "/hierarchy", produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Map> getAllDestinationsHierarchy() {
		GenericResponse<Map> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(destinationService.getAllDestinationsRecursive()).build();
	}
	
	@GetMapping(value = "/tree", produces = MediaType.APPLICATION_JSON)
	public GenericResponse<DestinationTreeObject> getAllDestinationsTreeHierarchy() {
		GenericResponse<DestinationTreeObject> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK)
				.setDataList(destinationService.getAllDestinationsHierarchyRecursive()).build();
	}
	
	@GetMapping(value = "/exploretypes", produces = MediaType.APPLICATION_JSON)
	public GenericResponse<ExplorationType> getAllExplorationTypes() {
		GenericResponse<ExplorationType> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setDataList(destinationService.getAllExploreTypes()).build();
	}
}
