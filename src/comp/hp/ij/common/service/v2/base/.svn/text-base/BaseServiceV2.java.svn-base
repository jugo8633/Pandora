package comp.hp.ij.common.service.v2.base;
 

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import comp.hp.ij.common.service.v2.base.IServiceCallBackV2;
import comp.hp.ij.common.service.pandora.util.Logger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

/**
 * This class is parent class of all services. <BR>
 * It major is management of thread pool, client(activity or services) and Message handle. <BR>
 * Besides, There are several common functions put in here. <BR>   
 * About thread pool, every service that extends BaseService class have five threads in pool. <BR>
 * The number of default thread is defined in Constant.MAX_NUM_OF_THREAD <BR>
 * <BR>
 * About client management, there are several function to accomplish it.<BR>
 * include registerCallback, unregisterCallback, callback and handleMessage.
 * 
 * 
 * 
 *<P>This code was developed by HON HAI PRECISION IND. CO., LTD., 
 *CMMSG/DWHD/CCP/SWI, Code XXX for the Mars project.
 *
 *@Version 		14/08/2009
 *@author 		Ivan Chen
*/
public class BaseServiceV2 extends Service {

	private final String TAG = "BaseService";	
	private ExecutorService exec;
	private ClientHandlerV2 mCHandler = new ClientHandlerV2();
	private static Map<Integer,String> mVersionMap;
	
	final RemoteCallbackList<IServiceCallBackV2> mCallbacks
    = new RemoteCallbackList<IServiceCallBackV2>();
		
	
	/**
	 * The Constructors 
	 * 
	 * 
	 */
	public BaseServiceV2()
	{		
		if( mVersionMap == null )
		{
			mVersionMap = new HashMap<Integer,String>();
			mVersionMap.put(ConstantV2.SERVICE_TYPE_COMMON, ConstantV2.VERSION_COMMON);
			mVersionMap.put(ConstantV2.SERVICE_TYPE_WEATHER, ConstantV2.VERSION_WEAHTER);
			mVersionMap.put(ConstantV2.SERVICE_TYPE_PANDORA, ConstantV2.VERSION_PANDORA);
			mVersionMap.put(ConstantV2.SERVICE_TYPE_FACEBOOK, ConstantV2.VERSION_FACEBOOK);
		}
				
		Log.i(TAG,"Dump version number...");
		Log.i(TAG,ConstantV2.VERSION_COMMON);
		Log.i(TAG,ConstantV2.VERSION_WEAHTER);
		Log.i(TAG,ConstantV2.VERSION_PANDORA);
		Log.i(TAG,ConstantV2.VERSION_FACEBOOK);
		
		ConstantV2.init();
	}
	
	/**
	 * Return the version number 
	 * @param serviceType reference to Constant.SERVICE_TYPE_XXXXX
	 * @return the version number 
	 */	
	public String getVersionNumber(int serviceType)
	{
		return mVersionMap.get(serviceType);
	}
	
	/**
	 * Called by the system when the service is first created. <BR>
	 * This method will create thread pool with n number of thread <BR>
	 * The number of thread is depend on the Constant.MAX_NUM_OF_THREAD <BR>
	 *  	
	 */	
	@Override
    public void onCreate() {	
		exec = Executors.newFixedThreadPool(ConstantV2.MAX_NUM_OF_THREAD);
		super.onCreate(); 
	}

	/**
	 * Called by the system every time a client explicitly starts the service by calling  <BR>
	 * startService(Intent), providing the arguments it supplied and a unique integer token <BR>
	 * representing the start request
	 *  	
	 * @param intent  The Intent supplied to startService(Intent), as given 
	 * @param startId  A unique integer representing this specific request to start. Use with stopSelfResult(int).  
	 */
	public void onStart(Intent intent, int startId) {
		Log.i(TAG,"onStart !!!!!!!!!!!!!!!!!!!!!");
    }	
	
	/**
	 * Return the communication channel to the service
	 *  	
	 * @param intent The Intent that was used to bind to this service, as given to 
	 * Context.bindService. Note that any extras that were included with the Intent at 
	 * that point will not be seen here.
	 */
	@Override
	public IBinder onBind(Intent intent) {		
		return null;
	}
	
	/**
	 * Called by the system to notify a Service that it is no longer used and is being removed. <BR>
	 * Also shutdown the thread pool 
	 *  	 
	 */	
	@Override
    public void onDestroy() {
		Log.i(TAG,"onCreate shutdown!!!!!!!!!!!!!!!!!!!!!");	
		exec.shutdownNow();
        super.onDestroy();
    }
	
	/**
	 * Called when all clients have disconnected from a particular interface published by the service
	 *  	
	 * @param intent The Intent that was used to bind to this service, as given to 
	 * Context.bindService. Note that any extras that were included with the Intent at that 
	 * point will not be seen here
	 */
	@Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
    
