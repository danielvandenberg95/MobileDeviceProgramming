package nl.dvandenberg.mobiledeviceprogramming;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import nl.dvandenberg.mobiledeviceprogramming.birthdaycalendar.Birthday;
import nl.dvandenberg.mobiledeviceprogramming.birthdaycalendar.BirthdayDatabase;

public class Week3 extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private ListView listView;
    private BirthdayDatabase birthdayDatabase;
    private int showingMonth;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week3);
        listView = (ListView) findViewById(R.id.listViewBirthdays);
        birthdayDatabase = new BirthdayDatabase(this);
        showBirthdays(1);

        gestureDetector = new GestureDetector(this, this);
        View.OnTouchListener gestureListener = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        View mainLayout = findViewById(R.id.mainLayout);
        if (mainLayout != null) {
            mainLayout.setOnTouchListener(gestureListener);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Really delete this entry?");

                final Birthday entry = (Birthday) parent.getAdapter().getItem(position);
                builder.setMessage(entry.toString());
                builder.setPositiveButton("Yes.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        birthdayDatabase.removeBirthday(entry);
                        showBirthdays(showingMonth);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("No, don't delete this entry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }

    public void showAddBirthdayPopup(View view) {
        if (BuildConfig.DEBUG && !view.equals(findViewById(R.id.buttonAddBirthday))) {
            throw new AssertionError();
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setTitle("Add birthday");
        builder.setView(R.layout.add_birthday);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText name = (EditText) ((AlertDialog) dialog).findViewById(R.id.editTextName);
                EditText month = (EditText) ((AlertDialog) dialog).findViewById(R.id.monthOfBirth);
                EditText day = (EditText) ((AlertDialog) dialog).findViewById(R.id.dayOfBirth);
                if (name == null || month == null || day == null) {
                    return;
                }
                final String stringName = name.getText().toString();
                if (stringName.isEmpty()) {
                    name.requestFocus();
                    return;
                }
                final String stringMonth = month.getText().toString();
                if (stringMonth.isEmpty()) {
                    month.requestFocus();
                    return;
                }
                final String stringDay = day.getText().toString();
                if (stringDay.isEmpty()) {
                    day.requestFocus();
                    return;
                }
                final int parsedMonth = Integer.parseInt(stringMonth);
                final int parsedDay = Integer.parseInt(stringDay);
                birthdayDatabase.addBirthday(new Birthday(stringName, parsedMonth, parsedDay, -1));
                showBirthdays(parsedMonth);
            }
        });
        builder.create().show();
    }

    private void showBirthdays(int month) {
        showingMonth = month;
        List<Birthday> birthdays = birthdayDatabase.getBirthdays(month);
        ArrayAdapter<Birthday> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, birthdays);
        listView.setAdapter(arrayAdapter);
        Log.d("Month", "Showing month " + month);
        setTitle("Week 3 | Maand " + month);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d("Fling", "X: " + velocityX);
        if (Math.abs(velocityX) > 8) {
            showBirthdays(((showingMonth + 11 + (velocityX < 0 ? 1 : -1)) % 12) + 1);
            return true;
        }
        return false;
    }
}
