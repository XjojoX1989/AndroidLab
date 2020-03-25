package project.chris.androidLab.Ch11_TouchEvent;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class TouchView extends View {
    public TouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /*
    return true:表示後續的事件不再繼續傳遞給下一個view，例如:跟TouchView重疊的View or 父View 的touch event都不在觸發
           false:會繼續傳遞後續事件
           其實只有在ACTION_DOWN的時候返回true才有功用，其他ACTION其實沒差。
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.e("＊＊＊", "子View ontouch");
        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            performClick();
        }

        return true;
    }


}
