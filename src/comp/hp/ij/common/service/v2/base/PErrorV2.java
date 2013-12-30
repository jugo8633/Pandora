package comp.hp.ij.common.service.v2.base;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class store the error code and be passed to activity when error occurs.
 * 
 * 
*<P>This code was developed by HON HAI PRECISION IND. CO., LTD., 
*CMMSG/DWHD/CCP/SWI, Code XXX for the Mars project.
*
*@Version 		14/08/2009
*@author 		Ivan Chen
*/
public class PErrorV2 implements Parcelable {
	public int mCategory;
	public int mCode;
	
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) 
    {	
		out.writeInt(mCategory);
		out.writeInt(mCode);
    }
	
	public void readFromParcel(Parcel in) 
    {			
		mCategory = in.readInt();
		mCode = in.readInt();			
    }
	
	public static final Parcelable.Creator<PErrorV2> CREATOR  = new Parcelable.Creator<PErrorV2>() 
    {    	
        public PErrorV2 createFromParcel(Parcel in) 
        {   
            return new PErrorV2(in);
        }

        public PErrorV2[] newArray(int size) 
        {
            return new PErrorV2[size];
        }
    }; 
	
		
	public PErrorV2(Parcel in) {		
		mCategory = in.readInt();
		mCode = in.readInt();	
	}
	
	public PErrorV2() {
		
	}
}
