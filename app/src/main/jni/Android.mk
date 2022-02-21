# Copyright (C) 2009 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
LOCAL_PATH := $(call my-dir)

# FFmpeg library
include $(CLEAR_VARS)
LOCAL_MODULE := avformat
LOCAL_SRC_FILES := ${TARGET_ARCH_ABI}/libavformat.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := avcodec
LOCAL_SRC_FILES := ${TARGET_ARCH_ABI}/libavcodec.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := avfilter
LOCAL_SRC_FILES := ${TARGET_ARCH_ABI}/libavfilter.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := avutil
LOCAL_SRC_FILES := ${TARGET_ARCH_ABI}/libavutil.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := swresample
LOCAL_SRC_FILES := ${TARGET_ARCH_ABI}/libswresample.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := swscale
LOCAL_SRC_FILES := ${TARGET_ARCH_ABI}/libswscale.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := rtmp
LOCAL_SRC_FILES := ${TARGET_ARCH_ABI}/librtmp.a
include $(PREBUILT_STATIC_LIBRARY)

# Program
include $(CLEAR_VARS)
LOCAL_MODULE    := rtmp-push
LOCAL_SRC_FILES := rtmp-push.cpp
LOCAL_C_INCLUDES +=$(LOCAL_PATH)/include
LOCAL_STATIC_LIBRARIES := avformat avcodec avfilter avutil swresample swscale rtmp
LOCAL_CPPFLAGS:=-std=c++11
LOCAL_LDLIBS += -lc -lz -llog
LOCAL_CPP_FEATURES += exceptions
include $(BUILD_SHARED_LIBRARY)

