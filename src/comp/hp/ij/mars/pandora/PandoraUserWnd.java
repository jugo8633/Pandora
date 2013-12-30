/**
 * @author jugo
 * @date 2009-12-01
 * @description pandora multi-user list window
 */

package comp.hp.ij.mars.pandora;


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
import comp.hp.ij.mars.pandora.R;
import frame.view.MenuList;


public class PandoraUserWnd extends PandoraWnd{

	private final String TAG = "PandoraUserWnd";
	private final int m_nMainLayoutResId = R.layout.pandora_users;
	private final int m_nFootLayoutResId = R.layout.pandora_users_footbar;
	private final int mnHeaderLayoutResId = R.layout.pandora_header;
	private ListView		listView = null;
	private static boolean  m_bRemoveAble = false;
	private MenuList footbarMenu = null;
	
	public PandoraUserWnd(Activity active, Handler handler, int nId){
		super(active, handler, nId);
		super.setLayoutResId(m_nMainLayoutResId, m_nFootLayoutResId, mnHeaderLayoutResId);
		super.setViewTouchEvent(R.id.ryUsersNewUser);
		super.setViewTouchEvent(R.id.ryUsersRemoveUser);
	}
	
	@Override
	protected void onShow() {
		footbarMenu = (MenuList)super.getApp().findViewById(R.id.menuListFootbarChk);
		footbarMenu.setMenuView(R.layout.footbar_check_menu,
				null,
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		super.RegisterEvent(R.id.tvFootbarChkMenuOK, EVENT_TOUCH);
		
		application.userData = ((PandoraApplication)super.getApp().getApplication()).userData;
		initListView();
		if(getRemoveState()){
			footbarMenu.showMenu(true);
		}
		updateOptionState();
		
		TextView tvHeader = (TextView)super.getApp().findViewById(R.id.tvPandoraHeaderLogout);
		if( null != tvHeader ){
			tvHeader.setVisibility(View.INVISIBLE);
		}
	}
	
	@Override
	protected void onClose() {
		if(listView != null){
        	listView = null;
        }
	}
	
	@Override
	protected void onClick(int resId) {
		RelativeLayout rlTmp = (RelativeLayout)listView.findViewById(resId);
		ImageView ivTmp = null;
		if(null != rlTmp){
			ivTmp = (ImageView)rlTmp.findViewById(R.id.ivUsersState);
		}
		
		if(getRemoveState()){ // select user to remove
			if(application.userData.getUserDelected(resId)){
				application.userData.setUserDeleted(resId, false);
				if(null != ivTmp){
					ivTmp.setImageResource(R.color.List_BG);
				}
			}else{
				application.userData.setUserDeleted(resId ,true);
				if(null != ivTmp){
					ivTmp.setImageResource(R.drawable.pandora_remove_icon);
				}
			}
		}else{ // switch user
			if(null != rlTmp){
				TextView tvTmp = (TextView)rlTmp.findViewById(R.id.tvUsersName);
				if( null != tvTmp ){
					tvTmp.setTextColor(COLOR_FOCUS);
				}
			}
			sendAppMsg(WND_MSG, WND_SWITCH_USER, String.valueOf(resId));
		}
	}

	@Override
	protected void onTouchDown(int resId) {
		switch(resId){
		case R.id.ryUsersNewUser:
			if(application.userData.getUserCount() >= application.userData.MAX_USER_ACCOUNT){
				return;
			}
			((ImageView)super.getApp().findViewById(R.id.ivUsersNewUser)).setImageResource(R.drawable.pandora_sta_add_focus);
			((TextView)super.getApp().findViewById(R.id.tvUsersNewUser)).setTextColor(COLOR_FOCUS);
			break;
		case R.id.ryUsersRemoveUser:	
			if(application.userData.getUserCount() <= 0){
				return;
			}
			switchRemoveAble();
			footbarMenu.showMenu(true);
			break;
		case R.id.tvFootbarChkMenuOK:
			((TextView)super.getApp().findViewById(R.id.tvFootbarChkMenuOK)).setBackgroundResource(R.drawable.ok_btn_focus);
			break;
		}
	}

	@Override
	protected void onTouchUp(int resId) {
		switch(resId){
		case R.id.ryUsersNewUser:
			if(application.userData.getUserCount() >= application.userData.MAX_USER_ACCOUNT){
				return;
			}
			((ImageView)super.getApp().findViewById(R.id.ivUsersNewUser)).setImageResource(R.drawable.pandora_sta_add);
			((TextView)super.getApp().findViewById(R.id.tvUsersNewUser)).setTextColor(COLOR_WHITE);
			sendAppMsg(WND_MSG, WND_NEW_USER, null);
			break;
		case R.id.ryUsersRemoveUser:
			break;
		case R.id.tvFootbarChkMenuOK:
			sendAppMsg(WND_MSG, WND_REMOVE_USER,null);
			((TextView)super.getApp().findViewById(R.id.tvFootbarChkMenuOK)).setBackgroundResource(R.drawable.ok_btn);
			switchRemoveAble();
			footbarMenu.showMenu(false);
			break;
		}
	}
	
	public void updateUserList(){
		listView.invalidateViews();
		updateOptionState();
		Log.v(TAG,"update user list");
	}
	
	private void initListView(){
        if(listView != null){
        	listView = null;
        }
        
        listView = (ListView)super.getApp().findViewById(R.id.lvUsersList);
        try{
    		listView.setAdapter(new UsersAdapter(super.getApp()));
    		Log.i(TAG,"users listview set adapter ok");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	}
	
	private boolean switchRemoveAble(){
		if(m_bRemoveAble){
			m_bRemoveAble = false;
		}else{
			m_bRemoveAble = true;
		}
		return m_bRemoveAble;
	}
	
	private boolean getRemoveState(){
		return m_bRemoveAble;
	}
	
	private void clearRemoveSelected(){
		if(application.userData == null){
			return;
		}
	
		for(int i=0; i < application.userData.getUserCount(); i++){
			application.userData.setUserDeleted(i, false);
		}
	}
	
	public class UsersView{
		ImageView	    m_ivUserState;
		TextView	    m_tvUserName;
		RelativeLayout	m_rlUserItem;
	}
	public class UsersAdapter extends BaseAdapter{

		private Context m_context;
		private LayoutInflater m_inflater;
		
		public UsersAdapter(Context c){
			m_context = c;
			m_inflater = LayoutInflater.from(this.m_context);
		}
		
		public int getCount() {
			return application.userData.getUserCount();
		}

		public Object getItem(int arg0) {
			if(application.userData != null){
				return application.userData.getUserItem(arg0);
			}
			return null;
		}

		public long getItemId(int arg0) {
			System.out.println("getItemId = " + String.valueOf(arg0));
			return arg0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final UsersView usersView;
			
			if(convertView != null){convertView = null;}
		
			convertView = m_inflater.inflate(R.layout.pandora_users_item,null);
	
			usersView = new UsersView();
			usersView.m_ivUserState   = (ImageView)convertView.findViewById(R.id.ivUsersState);
			usersView.m_tvUserName    = (TextView) convertView.findViewById(R.id.tvUsersName);
			usersView.m_rlUserItem    = (RelativeLayout) convertView.findViewById(R.id.rlUsersItem);
			usersView.m_rlUserItem.setId(position);
			initEvent(usersView.m_rlUserItem);
			convertView.setTag(usersView);
			
			if(application.userData.getUserName(position) != null){
				usersView.m_tvUserName.setText(application.userData.getUserName(position));
			}
			
			if(getRemoveState()){
				if(application.userData.getUserDelected(position)){
					usersView.m_ivUserState.setBackgroundResource(R.drawable.pandora_remove_icon);
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

	private void updateOptionState(){
		ImageView ivTmp = null;
		TextView tvTmp = null;
		
		if(application.userData.getUserCount() >= application.userData.MAX_USER_ACCOUNT){
			ivTmp = (ImageView)super.getApp().findViewById(R.id.ivUsersNewUser);
			if(null != ivTmp){
				ivTmp.setAlpha(153);
			}
			ivTmp = (ImageView)super.getApp().findViewById(R.id.ivUsersRemoveUser);
			if(null != ivTmp){
				ivTmp.setAlpha(255);
			}
			tvTmp = (TextView)super.getApp().findViewById(R.id.tvUsersNewUser);
			if(null != tvTmp){
				tvTmp.setTextColor(0xff999999);
			}
			Log.v(TAG,"MAX User Account");
		}else if( application.userData.getUserCount() <= 0){
			ivTmp = (ImageView)super.getApp().findViewById(R.id.ivUsersRemoveUser);
			if(null != ivTmp){
				ivTmp.setAlpha(153);
			}
			ivTmp = (ImageView)super.getApp().findViewById(R.id.ivUsersNewUser);
			if(null != ivTmp){
				ivTmp.setAlpha(255);
			}
			tvTmp = (TextView)super.getApp().findViewById(R.id.tvUsersRemoveUser);
			if(null != tvTmp){
				tvTmp.setTextColor(0xff999999);
			}
			Log.v(TAG,"No User Account");
		}else{
		
			ivTmp = (ImageView)super.getApp().findViewById(R.id.ivUsersNewUser);
			if(null != ivTmp){
				ivTmp.setAlpha(255);
			}
			tvTmp = (TextView)super.getApp().findViewById(R.id.tvUsersNewUser);
			if(null != tvTmp){
				tvTmp.setTextColor(COLOR_WHITE);
			}
		
			ivTmp = (ImageView)super.getApp().findViewById(R.id.ivUsersRemoveUser);
			if(null != ivTmp){
				ivTmp.setAlpha(255);
			}
			tvTmp = (TextView)super.getApp().findViewById(R.id.tvUsersRemoveUser);
			if(null != tvTmp){
				tvTmp.setTextColor(COLOR_WHITE);
			}
			Log.v(TAG,"normal User Account");
		}
		
	}
	
}
