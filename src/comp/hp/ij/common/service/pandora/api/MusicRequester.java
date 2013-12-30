package comp.hp.ij.common.service.pandora.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import comp.hp.ij.common.service.pandora.data.ParcelArtist;
import comp.hp.ij.common.service.pandora.data.ParcelSearchResult;
import comp.hp.ij.common.service.pandora.data.ParcelSong;
import comp.hp.ij.common.service.pandora.util.Logger;

/**
 * Performs User API calls
 */
public class MusicRequester extends BaseApi {
	protected AuthRequester authRequester;

	public MusicRequester(Protocol protocol, AuthRequester authRequester) {
		super(protocol);
		this.authRequester = authRequester;
	}

	@SuppressWarnings("unchecked")
	public ParcelSearchResult search(String sSearchText) throws Exception {
		Logger.d("sSearchText: [" + sSearchText + "]");

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("searchText", sSearchText);
		params.put("userAuthToken", authRequester.getUserAuthToken());
		params.put("syncTime", authRequester.getSyncTime());

		HashMap<String, String> urlParameters = getUrlParameters("music.search");

		Result result = protocol.sendRequest(true, urlParameters, params);
		
		ArrayList jsonArtistList = result.getArray("artists");
		ArrayList jsonSongList = result.getArray("songs");

		ArrayList<ParcelArtist> artistList = new ArrayList<ParcelArtist>();
		ArrayList<ParcelSong> songList = new ArrayList<ParcelSong>();

		if (0 < jsonArtistList.size()) {

			for (Iterator iterator = jsonArtistList.iterator(); iterator.hasNext();) {
				Result artistResult = (Result) iterator.next();
				ParcelArtist parcelArtist = new ParcelArtist();
				String artistName = artistResult.getString("artistName");
				String musicToken = artistResult.getString("musicToken");
				String score = artistResult.getString("score");
				String likelyMatch = artistResult.getString("likelyMatch");

				parcelArtist.setArtistName(artistName);
				parcelArtist.setMusicToken(musicToken);
				parcelArtist.setScore(score);
				parcelArtist.setLikelyMatch(likelyMatch);

				artistList.add(parcelArtist);
			}

		} else {
			Logger.d("artists array size is 0");
		}

		if (0 < jsonSongList.size()) {

			for (Iterator iterator = jsonSongList.iterator(); iterator.hasNext();) {
				Result songResult = (Result) iterator.next();
				ParcelSong parcelSong = new ParcelSong();
				String songName = songResult.getString("songName");
				String artistName = songResult.getString("artistName");
				String musicToken = songResult.getString("musicToken");
				String score = songResult.getString("score");

				parcelSong.setSongName(songName);
				parcelSong.setArtistName(artistName);
				parcelSong.setMusicToken(musicToken);
				parcelSong.setScore(score);
				
				songList.add(parcelSong);
			}

		} else {
			Logger.d("songs array size is 0");
		}

		ParcelSearchResult searchResult = new ParcelSearchResult();
		searchResult.setArtists(artistList);
		searchResult.setSongs(songList);

		if (searchResult.getArtists() != null) {
			Logger.d("artists count: [" + searchResult.getArtists().size() + "]");
		}

		if (searchResult.getSongs() != null) {
			Logger.d("songs count:   [" + searchResult.getSongs().size() + "]");
		}

		return searchResult;

	}

	public HashMap<String, String> getUrlParameters(String method) {
		HashMap<String, String> parameters = super.getUrlParameters(method);
		parameters.put("partner_id", authRequester.getPartnerId());
		parameters.put("user_id", authRequester.getUserId());
		parameters.put("auth_token", authRequester.getUserAuthToken());
		return parameters;
	}
}