package comp.hp.ij.common.service.pandora.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelExplanation implements Parcelable {

	private String focusTraitName;
    private String focusTraitId;

    public int describeContents() {
        return 0;
    }
    
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(focusTraitName);
        out.writeString(focusTraitId);
    }
    
    public void readFromParcel(Parcel in) {
        focusTraitName = in.readString();
        focusTraitId = in.readString();
    }
    
    public static final Parcelable.Creator<ParcelExplanation> CREATOR = new Parcelable.Creator<ParcelExplanation>() {       
        public ParcelExplanation createFromParcel(Parcel in) {           
            return new ParcelExplanation(in);
        }

        public ParcelExplanation[] newArray(int size) {
            return new ParcelExplanation[size];
        }
    };
    
    public ParcelExplanation(Parcel in) {
        focusTraitName = in.readString();
        focusTraitId = in.readString();
    }
    
    public ParcelExplanation() {}

	public String getFocusTraitName() {
		return focusTraitName;
	}
	public void setFocusTraitName(String focusTraitName) {
		this.focusTraitName = focusTraitName;
	}

	public String getFocusTraitId() {
		return focusTraitId;
	}
	public void setFocusTraitId(String focusTraitId) {
		this.focusTraitId = focusTraitId;
	}
   
}
