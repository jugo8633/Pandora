package comp.hp.ij.mars.commui;


import android.os.Bundle;
import android.view.InflateException;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import comp.hp.ij.mars.pandora.R;		// change to your own package

public class CommActivity extends BaseActivity {
	protected static final int PAGE_INDICATOR_ID = 0x6A000000;
	public static final String PREFS = "CommActivityPref";
	
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.commui_main);
    }
    
    /** Put a view group to primary view */
    protected void attachPrimaryView(int viewID) throws InflateException {
    	addMyView(viewID, R.id.commUI_main_primaryView);
    }
    
    /** Put a view group to footer bar */
    protected void attachFooterBar(int viewID) throws InflateException {
    	addMyView(viewID, R.id.commUI_main_footerBar);
    }
    
    /** Put a view group to header bar */
    protected void attachHeaderBar(int viewID) throws InflateException {
    	addMyView(viewID, R.id.commUI_main_headerBar);
    }
    
    /** Put a view group to search bar */
    protected void attachSearchBar(int viewID) throws InflateException {
    	addMyView(viewID, R.id.commUI_main_searchBar);
    } 
    
    protected void hideFooterBar(){
    	RelativeLayout rlFootBar = (RelativeLayout)super.findViewById(R.id.commUI_main_footerBar);
    	if( null != rlFootBar){
    		rlFootBar.setVisibility(View.GONE);
    	}
    }
}