    /**
	 * return the message handler  
	 *  	 
	 * @return a Message Handler
	 */	
    protected Handler getMsgHandler() { return mMsgHandler;}
    
    private Handler mMsgHandler = new Handler() {
        public void handleMessage(Message msg) {
        	Log.i(TAG,"handleMessage");	
            switch (msg.what) {
                case ConstantV2.REQUEST_COMPLETED:
                {                	
                	Log.i(TAG,"handleMessage REQUEST_COMPLETED CCode=" + msg.getData().getInt(ConstantV2.CLIENT_CODE) + " APIID=" + msg.getData().getString("APIID"));                	
                	callback(msg);
	                break;
                }
                default:
                    super.handleMessage(msg);
            }
        }

    };
        
    	
    private void callback(Message msg) 
    {    	
        final int N = mCallbacks.beginBroadcast();        
        Log.i(TAG,"callback !!!!!!!!!!!!!!!!!!!!!");
        
        Log.i(TAG,"clientCode="+msg.getData().getInt(ConstantV2.CLIENT_CODE));
        for (int i=0; i<N; i++) {
            try {
            	Log.i(TAG,"CCode=" + mCallbacks.getBroadcastItem(i).hashCode());
            	int clientCode = msg.getData().getInt(ConstantV2.CLIENT_CODE);
            	int callingId = msg.getData().getInt(ConstantV2.CALLING_ID);            	
            	if( mCallbacks.getBroadcastItem(i).hashCode() == clientCode )
            	{
            		Log.i(TAG,"call ResultCallBack");
            		mCallbacks.getBroadcastItem(i).ResultCallBack(msg.what, callingId, msg.arg2, (PResultV2)msg.getData().getParcelable(ConstantV2.RESULT));
            	}
            } catch (RemoteException e) {
            	Log.e(TAG,"callback: " + e.toString());
            }
        }
        mCallbacks.finishBroadcast();        
    }
       
    /**
	 * After client bind service and get the IBinder object, it must call this method. The service 
	 * will send the response or event back to client depend on client's hashCode and cb object
	 *     
	 * @param hashCode client's unique ID. ( for example: this.hashCode() )
	 * @param cb an IServiceCallBack object.	 
	 */
    public void registerCallback(int hashCode, IServiceCallBackV2 cb) {
    	Log.i(TAG,"registerCallback " );
        if (cb != null)
        {   
        	mCHandler.addClient(hashCode,cb.hashCode());
        	mCallbacks.register(cb);            	 
        	Log.i(TAG,"registerCallback mumber=" + mCallbacks.beginBroadcast());        	
        }           
    }
    
    /**
	 * When client doesn't need service anymore, calls this method to unregister.
	 * This is important procedure. You may get the out of memory problem if loss call 
	 * this method to unregister.    
	 *  
	 * @param hashCode client's unique ID. ( for example: this.hashCode() )
	 * @param cb an IServiceCallBack object.	 
	 */
    public void unregisterCallback(int hashCode, IServiceCallBackV2 cb) {
    	Log.i(TAG,"unregisterCallback " );
        if (cb != null)
        {        	
        	mCHandler.removeClient(hashCode);
        	mCallbacks.unregister(cb);
        	Log.i(TAG,"unregisterCallback mumber=" + mCallbacks.beginBroadcast());      
        }        	
    }
    
    /**
	 * this method will package the parameter APIID and clientCode into the object req.   
	 * Then put the thread mSTread into thread pool and execute it. 
	 *  
	 * @param hashCode client's unique ID. ( for example: this.hashCode() )
	 * @param req parameter set.
	 * @param apiid the function will be performed is base on apiid number.
	 * @param mSTread a thread that is going to be executed 
	 * @return a number to identify this apiid call. The same number will be passed to callback function
	 *         when job done.  
	 */    
    public int executeServiceThread(int hashCode, Bundle req, int apiid, IServiceThreadV2 mSTread)
    {   	
    	int clientCode = mCHandler.getClient(hashCode);
    	
    	if( clientCode == 0 )
    	{
    		Log.e(TAG,"exectueServiceThread: Can't find Client code , do not thing"  );
    		return 0;        	
    	} 
    	
    	Log.i(TAG,"CLIENT_CODE =" + clientCode  );
    	req.putString(ConstantV2.APIID, String.valueOf(apiid));
    	req.putInt(ConstantV2.CLIENT_CODE, clientCode );
    	
    	Log.i(TAG,"putRequest !!!!!!!!!!!!!!!!!!!!! "  );
    	
    	int id = mSTread.putRequest(req);
    	Log.i(TAG,"Request id="+id);
    	  
    	exec.execute(mSTread);	
    	return id;
    }
    
    public void registerClient(int iHashCodeInput) {
        Logger.d();
        mCHandler.addClient(iHashCodeInput, iHashCodeInput);
    }    
    public void unregisterClient(int iHashCodeInput) {
        Logger.d();
        mCHandler.removeClient(iHashCodeInput);
    }
}
