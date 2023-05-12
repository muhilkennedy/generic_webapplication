package com.mken.itinerary.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.mken.itinerary.entity.Attraction;
import com.mken.itinerary.entity.AttractionSeason;
import com.mken.itinerary.entity.Destination;
import com.mken.itinerary.exception.AttractionException;
import com.mken.itinerary.model.SeasonRequest;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
public interface AttractionService {

	Attraction updateAttractionPicture(Long attractionId, File picture) throws AttractionException, IOException, Exception;

	Flux<?> getAllAttractionsReactive();

	List<?> getAllAttractions();

	Attraction createAttraction(Attraction destination);

	Destination createSeasonInfoForAttraction(Long attractionId, List<SeasonRequest> seasonsRequest)
			throws AttractionException;

	AttractionSeason updateSeasonDetail(Long SeasonId, int value) throws AttractionException;

	List<Attraction> getAllAttractionsForDestination(Long destinationId);

	Flux<Attraction> getAllAttractionsForDestinationReactive(Long destinationId);

	Attraction updateAttractionPicture(Long attractionId, List<File> picture) throws AttractionException;

	Attraction getAttractionById(Long attractionId);

}
