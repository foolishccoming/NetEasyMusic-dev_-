package t3.henu.neteasymusic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        // 小于定时时间，重新启动定时服务
        if (AlarmService.tickCount++ < AlarmService.timelong) {
            Intent start = new Intent(context, AlarmService.class);
            context.startService(start);
        } else {
            // 等于定时时间关闭服务，关闭程序
            Intent stop = new Intent(context, AlarmService.class);
            context.stopService(stop);
            System.exit(0);
        }
        // 前3秒给出提示
        if (AlarmService.tickCount == AlarmService.timelong - 3)
            Toast.makeText(context, "即将关闭程序", Toast.LENGTH_LONG).show();
    }
}