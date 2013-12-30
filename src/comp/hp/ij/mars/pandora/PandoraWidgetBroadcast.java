package comp.hp.ij.mars.pandora;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;

public class PandoraWidgetBroadcast {
	
	private final String TAG = "PandoraWidgetBroadcase";
	public final static int	WIDGET_DEFAULT	= 0x00A1;
	public final static int	PLAYNOW_UPDATE	= 0x00A2;
	public final static int WIDGET_UPDATE_ALBUM_ART = 0x00A3;
	
	public PandoraWidgetBroadcast(){
		
	}

	public void broadcastMsg(Context context, int nId, 
			String szStationName, 
			String szSongName, 
			String szArtistName, 
			String szAlbumName, 
			String szAlbumArtUrl ){
		
		Intent i = new Intent(AppWidgetManager.EXTRA_CUSTOM_EXTRAS);
		i.putExtra("id", nId);
		i.putExtra("StationName", szStationName);
		i.putExtra("SongName", szSongName);
		i.putExtra("ArtistName", szArtistName);
		i.putExtra("AlbumName", szAlbumName);
		i.putExtra("AlbumArtUrl", szAlbumArtUrl);
		
		context.sendBroadcast(i);
	}

}
