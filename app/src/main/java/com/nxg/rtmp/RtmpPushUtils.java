package com.nxg.rtmp;

public class RtmpPushUtils {

    static {
        System.loadLibrary("rtmp-push");
    }

    public static native int setCallback(PushCallback pushCallback);

    public static native String getAvcodecConfiguration();

    public static native int pushRtmpFile(String rtmpUrl,String path);

    interface PushCallback {
        void videoCallback(final long pts, final long dts, final long duration, final long index);
    }

}
