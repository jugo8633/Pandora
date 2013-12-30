package comp.hp.ij.common.service.pandora;

import comp.hp.ij.common.service.pandora.api.PandoraAPIConstants;
import comp.hp.ij.common.service.v2.base.ConstantV2;

/**
 * This code was developed by HON HAI PRECISION IND. CO., LTD., CMMSG/DWHD/CCP/SWI, Code for the Mars project.
 * 
 * @author Chance Wang
 */
public class PandoraServiceConstants extends PandoraAPIConstants {
        
    public static final String PANDORA_SERVICE_URI = "comp.hp.ij.common.service.pandora.PandoraService.REMOTE_SERVICE";
    
    public static final int PANDORA_SERVICE_REGISTER_CLIENT          = 0x00000001 + ConstantV2.PANDORA_API_BASE;
    public static final int PANDORA_SERVICE_UNREGISTER_CLIENT        = 0x00000002 + ConstantV2.PANDORA_API_BASE;
    public static final int PANDORA_SERVICE_READY                    = 0x00000003 + ConstantV2.PANDORA_API_BASE;
    public static final int PANDORA_SERVICE_RESET_IDLE_TIMER         = 0x00000004 + ConstantV2.PANDORA_API_BASE;
    public static final int PANDORA_SERVICE_FIRE_IDLE_ALARM          = 0x00000005 + ConstantV2.PANDORA_API_BASE;
    public static final int PANDORA_SERVICE_ERROR_EVENT              = 0x00000006 + ConstantV2.PANDORA_API_BASE;
    
    // Pandora service api list
    public static final int APIID_PANDORA_IS_ASSOCIATED              = 0x00000007 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_DO_LOGOUT                  = 0x00000008 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_GENERATE_ACTIVIATION_CODE  = 0x00000009 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_SET_LOGIN_AUTH             = 0x00000010 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_DO_LOGIN                   = 0x00000011 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_DO_DEVICE_LOGIN            = 0x00000012 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_GET_NOWPLAY_STATION_DATA   = 0x00000013 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_GET_STATION_LIST           = 0x00000014 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_DELETE_STATION             = 0x00000015 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_GET_PLAYLIST               = 0x00000016 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_UPDATE_PLAYLIST            = 0x00000017 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_DOWNLOAD_IMAGE             = 0x00000018 + ConstantV2.PANDORA_API_BASE;
    
    public static final int APIID_PANDORA_GET_AD_METADATA            = 0x00000019 + ConstantV2.PANDORA_API_BASE;

    public static final int APIID_PANDORA_ADD_ARTIST_BOOKMARK        = 0x00000020 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_ADD_SONG_BOOKMARK          = 0x00000021 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_ADD_POSITIVE_FEEDBACK      = 0x00000022 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_ADD_NEGATIVE_FEEDBACK      = 0x00000023 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_SLEEP_SONG                 = 0x00000024 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_EXPLAIN_TRACK              = 0x00000025 + ConstantV2.PANDORA_API_BASE;

    public static final int APIID_PANDORA_MUSIC_SEARCH               = 0x00000026 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_MUSIC_SEARCH_AUTO_COMPLETE = 0x00000027 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_CREATE_STATION             = 0x00000028 + ConstantV2.PANDORA_API_BASE;
    
    // Media player api list
    public static final int APIID_PANDORA_TOTAL_DURATION             = 0x00000029 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_CURRENT_POSITION           = 0x00000030 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_PAUSE_MUSIC                = 0x00000031 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_RESUME_MUSIC               = 0x00000032 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_SKIP_SONG                  = 0x00000033 + ConstantV2.PANDORA_API_BASE;
    public static final int APIID_PANDORA_STOP_MUSIC                 = 0x00000034 + ConstantV2.PANDORA_API_BASE;
    
    // Pandora Widget
    public static final int PANDORA_SERVICE_UPDATE_WIDGET            = 0x00000037 + ConstantV2.PANDORA_API_BASE;
        
    // ==================== BroadcastReceiver ==================== //
    public static final String UI_INPUT_UUID = "PandoraActivity.UIInputUUID";
    
    // Activity IntentFilter api action
    public static final String SERVICE_READY_RETURN       = "PandoraService.serviceReadyReturn";
    public static final String SERVICE_IDLE_ALARM_RETURN  = "PandoraService.serviceIdleAlarmReturn";
    public static final String SERVICE_ERROR_EVENT_RETURN = "PandoraService.serviceErrorEventReturn";
    
    public static final String IS_ASSOCIATED_RETURN             = "PandoraService.isAssociatedReturn";
    public static final String DO_LOGOUT_RETURN                 = "PandoraService.doLogoutReturn";
    public static final String GENERATE_ACTIVIATION_CODE_RETURN = "PandoraService.generateActiviationCodeReturn";
    public static final String DO_LOGIN_RETURN                  = "PandoraService.doLoginReturn";
    public static final String DO_DEVICE_LOGIN_RETURN           = "PandoraService.doDeviceLoginReturn";
    public static final String GET_NOWPLAY_STATION_DATA_RETURN  = "PandoraService.getNowPlayStationDataReturn";
    public static final String GET_STATION_LIST_RETURN          = "PandoraService.getStationListReturn";
    public static final String DELETE_STATION_RETURN            = "PandoraService.deleteStationReturn";
    public static final String GET_PLAYLIST_RETURN              = "PandoraService.getPlaylistReturn";
    public static final String UPDATE_PLAYLIST_RETURN           = "PandoraService.updatePlaylistReturn";
    public static final String DOWNLOAD_IMAGE_RETURN            = "PanodraService.downloadImageReturn";
    
    public static final String GET_AD_METADATA_RETURN = "PandoraService.getAdMetadataReturn";
    
