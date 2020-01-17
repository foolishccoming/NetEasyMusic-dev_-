package t3.henu.neteasymusic;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;



public class AlarmService extends Service {
    //设置定时时间
    public static int timelong = 0;
    // 定时计数器
    public static int tickCount = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("启动定时服务")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .build();


        manager.notify(1, notification);
        startForeground(1, notification);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        // 设置1秒定时
        long setTime = 1000 + SystemClock.elapsedRealtime();
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent p = PendingIntent.getBroadcast(this, 0, i, 0);
        //设置定时任务
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, setTime, p);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}