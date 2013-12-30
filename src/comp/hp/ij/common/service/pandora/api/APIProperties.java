package comp.hp.ij.common.service.pandora.api;

import comp.hp.ij.common.service.pandora.util.Logger;

public class APIProperties {
    
    private static final boolean isTesting = PandoraAPIConstants.IS_TESTING;
    
    private static final String sSeed = "MarsPandora";

    public static final String API_PROTOCOL = "json";
    
    private static final String API_URL_JSON_TESTING = "http://tuner-beta.savagebeast.com:80/services/json/";
    private static final String API_URL_JSON_PRODUCTION = "http://tuner.pandora.com:80/services/json/";
    
    private static final String SECURE_API_URL_JSON_TESTING = "https://tuner-beta.savagebeast.com:443/services/json/";
    private static final String SECURE_API_URL_JSON_PRODUCTION = "https://tuner.pandora.com:443/services/json/";

    //private static final String SYNC_TIME_KEY_TESTING = "DDA89BE9DAE4B49E"; // Oxygen
    //private static final String SYNC_TIME_KEY_TESTING = "21C81D72DDB06708"; // Mars
    private static final String SYNC_TIME_KEY_TESTING = "7EEE84AAA3AC781F17C2BFB1C9F9D40A628D114536E55BA45349DB4B4DA41383"; // Mars encrypt
    private static final String SYNC_TIME_KEY_PRODUCTION = "";
    
    //private static final String REQUEST_KEY_TESTING = "89CAD8B5EA519169"; // Oxygen
    //private static final String REQUEST_KEY_TESTING = "17E5479AC9FE1B06"; // Mars
    private static final String REQUEST_KEY_TESTING = "4B4FFB2193BD57B70AC8996D840336DB628D114536E55BA45349DB4B4DA41383"; // Mars encrypt
    private static final String REQUEST_KEY_PRODUCTION = "";
    
    //private static final String DEVICE_MODEL_TESTING = "foxconn"; // Oxygen
    //private static final String DEVICE_MODEL_TESTING = "foxconn7"; // Mars
    private static final String DEVICE_MODEL_TESTING = "72DA8AE008C93F7B94DEAB69C3AAAF79"; // Mars encrypt
    private static final String DEVICE_MODEL_PRODUCTION = "";
    
    //private static final String PARTNER_USERNAME_TESTING = "foxconn"; // Oxygen
    //private static final String PARTNER_USERNAME_TESTING = "foxconn0"; // Mars
    private static final String PARTNER_USERNAME_TESTING = "01512BA8C09E6299A2CCE352C5FB84F2"; // Mars encrypt
    private static final String PARTNER_USERNAME_PRODUCTION = "";
    
    //private static final String PARTNER_PASSWORD_TESTING = "C8908F9F9A0780823613DD37D8B929033703B12937B61E29"; // Oxygen
    //private static final String PARTNER_PASSWORD_TESTING = "38BEE44CD8E1C17C9577D4D72CB758AB75D3DF08F8409516"; // Mars
    private static final String PARTNER_PASSWORD_TESTING = "82E28004D283CF264105F631641A7A8BC72E9086E98FC6F052ABB1A7D13BCFE1E145208B5992F42591E21926DFE8A800628D114536E55BA45349DB4B4DA41383"; // Mars encrypt
    private static final String PARTNER_PASSWORD_PRODUCTION = "";
    
