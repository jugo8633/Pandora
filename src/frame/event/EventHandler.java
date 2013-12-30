package frame.event;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.os.Handler;
import android.os.Message;

public class EventHandler extends EventMessage{
	
	private final String TAG	= "EventHandler";
	private Handler m_handler	= null;
	private boolean m_bEventConsume = true;
	
	public EventHandler(){
		
	}
	
	public EventHandler(Handler handler){
		m_handler = handler;
		if( m_handler != null){
			sendMsg(0,0,EVENT_HANDLE_CREATED);
		}
	}
	
	public void init(Handler handler){
		m_handler = handler;
		if( m_handler != null){
			sendMsg(0,0,EVENT_HANDLE_CREATED);
		}
	}
	
	public void registerViewOnClick(View v){
		v.setOnClickListener(listenerOnClick);
	}
	
	public void registerViewOnTouch(View v){
		v.setOnTouchListener(listenerOnTouch);
	}
	
	
	OnClickListener listenerOnClick = new OnClickListener(){
    	public void onClick(View v){
    		sendMsg(EVENT_HANDLE_ON_CLICK, v.getId(), EVENT_HANDLE_ON_CLICK);
    	}
    };
    
    OnTouchListener listenerOnTouch = new OnTouchListener(){
    	public boolean onTouch (View v, MotionEvent event){
    		int nTouch = EVENT_HANDLE_ON_TOUCH;
    		switch(event.getAction()){
    		case MotionEvent.ACTION_DOWN:
    			nTouch = EVENT_HANDLE_ON_TOUCH_DOWN;
    			break;
    		case MotionEvent.ACTION_MOVE:
    			nTouch = EVENT_HANDLE_ON_TOUCH_MOVE;
    			break;
    		case MotionEvent.ACTION_CANCEL:
    			nTouch = EVENT_HANDLE_ON_TOUCH_CANCEL;
    			break;
    		case MotionEvent.ACTION_UP:
    			nTouch = EVENT_HANDLE_ON_TOUCH_UP;
    			break;
    		case MotionEvent.ACTION_OUTSIDE:
    			nTouch = EVENT_HANDLE_ON_TOUCH_OUTSIDE;
    			break;
    			default:
    				nTouch = event.getAction();
    			break;
    		}
    		
    		sendMsg(nTouch, v.getId(), EVENT_HANDLE_ON_TOUCH);
    		if(m_bEventConsume){
    			return true;
    		}else{
    			return false;
    		}
    	}
    };
    
 
   public void setEventConSume(boolean bConSume){
	   m_bEventConsume = bConSume;
   }
   
    private void sendMsg(int arg1, int arg2, int what){
    	if(m_handler == null){
    		Log.e(TAG, "invalid Handler");
    		return;
    	}
    	Message msg = new Message();
    	msg.what = what; // event...
		msg.arg1 = arg1; // click or press...
		msg.arg2 = arg2; // widget id
		m_handler.sendMessage(msg);
    }
}
