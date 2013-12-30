package comp.hp.ij.common.service.pandora.api;

//import java.io.IOException;
//import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import comp.hp.ij.common.service.pandora.api.exception.PandoraBaseException;
import comp.hp.ij.common.service.pandora.data.ParcelAd;
import comp.hp.ij.common.service.pandora.data.ParcelDeviceActivationData;
import comp.hp.ij.common.service.pandora.data.ParcelExplanation;
import comp.hp.ij.common.service.pandora.data.ParcelSearchResult;
import comp.hp.ij.common.service.pandora.data.ParcelStation;
import comp.hp.ij.common.service.pandora.data.ParcelTrack;
import comp.hp.ij.common.service.pandora.exception.CCPExecuteIOException;
import comp.hp.ij.common.service.pandora.exception.CCPSocketTimeoutException;
import comp.hp.ij.common.service.pandora.exception.CCPUnknownHostException;
import comp.hp.ij.common.service.pandora.util.Debugger;
import comp.hp.ij.common.service.pandora.util.Logger;

/**
 * Pandora Client Example - A simple client implementation that demonstrates the basic API operations required to start playing music.
 * <p/>
 * <p/>
 * Running the example:
 * <p/>
 * Requires Java 1.5 or greater
 * <p/>
 * 2) Edit "classes/client.properties", add your developer configuration.
 * <p/>
 * 3) Include the following in your classpath:
 * <p/>
 * - "classes/" - "lib/" - all jar files
 */
public class PandoraClient {

    private Security security;
    private Protocol protocol;
    
    private Protocol protocolAutoComplete;

    private AuthRequester authRequester = null;

    //private String apiProtocolName;
    private String jsonApiUrl;
    private String jsonSecureApiUrl;
    //private String xmlrpcApiUrl;
    //private String xmlrpcSecureApiUrl;
    private String syncTimeKey;
    private String requestKey;
    private String partnerUsername;
    private String partnerPassword;
    private String pandoraUsername;
    private String pandoraPassword;
    private String deviceModel;

    private String partnerAuthToken;
    private String userAuthToken;
    
    private ParcelDeviceActivationData parcelDeviceActivationData = null;
    private ArrayList<ParcelStation> alParcelStationList = null;
    private ArrayList<Item> alTrackList = null;
    private ArrayList<ParcelExplanation> alParcelExplanation = null;
    private ParcelSearchResult parcelMusicSearchResult = null;
    private ParcelSearchResult parcelMusicSearchAutoCompleteResult = null;
    private ParcelStation parcelStation = null;
    private Item itemAd = null;
    
    private static final boolean isDebugReadOnlyMode = false; // TODO debug read only mode

    public String getPartnerAuthToken() {
        return partnerAuthToken;
    }

    public void setPartnerAuthToken(String partnerAuthTokenInput) {
        partnerAuthToken = partnerAuthTokenInput;
    }

    public String getUserAuthToken() {
        return userAuthToken;
    }

    public void setUserAuthToken(String userAuthTokenInput) {
        userAuthToken = userAuthTokenInput;
    }

    public PandoraClient() {
        try {
            loadProperties();
            security = new Security(syncTimeKey, requestKey);

            // +++++ JSON only now +++++ //
            protocol = new JSONProtocol(security, jsonApiUrl, jsonSecureApiUrl);
            
            /*
            if ("json".equals(apiProtocolName)) {
                protocol = new JSONProtocol(security, jsonApiUrl, jsonSecureApiUrl);
            } else {
                protocol = new XmlRpcProtocol(security, xmlrpcApiUrl, xmlrpcSecureApiUrl);
            }
            */
            // ----- JSON only now ----- //
        } catch (Exception e) {
            Logger.e(e);
        }
    }
    
    /**
     * Tests if the client is allowed to use the Pandora service
     * by checking the calling client's IP address.
     * 
     * @return Success:<br/>
     *         PandoraAPIConstants.CHECK_LICENSE_SUCCESS<br/>
     *         <br/>
     *         Error:<br/>
     *         PandoraAPIConstants.FAIL_TO_CONNECT_TO_INTERNET<br/>
     *         PandoraAPIConstants.CHECK_LICENSE_FAILED<br/>
     *         <br/>
     *         0 PandoraAPIErrorCode.INTERNAL_ERROR<br/>
     *         1 PandoraAPIErrorCode.MAINTENANCE_MODE<br/>
     *         2 PandoraAPIErrorCode.URL_PARAM_MISSING_METHOD<br/>
     *         8 PandoraAPIErrorCode.PARAMETER_TYPE_MISMATCH<br/>
     *         15 PandoraAPIErrorCode.NON_SECURE_PROTOCOL_REQUIRED
     */
    public int checkLicense() {
        Logger.d();
        
        int iReturnCode = PandoraAPIConstants.CHECK_LICENSE_SUCCESS;        
        try {
            TestRequester testRequester = new TestRequester(protocol);
            if (!testRequester.checkLicensing()) {
                iReturnCode = PandoraAPIConstants.CHECK_LICENSE_FAILED;
            }
        } catch (Exception e) {
            try {
                iReturnCode = exceptionHandler(e);
            } catch (Exception ex) {
                Logger.e("Client not licensed to use Pandora", ex);
                iReturnCode = PandoraAPIConstants.CHECK_LICENSE_FAILED;
            }
        }
        Debugger.debugReturnCode(iReturnCode);
        return iReturnCode;
    }
    
    /**
     * This method is the first step in the authentication process.
     * The calling application must provide a valid partner username,
     * password and deviceModel combination in order to receive its authentication token.
     * 
     * @return Success:<br/>
     *         PandoraAPIConstants.PARTNER_LOGIN_SUCCESS<br/>
     *         <br/>
     *         Error:<br/>
     *         PandoraAPIConstants.FAIL_TO_CONNECT_TO_INTERNET<br/>
     *         PandoraAPIConstants.PARTNER_LOGIN_FAILED<br/>
     *         <br/>
     *         0 PandoraAPIErrorCode.INTERNAL_ERROR<br/>
     *         1 PandoraAPIErrorCode.MAINTENANCE_MODE<br/>
     *         2 PandoraAPIErrorCode.URL_PARAM_MISSING_METHOD<br/>
     *         3 PandoraAPIErrorCode.URL_PARAM_MISSING_AUTH_TOKEN<br/>
     *         6 PandoraAPIErrorCode.SECURE_PROTOCOL_REQUIRED<br/>
     *         8 PandoraAPIErrorCode.PARAMETER_TYPE_MISMATCH<br/>
     *         9 PandoraAPIErrorCode.PARAMETER_MISSING<br/>
     *         10 PandoraAPIErrorCode.PARAMETER_VALUE_INVALID<br/>
     *         11 PandoraAPIErrorCode.API_VERSION_NOT_SUPPORTED<br/>
     *         12 PandoraAPIErrorCode.ERROR_LICENSING_RESTRICTIONS<br/>
     *         1002 PandoraAPIErrorCode.INVALID_LOGIN<br/>
     *         1023 PandoraAPIErrorCode.DEVICE_MODEL_INVALID<br/>
     *         1024 PandoraAPIErrorCode.DEVICE_DISABLED
     */
    public int partnerLogin() {
        Logger.d("Logging in the partner: [" + partnerUsername + "]");

        int iReturnCode = PandoraAPIConstants.PARTNER_LOGIN_SUCCESS;
        try {
            authRequester = new AuthRequester(protocol, security);
            authRequester.partnerLogin(partnerUsername, partnerPassword, deviceModel);
            
            String sAutoCompleteUrl = authRequester.getAutoCompleteUrl();
            protocolAutoComplete = new AutoCompleteProtocol(security, sAutoCompleteUrl, sAutoCompleteUrl);
        } catch (Exception e) {
            try {
                iReturnCode = exceptionHandler(e);
            } catch (Exception ex) {
                Logger.e("Partner login failed", ex);
                iReturnCode = PandoraAPIConstants.PARTNER_LOGIN_FAILED;
            }
        }
        Debugger.debugReturnCode(iReturnCode);
        return iReturnCode;
    }
    
