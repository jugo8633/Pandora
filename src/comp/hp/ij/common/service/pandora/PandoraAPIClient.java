package comp.hp.ij.common.service.pandora;

import java.util.ArrayList;
import java.util.UUID;

import comp.hp.ij.common.service.pandora.api.FileDownloader;
import comp.hp.ij.common.service.pandora.api.Item;
import comp.hp.ij.common.service.pandora.api.PandoraAPIConstants;
import comp.hp.ij.common.service.pandora.api.PandoraAPIErrorCode;
import comp.hp.ij.common.service.pandora.api.PandoraClient;

import android.content.SharedPreferences;
import android.graphics.Bitmap;

import comp.hp.ij.common.service.pandora.data.ParcelDeviceActivationData;
import comp.hp.ij.common.service.pandora.data.ParcelExplanation;
import comp.hp.ij.common.service.pandora.data.ParcelSearchResult;
import comp.hp.ij.common.service.pandora.data.ParcelStation;
import comp.hp.ij.common.service.pandora.util.Logger;

/**
 * This code was developed by HON HAI PRECISION IND. CO., LTD., CMMSG/DWHD/CCP/SWI, Code for the Mars project.
 * 
 * @author Chance Wang
 */
public class PandoraAPIClient {
    
    private PandoraClient pandoraClient = null;

    private SharedPreferences sharedPreferences = null;
    
    /**
     * Constructor.
     */
    public PandoraAPIClient() {
        Logger.d("Initial PandoraAPIClient");
        if (null == pandoraClient) {
            pandoraClient = new PandoraClient();
        }
        
        Logger.d("BOARD:       [" + android.os.Build.BOARD + "]");
        Logger.d("BRAND:       [" + android.os.Build.BRAND + "]");
        Logger.d("DEVICE:      [" + android.os.Build.DEVICE + "]");
        Logger.d("DISPLAY:     [" + android.os.Build.DISPLAY + "]");
        Logger.d("FINGERPRINT: [" + android.os.Build.FINGERPRINT + "]");
        Logger.d("HOST:        [" + android.os.Build.HOST + "]");
        Logger.d("ID:          [" + android.os.Build.ID + "]");
        Logger.d("MODEL:       [" + android.os.Build.MODEL + "]");
        Logger.d("PRODUCT:     [" + android.os.Build.PRODUCT + "]");
        Logger.d("TAGS:        [" + android.os.Build.TAGS + "]");
        Logger.d("TIME:        [" + android.os.Build.TIME + "]");
        Logger.d("TYPE:        [" + android.os.Build.TYPE + "]");
        Logger.d("USER:        [" + android.os.Build.USER + "]");
    }

    /**
     * Set SharedPreferences object.
     * 
     * @param sharedPreferencesInput sharedPreferencesInput
     */
    public void setPreferences(SharedPreferences sharedPreferencesInput) {
        sharedPreferences = sharedPreferencesInput;
    }

    /**
     * Check if the device is associated.
     * 
     * @return Return true if the device is already associated else return false
     */
    public boolean isAssociated() {
        boolean isAssociated = getDeviceIdFromDevice().equals(getDeviceIdFromPreference());
        Logger.d("isAssociated: [" + isAssociated + "]");
        return isAssociated;
    }
    
    /**
     * Disassociate the device with current Pandora account.
     * 
     * @return API call status
     */
    public int doLogout() {
        Logger.d();
        int iReturnCode = PandoraAPIConstants.LOGOUT_SUCCESS;
        if (isAssociated()) {
            iReturnCode = pandoraClient.doLogout(getDeviceIdFromDevice());
            iReturnCode = (PandoraAPIConstants.DISASSOCIATE_SUCCESS == iReturnCode) ? PandoraAPIConstants.LOGOUT_SUCCESS : iReturnCode;
            if (PandoraAPIConstants.LOGOUT_SUCCESS == iReturnCode ||
                    PandoraAPIErrorCode.DEVICE_NOT_FOUND == iReturnCode) {
                removeDeviceIdFromPreference();
            }
        } else {
            Logger.d("Device is not associated");
            iReturnCode = PandoraAPIConstants.DEVICE_NOT_ASSOCIATED;
        }
        return iReturnCode;
    }
    
