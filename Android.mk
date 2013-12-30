LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

############################################################
LOCAL_STATIC_JAVA_LIBRARIES := MarsPandoraUI-jar-lib
LOCAL_STATIC_JAVA_LIBRARIES := MarsPandoraUI-jar-lib2
LOCAL_MODULE_TAGS := samples
############################################################

LOCAL_SRC_FILES := $(call all-subdir-java-files)

LOCAL_PACKAGE_NAME := MarsPandoraUI

include $(BUILD_PACKAGE)

############################################################
include $(CLEAR_VARS)

LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := \
MarsPandoraUI-jar-lib:libs/commons-codec-1.3.jar\
MarsPandoraUI-jar-lib2:libs/cryptix-jce-provider.jar 

include $(BUILD_MULTI_PREBUILT)

include $(call all-makefiles-under,$(LOCAL_PATH))
