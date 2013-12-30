package comp.hp.ij.mars.pandora;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import comp.hp.ij.common.service.pandora.MessageHandler;
import comp.hp.ij.common.service.pandora.PandoraServiceAdapter;
import comp.hp.ij.common.service.pandora.PandoraServiceConstants;
import comp.hp.ij.common.service.pandora.api.Item;
import comp.hp.ij.common.service.pandora.api.PandoraAPIConstants;
import comp.hp.ij.common.service.pandora.api.PandoraAPIErrorCode;
import comp.hp.ij.common.service.pandora.data.PPandora;
import comp.hp.ij.common.service.pandora.data.ParcelAd;
import comp.hp.ij.common.service.pandora.data.ParcelArtist;
import comp.hp.ij.common.service.pandora.data.ParcelDeviceActivationData;
import comp.hp.ij.common.service.pandora.data.ParcelExplanation;
import comp.hp.ij.common.service.pandora.data.ParcelSearchResult;
import comp.hp.ij.common.service.pandora.data.ParcelSong;
import comp.hp.ij.common.service.pandora.data.ParcelStation;
import comp.hp.ij.common.service.pandora.data.ParcelTrack;
import comp.hp.ij.common.service.pandora.util.Debugger;
import comp.hp.ij.common.service.pandora.util.Logger;
import comp.hp.ij.common.service.v2.base.ConstantV2;
import comp.hp.ij.common.service.v2.base.PResultV2;

import frame.event.EventMessage;


public class PandoraAPIService {

	private final String TAG = "PandoraAPIService";
	// +++++ id should use static for rotate +++++ //
    private static String sIsAssociatedId           = "";
    private static String sDoLogoutId               = "";
    private static String sGenerateActivationCodeId = "";
    private static String sDoDeviceLoginId          = "";
    private static String sDoLoginId                = "";
    private static String sGetNowPlayStationDataId  = "";
    private static String sGetStationListId         = "";
    private static String sDeleteStationId          = "";
    private static String sGetPlaylistId            = "";
    private static String sUpdatePlaylistId         = "";
    
    private static String sPauseMusicId             = "";
    private static String sResumeMusicId            = "";
    private static String sSkipSongId               = "";
    private static String sStopMusicId              = "";
    
    private static String sAddArtistBookmarkId      = "";
    private static String sAddSongBookmarkId        = "";
    private static String sAddPositiveFeedbackId    = "";
    private static String sAddNegativeFeedbackId    = "";
    private static String sSleepSongId              = "";
    private static String sExplainTrackId           = "";
    
    private static String sMusicSearchId             = "";
    private static String sMusicSearchAutoCompleteId = "";
    private static String sCreateStationId           = "";
    private boolean isClickSkipSongButton = false;
    
    private PandoraServiceAdapter pandoraServiceAdapter = null;
    
    private int iNowPlayIndex          = -1;
    private String sNowPlayTrackToken  = "";
    
    private static ArrayList<String> alMusicSearchResultArtist = null;
    private static ArrayList<String> alMusicSearchResultArtistMusicToken = null;
    private static ArrayList<String> alMusicSearchResultSongArtist = null;
    private static ArrayList<String> alMusicSearchResultSongMusicToken = null;
    private MessageHandler messageHandler = null;
    private Handler eventHandler = null;
    private PandoraApplication application = null;
    private Context mContext = null;
    private boolean	mbServiceReady = false;
        
    public PandoraAPIService(){
    	
    }
    
	public void initPandoraService(Context context, PandoraApplication PApplication){
		pandoraServiceAdapter = new PandoraServiceAdapter(context, context.hashCode());
		pandoraServiceAdapter.setActivityMessageHandler(activityMessageHandler);
		application = PApplication;
		mContext = context;
		messageHandler = new MessageHandler(context);
	}
	
	 public void closeService(){
	    pandoraServiceAdapter.destroy();
	 }
	  
	 public void initEventHandler(Handler handler){
		 eventHandler = handler;
	 }
	 
	 public void isAssociated(){
            sIsAssociatedId = getUUID();
            Logger.d("sisAssociatedId: [" + sIsAssociatedId + "]");                
            pandoraServiceAdapter.isAssociated(sIsAssociatedId);
	 }
	  
	 public void doLogin( String szAccount, String szPassword ){
         pandoraServiceAdapter.setLoginAuth(szAccount, szPassword); 
         sDoLoginId = getUUID();
         Logger.d("sDoLoginId: [" + sDoLoginId + "]");                
         pandoraServiceAdapter.doLogin(sDoLoginId);
	 }
        
	 public void generateActivationCode(){
		 sGenerateActivationCodeId = getUUID();
         Logger.d("sGenerateActivationCodeId: [" + sGenerateActivationCodeId + "]");                
         pandoraServiceAdapter.generateActivationCode(sGenerateActivationCodeId);
	 }
	 
	 public void doDeviceLogin() {
         sDoDeviceLoginId = getUUID();
         Logger.d("sDoDeviceLoginId: [" + sDoDeviceLoginId + "]");                
         pandoraServiceAdapter.doDeviceLogin(sDoDeviceLoginId);
     }
	 
	 public void getStationList(){
         sGetStationListId = getUUID();
         Logger.d("sGetStationListId: [" + sGetStationListId + "]");                
         pandoraServiceAdapter.getStationList(sGetStationListId);
	 }
        
	 public void getPlaylist(final String szStationToken) {
         sGetPlaylistId = getUUID();
         Logger.d("sGetPlayListId: [" + sGetPlaylistId + "]");                    
         pandoraServiceAdapter.getPlaylist(sGetPlaylistId, szStationToken);
	 }
	 
	 public void stopMusic(){
         sStopMusicId = getUUID();
         Logger.d("sStopMusicId: [" + sStopMusicId + "]");
         pandoraServiceAdapter.stopMusic(sStopMusicId);
	 }
	 
	 public void doLogout() { 
         sDoLogoutId = getUUID();
         Logger.d("sDoLogoutId: [" + sDoLogoutId + "]");                
         pandoraServiceAdapter.doLogout(sDoLogoutId);
	 }

	 public void skipSong(){
		 if(isADS()){
			 return;
		 }
         sSkipSongId = getUUID();
         Logger.d("sSkipSongId: [" + sSkipSongId + "]");
         isClickSkipSongButton = true;
         pandoraServiceAdapter.skipSong(sSkipSongId);
	 }
	 
