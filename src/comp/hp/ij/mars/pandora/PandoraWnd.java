package comp.hp.ij.mars.pandora;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.RelativeLayout;
import frame.window.WndBase;
import frame.event.EventHandler;

public abstract class PandoraWnd extends WndBase{

	private final String TAG = "PandoraWnd";
	public	final	int		HIDE_VIEW		= -1;
	public	static final int EVENT_TOUCH	= 0;
	public	static final int EVENT_CLICK	= 1;
	private int		m_nMainLayoutResId		= HIDE_VIEW;
	private int		m_nFootLayoutResId		= HIDE_VIEW;
	private int		m_nHeaderLayoutResId	= HIDE_VIEW;
	private EventHandler WndEvent = null;
	private	List<Integer>listTouchResId = null;
	private	List<Integer>listClickResId = null;
	protected abstract void onTouchDown(int nResId);
	protected abstract void onTouchUp(int nResId);
	protected abstract void onClick(int nResId);
	protected abstract void onShow();
	protected abstract void onClose();
	protected PandoraApplication application = null;
	
	public PandoraWnd(Activity active, Handler handler, int nId){
		super(active, handler, nId);
		application = (PandoraApplication)active.getApplication();
		WndEvent = new EventHandler(WndHandler);
		listTouchResId = new ArrayList<Integer>();
		listClickResId = new ArrayList<Integer>();
	}
	
	@Override
	public void showWindow(boolean bShow) {
		if(bShow){
			if(HIDE_VIEW == m_nFootLayoutResId && HIDE_VIEW == m_nHeaderLayoutResId){
				showCommUI(false);
				if(HIDE_VIEW != m_nMainLayoutResId){
					super.getApp().setContentView(m_nMainLayoutResId);
					onShow();
					initEvents();
					sendAppMsg(WND_MSG,WND_SHOW,null);
				}else{
					Log.e(TAG,"show main view fail, layout resource is invalid");
				}
			}else{
				showCommUI(true);
				if(HIDE_VIEW != m_nMainLayoutResId){
					((PandoraApp)super.getApp()).addMainView(m_nMainLayoutResId);
					if(HIDE_VIEW != m_nFootLayoutResId){
						((PandoraApp)super.getApp()).addFootView(m_nFootLayoutResId);
					}else{
						View vTmp = super.getApp().findViewById(R.id.commUI_main_footerBar);
						if(null != vTmp){
							vTmp.setVisibility(View.GONE);
							vTmp = super.getApp().findViewById(R.id.commUI_main_primaryView);
							if(null != vTmp){
								LayoutParams params = (RelativeLayout.LayoutParams)vTmp.getLayoutParams();
								params.bottomMargin = 0;	
								vTmp.setLayoutParams(params);
							}
						}else{
							Log.e(TAG,"get foot layout fail");
						}
					}
					if(HIDE_VIEW != m_nHeaderLayoutResId){
						((PandoraApp)super.getApp()).addHeaderView(m_nHeaderLayoutResId);
					}else{
						View vTmp = super.getApp().findViewById(R.id.commUI_main_headerBar);
						if(null != vTmp){
							vTmp.setVisibility(View.GONE);
							vTmp = super.getApp().findViewById(R.id.commUI_main_primaryView);
							if(null != vTmp){
								LayoutParams params = (RelativeLayout.LayoutParams)vTmp.getLayoutParams();
								params.topMargin = 0;
								if(400 > params.height){
									params.height = 400;
								}else{
									params.height = 710;
								}
								vTmp.setLayoutParams(params);
							}
						}else{
							Log.e(TAG,"get header layout fail");
						}
					}
					onShow();
					initEvents();
					sendAppMsg(WND_MSG,WND_SHOW,null);
				}else{
					Log.e(TAG,"show main view fail, layout resource is invalid");
				}
			}
		}else{
			
		}
	}
	
	@Override
	public void closeWindow() {
		onClose();
		if(listTouchResId != null){
			if(listTouchResId.size() > 0){
				listTouchResId.clear();
			}
			listTouchResId = null;
		}
		if(listClickResId != null){
			if(listClickResId.size() > 0){
				listClickResId.clear();
			}
			listClickResId = null;
		}
		if(null != WndEvent){
			WndEvent = null;
		}
	}
	
