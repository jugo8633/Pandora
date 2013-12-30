package comp.hp.ij.common.service.pandora.data;

import comp.hp.ij.common.service.pandora.api.Item;
import comp.hp.ij.common.service.pandora.api.Result;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelTrack implements Item, Parcelable {
    
    private String albumArtUrl;
    private String albumName;
    private boolean[] allowFeedback = new boolean[1];
    private String albumDetailUrl;
    private String amazonAlbumAsin;
    private String amazonAlbumDigitalAsin;
    private String amazonAlbumUrl;
    private String amazonSongDigitalAsin;
    private String artistDetailUrl;
    private String artistName;
    private String audioUrl;
    // Map audioUrlMap
    private String itunesSongUrl;
    private String songRating;
    private String newStationMessage;
    private String songName;
    private String songDetailUrl;
    private String stationId;
    private String trackGain;
    private String trackToken;

    public int describeContents() {
        return 0;
    }
    
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(albumArtUrl);
        out.writeString(albumName);
        out.writeBooleanArray(allowFeedback);
        out.writeString(albumDetailUrl);
        out.writeString(amazonAlbumAsin);
        out.writeString(amazonAlbumDigitalAsin);
        out.writeString(amazonAlbumUrl);
        out.writeString(amazonSongDigitalAsin);
        out.writeString(artistDetailUrl);
        out.writeString(artistName);
        out.writeString(audioUrl);
        out.writeString(itunesSongUrl);
        out.writeString(songRating);
        out.writeString(newStationMessage);
        out.writeString(songName);
        out.writeString(songDetailUrl);
        out.writeString(stationId);
        out.writeString(trackGain);
        out.writeString(trackToken);
    }
    
    public void readFromParcel(Parcel in) {
        albumArtUrl = in.readString();
        albumName = in.readString();
        in.readBooleanArray(allowFeedback);
        albumDetailUrl = in.readString();
        amazonAlbumAsin = in.readString();
        amazonAlbumDigitalAsin = in.readString();
        amazonAlbumUrl = in.readString();
        amazonSongDigitalAsin = in.readString();
        artistDetailUrl = in.readString();
        artistName = in.readString();
        audioUrl = in.readString();
        itunesSongUrl = in.readString();
        songRating = in.readString();
        newStationMessage = in.readString();
        songName = in.readString();
        songDetailUrl = in.readString();
        stationId = in.readString();
        trackGain = in.readString();
        trackToken = in.readString();
    }
    
    public static final Parcelable.Creator<ParcelTrack> CREATOR = new Parcelable.Creator<ParcelTrack>() {       
        public ParcelTrack createFromParcel(Parcel in) {
            return new ParcelTrack(in);
        }

        public ParcelTrack[] newArray(int size) {
            return new ParcelTrack[size];
        }
    };
    
    public ParcelTrack(Parcel in) {
        albumArtUrl = in.readString();
        albumName = in.readString();
        in.readBooleanArray(allowFeedback);
        albumDetailUrl = in.readString();
        amazonAlbumAsin = in.readString();
        amazonAlbumDigitalAsin = in.readString();
        amazonAlbumUrl = in.readString();
        amazonSongDigitalAsin = in.readString();
        artistDetailUrl = in.readString();
        artistName = in.readString();
        audioUrl = in.readString();
        itunesSongUrl = in.readString();
        songRating = in.readString();
        newStationMessage = in.readString();
        songName = in.readString();
        songDetailUrl = in.readString();
        stationId = in.readString();
        trackGain = in.readString();
        trackToken = in.readString();
    }
    
    public ParcelTrack() {}
    
    public void setResult(Result resultItem) {
        albumArtUrl = resultItem.getString("albumArtUrl");
        albumName = resultItem.getString("albumName");
        allowFeedback[0] = resultItem.getBoolean("allowFeedback");
        albumDetailUrl = resultItem.getString("albumDetailUrl");
        amazonAlbumAsin = resultItem.getString("amazonAlbumAsin");
        amazonAlbumDigitalAsin = resultItem.getString("amazonAlbumDigitalAsin");
        amazonAlbumUrl = resultItem.getString("amazonAlbumUrl");
        amazonSongDigitalAsin = resultItem.getString("amazonSongDigitalAsin");
        artistDetailUrl = resultItem.getString("artistDetailUrl");
        artistName = resultItem.getString("artistName");
        audioUrl = resultItem.getString("audioUrl");
        itunesSongUrl = resultItem.getString("itunesSongUrl");
        songRating = resultItem.getString("songRating");
        newStationMessage = resultItem.getString("newStationMessage");
        songName = resultItem.getString("songName");
        songDetailUrl = resultItem.getString("songDetailUrl");
        stationId = resultItem.getString("stationId");
        trackGain = resultItem.getString("trackGain");
        trackToken = resultItem.getString("trackToken");
    }
    
    public String getAlbumArtUrl() {
        return albumArtUrl;
    }
    
    public String getAlbumName() {
        return albumName;
    }
    
    /**
     * @return the allowFeedback
     */
    public boolean isAllowFeedback() {
        return allowFeedback[0];
    }

    /**
     * @return the albumDetailUrl
     */
    public String getAlbumDetailUrl() {
        return albumDetailUrl;
    }

    /**
     * @return the amazonAlbumAsin
     */
    public String getAmazonAlbumAsin() {
        return amazonAlbumAsin;
    }

    /**
     * @return the amazonAlbumDigitalAsin
     */
    public String getAmazonAlbumDigitalAsin() {
        return amazonAlbumDigitalAsin;
    }

    /**
     * @return the amazonAlbumUrl
     */
    public String getAmazonAlbumUrl() {
        return amazonAlbumUrl;
    }

    /**
     * @return the amazonSongDigitalAsin
     */
    public String getAmazonSongDigitalAsin() {
        return amazonSongDigitalAsin;
    }

    /**
     * @return the artistDetailUrl
     */
    public String getArtistDetailUrl() {
        return artistDetailUrl;
    }

    /**
     * @return the artistName
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * @return the audioUrl
     */
    public String getAudioUrl() {
        return audioUrl;
    }

    /**
     * @return the itunesSongUrl
     */
    public String getItunesSongUrl() {
        return itunesSongUrl;
    }

    /**
     * @return the songRating
     */
    public String getSongRating() {
        return songRating;
    }
    public void setSongRating(String sSongRatingInput) {
        songRating = sSongRatingInput;
    }

    /**
     * @return the newStationMessage
     */
    public String getNewStationMessage() {
        return newStationMessage;
    }

    /**
     * @return the songName
     */
    public String getSongName() {
        return songName;
    }

    /**
     * @return the songDetailUrl
     */
    public String getSongDetailUrl() {
        return songDetailUrl;
    }

    /**
     * @return the stationId
     */
    public String getStationId() {
        return stationId;
    }

    /**
     * @return the trackGain
     */
    public String getTrackGain() {
        return trackGain;
    }

    /**
     * @return the trackToken
     */
    public String getTrackToken() {
        return trackToken;
    }
}
