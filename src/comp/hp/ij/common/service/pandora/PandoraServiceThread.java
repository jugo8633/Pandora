package comp.hp.ij.common.service.pandora;

import java.util.ArrayList;

import comp.hp.ij.common.service.mplayer.PlayerState;
import comp.hp.ij.common.service.mplayer.adapter.MediaPlaybackServiceAdapter;
import comp.hp.ij.common.service.pandora.api.Item;
import comp.hp.ij.common.service.pandora.api.PandoraAPIConstants;
import comp.hp.ij.common.service.pandora.api.PandoraAPIErrorCode;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import comp.hp.ij.common.service.pandora.data.PPandora;
import comp.hp.ij.common.service.pandora.data.ParcelDeviceActivationData;
import comp.hp.ij.common.service.pandora.data.ParcelExplanation;
import comp.hp.ij.common.service.pandora.data.ParcelSearchResult;
import comp.hp.ij.common.service.pandora.data.ParcelStation;
import comp.hp.ij.common.service.pandora.data.ParcelTrack;
import comp.hp.ij.common.service.pandora.util.Debugger;
import comp.hp.ij.common.service.pandora.util.Logger;
import comp.hp.ij.common.service.v2.base.BaseServiceThreadV2;
import comp.hp.ij.common.service.v2.base.ConstantV2;
import comp.hp.ij.common.service.v2.base.IServiceThreadV2;
import comp.hp.ij.common.service.v2.base.PResultV2;

/**
 * This code was developed by HON HAI PRECISION IND. CO., LTD., CMMSG/DWHD/CCP/SWI, Code for the Mars project.
 * 
 * @author Chance Wang
 */
public class PandoraServiceThread extends BaseServiceThreadV2 implements IServiceThreadV2 {
    private Handler serviceMessageHandler;
    
    private PandoraService pandoraService = null;
    private PPandora pPandora = null;
    private PandoraAPIClient pandoraAPIClient = null;
    
    private String sPandoraUserName = "";
    private String sPandoraPassword = "";

    private String sUserAuthToken = "";
    private String sPartnerAuthToken = "";
    
    private String sNowPlayStationToken = "";

    private MediaPlaybackServiceAdapter mediaPlaybackServiceAdapter = null;
    
    private boolean isGetPlaylistSuccess = true;
    
    /**
     * 
     * @param handlerInput msgHandlerInput
     */
    public PandoraServiceThread(Handler handlerInput) {       
        super(ConstantV2.SERVICE_TYPE_PANDORA); //important
        serviceMessageHandler = handlerInput;
        
        if (null == pPandora) {
            pPandora = new PPandora();
        }
        if (null == pandoraAPIClient) {
            pandoraAPIClient = new PandoraAPIClient();
        }
    }
    
    @Override
    public void run() {
        Logger.d("PandoraServiceThread run()");
        handleRequest(getRequest());
    }
    
    /**
     * 
     * @param pandoraServiceInput pandoraServiceInput
     */
    public void setPandoraService(PandoraService pandoraServiceInput) {
        pandoraService = pandoraServiceInput;
        pandoraAPIClient.setPreferences(pandoraService.getSharedPreferences("deviceAssociation", 0));
    }
    
    /**
     * 
     * @param sUserName user name
     * @param sPassword password
     */
    public void setLoginAuth(String sUserName, String sPassword) {
        sPandoraUserName = sUserName;
        sPandoraPassword = sPassword;
    }
    
    /**
     * 
     * @param interfaceMediaPlaybackServiceAdapterInput interfaceMediaPlaybackServiceAdapterInput
     */
    public void setMediaPlaybackServiceAdapter(MediaPlaybackServiceAdapter mediaPlaybackServiceAdapterInput) {
        mediaPlaybackServiceAdapter = mediaPlaybackServiceAdapterInput;
        mediaPlaybackServiceAdapter.setMessageHandler(mediaPlaybackServiceAdapterMessageHandler);
    }
    
    public void updateWidget() {
        if (!"".equals(sNowPlayStationToken)) {
            broadcast2Widget();
        } else {
            broadcastDefault2Widget();
        }
    }
    
