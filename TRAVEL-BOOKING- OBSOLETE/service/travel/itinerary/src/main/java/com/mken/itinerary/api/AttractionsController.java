package com.mken.itinerary.api;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mken.itinerary.entity.Attraction;
import com.mken.itinerary.service.AttractionService;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("/attraction")
public class AttractionsController {

	@Autowired
	private AttractionService attractionService;

	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Attraction> getAllDestinations(
			@RequestParam(name = "destinationId", required = false) Long destinationId) {
		GenericResponse<Attraction> response = new GenericResponse<>();
		if (destinationId != null) {
			return response.setStatus(Response.Status.OK)
					.setDataList(attractionService.getAllAttractionsForDestination(destinationId)).build();
		}
		return response.setStatus(Response.Status.OK).setDataList(attractionService.getAllAttractions()).build();
	}

	@GetMapping(value = "/fetch", produces = org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Attraction> getAllDestinationsReactive(
			@RequestParam(name = "destinationId", required = false) Long destinationId) {
		if (destinationId != null) {
			return (Flux<Attraction>) attractionService.getAllAttractionsForDestinationReactive(destinationId);
		}
		return (Flux<Attraction>) attractionService.getAllAttractionsReactive();
	}
}
