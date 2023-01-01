package com.base.service;

import com.base.entity.SequenceNumber;

public interface SequenceNumberService extends BaseService {

	SequenceNumber getSequence(String className);

	SequenceNumber createSequence(String className);

}