    /**
     * 
     * @param pPandoraInput pPandoraInput
     * @param iParameterInput iParameterInput
     * @param bundleInput bundleInput
     * @param what what
     * @param result result
     */
    private void sendResult(PPandora pPandoraInput, int iParameterInput, Bundle bundleInput, int what, int result) {
        PResultV2 pResult = new PResultV2();
        
        pResult.mData = pPandoraInput;
        pResult.mParameter = iParameterInput;
        bundleInput.putParcelable(ConstantV2.RESULT, pResult);
                    
        Message msg = new Message();
        msg.what = what;        
        msg.arg2 = result;
        msg.setData(bundleInput);
        
        serviceMessageHandler.sendMessage(msg);
    }
    
    /**
     * 
     * @param pPandoraInput pPandoraInput
     * @param iParameterInput iParameterInput
     */
    private void simpleSendResult(PPandora pPandoraInput, int iParameterInput) {
        if (PandoraServiceConstants.APIID_PANDORA_CURRENT_POSITION == iParameterInput) {
            Bundle bundle = (Bundle) getRequest().clone(); // clone prevent command overwrite
            sendResult(pPandoraInput, iParameterInput, bundle, ConstantV2.REQUEST_COMPLETED, ConstantV2.REQUEST_SUCCESS);
        } else {
            sendResult(pPandoraInput, iParameterInput, getRequest(), ConstantV2.REQUEST_COMPLETED, ConstantV2.REQUEST_SUCCESS);
        }
    }
    
