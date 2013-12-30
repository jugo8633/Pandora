package comp.hp.ij.mars.commui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.view.InflateException;

public class BaseActivity extends Activity {
	
	protected void addMyView(int viewID, int baseViewID) throws InflateException {
    	RelativeLayout base = (RelativeLayout) findViewById(baseViewID);
    	String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layInflater = (LayoutInflater)getSystemService(infService);
        layInflater.inflate(viewID, base, true);
    }
}
