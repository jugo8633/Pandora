package comp.hp.ij.common.service.pandora.data;

import java.util.ArrayList;

import comp.hp.ij.common.service.pandora.api.Item;
import comp.hp.ij.common.service.v2.base.IDataV2;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class PPandora implements IDataV2 {
    
    private int iLoginReturn = -1;
    
    private int iNowPlayStationIndex = -1;
    private String sNowPlayStationToken = null;
    
    private int iGetStationListReturn = -1;
    private ArrayList<ParcelStation> alParcelStationList = null;
    
    private int iGetPlaylistReturn = -1;
    private ArrayList<Item> alPlaylist = null;
    
    private int iMusicSearchReturn = -1;
	private ParcelSearchResult parcelMusicSearchResult;
    private int iMusicSearchAutoCompleteReturn = -1;
    private ParcelSearchResult parcelMusicSearchAutoCompleteResult;
	private int iCreateStationReturn = -1;
	private ParcelStation createdParcelStation;
	private int isAssociationResult;
	private int iGenerateDeviceActivationCodeReturn;
	private ParcelDeviceActivationData parcelDeviceActivationData;
	private int iDoDeviceLoginReturn = -1;
	private Bitmap downloadedImage;
	private int logoutReturn = -1;
	private int addFeedbackReturn = -1;
	private int deleteStationReturn = -1;
	private boolean[] isDeleteNowPlayStation = new boolean[1];
	private int addArtistBookmarkReturn = -1;
	private int addSongBookmarkReturn = -1;
	private int sleepSongReturn = -1;
	private int iExplainTrackReturn = -1;
	private ArrayList<ParcelExplanation> alExplanations = null;
	private int disassociateDeviceReturn = -1;
    private int iNowPlayIndex    = -1;
    
    private int iTotalDuration   = -1;
    private int iCurrentPosition = -1;
    private int iStopMusicStatus = -1;
    
    private boolean[] isReachSkipLimit = new boolean[1];
    
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(iLoginReturn);
        out.writeInt(iNowPlayStationIndex);
        out.writeString(sNowPlayStationToken);
        out.writeInt(iGetStationListReturn);
        out.writeList(alParcelStationList);
        out.writeInt(iGetPlaylistReturn);
        out.writeList(alPlaylist);
        out.writeInt(iMusicSearchReturn);
        out.writeParcelable(parcelMusicSearchResult, flags);
        out.writeInt(iMusicSearchAutoCompleteReturn);
        out.writeParcelable(parcelMusicSearchAutoCompleteResult, flags);
        out.writeInt(iCreateStationReturn);
        out.writeParcelable(createdParcelStation, flags);
        out.writeInt(isAssociationResult);
        out.writeInt(iGenerateDeviceActivationCodeReturn);
        out.writeParcelable(parcelDeviceActivationData, flags);
        out.writeInt(iDoDeviceLoginReturn);
        out.writeParcelable(downloadedImage, flags);
        out.writeInt(logoutReturn);
        out.writeInt(addFeedbackReturn);
		out.writeInt(deleteStationReturn);
		out.writeBooleanArray(isDeleteNowPlayStation);
		out.writeInt(addArtistBookmarkReturn);
		out.writeInt(addSongBookmarkReturn);
		out.writeInt(sleepSongReturn);
		out.writeInt(iExplainTrackReturn);
		out.writeList(alExplanations);
		out.writeInt(disassociateDeviceReturn);
        out.writeInt(iNowPlayIndex);
        out.writeInt(iTotalDuration);
        out.writeInt(iCurrentPosition);
        out.writeInt(iStopMusicStatus);
        out.writeBooleanArray(isReachSkipLimit);
    }
    
    @SuppressWarnings("unchecked")
    public void readFromParcel(Parcel in) {
        iLoginReturn = in.readInt();
        iNowPlayStationIndex = in.readInt();
        sNowPlayStationToken = in.readString();
        iGetStationListReturn = in.readInt();
        alParcelStationList = in.readArrayList(getClass().getClassLoader());
        iGetPlaylistReturn = in.readInt();
        alPlaylist = in.readArrayList(getClass().getClassLoader());
        iMusicSearchReturn = in.readInt();
        parcelMusicSearchResult = in.readParcelable(getClass().getClassLoader());
        iMusicSearchAutoCompleteReturn = in.readInt();
        parcelMusicSearchAutoCompleteResult = in.readParcelable(getClass().getClassLoader());
        iCreateStationReturn = in.readInt();
        createdParcelStation = in.readParcelable(getClass().getClassLoader());
        isAssociationResult = in.readInt();
        iGenerateDeviceActivationCodeReturn = in.readInt();
        parcelDeviceActivationData = in.readParcelable(getClass().getClassLoader());
        iDoDeviceLoginReturn = in.readInt();
        downloadedImage = in.readParcelable(getClass().getClassLoader());
        logoutReturn = in.readInt();
        addFeedbackReturn = in.readInt();
        deleteStationReturn = in.readInt();
        in.readBooleanArray(isDeleteNowPlayStation);
        addArtistBookmarkReturn = in.readInt();
        addSongBookmarkReturn = in.readInt();
        sleepSongReturn = in.readInt();
        iExplainTrackReturn = in.readInt();
        alExplanations = in.readArrayList(getClass().getClassLoader());
        disassociateDeviceReturn = in.readInt();
        iNowPlayIndex = in.readInt();
        iTotalDuration = in.readInt();
        iCurrentPosition = in.readInt();
        iStopMusicStatus = in.readInt();
        in.readBooleanArray(isReachSkipLimit);
    }

    public static final Parcelable.Creator<PPandora> CREATOR = new Parcelable.Creator<PPandora>() {       
        public PPandora createFromParcel(Parcel in) {
            return new PPandora(in);
        }

        public PPandora[] newArray(int size) {
            return new PPandora[size];
        }
    };
    
    @SuppressWarnings("unchecked")
    public PPandora(Parcel in) {
        iLoginReturn = in.readInt();
        iNowPlayStationIndex = in.readInt();
        sNowPlayStationToken = in.readString();
        iGetStationListReturn = in.readInt();
        alParcelStationList = in.readArrayList(getClass().getClassLoader());
        iGetPlaylistReturn = in.readInt();
        alPlaylist = in.readArrayList(getClass().getClassLoader());
        iMusicSearchReturn = in.readInt();
        parcelMusicSearchResult = in.readParcelable(getClass().getClassLoader());
        iMusicSearchAutoCompleteReturn = in.readInt();
        parcelMusicSearchAutoCompleteResult = in.readParcelable(getClass().getClassLoader());
        iCreateStationReturn = in.readInt();
        createdParcelStation = in.readParcelable(getClass().getClassLoader());
        isAssociationResult = in.readInt();
        iGenerateDeviceActivationCodeReturn = in.readInt();
        parcelDeviceActivationData = in.readParcelable(getClass().getClassLoader());
        iDoDeviceLoginReturn = in.readInt();
        downloadedImage = in.readParcelable(getClass().getClassLoader());
        logoutReturn = in.readInt();
        addFeedbackReturn = in.readInt();
        deleteStationReturn = in.readInt();
        in.readBooleanArray(isDeleteNowPlayStation);
        addArtistBookmarkReturn = in.readInt();
        addSongBookmarkReturn = in.readInt();
        sleepSongReturn = in.readInt();
        iExplainTrackReturn = in.readInt();
        alExplanations = in.readArrayList(getClass().getClassLoader());
        disassociateDeviceReturn = in.readInt();
        iNowPlayIndex = in.readInt();
        iTotalDuration = in.readInt();
        iCurrentPosition = in.readInt();
        iStopMusicStatus = in.readInt();
        in.readBooleanArray(isReachSkipLimit);
    }
    
    public PPandora() {
    }
    
    public void setDoLoginReturn(int iDoLoginInput) {
        iLoginReturn = iDoLoginInput;
    }
    public int getDoLoginReturn() {
        return iLoginReturn;
    }
    
    public void setNowPlayStationIndex(int iNowPlayStationIndexInput) {
        iNowPlayStationIndex = iNowPlayStationIndexInput;
    }
    public int getNowPlayStationIndex() {
        return iNowPlayStationIndex;
    }
    
    public void setNowPlayStationToken(String sNowPlayStationTokenInput) {
        sNowPlayStationToken = sNowPlayStationTokenInput;
    }
    public String getNowPlayStationToken() {
        return sNowPlayStationToken;
    }
    
    public void setGetStationListReturn(int iGetStationListReturnInput) {
        iGetStationListReturn = iGetStationListReturnInput;
    }
    public int getGetStationListReturn() {
        return iGetStationListReturn;
    }
    
    public void setStationList(ArrayList<ParcelStation> alParcelStationListInput) {
        alParcelStationList = alParcelStationListInput;
    }
    public ArrayList<ParcelStation> getStationList() {
        return alParcelStationList;
    }
    
    public void setGetPlaylistReturn(int iGetPlaylistReturnInput) {
        iGetPlaylistReturn = iGetPlaylistReturnInput;
    }
    public int getGetPlaylistReturn() {
        return iGetPlaylistReturn;
    }
    
    public void setPlaylist(ArrayList<Item> alPlaylistInput) {
        alPlaylist = alPlaylistInput;
    }
    public ArrayList<Item> getPlaylist() {
        return alPlaylist;
    }
    
    public void setNowPlayIndex(int iNowPlayIndexInput) {
        iNowPlayIndex = iNowPlayIndexInput;
    }
    public int getNowPlayIndex() {
        return iNowPlayIndex;
    }
    
    public void setTotalDuration(int iTotalDurationInput) {
        iTotalDuration = iTotalDurationInput;
    }
    public int getTotalDuration() {
        return iTotalDuration;
    }
    
    public void setCurrentPosition(int iCurrentPositionInput) {
        iCurrentPosition = iCurrentPositionInput;
    }
    public int getCurrentPosition() {
        return iCurrentPosition;
    }
    
    public void setStopMusicStatus(int iStopMusicStatusInput) {
        iStopMusicStatus = iStopMusicStatusInput;
    }
    public int getStopMusicStatus() {
        return iStopMusicStatus;
    }

	public int getIsAssociationResult() {
		return isAssociationResult;
	}
	public void setIsAssociationResult(int isAssociationResultInput) {
		isAssociationResult = isAssociationResultInput;
	}
	
	public int getGenerateActivationCodeReturn() {
	    return iGenerateDeviceActivationCodeReturn;
	}
	public void setGenerateActivationCodeReturn(int iGenerateDeviceActivationCodeReturnInput) {
        iGenerateDeviceActivationCodeReturn = iGenerateDeviceActivationCodeReturnInput;
    }
	
	public int getDoDeviceLoginReturn() {
	    return iDoDeviceLoginReturn;
	}
	public void setDoDeviceLoginReturn(int iDoDeviceLoginReturnInput) {
	    iDoDeviceLoginReturn = iDoDeviceLoginReturnInput;
	}

	public ParcelDeviceActivationData getDeviceActivationData() {
		return parcelDeviceActivationData;
	}
	public void setDeviceActivationData(ParcelDeviceActivationData parcelDeviceActivationDataInput) {
		parcelDeviceActivationData = parcelDeviceActivationDataInput;
	}

	public int getLogoutReturn() {
		return logoutReturn;
	}
	public void setLogoutReturn(int logoutReturn) {
		this.logoutReturn = logoutReturn;
	}
	
	public int getMusicSearchReturn() {
	    return iMusicSearchReturn;
	}
	public void setMusicSearchReturn(int iMusicSearchReturnInput) {
	    iMusicSearchReturn = iMusicSearchReturnInput;
	}
	
	public ParcelSearchResult getMusicSearchResult() {
		return parcelMusicSearchResult;
	}
	public void setMusicSearchResult(ParcelSearchResult searchResult) {
		this.parcelMusicSearchResult = searchResult;
	}
	
	public int getMusicSearchAutoCompleteReturn() {
	    return iMusicSearchAutoCompleteReturn;
	}
	public void setMusicSearchAutoCompleteReturn(int iMusicSearchReturnAutoCompleteInput) {
	    iMusicSearchAutoCompleteReturn = iMusicSearchReturnAutoCompleteInput;
	}
	
	public ParcelSearchResult getMusicSearchAutoCompleteResult() {
	    return parcelMusicSearchAutoCompleteResult;
	}
	public void setMusicSearchAutoCompleteResult(ParcelSearchResult searchResult) {
	    this.parcelMusicSearchAutoCompleteResult = searchResult;
	}
	
	public int getCreateStationReturn() {
	    return iCreateStationReturn;
	}
	public void setCreateStationReturn(int iCreateStationReturnInput) {
	    iCreateStationReturn = iCreateStationReturnInput;
	}

	public ParcelStation getCreatedParcelStation() {
		return createdParcelStation;
	}

	public void setCreatedParcelStation(ParcelStation createdParcelStation) {
		this.createdParcelStation = createdParcelStation;
	}

	public int getAddFeedbackReturn() {
		return addFeedbackReturn;
	}

	public void setAddFeedbackReturn(int addFeedbackReturn) {
		this.addFeedbackReturn = addFeedbackReturn;
	}

	public int getDeleteStationReturn() {
		return deleteStationReturn;
	}
	public void setDeleteStationReturn(int deleteStationReturnInput) {
		deleteStationReturn = deleteStationReturnInput;
	}
	
	public boolean getIsDeleteNowPlayStation() {
	    return isDeleteNowPlayStation[0];
	}
	public void setIsDeleteNowPlayStation(boolean isDeleteNowPlayStationInput) {
	    isDeleteNowPlayStation[0] = isDeleteNowPlayStationInput;
	}

	public int getAddArtistBookmarkReturn() {
		return addArtistBookmarkReturn;
	}

	public void setAddArtistBookmarkReturn(int addArtistBookmarkReturn) {
		this.addArtistBookmarkReturn = addArtistBookmarkReturn;
	}

	public int getAddSongBookmarkReturn() {
		return addSongBookmarkReturn;
	}

	public void setAddSongBookmarkReturn(int addSongBookmarkReturn) {
		this.addSongBookmarkReturn = addSongBookmarkReturn;
	}

	public int getSleepSongReturn() {
		return sleepSongReturn;
	}

	public void setSleepSongReturn(int sleepSongReturn) {
		this.sleepSongReturn = sleepSongReturn;
	}
	
	public int getExplainTrackReturn() {
	    return iExplainTrackReturn;
	}
	
	public void setExplainTrackReturn(int iExplainTrackReturnInput) {
	    iExplainTrackReturn = iExplainTrackReturnInput;
	}

	public ArrayList<ParcelExplanation> getExplanations() {
		return alExplanations;
	}

	public void setExplanations(ArrayList<ParcelExplanation> explanations) {
		this.alExplanations = explanations;
	}

	public Bitmap getDownloadedImage() {
		return downloadedImage;
	}

	public void setDownloadedImage(Bitmap downloadedImage) {
		this.downloadedImage = downloadedImage;
	}

	public boolean getIsReachSkipLimit() {
	    return isReachSkipLimit[0];
	}
	
	public void setIsReachSkipLimit(boolean isReachSkipLimitInput) {
	    isReachSkipLimit[0] = isReachSkipLimitInput;
	}
}
