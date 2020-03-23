package project.chris.androidLab.Ch07_TextAlign_AlgebraMatrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import project.chris.androidLab.R;
import project.chris.androidLab.Utils;

public class BreakText extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private String article = "Give lady of they such they sure it. Me contained explained my education. Vulgar as hearts by garret. Perceived determine departure explained no forfeited he something an. Contrasted dissimilar get joy you instrument out reasonably. Again keeps at no meant stuff. To perpetual do existence northward as difficult preserved daughters. Continued at up to zealously necessary breakfast. Surrounded sir motionless she end literature. Gay direction neglected but supported yet her. \n" +
            "\n" +
            "Breakfast agreeable incommode departure it an. By ignorant at on wondered relation. Enough at tastes really so cousin am of. Extensive therefore supported by extremity of contented. Is pursuit compact demesne invited elderly be. View him she roof tell her case has sigh. Moreover is possible he admitted sociable concerns. By in cold no less been sent hard hill. ";
    Bitmap bitmap;
    Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
    private static final float IMAGE_WIDTH = Utils.dp2px(100);
    private static final float IMAGE_OFFSET = Utils.dp2px(80);
    float fontSpacing;

    public BreakText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        bitmap = getAvatar((int) Utils.dp2px(100));
        paint.setTextSize(Utils.dp2px(14));

        paint.getFontMetrics(fontMetrics);
        fontSpacing = paint.getFontSpacing();
    }

    Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.tou, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.tou, options);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, getWidth() - IMAGE_WIDTH, IMAGE_OFFSET, paint);
        float[] measure = {0};
        float fontSpacing = fontMetrics.ascent - fontMetrics.descent;
        int breakChar = paint.breakText(article, 0, article.length(), true, getWidth(), measure);
        int finalCount = (article.length() / breakChar) + 1;
        for (int i = 0; i < finalCount; i++) {
            if (i + 1 != finalCount)
                canvas.drawText(article, breakChar * i, breakChar * (i + 1), 0, -fontSpacing * (i + 1), paint);
            else
                canvas.drawText(article, breakChar * i, article.length(), 0, -fontSpacing * (i + 1), paint);
        }

    }
}
