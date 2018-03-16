package com.fahmiyuseri.psisingapore.Util;

public class HttpErrorKey {
	public static final String timeoutKey = "timeout";
	public static final String connectKey = "connection";
	
	public static String getErrorResponse(String errorKey)
	{
		String response=null;

		if(errorKey.equals(timeoutKey)){
			response = "Connection timed out - please try again.";
		}
		else{
			response = "Problem while connecting to server. Does your connection working well?";
		}
		return response;
	}

	public static String getErrorResponseJSON(int rCode){
		String response = "{\"response_code\":" + rCode + "}";

		return response;
	}
}
