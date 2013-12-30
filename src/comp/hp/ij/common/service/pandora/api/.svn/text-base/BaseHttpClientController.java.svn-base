package comp.hp.ij.common.service.pandora.api;

import java.io.InputStream;

import javax.xml.parsers.FactoryConfigurationError;

import org.apache.http.HttpResponse;

import comp.hp.ij.common.ccphttpclient.CCPHttpClient;
import comp.hp.ij.common.ccphttpclient.CCPHttpClientCallback;
import comp.hp.ij.common.ccphttpclient.CCPHttpClient.PostContentBuilder;
import comp.hp.ij.common.service.pandora.util.Logger;

/**
 * This code was developed by HON HAI PRECISION IND. CO., LTD., CMMSG/DWHD/CCP/SWI, Code for the Mars project.
 * 
 * @author Chance Wang
 */
public class BaseHttpClientController {

    protected HttpResponse mRes;
    protected static String proxy_host = "";
    protected static int proxy_port = 0;
    protected static String proxy_name = "";
    protected static String proxy_pwd = "";

    private CCPHttpClient mHttpClient;
    protected boolean mLock;
    protected Thread mThared;
    protected long mContentLen = 0;
    protected int mHttpClientErrorCode = PandoraAPIConstants.DOWNLOAD_IMAGE_SUCCESS;

    /**
     * disconnect.
     */
    public void disconnect() {
        if (mHttpClient != null) {
            Logger.d("mHttpClient disconnect");
            mHttpClient.disconnect();
        }
    }

    /**
     * 
     * @return CCPHttpClient
     */
    public CCPHttpClient getCCPClient() {
        return mHttpClient;
    }

    /**
     * 
     * @return CCPHttpClient
     */
    public CCPHttpClient getClientInstance() {
        mHttpClient = new CCPHttpClient();
        mHttpClient.initRequest((CCPHttpClientCallback) this, 1, PandoraAPIConstants.NETWORK_TIMEOUT);

        return mHttpClient;
    }

    /**
     * Constructor.
     */
    public BaseHttpClientController() {
        mHttpClient = getClientInstance();
    }

    /**
     * 
     * @param url url
     * @param port port
     * @param data data
     * 
     * @return Object
     */
    public Object doHttpPostFile(String url, int port, byte[] data) {

        InputStream is = null;

        try {

            PostContentBuilder builder = mHttpClient.new PostContentBuilder();

            builder.setPostContent(data);

            mHttpClient.startPostMethod(url, port, builder);

            waitHttpClient();

            if ((mHttpClientErrorCode == PandoraAPIConstants.DOWNLOAD_IMAGE_SUCCESS) && mRes != null) {

                mContentLen = mRes.getEntity().getContentLength();
                is = mRes.getEntity().getContent();

            } else {

                mHttpClientErrorCode = PandoraAPIConstants.DOWNLOAD_IMAGE_FAILED;
                return null;
            }

        } catch (FactoryConfigurationError ex) {
            ex.printStackTrace();
            mLock = false;

        } catch (Exception ex) {
            ex.printStackTrace();
            mLock = false;

        }

        return is;
    }

    /**
     * 
     * @param url url
     * @param params params
     * @param port port
     * 
     * @return Object
     */
    public synchronized Object doHttpPost(String url, String params, int port) {

        Logger.d();

        InputStream is = null;

        try {

            PostContentBuilder builder = mHttpClient.new PostContentBuilder();
            builder.setPostContent(params);
            mHttpClient.setHttpHeader("Content-Type", "application/x-www-form-urlencoded");

            mHttpClient.startPostMethod(url, port, builder);

            waitHttpClient();

            if ((mHttpClientErrorCode == PandoraAPIConstants.DOWNLOAD_IMAGE_FAILED) && mRes != null) {

                mContentLen = mRes.getEntity().getContentLength();
                is = mRes.getEntity().getContent();

            } else {

                mHttpClientErrorCode = PandoraAPIConstants.DOWNLOAD_IMAGE_FAILED;
                return null;
            }

        } catch (FactoryConfigurationError ex) {
            ex.printStackTrace();
            mHttpClientErrorCode = PandoraAPIConstants.DOWNLOAD_IMAGE_FAILED;
            mLock = false;

        } catch (Exception ex) {
            mHttpClientErrorCode = PandoraAPIConstants.DOWNLOAD_IMAGE_FAILED;
            ex.printStackTrace();
            mLock = false;

        }

        return is;

    }

    /**
     * 
     * @param url url
     * @param port port
     * 
     * @return Object
     */
    public synchronized Object doHttpGet(String url, int port) {

        Logger.d();

        InputStream is = null;

        try {

            mHttpClient.startGetMethod(url, port);
            Logger.d("StartGetMethod Execute");
            Logger.d("URL = [" + url + "], port = [" + port + "]");
            waitHttpClient();
            Logger.d("StartGetMethod End");

            if ((mHttpClientErrorCode == PandoraAPIConstants.DOWNLOAD_IMAGE_SUCCESS) && mRes != null) {

                mContentLen = mRes.getEntity().getContentLength();
                is = mRes.getEntity().getContent();

            } else {
                mHttpClientErrorCode = PandoraAPIConstants.DOWNLOAD_IMAGE_FAILED;
                return null;
            }

        } catch (FactoryConfigurationError ex) {
            ex.printStackTrace();
            mHttpClientErrorCode = PandoraAPIConstants.DOWNLOAD_IMAGE_FAILED;
            mLock = false;

        } catch (Exception ex) {
            ex.printStackTrace();
            mHttpClientErrorCode = PandoraAPIConstants.DOWNLOAD_IMAGE_FAILED;
            mLock = false;

        }

        return is;

    }

    /**
     * 
     * @param header header
     * @param value value
     */
    public void setHttpHeader(String header, String value) {
        mHttpClient.setHttpHeader(header, value);
    }

    /**
     * 
     * @return get http client error code
     */
    public int getHttpClientErrorCode() {
        return mHttpClientErrorCode;
    }

    /**
     * disconnect http client.
     */
    public void disconnectHttpClient() {
        if (mHttpClient != null) {
            mHttpClient.disconnect();
        }
    }

    /**
     * wait http client.
     */
    private void waitHttpClient() {

        int iCount = 0;
        mThared = Thread.currentThread();
        mLock = true;
        while (mLock) {
            try {
                Thread.sleep(1000);
                Logger.d("wait http response ..................................");
            } catch (InterruptedException e) {
                Logger.d("BaseHttpClientController current thread id = " + mThared.getId());
                Logger.d("waitHttpClient:" + e.toString());
            }

            if (iCount++ > 120) {
                break;
            }
        }
    }

}
