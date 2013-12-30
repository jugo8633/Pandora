/**
 * @author jugo
 * @date 2009-12-03
 */
package comp.hp.ij.mars.pandora;

import comp.hp.ij.common.service.pandora.api.FileDownloader;
import comp.hp.ij.common.service.pandora.util.Logger;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;


public class PandoraPlaynowInfoWnd extends PandoraWnd{

	private final String TAG = "PandoraPlaynowInfoWnd";
	private final int m_nMainLayoutResId   = R.layout.pandora_playnow_info;
	private final int m_nMainLayoutResId_v = R.layout.pandora_playnow_info_v;
	private static String mszExplain = null;
	private static String mszStation = null;
	private static String mszArtistName = null;
	private static String mszSongName = null;
	private static String mszAlbumName = null;
	private static String mszAlbumArtURL = null;
	private static Bitmap mbmpAlbumArt = null;
	
	
	public PandoraPlaynowInfoWnd(Activity active, Handler handler, int nId){
		super(active, handler, nId);
		
		int nOrientation = application.getOrientation();
		if(PandoraApplication.ORIENTATION_LAND == nOrientation){
			super.setLayoutResId(m_nMainLayoutResId, HIDE_VIEW, HIDE_VIEW);
		}else{
			super.setLayoutResId(m_nMainLayoutResId_v, HIDE_VIEW, HIDE_VIEW);
		}
		
		super.setViewTouchEvent(R.id.tvPlayNowInfoOK);
	}
	

	@Override
	protected void onShow() {
		if(null != mszExplain){
			setExplain(mszExplain);
			setSongInfo(mszStation, mszArtistName, mszSongName, mszAlbumName);
			if(null != mbmpAlbumArt){
				ImageView ivTmp = (ImageView)super.getApp().findViewById(R.id.ivPlnInfoAlbumArt);
				if(null != ivTmp){
					ivTmp.setImageBitmap(mbmpAlbumArt);
				}
			}else{
				setAlbumArt(mszAlbumArtURL);
			}
		}
	}
	
	@Override
	protected void onClose() {
	}
	
	@Override
	protected void onTouchDown(int resId) {
		switch(resId){
		case R.id.tvPlayNowInfoOK:
			((TextView)super.getApp().findViewById(R.id.tvPlayNowInfoOK)).setBackgroundResource(R.drawable.ok_btn_focus);
			break;
		}
	}
	
	@Override
	protected void onTouchUp(int resId) {
		switch(resId){
		case R.id.tvPlayNowInfoOK:
			((TextView)super.getApp().findViewById(R.id.tvPlayNowInfoOK)).setBackgroundResource(R.drawable.ok_btn);
			mszExplain = null;
			mszStation = null;
			mszArtistName = null;
			mszSongName = null;
			mszAlbumName = null;
			mszAlbumArtURL = null;
			mbmpAlbumArt = null;
			sendAppMsg(WND_MSG,WND_STOP, null);
			break;
		}
	}
	
	@Override
	protected void onClick(int resId) {
	}
	
	public void setExplain(String szExplain){
		TextView tvTmp = (TextView)super.getApp().findViewById( R.id.tvPlayNowInfoContent );
		mszExplain = szExplain;
		if(null != tvTmp){
			tvTmp.setText(mszExplain);
		}
	}
	
	public void setSongInfo(String szStation, String szArtistName, String szSongName, String szAlbumName){
		mszStation = szStation;
		mszArtistName = szArtistName;
		mszSongName = szSongName;
		mszAlbumName = szAlbumName;
		TextView tvTmp = (TextView)super.getApp().findViewById( R.id.tvPlnInfoStationName );
		if(null != tvTmp){
			tvTmp.setText(szStation);
		}
		tvTmp = (TextView)super.getApp().findViewById( R.id.tvPlnInfoArtistName );
		if(null != tvTmp){
			tvTmp.setText(szArtistName);
		}
		tvTmp = (TextView)super.getApp().findViewById( R.id.tvPlnInfoSongName );
		if(null != tvTmp){
			tvTmp.setText(szSongName);
		}
		tvTmp = (TextView)super.getApp().findViewById( R.id.tvPlnInfoAlbumName );
		if(null != tvTmp){
			tvTmp.setText(szAlbumName);
		}
	}
	
	public void setAlbumArt(String szURL){
		if(null == szURL){
			return;
		}
		mszAlbumArtURL = szURL;
		mbmpAlbumArt = getAlbumArt(szURL);
		if( null != mbmpAlbumArt ){
			ImageView ivTmp = (ImageView)super.getApp().findViewById(R.id.ivPlnInfoAlbumArt);
			if(null != ivTmp){
				ivTmp.setImageBitmap(mbmpAlbumArt);
			}
		}
	}
	
	private Bitmap getAlbumArt(String szURL){
		Bitmap downloadedBitmap = null;
		FileDownloader fileDownloader = new FileDownloader();
		try {
			downloadedBitmap = fileDownloader.downloadImage(szURL);
			fileDownloader.disconnect();			
		} catch (Exception e) {
		    Logger.e(e);
		}
		return downloadedBitmap;
	}
}