	public void setLayoutResId(int nMain, int nFoot, int nHeader){
		if(nMain == -1){
			return;
		}
		m_nMainLayoutResId = nMain;
		if(nFoot != -1){
			m_nFootLayoutResId = nFoot;
		}
		if(nHeader != -1){
			m_nHeaderLayoutResId = nHeader;
		}
	}
	
	public void setViewTouchEvent(int nResId){
		listTouchResId.add(nResId);
	}
	
	public void setViewClickEvent(int nResId){
		listClickResId.add(nResId);
	}
	
	private void initEvents(){
		if(null != listTouchResId){
			if(listTouchResId.size() > 0){
				for(int i=0; i < listTouchResId.size(); i++){
					View tView = super.getApp().findViewById(listTouchResId.get(i));
					if(null != tView){
						WndEvent.registerViewOnTouch(tView);
					}
				}
			}
		}
		
		if(null != listClickResId){
			if(listClickResId.size() > 0){
				for(int i=0; i < listClickResId.size(); i++){
					View cView = super.getApp().findViewById(listClickResId.get(i));
					if(null != cView){
						WndEvent.registerViewOnClick(cView);
					}
				}
			}
		}
	}
	
	public void RegisterEvent(int nResId, int nEventType){
		View cView = super.getApp().findViewById(nResId);
		if(null == cView){
			Log.e(TAG,"register event fail , view id="+nResId);
			return;
		}
		switch(nEventType){
		case EVENT_TOUCH:
			WndEvent.registerViewOnTouch(cView);
			break;
		case EVENT_CLICK:
			WndEvent.registerViewOnClick(cView);
			break;
		}
	}
	
	public void RegisterEvent(View vRes, int nEventType){
		if(null == vRes){
			Log.e(TAG,"register event fail , view invalid");
			return;
		}
		switch(nEventType){
		case EVENT_TOUCH:
			WndEvent.registerViewOnTouch(vRes);
			break;
		case EVENT_CLICK:
			WndEvent.registerViewOnClick(vRes);
			break;
		}
	}
	
	private void showCommUI(boolean bShow){    
		if(bShow){
			Log.d(TAG,"show comm ui window");
	        super.getApp().setContentView(R.layout.commui_main);
	        RelativeLayout lymain = (RelativeLayout)super.getApp().findViewById(R.id.commUI_main);
	        if(lymain != null){
	        	lymain.setBackgroundColor(Color.rgb(0, 0, 0));
			}
		}else{
			Log.d(TAG,"no show comm ui window");
			RelativeLayout lymain2 = (RelativeLayout)super.getApp().findViewById(R.id.commUI_main);
			if(lymain2 != null){
				lymain2.removeAllViews();
				lymain2.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * window event handle
	 */
	private Handler WndHandler = new Handler() {
        @Override 
        public void handleMessage(Message msg) {
        	switch (msg.what) {
        	case EventHandler.EVENT_HANDLE_CREATED:
        		break;
        	case EventHandler.EVENT_HANDLE_ON_TOUCH:			
        		switch(msg.arg1){
            	case EventHandler.EVENT_HANDLE_ON_TOUCH_DOWN:
            		application.PService.resetIdleTime();
            		onTouchDown(msg.arg2);
            		break;
            	case EventHandler.EVENT_HANDLE_ON_TOUCH_UP:		
            		onTouchUp(msg.arg2);
            		break;
        		}
        		break;
        	case EventHandler.EVENT_HANDLE_ON_CLICK:
        		application.PService.resetIdleTime();
        		onClick(msg.arg2);
        		break;
        	}
        }
	};

	public void initOrientation(int nLandResId, int nPortResId){
		int nOrientation = application.getOrientation();
		if(PandoraApplication.ORIENTATION_LAND == nOrientation){
			setLayoutResId(nLandResId, HIDE_VIEW, HIDE_VIEW);
			Log.v(TAG,"set layout to land");
		}else{
			setLayoutResId(nPortResId, HIDE_VIEW, HIDE_VIEW);
			Log.v(TAG,"set layout to port");
		}
	}
}
