package com.jct.davidandyair.androiddriver5779_1395_8250.model.backend;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Drive;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * <h1>MyService Service</h1></h1>
 * Service runs in the background and checks every 10 seconds
 * if a new drive has been added to the database.
 * If so, it activates the BroadcastReceiver.
 * @author  David Rakovsky and Yair Ben-David
 * @version 1.0
 * @since   2019-02-05
 */
public class MyService extends Service {
    FireBaseBackend.NotifyDataChange<List<Drive>> listener;


    /**
     * Function On Once the service is created, the message shows that the service was created.
     * @return void
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(), " Service Create", Toast.LENGTH_LONG);

    }

    /**
     * Function Once the service is activated,
     * the function checks every 10 seconds if there is an update
     * in the travel list, and if so, activates the BroadcastReceiver.
     * @return START_STICKY
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        listener = new FireBaseBackend.NotifyDataChange<List<Drive>>() {
            @Override
            public void OnDataChanged(List<Drive> obj) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.SECOND,-10);
                Date date = calendar.getTime();
                for (Drive drive : obj) {
                   if (drive.getWhenLoadToFIrebase().after(date)) {
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

    /**
     * Function Once the service is destroyed,
     * the function removes the listener and announces its destruction.
     * @return void
     */
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
