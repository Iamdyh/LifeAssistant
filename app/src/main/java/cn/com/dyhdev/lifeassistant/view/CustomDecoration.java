package cn.com.dyhdev.lifeassistant.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.view
 * 文件名:     CustomDecoration
 * 作者:       dyh
 * 时间:       2018/2/22 19:29
 * 描述:       自定义RecyclerView分割线
 */

public class CustomDecoration extends RecyclerView.ItemDecoration {

    private Context mContext;
    private Drawable mDivider;
    private int mOrientation;

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;  //水平
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;  //垂直

    //通过获取系统属性中的listDivider来添加，在系统中的AppTheme中设置
    public static final int[] ATRRS = new int[]{android.R.attr.listDivider};

    public CustomDecoration(Context context, int orientation){
        this.mContext = context;
        final TypedArray ta = context.obtainStyledAttributes(ATRRS);
        this.mDivider = ta.getDrawable(0);
        ta.recycle();
        setOrientation(orientation);

    }

    /**
     * 设置屏幕方向
     * @param orientation
     */
    private void setOrientation(int orientation) {
        if(orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST){
            throw  new IllegalArgumentException("illegal orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if(mOrientation == HORIZONTAL_LIST){
            drawVerticalLine(c, parent, state);
        }else{
            drawHorizontalLine(c, parent, state);
        }
    }

    //横线
    private void drawHorizontalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for(int i = 0; i < childCount; i++){
            final View childView = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)childView.getLayoutParams();
            final int top = childView.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }

    }

    //竖线
    private void drawVerticalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        for(int i = 0; i < childCount; i++){
            final View childView = parent.getChildAt(i);
            final RecyclerView.LayoutParams pa = (RecyclerView.LayoutParams)childView.getLayoutParams();
            final int left = childView.getRight() + pa.rightMargin;
            final int right = left + mDivider.getIntrinsicWidth();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(mOrientation == HORIZONTAL_LIST){
            //画横线，往下偏移一个分割线的高度
            outRect.set(0,0,0, mDivider.getIntrinsicHeight());
        }else{
            //画竖线，往右偏移一个分割线的宽度
            outRect.set(0,0,mDivider.getIntrinsicWidth(), 0);
        }
    }
}