    /**
     * 
     * @return ParcelDeviceActivation
     */
    public int generateDeviceActivationCode() {
        Logger.d();
        return pandoraClient.generateDeviceActivationCode(getDeviceIdFromDevice());
    }
    
    public ParcelDeviceActivationData getDeviceActivitationData() {
        return pandoraClient.getDeviceActivationData();
    }
    
    /**
     * do device login to check if user has finished web registration.
     * 
     * @return
     */
    public int doDeviceLogin() {
        Logger.d();
        int iReturnCode = -1;
        Logger.d("do device login");
        iReturnCode = pandoraClient.doDeviceLogin(getDeviceIdFromDevice());
        if (PandoraAPIConstants.DEVICE_LOGIN_SUCCESS == iReturnCode) {
            saveDeviceIdToPreference(getDeviceIdFromDevice());
        }
        Logger.d("iReturnCode: [" + iReturnCode + "]");
        return iReturnCode;
    }

    /**
     * 
     * @param userName userName
     * @param password password
     */
    public void setPandoraAuth(String userName, String password) {
        Logger.d("userName: [" + userName + "]");
        Logger.d("password: [" + password + "]");
        pandoraClient.setPandoraAuth(userName, password);
    }
    
    /**
     * 
     * @return do login return value
     */
    public int doLogin() {
        Logger.d();
        int iReturnCode = -1;
        if (isAssociated()) {
            Logger.d("do device login");
            iReturnCode = pandoraClient.doDeviceLogin(getDeviceIdFromDevice());
        } else {
            Logger.d("do user login");
            iReturnCode = pandoraClient.doUserLogin();
            if (PandoraAPIConstants.USER_LOGIN_SUCCESS == iReturnCode) {
                int iAssociateReturn = pandoraClient.associateDevice(getDeviceIdFromDevice());
                if (PandoraAPIConstants.ASSOCIATE_SUCCESS == iAssociateReturn) {
                    saveDeviceIdToPreference(getDeviceIdFromDevice());
                } else {
                    Logger.d("Associate Fail");
                    iReturnCode = iAssociateReturn;
                }
            }
        }
        Logger.d("iReturnCode: [" + iReturnCode + "]");
        return iReturnCode;
    }

    /**
     * 
     * @return ArrayList with ParcelStation
     */
    public int getStationList() {
        Logger.d();
        return pandoraClient.getStationList();
    }
    
    public ArrayList<ParcelStation> getStationListData() {
        return pandoraClient.getStationListData();
    }
    
    /**
     * 
     * @param sStationToken stationToken
     * 
     * @return delete station return value
     */
    public int deleteStation(String sStationToken) {
        Logger.d();
        return pandoraClient.deleteStation(sStationToken);
    }

    /**
     * 
     * @param sStationTokenInput sStationTokenInput
     * 
     * @return ArrayList with Item
     */
    public int getPlaylist(String sStationTokenInput) {
        Logger.d();
        int iReturnCode = pandoraClient.getPlaylist(sStationTokenInput);
        while (PandoraAPIErrorCode.INVALID_AUTH_TOKEN == iReturnCode) {
            iReturnCode = doLogin();
            if (PandoraAPIConstants.USER_LOGIN_SUCCESS == iReturnCode
                    || PandoraAPIConstants.DEVICE_LOGIN_SUCCESS == iReturnCode) {
                iReturnCode = pandoraClient.getPlaylist(sStationTokenInput);
            }
        }
        return iReturnCode;
    }
    
    public ArrayList<Item> getPlaylistData() {
        return pandoraClient.getPlaylistData();
    }
    
    public int getAdMetadata(String sAdToken) {
        Logger.d();
        return pandoraClient.getAdMetadata(sAdToken);
    }
    
