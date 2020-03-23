package project.chris.androidLab.Ch09_BitmapDrawable_MaterialEditText;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import project.chris.androidLab.Utils;

public class MaterialEditText extends android.support.v7.widget.AppCompatEditText {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float TEXT_HORIZONTAL_SPACING = Utils.dp2px(6);
    private float TEXT_VERTICAL_SPACING = Utils.dp2px(14);
    private float TEXT_SIZE = Utils.dp2px(10);
    private float floatingSpace;
    private int TEXT_ANIMATION_OFFSET = (int) Utils.dp2px(14);
    private boolean isFloatingShown;

    public MaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setTextSize(TEXT_SIZE);
    }

    {
        setPadding(getPaddingLeft(), (int) Utils.dp2px(20), getPaddingRight(), getPaddingBottom());
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isFloatingShown && TextUtils.isEmpty(s)) {
                    isFloatingShown = false;
                    ObjectAnimator animator = ObjectAnimator.ofFloat(MaterialEditText.this, "floatingSpace", 0);
                    animator.start();
                } else if (!isFloatingShown && !TextUtils.isEmpty(s)) {
                    isFloatingShown = true;
                    ObjectAnimator animator = ObjectAnimator.ofFloat(MaterialEditText.this, "floatingSpace", 1);
                    animator.start();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAlpha((int) (0xff * floatingSpace));
        float extraOffset = TEXT_ANIMATION_OFFSET * (1 - floatingSpace);
        canvas.drawText(getHint().toString(), TEXT_HORIZONTAL_SPACING, TEXT_VERTICAL_SPACING + extraOffset, paint);
    }

    public float getFloatingSpace() {
        return floatingSpace;
    }

    public void setFloatingSpace(float floatingSpace) {
        this.floatingSpace = floatingSpace;
        invalidate();
    }
}
