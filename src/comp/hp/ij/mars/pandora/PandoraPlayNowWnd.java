package comp.hp.ij.mars.pandora;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import comp.hp.ij.common.service.pandora.api.FileDownloader;
import comp.hp.ij.common.service.pandora.util.Logger;

import frame.view.MarqueeTextView;
import frame.view.MenuList;


public class PandoraPlayNowWnd extends PandoraWnd{

	private final String TAG = "PandoraPlayNowWnd";
	private final int m_nMainLayoutResId  = R.layout.pandora_playnow;
	private final int m_nFootLayoutResId  = R.layout.pandora_playnow_footbar;
	private final int mnHeaderLayoutResId = R.layout.pandora_header_contextual_btn;
	private Gallery playnowGallery = null;
	private static Thread thdAlbumArt = null;
	private Thread thdTrack = null;
	private MenuList menu = null;
	private int mnPlayNowTrackItem = R.layout.pandora_playnow_track;
		
	public PandoraPlayNowWnd(Activity active, Handler handler, int nId){
		super(active, handler, nId);
		super.setLayoutResId(m_nMainLayoutResId, m_nFootLayoutResId, mnHeaderLayoutResId);
		super.setViewTouchEvent(R.id.rlPlnFootPause);
		super.setViewTouchEvent(R.id.rlPlnFootThumbUp);
		super.setViewTouchEvent(R.id.rlPlnFootThumbDown);
		super.setViewTouchEvent(R.id.rlPlnFootNextSong);
		super.setViewTouchEvent(R.id.rlPlnFootMenu);
		super.setViewTouchEvent(R.id.tvPandoraHeaderBack);
		super.setViewTouchEvent(R.id.tvPandoraHeaderLogout);
		int nOrientation = application.getOrientation();
		if(PandoraApplication.ORIENTATION_LAND == nOrientation){
			mnPlayNowTrackItem = R.layout.pandora_playnow_track;
		}else{
			mnPlayNowTrackItem = R.layout.pandora_playnow_track_v;
		}
	}
	
