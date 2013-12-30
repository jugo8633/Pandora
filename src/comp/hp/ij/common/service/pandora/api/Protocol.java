package comp.hp.ij.common.service.pandora.api;

import org.apache.http.HttpResponse;

import comp.hp.ij.common.ccphttpclient.CCPHttpClient;
import comp.hp.ij.common.ccphttpclient.CCPHttpClientCallback;
import comp.hp.ij.common.ccphttpclient.RequestInfo;
import comp.hp.ij.common.ccphttpclient.CCPHttpClient.PostContentBuilder;
import comp.hp.ij.common.service.pandora.api.exception.PandoraBaseException;
import comp.hp.ij.common.service.pandora.exception.CCPExecuteIOException;
import comp.hp.ij.common.service.pandora.exception.CCPSocketTimeoutException;
import comp.hp.ij.common.service.pandora.exception.CCPUnknownHostException;
import comp.hp.ij.common.service.pandora.util.Debugger;
import comp.hp.ij.common.service.pandora.util.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Base class for communicating with the Pandora Server
 *
 * For this example we chose to utilize the Apache Commons HttpClient for our http transport.
 * See http://hc.apache.org/httpcomponents-client/index.html for more information
 * 
 */
public abstract class Protocol implements CCPHttpClientCallback {

	private URL httpUrl;
	private URL httpsUrl;

	private Security security;
	
	private boolean isCCPHttpClientFinish;
	private int iCCPHttpClientReturnCode = CCPHttpClient.SUCCESS_CCPHTTPCLIENT;
	private CCPHttpClient ccpHttpClient = null;
	private HttpResponse httpResponse;
	private Thread thread;

	public Protocol(Security security, String httpUrl, String httpsUrl) throws Exception {
		this.httpUrl = new URL(httpUrl);
		this.httpsUrl = new URL(httpsUrl);
		this.security = security;
		
		if (null == ccpHttpClient) {
		    Logger.d("CCPHttpClient.initRequest()");
		    ccpHttpClient = new CCPHttpClient();
	        ccpHttpClient.initRequest((CCPHttpClientCallback) this, 1, PandoraAPIConstants.NETWORK_TIMEOUT);
		}
	}

	public Result sendSecureRequest(boolean encryptPayload, HashMap<String, String> urlParameters, HashMap<String, Object> params) throws Exception {
		return execute(encryptPayload, buildUrl(true, urlParameters), urlParameters.get("method"), params);
	}

	public Result sendRequest(boolean encryptPayload, HashMap<String, String> urlParameters, HashMap<String, Object> params) throws Exception {
		return execute(encryptPayload, buildUrl(false, urlParameters), urlParameters.get("method"), params);
	}
	
	public Result sendAutoCompleteRequest(String sAutoCompleteUrl, HashMap<String, String> urlParameters) throws Exception {
	    return execute(buildUrl(sAutoCompleteUrl, urlParameters));
	}

	abstract String getContentType();
	abstract String getPayload(String method, HashMap<String, Object> params);
	abstract Result getResultFromResponse(String response) throws Exception;

