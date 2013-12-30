package comp.hp.ij.common.service.pandora.api;

import java.util.HashMap;

import comp.hp.ij.common.service.pandora.data.ParcelAd;

public class AdRequester extends BaseApi {
    private AuthRequester authRequester;
    
    public AdRequester(Protocol protocol, AuthRequester authRequesterInput) {
        super(protocol);
        authRequester = authRequesterInput;
    }
    
    public Item getAdMetadata(String sAdTokenInput) throws Exception {
        Item itemReturn = new ParcelAd();
        
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("adToken", sAdTokenInput);
        params.put("userAuthToken", authRequester.getUserAuthToken());
        params.put("syncTime", authRequester.getSyncTime());

        HashMap<String, String> urlParameters = getUrlParameters("ad.getAdMetadata");

        // if there is something wrong it will throw exception here
        Result result = protocol.sendRequest(true, urlParameters, params);
        
        ((ParcelAd) itemReturn).setAdToken(sAdTokenInput);
        ((ParcelAd) itemReturn).setResult(result);
        
        return itemReturn;
    }
    
    public HashMap<String, String> getUrlParameters(String method) {
        HashMap<String, String> parameters = super.getUrlParameters(method);
        parameters.put("auth_token", authRequester.getUserAuthToken());
        parameters.put("partner_id", authRequester.getPartnerId());
        parameters.put("user_id", authRequester.getUserId());
        return parameters;
    }
    
}
