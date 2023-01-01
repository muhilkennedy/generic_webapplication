package com.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Muhil
 * Annotate with this interface for crud op methods to load/evict objects into temp cache.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cacheble {

	String key() default "";
	
	boolean update() default false;
	
	boolean evict() default false;
}
