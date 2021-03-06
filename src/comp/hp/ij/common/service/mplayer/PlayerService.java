package comp.hp.ij.common.service.mplayer;

import java.io.IOException;

import comp.hp.ij.common.service.mplayer.IMediaPlaybackService;
import comp.hp.ij.common.service.mplayer.IMediaPlaybackServiceCallback;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.media.AudioManager;
import android.media.MediaPlayer;


public class PlayerService extends Service {

    final RemoteCallbackList<IMediaPlaybackServiceCallback> mCallbacks
    = new RemoteCallbackList<IMediaPlaybackServiceCallback>();
	private final String TAG = "ccp";
	private MultiPlayer mMediaPlayer = null;
	private float mCurrentVolume = 0.5f;
	private int mCurrentBuffer = 0;
	private int mBufferTimeOut = 10; //second
	private boolean mKeepCheck = false;
	//private AudioManager audioma;
	//private int volume = 5;

	@Override
	public void onCreate() {		
		super.onCreate();
		mMediaPlayer = new MultiPlayer();
		mMediaPlayer.setHandler(mMediaplayerHandler);
		//audioma = (AudioManager) getSystemService(Context.AUDIO_SERVICE);	
		//volume = audioma.getStreamVolume(AudioManager.STREAM_MUSIC);
		Log.i(TAG, "Player Service Created");
	}

	@Override
	public void onRebind(Intent intent) {
		Log.i(TAG, "ReBind Player Service");
		super.onRebind(intent);
	}

	@Override
	public void onStart(Intent intent, int startId){
		Log.i(TAG, "Player Service Started");
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.i(TAG, "Bind Player Service ");
		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.i(TAG, "Unbind Player Service");
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "Player Service Destroy");

