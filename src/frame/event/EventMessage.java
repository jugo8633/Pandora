package frame.event;

public class EventMessage {
	
	public static final String TRUE                 = "1";
	public static final String FALSE                = "0";
	
	public static final int WND_MSG					= 2000;
	public static final int WND_SHOW				= WND_MSG + 1;
	public static final int WND_STOP				= WND_MSG + 2;
	public static final int WND_EXCP				= WND_MSG + 3; // window exception
	public static final int	WND_ITEM_CLICK			= WND_MSG + 4; 
	public static final int	WND_SHOW_USER_WND		= WND_MSG + 5; 
	public static final int	WND_FINISH				= WND_MSG + 6; 
	public static final int	WND_SHOW_LOGIN			= WND_MSG + 7;
	public static final int	WND_SHOW_STATION		= WND_MSG + 8;
	public static final int	WND_CREATE_STATION		= WND_MSG + 9;
	public static final int	WND_BTN_OK				= WND_MSG + 10;
	public static final int	WND_BTN_CANCEL			= WND_MSG + 11;
	public static final int WND_SHOW_PLAY_MENU		= WND_MSG + 12;
	public static final int WND_CLOSE_PLAY_MENU		= WND_MSG + 13;
	public static final int WND_WHY_PLAY			= WND_MSG + 14;
	public static final int	WND_CLOSE_INPUT_WND		= WND_MSG + 15;
	public static final int WND_PLAY_NEXT_SNOG		= WND_MSG + 17;
	public static final int WND_THUMB_UP            = WND_MSG + 18;
	public static final int WND_THUMB_DOWN          = WND_MSG + 19;
	public static final int WND_BOOK_MARK_ARTIST    = WND_MSG + 20;
	public static final int WND_BOOK_MARK_SONG      = WND_MSG + 21;
	public static final int WND_SLEEP_SONG          = WND_MSG + 22;
	public static final int WND_PAUSE_SONG          = WND_MSG + 23;
	public static final int WND_RESUME_SONG         = WND_MSG + 24;
	public static final int WND_LOGOUT              = WND_MSG + 25;
	public static final int WND_SHOW_ACCOUNT_WND    = WND_MSG + 26;
	public static final int WND_PROGRESS            = WND_MSG + 27;
	public static final int WND_REMOVE_STATION      = WND_MSG + 28;
	public static final int WND_NEW_USER            = WND_MSG + 29;
	public static final int WND_TEXT_CHANGE         = WND_MSG + 30;
	public static final int WND_SWITCH_USER         = WND_MSG + 31;
	public static final int WND_REMOVE_USER         = WND_MSG + 32;
	
	
	public static final int EVENT_HANDLE					= 3000;
	public static final int EVENT_HANDLE_CREATED 			= EVENT_HANDLE + 1;
	public static final int EVENT_HANDLE_ON_CLICK			= EVENT_HANDLE + 2;
	public static final int EVENT_HANDLE_ON_PRESS			= EVENT_HANDLE + 3;
	public static final int EVENT_HANDLE_ON_TOUCH			= EVENT_HANDLE + 4;
	public static final int EVENT_HANDLE_ON_TOUCH_DOWN		= EVENT_HANDLE + 5;
	public static final int EVENT_HANDLE_ON_TOUCH_UP		= EVENT_HANDLE + 6;
	public static final int EVENT_HANDLE_ON_TOUCH_MOVE		= EVENT_HANDLE + 7;
	public static final int EVENT_HANDLE_ON_TOUCH_CANCEL	= EVENT_HANDLE + 8;
	public static final int EVENT_HANDLE_ON_TOUCH_OUTSIDE	= EVENT_HANDLE + 9;
	
	
	/**
	 * @author jugo
	 * @descript define pandora service event
	 */
	public static final int	SERVICE_MSG			              = 4000;
	public static final int SERVICE_LOGIN_SUCCESS	          = SERVICE_MSG + 1;
	public static final int SERVICE_LOGIN_FAIL	              = SERVICE_MSG + 1 + 0xAA;
	public static final int SERVICE_STATION_GET_SUCCESS	      = SERVICE_MSG + 2;
	public static final int SERVICE_STATION_GET_FAIL	      = SERVICE_MSG + 2 + 0xAA;
	public static final int	SERVICE_UPDATE_PLAYLIST	          = SERVICE_MSG + 4;
	public static final int SERVICE_UPDATE_CURRENT_POSITION   = SERVICE_MSG + 6;
	public static final int SERVICE_EXPLAIN_TRACK_SUCCESS     = SERVICE_MSG + 7;
	public static final int SERVICE_EXPLAIN_TRACK_FAIL        = SERVICE_MSG + 7 + 0xAA;
	public static final int SERVICE_NOT_ASSOCIATED            = SERVICE_MSG + 8;
	public static final int SERVICE_CREATE_STATION_SUCCESS    = SERVICE_MSG + 9;
	public static final int SERVICE_CREATE_STATION_FAIL       = SERVICE_MSG + 9 + 0xAA;
	public static final int SERVICE_READY                     = SERVICE_MSG + 10;
	public static final int SERVICE_REMOVE_STATION_SUCCESS    = SERVICE_MSG + 11;
	public static final int SERVICE_REMOVE_STATION_FAIL       = SERVICE_MSG + 11 + 0xAA;
	public static final int SERVICE_LOGOUT_SUCCESS            = SERVICE_MSG + 12;
	public static final int SERVICE_LOGOUT_FAIL               = SERVICE_MSG + 12 + 0xAA;
	public static final int SERVICE_NOWPLAY_TOKEN             = SERVICE_MSG + 14;
	public static final int SERVICE_BOOKMARK_SUCCESS          = SERVICE_MSG + 16;
	public static final int SERVICE_BOOKMARK_FAIL             = SERVICE_MSG + 16 + 0xAA;
	public static final int SERVICE_NEGATIVE_FEEDBACK_SUCCESS = SERVICE_MSG + 17;
	public static final int SERVICE_NEGATIVE_FEEDBACK_FAIL    = SERVICE_MSG + 17 + 0xAA;
	public static final int SERVICE_POSITIVE_FEEDBACK_SUCCESS = SERVICE_MSG + 18;
	public static final int SERVICE_POSITIVE_FEEDBACK_FAIL    = SERVICE_MSG + 18 + 0xAA;
	public static final int SERVICE_SLEEP_SONG_SUCCESS        = SERVICE_MSG + 19;
	public static final int SERVICE_SLEEP_SONG_FAIL           = SERVICE_MSG + 19 + 0xAA;
	public static final int SERVICE_SEARCH_RESULT_SUCCESS     = SERVICE_MSG + 20;
	public static final int SERVICE_SEARCH_RESULT_FAIL        = SERVICE_MSG + 20 + 0xAA;
	public static final int SERVICE_ACTIVATION_CODE_SUCCESS   = SERVICE_MSG + 21;
	public static final int SERVICE_ACTIVATION_CODE_FAIL      = SERVICE_MSG + 21 + 0xAA;
	public static final int SERVICE_SKIP_SONG_FAIL            = SERVICE_MSG + 22 + 0xAA;
	public static final int SERVICE_ADS_OPTION_FAIL           = SERVICE_MSG + 23 + 0xAA;
	public static final int SERVICE_AUTO_COMPLETE_SUCCESS     = SERVICE_MSG + 24;
	public static final int SERVICE_AUTO_COMPLETE_FAIL        = SERVICE_MSG + 24 + 0xAA;
	public static final int SERVICE_FIRE_IDLE_ALARM           = SERVICE_MSG + 25;
	public static final int SERVICE_ERROR_EVENT               = SERVICE_MSG + 26 + 0xAA;
	public static final int SERVICE_READ_ONLY                 = SERVICE_MSG + 27;
	public static final int SERVICE_REACH_SKIP_LIMITATION     = SERVICE_MSG + 29;
	public static final int SHARED_DATA_MSG = SERVICE_MSG + 200;
	
	
	/**
	 * @author jugo
	 * @descript define view run
	 */
	public static final int	RUN_WND					= 1000;
	public static final int	RUN_LOADWND				= RUN_WND + 1;
	public static final int	RUN_INFOWND				= RUN_WND + 2;
	public static final int	RUN_LOGINWND			= RUN_WND + 3;
	public static final int	RUN_ACCOUNTWND			= RUN_WND + 4;
	public static final int	RUN_STATIONWND			= RUN_WND + 5;
	public static final int	RUN_USERWND				= RUN_WND + 7;
	public static final int	RUN_PLAYINFOWND			= RUN_WND + 8;
	public static final int	RUN_PLAYWND				= RUN_WND + 9;
	public static final int RUN_SEARCHWND           = RUN_WND + 10;
	public static final int RUN_SEARCH_INPUTWND     = RUN_WND + 11;
	public static final int RUN_STATION_REMOVE_CHK_WND = RUN_WND + 12;
	public static final int	RUN_MAX					= RUN_WND + 13;
	
	public static final int SHOW_PROGRESS_DLG       = RUN_WND + 103;
	public static final int SHOW_ALARM_DLG          = RUN_WND + 104;
	
	/**
	 * @author jugo
	 * @descript define key code
	 */
	public static final int	KEY_BACK		= 4;
	
	
	public EventMessage(){
		
	}
}
