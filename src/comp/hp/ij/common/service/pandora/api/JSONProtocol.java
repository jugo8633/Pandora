package comp.hp.ij.common.service.pandora.api;

import java.util.*;
import org.json.JSONObject;
import comp.hp.ij.common.service.pandora.api.JSONResult;
import comp.hp.ij.common.service.pandora.util.Logger;

/**
 * Json implementation for communicating with the Pandora server
 */
public class JSONProtocol extends Protocol {
	public JSONProtocol(Security security, String httpUrl, String httpsUrl) throws Exception {
		super(security, httpUrl, httpsUrl);
	}

	public String getContentType() {
		return "text/plain";
	}

	public String getPayload(String method, HashMap<String, Object> params) {
		return new JSONObject(params).toString(); //new
	//	return JSONObject.fromObject(params).toString();
	}

	public Result getResultFromResponse(String response) throws Exception {		
		JSONObject responseObject = new JSONObject(response);
		validateResponse(responseObject);
		if(responseObject.has("result")){
			JSONObject resultObject = (JSONObject) responseObject.get("result");
			//Logger.d("resultObject: [" + resultObject.toString() + "]");
			return new JSONResult(resultObject);
		}
		return new JSONResult(responseObject);
	}

	/**
	 * Validate the response validity
	 */
	private void validateResponse(JSONObject responseObject) throws Exception {
		if (responseObject.has("stat")) {
			String status = (String) responseObject.get("stat");
			if (status.equals("fail")) {			    
			    int iErrorCode = responseObject.getInt("code");			    
			    handleAPIErrorCode(iErrorCode);
			}
		}
	}
	
}