		if (mMediaPlayer != null) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.stop();
			}
			mMediaPlayer.releasePlayer();
			mMediaPlayer = null;
		}
		super.onDestroy();
	}

	private void errorCallback(RemoteCallbackList<IMediaPlaybackServiceCallback> cb, int N, int message) {
         for (int i = 0; i < N; i++) {
 	        try {
 	            cb.getBroadcastItem(i).onErrorReport(message);
 	        } catch (RemoteException e) {
 	        	e.printStackTrace();
 	        }
 	    }
 	    cb.finishBroadcast();
	}

	private void statusCallback(RemoteCallbackList<IMediaPlaybackServiceCallback> cb, int mNun, int message) {
        for (int i = 0; i < mNun; i++) {
	        try {
	            cb.getBroadcastItem(i).onComplete(message);
	        } catch (RemoteException e) {
	        	e.printStackTrace();
	        }
	    }
	    cb.finishBroadcast();
	}

	/**
	 *
	 */
    private Handler mMediaplayerHandler = new Handler() {
        public void handleMessage(Message msg){
        	final int mNun = mCallbacks.beginBroadcast();
            switch (msg.what){
            	case PlayerState.PLAYER_SERVER_DIED:
            		errorCallback(mCallbacks, mNun, PlayerState.PLAYER_SERVER_DIED);
                break;
            	case PlayerState.PLAYER_FAIL_TO_PLAY:
            		errorCallback(mCallbacks, mNun, PlayerState.PLAYER_FAIL_TO_PLAY);
            	    break;
            	case PlayerState.PLAYER_UNKNOWN:
            		errorCallback(mCallbacks, mNun, PlayerState.PLAYER_UNKNOWN);
            		break;
                case PlayerState.PLAYER_IS_PLAYING:
                	errorCallback(mCallbacks, mNun, PlayerState.PLAYER_IS_PLAYING);
                    break;
                case PlayerState.PLAYER_NETWORK_SLOW:
                	errorCallback(mCallbacks, mNun, PlayerState.PLAYER_NETWORK_SLOW);
                	break;
                case PlayerState.PLAYER_IDLE:
                	statusCallback(mCallbacks, mNun, PlayerState.PLAYER_IDLE);
                    break;
                case PlayerState.PLAYER_PLAY_COMPLETED:
                	statusCallback(mCallbacks, mNun, PlayerState.PLAYER_PLAY_COMPLETED);
                    break;
                case PlayerState.PLAYER_PREPARED:
                	statusCallback(mCallbacks, mNun, PlayerState.PLAYER_PREPARED);
                	break;
                case PlayerState.PLAYER_PLAY:
                	statusCallback(mCallbacks, mNun, PlayerState.PLAYER_PLAY);
                    break;
                case PlayerState.PLAYER_PAUSE:
                	statusCallback(mCallbacks, mNun, PlayerState.PLAYER_PAUSE);
                	break;                
                default:
                    break;
            }
        }
    };

    /**
     * play control
     *
	 */

    public void openUri(String sourceUri) {
    	if (!isPlaying()) {
    		Log.d(TAG, "Play music" + " - " + sourceUri);
    			if (sourceUri != null) {
           		try {             		
           			mMediaplayerHandler.removeCallbacks(mBufferCheckRunnable);
             		mMediaPlayer.setDataSourceAsync(sourceUri);        
             		mKeepCheck = true;
             		mMediaplayerHandler.postDelayed(mBufferCheckRunnable, mBufferTimeOut*1000);
               	} catch (Exception ex) {
               		ex.printStackTrace();
               	}
           	}
    	} else {
    		mMediaplayerHandler.sendEmptyMessage(PlayerState.PLAYER_IS_PLAYING);
    	}
    }

    public void play() {
    	Log.d(TAG, "play()");
    	if (mMediaPlayer.isInitialized()) {
    		try {
    			Log.d(TAG, "play() is called");
    			if (!mKeepCheck) {
    				mKeepCheck = true;
    				mMediaplayerHandler.postDelayed(mBufferCheckRunnable, mBufferTimeOut*1000);	    				
    			}    			
    			mMediaPlayer.start();
    			mMediaPlayer.setVolume(mCurrentVolume);
    		} catch (Exception ex) {
    			ex.printStackTrace();
    		}
    		stopForeground(true);
    	}
    }

	public void stop() {
		Log.d(TAG, "stop()");
        if (mMediaPlayer.isInitialized()) {
        	try {
        		Log.d(TAG, "stop() is called");
        		mKeepCheck = false;
        		mMediaplayerHandler.removeCallbacks(mBufferCheckRunnable);        		
        		mCurrentBuffer = 0;
				mMediaPlayer.stop();				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			stopForeground(false);
        }
	}

	public void pause() {
		Log.d(TAG, "pause()");
		if (isPlaying()) {
			try {
				Log.d(TAG, "pause() is called");
				mKeepCheck = false;				
				mMediaplayerHandler.removeCallbacks(mBufferCheckRunnable);
				mMediaPlayer.pause();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			stopForeground(false);
		}
	}

	public int duration() {
		if (mMediaPlayer.isInitialized()) {
			try {
				return mMediaPlayer.duration();
			} catch (Exception ex) {
				ex.printStackTrace();
				return -1;
			}
		} else {
			return -1;
		}
	}

    public boolean isPlaying() {
        if (mMediaPlayer.isInitialized()) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }


    public int position() {
        if (mMediaPlayer.isInitialized()) {
            return mMediaPlayer.position();
        }
        return -1;
    }


    public void setVolume(float vol) {
    	mCurrentVolume = vol;
        mMediaPlayer.setVolume(vol);
    	/*
    	if (vol == 0.0f) {
    		 volume++;
    	     audioma.adjustVolume(AudioManager.STREAM_MUSIC, volume);
    	} else {
    		volume--;
    	    audioma.adjustVolume(AudioManager.ADJUST_RAISE,volume);
    	}
    	*/
    	//audioma.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,0);
    	//volume = audioma.getStreamVolume(AudioManager.STREAM_MUSIC);
    }
    
    public float getVolume() {
        return mCurrentVolume;
    }
    
    public int getCurrentState() {    	
    	return mMediaPlayer.state();
    }
    
    private void checkMediaBuffer() {
    	int newbuffer = mMediaPlayer.buffer();
    	Log.d(TAG, "Curr Buffer = " + String.valueOf(newbuffer) + "%;  Prev Buffer ="  + String.valueOf(mCurrentBuffer) + "%");
    	if (newbuffer != 100) {
    		if (newbuffer <= mCurrentBuffer) {
    			mMediaplayerHandler.sendEmptyMessage(PlayerState.PLAYER_NETWORK_SLOW);
    			Log.w(TAG, "----------Network Slow----------Network Slow----------Network Slow----------Network Slow----------");
    			if (mKeepCheck) {
    				mMediaplayerHandler.postDelayed(mBufferCheckRunnable, mBufferTimeOut*1000);
    			}
    		} else {
    			mCurrentBuffer = newbuffer;
    			if (mKeepCheck) {
    				mMediaplayerHandler.postDelayed(mBufferCheckRunnable, mBufferTimeOut*1000);	
    			}
    		}    			    		
    	} else {
    		Log.d(TAG, "Buffer Completed");
    		mCurrentBuffer = 0;
    	}
    }
    
    private Runnable mBufferCheckRunnable = new Runnable(){
    	public void run(){    
    		checkMediaBuffer();
    	}
    };
    
	private String toLog(int code) {
		if (code == PlayerState.PLAYER_END) {
			return "PLAYER_END";
		} else if (code == PlayerState.PLAYER_IDLE) {
			return "PLAYER_IDLE";
		} else if (code == PlayerState.PLAYER_INITIALED) {
			return "PLAYER_INITIALED";
		} else if (code == PlayerState.PLAYER_PREPARED) {
			return "PLAYER_PREPARED";
		} else if (code == PlayerState.PLAYER_PLAY) {
			return "PLAYER_PLAY";
		} else if (code == PlayerState.PLAYER_PAUSE) {
			return "PLAYER_PAUSE";
		} else if (code == PlayerState.PLAYER_STOP) {
			return "PLAYER_STOP";
		} else if (code == PlayerState.PLAYER_PLAY_COMPLETED) {
			return "PLAYER_PLAY_COMPLETED";
		} else {
			return "N0 Match";
		}		
	}
    
    
    /**
     * Provides a unified interface for dealing with midi files and
     * other media files.
     */
    private class MultiPlayer {
        private MediaPlayer mMediaPlayer = new MediaPlayer();
        private Handler mHandler         = null;
        private boolean mIsInitialized   = false;
        private boolean mIsPreparing     = false;
        private int mCurrentState        = PlayerState.PLAYER_IDLE;
        private int mMediaBuffer         = 0;

        public MultiPlayer() {
//            mMediaPlayer.setWakeMode(MediaPlaybackService.this, PowerManager.PARTIAL_WAKE_LOCK);
        }

        public void setDataSourceAsync(String path) {
            try {
            	Log.d(TAG, "before setDataSourceAsync() - " + toLog(mCurrentState));
                mMediaPlayer.reset();
            	mMediaPlayer.setDataSource(path);
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setOnPreparedListener(preparedlistener);
                mMediaPlayer.setOnBufferingUpdateListener(bufferlistener);
                mMediaPlayer.setOnInfoListener(infoListener);                
                mIsPreparing = true;
                mCurrentState = PlayerState.PLAYER_INITIALED;
                mMediaBuffer  = 0;
                mMediaPlayer.prepareAsync();
            } catch (IOException ex) {
            	ex.printStackTrace();
            	mCurrentState = PlayerState.PLAYER_IDLE;
                mIsInitialized = false;
                mIsPreparing   = false;
                return;
            } catch (IllegalArgumentException ex) {
            	ex.printStackTrace();
            	mCurrentState = PlayerState.PLAYER_IDLE;
                mIsInitialized = false;
                mIsPreparing   = false;
                return;
            } catch (IllegalStateException ex) {
            	ex.printStackTrace();
            	mCurrentState = PlayerState.PLAYER_IDLE;
            	mIsInitialized = false;
            	mIsPreparing   = false;
                return;
            }
            mMediaPlayer.setOnCompletionListener(listener);
            mMediaPlayer.setOnErrorListener(errorListener);  
            Log.d(TAG, "after  setDataSourceAsync() - " + toLog(mCurrentState));
        }       

        public boolean isInitialized() {
            return mIsInitialized;
        }

        public void start() {
        	try {
        		Log.d(TAG, "before start() - " + toLog(mCurrentState));
        		//mMediaPlayer.setLooping(true);
        		mMediaPlayer.start();
                mCurrentState = PlayerState.PLAYER_PLAY;
                mHandler.sendEmptyMessage(PlayerState.PLAYER_PLAY);
                Log.d(TAG, "after  start() - " + toLog(mCurrentState));
        	} catch (IllegalStateException ex) {
        		ex.printStackTrace();
        		mHandler.sendEmptyMessage(PlayerState.PLAYER_ERROR);
            }
        }

        public void stop() {
        	Log.d(TAG, "before stop() - " + toLog(mCurrentState));
            mMediaPlayer.reset();
            mMediaBuffer = 0;
            mIsInitialized = false;
            mCurrentState = PlayerState.PLAYER_IDLE;            
            mHandler.sendEmptyMessage(PlayerState.PLAYER_IDLE);
            Log.d(TAG, "after  stop() - " + toLog(mCurrentState));
        }

        public void pause() {
        	try {
        		Log.d(TAG, "before pause() - " + toLog(mCurrentState));
        		mMediaPlayer.pause();
                mCurrentState = PlayerState.PLAYER_PAUSE;
                mHandler.sendEmptyMessage(PlayerState.PLAYER_PAUSE);
                Log.d(TAG, "after  pause() - " + toLog(mCurrentState));
        	} catch (IllegalStateException ex) {
        		ex.printStackTrace();
        		mHandler.sendEmptyMessage(PlayerState.PLAYER_ERROR);
            }
        }
        
        public boolean isPlaying() {
            return mMediaPlayer.isPlaying();
        }

        public void setHandler(Handler handler) {
            mHandler = handler;
        }

        MediaPlayer.OnCompletionListener listener = new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
            	Log.d(TAG, "before OnComplete is Called - " + toLog(mCurrentState)); 
            	
            	mIsInitialized = false;
            	if (mCurrentState == PlayerState.PLAYER_PLAY) {
            		Log.d(TAG, "Song Complete" );
            		mCurrentState = PlayerState.PLAYER_PLAY_COMPLETED;
                    mHandler.sendEmptyMessage(PlayerState.PLAYER_PLAY_COMPLETED);	
            	} else {
            		Log.d(TAG, "Fail to Play ");
            		
            		if (mCurrentState == PlayerState.PLAYER_INITIALED) {
            			mMediaPlayer.reset();
            			mCurrentState = PlayerState.PLAYER_IDLE;
                		mHandler.sendEmptyMessage(PlayerState.PLAYER_FAIL_TO_PLAY);
                		mIsPreparing = false;	
            		}            		
            	}
            	Log.d(TAG, "after  OnComplete is Called - " + toLog(mCurrentState));
            }
        };
        
        MediaPlayer.OnInfoListener infoListener = new MediaPlayer.OnInfoListener() {
			public boolean onInfo(MediaPlayer mp, int what, int extra) {				
				Log.d(TAG,"InfoListener" + String.valueOf(what) + "-" + String.valueOf(extra));
				return false;
			}        	
        };
        
        MediaPlayer.OnPreparedListener preparedlistener = new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
            	Log.d(TAG, "before OnPreparedListener is Called - " + toLog(mCurrentState));
                mIsPreparing = false;
                mIsInitialized = true;
                mCurrentState = PlayerState.PLAYER_PREPARED;
                mHandler.sendEmptyMessage(PlayerState.PLAYER_PREPARED);
                Log.d(TAG, "after  OnPreparedListener is Called - " + toLog(mCurrentState));
            }
        };

        MediaPlayer.OnErrorListener errorListener = new MediaPlayer.OnErrorListener() {
            public boolean onError(MediaPlayer mp, int what, int extra) {
            	Log.d(TAG, "before OnError is Called - " + toLog(mCurrentState));
            	mIsInitialized = false;            	
            	Log.e(TAG, "OnErrorListener: what = " + String.valueOf(what) + "; extra =  " + String.valueOf(extra));
                switch (what) {
                case MediaPlayer.MEDIA_ERROR_UNKNOWN:                	
                	mMediaPlayer.reset();
                	if (mIsPreparing) {               
                		Log.d(TAG, "MEDIA_FAIL_TO_PLAY");
                		mHandler.sendEmptyMessage(PlayerState.PLAYER_FAIL_TO_PLAY);
                		mIsPreparing = false;
                	} else {
                		Log.d(TAG, "MEDIA_ERROR_UNKNOWN");
                		mHandler.sendEmptyMessage(PlayerState.PLAYER_UNKNOWN);
                	}
                	return true;
                case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                	Log.d(TAG, "MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK");
                	mHandler.sendEmptyMessage(PlayerState.PLAYER_ERROR);
                	return true;
                case MediaPlayer.MEDIA_ERROR_SERVER_DIED:                   
                    mMediaPlayer.release();
                    mMediaPlayer = new MediaPlayer();
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(PlayerState.PLAYER_SERVER_DIED), 2000);
                    return true;
                default:
                    break;
                }
                Log.d(TAG, "after  OnError is Called - " + toLog(mCurrentState));
                return false;
           }
        };

        MediaPlayer.OnBufferingUpdateListener bufferlistener = new MediaPlayer.OnBufferingUpdateListener() {
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
            	Log.v(TAG, "onBufferingUpdate" + String.valueOf(percent));
            	mMediaBuffer = percent;            
            }
        };

        public int duration() {
            return mMediaPlayer.getDuration();
        }

        public int position() {
            return mMediaPlayer.getCurrentPosition();
        }

        public void setVolume(float vol) {        
            mMediaPlayer.setVolume(vol, vol);
        }
        
        public int state() {
        	return mCurrentState;
        }
        
        public int buffer() {
        	return mMediaBuffer;
        }

        public void releasePlayer(){
        	mMediaPlayer.release();
        }
    }

	//** player binber */
	 private final IMediaPlaybackService.Stub mBinder = new IMediaPlaybackService.Stub()
	    {
			public boolean isPlaying() throws RemoteException {
				return PlayerService.this.isPlaying();
			}

			public void openUri(String fileUri) throws RemoteException {
				PlayerService.this.openUri(fileUri);
			}

			public void playMusic() throws RemoteException {
				PlayerService.this.play();
			}

			public void pauseMusic() throws RemoteException {
				PlayerService.this.pause();
			}

			public void stopMusic() throws RemoteException {
				PlayerService.this.stop();
			}

			public int getPosition() throws RemoteException {			
				return PlayerService.this.position();
			}

			public int getDuration() throws RemoteException {			
				return PlayerService.this.duration();
			}
			
			public float getVolume() throws RemoteException {			
				return PlayerService.this.getVolume();
			}

			public void setVolume(float volume) throws RemoteException {
				PlayerService.this.setVolume(volume);
			}

			public int getCurrentState() throws RemoteException {
				return PlayerService.this.getCurrentState();
			}

			public void setNetWorkTimeOut(int sec) throws RemoteException {
				if (sec > 0) {
					mBufferTimeOut = sec;
				}				
			}
			
			public void registerCallback(IMediaPlaybackServiceCallback cb)
					throws RemoteException {
				if (cb != null) {
					mCallbacks.register(cb);
				}
			}
			
			public void unregisterCallback(IMediaPlaybackServiceCallback cb)
					throws RemoteException {
				if (cb != null) {
					mCallbacks.unregister(cb);
				}
			}
	    };
}