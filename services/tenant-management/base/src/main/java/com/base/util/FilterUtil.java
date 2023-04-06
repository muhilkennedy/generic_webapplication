package com.base.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import com.base.annotation.UserAuthValidation;


public class FilterUtil {
	
	/**
	 * @return identify rest controller endpoints with validate user token annotation.
	 */
	@Deprecated
	public static String[] getAuthUrlPatterns() {
		List<String> paths = new ArrayList<>();
		Set<Method> apis = new Reflections("com.", new MethodAnnotationsScanner())
				.getMethodsAnnotatedWith(UserAuthValidation.class);
		apis.parallelStream().forEach(api -> {
			RequestMapping methodRequestMapping = api.getAnnotation(RequestMapping.class);
			RequestMapping classRequestMapping = api.getDeclaringClass().getAnnotation(RequestMapping.class);
			Arrays.stream(methodRequestMapping.value()).forEach(mapping -> {
				if (mapping.contains("{")) {
					// this is needed incase path variables are present.
					String[] arr = mapping.split("{");
					mapping = arr[0];
				}
				String path = String.format("/%s%s/*", classRequestMapping.value()[0], mapping);
				Log.base.debug(path);
				paths.add(path);
			});
		});
		Log.base.debug("Identified User Auth URL patterns : " + paths.toString());
		return paths.stream().toArray(String[]::new);
	}
	
	public static String[] getUserTokenFilterPatterns() {
		List<String> paths = new ArrayList<>();
		Set<Class<?>> clsSet = new Reflections("com.").getTypesAnnotatedWith(UserAuthValidation.class);
		clsSet.stream().forEach(cls -> {
			RequestMapping clsRequestMapping = cls.getAnnotation(RequestMapping.class);
			Assert.notNull(clsRequestMapping.value(), "Value required for RequestMapping in class : " + cls.getName());
			Arrays.stream(clsRequestMapping.value()).forEach(mapping -> {
				String path = String.format("/%s/*", mapping);
				Log.base.debug(path);
				paths.add(path);
			});
		});
		Log.base.debug("Identified User Auth URL patterns : " + paths.toString());
		return paths.stream().toArray(String[]::new);
	}

}
