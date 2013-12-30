package comp.hp.ij.common.service.pandora;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import comp.hp.ij.common.service.mplayer.adapter.MediaPlaybackServiceAdapter;
import comp.hp.ij.common.service.pandora.util.Logger;
import comp.hp.ij.common.service.v2.base.BaseServiceV2;
import comp.hp.ij.common.service.v2.base.ConstantV2;
import comp.hp.ij.common.service.v2.base.PResultV2;

/**
 * This code was developed by HON HAI PRECISION IND. CO., LTD., CMMSG/DWHD/CCP/SWI, Code for the Mars project.
 * 
 * @author Chance Wang
 */
public class PandoraService extends BaseServiceV2 {
            
    private PandoraServiceThread pandoraServiceThread = null;
    private PandoraServiceIdleAlarmThread pandoraServiceIdleAlarmThread = null;
    
    private ServiceBroadcastReceiver serviceBroadcastReceiver = new ServiceBroadcastReceiver();
    
    private MediaPlaybackServiceAdapter mediaPlaybackServiceAdapter = null;
    
    @Override
    public void onCreate() {
        Logger.d();
        super.onCreate();

        if (null == pandoraServiceThread) {
            pandoraServiceThread = new PandoraServiceThread(serviceMessageHandler);
            pandoraServiceThread.setPandoraService(this);
        }
        
        if (null == pandoraServiceIdleAlarmThread) {
            pandoraServiceIdleAlarmThread = new PandoraServiceIdleAlarmThread(serviceMessageHandler);
            new Thread(pandoraServiceIdleAlarmThread).start();
        }
        
        registerServiceReceiver();

        if (null == mediaPlaybackServiceAdapter) {
            mediaPlaybackServiceAdapter = new MediaPlaybackServiceAdapter(this);
            pandoraServiceThread.setMediaPlaybackServiceAdapter(mediaPlaybackServiceAdapter);
        }        
    }
    
    public void onStart(Intent intent, int startId) {
        Logger.d();
        notifyAdapterServiceIsReady();
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        Logger.d();
        return mBinder;
    }
    
    @Override
    public boolean onUnbind(Intent intent) {
        Logger.d();
        return super.onUnbind(intent);
    }
    
    @Override
    public void onDestroy() {
        Logger.d();
        super.onDestroy();
        unregisterServiceReceiver();
        mediaPlaybackServiceAdapter.destroy();
    }
        
