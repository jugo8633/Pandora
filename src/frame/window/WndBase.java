package frame.window;

import frame.event.EventHandler;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;


/**
 * 
 * @author jugo
 * @description handle window event
 * @version 0.1
 */
public abstract class WndBase extends EventHandler{
	
	private Activity	theApp		= null;
	private Handler		theHandler	= null;
	private int			theWndId	= -1;
	private boolean		m_bInited	= false;
	
	public abstract void showWindow(boolean bShow);
	public abstract void closeWindow();
	
	public final int COLOR_FOCUS = 0xff6ccbf7;
	public final int COLOR_WHITE = 0xffffffff;
	
	public WndBase(){
		m_bInited = false;
	}
	
	public WndBase(Activity active, Handler handler, int nWndId){
		init(active, handler, nWndId);
	}
	
	protected void init(Activity active, Handler handler, int nWndId){
		theApp		= active;
		theHandler	= handler;
		theWndId	= nWndId;
		super.init(theHandler);
		m_bInited = true;
	}
	
	public boolean isInit(){
		return m_bInited;
	}
	
	protected void sendAppMsg(int nWhat, int nArg1, Object objData){
		if(theHandler == null){
			return;
		}
		
		Message msg = new Message();
		msg.what = nWhat; 		// message type
		msg.arg1 = nArg1; 		// message
		msg.arg2 = theWndId; 	// window id
		msg.obj = objData;
		theHandler.sendMessage(msg);
	}
	
	protected Activity getApp(){
		return theApp;
	}
	
	protected void showLayout(int nViewID){
		theApp.setContentView(nViewID);
	}
	
	public void registerViewOnClick(View v){
		super.registerViewOnClick(v);
	}
	
	public void registerViewOnTouch(View v){
		super.registerViewOnTouch(v);
	}
}
