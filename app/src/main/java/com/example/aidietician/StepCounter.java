package com.example.aidietician;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StepCounter extends Service implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor stepSensor;
    private int stepCount;
    private SharedPreferences sharedPreferences;
    private String sharedPrefKey = "StepCountPref";
    private String stepCountKey = "StepCount";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

    private Handler handler;
    private Runnable resetStepCountRunnable = new Runnable() {
        @Override
        public void run() {
            resetStepCount();
        }
    };

    public void onCreate(){
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sharedPreferences = getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE);
        stepCount = sharedPreferences.getInt(stepCountKey, 0);
        handler = new Handler();
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        if(stepSensor != null){
            sensorManager.registerListener(this,stepSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }

        handler.postDelayed(resetStepCountRunnable, getMillisToNextDay());
        return START_STICKY;
    }

//    public StepCounter() {
//
//    }

    public void onDestroy(){
        super.onDestroy();
        sensorManager.unregisterListener(this);
        handler.removeCallbacks(resetStepCountRunnable);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            stepCount = (int) event.values[0];
            broadcastStepCount();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void resetStepCount() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(stepCountKey, stepCount);
        editor.apply();

        stepCount = 0;
        handler.postDelayed(resetStepCountRunnable, getMillisToNextDay());
    }

    private long getMillisToNextDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long currentTime = System.currentTimeMillis();
        long nextDayTime = calendar.getTimeInMillis();

        return nextDayTime - currentTime;
    }

    private void broadcastStepCount() {
        Intent intent = new Intent("STEP_COUNT_UPDATE");
        intent.putExtra("step_count",stepCount);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}