	 public void thumbsUp(){
		 if(isADS()){
			 return;
		 }
		 if(!application.playData.isAllowFeedback()){
			 Log.d(TAG,"thumbsUp invalid, track is share station");	 
			 String szMsg;
			 szMsg = mContext.getString(R.string.thumbs_not_allowed_for_shared_station);
			 sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_POSITIVE_FEEDBACK_FAIL, szMsg);
			 return;
		 }
         sAddPositiveFeedbackId = getUUID();
         Logger.d("sAddPositiveFeedbackId: [" + sAddPositiveFeedbackId + "]");
         pandoraServiceAdapter.addPostiveFeedback(sAddPositiveFeedbackId, sNowPlayTrackToken);
	 }
         
	 public void thumbsDown(){
		 if(isADS()){
			 return;
		 }
		 if(!application.playData.isAllowFeedback()){
			 Log.d(TAG,"thumbsDown invalid, track is share station");
			 String szMsg;
			 szMsg = mContext.getString(R.string.thumbs_not_allowed_for_shared_station);
			 sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_POSITIVE_FEEDBACK_FAIL, szMsg);
			 return;
		 }
         sAddNegativeFeedbackId = getUUID();
         Logger.d("sAddNegativeFeedbackId: [" + sAddNegativeFeedbackId + "]");
         pandoraServiceAdapter.addNegativeFeedback(sAddNegativeFeedbackId, sNowPlayTrackToken);
	 }
     
	 public void pauseMusic(){
         sPauseMusicId = getUUID();
         Logger.d("sPauseMusicId: [" + sPauseMusicId + "]");
         pandoraServiceAdapter.pauseMusic(sPauseMusicId);
	 }
     
	 public void resumeMusic(){
         sResumeMusicId = getUUID();
         Logger.d("sResumeMusicId: [" + sResumeMusicId + "]");
         pandoraServiceAdapter.resumeMusic(sResumeMusicId);
	 }
      
	 public void createStation(String szMusicToken) {
        if (!"".equals(szMusicToken)) {
            sCreateStationId = getUUID();
            Logger.d("sCreateStationId: [" + sCreateStationId + "]");
            pandoraServiceAdapter.createStation(sCreateStationId, szMusicToken);
        } else {
            Logger.d("Empty music token");
        }
	 }
        
	 public void explainTrack() {
		 if(isADS()){
			 return;
		 }
         sExplainTrackId = getUUID();
         Logger.d("sExplainTrackId: [" + sExplainTrackId + "]");
         pandoraServiceAdapter.explainTrack(sExplainTrackId, sNowPlayTrackToken);
	 }
        
	 public void bookmarkArtist(){
		 if(isADS()){
			 return;
		 }
         sAddArtistBookmarkId = getUUID();
         Logger.d("sAddArtistBookmarkId: [" + sAddArtistBookmarkId + "]");
         pandoraServiceAdapter.addArtistBookmark(sAddArtistBookmarkId, sNowPlayTrackToken);
	 }
     
	 public void bookmarkSong(){
		 if(isADS()){
			 return;
		 }
         sAddSongBookmarkId = getUUID();
         Logger.d("sAddSongBookmarkId: [" + sAddSongBookmarkId + "]");
         pandoraServiceAdapter.addSongBookmark(sAddSongBookmarkId, sNowPlayTrackToken);
	 }
     
	 public void sleepSong(){
		 if(isADS()){
			 return;
		 }
         sSleepSongId = getUUID();
         Logger.d("sSleepSongId: [" + sSleepSongId + "]");
         pandoraServiceAdapter.sleepSong(sSleepSongId, sNowPlayTrackToken);
	 }
     
	 public void musicSearch(final String sSearchText) { 
         sMusicSearchId = getUUID();
         Logger.d("sMusicSearchId: [" + sMusicSearchId + "]" + " sSearchText: [" + sSearchText + "]");
         pandoraServiceAdapter.musicSearch(sMusicSearchId, sSearchText);
	 }
         
	 public void deleteStation(String sStationToken) { 
         Logger.d("sStationToken: [" + sStationToken + "]");
         sDeleteStationId = getUUID();
         Logger.d("sDeleteStationId: [" + sDeleteStationId + "]");
         pandoraServiceAdapter.deleteStation(sDeleteStationId, sStationToken);
	 }
        
	 public void autoComplete(String szStr){
		 sMusicSearchAutoCompleteId = getUUID();
         Logger.d("sMusicSearchAutoCompleteId: [" + sMusicSearchAutoCompleteId + "]");
         pandoraServiceAdapter.musicSearchAutoComplete(sMusicSearchAutoCompleteId, szStr);
	 }
	 
	 public void resetIdleTime(){
		 pandoraServiceAdapter.resetIdleAlarmTimer();
	 }
	 
	 public boolean isServiceReady(){
		 return mbServiceReady;
	 }
	 
	 public void getNowPlayStationData() {
         sGetNowPlayStationDataId = getUUID();
         Logger.d("sGetNowPlayStationDataId: [" + sGetNowPlayStationDataId + "]");
         pandoraServiceAdapter.getNowPlayStationData(sGetNowPlayStationDataId);
     }
	 
	 private final Handler activityMessageHandler = new Handler() {
	     
	     public void handleMessage(Message message) {

	         if (PandoraServiceConstants.PANDORA_SERVICE_READY == message.what) {
	             //	sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_READY, null);
	             mbServiceReady = true;
	             Logger.d("PANDORA_SERVICE_READY");
	             return;
	         }

	         // handle 8 hours idle behavior
	         if (PandoraServiceConstants.PANDORA_SERVICE_FIRE_IDLE_ALARM == message.what) {
	             int iReturnCode = PandoraAPIConstants.ARE_YOU_STILL_THERE;
	             String sMessage = messageHandler.getMessage(iReturnCode);
	             sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_FIRE_IDLE_ALARM,sMessage);
	             Logger.d("PANDORA_SERVICE_FIRE_IDLE_ALARM");
	             return;
	         }

	         // handle service error event
	         if (PandoraServiceConstants.PANDORA_SERVICE_ERROR_EVENT == message.what) {
	             int iReturnCode = message.arg1;
	             Logger.d("PANDORA_SERVICE_ERROR_EVENT: [" + iReturnCode + "]");
	             String sMessage = messageHandler.getMessage(iReturnCode);
	             Debugger.debugReturnCode(iReturnCode);
	             sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_ERROR_EVENT, sMessage);
	             return;
	         }

	         int iResultCode = message.arg1;
	         Bundle bundle = (Bundle) message.obj;

	         String sUUID = bundle.getString(PandoraServiceConstants.UI_INPUT_UUID);
	         //Logger.d("sUUID: [" + sUUID + "]");

	         PResultV2 pResult = (PResultV2) bundle.getParcelable(ConstantV2.RESULT);
	         
	         debugAPIID(pResult.mParameter); // TODO debug APIID

	         if (sIsAssociatedId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 if (PandoraServiceConstants.APIID_PANDORA_IS_ASSOCIATED == pResult.mParameter) {
	                     PPandora pPandora = (PPandora) pResult.mData;
	                     if (PandoraServiceConstants.DEVICE_ASSOCIATED == pPandora.getIsAssociationResult()) {
	                         sDoLoginId = getUUID();
	                         Logger.d("sDoLoginId: [" + sDoLoginId + "]");
	                         pandoraServiceAdapter.doLogin(sDoLoginId);
	                     } else {
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_NOT_ASSOCIATED, null);
	                     }
	                 }
	             }
	         } else if (sDoLogoutId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 if (PandoraServiceConstants.APIID_PANDORA_DO_LOGOUT == pResult.mParameter) {
	                     PPandora pPandora = (PPandora) pResult.mData;
	                     int iReturnCode = pPandora.getLogoutReturn();
	                     String sMessage = messageHandler.getMessage(iReturnCode);

	                     if (PandoraServiceConstants.LOGOUT_SUCCESS == pPandora.getLogoutReturn()) {
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_LOGOUT_SUCCESS, sMessage);
	                     } else {
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_LOGOUT_FAIL, sMessage);
	                     }
	                 }
	             }
	         } else if (sGenerateActivationCodeId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 if (PandoraServiceConstants.APIID_PANDORA_GENERATE_ACTIVIATION_CODE == pResult.mParameter) {
	                     PPandora pPandora = (PPandora) pResult.mData;
	                     int iReturnCode = pPandora.getGenerateActivationCodeReturn();
	                     String sMessage = messageHandler.getMessage(iReturnCode);

	                     if (PandoraAPIConstants.GENERATE_ACTIVATION_CODE_SUCCESS == iReturnCode) {
	                         ParcelDeviceActivationData deviceActivationData = pPandora.getDeviceActivationData();
	                         if (null != deviceActivationData) {
	                             String sActivationCode = deviceActivationData.getActivationCode();
	                             String sActivationUrl  = deviceActivationData.getActivationUrl();
	                             sMessage = "Activation code: [" + sActivationCode + "], URL: [" + sActivationUrl + "]";
	                             String[]actionCode = new String[2];
	                             actionCode[0] = sActivationCode;
	                             actionCode[1] = sActivationUrl;
	                             sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_ACTIVATION_CODE_SUCCESS,actionCode);
	                         } else {
	                             sMessage = "deviceActivationData is null";
	                             sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_ACTIVATION_CODE_FAIL,sMessage);
	                         }
	                     } else {
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_ACTIVATION_CODE_FAIL,sMessage);
	                         Debugger.debugReturnCode(iReturnCode);
	                     }
	                 }
	             }
	         } else if (sDoLoginId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 if (PandoraServiceConstants.APIID_PANDORA_DO_LOGIN == pResult.mParameter) {
	                     PPandora pPandora = (PPandora) pResult.mData;
	                     int iReturnCode = pPandora.getDoLoginReturn();
	                     String sMessage = messageHandler.getMessage(iReturnCode);

	                     if (iReturnCode == PandoraAPIErrorCode.READ_ONLY_MODE) {
	                         iReturnCode = PandoraAPIConstants.USER_LOGIN_SUCCESS;
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_READ_ONLY, sMessage);
	                     }

	                     if (PandoraAPIConstants.USER_LOGIN_SUCCESS == iReturnCode
	                             || PandoraAPIConstants.DEVICE_LOGIN_SUCCESS == iReturnCode) {
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_LOGIN_SUCCESS, sMessage);
	                     } else {
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_LOGIN_FAIL, sMessage);
	                         Debugger.debugReturnCode(iReturnCode);
	                     }
	                 }
	             }
	         }else if (sDoDeviceLoginId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 if (PandoraServiceConstants.APIID_PANDORA_DO_DEVICE_LOGIN == pResult.mParameter) {
	                     PPandora pPandora = (PPandora) pResult.mData;
	                     int iReturnCode = pPandora.getDoDeviceLoginReturn();
	                     String sMessage = messageHandler.getMessage(iReturnCode);

	                     if (PandoraAPIConstants.DEVICE_LOGIN_SUCCESS == iReturnCode) {
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_LOGIN_SUCCESS, sMessage);
	                     } else {
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_LOGIN_FAIL, sMessage);
	                         Debugger.debugReturnCode(iReturnCode);
	                     }
	                 }
	             }
	         }else if (sGetNowPlayStationDataId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 if (PandoraServiceConstants.APIID_PANDORA_GET_NOWPLAY_STATION_DATA == pResult.mParameter) {
	                     PPandora pPandora = (PPandora) pResult.mData;
	                     int iNowPlayStationIndex = pPandora.getNowPlayStationIndex();
	                     String szNowPlayStationIndex = String.valueOf(iNowPlayStationIndex);
	                     String sNowPlayStationToken = pPandora.getNowPlayStationToken();
	                     String[]arrNowPlay = new String[2];
	                     arrNowPlay[0] = szNowPlayStationIndex;
	                     arrNowPlay[1] = sNowPlayStationToken;
	                     sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_NOWPLAY_TOKEN, arrNowPlay);
	                 }
	             }
	         } else if (sGetStationListId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 if (PandoraServiceConstants.APIID_PANDORA_GET_STATION_LIST == pResult.mParameter) {
	                     PPandora pPandora = (PPandora) pResult.mData;
	                     int iReturnCode = pPandora.getGetStationListReturn();
	                     String sMessage = messageHandler.getMessage(iReturnCode);
	                     
	                     debugStationList(pPandora); // TODO debug station list

	                     if (PandoraAPIConstants.GET_STATION_LIST_SUCCESS == iReturnCode) {
	                         ArrayList<ParcelStation> alStationList = pPandora.getStationList();
	                         int iStationListSize = alStationList.size();

	                         Logger.d("iStationListSize: [" + iStationListSize + "]");

	                         if (0 < iStationListSize) {
	                             for (ParcelStation parcelStation : alStationList) {
	                                 application.setStationData(
	                                         parcelStation.getStationName(), 
	                                         parcelStation.isAllowRename(), 
	                                         parcelStation.getStationToken(), 
	                                         parcelStation.isAllowDelete(), 
	                                         parcelStation.isQuickMix(), 
	                                         parcelStation.isShared(), 
	                                         Long.parseLong(parcelStation.getStationId()));
	                             }
	                             sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_STATION_GET_SUCCESS, String.valueOf(iStationListSize));
	                         } else if (0 == iStationListSize) {
	                             sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_STATION_GET_SUCCESS, "0");
	                         }
	                     } else {
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_STATION_GET_FAIL, sMessage);
	                         Debugger.debugReturnCode(iReturnCode);
	                     } 
	                 }
	             }
	         } else if (sDeleteStationId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 if (PandoraServiceConstants.APIID_PANDORA_DELETE_STATION == pResult.mParameter) {
	                     PPandora pPandora = (PPandora) pResult.mData;
	                     int iReturnCode = pPandora.getDeleteStationReturn();
	                     String sMessage = messageHandler.getMessage(iReturnCode);

	                     if (PandoraAPIConstants.DELETE_STATION_SUCCESS == iReturnCode) {
	                         boolean isDeleteNowPlayStation = pPandora.getIsDeleteNowPlayStation();
	                         if (isDeleteNowPlayStation) {
	                             application.userData.setCurrLastStationToken(null);
	                             application.stationData.setCurrentStationToken(null);
	                         } 
	                         Logger.d("isDeleteNowPlayStation: [" + isDeleteNowPlayStation + "]");
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_REMOVE_STATION_SUCCESS, sMessage);
	                     } else {
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_REMOVE_STATION_FAIL, sMessage);
	                         Debugger.debugReturnCode(iReturnCode);
	                     }
	                 }
	             }
	         } else if (sGetPlaylistId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 handleBeginPlayback(pResult);
	             }
	         } else if (sUpdatePlaylistId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 handleBeginPlayback(pResult);
	             }
	             /*   } else if (sDownloadImageId.equals(sUUID)) {
	                if (basicCheck(iResultCode, pResult)) {
	                    handleBeginPlayback(pResult);
	                } */
	         } else if (sPauseMusicId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 handleBeginPlayback(pResult);
	             }
	         } else if (sResumeMusicId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 handleBeginPlayback(pResult);
	             }
	         } else if (sSkipSongId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 if (isReachSkipLimit(pResult) && isClickSkipSongButton ) {
	                     isClickSkipSongButton = false;
	                     int iReturnCode = PandoraAPIConstants.REACH_SKIP_LIMITATION;
	                     String sMessage = messageHandler.getMessage(iReturnCode);
	                     Debugger.debugReturnCode(iReturnCode);
	                     sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_SKIP_SONG_FAIL, sMessage);
	                     Logger.d("UI get skip limitation alert"); // TODO UI handle necessary
	                 }
	                 handleBeginPlayback(pResult);
	             }
	         } else if (sStopMusicId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 if (PandoraServiceConstants.APIID_PANDORA_STOP_MUSIC == pResult.mParameter) {
	                     PPandora pPandora = (PPandora) pResult.mData;
	                     Logger.d("pPandora.getStopMusicStatus(): [" + pPandora.getStopMusicStatus() + "]");
	                     
	                     doLogout();
	                 }
	             }
	         } else if (sAddArtistBookmarkId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 handleBeginPlayback(pResult);

	                 if (PandoraServiceConstants.APIID_PANDORA_ADD_ARTIST_BOOKMARK == pResult.mParameter) {
	                     PPandora pPandora = (PPandora) pResult.mData;
	                     int iReturnCode = pPandora.getAddArtistBookmarkReturn();
	                     String sMessage = messageHandler.getMessage(iReturnCode);

	                     if (PandoraAPIConstants.ADD_ARTIST_BOOKMARK_SUCCESS == iReturnCode) {
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_BOOKMARK_SUCCESS, sMessage);
	                     } else {
	                         Debugger.debugReturnCode(iReturnCode);
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_BOOKMARK_FAIL, sMessage);
	                     }
	                 }
	             }
	         } else if (sAddSongBookmarkId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 handleBeginPlayback(pResult);

	                 if (PandoraServiceConstants.APIID_PANDORA_ADD_SONG_BOOKMARK == pResult.mParameter) {
	                     PPandora pPandora = (PPandora) pResult.mData;
	                     int iReturnCode = pPandora.getAddSongBookmarkReturn();
	                     String sMessage = messageHandler.getMessage(iReturnCode);

	                     if (PandoraAPIConstants.ADD_SONG_BOOKMARK_SUCCESS == iReturnCode) {
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_BOOKMARK_SUCCESS, sMessage);
	                     } else {
	                         Debugger.debugReturnCode(iReturnCode);
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_BOOKMARK_FAIL, sMessage);
	                     }
	                 }
	             }
	         } else if (sAddNegativeFeedbackId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 handleBeginPlayback(pResult);

	                 if (PandoraServiceConstants.APIID_PANDORA_ADD_NEGATIVE_FEEDBACK == pResult.mParameter) {
	                     PPandora pPandora = (PPandora) pResult.mData;
	                     int iReturnCode = pPandora.getAddFeedbackReturn();
	                     String sMessage = messageHandler.getMessage(iReturnCode);

	                     if (PandoraAPIConstants.ADD_FEEDBACK_SUCCESS == iReturnCode) {
	                         ArrayList<String> alMessages = new ArrayList<String>();
	                         if (isReachSkipLimit(pResult)) {
	                             alMessages.add(Boolean.TRUE.toString());
	                             iReturnCode = PandoraAPIConstants.REACH_SKIP_LIMITATION;
	                             sMessage = messageHandler.getMessage(iReturnCode);
	                             Debugger.debugReturnCode(iReturnCode);
	                         }
	                         alMessages.add(sMessage);
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_NEGATIVE_FEEDBACK_SUCCESS, alMessages);
	                     } else {
	                         Debugger.debugReturnCode(iReturnCode);
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_NEGATIVE_FEEDBACK_FAIL, sMessage);
	                     }
	                 }
	             }
	         } else if (sAddPositiveFeedbackId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 handleBeginPlayback(pResult);

	                 if (PandoraServiceConstants.APIID_PANDORA_ADD_POSITIVE_FEEDBACK == pResult.mParameter) {
	                     PPandora pPandora = (PPandora) pResult.mData;
	                     int iReturnCode = pPandora.getAddFeedbackReturn();
	                     String sMessage = messageHandler.getMessage(iReturnCode);

	                     if (PandoraAPIConstants.ADD_FEEDBACK_SUCCESS == iReturnCode) {
	                         Logger.d(sMessage);
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_POSITIVE_FEEDBACK_SUCCESS, sMessage);
	                     } else {
	                         Debugger.debugReturnCode(iReturnCode);
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_POSITIVE_FEEDBACK_FAIL, sMessage);
	                     }
	                 }
	             }
	         } else if (sSleepSongId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 handleBeginPlayback(pResult);

	                 if (PandoraServiceConstants.APIID_PANDORA_SLEEP_SONG == pResult.mParameter) {
	                     PPandora pPandora = (PPandora) pResult.mData;
	                     int iReturnCode = pPandora.getSleepSongReturn();
	                     String sMessage = messageHandler.getMessage(iReturnCode);

	                     if (PandoraAPIConstants.SLEEP_SONG_SUCCESS == iReturnCode) {
	                         ArrayList<String> alMessages = new ArrayList<String>();
	                         if (isReachSkipLimit(pResult)) {
	                             alMessages.add(Boolean.TRUE.toString());
	                             iReturnCode = PandoraAPIConstants.REACH_SKIP_LIMITATION;
	                             sMessage = messageHandler.getMessage(iReturnCode);
	                             Debugger.debugReturnCode(iReturnCode);
	                         }
	                         alMessages.add(sMessage);
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_SLEEP_SONG_SUCCESS, alMessages);
	                     } else {
	                         Debugger.debugReturnCode(iReturnCode);
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_SLEEP_SONG_FAIL, sMessage);
	                     }
	                 }
	             }
	         } else if (sExplainTrackId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 handleBeginPlayback(pResult);

	                 if (PandoraServiceConstants.APIID_PANDORA_EXPLAIN_TRACK == pResult.mParameter) {
	                     PPandora pPandora = (PPandora) pResult.mData;
	                     int iReturnCode = pPandora.getExplainTrackReturn();
	                     String sMessage = messageHandler.getMessage(iReturnCode);

	                     if (PandoraAPIConstants.EXPLAIN_TRACK_SUCCESS == iReturnCode) {
	                         ArrayList<ParcelExplanation> alExplanations = pPandora.getExplanations();

	                         if (null != alExplanations && 0 < alExplanations.size()) {
	                             int iExplanationSize = alExplanations.size();
	                             Logger.d("iExplanationSize: [" + iExplanationSize + "]");
	                             String sExplanation = "";
	                             //   sExplanation += System.getProperty("line.separator");
	                             for (int i = 0; i < iExplanationSize; i++) {
	                                 sExplanation += alExplanations.get(i).getFocusTraitName();
	                                 if (i < (iExplanationSize - 1)) {
	                                     sExplanation += ", ";
	                                 } else {
	                                     sExplanation += ".";
	                                 }
	                             }
	                             sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_EXPLAIN_TRACK_SUCCESS, sExplanation);
	                         } else {
	                             sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_EXPLAIN_TRACK_SUCCESS, "No explanation.");
	                         }
	                     } else {
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_EXPLAIN_TRACK_FAIL, sMessage);
	                         Debugger.debugReturnCode(iReturnCode);
	                     }
	                 }
	             }
	         } else if (sMusicSearchId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 handleBeginPlayback(pResult);

	                 if (PandoraServiceConstants.APIID_PANDORA_MUSIC_SEARCH == pResult.mParameter) {
	                     PPandora pPandora = (PPandora) pResult.mData;
	                     int iReturnCode = pPandora.getMusicSearchReturn();
	                     String sMessage = messageHandler.getMessage(iReturnCode);

	                     if (PandoraAPIConstants.MUSIC_SEARCH_SUCCESS == iReturnCode) {
	                         ParcelSearchResult searchResult = pPandora.getMusicSearchResult();
	                         if (null != searchResult) {

	                             ArrayList<ParcelArtist> artistList = searchResult.getArtists();
	                             ArrayList<ParcelSong> songList = searchResult.getSongs();

	                             alMusicSearchResultArtist = new ArrayList<String>();
	                             alMusicSearchResultArtistMusicToken = new ArrayList<String>();
	                             if (null != artistList && 0 < artistList.size()) {
	                                 for (ParcelArtist artist : artistList) {
	                                     alMusicSearchResultArtist.add(artist.getArtistName());
	                                     alMusicSearchResultArtistMusicToken.add(artist.getMusicToken());
	                                 }
	                             }

	                             alMusicSearchResultSongArtist = new ArrayList<String>();
	                             alMusicSearchResultSongMusicToken = new ArrayList<String>();
	                             if (songList != null && songList.size() > 0) {
	                                 for (ParcelSong song : songList) {
	                                     alMusicSearchResultSongArtist.add(song.getSongName() + " by " + song.getArtistName());
	                                     alMusicSearchResultSongMusicToken.add(song.getMusicToken());
	                                 }
	                             }
	                             sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_SEARCH_RESULT_SUCCESS, sMessage);
	                         } else {
	                             sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_SEARCH_RESULT_FAIL, sMessage);
	                             Logger.d("searchResult is null");
	                         }
	                     } else {
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_SEARCH_RESULT_FAIL, sMessage);
	                         Debugger.debugReturnCode(iReturnCode);
	                     }
	                 }
	             }
	         } else if (sMusicSearchAutoCompleteId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 handleBeginPlayback(pResult);

	                 if (PandoraServiceConstants.APIID_PANDORA_MUSIC_SEARCH_AUTO_COMPLETE == pResult.mParameter) {
	                     PPandora pPandora = (PPandora) pResult.mData;
	                     int iReturnCode = pPandora.getMusicSearchAutoCompleteReturn();
	                     String sMessage = messageHandler.getMessage(iReturnCode);

	                     if (PandoraAPIConstants.MUSIC_SEARCH_AUTO_COMPLETE_SUCCESS == iReturnCode) {
	                         ParcelSearchResult searchResult = pPandora.getMusicSearchAutoCompleteResult();

	                         if (null != searchResult) {
	                             ArrayList<ParcelArtist> artistList = searchResult.getArtists();
	                             ArrayList<ParcelSong> songList = searchResult.getSongs();
	                             ArrayList<PandoraAutoCompleteData> musicItem = new ArrayList<PandoraAutoCompleteData>();

	                             if (null != artistList && 0 < artistList.size()) {

	                                 for (ParcelArtist artist : artistList) {
	                                     final String sMusicToken  = artist.getMusicToken();
	                                     final String szArtistName = artist.getArtistName();
	                                     musicItem.add(new PandoraAutoCompleteData(szArtistName, sMusicToken, szArtistName));
	                                     Log.d(TAG,"Artist: [" + szArtistName + "]");
	                                 }
	                             }

	                             if (songList != null && songList.size() > 0) {
	                                 for (ParcelSong song : songList) {
	                                     final String sMusicToken = song.getMusicToken();
	                                     final String szSongName  = song.getSongName();
	                                     final String szCombine   = szSongName + 
	                                     " by Artist: " + 
	                                     song.getArtistName();
	                                     musicItem.add(new PandoraAutoCompleteData(szSongName,sMusicToken,szCombine ));
	                                     Log.d(TAG,"Song: [" + szCombine + "]");
	                                 }
	                             }
	                             sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_AUTO_COMPLETE_SUCCESS, musicItem); 
	                         } else {
	                             Logger.d("searchResult is null");
	                         }
	                     } else {
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_AUTO_COMPLETE_FAIL, sMessage); 
	                         Debugger.debugReturnCode(iReturnCode);
	                     }
	                 }
	             }
	         } else if (sCreateStationId.equals(sUUID)) {
	             if (basicCheck(iResultCode, pResult)) {
	                 if (PandoraServiceConstants.APIID_PANDORA_CREATE_STATION == pResult.mParameter) {
	                     PPandora pPandora = (PPandora) pResult.mData;
	                     int iReturnCode = pPandora.getCreateStationReturn();
	                     String sMessage = messageHandler.getMessage(iReturnCode);

	                     if (PandoraAPIConstants.CREATE_STATION_SUCCESS == iReturnCode) {
	                         ParcelStation parcelStation = pPandora.getCreatedParcelStation();
	                         if (null != parcelStation) {
	                             String szStationToken = parcelStation.getStationToken();
	                             String szStationName  = parcelStation.getStationName();
	                             application.stationData.setCurrentStationToken(szStationToken);
	                             application.stationData.setCurrentStationName(szStationName);
	                             
                                 application.insertStationData(
                                         parcelStation.getStationName(), 
                                         parcelStation.isAllowRename(), 
                                         parcelStation.getStationToken(), 
                                         parcelStation.isAllowDelete(), 
                                         parcelStation.isQuickMix(), 
                                         parcelStation.isShared(), 
                                         Long.parseLong(parcelStation.getStationId()));
                                 
	                             sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_CREATE_STATION_SUCCESS, sMessage);
	                         } else {
	                             sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_CREATE_STATION_FAIL, sMessage);
	                             Logger.d("Create Station Object is null");
	                         }
	                     } else {
	                         sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_CREATE_STATION_FAIL, sMessage);
	                         Debugger.debugReturnCode(iReturnCode);
	                     }
	                 }
	             }
	         } else {
	             Logger.d("Un-handle uuid: [" + sUUID + "]");
	         }
	     }
	     
	     private void debugAPIID(int iAPIID) {
	         /*
	         switch (iAPIID) {
	             case PandoraServiceConstants.APIID_PANDORA_IS_ASSOCIATED:
	                 Logger.d("APIID_PANDORA_IS_ASSOCIATED"); break;
	             case PandoraServiceConstants.APIID_PANDORA_DO_LOGOUT:
	                 Logger.d("APIID_PANDORA_DO_LOGOUT"); break;
	             case PandoraServiceConstants.APIID_PANDORA_GENERATE_ACTIVIATION_CODE:
	                 Logger.d("APIID_PANDORA_GENERATE_ACTIVIATION_CODE"); break;
	             case PandoraServiceConstants.APIID_PANDORA_DO_LOGIN:
	                 Logger.d("APIID_PANDORA_DO_LOGIN"); break;
	             case PandoraServiceConstants.APIID_PANDORA_GET_NOWPLAY_STATION_DATA:
	                 Logger.d("APIID_PANDORA_GET_NOWPLAY_STATION_DATA"); break;
	             case PandoraServiceConstants.APIID_PANDORA_GET_STATION_LIST:
	                 Logger.d("APIID_PANDORA_GET_STATION_LIST"); break;
	             case PandoraServiceConstants.APIID_PANDORA_DELETE_STATION:
	                 Logger.d("APIID_PANDORA_DELETE_STATION"); break;
	             case PandoraServiceConstants.APIID_PANDORA_GET_PLAYLIST:
	                 Logger.d("APIID_PANDORA_GET_PLAYLIST"); break;
	             case PandoraServiceConstants.APIID_PANDORA_TOTAL_DURATION:
	                 Logger.d("APIID_PANDORA_TOTAL_DURATION"); break;
	             case PandoraServiceConstants.APIID_PANDORA_UPDATE_PLAYLIST:
	                 Logger.d("APIID_PANDORA_UPDATE_PLAYLIST"); break;
	             case PandoraServiceConstants.APIID_PANDORA_CURRENT_POSITION:
	                 Logger.d("APIID_PANDORA_CURRENT_POSITION"); break;
	             case PandoraServiceConstants.APIID_PANDORA_ADD_ARTIST_BOOKMARK:
	                 Logger.d("APIID_PANDORA_ADD_ARTIST_BOOKMARK"); break;
	             case PandoraServiceConstants.APIID_PANDORA_ADD_SONG_BOOKMARK:
	                 Logger.d("APIID_PANDORA_ADD_SONG_BOOKMARK"); break;
	             case PandoraServiceConstants.APIID_PANDORA_ADD_POSITIVE_FEEDBACK:
	                 Logger.d("APIID_PANDORA_ADD_POSITIVE_FEEDBACK"); break;
	             case PandoraServiceConstants.APIID_PANDORA_ADD_NEGATIVE_FEEDBACK:
	                 Logger.d("APIID_PANDORA_ADD_NEGATIVE_FEEDBACK"); break;
	             case PandoraServiceConstants.APIID_PANDORA_SLEEP_SONG:
	                 Logger.d("APIID_PANDORA_SLEEP_SONG"); break;
	             case PandoraServiceConstants.APIID_PANDORA_EXPLAIN_TRACK:
	                 Logger.d("APIID_PANDORA_EXPLAIN_TRACK"); break;
	             case PandoraServiceConstants.APIID_PANDORA_MUSIC_SEARCH:
	                 Logger.d("APIID_PANDORA_MUSIC_SEARCH"); break;
	             case PandoraServiceConstants.APIID_PANDORA_CREATE_STATION:
	                 Logger.d("APIID_PANDORA_CREATE_STATION"); break;
	             default:
	                 Logger.d("iAPIID: [" + iAPIID + "]"); break;
	         }
	         */
	     }
	 };
	 
	    /**
	     * Function basicCheck.
	     * 
	     * @param iResultCode iResultCode
	     * @param pResult PResult pResult
	     * @return if success return true, else return false
	     */
	    private boolean basicCheck(int iResultCode, PResultV2 pResult) {
	        boolean isCheckSuccess = true;
	        if (iResultCode == ConstantV2.REQUEST_SUCCESS) {
	            if (null != pResult) {
	                if (null == pResult.mData) {
	                    isCheckSuccess = false;
	                    Logger.d("pResult.mData is null");
	                }
	            } else {
	                isCheckSuccess = false;
	                Logger.d("pResult is null");
	            }
	        } else {
	            isCheckSuccess = false;
	            Logger.d("Fail reason: [" + iResultCode + "]");
	        }
	        return isCheckSuccess;
	    }
	    
	    
	    /**
	     * Function handleBeginPlayback.
	     * 
	     * @param pResult PResult pResult
	     */
	    private void handleBeginPlayback(PResultV2 pResult) {
	        if (PandoraServiceConstants.APIID_PANDORA_TOTAL_DURATION == pResult.mParameter) {
	            handleTotalDurationCallback(pResult);
	        }
	        if (PandoraServiceConstants.APIID_PANDORA_CURRENT_POSITION == pResult.mParameter) {
                handleCurrentPosition(pResult);
	        }
	        if (PandoraServiceConstants.APIID_PANDORA_UPDATE_PLAYLIST == pResult.mParameter) {
	            handleUpdatePlaylistCallback(pResult);
	        }
	    }
	    
	    /**
	     * Function handleTotalDurationCallback.
	     * 
	     * @param pResult PResult pResult
	     */
	    private void handleTotalDurationCallback(PResultV2 pResult) {
	        PPandora pPandora = (PPandora) pResult.mData;
	        int iTotalDuration = pPandora.getTotalDuration();
	        Logger.d("iTotalDuration: [" + iTotalDuration + "]");
	        
	        if (0 < iTotalDuration) {
	            sUpdatePlaylistId = getUUID();
	            Logger.d("sUpdatePlaylistId: [" + sUpdatePlaylistId + "]");
	            pandoraServiceAdapter.updatePlaylist(sUpdatePlaylistId);
	            
//	            iTotalDurationMinute = iTotalDuration / 60;
//	            iTotalDurationSecond = iTotalDuration % 60;
	        }
	        
	        iNowPlayIndex = pPandora.getNowPlayIndex();
	        Logger.d("iNowPlayIndex: [" + iNowPlayIndex + "]");
	    }
	    
	    /**
	     * Function handleCurrentPosition.
	     * 
	     * @param pResult PResult pResult
	     */
	    private void handleCurrentPosition(PResultV2 pResult) {
	        PPandora pPandora = (PPandora) pResult.mData;
	        int iCurrentPosition = pPandora.getCurrentPosition();
	        String sCurrentPosition = Integer.toString(iCurrentPosition);
	        //Logger.d("iCurrentPosition: [" + iCurrentPosition + "]");
	        //Logger.d("sCurrentPosition: [" + sCurrentPosition + "]");
	        
	        sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_UPDATE_CURRENT_POSITION, sCurrentPosition);
	        