	private Result execute(boolean encryptPayload, URL apiUrl, String method, HashMap<String, Object> params) throws Exception {
	    
	    ccpHttpClient.setHttpHeader("Content-Type", getContentType());
	    String sPayload = getPayload(method, params);
	    //Logger.d("Request Payload: [" + sPayload + "]");
	    PostContentBuilder postContentBuilder =  ccpHttpClient.new PostContentBuilder();
	    if (encryptPayload) {
	        //Logger.d("encryptPayload");
	        postContentBuilder.setPostContent(security.encrypt(sPayload));
	    } else {
	        //Logger.d("no encryptPayload");
	        postContentBuilder.setPostContent(sPayload);
	    }
	    
	    //Logger.d("apiUrl.toString(): [" + apiUrl.toString() + "]");
	    
	    int iFirstIndex = apiUrl.toString().indexOf(':');
	    //Logger.d("iFirstIndex: [" + iFirstIndex + "]");
	    int iSecondIndex = apiUrl.toString().indexOf(':', iFirstIndex + 1);
	    //Logger.d("iSecondIndex: [" + iSecondIndex + "]");
	    int iThirdIndex = apiUrl.toString().indexOf('/', iSecondIndex + 1);
	    //Logger.d("iThirdIndex: [" + iThirdIndex + "]");
	    String sPort = apiUrl.toString().substring(iSecondIndex + 1, iThirdIndex);
	    //Logger.d("sPort: [" + sPort + "]");
	    int iPort = Integer.parseInt(sPort);
	    //Logger.d("iPort: [" + iPort + "]");
	    
	    Logger.d("ccpHttpClient.startPostMethod");
	    iCCPHttpClientReturnCode = CCPHttpClient.SUCCESS_CCPHTTPCLIENT;
	    ccpHttpClient.startPostMethod(apiUrl.toString(), iPort, postContentBuilder);
	    Logger.d("Wait CCPHTTPClient start");
	    waitCCPHttpClient();
	    Logger.d("Wait CCPHTTPClient end");    

	    // throw exception cause by CCPHttpClient
	    handleCCPHttpClientReturn(iCCPHttpClientReturnCode);
	        
        String sResponse = "";
        InputStream is = httpResponse.getEntity().getContent();             
        sResponse = convertStreamToString(is);
        //Logger.d("sResponse: [" + sResponse + "]");
        
        // throw exception for Pandora error code
        return getResultFromResponse(sResponse);
	    
        /*
	    PostMethod post = new PostMethod(apiUrl.toString());
		post.setRequestHeader("Content-Type", getContentType());
		String payload = getPayload(method, params);
		RequestEntity requestEntity = preparePayload(encryptPayload, payload);
		post.setRequestEntity(requestEntity);
		httpClient.executeMethod(post);
		String response = post.getResponseBodyAsString();
		return getResultFromResponse(response);
		*/
	}
	
	private Result execute(URL url) throws Exception {
	    ccpHttpClient.setHttpHeader("Content-Type", getContentType());

	    Logger.d("ccpHttpClient.startGetMethod");
	    iCCPHttpClientReturnCode = CCPHttpClient.SUCCESS_CCPHTTPCLIENT;
	    //ccpHttpClient.setHttpHeader("Keep-Alive", "timeout=10");
	    ccpHttpClient.startGetMethod(url.toString(), 80);
	    Logger.d("Wait CCPHTTPClient start");
	    waitCCPHttpClient();
	    Logger.d("Wait CCPHTTPClient end"); 
	    // throw exception cause by CCPHttpClient
	    handleCCPHttpClientReturn(iCCPHttpClientReturnCode);

	    String sResponse = "";
	    InputStream is = httpResponse.getEntity().getContent();             
	    sResponse = convertStreamToString(is);
	    //Logger.d("sResponse: [" + sResponse + "]");

	    return getResultFromResponse(sResponse);
	}

	/**
	 * Encrypt the payload when the calling
	 */
	/*
	RequestEntity preparePayload(boolean encryptPayload, String payload) throws BadPaddingException,
		IllegalBlockSizeException {
		RequestEntity requestEntity;
		Log.d(TAG, "Request Payload: [" + payload + "]");
		if (encryptPayload) {
			requestEntity = new StringRequestEntity(security.encrypt(payload));
		} else {
			requestEntity = new StringRequestEntity(payload);
		}
		return requestEntity;
	}
	*/
	
    public void onCompleted(CCPHttpClient httpcb, RequestInfo requestInfo, HttpResponse response) {
        Logger.d();
        
        httpResponse = response;
        isCCPHttpClientFinish = true; 
        if (null != thread) {
            thread.interrupt();
        }
    }
    
