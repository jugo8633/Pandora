package comp.hp.ij.mars.pandora;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import comp.hp.ij.common.service.pandora.util.Logger;
import comp.hp.ij.mars.pandora.PandoraSearchWnd.SearchItem;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Process;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;


public class PandoraInputWnd extends PandoraWnd{

	private final String TAG = "PandoraInputWnd";
	private final int	KEY_BACK		= 4;
	private Dialog		InputDlg = null;
	private AutoCompleteTextView autoTextView = null;
	private static String[]MUSIC = null;
	private static ArrayList<PandoraAutoCompleteData> musicItems = new ArrayList<PandoraAutoCompleteData>();
	private TextWatcher textWatcher = null;
	private Thread mThread = null;
	
	public PandoraInputWnd(Activity active, Handler handler, int nId){
		super(active, handler, nId);
		super.setLayoutResId(HIDE_VIEW, HIDE_VIEW, HIDE_VIEW);

		initDialog();
		super.RegisterEvent(InputDlg.findViewById(R.id.tvInputOK), super.EVENT_TOUCH);
		super.RegisterEvent(InputDlg.findViewById(R.id.tvInputCancel), super.EVENT_TOUCH);		
	}

	@Override
	protected void onShow() {
	}
	
	@Override
	protected void onClose() {
		if(null != InputDlg){
			if(InputDlg.isShowing()){
				InputDlg.dismiss();
				Log.i(TAG,"cancel dialog window");
			}
		}
		if(null != mThread){
			mThread = null;
		}
	}
	
	@Override
	protected void onClick(int resId) {

	}

	@Override
	protected void onTouchDown(int resId) {
		if(isBtn(resId)){
    		(InputDlg.findViewById(resId)).setBackgroundResource(R.drawable.ok_btn_focus);
    	}
	}

	@Override
	protected void onTouchUp(int resId) {
		if(isBtn(resId)){
    		(InputDlg.findViewById(resId)).setBackgroundResource(R.drawable.ok_btn);
    		switch(resId){
    		case R.id.tvInputOK:
    			String szSearchStr = getSearchString();
    			sendAppMsg(WND_MSG,WND_BTN_OK, szSearchStr);
    			break;
    		case R.id.tvInputCancel:
    			sendAppMsg(WND_MSG,WND_CLOSE_INPUT_WND, null);
    			break;
    		}	
    	}
	}
	
	private void initDialog(){
		InputDlg = new Dialog(super.getApp());	
		InputDlg.getWindow().setBackgroundDrawableResource(R.color.Black);
		InputDlg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		InputDlg.setContentView(R.layout.pandora_input_dialog);
		InputDlg.setOnKeyListener(new OnKeyListener(){
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if(KEY_BACK == keyCode){
					sendAppMsg(WND_MSG,WND_CLOSE_INPUT_WND, null);
				}
				return false;
			}
			});
		
		/**
		 * auto complete edit
		 */
		autoTextView = (AutoCompleteTextView) InputDlg.findViewById(R.id.etInputSearch);
	//	autoTextView.setInputType(InputType.TYPE_NULL);
		initTextWatcher();
	}
	
	private void initTextWatcher(){
		textWatcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            	if(null != mThread){
        			mThread = null;
        		}
            	autoTextView.dismissDropDown();
            	if(autoTextView.isPerformingCompletion()){
            		return;
            	}
                Logger.d("onTextChanged: [" + s + "]");            
                
                if (!"".equals(s)) {
                	getAutoCompleteStr();
                }
            }

			public void afterTextChanged(Editable s) {
				
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
        };
		autoTextView.addTextChangedListener(textWatcher);
	}
	
	public void getAutoCompleteStr(){
		if(null != mThread){
			mThread = null;
		}
		
		mThread = new Thread(){
			public void run()
	        { 
	        	try {
					sleep(100);
					Editable eb = autoTextView.getText();
					String szStr = eb.toString();
					if(null != szStr && szStr.length() > 0){
						sendAppMsg(WND_MSG,WND_TEXT_CHANGE, szStr);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        }
		};
		mThread.start();
	}
	
	public Dialog getDialog(){
		return InputDlg;
	}
	
	private boolean isBtn(int nId){
		if(nId == R.id.tvInputOK || nId == R.id.tvInputCancel ){
			return true;
		}
		return false;
	}
		
	public String getSearchString(){
		AutoCompleteTextView etTmp = (AutoCompleteTextView)InputDlg.findViewById(R.id.etInputSearch);
		if(null != etTmp){
			Editable ea = etTmp.getText();
			Log.d(TAG,"station search: " + ea.toString());
			return ea.toString();
		}
		return null;
	}	
	//synchronized
	synchronized private void updateAutoComplete(){
		if(null == autoTextView){
			return;
		}
		if( 0 >= autoTextView.getTextSize() ){
			return;
		}
		try{
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(super.getApp(), 
					R.layout.small_list_item_1, MUSIC);
			autoTextView.setAdapter(adapter);
			autoTextView.setThreshold(99999);
			autoTextView.showDropDown();
			autoTextView.setOnItemClickListener(clickListener);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	OnItemClickListener clickListener = new OnItemClickListener(){

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if( 0 > arg2 || musicItems.size() <= arg2){
				Log.e(TAG,"onItemClick fail, index = " + arg2 + " musicItems size = " + musicItems.size());
				return;
			}
			String szSearch     = musicItems.get(arg2).getItem();
			String szMusicToken = musicItems.get(arg2).getMusicToken();
			autoTextView.removeTextChangedListener(textWatcher);
			autoTextView.setText(szSearch);
			autoTextView.dismissDropDown();
			PandoraInputWnd.this.initTextWatcher();
			if(null != szMusicToken){
				sendAppMsg(WND_MSG,WND_CREATE_STATION, szMusicToken );
			}
			
		}
		
	};
	
	synchronized public void setMusicStr(ArrayList<PandoraAutoCompleteData> pmusicItems){
		if(null == autoTextView){
			return;
		}
		if( 0 >= autoTextView.getTextSize() || (pmusicItems.size() <= 0)){
			return;
		}
		if(null != pmusicItems){
			musicItems = pmusicItems;
			MUSIC = new String[musicItems.size()];
			for(int i = 0; i < musicItems.size(); i++){
				MUSIC[i] = musicItems.get(i).getDisplay();
			}
			if(MUSIC.length > 0){
				updateAutoComplete();
			}
		}
	}
	
}
