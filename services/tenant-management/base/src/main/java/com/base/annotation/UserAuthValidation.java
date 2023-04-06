package com.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.base.security.Permissions;

/**
 * @author Muhil
 * class annotated will be added as part of user filter based on the request mapping.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UserAuthValidation {
	
}