/*	        if (0 < iCurrentPosition) {
	            iCurrentPositionMinute = iCurrentPosition / 60;
	            iCurrentPositionSecond = iCurrentPosition % 60;
	        }
*/	        
	    //    updateTextViewPlaylist();
	    }
	    
	    /**
	     * Function handleUpdatePlaylistCallback.
	     * 
	     * @param pResult PResult pResult
	     */
	    private void handleUpdatePlaylistCallback(PResultV2 pResult) {
	        PPandora pPandora = (PPandora) pResult.mData;        
	        int iReturnCode = pPandora.getGetPlaylistReturn();
	        
	        if (PandoraAPIConstants.GET_PLAYLIST_SUCCESS == iReturnCode) {
	            ArrayList<Item> alPlaylist = pPandora.getPlaylist();
	            
	            debugPlaylist(pPandora); // TODO debug playlist
	            
	            if (null != alPlaylist && 0 < alPlaylist.size()) {
	                
                    String sTotalDurationData = Integer.toString(pPandora.getTotalDuration());
                    Logger.d("sTotalDurationData [" + sTotalDurationData + "]");
	                
	            	if (application.playData.getPlayCount() > 0) {
	            		Item item = alPlaylist.get(iNowPlayIndex);
	            		if (item instanceof ParcelTrack) {
	            			ParcelTrack parcelTrack = (ParcelTrack) item;
	            			application.setPlayData(null, 
	            					parcelTrack.getSongName(), 
	            					parcelTrack.getArtistName(), 
	            					parcelTrack.getAlbumName(), 
	            					parcelTrack.getAlbumArtUrl(), 
	            					parcelTrack.getAudioUrl(), 
	            					parcelTrack.getTrackToken(), 
	            					parcelTrack.getSongRating(),
	            					sTotalDurationData,
	            					parcelTrack.isAllowFeedback(),
	            					false);
	            			sNowPlayTrackToken = parcelTrack.getTrackToken();
	            		} else if (item instanceof ParcelAd) {
	            			ParcelAd parcelAd = (ParcelAd) item;
	            			application.setPlayData(null, 
	            					parcelAd.getTitle(), 
	            					parcelAd.getCompanyName(), 
	            					"", 
	            					parcelAd.getImageUrl(), 
	            					"", 
	            					"", 
	            					"0",
	            					sTotalDurationData,
	            					false,
	            					true);
	            		}
	            		
	            		sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_UPDATE_PLAYLIST,"SUCCESS");
	            		return;
	            	}
	            	
	            	for (int i = 0 ; i < alPlaylist.size() ; i++) {
	            		if(iNowPlayIndex < i){
	            			break;
	            		}
	            		
	            		Item item = alPlaylist.get(i);
	            		if (item instanceof ParcelTrack) {
	            			ParcelTrack parcelTrack = (ParcelTrack) item;
	            			application.setPlayData(null, 
	            					parcelTrack.getSongName(), 
	            					parcelTrack.getArtistName(), 
	            					parcelTrack.getAlbumName(), 
	            					parcelTrack.getAlbumArtUrl(), 
	            					parcelTrack.getAudioUrl(), 
	            					parcelTrack.getTrackToken(), 
	            					parcelTrack.getSongRating(),
	            					sTotalDurationData,
	            					parcelTrack.isAllowFeedback(),
	            					false);
	            			sNowPlayTrackToken = parcelTrack.getTrackToken();
	            			
	            		}else if (item instanceof ParcelAd) {
	            			ParcelAd parcelAd = (ParcelAd) item;
	            			application.setPlayData(null, 
	            					parcelAd.getTitle(), 
	            					parcelAd.getCompanyName(), 
	            					"", 
	            					parcelAd.getImageUrl(), 
	            					"", 
	            					"", 
	            					"0",
	            					sTotalDurationData,
	            					false,
	            					true);
	            		}
	            	} 
	            	sendAppMsg(EventMessage.SERVICE_MSG,EventMessage.SERVICE_UPDATE_PLAYLIST, "SUCCESS");
	            }else {
	            	Debugger.debugReturnCode(iReturnCode);
	            	sendAppMsg(EventMessage.SERVICE_MSG,EventMessage.SERVICE_UPDATE_PLAYLIST, "FAIL");
	            }
	        }
	    }
	     
	    private String getUUID() {
	        return UUID.randomUUID().toString();
	    }
	    
	    public ArrayList<String> getSearchResultArtist(){
			return alMusicSearchResultArtist;
		}
		
		public ArrayList<String> getSearchResultArtistMusicToken(){
			return alMusicSearchResultArtistMusicToken;
		}
		
		public ArrayList<String> getSearchResultSongArtist(){
			return alMusicSearchResultSongArtist;
		}
		
		public ArrayList<String> getSearchResultSongMusicToken(){
			return alMusicSearchResultSongMusicToken;
		}
		
	    /**
	     * Function debugStationList.
	     * 
	     * @param pPandora PPandora pPandora
	     */
	    private void debugStationList(PPandora pPandora) {
	        /*
	    	int i = 0;
	        ArrayList<ParcelStation> alStationList = pPandora.getStationList();
	        if (0 < alStationList.size()) {
	            for (ParcelStation parcelStation : alStationList) {
	                //Logger.d("parcelStation[" + i + "].isAllowAddMusic(): [" + parcelStation.isAllowAddMusic() + "]");
	                //Logger.d("parcelStation[" + i + "].isAllowRename():   [" + parcelStation.isAllowRename() + "]");
	                Logger.d("parcelStation[" + i + "].getStationToken(): [" + parcelStation.getStationToken() + "]");
	                Logger.d("parcelStation[" + i + "].isAllowDelete():   [" + parcelStation.isAllowDelete() + "]");
	                //Logger.d("parcelStation[" + i + "].getDateCreated():  [" + parcelStation.getDateCreated() + "]");
	                Logger.d("parcelStation[" + i + "].isQuickMix():      [" + parcelStation.isQuickMix() + "]");
	                Logger.d("parcelStation[" + i + "].isShared():        [" + parcelStation.isShared() + "]");
	                //Logger.d("parcelStation[" + i + "].getStationId():    [" + parcelStation.getStationId() + "]");
	                Logger.d("parcelStation[" + i + "].getStationName():  [" + parcelStation.getStationName() + "]");
	                i++;
	            }
	        }
	        */
	    }
	    
	    /**
	     * Function debugPlaylist.
	     * 
	     * @param pPandora PPandora pPandora
	     */
	    private void debugPlaylist(PPandora pPandora) {
	    	int i = 0;
	        ArrayList<Item> alPlaylist = pPandora.getPlaylist();
	        if (null != alPlaylist && 0 < alPlaylist.size()) {
	            for (Item item : alPlaylist) {
	                if (item instanceof ParcelTrack) {
	                    ParcelTrack parcelTrack = (ParcelTrack) item;
	                    Logger.d("parcelTrack[" + i + "].getAlbumArtUrl():            [" + parcelTrack.getAlbumArtUrl() + "]");
	                    Logger.d("parcelTrack[" + i + "].getAlbumName():              [" + parcelTrack.getAlbumName() + "]");
	                    Logger.d("parcelTrack[" + i + "].isAllowFeedback():           [" + parcelTrack.isAllowFeedback() + "]");
	                    //Logger.d("parcelTrack[" + i + "].getAlbumDetailUrl():         [" + parcelTrack.getAlbumDetailUrl() + "]");
	                    //Logger.d("parcelTrack[" + i + "].getAmazonAlbumAsin():        [" + parcelTrack.getAmazonAlbumAsin() + "]");
	                    //Logger.d("parcelTrack[" + i + "].getAmazonAlbumDigitalAsin(): [" + parcelTrack.getAmazonAlbumDigitalAsin() + "]");
	                    //Logger.d("parcelTrack[" + i + "].getAmazonAlbumUrl():         [" + parcelTrack.getAmazonAlbumUrl() + "]");
	                    //Logger.d("parcelTrack[" + i + "].getAmazonSongDigitalAsin():  [" + parcelTrack.getAmazonSongDigitalAsin() + "]");
	                    //Logger.d("parcelTrack[" + i + "].getArtistDetailUrl():        [" + parcelTrack.getArtistDetailUrl() + "]");
	                    Logger.d("parcelTrack[" + i + "].getArtistName():             [" + parcelTrack.getArtistName() + "]");
	                    Logger.d("parcelTrack[" + i + "].getAudioUrl():               [" + parcelTrack.getAudioUrl() + "]");
	                    //Logger.d("parcelTrack[" + i + "].getItunesSongUrl():          [" + parcelTrack.getItunesSongUrl() + "]");
	                    Logger.d("parcelTrack[" + i + "].getSongRating():             [" + parcelTrack.getSongRating() + "]");
	                    //Logger.d("parcelTrack[" + i + "].getNewStationMessage():      [" + parcelTrack.getNewStationMessage() + "]");
	                    Logger.d("parcelTrack[" + i + "].getSongName():               [" + parcelTrack.getSongName() + "]");
	                    //Logger.d("parcelTrack[" + i + "].getSongDetailUrl():          [" + parcelTrack.getSongDetailUrl() + "]");
	                    //Logger.d("parcelTrack[" + i + "].getStationId():              [" + parcelTrack.getStationId() + "]");
	                    //Logger.d("parcelTrack[" + i + "].getTrackGain():              [" + parcelTrack.getTrackGain() + "]");
	                    Logger.d("parcelTrack[" + i + "].getTrackToken():             [" + parcelTrack.getTrackToken() + "]");
	                    i++;
	                } else if (item instanceof ParcelAd) {
	                    ParcelAd parcelAd = (ParcelAd) item;
	                    Logger.d("ParcelAd[" + i + "].getImageUrl():                  [" + parcelAd.getImageUrl() + "]");
	                    Logger.d("ParcelAd[" + i + "].getCompanyName():               [" + parcelAd.getCompanyName() + "]");
	                    Logger.d("ParcelAd[" + i + "].getTitle():                     [" + parcelAd.getTitle() + "]");
	                    Logger.d("ParcelAd[" + i + "].getAudioUrl():                  [" + parcelAd.getAudioUrl() + "]");
	                    i++;
	                } else {
	                    Logger.d("What is this?");
	                }
	            }
	        }
	    }   
	  
	    private boolean isReachSkipLimit(PResultV2 pResult) {
	        PPandora pPandora = (PPandora) pResult.mData;
	        
	        return pPandora.getIsReachSkipLimit();
	    }
	    
	    private boolean isADS(){
	    	int nLastIndex = application.playData.getPlayCount();
	    	nLastIndex--;
	    	if(application.playData.isADS(nLastIndex)){
				 String szMsg;
				 szMsg = mContext.getString(R.string.not_available_for_audio_ads);
				 sendAppMsg(EventMessage.SERVICE_MSG, EventMessage.SERVICE_ADS_OPTION_FAIL, szMsg);
				 return true;
			 }
	    	return false;
	    }
	    
	    private void sendAppMsg(int nWhat, int nArg1, Object objData){
			if(eventHandler == null){
				return;
			}
			
			Message msg = new Message();
			msg.what = nWhat; 		// message type
			msg.arg1 = nArg1; 		// message
			msg.arg2 = EventMessage.SERVICE_MSG; 	// window id
			msg.obj = objData;
			eventHandler.sendMessage(msg);
		}
}
