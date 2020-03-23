package project.chris.androidLab.Ch10_Layout;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class SquareImageView extends AppCompatImageView {
    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //此種寫法容易最後layout時parent view容易算錯，因為最終layout是我們自己寫的
//    @Override
//    public void layout(int l, int t, int r, int b) {
//        int width = r - l;
//        int height = b - t;
//        int size = Math.max(width, height);
//
//        super.layout(l, t, l + size, t + size);
//    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);//這裡會算出你的尺寸
        int width = getMeasuredWidth();//拿到由xml計算出的寬
        int height = getMeasuredHeight();//拿到由xml計算出的高
        int squareSize = Math.min(width, height);
        setMeasuredDimension(squareSize, squareSize);
    }
}
