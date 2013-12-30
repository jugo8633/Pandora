package comp.hp.ij.common.service.pandora.api;

public class PandoraAPIErrorCode {
    // Error code defined by Pandora
    
    /** internal server error */
    public static final int INTERNAL_ERROR = 0;
    
    /** maintenance mode (site unavailable) */
    public static final int MAINTENANCE_MODE = 1;
    
    /** 'method' url parameter is missing/invalid */
    public static final int URL_PARAM_MISSING_METHOD = 2;
    
    /** 'auth_token' url parameter is missing/invalid */
    public static final int URL_PARAM_MISSING_AUTH_TOKEN = 3;
    
    /** 'partner_id' url parameter is missing/invalid */
    public static final int URL_PARAM_MISSING_PARTNER_ID = 4;
    
    /** 'user_id' url parameter is missing/invalid */
    public static final int URL_PARAM_MISSING_USER_ID = 5;
    
    /** method requires secure protocol (call the method using 'https') */
    public static final int SECURE_PROTOCOL_REQUIRED = 6;
    
    /** method requires Pandora signed certificate */
    public static final int CERTIFICATE_REQUIRED = 7;
    
    /** Parameter type mismatch; check the type of the parameter supplied */
    public static final int PARAMETER_TYPE_MISMATCH = 8;
    
    /** Required parameter missing */
    public static final int PARAMETER_MISSING = 9;
    
    /** Parameter value invalid */
    public static final int PARAMETER_VALUE_INVALID = 10;
    
    /** api version not supported (upgrade client to support new version) */
    public static final int API_VERSION_NOT_SUPPORTED = 11;
    
    /** client restricted because of licensing restrictions (i.e. located outside the US) */
    public static final int LICENSING_RESTRICTIONS = 12;
    
    /** syncTime sent in the request is out of sync with the server's time */
    public static final int TIME_OUT_OF_SYNC = 13;
    
    /** the method on the server does not exist */
    public static final int METHOD_NOT_FOUND = 14;
    
    /** method requires non secure protocol (call the method using 'http') */
    public static final int NON_SECURE_PROTOCOL_REQUIRED = 15;
    
    /** 
     * Pandora performing system/database maintenance.<br/>
     * Suggested error message:<br/>
     * Pandora is conducting system maintenance.<br/>
     * You will be able to listen to existing stations while they work on their systems,<br/>
     * but you won't be able to create new stations,<br/>
     * submit feedback or edit your account in any way until the maintenance is complete.<br/>
     * Thanks for your patience.
     */
    public static final int READ_ONLY_MODE = 1000;
    
    /** invalid auth token (auth token expired, need to re-authenticate) */
    public static final int INVALID_AUTH_TOKEN = 1001;
    
    /** invalid login (username/password invalid) */
    public static final int INVALID_LOGIN = 1002;
    
    /** User not active; user's account has been suspended/disabled */
    public static final int USER_NOT_ACTIVE = 1003;
    
    /** User not authorized (e.g. renaming some else's station) */
    public static final int USER_NOT_AUTHORIZED = 1004;
    
    /** create station failed, reached max station limit */
    public static final int MAX_STATIONS_REACHED = 1005;
    
    /** Station does not exist; invalid station or station has been deleted */
    public static final int STATION_DOES_NOT_EXIST = 1006;
    
    /** Complimentary period already used for this user/device */
    public static final int COMPLIMENTARY_PERIOD_ALREADY_IN_USE = 1007;
    
    /**
     * Calling a method when not allowed (e.g. calling station.renameStation
     * when the station's 'allowRename' property is set to false)
     */
    public static final int CALL_NOT_ALLOWED = 1008;
    
    /** Device could not be found with the supplied device Id */
    public static final int DEVICE_NOT_FOUND = 1009;
    
    /** Partner not authorized to perform action */
    public static final int PARTNER_NOT_AUTHORIZED = 1010;
    
    /** Username is malformed. See Parameter Formats section for a valid username format */
    public static final int INVALID_USERNAME = 1011;
    
    /** Password is malformed. See Parameter Formats section for a valid password format */
    public static final int INVALID_PASSWORD = 1012;
    
    /** Username provided has already been used. */
    public static final int USERNAME_ALREADY_EXISTS = 1013;
    
    /** Device is already associated to an account */
    public static final int DEVICE_ALREADY_ASSOCIATED_TO_ACCOUNT = 1014;
    
    /** Values supplied exceed maximum length allowed */
    public static final int DEVICE_METADATA_TOO_LONG = 1015;
    
    /** Email Address is invalid */
    public static final int INVALID_EMAIL_ADDRESS = 1016;
    
    /** Station name is too long */
    public static final int STATION_NAME_TOO_LONG = 1017;
    
    /** the pin provided doesn't match our records */
    public static final int EXPLICIT_PIN_INCORRECT = 1018;
    
    /** trying to do a pin operation when the content filter is not enabled */
    public static final int EXPLICIT_CONTENT_FILTER_NOT_ENABLED = 1019;
    
    /** Explicit PIN contains invalid characters (allowed characters are a-zA-Z0-9) */
    public static final int EXPLICIT_PIN_MALFORMED = 1020;
    
    /** Explicit PIN has not been set yet */
    public static final int EXPLICIT_PIN_NOT_SET = 1021;
    
    /** Explicit PIN has already been set */
    public static final int EXPLICIT_PIN_ALREADY_SET = 1022;
    
    /** Device Model is invalid */
    public static final int DEVICE_MODEL_INVALID = 1023;
    
    /** Zip code is invalid */
    public static final int INVALID_ZIP_CODE = 1024;
    
    /** Birth year is invalid */
    public static final int INVALID_BIRTH_YEAR = 1025;
    
    /** User too young to use service */
    public static final int USER_TOO_YOUNG = 1026;
    
    /** Gener value is invalid */
    public static final int INVALID_GENDER = 1027;
    
    /** Country code is invalid */
    public static final int INVALID_COUNTRY_CODE = 1028;
    
    /** User account not found */
    public static final int USER_NOT_FOUND = 1029;
    
    /** ad token is invalid. Verify your code is using the ad token returned from station.getPlaylist */
    public static final int INVALID_AD_TOKEN = 1030;
    
    /** Not enough stations to create a QuickMix */
    public static final int ERROR_QUICKMIX_REQUIRES_MORE_STATIONS = 1031;
    
    /** Not enough seeds (artist and/or songs) for the station definition */
    public static final int ERROR_REMOVING_TOO_MANY_SEEDS = 1032;
    
    /** Device model provided has already been used. */
    public static final int ERROR_DEVICE_MODEL_ALREADY_EXISTS = 1033;
    
    /** Device model is disabled. */
    public static final int ERROR_DEVICE_DISABLED = 1034;

}
