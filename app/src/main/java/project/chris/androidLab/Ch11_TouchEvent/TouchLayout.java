package project.chris.androidLab.Ch11_TouchEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class TouchLayout extends RelativeLayout {
    public TouchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("＊＊＊", "父View onInterceptTouchEvent");
        float delta = ev.getY();
        if (delta>100)
            return true;
        else
            return false;

//        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("＊＊＊", "父View ontouch");
        return false;
    }
}
