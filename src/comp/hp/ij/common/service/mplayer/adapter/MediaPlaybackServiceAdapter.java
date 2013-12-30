package comp.hp.ij.common.service.mplayer.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

import comp.hp.ij.common.service.mplayer.IMediaPlaybackService;
import comp.hp.ij.common.service.mplayer.IMediaPlaybackServiceCallback;
import comp.hp.ij.common.service.mplayer.PlayerState;
import comp.hp.ij.common.service.pandora.PandoraService;
import comp.hp.ij.common.service.pandora.PandoraServiceConstants;
import comp.hp.ij.common.service.pandora.api.Item;
import comp.hp.ij.common.service.pandora.data.ParcelAd;
import comp.hp.ij.common.service.pandora.data.ParcelStation;
import comp.hp.ij.common.service.pandora.data.ParcelTrack;
import comp.hp.ij.common.service.pandora.util.Logger;

/**
 * This class hide the detail of using service. Let the user more easy use it.
 * 
 * <P>
 * This code was developed by HON HAI PRECISION IND. CO., LTD., CMMSG/DWHD/CCP/SWI, Code for the Mars project.
 * 
 * @author Chance Wang
 */
public class MediaPlaybackServiceAdapter {
    private int iTotalDuration = 0;
    private int iCurrentPosition = -1;

    private PandoraService pandoraService = null;
    private IMediaPlaybackService interfaceMediaPlaybackService;

    private Handler threadMessageHandler;

    private String sNowPlayStationToken = null;
    private List<String> alStationTokens = new ArrayList<String>();
    private Map<String, String> mapStationNames = new HashMap<String, String>();
    private Map<String, Integer> mapStationSkipCounter = new HashMap<String, Integer>();
    private Map<String, Long> mapStationSkipTime = new HashMap<String, Long>();

    private int iNowPlayIndex = -1;
    private ArrayList<Item> alPlaylist = new ArrayList<Item>();

    private boolean isThumbsDown = false;
    private boolean isMusicPaused = false;
    private boolean isSkipSong = false;
    private boolean isDeleteStation = false;
    
    private boolean isReachSkipLimitation = false;

    /**
     * 
     * @param pandoraServiceInput pandoraServiceInput
     */
    public MediaPlaybackServiceAdapter(PandoraService pandoraServiceInput) {
        pandoraService = pandoraServiceInput;
        
        Intent intent = new Intent("comp.hp.ij.common.service.mplayer.PlayerService.REMOTE_SERVICE");
        ((Context) pandoraService).startService(intent);
        ((Context) pandoraService).bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    } // end of PandoraServiceAdapter()

    public void destroy() {
        Logger.d();
        try {
            if (null != interfaceMediaPlaybackService) {
                interfaceMediaPlaybackService.unregisterCallback(mCallback);
            }
            if (null != pandoraService) {
                ((Context) pandoraService).unbindService(serviceConnection);
            }
        } catch (Exception e) {
            Logger.e(e);
        }
    } // end of destroy()

    public boolean isServiceAvailable() {
        if (null == interfaceMediaPlaybackService) {
            return false;
        } else {
            return true;
        }
    } // end of IsServiceAvailable()

    public void setMessageHandler(Handler messageHandler) {
        threadMessageHandler = messageHandler;
    }
    
    public void clearStationData() {
        sNowPlayStationToken = null;
        alStationTokens.clear();
        mapStationNames.clear();
        mapStationSkipCounter.clear();
        mapStationSkipTime.clear();
    }

