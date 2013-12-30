package comp.hp.ij.common.service.pandora.api;
/**
 *  Object representation of Station attributes
 *
 *  Not all available Station attributes have been mapped here.  See the API docs for the full list of attributes.
 */
public class Station {

	private String stationName;
	private String stationToken;

	public Station(Result stationResult) {
		stationName = stationResult.getString("stationName");
		stationToken = stationResult.getString("stationToken");
	}

	public String getStationName() {
		return stationName;
	}

	public String getStationToken() {
		return stationToken;
	}
}
