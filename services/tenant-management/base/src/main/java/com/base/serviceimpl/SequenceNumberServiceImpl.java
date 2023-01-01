package com.base.serviceimpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.SequenceNumberRepository;
import com.base.entity.BaseObject;
import com.base.entity.SequenceNumber;
import com.base.service.SequenceNumberService;

@Service
@Transactional
public class SequenceNumberServiceImpl implements SequenceNumberService {
	
	@Autowired
	private SequenceNumberRepository sequenceNumberRepo;
	
	@Override
	public Object save(BaseObject obj) {
		return sequenceNumberRepo.save((SequenceNumber)obj);
	}

	@Override
	public Object saveAndFlush(BaseObject obj) {
		return sequenceNumberRepo.saveAndFlush((SequenceNumber)obj);
	}

	@Override
	public Object findById(Object rootId) {
		return sequenceNumberRepo.findById((Long) rootId);
	}
	
	@Override
	public SequenceNumber getSequence(String className) {
		return sequenceNumberRepo.findSequenceByClassName(className);
	}
	
	@Override
	public SequenceNumber createSequence(String className) {
		SequenceNumber sequenceNumber = new SequenceNumber();
		sequenceNumber.setClassName(className);
		sequenceNumber.setIncrementValue(1);
		sequenceNumber.setNextValue(1);
		return (SequenceNumber) saveAndFlush(sequenceNumber);
	}

	@Override
	public void delete(BaseObject obj) {
		sequenceNumberRepo.delete((SequenceNumber) obj);
	}	

}
