package com.lq.view.widgets;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.lq.view.R;
import com.lq.view.utils.Utils;

/**
 * @Description:
 * @author: mick
 * @CreateAt: 6/21/21 3:15 PM
 *
 * @UpdateUser: mick
 * @UpdateDate: 6/21/21 3:15 PM
 * @UpdateRemark:
 */
public class FloatingLabelEditText extends androidx.appcompat.widget.AppCompatEditText {
    public static final float TEXT_SIZE = Utils.dp2Px(12);
    public static final float TEXT_MARGIN = Utils.dp2Px(8);
    public static final float TEXT_VER_OFFSET = Utils.dp2Px(20);
    public static final float TEXT_HOR_OFFSET = Utils.dp2Px(5);
    public static final float ANIMATOR_VER_OFFSET = Utils.dp2Px(15);
    public static final String TEXT_COLOR = "#333333";

    private Paint paint;
    private float floatingProgress;
    private ObjectAnimator floatingAnimator;
    private boolean isFloatingShown;
    private boolean floatingEnable;
    private Rect backgroundPadding = new Rect();

    public FloatingLabelEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FloatingLabelEditText);
        floatingEnable = typedArray.getBoolean(R.styleable.FloatingLabelEditText_floatingEnable, false);
        typedArray.recycle();
        init();
    }


    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(TEXT_SIZE);
        paint.setColor(Color.parseColor(TEXT_COLOR));

        onFloatingEnableChange();

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (floatingEnable) {
                    if (isFloatingShown && TextUtils.isEmpty(s)) {
                        isFloatingShown = false;
                        getAnimator().reverse();
                    } else if (!isFloatingShown && !TextUtils.isEmpty(s)) {
                        isFloatingShown = true;
                        getAnimator().start();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void onFloatingEnableChange() {
        getBackground().getPadding(backgroundPadding);
        if (floatingEnable) {
            setPadding(getPaddingLeft(), (int) (backgroundPadding.top + TEXT_SIZE + TEXT_MARGIN), getPaddingRight(), getPaddingBottom());
        } else {
            setPadding(getPaddingLeft(), backgroundPadding.top, getPaddingRight(), getPaddingBottom());
        }
    }

    public ObjectAnimator getAnimator() {
        if (floatingAnimator == null) {
            floatingAnimator = ObjectAnimator.ofFloat(this, "floatingProgress", 0, 1);
        }
        return floatingAnimator;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAlpha((int) (0xff * floatingProgress));
        String hint = getHint().toString();

        float extraOffset = ANIMATOR_VER_OFFSET * (1 - floatingProgress);
        canvas.drawText(hint, TEXT_HOR_OFFSET, TEXT_VER_OFFSET + extraOffset, paint);

    }

    public void setFloatingProgress(float floatingProgress) {
        this.floatingProgress = floatingProgress;
        invalidate();
    }

    public void setFloatingEnable(boolean floatingEnable) {
        if (floatingEnable != this.floatingEnable) {
            this.floatingEnable = floatingEnable;
            onFloatingEnableChange();
        }
    }
}