    public void setStationList(ArrayList<ParcelStation> alStationListInput) {
        if (null != alStationListInput) {
            for (ParcelStation parcelStation : alStationListInput) {
                String sStationToken = parcelStation.getStationToken();
                
                if (!alStationTokens.contains(sStationToken)) {
                    if (1 < alStationListInput.size()) {
                        alStationTokens.add(sStationToken);
                    } else {
                        if (0 != alStationTokens.size()) {
                            // TODO new station will be insert at index 1 because sorting by creating date
                            alStationTokens.add(1, sStationToken);
                        }
                    }
                }
                
                mapStationNames.put(sStationToken, parcelStation.getStationName());
                
                if (!mapStationSkipCounter.containsKey(sStationToken)) {
                    //Logger.d("initial skip counter for [" + parcelStation.getStationName() + "][" + sStationToken + "]");
                    mapStationSkipCounter.put(sStationToken, 0);
                }
                if (!mapStationSkipTime.containsKey(sStationToken)) {
                    //Logger.d("initial skip time for [" + parcelStation.getStationName() + "][" + sStationToken + "]");
                    mapStationSkipTime.put(sStationToken, 0l);
                }
            }
        } else {
            Logger.e("alStationListInput is null");
        }
    }

    public void setNowPlayStationToken(String sStationTokenInput) {
        sNowPlayStationToken = sStationTokenInput;
    }
    
    public int getNowPlayStationIndex() {
        return alStationTokens.indexOf(sNowPlayStationToken);
    }

    public String getNowPlayStationToken() {
        return sNowPlayStationToken;
    }

    public String getNowPlayStationName() {
        return mapStationNames.get(sNowPlayStationToken);
    }

    public void addPlaylist(ArrayList<Item> alPlaylistInput) {
        Logger.d();
        
        if (null != alPlaylistInput) {
            Logger.d("alPlayList.size() - before add: [" + alPlaylist.size() + "]");
            
            for (Item item : alPlaylistInput) {
                alPlaylist.add(item);
            }
            iNowPlayIndex++;
            
            int iRemoveCounter = iNowPlayIndex - PandoraServiceConstants.MAX_PREVIOUS_TRACKS;
            if (0 < iRemoveCounter) {
                for (int i = 0; i < iRemoveCounter; i++) {
                    alPlaylist.remove(0);
                    iNowPlayIndex--;
                }
            }
            
            Logger.d("alPlayList.size() - after add:  [" + alPlaylist.size() + "]");            
        } else {
            Logger.d("alPlaylistInput is null");
        }
    }

    public void setPlaylist(ArrayList<Item> alPlaylistInput) {
        if (null != alPlaylistInput) {
            Logger.d("alPlayListInput.size(): [" + alPlaylistInput.size() + "]");
            alPlaylist = alPlaylistInput;
            Logger.d("alPlayList.size():      [" + alPlaylist.size() + "]");
        } else {
            Logger.d("alPlaylistInput is null");
        }
    }

    public ArrayList<Item> getPlaylist() {
        return alPlaylist;
    }

    public void playMusic() {
        try {
            iNowPlayIndex = (-1 == iNowPlayIndex) ? 0 : iNowPlayIndex;
            Item item = alPlaylist.get(iNowPlayIndex);
            if (item instanceof ParcelTrack) {
                ParcelTrack parcelTrack = (ParcelTrack) item;
                Logger.d("[" + iNowPlayIndex + "][" + parcelTrack.getSongName() + "]");
                interfaceMediaPlaybackService.openUri(parcelTrack.getAudioUrl());
            } else if (item instanceof ParcelAd) {
                ParcelAd parcelAd = (ParcelAd) item;
                String sAdToken = parcelAd.getAdToken();
                Logger.d("sAdToken: [" + sAdToken + "]");
                sendMessage2Thread(PandoraServiceConstants.APIID_PANDORA_GET_AD_METADATA, sAdToken);
            } else {
                Logger.d("What is this?");
            }
        } catch (Exception e) {
            Logger.e(e);
        }
    }

    public void playMusic(String sAudioURL) {
        try {
            interfaceMediaPlaybackService.openUri(sAudioURL);
        } catch (Exception e) {
            Logger.e(e);
        }
    }

