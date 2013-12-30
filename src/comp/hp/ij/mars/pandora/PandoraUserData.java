/**
 * @author jugo
 * @date 2009-12-04
 */
package comp.hp.ij.mars.pandora;

import java.util.ArrayList;

import android.util.Log;

import comp.hp.ij.common.service.pandora.util.Logger;


public class PandoraUserData {
	
	private final String TAG = "PandoraUserData";
	public	ArrayList<UsersItem> usersData = null;
	private PandoraApplication application = null;
	
	private static final String USER_FILE = "PandoraUser";
	public static final String USER_COUNT = "userCount";
	public static final String USER_ACCOUNT = "userAccount";
	public static final String USER_PASSWORD = "userPassword";
	public static final String USER_LAST_STATION_TOKEN = "userLastStationToken";
	public static final String USER_SAVE_PWD = "userSavePwd";
	
	private String mszCurrLoginAccount  = null;
	private String mszCurrLoginPassword = null;
	private String mszCurrLastStationToken = null;
	private boolean mbCurrLoginSavePwd  = false;
	public final int MAX_USER_ACCOUNT  = 4;
	
	
    private class UsersItem {
        private String  mszUserName = null;
        private String  mszAccount = null;
        private String  mszPassword = null;
        private String  mszLastStationToken = null;
        private boolean mbIsSavePwd = false;
        private boolean mbDelSelected = false;

        public UsersItem(String szUserName, String szAccount, String szPassword, String szLastStationToken, boolean bSavePwd) {
            mszUserName         = szUserName;
            mszAccount          = szAccount;
            mszPassword         = szPassword;
            mszLastStationToken = szLastStationToken;
            mbDelSelected       = false;
            mbIsSavePwd         = bSavePwd;
        }
    }
	
	public PandoraUserData(){
		usersData = new ArrayList<UsersItem>();
		clearUserData();
	}
	
	public void init(PandoraApplication PApplication){
		application = PApplication;
		initUserData();
	}
	
    public int addUserData(String szUserName, String szAccount, String szPassword, String szLastStationToken, boolean bSavePwd) {

        Logger.d("szUserName [" + szUserName
                + "] szAccount [" + szAccount
                + "] szPassword [" + szPassword
                + "] szLastStationToken [" + szLastStationToken
                + "] bSavePwd [" + bSavePwd + "]");
        
        application.sharedData.initPreferences(USER_FILE);

        if (isAccountExist(szAccount, szLastStationToken, bSavePwd)) {
            application.sharedData.close();
            return usersData.size();
        }

        String szUserCount = application.sharedData.getValue(USER_COUNT);
        Log.v(TAG, "get user counts = [" + szUserCount + "]");

        if (null == szUserCount) {
            usersData.add(new UsersItem(szUserName, szAccount, szPassword, szLastStationToken, bSavePwd));
            application.sharedData.setValue(USER_COUNT, "1");
            saveUserAccount("1", szUserName, szAccount, szPassword, szLastStationToken, bSavePwd);
        } else {
            int nCount = Integer.valueOf(szUserCount);
            if (MAX_USER_ACCOUNT > nCount) {
                if (null != szAccount && null != szPassword) {
                    usersData.add(new UsersItem(szUserName, szAccount, szPassword, szLastStationToken, bSavePwd));
                    nCount++;
                    String szCount = String.valueOf(nCount);
                    saveUserAccount(szCount, szUserName, szAccount, szPassword, szLastStationToken, bSavePwd);
                    application.sharedData.setValue(USER_COUNT, szCount);
                    Log.v(TAG, "save user account , account = [" + szAccount + "] count = [" + szCount + "]");
                } else {
                    Log.v(TAG, "save user account fail, invalid account");
                }
            } else {
                Log.v(TAG, "users count over max, user count = [" + nCount + "]");
            }
        }
        application.sharedData.close();

        return usersData.size();
    }
	
