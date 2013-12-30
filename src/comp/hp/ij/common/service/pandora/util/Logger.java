package comp.hp.ij.common.service.pandora.util;

import android.util.Log;

public class Logger {
    
    private static final boolean isDebug = true;
    
    private static final String CLASS_NAME = "Logger";

    public static void d(String sLogMessage) {
        if (!isDebug) { return; }
        
        boolean isFindCaller = false;
        
        String sProcessID = Integer.toString(android.os.Process.myPid());
        
        String sClassName = "";

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
                
        for (int i = 0 ; i < stackTraceElements.length ; i++) {
            sClassName  = stackTraceElements[i].getFileName().split("\\.")[0];
            if (isFindCaller && !CLASS_NAME.equals(sClassName)) {
                String sMethodName = stackTraceElements[i].getMethodName();
                int iLineNumber    = stackTraceElements[i].getLineNumber();
                Log.d(sProcessID, "[" + sClassName + "]["
                        + sMethodName + "]["
                        + iLineNumber + "] " + sLogMessage);
                break;
            }
            
            isFindCaller = CLASS_NAME.equals(sClassName);
        }
    }
    
    public static void d() {
        d("");
    }
    
    public static void e(String sLogMessage, Throwable throwableException) {
        if (!isDebug) { return; }
        
        boolean isFindCaller = false;
        
        String sProcessID = Integer.toString(android.os.Process.myPid());
        
        String sClassName = "";

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
                
        for (int i = 0 ; i < stackTraceElements.length ; i++) {
            sClassName  = stackTraceElements[i].getFileName().split("\\.")[0];
            if (isFindCaller && !CLASS_NAME.equals(sClassName)) {
                String sMethodName = stackTraceElements[i].getMethodName();
                int iLineNumber    = stackTraceElements[i].getLineNumber();
                Log.e(sProcessID, "[" + sClassName + "]["
                        + sMethodName + "]["
                        + iLineNumber + "] " + sLogMessage);
                break;
            }
            
            isFindCaller = CLASS_NAME.equals(sClassName);
        }
        
        if (null != throwableException) {
            Log.e(sClassName, Log.getStackTraceString(throwableException));
        }
    }
    
    public static void e(String sLogMessage) {
        e(sLogMessage, null);
    }
    
    public static void e(Throwable throwableException) {
        e("", throwableException);
    }
    
}
