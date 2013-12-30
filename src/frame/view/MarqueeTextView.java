/**
 * @author jugo
 * @date 2009-12-03
 */
package frame.view;

import comp.hp.ij.common.service.pandora.util.Logger;

import android.content.Context;
import android.graphics.Rect;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

public class MarqueeTextView extends TextView {

	private final String TAG = "MarqueeTextView";
	private Scroller mSlr;
	private int mRndDuration = 20000;
	private boolean mPaused = false;
	private String mszText = null;
	private int mndistance = 0;

	
	public MarqueeTextView(Context context) {
		this(context, null);
	}

	public MarqueeTextView(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.textViewStyle);
	}

	public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setSingleLine();
		setEllipsize(null);
	}

/**
* begin to scroll the text from the original position
*/
	public boolean startScroll() {
		int nViewWidth = getViewWidth();
		int nTextWidth = calculateScrollingLen();
		if(nViewWidth >= nTextWidth ){
			return false;
		}
		
		setHorizontallyScrolling(true);
		mRndDuration = (nTextWidth * 100) / 2;
		mszText = this.getText().toString();
			
		String szTmp = mszText 
		+ System.getProperty("line.separator")
		+ System.getProperty("line.separator")
		+ System.getProperty("line.separator")
		+ "_" ;
		this.setText(szTmp);
		
		mndistance = (int)Layout.getDesiredWidth(mszText, this.getPaint());
		mndistance += 20;
			
		szTmp = mszText
		+ System.getProperty("line.separator")
		+ System.getProperty("line.separator")
		+ System.getProperty("line.separator")
		+ System.getProperty("line.separator")
		+ mszText;
		this.setText(szTmp);

		mPaused = true;
		
		resumeScroll();	
		return true;
}

	public void setViewWidth(int nPix){
		LayoutParams params = this.getLayoutParams();
		params.width = nPix;
		this.setLayoutParams(params);
	}
/**
* resume the scroll from the pausing point
*/
	public void resumeScroll() {

		if (!mPaused)
			return;
		setHorizontallyScrolling(true);

		if(null != mSlr){
			mSlr = null;
		}
		// use LinearInterpolator for steady scrolling
		mSlr = new Scroller(this.getContext(), new LinearInterpolator());
		setScroller(mSlr);

//		Log.d(TAG," mndistance" + mndistance + " mRndDuration" + mRndDuration);
		mSlr.startScroll(0, 0, mndistance , 0, mRndDuration);
		
		mPaused = false;
	}

	/**
	 * calculate the scrolling length of the text in pixel
	 *
	 * @return the scrolling length in pixels
	 */
	private int calculateScrollingLen() {
		TextPaint tp = getPaint();
		Rect rect = new Rect();
		String strTxt = getText().toString();
		tp.getTextBounds(strTxt, 0, strTxt.length(), rect);
		int scrollingLen = rect.width();// + getWidth();
		rect = null;
		return scrollingLen;
	}

	/**
	 * pause scrolling the text
	 */
	public void pauseScroll() {
		if (null == mSlr)
			return;

		if (mPaused)
			return;

		mPaused = true;

		if(mSlr.isFinished()){
			return;
		}
		mSlr.abortAnimation();
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		if (null == mSlr) return;
		if (mSlr.isFinished() && (!mPaused)) {
			mPaused = true;
			this.resumeScroll();
		} 
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
		if(hasWindowFocus){
			resumeScroll();
		}else{
			pauseScroll();
		}
	}
	
	public int getRndDuration() {
		return mRndDuration;
	}

	public void setRndDuration(int duration) {
		this.mRndDuration = duration;
	}

	public boolean isPaused() {
		return mPaused;
	}

	private int getViewWidth(){
		ViewGroup.LayoutParams params = this.getLayoutParams();
		int nWidth = params.width;
		return nWidth;
	}
	

	
}

