package com.chazzzz.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

/**
 * Utility methods for web application server side operations
 * 
 * @author chazz
 *
 */
public class WebUtil {
	
	/**
	 * A request to url using "GET" method
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static String get(String url, Map<String, String> params) throws IOException {
		return WebUtil.sendRequest("GET", url, params);
	}
	
	/**
	 * A request to a url using "POST" method
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static String post (String url, Map<String, String> params) throws IOException {
		return WebUtil.sendRequest("POST", url, params);
	}
	
	private static String sendRequest(String method, String requestUrl, Map<String, String> params) throws IllegalStateException, IOException{
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpUriRequest request = null;
		
		switch(method){
			case "GET": {
				if(params != null && params.size() > 0 ){
					StringBuilder rawUrl = new StringBuilder(requestUrl).append("?");
					for(String paramKey: params.keySet()){
						rawUrl.append(paramKey)
							.append("=")
							.append(params.get(paramKey))
							.append("&");
					}
					requestUrl = rawUrl.substring(0, rawUrl.length()-1);
				}
				request = new HttpGet(requestUrl);
			}break;
			
			case "POST": {
				request = new HttpPost(requestUrl);
				if(params != null && params.size() > 0){
					ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					for(String paramKey: params.keySet()){
						nameValuePair.add(new BasicNameValuePair(paramKey, params.get(paramKey)));
					}
					
					((HttpPost) request).setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
				}
				
			}break;
			case "DELETE": {
				request = new HttpDelete(requestUrl);
			}break;
			case "PUT": {
				request = new HttpPut(requestUrl);
				if(params != null && params.size() > 0){
					ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					for(String paramKey: params.keySet()){
						nameValuePair.add(new BasicNameValuePair(paramKey, params.get(paramKey)));
					}
					
					((HttpPut) request).setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
				}
			}break;
		default:
			throw new IllegalArgumentException("No request method is specified.");
		}
		
		HttpResponse response = client.execute(request);
		
		InputStream inputStream = response.getEntity().getContent();
		
		String data = convertStreamToString(inputStream);
		
		inputStream.close();
		
		return data;
	}
	
	private static String convertStreamToString(InputStream inputStream) {
		try {
			return new Scanner(inputStream).useDelimiter("\\A").next();
		} catch (NoSuchElementException e) {
			return "";
		}
	}
}
