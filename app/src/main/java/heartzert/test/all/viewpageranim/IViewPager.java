package heartzert.test.all.viewpageranim;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

/**
 * Created by heartzert on 2019/4/4.
 * Email: heartzert@163.com
 */
public class IViewPager extends ViewPager {

    Context mContext;

    float lastPointX = 0;

    float lastPointY = 0;

    public IViewPager(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public IViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        GestureDetector mGestureDetector = new GestureDetector(mContext, new OnGestureListener() {
            @Override
            public boolean onDown(final MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(final MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(final MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(final MotionEvent e1, final MotionEvent e2, final float distanceX,
                    final float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(final MotionEvent e) {

            }

            @Override
            public boolean onFling(final MotionEvent e1, final MotionEvent e2, final float velocityX,
                    final float velocityY) {
                return false;
            }
        });
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastPointX = ev.getX();
                lastPointY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(ev);
    }
}
