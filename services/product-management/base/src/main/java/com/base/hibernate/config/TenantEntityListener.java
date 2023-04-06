package com.base.hibernate.config;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.springframework.beans.factory.annotation.Autowired;

import com.base.configuration.AutowireHelper;
import com.base.entity.MultiTenantEntity;
import com.platform.session.BaseSession;
import com.platform.util.PlatformUtil;

/**
 * @author Muhil Kennedy
 * perform pre perist multitenant operations.
 */
public class TenantEntityListener {
	
	@PrePersist
    private void prePersist(Object object) {
		//baseSession will be null as entity listeners are not managed by spring containers
        if(object instanceof MultiTenantEntity){
        	//AutowireHelper.autowire(this, this.baseSession);
        	MultiTenantEntity entity = (MultiTenantEntity)object;
            entity.setTenantId(BaseSession.getTenantId());
            if (entity.getTimeCreated() == 0L) {
            	entity.setTimeCreated(System.currentTimeMillis());
    		}
            entity.setTimeUpdated(System.currentTimeMillis());
    		if(entity.getModifiedBy() == null) {
    			entity.setModifiedBy(getCurrentUser());
    			entity.setCreatedBy(getCurrentUser());
    		}
        }
    }
	
	@PreUpdate
	private void preUpdate(Object object) {
        if(object instanceof MultiTenantEntity){
        	//AutowireHelper.autowire(this, this.baseSession);
        	MultiTenantEntity entity = (MultiTenantEntity)object;
            entity.setTenantId(BaseSession.getTenantId());
            entity.setTimeUpdated(System.currentTimeMillis());
    		if(entity.getModifiedBy() == null) {
    			entity.setModifiedBy(getCurrentUser());
    		}
    		entity.setVersion(entity.getVersion()+1);
        }
    }
	
	@PreRemove
	private void preRemove(Object object) {
        if(object instanceof MultiTenantEntity){
        	//AutowireHelper.autowire(this, this.baseSession);
        	MultiTenantEntity entity = (MultiTenantEntity)object;
            entity.setTenantId(BaseSession.getTenantId());
        }
    }
	
	private String getCurrentUser() {
		if (BaseSession.getUser() == null) {
			return PlatformUtil.DEFAULT_USER_ID;
		}
		return BaseSession.getUser().getObjectId();
	}

}
