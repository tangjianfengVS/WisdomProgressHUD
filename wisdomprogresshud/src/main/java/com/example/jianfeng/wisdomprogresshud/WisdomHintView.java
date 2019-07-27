package com.example.jianfeng.wisdomprogresshud;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class WisdomHintView extends RelativeLayout {

    private WisdomScreenUtils screenUtils = new WisdomScreenUtils();

    // left，right边距
    private final int EdgeDistance = 12;

    // top，bottom边离
    private final int EdgeDistanceBottom = 11;

    // 文字，图片间距
    private final int BaseImageEdgeDistance = 10;

    private final int BaseWidth = 90;

    private final int BaseHeiht = 32;

    private final int BaseImageSize = 20;

    private TextView textView;

    private ImageView imageView;

    private WisdomHUDRoundView coverView;

    private int showState;

    private String text;

    public float size = 12;


    public WisdomHintView(Context context) {
        super(context);
        coverView = new WisdomHUDRoundView(context);
        coverView.setBackgroundColor(0xA6000000);
        coverView.setId(R.id.coverView);

        RelativeLayout.LayoutParams coverViewLayoutParam = new LayoutParams(BaseWidth,BaseHeiht);
        coverViewLayoutParam.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.addView(coverView,coverViewLayoutParam);

        textView = new TextView(context);
        // 字体
        Paint mp = new Paint();
        mp.setTypeface(Typeface.DEFAULT_BOLD);
        //textView.setBackgroundColor(Color.GREEN);
        this.addView(textView);

        imageView = new ImageView(context);

        RelativeLayout.LayoutParams imageLayoutParam = new LayoutParams(screenUtils.dip2px(getContext(), BaseImageSize),screenUtils.dip2px(getContext(), BaseImageSize));
        imageLayoutParam.addRule(RelativeLayout.ALIGN_TOP,coverView.getId());
        imageLayoutParam.setMargins(0,screenUtils.dip2px(getContext(), EdgeDistanceBottom),0,0);
        imageLayoutParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
        //imageView.setBackgroundColor(Color.GREEN);
        this.addView(imageView,imageLayoutParam);
    }


    public static WisdomHintView init(Context context, int showState, String text) {
        WisdomHintView wisdomBaseView = new WisdomHintView(context);
        wisdomBaseView.showState = showState;
        wisdomBaseView.text = text;
        return wisdomBaseView;
    }


    public void update(int showState, String text){
        this.showState = showState;
        this.text = text;
    }


    public void updateUI(){

        switch (showState) {
            case WisdomProgressHUD.Default:
                imageView.clearAnimation();
                imageView.setVisibility(View.GONE);
                updateText(true);
                break;

            case WisdomProgressHUD.Succee:
            case WisdomProgressHUD.Error:
            case WisdomProgressHUD.Warning:
                imageView.clearAnimation();
                imageView.setVisibility(View.VISIBLE);

                if (showState == WisdomProgressHUD.Succee){
                    imageView.setImageResource(R.mipmap.wisdom_succee);
                }else if (showState == WisdomProgressHUD.Error){
                    imageView.setImageResource(R.mipmap.wisdom_failure);
                }else if (showState == WisdomProgressHUD.Warning){
                    imageView.setImageResource(R.mipmap.wisdom_warning);
                }

                int textHeight = updateText(false);
                updateImage(textHeight);
                break;

            case WisdomProgressHUD.Loading:
                imageView.setImageResource(R.mipmap.wisdom_loading);
                imageView.setVisibility(View.VISIBLE);

                int height = updateText(false);
                updateImage(height);
                imageAnimation();
                break;
        }
    }


    private int updateText(Boolean setCerter){
        textView.setText(text);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,size);

        Rect bounds = new Rect();
        TextPaint paint = textView.getPaint();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int width = screenUtils.px2dip(getContext(), bounds.width());

        RelativeLayout.LayoutParams coverViewLayoutParam = (RelativeLayout.LayoutParams) coverView.getLayoutParams();
        RelativeLayout.LayoutParams textViewLayoutParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        textViewLayoutParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textViewLayoutParam.addRule(RelativeLayout.ALIGN_BOTTOM,coverView.getId());

        if (setCerter){
            if (size + EdgeDistanceBottom *2 > BaseHeiht){
                coverViewLayoutParam.height = (int) (size + EdgeDistanceBottom *2);
            }else{
                coverViewLayoutParam.height = screenUtils.dip2px(getContext(), BaseHeiht);
            }
            textViewLayoutParam.addRule(RelativeLayout.CENTER_IN_PARENT);
        } else {
            textViewLayoutParam.setMargins(0,0,0,screenUtils.dip2px(getContext(), EdgeDistanceBottom));
        }

        textView.setLayoutParams(textViewLayoutParam);

        if (width + EdgeDistance *2 > BaseWidth){
            coverViewLayoutParam.width = screenUtils.dip2px(getContext(), width + EdgeDistance *2);
        }else{
            coverViewLayoutParam.width =  screenUtils.dip2px(getContext(), BaseWidth);
        }
        return (int) size;
    }


    private void updateImage(int textHeight){
        RelativeLayout.LayoutParams coverViewLayoutParam = (RelativeLayout.LayoutParams) coverView.getLayoutParams();
        int height = textHeight + BaseImageSize + EdgeDistanceBottom * 2 + BaseImageEdgeDistance;
        coverViewLayoutParam.height = screenUtils.dip2px(getContext(), height);
    }


    private void imageAnimation(){
        /** 旋转动画 */
        Animation rotate = AnimationUtils.loadAnimation(getContext(), R.anim.wisdow_rotate_anim);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        imageView.startAnimation(rotate);
    }
}
