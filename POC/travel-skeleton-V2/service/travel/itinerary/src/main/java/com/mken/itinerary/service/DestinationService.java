package com.mken.itinerary.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.mken.itinerary.entity.Destination;
import com.mken.itinerary.entity.DestinationDetail;
import com.mken.itinerary.entity.DestinationSeason;
import com.mken.itinerary.exception.DestinationException;
import com.mken.itinerary.model.DestinationAttributes;
import com.mken.itinerary.model.DestinationTreeObject;
import com.mken.itinerary.model.SeasonRequest;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
public interface DestinationService {

	Destination createDestination(Destination destination);
	
	List<?> getAllDestinations();
	
	Flux<?> getAllDestinationsReactive();

	Destination updateDestinationPicture(Long destinationId, File file) throws IOException, DestinationException;

	Destination createSeasonInfoForDestination(Long destinationId,
			List<SeasonRequest> destinationSeasonRequests) throws DestinationException;

	DestinationSeason updateSeasonDetail(Long SeasonId, int value) throws DestinationException;

	Destination getDestinationById(Long rootId);

	Destination uploadDestinationPictures(Long destinationId, List<File> files) throws DestinationException;

	DestinationDetail createDestinationDetail(Long destinationId, DestinationAttributes attributes)
			throws DestinationException;

	void deleteDestination(Long rootId);

	Map<?,?> getAllDestinationsRecursive();

	List<Destination> getAllChildrenDestinations(Long parentId);

	List<Long> getAllChildrenIdForDestination(Long parentId);

	List<DestinationTreeObject> getAllDestinationsHierarchyRecursive();

}
