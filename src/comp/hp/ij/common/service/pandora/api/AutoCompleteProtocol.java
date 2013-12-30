package comp.hp.ij.common.service.pandora.api;

import java.util.HashMap;

import org.json.JSONObject;

import comp.hp.ij.common.service.pandora.util.Logger;

public class AutoCompleteProtocol extends Protocol {

    public AutoCompleteProtocol(Security security, String httpUrl, String httpsUrl) throws Exception {
        super(security, httpUrl, httpsUrl);
    }

    @Override
    public String getContentType() {
        return "text/plain";
    }

    @Override
    public String getPayload(String method, HashMap<String, Object> params) {
        return null; // TODO Useless method
    }

    @Override
    public Result getResultFromResponse(String sResponse) throws Exception {
                
        //{"stat":"ok",
        // "result":{
        //   "artists":[
        //     {"artistName":"a",
        //      "musicToken":"R219425"}],
        //   "songs":[
        //     {"songName":"A.A.A.",
        //      "artistName":"Shonen Knife",
        //      "musicToken":"S465553"},
        //     {"songName":"Aaa",
        //      "artistName":"Strapping Young Lad",
        //      "musicToken":"S912832"},
        //     {"songName":"AA",
        //      "artistName":"Electroputas",
        //      "musicToken":"S202860"}]
        // }}
        
        String sJSONHeader = "{\"stat\":\"ok\",\"result\":{";
        String sJSONTail   = "}}";
        
        StringBuffer sbJSONSearchText = new StringBuffer();
        sbJSONSearchText.append("\"searchText\":");
        StringBuffer sbJSONArtists = new StringBuffer();
        sbJSONArtists.append("\"artists\":[");
        StringBuffer sbJSONSongs = new StringBuffer();
        sbJSONSongs.append("\"songs\":[");
        
        boolean isFirstArtist = true;
        boolean isFirstSong   = true;
        
        String[] saLine = sResponse.split("\n");
        for (int i = 0 ; i < saLine.length ; i++) {
            
            Logger.d("saLine[" + i + "]: [" + saLine[i] + "]");
            
            int iIndexOf = saLine[i].indexOf("\"");
            if (0 < iIndexOf) {
                String sNewString = saLine[i].replaceAll("\"", "\\\\\"");
                saLine[i] = sNewString;
                Logger.d("saLine[new][" + i + "]: [" + saLine[i] + "]");
            }
            String[] saData = saLine[i].split("\t");
                        
            if (1 == saData.length) {
                Logger.d("search text: [" + saData[0] + "]");
                sbJSONSearchText.append("\"");
                sbJSONSearchText.append(saData[0]);
                sbJSONSearchText.append("\",");
            } else if (2 == saData.length) { // artist, [index 0]: musicToken, [index 1]: Artist Name
                if (isFirstArtist) {
                    sbJSONArtists.append("{");
                    isFirstArtist = false;
                } else {
                    sbJSONArtists.append(",{");
                }
                sbJSONArtists.append("\"artistName\":\"");
                sbJSONArtists.append(saData[1]);
                sbJSONArtists.append("\",\"musicToken\":\"");
                sbJSONArtists.append(saData[0]);
                sbJSONArtists.append("\"}");
            } else if (3 == saData.length) { // song, [index 0]: musicToken, [index 1]: Artist Name, [index 2]: Track Title
                if (isFirstSong) {
                    sbJSONSongs.append("{");
                    isFirstSong = false;
                } else {
                    sbJSONSongs.append(",{");
                }
                sbJSONSongs.append("\"songName\":\"");
                sbJSONSongs.append(saData[2]);
                sbJSONSongs.append("\",\"artistName\":\"");
                sbJSONSongs.append(saData[1]);
                sbJSONSongs.append("\",\"musicToken\":\"");
                sbJSONSongs.append(saData[0]);
                sbJSONSongs.append("\"}");
            }
        }
        
        sbJSONArtists.append("]");
        sbJSONSongs.append("]");
        
        StringBuffer sbJSONResponse = new StringBuffer();
        sbJSONResponse.append(sJSONHeader);
        sbJSONResponse.append(sbJSONSearchText);
        sbJSONResponse.append(sbJSONArtists);
        sbJSONResponse.append(",");
        sbJSONResponse.append(sbJSONSongs);
        sbJSONResponse.append(sJSONTail);
        
        String sJSONResponse = sbJSONResponse.toString();
        Logger.d("sJSONResponse: [" + sJSONResponse + "]");

        JSONObject responseObject = new JSONObject(sJSONResponse);
        //validateResponse(responseObject);
        if(responseObject.has("result")){
            JSONObject resultObject = (JSONObject) responseObject.get("result");
            Logger.d("resultObject: [" + resultObject.toString() + "]");
            return new JSONResult(resultObject);
        }
        return new JSONResult(responseObject);
    }

}