	@Override
	protected void onShow() {
		initHeader();
		initPlaynowList();
		updatePlaynowList();

		menu = (MenuList)super.getApp().findViewById(R.id.rlPlayNowMenuList);
		menu.setMenuView(R.layout.menu_playnow,
				null,
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		super.RegisterEvent(R.id.tvPlayMenuBookMarkArtist, EVENT_TOUCH);
		super.RegisterEvent(R.id.tvPlayMenuBookMarkSong, EVENT_TOUCH);
		super.RegisterEvent(R.id.tvPlayMenuWhyThisSong, EVENT_TOUCH);
		super.RegisterEvent(R.id.tvPlayMenuDotPlay, EVENT_TOUCH);

		initState();
	}
	
	@Override
	protected void onClose() {
		if( null != thdAlbumArt ){
			if(thdAlbumArt.isAlive()){
				thdAlbumArt = null;
			}
		}
		if( null != thdTrack ){
			thdTrack = null;
		}
	}
	
	@Override
	protected void onClick(int resId) {
	}

	@Override
	protected void onTouchDown(int resId) {
		switch(resId){
		case R.id.rlPlnFootPause: {
			boolean bPauseSong = false;
			bPauseSong = application.getPlayNowPauseSongStatus();
			if(bPauseSong){
				sendAppMsg(WND_MSG,WND_RESUME_SONG, null);
				((ImageView)super.getApp().findViewById(R.id.ivPlnPause))
				.setImageResource(R.drawable.pandora_pln_pause);
				((TextView)super.getApp().findViewById(R.id.tvPlnPause))
				.setText(super.getApp().getString(R.string.pause));
				((TextView)super.getApp().findViewById(R.id.tvPlnPause))
				.setTextColor(COLOR_WHITE);
				application.setPlayNowPauseSongStatus(false);	
			}else{
				sendAppMsg(WND_MSG,WND_PAUSE_SONG, null);
				((ImageView)super.getApp().findViewById(R.id.ivPlnPause))
				.setImageResource(R.drawable.pandora_pln_pause_focus);
				((TextView)super.getApp().findViewById(R.id.tvPlnPause))
				.setText(super.getApp().getString(R.string.resume));
				((TextView)super.getApp().findViewById(R.id.tvPlnPause))
				.setTextColor(COLOR_FOCUS);
				application.setPlayNowPauseSongStatus(true);	
			}
			break;
		}
		case R.id.rlPlnFootThumbUp:
			((ImageView)super.getApp().findViewById(R.id.ivPlnThumbUp))
			.setImageResource(R.drawable.pandora_pln_thumbup_focus);
			((TextView)super.getApp().findViewById(R.id.tvPlnThumbUp))
			.setTextColor(COLOR_FOCUS);
			sendAppMsg(WND_MSG, WND_THUMB_UP, null);
			break;
		case R.id.rlPlnFootThumbDown:
			((ImageView)super.getApp().findViewById(R.id.ivPlnThumbDown))
			.setImageResource(R.drawable.pandora_pln_thumbdown_focus);
			((TextView)super.getApp().findViewById(R.id.tvPlnThumbDown))
			.setTextColor(COLOR_FOCUS);
			sendAppMsg(WND_MSG, WND_THUMB_DOWN, null);
			break;
        case R.id.rlPlnFootNextSong:
            ((ImageView) super.getApp().findViewById(R.id.ivPlnNextSong)).setImageResource(R.drawable.pandora_pln_next_song_focus);
            ((TextView) super.getApp().findViewById(R.id.tvPlnNextSong)).setTextColor(COLOR_FOCUS);
            sendAppMsg(WND_MSG, WND_PLAY_NEXT_SNOG, null);
            break;
		case R.id.rlPlnFootMenu:
			boolean bMenuShow = false;
			bMenuShow = application.getPlayNowMenuStatus();
			if(bMenuShow){
				menu.showMenu(false);
				((ImageView)super.getApp().findViewById(R.id.ivPlnMenu))
				.setImageResource(R.drawable.pandora_pln_menu);
				((TextView)super.getApp().findViewById(R.id.tvPlnMenu))
				.setTextColor(COLOR_WHITE);
				application.setPlayNowMenuStatus(false);
			}else
			{
				menu.showMenu(true);
				((ImageView)super.getApp().findViewById(R.id.ivPlnMenu))
				.setImageResource(R.drawable.pandora_pln_menu_focus);
				((TextView)super.getApp().findViewById(R.id.tvPlnMenu))
				.setTextColor(COLOR_FOCUS);
				application.setPlayNowMenuStatus(true);
			}
			break;
		case R.id.tvPandoraHeaderBack:
			TextView tvTmp = (TextView) super.getApp().findViewById(R.id.tvPandoraHeaderBack);
			if( null != tvTmp ){
				tvTmp.setBackgroundResource(R.drawable.btn_header_focus);
			}
			sendAppMsg(SHOW_PROGRESS_DLG, WND_SHOW, null);
			break;
		case R.id.tvPandoraHeaderLogout:
			((TextView)super.getApp().findViewById(R.id.tvPandoraHeaderLogout))
			.setBackgroundResource(R.drawable.btn_logout_focus);
			break;
		case R.id.tvPlayMenuBookMarkArtist:
		case R.id.tvPlayMenuBookMarkSong:
		case R.id.tvPlayMenuWhyThisSong:
		case R.id.tvPlayMenuDotPlay:
			((TextView)super.getApp().findViewById(resId)).setTextColor(COLOR_FOCUS);
			break;
		}
	}

	@Override
	protected void onTouchUp(int resId) {
		switch(resId){
		case R.id.rlPlnFootThumbUp:
			((ImageView)super.getApp().findViewById(R.id.ivPlnThumbUp))
			.setImageResource(R.drawable.pandora_pln_thumbup);
			((TextView)super.getApp().findViewById(R.id.tvPlnThumbUp))
			.setTextColor(COLOR_WHITE);
			break;
		case R.id.rlPlnFootThumbDown:
			((ImageView)super.getApp().findViewById(R.id.ivPlnThumbDown))
			.setImageResource(R.drawable.pandora_pln_thumbdown);
			((TextView)super.getApp().findViewById(R.id.tvPlnThumbDown))
			.setTextColor(COLOR_WHITE);
			break;
		case R.id.rlPlnFootNextSong:
			((ImageView)super.getApp().findViewById(R.id.ivPlnNextSong))
			.setImageResource(R.drawable.pandora_pln_next_song);
			((TextView)super.getApp().findViewById(R.id.tvPlnNextSong))
			.setTextColor(COLOR_WHITE);
			break;
		case R.id.tvPandoraHeaderBack:
			TextView tvTmp = (TextView) super.getApp().findViewById(R.id.tvPandoraHeaderBack);
			if( null != tvTmp ){
				tvTmp.setBackgroundResource(R.drawable.btn_header);
			}
			sendAppMsg(WND_MSG, WND_STOP, null);
			break;
		case R.id.tvPlayMenuBookMarkArtist:
			((TextView)super.getApp().findViewById(resId)).setTextColor(COLOR_WHITE);
			sendAppMsg(WND_MSG, WND_BOOK_MARK_ARTIST, null);
			break;
		case R.id.tvPlayMenuBookMarkSong:
			((TextView)super.getApp().findViewById(resId)).setTextColor(COLOR_WHITE);
			sendAppMsg(WND_MSG, WND_BOOK_MARK_SONG, null);
			break;
		case R.id.tvPlayMenuWhyThisSong:
			((TextView)super.getApp().findViewById(resId)).setTextColor(COLOR_WHITE);
			sendAppMsg(WND_MSG,WND_WHY_PLAY, null);
			break;
		case R.id.tvPlayMenuDotPlay:
			((TextView)super.getApp().findViewById(resId)).setTextColor(COLOR_WHITE);
			sendAppMsg(WND_MSG, WND_SLEEP_SONG, null);
			break;	
		case R.id.tvPandoraHeaderLogout:
			((TextView)super.getApp().findViewById(R.id.tvPandoraHeaderLogout))
			.setBackgroundResource(R.drawable.btn_logout);
			sendAppMsg(WND_MSG,WND_LOGOUT, null);
			break;
		}
	}
	
	private void initHeader(){
		String szStationName = application.stationData.getCurrStationName();
		Log.v(TAG,"get current station name: " + szStationName);
		MarqueeTextView stvTmp = (MarqueeTextView)super.getApp().findViewById(R.id.tvPandoraHeaderTitle);
		int nOrientation = application.getOrientation();
		if(PandoraApplication.ORIENTATION_LAND == nOrientation){
			stvTmp.setViewWidth(550);
		}
		if( null != stvTmp ){
			if(null != szStationName && szStationName.length() > 0){
				stvTmp.setText(szStationName);
				stvTmp.startScroll();
			}else{
				stvTmp.setText("");
			}
		}
	}
	
	private void initState(){
		boolean bPauseSong = false;
		boolean bMenuShow  = false;
		bPauseSong = application.getPlayNowPauseSongStatus();
		bMenuShow  = application.getPlayNowMenuStatus();
		if(bPauseSong){
			((ImageView)super.getApp().findViewById(R.id.ivPlnPause))
			.setImageResource(R.drawable.pandora_pln_pause_focus);
			((TextView)super.getApp().findViewById(R.id.tvPlnPause))
			.setText(super.getApp().getString(R.string.resume));
			((TextView)super.getApp().findViewById(R.id.tvPlnPause))
			.setTextColor(COLOR_FOCUS);
		}else{
			((ImageView)super.getApp().findViewById(R.id.ivPlnPause))
			.setImageResource(R.drawable.pandora_pln_pause);
			((TextView)super.getApp().findViewById(R.id.tvPlnPause))
			.setText(super.getApp().getString(R.string.pause));
			((TextView)super.getApp().findViewById(R.id.tvPlnPause))
			.setTextColor(COLOR_WHITE);
		}
		
		if(bMenuShow){
			menu.showMenu(true);
			((ImageView)super.getApp().findViewById(R.id.ivPlnMenu))
			.setImageResource(R.drawable.pandora_pln_menu_focus);
			((TextView)super.getApp().findViewById(R.id.tvPlnMenu))
			.setTextColor(COLOR_FOCUS);
		}else{
			menu.showMenu(false);
			((ImageView)super.getApp().findViewById(R.id.ivPlnMenu))
			.setImageResource(R.drawable.pandora_pln_menu);
			((TextView)super.getApp().findViewById(R.id.tvPlnMenu))
			.setTextColor(COLOR_WHITE);
		}
	}
	
	public void showMenu(){
		((ImageView)super.getApp().findViewById(R.id.ivPlnMenu)).setImageResource(R.drawable.pandora_pln_menu_focus);
		((TextView)super.getApp().findViewById(R.id.tvPlnMenu)).setTextColor(COLOR_FOCUS);
		application.setPlayNowMenuStatus(true);
		sendAppMsg(WND_MSG,WND_SHOW_PLAY_MENU, null);	
	}
	
	private void initPlaynowList(){
		if(playnowGallery != null)
		{
			playnowGallery = null;
		}
		playnowGallery = (Gallery) super.getApp().findViewById(R.id.GalleryPln);
		playnowGallery.setAnimationDuration(300);
		//playnowGalley.setAlwaysDrawnWithCacheEnabled(true); // TODO
		//playnowGalley.setAnimationCacheEnabled(true);
		playnowGallery.setOnItemSelectedListener(selectedListener);
		playnowGallery.setOnTouchListener(touchListener);
	}
	
	OnItemSelectedListener selectedListener = new OnItemSelectedListener(){

		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			setPreviewShow(position);
		}

		public void onNothingSelected(AdapterView<?> parent) {

		}
		
	};
	
