package comp.hp.ij.common.service.pandora.api;

import java.io.InputStream;

import org.apache.http.HttpResponse;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import comp.hp.ij.common.ccphttpclient.CCPHttpClient;
import comp.hp.ij.common.ccphttpclient.CCPHttpClientCallback;
import comp.hp.ij.common.ccphttpclient.RequestInfo;

import comp.hp.ij.common.service.pandora.util.Logger;


/**
 * FileDownloader is the helper class that deals with the downloading file from the remote server.
 * 
 *
 */
public class FileDownloader extends BaseHttpClientController implements CCPHttpClientCallback{

	public static final int RESPONSE_BITMAP_TYPE = 1;
	public Bitmap bitmap = null;



	public void disconnect(){
		super.disconnectHttpClient();
	}
	
	public FileDownloader() {		
		
		
	}

	/**
	 * Given image url address and download the bitmap.
	 * @param address the string of url address
	 * @return an instance of Bitmap
	 */
	public Bitmap downloadImage(String address) {

		try {
			bitmap = null;
			InputStream is = (InputStream) doHttpGet(address, 80);
			if (getHttpClientErrorCode() != PandoraAPIConstants.DOWNLOAD_IMAGE_SUCCESS) {
				Logger.d("No image return");
				return null;
			}
			 

			
			bitmap = BitmapFactory.decodeStream(is);
			Logger.d( "Bitmap size = " + bitmap.getRowBytes());
			
			return bitmap;
		} catch (Exception ex) {
			
			Logger.d("downloadBitmapFile, mHttpClientErrorCode = "
					+ mHttpClientErrorCode);
			ex.printStackTrace();
			return null;

		}

	}
	
	public void onCompleted(CCPHttpClient arg0, RequestInfo arg1,
			HttpResponse arg2) {

		Logger.d();

		int statusCode = arg2.getStatusLine().getStatusCode();
	
		Logger
				.d(String.format("onCompleted http status code:%d",
						statusCode));

		if (statusCode == 200) {
			Logger.d( "Status Code = " + statusCode);
			mRes = arg2;
		} else {
			Logger.d("onCompleted: unExpect status code:" + statusCode);
			mRes = null;
		}

		mLock = false;
		if (mThared != null)
			mThared.interrupt();
	}

	public void onError(RequestInfo arg0, HttpResponse arg1, int arg2) {

		
		mHttpClientErrorCode = PandoraAPIConstants.DOWNLOAD_IMAGE_FAILED;
		Logger.d(" HttpClientCodeError = " + mHttpClientErrorCode);		
		mRes = arg1;
		mLock = false;
		if (mThared != null)
			mThared.interrupt();
	}

	public void onProgress(CCPHttpClient cbObj, RequestInfo requestinfo,
			long total_size, long read_size, long current_size) {
		// TODO Auto-generated method stub

	}

}
