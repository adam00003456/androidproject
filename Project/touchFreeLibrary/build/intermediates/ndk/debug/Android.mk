LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := touch_free_library
LOCAL_CFLAGS := -I/Users/Adam/Desktop/opencv/sdk/native/jni/include -llog -ldl
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	C:\Users\Adam\AndroidStudioProjects\Project\touchFreeLibrary\src\main\jni\Android.mk \
	C:\Users\Adam\AndroidStudioProjects\Project\touchFreeLibrary\src\main\jni\Application.mk \
	C:\Users\Adam\AndroidStudioProjects\Project\touchFreeLibrary\src\main\jni\motion_averager.cpp \

LOCAL_C_INCLUDES += C:\Users\Adam\AndroidStudioProjects\Project\touchFreeLibrary\src\main\jni
LOCAL_C_INCLUDES += C:\Users\Adam\AndroidStudioProjects\Project\touchFreeLibrary\src\debug\jni

include $(BUILD_SHARED_LIBRARY)
