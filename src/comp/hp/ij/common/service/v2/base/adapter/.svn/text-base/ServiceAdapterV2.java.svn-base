package comp.hp.ij.common.service.v2.base.adapter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
  
import comp.hp.ij.common.service.v2.base.IServiceCallBackV2;
import comp.hp.ij.common.service.v2.base.ConstantV2;
import comp.hp.ij.common.service.v2.base.PResultV2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 *  The xxxxAdapter is for client side. This kinds of class implement the detail that <BR>
 *  implementation of communication with service. So user can easy to use service without knowing it<BR>
 *  And adapter can decrease the impact when service changes. <BR>
 *    
 *  It get the callback from service and dispatch to processResponse or processEvent.
 *  
 * 
*<P>This code was developed by HON HAI PRECISION IND. CO., LTD., 
*CMMSG/DWHD/CCP/SWI, Code XXX for the Mars project.
*
*@Version 		14/08/2009
*@author 		Ivan Chen
*/
public abstract class ServiceAdapterV2 implements Runnable{
	private final String TAG = "ServiceAdapter";
	private IServiceAdapterV2 mAct;
	private static Map<Integer,String> mVersionMap = new HashMap<Integer,String>();
	private ExecutorService mExec;
	private boolean mIsReady=false;
	 
	public abstract void destroy();
	public abstract boolean isServiceAvailable();
	
	/**
	 * The Constructors. run a thread to polling IBinder 
	 * 
	 * @param 	act an object of activity that implement IServiceAdapter interface	 
	 */
	public ServiceAdapterV2(IServiceAdapterV2 act)
	{				
		mAct = act;		
		
		if( mAct != null)
    		Log.i(TAG,"handleMessage: mAct=" + mAct.toString());
    	else 
    		Log.i(TAG,"handleMessage: mAct=null");
			
		mVersionMap.put(ConstantV2.SERVICE_TYPE_COMMON, ConstantV2.VERSION_COMMON);
		mVersionMap.put(ConstantV2.SERVICE_TYPE_WEATHER, ConstantV2.VERSION_WEAHTER);
		mVersionMap.put(ConstantV2.SERVICE_TYPE_PANDORA, ConstantV2.VERSION_PANDORA);
		mVersionMap.put(ConstantV2.SERVICE_TYPE_FACEBOOK, ConstantV2.VERSION_FACEBOOK);
		Log.i(TAG,"ServiceAdapter:out");
		
		mExec = Executors.newFixedThreadPool(1);
		Log.i(TAG,"ServiceAdapter: run thread");
		mExec.execute(this);
		Log.i(TAG,"ServiceAdapter: finish");
	}
		
	/**
	 * get callback object	 
	 * @return return the IServiceCallBack object 
	 */	
	protected IServiceCallBackV2 getCallback() { return mCallback; }
	
	/**
	 * Return the version number 
	 * @param serviceType reference to Constant.SERVICE_TYPE_XXXXX
	 * @return the version number 
	 */	
	public String getVersionNumber(int serviceType)
	{
		return mVersionMap.get(serviceType);
	}
			 
    private IServiceCallBackV2 mCallback = new IServiceCallBackV2.Stub() {
        
        public void ResultCallBack(int what,int arg1,int arg2, PResultV2 pRes)
        {
        	Log.i(TAG,"ResultCallBack");       
        	
        	 //Test code            
            if( pRes == null )
            	Log.i(TAG,"callback PResult is null");
            else
            {
            	Log.i(TAG,"callback res.mParameter = " + pRes.mParameter); 
            }
        	
        	
           	Bundle bundle = new Bundle();
        	bundle.putParcelable(ConstantV2.RESULT, pRes);
        	        	
        	Message msg = new Message();
        	msg.what = what;
        	msg.arg1 = arg1;
        	msg.arg2 = arg2;        	
        	msg.setData(bundle);        	
        	mMsgHandler.sendMessage(msg);
        }        

    };
    
    /**
	 * Send the service ready event to activity.  	 
	 */	
    public void SendServiceReadyEvent()
    {
    	Message msg = new Message();
    	msg.what = ConstantV2.SERVICE_READY;    	     	
    	mMsgHandler.sendMessage(msg);
    	
    	mExec.shutdown();
    }
    
    /**
	 * set the service ready. call by onServiceConnected() in child class   	 
	 */
    public void setServiceReady() { mIsReady = true;  }
    
    /**
	 * polling the service event   	 
	 */
    public void run() {
		// TODO Auto-generated method stub
		Log.i(TAG,"polling service ready event");
		for(int i=0;i<100;i++)
		{
			if( mIsReady )
			{				
				SendServiceReadyEvent();
				return;
			}
										
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Log.e(TAG,"adapter can't get service ready callback...");
	}
    
    private Handler mMsgHandler = new Handler() {       
    	 /**
    	 * handle message. 
    	 * msg.what = Constant.REQUEST_COMPLETED, call processResponse();
    	 * msg.what = Constant.SERVICE_READY, call processEvent();
    	 * @param msg Message object    	 
    	 */
    	public void handleMessage(Message msg) {
        	
        	if( mAct != null)
        		Log.i(TAG,"handleMessage: mAct=" + mAct.toString());
        	else
        		Log.i(TAG,"handleMessage: mAct=null");
        		
            switch (msg.what) {
                case ConstantV2.REQUEST_COMPLETED:
                {   
                	Log.i(TAG,"REQUEST_COMPLETED ");                                 	
                	mAct.processResponse(msg.arg1,msg.arg2, (PResultV2)msg.getData().getParcelable(ConstantV2.RESULT) );
	                break;
                }
                case ConstantV2.SERVICE_READY:
                	mAct.processEvent(ConstantV2.EVENT_SERVICE_READY, null);
                	break;
                default:
                    super.handleMessage(msg);
            }
        }

    };
    
    
}