    /**
     * This method removes the deviceId<->user association.
     * 
     * @param sDeviceId
     * 
     * @return Success:<br/>
     *         PandoraAPIConstants.LOGOUT_SUCCESS<br/>
     *         <br/>
     *         Error:<br/>
     *         PandoraAPIConstants.FAIL_TO_CONNECT_TO_INTERNET<br/>
     *         PandoraAPIConstants.CHECK_LICENSE_FAILED<br/>
     *         PandoraAPIConstants.PARTNER_LOGIN_FAILED<br/>
     *         PandoraAPIConstants.LOGOUT_FAILED<br/>
     *         <br/>
     *         0 PandoraAPIErrorCode.INTERNAL_ERROR<br/>
     *         1 PandoraAPIErrorCode.MAINTENANCE_MODE<br/>
     *         2 PandoraAPIErrorCode.URL_PARAM_MISSING_METHOD<br/>
     *         3 PandoraAPIErrorCode.URL_PARAM_MISSING_AUTH_TOKEN<br/>
     *         4 PandoraAPIErrorCode.URL_PARAM_MISSING_PARTNER_ID<br/>
     *         6 PandoraAPIErrorCode.SECURE_PROTOCOL_REQUIRED<br/>
     *         7 PandoraAPIErrorCode.CERTIFICATE_REQUIRED<br/>
     *         8 PandoraAPIErrorCode.PARAMETER_TYPE_MISMATCH<br/>
     *         9 PandoraAPIErrorCode.PARAMETER_MISSING<br/>
     *         12 PandoraAPIErrorCode.ERROR_LICENSING_RESTRICTIONS<br/>
     *         13 PandoraAPIErrorCode.TIME_OUT_OF_SYNC<br/>
     *         1000 PandoraAPIErrorCode.READ_ONLY_MODE<br/>
     *         1001 PandoraAPIErrorCode.INVALID_AUTH_TOKEN<br/>
     *         1009 PandoraAPIErrorCode.DEVICE_NOT_FOUND
     */
    public int doLogout(String sDeviceId) {
        Logger.d("sDeviceId: [" + sDeviceId + "]");
        
        if (isDebugReadOnlyMode) {
            Logger.d("Debug Read Only Mode");
            return PandoraAPIErrorCode.READ_ONLY_MODE;
        }

        int iReturnCode = checkLicense();
        if (PandoraAPIConstants.CHECK_LICENSE_SUCCESS != iReturnCode) {
            return iReturnCode;
        }
        
        iReturnCode = partnerLogin();
        if (PandoraAPIConstants.PARTNER_LOGIN_SUCCESS != iReturnCode) {
            return iReturnCode;
        }
        
        iReturnCode = PandoraAPIConstants.LOGOUT_SUCCESS;
        try {
            DeviceRequester deviceRequester = new DeviceRequester(protocol, authRequester);
            iReturnCode = deviceRequester.disassociateDevice(sDeviceId);
        } catch (Exception e) {
            try {
                iReturnCode = exceptionHandler(e);
            } catch (Exception ex) {
                Logger.e("Disassociate device failed", ex);
                iReturnCode = PandoraAPIConstants.LOGOUT_FAILED;
            }
        }
        
        Debugger.debugReturnCode(iReturnCode);
        return iReturnCode;
    }
    
    /**
     * This method is used for creating an account on the web site
     * and will link to the deviceId to the new account.
     * After the user has created the Pandora account, the device can login using the deviceId.
     * 
     * @param sDeviceId
     * 
     * @return Success:<br/>
     *         PandoraAPIConstants.GENERATE_ACTIVATION_CODE_SUCCESS<br/>
     *         <br/>
     *         Error:<br/>
     *         PandoraAPIConstants.FAIL_TO_CONNECT_TO_INTERNET<br/>
     *         PandoraAPIConstants.CHECK_LICENSE_FAILED<br/>
     *         PandoraAPIConstants.PARTNER_LOGIN_FAILED<br/>
     *         PandoraAPIConstants.GENERATE_ACTIVATION_CODE_FAILED<br/>
     *         <br/>
     *         0 PandoraAPIErrorCode.INTERNAL_ERROR<br/>
     *         1 PandoraAPIErrorCode.MAINTENANCE_MODE<br/>
     *         2 PandoraAPIErrorCode.URL_PARAM_MISSING_METHOD<br/>
     *         3 PandoraAPIErrorCode.URL_PARAM_MISSING_AUTH_TOKEN<br/>
     *         4 PandoraAPIErrorCode.URL_PARAM_MISSING_PARTNER_ID<br/>
     *         6 PandoraAPIErrorCode.SECURE_PROTOCOL_REQUIRED<br/>
     *         7 PandoraAPIErrorCode.CERTIFICATE_REQUIRED<br/>
     *         8 PandoraAPIErrorCode.PARAMETER_TYPE_MISMATCH<br/>
     *         9 PandoraAPIErrorCode.PARAMETER_MISSING<br/>
     *         12 PandoraAPIErrorCode.ERROR_LICENSING_RESTRICTIONS<br/>
     *         13 PandoraAPIErrorCode.TIME_OUT_OF_SYNC<br/>
     *         1000 PandoraAPIErrorCode.READ_ONLY_MODE<br/>
     *         1001 PandoraAPIErrorCode.INVALID_AUTH_TOKEN<br/>
     *         1008 PandoraAPIErrorCode.CALL_NOT_ALLOWED<br/>
     *         1010 PandoraAPIErrorCode.PARTNER_NOT_AUTHORIZED
     */
    public int generateDeviceActivationCode(String sDeviceId) {
        Logger.d("sDeviceId: [" + sDeviceId + "]");
        
        if (isDebugReadOnlyMode) {
            Logger.d("Debug Read Only Mode");
            return PandoraAPIErrorCode.READ_ONLY_MODE;
        }
        
        int iReturnCode = checkLicense();
        if (PandoraAPIConstants.CHECK_LICENSE_SUCCESS != iReturnCode) {
            return iReturnCode;
        }
        
        iReturnCode = partnerLogin();
        if (PandoraAPIConstants.PARTNER_LOGIN_SUCCESS != iReturnCode) {
            return iReturnCode;
        }
        
        iReturnCode = PandoraAPIConstants.GENERATE_ACTIVATION_CODE_SUCCESS;
        try {
            DeviceRequester deviceRequester = new DeviceRequester(protocol, authRequester);
            parcelDeviceActivationData = deviceRequester.generateDeviceActivationCode(sDeviceId);
            if (null != parcelDeviceActivationData) {
                Logger.d("Activation code: [" + parcelDeviceActivationData.getActivationCode() + "]");
                Logger.d("Activation Url:  [" + parcelDeviceActivationData.getActivationUrl() + "]");
            } else {
                Logger.d("parcelDeviceActivationData is null");
                iReturnCode = PandoraAPIConstants.GENERATE_ACTIVATION_CODE_FAILED;
            }
        } catch (Exception e) {
            try {
                iReturnCode = exceptionHandler(e);
            } catch (Exception ex) {
                Logger.e("Generate device activation code failed.", ex);
                iReturnCode = PandoraAPIConstants.GENERATE_ACTIVATION_CODE_FAILED;
            }
        }
        
        Debugger.debugReturnCode(iReturnCode);
        return iReturnCode;
    }
    