    public void setAdMetadata(Item itemInput) {
        String sAdToken = ((ParcelAd) itemInput).getAdToken();
        String sAudioUrl = ((ParcelAd) itemInput).getAudioUrl();
        String sCompanyName = ((ParcelAd) itemInput).getCompanyName();
        String sImageUrl = ((ParcelAd) itemInput).getImageUrl();
        String sTitle = ((ParcelAd) itemInput).getTitle();

        Logger.d("sAdToken:     [" + sAdToken + "]");
        Logger.d("sAudioUrl:    [" + sAudioUrl + "]");
        Logger.d("sCompanyName: [" + sCompanyName + "]");
        Logger.d("sImageUrl:    [" + sImageUrl + "]");
        Logger.d("sTitle:       [" + sTitle + "]");

        Item item = alPlaylist.get(iNowPlayIndex);
        if (item instanceof ParcelAd) {
            ParcelAd parcelAd = (ParcelAd) item;
            Logger.d("parcelAd.getAdToken(): [" + parcelAd.getAdToken() + "]");
            parcelAd.setAudioUrl(sAudioUrl);
            parcelAd.setCompanyName(sCompanyName);
            parcelAd.setImageUrl(sImageUrl);
            parcelAd.setTitle(sTitle);
        } else {
            Logger.d("Not a ad");
        }

        try {
            interfaceMediaPlaybackService.openUri(sAudioUrl);
        } catch (Exception e) {
            Logger.e(e);
        }
    }

    public void pauseMusic() {
        if (isMusicPaused) {
            return;
        }

        try {
            // +++++ certification 1.25 +++++ //
            int iCurrentPositionReturn = -1;

            if (isPlaying()) {
                iCurrentPositionReturn = interfaceMediaPlaybackService.getPosition() / 1000;
                
                int iBytesPerSecond = (128 / 8) * 1024;
                int iFileSizeBytes = iBytesPerSecond * iCurrentPositionReturn;
                
                Logger.d("iFileSizeBytes: [" + iFileSizeBytes + "]");
            }
            // ----- certification 1.25 ----- //
            interfaceMediaPlaybackService.pauseMusic();
        } catch (Exception e) {
            Logger.e(e);
        }
    }

    public void resumeMusic() {
        if (!isMusicPaused) {
            return;
        }

        try {
            interfaceMediaPlaybackService.playMusic();
        } catch (Exception e) {
            Logger.e(e);
        }
    }

    public void thumbsUp() {
        Item item = alPlaylist.get(iNowPlayIndex);
        if (item instanceof ParcelTrack) {
            ParcelTrack parcelTrack = (ParcelTrack) item;
            Logger.d("[" + iNowPlayIndex + "][" + parcelTrack.getSongName() + "]");
            parcelTrack.setSongRating("1");
        }
    }

    public void thumbsDown() {
        Item item = alPlaylist.get(iNowPlayIndex);
        if (item instanceof ParcelTrack) {
            ParcelTrack parcelTrack = (ParcelTrack) item;
            Logger.d("Thumbs down [" + iNowPlayIndex + "][" + parcelTrack.getSongName() + "]");
            parcelTrack.setSongRating("-1");
        }
        
        isThumbsDown = true;
        isReachSkipLimitation = checkIfReachSkipLimitation();
        if (!isReachSkipLimitation) {
            if (isPlaying()) {
                stopMusic();
            } else {
                Message message = new Message();
                message.what = PlayerState.PLAYER_IDLE;
                mPlayMsgHandle.sendMessage(message);
            }
        }
    }

    public void skipSong() {
        isSkipSong = true;
        isReachSkipLimitation = checkIfReachSkipLimitation();
        if (!isReachSkipLimitation) {
            if (isPlaying()) {
                stopMusic();
            } else {
                Message message = new Message();
                message.what = PlayerState.PLAYER_IDLE;
                mPlayMsgHandle.sendMessage(message);
            }
        }
    }
    
    public boolean isReachSkipLimitation() {
        return isReachSkipLimitation;
    }

