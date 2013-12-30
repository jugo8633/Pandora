package frame.view;

import android.R;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;


public class FlipperTextView extends RelativeLayout{

	private final String TAG = "MarqueeTextView";
	private Context mContext = null;
	private AnimationType animationType = new AnimationType();
	private ViewFlipper flipper = null;
	private TextView[]arrTextView = new TextView[2];
	
	public FlipperTextView(Context context) {
		super(context);
		mContext = context;
		flipper = new ViewFlipper(context);
	}

	public FlipperTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		flipper = new ViewFlipper(context);
	}
	
	public FlipperTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		flipper = new ViewFlipper(context);
	}

	synchronized public void init(String szText, float ltextSize, int nWidth, int nHeight, int nDuration){
		if(null == mContext){
			return;
		}
		
		for(int i = 0; i < arrTextView.length; i++){
			arrTextView[i] = new TextView(mContext);
			arrTextView[i].setLayoutParams(new LayoutParams(
					nWidth,
					nHeight));
			arrTextView[i].setSingleLine();
			arrTextView[i].setGravity(Gravity.CENTER_VERTICAL );
			arrTextView[i].setText(szText);
			arrTextView[i].setTextSize(ltextSize);
			arrTextView[i].setTextColor(0xffffffff);
			arrTextView[i].setHintTextColor(0xffffffff);
		}
		
		flipper.setLayoutParams(new LayoutParams(nWidth,nHeight));
		for(int j = 0; j < arrTextView.length; j++ ){
			flipper.addView(arrTextView[j], j);
		}
		flipper.setInAnimation(animationType.inFromRightAnimation(nDuration));
    	flipper.setOutAnimation(animationType.outToLeftAnimation(nDuration));
    	flipper.setFlipInterval(nDuration * 2);
    	
    	this.addView(flipper);
    	
	}
	public void setText(){
		
	}

	public void startFlipping(){
		int nStrLen = getTextLen();
		int nWidth = getParentWidth();
		Log.d(TAG,"text length = " + nStrLen + " full width = " + nWidth);
		if(nWidth < nStrLen){
			flipper.startFlipping();
		}
	}
	
	public void stopFlipping(){
		if(flipper.isFlipping()){
			flipper.stopFlipping();
		}
	}
	
	/**
	* calculate the scrolling length of the text in pixel
	*
	* @return the scrolling length in pixels
	*/
	private int getTextLen() {
		TextPaint tp = arrTextView[0].getPaint();
		Rect rect = new Rect();
		String strTxt = arrTextView[0].getText().toString();
		tp.getTextBounds(strTxt, 0, strTxt.length(), rect);
		int scrollingLen = rect.width() + arrTextView[0].getWidth();
		rect = null;
		return scrollingLen;
	}
	
	private int getTextLen(String szText){
		TextPaint tp = arrTextView[0].getPaint();
		Rect rect = new Rect();
		tp.getTextBounds(szText, 0, szText.length(), rect);
		int scrollingLen = rect.width() + arrTextView[0].getWidth();
		rect = null;
		return scrollingLen;
	}
	
	private int getParentWidth(){
		ViewGroup.LayoutParams params = this.getLayoutParams();
		int nWidth = params.width;
		return nWidth;
	}
}
