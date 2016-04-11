package nl.dvandenberg.mobiledeviceprogramming.birthdaycalendar;

import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * Created by Daniel on 4/9/2016.
 */
public class Birthday {
    private final String name;
    private final int month;
    private final int day;
    private final int id;

    public Birthday(String name, int month, int day, int id) {
        this.name = name;
        this.month = month;
        this.day = day;
        this.id = id;
    }

    public static Birthday fromCursor(Cursor c) {
        String name = c.getString(c.getColumnIndexOrThrow(BirthdayEntry.COLUMN_NAME_NAME));
        int month = c.getInt(c.getColumnIndexOrThrow(BirthdayEntry.COLUMN_NAME_MONTH));
        int day = c.getInt(c.getColumnIndexOrThrow(BirthdayEntry.COLUMN_NAME_DAY));
        int id = c.getInt(c.getColumnIndexOrThrow(BirthdayEntry.COLUMN_NAME_ENTRY_ID));
        return new Birthday(name, month, day, id);
    }

    public String getName() {
        return name;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "[" + this.id + "] " + this.name + ": " + this.day + "-" + this.month;
    }

    public static abstract class BirthdayEntry implements BaseColumns {
        public static final String TABLE_NAME = "birthdays";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_MONTH = "month";
        public static final String COLUMN_NAME_DAY = "day";
    }

    public static abstract class BirthdayTable {
        public static final String TEXT_TYPE = " TEXT";
        public static final String INT_TYPE = " INTEGER";
        public static final String COMMA_SEP = ",";
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + BirthdayEntry.TABLE_NAME + " (" +
                        BirthdayEntry.COLUMN_NAME_ENTRY_ID + INT_TYPE + " PRIMARY KEY," +
                        BirthdayEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                        BirthdayEntry.COLUMN_NAME_MONTH + INT_TYPE + COMMA_SEP +
                        BirthdayEntry.COLUMN_NAME_DAY + INT_TYPE +
                        " )";
    }
}
