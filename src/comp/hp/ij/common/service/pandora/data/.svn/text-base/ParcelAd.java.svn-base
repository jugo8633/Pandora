package comp.hp.ij.common.service.pandora.data;

import android.os.Parcel;
import android.os.Parcelable;

import comp.hp.ij.common.service.pandora.api.Item;
import comp.hp.ij.common.service.pandora.api.Result;

public class ParcelAd implements Item, Parcelable {
    
    private String adToken = "";
    private String audioUrl = "";
    private String companyName = "";
    private String clickThroughUrl = "";
    private String imageUrl = "";
    private String title = "";

    public int describeContents() {
        return 0;
    }
    
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(adToken);
        out.writeString(audioUrl);
        out.writeString(companyName);
        out.writeString(clickThroughUrl);
        out.writeString(imageUrl);
        out.writeString(title);
    }
    
    public void readFromParcel(Parcel in) {
        adToken = in.readString();
        audioUrl = in.readString();
        companyName = in.readString();
        clickThroughUrl = in.readString();
        imageUrl = in.readString();
        title = in.readString();
    }
    
    public static final Parcelable.Creator<ParcelAd> CREATOR = new Parcelable.Creator<ParcelAd>() {       
        public ParcelAd createFromParcel(Parcel in) {
            return new ParcelAd(in);
        }

        public ParcelAd[] newArray(int size) {
            return new ParcelAd[size];
        }
    };
    
    public ParcelAd(Parcel in) {
        adToken = in.readString();
        audioUrl = in.readString();
        companyName = in.readString();
        clickThroughUrl = in.readString();
        imageUrl = in.readString();
        title = in.readString();
    }
    
    public ParcelAd() {}
    
    public void setResult(Result resultItem) {
        audioUrl = resultItem.getString("audioUrl");
        companyName = resultItem.getString("companyName");
        clickThroughUrl = resultItem.getString("clickThroughUrl");
        imageUrl = resultItem.getString("imageUrl");
        title = resultItem.getString("title");
    }

    public String getAdToken() {
        return adToken;
    }
    public void setAdToken(Result resultItem) {
        adToken = resultItem.getString("adToken");
    }
    public void setAdToken(String sAdTokenInput) {
        adToken = sAdTokenInput;
    }
    
    public String getAudioUrl() {
        return audioUrl;
    }
    public void setAudioUrl(String sAudioUrlInput) {
        audioUrl = sAudioUrlInput;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String sCompanyNameInput) {
        companyName = sCompanyNameInput;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String sImageUrlInput) {
        imageUrl = sImageUrlInput;
    }
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String sTitleInput) {
        title = sTitleInput;
    }

}
