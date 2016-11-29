package hanle.com.alarm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Main2Activity {


    public static void main(String args[]) {
        ArrayList<String> title = new ArrayList<>();
        HashSet<String> al = new HashSet<String>();
        Iterator<String> itr;
        StringBuilder s1 = new StringBuilder();
        int k = 0;
        StringBuilder sb = null;
        String sal = null;

        title.add("saurabh");
        title.add("satarday");

        // Create a hash map
        for (int i = 0; i < title.size(); i++) {

            al.add(title.get(i));

        }

        System.out.println("Set:" + al);


        System.out.println(al.size());

        for (String str : al) {
            s1 = new StringBuilder();
            //System.out.println(++k+"."+str+"\n");
            s1.append(k++);
            s1.append(str);
            s1.append("\n");

        }
        sal = s1.toString();
        System.out.println(sal);

    }
}