    public ParcelDeviceActivationData getDeviceActivationData() {
        return parcelDeviceActivationData;
    }

    /**
     * This method is used to authenticate the device for further access to the system,
     * after the calling application has itself been authenticated.
     * 
     * @param sDeviceId
     * 
     * @return Success:<br/>
     *         PandoraAPIConstants.DEVICE_LOGIN_SUCCESS<br/>
     *         <br/>
     *         Error:<br/>
     *         PandoraAPIConstants.FAIL_TO_CONNECT_TO_INTERNET<br/>
     *         PandoraAPIConstants.CHECK_LICENSE_FAILED<br/>
     *         PandoraAPIConstants.PARTNER_LOGIN_FAILED<br/>
     *         PandoraAPIConstants.DEVICE_LOGIN_FAILED<br/>
     *         <br/>
     *         0 PandoraAPIErrorCode.INTERNAL_ERROR<br/>
     *         1 PandoraAPIErrorCode.MAINTENANCE_MODE<br/>
     *         2 PandoraAPIErrorCode.URL_PARAM_MISSING_METHOD<br/>
     *         3 PandoraAPIErrorCode.URL_PARAM_MISSING_AUTH_TOKEN<br/>
     *         4 PandoraAPIErrorCode.URL_PARAM_MISSING_PARTNER_ID<br/>
     *         6 PandoraAPIErrorCode.SECURE_PROTOCOL_REQUIRED<br/>
     *         8 PandoraAPIErrorCode.PARAMETER_TYPE_MISMATCH<br/>
     *         9 PandoraAPIErrorCode.PARAMETER_MISSING<br/>
     *         10 PandoraAPIErrorCode.PARAMETER_VALUE_INVALID<br/>
     *         11 PandoraAPIErrorCode.API_VERSION_NOT_SUPPORTED<br/>
     *         12 PandoraAPIErrorCode.ERROR_LICENSING_RESTRICTIONS<br/>
     *         13 PandoraAPIErrorCode.TIME_OUT_OF_SYNC<br/>
     *         1001 PandoraAPIErrorCode.INVALID_AUTH_TOKEN<br/>
     *         1002 PandoraAPIErrorCode.INVALID_LOGIN<br/>
     *         1003 PandoraAPIErrorCode.USER_NOT_ACTIVE<br/>
     *         1009 PandoraAPIErrorCode.DEVICE_NOT_FOUND
     */
    public int doDeviceLogin(String sDeviceId) {
        Logger.d("sDeviceId: [" + sDeviceId + "]");

        int iReturnCode = checkLicense();
        if (PandoraAPIConstants.CHECK_LICENSE_SUCCESS != iReturnCode) {
            return iReturnCode;
        }
        
        iReturnCode = partnerLogin();
        if (PandoraAPIConstants.PARTNER_LOGIN_SUCCESS != iReturnCode) {
            return iReturnCode;
        }

        // Login the Device
        Logger.d("Logging in the device with device id: [" + sDeviceId + "]");
        iReturnCode = PandoraAPIConstants.DEVICE_LOGIN_SUCCESS;
        try {
            authRequester.deviceLogin(sDeviceId);
            userAuthToken = authRequester.getUserAuthToken();
            partnerAuthToken = authRequester.getPartnerAuthToken();
        } catch (Exception e) {
            try {
                iReturnCode = exceptionHandler(e);
            } catch (Exception ex) {
                Logger.e("Device login failed", ex);
                iReturnCode = PandoraAPIConstants.DEVICE_LOGIN_FAILED;
            }
        }

        Debugger.debugReturnCode(iReturnCode);
        return iReturnCode;
    }

    public void setPandoraAuth(String sUsername, String sPassword) {
        pandoraUsername = sUsername;
        pandoraPassword = sPassword;
    }

    /**
     * This method is used to authenticate the user for further access to the system,
     * after the calling application has itself been authenticated.
     * 
     * @param sDeviceId
     * 
     * @return Success:<br/>
     *         PandoraAPIConstants.USER_LOGIN_SUCCESS<br/>
     *         <br/>
     *         Error:<br/>
     *         PandoraAPIConstants.FAIL_TO_CONNECT_TO_INTERNET<br/>
     *         PandoraAPIConstants.CHECK_LICENSE_FAILED<br/>
     *         PandoraAPIConstants.PARTNER_LOGIN_FAILED<br/>
     *         PandoraAPIConstants.USER_LOGIN_FAILED<br/>
     *         <br/>
     *         0 PandoraAPIErrorCode.INTERNAL_ERROR<br/>
     *         1 PandoraAPIErrorCode.MAINTENANCE_MODE<br/>
     *         2 PandoraAPIErrorCode.URL_PARAM_MISSING_METHOD<br/>
     *         3 PandoraAPIErrorCode.URL_PARAM_MISSING_AUTH_TOKEN<br/>
     *         4 PandoraAPIErrorCode.URL_PARAM_MISSING_PARTNER_ID<br/>
     *         6 PandoraAPIErrorCode.SECURE_PROTOCOL_REQUIRED<br/>
     *         8 PandoraAPIErrorCode.PARAMETER_TYPE_MISMATCH<br/>
     *         9 PandoraAPIErrorCode.PARAMETER_MISSING<br/>
     *         10 PandoraAPIErrorCode.PARAMETER_VALUE_INVALID<br/>
     *         11 PandoraAPIErrorCode.API_VERSION_NOT_SUPPORTED<br/>
     *         12 PandoraAPIErrorCode.ERROR_LICENSING_RESTRICTIONS<br/>
     *         13 PandoraAPIErrorCode.TIME_OUT_OF_SYNC<br/>
     *         1001 PandoraAPIErrorCode.INVALID_AUTH_TOKEN<br/>
     *         1002 PandoraAPIErrorCode.INVALID_LOGIN<br/>
     *         1003 PandoraAPIErrorCode.USER_NOT_ACTIVE<br/>
     *         1009 PandoraAPIErrorCode.DEVICE_NOT_FOUND
     */
    public int doUserLogin() {
        Logger.d();

        int iReturnCode = checkLicense();
        if (PandoraAPIConstants.CHECK_LICENSE_SUCCESS != iReturnCode) {
            return iReturnCode;
        }
        
        iReturnCode = partnerLogin();
        if (PandoraAPIConstants.PARTNER_LOGIN_SUCCESS != iReturnCode) {
            return iReturnCode;
        }
        
        // Login the user
        Logger.d("Logging in the user: [" + pandoraUsername + "]");
        iReturnCode = PandoraAPIConstants.USER_LOGIN_SUCCESS;
        try {
            authRequester.userLogin(pandoraUsername, pandoraPassword);
            userAuthToken = authRequester.getUserAuthToken();
        } catch (Exception e) {
            try {
                iReturnCode = exceptionHandler(e);
            } catch (Exception ex) {
                Logger.e("User login failed", ex);
                iReturnCode = PandoraAPIConstants.USER_LOGIN_FAILED;
            }
        }

        Debugger.debugReturnCode(iReturnCode);
        return iReturnCode;
    }
    
