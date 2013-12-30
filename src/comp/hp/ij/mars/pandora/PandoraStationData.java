package comp.hp.ij.mars.pandora;

import java.util.ArrayList;
import java.util.List;

public class PandoraStationData {

	public	List<StationItem>stationData = null;
	private int m_nCurrPlay = -1;
	private String mszCurrStationToken = null;
	private String mszCurrStationName  = null;
	
	private class StationItem{
		private boolean	m_bAllowRename;
		private String	m_szStationToken;
		private boolean	m_bAllowDelete;
		private boolean	m_bQuickMix;
		private boolean	m_bShared;
		private long	m_lStationId;
		private String	m_szStationName;
		private boolean	m_bDelSelected;
		private boolean	m_bSelPlayed;
//		public TableRow	m_trStationItem;
//		public TextView	m_tvStationState;

		public StationItem(String szStationName, 
				boolean bDelSelected, 
				boolean bSelPlayed, 
				boolean bAllowRename, 
				String szStationToken, 
				boolean bAllowDelete, 
				boolean bQuickMix, 
				boolean bShared, 
				long lStationId){
			m_szStationName		= szStationName;
			m_bAllowRename		= bAllowRename;
			m_szStationToken	= szStationToken;
			m_bAllowDelete		= bAllowDelete;
			m_bQuickMix			= bQuickMix;
			m_bShared			= bShared;
			m_lStationId		= lStationId;
			
//			m_trStationItem		= null;
//			m_tvStationState	= null;
			m_bDelSelected		= bDelSelected;
			m_bSelPlayed		= bSelPlayed;
		}
	}
	
	public PandoraStationData(){
		stationData = new ArrayList<StationItem>();
	}
	
	public int addStationData(String szStationName, 
			boolean bAllowRename, 
			String szStationToken, 
			boolean bAllowDelete, 
			boolean bQuickMix, 
			boolean bShared, 
			long lStationId){
		stationData.add(new StationItem(szStationName, false, false, bAllowRename, szStationToken, bAllowDelete, bQuickMix, bShared, lStationId));
		return stationData.size();
	}
	
	public int insertStationData(String szStationName, 
	        boolean bAllowRename, 
	        String szStationToken, 
	        boolean bAllowDelete, 
	        boolean bQuickMix, 
	        boolean bShared, 
	        long lStationId) {
	    boolean bIsAlreadyHaveStationToken = false;
	    for (int i = 0 ; i < stationData.size() ; i++) {
	        if (stationData.get(i).m_szStationToken.equals(szStationToken)) {
	            bIsAlreadyHaveStationToken = true;
	            break;
	        }
	    }
	    if (!bIsAlreadyHaveStationToken) {
	        if (0 != stationData.size()) {
	            // TODO new station will be insert at index 1 because sorting by creating date
	            stationData.add(1, new StationItem(szStationName, false, false, bAllowRename, szStationToken, bAllowDelete, bQuickMix, bShared, lStationId));
	        }
	    }
	    return stationData.size();
	}
	
	public StationItem getStationItem(int nIndex){
		if(!isDataValid(nIndex)){
			return null;
		}
		return stationData.get(nIndex);
	}
	
    public int getStationCount() {
        if (null == stationData) {
            return 0;
        }
        return stationData.size();
    }
	
	public void clearStationData(){
		if(stationData != null){
			if(stationData.size() > 0){
				stationData.clear();
			}
		}
	}
	
	public String getStationName(int nIndex){
		if(!isDataValid(nIndex)){
			return null;
		}
		return stationData.get(nIndex).m_szStationName;
	}
	public String getStationName(String sStationToken) {
	    String sReturn = "";
	    if (null != sStationToken && !"".equals(sStationToken)) {
	        for (int i = 0; i < stationData.size(); i++) {
	            if (sStationToken.equals(stationData.get(i).m_szStationToken)) {
	                sReturn = stationData.get(i).m_szStationName;
	                break;
	            }
	        }
	    }
	    return sReturn;
	}
	
	public boolean getStationDeleted(int nIndex){
		if(!isDataValid(nIndex)){
			return false;
		}
		return stationData.get(nIndex).m_bDelSelected;
	}
	
	public boolean getStationPlayed(int nIndex){
		if(!isDataValid(nIndex)){
			return false;
		}
		return stationData.get(nIndex).m_bSelPlayed;
	}
	
