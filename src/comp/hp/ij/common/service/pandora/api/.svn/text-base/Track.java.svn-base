package comp.hp.ij.common.service.pandora.api;
/**
 *  Object representation of Track attributes
 *
 *  Not all available Track attributes have been mapped here.  See the API docs for the full list of attributes.
 */
public class Track implements Item {

	private String songName;
	private String artistName;
	private String audioUrl;

	public Track(Result trackResult) {
		songName = (String) trackResult.getString("songName");
		artistName = (String) trackResult.getString("artistName");
		audioUrl = (String) trackResult.getString("audioUrl");
	}

	public String getSongName() {
		return songName;
	}

	public String getArtistName() {
		return artistName;
	}

	public String getAudioUrl() {
		return audioUrl;
	}
}

