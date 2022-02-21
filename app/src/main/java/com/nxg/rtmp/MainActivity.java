package com.nxg.rtmp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.sample_text);
        RtmpPushUtils.setCallback((pts, dts, duration, index) -> {
            Log.d(TAG, "videoCallback: pts " + pts);
            Log.d(TAG, "videoCallback: dts " + dts);
            Log.d(TAG, "videoCallback: duration " + duration);
            Log.d(TAG, "videoCallback: index " + index);
        });
        tv.setText(RtmpPushUtils.getAvcodecConfiguration() + "");
        //保存历史记录，判断SDCard是否存在并且可读写
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            System.out.println("getExternalStorageState成功！");
        } else {
            System.out.println("getExternalStorageState失败！");

        }
        pushRtmp();

    }

    public void pushRtmp() {
        new Thread() {
            @Override
            public void run() {
                Log.i(TAG, "run: pushRtmpFile --->" + Environment.getExternalStorageDirectory().getAbsolutePath());
                File File = new File(Environment.getExternalStorageDirectory() + "/Download/source.200kbps.768x320.flv");
                if (File.canRead()) {
                    Log.i(TAG, "run: pushRtmpFile ---> can read");
                }
                if (File.exists()) {
                    Log.i(TAG, "run: pushRtmpFile ---> exists");
                }
                Log.d(TAG, "run: pushRtmpFile ret = " + RtmpPushUtils.pushRtmpFile("rtmp://192.168.1.139:1935/live/livestream",
                        Environment.getExternalStorageDirectory() + "/Download/source.200kbps.768x320.flv"));

            }
        }.start();
    }

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    private void requestPermission() {

        Log.i(TAG, "requestPermission");
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "checkSelfPermission");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                Log.i(TAG, "shouldShowRequestPermissionRationale");
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            } else {
                Log.i(TAG, "requestPermissions");
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "onRequestPermissionsResult granted");
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.


                } else {
                    Log.i(TAG, "onRequestPermissionsResult denied");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    showWaringDialog();
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void showWaringDialog() {
        new AlertDialog.Builder(this)
                .setTitle("警告！")
                .setMessage("请前往设置->应用->RtmpPushJNI->权限中打开相关权限，否则功能无法正常运行！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 一般情况下如果用户不授权的话，功能是无法运行的，做退出处理
                        finish();
                    }
                }).show();
    }

}