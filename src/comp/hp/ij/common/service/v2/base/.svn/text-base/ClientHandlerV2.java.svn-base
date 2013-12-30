package comp.hp.ij.common.service.v2.base;

import java.util.HashMap;
import java.util.Map;

/**
 *  this class create a map to maintain the relationship between activity and callback <BR>
 *  There two major values. First one is a identify number of activity and it is also the key of map<BR>
 *  Second, a clientCode that is get from IServiceCallBack object when registerCallback function called.<BR>
 *  for example :  key: (activity)this.hashCode(),  clientCode:  (IServiceCallBack)cb.hashCode()
 * 
*<P>This code was developed by HON HAI PRECISION IND. CO., LTD., 
*CMMSG/DWHD/CCP/SWI, Code XXX for the Mars project.
*
*@Version 		14/08/2009
*@author 		Ivan Chen
*/
public class ClientHandlerV2 {
	Map<Integer,Integer> mClientMap;
	
	/**
	 * The Constructors 
	 * 	 
	 */
	public ClientHandlerV2()
	{
		mClientMap = new HashMap<Integer,Integer>();    	
	}
	
	/**
	 * add client  
	 * @param hashCode an identify number of client  
	 * @param clientCode get from cb.hashCode().	 
	 */	
	public void addClient(int hashCode,int clientCode)
	{
		mClientMap.put(hashCode, clientCode);
	}
	
	/**
	 * get clientCode  
	 * @param hashCode an identify number of client  
	 * @return clientCode 
	 */
	public int getClient(int hashCode)
	{
		Integer code = (Integer) mClientMap.get(hashCode);
		
		if ( code == null )
			return 0;
		else
			return code;
	}
	
	/**
	 * remove client  
	 * @param hashCode an identify number of client  	 
	 */
	public void removeClient(int hashCode)
	{
		mClientMap.remove(hashCode);
	}
	
}