	private void saveUserAccount(String szNum, String szUserName, String szAccount, String szPassword, String szLastStationToken, boolean bSavePwd){
		if(null == application.sharedData){
			return;
		}
		String szUser_account = USER_ACCOUNT + szNum;
		String szUser_password = USER_PASSWORD + szNum;
		String szUser_lastStationToken = USER_LAST_STATION_TOKEN + szNum;
		String szUser_save_pwd = USER_SAVE_PWD + szNum;
		application.sharedData.setValue(szUser_account, szAccount);
		application.sharedData.setValue(szUser_password, szPassword);
		application.sharedData.setValue(szUser_lastStationToken, szLastStationToken);
		application.sharedData.setValue(szUser_save_pwd, bSavePwd ? "1" : "0");
	}
	
	private void initUserData(){
		application.sharedData.initPreferences(USER_FILE);
		String szUserCount = application.sharedData.getValue(USER_COUNT);
		if(null == szUserCount){
			application.sharedData.close();
			return;
		}
		
		int nCount = Integer.valueOf(szUserCount);
		if( 0 >= nCount){
			application.sharedData.close();
			return;
		}
		
		clearUserData();
		String szNum = null;
		String szAccount = null;
		String szPassword = null;
		String szLastStationToken = null;
		String szIsSavePwd = null;
		boolean bIsSavePwd = false;
		for(int i=1; i <= MAX_USER_ACCOUNT; i++){
			szNum = String.valueOf(i);
			szAccount = application.sharedData.getValue(USER_ACCOUNT + szNum);
			szPassword = application.sharedData.getValue(USER_PASSWORD + szNum);
			szLastStationToken = application.sharedData.getValue(USER_LAST_STATION_TOKEN + szNum);
			szIsSavePwd = application.sharedData.getValue(USER_SAVE_PWD + szNum);
			if(null != szIsSavePwd){
				if(szIsSavePwd.equals("1")){
					bIsSavePwd = true;
				}else{
					bIsSavePwd = false;
				}
			}
			if(null != szAccount && null != szPassword && null != szIsSavePwd){
				usersData.add(new UsersItem(szAccount, szAccount, szPassword, szLastStationToken, bIsSavePwd ));
			}
		}
		application.sharedData.close();
	}
	
	public UsersItem getUserItem(int nIndex){
		if(!isDataValid(nIndex)){
			return null;
		}
		return usersData.get(nIndex);
	}
	
	public int getUserCount(){
		if(usersData == null){
			return 0;
		}
		return usersData.size();
	}
	
	public void removeUser(int nIndex){
		if(!isDataValid(nIndex)){
			return;
		}
		usersData.remove(nIndex);
	}
	
    public void clearUserData() {
        if (null != usersData) {
            if (0 < usersData.size()) {
                usersData.clear();
            }
        }
    }
	
    public void updateUserShareData() {
        int nCount = getUserCount();
        clearUserShareData();
        if (0 >= nCount) {
            return;
        }

        String szAccount = null;
        String szPassword = null;
        String szLastStationToken = null;
        boolean bIsSavePwd = false;
        application.sharedData.initPreferences(USER_FILE);
        nCount = 0;
        for (int i = 0; i < getUserCount(); i++) {
            szAccount = getUserAccount(i);
            szPassword = getUserPassword(i);
            szLastStationToken = getUserLastStationToken(i);
            bIsSavePwd = getUserIsSavePassword(i);
            nCount++;
            saveUserAccount(String.valueOf(nCount), szAccount, szAccount, szPassword, szLastStationToken, bIsSavePwd);
        }
        application.sharedData.setValue(USER_COUNT, String.valueOf(nCount));
        application.sharedData.close();
    }
	
	private void clearUserShareData(){
		application.sharedData.initPreferences(USER_FILE);
		String szUser_account = null;
		String szUser_password = null;
		String szUser_lastStationToken = null;
		String szUser_save_pwd = null;
		application.sharedData.setValue(USER_COUNT, "0");
		for(int i = 1; i <= MAX_USER_ACCOUNT; i++){
			szUser_account = USER_ACCOUNT + i;
			szUser_password = USER_PASSWORD + i;
			szUser_lastStationToken = USER_LAST_STATION_TOKEN + i;
			szUser_save_pwd = USER_SAVE_PWD + i;
			application.sharedData.setValue(szUser_account, null);
			application.sharedData.setValue(szUser_password, null);
			application.sharedData.setValue(szUser_lastStationToken, null);
			application.sharedData.setValue(szUser_save_pwd, "0");
		}
		application.sharedData.close();
	}
	
