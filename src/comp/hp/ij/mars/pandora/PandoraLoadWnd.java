package comp.hp.ij.mars.pandora;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PandoraLoadWnd extends PandoraWnd{
	
	private final String TAG	= "PandoraLoadWnd";
	private final int m_nMainLayoutResId = R.layout.pandora_loading;
	private boolean	m_bStop		= false;
	private boolean m_bTerm		= false;
	private static int	m_nPosition	= 0;
	
	private boolean m_bIsGetStationListSuccess = false;
	
	public PandoraLoadWnd(Activity active, Handler handler, int nId){
		super(active, handler, nId);
		super.setLayoutResId(m_nMainLayoutResId, HIDE_VIEW, HIDE_VIEW);
	}
	
	@Override
	protected void onClick(int resId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onClose() {
		// TODO Auto-generated method stub
		m_bTerm = true;
		m_bStop = true;
	}

	@Override
	protected void onShow() {
		// TODO Auto-generated method stub
		m_bTerm = false;
	}

	@Override
	protected void onTouchDown(int resId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onTouchUp(int resId) {
		// TODO Auto-generated method stub
		
	}

	public void setRunStop(){
		m_bStop = true;
	}
	
	public void initProgressBar(){
		final ProgressBar progressHorizontal = (ProgressBar) super.getApp().findViewById(R.id.pbLoading);
		progressHorizontal.setProgress(0);
		progressHorizontal.setSecondaryProgress(0);
		m_nPosition = 0;
	}
	
	public void setProgressPosition(int nPosition){
		final ProgressBar progressHorizontal = (ProgressBar) super.getApp().findViewById(R.id.pbLoading);
		progressHorizontal.setProgress(nPosition);
	}
	
	public int getProgressPosition(){
		final ProgressBar progressHorizontal = (ProgressBar) super.getApp().findViewById(R.id.pbLoading);
		return progressHorizontal.getProgress();
	}
	
	public void setInfo(String szInfo){
		TextView tvConnStatus = (TextView)super.getApp().findViewById(R.id.tvConnect);
		if(null != tvConnStatus){
			tvConnStatus.setText(szInfo);
		}
	}
	
	public boolean getIsGetStationListSuccess() {
	    return m_bIsGetStationListSuccess;
	}
	public void setIsGetStationListSuccess(boolean isGetStationListSuccess) {
	    m_bIsGetStationListSuccess = isGetStationListSuccess;
	}
	
	public void runLoading(){
		
		final ProgressBar progressHorizontal = (ProgressBar) super.getApp().findViewById(R.id.pbLoading);
		m_bStop = false;
		new Thread()
	    { 
	      public void run()
	      { 
	      	try
	      	{ 
	      		for(int i=m_nPosition; i < 100; i++,m_nPosition++){
	      			progressHorizontal.setProgress(i);
	      			if(m_bTerm){
	      				break;
		      		}
	      			if(m_bStop){
	      				progressHorizontal.setProgress(100);
	      				break;
	      			}
	      			java.lang.Thread.sleep(350);
	     		}
	      	}
	      	catch (Exception e)
	      	{
	      		m_nPosition = 100;
	      		e.printStackTrace();
	      		sendAppMsg(WND_MSG, WND_EXCP, null);
	      	}finally{	
	      		Log.i(TAG,"thread finally - pandora loading window");
	      		if(m_bTerm){
	      			return;
	      		}
	      		m_nPosition = 100;
	      		sendAppMsg(WND_MSG, WND_STOP, null);
	      	}
	      }
	    }.start();
	}

	
}