	public void setStationDeleted(int nIndex, boolean bDel){
		if(!isDataValid(nIndex)){
			return;
		}
		stationData.get(nIndex).m_bDelSelected = bDel;
	}
	
	public void setStationPlayed(int nIndex, boolean nSel){
		if(!isDataValid(nIndex)){
			return;
		}
		if(nSel){
			m_nCurrPlay = nIndex;
		}else{
			m_nCurrPlay = -1;
		}
		stationData.get(nIndex).m_bSelPlayed = nSel;
	}
	
	public int getCurrPlay(){
		return m_nCurrPlay;
	}
	
/*	public void setTableRowItem(int nIndex, TableRow row){
		if(!isDataValid(nIndex)){
			return;
		}
		stationData.get(nIndex).m_trStationItem = row;
	} */
	
	public void setAllowRename(int nIndex, boolean bAllow){
		if(!isDataValid(nIndex)){
			return;
		}
		stationData.get(nIndex).m_bAllowRename = bAllow;
	}
	
	public boolean getAllowRename(int nIndex){
		if(!isDataValid(nIndex)){
			return false;
		}
		return stationData.get(nIndex).m_bAllowRename;
	}
	
	public void setStationToken(int nIndex, String szToken){
		if(!isDataValid(nIndex)){
			return;
		}
		stationData.get(nIndex).m_szStationToken = szToken;
	}
	
	public String getStationToken(int nIndex ){
		if(!isDataValid(nIndex)){
			return null;
		}
		return stationData.get(nIndex).m_szStationToken;
	}
	
	public void setAllowDelete(int nIndex, boolean bAllow){
		if(!isDataValid(nIndex)){
			return;
		}
		stationData.get(nIndex).m_bAllowDelete = bAllow;
	}
	
	public boolean setAllowDelete(int nIndex){
		if(!isDataValid(nIndex)){
			return false;
		}
		return stationData.get(nIndex).m_bAllowDelete;
	}
	
	public void setQuickMix(int nIndex, boolean bQuickMix){
		if(!isDataValid(nIndex)){
			return;
		}
		stationData.get(nIndex).m_bQuickMix = bQuickMix;
	}
	
	public boolean getQuickMix(int nIndex){
		if(!isDataValid(nIndex)){
			return false;
		}
		return stationData.get(nIndex).m_bQuickMix;
	}
	
	public void setShared(int nIndex, boolean bShared){
		if(!isDataValid(nIndex)){
			return;
		}
		stationData.get(nIndex).m_bShared = bShared;
	}
	
	public boolean getShared(int nIndex){
		if(!isDataValid(nIndex)){
			return false;
		}
		return stationData.get(nIndex).m_bShared;
	}
	
	public void setStationId(int nIndex, long lId){
		if(!isDataValid(nIndex)){
			return;
		}
		stationData.get(nIndex).m_lStationId = lId;
	}
	
	public long getStationId(int nIndex){
		if(!isDataValid(nIndex)){
			return -1;
		}
		return stationData.get(nIndex).m_lStationId;
	}
	
/*	public TableRow getStationTrItem(int nIndex){
		if(!isDataValid(nIndex)){
			return null;
		}
		return stationData.get(nIndex).m_trStationItem;
	} */
	
/*	public void setStationState(int nIndex, TextView tvState){
		if(!isDataValid(nIndex)){
			return;
		}
		stationData.get(nIndex).m_tvStationState = tvState;
	} */
	
/*	public TextView getStationTvState(int nIndex){
		if(!isDataValid(nIndex)){
			return null;
		}
		return stationData.get(nIndex).m_tvStationState;
	} */
	
	public int getStationDeletedCount(){
		if(stationData == null){
			return 0;
		}
		int nCount = 0;
		
		for(int i=0; i < getStationCount(); i++){
			if(getStationDeleted(i)){
				nCount++;
			}
		}
		
		return nCount;
	}
	
	public void setCurrentStationToken(String szStationToken){
		mszCurrStationToken = szStationToken;
	}
	
	public void setCurrentStationName(String szStationName){
		mszCurrStationName = szStationName;
	}
	
	public String getCurrStationName(){
		return mszCurrStationName;
	}
	
	public String getCurrStationToken(){
		return mszCurrStationToken;
	}
	
	private boolean isDataValid(int nIndex){
		if(stationData == null){
			return false;
		}
		if( 0 > nIndex || nIndex >= stationData.size()){
			return false;
		}
		return true;
	}
}
