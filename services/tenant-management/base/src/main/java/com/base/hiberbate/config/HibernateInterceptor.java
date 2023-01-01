package com.base.hiberbate.config;

import org.hibernate.EmptyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class HibernateInterceptor extends EmptyInterceptor {
	
//	@Autowired
//	BaseSession baseService;
	
//	@Override
//	public boolean onSave(
//			Object entity, 
//			Serializable id, 
//			Object[] state, 
//			String[] propertyNames, 
//			Type[] types) {
//		if(entity instanceof AbstractEntity) {
//			((AbstractEntity)entity).setTenantId(baseService.getTenantId());
//			
//			//check if id is null, then generate and setid
//			
//		}
//		return super.onSave(entity, id, state, propertyNames, types);
//	}
//	
//	@Override
//	public void onDelete(
//			Object entity, 
//			Serializable id, 
//			Object[] state, 
//			String[] propertyNames, 
//			Type[] types) {
//		if(entity instanceof AbstractEntity) {
//			((AbstractEntity)entity).setTenantId(baseService.getTenantId());
//		}
//		super.onDelete(entity, id, state, propertyNames, types);
//	}
//	
//	@Override
//	public boolean onFlushDirty(
//			Object entity, 
//			Serializable id, 
//			Object[] currentState, 
//			Object[] previousState, 
//			String[] propertyNames, 
//			Type[] types) {
//		if(entity instanceof AbstractEntity) {
//			((AbstractEntity)entity).setTenantId(baseService.getTenantId());
//		}
//		return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
//	}
//	
//	@Override
//	public boolean onLoad(
//			Object entity, 
//			Serializable id, 
//			Object[] state, 
//			String[] propertyNames, 
//			Type[] types) {
//		if(entity instanceof AbstractEntity) {
//			((AbstractEntity)entity).setTenantId(baseService.getTenantId());
//		}
//		return super.onLoad(entity, id, state, propertyNames, types);
//	}
	
}