    public void getTotalDuration() {
        try {
            iTotalDuration = interfaceMediaPlaybackService.getDuration() / 1000;
            Logger.d("iTotalDuration: [" + iTotalDuration + "]");

            sendMessage2Thread(PandoraServiceConstants.APIID_PANDORA_TOTAL_DURATION, iTotalDuration);
        } catch (Exception e) {
            Logger.e(e);
        }
    }

    public int getCurrentPosition() {
        int iCurrentPositionReturn = -1;

        try {
            if (isPlaying()) {
                iCurrentPositionReturn = interfaceMediaPlaybackService.getPosition() / 1000;
            }
        } catch (Exception e) {
            Logger.e(e);
        }

        return iCurrentPositionReturn;
    }

    public int stopMusic() {
        int iStopMusicReturn = -1;

        try {
            interfaceMediaPlaybackService.stopMusic();
            iStopMusicReturn = 1;
        } catch (Exception e) {
            Logger.e(e);
        }

        return iStopMusicReturn;
    }

    public void setNowPlayIndex(int iNowPlayIndexInput) {
        iNowPlayIndex = iNowPlayIndexInput;
    }

    public int getNowPlayIndex() {
        return iNowPlayIndex;
    }

    public boolean isPlaying() {
        boolean isPlayingReturn = false;
        try {
            isPlayingReturn = interfaceMediaPlaybackService.isPlaying();
        } catch (Exception e) {
            Logger.e(e);
        }
        return isPlayingReturn;
    }
    
    public void deleteStationData(String sStationTokenInput) {
        if (null != sStationTokenInput) {
            if (alStationTokens.contains(sStationTokenInput)) {
                alStationTokens.remove(sStationTokenInput);
            }
            if (mapStationNames.containsKey(sStationTokenInput)) {
                Logger.d("Remove station [" + mapStationNames.get(sStationTokenInput) + "]");
                mapStationNames.remove(sStationTokenInput);
            }
            if (mapStationSkipCounter.containsKey(sStationTokenInput)) {
                mapStationSkipCounter.remove(sStationTokenInput);
            }
            if (mapStationSkipTime.containsKey(sStationTokenInput)) {
                mapStationSkipTime.remove(sStationTokenInput);
            }
            
            if (1 == alStationTokens.size()) {
                alStationTokens.clear();
                mapStationNames.clear();
                mapStationSkipCounter.clear();
                mapStationSkipTime.clear();
            }
        }
    }

    public boolean checkIsDeleteNowPlayStation(String sStationTokenInput) {
        Logger.d("sStationTokenInput:   [" + sStationTokenInput + "]");
        Logger.d("sNowPlayStationToken: [" + sNowPlayStationToken + "]");
        
        if (null != sStationTokenInput && null != sNowPlayStationToken) {            
            if (sStationTokenInput.equals(sNowPlayStationToken)) {
                isDeleteStation = true;
                sNowPlayStationToken = null;
                
                int iStopMusicReturn = stopMusic();
                Logger.d("iStopMusicReturn: [" + iStopMusicReturn + "]");
                
                return true;
            }
        }        
        return false;
    }
    
    public void removeCurrentAd() {
        Logger.d("iNowPlayIndex: [" + iNowPlayIndex + "]");
        Item item = alPlaylist.get(iNowPlayIndex);
        if (null != item && item instanceof ParcelAd) {
            alPlaylist.remove(iNowPlayIndex);
            iNowPlayIndex--;
        }
    }
    