    public Item getAdMetadataData() {
        return pandoraClient.getAdMetadataData();
    }

    /**
     * 
     * @param sTrackToken sTrackToken
     * 
     * @return add artist bookmark return value
     */
    public int addArtistBookmark(String sTrackToken) {
        Logger.d();
        return pandoraClient.addArtistBookmark(sTrackToken);
    }

    /**
     * 
     * @param sTrackToken sTrackToken
     * 
     * @return add song bookmark return value
     */
    public int addSongBookmark(String sTrackToken) {
        Logger.d();
        return pandoraClient.addSongBookmark(sTrackToken);
    }
    
    /**
     * 
     * @param sTrackToken sTrackToken
     * 
     * @return add positive feedback return value
     */
    public int addPositiveFeedback(String sTrackToken) {
        Logger.d();
        return pandoraClient.addFeedback(sTrackToken, true);
    }

    /**
     * 
     * @param sTrackToken sTrackToken
     * 
     * @return add negative feedback return value
     */
    public int addNegativeFeedback(String sTrackToken) {
        Logger.d();
        return pandoraClient.addFeedback(sTrackToken, false);
    }
    
    /**
     * 
     * @param sTrackToken sTrackToken
     * 
     * @return sleep song return value
     */
    public int sleepSong(String sTrackToken) {
        Logger.d();
        return pandoraClient.sleepSong(sTrackToken);
    }

    /**
     * 
     * @param sTrackToken sTrackToken
     * 
     * @return ArrayList with ParcelExplanation
     */
    public int explainTrack(String sTrackToken) {
        Logger.d();
        return pandoraClient.explainTrack(sTrackToken);
    }
    
    public ArrayList<ParcelExplanation> getExplainTrackData() {
        return pandoraClient.getExplainTrackData();
    }

    /**
     * 
     * @param sSearchText searchText
     * 
     * @return ParcelSearchResult
     */
    public int musicSearch(String sSearchText) {
        Logger.d();
        return pandoraClient.musicSearch(sSearchText);
        /*
        ParcelSearchResult searchResult = null;
        try {
            searchResult = pandoraClient.musicSearch(sSearchText);
            if (searchResult != null) {
                Logger.d("Search Result");
                ArrayList<ParcelArtist> artistList = searchResult.getArtists();
                ArrayList<ParcelSong> songList = searchResult.getSongs();

                if (null != artistList) {
                    if (artistList.size() > 0) {
                        for (ParcelArtist artist : artistList) {
                            Logger.d("Artist Name = [" + artist.getArtistName() + "], Music Token = [" + artist.getMusicToken() + "]");
                        }
                    }
                } else {
                    Logger.d("artist is null");
                }

                if (null != songList) {
                    if (songList.size() > 0) {
                        for (ParcelSong song : songList) {
                            Logger.d("Song Name = [" + song.getSongName() + "], Music Token = [" + song.getMusicToken() + "]");
                        }
                    }
                } else {
                    Logger.d("songs is null");
                }
            }
        } catch (Exception e) {
            Logger.e(e);
        }
        return searchResult;
        */
    }
    
    public ParcelSearchResult getMusicSearchData() {
        return pandoraClient.getMusicSearchData();
    }
    
    public int musicSearchAutoComplete(String sSearchText) {
        Logger.d();
        return pandoraClient.musicSearchAutoComplete(sSearchText);
    }
    
    public ParcelSearchResult getMusicSearchAutoCompleteData() {
        return pandoraClient.getMusicSearchAutoCompleteData();
    }

    /**
     * 
     * @param sMusicToken sMusicToken
     * 
     * @return ParcelStation
     */
    public int createStation(String sMusicToken) {
        Logger.d();
        return pandoraClient.createStationByMusicToken(sMusicToken);
    }
    
    public ParcelStation getCreateStationData() {
        return pandoraClient.getCreateStationData();
    }

