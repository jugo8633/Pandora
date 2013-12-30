package comp.hp.ij.common.service.pandora.api;

public class PandoraAPIConstants {
    // switch Pandora testing or production server
    public static final boolean IS_TESTING = true;
    
    // CCPHttpClient timeout 30 seconds
    public static final int NETWORK_TIMEOUT = 30;
    
    // skip limitation
    public static final int SKIP_LIMIT = 6;
    
    private static int iReturn = 10000;
    
    public static final int UNKNOWN_EXCEPTION                = iReturn++;
    
    // network problem return
    public static final int NETWORK_UNREACHABLE              = iReturn++;
    public static final int FAIL_TO_CONNECT_TO_INTERNET      = iReturn++;
    public static final int FAIL_TO_CONNECT_TO_PANDORA       = iReturn++;
    
    // check license return
    public static final int CHECK_LICENSE_SUCCESS            = iReturn++;
    public static final int CHECK_LICENSE_FAILED             = iReturn++;
    
    // partner login return
    public static final int PARTNER_LOGIN_SUCCESS            = iReturn++;
    public static final int PARTNER_LOGIN_FAILED             = iReturn++;
    
    // doLogin return
    public static final int USER_LOGIN_SUCCESS               = iReturn++;
    public static final int USER_LOGIN_FAILED                = iReturn++;
    public static final int INVALID_USERNAME_PASSWORD        = iReturn++;
    public static final int USER_IS_SUSPENDED                = iReturn++;
    
    public static final int DEVICE_LOGIN_SUCCESS             = iReturn++;
    public static final int DEVICE_LOGIN_FAILED              = iReturn++;
    
    public static final int ASSOCIATE_SUCCESS                = iReturn++;
    public static final int ASSOCIATE_FAILED                 = iReturn++;
    
    // isAssociated() return
    public static final int DEVICE_ASSOCIATED     = iReturn++;
    public static final int DEVICE_NOT_ASSOCIATED = iReturn++;
    
    // doLogout return
    public static final int LOGOUT_SUCCESS    = iReturn++;
    public static final int LOGOUT_FAILED     = iReturn++;
    
    public static final int DISASSOCIATE_SUCCESS             = iReturn++;
    public static final int DISASSOCIATE_FAILED              = iReturn++;

    // generate activation code return
    public static final int GENERATE_ACTIVATION_CODE_SUCCESS = iReturn++;
    public static final int GENERATE_ACTIVATION_CODE_FAILED  = iReturn++;
    
    // get station list return
    public static final int GET_STATION_LIST_SUCCESS = iReturn++;
    public static final int GET_STATION_LIST_FAILED = iReturn++;
    
    // delete station return
    public static final int DELETE_STATION_SUCCESS = iReturn++;
    public static final int DELETE_STATION_FAILED  = iReturn++;
    
    // get play list return
    public static final int GET_PLAYLIST_SUCCESS = iReturn++;
    public static final int GET_PLAYLIST_FAILED = iReturn++;
    
    // bookmark return
    public static final int ADD_ARTIST_BOOKMARK_SUCCESS = iReturn++;
    public static final int ADD_ARTIST_BOOKMARK_FAILED  = iReturn++;
    public static final int ADD_SONG_BOOKMARK_SUCCESS   = iReturn++;
    public static final int ADD_SONG_BOOKMARK_FAILED    = iReturn++;
    
    // feedback return
    public static final int ADD_FEEDBACK_SUCCESS   = iReturn++;
    public static final int ADD_FEEDBACK_FAILED    = iReturn++;

    // sleep song return
    public static final int SLEEP_SONG_SUCCESS = iReturn++;
    public static final int SLEEP_SONG_FAILED  = iReturn++;
    
    // explain track return
    public static final int EXPLAIN_TRACK_SUCCESS = iReturn++;
    public static final int EXPLAIN_TRACK_FAILED  = iReturn++;
    
    // music search return
    public static final int MUSIC_SEARCH_SUCCESS = iReturn++;
    public static final int MUSIC_SEARCH_FAILED  = iReturn++;
    public static final int MUSIC_SEARCH_WITH_EMPTY_TEXT = iReturn++;
    
    // music search auto complete return
    public static final int MUSIC_SEARCH_AUTO_COMPLETE_SUCCESS = iReturn++;
    public static final int MUSIC_SEARCH_AUTO_COMPLETE_FAILED  = iReturn++;
    public static final int MUSIC_SEARCH_AUTO_COMPLETE_WITH_EMPTY_TEXT = iReturn++;
    
    // create station return
    public static final int CREATE_STATION_SUCCESS = iReturn++;
    public static final int CREATE_STATION_FAILED  = iReturn++;
    public static final int CREATE_STATION_WITH_EMPTY_MUSIC_TOKEN  = iReturn++;
    
    // get ad metadata return
    public static final int GET_AD_METADATA_SUCCESS = iReturn++;
    public static final int GET_AD_METADATA_FAILED  = iReturn++;
    
    // reach skip limitation
    public static final int REACH_SKIP_LIMITATION = iReturn++;
    
    // are you still there - 8 hours idle alarm
    public static final int ARE_YOU_STILL_THERE = iReturn++;
    
    // download image return
    public static final int DOWNLOAD_IMAGE_SUCCESS = iReturn++;
    public static final int DOWNLOAD_IMAGE_FAILED  = iReturn++;
}
