package nl.dvandenberg.mobiledeviceprogramming;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Week1 extends AppCompatActivity {
    private int buttonPressCount = 0;
    private TextView outputTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week1);
        outputTextView = (TextView) findViewById(R.id.buttonPressCount);
        if (savedInstanceState != null) {
            buttonPressCount = savedInstanceState.getInt("buttonPressCount", 0);
        }
        setOutputText();
    }

    private void setOutputText() {
        outputTextView.setText(getButtonPressText());
    }

    private String getButtonPressText() {
        return String.format(getString(R.string.buttonPressCount), buttonPressCount);
    }

    public void click(View view) {
        if (BuildConfig.DEBUG && !view.equals(findViewById(R.id.buttonIncrease))) {
            throw new AssertionError();
        }
        buttonPressCount++;
        setOutputText();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("buttonPressCount", buttonPressCount);
        super.onSaveInstanceState(outState);
    }

    public void share(View view) {
        if (BuildConfig.DEBUG && !view.equals(findViewById(R.id.buttonShare))) {
            throw new AssertionError();
        }
        ShareMethods.shareString(this, this.getResources().getString(R.string.share_using), getButtonPressText());
    }

}
