package comp.hp.ij.common.service.pandora.api;

import java.util.HashMap;

import comp.hp.ij.common.service.pandora.util.Logger;

/**
 * Performs Auth API calls.
 * 
 * Additionally, stores auth credentials and syncTime required for other API requests.
 */
public class AuthRequester extends BaseApi {
    private String sPartnerAuthToken;
    private String sPartnerId;
    
    private String sUserAuthToken;
    private String sUserId;
    
    private String sAutoCompleteUrl;

    private long lServerSyncTime;
    private long lClientStartTime;

    private Security security;

    /**
     * Constructor.
     * 
     * @param protocol protocol
     * @param security security
     */
    public AuthRequester(Protocol protocol, Security security) {
        super(protocol);
        this.security = security;
    }

    /**
     * 
     * @param username username
     * @param password password
     * @param deviceModel deviceModel
     * 
     * @throws Exception Exception
     */
    public void partnerLogin(String username, String password, String deviceModel) throws Exception {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        params.put("password", password);
        params.put("version", "4");
        params.put("deviceModel", deviceModel);
        params.put("includeUrls", true);

        // if there is something wrong it will throw exception here
        Result result = protocol.sendSecureRequest(false, getUrlParameters("auth.partnerLogin"), params);
        
        sPartnerAuthToken = result.getString("partnerAuthToken");
        sPartnerId = result.getString("partnerId");
        
        //Logger.d("partnerAuthToken: [" + sPartnerAuthToken + "]");
        //Logger.d("sPartnerId:       [" + sPartnerId + "]");
        
        Result resultUrls = result.getResult("urls");
        sAutoCompleteUrl = resultUrls.getString("autoComplete");
        //Logger.d("sAutoCompleteUrl: [" + sAutoCompleteUrl + "]");
        
        String encodedSyncTime = result.getString("syncTime");
        lClientStartTime = System.currentTimeMillis() / 1000;
        lServerSyncTime = Long.parseLong(security.decrypt(encodedSyncTime));
        
        Logger.d("lServerSyncTime: [" + lServerSyncTime + "]");
        lServerSyncTime = (lServerSyncTime < 1000000000) ? (lServerSyncTime += 1000000000) : lServerSyncTime;
        Logger.d("lServerSyncTime: [" + lServerSyncTime + "]");
    }

    /**
     * 
     * @param sUsername username
     * @param sPassword password
     * 
     * @throws Exception Exception
     */
    public void userLogin(String sUsername, String sPassword) throws Exception {

        if (null == getPartnerAuthToken()) {
            throw new Exception("Partner Auth Token is required for user login");
        }

        if (null == getPartnerId()) {
            throw new Exception("Partner Id is required for user login");
        }

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("partnerAuthToken", sPartnerAuthToken);
        params.put("loginType", "user");
        params.put("username", sUsername);
        params.put("password", sPassword);
        params.put("syncTime", getSyncTime());

        HashMap<String, String> urlParameters = getUrlParameters("auth.userLogin");
        urlParameters.put("auth_token", sPartnerAuthToken);
        urlParameters.put("partner_id", sPartnerId);

        // if there is something wrong it will throw exception here
        Result result = protocol.sendSecureRequest(true, urlParameters, params);
        
        sUserAuthToken = result.getString("userAuthToken");
        sUserId = result.getString("userId");
    }

    /**
     * 
     * @param sDeviceId deviceId
     * 
     * @throws Exception Exception
     */
    public void deviceLogin(String sDeviceId) throws Exception {
        Logger.d("sDeviceId: [" + sDeviceId + "]");

        if (null == getPartnerAuthToken()) {
            throw new Exception("Partner Auth Token is required for user login");
        }

        if (null == getPartnerId()) {
            throw new Exception("Partner Id is required for user login");
        }

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("partnerAuthToken", sPartnerAuthToken);
        params.put("loginType", "deviceId");
        params.put("deviceId", sDeviceId);
        params.put("syncTime", getSyncTime());

        HashMap<String, String> urlParameters = getUrlParameters("auth.userLogin");
        urlParameters.put("auth_token", sPartnerAuthToken);
        urlParameters.put("partner_id", sPartnerId);

        // if there is something wrong it will throw exception here
        Result result = protocol.sendSecureRequest(true, urlParameters, params);
        
        sUserAuthToken = result.getString("userAuthToken");
        sUserId = result.getString("userId");
        
        Logger.d("userAuthToken: [" + sUserAuthToken + "]");
        Logger.d("sUserId:       [" + sUserId + "]");
    }

    /**
     * 
     * @return sync time
     */
    public Long getSyncTime() {
        long lCurrentClientTime = System.currentTimeMillis() / 1000;
        //return lServerSyncTime + (currentClientTime - lClientStartTime);
        long lReturn = lServerSyncTime + (lCurrentClientTime - lClientStartTime);
        Logger.d("serverSyncTime:    [" + lServerSyncTime + "]");
        Logger.d("currentClientTime: [" + lCurrentClientTime + "]");
        Logger.d("clientStartTime:   [" + lClientStartTime + "]");
        Logger.d("lReturn:           [" + lReturn + "]");
        return lReturn;
    }

    /**
     * 
     * @return partner auth token
     */
    public String getPartnerAuthToken() {
        return sPartnerAuthToken;
    }

    /**
     * 
     * @return user auth token
     */
    public String getUserAuthToken() {
        return sUserAuthToken;
    }

    /**
     * 
     * @return partner id
     */
    public String getPartnerId() {
        return sPartnerId;
    }

    /**
     * 
     * @return user id
     */
    public String getUserId() {
        return sUserId;
    }

    /**
     * 
     * @return Pandora auto complete server url
     */
    public String getAutoCompleteUrl() {
        return sAutoCompleteUrl;
    }
}
