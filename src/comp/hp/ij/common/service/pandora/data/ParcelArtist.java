package comp.hp.ij.common.service.pandora.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelArtist implements Parcelable {
    
	private String likelyMatch;
    private String artistName;
    private String musicToken;
    private String score;

    public int describeContents() {
        return 0;
    }
    
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(likelyMatch);
        out.writeString(artistName);
        out.writeString(musicToken);
        out.writeString(score);
    }
    
    public void readFromParcel(Parcel in) {
        likelyMatch = in.readString();
        artistName = in.readString();
        musicToken = in.readString();
        score = in.readString();
    }
    
    public static final Parcelable.Creator<ParcelArtist> CREATOR = new Parcelable.Creator<ParcelArtist>() {       
        public ParcelArtist createFromParcel(Parcel in) {           
            return new ParcelArtist(in);
        }

        public ParcelArtist[] newArray(int size) {
            return new ParcelArtist[size];
        }
    };
    
    public ParcelArtist(Parcel in) {
        likelyMatch = in.readString();
        artistName = in.readString();
        musicToken = in.readString();
        score = in.readString();
    }
    
    public ParcelArtist() {}

	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getMusicToken() {
		return musicToken;
	}
	public void setMusicToken(String musicToken) {
		this.musicToken = musicToken;
	}

	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}

	public String getLikelyMatch() {
		return likelyMatch;
	}
	public void setLikelyMatch(String likelyMatch) {
		this.likelyMatch = likelyMatch;
	}
   
}
