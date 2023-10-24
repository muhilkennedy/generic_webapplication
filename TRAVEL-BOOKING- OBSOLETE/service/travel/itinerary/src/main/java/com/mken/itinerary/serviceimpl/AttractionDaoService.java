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
import com.mken.itinerary.entity.Attraction;
import com.mken.itinerary.entity.AttractionFileBlob;
import com.mken.itinerary.entity.AttractionSeason;
import com.mken.itinerary.jpa.repo.AttractionFileBlobRepository;
import com.mken.itinerary.jpa.repo.AttractionRepository;
import com.mken.itinerary.jpa.repo.AttractionSeasonRepository;
import com.mken.itinerary.r2db.repo.AttractionR2Repository;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
@Service
@Qualifier("AtrractionDao")
public class AttractionDaoService implements BaseDaoService {

	@Autowired
	private AttractionRepository attractionRepo;

	@Autowired
	private AttractionR2Repository attractionR2Repo;

	@Autowired
	private AttractionSeasonRepository attractionSeasonRepo;

	@Autowired
	private AttractionFileBlobRepository attractionFBRepo;

	@Autowired
	private CacheManager cacheManager;

	@Override
	@CachePut(value = CacheUtil.ATTRACTION_CACHE_NAME, key = "#obj.rootId")
	public BaseEntity save(BaseEntity obj) {
		return attractionRepo.save((Attraction) obj);
	}

	@Override
	@CachePut(value = CacheUtil.ATTRACTION_CACHE_NAME, key = "#obj.rootId")
	public BaseEntity saveAndFlush(BaseEntity obj) {
		return attractionRepo.saveAndFlush((Attraction) obj);
	}

	@Override
	@Cacheable(value = CacheUtil.ATTRACTION_CACHE_NAME, key = "#rootId")
	public BaseEntity findById(Long rootId) {
		return attractionRepo.findById(rootId).get();
	}

	@Override
	@CacheEvict(value = CacheUtil.ATTRACTION_CACHE_NAME, key = "#obj.rootId")
	public void delete(BaseEntity obj) {
		attractionRepo.delete((Attraction) obj);
	}
	
	@Override
	@CacheEvict(value = CacheUtil.ATTRACTION_CACHE_NAME, key = "#obj.rootId")
	public void deleteById(Long rootId) {
		attractionRepo.deleteById(rootId);
		
	}

	@Override
	public List<?> findAll() {
		return attractionRepo.findAll();
	}

	@Override
	public Flux<?> findAllReactive() {
		return attractionR2Repo.findAll();
	}
	
	public List<Attraction> findAllAttractionForDestination(Long rootId) {
		return attractionRepo.findAllAttractionsForParent(rootId);
	}
	
	public Flux<Attraction> findAllAttractionForDestinationReactive(Long rootId) {
		return attractionR2Repo.findAllAttractionsForParent(rootId);
	}

	public synchronized void clearAttractionCache() {
		if (cacheManager.getCache(CacheUtil.ATTRACTION_CACHE_NAME) != null) {
			cacheManager.getCache(CacheUtil.ATTRACTION_CACHE_NAME).clear();
			Log.itinerary.warn("{} Cache cleared", CacheUtil.ATTRACTION_CACHE_NAME);
		}
	}
	
	/*********************
	 * AttractionFileBlob
	 ********************/

	public AttractionFileBlob save(AttractionFileBlob dfb) {
		clearAttractionCache();
		return attractionFBRepo.save(dfb);
	}

	public AttractionFileBlob saveAndFlush(AttractionFileBlob dfb) {
		clearAttractionCache();
		return attractionFBRepo.saveAndFlush(dfb);
	}

	public void delete(AttractionFileBlob dfb) {
		clearAttractionCache();
		attractionFBRepo.delete(dfb);
	}

	public AttractionFileBlob findAttractionFileBlobById(Long rootId) {
		return attractionFBRepo.findById(rootId).get();
	}

	/*********************
	 * AttractionSeasons
	 ********************/

	public AttractionSeason save(AttractionSeason dfb) {
		clearAttractionCache();
		return attractionSeasonRepo.save(dfb);
	}

	public AttractionSeason saveAndFlush(AttractionSeason dfb) {
		clearAttractionCache();
		return attractionSeasonRepo.saveAndFlush(dfb);
	}

	public void delete(AttractionSeason dfb) {
		clearAttractionCache();
		attractionSeasonRepo.delete(dfb);
	}

	public AttractionSeason findAttractionSeasonById(Long rootId) {
		return attractionSeasonRepo.findById(rootId).get();
	}
	
}
