package comp.hp.ij.mars.pandora;


import java.util.List;

import comp.hp.ij.common.service.pandora.PandoraServiceConstants;
import comp.hp.ij.common.service.pandora.api.FileDownloader;
import comp.hp.ij.common.service.pandora.util.Logger;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

public class PandoraWidgetProvider extends AppWidgetProvider {

	private final  String TAG = "PandoraWidgetProvider";
	private static String m_szStationName = null;
	private static String m_szSongName = null;
	private static String m_szArtistName = null; 
	private static String m_szAlbumName = null;
	private String m_szAlbumArtUrl;
	private Thread thdTrack = null;
	private static Bitmap bmpAlbumArt = null;
	private Context mContext = null;
	
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {	
        for (int i = 0; i < appWidgetIds.length; i++) {   
            updateAppWidget(context, appWidgetManager, appWidgetIds[i], PandoraWidgetBroadcast.WIDGET_DEFAULT);
        }
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		Intent i = new Intent(PandoraServiceConstants.UPDATE_WIDGET);
		i.putExtra("widget", 0);
		context.sendBroadcast(i);
	}
	
	public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,int appWidgetId, int nMode) {
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pandora_widget_layout);
		Intent intApp = new Intent(context, PandoraApp.class);
		PendingIntent launchIntent = PendingIntent.getActivity(context, 0, intApp, 0);
		views.setOnClickPendingIntent(R.id.llWidgetLayout,launchIntent);
		switch(nMode){
		case PandoraWidgetBroadcast.WIDGET_DEFAULT:
			views.setViewVisibility(R.id.llWidgetPlayNowLayout, View.GONE);
			views.setViewVisibility(R.id.llWidgetDefaultLayout, View.VISIBLE);
			Log.i(TAG,"pandora widget show default view");
			break;
		case PandoraWidgetBroadcast.PLAYNOW_UPDATE:
			views.setViewVisibility(R.id.llWidgetDefaultLayout, View.GONE);
			views.setViewVisibility(R.id.llWidgetPlayNowLayout, View.VISIBLE);
			mContext = context;
			setPlayNowInfo(views);
			Log.i(TAG,"pandora widget show playnow view!");
			break;
		case PandoraWidgetBroadcast.WIDGET_UPDATE_ALBUM_ART:
			if(null != bmpAlbumArt){
    			views.setImageViewBitmap(R.id.ivWidgetPlayNowAlbumArt, bmpAlbumArt);
    			views.setTextViewText(R.id.tvWidgetPlayNowStationName, m_szStationName);
    			views.setTextViewText(R.id.tvWidgetPlayNowSongName, m_szSongName);
    			views.setTextViewText(R.id.tvWidgetPlayNowArtist, m_szArtistName);
    			views.setTextViewText(R.id.tvWidgetPlayNowAlbumName, m_szAlbumName);
    		}
			break;
		default:
			if(null != m_szStationName){
				views.setViewVisibility(R.id.llWidgetDefaultLayout, View.GONE);
				views.setViewVisibility(R.id.llWidgetPlayNowLayout, View.VISIBLE);
				mContext = context;
				setPlayNowInfo(views);
			}else{
				views.setViewVisibility(R.id.llWidgetPlayNowLayout, View.GONE);
				views.setViewVisibility(R.id.llWidgetDefaultLayout, View.VISIBLE);
				views.setOnClickPendingIntent(R.id.ivWidgetAlbumArt, launchIntent);
				Log.i(TAG,"pandora widget show default view...");
			}
			break;
		}
		
		appWidgetManager.updateAppWidget(appWidgetId, views);
	}
	

	@Override
	public void onReceive(Context context, Intent intent) {
		int[] listIds = null;
		String action = intent.getAction();
		Log.i(TAG,"onReceive get action: " + action);
		
		if(AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)){
		
		}else if(AppWidgetManager.EXTRA_CUSTOM_EXTRAS.equals(action)){
			Bundle bunde = intent.getExtras();
			Log.i(TAG,"receive id = " + bunde.getInt("id"));
				
			if(0 < bunde.getInt("id")){
				switch(bunde.getInt("id")){
				case PandoraWidgetBroadcast.WIDGET_DEFAULT:
					listIds = getWidgetIds(context,".PandoraWidgetProvider");
					if(listIds != null){
						for(int i=0; i < listIds.length; i++){
							updateAppWidget(context, AppWidgetManager.getInstance(context), listIds[i], PandoraWidgetBroadcast.WIDGET_DEFAULT);
						}
					}
					break;
				case PandoraWidgetBroadcast.PLAYNOW_UPDATE:
					getExtraData(bunde);
					listIds = getWidgetIds(context,".PandoraWidgetProvider");
					if(listIds != null){
						for(int i=0; i < listIds.length; i++){
							updateAppWidget(context, AppWidgetManager.getInstance(context), listIds[i], PandoraWidgetBroadcast.PLAYNOW_UPDATE);
						}
					}
					break;
				case PandoraWidgetBroadcast.WIDGET_UPDATE_ALBUM_ART:
					listIds = getWidgetIds(context,".PandoraWidgetProvider");
					if(listIds != null){
						for(int i=0; i < listIds.length; i++){
							updateAppWidget(context, AppWidgetManager.getInstance(context), listIds[i], PandoraWidgetBroadcast.WIDGET_UPDATE_ALBUM_ART);
						}
					}
					break;
				}
			}
		}
		super.onReceive(context, intent);
	}
	
	private void getExtraData(Bundle bunde){
		m_szStationName	= bunde.getString("StationName");
		m_szSongName	= bunde.getString("SongName");
		m_szArtistName	= bunde.getString("ArtistName");
		m_szAlbumName	= bunde.getString("AlbumName");
		m_szAlbumArtUrl	= bunde.getString("AlbumArtUrl");
	}
		
	private  void setPlayNowInfo(RemoteViews views){
		views.setTextViewText(R.id.tvWidgetPlayNowStationName, m_szStationName);
		views.setTextViewText(R.id.tvWidgetPlayNowSongName, m_szSongName);
		views.setTextViewText(R.id.tvWidgetPlayNowArtist, m_szArtistName);
		views.setTextViewText(R.id.tvWidgetPlayNowAlbumName, m_szAlbumName);
		bmpAlbumArt = null;
		runDownloadAlbumArt();
	}
	
	private int[] getWidgetIds(Context context, String szClassName){
		
		AppWidgetManager gm = AppWidgetManager.getInstance(context);
		List<AppWidgetProviderInfo> listg = gm.getInstalledProviders();
		for(int k=0; k < listg.size(); k++){
			AppWidgetProviderInfo providerInfo = listg.get(k);
			if(providerInfo.provider.getShortClassName().equals(".PandoraWidgetProvider")){
				return gm.getAppWidgetIds(providerInfo.provider);
			}
		}
		return null;
	}
	
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
	
	private void runDownloadAlbumArt(){
		if(null != thdTrack){
			thdTrack = null;
		}
		thdTrack = new Thread()
	    {
	      public void run()
	      { 
	      	try
	      	{ 
	      		bmpAlbumArt = getAlbumArt(m_szAlbumArtUrl);
	      		if(null != bmpAlbumArt){
	      			Intent i = new Intent(AppWidgetManager.EXTRA_CUSTOM_EXTRAS);
	      			i.putExtra("id", PandoraWidgetBroadcast.WIDGET_UPDATE_ALBUM_ART);
	      			mContext.sendBroadcast(i);
	      		}
	      	}
	      	catch (Exception e)
	      	{
	      		e.printStackTrace();
	      	}
	      }
	    };
	    thdTrack.start();
	}
	
	
}
