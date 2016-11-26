//package hanle.com.alarm;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import java.io.Serializable;
//
///**
// * Created by Hanle on 11/24/2016.
// */
//
//public class SqlliteModel implements Parcelable {
//
//    String eventid, event_title;
//
//    public  SqlliteModel(String eID,String evtitle)
//    {
//            this.eventid=eID;
//            this.event_title = evtitle;
//    }
//
//
//    private SqlliteModel(Parcel in) {
//        // This order must match the order in writeToParcel()
//        a1 = in.readString();
//        a2 = in.readString();
//        // Continue doing this for the rest of your member data
//    }
//    public String getEventid() {
//        return eventid;
//    }
//
//    public void setEventid(String eventid) {
//        this.eventid = eventid;
//    }
//
//    public String getEvent_title() {
//        return event_title;
//    }
//
//    public void setEvent_title(String event_title) {
//        this.event_title = event_title;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//
//    }
//}
