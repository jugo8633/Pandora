package comp.hp.ij.mars.pandora;

import java.util.ArrayList;
import frame.view.MarqueeTextView;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PandoraSearchWnd extends PandoraWnd{

	private final String TAG = "PandoraSearchWnd";
	private final int m_nMainLayoutResId  = R.layout.pandora_search;
	private final int mnHeaderLayoutResId = R.layout.pandora_header_contextual_btn;
	private ListView listView = null;
	private static ArrayList<SearchItem> searchItem = new ArrayList<SearchItem>();
	
	public PandoraSearchWnd(Activity active, Handler handler, int id) {
		super(active, handler, id);
		super.setLayoutResId(m_nMainLayoutResId, HIDE_VIEW, mnHeaderLayoutResId);
		super.setViewTouchEvent(R.id.tvPandoraHeaderBack);
		super.setViewTouchEvent(R.id.tvPandoraHeaderLogout);
	}

	public class SearchItem{
		private String mszType = null;
		private String mszSearchItem = null;
		private String mszMusicToken = null;
		
		public SearchItem(String szType, String szSearchItem, String szMusicToken){
			mszType = szType;
			mszSearchItem = szSearchItem;
			mszMusicToken = szMusicToken;
		}
		public String getType(){
			return mszType;
		}
		public String getItem(){
			return mszSearchItem;
		}
		public String getMusicToken(){
			return mszMusicToken;
		}
	}
	
	@Override
	protected void onShow() {
		initTitle();
		initListView();
	}
	
	@Override
	protected void onClose() {
		if(null != listView){
			listView = null;
		}
	}
	
	@Override
	protected void onClick(int resId) {
		Log.d(TAG,"select search result item: " + resId);
		
		// +++++ restore default color +++++ //
		for (int i = 0 ; i < searchItem.size() ; i++) {
		    MarqueeTextView tvTmp = (MarqueeTextView) listView.findViewById(i + 255);
		    if((null != tvTmp)){
                tvTmp.setTextColor(COLOR_WHITE);
            }
		}
		// ----- restore default color ----- //
		
		String szMusicToken = null;
		if( 0 <= resId && resId < searchItem.size()){
			szMusicToken = searchItem.get(resId).mszMusicToken;
		}else{
			Log.e(TAG,"invalid search item data index! total search item = " + searchItem.size());
		}
		if(null != szMusicToken){
			MarqueeTextView tvTmp = (MarqueeTextView)listView.findViewById(resId + 255);
			if((null != tvTmp)){
				tvTmp.setTextColor(COLOR_FOCUS);
			}
			sendAppMsg(WND_MSG, WND_ITEM_CLICK, szMusicToken);
		}
	}

	@Override
	protected void onTouchDown(int resId) {
		switch(resId){
		case R.id.tvPandoraHeaderBack:
			TextView tvTmp = (TextView)super.getApp().findViewById(R.id.tvPandoraHeaderBack);
			if( null != tvTmp ){
				tvTmp.setBackgroundResource(R.drawable.btn_header_focus);
			}
			break;
		case R.id.tvPandoraHeaderLogout:	
			((TextView)super.getApp().findViewById(R.id.tvPandoraHeaderLogout))
			.setBackgroundResource(R.drawable.btn_logout_focus);
			break;
		}
	}

	@Override
	protected void onTouchUp(int resId) {
		switch(resId){
		case R.id.tvPandoraHeaderBack:
			TextView tvTmp = (TextView)super.getApp().findViewById(R.id.tvPandoraHeaderBack);
			if( null != tvTmp ){
				tvTmp.setBackgroundResource(R.drawable.btn_header);
			}
			sendAppMsg(WND_MSG, WND_STOP, null);
			break;
		case R.id.tvPandoraHeaderLogout:	
			((TextView)super.getApp().findViewById(R.id.tvPandoraHeaderLogout))
			.setBackgroundResource(R.drawable.btn_logout);
			sendAppMsg(WND_MSG,WND_LOGOUT, null);
			break;
		}
	}
	
	private void initTitle(){
		MarqueeTextView tvView = (MarqueeTextView)super.getApp().findViewById(R.id.tvPandoraHeaderTitle);
		if(null != tvView){
			tvView.setText(super.getApp().getString(R.string.search_result));
		}
	}
	
	private void initListView(){
        if(listView != null){
        	listView = null;
        }
        
        listView = (ListView)super.getApp().findViewById(R.id.lvSearchResult);
        try{
    		listView.setAdapter(new SearchAdapter(super.getApp()));
    		Log.i(TAG,"search result listview set adapter ok");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	}
	
	public void initData(PandoraAPIService pservice){
		int nIndex = 0;
		String szTmp = null;
		String szToken = null;
		clearData();
		if( null != pservice.getSearchResultArtist() ){
			int nArtistCount = pservice.getSearchResultArtist().size();
			for(nIndex = 0; nIndex < nArtistCount; nIndex++){
				szTmp = pservice.getSearchResultArtist().get(nIndex);
				szToken = pservice.getSearchResultArtistMusicToken().get(nIndex);
				searchItem.add(new SearchItem("Artist", szTmp, szToken));
			}
		}
		if( null != pservice.getSearchResultSongArtist()){
			int nSongCount = pservice.getSearchResultSongArtist().size();
			for(nIndex = 0; nIndex < nSongCount; nIndex++){
				szTmp = pservice.getSearchResultSongArtist().get(nIndex);
				szToken = pservice.getSearchResultSongMusicToken().get(nIndex);
				searchItem.add(new SearchItem("Song", szTmp, szToken));
			}
		}
	}
	
	public void clearData(){
		if( null != searchItem){
			searchItem.clear();
		}
	}
	
	public class ViewItem{
		TextView	      mtvType;
		MarqueeTextView	  mtvItem;
		RelativeLayout    mrlItem;
	}
	
	public String getMusicToken(int nIndex){
		String szMusicToken = null;
		if( -1 != nIndex ){
			if( 0 <= nIndex && nIndex < searchItem.size()){
				szMusicToken = searchItem.get(nIndex).mszMusicToken;
			}
		}
		return szMusicToken;
	} 
	
	public class SearchAdapter extends BaseAdapter{

		private Context m_context;
		private LayoutInflater m_inflater;
		
		public SearchAdapter(Context c){
			m_context = c;
			m_inflater = LayoutInflater.from(this.m_context);
		}
		
		public int getCount() {
			TextView tvTmp = (TextView)PandoraSearchWnd.this.getApp().findViewById(R.id.tvSearchCount);
			if( null != tvTmp ){
				String szStatus = "Search found " + searchItem.size() + " stations";
				tvTmp.setText(szStatus);
			}
			return searchItem.size();
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewItem viewItem;
		    if( null != convertView ){
		    	convertView = null;
		    }
			viewItem = new ViewItem();
			convertView = m_inflater.inflate(R.layout.pandora_search_item,null);
			viewItem.mtvType = (TextView)convertView.findViewById(R.id.tvSearchType);
			viewItem.mtvItem = (MarqueeTextView)convertView.findViewById(R.id.tvSearchResult);
			viewItem.mrlItem = (RelativeLayout)convertView.findViewById(R.id.rlSearchItem);
			viewItem.mrlItem.setId(position);
			viewItem.mtvItem.setId(position + 255);
			PandoraSearchWnd.this.RegisterEvent(viewItem.mrlItem, PandoraWnd.EVENT_CLICK);
			if( null != viewItem.mtvType ){
				viewItem.mtvType.setText(searchItem.get(position).mszType);
			}
			if( null != viewItem.mtvItem ){
				if(PandoraApplication.ORIENTATION_LAND == application.getOrientation()){
					viewItem.mtvItem.setViewWidth(620);
				}	
				viewItem.mtvItem.setText(searchItem.get(position).mszSearchItem );
				viewItem.mtvItem.startScroll();
			}
			
			return convertView;
		}
		
	};
}
