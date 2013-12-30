package comp.hp.ij.common.service.pandora.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import comp.hp.ij.common.service.pandora.data.ParcelExplanation;
import comp.hp.ij.common.service.pandora.util.Logger;

/**
 * Performs User API calls
 */
public class TrackRequester extends BaseApi {
	protected AuthRequester authRequester;

	public TrackRequester(Protocol protocol, AuthRequester authRequester) {
		super(protocol);
		this.authRequester = authRequester;
	}

	@SuppressWarnings("unchecked")
    public ArrayList<ParcelExplanation> explainTrack(String sTrackToken) throws Exception {
		Logger.d();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("trackToken", sTrackToken);
		params.put("userAuthToken", authRequester.getUserAuthToken());
		params.put("syncTime", authRequester.getSyncTime());

		HashMap<String, String> urlParameters = getUrlParameters("track.explainTrack");
		urlParameters.put("auth_token", authRequester.getUserAuthToken());

		Result result = protocol.sendRequest(true, urlParameters, params);

		ArrayList<ParcelExplanation> resultList = new ArrayList<ParcelExplanation>();

		ArrayList results = result.getArray("explanations");

		if (results.size() > 0) {
			for (Iterator iterator = results.iterator(); iterator.hasNext();) {
				Result explanationResult = (Result) iterator.next();
				ParcelExplanation parcelExplanation = new ParcelExplanation();
				String traitName = explanationResult
						.getString("focusTraitName");				
				String traitId = explanationResult.getString("focusTraitId");
				parcelExplanation.setFocusTraitName(traitName);
				parcelExplanation.setFocusTraitId(traitId);
				resultList.add(parcelExplanation);
			}
		}
		Logger.d("explanation count: [" + resultList.size() + "]");

		return resultList;

	}

	public HashMap<String, String> getUrlParameters(String method) {
		HashMap<String, String> parameters = super.getUrlParameters(method);
		parameters.put("partner_id", authRequester.getPartnerId());
		parameters.put("user_id", authRequester.getUserId());
		parameters.put("auth_token", authRequester.getUserAuthToken());
		return parameters;
	}
}