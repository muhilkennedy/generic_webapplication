package com.base.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author Muhil
 * Util methods to make Http call using Apache library
 */
public class HttpUtil {

	public static String get(String url) throws IOException, URISyntaxException {
		return get(url, null, null);
	}

	public static String get(String url, List<NameValuePair> params) throws IOException, URISyntaxException {
		return get(url, null, params);
	}

	public static String get(String url, List<Header> headers, List<NameValuePair> params)
			throws IOException, URISyntaxException {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpGet request = httpGet(url, headers, params);
			return client.execute(request).getEntity().toString();
		}
	}

	public static String post(String url) throws IOException, URISyntaxException {
		return post(url, null, null, null);
	}

	public static String post(String url, List<NameValuePair> params, String jsonBody)
			throws IOException, URISyntaxException {
		return post(url, null, null, null);
	}

	public static String post(String url, List<Header> headers, List<NameValuePair> params)
			throws IOException, URISyntaxException {
		return post(url, null, null, null);
	}

	public static String post(String url, List<Header> headers, List<NameValuePair> params, String jsonBody)
			throws IOException, URISyntaxException {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpPost request = httpPost(url, headers, params, jsonBody);
			return client.execute(request).getEntity().toString();

		}
	}

	public static HttpGet httpGet(String url, List<Header> headers, List<NameValuePair> params)
			throws URISyntaxException {
		HttpGet request = new HttpGet(url);
		if (headers != null && !headers.isEmpty()) {
			request.setHeaders((Header[]) headers.toArray());
		}
		URI uri = prepareURI(request, params);
		request.setURI(uri);
		return request;
	}

	public static HttpPost httpPost(String url, List<Header> headers, List<NameValuePair> params, String jsonBody)
			throws UnsupportedEncodingException, URISyntaxException {
		HttpPost request = new HttpPost(url);
		StringEntity entity = new StringEntity(jsonBody);
		request.setEntity(entity);
		if (headers != null && !headers.isEmpty()) {
			request.setHeaders((Header[]) headers.toArray());
		}
		URI uri = prepareURI(request, params);
		request.setURI(uri);
		return request;
	}
	
	public static HttpPut httpPut(String url, List<Header> headers, List<NameValuePair> params, String jsonBody)
			throws UnsupportedEncodingException, URISyntaxException {
		HttpPut request = new HttpPut(url);
		StringEntity entity = new StringEntity(jsonBody);
		request.setEntity(entity);
		if (headers != null && !headers.isEmpty()) {
			request.setHeaders((Header[]) headers.toArray());
		}
		URI uri = prepareURI(request, params);
		request.setURI(uri);
		return request;
	}
	
	public static HttpDelete httpDelete(String url, List<Header> headers, List<NameValuePair> params)
			throws UnsupportedEncodingException, URISyntaxException {
		HttpDelete request = new HttpDelete(url);
		if (headers != null && !headers.isEmpty()) {
			request.setHeaders((Header[]) headers.toArray());
		}
		URI uri = prepareURI(request, params);
		request.setURI(uri);
		return request;
	}

	public static URI prepareURI(HttpRequestBase request, List<NameValuePair> params) throws URISyntaxException {
		URI uri = null;
		if (params != null && !params.isEmpty()) {
			uri = new URIBuilder(request.getURI()).addParameters(params).build();
		} else {
			uri = new URIBuilder(request.getURI()).build();
		}
		return uri;
	}

}
