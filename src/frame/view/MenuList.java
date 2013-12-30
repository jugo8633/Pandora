package frame.view;

import frame.event.EventHandler;
import frame.window.ThreadHandler;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MenuList extends RelativeLayout{

	private final String TAG = "MenuList";
	private Context mContext = null;
	private ViewFlipper flipper = null;
	private int mnAnimationDuration = 500;
	public final int ANIMATION_DOWN = 0;
	public final int ANIMATION_UP = 1;
	private ThreadHandler thdFlip = null;
	private EventHandler eventHandle = null;
	private AnimationType animationType = new AnimationType();
	
	public MenuList(Context context) {
		super(context);
		mContext = context;
		flipper = new ViewFlipper(context);
	}

	public MenuList(Context context, AttributeSet attrs){
		super(context, attrs);
		mContext = context;
		flipper = new ViewFlipper(context);
	}
	
	public MenuList(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		flipper = new ViewFlipper(context);
	}
	
	public void setMenuView(int nResId, Handler handler, int nWidth, int nHeight){
		if(null == mContext){
			return;
		}
		if(null != handler){
			eventHandle = new EventHandler(handler);
		}
		TextView tvTmp = new TextView(mContext);
		tvTmp.setLayoutParams(new LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		
		LinearLayout llmenu = new LinearLayout(mContext);
		llmenu.setLayoutParams(new LayoutParams(nWidth,nHeight));
		LayoutInflater layInflater = (LayoutInflater)mContext.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		if(null == layInflater.inflate(nResId, llmenu, true)){
        	Log.e(TAG,"LayoutInflater fail");
        	return;
        }
		
		flipper.setLayoutParams(new LayoutParams(nWidth,nHeight));
		flipper.addView(tvTmp, 0);
		flipper.addView(llmenu, 1);
		flipper.setInAnimation(animationType.inFromDownAnimation(mnAnimationDuration));
    	flipper.setOutAnimation(animationType.outToDownAnimation(mnAnimationDuration));
    	flipper.setVisibility(GONE);
    	this.addView(flipper);
	}
	
	public void showMenu(boolean bShow){
		if(bShow && 0 == flipper.getDisplayedChild()){
			flipper.setVisibility(VISIBLE);
			flipper.showNext();
		}
		if(!bShow && 0 != flipper.getDisplayedChild()){
			flipper.showNext();
			ListViewGone();
		}
	}
	
	public void setAnimationDuration(int nDuration){
		if( 0 >= nDuration || nDuration >= 10000){
			return;
		}
		mnAnimationDuration = nDuration;
	}
	
	public void setAnimation(int nAnimation){
		
		switch(nAnimation){
		case ANIMATION_UP:
			if(flipper != null){
	        	flipper.setInAnimation(animationType.inFromDownAnimation(mnAnimationDuration));
	        	flipper.setOutAnimation(animationType.outToDownAnimation(mnAnimationDuration));
	        }else{
	        	Log.e(TAG,"flipper is invalid");
	        }
			break;
		case ANIMATION_DOWN:
			if(flipper != null){
	        	flipper.setInAnimation(animationType.inFromTopAnimation(mnAnimationDuration));
	        	flipper.setOutAnimation(animationType.outToTopAnimation(mnAnimationDuration));
	        }else{
	        	Log.e(TAG,"flipper is invalid");
	        }
			break;
			default:
				if(flipper != null){
					flipper.setInAnimation(animationType.inFromDownAnimation(mnAnimationDuration));
		        	flipper.setOutAnimation(animationType.outToDownAnimation(mnAnimationDuration));
		        }else{
		        	Log.e(TAG,"flipper is invalid");
		        }
				break;
		}
	}

	private Handler updateHandler = new Handler() {
        @Override 
        public void handleMessage(Message msg) {
        	switch (msg.what) {
        	case 0:
        		if(null != flipper){
        			flipper.setVisibility(View.VISIBLE);
        		}
        		break;
        	case 1:
        		if(null != flipper){
        			flipper.setVisibility(View.GONE);
        		}
        		break;
        	}
        }
	};
	
	private void ListViewGone(){
		if( null != thdFlip){
			thdFlip = null;
		}
		
		thdFlip  = new ThreadHandler(new Runnable() {
            public void run() {
                try {
                	Thread.sleep(500);
					updateHandler.sendEmptyMessage(1);
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
		});
		thdFlip.start();
	}
	
	public void setViewTouchEvent(View view){
		if(null == eventHandle || null == view){
			return;
		}
		eventHandle.registerViewOnTouch(view);
	}
}
