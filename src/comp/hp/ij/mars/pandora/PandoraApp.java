/**
 * @author jugo
 * @date 2009-12-01
 * @description activity 
 */

package comp.hp.ij.mars.pandora;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.widget.AutoCompleteTextView;

import comp.hp.ij.common.service.pandora.util.Logger;
import comp.hp.ij.mars.commui.CommActivity;

import frame.event.EventMessage;
import frame.view.AlarmDialog;
import frame.view.ProgressDialog;


public class PandoraApp extends CommActivity {
	
	private final String TAG = "PandoraApp";
	
	private final String CONFIG_FILE = "PandoraConf";
	private final String CONFIG_KEY_ACCOUNT = "ACCOUNT";
	private final String CONFIG_KEY_PASSWORD = "PASSWORD";
	private final String CONFIG_KEY_LAST_STATION_TOKEN = "LAST_STATION_TOKEN";
	private final String CONFIG_KEY_SAVEACCOUNT = "SAVEACCOUNT";
	
	public  HashMap<Integer,PandoraWnd>WndData = new HashMap<Integer,PandoraWnd>();;
    private boolean mbPassKey = false;
    private boolean mbIsShowPlayNowWnd = false;  
    private PandoraApplication application = null;
    
    private boolean mbShouldResetPauseButtonForPlayNowWnd = true;
    private boolean mbIsFromPlayNowWndWithEmptyStationList = false;
    private boolean mbIsRemoveStationFinished = false;
    private boolean mbIsExecuteLogout = false;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application =  (PandoraApplication)this.getApplication();
        getOrientation();
        try{	 
        	initWnd();
			if(savedInstanceState != null){  // rotate
				int nRunWnd = savedInstanceState.getInt("RunWnd");
				Log.i(TAG,"get instance run window =" + String.valueOf(nRunWnd));
				showWnd(nRunWnd);
			}else{
				showWnd(EventMessage.RUN_LOADWND);	
				((PandoraLoadWnd)WndData.get(EventMessage.RUN_LOADWND)).initProgressBar();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		application.initEventHandler(wndHandler);
	}
	
	@Override
	protected void onDestroy() {
		application.setSwitchUserIndex(-1);
		releaseWnd();
		if(this.isFinishing()){
			Log.i(TAG,"pandora application finish");	
			application.setCurrentRunWnd(EventMessage.RUN_LOADWND);
		}
		super.onDestroy();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		int nRunWnd = application.getCurrentRunWnd();
		outState.putInt("RunWnd", nRunWnd);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.i(TAG,"receive key down, keyCode=" + String.valueOf(keyCode));
		
		showAlarmDlg(false,null,null);
		showProgressDlg(false);
		
		application.PService.resetIdleTime();
		int nRunWnd = application.getCurrentRunWnd();
		
		switch(keyCode){
		case EventMessage.KEY_BACK:
			switch(nRunWnd){
			case EventMessage.RUN_USERWND:
				showWnd(EventMessage.RUN_STATIONWND);
				return true;
			case EventMessage.RUN_PLAYWND:
                if (0 == application.stationData.getStationCount()) {
                    application.PService.getStationList();
                } else {
                    showWnd(EventMessage.RUN_STATIONWND);
                }
				return true;
			case EventMessage.RUN_PLAYINFOWND:
				showWnd(EventMessage.RUN_PLAYWND);
				return true;
			case EventMessage.RUN_ACCOUNTWND:
				showWnd(EventMessage.RUN_LOGINWND);
				return true;
			case EventMessage.RUN_SEARCHWND:
				mbPassKey = true;
				showDialog(EventMessage.RUN_SEARCH_INPUTWND);
				return true;
			case EventMessage.RUN_STATIONWND:
			    //+++ Chance 2009-12-21 mantis 4877 +++//
			    //Logger.d("application.getStationRemoveState(): [" + application.getStationRemoveState() + "]");
			    if (application.getStationRemoveState()) {
			        ((PandoraStationWnd) WndData.get(EventMessage.RUN_STATIONWND)).removeStationFinish(true);
			        return true;
			    } else {
			        finish();
			        return super.onKeyDown(keyCode, event);
			    }
			    //--- Chance 2009-12-21 mantis 4877 ---//
			case EventMessage.RUN_LOGINWND:
				String szAccount = application.userData.getCurrLoginAccount();
				String szPassword = application.userData.getCurrLoginPassword();
				String szLastStationToken = application.userData.getCurrLastStationToken();
				boolean bSavePwd = application.userData.getCurrLoginSavePwd();
				setSaveCurrentLogin(szAccount, szPassword, szLastStationToken, bSavePwd ? "1" : "0");
				finish();
				return super.onKeyDown(keyCode, event);
			case EventMessage.RUN_INFOWND:
			case EventMessage.RUN_LOADWND:
				finish();
				return super.onKeyDown(keyCode, event);
			default:
				return true;
			}
		//	break;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.v(TAG,"onConfigurationChanged orientation = " + newConfig.orientation);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}

	public void showMainView(int nViewId){
		attachPrimaryView(nViewId);
	}
	
	public void showFootView(int nViewID){
		attachFooterBar(nViewID);
	}
	
	public void addMainView(int nViewId){
		attachPrimaryView(nViewId);
	}
	
	public void addFootView(int nViewId){
		attachFooterBar(nViewId);
	}
	
	public void hideFootView(){
		hideFooterBar();
	}
	
	public void addHeaderView(int nViewId){
		attachHeaderBar(nViewId);
	}
		
    private boolean checkLogin(String szAccount, String szPassword) {
        if (null == szAccount || null == szPassword) {
            return false;
        }
        if (0 >= szAccount.length() || 0 >= szPassword.length()) {
            return false;
        }
        return true;
    }
	
	private void getOrientation(){
		final Display display = getWindow().getWindowManager().getDefaultDisplay();
		if(-1 != application.getOrientation()){
			Log.v(TAG,"application.getOrientation = " + application.getOrientation());
			return;
		}
		
		int nHeight = display.getHeight();
		int nWidth  = display.getWidth();
		Log.v(TAG,"Display width = " + nWidth + " height = " + nHeight);
		if( nWidth > nHeight ){ 
			application.setOrientation(PandoraApplication.ORIENTATION_LAND);
			Log.v(TAG,"getOrientation: land");
		}else{ 
			application.setOrientation(PandoraApplication.ORIENTATION_PORT);
			Log.v(TAG,"getOrientation: port");
		}
	}
	
	public void initPlayData(String szStationToken){
		if(szStationToken == null){
			return;
		}
		
		if(null != application.playData){
			application.playData.clearPlayData();
		}
		application.PService.getPlaylist(szStationToken);
	}
	
	/**
	 * @author jugo
	 * @description init and function for handle view window
	 */
	private void initWnd(){
		releaseWnd();
		WndData.put(EventMessage.RUN_LOADWND, new PandoraLoadWnd(this,wndHandler,EventMessage.RUN_LOADWND));
		WndData.put(EventMessage.RUN_STATIONWND, new PandoraStationWnd(this,wndHandler,EventMessage.RUN_STATIONWND));
		WndData.put(EventMessage.RUN_PLAYWND, new PandoraPlayNowWnd(this,wndHandler,EventMessage.RUN_PLAYWND));
		WndData.put(EventMessage.RUN_USERWND, new PandoraUserWnd(this, wndHandler,EventMessage.RUN_USERWND));
		WndData.put(EventMessage.RUN_INFOWND, new PandoraInfoWnd(this, wndHandler, EventMessage.RUN_INFOWND));
		WndData.put(EventMessage.RUN_LOGINWND, new PandoraLoginWnd(this, wndHandler, EventMessage.RUN_LOGINWND));
		WndData.put(EventMessage.RUN_PLAYINFOWND, new PandoraPlaynowInfoWnd(this, wndHandler, EventMessage.RUN_PLAYINFOWND));
		WndData.put(EventMessage.RUN_ACCOUNTWND, new PandoraAccountWnd(this, wndHandler, EventMessage.RUN_ACCOUNTWND));
		WndData.put(EventMessage.RUN_SEARCHWND, new PandoraSearchWnd(this, wndHandler, EventMessage.RUN_SEARCHWND));
		WndData.put(EventMessage.RUN_SEARCH_INPUTWND, new PandoraInputWnd(this, wndHandler, EventMessage.RUN_SEARCH_INPUTWND));
		WndData.put(EventMessage.RUN_STATION_REMOVE_CHK_WND, new PandoraStationRemoveChkDlg(this, wndHandler, EventMessage.RUN_STATION_REMOVE_CHK_WND));
	}

	private void releaseWnd(){
		if(WndData != null){
			if(WndData.size() > 0){
				for(int i = (EventMessage.RUN_WND+1); i < EventMessage.RUN_MAX; i++){
					if(WndData.get(i) != null){
						Log.i(TAG,"release window ; " + i);
						WndData.get(i).closeWindow();
					}
				}
				WndData.clear();
			}
		}
	}

	private void showWnd(int nRunWnd){	
		application.setCurrentRunWnd(nRunWnd);
				
		if(WndData.containsKey(nRunWnd)){
			application.setCurrentRunWnd(nRunWnd);
			WndData.get(nRunWnd).showWindow(true);
		}else
		{
			Log.d(TAG,"show window fail, window not in container");
		}
	}
	
	
	//************************************************************
	// windows event handle
	// 
	//************************************************************
	private Handler wndHandler = new Handler() {
        @Override 
        public void handleMessage(Message msg) {
        	switch(msg.what){
        	case EventMessage.WND_MSG:
        		switch(msg.arg2){
        		case EventMessage.RUN_LOADWND:
        			onLoadWnd(msg.arg1);
        			break;
        		case EventMessage.RUN_INFOWND:
        			onInfoWnd(msg.arg1);
        			break;
        		case EventMessage.RUN_LOGINWND:
        			onLoginWnd(msg.arg1, msg.obj);
        			break;
        		case EventMessage.RUN_ACCOUNTWND:
        			onAccountWnd(msg.arg1);
        			break;
        		case EventMessage.RUN_STATIONWND:
        			onStationWnd(msg.arg1, msg.obj);
        			break;
        		case EventMessage.RUN_PLAYWND:
        			onPlayNow(msg.arg1);
        			break;
        		case EventMessage.RUN_PLAYINFOWND:
        			onPlayInfoWnd(msg.arg1);
        			break;
        		case EventMessage.RUN_SEARCH_INPUTWND:
        			onInputWnd(msg.arg1, msg.obj);
        			break;
        		case EventMessage.RUN_SEARCHWND:
        			onSearchWnd(msg.arg1, msg.obj);
        			break;
        		case EventMessage.RUN_USERWND:
        			onUserWnd(msg.arg1, msg.obj);
        			break;
        		case EventMessage.RUN_STATION_REMOVE_CHK_WND:
        			onStationRemoveChkDlg(msg.arg1, msg.obj);
        			break;
        		}
        		break;
        	case EventMessage.SERVICE_MSG:
        		onService(msg.arg1, msg.obj);
        		break;
        	case EventMessage.SHOW_PROGRESS_DLG:
    			onProgressDlg(msg.arg1);
    			break;
    		case EventMessage.SHOW_ALARM_DLG:
    			onAlarmDlg(msg.arg1);
    			break;	
        	}
        }
	};
	
	private void onLoadWnd(int nMsg){
		switch(nMsg){
		case EventMessage.WND_SHOW:
			
			boolean bIsServiceReady = false;
			if(null != application.PService){
				bIsServiceReady = application.PService.isServiceReady();
			}
			
			Log.i(TAG,"[WND_SHOW] pandora loading window show");
			if(null != WndData.get(EventMessage.RUN_LOADWND)){
				int nPosi = ((PandoraLoadWnd)WndData.get(EventMessage.RUN_LOADWND)).getProgressPosition();
				Log.i(TAG,"loading position : " + nPosi);
				if( 0 < nPosi && nPosi < 100){
					((PandoraLoadWnd)WndData.get(EventMessage.RUN_LOADWND)).runLoading();
					return;
				}
				if(bIsServiceReady){
					((PandoraLoadWnd)WndData.get(EventMessage.RUN_LOADWND)).runLoading();
					application.PService.isAssociated();
				}else{
					new Thread()
				    { 
				      public void run()
				      { 
				      	try
				      	{ 
				      		int nCount = 0;
				      		while(true){
				      			if(null != application.PService){
				      				if(application.PService.isServiceReady() || nCount > 5){
				      					break;
				      				}
				      			}
				      			sleep(1000);
				      			nCount++;
				      		}
				      	}
				      	catch (Exception e)
				      	{
				      		e.printStackTrace();
				      	}finally{	
				      		if(application.PService.isServiceReady()){
				      			((PandoraLoadWnd)WndData.get(EventMessage.RUN_LOADWND)).runLoading();
				      			application.PService.isAssociated();
							}else{
								Log.e(TAG,"pandora service not ready, init timeout");
							}
				      	}
				      }
				    }.start();
				}
			}
			break;
        case EventMessage.WND_STOP:
            Log.i(TAG, "[WND_STOP] pandora loading window progress stop");
            PandoraWnd pandoraWnd = WndData.get(EventMessage.RUN_LOADWND);
            if (null != pandoraWnd) {
                if (!((PandoraLoadWnd) pandoraWnd).getIsGetStationListSuccess()) {
                    ((PandoraLoadWnd) pandoraWnd).setInfo("Connecting to the internet fail...");
                }
                ((PandoraLoadWnd) pandoraWnd).setRunStop();
            }
            break;
		case EventMessage.WND_EXCP:
			Log.i(TAG,"[WND_EXCP] pandora loading window progress Exception !");
			break;
		}
	}
	
	private void onInfoWnd(int nMsg){
		switch(nMsg){
		case EventMessage.WND_SHOW:
			Log.i(TAG,"[WND_SHOW] pandora info window show");
			break;
		case EventMessage.WND_EXCP:
			Log.i(TAG,"[WND_EXCP] pandora info window progress Exception !");
			break;
		case EventMessage.WND_SHOW_LOGIN:
			WndData.get(EventMessage.RUN_INFOWND).showWindow(false);
			showWnd(EventMessage.RUN_LOGINWND);
			break;
		case EventMessage.WND_SHOW_ACCOUNT_WND:
			this.showProgressDlg(true);
			application.setActivationCode(null, null);
			application.PService.generateActivationCode();
			break;
		}
	}
	
	private void onLoginWnd(int nMsg, Object objData){
		switch(nMsg){
		case EventMessage.WND_SHOW:
		    PandoraLoginWnd pandoraLoginWnd = (PandoraLoginWnd) getWindow(EventMessage.RUN_LOGINWND);
            if (isSaveAccount()) {
                String szAccount = null, szPassword = null;
                szAccount = getSavedAccount();
                szPassword = getSavedPassword();
                if (null == szAccount) {
                    return;
                }
                if (null != pandoraLoginWnd) {
                    if (null != szPassword && 0 < szPassword.length()) {
                        pandoraLoginWnd.initConf(true, szAccount, szPassword);
                    } else {
                        pandoraLoginWnd.initConf(false, szAccount, szPassword);
                    }
                }
            }
            
            if (null != pandoraLoginWnd) {
                String szAccount = getSavedAccount();
                String szLastStationToken = getSavedLastStationToken();
                pandoraLoginWnd.setLastStationInfo(szAccount, szLastStationToken);
            }
            
            Log.i(TAG, "[WND_SHOW] pandora login window show");
            break;
		case EventMessage.WND_EXCP:
			Log.i(TAG,"[WND_EXCP] pandora login window progress Exception !");
			break;
		case EventMessage.WND_BTN_OK:
            showProgressDlg(true);
            application.setCurrentPlayStation(-1);
            application.stationData.setCurrentStationToken(null);
            application.stationData.setCurrentStationName(null);
            String[] aszLogin = (String[]) objData;
            if (null != aszLogin) {
                String szAccount = aszLogin[0];
                String szPassword = aszLogin[1];
                Log.i(TAG, "get login account: [" + szAccount + "] get password: [" + szPassword + "]");
                if (checkLogin(szAccount, szPassword)) { // login account valid
                    if (null != application.PService) {
                        application.PService.doLogin(szAccount, szPassword);
                    }
                } else { // login account invalid
                    showProgressDlg(false);
                    showAlarmDlg(true, null, "Login Account or Password invalid");
                }

                setSaveCurrentLogin(aszLogin[0], aszLogin[1], aszLogin[2], aszLogin[3]);
            } else {
                showAlarmDlg(true, null, "Login Fail");
            }
            break;
		}
	}

    private boolean isSaveAccount() {
        boolean bSave = false;
        application.sharedData.initPreferences(CONFIG_FILE);
        String szIsSave = application.sharedData.getValue(CONFIG_KEY_SAVEACCOUNT);
        application.sharedData.close();
        if (null == szIsSave) {
            return false;
        }
        if (szIsSave.equals(EventMessage.TRUE)) {
            bSave = true;
        } else {
            bSave = false;
        }
        return bSave;
    }
	
    private String getSavedAccount() {
        application.sharedData.initPreferences(CONFIG_FILE);
        String szAccount = application.sharedData.getValue(CONFIG_KEY_ACCOUNT);
        application.sharedData.close();
        return szAccount;
    }

    private String getSavedPassword() {
        application.sharedData.initPreferences(CONFIG_FILE);
        String szPassword = application.sharedData.getValue(CONFIG_KEY_PASSWORD);
        application.sharedData.close();
        return szPassword;
    }

    private String getSavedLastStationToken() {
        application.sharedData.initPreferences(CONFIG_FILE);
        String szLastStationToken = application.sharedData.getValue(CONFIG_KEY_LAST_STATION_TOKEN);
        application.sharedData.close();
        return szLastStationToken;
    }
    
    private String getSavedIsSavePassword() {
        application.sharedData.initPreferences(CONFIG_FILE);
        String szIsSavePassword = application.sharedData.getValue(CONFIG_KEY_SAVEACCOUNT);
        application.sharedData.close();
        return szIsSavePassword;
    }
	
    private void setSaveCurrentLogin(String szAccount, String szPassword, String szLastStationToken, String szSave) {
        Logger.d("szAccount [" + szAccount
                + "] szPassword [" + szPassword
                + "] szLastStationToken [" + szLastStationToken
                + "] szSave [" + szSave + "]");

        if (null == szSave) {
            return;
        }
        application.sharedData.initPreferences(CONFIG_FILE);
        
        if (szSave.equals(EventMessage.TRUE)) { // save password
            application.sharedData.setValue(CONFIG_KEY_ACCOUNT, szAccount);
            application.sharedData.setValue(CONFIG_KEY_PASSWORD, szPassword);
            application.sharedData.setValue(CONFIG_KEY_LAST_STATION_TOKEN, szLastStationToken);
            application.sharedData.setValue(CONFIG_KEY_SAVEACCOUNT, EventMessage.TRUE);
            application.userData.setCurrLogin(szAccount, szPassword, szLastStationToken, true);
        } else {
            application.sharedData.setValue(CONFIG_KEY_ACCOUNT, szAccount);
            application.sharedData.setValue(CONFIG_KEY_LAST_STATION_TOKEN, szLastStationToken);
            application.sharedData.setValue(CONFIG_KEY_SAVEACCOUNT, EventMessage.FALSE);
            application.userData.setCurrLogin(szAccount, szPassword, szLastStationToken, false);
        }
        application.sharedData.close();
    }
	
	private void onAccountWnd(int nMsg){
		switch(nMsg){
		case EventMessage.WND_SHOW:
			break;
		case EventMessage.WND_BTN_OK:
			if( null != application.PService){
				String szCode = application.getActivationCode();
				if(null != szCode){
					this.showProgressDlg(true);
					application.PService.doDeviceLogin();
				}
			}else{
				showWnd(EventMessage.RUN_LOGINWND);
			}
			break;
		}
	}
	
	private void onStationWnd(int nMsg, Object objData){
		String szMsg = null;
		String szStationToken = null;
		
		switch(nMsg){
		case EventMessage.WND_SHOW:
			showProgressDlg(false);
			Log.i(TAG,"[WND_SHOW] pandora station window show");
			break;
		case EventMessage.WND_STOP:
			Log.i(TAG,"[WND_STOP] pandora station window progress stop");
			break;
		case EventMessage.WND_EXCP:
			szMsg = (String)objData;
			showAlarmDlg(true,null,szMsg);
			Log.i(TAG,"[WND_EXCP] pandora station window progress Exception !");
			break;
		case EventMessage.WND_ITEM_CLICK:
            Log.i(TAG, "[WND_ITEM_CLICK] pandora station window station item click");
            String[] arrPlayStation = new String[2];
            arrPlayStation = (String[]) objData;
            if (null == arrPlayStation) {
                return;
            }

            int nCurrentSelected = Integer.valueOf(arrPlayStation[0]);
            if (-1 == nCurrentSelected || nCurrentSelected >= application.stationData.getStationCount()) {
                Log.d(TAG, "stationData invalid");
                return;
            }
			
			szStationToken  = arrPlayStation[1];	
            if (null == szStationToken) {
                Log.d(TAG, "StationToken is null");
                return;
            }

            String sCurrentStationToken = application.stationData.getCurrStationToken();
            mbShouldResetPauseButtonForPlayNowWnd = szStationToken.equals(sCurrentStationToken) ? false : true;
            Logger.d("mbShouldResetPauseButtonForPlayNowWnd [" + mbShouldResetPauseButtonForPlayNowWnd + "]");
			
			application.userData.setCurrLastStationToken(szStationToken);
            String szAccount = application.userData.getCurrLoginAccount();
            String szPassword = application.userData.getCurrLoginPassword();
            String szLastStationToken = application.userData.getCurrLastStationToken();
            boolean bIsSavePwd = application.userData.getCurrLoginSavePwd();
            application.userData.addUserData(szAccount, szAccount,
                    szPassword, szLastStationToken, bIsSavePwd);
            setSaveCurrentLogin(szAccount, szPassword, szLastStationToken, bIsSavePwd ? EventMessage.TRUE : EventMessage.FALSE);
			
            this.showProgressDlg(true);
            initPlayData(szStationToken);
			application.stationData.setCurrentStationToken(szStationToken);
			mbIsShowPlayNowWnd = true;	
			break;
		case EventMessage.WND_SHOW_USER_WND:
			Log.i(TAG,"[WND_SHOW_USER_WND] pandora station window run switch user");
			showWnd(EventMessage.RUN_USERWND);
			break;
		case EventMessage.WND_FINISH:
			finish();
			break;
		case EventMessage.WND_CREATE_STATION:
			if(null != application.stationData){
				int nStationCount;
				nStationCount = application.stationData.getStationCount();
				if(100 <= nStationCount){
					szMsg = this.getString(R.string.station_limit);
					showAlarmDlg(true,null,szMsg);
					return;
				}
			}
			((PandoraStationWnd) WndData.get(EventMessage.RUN_STATIONWND)).clearStationStatus();
			showDialog(EventMessage.RUN_SEARCH_INPUTWND);
			break;
		case EventMessage.WND_LOGOUT:
		    executeLogout();
			break;
		case EventMessage.WND_REMOVE_STATION:
			int nDelCount = application.stationData.getStationDeletedCount();
			if( 0 >= nDelCount ){
				((PandoraStationWnd)WndData.get(EventMessage.RUN_STATIONWND)).removeStationFinish(true);
				return;
			}
			
			PandoraStationRemoveChkDlg pandoraStationRemoveChkDlg = (PandoraStationRemoveChkDlg) WndData.get(EventMessage.RUN_STATION_REMOVE_CHK_WND);
			pandoraStationRemoveChkDlg.initDialog();
			for(int i=0; i < application.stationData.getStationCount(); i++){
				if(application.stationData.getStationDeleted(i)){
					String szStationName = application.stationData.getStationName(i);
					if(null != szStationName){
					    pandoraStationRemoveChkDlg.addStationName(szStationName);
					}else{
						continue;
					}
				}
			}
			pandoraStationRemoveChkDlg.startScollStationName();
					
			showDialog(EventMessage.RUN_STATION_REMOVE_CHK_WND);
		/*	showProgressDlg(true);
			if(null == application.stationData){
				showProgressDlg(false);
				return;
			}
			if(0 >= application.stationData.getStationDeletedCount()){
				showProgressDlg(false);
				return;
			}
			for(int i=0; i < application.stationData.getStationCount(); i++){
				if(application.stationData.getStationDelected(i)){
					szStationToken = application.stationData.getStationToken(i);
					if(null != application.PService && null != szStationToken){
						application.stationData.setStationDeleted(i, false);
						application.PService.deleteStation(szStationToken);
						break;
					}else{
						continue;
					}
				}
			} */
			break;
		}
	}
	
	private void onStationRemoveChkDlg(int nMsg, Object objData){
		switch(nMsg){
		case EventMessage.WND_SHOW:
			showProgressDlg(false);
			Log.i(TAG,"[WND_SHOW] pandora station remove chk window show");
			break;
		case EventMessage.WND_STOP:
			removeDialog(EventMessage.RUN_STATION_REMOVE_CHK_WND);
			((PandoraStationRemoveChkDlg)WndData
					.get(EventMessage.RUN_STATION_REMOVE_CHK_WND))
					.clearStationName();
			((PandoraStationWnd)WndData.get(EventMessage.RUN_STATIONWND)).removeStationFinish(true);
			Log.i(TAG,"[WND_STOP] pandora station remove chk window progress stop");
			break;
		case EventMessage.WND_EXCP:
			Log.i(TAG,"[WND_EXCP] pandora station remove chk window progress Exception !");
			break;
		case EventMessage.WND_BTN_OK:
			showProgressDlg(true);
			removeDialog(EventMessage.RUN_STATION_REMOVE_CHK_WND);
			((PandoraStationRemoveChkDlg)WndData
					.get(EventMessage.RUN_STATION_REMOVE_CHK_WND))
					.clearStationName();
			if(null == application.stationData){
				showProgressDlg(false);
				return;
			}
			if(0 >= application.stationData.getStationDeletedCount()){
				showProgressDlg(false);
				return;
			}
			for(int i=0; i < application.stationData.getStationCount(); i++){
				if(application.stationData.getStationDeleted(i)){
					String szStationToken = application.stationData.getStationToken(i);
					if(null != application.PService && null != szStationToken){
						application.stationData.setStationDeleted(i, false);
						application.PService.deleteStation(szStationToken);
						break;
					}else{
						continue;
					}
				}
			}
			break;
		}
	}
	
	private void onPlayNow(int nMsg){
		switch(nMsg){
		case EventMessage.WND_SHOW:
			Log.i(TAG,"[WND_SHOW] pandora playnow window show");
			break;
		case EventMessage.WND_STOP:
            if (0 == application.stationData.getStationCount()) {
                if (null != application.PService) {
                    mbIsFromPlayNowWndWithEmptyStationList = true;
                    application.PService.getStationList();
                } else {
                    Log.e(TAG, "pandora service invalid");
                }
            } else {
                showWnd(EventMessage.RUN_STATIONWND);
            }
			break;
		case EventMessage.WND_PLAY_NEXT_SNOG:
			if(application.PService != null){
				this.showProgressDlg(true);
				application.PService.skipSong();
			}
			break;
		case EventMessage.WND_THUMB_UP:
			if(application.PService != null){
				showProgressDlg(true);
				application.PService.thumbsUp();
			}
			break;
		case EventMessage.WND_THUMB_DOWN:
			if(application.PService != null){
				showProgressDlg(true);
				application.PService.thumbsDown();
			}
			break;
		case EventMessage.WND_PAUSE_SONG:
			if(application.PService != null){
				application.PService.pauseMusic();
			}
			break;
		case EventMessage.WND_RESUME_SONG:
			if(application.PService != null){
				application.PService.resumeMusic();	
			}
			break;
		case EventMessage.WND_WHY_PLAY:
			if(null != application.PService){
				showProgressDlg(true);
				application.PService.explainTrack();
			}
			break;
		case EventMessage.WND_BOOK_MARK_ARTIST:
			if(null != application.PService){
				showProgressDlg(true);
				application.PService.bookmarkArtist();
			}
			break;
		case EventMessage.WND_BOOK_MARK_SONG:
			if(null != application.PService){
				showProgressDlg(true);
				application.PService.bookmarkSong();
			}
			break;
		case EventMessage.WND_SLEEP_SONG:
			if(null != application.PService){
				showProgressDlg(true);
				application.PService.sleepSong();
			}
			break;	
		case EventMessage.WND_LOGOUT:
		    executeLogout();
			break;
		}
	}
	
	private void onUserWnd(int nMsg, Object objData){
		switch(nMsg){
		case EventMessage.WND_SHOW:	
			break;
		case EventMessage.WND_NEW_USER:
			this.showProgressDlg(true);
			application.setSwitchUserIndex(-2);
			application.PService.stopMusic();
			//application.PService.doLogout();
		//	this.showWnd(EventMessage.RUN_INFOWND);
			break;
        case EventMessage.WND_SWITCH_USER:
            showProgressDlg(true);
            if (null != application.PService) {
                String szIndex = (String) objData;
                int nIndex = Integer.valueOf(szIndex);
                String szCurrAccount = application.userData.getCurrLoginAccount();
                if (null == szCurrAccount) {
                    szCurrAccount = getSavedAccount();
                }
                String szSwitchAccount = null;
                if (null != szCurrAccount) {
                    szSwitchAccount = application.userData.getUserAccount(nIndex);
                    if (szCurrAccount.equals(szSwitchAccount)) {
                        this.showWnd(EventMessage.RUN_STATIONWND);
                        return;
                    }
                }
                application.setSwitchUserIndex(nIndex);
                application.PService.stopMusic();
                // application.PService.doLogout();
            }
            break;
		case EventMessage.WND_REMOVE_USER:
			removeUser();
			((PandoraUserWnd)WndData.get(EventMessage.RUN_USERWND)).updateUserList();
			break;
		}
	}
	
	private void onService(int nMsg, Object objData){
	    int nRunWnd = application.getCurrentRunWnd();

	    String szResult = null;
		String szStationToken = null;
		
		PandoraWnd pandoraWnd = null;
		
		switch(nMsg){
		case EventMessage.SERVICE_READY:
			break;
		case EventMessage.SERVICE_LOGIN_SUCCESS:
			Log.i(TAG,"[SERVICE] pandora login success");
			/**
			 * @save to multi user
			 */			
			if (EventMessage.RUN_LOGINWND == nRunWnd || EventMessage.RUN_USERWND == nRunWnd) {
    			if (application.userData.getCurrLoginSavePwd()) {
                    application.userData.addUserData(application.userData.getCurrLoginAccount(),
                            application.userData.getCurrLoginAccount(),
                            application.userData.getCurrLoginPassword(),
                            application.userData.getCurrLastStationToken(), true);
                } else {
                    application.userData.addUserData(application.userData.getCurrLoginAccount(),
                            application.userData.getCurrLoginAccount(),
                            application.userData.getCurrLoginPassword(),
                            application.userData.getCurrLastStationToken(), false);
                }
			} else if (EventMessage.RUN_LOADWND == nRunWnd) {
                setSaveCurrentLogin(getSavedAccount(), getSavedPassword(),
                        getSavedLastStationToken(), getSavedIsSavePassword());
			} else {
			    Logger.e("Where are you come from? [" + nRunWnd + "]");
			}
			
            if (null != application.PService) {
                application.stationData.clearStationData();
                application.PService.getStationList();
            } else {
                Log.e(TAG, "pandora service invalid");
            }
			break;
        case EventMessage.SERVICE_LOGIN_FAIL:
            application.userData.setCurrLogin(null, null, null, false);
            pandoraWnd = WndData.get(EventMessage.RUN_LOADWND);
            if (null != pandoraWnd) {
                ((PandoraLoadWnd) pandoraWnd).setInfo("Connecting to the internet fail...");
                ((PandoraLoadWnd) pandoraWnd).setRunStop();
            }
            showWnd(EventMessage.RUN_INFOWND);
            String szState = (String) objData;
            showProgressDlg(false);
            showAlarmDlg(true, null, szState);
            break;
		case EventMessage.SERVICE_STATION_GET_SUCCESS:
            szResult = (String) objData;
            int nStationCount = Integer.parseInt(szResult);
            pandoraWnd = WndData.get(EventMessage.RUN_LOADWND);
            if (null != pandoraWnd) {
                ((PandoraLoadWnd) pandoraWnd).setIsGetStationListSuccess(true);
                ((PandoraLoadWnd) pandoraWnd).setRunStop();
                ((PandoraLoadWnd) pandoraWnd).showWindow(false);
            }
            if (0 == nStationCount) { // no station, run create station window
                application.stationData.clearStationData();
                showWnd(EventMessage.RUN_STATIONWND);
                showDialog(EventMessage.RUN_SEARCH_INPUTWND);
                return;
            }
            
            String sLastStationToken = application.userData.getCurrLastStationToken();
            Logger.d("sLastStationToken [" + sLastStationToken + "]");
            application.stationData.setCurrentStationToken(sLastStationToken);
            String sLastStationName = application.stationData.getStationName(sLastStationToken);
            Logger.d("sLastStationName [" + sLastStationName + "]");
            application.stationData.setCurrentStationName(sLastStationName);

            szStationToken = application.stationData.getCurrStationToken();
            Logger.d("SERVICE_STATION_GET_SUCCESS, szStationToken [" + szStationToken + "]");
            
            if (null != szStationToken) {
                if (mbIsFromPlayNowWndWithEmptyStationList) {
                    mbIsFromPlayNowWndWithEmptyStationList = false;
                    showWnd(EventMessage.RUN_STATIONWND);
                    return;
                }
                
                if (mbIsRemoveStationFinished) {
                    mbIsRemoveStationFinished = false;
                    showWnd(EventMessage.RUN_STATIONWND);
                    return;
                }
                
                initPlayData(szStationToken);
                mbIsShowPlayNowWnd = true;
            } else {
                showWnd(EventMessage.RUN_STATIONWND);
            }
            break;
        case EventMessage.SERVICE_UPDATE_PLAYLIST:
            showProgressDlg(false);
            szResult = (String) objData;
            if ("FAIL".equals(szResult)) {
                showAlarmDlg(true, null, "Run Play now fail");
                return;
            }
            
            if (mbShouldResetPauseButtonForPlayNowWnd) {
                ((PandoraPlayNowWnd) WndData.get(EventMessage.RUN_PLAYWND)).resetPauseButton();
            }
            mbShouldResetPauseButtonForPlayNowWnd = true;
            
            if (EventMessage.RUN_PLAYWND != nRunWnd) {
                if (mbIsShowPlayNowWnd) {
                    mbIsShowPlayNowWnd = false;
                    showWnd(EventMessage.RUN_PLAYWND);
                }
            } else {
                ((PandoraPlayNowWnd) WndData.get(EventMessage.RUN_PLAYWND)).updatePlaynowList();
            }
            break;
		case EventMessage.SERVICE_UPDATE_CURRENT_POSITION: {
		    //Logger.d("EventMessage.SERVICE_UPDATE_CURRENT_POSITION");
		    if (EventMessage.RUN_PLAYWND == nRunWnd) {
		        //Logger.d("Need to update progress bar");
		        int iIndexNowPlay = application.playData.getNowPlayIndex();
		        String sCurrentPosition = (String) objData;
		        application.playData.setCurrentPosition(iIndexNowPlay, sCurrentPosition);
		        ((PandoraPlayNowWnd) WndData.get(EventMessage.RUN_PLAYWND)).updateProgress();
		    }
		    break;
		}
		case EventMessage.SERVICE_POSITIVE_FEEDBACK_SUCCESS: // thumb up
			if(null != application.playData){
				application.playData.setThumbUp(true);
				((PandoraPlayNowWnd) WndData.get(EventMessage.RUN_PLAYWND)).updatePlaynowList();
			}
			showProgressDlg(false);
			break;
		case EventMessage.SERVICE_NEGATIVE_FEEDBACK_SUCCESS: // thumb down
			if(null != application.playData){
				application.playData.setThumbUp(false);
				
                ArrayList<String> alMessagesReturn = (ArrayList<String>) objData;
                boolean isReachSkipLimitation = Boolean.parseBoolean(alMessagesReturn.get(0));
                if (isReachSkipLimitation) {
                    Logger.d("Reach skip limitation");
                    Message message = new Message();
                    message.what = EventMessage.SERVICE_MSG;
                    message.arg1 = EventMessage.SERVICE_REACH_SKIP_LIMITATION;
                    message.obj  = alMessagesReturn.get(1);
                    wndHandler.sendMessage(message);
                } else {
                    Logger.d("Not reach skip limitation yet");
                }
                ((PandoraPlayNowWnd) WndData.get(EventMessage.RUN_PLAYWND)).updatePlaynowList();
			}
			showProgressDlg(false);
			break;
		case EventMessage.SERVICE_BOOKMARK_SUCCESS:
			showProgressDlg(false);
			break;
		case EventMessage.SERVICE_SLEEP_SONG_SUCCESS:
            ArrayList<String> alMessagesReturn = (ArrayList<String>) objData;
            boolean isReachSkipLimitation = Boolean.parseBoolean(alMessagesReturn.get(0));
            if (isReachSkipLimitation) {
                Logger.d("Reach skip limitation");
                Message message = new Message();
                message.what = EventMessage.SERVICE_MSG;
                message.arg1 = EventMessage.SERVICE_REACH_SKIP_LIMITATION;
                message.obj  = alMessagesReturn.get(1);
                wndHandler.sendMessage(message);
            } else {
                Logger.d("Not reach skip limitation yet");
            }
            
			showProgressDlg(false);
			break;
		case EventMessage.SERVICE_EXPLAIN_TRACK_SUCCESS:
			showProgressDlg(false);
			showWnd(EventMessage.RUN_PLAYINFOWND);
			if(null != application.PService){
				String szExplain = (String)objData;//PService.getExplainStr();
				if(null != WndData.get(EventMessage.RUN_PLAYINFOWND)){
					((PandoraPlaynowInfoWnd)WndData.get(EventMessage.RUN_PLAYINFOWND)).setExplain(szExplain);
					if( null != application.playData ){
						int nIndex = application.playData.getPlayCount() - 1;
						((PandoraPlaynowInfoWnd)WndData.get(EventMessage.RUN_PLAYINFOWND)).setSongInfo(
								application.playData.getStationName(nIndex), 
								application.playData.getArtistName(nIndex), 
								application.playData.getSongName(nIndex), 
								application.playData.getAlbumName(nIndex));
						String szURL = application.playData.getAlbumArt(nIndex);
						((PandoraPlaynowInfoWnd)WndData.get(EventMessage.RUN_PLAYINFOWND)).setAlbumArt(szURL);
					}
				}
			}
			break;
		case EventMessage.SERVICE_NOT_ASSOCIATED:
			((PandoraLoadWnd)WndData.get(EventMessage.RUN_LOADWND)).setRunStop();
			showWnd(EventMessage.RUN_INFOWND);
			break;
		case EventMessage.SERVICE_SEARCH_RESULT_SUCCESS:
			if( null != application.PService ){
				if( application.PService.getSearchResultArtist().size() > 0 || 
						application.PService.getSearchResultSongArtist().size() > 0 ){
					((PandoraSearchWnd)WndData.get(EventMessage.RUN_SEARCHWND)).initData(application.PService);
					if( (application.PService.getSearchResultArtist().size() + application.PService.getSearchResultSongArtist().size()) == 1 ){
						String szMusicToken = ((PandoraSearchWnd)WndData.get(EventMessage.RUN_SEARCHWND)).getMusicToken(0);
						if( null != szMusicToken && null != application.PService){
							application.PService.createStation(szMusicToken);
						}
					}else{
						showProgressDlg(false);
						showWnd(EventMessage.RUN_SEARCHWND);
					}
				}else{
					showProgressDlg(false);
					showWnd(EventMessage.RUN_STATIONWND);
				}
			}
			break;
		case EventMessage.SERVICE_CREATE_STATION_SUCCESS: {
            szStationToken = application.stationData.getCurrStationToken();
            if (null == szStationToken) {
                showProgressDlg(false);
                return;
            }
            
            application.userData.setCurrLastStationToken(szStationToken);
            String szAccount = application.userData.getCurrLoginAccount();
            String szPassword = application.userData.getCurrLoginPassword();
            String szLastStationToken = application.userData.getCurrLastStationToken();
            boolean bIsSavePwd = application.userData.getCurrLoginSavePwd();
            application.userData.addUserData(szAccount, szAccount,
                    szPassword, szLastStationToken, bIsSavePwd);
            setSaveCurrentLogin(szAccount, szPassword, szLastStationToken, bIsSavePwd ? EventMessage.TRUE : EventMessage.FALSE);
            
            showProgressDlg(true);
            initPlayData(szStationToken);
            showWnd(EventMessage.RUN_PLAYWND);
            break;
		}
		case EventMessage.SERVICE_REMOVE_STATION_SUCCESS:
            if (0 >= application.stationData.getStationDeletedCount()) {
                if (EventMessage.RUN_STATIONWND == nRunWnd) {
                    if (null != application.PService) {
                        application.stationData.clearStationData();
                        mbIsRemoveStationFinished = true;
                        application.PService.getStationList();
                    } else {
                        Log.e(TAG, "pandora service invalid");
                    }
                }
                ((PandoraStationWnd) WndData.get(EventMessage.RUN_STATIONWND)).removeStationFinish(false);
            } else {
                for (int i = 0; i < application.stationData.getStationCount(); i++) {
                    if (application.stationData.getStationDeleted(i)) {
                        szStationToken = application.stationData.getStationToken(i);
                        if (null != application.PService && null != szStationToken) {
                            application.stationData.setStationDeleted(i, false);
                            application.PService.deleteStation(szStationToken);
                        } else {
                            showProgressDlg(false);
                            showAlarmDlg(true, null, "Remove Station Fail, Data invalid");
                        }
                        break;
                    }
                }
            }
			break;
		case EventMessage.SERVICE_AUTO_COMPLETE_SUCCESS:
			ArrayList<PandoraAutoCompleteData> musicItems;
			musicItems = (ArrayList<PandoraAutoCompleteData>)objData;
			if(null != musicItems){
				((PandoraInputWnd)WndData.get(EventMessage.RUN_SEARCH_INPUTWND)).setMusicStr(musicItems);
			}
			break;
		case EventMessage.WND_PROGRESS:
			showProgressDlg(true);
			break;
		case EventMessage.SERVICE_FIRE_IDLE_ALARM:
			szResult = (String)objData;
			showProgressDlg(false);
			application.setIdleAlarm(true);
			showAlarmDlg(true, null, szResult);
			break;
		case EventMessage.SERVICE_ACTIVATION_CODE_SUCCESS:
			String[]actionCode = new String[2];
			actionCode = (String[])objData;
			String szCode = actionCode[0];
			String szURL  = actionCode[1];
			showProgressDlg(false);
			if(null != szCode && null != szURL){
				application.setActivationCode(szCode, szURL);
				showWnd(EventMessage.RUN_ACCOUNTWND);
			}
			break;
		case EventMessage.SERVICE_LOGOUT_SUCCESS:
            int nSwitchUserIndex = -1;
            nSwitchUserIndex = application.getSwitchUserIndex();
            if (-1 != nSwitchUserIndex) { // -2 : new user, > -1 : switch user
                String szCurrAccount = application.userData.getCurrLoginAccount();
                String szCurrPassword = application.userData.getCurrLoginPassword();
                String szCurrLastStationToken = application.userData.getCurrLastStationToken();
                boolean bCurrIsSavePwd = application.userData.getCurrLoginSavePwd();
                application.userData.addUserData(szCurrAccount, szCurrAccount,
                        szCurrPassword, szCurrLastStationToken, bCurrIsSavePwd);
                
                application.setSwitchUserIndex(-1);
                String szAccount = application.userData.getUserAccount(nSwitchUserIndex);
                String szPassword = application.userData.getUserPassword(nSwitchUserIndex);
                String szLastStationToken = application.userData.getUserLastStationToken(nSwitchUserIndex);
                boolean bIsSavePwd = application.userData.getUserIsSavePassword(nSwitchUserIndex);
                switchUser(szAccount, szPassword, szLastStationToken, bIsSavePwd);
            // TODO unreach code
            /*
            } else if (-2 == nSwitchUserIndex) { // new user
                application.setSwitchUserIndex(-1);
                setSaveCurrentLogin(null, null, null, EventMessage.FALSE);
                this.showWnd(EventMessage.RUN_LOGINWND);
                this.showProgressDlg(false);
            */
            } else {
                application.setCurrentPlayStation(-1);
                
                String szAccount = application.userData.getCurrLoginAccount();
                String szPassword = application.userData.getCurrLoginPassword();
                String szLastStationToken = application.userData.getCurrLastStationToken();
                boolean bIsSavePwd = application.userData.getCurrLoginSavePwd();
                application.userData.addUserData(szAccount, szAccount,
                        szPassword, szLastStationToken, bIsSavePwd);
                setSaveCurrentLogin(szAccount, szPassword, szLastStationToken, bIsSavePwd ? EventMessage.TRUE : EventMessage.FALSE);
                
                this.showProgressDlg(false);
                finish();
            }
            break;
		case EventMessage.SERVICE_NOWPLAY_TOKEN:
			String[]arrNowPlay = new String[2];
			arrNowPlay = (String[])objData;
            if(null != arrNowPlay){
            	int nIndex = Integer.valueOf(arrNowPlay[0]);
            	((PandoraStationWnd)WndData.get(EventMessage.RUN_STATIONWND)).setPlayStation(nIndex);
            	((PandoraStationWnd)WndData.get(EventMessage.RUN_STATIONWND)).setStationListPosition();
            }
			break;
		case EventMessage.SERVICE_LOGOUT_FAIL:
		case EventMessage.SERVICE_READ_ONLY:
		case EventMessage.SERVICE_ACTIVATION_CODE_FAIL:
		case EventMessage.SERVICE_STATION_GET_FAIL:
		case EventMessage.SERVICE_REMOVE_STATION_FAIL:	
		case EventMessage.SERVICE_EXPLAIN_TRACK_FAIL:
		case EventMessage.SERVICE_SLEEP_SONG_FAIL:
		case EventMessage.SERVICE_BOOKMARK_FAIL:
		case EventMessage.SERVICE_NEGATIVE_FEEDBACK_FAIL:
		case EventMessage.SERVICE_POSITIVE_FEEDBACK_FAIL:
		case EventMessage.SERVICE_SEARCH_RESULT_FAIL:
		case EventMessage.SERVICE_CREATE_STATION_FAIL:
		case EventMessage.SERVICE_SKIP_SONG_FAIL:
		case EventMessage.SERVICE_ADS_OPTION_FAIL:
		case EventMessage.SERVICE_ERROR_EVENT:
		case EventMessage.SERVICE_AUTO_COMPLETE_FAIL:
		case EventMessage.SERVICE_REACH_SKIP_LIMITATION:
			szResult = (String)objData;
			showProgressDlg(false);
			showAlarmDlg(true, null, szResult);
			break;
		}
	}
	
	private void onInputWnd(int nMsg, Object objData){
		switch(nMsg){
		case EventMessage.WND_CLOSE_INPUT_WND:
			if(mbPassKey){
				mbPassKey = false;
				return;
			}
			removeDialog(EventMessage.RUN_SEARCH_INPUTWND);
			showWnd(EventMessage.RUN_STATIONWND);
			break;
		case EventMessage.WND_BTN_OK:
			String mszSearch = (String)objData;//((PandoraInputWnd)WndData.get(EventMessage.RUN_SEARCH_INPUTWND)).getSearchString();
			removeDialog(EventMessage.RUN_SEARCH_INPUTWND);
			if( null == mszSearch || mszSearch.length() <= 0){
				showWnd(EventMessage.RUN_STATIONWND);
			}else{
				if( null != application.PService){
					((PandoraSearchWnd)WndData.get(EventMessage.RUN_SEARCHWND)).clearData();
					showWnd(EventMessage.RUN_SEARCHWND);
					showProgressDlg(true);
					application.PService.musicSearch(mszSearch);
				}else{
					showWnd(EventMessage.RUN_STATIONWND);
				}
			}
			break;
		case EventMessage.WND_TEXT_CHANGE:
			if( null != application.PService){
				CharSequence cStr = (CharSequence)objData;
				if(cStr.length() > 0){
					application.PService.autoComplete(cStr.toString());
				}
			}
			break;
		case EventMessage.WND_CREATE_STATION:
			String szMusicToken = (String)objData;
			if( null != szMusicToken && null != application.PService){
				this.showProgressDlg(true);
				application.PService.createStation(szMusicToken);
				removeDialog(EventMessage.RUN_SEARCH_INPUTWND);
			}
			break;
		}
	}
	
	private void onProgressDlg(int nMsg){
		switch(nMsg){
		case EventMessage.WND_SHOW:
			showProgressDlg(true);
			break;
		case EventMessage.WND_STOP:
			showProgressDlg(false);
			break;
		}
	}
	
    private void onAlarmDlg(int nMsg) {
        switch (nMsg) {
            case EventMessage.WND_STOP:
                showAlarmDlg(false, null, null);
                if (mbIsExecuteLogout) {
                    finish();
                }
                break;
        }
    }
	
	private void onPlayInfoWnd(int nMsg){
		switch(nMsg){
		case EventMessage.WND_SHOW:
			Log.i(TAG,"[WND_SHOW] pandora play info window show");
			break;
		case EventMessage.WND_EXCP:
			Log.i(TAG,"[WND_EXCP] pandora play info window progress Exception !");
			break;
		case EventMessage.WND_STOP:
			showWnd(EventMessage.RUN_PLAYWND);
			break;
		}
	}
	
	private void onSearchWnd(int nMsg, Object objData){
		switch(nMsg){
		case EventMessage.WND_SHOW:
			Log.i(TAG,"[WND_SHOW] pandora search window show");
			break;
		case EventMessage.WND_EXCP:
			Log.i(TAG,"[WND_EXCP] pandora search window progress Exception !");
			break;
		case EventMessage.WND_STOP:
			showWnd(EventMessage.RUN_STATIONWND);
			break;
		case EventMessage.WND_ITEM_CLICK:
			String szMusicToken = (String)objData;
			if( null != szMusicToken && null != application.PService){
				this.showProgressDlg(true);
				application.PService.createStation(szMusicToken);
			}
			break;
		case EventMessage.WND_LOGOUT:
		    executeLogout();
			break;
		}
	}
	
	private void executeLogout() {
	    mbIsExecuteLogout = true;
        if (null != application.PService) {
            this.showProgressDlg(true);
            application.PService.stopMusic();
            //application.PService.doLogout();
        } else {
            finish();
        }
	}
	
	public PandoraWnd getWindow(int nId){
		if(null != WndData){
			if(WndData.containsKey(nId)){
				return WndData.get(nId);
			}
		}
		
		return null;
	}
	
    private void switchUser(String szAccount, String szPassword, String szLastStationToken, boolean bIsSavePwd) {
        Logger.d("szAccount [" + szAccount
                + "], szPassword [" + szPassword
                + "], szLastStationToken [" + szLastStationToken
                + "], bIsSavePwd [" + bIsSavePwd + "]");
        
        if (bIsSavePwd) {
            application.userData.setCurrLogin(szAccount, szPassword, szLastStationToken, true);
            setSaveCurrentLogin(szAccount, szPassword, szLastStationToken, EventMessage.TRUE);
        } else {
            application.userData.setCurrLogin(szAccount, szPassword, szLastStationToken, false);
            setSaveCurrentLogin(szAccount, szPassword, szLastStationToken, EventMessage.FALSE);
            this.showWnd(EventMessage.RUN_LOGINWND);
            this.showProgressDlg(false);
            return;
        }

        if (checkLogin(szAccount, szPassword)) { // login account valid
            if (null != application.PService) {
                application.PService.doLogin(szAccount, szPassword);
            }
        } else { // login account invalid
            showProgressDlg(false);
            showAlarmDlg(true, null, "Login Account or Password invalid");
        }
    }
	
	private void removeUser(){
		int nCount = application.userData.getUserCount();
		if( 0 >= nCount || 0 >= application.userData.getUserDeletedCount()){
			return;
		}
		
		for(int i = (nCount - 1); i >= 0; i--){
			if(application.userData.getUserDelected(i)){
				application.userData.removeUser(i);
				Log.v(TAG,"remove user index = " + i);
			}
		}
		application.userData.updateUserShareData();
	}
	
	public void showProgressDlg(boolean bShow){
		try{
			if(bShow){
				this.showDialog(EventMessage.SHOW_PROGRESS_DLG);
			}else{
				this.removeDialog(EventMessage.SHOW_PROGRESS_DLG);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void showAlarmDlg(boolean bShow, String szTitle, String szAlarm){
		try {
			if (bShow) {
				application.setAlarmText(szAlarm);
				this.showDialog(EventMessage.SHOW_ALARM_DLG);
				application.setAlarmShowed(true);
			} else {
				if (null != application.PService) {
					boolean bIsIdleAlarm = application.isIdleAlarm();
					if (bIsIdleAlarm) {
						application.PService.resumeMusic();
					}
					application.PService.resetIdleTime();
				}
				this.removeDialog(EventMessage.SHOW_ALARM_DLG);
				application.setAlarmShowed(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//************************************************************
	// dialog window
	// for create station window , menu window
	//************************************************************
	@Override
	protected Dialog onCreateDialog(int id) {
			
		switch(id){
		case EventMessage.SHOW_ALARM_DLG:
	        AlarmDialog alarmDlg = new  AlarmDialog(this, wndHandler, EventMessage.SHOW_ALARM_DLG);
	        String szAlarm = application.getAlarmText();
	        alarmDlg.setAlarmInfo(szAlarm);
	        alarmDlg.showWindow(true);
	        return alarmDlg.getDialog();
		case EventMessage.SHOW_PROGRESS_DLG:
			ProgressDialog progDlg = new ProgressDialog(this, wndHandler, EventMessage.SHOW_PROGRESS_DLG);
			progDlg.showWindow(true);
			return progDlg.getDialog();
		case EventMessage.RUN_SEARCH_INPUTWND:
			AutoCompleteTextView etTmp = (AutoCompleteTextView)((PandoraInputWnd)WndData.get(EventMessage.RUN_SEARCH_INPUTWND)).getDialog().findViewById(R.id.etInputSearch);
			if(null != etTmp){
				etTmp.setText("");
			}
			return ((PandoraInputWnd)WndData.get(EventMessage.RUN_SEARCH_INPUTWND)).getDialog();
		case EventMessage.RUN_STATION_REMOVE_CHK_WND:
			return ((PandoraStationRemoveChkDlg)WndData.get(EventMessage.RUN_STATION_REMOVE_CHK_WND)).getDialog();
		}
		return super.onCreateDialog(id);
	}    
}
