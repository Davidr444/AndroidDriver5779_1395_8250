package com.jct.davidandyair.androiddriver5779_1395_8250.model.backend;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Drive;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyService extends Service {
    FireBaseBackend.NotifyDataChange<List<Drive>> listener;
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(), " Service Create", Toast.LENGTH_LONG);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        listener = new FireBaseBackend.NotifyDataChange<List<Drive>>() {
            @Override
            public void OnDataChanged(List<Drive> obj) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.SECOND,-10);
                Date date = calendar.getTime();
                for (Drive drive : obj) {
                   // todo: if (drive.getWhenLoadToFirebase().after(date)) {
                    {
                        Intent intent = new Intent(MyService.this, MyReceiver.class);
                        sendBroadcast(intent);
                    }
                }
            }
            @Override
            public void onFailure(Exception exception) {
            }
        };
        FactoryBackend.getBackend().addNotifyDataChangeListener(listener);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FactoryBackend.getBackend().removeNotifyDataChangeListener(listener);
        Toast.makeText(getApplicationContext(), " Service destroy", Toast.LENGTH_LONG);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
