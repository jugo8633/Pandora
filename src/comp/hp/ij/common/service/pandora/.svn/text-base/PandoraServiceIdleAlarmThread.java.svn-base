package comp.hp.ij.common.service.pandora;

import android.os.Handler;
import android.os.Message;
import comp.hp.ij.common.service.pandora.util.Logger;
import comp.hp.ij.common.service.v2.base.ConstantV2;

public class PandoraServiceIdleAlarmThread implements Runnable {
    
    private boolean shouldStopTimer = false;
    private boolean hasSendMessageToActivity = false;
    
    private long lIdleAlarmTimer = 0;
    
    private Handler serviceMessageHandler;
    
    public PandoraServiceIdleAlarmThread(Handler handlerInput) {
        serviceMessageHandler = handlerInput;
        
        lIdleAlarmTimer = System.currentTimeMillis();
    }

    public void run() {
        while (!shouldStopTimer) {
            try {
                long lIdleMillis = System.currentTimeMillis() - lIdleAlarmTimer;
                //Logger.d("lIdleMillis: [" + lIdleMillis + "]");
                if ((lIdleMillis > 8 * 60 * 60 * 1000) && !hasSendMessageToActivity) {
                    hasSendMessageToActivity = true;
                    
                    Message msg = new Message();
                    msg.what = ConstantV2.PANDORA_SERVICE_INTERNAL_CALL;        
                    msg.arg1 = PandoraServiceConstants.PANDORA_SERVICE_FIRE_IDLE_ALARM;
                    
                    serviceMessageHandler.sendMessage(msg);
                }
                Thread.sleep(5 * 1000);
            } catch (Exception e) {
                Logger.e(e);
            }
        }
    }
    
    public void resetIdleAlarmTimer() {
        hasSendMessageToActivity = false;
        
        lIdleAlarmTimer = System.currentTimeMillis();
    }

}
