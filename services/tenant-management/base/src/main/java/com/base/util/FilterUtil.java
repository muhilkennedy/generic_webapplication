package com.base.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.springframework.web.bind.annotation.RequestMapping;

import com.base.annotation.ValidateUserToken;

@SuppressWarnings("deprecation")
public class FilterUtil {
	
	/**
	 * @return identify rest controller endpoints with validate user token annotation.
	 */
	public static String[] getAuthUrlPatterns() {
		List<String> paths = new ArrayList<>();
		Set<Method> apis = new Reflections("com.", new MethodAnnotationsScanner())
				.getMethodsAnnotatedWith(ValidateUserToken.class);
		apis.parallelStream().forEach(api -> {
			RequestMapping methodRequestMapping = api.getAnnotation(RequestMapping.class);
			RequestMapping classRequestMapping = api.getDeclaringClass().getAnnotation(RequestMapping.class);
			Arrays.stream(methodRequestMapping.value()).forEach(mapping -> {
				if (mapping.contains("{")) {
					// this is needed incase path variables are present.
					String[] arr = mapping.split("{");
					mapping = arr[0];
				}
				paths.add(String.format("/%s%s/*", classRequestMapping.value()[0], mapping));
			});
		});
		Log.base.debug("Identified User Auth URL patterns : " + paths.toString());
		return paths.stream().toArray(String[]::new);
	}

}
