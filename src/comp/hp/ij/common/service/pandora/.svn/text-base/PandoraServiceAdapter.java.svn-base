package comp.hp.ij.common.service.pandora;

import comp.hp.ij.common.service.pandora.util.Logger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

public class PandoraServiceAdapter extends BroadcastReceiver {
    
    private int iActivityHashCode = 0;
        
    private Context context = null;
    
    private ActivityBroadcastReceiver activityBroadcastReceiver = new ActivityBroadcastReceiver();

    public PandoraServiceAdapter(Context contextInput, int iHashCodeInput) {
        iActivityHashCode = iHashCodeInput;
        Logger.d("iActivityHashCode: [" + iActivityHashCode + "]");
        
        context = contextInput;
        registerActivityReceiver();
        registerAdapterReceiver(); // must before startService()
        Intent intent = new Intent(PandoraServiceConstants.PANDORA_SERVICE_URI);
        context.startService(intent);
        //context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
    
    public void destroy() {
        Logger.d();
        try {
            unregisterActivityReceiver();
            unregisterAdapterReceiver();
            simpleSendBroadcast(PandoraServiceConstants.UNREGISTER_CLIENT);
            //interfacePandoraService.unregisterClient(iAdapterHashCode);
            //context.unbindService(serviceConnection);
        } catch (Exception e) {
            Logger.e(e);
        }
    }
    
    public void setActivityMessageHandler(Handler activityMessageHandlerInput) {
        activityBroadcastReceiver.setMessageHandler(activityMessageHandlerInput);
    }
    
    public void resetIdleAlarmTimer() {
        Logger.d();
        simpleSendBroadcast(PandoraServiceConstants.RESET_IDLE_ALARM_TIMER);
    }

    public void isAssociated(String sUUID) {
        Logger.d();
        simpleSendBroadcast(PandoraServiceConstants.IS_ASSOCIATED, sUUID);
    }
    
    public void doLogout(String sUUID) {
        Logger.d();
        simpleSendBroadcast(PandoraServiceConstants.DO_LOGOUT, sUUID);
    }
    
    public void generateActivationCode(String sUUID) {
        Logger.d();
        simpleSendBroadcast(PandoraServiceConstants.GENERATE_ACTIVIATION_CODE, sUUID);
    }
    
    public void doDeviceLogin(String sUUID) {
        Logger.d();
        simpleSendBroadcast(PandoraServiceConstants.DO_DEVICE_LOGIN, sUUID);
    }
    
    public void setLoginAuth(String sUsername, String sPassword) {
        Logger.d();
        Intent intent = new Intent();
        intent.putExtra(PandoraServiceConstants.ADAPTER_ARGUMENT_USERNAME, sUsername);
        intent.putExtra(PandoraServiceConstants.ADAPTER_ARGUMENT_PASSWORD, sPassword);
        simpleSendBroadcast(PandoraServiceConstants.SET_LOGIN_AUTH, "", intent);
    }
    
    public void doLogin(String sUUID) {
        Logger.d();
        simpleSendBroadcast(PandoraServiceConstants.DO_LOGIN, sUUID);
    }
    
    public void getNowPlayStationData(String sUUID) {
        Logger.d();
        simpleSendBroadcast(PandoraServiceConstants.GET_NOWPLAY_STATION_DATA, sUUID);
    }
    
    public void getStationList(String sUUID) {
        Logger.d();
        simpleSendBroadcast(PandoraServiceConstants.GET_STATION_LIST, sUUID);
    }
    
    public void deleteStation(String sUUID, String sStationToken) {
        Logger.d();
        Intent intent = new Intent();
        intent.putExtra(PandoraServiceConstants.ADAPTER_ARGUMENT_STATION_TOKEN, sStationToken);
        simpleSendBroadcast(PandoraServiceConstants.DELETE_STATION, sUUID, intent);
    }
    
    public void getPlaylist(String sUUID, String sStationToken) {
        Logger.d();
        Intent intent = new Intent();
        intent.putExtra(PandoraServiceConstants.ADAPTER_ARGUMENT_STATION_TOKEN, sStationToken);
        simpleSendBroadcast(PandoraServiceConstants.GET_PLAYLIST, sUUID, intent);
    }
    
    public void updatePlaylist(String sUUID) {
        Logger.d();
        simpleSendBroadcast(PandoraServiceConstants.UPDATE_PLAYLIST, sUUID);
    }
    
    public void downloadImage(String sUUID, String sAlbumArtUrl) {
        Logger.d();
        Intent intent = new Intent();
        intent.putExtra(PandoraServiceConstants.ADAPTER_ARGUMENT_ALBUM_ART_URL, sAlbumArtUrl);
        simpleSendBroadcast(PandoraServiceConstants.DOWNLOAD_IMAGE, sUUID, intent);
    }
    
    public void pauseMusic(String sUUID) {
        Logger.d();
        simpleSendBroadcast(PandoraServiceConstants.PAUSE_MUSIC, sUUID);
    }

    public void resumeMusic(String sUUID) {
        Logger.d();
        simpleSendBroadcast(PandoraServiceConstants.RESUME_MUSIC, sUUID);
    }

    public void skipSong(String sUUID) {
        Logger.d();
        simpleSendBroadcast(PandoraServiceConstants.SKIP_SONG, sUUID);
    }
    
    public void stopMusic(String sUUID) {
        Logger.d();
        simpleSendBroadcast(PandoraServiceConstants.STOP_MUSIC, sUUID);
    }
    
    public void addArtistBookmark(String sUUID, String sTrackToken) {
        Logger.d();
        Intent intent = new Intent();
        intent.putExtra(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN, sTrackToken);
        simpleSendBroadcast(PandoraServiceConstants.ADD_ARTIST_BOOKMARK, sUUID, intent);
    }

    public void addSongBookmark(String sUUID, String sTrackToken) {
        Logger.d();
        Intent intent = new Intent();
        intent.putExtra(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN, sTrackToken);
        simpleSendBroadcast(PandoraServiceConstants.ADD_SONG_BOOKMARK, sUUID, intent);
    }

    public void addPostiveFeedback(String sUUID, String sTrackToken) {
        Logger.d();
        Intent intent = new Intent();
        intent.putExtra(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN, sTrackToken);
        simpleSendBroadcast(PandoraServiceConstants.ADD_POSITIVE_FEEDBACK, sUUID, intent);
    }

    public void addNegativeFeedback(String sUUID, String sTrackToken) {
        Logger.d();
        Intent intent = new Intent();
        intent.putExtra(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN, sTrackToken);
        simpleSendBroadcast(PandoraServiceConstants.ADD_NEGATIVE_FEEDBACK, sUUID, intent);
    }
    
    public void sleepSong(String sUUID, String sTrackToken) {
        Logger.d();
        Intent intent = new Intent();
        intent.putExtra(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN, sTrackToken);
        simpleSendBroadcast(PandoraServiceConstants.SLEEP_SONG, sUUID, intent);
    }

    public void explainTrack(String sUUID, String sTrackToken) {
        Logger.d();
        Intent intent = new Intent();
        intent.putExtra(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN, sTrackToken);
        simpleSendBroadcast(PandoraServiceConstants.EXPLAIN_TRACK, sUUID, intent);
    }

    public void musicSearch(String sUUID, String sSearchText) {
        Logger.d();
        Intent intent = new Intent();
        intent.putExtra(PandoraServiceConstants.ADAPTER_ARGUMENT_SEARCH_TEXT, sSearchText);
        simpleSendBroadcast(PandoraServiceConstants.MUSIC_SEARCH, sUUID, intent);
    }
    
    public void musicSearchAutoComplete(String sUUID, String sSearchText) {
        Logger.d();
        Intent intent = new Intent();
        intent.putExtra(PandoraServiceConstants.ADAPTER_ARGUMENT_SEARCH_TEXT, sSearchText);
        simpleSendBroadcast(PandoraServiceConstants.MUSIC_SEARCH_AUTO_COMPLETE, sUUID, intent);
    }

    public void createStation(String sUUID, String sMusicToken) {
        Logger.d();
        Intent intent = new Intent();
        intent.putExtra(PandoraServiceConstants.ADAPTER_ARGUMENT_MUSIC_TOKEN, sMusicToken);
        simpleSendBroadcast(PandoraServiceConstants.CREATE_STATION, sUUID, intent);
    }
    
    private void simpleSendBroadcast(String sActionInput, String sUUID, Intent intentInput) {
        Logger.d("sActionInput [" + sActionInput + "]");
        Intent intent = null;
        if (null != intentInput) {
            intent = intentInput;
            intent.setAction(sActionInput);
        } else {
            intent = new Intent(sActionInput);
        }
        
        intent.putExtra(PandoraServiceConstants.ACTIVITY_HASH_CODE, iActivityHashCode);
        intent.putExtra(PandoraServiceConstants.UI_INPUT_UUID, sUUID);
        context.sendBroadcast(intent);
    }
    
    private void simpleSendBroadcast(String sActionInput, String sUUID) {
        simpleSendBroadcast(sActionInput, sUUID, null);
    }
    
    private void simpleSendBroadcast(String sActionInput) {
        simpleSendBroadcast(sActionInput, null, null);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String sAction = intent.getAction();
        Logger.d("sAction: [" + sAction + "]");
        
        if (PandoraServiceConstants.NOTIFY_ADAPTER_SERVICE_IS_READY.equals(sAction)) {
            simpleSendBroadcast(PandoraServiceConstants.REGISTER_CLIENT);
        }
    }
    
    private void registerAdapterReceiver() {
        Logger.d();
        IntentFilter intentFilter = new IntentFilter("comp.hp.ij.common.service.pandora.PandoraServiceAdapter");
        intentFilter.addAction(PandoraServiceConstants.NOTIFY_ADAPTER_SERVICE_IS_READY);        
        context.registerReceiver(this, intentFilter);
    }

    private void unregisterAdapterReceiver() {
        Logger.d();
        context.unregisterReceiver(this);
    }
    
    private void registerActivityReceiver() {
        Logger.d();
        IntentFilter intentFilter = new IntentFilter("comp.hp.ij.common.service.pandora.ActivityBroadcastReceiver");
                
        intentFilter.addAction(PandoraServiceConstants.SERVICE_READY_RETURN);
        intentFilter.addAction(PandoraServiceConstants.SERVICE_IDLE_ALARM_RETURN);
        intentFilter.addAction(PandoraServiceConstants.SERVICE_ERROR_EVENT_RETURN);
        
        intentFilter.addAction(PandoraServiceConstants.DO_LOGIN_RETURN);
        intentFilter.addAction(PandoraServiceConstants.DO_DEVICE_LOGIN_RETURN);
        intentFilter.addAction(PandoraServiceConstants.GET_NOWPLAY_STATION_DATA_RETURN);
        intentFilter.addAction(PandoraServiceConstants.GET_STATION_LIST_RETURN);
        intentFilter.addAction(PandoraServiceConstants.GET_PLAYLIST_RETURN);
        intentFilter.addAction(PandoraServiceConstants.GET_AD_METADATA_RETURN);
        intentFilter.addAction(PandoraServiceConstants.UPDATE_PLAYLIST_RETURN);
        intentFilter.addAction(PandoraServiceConstants.SKIP_SONG_RETURN);
        
        intentFilter.addAction(PandoraServiceConstants.TOTAL_DURATION_RETURN);
        intentFilter.addAction(PandoraServiceConstants.CURRENT_POSITION_RETURN);
        intentFilter.addAction(PandoraServiceConstants.PAUSE_MUSIC_RETURN);
        intentFilter.addAction(PandoraServiceConstants.RESUME_MUSIC_RETURN);
        intentFilter.addAction(PandoraServiceConstants.STOP_MUSIC_RETURN);
        
        intentFilter.addAction(PandoraServiceConstants.IS_ASSOCIATED_RETURN);
        intentFilter.addAction(PandoraServiceConstants.GENERATE_ACTIVIATION_CODE_RETURN);
        intentFilter.addAction(PandoraServiceConstants.DO_LOGOUT_RETURN);
        intentFilter.addAction(PandoraServiceConstants.ADD_ARTIST_BOOKMARK_RETURN);
        intentFilter.addAction(PandoraServiceConstants.ADD_SONG_BOOKMARK_RETURN);
        intentFilter.addAction(PandoraServiceConstants.EXPLAIN_TRACK_RETURN);
        intentFilter.addAction(PandoraServiceConstants.MUSIC_SEARCH_RETURN);
        intentFilter.addAction(PandoraServiceConstants.MUSIC_SEARCH_AUTO_COMPLETE_RETURN);
        intentFilter.addAction(PandoraServiceConstants.SLEEP_SONG_RETURN);
        intentFilter.addAction(PandoraServiceConstants.ADD_POSITIVE_FEEDBACK_RETURN);
        intentFilter.addAction(PandoraServiceConstants.ADD_NEGATIVE_FEEDBACK_RETURN);
        intentFilter.addAction(PandoraServiceConstants.CREATE_STATION_RETURN);
        intentFilter.addAction(PandoraServiceConstants.DELETE_STATION_RETURN);
        
        intentFilter.addAction(PandoraServiceConstants.DOWNLOAD_IMAGE_RETURN);
        
        context.registerReceiver(activityBroadcastReceiver, intentFilter);
    }
    
    private void unregisterActivityReceiver() {
        context.unregisterReceiver(activityBroadcastReceiver);
        activityBroadcastReceiver.setMessageHandler(null);
    }

}