    public void onError(RequestInfo requestInfo, HttpResponse response, int errorCode) {
        Logger.d();
        
        iCCPHttpClientReturnCode = errorCode;
        debugCCPHttpClientErrorCode(errorCode);
        
        httpResponse = response;
        isCCPHttpClientFinish = true;    
        if (null != thread) {
            thread.interrupt();
        }
    }
    
    public void onProgress(CCPHttpClient cbObj, RequestInfo requestinfo, long total_size, long read_size, long current_size) {
    }

	/**
	 * Build the URL
	 */
	private URL buildUrl(boolean isSecure, HashMap<String, String> urlParameters) throws Exception {
		URL baseUrl = isSecure ? httpsUrl : httpUrl;
		StringBuilder builder = new StringBuilder();
		builder.append(baseUrl);

		int counter = 0;
		for (String parameterKey : urlParameters.keySet()) {
		    //Logger.d("key: [" + parameterKey + "], value: [" + urlParameters.get(parameterKey) + "]");
			builder.append((0 == counter) ? "?" : "&");
			counter++;
			builder.append(parameterKey);
			builder.append("=");
			
			// +++++ handle INVALID_AUTH_TOKEN error, throw PandoraInvalidAuthTokenException +++++ //
			try {
			    builder.append(URLEncoder.encode(urlParameters.get(parameterKey), "UTF-8"));
			} catch (NullPointerException e) {
			    if ("auth_token".equals(parameterKey) && null == urlParameters.get(parameterKey)) {
			        Logger.e("Convert NullPointerException to PandoraInvalidAuthTokenException.");
			        throw new PandoraBaseException(PandoraAPIErrorCode.INVALID_AUTH_TOKEN);
			    }
			    throw e;
			}
			// ----- handle INVALID_AUTH_TOKEN error, throw PandoraInvalidAuthTokenException ----- //
		}

		//Logger.d("Request URL: [" + builder.toString() + "]");
		return new URL(builder.toString());
	}
	
	private URL buildUrl(String sBaseUrl, HashMap<String, String> urlParameters) throws Exception {
	    StringBuilder builder = new StringBuilder();
	    builder.append(sBaseUrl);

	    int counter = 0;
	    for (String parameterKey : urlParameters.keySet()) {
	        //Logger.d("key: [" + parameterKey + "], value: [" + urlParameters.get(parameterKey) + "]");
	        builder.append((0 == counter) ? "?" : "&");
	        counter++;
	        builder.append(parameterKey);
	        builder.append("=");
	        builder.append(URLEncoder.encode(urlParameters.get(parameterKey), "UTF-8"));
	    }

	    //Logger.d("Request URL: [" + builder.toString() + "]");
	    return new URL(builder.toString());
	}
	
