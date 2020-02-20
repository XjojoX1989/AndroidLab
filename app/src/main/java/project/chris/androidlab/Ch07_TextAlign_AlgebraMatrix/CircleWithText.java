package project.chris.androidlab.Ch07_TextAlign_AlgebraMatrix;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import project.chris.androidlab.Utils;

public class CircleWithText extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final float RADIUS = Utils.dp2px(150);
    Paint.FontMetrics fontMetrics = new Paint.FontMetrics();


    public CircleWithText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setTextSize(Utils.dp2px(100));
        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Quicksand-Regular.ttf"));
        paint.setColor(Color.LTGRAY);
        paint.getFontMetrics(fontMetrics);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(10));
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, RADIUS, paint);

        paint.setColor(Color.BLUE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS, 270, 210, false, paint);

        paint.setTextSize(Utils.dp2px(100));
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        float offset = ((fontMetrics.ascent + fontMetrics.descent) / 2);
        canvas.drawText("abygba", getWidth() / 2, (getHeight() / 2) - offset, paint);
    }
}
