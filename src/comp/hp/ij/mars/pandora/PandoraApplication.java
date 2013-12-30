/**
 * @author jugo
 * @date 2009-12-01
 * @description Application Level Control 
 */

package comp.hp.ij.mars.pandora;


import android.app.Application;
import android.content.res.Configuration;
import android.os.Handler;
import android.util.Log;
import frame.event.EventMessage;
import frame.view.SharedData;

public class PandoraApplication extends Application {

	private final  String TAG = "PandoraApplication";
	public PandoraAPIService	PService	= new PandoraAPIService();
	public PandoraStationData	stationData = new PandoraStationData(); 
	public PandoraPlaynowData	playData	= new PandoraPlaynowData();
	public PandoraUserData		userData 	= new PandoraUserData();
	public SharedData           sharedData  = new SharedData();
	private int mCurrentRunWnd = -1;
	private String mszAlarm = null;
	private boolean mbIsIdleAlarm = false;
	private String mszActivationCode = null;
	private String mszActivationURL  = null;
	private int mnSwitchUserIndex = -1;
	private int mnOrientation = -1;
	private boolean mbStationRemoveStatus = false;
	private int mnCurrentPlayStation = -1;
	private boolean mbPlayNowMenuShow  = false;
	private boolean mbPlayNowPauseSong = false;
	public static final int ORIENTATION_PORT = 1;
	public static final int ORIENTATION_LAND = 2;
	public static final int MAX_PLAY_NOW_TRACK = 12;
	public final int MAX_AUTO_COMPLETE = 10;
	private String[]arrAutoComplete = new String[MAX_AUTO_COMPLETE];
	private boolean mbAlarmShowed = false;

	
	
