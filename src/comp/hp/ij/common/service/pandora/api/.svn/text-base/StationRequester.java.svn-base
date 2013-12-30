package comp.hp.ij.common.service.pandora.api;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import comp.hp.ij.common.service.pandora.data.ParcelAd;
import comp.hp.ij.common.service.pandora.data.ParcelStation;
import comp.hp.ij.common.service.pandora.data.ParcelTrack;
import comp.hp.ij.common.service.pandora.util.Logger;

/**
 * Performs Station API calls
 */
public class StationRequester extends BaseApi {
    private AuthRequester authRequester;
    
	public StationRequester(Protocol protocol, AuthRequester authRequesterInput) {
		super(protocol);
		authRequester = authRequesterInput;
	}

	public int addFeedback(String sTrackToken, boolean isPositive) throws Exception {
	    Logger.d();
	    
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userAuthToken", authRequester.getUserAuthToken());
		params.put("syncTime", authRequester.getSyncTime());
		params.put("trackToken", sTrackToken);
		params.put("isPositive", isPositive);

		HashMap<String, String> urlParameters = getUrlParameters("station.addFeedback");

		Result result = protocol.sendRequest(true, urlParameters, params);		
		String stat = result.getString("stat");

        if (null == stat || !"ok".equalsIgnoreCase(stat)) {
            return PandoraAPIConstants.ADD_FEEDBACK_FAILED;
        } else {
            return PandoraAPIConstants.ADD_FEEDBACK_SUCCESS;
        }
	}

	public int deleteStation(String stationToken) throws Exception {
	    Logger.d();
	    
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userAuthToken", authRequester.getUserAuthToken());
		params.put("syncTime", authRequester.getSyncTime());
		params.put("stationToken", stationToken);

		HashMap<String, String> urlParameters = getUrlParameters("station.deleteStation");
		
		Result result = protocol.sendRequest(true, urlParameters, params);
		String stat = result.getString("stat");

        if (null == stat || !"ok".equalsIgnoreCase(stat)) {
            return PandoraAPIConstants.DELETE_STATION_FAILED;
        } else {
            return PandoraAPIConstants.DELETE_STATION_SUCCESS;
        }
	}

	public ParcelStation createStationByMusicToken(String sMusicToken, boolean returnArtUrl) throws Exception {
	    Logger.d();
	    
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userAuthToken", authRequester.getUserAuthToken());
		params.put("syncTime", authRequester.getSyncTime());
		params.put("musicToken", sMusicToken);
		params.put("includeStationArtUrl", returnArtUrl);

		HashMap<String, String> urlParameters = getUrlParameters("station.createStation");

		Result result = protocol.sendRequest(true, urlParameters, params);
		ParcelStation pStation = new ParcelStation();
		pStation.setResult(result);

		return pStation;
	}

	@SuppressWarnings("unchecked")
    public List<Item> getPlaylist(String stationToken) throws Exception {
		List<Item> items = new ArrayList<Item>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userAuthToken", authRequester.getUserAuthToken());
		params.put("syncTime", authRequester.getSyncTime());
		params.put("stationToken", stationToken);

		HashMap<String, String> urlParameters = getUrlParameters("station.getPlaylist");

		Result result = protocol.sendSecureRequest(true, urlParameters, params);
		ArrayList results = result.getArray("items");

		if (0 < results.size()) {
			for (Iterator iterator = results.iterator(); iterator.hasNext();) {
				Result item = (Result) iterator.next();
				Item resultItem = null;
				/*
				if (item.containsKey("adToken")) {
					resultItem = new Ad(item);
				} else {
					resultItem = new Track(item);
				}
				items.add(resultItem);
				*/
				if (item.containsKey("adToken")) {
				    resultItem = new ParcelAd();
                    ((ParcelAd) resultItem).setAdToken(item);
                    items.add(resultItem);
				} else {
                    resultItem = new ParcelTrack();
                    ((ParcelTrack) resultItem).setResult(item);
                    items.add(resultItem);
				}
			}
		}
		return items;
	}
	
	public HashMap<String, String> getUrlParameters(String method) {
	    HashMap<String, String> parameters = super.getUrlParameters(method);
	    parameters.put("auth_token", authRequester.getUserAuthToken());
	    parameters.put("partner_id", authRequester.getPartnerId());
	    parameters.put("user_id", authRequester.getUserId());
	    return parameters;
	}
	   
}
