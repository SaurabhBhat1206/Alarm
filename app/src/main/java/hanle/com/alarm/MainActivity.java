package hanle.com.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button)findViewById(R.id.button);
        Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListOfEvent1.class);
                startActivity(i);
            }
        });

        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
        //startAt10();

    }


    public void startAt10() {
        AlarmManager alarmManager =  (AlarmManager) getSystemService(ALARM_SERVICE);
        Date dat  = new Date();//initializes to now
        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();
        cal_now.setTime(dat);
        cal_alarm.setTime(dat);
        cal_alarm.set(Calendar.HOUR_OF_DAY,11);//set the alarm time
        cal_alarm.set(Calendar.MINUTE, 30);
        cal_alarm.set(Calendar.SECOND,0);
        if(cal_alarm.before(cal_now)){//if its in the past increment
            cal_alarm.add(Calendar.DATE,1);
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
    }
}
