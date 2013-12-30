package comp.hp.ij.common.service.v2.base;

import java.util.HashMap;
import java.util.Map;

/**
 * All common constant value put in this class. <BR>
 * 1. Service version number <BR>
 * 2. Callback message <BR>
 * 3. service type <BR>
 * 4. the base of calling ID for each service  
 * 
 * 
*<P>This code was developed by HON HAI PRECISION IND. CO., LTD., 
*CMMSG/DWHD/CCP/SWI, Code XXX for the Mars project.
*
*@Version 		14/08/2009
*@author 		Ivan Chen
*/
public class ConstantV2 {
	public static final int MAX_NUM_OF_THREAD = 5;
	
	public static final String WEATHER_SERVICE_URI = "comp.hp.ij.common.service.weather.WeatherService.REMOTE_SERVICE";
	
	//version number
	//rule: XX(Major).XX(Minor)_Service.XX(Build)
	//Major : change interface , Minor: service internal change , Build: bug fixed
	public static final String VERSION_COMMON = "01.01_Common.00";
	public static final String VERSION_WEAHTER = "01.01_Weather.00";
	public static final String VERSION_PANDORA = "00.00_Pandora.00";
	public static final String VERSION_FACEBOOK = "00.00_Facebook.00";	
		
	//callback Message
	// msg.what
	public static final int REQUEST_COMPLETED = 1;
	public static final int SERVICE_READY = 2;
	public static final int PANDORA_SERVICE_BROADCAST_CALL = 3;
	public static final int PANDORA_SERVICE_INTERNAL_CALL = 4;
	
	// msg.arg2 ( result )
	public static final int REQUEST_SUCCESS = 0;
	public static final int REQUEST_FAILS = 1;
	
	//event callback 
	public static final int EVENT_SERVICE_READY = 1;
	
	//PError : Error Category
	public static final int CATEGORY_HTTPCLIENT = 		1;
	public static final int CATEGORY_BASE_SERVICE = 	2;
	public static final int CATEGORY_WEATHER_SERVICE = 	3;
	public static final int CATEGORY_FACEBOOK_SERVICE = 4;
	public static final int CATEGORY_PANDORAS_ERVICE = 	5;
		
	//common parameter
	public static final String CALLING_ID = "CALLING_ID";  
	public static final String APIID = "APIID";
	public static final String CLIENT_CODE = "CLIENT_CODE";
	public static final String RESULT_CODE = "RESULT_CODE";
	public static final String RESULT = "RESULT";
		
	public static Map<Integer,Integer> mServiceMap;
	// service type
	// note: (important) if add a new service type, please also add a mapping in init()
	public static final int SERVICE_TYPE_COMMON = 0;
	public static final int SERVICE_TYPE_WEATHER = 1;
	public static final int SERVICE_TYPE_PANDORA = 2;
	public static final int SERVICE_TYPE_FACEBOOK = 3;
	public static final int SERVICE_TYPE_TEMPLET = 10;
	
	//note: following constants are also use to generate token ( calling id )	     
	public static final int NUMBER_OF_API_BASE = 1000;
	public static final int COMMON_API_BASE = SERVICE_TYPE_COMMON * NUMBER_OF_API_BASE;
	public static final int WEAHTER_API_BASE = SERVICE_TYPE_WEATHER * NUMBER_OF_API_BASE;
	public static final int PANDORA_API_BASE = SERVICE_TYPE_PANDORA * NUMBER_OF_API_BASE;
	public static final int FACEBOOK_API_BASE = SERVICE_TYPE_FACEBOOK * NUMBER_OF_API_BASE;
	public static final int TEMPLET_API_BASE = SERVICE_TYPE_FACEBOOK * NUMBER_OF_API_BASE;
	
	public static void init()
	{
		if( mServiceMap == null )
		{
			mServiceMap = new HashMap<Integer,Integer>();    
			mServiceMap.put(SERVICE_TYPE_COMMON, COMMON_API_BASE);
			mServiceMap.put(SERVICE_TYPE_WEATHER, WEAHTER_API_BASE);
			mServiceMap.put(SERVICE_TYPE_PANDORA, PANDORA_API_BASE);
			mServiceMap.put(SERVICE_TYPE_FACEBOOK, FACEBOOK_API_BASE);
		}		
	}
}
