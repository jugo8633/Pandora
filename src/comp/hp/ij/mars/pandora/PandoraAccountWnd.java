package comp.hp.ij.mars.pandora;

import android.app.Activity;
import android.os.Handler;
import android.widget.TextView;

public class PandoraAccountWnd extends PandoraWnd{

//	private final String TAG	= "PandoraAccountWnd";
	private final int m_nMainLayoutResId = R.layout.pandora_account;
	private final int m_nMainLayoutResId_v = R.layout.pandora_account_v;
	
	public PandoraAccountWnd(Activity active, Handler handler, int id) {
		super(active, handler, id);
		super.initOrientation(m_nMainLayoutResId, m_nMainLayoutResId_v);
		super.setViewTouchEvent(R.id.tvCABtnOK);
	}

	@Override
	protected void onShow() {
		String szCode = application.getActivationCode();
		String szURL  = application.getActivationURL();
		setActionCode(szCode, szURL);
	}
	
	@Override
	protected void onClose() {		
	}
	
	@Override
	protected void onClick(int resId) {
	}

	@Override
	protected void onTouchDown(int resId) {
		switch(resId){
		case R.id.tvCABtnOK:
			((TextView)super.getApp().findViewById(R.id.tvCABtnOK)).setBackgroundResource(R.drawable.ok_btn_focus);	
			break;
		}
	}

	@Override
	protected void onTouchUp(int resId) {
		switch(resId){
		case R.id.tvCABtnOK:
			((TextView)super.getApp().findViewById(R.id.tvCABtnOK)).setBackgroundResource(R.drawable.ok_btn);
			sendAppMsg(WND_MSG, WND_BTN_OK, null );
			break;
		}
	}

	public void setActionCode(String szCode, String szURL){
		TextView tvTmp = (TextView)super.getApp().findViewById(R.id.tvBtnVisit);
		if(null != tvTmp && null != szURL){
			tvTmp.setText(szURL);
		}
		
		tvTmp = (TextView)super.getApp().findViewById(R.id.tvAccountActionCode);
		if(null != tvTmp && null != szCode){
			tvTmp.setText(szCode);
		}
	}
}
