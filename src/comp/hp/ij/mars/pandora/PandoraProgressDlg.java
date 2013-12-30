package comp.hp.ij.mars.pandora;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import frame.event.EventHandler;
import frame.window.WndBase;

public class PandoraProgressDlg extends WndBase{

	private final String	TAG = "PandoraProgressDlg";
	private final int		KEY_BACK		= 4;
	private Activity		theAct = null;
	private Dialog			progDlg = null;
	private EventHandler	progWndEvent = null;
	
	public PandoraProgressDlg(Activity active, Handler handler, int nId){
		super.init(active, handler, nId);
		theAct = active;
	}
	
	@Override
	public void showWindow(boolean bShow) {
		if(bShow){
			progWndEvent = new EventHandler(progWndHandler);
			progDlg = new Dialog(theAct);	
			progDlg.getWindow().setBackgroundDrawableResource(R.drawable.transparent_background);
			progDlg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
			progDlg.setContentView(R.layout.progress);
			progDlg.setOnKeyListener(new OnKeyListener(){
//				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if(KEY_BACK == keyCode){
				//		sendAppMsg(WND_MSG,WND_CLOSE_PROG_WND);
					}
					return false;
				}
				});
			
			if(bShow){
				if(progDlg.isShowing()){
					return;
				}
				try{
					progDlg.show();
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				progDlg.cancel();
			} 
		}else{
			
		}
	}
	
	@Override
	public void closeWindow() {
		if(null != progWndEvent){
			progWndEvent = null;
		}
		try{
			if(null != progDlg){
				if(progDlg.isShowing()){
					progDlg.dismiss();
				}
				Log.i(TAG,"close prog window");
			}
			}catch(Exception e){
				e.printStackTrace();
			} 
	}


	public Dialog getDialog(){
		return progDlg;
	}
	
	//************************************************************
	// progress windows event handle
	// 
	//************************************************************
	private Handler progWndHandler = new Handler() {
        @Override 
        public void handleMessage(Message msg) {
        	switch (msg.what) {
        	case EventHandler.EVENT_HANDLE_CREATED:
        		break;
        	case EventHandler.EVENT_HANDLE_ON_TOUCH:				// touch event
        		switch(msg.arg1){
            	case EventHandler.EVENT_HANDLE_ON_TOUCH_DOWN:		// touch down
            	
            		break;
            	case EventHandler.EVENT_HANDLE_ON_TOUCH_UP:			// touch up
            	
            		break;
        		}
        		break;
        	case EventHandler.EVENT_HANDLE_ON_CLICK:
        		
        		break;
        	}
        }
	};

}
