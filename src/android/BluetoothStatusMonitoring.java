package com.laysolitary.bluestatus;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * This class echoes a string called from JavaScript.
 */
public class BluetoothStatusMonitoring extends CordovaPlugin {
    public static final String LOG_TAG = "BluetoothStatusMonitoring";
    private static CallbackContext bleStatusContext;
    private static CallbackContext gpsStatusContext;


    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getBlutoothStatus")) {
            bleStatusContext = callbackContext;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
            cordova.getContext().registerReceiver(new BlueStatusReceiver(), intentFilter);
            if (args.getString(0) != null && args.getString(0) != "") {

                PluginResult result = new PluginResult(PluginResult.Status.OK, args.getString(0));
                result.setKeepCallback(true);
                bleStatusContext.sendPluginResult(result);
            }
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter.isEnabled()) {
                PluginResult resultenable = new PluginResult(PluginResult.Status.OK, "bluetoothopen");
                resultenable.setKeepCallback(true);
                bleStatusContext.sendPluginResult(resultenable);
            } else {
                PluginResult resultenable = new PluginResult(PluginResult.Status.OK, "bluetoothoff");
                resultenable.setKeepCallback(true);
                bleStatusContext.sendPluginResult(resultenable);
            }
           return true;
        } else if (action.equals("getGPSStatus")) {
            gpsStatusContext = callbackContext;
            IntentFilter filter = new IntentFilter();
            filter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
            cordova.getContext().registerReceiver(new GpsStatusReceiver(), filter);
            if (args.getString(0) != null && args.getString(0) != "") {
                PluginResult result = new PluginResult(PluginResult.Status.OK, args.getString(0));
                result.setKeepCallback(true);
                gpsStatusContext.sendPluginResult(result);
            }
            gpsIsOpen(cordova.getContext());
            return true;
        }
        return false;
    }
    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context
     * @return true 表示开启
     */
    public boolean gpsIsOpen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            PluginResult resultenable = new PluginResult(PluginResult.Status.OK, "gpsopen");
            resultenable.setKeepCallback(true);
            gpsStatusContext.sendPluginResult(resultenable);
            return true;
        }
        PluginResult resultenable = new PluginResult(PluginResult.Status.OK, "gpsoff");
        resultenable.setKeepCallback(true);
        gpsStatusContext.sendPluginResult(resultenable);
        return false;
    }
    /**
     * 监听GPS 状态变化广播
     */
    private class GpsStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(LocationManager.PROVIDERS_CHANGED_ACTION)){
                gpsIsOpen(context);
            }
        }
    }

    private class BlueStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                int intExtra = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                if (BluetoothAdapter.STATE_OFF == intExtra) {
                    PluginResult result = new PluginResult(PluginResult.Status.OK, "bluetoothoff");
                    result.setKeepCallback(true);
                    bleStatusContext.sendPluginResult(result);
                } else if (BluetoothAdapter.STATE_ON == intExtra) {
                    PluginResult result = new PluginResult(PluginResult.Status.OK, "bluetoothopen");
                    result.setKeepCallback(true);
                    bleStatusContext.sendPluginResult(result);
                }
            }
        }
    }
}





