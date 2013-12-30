package comp.hp.ij.common.service.mplayer;
import comp.hp.ij.common.service.mplayer.IMediaPlaybackServiceCallback;

interface  IMediaPlaybackService{
	
	/**
	*	true -> media player is playing music ; false-> media player is not playing.
	*/
	boolean isPlaying();
	
	/**
	*	give one file URI, it would make media player from IDLE to INITIALED.
	*	then media player would try to prepare , if prepare error, it would call onReportError.
	*	If prepare success , it would make mediap layer from INITIALED TO PREPARED.  
	*/
	void openUri(String fileUri);
	
	void setNetWorkTimeOut(int sec);
	
	/**
	*	It would trigger media player start play music and media player would
	*	from PREPARED to PLAY
	*/
	void playMusic();
	
	/**
	*	If media player is play music, it would trigger media player from PLAY to IDLE
	*/	
	void stopMusic();
	
	/**
	* 
	*/	
	void pauseMusic();
	
	/**
	*
	*/	
	int getPosition();
	
	/**
	*
	*/		
	void setVolume(float volume);
	
	/**
	*
	*/	
	float getVolume();
	
	int getDuration();
	
	/**
	*
	*/	
	int getCurrentState();
	
	/**
    * Register a previously registered callback interface.
    */
	void registerCallback(IMediaPlaybackServiceCallback cb);
    
    /**
     * Remove a previously registered callback interface.
     */
    void unregisterCallback(IMediaPlaybackServiceCallback cb);
}