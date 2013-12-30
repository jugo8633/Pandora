package comp.hp.ij.mars.pandora;

import java.util.ArrayList;
import java.util.List;

import comp.hp.ij.common.service.pandora.util.Logger;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class PandoraPlaynowData {

	private final String TAG = "PandoraPlaynowData";
	public  List<PlayData>playData = null;
	
	public PandoraPlaynowData(){
		playData = new ArrayList<PlayData>();
		clearPlayData();
	}
	
	public class PlayData{
		private String 	m_szStationName;
		private String 	m_szSongName;
		private String 	m_szArtistName;
		private String 	m_szAlbumName;
		private String	m_szAlbumArtUrl;
		private String	m_szAudioUrl;
		private String	m_szTrackToken;
		private int mnSongRating;
		private boolean mbAllowFeedback;
		private boolean mbIsADS;
		private ImageView m_ivThumbUp;
		private Bitmap mBmpAlbumArt;
		private String m_szTotalDuration;
		private String m_szCurrentPosition;
		
		public PlayData(String szStationName, 
				String szSongName, 
				String szArtistName, 
				String szAlbumName, 
				String szAlbumArtUrl, 
				String szAudioUrl, 
				String szTrackToken, 
				String szSongRating,
				String szTotalDuration,
				boolean bAllowFeedback,
				boolean bIsADS){
			this.m_szStationName   = szStationName;
			this.m_szSongName      = szSongName;
			this.m_szArtistName    = szArtistName;
			this.m_szAlbumName     = szAlbumName;
			this.m_szAlbumArtUrl   = szAlbumArtUrl;
			this.m_szAudioUrl      = szAudioUrl;
			this.m_szTrackToken	   = szTrackToken;
			this.m_ivThumbUp       = null;
			this.mnSongRating      = Integer.parseInt(szSongRating);
			this.m_szTotalDuration = szTotalDuration;
			this.mbAllowFeedback   = bAllowFeedback;
			this.mbIsADS           = bIsADS;
			this.mBmpAlbumArt      = null;
		}
	}
	
	public int addPlayData(String szStationName, 
			String szSongName, 
			String szArtistName, 
			String szAlbumName, 
			String szAlbumArtUrl, 
			String szAudioUrl, 
			String szTrackToken, 
			String szSongRating,
			String szTotalDuration,
			boolean bAllowFeedback,
			boolean bIsADS){
		playData.add(new PlayData(
				szStationName, 
				szSongName, 
				szArtistName, 
				szAlbumName, 
				szAlbumArtUrl, 
				szAudioUrl, 
				szTrackToken, 
				szSongRating,
				szTotalDuration,
				bAllowFeedback,
				bIsADS));
		return playData.size();
	}
	
	public void clearPlayData(){
		if(playData == null){
			return;
		}
		if(playData.size() <= 0){
			return;
		}
		playData.clear();
	}
	
	public int getPlayCount(){
		if(playData == null){
			return 0;
		}
		return playData.size();
	}
	
	public int getNowPlayIndex() {
	    return getPlayCount() - 1;
	}
	
	public PlayData getPlayItem(int nIndex){
		if(!isDataValid(nIndex)){
			return null;
		}
		return playData.get(nIndex);
	}
	
	public String getStationName(int nIndex){
		if(!isDataValid(nIndex)){
			return null;
		}
		return playData.get(nIndex).m_szStationName;
	}
	
	public String getArtistName(int nIndex){
		if(!isDataValid(nIndex)){
			return null;
		}
		return playData.get(nIndex).m_szArtistName;
	}
	
	public String getSongName(int nIndex){
		if(!isDataValid(nIndex)){
			return null;
		}
		return playData.get(nIndex).m_szSongName;
	}
	
	public String getAlbumName(int nIndex){
		if(!isDataValid(nIndex)){
			return null;
		}
		return playData.get(nIndex).m_szAlbumName;
	}
	
	public String getAlbumArt(int nIndex){
		if(!isDataValid(nIndex)){
			return null;
		}
		return playData.get(nIndex).m_szAlbumArtUrl;
	}
	
	// TODO 0
	/*
	public boolean getThumbUp(int nIndex){
		if(!isDataValid(nIndex)){
			return false;
		}
		boolean bThumbUp = false;
		if(playData.get(nIndex).mnSongRating == 1){
			bThumbUp = true; 
		}else{
			bThumbUp =  false;
		}
		return bThumbUp;
	}
	*/
	public int getThumb(int nIndex) {
	    if (!isDataValid(nIndex)) {
	        return 0;
	    }
	    return playData.get(nIndex).mnSongRating;
	}
	
	public void setThumbUp(boolean bThumbUp){
		int nLastIndex;
		nLastIndex = getPlayCount() - 1;
		if(bThumbUp){ // set thumb up
			playData.get(nLastIndex).mnSongRating = 1;
		}else{ // set thumb down
			//playData.get(nLastIndex).mnSongRating = 0; // TODO 0
		    playData.get(nLastIndex).mnSongRating = -1;
		}
	}
	
	public void setThumbUpView(int nIndex, ImageView ivThumbUp){
		if(!isDataValid(nIndex)){
			return ;
		}
		playData.get(nIndex).m_ivThumbUp = ivThumbUp;
	}
	
	public ImageView getThumbUpView(int nIndex){
		if(!isDataValid(nIndex)){
			return null;
		}
		return playData.get(nIndex).m_ivThumbUp;
	}
	
	public void setSongRating(int nIndex, int nRating){
		if(!isDataValid(nIndex)){
			return;
		}
		int nSongRating = 0;
		if( -1 > nRating || 1 < nRating ){
			nSongRating = 0;
		}
		nSongRating = nRating;
		playData.get(nIndex).mnSongRating = nSongRating;
	}
	
	public int getSongRating(int nIndex){
		if(!isDataValid(nIndex)){
			return 0;
		}
		int nSongRating = 0;
		nSongRating = playData.get(nIndex).mnSongRating;
		return nSongRating;
	}
	
	public boolean isAllowFeedback(){
		int nLastIndex = getPlayCount();
    	nLastIndex--;
		if(!isDataValid(nLastIndex)){
			return false;
		}
		boolean bAllowFeedback = false;
		bAllowFeedback = playData.get(nLastIndex).mbAllowFeedback;
		return bAllowFeedback;
	}
	
	public boolean isADS(int nIndex){
		if(!isDataValid(nIndex)){
			return false;
		}
		boolean bADS = false;
		bADS = playData.get(nIndex).mbIsADS;
		
		Log.d(TAG,"song index = " + nIndex
				+ " name = " + playData.get(nIndex).m_szSongName
				+ " isADS = " + playData.get(nIndex).mbIsADS
				+ " AllowFeedback = " + playData.get(nIndex).mbAllowFeedback);
		return bADS;
	}
	public void setAlbumArtBmp(int nIndex, Bitmap bmpArt){
		if(!isDataValid(nIndex)){
			Log.d(TAG,"setAlbumArtBmp fail, data invalid");
			return;
		}
		playData.get(nIndex).mBmpAlbumArt = bmpArt;
	}
	
	public Bitmap getAlbumArtBmp(int nIndex){
		if(!isDataValid(nIndex)){
			Log.d(TAG,"getAlbumArtBmp fail, data invalid");
			return null;
		}
		return playData.get(nIndex).mBmpAlbumArt;
	}
		
	public void removeData(int nIndex){
		if(!isDataValid(nIndex)){
			return ;
		}
		playData.remove(nIndex);
	}
	
	private boolean isDataValid(int nIndex){
		if(playData == null){
			return false;
		}
		if( 0 > nIndex || nIndex >= playData.size()){
			return false;
		}
		return true;
	}
	
	public String getTotalDuration(int nIndex) {
	    String sReturn = ""; //"??:??";
	    if(isDataValid(nIndex)) {
	       sReturn = playData.get(nIndex).m_szTotalDuration;
	    }
	    //Logger.d("sReturn [" + sReturn + "]");
	    return sReturn;
	}
	public void setTotalDuration(int nIndex, String sTotalDurationInput) {
	    if(isDataValid(nIndex)) {
	        //Logger.d("sTotalDurationInput [" + sTotalDurationInput + "]");
	        playData.get(nIndex).m_szTotalDuration = sTotalDurationInput;
	    }
	}
	
	public String getCurrentPosition(int nIndex) {
	    String sReturn = ""; //"??:??";
	    if(isDataValid(nIndex)) {
	        sReturn = playData.get(nIndex).m_szCurrentPosition;
	    }
	    //Logger.d("sReturn [" + sReturn + "]");
        return sReturn;
	}
	public void setCurrentPosition(int nIndex, String sCurrentPositionInput) {
	    if(isDataValid(nIndex)) {
	        //Logger.d("sCurrentPositionInput [" + sCurrentPositionInput + "]");
	        playData.get(nIndex).m_szCurrentPosition = sCurrentPositionInput;
	    }
	}
}
