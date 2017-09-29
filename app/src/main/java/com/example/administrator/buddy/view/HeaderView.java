package com.example.administrator.buddy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.administrator.buddy.R;

/**
 * Created by zhuj on 2017/6/8.
 */
public class HeaderView extends LinearLayout {

  @BindView(R.id.text_header_back) TextView mTextHeaderBack;//返回按钮文字
  @BindView(R.id.image_header_back) ImageView mImageHeaderBack;//返回按钮图片
  @BindView(R.id.layout_header_back) FrameLayout mFrameLayoutHeaderBack;
  @BindView(R.id.layout_header_right) FrameLayout mFramelayoutHeaderRight;
  @BindView(R.id.text_header_right) TextView mTextHeaderRight;
  @BindView(R.id.image_header_right) ImageView mImageHeaderRight;
  @BindView(R.id.text_header_title) TextView mTextHeaderTitle;
  @BindView(R.id.rel_header) RelativeLayout mRelHeader;
  private Context mContext;
  private boolean mShowRight;
  private boolean mShowBack;
  private int mTitleResId;
  private int mRightResId;
  private int mLeftResId;
  private String mActivityTitleString;//标题文字
  private String mRightString;//右边文字
  private String mLeftString;//左边文字
  public HeaderView(Context context) {
    super(context);
    init();
  }

  public HeaderView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mContext = context;
    getAttrs(context, attrs);
    init();
  }

  private void init() {
    View view = LayoutInflater.from(mContext).inflate(R.layout.view_header, this);
    //以下两句的顺序不能调换，要先addView，然后才能通过findViewById找到该TextView
    ButterKnife.bind(view);
    mTextHeaderTitle.setText(mActivityTitleString);
    //mTextHeaderTitle.setTextColor(getResources().getColor(R.color.white));
    setShowRight(mShowRight);
    setShowBack(mShowBack);
    if (mTitleResId != 0) {
      mTextHeaderTitle.setBackgroundResource(mTitleResId);
      mImageHeaderRight.setVisibility(GONE);
    }
    if (mRightResId != 0) {
      mImageHeaderRight.setBackgroundResource(mRightResId);
      mTextHeaderRight.setVisibility(GONE);
    }
    if (mLeftResId != 0) {
      mImageHeaderBack.setBackgroundResource(mLeftResId);
    }
    if (mLeftString != null) {
      mImageHeaderBack.setVisibility(GONE);
      mTextHeaderBack.setText(mLeftString);
    }
    mTextHeaderRight.setText(mRightString);
  }

  /**
   * get attrs
   */
  private void getAttrs(Context context, AttributeSet attrs) {
    TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HeaderView);
    mActivityTitleString = ta.getString(R.styleable.HeaderView_titleText);
    mRightString = ta.getString(R.styleable.HeaderView_rightText);
    mLeftString = ta.getString(R.styleable.HeaderView_leftText);
    mShowRight = ta.getBoolean(R.styleable.HeaderView_showRight, false);
    mShowBack = ta.getBoolean(R.styleable.HeaderView_showBack, false);
    mTitleResId = ta.getResourceId(R.styleable.HeaderView_titleIcon, 0);
    mLeftResId = ta.getResourceId(R.styleable.HeaderView_leftIcon, 0);
    mRightResId = ta.getResourceId(R.styleable.HeaderView_rightIcon, 0);
    ta.recycle();
  }

  public TextView getTextHeaderRight() {
    return mTextHeaderRight;
  }

  public ImageView getHeaderRightImageView() {
    return mImageHeaderRight;
  }


  public void setHideBackGround(){
    mRelHeader.setBackgroundColor(Color.TRANSPARENT);
  }
  /**
   * 设置title
   */
  public void setTitle(String title) {
    mTextHeaderTitle.setText(title);
  }

  /**
   * 设置title
   */
  public void setTitle(int title) {
    mTextHeaderTitle.setText(title);
  }

  //设置返回文字
  public void setBackText(int title) {
    mTextHeaderBack.setText(title);
    mImageHeaderBack.setVisibility(GONE);
  }  //设置返回文字

  public void setBackText(String title) {
    mTextHeaderBack.setText(title);
    mImageHeaderBack.setVisibility(GONE);
  }

  public void setRightText(String title) {
    mTextHeaderRight.setText(title);
    mTextHeaderRight.setVisibility(VISIBLE);
    mImageHeaderRight.setVisibility(GONE);
  }

  public void setRightText(int title) {
    mTextHeaderRight.setText(title);
    mTextHeaderRight.setVisibility(VISIBLE);
    mImageHeaderRight.setVisibility(GONE);
  }

  public void setRightIcon(int resId) {
    mTextHeaderRight.setVisibility(GONE);
    mImageHeaderRight.setImageResource(resId);
    mImageHeaderRight.setVisibility(VISIBLE);
  }

  public void setTextHeaderRightVisibility(int visibility) {
    mTextHeaderRight.setVisibility(visibility);
  }

  public void setTextHeaderBackVisibility(int visibility) {
    mTextHeaderBack.setVisibility(visibility);
  }

  public void setLayoutHeaderBackVisibility(int visibility) {
    mFrameLayoutHeaderBack.setVisibility(visibility);
  }

  public void setLayoutHeaderRightVisibility(int visibility) {
    mFramelayoutHeaderRight.setVisibility(visibility);
  }

  public void setShowBack(boolean backShow) {
    mShowBack = backShow;
    if (mShowBack) {
      mFrameLayoutHeaderBack.setVisibility(VISIBLE);
    } else {
      mFrameLayoutHeaderBack.setVisibility(View.INVISIBLE);
    }
  }

  public void setShowRight(boolean rightShow) {
    mShowRight = rightShow;
    if (mShowRight) {
      mFramelayoutHeaderRight.setVisibility(VISIBLE);
    } else {
      mFramelayoutHeaderRight.setVisibility(GONE);
    }
  }
}
