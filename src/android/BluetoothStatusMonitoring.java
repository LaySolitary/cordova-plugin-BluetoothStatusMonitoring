package com.laysolitary.bluestatus;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * This class echoes a string called from JavaScript.
 */
public class BluetoothStatusMonitoring extends CordovaPlugin {

    public static final String LOG_TAG = "bluestatus";
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getBlutoothStatus")) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
            this.cordova.getActivity().registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    System.out.println(action);
                    if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                        int intExtra = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                        if (BluetoothAdapter.STATE_OFF == intExtra) {
                            PluginResult result = new PluginResult(PluginResult.Status.OK, "蓝牙关闭3");
                            result.setKeepCallback(true);
                            callbackContext.sendPluginResult(result);
                        } else if (BluetoothAdapter.STATE_ON == intExtra) {
                            PluginResult result = new PluginResult(PluginResult.Status.OK, "蓝牙关闭3");
                            result.setKeepCallback(true);
                            callbackContext.sendPluginResult(result);
                        }
                    } else if (action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
                        System.out.println("蓝牙已断开");
                    } else if (action.equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {
                        System.out.println("蓝牙已连接" );
                    }
                }
            }, intentFilter);
           return true;
        }
        return false;
    }
}



