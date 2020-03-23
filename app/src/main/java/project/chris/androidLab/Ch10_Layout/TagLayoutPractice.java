package project.chris.androidLab.Ch10_Layout;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TagLayoutPractice extends ViewGroup {

    List<Rect> children = new ArrayList<>();

    public TagLayoutPractice(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = getChildCount();
        for (int i = 0; i < size; i++) {
            View child = getChildAt(i);
            Rect bound = new Rect();
            if (i == 0) {
                bound.set(0, 0, 200, 200);
            } else {
                bound.set(0, 200, 200, 400);
            }
            children.set(i, bound);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int size = getChildCount();
        for (int i = 0; i < size; i++) {
            View child = getChildAt(i);
            Rect bound = children.get(i);
            child.layout(bound.left, bound.top, bound.right, bound.bottom);
        }
    }
}