	OnTouchListener touchListener = new OnTouchListener() {

		public boolean onTouch(View v, MotionEvent event) {
			switch(event.getAction()){
    			case MotionEvent.ACTION_DOWN: {
    			    break;
    			}
    			case MotionEvent.ACTION_UP: {
    				runPreviewShow();
    				break;
    			}
			}
			return false;
		}
		
	};
	
	public void updatePlaynowList(){
		if(playnowGallery == null){
			return;
		}
		if(application.playData == null){
			return;
		}
		if( null != thdAlbumArt ){
			if(thdAlbumArt.isAlive()){
				thdAlbumArt = null;
			}
		}
		if(application.playData.getPlayCount() > 0){
			while(true){
				if(PandoraApplication.MAX_PLAY_NOW_TRACK >= application.playData.getPlayCount()){
					break;
				}
				application.playData.removeData(0);
			}
			//playnowGallery.destroyDrawingCache(); // TODO 0
			playnowGallery.setAdapter(new PlayAdapter(super.getApp()));
			playnowGallery.setSelection(application.playData.getNowPlayIndex());
						
			setAlbumArt();
			runPreviewShow();
		}
	}
	
	public void resetPauseButton() {
        boolean bPauseSong = false;
        bPauseSong = application.getPlayNowPauseSongStatus();
        if (bPauseSong) {
            ImageView imageViewPause = ((ImageView) super.getApp().findViewById(R.id.ivPlnPause));
            TextView textViewPause = ((TextView) super.getApp().findViewById(R.id.tvPlnPause));
            if (null != imageViewPause && null != textViewPause) {
                imageViewPause.setImageResource(R.drawable.pandora_pln_pause);
                textViewPause.setText(super.getApp().getString(R.string.pause));
                textViewPause.setTextColor(COLOR_WHITE);
            }
            application.setPlayNowPauseSongStatus(false);   
        }
	}
		