    /*
    public static void generateAllEncryption() { // TODO generate all encryption
        try {
            String sEncryption = "";
            
            sEncryption = SimpleCrypto.encrypt(sSeed, SYNC_TIME_KEY_TESTING);
            Logger.d("SYNC_TIME_KEY_TESTING:       [" + sEncryption + "]");
            sEncryption = SimpleCrypto.encrypt(sSeed, SYNC_TIME_KEY_PRODUCTION);
            Logger.d("SYNC_TIME_KEY_PRODUCTION:    [" + sEncryption + "]");
            
            sEncryption = SimpleCrypto.encrypt(sSeed, REQUEST_KEY_TESTING);
            Logger.d("REQUEST_KEY_TESTING:         [" + sEncryption + "]");
            sEncryption = SimpleCrypto.encrypt(sSeed, REQUEST_KEY_PRODUCTION);
            Logger.d("REQUEST_KEY_PRODUCTION:      [" + sEncryption + "]");
            
            sEncryption = SimpleCrypto.encrypt(sSeed, DEVICE_MODEL_TESTING);
            Logger.d("DEVICE_MODEL_TESTING:        [" + sEncryption + "]");
            sEncryption = SimpleCrypto.encrypt(sSeed, DEVICE_MODEL_PRODUCTION);
            Logger.d("DEVICE_MODEL_PRODUCTION:     [" + sEncryption + "]");
            
            sEncryption = SimpleCrypto.encrypt(sSeed, PARTNER_USERNAME_TESTING);
            Logger.d("PARTNER_USERNAME_TESTING:    [" + sEncryption + "]");
            sEncryption = SimpleCrypto.encrypt(sSeed, PARTNER_USERNAME_PRODUCTION);
            Logger.d("PARTNER_USERNAME_PRODUCTION: [" + sEncryption + "]");
            
            sEncryption = SimpleCrypto.encrypt(sSeed, PARTNER_PASSWORD_TESTING);
            Logger.d("PARTNER_PASSWORD_TESTING:    [" + sEncryption + "]");
            sEncryption = SimpleCrypto.encrypt(sSeed, PARTNER_PASSWORD_PRODUCTION);
            Logger.d("PARTNER_PASSWORD_PRODUCTION: [" + sEncryption + "]");
        } catch (Exception e) {
            Logger.e(e);
        }
    }
    */
    
    public static String getApiUrlJson() {
        String sReturn = API_URL_JSON_PRODUCTION;
        if (isTesting) {
            sReturn = API_URL_JSON_TESTING;
        }
        return sReturn;
    }
    
    public static String getSecureApiUrlJson() {
        String sReturn = SECURE_API_URL_JSON_PRODUCTION;
        if (isTesting) {
            sReturn = SECURE_API_URL_JSON_TESTING;
        }
        return sReturn;
    }

    public static String getSyncTimeKey() {
        String sReturn = "";
        try {
            if (isTesting) {
                sReturn = SimpleCrypto.decrypt(sSeed, SYNC_TIME_KEY_TESTING);
            } else {
                sReturn = SimpleCrypto.decrypt(sSeed, SYNC_TIME_KEY_PRODUCTION);
            }
        } catch (Exception e) {
            sReturn = "";
            Logger.e(e);
        }
        return sReturn;
    }
    
    public static String getRequestKey() {
        String sReturn = "";
        try {
            if (isTesting) {
                sReturn = SimpleCrypto.decrypt(sSeed, REQUEST_KEY_TESTING);
            } else {
                sReturn = SimpleCrypto.decrypt(sSeed, REQUEST_KEY_PRODUCTION);
            }
        } catch (Exception e) {
            sReturn = "";
            Logger.e(e);
        }
        return sReturn;
    }
    
    public static String getDeviceModel() {
        String sReturn = "";
        try {
            if (isTesting) {
                sReturn = SimpleCrypto.decrypt(sSeed, DEVICE_MODEL_TESTING);
            } else {
                sReturn = SimpleCrypto.decrypt(sSeed, DEVICE_MODEL_PRODUCTION);
            }
        } catch (Exception e) {
            sReturn = "";
            Logger.e(e);
        }
        return sReturn;
    }
    
    public static String getPartnerUsername() {
        String sReturn = "";
        try {
            if (isTesting) {
                sReturn = SimpleCrypto.decrypt(sSeed, PARTNER_USERNAME_TESTING);
            } else {
                sReturn = SimpleCrypto.decrypt(sSeed, PARTNER_USERNAME_PRODUCTION);
            }
        } catch (Exception e) {
            sReturn = "";
            Logger.e(e);
        }
        return sReturn;
    }
    
    public static String getPartnerPassword() {
        String sReturn = "";
        try {
            if (isTesting) {
                sReturn = SimpleCrypto.decrypt(sSeed, PARTNER_PASSWORD_TESTING);
            } else {
                sReturn = SimpleCrypto.decrypt(sSeed, PARTNER_PASSWORD_PRODUCTION);
            }
        } catch (Exception e) {
            sReturn = "";
            Logger.e(e);
        }
        return sReturn;
    }
}
