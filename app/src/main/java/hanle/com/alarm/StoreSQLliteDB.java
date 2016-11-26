package hanle.com.alarm;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Hanle on 11/22/2016.
 */

public class StoreSQLliteDB extends IntentService {
    private static final String TAG = StoreSQLliteDB.class.getSimpleName();
    DBController dbController;
    public static final long INTERVALONEMINUTE = 5 * 60 * 1000;

    public StoreSQLliteDB() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if(intent.getStringExtra("key")!=null){
            storeIntoSqllite();
        }

    }

    private void storeIntoSqllite() {

        String endpoint1 = "http://uat.hanlesolutions.com/hanle-test/14-09-2016-live/newchat/gcm_chat/v1/index.php/chat_rooms_list/91&8147494497";


        StringRequest strReq = new StringRequest(Request.Method.GET,
                endpoint1, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "response: " + response);

                try {
                    JSONObject obj = new JSONObject(response);

                    if (obj.getBoolean("error") == false) {
                        JSONArray chatRoomsArray = obj.getJSONArray("chat_rooms");
                        Log.d("Array length", String.valueOf(chatRoomsArray.length()));

                            for (int i = 0; i < chatRoomsArray.length(); i++) {
                                JSONObject chatRoomsObj = (JSONObject) chatRoomsArray.get(i);
                                dbController = new DBController(getApplicationContext());
                                HashMap<String, String> queryValues = new HashMap<String, String>();
                                queryValues.put("eID", chatRoomsObj.getString("event_id"));
                                queryValues.put("event_title", chatRoomsObj.getString("event_title"));
                                queryValues.put("event_status", chatRoomsObj.getString("event_status"));
                                queryValues.put("share_detial", chatRoomsObj.getString("share_detail"));
                                queryValues.put("user_attending_status", chatRoomsObj.getString("user_attending_status"));
                                queryValues.put("inviter_name", chatRoomsObj.getString("inviter_name"));
                                queryValues.put("date", chatRoomsObj.getString("date"));
                                queryValues.put("time", chatRoomsObj.getString("time"));
                                dbController.insertEvent(queryValues);
                                ArrayList<HashMap<String, String>> animalList = dbController.getallEvents();
                                for (HashMap<String, String> entry : animalList) {
                                    //System.out.println("SQL OP from new loop:" + entry.get("event_title"));
                                    AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

                                    Intent service1 = new Intent(getApplicationContext(), ScheduledPush.class);
                                    final PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(), ScheduledPush.REQUEST_CODE,
                                            service1, PendingIntent.FLAG_UPDATE_CURRENT);
                                    //long firstMillis = System.currentTimeMillis(); // alarm is set right away
                                    alarm.cancel(pIntent);

                                    Calendar alarmStartTime = Calendar.getInstance();
                                    Calendar now = Calendar.getInstance();
                                    alarmStartTime.set(Calendar.HOUR_OF_DAY, 8);
                                    alarmStartTime.set(Calendar.MINUTE, 00);
                                    alarmStartTime.set(Calendar.SECOND, 0);
                                    if (now.after(alarmStartTime)) {
                                        Log.d("Hey","Added a day");
                                        alarmStartTime.add(Calendar.DATE, 1);
                                    }
                                    alarm.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(),
                                            AlarmManager.INTERVAL_DAY, pIntent);


                                }

                            }



                    } else {
                        // error in fetching chat rooms
                        Toast.makeText(getApplicationContext(), "" + obj.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    }


                    // check for error flag


                } catch (JSONException e) {
                    Log.e(TAG, "json parsing error: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Server did not respond!!", Toast.LENGTH_LONG).show();
                }
//                catch (ParseException e) {
//                    e.printStackTrace();
//                }


                // subscribing to all chat room topics
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                Log.e(TAG, "Volley error: " + error.getMessage() + ", code: " + networkResponse);
                //Toast.makeText(ListOfEvent1.this, "Server did not respond!!", Toast.LENGTH_SHORT).show();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "time out error", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Server did not respond!!", Toast.LENGTH_SHORT).show();

                }
            }
        });


        strReq.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);
    }


}
