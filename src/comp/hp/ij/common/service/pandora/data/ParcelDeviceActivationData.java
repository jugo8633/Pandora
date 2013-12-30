package comp.hp.ij.common.service.pandora.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelDeviceActivationData implements Parcelable {
    
	private String activationCode;
    private String activationUrl;
    
    public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public String getActivationUrl() {
		return activationUrl;
	}

	public void setActivationUrl(String activationUrl) {
		this.activationUrl = activationUrl;
	}

    public int describeContents() {
        return 0;
    }
    
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(activationCode);
        out.writeString(activationUrl);
    }
    
    public void readFromParcel(Parcel in) {
        activationCode = in.readString();
        activationUrl = in.readString();
    }
    
    public static final Parcelable.Creator<ParcelDeviceActivationData> CREATOR = new Parcelable.Creator<ParcelDeviceActivationData>() {       
        public ParcelDeviceActivationData createFromParcel(Parcel in) {           
            return new ParcelDeviceActivationData(in);
        }

        public ParcelDeviceActivationData[] newArray(int size) {
            return new ParcelDeviceActivationData[size];
        }
    };
    
    public ParcelDeviceActivationData(Parcel in) {
        activationCode = in.readString();
        activationUrl = in.readString();
    }
    
    public ParcelDeviceActivationData() {}
   
}