    /**
     * Creates a link between the user and a deviceId.
     * Once the user has been associated to a device, the device can call auth.userLogin
     * and only provide the deviceId to perform authentication.
     * 
     * @param sDeviceId
     * 
     * @return Success:<br/>
     *         PandoraAPIConstants.ASSOCIATE_SUCCESS<br/>
     *         <br/>
     *         Error:<br/>
     *         PandoraAPIConstants.FAIL_TO_CONNECT_TO_INTERNET<br/>
     *         PandoraAPIConstants.ASSOCIATE_FAILED<br/>
     *         <br/>
     *         0 PandoraAPIErrorCode.INTERNAL_ERROR<br/>
     *         1 PandoraAPIErrorCode.MAINTENANCE_MODE<br/>
     *         2 PandoraAPIErrorCode.URL_PARAM_MISSING_METHOD<br/>
     *         3 PandoraAPIErrorCode.URL_PARAM_MISSING_AUTH_TOKEN<br/>
     *         5 PandoraAPIErrorCode.URL_PARAM_MISSING_USER_ID<br/>
     *         6 PandoraAPIErrorCode.SECURE_PROTOCOL_REQUIRED<br/>
     *         7 PandoraAPIErrorCode.CERTIFICATE_REQUIRED<br/>
     *         8 PandoraAPIErrorCode.PARAMETER_TYPE_MISMATCH<br/>
     *         9 PandoraAPIErrorCode.PARAMETER_MISSING<br/>
     *         12 PandoraAPIErrorCode.ERROR_LICENSING_RESTRICTIONS<br/>
     *         13 PandoraAPIErrorCode.TIME_OUT_OF_SYNC<br/>
     *         1000 PandoraAPIErrorCode.READ_ONLY_MODE<br/>
     *         1001 PandoraAPIErrorCode.INVALID_AUTH_TOKEN
     */
    public int associateDevice(String sDeviceId) {
        Logger.d("sDeviceId: [" + sDeviceId + "]");
        
        if (isDebugReadOnlyMode) {
            Logger.d("Debug Read Only Mode");
            return PandoraAPIErrorCode.READ_ONLY_MODE;
        }

        int iReturnCode = PandoraAPIConstants.ASSOCIATE_SUCCESS;
        try {
            UserRequester userRequester = new UserRequester(protocol, authRequester);
            iReturnCode = userRequester.associateDevice(sDeviceId);
        } catch (Exception e) {
            try {
                iReturnCode = exceptionHandler(e);
            } catch (Exception ex) {
                Logger.e(ex);
                iReturnCode = PandoraAPIConstants.ASSOCIATE_FAILED;
            }
        }
        
        Debugger.debugReturnCode(iReturnCode);
        return iReturnCode;
    }

    /**
     * This method retrieves the list of stations that this user has created
     * and the list of shared stations to which the user has listened.
     * 
     * @return Success:<br/>
     *         PandoraAPIConstants.GET_STATION_LIST_SUCCESS<br/>
     *         <br/>
     *         Error:<br/>
     *         PandoraAPIConstants.FAIL_TO_CONNECT_TO_INTERNET<br/>
     *         PandoraAPIConstants.GET_STATION_LIST_FAILED<br/>
     *         <br/>
     *         0 PandoraAPIErrorCode.INTERNAL_ERROR<br/>
     *         1 PandoraAPIErrorCode.MAINTENANCE_MODE<br/>
     *         2 PandoraAPIErrorCode.URL_PARAM_MISSING_METHOD<br/>
     *         3 PandoraAPIErrorCode.URL_PARAM_MISSING_AUTH_TOKEN<br/>
     *         5 PandoraAPIErrorCode.URL_PARAM_MISSING_USER_ID<br/>
     *         8 PandoraAPIErrorCode.PARAMETER_TYPE_MISMATCH<br/>
     *         9 PandoraAPIErrorCode.PARAMETER_MISSING<br/>
     *         10 PandoraAPIErrorCode.PARAMETER_VALUE_INVALID<br/>
     *         12 PandoraAPIErrorCode.ERROR_LICENSING_RESTRICTIONS<br/>
     *         13 PandoraAPIErrorCode.TIME_OUT_OF_SYNC<br/>
     *         15 PandoraAPIErrorCode.NON_SECURE_PROTOCOL_REQUIRED<br/>
     *         1001 PandoraAPIErrorCode.INVALID_AUTH_TOKEN
     */
    public int getStationList() {
        Logger.d();
        int iReturnCode = PandoraAPIConstants.GET_STATION_LIST_SUCCESS;
        try {
            UserRequester userRequester = new UserRequester(protocol, authRequester);
            alParcelStationList = userRequester.getStationList();
            if (null == alParcelStationList) {
                Logger.d("alParcelStationList is null");
                iReturnCode = PandoraAPIConstants.GET_STATION_LIST_FAILED;
            }
        } catch (Exception e) {
            try {
                iReturnCode = exceptionHandler(e);
            } catch (Exception ex) {
                Logger.e(ex);
                iReturnCode = PandoraAPIConstants.GET_STATION_LIST_FAILED;
            }
        }
        Debugger.debugReturnCode(iReturnCode);
        return iReturnCode;
    }
    
    public ArrayList<ParcelStation> getStationListData() {
        return alParcelStationList;
    }
    
    /**
     * Removes this station from the user's station list.
     * 
     * @param sStationToken
     * 
     * @return Success:<br/>
     *         PandoraAPIConstants.DELETE_STATION_SUCCESS<br/>
     *         <br/>
     *         Error:<br/>
     *         PandoraAPIConstants.FAIL_TO_CONNECT_TO_INTERNET<br/>
     *         PandoraAPIConstants.DELETE_STATION_FAILED<br/>
     *         <br/>
     *         0 PandoraAPIErrorCode.INTERNAL_ERROR<br/>
     *         1 PandoraAPIErrorCode.MAINTENANCE_MODE<br/>
     *         2 PandoraAPIErrorCode.URL_PARAM_MISSING_METHOD<br/>
     *         3 PandoraAPIErrorCode.URL_PARAM_MISSING_AUTH_TOKEN<br/>
     *         5 PandoraAPIErrorCode.URL_PARAM_MISSING_USER_ID<br/>
     *         8 PandoraAPIErrorCode.PARAMETER_TYPE_MISMATCH<br/>
     *         9 PandoraAPIErrorCode.PARAMETER_MISSING<br/>
     *         10 PandoraAPIErrorCode.PARAMETER_VALUE_INVALID<br/>
     *         12 PandoraAPIErrorCode.ERROR_LICENSING_RESTRICTIONS<br/>
     *         13 PandoraAPIErrorCode.TIME_OUT_OF_SYNC<br/>
     *         15 PandoraAPIErrorCode.NON_SECURE_PROTOCOL_REQUIRED<br/>
     *         1000 PandoraAPIErrorCode.READ_ONLY_MODE<br/>
     *         1001 PandoraAPIErrorCode.INVALID_AUTH_TOKEN<br/>
     *         1003 PandoraAPIErrorCode.USER_NOT_ACTIVE<br/>
     *         1004 PandoraAPIErrorCode.USER_NOT_AUTHORIZED<br/>
     *         1006 PandoraAPIErrorCode.STATION_DOES_NOT_EXIST<br/>
     *         1008 PandoraAPIErrorCode.CALL_NOT_ALLOWED
     */
    public int deleteStation(String sStationToken) {
        Logger.d("sStationToken: [" + sStationToken + "]");
        
        if (isDebugReadOnlyMode) {
            Logger.d("Debug Read Only Mode");
            return PandoraAPIErrorCode.READ_ONLY_MODE;
        }
        
        int iReturnCode = PandoraAPIConstants.DELETE_STATION_SUCCESS;

        try {
            StationRequester stationRequester = new StationRequester(protocol, authRequester);
            iReturnCode = stationRequester.deleteStation(sStationToken);
        } catch (Exception e) {
            try {
                iReturnCode = exceptionHandler(e);
            } catch (Exception ex) {
                Logger.e(ex);
                iReturnCode = PandoraAPIConstants.DELETE_STATION_FAILED;
            }
        }
        
        Debugger.debugReturnCode(iReturnCode);
        return iReturnCode;
    }
    
