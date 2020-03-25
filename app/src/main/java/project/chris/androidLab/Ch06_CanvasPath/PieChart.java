package project.chris.androidLab.Ch06_CanvasPath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import project.chris.androidLab.Utils;

public class PieChart extends View {
    private static final int RADIUS = (int) Utils.dp2px(150);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    RectF bound = new RectF();
    int[] angles = {60, 100, 120, 80};
    int[] colors = {Color.parseColor("#2729FF"), Color.parseColor("#c21858"),
            Color.parseColor("#009688"),
            Color.parseColor("#ff8f00")};

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bound.set(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS,
                getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int currentAngle = 0;
        for (int i = 0; i < angles.length; i++) {
            paint.setColor(colors[i]);
            canvas.drawArc(bound, currentAngle, angles[i], true, paint);
            currentAngle += angles[i];
        }
    }
}
