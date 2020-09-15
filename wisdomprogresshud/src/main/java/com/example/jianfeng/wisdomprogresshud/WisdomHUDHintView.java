package com.example.jianfeng.wisdomprogresshud;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.example.jianfeng.wisdomprogresshud.WisdomProgressHUD.CurrentHUDStyleState;
import static com.example.jianfeng.wisdomprogresshud.WisdomProgressHUD.CurrentHUDTextSize;


class WisdomHUDHintView extends RelativeLayout {

    private WisdomScreenUtils screenUtils = new WisdomScreenUtils();

    private RelativeLayout mCoverLayout;

    private WisdomHUDRoundView mHudRoundView;

    private TextView mTextView;

    private ImageView mImageView;

    // top，bottom 边离
    private final int TBEdgeDistance = 11;

    // left，right 边距
    private final int LREdgeDistance = 14;

    // 文字，图片间距
    private final int TextImageEdgeDistance = 7;

    // 字体大小
    private final int TextMinSize = 10;

    private final int TextMaxSize = 18;

    private final int BaseImageSize = 22;

    private final int HUDMinWidth = 92;    // 最小宽度

    private final int HUDMaxWidth = (int) screenUtils.getScreenWidthDp(getContext())/3*2+20; // 最大宽度


    public WisdomHUDHintView(Context context) {
        super(context);

        setClickable(true);

        setFocusable(true);

        setOnClickListener(null);

        setupUI();
    }


    @SuppressLint("ResourceType")
    private void setupUI(){

        mTextView = new TextView(getContext());
        mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        mTextView.setId(285);
        mTextView.setText(" ");

        int size = CurrentHUDTextSize;
        if (size > TextMaxSize){
            size = TextMaxSize;
        }else if(size < TextMinSize){
            size = TextMinSize;
        }

        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        mTextView.setTextColor(Color.parseColor("#000000"));
        Paint mp = new Paint();        // 字体
        mp.setTypeface(Typeface.DEFAULT_BOLD);
        //mTextView.setBackgroundColor(Color.GREEN);

        mImageView = new ImageView(getContext());
        mImageView.setId(286);
        mImageView.setImageResource(R.mipmap.wisdom_succee);
        //mImageView.setBackgroundColor(Color.GREEN);

        mCoverLayout = new RelativeLayout(getContext());
        mCoverLayout.setBackgroundColor(Color.parseColor("#73000000"));
        RelativeLayout.LayoutParams containerParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mCoverLayout, containerParams);

        mHudRoundView = new WisdomHUDRoundView(getContext());
        RelativeLayout.LayoutParams hudRoundParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        hudRoundParams.addRule(RelativeLayout.ALIGN_TOP, mImageView.getId());
        hudRoundParams.addRule(RelativeLayout.ALIGN_BOTTOM, mTextView.getId());
        hudRoundParams.addRule(RelativeLayout.ALIGN_LEFT, mTextView.getId());
        hudRoundParams.addRule(RelativeLayout.ALIGN_RIGHT, mTextView.getId());
        hudRoundParams.rightMargin = screenUtils.dip2px(getContext(), -LREdgeDistance);
        hudRoundParams.leftMargin = screenUtils.dip2px(getContext(), -LREdgeDistance);
        hudRoundParams.bottomMargin = screenUtils.dip2px(getContext(), -TBEdgeDistance);
        hudRoundParams.topMargin = screenUtils.dip2px(getContext(), -TBEdgeDistance);
        addView(mHudRoundView, hudRoundParams);

        // textView
        RelativeLayout.LayoutParams textViewParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        textViewParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(mTextView, textViewParams);

        // imageView
        RelativeLayout.LayoutParams imageLayoutParams = new LayoutParams(screenUtils.dip2px(getContext(), BaseImageSize),screenUtils.dip2px(getContext(), BaseImageSize));
        imageLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        imageLayoutParams.addRule(RelativeLayout.ABOVE, mTextView.getId());
        imageLayoutParams.bottomMargin = screenUtils.dip2px(getContext(), TextImageEdgeDistance);
        addView(mImageView, imageLayoutParams);

