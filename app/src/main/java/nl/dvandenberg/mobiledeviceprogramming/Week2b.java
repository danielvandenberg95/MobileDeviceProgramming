package nl.dvandenberg.mobiledeviceprogramming;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Week2b extends AppCompatActivity implements View.OnTouchListener {
    // possible touch states
    final static int NONE = 0;
    final static int DRAG = 1;
    final static int ZOOM = 2;
    final static float MIN_DIST = 50;
    private static final float MIN_ROTATION = 0;
    Matrix matrix = new Matrix();
    Matrix eventMatrix = new Matrix();
    int touchState = NONE;
    float eventDistance = 0;
    float eventX = 0, eventY = 0;
    private float rotation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week2b);
        ImageView view = (ImageView) findViewById(R.id.imageView);
        if (view != null) {
            view.setOnTouchListener(this);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView view = (ImageView) v;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                //primary touch event starts: remember touch down location
                touchState = DRAG;
                eventX = event.getX(0);
                eventY = event.getY(0);
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                //secondary touch event starts: remember distance and center
                eventDistance = calcDistance(event);
                rotation = calculateRotation(event);
                Log.d("Rotation", "Start: " + rotation);
                calcMidpoint(event);
                if (eventDistance > MIN_DIST)
                    touchState = ZOOM;
                else
                    touchState = NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                if (touchState == DRAG) {
                    //single finger drag, translate accordingly
                    matrix.set(eventMatrix);
                    matrix.postTranslate(event.getX(0) - eventX,
                            event.getY(0) - eventY);

                } else if (touchState == ZOOM) {
                    //multi-finger zoom, scale accordingly around center
                    float dist = calcDistance(event);
                    float rotation = calculateRotation(event) - this.rotation;
                    Log.d("Rotation", "now: " + rotation);

                    if (dist > MIN_DIST || Math.abs(rotation) > MIN_ROTATION) {
                        matrix.set(eventMatrix);
                        if (dist > MIN_DIST) {
                            float scale = dist / eventDistance;
                            matrix.postScale(scale, scale, eventX, eventY);
                        }
                        if (Math.abs(rotation) > MIN_ROTATION) {
                            matrix.postRotate(rotation, eventX, eventY);
                        }
                    }
                }

                // Perform the transformation
                view.setImageMatrix(matrix);
                break;

            case MotionEvent.ACTION_UP:
                eventMatrix.set(matrix);
                touchState = NONE;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                eventMatrix.set(matrix);
                touchState = NONE;
                break;
        }

        return true;
    }

    private float calculateRotation(MotionEvent event) {
        return (float) Math.toDegrees(Math.atan2(event.getY(1) - event.getY(0), event.getX(1) - event.getX(0)));
    }

    private float calcDistance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void calcMidpoint(MotionEvent event) {
        eventX = (event.getX(0) + event.getX(1)) / 2;
        eventY = (event.getY(0) + event.getY(1)) / 2;
    }
}
