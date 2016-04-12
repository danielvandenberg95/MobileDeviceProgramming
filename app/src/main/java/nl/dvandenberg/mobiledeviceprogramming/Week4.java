package nl.dvandenberg.mobiledeviceprogramming;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Week4 extends AppCompatActivity {

    private final static URL url;

    static {
        URL url1;
        try {
            url1 = new URL("http://192.168.137.1:8080/hello");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            url1 = null;

        }
        url = url1;
    }

    private EditText textViewOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week4);
        textViewOutput = (EditText) findViewById(R.id.editTextReply);
    }

    public void sendRequest(View view) {
        if (BuildConfig.DEBUG && !view.equals(findViewById(R.id.buttonSendRequest))) {
            throw new AssertionError();
        }
        final EditText editTextAge = (EditText) findViewById(R.id.editTextAge);
        final EditText editTextName = (EditText) findViewById(R.id.editTextName);

        new Thread(new executeRequest(Integer.parseInt(editTextAge != null ? editTextAge.getText().toString() : null), editTextName != null ? editTextName.getText().toString() : null)).start();

    }

    private class executeRequest implements Runnable {
        private final int age;
        private final String name;

        public executeRequest(int age, String name) {

            this.age = age;
            this.name = name;
        }

        @Override
        public void run() {
            final StringBuilder stringBuilder = new StringBuilder();
            try {
                URLConnection urlConnection = url.openConnection();
                urlConnection.setDoOutput(true);


                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write("name=" + name + "&age=" + age);
                writer.flush();
                writer.close();
                os.close();

                BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                String s = r.readLine();
                while (s != null) {
                    stringBuilder.append(s);
                    stringBuilder.append("\n");
                    s = r.readLine();
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textViewOutput.setText(stringBuilder.toString());
                }
            });
        }
    }
}