    /**
     * 
     * @param bundleInput bundleInput
     */
    private void handleRequest(Bundle bundleInput) {
        int iAPIID = -1;
        String sAPIID;

        sAPIID = bundleInput.getString(ConstantV2.APIID);
        iAPIID = Integer.parseInt(sAPIID);
        Logger.d("sAPIID: [" + sAPIID + "], iAPIID: [" + iAPIID + "]");
        
        switch(iAPIID) {
            case PandoraServiceConstants.APIID_PANDORA_IS_ASSOCIATED:
                Logger.d("call pandoraAPIClient.isAssociated()");
                if (pandoraAPIClient.isAssociated()) {
                    Logger.d("Device is associated");
                    pPandora.setIsAssociationResult(PandoraAPIConstants.DEVICE_ASSOCIATED);
                } else {
                    Logger.d("Device is not associated");
                    pPandora.setIsAssociationResult(PandoraAPIConstants.DEVICE_NOT_ASSOCIATED);
                }
                simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_IS_ASSOCIATED);
                break;
            case PandoraServiceConstants.APIID_PANDORA_DO_LOGOUT: {
                Logger.d("call pandoraAPIClient.doLogout()");

                int iReturnCode = pandoraAPIClient.doLogout();
                pPandora.setLogoutReturn(iReturnCode);
                
                sNowPlayStationToken = "";
                mediaPlaybackServiceAdapter.clearStationData();

                simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_DO_LOGOUT);
                
                broadcastDefault2Widget();
                break;
            }
            case PandoraServiceConstants.APIID_PANDORA_GENERATE_ACTIVIATION_CODE: {
                Logger.d("call pandoraAPIClient.generateDeviceActivationCode()");

                int iReturnCode = pandoraAPIClient.generateDeviceActivationCode();
                ParcelDeviceActivationData parcelDeviceActivationData = null;
                if (PandoraAPIConstants.GENERATE_ACTIVATION_CODE_SUCCESS == iReturnCode) {
                    parcelDeviceActivationData = pandoraAPIClient.getDeviceActivitationData();
                    if (null != parcelDeviceActivationData) {
                        Logger.d("Activation Code: [" + parcelDeviceActivationData.getActivationCode() + "]");
                        Logger.d("Actication URL:  [" + parcelDeviceActivationData.getActivationUrl() + "]");
                    } else {
                        Logger.d("parcelDeviceActivationData is null");
                    }
                }
                pPandora.setGenerateActivationCodeReturn(iReturnCode);
                pPandora.setDeviceActivationData(parcelDeviceActivationData);

                simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_GENERATE_ACTIVIATION_CODE);
                break;
            }
            case PandoraServiceConstants.APIID_PANDORA_DO_DEVICE_LOGIN: {
                Logger.d("call pandoraAPIClient.doDeviceLogin()");
                int iDoDeviceLoginReturn = -1;
                iDoDeviceLoginReturn = pandoraAPIClient.doDeviceLogin();
                Logger.d("iDoLoginReturn: [" + iDoDeviceLoginReturn + "]");            
                pPandora.setDoDeviceLoginReturn(iDoDeviceLoginReturn);            
                simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_DO_DEVICE_LOGIN);
                break;
            }
            case PandoraServiceConstants.APIID_PANDORA_DO_LOGIN: {
                Logger.d("call pandoraAPIClient.doLogin()");
                int iDoLoginReturn = -1;
                pandoraAPIClient.setPandoraAuth(sPandoraUserName, sPandoraPassword);
                iDoLoginReturn = pandoraAPIClient.doLogin();
                Logger.d("iDoLoginReturn: [" + iDoLoginReturn + "]");            
                pPandora.setDoLoginReturn(iDoLoginReturn);            
                simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_DO_LOGIN);
                break;
            }
            case PandoraServiceConstants.APIID_PANDORA_GET_NOWPLAY_STATION_DATA: {
                int iNowPlayStationIndex = mediaPlaybackServiceAdapter.getNowPlayStationIndex();
                Logger.d("iNowPlayStationIndex: [" + iNowPlayStationIndex + "]");
                String sNowPlayStationToken = mediaPlaybackServiceAdapter.getNowPlayStationToken();
                Logger.d("sNowPlayStationToken: [" + sNowPlayStationToken + "]");
                
                pPandora.setNowPlayStationIndex(iNowPlayStationIndex);
                pPandora.setNowPlayStationToken(sNowPlayStationToken);
                simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_GET_NOWPLAY_STATION_DATA);
                break;
            }
            case PandoraServiceConstants.APIID_PANDORA_GET_STATION_LIST: {
                Logger.d("call pandoraAPIClient.getStationList()");
                
                int iReturnCode = pandoraAPIClient.getStationList();
                ArrayList<ParcelStation> alParcelStationList = null;
                if (PandoraAPIConstants.GET_STATION_LIST_SUCCESS == iReturnCode) {
                    alParcelStationList = pandoraAPIClient.getStationListData();
                    if (null != alParcelStationList) {
                        Logger.d("alParcelStationList.size(): [" + alParcelStationList.size() + "]");
                    } else {
                        Logger.d("alParcelStationList is null");
                    }
                }
                mediaPlaybackServiceAdapter.setStationList(alParcelStationList);
                pPandora.setGetStationListReturn(iReturnCode);
                pPandora.setStationList(alParcelStationList);
                simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_GET_STATION_LIST);
                break;
            }
            case PandoraServiceConstants.APIID_PANDORA_DELETE_STATION: {
                Logger.d("call pandoraAPIClient.deleteStation()");

                String sStationToken = bundleInput.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_STATION_TOKEN);

                pPandora.setIsDeleteNowPlayStation(false);
                if (null != sStationToken) {
                    int deleteStationReturn = pandoraAPIClient.deleteStation(sStationToken);
                    pPandora.setDeleteStationReturn(deleteStationReturn);
                    Logger.d("Delete Station Reurn: [" + deleteStationReturn + "]");
                    
                    if (PandoraAPIConstants.DELETE_STATION_SUCCESS == deleteStationReturn) {
                        mediaPlaybackServiceAdapter.deleteStationData(sStationToken);
                        boolean isDeleteNowPlayStation = mediaPlaybackServiceAdapter.checkIsDeleteNowPlayStation(sStationToken);
                        pPandora.setIsDeleteNowPlayStation(isDeleteNowPlayStation);
                        if (isDeleteNowPlayStation) {
                            sNowPlayStationToken = "";
                        }
                    }
                } else {
                    Logger.d("Station Token is null");
                }

                simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_DELETE_STATION);
                break;
            }
            case PandoraServiceConstants.APIID_PANDORA_GET_PLAYLIST: {
                Logger.d("call pandoraAPIClient.getPlayList()");
                String sStationTokenInput = bundleInput.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_STATION_TOKEN);
                
                if (!sNowPlayStationToken.equals(sStationTokenInput) || !isGetPlaylistSuccess) {
                    mediaPlaybackServiceAdapter.stopMusic();
                    mediaPlaybackServiceAdapter.setNowPlayIndex(-1);
                    mediaPlaybackServiceAdapter.setNowPlayStationToken(sStationTokenInput);
                    sNowPlayStationToken = sStationTokenInput;
                    
                    int iReturnCode = pandoraAPIClient.getPlaylist(sStationTokenInput);
                    ArrayList<Item> alPlaylist = null;
                    if (PandoraAPIConstants.GET_PLAYLIST_SUCCESS == iReturnCode) {
                        isGetPlaylistSuccess = true;
                        
                        alPlaylist = pandoraAPIClient.getPlaylistData();
                        if (null != alPlaylist) {
                            Logger.d("alPlaylist.size(): [" + alPlaylist.size() + "]");
                            
                            mediaPlaybackServiceAdapter.setPlaylist(alPlaylist);
                            mediaPlaybackServiceAdapter.playMusic();
                        }
                    } else {
                        isGetPlaylistSuccess = false;
                        
                        Message errorMessage = new Message();
                        errorMessage.what = ConstantV2.PANDORA_SERVICE_INTERNAL_CALL;        
                        errorMessage.arg2 = iReturnCode;
                        Logger.d("msg.arg2: [" + errorMessage.arg2 + "]");
                        
                        serviceMessageHandler.sendMessage(errorMessage);
                        
                        broadcastDefault2Widget();
                        return;
                    }
    
                    pPandora.setGetPlaylistReturn(iReturnCode);
                    pPandora.setPlaylist(alPlaylist);
                    
                    simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_GET_PLAYLIST);
                } else {
                    Logger.d("The same station");
                    mediaPlaybackServiceAdapter.getTotalDuration();
                }
                break;
            }
            case PandoraServiceConstants.APIID_PANDORA_UPDATE_PLAYLIST: {
                ArrayList<Item> alUpdatedPlaylist = mediaPlaybackServiceAdapter.getPlaylist();
                pPandora.setPlaylist(alUpdatedPlaylist);
                simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_UPDATE_PLAYLIST);
                break;
            }
            case PandoraServiceConstants.APIID_PANDORA_DOWNLOAD_IMAGE: {
                Logger.d("call pandoraAPIClient.downloadImage()");
                
                String sImageUrl = bundleInput.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_ALBUM_ART_URL);
                
                if (null != sImageUrl) {
                    Bitmap downloadedImage = pandoraAPIClient.downloadImage(sImageUrl);
                    pPandora.setDownloadedImage(downloadedImage);
                    if (null != downloadedImage) {
                        Logger.d("Image row bytes: [" + downloadedImage.getRowBytes() + "]");
                    } else { 
                        Logger.d("Download image is null");
                    }
                } else {
                    Logger.d("Image Url is null");
                }
                
                simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_DOWNLOAD_IMAGE);
                break;
            }
            case PandoraServiceConstants.APIID_PANDORA_PAUSE_MUSIC:
                mediaPlaybackServiceAdapter.pauseMusic();
                break;
            case PandoraServiceConstants.APIID_PANDORA_RESUME_MUSIC:
                mediaPlaybackServiceAdapter.resumeMusic();
                break;
            case PandoraServiceConstants.APIID_PANDORA_SKIP_SONG:
                pPandora.setIsReachSkipLimit(false);
                mediaPlaybackServiceAdapter.skipSong();
                if (mediaPlaybackServiceAdapter.isReachSkipLimitation()) {
                    pPandora.setIsReachSkipLimit(true);
                }
                simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_SKIP_SONG);
                break;
            case PandoraServiceConstants.APIID_PANDORA_STOP_MUSIC:
                Logger.d("call mediaPlaybackServiceAdapter.stopMusic()");
                pPandora.setStopMusicStatus(mediaPlaybackServiceAdapter.stopMusic());
                simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_STOP_MUSIC);
                break;
            case PandoraServiceConstants.APIID_PANDORA_ADD_ARTIST_BOOKMARK: {
                Logger.d("call pandoraAPIClient.addArtistBookmark()");

                String sTrackToken = bundleInput.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN);
                if (null != sTrackToken) {
                    int artistBookmarkReturnId = pandoraAPIClient.addArtistBookmark(sTrackToken);
                    pPandora.setAddArtistBookmarkReturn(artistBookmarkReturnId);

                } else {
                    Logger.d("Track Token is null");
                }
                simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_ADD_ARTIST_BOOKMARK);
                break;
            }
            case PandoraServiceConstants.APIID_PANDORA_ADD_SONG_BOOKMARK: {
                Logger.d("call pandoraAPIClient.addSongBookmark()");

                String sTrackToken = bundleInput.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN);
                if (null != sTrackToken) {
                    int songBookmarkReturnId = pandoraAPIClient.addSongBookmark(sTrackToken);
                    pPandora.setAddSongBookmarkReturn(songBookmarkReturnId);
                } else {
                    Logger.d("Track Token is null");
                }
                simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_ADD_SONG_BOOKMARK);
                break;
            }
            case PandoraServiceConstants.APIID_PANDORA_ADD_POSITIVE_FEEDBACK: {
                Logger.d("call pandoraAPIClient.addPositiveFeedback()");

                String sTrackToken = bundleInput.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN);

                if (null != sTrackToken) {
                    int addFeedbackReturn = pandoraAPIClient.addPositiveFeedback(sTrackToken);
                    pPandora.setAddFeedbackReturn(addFeedbackReturn);
                    if (PandoraAPIConstants.ADD_FEEDBACK_SUCCESS == addFeedbackReturn) {
                        mediaPlaybackServiceAdapter.thumbsUp();
                    }
                } else {
                    Logger.d("Track Token is null");
                }
                simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_ADD_POSITIVE_FEEDBACK);
                break;
            }
            case PandoraServiceConstants.APIID_PANDORA_ADD_NEGATIVE_FEEDBACK: {
                Logger.d("call pandoraAPIClient.addNegativeFeedback()");

                String sTrackToken = bundleInput.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN);

                if (null != sTrackToken) {
                    int addFeedbackReturn = pandoraAPIClient.addNegativeFeedback(sTrackToken);
                    pPandora.setAddFeedbackReturn(addFeedbackReturn);
                    if (PandoraAPIConstants.ADD_FEEDBACK_SUCCESS == addFeedbackReturn) {
                        pPandora.setIsReachSkipLimit(false);
                        mediaPlaybackServiceAdapter.thumbsDown();
                        if (mediaPlaybackServiceAdapter.isReachSkipLimitation()) {
                            pPandora.setIsReachSkipLimit(true);
                        }
                    }
                } else {
                    Logger.d("Track Token is null");
                }
                simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_ADD_NEGATIVE_FEEDBACK);
                break;
            }
            case PandoraServiceConstants.APIID_PANDORA_SLEEP_SONG: {
                Logger.d("call pandoraAPIClient.sleepSong()");

                String sTrackToken = bundleInput.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN);

                if (null != sTrackToken) {
                    int sleepSongReturn = pandoraAPIClient.sleepSong(sTrackToken);
                    pPandora.setSleepSongReturn(sleepSongReturn);
                    if (PandoraAPIConstants.SLEEP_SONG_SUCCESS == sleepSongReturn) {
                        pPandora.setIsReachSkipLimit(false);
                        mediaPlaybackServiceAdapter.skipSong();
                        if (mediaPlaybackServiceAdapter.isReachSkipLimitation()) {
                            pPandora.setIsReachSkipLimit(true);
                        }
                    }
                } else {
                    Logger.d("Track Token is null");
                }
                simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_SLEEP_SONG);
                break;
            }
            case PandoraServiceConstants.APIID_PANDORA_EXPLAIN_TRACK: {
                Logger.d("call pandoraAPIClient.explainTrack()");

                String sTrackToken = bundleInput.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_TRACK_TOKEN);
                if (null != sTrackToken) {
                    int iReturnCode = pandoraAPIClient.explainTrack(sTrackToken);
                    ArrayList<ParcelExplanation> alExplainTrack = null;
                    if (PandoraAPIConstants.EXPLAIN_TRACK_SUCCESS == iReturnCode) {
                        alExplainTrack = pandoraAPIClient.getExplainTrackData();
                    }
                    pPandora.setExplainTrackReturn(iReturnCode);
                    pPandora.setExplanations(alExplainTrack);
                } else {
                    Logger.d("Track Token is null");
                }
                simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_EXPLAIN_TRACK);
                break;
            }
            case PandoraServiceConstants.APIID_PANDORA_MUSIC_SEARCH: {
                Logger.d("call pandoraAPIClient.musicSearch()");

                String sSearchText = bundleInput.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_SEARCH_TEXT);

                int iReturnCode = PandoraAPIConstants.MUSIC_SEARCH_WITH_EMPTY_TEXT;
                ParcelSearchResult parcelSearchResult = null;
                if (null != sSearchText && !"".equals(sSearchText)) {
                    iReturnCode = pandoraAPIClient.musicSearch(sSearchText);
                    if (PandoraAPIConstants.MUSIC_SEARCH_SUCCESS == iReturnCode) {
                        parcelSearchResult = pandoraAPIClient.getMusicSearchData();
                    }
                } else {
                    Logger.d("Search text is null");
                }
                pPandora.setMusicSearchReturn(iReturnCode);
                pPandora.setMusicSearchResult(parcelSearchResult);
                simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_MUSIC_SEARCH);
                break;
            }
            case PandoraServiceConstants.APIID_PANDORA_MUSIC_SEARCH_AUTO_COMPLETE: {
                Logger.d("call pandoraAPIClient.musicSearchAutoComplete()");

                String sSearchText = bundleInput.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_SEARCH_TEXT);

                int iReturnCode = PandoraAPIConstants.MUSIC_SEARCH_AUTO_COMPLETE_WITH_EMPTY_TEXT;
                ParcelSearchResult parcelSearchResult = null;
                if (null != sSearchText && !"".equals(sSearchText)) {
                    iReturnCode = pandoraAPIClient.musicSearchAutoComplete(sSearchText);
                    if (PandoraAPIConstants.MUSIC_SEARCH_AUTO_COMPLETE_SUCCESS == iReturnCode) {
                        parcelSearchResult = pandoraAPIClient.getMusicSearchAutoCompleteData();
                    }
                } else {
                    Logger.d("Search text is null");
                }
                pPandora.setMusicSearchAutoCompleteReturn(iReturnCode);
                pPandora.setMusicSearchAutoCompleteResult(parcelSearchResult);
                simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_MUSIC_SEARCH_AUTO_COMPLETE);
                break;
            }
            case PandoraServiceConstants.APIID_PANDORA_CREATE_STATION: {
                Logger.d("call pandoraAPIClient.createStation()");

                String sMusicToken = bundleInput.getString(PandoraServiceConstants.ADAPTER_ARGUMENT_MUSIC_TOKEN);

                int iReturnCode = PandoraAPIConstants.CREATE_STATION_WITH_EMPTY_MUSIC_TOKEN;
                ParcelStation parcelStation = null;
                if (null != sMusicToken) {
                    iReturnCode = pandoraAPIClient.createStation(sMusicToken);
                    if (PandoraAPIConstants.CREATE_STATION_SUCCESS == iReturnCode) {
                        parcelStation = pandoraAPIClient.getCreateStationData();
                    }

                    if (null != parcelStation) {
                        Logger.d("Created Station Name:            [" + parcelStation.getStationName() + "]");
                        Logger.d("Created Station Id:              [" + parcelStation.getStationId() + "]");
                        Logger.d("Created Station IsAllowAddMusic: [" + parcelStation.isAllowAddMusic() + "]");
                        Logger.d("Created Station IsAllowDelete:   [" + parcelStation.isAllowDelete() + "]");
                        Logger.d("Created Station IsAllowRename:   [" + parcelStation.isAllowRename() + "]");
                        Logger.d("Created Station IsQuickMix:      [" + parcelStation.isQuickMix() + "]");
                        Logger.d("Created Station IsShared:        [" + parcelStation.isShared() + "]");
                        Logger.d("Created Station Token:           [" + parcelStation.getStationToken() + "]");
                        
                        ArrayList<ParcelStation> alCreateStation = new ArrayList<ParcelStation>();
                        alCreateStation.add(parcelStation);
                        mediaPlaybackServiceAdapter.setStationList(alCreateStation);
                        
                    } else {
                        Logger.d("parcelStation is null");
                    }
                } else {
                    Logger.d("Music Token is null");
                }

                pPandora.setCreateStationReturn(iReturnCode);
                pPandora.setCreatedParcelStation(parcelStation);
                simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_CREATE_STATION);
                break;
            }
        }
    }
    
    private final Handler mediaPlaybackServiceAdapterMessageHandler = new Handler() {
        public void handleMessage(Message message) {
            if (PandoraServiceConstants.APIID_PANDORA_CURRENT_POSITION != message.what) {
                Logger.d("enter handleMessage()");
            }
            switch(message.what) {
                case PandoraServiceConstants.APIID_PANDORA_TOTAL_DURATION: {
                    Logger.d("APIID_PANDORA_TOTAL_DURATION");
                    pPandora.setTotalDuration(message.arg1); // message.arg1 = total duration
                    pPandora.setNowPlayIndex(mediaPlaybackServiceAdapter.getNowPlayIndex());
                    simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_TOTAL_DURATION);
                    
                    broadcast2Widget();
                    break;
                }
                case PlayerState.PLAYER_PREPARED:
                    Logger.d("PLAYER_PREPARED");
                    break;
                case PandoraServiceConstants.APIID_PANDORA_CURRENT_POSITION: {
                    pPandora.setCurrentPosition(message.arg1); // message.arg1 = current position
                    simpleSendResult(pPandora, PandoraServiceConstants.APIID_PANDORA_CURRENT_POSITION);
                    break;
                }
                case PandoraServiceConstants.APIID_PANDORA_GET_PLAYLIST: {
                    int iReturnCode = pandoraAPIClient.getPlaylist(sNowPlayStationToken);
                    ArrayList<Item> alPlaylist = null;
                    if (PandoraAPIConstants.GET_PLAYLIST_SUCCESS == iReturnCode) {
                        isGetPlaylistSuccess = true;
                        
                        alPlaylist = pandoraAPIClient.getPlaylistData();
                        if (null != alPlaylist) {
                            Logger.d("alPlaylist.size(): [" + alPlaylist.size() + "]");
                            
                            mediaPlaybackServiceAdapter.addPlaylist(alPlaylist);
                            mediaPlaybackServiceAdapter.playMusic();
                        }
                    } else {
                        isGetPlaylistSuccess = false;
                        
                        Message errorMessage = new Message();
                        errorMessage.what = ConstantV2.PANDORA_SERVICE_INTERNAL_CALL;        
                        errorMessage.arg2 = iReturnCode;
                        Logger.d("msg.arg2: [" + errorMessage.arg2 + "]");
                        
                        serviceMessageHandler.sendMessage(errorMessage);
                        
                        broadcastDefault2Widget();
                        return;
                    }
                    
                    break;
                }
                case PandoraServiceConstants.APIID_PANDORA_GET_AD_METADATA: {
                    String sAdToken = (String) message.obj;
                    Logger.d("sAdToken: [" + sAdToken + "]");
                    
                    int iReturnCode = pandoraAPIClient.getAdMetadata(sAdToken);
                    while (PandoraAPIErrorCode.INVALID_AUTH_TOKEN == iReturnCode) {
                        iReturnCode = pandoraAPIClient.doLogin();
                        if (PandoraAPIConstants.USER_LOGIN_SUCCESS == iReturnCode
                                || PandoraAPIConstants.DEVICE_LOGIN_SUCCESS == iReturnCode) {
                            iReturnCode = pandoraAPIClient.getAdMetadata(sAdToken);
                        }
                    }
                    
                    if (PandoraAPIConstants.GET_AD_METADATA_SUCCESS == iReturnCode) {
                        Item itemAd = pandoraAPIClient.getAdMetadataData();
                        mediaPlaybackServiceAdapter.setAdMetadata(itemAd);
                    } else {
                        Logger.e("Something wrong with error code: [" + iReturnCode + "]");
                        Debugger.debugReturnCode(iReturnCode);
                        
                        isGetPlaylistSuccess = false;
                        
                        mediaPlaybackServiceAdapter.removeCurrentAd();
                        
                        Message errorMessage = new Message();
                        errorMessage.what = ConstantV2.PANDORA_SERVICE_INTERNAL_CALL;        
                        errorMessage.arg2 = iReturnCode;
                        Logger.d("msg.arg2: [" + errorMessage.arg2 + "]");
                        
                        serviceMessageHandler.sendMessage(errorMessage);
                        
                        broadcastDefault2Widget();
                    }
                    
                    break;
                }
            }
        }
    };
    
    /**
     * broadcast to Pandora widget.
     */
    private void broadcast2Widget() {
        String sStationName = ""; 
        String sSongName    = "";
        String sArtistName  = "";
        String sAlbumName   = "";
        String sAlbumArtUrl = "";
        ArrayList<Item> alItem = mediaPlaybackServiceAdapter.getPlaylist();
        int iNowPlayIndex = mediaPlaybackServiceAdapter.getNowPlayIndex();
        if (-1 != iNowPlayIndex) {
            Item item = alItem.get(mediaPlaybackServiceAdapter.getNowPlayIndex());
            if (null != item && item instanceof ParcelTrack) {
                sStationName = mediaPlaybackServiceAdapter.getNowPlayStationName();
                
                ParcelTrack parcelTrack = (ParcelTrack) item;
                sSongName    = parcelTrack.getSongName();
                sArtistName  = parcelTrack.getArtistName();
                sAlbumName   = parcelTrack.getAlbumName();
                sAlbumArtUrl = parcelTrack.getAlbumArtUrl();
                
                PandoraWidgetBroadcast pb = new PandoraWidgetBroadcast();
                pb.broadcastMsg((Context) pandoraService, PandoraWidgetBroadcast.PLAYNOW_UPDATE, sStationName, sSongName, sArtistName, sAlbumName, sAlbumArtUrl);
            }
        }
    }
    
    private void broadcastDefault2Widget() {
        PandoraWidgetBroadcast pb = new PandoraWidgetBroadcast();
        pb.broadcastMsg((Context) pandoraService, PandoraWidgetBroadcast.WIDGET_DEFAULT, "", "", "", "", "");
    }

    /**
     * 
     * @return user auth token
     */
    public String getUserAuthToken() {
        return sUserAuthToken;
    }

    /**
     * 
     * @param userAuthTokenInput userAuthTokenInput
     */
    public void setUserAuthToken(String userAuthTokenInput) {
        sUserAuthToken = userAuthTokenInput;
        }

    /**
     * 
     * @return partner auth token
     */
    public String getPartnerAuthToken() {
        return sPartnerAuthToken;
    }

    /**
     * 
     * @param partnerAuthTokenInput partnerAuthTokenInput
     */
    public void setPartnerAuthToken(String partnerAuthTokenInput) {
        sPartnerAuthToken = partnerAuthTokenInput;
    }
}
