package comp.hp.ij.mars.pandora;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class PandoraInfoWnd extends PandoraWnd{

	private final String TAG	= "PandoraInfoWnd";
	private final int m_nMainLayoutResId = R.layout.pandora_info;
	private final int mnHeaderLayoutResId = R.layout.pandora_header;
	
	public PandoraInfoWnd(Activity active, Handler handler, int nId){
		super(active, handler, nId);
		super.setLayoutResId(m_nMainLayoutResId, HIDE_VIEW, mnHeaderLayoutResId);
		super.setViewTouchEvent(R.id.tvInfoCreateAccount);
		super.setViewTouchEvent(R.id.tvInfoExistMember);
	}
	
	@Override
	protected void onClick(int resId) {
	
	}

	@Override
	protected void onClose() {

	}

	@Override
	protected void onShow() {	
		TextView tvHeader = (TextView)super.getApp().findViewById(R.id.tvPandoraHeaderLogout);
		if( null != tvHeader ){
			tvHeader.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	protected void onTouchDown(int resId) {
		switch(resId){
		case R.id.tvInfoCreateAccount:
			((TextView)super.getApp().findViewById(R.id.tvInfoCreateAccount)).setBackgroundResource(R.drawable.signin_btn_f);	
			break;
		case R.id.tvInfoExistMember:		
			((TextView)super.getApp().findViewById(R.id.tvInfoExistMember)).setBackgroundResource(R.drawable.signin_btn_f);
			break;
		}
	}

	@Override
	protected void onTouchUp(int resId) {
		switch(resId){
		case R.id.tvInfoCreateAccount:
			((TextView)super.getApp().findViewById(R.id.tvInfoCreateAccount)).setBackgroundResource(R.drawable.signin_btn);
			sendAppMsg(WND_MSG, WND_SHOW_ACCOUNT_WND, null );
			break;
		case R.id.tvInfoExistMember:		
			((TextView)super.getApp().findViewById(R.id.tvInfoExistMember)).setBackgroundResource(R.drawable.signin_btn);
			sendAppMsg(WND_MSG, WND_SHOW_LOGIN, null);
			break;
		}
	}
		
	private void launchBrowser() {		
		Intent i = new Intent(Intent.ACTION_VIEW); 
		Uri u = Uri.parse("http://www.pandora.com"); 
		i.setData(u); 
		super.getApp().startActivity(i);
		
	}	
}
