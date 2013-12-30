package comp.hp.ij.common.service.pandora.api;

import java.util.HashMap;

/**
 * Performs User API calls.
 */
public class BookmarkRequester extends BaseApi {
    protected AuthRequester authRequester;

    /**
     * Constructor.
     * 
     * @param protocol protocol
     * @param authRequester authRequester
     */
    public BookmarkRequester(Protocol protocol, AuthRequester authRequester) {
        super(protocol);
        this.authRequester = authRequester;
    }

    /**
     * 
     * @param trackToken trackToken
     * 
     * @return add artist bookmark return value
     * 
     * @throws Exception Exception
     */
    public int addArtistBookmark(String trackToken) throws Exception {

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("trackToken", trackToken);
        params.put("userAuthToken", authRequester.getUserAuthToken());
        params.put("syncTime", authRequester.getSyncTime());

        HashMap<String, String> urlParameters = getUrlParameters("bookmark.addArtistBookmark");

        Result result = protocol.sendRequest(true, urlParameters, params);
        String stat = result.getString("stat");
        if (null == stat || !"ok".equalsIgnoreCase(stat)) {
            return PandoraAPIConstants.ADD_ARTIST_BOOKMARK_FAILED;
        } else {
            return PandoraAPIConstants.ADD_ARTIST_BOOKMARK_SUCCESS;
        }
    }

    /**
     * 
     * @param trackToken trackToken
     * 
     * @return add song bookmark return value
     * 
     * @throws Exception Exception
     */
    public int addSongBookmark(String trackToken) throws Exception {

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("trackToken", trackToken);
        params.put("userAuthToken", authRequester.getUserAuthToken());
        params.put("syncTime", authRequester.getSyncTime());

        HashMap<String, String> urlParameters = getUrlParameters("bookmark.addSongBookmark");

        Result result = protocol.sendRequest(true, urlParameters, params);
        String stat = result.getString("stat");
        if (null == stat || !"ok".equalsIgnoreCase(stat)) {
            return PandoraAPIConstants.ADD_SONG_BOOKMARK_FAILED;
        } else {
            return PandoraAPIConstants.ADD_SONG_BOOKMARK_SUCCESS;
        }
    }

    @Override
    public HashMap<String, String> getUrlParameters(String method) {
        HashMap<String, String> parameters = super.getUrlParameters(method);
        parameters.put("partner_id", authRequester.getPartnerId());
        parameters.put("user_id", authRequester.getUserId());
        parameters.put("auth_token", authRequester.getUserAuthToken());
        return parameters;
    }
}