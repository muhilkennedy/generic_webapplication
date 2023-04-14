package com.mken.base.configuration;


import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.reflections.Reflections;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import com.mken.base.util.Log;
import com.platform.annotations.ClassMetaProperty;


/**
 * @author Muhil
 *
 */
@Configuration
public class StartupValidations {

	/**
	 * Identify if came class code for entity tables.
	 */
	@PostConstruct
	private void validateClassCode() {
		Reflections reflection = new Reflections("com.mken.");
		Set<String> uniqueSet = new HashSet<String>();
		Set<Class<?>> classes = reflection.getTypesAnnotatedWith(ClassMetaProperty.class);
		long count = classes.stream().map(cls -> (ClassMetaProperty) cls.getAnnotation(ClassMetaProperty.class))
				.filter(cls -> !uniqueSet.add(cls.code())).count();
		Assert.isTrue(!(count > 0), "Duplicate Class Codes Detected");
		Log.base.debug("Class codes validated");
	}

}
