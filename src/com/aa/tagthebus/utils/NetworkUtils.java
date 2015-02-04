package com.aa.tagthebus.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.client.Header;
import retrofit.client.Response;

public class NetworkUtils {
 
	public static String COOKIE = "";
	// The base API endpoint
	final static public String MEMORINK_DOMAIN = "http://aurora.memorink.net/api" ; 
	
	public static String getCookie(Response arg1) {
		String cookie = "";

		List<Header> headers = arg1.getHeaders();
		for(Header header : headers){
			String headerName = header.getName();
			if(headerName != null && headerName.length() > 0){
				if(headerName.equalsIgnoreCase("set-cookie")){
					cookie = header.getValue();
					break;
				}
			}
		}
		int start = cookie.indexOf("s=");
		int end = cookie.indexOf(";");
		if(start > -1 && end > -1 && start < end)
			cookie = cookie.substring(start, end);
		else
			cookie = "";
		return cookie;
	}
	
	public static String getPostCookie(Response arg1) {
		String cookie = "";
		String result = "";
		List<Header> headers = arg1.getHeaders();
		for(Header header : headers){
			String headerName = header.getName();
			if(headerName != null && headerName.length() > 0){
				if(headerName.equalsIgnoreCase("set-cookie")){
					cookie = header.getValue();
					break;
				}
			}
		}
		String[] cookieParts = cookie.split(";");
		
		for (String string : cookieParts) {
			String subS = string.substring(0, 2);
			if(subS.equals("s=")){
				if(!COOKIE.contains("s=")){
					result += string;
				}
			}
			if(subS.equals("p=")){
				if(!COOKIE.contains("p=")){
					result += string;
				}
			}
		}
		return result;
	}
	
	public static JSONObject getFormattedBody(Response arg1) {
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(arg1.getBody().in()));
			String line;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonResponse = null;
		try {
			jsonResponse = new JSONObject(sb.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonResponse;
	}
	
	public class AccessToken{
		String value;

		public AccessToken() {}

		public String getValue() {
			return value;
		}
		public void setValue(String value){
			this.value = value;
		}
	}

	public class Status{
		String status;

		public Status() {}

		public String getStatus() {
			return status;
		}
		public void setStatus(String status){
			this.status = status;
		}
	}
	public class MemoError{
		String error;

		public MemoError() {}

		public String getError() {
			return error;
		}
		public void setError(String error){
			this.error = error;
		}
	}
	
}
