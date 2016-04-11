package nl.dvandenberg.mobiledeviceprogramming.birthdaycalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 4/9/2016.
 */
public class BirthdayDatabase {


    private final SQLiteDatabase birthdayDbWritable;
    private final SQLiteDatabase birthdayDbReadable;

    public BirthdayDatabase(Context context) {
        BirthdayDbHelper birthdayDbHelper = new BirthdayDbHelper(context);
        birthdayDbReadable = birthdayDbHelper.getReadableDatabase();
        birthdayDbWritable = birthdayDbHelper.getWritableDatabase();
    }

    public List<Birthday> getBirthdays(int month) {
        String[] projection = {
                Birthday.BirthdayEntry.COLUMN_NAME_ENTRY_ID,
                Birthday.BirthdayEntry.COLUMN_NAME_DAY,
                Birthday.BirthdayEntry.COLUMN_NAME_MONTH,
                Birthday.BirthdayEntry.COLUMN_NAME_NAME
        };
        String sortOrder =
                Birthday.BirthdayEntry.COLUMN_NAME_DAY + "," + Birthday.BirthdayEntry.COLUMN_NAME_NAME + " DESC";
        String selection = Birthday.BirthdayEntry.COLUMN_NAME_MONTH + "=?";

        Cursor c = birthdayDbReadable.query(
                Birthday.BirthdayEntry.TABLE_NAME,
                projection,
                selection,
                new String[]{String.valueOf(month)},
                null,
                null,
                sortOrder
        );

        c.moveToFirst();
        List<Birthday> returnSet = new ArrayList<>();
        while (!c.isAfterLast()) {
            returnSet.add(Birthday.fromCursor(c));
            c.moveToNext();
        }
        return returnSet;
    }

    public void addBirthday(Birthday birthday) {
        birthdayDbWritable.insert(Birthday.BirthdayEntry.TABLE_NAME, null, birthdayToContentValues(birthday));
    }

    private ContentValues birthdayToContentValues(Birthday birthday) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Birthday.BirthdayEntry.COLUMN_NAME_NAME, birthday.getName());
        contentValues.put(Birthday.BirthdayEntry.COLUMN_NAME_DAY, birthday.getDay());
        contentValues.put(Birthday.BirthdayEntry.COLUMN_NAME_MONTH, birthday.getMonth());
        return contentValues;
    }

    public void removeBirthday(Birthday entry) {
        birthdayDbWritable.delete(Birthday.BirthdayEntry.TABLE_NAME, Birthday.BirthdayEntry.COLUMN_NAME_ENTRY_ID + "=?", new String[]{String.valueOf(entry.getId())});
    }
}
