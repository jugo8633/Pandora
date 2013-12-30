package comp.hp.ij.common.service.pandora.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelSong implements Parcelable {
    
	private String songName;
    private String artistName;
    private String musicToken;
    private String score;

    public int describeContents() {
        return 0;
    }
    
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(songName);
        out.writeString(artistName);
        out.writeString(musicToken);
        out.writeString(score);
    }
    
    public void readFromParcel(Parcel in) {
        songName = in.readString();
        artistName = in.readString();
        musicToken = in.readString();
        score = in.readString();
    }
    
    public static final Parcelable.Creator<ParcelSong> CREATOR = new Parcelable.Creator<ParcelSong>() {       
        public ParcelSong createFromParcel(Parcel in) {           
            return new ParcelSong(in);
        }

        public ParcelSong[] newArray(int size) {
            return new ParcelSong[size];
        }
    };
    
    public ParcelSong(Parcel in) {
        songName = in.readString();
        artistName = in.readString();
        musicToken = in.readString();
        score = in.readString();
    }
    
    public ParcelSong() {}

	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}

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
   
}
