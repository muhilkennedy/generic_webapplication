package com.mken.itinerary.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mken.base.util.Log;
import com.mken.itinerary.entity.Destination;
import com.mken.itinerary.entity.DestinationDetail;
import com.mken.itinerary.entity.DestinationFileBlob;
import com.mken.itinerary.entity.DestinationSeason;
import com.mken.itinerary.entity.ExplorationType;
import com.mken.itinerary.exception.DestinationException;
import com.mken.itinerary.model.DestinationAttributes;
import com.mken.itinerary.model.DestinationTreeObject;
import com.mken.itinerary.model.SeasonRequest;
import com.mken.itinerary.service.DestinationService;
import com.platform.messages.StoreType;
import com.platform.service.StorageService;
import com.platform.util.ImageUtil;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
@Service
public class DestinationServiceImpl implements DestinationService {

	@Autowired
	private DestinationDaoService destinationDao;

	@Override
	public Destination createDestination(Destination destination) {
		return (Destination) destinationDao.save(destination);
	}

	@Override
	public List<?> getAllDestinations() {
		return destinationDao.findAll();
	}

	@Override
	public Flux<?> getAllDestinationsReactive() {
		return destinationDao.findAllReactive();
	}
	
	@Override
	public void deleteDestination(Long rootId) {
		destinationDao.deleteById(rootId);
	}
	
	@Override
	public Destination getDestinationById(Long rootId) {
		return (Destination) destinationDao.findById(rootId);
	}

	@Override
	public Destination updateDestinationPicture(Long destinationId, File file)
			throws IOException, DestinationException {
		Destination destination = (Destination) destinationDao.findById(destinationId);
		if (destination == null) {
			throw new DestinationException("Destination not found");
		}
		String picUrl = null;
		try {
			picUrl = StorageService.getStorage().saveFile(ImageUtil.compressImageForBanner(file),
					Destination.class.getSimpleName());
		} catch (Exception e) {
			Log.itinerary.error("Failed to process destination file : {}", e.getMessage());
			throw new DestinationException(e.getMessage());
		}
		destination.setPicture(picUrl);
		return (Destination) destinationDao.save(destination);
	}

	@Override
	public Destination createSeasonInfoForDestination(Long destinationId,
			List<SeasonRequest> destinationSeasonRequests) throws DestinationException {
		Destination destination = (Destination) destinationDao.findById(destinationId);
		if (destination == null) {
			throw new DestinationException("Destination not found");
		}
		List<DestinationSeason> seasons = new ArrayList();
		destinationSeasonRequests.forEach(request -> {
			DestinationSeason ds = destinationDao.findDestinationSeasonForMonth(destination.getRootId(),
					request.getMonth());
			if (ds != null) {
				ds.setScale(request.getScale());
			} else {
				ds = new DestinationSeason();
				ds.setDestination(destination);
				ds.setMonth(request.getMonth());
				ds.setScale(request.getScale());
			}
			//ds = destinationDao.save(ds);
			seasons.add(ds);
		});
		destination.setDestinationSeasons(seasons);
		return (Destination) destinationDao.saveAndFlush(destination);
	}

	@Override
	public DestinationSeason updateSeasonDetail(Long SeasonId, int value) throws DestinationException {
		DestinationSeason season = destinationDao.findDestSeasonById(SeasonId);
		if (season == null) {
			throw new DestinationException("Destination not found");
		}
		season.setScale(value);
		return destinationDao.save(season);
	}
	
	@Override
	public Destination uploadDestinationPictures(Long destinationId, List<File> files) throws DestinationException {
		Destination destination = getDestinationById(destinationId);
		if (destination == null) {
			throw new DestinationException("Destination not found");
		}
		List<DestinationFileBlob> fileBlobs = new ArrayList<>();
		for (File picture : files) {
			DestinationFileBlob dfb = new DestinationFileBlob();
			String picUrl = null;
			try {
				picUrl = StorageService.getStorage().saveFile(ImageUtil.compressImageForBanner(picture),
						DestinationFileBlob.class.getSimpleName());
			} catch (Exception e) {
				Log.itinerary.error("Failed to process destination file : {}", e.getMessage());
				throw new DestinationException(e.getMessage());
			}
			dfb.setDestination(destination);
			dfb.setMediaurl(picUrl);
			dfb.setStoretype(StoreType.GCP.name());
		}
		destination.setDestinationFileBlobs(fileBlobs);
		return (Destination) destinationDao.saveAndFlush(destination);
	}
	
	@Override
	public DestinationDetail createDestinationDetail(Long destinationId, DestinationAttributes attributes)
			throws DestinationException {
		Destination destination = getDestinationById(destinationId);
		if (destination == null) {
			throw new DestinationException("Destination not found");
		}
		DestinationDetail detail = new DestinationDetail();
		if(destination.getDestinationDetail() != null) {
			destination.getDestinationDetail().setAttributes(attributes);
			detail = destinationDao.saveAndFlush(destination.getDestinationDetail());
		}
		else {
			detail.setAttributes(attributes);
			detail.setDestination(destination);
			destination.setDestinationDetail(detail);
		}
		destination.setDestinationDetail(detail);
		destinationDao.saveAndFlush(destination);
		return detail;
	}
	
	@Override
	public List<Destination> getAllChildrenDestinations(Long parentId){
		return destinationDao.findAllChildrenForDestination(parentId);
	}
	
	@Override
	public List<Long> getAllChildrenIdForDestination(Long parentId){
		return destinationDao.findAllChildrenIdsForDestination(parentId);
	}
	
	@Override
	public Map<?,?> getAllDestinationsRecursive(){
		List<Long> baseDestinations = destinationDao.findAllBaseDestinationsIds();
		return getExplodedDestinations(baseDestinations);
	}
	
	private Map<?, ?> getExplodedDestinations(List<Long> destinationIds){
		Map map = new HashMap<>();
		for(Long destId : destinationIds) {
			Destination dest = getDestinationById(destId);
			map.put(dest.getName() , getExplodedDestinations(getAllChildrenIdForDestination(dest.getRootId())));
		}
		return (map.size()) > 0 ? map : null;
	}
	
	@Override
	public List<DestinationTreeObject> getAllDestinationsHierarchyRecursive(){
		List<Long> baseDestinations = destinationDao.findAllBaseDestinationsIds();
		return generateDestinationTreeHierarchy(baseDestinations);
	}
	
	private List<DestinationTreeObject> generateDestinationTreeHierarchy(List<Long> destinationIds){
		List<DestinationTreeObject> list = new ArrayList<>();
		for(Long destId : destinationIds) {
			Destination dest = getDestinationById(destId);
			DestinationTreeObject obj = new DestinationTreeObject();
			obj.setName(dest.getName());
			obj.setRootId(dest.getRootId());
			obj.setChildren(generateDestinationTreeHierarchy(getAllChildrenIdForDestination(dest.getRootId())));
			list.add(obj);
		}
		return list;
	}
	
	@Override
	public Destination updateDestinationDetail(Long destinationId, DestinationAttributes attributes)
			throws DestinationException {
		Destination destination = getDestinationById(destinationId);
		if (destination == null) {
			throw new DestinationException();
		}
		DestinationDetail detail = new DestinationDetail();
		detail.setAttributes(attributes);
		detail.setDestination(destination);
		destination.setDestinationDetail(detail);
		return (Destination) destinationDao.save(destination);
	}
	
	@Override
	public List<ExplorationType> getAllExploreTypes() {
		return destinationDao.findAllExplorationTypes();
	}

}
