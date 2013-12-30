package comp.hp.ij.common.service.pandora;

import comp.hp.ij.common.service.pandora.util.Logger;
import comp.hp.ij.common.service.v2.base.ConstantV2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class ActivityBroadcastReceiver extends BroadcastReceiver {
    
    private Handler activityMessageHandler = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        String sAction = intent.getAction();
        //Logger.d("sAction: [" + sAction + "]");
        
        Bundle bundle = intent.getExtras();
        handleReceiveResult(sAction, bundle);
    }
    
    public void setMessageHandler(Handler messageHandlerInput) {
        activityMessageHandler = messageHandlerInput;
    }
    
    /**
     * handle service return result
     * 
     * @param intentInput intent input
     */
    private void handleReceiveResult(String sActionInput, Bundle bundleInput) {
        int iResultCode = -1;

        if (null != bundleInput) {
            iResultCode = bundleInput.getInt(ConstantV2.RESULT_CODE);
        } else {
            Logger.d("bundleInput is null");
        }
        
        if (PandoraServiceConstants.SERVICE_READY_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.PANDORA_SERVICE_READY);
        } else if (PandoraServiceConstants.SERVICE_IDLE_ALARM_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.PANDORA_SERVICE_FIRE_IDLE_ALARM);
        } else if (PandoraServiceConstants.SERVICE_ERROR_EVENT_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.PANDORA_SERVICE_ERROR_EVENT, iResultCode);
        } else if (PandoraServiceConstants.IS_ASSOCIATED_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_IS_ASSOCIATED, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.DO_LOGOUT_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_DO_LOGOUT, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.GENERATE_ACTIVIATION_CODE_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_GENERATE_ACTIVIATION_CODE, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.DO_DEVICE_LOGIN_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_DO_DEVICE_LOGIN, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.DO_LOGIN_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_DO_LOGIN, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.GET_NOWPLAY_STATION_DATA_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_GET_NOWPLAY_STATION_DATA, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.GET_STATION_LIST_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_GET_STATION_LIST, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.GET_PLAYLIST_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_GET_PLAYLIST, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.GET_AD_METADATA_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_GET_AD_METADATA, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.UPDATE_PLAYLIST_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_UPDATE_PLAYLIST, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.SKIP_SONG_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_SKIP_SONG, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.TOTAL_DURATION_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_TOTAL_DURATION, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.CURRENT_POSITION_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_CURRENT_POSITION, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.PAUSE_MUSIC_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_PAUSE_MUSIC, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.RESUME_MUSIC_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_RESUME_MUSIC, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.STOP_MUSIC_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_STOP_MUSIC, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.ADD_ARTIST_BOOKMARK_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_ADD_ARTIST_BOOKMARK, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.ADD_SONG_BOOKMARK_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_ADD_SONG_BOOKMARK, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.EXPLAIN_TRACK_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_EXPLAIN_TRACK, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.MUSIC_SEARCH_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_MUSIC_SEARCH, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.MUSIC_SEARCH_AUTO_COMPLETE_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_MUSIC_SEARCH_AUTO_COMPLETE, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.SLEEP_SONG_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_SLEEP_SONG, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.ADD_POSITIVE_FEEDBACK_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_ADD_POSITIVE_FEEDBACK, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.ADD_NEGATIVE_FEEDBACK_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_ADD_NEGATIVE_FEEDBACK, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.CREATE_STATION_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_CREATE_STATION, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.DELETE_STATION_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_DELETE_STATION, iResultCode, bundleInput);
        } else if (PandoraServiceConstants.DOWNLOAD_IMAGE_RETURN.equals(sActionInput)) {
            sendMessage2Activity(PandoraServiceConstants.APIID_PANDORA_DOWNLOAD_IMAGE, iResultCode, bundleInput);
        } else {
            Logger.d("Unknown action: [" + sActionInput + "]");
        }
    }
    
    /**
     * 
     * @param iWhatInput
     * @param arg1Input
     * @param arg2Input
     * @param objectInput
     */
    private void sendMessage2Activity(int iWhatInput, int arg1Input, int arg2Input, Object objectInput) {
        if (null == activityMessageHandler) {
            Logger.d("activityMessageHandler is null");
            return;
        }
        
        try {
            Message message = new Message();
            message.what = iWhatInput;
            message.arg1 = arg1Input;
            message.arg2 = arg2Input;
            message.obj = objectInput;
            activityMessageHandler.sendMessage(message);
        } catch (Exception e) {
            Logger.e(e);
        }
    }
    
    /**
     * 
     * @param iWhatInput iWhatInput
     * @param arg1Input arg1Input
     * @param objectInput objectInput
     */
    private void sendMessage2Activity(int iWhatInput, int arg1Input, Object objectInput) {
        sendMessage2Activity(iWhatInput, arg1Input, -1, objectInput);
    }

    /**
     * 
     * @param iWhatInput iWhatInput
     */
    private void sendMessage2Activity(int iWhatInput) {
        sendMessage2Activity(iWhatInput, -1, -1, null);
    }
    
    private void sendMessage2Activity(int iWhatInput, int arg1Input) {
        sendMessage2Activity(iWhatInput, arg1Input, -1, null);
    }
    
}