    public PandoraApplication() {
		super();
	}
    
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mnOrientation = newConfig.orientation;
		Log.v(TAG,"onConfigurationChanged orientation = " + newConfig.orientation);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mCurrentRunWnd = EventMessage.RUN_LOADWND;
		PService.initPandoraService(getApplicationContext(), this);
		sharedData.initShareData(getApplicationContext());
		userData.init(this);
		Log.v(TAG,"onCreate");
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		closeApplication();
	}

	public int getOrientation(){
		return mnOrientation;
	}
	
	public void setOrientation(int nOri){
		mnOrientation = nOri;
	}
	
	public void closeApplication(){
		if(null != stationData){
			stationData.clearStationData();
			stationData = null;
		}
		if(null != playData){
			playData.clearPlayData();
			playData = null;
		}
		if(sharedData != null){
			sharedData = null;
		}
		if(userData != null){
			userData = null;
		}
		if(PService != null){
			PService.closeService();
			PService = null;
		}
	}
	
	public void initEventHandler(Handler handler){
		if(PService != null){
			PService.initEventHandler(handler);
		}
	}
	
	public int setStationData(String szStationName, 
			boolean bAllowRename, 
			String szStationToken, 
			boolean bAllowDelete, 
			boolean bQuickMix, 
			boolean bShared, 
			long lStationId) {	
        if (null != stationData) {
            stationData.addStationData(szStationName, bAllowRename, szStationToken, bAllowDelete, bQuickMix, bShared, lStationId);
        } else {
            Log.d(TAG, "add data to pandora station list fail, stationData is null");
            stationData = new PandoraStationData();
            stationData.addStationData(szStationName, bAllowRename, szStationToken, bAllowDelete, bQuickMix, bShared, lStationId);
            Log.i(TAG, "add data to pandora station list, station name = " + szStationName);
        }
		return stationData.getStationCount();
	}
	
	public int insertStationData(String szStationName, 
	        boolean bAllowRename, 
	        String szStationToken, 
	        boolean bAllowDelete, 
	        boolean bQuickMix, 
	        boolean bShared, 
	        long lStationId) {  
	    if (null != stationData) {
	        stationData.insertStationData(szStationName, bAllowRename, szStationToken, bAllowDelete, bQuickMix, bShared, lStationId);
	    } else {
	        Log.e(TAG, "stationData is null");
	        stationData = new PandoraStationData();
	    }
	    return stationData.getStationCount();
	}
		
	public int setPlayData(String szStationName, 
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
		String szCurrStationName = stationData.getCurrStationName();
		if(playData != null){
			playData.addPlayData(szCurrStationName, 
					szSongName, 
					szArtistName, 
					szAlbumName, 
					szAlbumArtUrl, 
					szAudioUrl, 
					szTrackToken, 
					szSongRating,
					szTotalDuration,
					bAllowFeedback,
					bIsADS);
			return playData.getPlayCount();
		}
		return 0;
	}
	
	public void setCurrentRunWnd(int nRunWndIndex){
		mCurrentRunWnd = nRunWndIndex;
	}
	
	public int getCurrentRunWnd(){
		return mCurrentRunWnd;
	}
	
	public void setAlarmText(String szAlarm){
		mszAlarm = szAlarm;
	}
	
	public String getAlarmText(){
		return mszAlarm;
	}
	
	public void setIdleAlarm(boolean bIsIdleAlarm){
		mbIsIdleAlarm = bIsIdleAlarm;
	}
	
	public boolean isIdleAlarm(){
		return mbIsIdleAlarm;
	}
	
	public void setActivationCode(String szCode, String szURL){
		mszActivationCode = szCode;
		mszActivationURL  = szURL;
	}
	
	public String getActivationCode(){
		return mszActivationCode;
	}
	
	public String getActivationURL(){
		return mszActivationURL;
	}
	
	public void setSwitchUserIndex(int bSwitchIndex){
		mnSwitchUserIndex = bSwitchIndex;
	}
	
	public int getSwitchUserIndex(){
		return mnSwitchUserIndex;
	}
	
	public void setStationRemoveState(boolean bRemove){
		this.mbStationRemoveStatus = bRemove;
	}
	
	public boolean getStationRemoveState(){
		return this.mbStationRemoveStatus;
	}
	
	public void setCurrentPlayStation(int nIndex){
		this.mnCurrentPlayStation = nIndex;
	}
	
	public int getCurrentPlayStation(){
		return this.mnCurrentPlayStation;
	}
	
	public void setPlayNowPauseSongStatus(boolean bShow){
		mbPlayNowPauseSong = bShow;
	}
	
	public boolean getPlayNowPauseSongStatus(){
		return mbPlayNowPauseSong;
	}
	
	public void setPlayNowMenuStatus(boolean bShow){
		mbPlayNowMenuShow = bShow;
	}
	
	public boolean getPlayNowMenuStatus(){
		return mbPlayNowMenuShow;
	}
	
	public void saveAutoCompleteData(){
		if( null == arrAutoComplete || 0 >= arrAutoComplete.length){
			return;
		}
		sharedData.initPreferences("PandoraAC.xml");	
		for(int i = 0; i < arrAutoComplete.length; i++){
			sharedData.setValue(String.valueOf(i), arrAutoComplete[i]);
		}
		sharedData.close();
	}
	
	public String[] getAutoCompleteData(){
		return arrAutoComplete;
	}
	
	public void addAutoCompleteData(String szAccount){
		if( null == arrAutoComplete){
			return;
		}
		int nIndex = 0;
		int nLength = arrAutoComplete.length;
		if(nLength < (MAX_AUTO_COMPLETE - 1)){
			nIndex = nLength + 1;
		}
		arrAutoComplete[nIndex] = szAccount;
		Log.v(TAG,"save auto complete data: index = " + nIndex + " Account = " + szAccount);
	}
	
	public void setAlarmShowed(boolean bShow){
		mbAlarmShowed = bShow;
	}
	
	public boolean getAlarmShowed(){
		return mbAlarmShowed;
	}
	
	
}