    private boolean checkIfReachSkipLimitation() {
        if (!isMusicPaused && !isPlaying()) {
            Logger.d("This skip command won't be counted.");
            return false; // if it isn't playing now, don't count
        }
        
        // +++++ handle skip limitation +++++ //
        // +++++ check time +++++ //
        long lSkipTime = mapStationSkipTime.get(sNowPlayStationToken);
        long lCurrentTimeInMillis = System.currentTimeMillis();
        Logger.d("lSkipTime: [" + lSkipTime + "], lCurrentTimeInMillis: [" + lCurrentTimeInMillis + "]");
        
        if (0 == lSkipTime) {
            mapStationSkipTime.put(sNowPlayStationToken, lCurrentTimeInMillis);
        } else {            
            if ((lCurrentTimeInMillis - lSkipTime) > (1 * 60 * 60 * 1000)) {
                mapStationSkipCounter.put(sNowPlayStationToken, 0);
                mapStationSkipTime.put(sNowPlayStationToken, 0l);
            }
        }
        // ----- check time ----- //
        
        int iSkipCounter = mapStationSkipCounter.get(sNowPlayStationToken) + 1;
        Logger.d("iSkipCounter: [" + iSkipCounter + "]");
        
        if (PandoraServiceConstants.SKIP_LIMIT < iSkipCounter) {
            Logger.d("Reach skip limitation: [" + PandoraServiceConstants.SKIP_LIMIT + "]");
            // TODO
            /*
            if (isSkipSong) {
                sendMessage2Thread(PandoraServiceConstants.APIID_PANDORA_SKIP_SONG);
            } else if (isThumbsDown) {
                sendMessage2Thread(PandoraServiceConstants.APIID_PANDORA_ADD_NEGATIVE_FEEDBACK);
            }
            */
            return true;
        } else {
            mapStationSkipCounter.put(sNowPlayStationToken, iSkipCounter);
            return false;
        }
        // ----- handle skip limitation ----- //
    }

    /*
    private final Handler getPositionMessageHandler = new Handler() {
        public void handleMessage(Message msg) {
        }
    };

    private Thread getPositionThread = new Thread() {
        public void run() {
            while (isPlaying()) {
                Message messageGetCurrentPosition = new Message();
                messageGetCurrentPosition.what = PandoraServiceConstants.APIID_PANDORA_CURRENT_POSITION;
                messageGetCurrentPosition.arg1 = getCurrentPosition();
                threadMessageHandler.sendMessage(messageGetCurrentPosition);
                
                try {
                    sleep(1000);
                } catch (Exception e) {
                    Logger.e(e);
                }

                getPositionMessageHandler.postDelayed(getPositionThread, 1000);
            }
            
            Logger.d("!isPlaying(), stop running getPositionThread");
        }
    };
    */

    private Handler mPlayMsgHandle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case PlayerState.PLAYER_PREPARED:
                Logger.d("PLAYER_PREPARED");
                try {
                    getTotalDuration();

                    interfaceMediaPlaybackService.playMusic();
                    Logger.d("playMusic() is called");

                    sendMessage2Thread(PlayerState.PLAYER_PREPARED);
                } catch (Exception e) {
                    Logger.e(e);
                }
                break;
            case PlayerState.PLAYER_PLAY:
                Logger.d("PLAYER_PLAY");
                isThumbsDown = false;
                isMusicPaused = false;
                isSkipSong = false;

                //new Thread(getPositionThread).start(); // TODO do not delete this line
                
                Thread getPositionThread = new Thread() {
                    public void run() {
                        while (isPlaying()) {
                            Message messageGetCurrentPosition = new Message();
                            messageGetCurrentPosition.what = PandoraServiceConstants.APIID_PANDORA_CURRENT_POSITION;
                            messageGetCurrentPosition.arg1 = getCurrentPosition();
                            threadMessageHandler.sendMessage(messageGetCurrentPosition);
                            
                            try {
                                sleep(1000);
                            } catch (Exception e) {
                                Logger.e(e);
                            }
                        }
                        
                        if (!isThumbsDown && !isMusicPaused && !isSkipSong) {
                            Message messageGetCurrentPosition = new Message();
                            messageGetCurrentPosition.what = PandoraServiceConstants.APIID_PANDORA_CURRENT_POSITION;
                            messageGetCurrentPosition.arg1 = iTotalDuration;
                            threadMessageHandler.sendMessage(messageGetCurrentPosition);
                        }
                        
                        Logger.d("!isPlaying(), stop running getPositionThread");
                    }
                };
                getPositionThread.start();