    /**
     * This method retrieves up to a 4-song set of music, chosen from the Pandora library
     * based on the user's music preferences, and could return zero or more audio ad tokens.
     * 
     * @param sStationToken
     * 
     * @return Success:<br/>
     *         PandoraAPIConstants.GET_PLAYLIST_SUCCESS<br/>
     *         <br/>
     *         Error:<br/>
     *         PandoraAPIConstants.FAIL_TO_CONNECT_TO_INTERNET<br/>
     *         PandoraAPIConstants.GET_PLAYLIST_FAILED<br/>
     *         <br/>
     *         0 PandoraAPIErrorCode.INTERNAL_ERROR<br/>
     *         1 PandoraAPIErrorCode.MAINTENANCE_MODE<br/>
     *         2 PandoraAPIErrorCode.URL_PARAM_MISSING_METHOD<br/>
     *         3 PandoraAPIErrorCode.URL_PARAM_MISSING_AUTH_TOKEN<br/>
     *         5 PandoraAPIErrorCode.URL_PARAM_MISSING_USER_ID<br/>
     *         6 PandoraAPIErrorCode.SECURE_PROTOCOL_REQUIRED<br/>
     *         7 PandoraAPIErrorCode.CERTIFICATE_REQUIRED<br/>
     *         8 PandoraAPIErrorCode.PARAMETER_TYPE_MISMATCH<br/>
     *         9 PandoraAPIErrorCode.PARAMETER_MISSING<br/>
     *         10 PandoraAPIErrorCode.PARAMETER_VALUE_INVALID<br/>
     *         12 PandoraAPIErrorCode.ERROR_LICENSING_RESTRICTIONS<br/>
     *         13 PandoraAPIErrorCode.TIME_OUT_OF_SYNC<br/>
     *         1001 PandoraAPIErrorCode.INVALID_AUTH_TOKEN<br/>
     *         1003 PandoraAPIErrorCode.USER_NOT_ACTIVE<br/>
     *         1004 PandoraAPIErrorCode.USER_NOT_AUTHORIZED<br/>
     *         1006 PandoraAPIErrorCode.STATION_DOES_NOT_EXIST
     */
    public int getPlaylist(String sStationToken) {
        Logger.d("sStationToken: [" + sStationToken + "]");
        
        int iReturnCode = PandoraAPIConstants.GET_PLAYLIST_SUCCESS;
        alTrackList = new ArrayList<Item>();
        try {
            StationRequester stationRequester = new StationRequester(protocol, authRequester);
            List<Item> items = stationRequester.getPlaylist(sStationToken);
            for (Item item : items) {
                if (item instanceof ParcelTrack) {
                    ParcelTrack parcelTrack = (ParcelTrack) item;
                    Logger.d("parcelTrack.getSongName(): [" + parcelTrack.getSongName() + "]");
                    alTrackList.add(parcelTrack);
                } else if (item instanceof ParcelAd) {
                    ParcelAd parcelAd = (ParcelAd) item;
                    Logger.d("parcelAd.getAdToken():     [" + parcelAd.getAdToken() + "]");
                    alTrackList.add(parcelAd);
                } else {
                    Logger.d("What is this?");
                }
            }
        } catch (Exception e) {
            try {
                iReturnCode = exceptionHandler(e);
            } catch (Exception ex) {
                Logger.e(ex);
                iReturnCode = PandoraAPIConstants.GET_PLAYLIST_FAILED;
            }
        }
        Debugger.debugReturnCode(iReturnCode);
        return iReturnCode;
    }
    
    public ArrayList<Item> getPlaylistData() {
        return alTrackList;
    }

    /**
     * Bookmarks are a way for Pandora users to note interesting artists and songs.
     * 
     * @param sTrackToken
     * 
     * @return Success:<br/>
     *         PandoraAPIConstants.ADD_ARTIST_BOOKMARK_SUCCESS<br/>
     *         <br/>
     *         Error:<br/>
     *         PandoraAPIConstants.FAIL_TO_CONNECT_TO_INTERNET<br/>
     *         PandoraAPIConstants.ADD_ARTIST_BOOKMARK_FAILED<br/>
     *         <br/>
     *         0 PandoraAPIErrorCode.INTERNAL_ERROR<br/>
     *         1 PandoraAPIErrorCode.MAINTENANCE_MODE<br/>
     *         2 PandoraAPIErrorCode.URL_PARAM_MISSING_METHOD<br/>
     *         3 PandoraAPIErrorCode.URL_PARAM_MISSING_AUTH_TOKEN<br/>
     *         5 PandoraAPIErrorCode.URL_PARAM_MISSING_USER_ID<br/>
     *         8 PandoraAPIErrorCode.PARAMETER_TYPE_MISMATCH<br/>
     *         9 PandoraAPIErrorCode.PARAMETER_MISSING<br/>
     *         10 PandoraAPIErrorCode.PARAMETER_VALUE_INVALID<br/>
     *         12 PandoraAPIErrorCode.ERROR_LICENSING_RESTRICTIONS<br/>
     *         13 PandoraAPIErrorCode.TIME_OUT_OF_SYNC<br/>
     *         15 PandoraAPIErrorCode.NON_SECURE_PROTOCOL_REQUIRED<br/>
     *         1000 PandoraAPIErrorCode.READ_ONLY_MODE<br/>
     *         1001 PandoraAPIErrorCode.INVALID_AUTH_TOKEN<br/>
     *         1003 PandoraAPIErrorCode.USER_NOT_ACTIVE<br/>
     *         1004 PandoraAPIErrorCode.USER_NOT_AUTHORIZED<br/>
     *         1006 PandoraAPIErrorCode.STATION_DOES_NOT_EXIST
     */
    public int addArtistBookmark(String sTrackToken) {
        Logger.d("sTrackToken: [" + sTrackToken + "]");
        
        if (isDebugReadOnlyMode) {
            Logger.d("Debug Read Only Mode");
            return PandoraAPIErrorCode.READ_ONLY_MODE;
        }

        int iReturnCode = PandoraAPIConstants.ADD_ARTIST_BOOKMARK_SUCCESS;
        try {
            BookmarkRequester bookmarkRequester = new BookmarkRequester(protocol, authRequester);
            iReturnCode = bookmarkRequester.addArtistBookmark(sTrackToken);
        } catch (Exception e) {
            try {
                iReturnCode = exceptionHandler(e);
            } catch (Exception ex) {
                Logger.e(ex);
                iReturnCode = PandoraAPIConstants.ADD_ARTIST_BOOKMARK_FAILED;
            }
        }
        Debugger.debugReturnCode(iReturnCode);
        return iReturnCode;
    }