	public void releaseUserData(){
		clearUserData();
		usersData = null;
	}
	
	public String getUserName(int nIndex){
		if(!isDataValid(nIndex)){
			return null;
		}
		UsersItem data = usersData.get(nIndex);
		return data.mszUserName;
	}
	
	public String getUserAccount(int nIndex){
		if(!isDataValid(nIndex)){
			return null;
		}
		UsersItem data = usersData.get(nIndex);
		return data.mszAccount;
	}
	
	public String getUserPassword(int nIndex){
		if(!isDataValid(nIndex)){
			return null;
		}
		UsersItem data = usersData.get(nIndex);
		return data.mszPassword;
	}
	
	public String getUserLastStationToken(int nIndex){
	    if(!isDataValid(nIndex)){
	        return null;
	    }
	    UsersItem data = usersData.get(nIndex);
	    return data.mszLastStationToken;
	}
	
	public boolean getUserIsSavePassword(int nIndex){
		if(!isDataValid(nIndex)){
			return false;
		}
		UsersItem data = usersData.get(nIndex);
		return data.mbIsSavePwd;
	}
	
	public boolean getUserDelected(int nIndex){
		if(!isDataValid(nIndex)){
			return false;
		}
		UsersItem data = usersData.get(nIndex);
		return data.mbDelSelected;
	}
	
	public void setUserDeleted(int nIndex, boolean bDel){
		if(!isDataValid(nIndex)){
			return;
		}
		usersData.get(nIndex).mbDelSelected = bDel;
		Log.v(TAG,"set user deleted status : " + nIndex);
	}

	public int getUserDeletedCount(){
		if(usersData == null){
			return 0;
		}
		int nCount = 0;
		
		for(int i=0; i < getUserCount(); i++){
			if(getUserDelected(i)){
				nCount++;
			}
		}
		
		return nCount;
	}
	
    public boolean isAccountExist(String szAccount, String szLastStationToken, boolean bIsSavePwd) {
        if (null != szAccount && 0 < getUserCount()) {
            String szTmp = null;
            for (int i = 0; i < getUserCount(); i++) {
                szTmp = getUserAccount(i);
                if (null == szTmp) {
                    continue;
                }
                if (szTmp.equals(szAccount)) {
                    boolean bSavePwd = false;
                    bSavePwd = this.getUserIsSavePassword(i);
                    if (bSavePwd != bIsSavePwd) {
                        String szUser_save_pwd = USER_SAVE_PWD + (i + 1);
                        application.sharedData.setValue(szUser_save_pwd, bIsSavePwd ? "1" : "0");
                        usersData.get(i).mbIsSavePwd = bIsSavePwd;
                    }
                    
                    application.sharedData.setValue((USER_LAST_STATION_TOKEN + (i + 1)), szLastStationToken);
                    usersData.get(i).mszLastStationToken = szLastStationToken;
                    return true;
                }
            }
        }
        return false;
    }
	
	private boolean isDataValid(int nIndex){
		if(usersData == null){
			return false;
		}
		if( 0 > nIndex || nIndex >= usersData.size()){
			Log.v(TAG,"invalid data index : " + nIndex);
			return false;
		}
		return true;
	}
	
	public void setCurrLogin(String szAccount, String szPassword, String szLastStationToken, boolean bSavePwd){
		if(null != szAccount && null != szPassword){
			mszCurrLoginAccount  = szAccount;
			mszCurrLoginPassword = szPassword;
			mszCurrLastStationToken = szLastStationToken;
			mbCurrLoginSavePwd   = bSavePwd;
		}
	}
	
	public String getCurrLoginAccount(){
		return mszCurrLoginAccount;
	}
	
	public String getCurrLoginPassword(){
		return mszCurrLoginPassword;
	}
	
	public String getCurrLastStationToken() {
	    return mszCurrLastStationToken;
	}
	public void setCurrLastStationToken(String szLastStationToken) {
	    mszCurrLastStationToken = szLastStationToken;
	}
	
	public boolean getCurrLoginSavePwd(){
		return mbCurrLoginSavePwd;
	}
	
	
}
