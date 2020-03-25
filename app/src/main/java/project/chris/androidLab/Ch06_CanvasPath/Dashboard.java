package project.chris.androidLab.Ch06_CanvasPath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import project.chris.androidLab.Utils;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Dashboard extends View {
    public static final float ANGLE = 120;
    public static final float RADIUS = Utils.dp2px(150);
    public static final float LENGTH = Utils.dp2px(100);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path dash = new Path();
    PathDashPathEffect pathDashPathEffect;

    public Dashboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 只寫大括號代表在constructor內的super後面執行,
     * 只有一個constructor可以寫在Dashboard(Context context)內,
     * 但若是有多個就很麻煩需要每個都寫, 因此改用這種寫法
     */ {
        paint.setStyle(Paint.Style.STROKE);
        dash.addRect(0, 0, Utils.dp2px(2), Utils.dp2px(10), Path.Direction.CW);
        Path arc = new Path();
        arc.addArc(getWidth() / 2 - RADIUS,
                getHeight() / 2 - RADIUS,
                getWidth() / 2 + RADIUS,
                getHeight() / 2 + RADIUS,
                90 + ANGLE / 2, 360 - ANGLE);
        PathMeasure pathMeasure = new PathMeasure(arc, false);
        //要幾個刻度
        int count = (int) (pathMeasure.getLength() / 20);
        pathDashPathEffect = new PathDashPathEffect(dash, count, 0, PathDashPathEffect.Style.ROTATE);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //畫弧線
        canvas.drawArc(getWidth() / 2 - RADIUS,
                getHeight() / 2 - RADIUS,
                getWidth() / 2 + RADIUS,
                getHeight() / 2 + RADIUS,
                90 + ANGLE / 2, 360 - ANGLE,
                false, paint);

        //畫刻度
        paint.setPathEffect(pathDashPathEffect);
        canvas.drawArc(getWidth() / 2 - RADIUS,
                getHeight() / 2 - RADIUS,
                getWidth() / 2 + RADIUS,
                getHeight() / 2 + RADIUS,
                90 + ANGLE / 2, 360 - ANGLE,
                false, paint);
        paint.setPathEffect(null);

        canvas.drawLine(getWidth() / 2, getHeight() / 2,
                (float) Math.cos(Math.toRadians(getAngleInSpecificMarkPosition(5))) * LENGTH + getWidth() / 2,
                (float) Math.sin(Math.toRadians(getAngleInSpecificMarkPosition(5))) * LENGTH + getHeight() / 2, paint);
    }

    int getAngleInSpecificMarkPosition(int position) {
        return (int) (90 + ANGLE / 2 + ((360 - ANGLE) / 20) * position);
    }
}
