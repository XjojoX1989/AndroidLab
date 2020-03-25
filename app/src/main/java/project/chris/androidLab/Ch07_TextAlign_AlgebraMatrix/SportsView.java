package project.chris.androidLab.Ch07_TextAlign_AlgebraMatrix;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import project.chris.androidLab.Utils;

public class SportsView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Rect rect = new Rect();
    public static final float RADIUS = Utils.dp2px(150);
    Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setTextSize(Utils.dp2px(100));
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
//        paint.setColor();
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, RADIUS, paint);

    paint.getFontMetrics(fontMetrics);
//    int offset = fontMetrics.
//        canvas.drawText();
//        paint.getTextBounds();
    }
}
