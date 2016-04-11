package nl.dvandenberg.mobiledeviceprogramming;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.Calendar;

public class Week2a extends AppCompatActivity implements DatePicker.OnDateChangedListener {

    private RadioGroup radioGroupGender;
    private EditText editTextName;
    private EditText editTextEmailAddress;
    private EditText editTextPhoneNumber;
    private Integer selectedYear;
    private Integer selectedMonth;
    private Integer selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week2a);
        radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmailAddress = (EditText) findViewById(R.id.editTextEmailAddress);
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        DatePicker datePickerDateOfBirth = (DatePicker) findViewById(R.id.datePickerDateOfBirth);
        Calendar calendar = Calendar.getInstance();
        if (calendar == null || datePickerDateOfBirth == null) {
            if (BuildConfig.DEBUG) {
                throw new AssertionError();
            }
        } else {
            datePickerDateOfBirth.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), this);
        }
    }

    public void doneFillingIn(View view) {
        if (BuildConfig.DEBUG && !view.equals(findViewById(R.id.buttonDone))) {
            throw new AssertionError();
        }
        if (checkAllFilledIn()) {
            ShareMethods.shareString(this, getResources().getString(R.string.share_using), generatePersonalDetailsString());
        } else {
            new AlertDialog.Builder(this).setTitle("Please fill in all fields.").setMessage("Not all fields have been filled in.").setNeutralButton("Take me back.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        }
    }

    private String generatePersonalDetailsString() {
        return String.format(getResources().getString(R.string.personalData), editTextName.getText().toString(), editTextEmailAddress.getText().toString(), editTextPhoneNumber.getText().toString(), selectedYear, selectedMonth, selectedDay);
    }

    private boolean checkAllFilledIn() {
        boolean allChecked;
        allChecked = radioGroupGender.getCheckedRadioButtonId() != -1;
        allChecked &= !editTextName.getText().toString().isEmpty();
        allChecked &= !editTextEmailAddress.getText().toString().isEmpty();
        allChecked &= !editTextPhoneNumber.getText().toString().isEmpty();
        allChecked &= selectedYear != null;
        allChecked &= selectedMonth != null;
        allChecked &= selectedDay != null;
        return allChecked;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.selectedYear = year;
        this.selectedMonth = monthOfYear;
        this.selectedDay = dayOfMonth;
    }
}
