package com.example.anukoolfinal.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.anukoolfinal.R;

public class ProtectorDeviceAdminReceiver extends DeviceAdminReceiver {
    public void onEnabled(Context context, Intent intent) {
        Toast.makeText(context, R.string.toast_msg_device_admin_enable, 1).show();
    }

    public void onDisabled(Context context, Intent intent) {
        Toast.makeText(context, R.string.toast_msg_device_admin_disable, 1).show();
    }
}
