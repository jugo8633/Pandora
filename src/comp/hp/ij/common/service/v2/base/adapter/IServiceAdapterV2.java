package comp.hp.ij.common.service.v2.base.adapter;

import android.os.Bundle;
import comp.hp.ij.common.service.v2.base.PResultV2;

/**
 * This interface define two callback methods that class needs to implement it. <BR>
 * 
 * 
 * 
*<P>This code was developed by HON HAI PRECISION IND. CO., LTD., 
*CMMSG/DWHD/CCP/SWI, Code XXX for the Mars project.
*
*@Version 		14/08/2009
*@author 		Ivan Chen
*/
public interface IServiceAdapterV2 {	
	void processResponse(int callingId,int result,PResultV2 pRes);
	void processEvent(int event, Bundle info);
}
