package com.base.hiberbate.config;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.springframework.beans.factory.annotation.Autowired;

import com.base.configuration.AutowireHelper;
import com.base.entity.MultiTenantEntity;
import com.base.service.BaseSession;

/**
 * @author Muhil Kennedy
 * perform pre perist multitenant operations.
 */
public class TenantEntityListener {

	@Autowired
	private BaseSession baseSession;
	
	@PrePersist
    private void prePersist(Object object) {
		//baseSession will be null as entity listeners are not managed by spring containers
        if(object instanceof MultiTenantEntity){
        	AutowireHelper.autowire(this, this.baseSession);
        	MultiTenantEntity entity = (MultiTenantEntity)object;
            entity.setTenantId(baseSession.getTenantId());
            if (entity.getTimeCreated() == null) {
            	entity.setTimeCreated(System.currentTimeMillis());
    		}
            entity.setTimeUpdated(System.currentTimeMillis());
    		if(entity.getModifiedBy() == null) {
    			entity.setModifiedBy(getModifiedUser());
    		}
        }
    }
	
	@PreUpdate
	private void preUpdate(Object object) {
        if(object instanceof MultiTenantEntity){
        	AutowireHelper.autowire(this, this.baseSession);
        	MultiTenantEntity entity = (MultiTenantEntity)object;
            entity.setTenantId(baseSession.getTenantId());
            entity.setTimeUpdated(System.currentTimeMillis());
    		if(entity.getModifiedBy() == null) {
    			entity.setModifiedBy(getModifiedUser());
    		}
        }
    }
	
	@PreRemove
	private void preRemove(Object object) {
        if(object instanceof MultiTenantEntity){
        	AutowireHelper.autowire(this, this.baseSession);
        	MultiTenantEntity entity = (MultiTenantEntity)object;
            entity.setTenantId(baseSession.getTenantId());
        }
    }
	
	private String getModifiedUser(){
		if(baseSession.getUserInfo() == null) {
			return "SYSTEM";
		}
		return "SYSTEM";
		//return baseSession.getUserInfo();
	}

}