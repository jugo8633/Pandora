package comp.hp.ij.common.service.pandora.data;

import comp.hp.ij.common.service.pandora.api.Result;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelStation implements Parcelable {
    
    private boolean[] allowAddMusic = new boolean[1];
    private boolean[] allowRename = new boolean[1];
    private String stationToken;
    private boolean[] allowDelete = new boolean[1];
    private String dateCreated;
    private boolean[] isQuickMix = new boolean[1];
    private boolean[] isShared = new boolean[1];
    private String stationId;
    private String stationName;
    
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeBooleanArray(allowAddMusic);
        out.writeBooleanArray(allowRename);
        out.writeString(stationToken);
        out.writeBooleanArray(allowDelete);
        out.writeString(dateCreated);
        out.writeBooleanArray(isQuickMix);
        out.writeBooleanArray(isShared);
        out.writeString(stationId);
        out.writeString(stationName);
    }
    
    public void readFromParcel(Parcel in) {
        in.readBooleanArray(allowAddMusic);
        in.readBooleanArray(allowRename);
        stationToken = in.readString();
        in.readBooleanArray(allowDelete);
        dateCreated = in.readString();
        in.readBooleanArray(isQuickMix);
        in.readBooleanArray(isShared);
        stationId = in.readString();
        stationName = in.readString();
    }

    public static final Parcelable.Creator<ParcelStation> CREATOR = new Parcelable.Creator<ParcelStation>() {       
        public ParcelStation createFromParcel(Parcel in) {
            return new ParcelStation(in);
        }

        public ParcelStation[] newArray(int size) {
            return new ParcelStation[size];
        }
    };
    
    public ParcelStation(Parcel in) {
        in.readBooleanArray(allowAddMusic);
        in.readBooleanArray(allowRename);
        stationToken = in.readString();
        in.readBooleanArray(allowDelete);
        dateCreated = in.readString();
        in.readBooleanArray(isQuickMix);
        in.readBooleanArray(isShared);
        stationId = in.readString();
        stationName = in.readString();
    }
    
    public ParcelStation() {
    }
    
    public void setResult(Result resultStation) {
        allowAddMusic[0] = resultStation.getBoolean("allowAddMusic");
        allowRename[0] = resultStation.getBoolean("allowRename");
        stationToken = resultStation.getString("stationToken");
        allowDelete[0] = resultStation.getBoolean("allowDelete");
        dateCreated = resultStation.getString("dateCreated");
        isQuickMix[0] = resultStation.getBoolean("isQuickMix");
        isShared[0] = resultStation.getBoolean("isShared");
        stationId = resultStation.getString("stationId");
        stationName = resultStation.getString("stationName");
    }
    
    public boolean isAllowAddMusic() {
        return allowAddMusic[0];
    }
    
    public boolean isAllowRename() {
        return allowRename[0];
    }
    
    public String getStationToken() {
        return stationToken;
    }
    
    public boolean isAllowDelete() {
        return allowDelete[0];
    }

    public String getDateCreated() {
        return dateCreated;
    }
    
    public boolean isQuickMix() {
        return isQuickMix[0];
    }
    
    public boolean isShared() {
        return isShared[0];
    }
    
    public String getStationId() {
        return stationId;
    }
    
    public String getStationName() {
        return stationName;
    }
}