    public static final String ADD_ARTIST_BOOKMARK_RETURN = "PandoraService.addArtistBookmarkReturn";
    public static final String ADD_SONG_BOOKMARK_RETURN = "PandoraService.addSongBookmarkReturn";
    public static final String ADD_POSITIVE_FEEDBACK_RETURN = "PandoraService.addPositiveFeedbackReturn";
    public static final String ADD_NEGATIVE_FEEDBACK_RETURN = "PandoraService.addNegativeFeedbackReturn";
    public static final String SLEEP_SONG_RETURN = "PandoraService.sleepSongReturn";
    public static final String EXPLAIN_TRACK_RETURN = "PandoraService.explainTrackReturn";

    public static final String MUSIC_SEARCH_RETURN = "PandoraService.musicSearchReturn";
    public static final String MUSIC_SEARCH_AUTO_COMPLETE_RETURN = "PandoraService.musicSearchAutoCompleteReturn";
    public static final String CREATE_STATION_RETURN = "PandoraService.createStationReturn";
    
    public static final String TOTAL_DURATION_RETURN = "PandoraService.totalDurationReturn";
    public static final String CURRENT_POSITION_RETURN = "PandoraService.currentPositionReturn";
    public static final String PAUSE_MUSIC_RETURN = "PandoraService.pauseMusicReturn";
    public static final String RESUME_MUSIC_RETURN = "PandoraService.resumeMusicReturn";
    public static final String SKIP_SONG_RETURN = "PandoraService.skipSongReturn";
    public static final String STOP_MUSIC_RETURN = "PandoraService.stopMusicReturn";

    // Service IntentFilter api action
    public static final String REGISTER_CLIENT = "PandoraService.registerClient";
    public static final String UNREGISTER_CLIENT = "PandoraService.unregisterClient";
    
    public static final String IS_ASSOCIATED             = "PandoraService.isAssociated";
    public static final String DO_LOGOUT                 = "PandoraService.doLogout";
    public static final String GENERATE_ACTIVIATION_CODE = "PandoraService.generateActiviationCode";
    public static final String SET_LOGIN_AUTH            = "PandoraService.setAuth";
    public static final String DO_LOGIN                  = "PandoraService.doLogin";
    public static final String DO_DEVICE_LOGIN           = "PandoraService.doDeviceLogin";
    public static final String GET_NOWPLAY_STATION_DATA  = "PandoraService.getNowPlayStationData";
    public static final String GET_STATION_LIST          = "PandoraService.getStationList";
    public static final String DELETE_STATION            = "PandoraService.deleteStation";
    public static final String GET_PLAYLIST              = "PandoraService.getPlaylist"; 
    public static final String UPDATE_PLAYLIST           = "PandoraService.updatePlaylist";
    public static final String DOWNLOAD_IMAGE            = "PanodraService.downloadImage";
    
    public static final String GET_AD_METADATA = "PandoraService.getAdMetadata";
    
    public static final String ADD_ARTIST_BOOKMARK = "PandoraService.addArtistBookmark";
    public static final String ADD_SONG_BOOKMARK = "PandoraService.addSongBookmark";
    public static final String ADD_POSITIVE_FEEDBACK = "PandoraService.addPositiveFeedback";
    public static final String ADD_NEGATIVE_FEEDBACK = "PandoraService.addNegativeFeedback";
    public static final String SLEEP_SONG = "PandoraService.sleepSong";
    public static final String EXPLAIN_TRACK = "PandoraService.explainTrack";

    public static final String MUSIC_SEARCH = "PandoraService.musicSearch";
    public static final String MUSIC_SEARCH_AUTO_COMPLETE = "PandoraService.musicSearchAutoComplete";
    public static final String CREATE_STATION = "PandoraService.createStation";
    
    public static final String TOTAL_DURATION = "PandoraService.totalDuration";
    public static final String CURRENT_POSITION = "PandoraService.currentPosition";
    public static final String PAUSE_MUSIC = "PandoraService.pauseMusic";
    public static final String RESUME_MUSIC = "PandoraService.resumeMusic";
    public static final String SKIP_SONG = "PandoraService.skipSong";
    public static final String STOP_MUSIC = "PandoraService.stopMusic";
    
    // PandoraService
    public static final String RESET_IDLE_ALARM_TIMER = "PandoraService.resetIdleAlarmTimer";
    public static final String NOTIFY_ADAPTER_SERVICE_IS_READY = "PandoraService.notifyAdapterServiceIsReady";
    
    // Pandora Widget
    public static final String UPDATE_WIDGET = "PandoraService.updateWidget";
    
    // PandoraServiceAdapter
    public static final String ACTIVITY_HASH_CODE             = "PandoraServiceAdapter.activityHashCode";
    
    public static final String ADAPTER_ARGUMENT_USERNAME      = "PandoraServiceAdapter.argumentUsername";
    public static final String ADAPTER_ARGUMENT_PASSWORD      = "PandoraServiceAdapter.argumentPassword";
    public static final String ADAPTER_ARGUMENT_STATION_TOKEN = "PandoraServiceAdapter.argumentStationToken";
    public static final String ADAPTER_ARGUMENT_ALBUM_ART_URL = "PandoraServiceAdapter.argumentAlbumArtUrl";
    public static final String ADAPTER_ARGUMENT_TRACK_TOKEN   = "PandoraServiceAdapter.argumentTrackToken";
    public static final String ADAPTER_ARGUMENT_SEARCH_TEXT   = "PandoraServiceAdapter.argumentSearchText";
    public static final String ADAPTER_ARGUMENT_MUSIC_TOKEN   = "PandoraServiceAdapter.argumentMusicToken";
    // ==================== BroadcastReceiver ==================== //
        
    // previous max track size
    public static final int MAX_PREVIOUS_TRACKS = 6;
}
