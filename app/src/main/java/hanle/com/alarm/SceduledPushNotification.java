package hanle.com.alarm;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Hanle on 10/24/2016.
 */

public class SceduledPushNotification extends IntentService {

    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    private static int NOTIFICATION_ID = 1;
    Notification notification;
    private static final String TAG = SceduledPushNotification.class.getSimpleName();
    StringBuilder sam;
    String sl;
    ArrayList<String> title;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public SceduledPushNotification() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        //ArrayList<SqlliteModel> sq = new ArrayList<>();
        Context context = this.getApplicationContext();
        Intent mIntent = new Intent(this, ListOfEvent1.class);

        title = intent.getStringArrayListExtra("ar");
        System.out.println("Array values:" + title);

        pendingIntent = PendingIntent.getActivity(context, ScheduledPush.REQUEST_CODE, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Resources res = this.getResources();
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        StringBuilder s1 = new StringBuilder();
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();


        for (int i = 0; i < title.size(); i++) {
//            s1.append(i);
//            String[] s = title.get(i).split(":");
//            s1.append(s[1]);
//            s1.append(",");


            //String eventtitle = s1.toString();
            String tl = "Event Scheduled for today:";
            //inboxStyle.addLine(title.get(i));
            Log.e("evt:", title.get(i));
            Notification notification;
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

            notification = builder.setSmallIcon(R.drawable.nooismall).setTicker(tl).setWhen(0)

                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.nooismall)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.nooismall))
                    .setTicker("nooi Events")
                    .setAutoCancel(true)
                    .setStyle(inboxStyle)
                    .setSound(soundUri)
                    .setContentTitle("Event Scheduled for today:")
                    .setContentText(i + 1+"." + title.get(i))
                    .build();


            notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
            notification.defaults |= Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
            notification.ledARGB = 0xFFFFA500;
            notification.ledOnMS = 800;
            notification.ledOffMS = 1000;
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID, notification);

//         intent = new Intent(getApplicationContext(), ScheduledPush.class);
//        final PendingIntent pIntent = PendingIntent.getBroadcast(this, ScheduledPush.REQUEST_CODE,
//                intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//        alarm.cancel(pIntent);
//        Log.e(TAG,"Alarm cancelled");
            //AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        }
    }
}