    /**
     * Bookmarks are a way for Pandora users to note interesting artists and songs.
     * 
     * @param sTrackToken
     * 
     * @return Success:<br/>
     *         PandoraAPIConstants.ADD_SONG_BOOKMARK_SUCCESS<br/>
     *         <br/>
     *         Error:<br/>
     *         PandoraAPIConstants.FAIL_TO_CONNECT_TO_INTERNET<br/>
     *         PandoraAPIConstants.ADD_SONG_BOOKMARK_FAILED<br/>
     *         <br/>
     *         0 PandoraAPIErrorCode.INTERNAL_ERROR<br/>
     *         1 PandoraAPIErrorCode.MAINTENANCE_MODE<br/>
     *         2 PandoraAPIErrorCode.URL_PARAM_MISSING_METHOD<br/>
     *         3 PandoraAPIErrorCode.URL_PARAM_MISSING_AUTH_TOKEN<br/>
     *         5 PandoraAPIErrorCode.URL_PARAM_MISSING_USER_ID<br/>
     *         8 PandoraAPIErrorCode.PARAMETER_TYPE_MISMATCH<br/>
     *         9 PandoraAPIErrorCode.PARAMETER_MISSING<br/>
     *         10 PandoraAPIErrorCode.PARAMETER_VALUE_INVALID<br/>
     *         12 PandoraAPIErrorCode.ERROR_LICENSING_RESTRICTIONS<br/>
     *         13 PandoraAPIErrorCode.TIME_OUT_OF_SYNC<br/>
     *         15 PandoraAPIErrorCode.NON_SECURE_PROTOCOL_REQUIRED<br/>
     *         1000 PandoraAPIErrorCode.READ_ONLY_MODE<br/>
     *         1001 PandoraAPIErrorCode.INVALID_AUTH_TOKEN<br/>
     *         1003 PandoraAPIErrorCode.USER_NOT_ACTIVE<br/>
     *         1004 PandoraAPIErrorCode.USER_NOT_AUTHORIZED<br/>
     *         1006 PandoraAPIErrorCode.STATION_DOES_NOT_EXIST
     */
    public int addSongBookmark(String sTrackToken) {
        Logger.d("sTrackToken: [" + sTrackToken + "]");
        
        if (isDebugReadOnlyMode) {
            Logger.d("Debug Read Only Mode");
            return PandoraAPIErrorCode.READ_ONLY_MODE;
        }
        
        int iReturnCode = PandoraAPIConstants.ADD_SONG_BOOKMARK_SUCCESS;
        try {
            BookmarkRequester bookmarkRequester = new BookmarkRequester(protocol, authRequester);
            iReturnCode = bookmarkRequester.addSongBookmark(sTrackToken);
        } catch (Exception e) {
            try {
                iReturnCode = exceptionHandler(e);
            } catch (Exception ex) {
                Logger.e(ex);
                iReturnCode = PandoraAPIConstants.ADD_SONG_BOOKMARK_FAILED;
            }
        }
        Debugger.debugReturnCode(iReturnCode);
        return iReturnCode;
    }
    
    /**
     * Reports user's musical preferences for use in refining the current station.
     * 
     * @param sTrackToken
     * @param isPositive
     * 
     * @return Success:<br/>
     *         PandoraAPIConstants.ADD_FEEDBACK_SUCCESS<br/>
     *         <br/>
     *         Error:<br/>
     *         PandoraAPIConstants.FAIL_TO_CONNECT_TO_INTERNET<br/>
     *         PandoraAPIConstants.ADD_FEEDBACK_FAILED<br/>
     *         <br/>
     *         0 PandoraAPIErrorCode.INTERNAL_ERROR<br/>
     *         1 PandoraAPIErrorCode.MAINTENANCE_MODE<br/>
     *         2 PandoraAPIErrorCode.URL_PARAM_MISSING_METHOD<br/>
     *         3 PandoraAPIErrorCode.URL_PARAM_MISSING_AUTH_TOKEN<br/>
     *         5 PandoraAPIErrorCode.URL_PARAM_MISSING_USER_ID<br/>
     *         8 PandoraAPIErrorCode.PARAMETER_TYPE_MISMATCH<br/>
     *         9 PandoraAPIErrorCode.PARAMETER_MISSING<br/>
     *         10 PandoraAPIErrorCode.PARAMETER_VALUE_INVALID<br/>
     *         12 PandoraAPIErrorCode.ERROR_LICENSING_RESTRICTIONS<br/>
     *         13 PandoraAPIErrorCode.TIME_OUT_OF_SYNC<br/>
     *         15 PandoraAPIErrorCode.NON_SECURE_PROTOCOL_REQUIRED<br/>
     *         1000 PandoraAPIErrorCode.READ_ONLY_MODE<br/>
     *         1001 PandoraAPIErrorCode.INVALID_AUTH_TOKEN<br/>
     *         1003 PandoraAPIErrorCode.USER_NOT_ACTIVE<br/>
     *         1004 PandoraAPIErrorCode.USER_NOT_AUTHORIZED<br/>
     *         1006 PandoraAPIErrorCode.STATION_DOES_NOT_EXIST
     */
    public int addFeedback(String sTrackToken, boolean isPositive) {
        Logger.d("sTrackToken: [" + sTrackToken + "], isPositive: [" + isPositive + "]");
        
        if (isDebugReadOnlyMode) {
            Logger.d("Debug Read Only Mode");
            return PandoraAPIErrorCode.READ_ONLY_MODE;
        }
        
        int iReturnCode = PandoraAPIConstants.ADD_FEEDBACK_SUCCESS;
        try {
            StationRequester stationRequester = new StationRequester(protocol, authRequester);
            stationRequester.addFeedback(sTrackToken, isPositive);
        } catch (Exception e) {
            try {
                iReturnCode = exceptionHandler(e);
            } catch (Exception ex) {
                Logger.e(ex);
                iReturnCode = PandoraAPIConstants.ADD_FEEDBACK_FAILED;
            }
        }
        Debugger.debugReturnCode(iReturnCode);
        return iReturnCode;
    }
    
    /**
     * An end user can request to avoid playing a particular song for one month on any station.
     * 
     * @param sTrackToken
     * 
     * @return Success:<br/>
     *         PandoraAPIConstants.SLEEP_SONG_SUCCESS<br/>
     *         <br/>
     *         Error:<br/>
     *         PandoraAPIConstants.FAIL_TO_CONNECT_TO_INTERNET<br/>
     *         PandoraAPIConstants.SLEEP_SONG_FAILED<br/>
     *         <br/>
     *         0 PandoraAPIErrorCode.INTERNAL_ERROR<br/>
     *         1 PandoraAPIErrorCode.MAINTENANCE_MODE<br/>
     *         2 PandoraAPIErrorCode.URL_PARAM_MISSING_METHOD<br/>
     *         3 PandoraAPIErrorCode.URL_PARAM_MISSING_AUTH_TOKEN<br/>
     *         5 PandoraAPIErrorCode.URL_PARAM_MISSING_USER_ID<br/>
     *         8 PandoraAPIErrorCode.PARAMETER_TYPE_MISMATCH<br/>
     *         9 PandoraAPIErrorCode.PARAMETER_MISSING<br/>
     *         10 PandoraAPIErrorCode.PARAMETER_VALUE_INVALID<br/>
     *         12 PandoraAPIErrorCode.ERROR_LICENSING_RESTRICTIONS<br/>
     *         13 PandoraAPIErrorCode.TIME_OUT_OF_SYNC<br/>
     *         15 PandoraAPIErrorCode.NON_SECURE_PROTOCOL_REQUIRED<br/>
     *         1000 PandoraAPIErrorCode.READ_ONLY_MODE<br/>
     *         1001 PandoraAPIErrorCode.INVALID_AUTH_TOKEN<br/>
     *         1003 PandoraAPIErrorCode.USER_NOT_ACTIVE<br/>
     *         1004 PandoraAPIErrorCode.USER_NOT_AUTHORIZED<br/>
     *         1006 PandoraAPIErrorCode.STATION_DOES_NOT_EXIST
     */
    public int sleepSong(String sTrackToken) {
        Logger.d("sTrackToken: [" + sTrackToken + "]");
        
        if (isDebugReadOnlyMode) {
            Logger.d("Debug Read Only Mode");
            return PandoraAPIErrorCode.READ_ONLY_MODE;
        }
        
        int iReturnCode = PandoraAPIConstants.SLEEP_SONG_SUCCESS;
        try {
            UserRequester userRequester = new UserRequester(protocol, authRequester);
            userRequester.sleepSong(sTrackToken);
        } catch (Exception e) {
            try {
                iReturnCode = exceptionHandler(e);
            } catch (Exception ex) {
                Logger.e(ex);
                iReturnCode = PandoraAPIConstants.SLEEP_SONG_FAILED;
            }
        }
        Debugger.debugReturnCode(iReturnCode);
        return iReturnCode;
    }

