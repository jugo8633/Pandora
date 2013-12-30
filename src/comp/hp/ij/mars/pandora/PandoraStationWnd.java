/**
 * @author jugo
 * @date 2009-12-01
 * @description pandora station list window
 */

package comp.hp.ij.mars.pandora;


import frame.view.MarqueeTextView;
import frame.view.MenuList;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PandoraStationWnd extends PandoraWnd{

	private final String TAG = "PandoraStationWnd";
	private final int m_nMainLayoutResId = R.layout.pandora_station;
	private final int m_nFootLayoutResId = R.layout.pandora_station_footbar;
	private final int mnHeaderLayoutResId = R.layout.pandora_header;
	private ListView listView = null;
	private MenuList footbarMenu = null;

		
	public PandoraStationWnd(Activity active, Handler handler, int nId){
		super(active, handler, nId);
		super.setLayoutResId(m_nMainLayoutResId, m_nFootLayoutResId, mnHeaderLayoutResId);
		super.setViewTouchEvent(R.id.ryStaCreateStation);
		super.setViewTouchEvent(R.id.ryStaRemoveStation);
		super.setViewTouchEvent(R.id.ryStaSwitchUser);
		super.setViewTouchEvent(R.id.tvPandoraHeaderLogout);		
		super.setViewTouchEvent(R.id.rlFootbarChkMenu); // Chance 2009-12-21 mantis 4881
	}
	
	@Override
	protected void onShow() {
		sendAppMsg(SHOW_PROGRESS_DLG,WND_SHOW, null);
		
		footbarMenu = (MenuList)super.getApp().findViewById(R.id.menuListFootbarChk);
		footbarMenu.setMenuView(R.layout.footbar_check_menu,
				null,
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		super.RegisterEvent(R.id.tvFootbarChkMenuOK, EVENT_TOUCH);
		
		if(getRemoveState()){
			footbarMenu.showMenu(true);
		}
		
		initListView();
		int nPlayStation = application.getCurrentPlayStation();
		if( 0 < nPlayStation ){
			setStationListPosition(nPlayStation);
		}
		application.PService.getNowPlayStationData();
	}
	
	@Override
	protected void onClose() {
		if(listView != null){
        	listView = null;
        }
	}

	@Override
	protected void onTouchDown(int resId) {
		switch(resId){
		case R.id.ryStaCreateStation:
			((ImageView)super.getApp().findViewById(R.id.ivStaCreateStation))
			.setImageResource(R.drawable.pandora_sta_add_focus);
			((TextView)super.getApp().findViewById(R.id.tvStaCreateStation))
			.setTextColor(COLOR_FOCUS);
			break;
		case R.id.ryStaRemoveStation:	
            if (0 == application.stationData.getStationCount()) {
                return;
            }
			clearStationStatus();
			switchRemoveAble();
			footbarMenu.showMenu(true);
			break;
		case R.id.ryStaSwitchUser:
			((ImageView)super.getApp().findViewById(R.id.ivStaSwitchUser))
			.setImageResource(R.drawable.pandora_sta_switch_focus);
			((TextView)super.getApp().findViewById(R.id.tvStaSwitchUser))
			.setTextColor(COLOR_FOCUS);
			break;
		case R.id.tvPandoraHeaderLogout:	
			((TextView)super.getApp().findViewById(R.id.tvPandoraHeaderLogout))
			.setBackgroundResource(R.drawable.btn_logout_focus);
			break;
		case R.id.tvFootbarChkMenuOK:
			((TextView)super.getApp().findViewById(R.id.tvFootbarChkMenuOK))
			.setBackgroundResource(R.drawable.ok_btn_focus);
			break;
		}
	}

	@Override
	protected void onTouchUp(int resId) {
		switch(resId){
		case R.id.ryStaCreateStation:
			((ImageView)super.getApp().findViewById(R.id.ivStaCreateStation))
			.setImageResource(R.drawable.pandora_sta_add);
			((TextView)super.getApp().findViewById(R.id.tvStaCreateStation))
			.setTextColor(COLOR_WHITE);
			sendAppMsg(WND_MSG, WND_CREATE_STATION, null);
			break;
		case R.id.ryStaRemoveStation:
			break;
		case R.id.ryStaSwitchUser:
			((ImageView)super.getApp().findViewById(R.id.ivStaSwitchUser))
			.setImageResource(R.drawable.pandora_sta_switch);
			((TextView)super.getApp().findViewById(R.id.tvStaSwitchUser))
			.setTextColor(COLOR_WHITE);
			sendAppMsg(WND_MSG,WND_SHOW_USER_WND, null);
			break;
		case R.id.tvPandoraHeaderLogout:	
			((TextView)super.getApp().findViewById(R.id.tvPandoraHeaderLogout))
			.setBackgroundResource(R.drawable.btn_logout);
			application.setStationRemoveState(false); // TODO Chance 2009-12-21 mantis 4879
			sendAppMsg(WND_MSG,WND_LOGOUT, null);
			break;
		case R.id.tvFootbarChkMenuOK:
			sendAppMsg(WND_MSG, WND_REMOVE_STATION,null);
			break;
		}
	}
	
	@Override
    protected void onClick(int resId) {
        if (null == application.stationData) {
            Log.e(TAG, "stationData invalid");
            return;
        }

        if (getRemoveState()) {
            setRemoveStation(resId);
        } else {
            String szStationToken = null;
            szStationToken = setPlayStation(resId);
            if (null != szStationToken) {
                String[] arrPlayStation = new String[2];
                arrPlayStation[0] = String.valueOf(resId);
                arrPlayStation[1] = szStationToken;
                sendAppMsg(WND_MSG, WND_ITEM_CLICK, arrPlayStation);
            } else {
                Log.v(TAG, "get station name fail");
            }
        }
    }
	
	private void initListView(){
        if(listView != null){
        	listView = null;
        }
        
        listView = (ListView)super.getApp().findViewById(R.id.lvStation);
        try{
    		listView.setAdapter(new StationAdapter(super.getApp()));
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	}
	
	private void setStationInfo(int nCurrent, int nTotal){
		if(nTotal == 0){
			nCurrent = 0;
		}
		String szInfo = String.valueOf(nCurrent) + " of " + String.valueOf(nTotal) + " your stations";
		TextView tvTmp = (TextView)super.getApp().findViewById(R.id.tvStationInfo);
		if( tvTmp != null ){
			tvTmp.setText(szInfo);
		}
	}
	
	private void setStationListPosition(int nPos){
		if(listView == null){
			return;
		}
		listView.setSelection(nPos);
	}
	
	public void setStationListPosition(){
		int nSelected = application.getCurrentPlayStation();
		setStationListPosition(nSelected);
	}
	
	public class StationView{
		ImageView m_ivState;
		MarqueeTextView m_tvStationName;
		RelativeLayout m_rlStationItem;
	}
	public class StationAdapter extends BaseAdapter{

		private Context m_context;
		private LayoutInflater m_inflater;
		
		public StationAdapter(Context c){
			m_context = c;
			m_inflater = LayoutInflater.from(this.m_context);
		}
		
		public int getCount() {
			if(application.stationData != null){
				return application.stationData.getStationCount();
			}
			return 0;
		}

		public Object getItem(int position) {
			RelativeLayout rlTmp = (RelativeLayout)listView.findViewById(position);
			return rlTmp;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final StationView stationView;
			
			if(convertView != null){convertView = null;}
		
			convertView = m_inflater.inflate(R.layout.pandora_station_item,null);
	
			stationView = new StationView();
			stationView.m_ivState = (ImageView)convertView.findViewById(R.id.ivStationState);
			stationView.m_tvStationName = (MarqueeTextView)convertView.findViewById(R.id.StationName);
			stationView.m_rlStationItem = (RelativeLayout)convertView.findViewById(R.id.rlStationItemMain);
			stationView.m_rlStationItem.setId(position);			
			initEvent(stationView.m_rlStationItem);
			convertView.setTag(stationView);
			String szText = application.stationData.getStationName(position);
			if(szText != null){
				if(PandoraApplication.ORIENTATION_LAND == application.getOrientation()){
					stationView.m_tvStationName.setViewWidth(560);
				}	
				stationView.m_tvStationName.setText(szText);
				stationView.m_tvStationName.startScroll();
				if(application.getAlarmShowed()){
					stationView.m_tvStationName.pauseScroll();
				}
			}
			
			if(getRemoveState()){
				if(application.stationData.getStationDeleted(position)){
					stationView.m_ivState.setImageResource(R.drawable.pandora_remove_icon);
					stationView.m_ivState.setVisibility(View.VISIBLE);
				}else{
					stationView.m_ivState.setImageResource(R.drawable.pandora_remove_icon);
					stationView.m_ivState.setVisibility(View.INVISIBLE);
				}
			}else{
				if(application.stationData.getStationPlayed(position)){
					stationView.m_ivState.setImageResource(R.drawable.pandora_nowplay_icon);
					stationView.m_ivState.setVisibility(View.VISIBLE);
				}else{
					stationView.m_ivState.setImageResource(R.drawable.pandora_nowplay_icon);
					stationView.m_ivState.setVisibility(View.INVISIBLE);
				}
			}
			
			return convertView;
		}
	}
	
	private void initEvent(View v){
		if(null == v){
			return;
		}
		super.RegisterEvent(v, PandoraWnd.EVENT_CLICK);
	}
		
	private boolean switchRemoveAble(){
		boolean bRemoveState = application.getStationRemoveState();
		if(bRemoveState){
			application.setStationRemoveState(false);
		}else{
			application.setStationRemoveState(true);
		}
		bRemoveState = application.getStationRemoveState();
		return bRemoveState;
	}
	
	private boolean getRemoveState(){
		boolean bRemoveState = application.getStationRemoveState();
		return bRemoveState;
	}
	
	public void clearStationStatus(){
		if(application.stationData == null){
			return;
		}
		RelativeLayout rlTmp = null;
		ImageView ivState = null;
		for(int i=0; i < application.stationData.getStationCount(); i++){
			application.stationData.setStationDeleted(i, false);
			application.stationData.setStationPlayed(i, false);
			rlTmp = (RelativeLayout)listView.findViewById(i);
			if(null != rlTmp){
				ivState = (ImageView)rlTmp.findViewById(R.id.ivStationState);
				if(null != ivState){
					ivState.setVisibility(View.INVISIBLE);
				}
			}
		}
	}
	
	public String setPlayStation(int nIndex){
		if(getRemoveState()){
			return null;
		}
		String szStationName  = null;
		String szStationToken = null;
		int nSelected = application.getCurrentPlayStation();
		application.setCurrentPlayStation(nIndex);
		application.stationData.setStationPlayed(nIndex, true);
		szStationName  = application.stationData.getStationName(nIndex);
		szStationToken = application.stationData.getStationToken(nIndex);
		application.stationData.setCurrentStationName(szStationName);
		setStationInfo(nIndex + 1, application.stationData.getStationCount());
		
		RelativeLayout rlTmp = null;
		ImageView ivState = null;
	
		if( 0 <= nSelected && ( nSelected != nIndex )){
			application.stationData.setStationPlayed(nSelected, false);
			rlTmp = (RelativeLayout)listView.findViewById(nSelected);
			if(null != rlTmp){
				ivState = (ImageView)rlTmp.findViewById(R.id.ivStationState);
				if(null != ivState){
					ivState.setVisibility(View.INVISIBLE);
				}
			}
		}
		
		rlTmp = (RelativeLayout)listView.findViewById(nIndex);
		if(null != rlTmp){
			ivState = (ImageView)rlTmp.findViewById(R.id.ivStationState);
			if(null != ivState){
				ivState.setImageResource(R.drawable.pandora_nowplay_icon);
				ivState.setVisibility(View.VISIBLE);
			}
		}
		
		return szStationToken;
	}
	
	private void setRemoveStation(int nIndex){
		String szStationName = application.stationData.getStationName(nIndex);
		
		if(null != szStationName){
			if(szStationName.equals("QuickMix")){
				String szMsg;
				szMsg = super.getApp().getString(R.string.quickMix_station_cannot_be_deleted);
				sendAppMsg(WND_MSG,WND_EXCP, szMsg);
				return;
			}
		}
			
		RelativeLayout rlTmp = (RelativeLayout)listView.findViewById(nIndex);
		ImageView ivState = null;
		if(null != rlTmp){
			ivState = (ImageView)rlTmp.findViewById(R.id.ivStationState);
		}
		
		if(application.stationData.getStationDeleted(nIndex)){
			application.stationData.setStationDeleted(nIndex ,false);
			if(null != ivState){
				ivState.setVisibility(View.INVISIBLE);
			}
		}else{
			application.stationData.setStationDeleted(nIndex ,true);
			if(null != ivState){
				ivState.setImageResource(R.drawable.pandora_remove_icon);
				ivState.setVisibility(View.VISIBLE);
			}
		}
	}
	
	public void updateUserList(){
		listView.invalidateViews();
	}
	
	public void removeStationFinish(boolean bShowPlay){
		((TextView)super.getApp().findViewById(R.id.tvFootbarChkMenuOK))
		.setBackgroundResource(R.drawable.ok_btn);
		clearStationStatus();
		switchRemoveAble();
		footbarMenu.showMenu(false);
		if(bShowPlay){
			int nPlayIndex = application.getCurrentPlayStation();
			setPlayStation(nPlayIndex);
		}
	}
}
