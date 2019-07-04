package com.example.biyesdemo.RecyclerView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecycleViewDivider extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private Drawable mDivider;
    private int mDividerHeight = 2;
    private int mOrientation;
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    /**
     * 默认分割线，高度为2px，颜色为灰色
     */
    public RecycleViewDivider(Context context, int oritentation) {
        if (oritentation != LinearLayoutManager.VERTICAL && oritentation != LinearLayoutManager.HORIZONTAL) {
            try {
                throw new IllegalAccessException("请输入正确参数!");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        mOrientation = oritentation;
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    /**
     * 自定义分割线
     *
     * @param context
     * @param orientation，方向
     * @param drawableId，分割线图片
     */
    public RecycleViewDivider(Context context, int orientation, int drawableId) {
        this(context, orientation);
        mDivider = ContextCompat.getDrawable(context, drawableId);
        mDividerHeight = mDivider.getIntrinsicHeight();
    }

    /**
     * 自定义分割线
     *
     * @param context
     * @param orientation
     * @param dividerHeight
     * @param dividerColor
     */
    public RecycleViewDivider(Context context, int orientation, int dividerHeight, int dividerColor) {
        this(context, orientation);
        mDividerHeight = dividerHeight;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * 获取分割线尺寸
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, mDividerHeight);
    }

    //绘制分割线
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHprizontal(c, parent);
        }
    }

    //绘制横向分割线
    private void drawHprizontal(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int buttom = top + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, buttom);
                mDivider.draw(c);
            } else if (mPaint != null) {
                c.drawRect(left, top, right, buttom, mPaint);
            }
        }
    }

    //绘制竖向分割线
    private void drawVertical(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int buttom = parent.getMeasuredHeight() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, buttom);
                mDivider.draw(c);
            } else if (mPaint != null) {
                c.drawRect(left, top, right, buttom, mPaint);
            }
        }


    }
}
