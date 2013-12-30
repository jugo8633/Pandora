package comp.hp.ij.common.service.pandora.api;

import java.util.HashMap;

import comp.hp.ij.common.service.pandora.data.ParcelDeviceActivationData;
import comp.hp.ij.common.service.pandora.util.Logger;

/**
 * Performs User API calls
 */
public class DeviceRequester extends BaseApi {
	private AuthRequester authRequester;

	public DeviceRequester(Protocol protocol, AuthRequester authRequesterInput) {
		super(protocol);
		authRequester = authRequesterInput;
	}

	// Generate Activation Code and Url
	public ParcelDeviceActivationData generateDeviceActivationCode(String sDeviceId) throws Exception {
	    Logger.d();
	    
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("deviceId", sDeviceId);
		params.put("partnerAuthToken", authRequester.getPartnerAuthToken());
		params.put("syncTime", authRequester.getSyncTime());

		HashMap<String, String> urlParameters = getUrlParameters("device.generateDeviceActivationCode");

		// if there is something wrong it will throw exception here
		Result result = protocol.sendSecureRequest(true, urlParameters, params);
		
		String sActivationCode = result.getString("activationCode");
		String sActivationUrl = result.getString("activationUrl");
		Logger.d("sActivationCode: [" + sActivationCode + "]");
		Logger.d("sActivationUrl:  [" + sActivationUrl + "]");

		ParcelDeviceActivationData parcelDeviceActivationData = new ParcelDeviceActivationData();
		parcelDeviceActivationData.setActivationCode(sActivationCode);
		parcelDeviceActivationData.setActivationUrl(sActivationUrl);

		return parcelDeviceActivationData;
	}

	public int disassociateDevice(String sDeviceId) throws Exception {
	    Logger.d();
	    
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("deviceId", sDeviceId);
		params.put("partnerAuthToken", authRequester.getPartnerAuthToken());
		params.put("syncTime", authRequester.getSyncTime());

		HashMap<String, String> urlParameters = getUrlParameters("device.disassociateDevice");

		// if there is something wrong it will throw exception here
        protocol.sendSecureRequest(true, urlParameters, params);
        
        // return PandoraAPIConstants.DISASSOCIATE_SUCCESS if no exception
        return PandoraAPIConstants.DISASSOCIATE_SUCCESS;
	}

    public HashMap<String, String> getUrlParameters(String method) {
        HashMap<String, String> parameters = super.getUrlParameters(method);
        parameters.put("auth_token", authRequester.getPartnerAuthToken());
        parameters.put("partner_id", authRequester.getPartnerId());
        return parameters;
    }
}