	private void runPreviewShow(){
		if(null != thdTrack){
			thdTrack = null;
		}
		thdTrack = new Thread()
	    { 
	      public void run()
	      { 
	      	try
	      	{ 
	      		sleep(1000);
	      		Message msg = new Message();
	    		msg.arg1 = 10003;
	    		playHandler.sendMessage(msg);
	      	}
	      	catch (Exception e)
	      	{
	      		e.printStackTrace();
	      	}
	      }
	    };
	    thdTrack.start();
	}

	
    private void setPreviewShow(int nPosition) {
        Log.v(TAG, "setPreviewShow position = [" + nPosition + "]");
        int nOrientation = application.getOrientation();
        int iVisibilityParameter = View.GONE;
        RelativeLayout rlNotCurrentAlbumInfo = null;
        
        if (PandoraApplication.ORIENTATION_PORT == nOrientation) {
            iVisibilityParameter = View.INVISIBLE;
        }
        
        if (0 < nPosition) {
            rlNotCurrentAlbumInfo = (RelativeLayout) playnowGallery.findViewById(nPosition - 1);
            if (null != rlNotCurrentAlbumInfo) {
                rlNotCurrentAlbumInfo.setVisibility(iVisibilityParameter);
            } else {
                Log.e(TAG, "get RelativeLayout (nPosition - 1) invalid");
            }
            rlNotCurrentAlbumInfo = (RelativeLayout) playnowGallery.findViewById(nPosition + 1);
            if (null != rlNotCurrentAlbumInfo) {
                rlNotCurrentAlbumInfo.setVisibility(iVisibilityParameter);
            } else {
                Log.e(TAG, "get RelativeLayout (nPosition + 1) invalid");
            }
        } else if (0 == nPosition) {
            rlNotCurrentAlbumInfo = (RelativeLayout) playnowGallery.findViewById(nPosition + 1);
            if (null != rlNotCurrentAlbumInfo) {
                rlNotCurrentAlbumInfo.setVisibility(iVisibilityParameter);
            }
        }
        
        RelativeLayout rlCurrView = (RelativeLayout) playnowGallery.findViewById(nPosition);
        if (null != rlCurrView) {
            rlCurrView.setVisibility(View.VISIBLE);
        }
    }
	