        setItemColor();
    }


    public void update(int showState, String text){
        setItemColor();

        int size = CurrentHUDTextSize;
        if (size > TextMaxSize){
            size = TextMaxSize;
        }else if(size < TextMinSize){
            size = TextMinSize;
        }

        mTextView.setText("");
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);

        // mTextView
        if (text != null || text.length()>0){
            mTextView.setVisibility(VISIBLE);
            mTextView.setText(text);
        }

        Rect mr = new Rect();
        String str = mTextView.getText().toString();
        mTextView.getPaint().getTextBounds(str, 0, str.length(), mr);

        float textWidth = mr.width();
        textWidth = screenUtils.px2dip(getContext(), textWidth)+10;

        RelativeLayout.LayoutParams mTextParams = (LayoutParams) mTextView.getLayoutParams();
        if (textWidth + LREdgeDistance*2 < HUDMaxWidth){
            if (textWidth + LREdgeDistance*2 < HUDMinWidth){
                mTextParams.width = screenUtils.dip2px(getContext(), HUDMinWidth-LREdgeDistance*2);
            }else {
                mTextParams.width = screenUtils.dip2px(getContext(), textWidth+4);
            }
        }else {
            mTextParams.width = screenUtils.dip2px(getContext(), HUDMaxWidth-LREdgeDistance*2);
        }

        // mImageView
        RelativeLayout.LayoutParams imageLayoutParams = (LayoutParams) mImageView.getLayoutParams();

        switch (showState) {
            case WisdomHUDStatus.Succee:
            case WisdomHUDStatus.Error:
            case WisdomHUDStatus.Warning:
                if (showState == WisdomHUDStatus.Succee){
                    mImageView.setImageResource(R.mipmap.wisdom_succee);
                }else if (showState == WisdomHUDStatus.Error){
                    mImageView.setImageResource(R.mipmap.wisdom_failure);
                }else if (showState == WisdomHUDStatus.Warning){
                    mImageView.setImageResource(R.mipmap.wisdom_warning);
                }

                mImageView.clearAnimation();
                imageLayoutParams.width = screenUtils.dip2px(getContext(), BaseImageSize);
                imageLayoutParams.height = screenUtils.dip2px(getContext(), BaseImageSize);
                imageLayoutParams.bottomMargin = screenUtils.dip2px(getContext(), TextImageEdgeDistance);
                mImageView.setLayoutParams(imageLayoutParams);
                break;
            case WisdomHUDStatus.Loading:
                imageAnimation();
                imageLayoutParams.width = screenUtils.dip2px(getContext(), BaseImageSize);
                imageLayoutParams.height = screenUtils.dip2px(getContext(), BaseImageSize);
                imageLayoutParams.bottomMargin = screenUtils.dip2px(getContext(), TextImageEdgeDistance);

                mImageView.setImageResource(R.mipmap.wisdom_loading);
                mImageView.setLayoutParams(imageLayoutParams);
                break;
            case WisdomHUDStatus.Loading_Rotate:
                imageAnimation();
                imageLayoutParams.width = screenUtils.dip2px(getContext(), BaseImageSize);
                imageLayoutParams.height = screenUtils.dip2px(getContext(), BaseImageSize);
                imageLayoutParams.bottomMargin = screenUtils.dip2px(getContext(), TextImageEdgeDistance);

                mImageView.setImageResource(R.mipmap.wisdom_loading);
                mImageView.setLayoutParams(imageLayoutParams);

                mTextView.setTextColor(Color.parseColor("#00000000"));
                mHudRoundView.setBackgroundColor(0x00ffffff);
                break;
            default:
                mImageView.clearAnimation();
                imageLayoutParams.width = 0;
                imageLayoutParams.height = 0;
                imageLayoutParams.bottomMargin = 0;
                mImageView.setLayoutParams(imageLayoutParams);
                break;
        }
    }


    private void setItemColor(){
        if (CurrentHUDStyleState == WisdomHUDStyleStatus.WisdomHUDStyle_White){
            mHudRoundView.setBackgroundColor(0xA6ffffff);
            mTextView.setTextColor(Color.parseColor("#000000"));
        }else if (CurrentHUDStyleState == WisdomHUDStyleStatus.WisdomHUDStyle_Black){
            mHudRoundView.setBackgroundColor(0xA6000000);
            mTextView.setTextColor(Color.parseColor("#ffffff"));
        }else {
            mHudRoundView.setBackgroundColor(0xA6000000);
            mTextView.setTextColor(Color.parseColor("#ffffff"));
        }
    }


    private void imageAnimation(){
        /** 旋转动画 */
        Animation rotate = AnimationUtils.loadAnimation(getContext(), R.anim.wisdow_rotate_anim);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        mImageView.startAnimation(rotate);
    }

}
