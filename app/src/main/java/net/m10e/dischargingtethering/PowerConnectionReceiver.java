package net.m10e.dischargingtethering;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PowerConnectionReceiver extends BroadcastReceiver {
    private final String TAG = this.getClass().getSimpleName();
    private WifiHotspotManager wifiHotspotManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (wifiHotspotManager == null) {
            wifiHotspotManager = new WifiHotspotManager(context);
        }

        String action = intent.getAction();

        switch (action) {
            case Intent.ACTION_POWER_CONNECTED: {
                Log.i(TAG, "Plugged");
                wifiHotspotManager.turnOffHotspot();
                break;
            }
            case Intent.ACTION_POWER_DISCONNECTED: {
                Log.i(TAG, "Unplugged");
                wifiHotspotManager.turnOnHotspot();
                break;
            }
        }
    }
}
