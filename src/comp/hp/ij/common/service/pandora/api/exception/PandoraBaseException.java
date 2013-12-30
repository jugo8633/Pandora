package comp.hp.ij.common.service.pandora.api.exception;

import java.util.ArrayList;

import comp.hp.ij.common.service.pandora.api.PandoraAPIConstants;
import comp.hp.ij.common.service.pandora.api.PandoraAPIErrorCode;

@SuppressWarnings("serial")
public class PandoraBaseException extends Exception {
        
    private ArrayList<Integer> alErrorCodeList = new ArrayList<Integer>();
    private ArrayList<String> alExceptionList = new ArrayList<String>();
    
    public PandoraBaseException() {
        initExceptionList();
    }
    
    public PandoraBaseException(int iErrorCode) throws Exception {
        initExceptionList();
        try {
            Object o = Class.forName(getExceptionName(iErrorCode)).newInstance();
            throw (Exception) o;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public int getErrorCode() {
        String sClassName = getClass().getName();
        System.out.println("sClassName: [" + sClassName + "]");
        int iIndex = alExceptionList.indexOf(sClassName);
        if (-1 == iIndex) {
            return PandoraAPIConstants.UNKNOWN_EXCEPTION;
        }
        return alErrorCodeList.get(iIndex);
    }
    
    private String getExceptionName(int iErrorCode) {
        int iIndex = alErrorCodeList.indexOf(iErrorCode);
        if (-1 == iIndex) {
            return getClass().getName();
        }
        return alExceptionList.get(iIndex);
    }
    
    private void initExceptionList() {
        alErrorCodeList.add(PandoraAPIErrorCode.INTERNAL_ERROR);
        alExceptionList.add(PandoraInternalErrorException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.MAINTENANCE_MODE);
        alExceptionList.add(PandoraMaintenanceModeException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.URL_PARAM_MISSING_METHOD);
        alExceptionList.add(PandoraUrlParamMissingMethodException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.URL_PARAM_MISSING_AUTH_TOKEN);
        alExceptionList.add(PandoraUrlParamMissingAuthTokenException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.URL_PARAM_MISSING_PARTNER_ID);
        alExceptionList.add(PandoraUrlParamMissingPartnerIdException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.URL_PARAM_MISSING_USER_ID);
        alExceptionList.add(PandoraUrlParamMissingUserIdException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.SECURE_PROTOCOL_REQUIRED);
        alExceptionList.add(PandoraSecureProtocolRequiredException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.CERTIFICATE_REQUIRED);
        alExceptionList.add(PandoraCertificateRequiredException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.PARAMETER_TYPE_MISMATCH);
        alExceptionList.add(PandoraParameterTypeMismatchException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.PARAMETER_MISSING);
        alExceptionList.add(PandoraParameterMissingException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.PARAMETER_VALUE_INVALID);
        alExceptionList.add(PandoraParameterValueInvalidException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.API_VERSION_NOT_SUPPORTED);
        alExceptionList.add(PandoraApiVersionNotSupportedException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.LICENSING_RESTRICTIONS);
        alExceptionList.add(PandoraLicensingRestrictionsException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.TIME_OUT_OF_SYNC);
        alExceptionList.add(PandoraTimeOutOfSyncException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.METHOD_NOT_FOUND);
        alExceptionList.add(PandoraMethodNotFoundException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.NON_SECURE_PROTOCOL_REQUIRED);
        alExceptionList.add(PandoraNonSecureProtocolRequiredException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.READ_ONLY_MODE);
        alExceptionList.add(PandoraReadOnlyModeException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.INVALID_AUTH_TOKEN);
        alExceptionList.add(PandoraInvalidAuthTokenException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.INVALID_LOGIN);
        alExceptionList.add(PandoraInvalidLoginException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.USER_NOT_ACTIVE);
        alExceptionList.add(PandoraUserNotActiveException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.USER_NOT_AUTHORIZED);
        alExceptionList.add(PandoraUserNotAuthorizedException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.MAX_STATIONS_REACHED);
        alExceptionList.add(PandoraMaxStationsReachedException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.STATION_DOES_NOT_EXIST);
        alExceptionList.add(PandoraStationDoesNotExistException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.COMPLIMENTARY_PERIOD_ALREADY_IN_USE);
        alExceptionList.add(PandoraComplimentaryPeriodAlreadyInUseException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.CALL_NOT_ALLOWED);
        alExceptionList.add(PandoraCallNotAllowedException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.DEVICE_NOT_FOUND);
        alExceptionList.add(PandoraDeviceNotFoundException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.PARTNER_NOT_AUTHORIZED);
        alExceptionList.add(PandoraPartnerNotAuthorizedException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.INVALID_USERNAME);
        alExceptionList.add(PandoraInvalidUsernameException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.INVALID_PASSWORD);
        alExceptionList.add(PandoraInvalidPasswordException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.USERNAME_ALREADY_EXISTS);
        alExceptionList.add(PandoraUsernameAlreadyExistsException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.DEVICE_ALREADY_ASSOCIATED_TO_ACCOUNT);
        alExceptionList.add(PandoraDeviceAlreadyAssociatedToAccountException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.DEVICE_METADATA_TOO_LONG);
        alExceptionList.add(PandoraDeviceMetadataTooLongException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.INVALID_EMAIL_ADDRESS);
        alExceptionList.add(PandoraInvalidEmailAddressException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.STATION_NAME_TOO_LONG);
        alExceptionList.add(PandoraStationNameTooLongException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.EXPLICIT_PIN_INCORRECT);
        alExceptionList.add(PandoraExplicitPinIncorrectException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.EXPLICIT_CONTENT_FILTER_NOT_ENABLED);
        alExceptionList.add(PandoraExplicitContentFilterNotEnableException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.EXPLICIT_PIN_MALFORMED);
        alExceptionList.add(PandoraExplicitPinMalformedException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.EXPLICIT_PIN_NOT_SET);
        alExceptionList.add(PandoraExplicitPinNotSetException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.EXPLICIT_PIN_ALREADY_SET);
        alExceptionList.add(PandoraExplicitPinAlreadySetException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.DEVICE_MODEL_INVALID);
        alExceptionList.add(PandoraDeviceModelInvalidException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.INVALID_ZIP_CODE);
        alExceptionList.add(PandoraInvalidZipCodeException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.INVALID_BIRTH_YEAR);
        alExceptionList.add(PandoraInvalidBirthYearException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.USER_TOO_YOUNG);
        alExceptionList.add(PandoraUserTooYoungException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.INVALID_GENDER);
        alExceptionList.add(PandoraInvalidGenderException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.INVALID_COUNTRY_CODE);
        alExceptionList.add(PandoraInvalidCountryCodeException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.USER_NOT_FOUND);
        alExceptionList.add(PandoraUserNotFoundException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.INVALID_AD_TOKEN);
        alExceptionList.add(PandoraInvalidAdTokenException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.ERROR_QUICKMIX_REQUIRES_MORE_STATIONS);
        alExceptionList.add(PandoraErrorQuickmixRequiresMoreStationsException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.ERROR_REMOVING_TOO_MANY_SEEDS);
        alExceptionList.add(PandoraErrorRemovingTooManySeedsException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.ERROR_DEVICE_MODEL_ALREADY_EXISTS);
        alExceptionList.add(PandoraErrorDeviceModelAlreadyExistsException.class.getName());
        alErrorCodeList.add(PandoraAPIErrorCode.ERROR_DEVICE_DISABLED);
        alExceptionList.add(PandoraErrorDeviceDisabledException.class.getName());
    }

}