    private final Handler serviceMessageHandler = new Handler() {
        public void handleMessage(Message message) {
            //Logger.d();
            
            //System.out.println("message.what [" + message.what + "]");
            //System.out.println("message.arg1 [" + message.arg1 + "]");
            //System.out.println("message.arg2 [" + message.arg2 + "]");
            
            switch(message.what) {
                case ConstantV2.PANDORA_SERVICE_BROADCAST_CALL: {
                    Bundle bundle = message.getData();
                    int iActivityHashCode = bundle.getInt(PandoraServiceConstants.ACTIVITY_HASH_CODE);
                    Logger.d("iActivityHashCode: [" + iActivityHashCode + "]");
                    String sUUID = bundle.getString(PandoraServiceConstants.UI_INPUT_UUID);
                    Logger.d("sUUID: [" + sUUID + "]");
                    int iAPIID = message.arg1;
                    Logger.d("iAPIID: [" + iAPIID + "]");
                    
                    switch (iAPIID) {
                        case PandoraServiceConstants.PANDORA_SERVICE_REGISTER_CLIENT: {
                            try {
                                mBinder.registerClient(iActivityHashCode);
        
                                Intent intent = new Intent(PandoraServiceConstants.SERVICE_READY_RETURN);
                                sendBroadcast(intent);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.PANDORA_SERVICE_UNREGISTER_CLIENT: {
                            try {
                                mBinder.unregisterClient(iActivityHashCode);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_IS_ASSOCIATED: {
                            try {
                                mBinder.isAssociated(iActivityHashCode, sUUID);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_DO_LOGOUT: {
                            try {
                                mBinder.doLogout(iActivityHashCode, sUUID);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_GENERATE_ACTIVIATION_CODE: {
                            try {
                                mBinder.generateActivitionCode(iActivityHashCode, sUUID);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_DO_DEVICE_LOGIN: {
                            try {
                                mBinder.doDeviceLogin(iActivityHashCode, sUUID);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_SET_LOGIN_AUTH: {
                            String sUserName = bundle.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_USERNAME);
                            String sPassword = bundle.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_PASSWORD);
                            try {
                                mBinder.setLoginAuth(sUserName, sPassword);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_DO_LOGIN: {
                            try {
                                mBinder.doLogin(iActivityHashCode, sUUID);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_GET_NOWPLAY_STATION_DATA: {
                            try {
                                mBinder.getNowPlayStationData(iActivityHashCode, sUUID);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_GET_STATION_LIST: {
                            try {
                                mBinder.getStationList(iActivityHashCode, sUUID);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_DELETE_STATION: {
                            String sStationToken = bundle.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_STATION_TOKEN);
                            try {
                                mBinder.deleteStation(iActivityHashCode, sUUID, sStationToken);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_GET_PLAYLIST: {
                            String sStationToken = bundle.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_STATION_TOKEN);
                            try {
                                mBinder.getPlaylist(iActivityHashCode, sUUID, sStationToken);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_UPDATE_PLAYLIST: {
                            try {
                                mBinder.updatePlaylist(iActivityHashCode, sUUID);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_DOWNLOAD_IMAGE: {
                            String sAlbumArtUrl = bundle.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_ALBUM_ART_URL);
                            try {
                                mBinder.downloadImage(iActivityHashCode, sUUID, sAlbumArtUrl);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_PAUSE_MUSIC: {
                            try {
                                mBinder.pauseMusic(iActivityHashCode, sUUID);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_RESUME_MUSIC: {
                            try {
                                mBinder.resumeMusic(iActivityHashCode, sUUID);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_SKIP_SONG: {
                            try {
                                mBinder.skipSong(iActivityHashCode, sUUID);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_STOP_MUSIC: {
                            try {
                                mBinder.stopMusic(iActivityHashCode, sUUID);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_ADD_ARTIST_BOOKMARK: {
                            String sTrackToken = bundle.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN);
                            try {
                                mBinder.addArtistBookmark(iActivityHashCode, sUUID, sTrackToken);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_ADD_SONG_BOOKMARK: {
                            String sTrackToken = bundle.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN);
                            try {
                                mBinder.addSongBookmark(iActivityHashCode, sUUID, sTrackToken);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_ADD_POSITIVE_FEEDBACK: {
                            String sTrackToken = bundle.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN);
                            try {
                                mBinder.addPostiveFeedback(iActivityHashCode, sUUID, sTrackToken);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_ADD_NEGATIVE_FEEDBACK: {
                            String sTrackToken = bundle.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN);
                            try {
                                mBinder.addNegativeFeedback(iActivityHashCode, sUUID, sTrackToken);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_SLEEP_SONG: {
                            String sTrackToken = bundle.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN);
                            try {
                                mBinder.sleepSong(iActivityHashCode, sUUID, sTrackToken);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_EXPLAIN_TRACK: {
                            String sTrackToken = bundle.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN);
                            try {
                                mBinder.explainTrack(iActivityHashCode, sUUID, sTrackToken);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_MUSIC_SEARCH: {
                            String sSearchText = bundle.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_SEARCH_TEXT);
                            try {
                                mBinder.musicSearch(iActivityHashCode, sUUID, sSearchText);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_MUSIC_SEARCH_AUTO_COMPLETE: {
                            String sSearchText = bundle.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_SEARCH_TEXT);
                            try {
                                mBinder.musicSearchAutoComplete(iActivityHashCode, sUUID, sSearchText);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.APIID_PANDORA_CREATE_STATION: {
                            String sMusicToken = bundle.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_MUSIC_TOKEN);
                            try {
                                mBinder.createStation(iActivityHashCode, sUUID, sMusicToken);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.PANDORA_SERVICE_RESET_IDLE_TIMER: {
                            try {
                                mBinder.resetIdleAlarmTimer();
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                            break;
                        }
                        case PandoraServiceConstants.PANDORA_SERVICE_UPDATE_WIDGET: {
                            Logger.d();
                            pandoraServiceThread.updateWidget();
                            break;
                        }
                    }
                    break;
                }
                
                case ConstantV2.REQUEST_COMPLETED: {                    
                    String sAPIID = message.getData().getString("APIID");
                    int iAPIID = Integer.parseInt(sAPIID);
                    String sUUID = message.getData().getString(PandoraServiceConstants.UI_INPUT_UUID);
                    PResultV2 pResult = (PResultV2) message.getData().getParcelable(ConstantV2.RESULT);
                    
                    if (null != pResult && PandoraServiceConstants.APIID_PANDORA_CURRENT_POSITION != pResult.mParameter) {
                        Logger.d("REQUEST_COMPLETED CLIENT_CODE: [" 
                                + message.getData().getInt(ConstantV2.CLIENT_CODE)
                                + "], sAPIID: [" + sAPIID + "], sUUID: [" + sUUID + "]");
                    }
                    
                    switch (iAPIID) {
                        case PandoraServiceConstants.PANDORA_SERVICE_READY:
                            simpleSendBroadcast(PandoraServiceConstants.SERVICE_READY_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_IS_ASSOCIATED:
                            simpleSendBroadcast(PandoraServiceConstants.IS_ASSOCIATED_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_DO_DEVICE_LOGIN:
                            simpleSendBroadcast(PandoraServiceConstants.DO_DEVICE_LOGIN_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_DO_LOGOUT:
                            simpleSendBroadcast(PandoraServiceConstants.DO_LOGOUT_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_GENERATE_ACTIVIATION_CODE:
                            simpleSendBroadcast(PandoraServiceConstants.GENERATE_ACTIVIATION_CODE_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_DO_LOGIN:
                            simpleSendBroadcast(PandoraServiceConstants.DO_LOGIN_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_GET_NOWPLAY_STATION_DATA:
                            simpleSendBroadcast(PandoraServiceConstants.GET_NOWPLAY_STATION_DATA_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_GET_STATION_LIST:
                            simpleSendBroadcast(PandoraServiceConstants.GET_STATION_LIST_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_DELETE_STATION:
                            simpleSendBroadcast(PandoraServiceConstants.DELETE_STATION_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_GET_PLAYLIST:
                            simpleSendBroadcast(PandoraServiceConstants.GET_PLAYLIST_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_UPDATE_PLAYLIST:
                            simpleSendBroadcast(PandoraServiceConstants.UPDATE_PLAYLIST_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_DOWNLOAD_IMAGE:
                            simpleSendBroadcast(PandoraServiceConstants.DOWNLOAD_IMAGE_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_GET_AD_METADATA:
                            simpleSendBroadcast(PandoraServiceConstants.GET_AD_METADATA_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_ADD_ARTIST_BOOKMARK:
                            simpleSendBroadcast(PandoraServiceConstants.ADD_ARTIST_BOOKMARK_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_ADD_SONG_BOOKMARK:
                            simpleSendBroadcast(PandoraServiceConstants.ADD_SONG_BOOKMARK_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_ADD_POSITIVE_FEEDBACK:
                            simpleSendBroadcast(PandoraServiceConstants.ADD_POSITIVE_FEEDBACK_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_ADD_NEGATIVE_FEEDBACK:
                            simpleSendBroadcast(PandoraServiceConstants.ADD_NEGATIVE_FEEDBACK_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_SLEEP_SONG:
                            simpleSendBroadcast(PandoraServiceConstants.SLEEP_SONG_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_EXPLAIN_TRACK:
//                            Logger.d();
                            simpleSendBroadcast(PandoraServiceConstants.EXPLAIN_TRACK_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_MUSIC_SEARCH:
                            simpleSendBroadcast(PandoraServiceConstants.MUSIC_SEARCH_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_MUSIC_SEARCH_AUTO_COMPLETE:
                            simpleSendBroadcast(PandoraServiceConstants.MUSIC_SEARCH_AUTO_COMPLETE_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_CREATE_STATION:
                            simpleSendBroadcast(PandoraServiceConstants.CREATE_STATION_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_TOTAL_DURATION:
                            simpleSendBroadcast(PandoraServiceConstants.TOTAL_DURATION_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_CURRENT_POSITION:
                            simpleSendBroadcast(PandoraServiceConstants.CURRENT_POSITION_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_PAUSE_MUSIC:
                            simpleSendBroadcast(PandoraServiceConstants.PAUSE_MUSIC_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_RESUME_MUSIC:
                            simpleSendBroadcast(PandoraServiceConstants.RESUME_MUSIC_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_SKIP_SONG:
                            simpleSendBroadcast(PandoraServiceConstants.SKIP_SONG_RETURN, message);
                            break;
                        case PandoraServiceConstants.APIID_PANDORA_STOP_MUSIC:
                            simpleSendBroadcast(PandoraServiceConstants.STOP_MUSIC_RETURN, message);
                            break;
                    }
                    break;
                } // end of case ConstantV2.REQUEST_COMPLETED
                case ConstantV2.PANDORA_SERVICE_INTERNAL_CALL: {
                    if (PandoraServiceConstants.PANDORA_SERVICE_FIRE_IDLE_ALARM == message.arg1) {
                        Logger.d();
                        simpleSendBroadcast(PandoraServiceConstants.SERVICE_IDLE_ALARM_RETURN, message);
                        
                        // TODO direct call mediaPlaybackServiceAdapter
                        mediaPlaybackServiceAdapter.pauseMusic();
                    } else {
                        Logger.d();
                        simpleSendBroadcast(PandoraServiceConstants.SERVICE_ERROR_EVENT_RETURN, message);
                    }
                    break;
                }
            } // end of switch(message.what)
        }
    };
    
    private void simpleSendBroadcast(String sAction, Message message) {
        Intent intent = new Intent(sAction);
        intent.putExtra(ConstantV2.CALLING_ID, message.getData().getInt(ConstantV2.CALLING_ID));
        intent.putExtra(PandoraServiceConstants.UI_INPUT_UUID, message.getData().getString(PandoraServiceConstants.UI_INPUT_UUID));
        intent.putExtra(ConstantV2.RESULT_CODE, message.arg2);
        intent.putExtra(ConstantV2.RESULT, (PResultV2) message.getData().getParcelable(ConstantV2.RESULT));
        sendBroadcast(intent);
    }
    
    private void notifyAdapterServiceIsReady() {
        Intent intent = new Intent(PandoraServiceConstants.NOTIFY_ADAPTER_SERVICE_IS_READY);
        sendBroadcast(intent);
    }
    
    private final IPandoraService.Stub mBinder = new IPandoraService.Stub() {
        
        public void registerClient(int iActivityHashCode) {
            Logger.d();
            PandoraService.this.registerClient(iActivityHashCode);
        }        

        public void unregisterClient(int iActivityHashCode) {
            Logger.d();
            PandoraService.this.unregisterClient(iActivityHashCode);
        }
        
        public void resetIdleAlarmTimer() {
            Logger.d();
            pandoraServiceIdleAlarmThread.resetIdleAlarmTimer();
        }
        
        // Check if the device is associated
        public int isAssociated(int iActivityHashCode, String sUUID) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_IS_ASSOCIATED, pandoraServiceThread);
            } else {
                return -1;
            }
        }
        
        public int doLogout(int iActivityHashCode, String sUUID) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_DO_LOGOUT, pandoraServiceThread);
            } else {
                return -1;
            }
        }

        public int generateActivitionCode(int iActivityHashCode, String sUUID) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_GENERATE_ACTIVIATION_CODE, pandoraServiceThread);
            } else {
                return -1;
            }
        }
        
        public int doDeviceLogin(int iActivityHashCode, String sUUID) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_DO_DEVICE_LOGIN, pandoraServiceThread);
            } else {
                return -1;
            }
        }
        
        // Set user name and password on PandoraServiceThread.
        public void setLoginAuth(String sUsername, String sPassword) {
            Logger.d();
            if (null != pandoraServiceThread) {
                pandoraServiceThread.setLoginAuth(sUsername, sPassword);
            } else {
                Logger.e("pandoraServiceThread is null");
            }
        }
        
        public int doLogin(int iActivityHashCode, String sUUID) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_DO_LOGIN, pandoraServiceThread);
            } else {
                return -1;
            }
        }
        
        public int getNowPlayStationData(int iActivityHashCode, String sUUID) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_GET_NOWPLAY_STATION_DATA, pandoraServiceThread);
            } else {
                return -1;
            }
        }
        
        public int getStationList(int iActivityHashCode, String sUUID) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_GET_STATION_LIST, pandoraServiceThread);
            } else {
                return -1;
            }
        }
        
        public int deleteStation(int iActivityHashCode, String sUUID, String sStationToken) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                bundle.putString(PandoraServiceConstants.ADAPTER_ARGUMENT_STATION_TOKEN, sStationToken);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_DELETE_STATION, pandoraServiceThread);
            } else {
                return -1;
            }
        }
        
        public int getPlaylist(int iActivityHashCode, String sUUID, String sStationToken) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                bundle.putString(PandoraServiceConstants.ADAPTER_ARGUMENT_STATION_TOKEN, sStationToken);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_GET_PLAYLIST, pandoraServiceThread);
            } else {
                return -1;
            }
        }
        
        public int updatePlaylist(int iActivityHashCode, String sUUID) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_UPDATE_PLAYLIST, pandoraServiceThread);
            } else {
                return -1;
            }
        }
        
        public int downloadImage(int iActivityHashCode, String sUUID, String sAlbumArtUrl) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                bundle.putString(PandoraServiceConstants.ADAPTER_ARGUMENT_ALBUM_ART_URL, sAlbumArtUrl);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_DOWNLOAD_IMAGE, pandoraServiceThread);
            } else {
                return -1;
            }
        }
        
        public int pauseMusic(int iActivityHashCode, String sUUID) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_PAUSE_MUSIC, pandoraServiceThread);
            } else {
                return -1;
            }
        }
        
        public int resumeMusic(int iActivityHashCode, String sUUID) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_RESUME_MUSIC, pandoraServiceThread);
            } else {
                return -1;
            }
        }
        
        public int skipSong(int iActivityHashCode, String sUUID) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_SKIP_SONG, pandoraServiceThread);
            } else {
                return -1;
            }
        }

        public int stopMusic(int iActivityHashCode, String sUUID) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_STOP_MUSIC, pandoraServiceThread);
            } else {
                return -1;
            }
        }
        
        public int addArtistBookmark(int iActivityHashCode, String sUUID, String sTrackToken) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                bundle.putString(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN, sTrackToken);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_ADD_ARTIST_BOOKMARK, pandoraServiceThread);
            } else {
                return -1;
            }
        }

        public int addSongBookmark(int iActivityHashCode, String sUUID, String sTrackToken) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                bundle.putString(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN, sTrackToken);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_ADD_SONG_BOOKMARK, pandoraServiceThread);
            } else {
                return -1;
            }
        }
        
        public int addPostiveFeedback(int iActivityHashCode, String sUUID, String sTrackToken) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                bundle.putString(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN, sTrackToken);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_ADD_POSITIVE_FEEDBACK, pandoraServiceThread);
            } else {
                return -1;
            }
        }

        public int addNegativeFeedback(int iActivityHashCode, String sUUID, String sTrackToken) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                bundle.putString(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN, sTrackToken);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_ADD_NEGATIVE_FEEDBACK, pandoraServiceThread);
            } else {
                return -1;
            }
        }
        
        public int sleepSong(int iActivityHashCode, String sUUID, String sTrackToken) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                bundle.putString(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN, sTrackToken);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_SLEEP_SONG, pandoraServiceThread);
            } else {
                return -1;
            }
        }

        public int explainTrack(int iActivityHashCode, String sUUID, String sTrackToken) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                bundle.putString(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN, sTrackToken);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_EXPLAIN_TRACK, pandoraServiceThread);
            } else {
                return -1;
            }
        }

        public int musicSearch(int iActivityHashCode, String sUUID, String sSearchText) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                bundle.putString(PandoraServiceConstants.ADAPTER_ARGUMENT_SEARCH_TEXT, sSearchText);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_MUSIC_SEARCH, pandoraServiceThread);
            } else {
                return -1;
            }
        }
        
        public int musicSearchAutoComplete(int iActivityHashCode, String sUUID, String sSearchText) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                bundle.putString(PandoraServiceConstants.ADAPTER_ARGUMENT_SEARCH_TEXT, sSearchText);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_MUSIC_SEARCH_AUTO_COMPLETE, pandoraServiceThread);
            } else {
                return -1;
            }
        }

        public int createStation(int iActivityHashCode, String sUUID, String sMusicToken) {
            Logger.d();
            if (null != pandoraServiceThread) {
                Bundle bundle = new Bundle();
                bundle.putString(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
                bundle.putString(PandoraServiceConstants.ADAPTER_ARGUMENT_MUSIC_TOKEN, sMusicToken);
                return executeServiceThread(iActivityHashCode, bundle, PandoraServiceConstants.APIID_PANDORA_CREATE_STATION, pandoraServiceThread);
            } else {
                return -1;
            }
        }
        
    };
    
    private void registerServiceReceiver() {
        Logger.d();
        IntentFilter intentFilter = new IntentFilter("comp.hp.ij.common.service.pandora.ServiceBroadcastReceiver");
        
        intentFilter.addAction(PandoraServiceConstants.REGISTER_CLIENT);
        intentFilter.addAction(PandoraServiceConstants.UNREGISTER_CLIENT);
        
        intentFilter.addAction(PandoraServiceConstants.IS_ASSOCIATED);
        intentFilter.addAction(PandoraServiceConstants.DO_LOGOUT);
        intentFilter.addAction(PandoraServiceConstants.GENERATE_ACTIVIATION_CODE);
        intentFilter.addAction(PandoraServiceConstants.DO_DEVICE_LOGIN);
        intentFilter.addAction(PandoraServiceConstants.SET_LOGIN_AUTH);
        intentFilter.addAction(PandoraServiceConstants.DO_LOGIN);
        intentFilter.addAction(PandoraServiceConstants.GET_NOWPLAY_STATION_DATA);
        intentFilter.addAction(PandoraServiceConstants.GET_STATION_LIST);
        
        intentFilter.addAction(PandoraServiceConstants.GET_PLAYLIST);
        intentFilter.addAction(PandoraServiceConstants.GET_AD_METADATA);
        intentFilter.addAction(PandoraServiceConstants.UPDATE_PLAYLIST);
        intentFilter.addAction(PandoraServiceConstants.SKIP_SONG);
        
        intentFilter.addAction(PandoraServiceConstants.TOTAL_DURATION);
        intentFilter.addAction(PandoraServiceConstants.CURRENT_POSITION);
        intentFilter.addAction(PandoraServiceConstants.PAUSE_MUSIC);
        intentFilter.addAction(PandoraServiceConstants.RESUME_MUSIC);
        intentFilter.addAction(PandoraServiceConstants.STOP_MUSIC);
        
        intentFilter.addAction(PandoraServiceConstants.ADD_ARTIST_BOOKMARK);
        intentFilter.addAction(PandoraServiceConstants.ADD_SONG_BOOKMARK);
        intentFilter.addAction(PandoraServiceConstants.EXPLAIN_TRACK);
        intentFilter.addAction(PandoraServiceConstants.MUSIC_SEARCH);
        intentFilter.addAction(PandoraServiceConstants.MUSIC_SEARCH_AUTO_COMPLETE);
        intentFilter.addAction(PandoraServiceConstants.SLEEP_SONG);
        intentFilter.addAction(PandoraServiceConstants.ADD_POSITIVE_FEEDBACK);
        intentFilter.addAction(PandoraServiceConstants.ADD_NEGATIVE_FEEDBACK);
        intentFilter.addAction(PandoraServiceConstants.CREATE_STATION);
        intentFilter.addAction(PandoraServiceConstants.DELETE_STATION);
        
        intentFilter.addAction(PandoraServiceConstants.DOWNLOAD_IMAGE);
        
        intentFilter.addAction(PandoraServiceConstants.RESET_IDLE_ALARM_TIMER);
        
        intentFilter.addAction(PandoraServiceConstants.UPDATE_WIDGET);
        
        serviceBroadcastReceiver.setMessageHandler(serviceMessageHandler);
        registerReceiver(serviceBroadcastReceiver, intentFilter);
    }

    private void unregisterServiceReceiver() {
        Logger.d();
        unregisterReceiver(serviceBroadcastReceiver);
        serviceBroadcastReceiver.setMessageHandler(null);
    }

}
