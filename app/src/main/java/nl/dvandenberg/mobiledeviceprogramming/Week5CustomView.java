package nl.dvandenberg.mobiledeviceprogramming;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Daniel on 4/10/2016.
 */
public class Week5CustomView extends View {
    private final Paint clearPaint = new Paint();
    private final Paint circlePaint = new Paint();
    private final Point position = new Point();
    private final Rect clearRect = new Rect(0, 0, 150, 150);

    {
        clearPaint.setColor(Color.WHITE);
        circlePaint.setColor(Color.RED);
        position.set(75, 75);
    }

    public Week5CustomView(Context context) {
        super(context);
    }

    public Week5CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Week5CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getLayoutParams().width == ViewGroup.LayoutParams.MATCH_PARENT) {
            setMeasuredDimension(((View) getParent()).getWidth(), ((View) getParent()).getHeight());
            return;
        }
        setMeasuredDimension(150, 150);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(clearRect, clearPaint);
        canvas.drawCircle(position.x, position.y, 25, circlePaint);
    }

    public void up() {
        position.set(75, 25);
        invalidate();
    }

    public void down() {
        position.set(75, 125);
        invalidate();
    }

    public void left() {
        position.set(25, 75);
        invalidate();
    }

    public void right() {
        position.set(125, 75);
        invalidate();
    }
}
