package comp.hp.ij.common.service.pandora;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;

/**
 * This code was developed by HON HAI PRECISION IND. CO., LTD., CMMSG/DWHD/CCP/SWI, Code for the Mars project.
 * 
 * @author Chance Wang
 */
public class PandoraWidgetBroadcast {

    public static final int WIDGET_DEFAULT = 0x00A1;
    public static final int PLAYNOW_UPDATE = 0x00A2;

    /**
     * Constructor.
     */
    public PandoraWidgetBroadcast() {
    }

    /**
     * 
     * @param context context
     * @param nId nId
     * @param szStationName szStationName
     * @param szSongName szSongName
     * @param szArtistName szArtistName
     * @param szAlbumName szAlbumName
     * @param szAlbumArtUrl szAlbumArtUrl
     */
    public void broadcastMsg(Context context, int nId, 
            String szStationName, 
            String szSongName, 
            String szArtistName, 
            String szAlbumName, 
            String szAlbumArtUrl) {

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
