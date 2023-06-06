package com.mken.itinerary.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mken.base.email.service.BaseDaoService;
import com.mken.base.entity.BaseEntity;
import com.mken.base.util.CacheUtil;
import com.mken.base.util.Log;
import com.mken.itinerary.entity.Destination;
import com.mken.itinerary.entity.DestinationDetail;
import com.mken.itinerary.entity.DestinationFileBlob;
import com.mken.itinerary.entity.DestinationSeason;
import com.mken.itinerary.entity.ExplorationType;
import com.mken.itinerary.jpa.repo.DestinationDetailRepository;
import com.mken.itinerary.jpa.repo.DestinationFileBlobRepository;
import com.mken.itinerary.jpa.repo.DestinationRepository;
import com.mken.itinerary.jpa.repo.DestinationSeasonRespository;
import com.mken.itinerary.jpa.repo.ExplorationtypeRepository;
import com.mken.itinerary.r2db.repo.DestinationR2Repository;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
@Service
@Qualifier("DestinationDao")
public class DestinationDaoService implements BaseDaoService {
	
	public static final String KEY_EXPLORE_TYPES = "explore-types";

	@Autowired
	private DestinationRepository destinationRepo;

	@Autowired
	private DestinationR2Repository destinationR2Repo;

	@Autowired
	private DestinationFileBlobRepository destinationFileBlobRepo;

	@Autowired
	private DestinationSeasonRespository destinationSeasonsRepo;
	
	@Autowired
	private DestinationDetailRepository destinationDetailRepo;
	
	@Autowired
	private ExplorationtypeRepository explorationRepo;

	@Autowired
	private CacheManager cacheManager;

	@Override
	@CachePut(value = CacheUtil.DESTINATION_CACHE_NAME, key = "#obj.rootId")
	public BaseEntity save(BaseEntity obj) {
		return destinationRepo.save((Destination) obj);
	}

	@Override
	@CachePut(value = CacheUtil.DESTINATION_CACHE_NAME, key = "#obj.rootId")
	public BaseEntity saveAndFlush(BaseEntity obj) {
		return destinationRepo.saveAndFlush((Destination) obj);
	}

	@Override
	@Cacheable(value = CacheUtil.DESTINATION_CACHE_NAME, key = "#rootId")
	public BaseEntity findById(Long rootId) {
		return destinationRepo.findById(rootId).get();
	}

	@Override
	@CacheEvict(value = CacheUtil.DESTINATION_CACHE_NAME, key = "#obj.rootId")
	public void delete(BaseEntity obj) {
		destinationRepo.delete((Destination) obj);
	}
	
	@Override
	@CacheEvict(value = CacheUtil.DESTINATION_CACHE_NAME, key = "#obj.rootId")
	public void deleteById(Long rootId) {
		destinationRepo.deleteById(rootId);
	}

	public synchronized void clearDestinationCache() {
		if (cacheManager.getCache(CacheUtil.DESTINATION_CACHE_NAME) != null) {
			cacheManager.getCache(CacheUtil.DESTINATION_CACHE_NAME).clear();
			Log.itinerary.warn("{} Cache cleared", CacheUtil.DESTINATION_CACHE_NAME);
		}
	}
	
	public synchronized void clearDestinationCache(Long DestinationId) {
		if (cacheManager.getCache(CacheUtil.DESTINATION_CACHE_NAME) != null) {
			cacheManager.getCache(CacheUtil.DESTINATION_CACHE_NAME).evictIfPresent(DestinationId);
			Log.itinerary.warn("{} Cache cleared for Destination - {}", CacheUtil.DESTINATION_CACHE_NAME,
					DestinationId);
		}
	}

	@Override
	public List<?> findAll() {
		return destinationRepo.findAll();
	}

	@Override
	public Flux<?> findAllReactive() {
		return destinationR2Repo.findAll();
	}
	
	public List<Destination> findAllBaseDestinations() {
		return destinationRepo.findAllParentDestination();
	}

	public List<Destination> findAllChildrenForDestination(Long parentRootId) {
		return destinationRepo.findAllChildrenForDestination(parentRootId);
	}
	
	public List<Long> findAllBaseDestinationsIds() {
		return destinationRepo.findAllParentIdsDestination();
	}

	public List<Long> findAllChildrenIdsForDestination(Long parentRootId) {
		return destinationRepo.findAllChildrenIdsForDestination(parentRootId);
	}

	/*********************
	 * DestinationFileBlob
	 ********************/

	public DestinationFileBlob save(DestinationFileBlob dfb) {
		clearDestinationCache(dfb.getDestination().getRootId());
		return destinationFileBlobRepo.save(dfb);
	}

	public DestinationFileBlob saveAndFlush(DestinationFileBlob dfb) {
		clearDestinationCache(dfb.getDestination().getRootId());
		return destinationFileBlobRepo.saveAndFlush(dfb);
	}

	public void delete(DestinationFileBlob dfb) {
		clearDestinationCache(dfb.getDestination().getRootId());
		destinationFileBlobRepo.delete(dfb);
	}

	public DestinationFileBlob findDestFileBlobById(Long rootId) {
		return destinationFileBlobRepo.findById(rootId).get();
	}

	/*********************
	 * DestinationSeason
	 ********************/

	public DestinationSeason save(DestinationSeason ds) {
		clearDestinationCache(ds.getDestination().getRootId());
		return destinationSeasonsRepo.save(ds);
	}

	public DestinationSeason saveAndFlush(DestinationSeason ds) {
		clearDestinationCache(ds.getDestination().getRootId());
		return destinationSeasonsRepo.saveAndFlush(ds);
	}

	public void delete(DestinationSeason ds) {
		clearDestinationCache(ds.getDestination().getRootId());
		destinationSeasonsRepo.delete(ds);
	}

	public DestinationSeason findDestSeasonById (Long rootId) {
		return destinationSeasonsRepo.findById(rootId).get();
	}
	
	public DestinationSeason findDestinationSeasonForMonth(Long destinationId, int month) {
		return destinationSeasonsRepo.findSeasonForMonth(destinationId, month);
	}
	
	/*********************
	 * DestinationDetail
	 ********************/

	public DestinationDetail save(DestinationDetail dd) {
		clearDestinationCache(dd.getDestination().getRootId());
		return destinationDetailRepo.save(dd);
	}

	public DestinationDetail saveAndFlush(DestinationDetail dd) {
		clearDestinationCache();
		return destinationDetailRepo.saveAndFlush(dd);
	}

	public void delete(DestinationDetail dd) {
		clearDestinationCache(dd.getDestination().getRootId());
		destinationDetailRepo.delete(dd);
	}

	public DestinationDetail findDestDetailById(Long rootId) {
		return destinationDetailRepo.findById(rootId).get();
	}
	
	/*********************
	 * Exploration Type
	 ********************/
	@Cacheable(value = CacheUtil.DESTINATION_CACHE_NAME, key = "#root.target.KEY_EXPLORE_TYPES")
	public List<ExplorationType> findAllExplorationTypes() {
		return explorationRepo.findAll();
	}

}