    private void waitCCPHttpClient() {
        int iCount = 0;
        
        thread = Thread.currentThread();
        isCCPHttpClientFinish = false;
        while (!isCCPHttpClientFinish) {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                Logger.d("waitCCPHttpClient: [" + e.toString() + "]");
            }
                
            if( iCount++ > 120) {
                break;
            }
        }
    }
    
    private String convertStreamToString(InputStream is) {        
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));        
        StringBuilder sb = new StringBuilder(); 
        String line = null;        
         
        try {
            while (null != (line = reader.readLine())) {
                sb.append(line + "\n");
            }
        } catch (IOException ioe) {
            Logger.e(ioe);

        } finally {
            try {
                is.close();
            } catch (IOException ioe) {
                Logger.e(ioe);
            } catch (Exception e) {
                Logger.e(e);
            }
        }
 
        return sb.toString();
    }
    
    private void handleCCPHttpClientReturn(int iCCPHttpClientCode) throws Exception {
        if (CCPHttpClient.SUCCESS_CCPHTTPCLIENT != iCCPHttpClientCode) {
            switch (iCCPHttpClientCode) {
                case CCPHttpClient.ERROR_EXECUTE_SOCKETIMEOUT:
                    Logger.d("ERROR_EXECUTE_SOCKETIMEOUT");
                    throw new CCPSocketTimeoutException();
                case CCPHttpClient.ERROR_EXECUTE_UNKNOWNHOST:
                    Logger.d("ERROR_EXECUTE_UNKNOWNHOST");
                    throw new CCPUnknownHostException();
                case CCPHttpClient.ERROR_EXECUTE_IOEXCEPTION:
                    throw new CCPExecuteIOException();
                default:
                    Logger.d("iCCPHttpClientCode: [" + iCCPHttpClientCode + "] not handle yet");
                    throw new Exception();
            }
        }
    }
    
    private void debugCCPHttpClientErrorCode(int iErrorCode) {
        switch (iErrorCode) {
            case CCPHttpClient.ERROR_EXECUTE_CLIENT_PROTOCOL:
                Logger.d("ERROR_EXECUTE_CLIENT_PROTOCOL");
                break;
            case CCPHttpClient.ERROR_EXECUTE_EXCEPTION:
                Logger.d("ERROR_EXECUTE_EXCEPTION");
                break;
            case CCPHttpClient.ERROR_EXECUTE_IOEXCEPTION:
                Logger.d("ERROR_EXECUTE_IOEXCEPTION");
                break;
            case CCPHttpClient.ERROR_EXECUTE_NORESPONSE:
                Logger.d("ERROR_EXECUTE_NORESPONSE");
                break;
            case CCPHttpClient.ERROR_EXECUTE_SOCKETIMEOUT:
                Logger.d("ERROR_EXECUTE_SOCKETIMEOUT");
                break;
            case CCPHttpClient.ERROR_EXECUTE_UNKNOWNHOST:
                Logger.d("ERROR_EXECUTE_UNKNOWNHOST");
                break;
            case CCPHttpClient.ERROR_EXECUTE_URISYNTAXERROR:
                Logger.d("ERROR_EXECUTE_URISYNTAXERROR");
                break;
            case CCPHttpClient.ERROR_FILE_IOEXCEPTION:
                Logger.d("ERROR_FILE_IOEXCEPTION");
                break;
            case CCPHttpClient.ERROR_FILE_NOT_FOUND:
                Logger.d("ERROR_FILE_NOT_FOUND");
                break;
            case CCPHttpClient.ERROR_RESPONSE_4XX:
                Logger.d("ERROR_RESPONSE_4XX");
                break;
            case CCPHttpClient.ERROR_RESPONSE_5XX:
                Logger.d("ERROR_RESPONSE_5XX");
                break;
            case CCPHttpClient.ERROR_RESPONSE_ILLEGALSTATE:
                Logger.d("ERROR_RESPONSE_ILLEGALSTATE");
                break;
            case CCPHttpClient.ERROR_RESPONSE_IOEXCEPTION:
                Logger.d("ERROR_RESPONSE_IOEXCEPTION");
                break;
            case CCPHttpClient.ERROR_RESPONSE_NOSTATUSLINE:
                Logger.d("ERROR_RESPONSE_NOSTATUSLINE");
                break;
            case CCPHttpClient.ERROR_RESPONSE_NOSTREAM:
                Logger.d("ERROR_RESPONSE_NOSTREAM");
                break;
            case CCPHttpClient.ERROR_RESPONSE_OVER_LIMIT:
                Logger.d("ERROR_RESPONSE_OVER_LIMIT");
                break;
            case CCPHttpClient.ERROR_TIMEOUT_BEFORE_SENT:
                Logger.d("ERROR_TIMEOUT_BEFORE_SENT");
                break;
            case CCPHttpClient.ERROR_USER_ABORTED:
                Logger.d("ERROR_USER_ABORTED");
                break;
            default:
                Logger.d("Unknown CCPHttpClient error code: [" + iErrorCode + "]");
                break;
        }
    }
    
    protected void handleAPIErrorCode(int iErrorCode) throws Exception {
        Logger.d("iErrorCode: [" + iErrorCode + "]");
        Debugger.debugReturnCode(iErrorCode);
        throw new PandoraBaseException(iErrorCode);
    }
}