    /**
     * This method retrieves information about which of the attributes
     * are most relevant for this particular track when chosen for this
     * particular station.
     * 
     * @param sTrackToken
     * 
     * @return Success:<br/>
     *         PandoraAPIConstants.EXPLAIN_TRACK_SUCCESS<br/>
     *         <br/>
     *         Error:<br/>
     *         PandoraAPIConstants.FAIL_TO_CONNECT_TO_INTERNET<br/>
     *         PandoraAPIConstants.EXPLAIN_TRACK_FAILED<br/>
     *         <br/>
     *         0 PandoraAPIErrorCode.INTERNAL_ERROR<br/>
     *         1 PandoraAPIErrorCode.MAINTENANCE_MODE<br/>
     *         2 PandoraAPIErrorCode.URL_PARAM_MISSING_METHOD<br/>
     *         3 PandoraAPIErrorCode.URL_PARAM_MISSING_AUTH_TOKEN<br/>
     *         5 PandoraAPIErrorCode.URL_PARAM_MISSING_USER_ID<br/>
     *         8 PandoraAPIErrorCode.PARAMETER_TYPE_MISMATCH<br/>
     *         9 PandoraAPIErrorCode.PARAMETER_MISSING<br/>
     *         10 PandoraAPIErrorCode.PARAMETER_VALUE_INVALID<br/>
     *         12 PandoraAPIErrorCode.ERROR_LICENSING_RESTRICTIONS<br/>
     *         13 PandoraAPIErrorCode.TIME_OUT_OF_SYNC<br/>
     *         15 PandoraAPIErrorCode.NON_SECURE_PROTOCOL_REQUIRED<br/>
     *         1001 PandoraAPIErrorCode.INVALID_AUTH_TOKEN<br/>
     *         1003 PandoraAPIErrorCode.USER_NOT_ACTIVE<br/>
     *         1004 PandoraAPIErrorCode.USER_NOT_AUTHORIZED<br/>
     *         1006 PandoraAPIErrorCode.STATION_DOES_NOT_EXIST
     */
    public int explainTrack(String sTrackToken) {
        Logger.d("sTrackToken: [" + sTrackToken + "]");
        
        int iReturnCode = PandoraAPIConstants.EXPLAIN_TRACK_SUCCESS;
        try {
            TrackRequester trackRequester = new TrackRequester(protocol, authRequester);
            alParcelExplanation = trackRequester.explainTrack(sTrackToken);
            if (null == alParcelExplanation) {
                Logger.d("alParcelExplanation is null");
                iReturnCode = PandoraAPIConstants.EXPLAIN_TRACK_FAILED;
            }
        } catch (Exception e) {
            try {
                iReturnCode = exceptionHandler(e);
            } catch (Exception ex) {
                Logger.e(ex);
                iReturnCode = PandoraAPIConstants.EXPLAIN_TRACK_FAILED;
            }
        }
        Debugger.debugReturnCode(iReturnCode);
        return iReturnCode;
    }
    
    public ArrayList<ParcelExplanation> getExplainTrackData() {
        return alParcelExplanation;
    }

    /**
     * This method takes a string entered by the user, and compares it to song names and artist
     * names in the Pandora library.
     * 
     * @param sSearchText
     * 
     * @return Success:<br/>
     *         PandoraAPIConstants.MUSIC_SEARCH_SUCCESS<br/>
     *         <br/>
     *         Error:<br/>
     *         PandoraAPIConstants.FAIL_TO_CONNECT_TO_INTERNET<br/>
     *         PandoraAPIConstants.MUSIC_SEARCH_FAILED<br/>
     *         <br/>
     *         0 PandoraAPIErrorCode.INTERNAL_ERROR<br/>
     *         1 PandoraAPIErrorCode.MAINTENANCE_MODE<br/>
     *         2 PandoraAPIErrorCode.URL_PARAM_MISSING_METHOD<br/>
     *         3 PandoraAPIErrorCode.URL_PARAM_MISSING_AUTH_TOKEN<br/>
     *         5 PandoraAPIErrorCode.URL_PARAM_MISSING_USER_ID<br/>
     *         8 PandoraAPIErrorCode.PARAMETER_TYPE_MISMATCH<br/>
     *         9 PandoraAPIErrorCode.PARAMETER_MISSING<br/>
     *         10 PandoraAPIErrorCode.PARAMETER_VALUE_INVALID<br/>
     *         12 PandoraAPIErrorCode.ERROR_LICENSING_RESTRICTIONS<br/>
     *         13 PandoraAPIErrorCode.TIME_OUT_OF_SYNC<br/>
     *         15 PandoraAPIErrorCode.NON_SECURE_PROTOCOL_REQUIRED<br/>
     *         1001 PandoraAPIErrorCode.INVALID_AUTH_TOKEN<br/>
     *         1003 PandoraAPIErrorCode.USER_NOT_ACTIVE
     */
    public int musicSearch(String sSearchText) {
        Logger.d("sSearchText: [" + sSearchText + "]");

        int iReturnCode = PandoraAPIConstants.MUSIC_SEARCH_SUCCESS;
        try {
            MusicRequester musicRequester = new MusicRequester(protocol, authRequester);
            parcelMusicSearchResult = musicRequester.search(sSearchText);
        } catch (Exception e) {
            try {
                iReturnCode = exceptionHandler(e);
            } catch (Exception ex) {
                Logger.e(ex);
                iReturnCode = PandoraAPIConstants.MUSIC_SEARCH_FAILED;
            }
        }
        Debugger.debugReturnCode(iReturnCode);
        return iReturnCode;
    }
    
    public ParcelSearchResult getMusicSearchData() {
        return parcelMusicSearchResult;
    }
    
    public int musicSearchAutoComplete(String sSearchText) {
        Logger.d("sSearchText: [" + sSearchText + "]");

        int iReturnCode = PandoraAPIConstants.MUSIC_SEARCH_AUTO_COMPLETE_SUCCESS;
        try {
            AutoCompleteRequester autoCompleteRequester = new AutoCompleteRequester(protocolAutoComplete, authRequester);
            parcelMusicSearchAutoCompleteResult = autoCompleteRequester.search(sSearchText);
        } catch (Exception e) {
            try {
                iReturnCode = exceptionHandler(e);
            } catch (Exception ex) {
                Logger.e(ex);
                iReturnCode = PandoraAPIConstants.MUSIC_SEARCH_AUTO_COMPLETE_FAILED;
            }
        }
        Debugger.debugReturnCode(iReturnCode);
        return iReturnCode;
    }
    
    public ParcelSearchResult getMusicSearchAutoCompleteData() {
        return parcelMusicSearchAutoCompleteResult;
    }

