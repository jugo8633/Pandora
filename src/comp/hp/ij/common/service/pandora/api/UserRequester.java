package comp.hp.ij.common.service.pandora.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import comp.hp.ij.common.service.pandora.data.ParcelStation;
import comp.hp.ij.common.service.pandora.util.Logger;

/**
 * Performs User API calls
 */
public class UserRequester extends BaseApi {
	private AuthRequester authRequester;

	public UserRequester(Protocol protocol, AuthRequester authRequesterInput) {
		super(protocol);
		authRequester = authRequesterInput;
	}

	// Associate device
	public int associateDevice(String sDeviceId) throws Exception {
		Logger.d();

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("deviceId", sDeviceId);
		params.put("userAuthToken", authRequester.getUserAuthToken());
		params.put("syncTime", authRequester.getSyncTime());
		
		HashMap<String, String> urlParameters = getUrlParameters("user.associateDevice");
		
		// if there is something wrong it will throw exception here
		protocol.sendSecureRequest(true, urlParameters, params);
		
		// return PandoraAPIConstants.ASSOCIATE_SUCCESS if no exception
		return PandoraAPIConstants.ASSOCIATE_SUCCESS;
	}

	public int sleepSong(String sTrackToken) throws Exception {
		Logger.d();

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("trackToken", sTrackToken);
		params.put("userAuthToken", authRequester.getUserAuthToken());
		params.put("syncTime", authRequester.getSyncTime());
		
		HashMap<String, String> urlParameters = getUrlParameters("user.sleepSong");

		// if there is something wrong it will throw exception here
		protocol.sendRequest(true, urlParameters, params);
		
		// return PandoraAPIConstants.SLEEP_SONG_SUCCESS if no exception
        return PandoraAPIConstants.SLEEP_SONG_SUCCESS;
	}

	@SuppressWarnings("unchecked")
    public ArrayList<ParcelStation> getStationList() throws Exception {
	    ArrayList<ParcelStation> alStationList = new ArrayList<ParcelStation>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userAuthToken", authRequester.getUserAuthToken());
		params.put("syncTime", authRequester.getSyncTime());

		HashMap<String, String> urlParameters = getUrlParameters("user.getStationList");

		Result result = protocol.sendRequest(true, urlParameters, params);
		ArrayList results = result.getArray("stations");

		if (0 < results.size()) {
			for (Iterator iterator = results.iterator(); iterator.hasNext();) {
				Result stationResult = (Result) iterator.next();
				ParcelStation parcelStation = new ParcelStation();
				parcelStation.setResult(stationResult);
				alStationList.add(parcelStation);
			}
		}
		return alStationList;
	}

	public HashMap<String, String> getUrlParameters(String method) {
		HashMap<String, String> parameters = super.getUrlParameters(method);
		parameters.put("auth_token", authRequester.getUserAuthToken());
		parameters.put("partner_id", authRequester.getPartnerId());
		parameters.put("user_id", authRequester.getUserId());
		return parameters;
	}
}