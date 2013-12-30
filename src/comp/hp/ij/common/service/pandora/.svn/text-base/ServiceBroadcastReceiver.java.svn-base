package comp.hp.ij.common.service.pandora;

import comp.hp.ij.common.service.pandora.util.Logger;
import comp.hp.ij.common.service.v2.base.ConstantV2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class ServiceBroadcastReceiver extends BroadcastReceiver {
    
    private Handler serviceMessageHandler = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        String sAction = intent.getAction();
        Logger.d("sAction: [" + sAction + "]");
        
        Bundle bundle = intent.getExtras();
        
        if (PandoraServiceConstants.REGISTER_CLIENT.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.PANDORA_SERVICE_REGISTER_CLIENT, bundle);
        } else if (PandoraServiceConstants.UNREGISTER_CLIENT.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.PANDORA_SERVICE_UNREGISTER_CLIENT, bundle);
        } else if (PandoraServiceConstants.IS_ASSOCIATED.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_IS_ASSOCIATED, bundle);
        } else if (PandoraServiceConstants.DO_LOGOUT.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_DO_LOGOUT, bundle);
        } else if (PandoraServiceConstants.GENERATE_ACTIVIATION_CODE.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_GENERATE_ACTIVIATION_CODE, bundle);
        } else if (PandoraServiceConstants.DO_DEVICE_LOGIN.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_DO_DEVICE_LOGIN, bundle);
        } else if (PandoraServiceConstants.SET_LOGIN_AUTH.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_SET_LOGIN_AUTH, bundle);
        } else if (PandoraServiceConstants.DO_LOGIN.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_DO_LOGIN, bundle);
        } else if (PandoraServiceConstants.GET_NOWPLAY_STATION_DATA.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_GET_NOWPLAY_STATION_DATA, bundle);
        } else if (PandoraServiceConstants.GET_STATION_LIST.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_GET_STATION_LIST, bundle);
        } else if (PandoraServiceConstants.DELETE_STATION.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_DELETE_STATION, bundle);
        } else if (PandoraServiceConstants.GET_PLAYLIST.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_GET_PLAYLIST, bundle);
        } else if (PandoraServiceConstants.UPDATE_PLAYLIST.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_UPDATE_PLAYLIST, bundle);
        } else if (PandoraServiceConstants.DOWNLOAD_IMAGE.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_DOWNLOAD_IMAGE, bundle);
        } else if (PandoraServiceConstants.PAUSE_MUSIC.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_PAUSE_MUSIC, bundle);
        } else if (PandoraServiceConstants.RESUME_MUSIC.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_RESUME_MUSIC, bundle);
        } else if (PandoraServiceConstants.SKIP_SONG.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_SKIP_SONG, bundle);
        } else if (PandoraServiceConstants.STOP_MUSIC.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_STOP_MUSIC, bundle);
        } else if (PandoraServiceConstants.ADD_ARTIST_BOOKMARK.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_ADD_ARTIST_BOOKMARK, bundle);
        } else if (PandoraServiceConstants.ADD_SONG_BOOKMARK.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_ADD_SONG_BOOKMARK, bundle);
        } else if (PandoraServiceConstants.ADD_POSITIVE_FEEDBACK.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_ADD_POSITIVE_FEEDBACK, bundle);
        } else if (PandoraServiceConstants.ADD_NEGATIVE_FEEDBACK.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_ADD_NEGATIVE_FEEDBACK, bundle);
        } else if (PandoraServiceConstants.SLEEP_SONG.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_SLEEP_SONG, bundle);
        } else if (PandoraServiceConstants.EXPLAIN_TRACK.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_EXPLAIN_TRACK, bundle);
        } else if (PandoraServiceConstants.MUSIC_SEARCH.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_MUSIC_SEARCH, bundle);
        } else if (PandoraServiceConstants.MUSIC_SEARCH_AUTO_COMPLETE.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_MUSIC_SEARCH_AUTO_COMPLETE, bundle);
        } else if (PandoraServiceConstants.CREATE_STATION.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.APIID_PANDORA_CREATE_STATION, bundle);
        } else if (PandoraServiceConstants.RESET_IDLE_ALARM_TIMER.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.PANDORA_SERVICE_RESET_IDLE_TIMER, bundle);
        } else if (PandoraServiceConstants.UPDATE_WIDGET.equals(sAction)) {
            sendMessage2Service(PandoraServiceConstants.PANDORA_SERVICE_UPDATE_WIDGET, bundle);
        } else {
            Logger.d("Unknown action: [" + sAction + "]");
        }
    }
    
    
    public void setMessageHandler(Handler messageHandlerInput) {
        serviceMessageHandler = messageHandlerInput;
    }
    
    private void sendMessage2Service(int arg1Input, Bundle bundleInput) {
        if (null == serviceMessageHandler) {
            Logger.d("serviceMessageHandler is null");
            return;
        }
        
        try {
            Message message = new Message();
            message.what = ConstantV2.PANDORA_SERVICE_BROADCAST_CALL;
            message.arg1 = arg1Input;
            message.setData(bundleInput);
            serviceMessageHandler.sendMessage(message);
        } catch (Exception e) {
            Logger.e(e);
        }
    }
    
}