                break;
            case PlayerState.PLAYER_PAUSE:
                Logger.d("PLAYER_PAUSE");
                isMusicPaused = true;
                break;
            case PlayerState.PLAYER_IDLE:
                Logger.d("PLAYER_IDLE");
                
                if (isDeleteStation) {
                    isDeleteStation = false;
                    return;
                }

                if (isThumbsDown) {
                    //isThumbsDown = false;
                    int iSize = alPlaylist.size();
                    int iRemoveIndex = iNowPlayIndex + 1;
                    if (iNowPlayIndex < iSize - 1) {
                        for (int i = iRemoveIndex; i < iSize; i++) {
                            alPlaylist.remove(iRemoveIndex);
                        }
                    }
                    sendMessage2Thread(PandoraServiceConstants.APIID_PANDORA_GET_PLAYLIST);
                }

                if (isSkipSong) {
                    //isSkipSong = false;
                    if (iNowPlayIndex < alPlaylist.size() - 1) {
                        iNowPlayIndex++;
                        playMusic();
                    } else {
                        sendMessage2Thread(PandoraServiceConstants.APIID_PANDORA_GET_PLAYLIST);
                    }
                }
                break;
            case PlayerState.PLAYER_PLAY_COMPLETED:
                Logger.d("PLAYER_PLAY_COMPLETED");

                if (iNowPlayIndex < alPlaylist.size() - 1) {
                    iNowPlayIndex++;
                    playMusic();
                } else {
                    sendMessage2Thread(PandoraServiceConstants.APIID_PANDORA_GET_PLAYLIST);
                }
                break;
            case PlayerState.PLAYER_FAIL_TO_PLAY:
                Logger.d("PLAYER_FAIL_TO_PLAY");

                alPlaylist.remove(iNowPlayIndex);
                if (iNowPlayIndex == alPlaylist.size()) {
                    iNowPlayIndex--;
                    sendMessage2Thread(PandoraServiceConstants.APIID_PANDORA_GET_PLAYLIST);
                } else {
                    playMusic();
                }
                break;
            case PlayerState.PLAYER_SERVER_DIED:
                Logger.d("PLAYER_SERVER_DIED");
                break;
            case PlayerState.PLAYER_UNKNOWN:
                Logger.d("PLAYER_UNKNOWN");
                
