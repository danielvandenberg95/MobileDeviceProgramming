package nl.dvandenberg.mobiledeviceprogramming;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

public class LauncherActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final Map<String, Intent> intentMap = new HashMap<>();

    static {
        final Intent intentWeek1 = new Intent();
        intentWeek1.setClassName("nl.dvandenberg.mobiledeviceprogramming", Week1.class.getName());
        final Intent intentWeek2a = new Intent();
        intentWeek2a.setClassName("nl.dvandenberg.mobiledeviceprogramming", Week2a.class.getName());
        final Intent intentWeek2b = new Intent();
        intentWeek2b.setClassName("nl.dvandenberg.mobiledeviceprogramming", Week2b.class.getName());
        final Intent intentWeek3 = new Intent();
        intentWeek3.setClassName("nl.dvandenberg.mobiledeviceprogramming", Week3.class.getName());
        final Intent intentWeek4 = new Intent();
        intentWeek4.setClassName("nl.dvandenberg.mobiledeviceprogramming", Week4.class.getName());
        final Intent intentWeek5 = new Intent();
        intentWeek5.setClassName("nl.dvandenberg.mobiledeviceprogramming", Week5.class.getName());
        intentMap.put("Week 1", intentWeek1);
        intentMap.put("Week 2a", intentWeek2a);
        intentMap.put("Week 2b", intentWeek2b);
        intentMap.put("Week 3", intentWeek3);
        intentMap.put("Week 4", intentWeek4);
        intentMap.put("Week 5", intentWeek5);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        final ListView listView = (ListView) findViewById(R.id.listViewLauncherActivity);
        assert listView != null;
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(intentMap.get(String.valueOf(parent.getItemAtPosition(position))));
    }
}
