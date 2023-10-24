package com.mken.itinerary.serviceimpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mken.base.util.Log;
import com.mken.base.util.StorageUtil;
import com.mken.itinerary.entity.Attraction;
import com.mken.itinerary.entity.AttractionFileBlob;
import com.mken.itinerary.entity.AttractionSeason;
import com.mken.itinerary.entity.Destination;
import com.mken.itinerary.entity.DestinationFileBlob;
import com.mken.itinerary.exception.AttractionException;
import com.mken.itinerary.exception.DestinationException;
import com.mken.itinerary.model.SeasonRequest;
import com.mken.itinerary.service.AttractionService;
import com.platform.messages.StoreType;
import com.platform.service.StorageService;
import com.platform.util.ImageUtil;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
@Service
public class AttractionServiceImpl implements AttractionService {

	@Autowired
	private AttractionDaoService attractionDaoService;

	@Override
	public Attraction createAttraction(Attraction destination) {
		return (Attraction) attractionDaoService.save(destination);
	}

	@Override
	public List<?> getAllAttractions() {
		return attractionDaoService.findAll();
	}

	@Override
	public Flux<?> getAllAttractionsReactive() {
		return attractionDaoService.findAllReactive();
	}
	
	@Override
	public List<Attraction> getAllAttractionsForDestination(Long destinationId) {
		return attractionDaoService.findAllAttractionForDestination(destinationId);
	}
	
	@Override
	public Flux<Attraction> getAllAttractionsForDestinationReactive(Long destinationId) {
		return attractionDaoService.findAllAttractionForDestinationReactive(destinationId);
	}
	
	@Override
	public Attraction getAttractionById(Long attractionId) {
		return (Attraction) attractionDaoService.findById(attractionId);
	}

	@Override
	public Attraction updateAttractionPicture(Long attractionId, File picture) throws AttractionException  {
		Attraction attraction = (Attraction) attractionDaoService.findById(attractionId);
		if (attraction == null) {
			throw new AttractionException("Tourist Attraction not found");
		}
		String picUrl = null;
		try {
			picUrl = StorageService.getStorage().saveFile(ImageUtil.compressImageForBanner(picture),
					StorageUtil.GCS_ATTRACTIONS_PATH);
		} catch (Exception e) {
			Log.itinerary.error("Error Processing attraction picture : {}", e.getMessage());
			throw new AttractionException(e.getMessage());
		}
		attraction.setPicture(picUrl);
		return (Attraction) attractionDaoService.save(attraction);
	}
	
	@Override
	public Attraction updateAttractionPicture(Long attractionId, List<File> files) throws AttractionException  {
		Attraction attraction = (Attraction) attractionDaoService.findById(attractionId);
		if (attraction == null) {
			throw new AttractionException("Tourist Attraction not found");
		}
		
		List<AttractionFileBlob> fileBlobs = new ArrayList<>();
		for (File picture : files) {
			AttractionFileBlob afb = new AttractionFileBlob();
			String picUrl = null;
			try {
				picUrl = StorageService.getStorage().saveFile(ImageUtil.compressImageForBanner(picture),
						AttractionFileBlob.class.getSimpleName());
			} catch (Exception e) {
				Log.itinerary.error("Failed to process attraction file : {}", e.getMessage());
				throw new AttractionException(e.getMessage());
			}
			afb.setAttraction(attraction);
			afb.setMediaurl(picUrl);
			afb.setStoretype(StoreType.GCP.name());
		}
		attraction.setAttractionFileBlobs(fileBlobs);
		return (Attraction) attractionDaoService.save(attraction);
	}

	@Override
	public Destination createSeasonInfoForAttraction(Long attractionId, List<SeasonRequest> seasonsRequest)
			throws AttractionException {
		Attraction attraction = (Attraction) attractionDaoService.findById(attractionId);
		if (attraction == null) {
			throw new AttractionException("Tourist Attraction not found");
		}
		List<AttractionSeason> dstSeasons = new ArrayList<AttractionSeason>();
		seasonsRequest.forEach(request -> {
			AttractionSeason ds = new AttractionSeason();
			ds.setAttraction(attraction);
			ds.setMonth(request.getMonth());
			ds.setScale(request.getScale());
			ds = attractionDaoService.saveAndFlush(ds);
			dstSeasons.add(ds);
		});
		return (Destination) attractionDaoService.findById(attraction.getRootId());
	}

	@Override
	public AttractionSeason updateSeasonDetail(Long SeasonId, int value) throws AttractionException {
		AttractionSeason season = attractionDaoService.findAttractionSeasonById(SeasonId);
		if (season == null) {
			throw new AttractionException("Tourist Attraction not found");
		}
		season.setScale(value);
		return attractionDaoService.save(season);
	}

}
