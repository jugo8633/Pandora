package frame.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedData {

    private SharedPreferences preference = null;
    private Context mContext = null;

    public SharedData() {
    }

    public void initShareData(Context context) {
        mContext = context;
    }

    synchronized public void initPreferences(String szFileName) {
        if (null == szFileName) {
            return;
        }
        preference = mContext.getSharedPreferences(szFileName, 0);
    }

    synchronized public String getValue(String szKey) {
        if (null == preference || null == szKey) {
            return null;
        }
        String szValue = null;
        szValue = preference.getString(szKey, null);
        return szValue;
    }

    synchronized public void setValue(String szKey, String szValue) {
        if (null == szKey || null == preference) {
            return;
        }
        Editor editor = null;
        editor = preference.edit();
        if (null == editor) {
            return;
        }
        editor.putString(szKey, szValue);
        editor.commit();
    }

    synchronized public void close() {
        preference = null;
    }

}
