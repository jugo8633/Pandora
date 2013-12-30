package comp.hp.ij.common.service.v2.base;

import android.os.Bundle;
import android.util.Log;

/**
 * This class is parent class of all function implementation Thread. <BR>
 * It major is to generate calling id after calling the method putRequest <BR>  
 * In asyn function call, user get a calling ID(A) when calls a asyn function. <BR>
 * After job is done, service will send the result by callbcak function with a calling ID(B).<BR>
 * user can use calling ID(B) compare with calling ID(A). if match, then you can make sure that<BR> 
 * this callback is the result of calling ID(A). 
 * 
*<P>This code was developed by HON HAI PRECISION IND. CO., LTD., 
*CMMSG/DWHD/CCP/SWI, Code XXX for the Mars project.
*
*@Version 		14/08/2009
*@author 		Ivan Chen
*/
public class BaseServiceThreadV2 implements IServiceThreadV2{
	private final String TAG = "BaseServiceThread";
	
	private static int mServiceType=-1;
	private static int mToken;
	private Bundle mRequest;
	
	/**
	 * The Constructors. Arrange the scope of callingID base on the parameter 'type' <BR>  
	 * for example: Weather Service: <BR>
	 *  (below parameter defined in Constant class) <BR>
	 *  SERVICE_TYPE_WEATHER = 1, NUMBER_OF_API_BASE = 1000; <BR>
	 *  WEAHTER_API_BASE = SERVICE_TYPE_WEATHER * NUMBER_OF_API_BASE <BR>
	 *  calling id range =  WEAHTER_API_BASE ~ WEAHTER_API_BASE + NUMBER_OF_API_BASE - 1
	 * 
	 * @param 	type what kind of service you used. ( refer to Constant.SERVICE_TYPE_XXXX )	 
	 */
	public BaseServiceThreadV2(int type)
	{
		Log.i(TAG,"BaseServiceThread: mServiceType =" + mServiceType + " mToken=" + mToken);
		if( mServiceType < 0 )		
			initToken(type);
		
		mServiceType = type;		 
	}
	
	private void initToken(int type)
	{		
		mToken = 1 + ConstantV2.mServiceMap.get(type);
		Log.i(TAG,"initToken: mServiceType =" + type + " mToken=" + mToken);
	}
	
	private int getToken()
    {
		Log.i(TAG,"getToken: B mToken =" + mToken );		
		if( (mToken+1) >= ( ConstantV2.NUMBER_OF_API_BASE * ( mServiceType + 1) )	)
				initToken(mServiceType);
		else
			mToken++;
				
    	return mToken;
    }
	
	public Bundle getRequest() { return mRequest; }
	
	/**
	 * put the parameter set into thread.  
	 * @param req parameter set
	 * @return calling ID. Which a number to identify this apiid call. <BR> 
	 * The same number will be passed to callback function when job done.
	 */	
	public int putRequest(Bundle req)
	{
		int id;
		
		id = getToken();
		Log.i(TAG,"putRequest ID=" + id);				
		req.putInt(ConstantV2.CALLING_ID, id);
		mRequest = req;
		
		return id;
	}

	/**
	 * do nothing. subClass needs to override it	 
	 */	
	public void run() {
		// TODO Auto-generated method stub
		
	}	
}
