package comp.hp.ij.common.service.pandora.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelAssociationResult implements Parcelable {

	private int isAssociated;

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(isAssociated);
	}

	public void readFromParcel(Parcel in) {
		isAssociated = in.readInt();
	}

	public static final Parcelable.Creator<ParcelAssociationResult> CREATOR = new Parcelable.Creator<ParcelAssociationResult>() {
		public ParcelAssociationResult createFromParcel(Parcel in) {
			return new ParcelAssociationResult(in);
		}

		public ParcelAssociationResult[] newArray(int size) {
			return new ParcelAssociationResult[size];
		}
	};

	public ParcelAssociationResult(Parcel in) {
		isAssociated = in.readInt();
	}

	public ParcelAssociationResult() {
	}

}
