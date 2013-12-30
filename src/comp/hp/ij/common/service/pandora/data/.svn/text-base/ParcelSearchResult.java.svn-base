package comp.hp.ij.common.service.pandora.data;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelSearchResult implements Parcelable {

	private String nearMatchesAvailable;
	private ArrayList<ParcelSong> songs;
	private ArrayList<ParcelArtist> artists;

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeString(nearMatchesAvailable);
		out.writeList(songs);
		out.writeList(artists);
	}

	@SuppressWarnings("unchecked")
    public void readFromParcel(Parcel in) {
		nearMatchesAvailable = in.readString();
		songs = in.readArrayList(getClass().getClassLoader());
		artists = in.readArrayList(getClass().getClassLoader());
	}

	public static final Parcelable.Creator<ParcelSearchResult> CREATOR = new Parcelable.Creator<ParcelSearchResult>() {
		public ParcelSearchResult createFromParcel(Parcel in) {
			return new ParcelSearchResult(in);
		}

		public ParcelSearchResult[] newArray(int size) {
			return new ParcelSearchResult[size];
		}
	};

	@SuppressWarnings("unchecked")
	public ParcelSearchResult(Parcel in) {
		nearMatchesAvailable = in.readString();
		songs = in.readArrayList(getClass().getClassLoader());
		artists = in.readArrayList(getClass().getClassLoader());
	}

	public ParcelSearchResult() {
	}

	public String getNearMatchesAvailable() {
		return nearMatchesAvailable;
	}
	public void setNearMatchesAvailable(String nearMatchesAvailable) {
		this.nearMatchesAvailable = nearMatchesAvailable;
	}

	public ArrayList<ParcelSong> getSongs() {
		return songs;
	}
	public void setSongs(ArrayList<ParcelSong> songs) {
		this.songs = songs;
	}

	public ArrayList<ParcelArtist> getArtists() {
		return artists;
	}
	public void setArtists(ArrayList<ParcelArtist> artists) {
		this.artists = artists;
	}

}
