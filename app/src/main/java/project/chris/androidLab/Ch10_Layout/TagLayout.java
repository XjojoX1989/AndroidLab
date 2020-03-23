package project.chris.androidLab.Ch10_Layout;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TagLayout extends ViewGroup {

    List<Rect> childrenBounds = new ArrayList<>();

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthUsed = 0;
        int heightUsed = 0;
        int lineMaxHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {//呼叫getChildCount可以拿到子View數目
            View child = getChildAt(i);//呼叫getChildAt可以拿到每個子View
            //measureChildWithMargins可以把我們對view的要求和父View的要求做出計算
            //注意：此方法內需要帶Margin的LayoutParams, 所以我們需要override generateLayoutParams(AttributeSet attrs)
            //這樣一來就可以得到Margin了(這就是格式)
            measureChildWithMargins(child, widthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed);
            Rect childBound;
            if (childrenBounds.size() <= i) {
                childBound = new Rect();
                childrenBounds.add(childBound);
            } else {
                childBound = childrenBounds.get(i);
            }
            //如果width超過螢幕標界則需要換行，換行的高度需要超過先前的最大高度
            if (widthUsed + child.getMeasuredWidth() > MeasureSpec.getSize(widthMeasureSpec)) {
                widthUsed = 0;
                heightUsed = lineMaxHeight;

            }
            childBound.set(widthUsed, heightUsed, widthUsed + child.getMeasuredWidth(), heightUsed + child.getMeasuredHeight());
            widthUsed += child.getMeasuredWidth();
            lineMaxHeight =  Math.max(lineMaxHeight, child.getMeasuredHeight());
        }

        int width = widthUsed;//就是最後用的寬度
        int height = lineMaxHeight;//就是最高的高度
        setMeasuredDimension(width, height);

    }

    //onLayout拿到onMeasure存下來的位置
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {//呼叫getChildCount可以拿到子View數目
            View child = getChildAt(i);//呼叫getChildAt可以拿到每個子View
            Rect childBound = childrenBounds.get(i);
            child.layout(childBound.left, childBound.top, childBound.right, childBound.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
