package net.m10e.dischargingtethering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startService(View v) {
        Intent serviceIntent = new Intent(this, PowerConnectionService.class);
        ContextCompat.startForegroundService(this, serviceIntent);
        Log.v(TAG, "Started");
    }
    public void stopService(View v) {
        Intent serviceIntent = new Intent(this, PowerConnectionService.class);
        stopService(serviceIntent);
        Log.v(TAG, "Stopped");
    }
}
