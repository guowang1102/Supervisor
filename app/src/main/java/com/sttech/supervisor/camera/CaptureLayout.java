package com.sttech.supervisor.camera;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sttech.supervisor.camera.lisenter.CaptureLisenter;
import com.sttech.supervisor.camera.lisenter.ReturnLisenter;
import com.sttech.supervisor.camera.lisenter.TypeLisenter;


/**
 * create by CJT2325
 * 445263848@qq.com.
 */

public class CaptureLayout extends RelativeLayout {

    private CaptureLisenter captureLisenter;
    private TypeLisenter typeLisenter;
    private ReturnLisenter returnLisenter;

    public void setTypeLisenter(TypeLisenter typeLisenter) {
        this.typeLisenter = typeLisenter;
    }

    public void setCaptureLisenter(CaptureLisenter captureLisenter) {
        this.captureLisenter = captureLisenter;
    }

    public void setReturnLisenter(ReturnLisenter returnLisenter) {
        this.returnLisenter = returnLisenter;
    }

    private CaptureButton btn_capture;
    private TypeButton btn_confirm;
    private TypeButton btn_cancel;
    private ReturnButton btn_return;
    private TextView txt_tip;

    private int layout_width;
    private int layout_height;
    private int button_size;

    public CaptureLayout(Context context) {
        this(context, null);
    }

    public CaptureLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CaptureLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //get width
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        layout_width = outMetrics.widthPixels;
        button_size = (int) (layout_width / 4.5f);
        layout_height = button_size + (button_size / 5) * 2 + 80;

        initView();
        initEvent();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(layout_width, layout_height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void initEvent() {
        btn_cancel.setVisibility(INVISIBLE);
        btn_confirm.setVisibility(INVISIBLE);
    }

    private void startTypeBtnAnimator() {

        btn_capture.setVisibility(INVISIBLE);
        btn_return.setVisibility(INVISIBLE);
        btn_cancel.setVisibility(VISIBLE);
        btn_confirm.setVisibility(VISIBLE);
        btn_cancel.setClickable(false);
        btn_confirm.setClickable(false);
        ObjectAnimator animator_cancel = ObjectAnimator.ofFloat(btn_cancel, "translationX", layout_width / 4, 0);
        animator_cancel.setDuration(200);
        animator_cancel.start();
        ObjectAnimator animator_confirm = ObjectAnimator.ofFloat(btn_confirm, "translationX", -layout_width / 4, 0);
        animator_confirm.setDuration(200);
        animator_confirm.start();
        animator_confirm.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                btn_cancel.setClickable(true);
                btn_confirm.setClickable(true);
            }
        });
    }

    private boolean isFirst = true;

    private void startAlphaAnimation() {
        if (isFirst) {
            ObjectAnimator animator_txt_tip = ObjectAnimator.ofFloat(txt_tip, "alpha", 1f, 0f);
            animator_txt_tip.setDuration(500);
            animator_txt_tip.start();
            isFirst = false;
        }
    }

    private void initView() {
        setWillNotDraw(false);

        /**
         * btn_capture
         */
        btn_capture = new CaptureButton(getContext(), button_size);
        LayoutParams btn_capture_param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        btn_capture_param.addRule(CENTER_IN_PARENT, TRUE);
        btn_capture_param.setMargins(0, 152, 0, 0);
        btn_capture.setLayoutParams(btn_capture_param);
        btn_capture.setDuration(5000);
        btn_capture.setCaptureLisenter(new CaptureLisenter() {
            @Override
            public void takePictures() {
                if (captureLisenter != null) {
                    captureLisenter.takePictures();
                }
                startAlphaAnimation();
                startTypeBtnAnimator();
            }

            @Override
            public void recordShort(long time) {
                if (captureLisenter != null) {
                    captureLisenter.recordShort(time);
                }
                startAlphaAnimation();
            }

            @Override
            public void recordStart() {
                if (captureLisenter != null) {
                    captureLisenter.recordStart();
                }
                startAlphaAnimation();
            }

            @Override
            public void recordEnd(long time) {
                if (captureLisenter != null) {
                    captureLisenter.recordEnd(time);
                }
                startAlphaAnimation();
                startTypeBtnAnimator();
            }

            @Override
            public void recordZoom(float zoom) {
                if (captureLisenter != null) {
                    captureLisenter.recordZoom(zoom);
                }
            }
        });

        /**
         * btn_cancel
         */
        btn_cancel = new TypeButton(getContext(), TypeButton.TYPE_CANCEL, button_size);
        final LayoutParams btn_cancel_param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        btn_cancel_param.addRule(CENTER_VERTICAL, TRUE);
        btn_cancel_param.addRule(ALIGN_PARENT_LEFT, TRUE);
        btn_cancel_param.setMargins((layout_width / 4) - button_size / 2, 0, 0, 0);
        btn_cancel.setLayoutParams(btn_cancel_param);
        btn_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeLisenter != null) {
                    typeLisenter.cancel();
                }
                startAlphaAnimation();
                btn_cancel.setVisibility(INVISIBLE);
                btn_confirm.setVisibility(INVISIBLE);
                btn_capture.setVisibility(VISIBLE);
                btn_return.setVisibility(VISIBLE);
            }
        });

        /**
         * btn_confirm
         */
        btn_confirm = new TypeButton(getContext(), TypeButton.TYPE_CONFIRM, button_size);
        LayoutParams btn_confirm_param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        btn_confirm_param.addRule(CENTER_VERTICAL, TRUE);
        btn_confirm_param.addRule(ALIGN_PARENT_RIGHT, TRUE);
        btn_confirm_param.setMargins(0, 0, (layout_width / 4) - button_size / 2, 0);
        btn_confirm.setLayoutParams(btn_confirm_param);
        btn_confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeLisenter != null) {
                    typeLisenter.confirm();
                }
                startAlphaAnimation();  //TODO 需要动画
                btn_cancel.setVisibility(INVISIBLE);
                btn_confirm.setVisibility(INVISIBLE);
                btn_capture.setVisibility(VISIBLE);
                btn_return.setVisibility(VISIBLE);
            }
        });

        /**
         * btn_return
         */
        btn_return = new ReturnButton(getContext(), button_size / 2);
        LayoutParams btn_return_param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        btn_return_param.addRule(CENTER_VERTICAL, TRUE);
        btn_return_param.setMargins(layout_width / 6, 0, 0, 0);
        btn_return.setLayoutParams(btn_return_param);
        btn_return.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (captureLisenter != null) {
                    if (returnLisenter != null) {
                        returnLisenter.onReturn();
                    }
                }
            }
        });

        /**
         * txt_tip
         */
        txt_tip = new TextView(getContext());
        LayoutParams txt_param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        txt_param.setMargins(0, 0, 0, 0);
        txt_tip.setText("轻触拍照，长按摄像");
        txt_tip.setTextColor(0xFFFFFFFF);
        txt_tip.setGravity(Gravity.CENTER);
        txt_tip.setLayoutParams(txt_param);

        this.addView(btn_capture);
        this.addView(btn_cancel);
        this.addView(btn_confirm);
        this.addView(btn_return);
//        this.addView(txt_tip);

    }

    public void setTextWithAnimation() {
        txt_tip.setText("录制时间过短");
        ObjectAnimator animator_txt_tip = ObjectAnimator.ofFloat(txt_tip, "alpha", 0f, 1f, 1f, 0f);
        animator_txt_tip.setDuration(3000);
        animator_txt_tip.start();
    }

    public void setDuration(int duration) {
        btn_capture.setDuration(duration);
    }

}
