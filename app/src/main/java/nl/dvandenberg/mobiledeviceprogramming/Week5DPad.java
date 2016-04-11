package nl.dvandenberg.mobiledeviceprogramming;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Daniel on 4/10/2016.
 */
public class Week5DPad extends ImageView {
    {
        super.setImageDrawable(getContext().getDrawable(R.drawable.dpad));
    }

    public Week5DPad(Context context) {
        super(context);
    }

    public Week5DPad(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Week5DPad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }
        Week5CustomView customView = (Week5CustomView) ((ViewGroup) getParent()).findViewById(R.id.customView);
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (Math.abs(x - getWidth() / 2) < getWidth() / 4) {
            if (Math.abs(y - getHeight() / 2) > getHeight() / 4) {
                if (y > getHeight() / 2) {
                    customView.down();
                    return true;
                } else {
                    customView.up();
                    return true;
                }
            }
        }


        if (Math.abs(y - getHeight() / 2) < getHeight() / 4) {
            if (Math.abs(x - getWidth() / 2) > getWidth() / 4) {
                if (x > getWidth() / 2) {
                    customView.right();
                    return true;
                } else {
                    customView.left();
                    return true;
                }
            }
        }

        return false;
    }
}