	/*
	public class TrackView{
		ImageView	    m_ivAlbumArt;	
		MarqueeTextView	m_tvArtistName;
		MarqueeTextView	m_tvSongName;
		MarqueeTextView	m_tvAlbumName;
		ImageView	    m_ivThumbUp;
	}
	*/
	
	public class PlayAdapter extends BaseAdapter {

		private Context m_context;
		private LayoutInflater m_inflater;
		
		public PlayAdapter(Context c){
			m_context = c;
			m_inflater = LayoutInflater.from(this.m_context);
		}
		
		public int getCount() {
			if(null != application.playData){
				return application.playData.getPlayCount();
			}
			return 0;
		}

		public Object getItem(int position) {
		    /*
			if(application.playData != null){
			    View view = application.playData.getConvetView(arg0);
			    Logger.d("arg0 [" + arg0 + "] View [" + view + "]");
				return view;
			}
			*/
			return position;
		}

        public long getItemId(int position) {
            return position;
        }

		public View getView(int position, View convertView, ViewGroup parent) {
		    Logger.d();
		    
		    View viewReturn = m_inflater.inflate(mnPlayNowTrackItem, null);
		    
		    ImageView ivAlbumArt = (ImageView) viewReturn.findViewById(R.id.ivPlnTrackAlbumArt);
            if (null != ivAlbumArt) {
                ivAlbumArt.setId(position+100);
            }
            
		    MarqueeTextView mtvArtistName = (MarqueeTextView) viewReturn.findViewById(R.id.tvPlnTrackArtistName);
		    MarqueeTextView mtvSongName   = (MarqueeTextView) viewReturn.findViewById(R.id.tvPlnTrackSongName);
		    MarqueeTextView mtvAlbumName  = (MarqueeTextView) viewReturn.findViewById(R.id.tvPlnTrackAlbumName);
		    ImageView ivThumbUp           = (ImageView) viewReturn.findViewById(R.id.ivPlnTrackThumb);
		    
		    RelativeLayout rlTrackAlbumInfo = (RelativeLayout) viewReturn.findViewById(R.id.rlPlnTrackAlbumInfo);
            if (null != rlTrackAlbumInfo) {
                rlTrackAlbumInfo.setId(position);
            }
            
            if (null != application.playData) {
                if (null != application.playData.getPlayItem(position)) { 
                    
                    if (null != application.playData.getAlbumArt(position)) {
                        if (null != application.playData.getAlbumArtBmp(position)) {
                            ivAlbumArt.setImageBitmap(application.playData.getAlbumArtBmp(position));
                        }
                    }
                    
                    mtvArtistName.setText(application.playData.getArtistName(position)); 
                    mtvSongName.setText(application.playData.getSongName(position));
                    mtvAlbumName.setText(application.playData.getAlbumName(position));
                    mtvArtistName.startScroll();
                    mtvSongName.startScroll();
                    mtvAlbumName.startScroll();
                    if (application.getAlarmShowed()) {
                        mtvArtistName.pauseScroll();
                        mtvSongName.pauseScroll();
                        mtvAlbumName.pauseScroll();
                    }
                    
                    // TODO 0
                    /*
                    application.playData.setThumbUpView(position, ivThumbUp);
                    if (application.playData.getThumbUp(position)) {
                        ivThumbUp.setVisibility(View.VISIBLE);
                    } else {
                        ivThumbUp.setVisibility(View.INVISIBLE);
                    }
                    */
                    int iThumb = application.playData.getThumb(position);
                    if (0 == iThumb) {
                        //ivThumbUp.setVisibility(View.INVISIBLE);
                    } else if (1 == iThumb) {
                        ivThumbUp.setImageResource(R.drawable.pandora_pln_thumbup_mark);
                        ivThumbUp.setVisibility(View.VISIBLE);
                    } else if (-1 == iThumb) {
                        ivThumbUp.setImageResource(R.drawable.pandora_pln_thumbdown_mark);
                        ivThumbUp.setVisibility(View.VISIBLE);
                    } else {
                        Logger.e("Not support thumb type [" + iThumb + "]");
                    }
                }
            } // end of if (null != application.playData)
            
		    return viewReturn;
		    
		    /*
			TrackView trackView;
			if (convertView == null) {
				convertView = m_inflater.inflate(mnPlayNowTrackItem, null);
				trackView = new TrackView();
				trackView.m_ivAlbumArt    = (ImageView)convertView.findViewById(R.id.ivPlnTrackAlbumArt);
				trackView.m_tvArtistName  = (MarqueeTextView)convertView.findViewById(R.id.tvPlnTrackArtistName);
				trackView.m_tvSongName    = (MarqueeTextView)convertView.findViewById(R.id.tvPlnTrackSongName);
				trackView.m_tvAlbumName   = (MarqueeTextView)convertView.findViewById(R.id.tvPlnTrackAlbumName);
				trackView.m_ivThumbUp     = (ImageView)convertView.findViewById(R.id.ivPlnTrackThumbUp);
				RelativeLayout rlTrackRight = (RelativeLayout)convertView.findViewById(R.id.rlPlnTrackAlbumInfo);
								
				if(null != rlTrackRight){
					rlTrackRight.setId(position);
				}
			
				if(null != trackView.m_ivAlbumArt){
					trackView.m_ivAlbumArt.setId(position+100);
				}
				
				if(application.playData != null){
					if(application.playData.getPlayItem(position) != null){	
						if(null != application.playData.getAlbumArt(position)){
							if( null != application.playData.getAlbumArtBmp(position)){
								trackView.m_ivAlbumArt.setImageBitmap(application.playData.getAlbumArtBmp(position));
							}
						}  
						application.playData.setThumbUpView(position, trackView.m_ivThumbUp);
						trackView.m_tvArtistName.setText(application.playData.getArtistName(position));	
						trackView.m_tvSongName.setText(application.playData.getSongName(position));
						trackView.m_tvAlbumName.setText(application.playData.getAlbumName(position));
						trackView.m_tvArtistName.startScroll();
						trackView.m_tvSongName.startScroll();
						trackView.m_tvAlbumName.startScroll();
						if(application.getAlarmShowed()){
							trackView.m_tvArtistName.pauseScroll();
							trackView.m_tvSongName.pauseScroll();
							trackView.m_tvAlbumName.pauseScroll();
						}
						if(application.playData.getThumbUp(position)){
							trackView.m_ivThumbUp.setVisibility(View.VISIBLE);
						}else{
							trackView.m_ivThumbUp.setVisibility(View.INVISIBLE);
						}
					}
				}
			    convertView.setTag(trackView);
			   } else {
				   trackView = (TrackView)convertView.getTag();
			   } 
			return convertView;
		    */
		}		
	}
	
