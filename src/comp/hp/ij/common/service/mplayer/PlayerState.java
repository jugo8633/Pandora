package comp.hp.ij.common.service.mplayer;

public final class PlayerState {
	public static final int PLAYER_OK = 0;
	public static final int PLAYER_FAIL = -1;
	
	//** player state */
	public static final int PLAYER_ERROR          = -1;
	public static final int PLAYER_END            =  0;
	public static final int PLAYER_IDLE           =  1;
	public static final int PLAYER_INITIALED      =  2;
	public static final int PLAYER_PREPARED       =  3;
	public static final int PLAYER_PLAY           =  4;	
	public static final int PLAYER_PAUSE          =  5;
	public static final int PLAYER_STOP           =  6;
	public static final int PLAYER_PLAY_COMPLETED =  7;	
	
	//**player event code*/
	//public static final int EVENT_CODE         = 100;
	//public static final int PLAYER_DURATION    = EVENT_CODE + 1;
	//public static final int PLAYER_TRACK_ENDED = EVENT_CODE + 2;
	
	//** error event code */
	public static final int ERROR_CODE = -100;
	public static final int PLAYER_SERVER_DIED  = ERROR_CODE - 1;
	public static final int PLAYER_FAIL_TO_PLAY = ERROR_CODE - 2;
	public static final int PLAYER_UNKNOWN      = ERROR_CODE - 3;
	public static final int PLAYER_IS_PLAYING   = ERROR_CODE - 4;
	public static final int PLAYER_NETWORK_SLOW = ERROR_CODE - 5;
}