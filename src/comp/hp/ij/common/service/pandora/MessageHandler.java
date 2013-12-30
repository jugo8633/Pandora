package comp.hp.ij.common.service.pandora;

import java.util.ArrayList;

import android.content.Context;

import comp.hp.ij.common.service.pandora.api.PandoraAPIConstants;
import comp.hp.ij.common.service.pandora.api.PandoraAPIErrorCode;
import comp.hp.ij.mars.pandora.R;

public class MessageHandler {
    
    private ArrayList<Integer> alErrorCode  = null;
    private ArrayList<Integer> alResourceId = null;

    private Context context = null;
    
    public MessageHandler(Context contextInput) {
        context = contextInput;
        initialLists();
    }
    
    public String getMessage(int iReturnCode) {
        int iIndex = alErrorCode.indexOf(iReturnCode);
        String sReturn = "There is no alert message for code [" + iReturnCode + "] now.";
        if (-1 != iIndex) {
            String sTemp = context.getString(alResourceId.get(iIndex));
            sReturn = "".equals(sTemp) ? sReturn : sTemp;
        }
        return sReturn;
    }
    
    private void initialLists() {
        alErrorCode  = new ArrayList<Integer>();
        alResourceId = new ArrayList<Integer>();
        
        // +++++ PandoraAPIConstants +++++ //
        alErrorCode.add(PandoraAPIConstants.NETWORK_UNREACHABLE);
        alResourceId.add(R.string.NETWORK_UNREACHABLE);
        alErrorCode.add(PandoraAPIConstants.FAIL_TO_CONNECT_TO_INTERNET);
        alResourceId.add(R.string.FAIL_TO_CONNECT_TO_INTERNET);
        alErrorCode.add(PandoraAPIConstants.FAIL_TO_CONNECT_TO_PANDORA);
        alResourceId.add(R.string.FAIL_TO_CONNECT_TO_PANDORA);
        alErrorCode.add(PandoraAPIConstants.CHECK_LICENSE_SUCCESS);
        alResourceId.add(R.string.CHECK_LICENSE_SUCCESS);
        alErrorCode.add(PandoraAPIConstants.CHECK_LICENSE_FAILED);
        alResourceId.add(R.string.CHECK_LICENSE_FAILED);
        alErrorCode.add(PandoraAPIConstants.PARTNER_LOGIN_SUCCESS);
        alResourceId.add(R.string.PARTNER_LOGIN_SUCCESS);
        alErrorCode.add(PandoraAPIConstants.PARTNER_LOGIN_FAILED);
        alResourceId.add(R.string.PARTNER_LOGIN_FAILED);
        alErrorCode.add(PandoraAPIConstants.USER_LOGIN_SUCCESS);
        alResourceId.add(R.string.USER_LOGIN_SUCCESS);
        alErrorCode.add(PandoraAPIConstants.USER_LOGIN_FAILED);
        alResourceId.add(R.string.USER_LOGIN_FAILED);
        alErrorCode.add(PandoraAPIConstants.INVALID_USERNAME_PASSWORD);
        alResourceId.add(R.string.INVALID_USERNAME_PASSWORD);
        alErrorCode.add(PandoraAPIConstants.USER_IS_SUSPENDED);
        alResourceId.add(R.string.USER_IS_SUSPENDED);
        alErrorCode.add(PandoraAPIConstants.DEVICE_LOGIN_SUCCESS);
        alResourceId.add(R.string.DEVICE_LOGIN_SUCCESS);
        alErrorCode.add(PandoraAPIConstants.DEVICE_LOGIN_FAILED);
        alResourceId.add(R.string.DEVICE_LOGIN_FAILED);
        alErrorCode.add(PandoraAPIConstants.ASSOCIATE_SUCCESS);
        alResourceId.add(R.string.ASSOCIATE_SUCCESS);
        alErrorCode.add(PandoraAPIConstants.ASSOCIATE_FAILED);
        alResourceId.add(R.string.ASSOCIATE_FAILED);
        alErrorCode.add(PandoraAPIConstants.DEVICE_ASSOCIATED);
        alResourceId.add(R.string.DEVICE_ASSOCIATED);
        alErrorCode.add(PandoraAPIConstants.DEVICE_NOT_ASSOCIATED);
        alResourceId.add(R.string.DEVICE_NOT_ASSOCIATED);
        alErrorCode.add(PandoraAPIConstants.LOGOUT_SUCCESS);
        alResourceId.add(R.string.LOGOUT_SUCCESS);
        alErrorCode.add(PandoraAPIConstants.LOGOUT_FAILED);
        alResourceId.add(R.string.LOGOUT_FAILED);
        alErrorCode.add(PandoraAPIConstants.DISASSOCIATE_SUCCESS);
        alResourceId.add(R.string.DISASSOCIATE_SUCCESS);
        alErrorCode.add(PandoraAPIConstants.DISASSOCIATE_FAILED);
        alResourceId.add(R.string.DISASSOCIATE_FAILED);
        alErrorCode.add(PandoraAPIConstants.GENERATE_ACTIVATION_CODE_SUCCESS);
        alResourceId.add(R.string.GENERATE_ACTIVATION_CODE_SUCCESS);
        alErrorCode.add(PandoraAPIConstants.GENERATE_ACTIVATION_CODE_FAILED);
        alResourceId.add(R.string.GENERATE_ACTIVATION_CODE_FAILED);
        alErrorCode.add(PandoraAPIConstants.GET_STATION_LIST_SUCCESS);
        alResourceId.add(R.string.GET_STATION_LIST_SUCCESS);
        alErrorCode.add(PandoraAPIConstants.GET_STATION_LIST_FAILED);
        alResourceId.add(R.string.GET_STATION_LIST_FAILED);
        alErrorCode.add(PandoraAPIConstants.DELETE_STATION_SUCCESS);
        alResourceId.add(R.string.DELETE_STATION_SUCCESS);
        alErrorCode.add(PandoraAPIConstants.DELETE_STATION_FAILED);
        alResourceId.add(R.string.DELETE_STATION_FAILED);
        alErrorCode.add(PandoraAPIConstants.GET_PLAYLIST_SUCCESS);
        alResourceId.add(R.string.GET_PLAYLIST_SUCCESS);
        alErrorCode.add(PandoraAPIConstants.GET_PLAYLIST_FAILED);
        alResourceId.add(R.string.GET_PLAYLIST_FAILED);
        alErrorCode.add(PandoraAPIConstants.ADD_ARTIST_BOOKMARK_SUCCESS);
        alResourceId.add(R.string.ADD_ARTIST_BOOKMARK_SUCCESS);
        alErrorCode.add(PandoraAPIConstants.ADD_ARTIST_BOOKMARK_FAILED);
        alResourceId.add(R.string.ADD_ARTIST_BOOKMARK_FAILED);
        alErrorCode.add(PandoraAPIConstants.ADD_SONG_BOOKMARK_SUCCESS);
        alResourceId.add(R.string.ADD_SONG_BOOKMARK_SUCCESS);
        alErrorCode.add(PandoraAPIConstants.ADD_SONG_BOOKMARK_FAILED);
        alResourceId.add(R.string.ADD_SONG_BOOKMARK_FAILED);
        alErrorCode.add(PandoraAPIConstants.ADD_FEEDBACK_SUCCESS);
        alResourceId.add(R.string.ADD_FEEDBACK_SUCCESS);
        alErrorCode.add(PandoraAPIConstants.ADD_FEEDBACK_FAILED);
        alResourceId.add(R.string.ADD_FEEDBACK_FAILED);
        alErrorCode.add(PandoraAPIConstants.SLEEP_SONG_SUCCESS);
        alResourceId.add(R.string.SLEEP_SONG_SUCCESS);
        alErrorCode.add(PandoraAPIConstants.SLEEP_SONG_FAILED);
        alResourceId.add(R.string.SLEEP_SONG_FAILED);
        alErrorCode.add(PandoraAPIConstants.EXPLAIN_TRACK_SUCCESS);
        alResourceId.add(R.string.EXPLAIN_TRACK_SUCCESS);
        alErrorCode.add(PandoraAPIConstants.EXPLAIN_TRACK_FAILED);
        alResourceId.add(R.string.EXPLAIN_TRACK_FAILED);
        alErrorCode.add(PandoraAPIConstants.MUSIC_SEARCH_SUCCESS);
        alResourceId.add(R.string.MUSIC_SEARCH_SUCCESS);
        alErrorCode.add(PandoraAPIConstants.MUSIC_SEARCH_FAILED);
        alResourceId.add(R.string.MUSIC_SEARCH_FAILED);
        alErrorCode.add(PandoraAPIConstants.MUSIC_SEARCH_WITH_EMPTY_TEXT);
        alResourceId.add(R.string.MUSIC_SEARCH_WITH_EMPTY_TEXT);
        alErrorCode.add(PandoraAPIConstants.MUSIC_SEARCH_AUTO_COMPLETE_SUCCESS);
        alResourceId.add(R.string.MUSIC_SEARCH_AUTO_COMPLETE_SUCCESS);
        alErrorCode.add(PandoraAPIConstants.MUSIC_SEARCH_AUTO_COMPLETE_FAILED);
        alResourceId.add(R.string.MUSIC_SEARCH_AUTO_COMPLETE_FAILED);
        alErrorCode.add(PandoraAPIConstants.MUSIC_SEARCH_AUTO_COMPLETE_WITH_EMPTY_TEXT);
        alResourceId.add(R.string.MUSIC_SEARCH_AUTO_COMPLETE_WITH_EMPTY_TEXT);
        alErrorCode.add(PandoraAPIConstants.CREATE_STATION_SUCCESS);
        alResourceId.add(R.string.CREATE_STATION_SUCCESS);
        alErrorCode.add(PandoraAPIConstants.CREATE_STATION_FAILED);
        alResourceId.add(R.string.CREATE_STATION_FAILED);
        alErrorCode.add(PandoraAPIConstants.CREATE_STATION_WITH_EMPTY_MUSIC_TOKEN);
        alResourceId.add(R.string.CREATE_STATION_WITH_EMPTY_MUSIC_TOKEN);
        alErrorCode.add(PandoraAPIConstants.GET_AD_METADATA_SUCCESS);
        alResourceId.add(R.string.GET_AD_METADATA_SUCCESS);
        alErrorCode.add(PandoraAPIConstants.GET_AD_METADATA_FAILED);
        alResourceId.add(R.string.GET_AD_METADATA_FAILED);
        alErrorCode.add(PandoraAPIConstants.REACH_SKIP_LIMITATION);
        alResourceId.add(R.string.REACH_SKIP_LIMITATION);
        alErrorCode.add(PandoraAPIConstants.ARE_YOU_STILL_THERE);
        alResourceId.add(R.string.ARE_YOU_STILL_THERE);
        // ----- PandoraAPIConstants ----- //
        
        // +++++ PandoraAPIErrorCode +++++ //
        alErrorCode.add(PandoraAPIErrorCode.INTERNAL_ERROR);
        alResourceId.add(R.string.INTERNAL_ERROR);
        alErrorCode.add(PandoraAPIErrorCode.MAINTENANCE_MODE);
        alResourceId.add(R.string.MAINTENANCE_MODE);
        alErrorCode.add(PandoraAPIErrorCode.URL_PARAM_MISSING_METHOD);
        alResourceId.add(R.string.URL_PARAM_MISSING_METHOD);
        alErrorCode.add(PandoraAPIErrorCode.URL_PARAM_MISSING_AUTH_TOKEN);
        alResourceId.add(R.string.URL_PARAM_MISSING_AUTH_TOKEN);
        alErrorCode.add(PandoraAPIErrorCode.URL_PARAM_MISSING_PARTNER_ID);
        alResourceId.add(R.string.URL_PARAM_MISSING_PARTNER_ID);
        alErrorCode.add(PandoraAPIErrorCode.URL_PARAM_MISSING_USER_ID);
        alResourceId.add(R.string.URL_PARAM_MISSING_USER_ID);
        alErrorCode.add(PandoraAPIErrorCode.SECURE_PROTOCOL_REQUIRED);
        alResourceId.add(R.string.SECURE_PROTOCOL_REQUIRED);
        alErrorCode.add(PandoraAPIErrorCode.CERTIFICATE_REQUIRED);
        alResourceId.add(R.string.CERTIFICATE_REQUIRED);
        alErrorCode.add(PandoraAPIErrorCode.PARAMETER_TYPE_MISMATCH);
        alResourceId.add(R.string.PARAMETER_TYPE_MISMATCH);
        alErrorCode.add(PandoraAPIErrorCode.PARAMETER_MISSING);
        alResourceId.add(R.string.PARAMETER_MISSING);
        alErrorCode.add(PandoraAPIErrorCode.PARAMETER_VALUE_INVALID);
        alResourceId.add(R.string.PARAMETER_VALUE_INVALID);
        alErrorCode.add(PandoraAPIErrorCode.API_VERSION_NOT_SUPPORTED);
        alResourceId.add(R.string.API_VERSION_NOT_SUPPORTED);
        alErrorCode.add(PandoraAPIErrorCode.LICENSING_RESTRICTIONS);
        alResourceId.add(R.string.LICENSING_RESTRICTIONS);
        alErrorCode.add(PandoraAPIErrorCode.TIME_OUT_OF_SYNC);
        alResourceId.add(R.string.TIME_OUT_OF_SYNC);
        alErrorCode.add(PandoraAPIErrorCode.METHOD_NOT_FOUND);
        alResourceId.add(R.string.METHOD_NOT_FOUND);
        alErrorCode.add(PandoraAPIErrorCode.NON_SECURE_PROTOCOL_REQUIRED);
        alResourceId.add(R.string.NON_SECURE_PROTOCOL_REQUIRED);
        alErrorCode.add(PandoraAPIErrorCode.READ_ONLY_MODE);
        alResourceId.add(R.string.READ_ONLY_MODE);
        alErrorCode.add(PandoraAPIErrorCode.INVALID_AUTH_TOKEN);
        alResourceId.add(R.string.INVALID_AUTH_TOKEN);
        alErrorCode.add(PandoraAPIErrorCode.INVALID_LOGIN);
        alResourceId.add(R.string.INVALID_LOGIN);
        alErrorCode.add(PandoraAPIErrorCode.USER_NOT_ACTIVE);
        alResourceId.add(R.string.USER_NOT_ACTIVE);
        alErrorCode.add(PandoraAPIErrorCode.USER_NOT_AUTHORIZED);
        alResourceId.add(R.string.USER_NOT_AUTHORIZED);
        alErrorCode.add(PandoraAPIErrorCode.MAX_STATIONS_REACHED);
        alResourceId.add(R.string.MAX_STATIONS_REACHED);
        alErrorCode.add(PandoraAPIErrorCode.STATION_DOES_NOT_EXIST);
        alResourceId.add(R.string.STATION_DOES_NOT_EXIST);
        alErrorCode.add(PandoraAPIErrorCode.COMPLIMENTARY_PERIOD_ALREADY_IN_USE);
        alResourceId.add(R.string.COMPLIMENTARY_PERIOD_ALREADY_IN_USE);
        alErrorCode.add(PandoraAPIErrorCode.CALL_NOT_ALLOWED);
        alResourceId.add(R.string.CALL_NOT_ALLOWED);
        alErrorCode.add(PandoraAPIErrorCode.DEVICE_NOT_FOUND);
        alResourceId.add(R.string.DEVICE_NOT_FOUND);
        alErrorCode.add(PandoraAPIErrorCode.PARTNER_NOT_AUTHORIZED);
        alResourceId.add(R.string.PARTNER_NOT_AUTHORIZED);
        alErrorCode.add(PandoraAPIErrorCode.INVALID_USERNAME);
        alResourceId.add(R.string.INVALID_USERNAME);
        alErrorCode.add(PandoraAPIErrorCode.INVALID_PASSWORD);
        alResourceId.add(R.string.INVALID_PASSWORD);
        alErrorCode.add(PandoraAPIErrorCode.USERNAME_ALREADY_EXISTS);
        alResourceId.add(R.string.USERNAME_ALREADY_EXISTS);
        alErrorCode.add(PandoraAPIErrorCode.DEVICE_ALREADY_ASSOCIATED_TO_ACCOUNT);
        alResourceId.add(R.string.DEVICE_ALREADY_ASSOCIATED_TO_ACCOUNT);
        alErrorCode.add(PandoraAPIErrorCode.DEVICE_METADATA_TOO_LONG);
        alResourceId.add(R.string.DEVICE_METADATA_TOO_LONG);
        alErrorCode.add(PandoraAPIErrorCode.INVALID_EMAIL_ADDRESS);
        alResourceId.add(R.string.INVALID_EMAIL_ADDRESS);
        alErrorCode.add(PandoraAPIErrorCode.STATION_NAME_TOO_LONG);
        alResourceId.add(R.string.STATION_NAME_TOO_LONG);
        alErrorCode.add(PandoraAPIErrorCode.EXPLICIT_PIN_INCORRECT);
        alResourceId.add(R.string.EXPLICIT_PIN_INCORRECT);
        alErrorCode.add(PandoraAPIErrorCode.EXPLICIT_CONTENT_FILTER_NOT_ENABLED);
        alResourceId.add(R.string.EXPLICIT_CONTENT_FILTER_NOT_ENABLED);
        alErrorCode.add(PandoraAPIErrorCode.EXPLICIT_PIN_MALFORMED);
        alResourceId.add(R.string.EXPLICIT_PIN_MALFORMED);
        alErrorCode.add(PandoraAPIErrorCode.EXPLICIT_PIN_NOT_SET);
        alResourceId.add(R.string.EXPLICIT_PIN_NOT_SET);
        alErrorCode.add(PandoraAPIErrorCode.EXPLICIT_PIN_ALREADY_SET);
        alResourceId.add(R.string.EXPLICIT_PIN_ALREADY_SET);
        alErrorCode.add(PandoraAPIErrorCode.DEVICE_MODEL_INVALID);
        alResourceId.add(R.string.DEVICE_MODEL_INVALID);
        alErrorCode.add(PandoraAPIErrorCode.INVALID_ZIP_CODE);
        alResourceId.add(R.string.INVALID_ZIP_CODE);
        alErrorCode.add(PandoraAPIErrorCode.INVALID_BIRTH_YEAR);
        alResourceId.add(R.string.INVALID_BIRTH_YEAR);
        alErrorCode.add(PandoraAPIErrorCode.USER_TOO_YOUNG);
        alResourceId.add(R.string.USER_TOO_YOUNG);
        alErrorCode.add(PandoraAPIErrorCode.INVALID_GENDER);
        alResourceId.add(R.string.INVALID_GENDER);
        alErrorCode.add(PandoraAPIErrorCode.INVALID_COUNTRY_CODE);
        alResourceId.add(R.string.INVALID_COUNTRY_CODE);
        alErrorCode.add(PandoraAPIErrorCode.USER_NOT_FOUND);
        alResourceId.add(R.string.USER_NOT_FOUND);
        alErrorCode.add(PandoraAPIErrorCode.INVALID_AD_TOKEN);
        alResourceId.add(R.string.INVALID_AD_TOKEN);
        alErrorCode.add(PandoraAPIErrorCode.ERROR_QUICKMIX_REQUIRES_MORE_STATIONS);
        alResourceId.add(R.string.ERROR_QUICKMIX_REQUIRES_MORE_STATIONS);
        alErrorCode.add(PandoraAPIErrorCode.ERROR_REMOVING_TOO_MANY_SEEDS);
        alResourceId.add(R.string.ERROR_REMOVING_TOO_MANY_SEEDS);
        alErrorCode.add(PandoraAPIErrorCode.ERROR_DEVICE_MODEL_ALREADY_EXISTS);
        alResourceId.add(R.string.ERROR_DEVICE_MODEL_ALREADY_EXISTS);
        alErrorCode.add(PandoraAPIErrorCode.ERROR_DEVICE_DISABLED);
        alResourceId.add(R.string.ERROR_DEVICE_DISABLED);
        // +++++ PandoraAPIErrorCode +++++ //
    }
}