                if (iNowPlayIndex < alPlaylist.size() - 1) {
                    iNowPlayIndex++;
                    playMusic();
                } else {
                    sendMessage2Thread(PandoraServiceConstants.APIID_PANDORA_GET_PLAYLIST);
                }
                break;
            case PlayerState.PLAYER_NETWORK_SLOW: {
                Logger.d("PLAYER_NETWORK_SLOW");
                
                int iCurrentPositionReturn = -1;

                try {
                    if (isPlaying()) {
                        iCurrentPositionReturn = interfaceMediaPlaybackService.getPosition() / 1000;
                    }
                } catch (Exception e) {
                    Logger.e(e);
                }
                
                if (-1 != iCurrentPositionReturn) {
                    if (iCurrentPosition != iCurrentPositionReturn) {
                        iCurrentPosition = iCurrentPositionReturn;
                    } else {
                        Logger.d("No sound!");
                        stopMusic();
                        skipSong();
                    }
                }
                
                Logger.d("iCurrentPositionReturn: [" + iCurrentPositionReturn + "]");
                Logger.d("iCurrentPosition:       [" + iCurrentPosition + "]");
                break;
            }
            default:
                break;
            }
        }
    };

    /**
     * 
     * @param iWhatInput iWhatInput
     * @param arg1Input arg1Input
     * @param objectInput objectInput
     */
    private void sendMessage2Thread(int iWhatInput, int arg1Input, Object objectInput) {
        try {
            Message message = new Message();
            message.what = iWhatInput;
            message.arg1 = arg1Input;
            message.obj = objectInput;
            threadMessageHandler.sendMessage(message);
        } catch (Exception e) {
            Logger.e(e);
        }
    }

    /**
     * 
     * @param iWhatInput iWhatInput
     */
    private void sendMessage2Thread(int iWhatInput) {
        sendMessage2Thread(iWhatInput, -1, null);
    }

    /**
     * 
     * @param iWhatInput iWhatInput
     * @param arg1Input arg1Input
     */
    private void sendMessage2Thread(int iWhatInput, int arg1Input) {
        sendMessage2Thread(iWhatInput, arg1Input, null);
    }

    /**
     * 
     * @param iWhatInput iWhatInput
     * @param objectInput objectInput
     */
    private void sendMessage2Thread(int iWhatInput, Object objectInput) {
        sendMessage2Thread(iWhatInput, -1, objectInput);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Logger.d();
            interfaceMediaPlaybackService = IMediaPlaybackService.Stub.asInterface((IBinder) service);
            try {
                interfaceMediaPlaybackService.registerCallback(mCallback);
            } catch (RemoteException e) {
                Logger.e(e);
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            Logger.d();
            try{
                interfaceMediaPlaybackService.unregisterCallback(mCallback);
            } catch (RemoteException e){
                e.printStackTrace();
            }
            interfaceMediaPlaybackService = null;
        }
    };

    private IMediaPlaybackServiceCallback mCallback = new IMediaPlaybackServiceCallback.Stub() {

        public void onComplete(final int complete_status) {
            if (PlayerState.PLAYER_IDLE == complete_status) {
                Logger.d("PLAYER_IDLE");
                mPlayMsgHandle.sendEmptyMessage(PlayerState.PLAYER_IDLE);
            } else if (PlayerState.PLAYER_PREPARED == complete_status) {
                Logger.d("PLAYER_PREPARED");
                mPlayMsgHandle.sendEmptyMessage(PlayerState.PLAYER_PREPARED);
            } else if (PlayerState.PLAYER_PLAY == complete_status) {
                Logger.d("PLAYER_PLAY");
                mPlayMsgHandle.sendEmptyMessage(PlayerState.PLAYER_PLAY);
            } else if (PlayerState.PLAYER_PAUSE == complete_status) {
                Logger.d("PLAYER_PAUSE");
                mPlayMsgHandle.sendEmptyMessage(PlayerState.PLAYER_PAUSE);
            } else if (PlayerState.PLAYER_PLAY_COMPLETED == complete_status) {
                Logger.d("PLAYER_PLAY_COMPLETED");
                mPlayMsgHandle.sendEmptyMessage(PlayerState.PLAYER_PLAY_COMPLETED);
            }
        }

        public void onErrorReport(final int error_code) {
            if (PlayerState.PLAYER_SERVER_DIED == error_code) {
                Logger.d("PLAYER_SERVER_DIED");
                mPlayMsgHandle.sendEmptyMessage(PlayerState.PLAYER_SERVER_DIED);
            } else if (PlayerState.PLAYER_FAIL_TO_PLAY == error_code) {
                Logger.d("PLAYER_FAIL_TO_PLAY");
                mPlayMsgHandle.sendEmptyMessage(PlayerState.PLAYER_FAIL_TO_PLAY);
            } else if (PlayerState.PLAYER_IS_PLAYING == error_code) {
                Logger.d("PLAYER_IS_PLAYING");
                mPlayMsgHandle.sendEmptyMessage(PlayerState.PLAYER_IS_PLAYING);
            } else if (PlayerState.PLAYER_UNKNOWN == error_code) {
                Logger.d("PLAYER_UNKNOWN");
                mPlayMsgHandle.sendEmptyMessage(PlayerState.PLAYER_UNKNOWN);
            } else if (PlayerState.PLAYER_NETWORK_SLOW == error_code) {
            	Logger.d("PLAYER_NETWORK_SLOW");
            	mPlayMsgHandle.sendEmptyMessage(PlayerState.PLAYER_NETWORK_SLOW);
            }
        }
    };

}
