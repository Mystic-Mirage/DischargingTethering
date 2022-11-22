package net.m10e.dischargingtethering;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.ActivityCompat;

public class WifiHotspotManager {
    private final String TAG = this.getClass().getSimpleName();
    private final WifiManager wifiManager;
    private WifiManager.LocalOnlyHotspotReservation hotspotReservation;
    private final Context context;

    WifiHotspotManager(Context context) {
        this.context = context;
        wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    public void turnOnHotspot() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "no permissions");
            return;
        }
        wifiManager.startLocalOnlyHotspot(new WifiManager.LocalOnlyHotspotCallback() {
            @Override
            public void onStarted(WifiManager.LocalOnlyHotspotReservation reservation) {
                super.onStarted(reservation);
                hotspotReservation = reservation;
                WifiConfiguration currentConfig = hotspotReservation.getWifiConfiguration();

                printCurrentConfig(currentConfig);
                Log.v(TAG, "Local Hotspot Started");
            }

            @Override
            public void onStopped() {
                super.onStopped();
                Log.v(TAG, "Local Hotspot Stopped");
            }

            @Override
            public void onFailed(int reason) {
                super.onFailed(reason);
                Log.v(TAG, "Local Hotspot failed to start");
            }
        }, new Handler());
    }

    public void turnOffHotspot() {
        if (hotspotReservation != null) {
            hotspotReservation.close();
            hotspotReservation = null;
            Log.v(TAG, "Turned off hotspot");
        }
    }

    private void printCurrentConfig(WifiConfiguration wifiConfiguration) {
        Log.v(TAG, "SSID: " + wifiConfiguration.SSID + " | PASS: " + wifiConfiguration.preSharedKey);
    }
}
