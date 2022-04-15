package com.platform.base.hibernate.config;

import java.io.Serializable;
import java.sql.PreparedStatement;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.platform.base.entity.AbstractEntity;
import com.platform.base.service.BaseService;

@Configuration
@Component
public class HibernateInterceptor extends EmptyInterceptor {
	
	@Autowired
	BaseService baseService;


	@Override
	public boolean onSave(
			Object entity, 
			Serializable id, 
			Object[] state, 
			String[] propertyNames, 
			Type[] types) {
		if(entity instanceof AbstractEntity) {
			((AbstractEntity)entity).setTenantId(baseService.getTenantId());
		}
		return super.onSave(entity, id, state, propertyNames, types);
	}
	
	@Override
	public void onDelete(
			Object entity, 
			Serializable id, 
			Object[] state, 
			String[] propertyNames, 
			Type[] types) {
		if(entity instanceof AbstractEntity) {
			((AbstractEntity)entity).setTenantId(baseService.getTenantId());
		}
		super.onDelete(entity, id, state, propertyNames, types);
	}
	
	//override all
	
}
