package comp.hp.ij.common.service.pandora.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import comp.hp.ij.common.service.pandora.data.ParcelArtist;
import comp.hp.ij.common.service.pandora.data.ParcelSearchResult;
import comp.hp.ij.common.service.pandora.data.ParcelSong;
import comp.hp.ij.common.service.pandora.util.Logger;

public class AutoCompleteRequester extends BaseApi {
    private AuthRequester authRequester;

    public AutoCompleteRequester(Protocol protocolInput, AuthRequester authRequesterInput) {
        super(protocolInput);
        authRequester = authRequesterInput;
    }
    
    @SuppressWarnings("unchecked")
    public ParcelSearchResult search(String sSearchText) throws Exception {
        Logger.d("sSearchText: [" + sSearchText + "]");
        
        String sAutoCompleteUrl = authRequester.getAutoCompleteUrl();
        Logger.d("sAutoCompleteUrl: [" + sAutoCompleteUrl + "]");
        
        HashMap<String, String> urlParameters = new HashMap<String, String>();
        urlParameters.put("auth_token", authRequester.getPartnerAuthToken());
        urlParameters.put("query", sSearchText);
        
        Result result = protocol.sendAutoCompleteRequest(sAutoCompleteUrl, urlParameters);
        
        String sSearchTextResult = result.getString("searchText");
        Logger.d("sSearchTextResult: [" + sSearchTextResult + "]");
        
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
        
        return searchResult;
    }

}