	public void updateProgress() {
	    int iIndexNowPlay = application.playData.getNowPlayIndex();
	    int iSelectedItemPosition = playnowGallery.getSelectedItemPosition();
	    
	    if (iIndexNowPlay != iSelectedItemPosition) {
	        return;
	    }
	    
	    String sTotalDurationSeconds = application.playData.getTotalDuration(iIndexNowPlay);
	    String sCurrentPositionSeconds = application.playData.getCurrentPosition(iIndexNowPlay);
	    
	    int iTotalDuration   = 1;
	    int iCurrentPosition = 0;
	    try {
	        iTotalDuration = Integer.parseInt(sTotalDurationSeconds);
	        iCurrentPosition = Integer.parseInt(sCurrentPositionSeconds);
	        if (iCurrentPosition > iTotalDuration) {
	            iCurrentPosition = iTotalDuration;
	        }
	    } catch (Exception e) {
	        Logger.e("Can not parse [" + sTotalDurationSeconds + "] to integer");
	        Logger.e("Can not parse [" + sCurrentPositionSeconds + "] to integer");
	    }
	    
	    String sTotalDurationFormatted   = formatSeconds(sTotalDurationSeconds);
	    String sCurrentPositionFormatted = formatSeconds(sCurrentPositionSeconds);
	    
        int iCount = playnowGallery.getChildCount();
        if (0 < iCount) {
            View view = playnowGallery.getChildAt(iCount - 1);
            if (null != view) {
                View viewProgressBar = view.findViewById(R.id.pbPlnTrackProgress);
                if (null != viewProgressBar) {
                    ProgressBar progressBar = (ProgressBar) viewProgressBar;
                    if (View.INVISIBLE == progressBar.getVisibility()) {
                        Logger.d("progressBar INVISIBLE");
                        progressBar.setMax(iTotalDuration);
                        progressBar.setSecondaryProgress(0);
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(iCurrentPosition);
                }
                
                View viewTextViewTotalDuration = view.findViewById(R.id.tvPlnTrackTotalDuration);
                if (null != viewTextViewTotalDuration) {
                    TextView textViewTotalDuration = (TextView) viewTextViewTotalDuration;
                    if (View.INVISIBLE == textViewTotalDuration.getVisibility()) {
                        Logger.d("textViewTotalDuration INVISIBLE");
                        textViewTotalDuration.setText(sTotalDurationFormatted);
                        textViewTotalDuration.setVisibility(View.VISIBLE);
                    }
                }
                
                View viewTextViewCurrentPosition = view.findViewById(R.id.tvPlnTrackCurrentPosition);
                if (null != viewTextViewCurrentPosition) {
                    TextView textViewCurrentPosition = (TextView) viewTextViewCurrentPosition;
                    if (View.INVISIBLE == textViewCurrentPosition.getVisibility()) {
                        Logger.d("textViewCurrentPosition INVISIBLE");
                        textViewCurrentPosition.setVisibility(View.VISIBLE);
                    }
                    textViewCurrentPosition.setText(sCurrentPositionFormatted);
                }
            }
        }
	}
	
	private String formatSeconds(String sSecondsInput) {
	    String sReturn = ""; //"??:??";
	    try {
	        int iSeconds = Integer.parseInt(sSecondsInput);
            int iMinute = iSeconds / 60;
            int iSecond = iSeconds % 60;
            if (0 <= iMinute && 0 <= iSecond) {
                sReturn = "";
                sReturn += (10 > iMinute) ? ("0" + iMinute) : Integer.toString(iMinute);
                sReturn += ":";
                sReturn += (10 > iSecond) ? ("0" + iSecond) : Integer.toString(iSecond);
            }
	    } catch (Exception e) {
	        Logger.e("Can not parse [" + sSecondsInput + "] to integer");
	    }
	    //Logger.d("sReturn [" + sReturn + "]");
	    return sReturn;
	}
	
	private void setAlbumArt(){
		if( null != thdAlbumArt ){
			thdAlbumArt = null;
		}
		thdAlbumArt = new Thread(){
			public void run()
	        { 
				if(null == application.playData){
					return;
				}
	        	if( 0 < application.playData.getPlayCount()){
	        		
	        		for(int i = ( application.playData.getPlayCount() - 1); i >= 0; i--){
	        			if( null == application.playData.getAlbumArtBmp(i)){
	        				Bitmap bm = getAlbumArt(application.playData.getAlbumArt(i));
	        				Message msg = new Message();
		        			if(null != bm){
		        				application.playData.setAlbumArtBmp(i, bm);
		        				msg.arg1 = 10001;	
		        			}else{
		        				msg.arg1 = 10002;
		        			}
		        			msg.arg2 = i;
		        			Log.v(TAG,"playHandler.sendMessage(msg) #######################");
		        			playHandler.sendMessage(msg);
		        			runPreviewShow();
						}
	        		}
	        	}
	        }
		};
		thdAlbumArt.start();
	}
	
	public void setAlbumArtBmp(int nOption, int nPosition){
		ImageView ivTmp = (ImageView)playnowGallery.findViewById(nPosition+100);
		if(null == ivTmp){
			Log.e(TAG,"get album art image view fail, ImageView is invalid");
			return;
		}
		if( 0 == nOption){	
			if( null != ivTmp ){
				Bitmap bmpTmp = application.playData.getAlbumArtBmp(nPosition);
				if( null != bmpTmp ){
					ivTmp.setImageBitmap(bmpTmp);
				//	playnowGalley.requestFocus();
					int nIndex = playnowGallery.getSelectedItemPosition();
					if(nPosition == nIndex){
						playnowGallery.setSelection(nIndex);
					}
				//	playnowGalley.invalidate();
				}
			}
		}
		Message msg = new Message();
		msg.arg1 = 10003;
		playHandler.sendMessage(msg);
	}
	
	private Handler playHandler = new Handler() {
        @Override 
        public void handleMessage(Message msg) {
        	switch(msg.arg1){
        	case 10001: // album art download ok
        		setAlbumArtBmp(0, msg.arg2);
        		break;
        	case 10002: // album art download fail
        		setAlbumArtBmp(1, msg.arg2);
        		break;
        	case 10003: // show preview album art
        		int position;
    			position = playnowGallery.getSelectedItemPosition();
    			setPreviewShow(position);
        		break;
        	}
        }
	};
	
	private Bitmap getAlbumArt(String szURL){
		
		if(null == szURL || szURL.length() <= 0){
			return null;
		}
		
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