    /**
     * 
     * @param sAddress sAddress
     * 
     * @return Bitmap
     */
    public Bitmap downloadImage(String sAddress) {
        Logger.d();
        Bitmap downloadedBitmap = null;
        FileDownloader fileDownloader = new FileDownloader();
        try {
            downloadedBitmap = fileDownloader.downloadImage(sAddress);
            fileDownloader.disconnect();
        } catch (Exception e) {
            Logger.e(e);
        }
        return downloadedBitmap;
    }
    
    /**
     * 
     * @return MAC address
     */
    private String getDeviceIdFromDevice() {
        String sDeviceIdReturn = android.os.Build.ID;
        
        if ("CUPCAKE".equals(sDeviceIdReturn)) {
            String sUUIDDeviceId = sharedPreferences.getString("UUIDDeviceId", "");
            
            if ("".equals(sUUIDDeviceId)) {
                sUUIDDeviceId = UUID.randomUUID().toString();
                sharedPreferences.edit().putString("UUIDDeviceId", sUUIDDeviceId).commit();                
            }
            sDeviceIdReturn = sUUIDDeviceId;
            
            Logger.d("sUUIDDeviceId: [" + sUUIDDeviceId + "]");
        }
        
        Logger.d("sDeviceIdReturn: [" + sDeviceIdReturn + "]");
        return sDeviceIdReturn;
        
        /* // TODO old version use MAC as device id
        String sMACReturn = "00-11-22-33-44-55";
        //String sMACReturn = "00-55-44-21-33-12"; // TODO Author use

        try {
            File fileMAC = new File("/data/WLtest_1/MAC.txt");

            if (fileMAC.exists()) {
                InputStream is = new FileInputStream(fileMAC);
                long lFileLength = fileMAC.length();
                byte[] baMAC = new byte[(int) lFileLength];

                // Read in the bytes
                int offset = 0;
                int numRead = 0;
                while (offset < baMAC.length && 0 <= (numRead = is.read(baMAC, offset, baMAC.length - offset))) {
                    offset += numRead;
                }

                // Close the input stream and return bytes
                is.close();

                // Ensure all the bytes have been read in
                if (offset < baMAC.length) {
                    Logger.d("Could not completely read file: [" + fileMAC.getName() + "]");
                } else {
                    sMACReturn = "";
                    for (int i = 0; i < baMAC.length; i++) {
                        if (0 > baMAC[i]) {
                            sMACReturn += Integer.toHexString(baMAC[i] + 255).toUpperCase();
                        } else if (0 <= baMAC[i] && 10 > baMAC[i]) {
                            sMACReturn += "0" + Integer.toHexString(baMAC[i]);
                        } else {
                            sMACReturn += Integer.toHexString(baMAC[i]).toUpperCase();
                        }
                        if (i < baMAC.length - 1) {
                            sMACReturn += "-";
                        }
                    }
                }
            } else {
                Logger.d("/data/WLtest_1/MAC.txt does not exist.");
            }
        } catch (Exception e) {
            Logger.e(e);
        }

        Logger.d("sMACReturn: [" + sMACReturn + "]");
        return sMACReturn;
        */
    }

    /**
     * Save DeviceId to file if the device is associated.
     * 
     * @param sDeviceId deviceId
     */
    private void saveDeviceIdToPreference(String sDeviceId) {
        Logger.d("sDeviceId: [" + sDeviceId + "]");
        sharedPreferences.edit().putString("deviceId", sDeviceId).commit();
    }

    /**
     * If device is associated, the deviceId is need to be obtained for the subsequent use.
     * 
     * @return device id
     */
    private String getDeviceIdFromPreference() {
        String sDeviceId = sharedPreferences.getString("deviceId", "");
        Logger.d("sDeviceId: [" + sDeviceId + "]");
        return sDeviceId;
    }

    /**
     * Remove device id from the preference data if the device is disassociated.
     */
    private void removeDeviceIdFromPreference() {
        Logger.d();
        boolean isCommitSuccess = sharedPreferences.edit().remove("deviceId").commit();
        if (!isCommitSuccess) {
            Logger.e("Commit preference data failed.");
        }
    }

}
