package com.codeogenic.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.codeogenic.opensources.R;

/**
 * Created by clem_gumbs on 2/17/17.
 */

public class CollapsingLayout extends LinearLayout {

    private static final int DEFAULT_ANIM_DURATION = 500;
    private static final String TAG = CollapsingLayout.class.getSimpleName();

    private final int mHandleId;
    private final int mContentContainerId;
    private final int mContentId;
    private int mTitleId;

    private View mHandle;
    private View mContentContainer;
    private View mContent;
    private View mTitle;

    private boolean initExpand;
    private boolean mExpanded = false;
    private boolean mFirstOpen = true;
    private boolean isAnimation = false;

    private int mCollapsedHeight;
    private int mContentHeight;
    private int mContentWidth;
    private int mAnimationDuration = 0;

    private OnCollapseListener mListener;
    private float duration;

    public CollapsingLayout(Context context) {
        this(context, null);

    }


    public CollapsingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CollapsingLayout, 0, 0);

        mAnimationDuration = a.getInteger(R.styleable.CollapsingLayout_animationDuration, DEFAULT_ANIM_DURATION);

        int handleId = a.getResourceId(R.styleable.CollapsingLayout_handle, 0);
        if (!isInEditMode() && handleId == 0) {
            throw new IllegalArgumentException(
                    "The handle attribute is required and must refer to a valid child.");
        }

        int contentContainerId = a.getResourceId(R.styleable.CollapsingLayout_contentContainer, 0);
        if (!isInEditMode() && contentContainerId == 0) {
            throw new IllegalArgumentException("The content attribute is required and must refer to a valid child.");
        }

        int titleId = a.getResourceId(R.styleable.CollapsingLayout_titleHeader, 0);


        int contentId = a.getResourceId(R.styleable.CollapsingLayout_content, 0);
        if (!isInEditMode() && contentId == 0) {
            throw new IllegalArgumentException("The content attribute is required and must refer to a valid child.");
        }

       initExpand = a.getBoolean(R.styleable.CollapsingLayout_expand, false);

        mHandleId = handleId;
        mContentContainerId = contentContainerId;
        mContentId = contentId;
        mTitleId = titleId;

        init();

        a.recycle();
    }

    private void init() {
        this.setOrientation(VERTICAL);
        LayoutParams containerParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(containerParams);
        this.setGravity(Gravity.CENTER);
        this.setClickable(true);
        this.setFocusable(true);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mHandle = findViewById(mHandleId);
        if (!isInEditMode() && mHandle == null) {
            throw new IllegalArgumentException("The handle attribute is must refer to an existing child.");
        }

        mContentContainer = findViewById(mContentContainerId);
        if (!isInEditMode() && mContentContainer == null) {
            throw new IllegalArgumentException("The content container attribute must refer to an existing child.");
        }

        mContentContainer.getLayoutParams().height = 0;
        /*LayoutParams params = (LayoutParams) mContentContainer.getLayoutParams();
        params.addRule(RelativeLayout.BELOW, mHandle.getId());
        mContentContainer.setLayoutParams(params);*/

        mContent = findViewById(mContentId);
        if (!isInEditMode() && mContent == null) {
            throw new IllegalArgumentException("The content attribute must refer to an existing child.");
        }
        mTitle = findViewById(mTitleId);
        if (!isInEditMode() && mContent == null) {
           // throw new IllegalArgumentException("The content attribute must refer to an existing child.");
        }
        mContent.setVisibility(View.INVISIBLE);
        mHandle.setOnClickListener(new PanelToggler());

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mContentContainer.measure(widthMeasureSpec, heightMeasureSpec);
        mHandle.measure(MeasureSpec.UNSPECIFIED, heightMeasureSpec);
        //mCollapsedHeight = mHandle.getMeasuredHeight();
        mCollapsedHeight = 0;
        mContentWidth = mContentContainer.getMeasuredWidth();
        mContentHeight = mContentContainer.getMeasuredHeight()+20;

        /*mContentHeight = mHandle.getMeasuredHeight()+
                mContent.getMeasuredHeight()+10+
                mContent.getPaddingBottom()+mContent.getPaddingTop();*/

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        if (mFirstOpen) {
//           // mContentContainer.getLayoutParams().width = 0;
//            mContentContainer.getLayoutParams().height = mCollapsedHeight;
//            mFirstOpen = false;
//        }

        if(initExpand) {
            mContentContainer.getLayoutParams().height = mContentHeight;
            mContent.setVisibility(VISIBLE);
            mExpanded = true;
        }


    }

    public void setOnCollapseListener(OnCollapseListener mListener) {
        this.mListener = mListener;
    }

    public long getDuration() {
        return (long) duration;
    }

    private class PanelToggler implements OnClickListener {
        @Override
        public void onClick(View v) {
            if(!isInEditMode())
                Log.w(TAG, "panelToggle clicked");
            toggleExpand();

        }
    }

    private void toggleExpand(){

        if(isAnimation)
            return;

        Animation animation;
        isAnimation = true;
        if (mExpanded) {
            mContent.setVisibility(View.INVISIBLE);
            animation = new CollapseAnimation( mContentHeight, mCollapsedHeight);
            if (mListener != null) {
                mListener.onExpand(mHandle, mContentContainer);
            }
        } else {
            CollapsingLayout.this.invalidate();
            animation = new CollapseAnimation( mCollapsedHeight, mContentHeight);
            if (mListener != null) {
                mListener.onCollapse(mHandle, mContentContainer);
            }
        }

        animation.setDuration(mAnimationDuration);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                mExpanded = !mExpanded;
                if (mExpanded) {
                    mContent.setVisibility(View.VISIBLE);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isAnimation = false;
                    }
                },500);

            }
        });

        mContentContainer.startAnimation(animation);
    }


    private class CollapseAnimation extends Animation {

        private int mStartWidth;
        private int mEndWidth;
        private final int mStartHeight;
        private final int mEndHeight;

        public CollapseAnimation(int startHeight, int endHeight) {

            mStartHeight = startHeight;
            mEndHeight = endHeight - startHeight;

        }

        public CollapseAnimation(int startWidth, int endWidth, int startHeight, int endHeight) {
            mStartWidth = startWidth;
            mEndWidth = endWidth - startWidth;
            mStartHeight = startHeight;
            mEndHeight = endHeight - startHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            android.view.ViewGroup.LayoutParams params = mContentContainer.getLayoutParams();
            //lp.width = (int) (mStartWidth + mEndWidth * interpolatedTime);
            params.height = (int) (mStartHeight + mEndHeight * interpolatedTime);
            mContentContainer.setLayoutParams(params);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }

    public void setExpand(boolean expand) {
        initExpand = expand;

    }
    public boolean isExpanded() {
        return  mExpanded;
    }

    public void toggle(){
        toggleExpand();
    }

    public boolean isAnimating(){
        return isAnimation;
    }

}
