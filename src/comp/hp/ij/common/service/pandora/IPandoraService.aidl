package comp.hp.ij.common.service.pandora;

interface IPandoraService {
    void registerClient(int iActivityHashCode);
    void unregisterClient(int iActivityHashCode);
    
    void resetIdleAlarmTimer();

    int  isAssociated(int iActivityHashCode, String sUUID);
    int  doLogout(int iActivityHashCode, String sUUID);
    
    int  generateActivitionCode(int iActivityHashCode, String sUUID);
    int  doDeviceLogin(int iActivityHashCode, String sUUID);
    
    void setLoginAuth(String sUsername, String sPassword);
    int  doLogin(int iActivityHashCode, String sUUID);
    
    int  getNowPlayStationData(int iActivityHashCode, String sUUID);
    
    int  getStationList(int iActivityHashCode, String sUUID);
    int  deleteStation(int iActivityHashCode, String sUUID, String sStationToken);
    
    int  getPlaylist(int iActivityHashCode, String sUUID, String sStationToken);
    int  updatePlaylist(int iActivityHashCode, String sUUID);
    int  downloadImage(int iActivityHashCode, String sUUID, String sAlbumArtUrl);
    
    int  pauseMusic(int iActivityHashCode, String sUUID);
    int  resumeMusic(int iActivityHashCode, String sUUID);
    int  skipSong(int iActivityHashCode, String sUUID);
    int  stopMusic(int iActivityHashCode, String sUUID);
    
    int  addArtistBookmark(int iActivityHashCode, String sUUID, String sTrackToken);
    int  addSongBookmark(int iActivityHashCode, String sUUID, String sTrackToken);
    int  addPostiveFeedback(int iActivityHashCode, String sUUID, String sTrackToken);
    int  addNegativeFeedback(int iActivityHashCode, String sUUID, String sTrackToken);
    int  sleepSong(int iActivityHashCode, String sUUID, String sTrackToken);
    int  explainTrack(int iActivityHashCode, String sUUID, String sTrackToken);
    
    int  musicSearch(int iActivityHashCode, String sUUID, String sSearchText);
    int  musicSearchAutoComplete(int iActivityHashCode, String sUUID, String sSearchText);
    int  createStation(int iActivityHashCode, String sUUID, String sMusicToken);
}