package comp.hp.ij.mars.pandora;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import comp.hp.ij.common.service.pandora.util.Logger;

import frame.event.EventMessage;

public class PandoraLoginWnd extends PandoraWnd {

    private final int m_nMainLayoutResId = R.layout.pandora_login;
    private static boolean m_stbSavePassword = false;
    
    private String m_sSavedAccount = null;
    private String m_sSavedLastStationToken = null;

    public PandoraLoginWnd(Activity active, Handler handler, int nId) {
        super(active, handler, nId);
        super.setLayoutResId(m_nMainLayoutResId, HIDE_VIEW, HIDE_VIEW);
        super.setViewTouchEvent(R.id.tvLoginBtnOK);
        super.setViewClickEvent(R.id.ivLoginPwdChkBox);
    }

    @Override
    protected void onClick(int resId) {
        switch (resId) {
        case R.id.ivLoginPwdChkBox:
            setPasswordSave();
            break;
        }
    }

    @Override
    protected void onClose() {
    }

    @Override
    protected void onShow() {
        View view = super.getApp().findViewById(R.id.ivLoginPwdChkBox);
        if (null != view) {
            if (m_stbSavePassword) {
                ((ImageView) view).setImageResource(R.drawable.checkbox_focus);
            } else {
                ((ImageView) view).setImageResource(R.drawable.checkbox);
            }
        }
    }

    @Override
    protected void onTouchDown(int resId) {
        switch (resId) {
        case R.id.tvLoginBtnOK:
            ((TextView) super.getApp().findViewById(R.id.tvLoginBtnOK)).setBackgroundResource(R.drawable.ok_btn_focus);
            break;
        }
    }

    @Override
    protected void onTouchUp(int resId) {
        switch (resId) {
        case R.id.tvLoginBtnOK:
            String[] aszLogin = new String[4];
            aszLogin[0] = getLoginAccount();
            aszLogin[1] = getLoginPassword();
            
            Logger.d("aszLogin[0] [" + aszLogin[0]
                    + "], m_sSavedAccount [" + m_sSavedAccount
                    + "], m_sSavedLastStationToken [" + m_sSavedLastStationToken + "]");
            aszLogin[2] = m_sSavedLastStationToken;
            if (aszLogin[0] != null && !aszLogin[0].equals(m_sSavedAccount)) {
                aszLogin[2] = null;
            }
            
            if (m_stbSavePassword) {
                aszLogin[3] = EventMessage.TRUE;
            } else {
                aszLogin[3] = EventMessage.FALSE;
            }
            ((TextView) super.getApp().findViewById(R.id.tvLoginBtnOK)).setBackgroundResource(R.drawable.ok_btn);
            sendAppMsg(WND_MSG, WND_BTN_OK, aszLogin);
            break;
        }
    }

    public String getLoginAccount() {
        View view = super.getApp().findViewById(R.id.etLoginAccount);
        if (null == view) {
            return null;
        }
        return ((EditText) view).getText().toString();
    }

    public String getLoginPassword() {
        View view = super.getApp().findViewById(R.id.etLoginPassword);
        if (null == view) {
            return null;
        }
        return ((EditText) view).getText().toString();
    }

    private void setPasswordSave() {
        ImageView iv = (ImageView) super.getApp().findViewById(R.id.ivLoginPwdChkBox);
        if (iv == null) {
            return;
        }
        if (m_stbSavePassword) {
            m_stbSavePassword = false;
            iv.setImageResource(R.drawable.checkbox);
        } else {
            m_stbSavePassword = true;
            iv.setImageResource(R.drawable.checkbox_focus);
        }
    }

    public void initConf(boolean bIsSaveAccount, String szAccount, String szPassword) {
        m_stbSavePassword = bIsSaveAccount;
        
        EditText etTmp = (EditText) super.getApp().findViewById(R.id.etLoginAccount);
        if (null != etTmp && null != szAccount) {
            etTmp.setText(szAccount);
        }
        etTmp = (EditText) super.getApp().findViewById(R.id.etLoginPassword);
        if (null != etTmp && null != szPassword) {
            etTmp.setText(szPassword);
        }

        ImageView iv = (ImageView) super.getApp().findViewById(R.id.ivLoginPwdChkBox);
        if (null == iv) {
            return;
        }
        if (bIsSaveAccount) {
            iv.setImageResource(R.drawable.checkbox_focus);
        } else {
            iv.setImageResource(R.drawable.checkbox);
        }
    }
    
    public void setLastStationInfo(String szAccount, String szLastStationToken) {
        m_sSavedAccount = szAccount;
        m_sSavedLastStationToken = szLastStationToken;
    }

}
