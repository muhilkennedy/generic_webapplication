package com.base.generator;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.base.configuration.AutowireHelper;
import com.base.entity.SequenceNumber;
import com.base.service.SequenceNumberService;
import com.base.util.AnnotationUtil;
import com.base.util.Constants;

/**
 * @author Muhil Kennedy
 * Generate custom rootId based on class name/code appended with sequence number for each tenant.
 */
@Component
public class RootIdGenerator implements IdentifierGenerator {
	
	@Autowired
	private SequenceNumberService sequenceNumberService;
	
	@Override
	public synchronized Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {		
		AutowireHelper.autowire(this, this.sequenceNumberService);
		String classCode = AnnotationUtil.getClassMetaCode(object);
		SequenceNumber sequence = sequenceNumberService.getSequence(classCode);
		if (sequence == null) {
			sequence = sequenceNumberService.createSequence(classCode);
		}
		Assert.isTrue(sequence != null, "Something is wrong with rootId generation!");
		long nextValue = sequence.getNextValue();
		sequence.setNextValue(sequence.getNextValue() + sequence.getIncrementValue());
		sequenceNumberService.save(sequence);
		return sequence.getTenantId() + Constants.COLON_SEPERATOR + classCode + Constants.COLON_SEPERATOR + nextValue;
	}

}