    /**
     * This method creates a new station based on the results of a search (a musicToken) or
     * based on a track's artist or song (a trackToken).
     * 
     * @param sMusicToken
     * 
     * @return Success:<br/>
     *         PandoraAPIConstants.CREATE_STATION_SUCCESS<br/>
     *         <br/>
     *         Error:<br/>
     *         PandoraAPIConstants.FAIL_TO_CONNECT_TO_INTERNET<br/>
     *         PandoraAPIConstants.CREATE_STATION_FAILED<br/>
     *         <br/>
     *         0 PandoraAPIErrorCode.INTERNAL_ERROR<br/>
     *         1 PandoraAPIErrorCode.MAINTENANCE_MODE<br/>
     *         2 PandoraAPIErrorCode.URL_PARAM_MISSING_METHOD<br/>
     *         3 PandoraAPIErrorCode.URL_PARAM_MISSING_AUTH_TOKEN<br/>
     *         5 PandoraAPIErrorCode.URL_PARAM_MISSING_USER_ID<br/>
     *         8 PandoraAPIErrorCode.PARAMETER_TYPE_MISMATCH<br/>
     *         9 PandoraAPIErrorCode.PARAMETER_MISSING<br/>
     *         10 PandoraAPIErrorCode.PARAMETER_VALUE_INVALID<br/>
     *         12 PandoraAPIErrorCode.ERROR_LICENSING_RESTRICTIONS<br/>
     *         13 PandoraAPIErrorCode.TIME_OUT_OF_SYNC<br/>
     *         15 PandoraAPIErrorCode.NON_SECURE_PROTOCOL_REQUIRED<br/>
     *         1000 PandoraAPIErrorCode.READ_ONLY_MODE<br/>
     *         1001 PandoraAPIErrorCode.INVALID_AUTH_TOKEN<br/>
     *         1003 PandoraAPIErrorCode.USER_NOT_ACTIVE<br/>
     *         1005 PandoraAPIErrorCode.MAX_STATIONS_REACHED
     */
    public int createStationByMusicToken(String sMusicToken) {
        Logger.d("sMusicToken: [" + sMusicToken + "]");
        
        if (isDebugReadOnlyMode) {
            Logger.d("Debug Read Only Mode");
            return PandoraAPIErrorCode.READ_ONLY_MODE;
        }

        int iReturnCode = PandoraAPIConstants.CREATE_STATION_SUCCESS;
        try {
            StationRequester stationRequester = new StationRequester(protocol, authRequester);
            parcelStation = stationRequester.createStationByMusicToken(sMusicToken, false);
        } catch (Exception e) {
            try {
                iReturnCode = exceptionHandler(e);
            } catch (Exception ex) {
                Logger.e(ex);
                iReturnCode = PandoraAPIConstants.CREATE_STATION_FAILED;
            }
        }
        Debugger.debugReturnCode(iReturnCode);
        return iReturnCode;
    }
    
    public ParcelStation getCreateStationData() {
        return parcelStation;
    }
    
    /**
     * This method should be called at the time the audio ad is going to be played.
     * If there is an audio ad to be played, the audioUrl result will be returned.
     * 
     * @param sAdTokenInput
     * 
     * @return Success:<br/>
     *         PandoraAPIConstants.CREATE_STATION_SUCCESS<br/>
     *         <br/>
     *         Error:<br/>
     *         PandoraAPIConstants.FAIL_TO_CONNECT_TO_INTERNET<br/>
     *         PandoraAPIConstants.CREATE_STATION_FAILED<br/>
     *         <br/>
     *         0 PandoraAPIErrorCode.INTERNAL_ERROR<br/>
     *         1 PandoraAPIErrorCode.MAINTENANCE_MODE<br/>
     *         2 PandoraAPIErrorCode.URL_PARAM_MISSING_METHOD<br/>
     *         3 PandoraAPIErrorCode.URL_PARAM_MISSING_AUTH_TOKEN<br/>
     *         5 PandoraAPIErrorCode.URL_PARAM_MISSING_USER_ID<br/>
     *         8 PandoraAPIErrorCode.PARAMETER_TYPE_MISMATCH<br/>
     *         9 PandoraAPIErrorCode.PARAMETER_MISSING<br/>
     *         10 PandoraAPIErrorCode.PARAMETER_VALUE_INVALID<br/>
     *         12 PandoraAPIErrorCode.ERROR_LICENSING_RESTRICTIONS<br/>
     *         13 PandoraAPIErrorCode.TIME_OUT_OF_SYNC<br/>
     *         15 PandoraAPIErrorCode.NON_SECURE_PROTOCOL_REQUIRED<br/>
     *         1001 PandoraAPIErrorCode.INVALID_AUTH_TOKEN<br/>
     *         1030 PandoraAPIErrorCode.INVALID_AD_TOKEN
     */
    public int getAdMetadata(String sAdTokenInput) {
        Logger.d("sAdTokenInput: [" + sAdTokenInput + "]");
        
        int iReturnCode = PandoraAPIConstants.GET_AD_METADATA_SUCCESS;
        try {
            AdRequester adRequester = new AdRequester(protocol, authRequester);
            itemAd = adRequester.getAdMetadata(sAdTokenInput);
        } catch (Exception e) {
            try {
                iReturnCode = exceptionHandler(e);
            } catch (Exception ex) {
                Logger.e(ex);
                iReturnCode = PandoraAPIConstants.GET_AD_METADATA_FAILED;
            }
        }
        Debugger.debugReturnCode(iReturnCode);
        return iReturnCode;
    }
    
    public Item getAdMetadataData() {
        return itemAd;
    }
    
    private int exceptionHandler(Exception e) throws Exception {
        int iReturnCode = PandoraAPIConstants.UNKNOWN_EXCEPTION;
        
        if (e instanceof PandoraBaseException) {
            PandoraBaseException pandoraBaseException = (PandoraBaseException) e;
            iReturnCode = pandoraBaseException.getErrorCode();
        } else if (e instanceof CCPSocketTimeoutException) {
            Logger.e("CCPSocketTimeoutException");
            iReturnCode = PandoraAPIConstants.FAIL_TO_CONNECT_TO_INTERNET;
        } else if (e instanceof CCPUnknownHostException) {
            Logger.e("CCPUnknownHostException");
            iReturnCode = PandoraAPIConstants.FAIL_TO_CONNECT_TO_PANDORA;
        } else if (e instanceof CCPExecuteIOException) {
            Logger.e("CCPExecuteIOException");
            iReturnCode = PandoraAPIConstants.NETWORK_UNREACHABLE;
        } else {
            throw e;
        }
        
        return iReturnCode;
    }
    
    private void loadProperties() {
        //APIProperties.generateAllEncryption(); // TODO generate all encryption
        
        jsonApiUrl = APIProperties.getApiUrlJson();
        jsonSecureApiUrl = APIProperties.getSecureApiUrlJson();
        syncTimeKey = APIProperties.getSyncTimeKey();
        requestKey = APIProperties.getRequestKey();
        partnerUsername = APIProperties.getPartnerUsername();
        partnerPassword = APIProperties.getPartnerPassword();
        deviceModel = APIProperties.getDeviceModel();
    }

    /*
    private void loadProperties() throws IOException {
        Properties properties = new Properties();
        InputStream propertyStream = this.getClass().getClassLoader().getResourceAsStream("client.properties");
        if (propertyStream == null) {
            throw new RuntimeException("The \"client.properties\" file must be included in the classpath");
        }
        properties.load(propertyStream);

        apiProtocolName = getProperty(properties, "api.protocol");
        jsonApiUrl = getProperty(properties, "api.url.json");
        jsonSecureApiUrl = getProperty(properties, "secure.api.url.json");
        xmlrpcApiUrl = getProperty(properties, "api.url.xmlrpc");
        xmlrpcSecureApiUrl = getProperty(properties, "secure.api.url.xmlrpc");
        syncTimeKey = getProperty(properties, "sync.time.key");
        requestKey = getProperty(properties, "request.key");
        partnerUsername = getProperty(properties, "partner.username");
        partnerPassword = getProperty(properties, "partner.password");
        // pandoraUsername = getProperty(properties, "pandora.username");
        // pandoraPassword = getProperty(properties, "pandora.password");
        deviceModel = getProperty(properties, "device.model");
    }

    private String getProperty(Properties properties, String key) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().length() == 0) {
            throw new RuntimeException("A required property in is missing from the client properties: " + key + ".  Check the values the classes/client.properties file.");
        }
        return value;
    }
    */

}
