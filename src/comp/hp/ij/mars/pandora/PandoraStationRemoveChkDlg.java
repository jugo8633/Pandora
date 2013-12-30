package comp.hp.ij.mars.pandora;

import java.util.ArrayList;

import comp.hp.ij.common.service.pandora.util.Logger;

import frame.event.EventMessage;
import frame.view.MarqueeTextView;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class PandoraStationRemoveChkDlg extends PandoraWnd{

	private final String TAG = "PandoraStationRemoveChkDlg";
	private final int	KEY_BACK		= 4;
	private Dialog		ChkDlg = null;
	private MarqueeTextView tvStationName = null;
	private static String mszStationName = null;
	
	public PandoraStationRemoveChkDlg(Activity active, Handler handler, int nId) {
		super(active, handler, nId);
		super.setLayoutResId(HIDE_VIEW, HIDE_VIEW, HIDE_VIEW);
		initDialog();		
		if(null != mszStationName){
			tvStationName.setText(mszStationName);
			tvStationName.startScroll();
		}
	}

	public void initDialog(){
	    ChkDlg = new Dialog(super.getApp());	
	    ChkDlg.getWindow().setBackgroundDrawableResource(R.color.Black);
	    ChkDlg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	    ChkDlg.setContentView(R.layout.pandora_station_remove_check_dlg);
	    ChkDlg.setOnKeyListener(new OnKeyListener(){
	        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
	            if(KEY_BACK == keyCode){
	                sendAppMsg(WND_MSG,WND_STOP, null);
	            }
	            return false;
	        }
	    });

	    super.RegisterEvent(ChkDlg.findViewById(R.id.tvStationRemoveChkBtnYes), super.EVENT_TOUCH);
	    super.RegisterEvent(ChkDlg.findViewById(R.id.tvStationRemoveChkBtnNo), super.EVENT_TOUCH);
	    tvStationName = (MarqueeTextView)ChkDlg.findViewById(R.id.tvStationRemoveList);
	}
	
	public Dialog getDialog(){
		return ChkDlg;
	}
	
	public void addStationName(String szStationName){
		if(null == tvStationName){
			return;
		}
		String szTmp = tvStationName.getText().toString();
		if(null != szTmp && szTmp.length() > 0){
			mszStationName = szTmp + "    " + szStationName;
		}else{
			mszStationName = szStationName;
		}
		tvStationName.setText(mszStationName);
	}
	
	public void startScollStationName(){
		tvStationName.setGravity(Gravity.CENTER_VERTICAL);
		if(!tvStationName.startScroll()) {
			tvStationName.setGravity(Gravity.CENTER);
		}
	}
	
	public void clearStationName(){
		tvStationName.setGravity(Gravity.CENTER_VERTICAL);
		tvStationName.pauseScroll();
		tvStationName.setText(null);
		mszStationName = null;
	}
	
	@Override
	protected void onClick(int nResId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onClose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onShow() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onTouchDown(int nResId) {
		(ChkDlg.findViewById(nResId)).setBackgroundResource(R.drawable.ok_btn_focus);
	}

	@Override
	protected void onTouchUp(int nResId) {
		(ChkDlg.findViewById(nResId)).setBackgroundResource(R.drawable.ok_btn);
		switch(nResId){
		case R.id.tvStationRemoveChkBtnYes:
			sendAppMsg(WND_MSG,EventMessage.WND_BTN_OK, null);
			break;
		case R.id.tvStationRemoveChkBtnNo:
			sendAppMsg(WND_MSG,WND_STOP, null);
			break;
		}	
	}

}
