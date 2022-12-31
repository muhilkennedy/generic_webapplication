package com.base.serviceimpl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.base.util.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Muhil
 *
 * @param <T> return object type
 */
public class HttpClient<T> {

	T object;

	public HttpClient(T obj) {
		this.object = obj;
	}

	public T get(String url, List<Header> headers, List<NameValuePair> params) throws IOException, URISyntaxException {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpGet request = HttpUtil.httpGet(url, headers, params);
			ObjectMapper mapper = new ObjectMapper();
			T response = (T) client.execute(request,
					httpResponse -> mapper.readValue(httpResponse.getEntity().getContent(), object.getClass()));
			return response;
		}
	}

	public T p(String url, List<Header> headers, List<NameValuePair> params, String jsonBody)
			throws IOException, URISyntaxException {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpPost request = HttpUtil.httpPost(url, headers, params, jsonBody);
			ObjectMapper mapper = new ObjectMapper();
			T response = (T) client.execute(request,
					httpResponse -> mapper.readValue(httpResponse.getEntity().getContent(), object.getClass()));
			return response;
		}
	}

}
