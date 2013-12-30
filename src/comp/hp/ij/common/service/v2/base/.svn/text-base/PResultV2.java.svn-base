package comp.hp.ij.common.service.v2.base;

import java.util.ArrayList;
import android.os.Parcel;
import android.os.Parcelable;

/**
*<P>This code was developed by HON HAI PRECISION IND. CO., LTD., 
*CMMSG/DWHD/CCP/SWI, Code XXX for the Mars project.
*
*@Version 		14/08/2009
*@author 		Ivan Chen
*/
public class PResultV2 implements Parcelable {
	public int mParameter;
	public int mReserve;
	public ArrayList<PErrorV2> mPError;
	//public PWeather mPWeather;
	public IDataV2 mData;
	
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) 
    {	
		//Log.i("PResult","writeToParcel in");
		out.writeInt(mParameter);
		out.writeInt(mReserve);
		out.writeList(mPError);
		//out.writeParcelable(mPWeather, flags);	
		out.writeParcelable(mData, flags);
		//Log.i("PResult","writeToParcel out");
    }
	
	@SuppressWarnings("unchecked")
    public void readFromParcel(Parcel in) 
    {	
		//Log.i("PResult","readFromParcel in");
		mParameter = in.readInt();
		mReserve = in.readInt();
		mPError = in.readArrayList(getClass().getClassLoader());
		//mPWeather = in.readParcelable(getClass().getClassLoader());	
		mData = in.readParcelable(getClass().getClassLoader());
		//Log.i("PResult","readFromParcel out");
    }
	
	public static final Parcelable.Creator<PResultV2> CREATOR  = new Parcelable.Creator<PResultV2>() 
    {    	
        public PResultV2 createFromParcel(Parcel in) 
        {        	
        	//Log.i("PResult","createFromParcel");
            return new PResultV2(in);
        }

        public PResultV2[] newArray(int size) 
        {
        	//Log.i("PResult","PResult[] newArray(int size) ");
            return new PResultV2[size];
        }
    }; 
	
		
	@SuppressWarnings("unchecked")
    public PResultV2(Parcel in) {		
		//Log.i("PResult","PResult(Parcel in) in");
		mParameter = in.readInt();
		mReserve = in.readInt();
		mPError = in.readArrayList(getClass().getClassLoader());
		//mPWeather = in.readParcelable(getClass().getClassLoader());
		mData = in.readParcelable(getClass().getClassLoader());
		//Log.i("PResult","PResult(Parcel in) out");
	}
	
	public PResultV2() {
		
	}
}
