package com.example.anukoolfinal.customview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.CompoundButton;

import com.example.anukoolfinal.R;


/* renamed from: com.security.protector.view.Switch */
public class Switch extends CompoundButton {

    /* renamed from: z */
    private static final int[] f3851z = {16842912};


    private final Drawable f3852a;


    private final Drawable f3853b;


    private final int f3854c;

    
    private final int f3855d;

    /* renamed from: e */
    private CharSequence f3856e;

    /* renamed from: f */
    private CharSequence f3857f;

    /* renamed from: g */
    private int f3858g;

    /* renamed from: h */
    private final int f3859h;

    /* renamed from: i */
    private float f3860i;

    /* renamed from: j */
    private float f3861j;

    /* renamed from: k */
    private final VelocityTracker f3862k;

    /* renamed from: l */
    private final int f3863l;

    /* renamed from: m */
    private float f3864m;

    /* renamed from: n */
    private int f3865n;

    /* renamed from: o */
    private int f3866o;

    /* renamed from: p */
    private int f3867p;

    /* renamed from: q */
    private int f3868q;

    /* renamed from: r */
    private int f3869r;

    /* renamed from: s */
    private int f3870s;

    /* renamed from: t */
    private int f3871t;

    /* renamed from: u */
    private final TextPaint f3872u;

    /* renamed from: v */
    private ColorStateList f3873v;

    /* renamed from: w */
    private Layout f3874w;

    /* renamed from: x */
    private Layout f3875x;

    /* renamed from: y */
    private final Rect f3876y;

    public Switch(Context context) {
        this(context, (AttributeSet) null);
    }

    public Switch(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public Switch(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f3862k = VelocityTracker.obtain();
        this.f3876y = new Rect();
        this.f3872u = new TextPaint(1);
        Resources resources = getResources();
        this.f3872u.density = resources.getDisplayMetrics().density;
        this.f3852a = resources.getDrawable(R.drawable.switch_inner_holo_dark);
        this.f3853b = resources.getDrawable(R.drawable.switch_track_holo_dark);
        this.f3856e = resources.getString(R.string.switch_on);
        this.f3857f = resources.getString(R.string.switch_off);
        this.f3854c = m3435a(50);
        this.f3855d = m3435a(16);
        setSwitchTextAppearance(context);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.f3859h = viewConfiguration.getScaledTouchSlop();
        this.f3863l = viewConfiguration.getScaledMinimumFlingVelocity();
        refreshDrawableState();
        setChecked(isChecked());
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{16842981});
        setClickable(obtainStyledAttributes.getBoolean(0, true));
        obtainStyledAttributes.recycle();
        this.f3873v = new ColorStateList(new int[][]{new int[]{16842912}, new int[]{16842910}}, new int[]{-1, -7829368});
    }


    private int m3435a(int i) {
        return (int) ((getResources().getDisplayMetrics().density * ((float) i)) + 0.5f);
    }

    public void setSwitchTextAppearance(Context context) {
        try {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(16973894, new int[]{16842901, 16842902, 16842903, 16842904});
            ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(3);
            if (colorStateList != null) {
                this.f3873v = colorStateList;
            } else {
                this.f3873v = getTextColors();
            }
            int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(0, 0);
            if (!(dimensionPixelSize == 0 || ((float) dimensionPixelSize) == this.f3872u.getTextSize())) {
                this.f3872u.setTextSize((float) dimensionPixelSize);
                requestLayout();
            }
            m3437a(obtainStyledAttributes.getInt(1, -1), obtainStyledAttributes.getInt(2, -1));
            obtainStyledAttributes.recycle();
        } catch (Exception e) {
            this.f3873v = getTextColors();
            this.f3872u.setTextSize(28.0f);
            m3437a(-1, 0);
        }
    }


    private void m3437a(int i, int i2) {
        Typeface typeface = null;
        switch (i) {
            case 1:
                typeface = Typeface.SANS_SERIF;
                break;
            case 2:
                typeface = Typeface.SERIF;
                break;
            case 3:
                typeface = Typeface.MONOSPACE;
                break;
        }
        mo7953a(typeface, i2);
    }


    public void mo7953a(Typeface typeface, int i) {
        Typeface create;
        int i2;
        float f;
        boolean z = false;
        if (i > 0) {
            if (typeface == null) {
                create = Typeface.defaultFromStyle(i);
            } else {
                create = Typeface.create(typeface, i);
            }
            setSwitchTypeface(create);
            if (create != null) {
                i2 = create.getStyle();
            } else {
                i2 = 0;
            }
            int i3 = (i2 ^ -1) & i;
            TextPaint textPaint = this.f3872u;
            if ((i3 & 1) != 0) {
                z = true;
            }
            textPaint.setFakeBoldText(z);
            TextPaint textPaint2 = this.f3872u;
            if ((i3 & 2) != 0) {
                f = -0.25f;
            } else {
                f = 0.0f;
            }
            textPaint2.setTextSkewX(f);
            return;
        }
        this.f3872u.setFakeBoldText(false);
        this.f3872u.setTextSkewX(0.0f);
        setSwitchTypeface(typeface);
    }

    public void setSwitchTypeface(Typeface typeface) {
        if (this.f3872u.getTypeface() != typeface) {
            this.f3872u.setTypeface(typeface);
            requestLayout();
            invalidate();
        }
    }

    public CharSequence getTextOn() {
        return this.f3856e;
    }

    public void setTextOn(CharSequence charSequence) {
        this.f3856e = charSequence;
        requestLayout();
    }

    public CharSequence getTextOff() {
        return this.f3857f;
    }

    public void setTextOff(CharSequence charSequence) {
        this.f3857f = charSequence;
        requestLayout();
    }

    public void onMeasure(int i, int i2) {
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        if (this.f3874w == null) {
            this.f3874w = m3436a(this.f3856e);
        }
        if (this.f3875x == null) {
            this.f3875x = m3436a(this.f3857f);
        }
        this.f3852a.getPadding(this.f3876y);
        int i3 = this.f3854c;
        int intrinsicHeight = this.f3852a.getIntrinsicHeight() + m3435a(3);
        this.f3867p = intrinsicHeight;
        switch (mode) {
            case Integer.MIN_VALUE:
                Math.min(size, i3);
                break;
        }
        switch (mode2) {
            case Integer.MIN_VALUE:
                Math.min(size2, intrinsicHeight);
                break;
        }
        this.f3865n = i3;
        this.f3866o = intrinsicHeight;
        super.onMeasure(i, i2);
        if (getMeasuredHeight() < intrinsicHeight) {
            setMeasuredDimension(getMeasuredWidth(), intrinsicHeight);
        }
    }


    private Layout m3436a(CharSequence charSequence) {
        return new StaticLayout(charSequence, this.f3872u, (int) Math.ceil((double) Layout.getDesiredWidth(charSequence, this.f3872u)), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
    }


    private boolean m3440a(float f, float f2) {
        this.f3852a.getPadding(this.f3876y);
        int i = this.f3869r - this.f3859h;
        int i2 = (this.f3868q + ((int) (this.f3864m + 0.5f))) - this.f3859h;
        return f > ((float) i2) && f < ((float) ((((this.f3867p + i2) + this.f3876y.left) + this.f3876y.right) + this.f3859h)) && f2 > ((float) i) && f2 < ((float) (this.f3871t + this.f3859h));
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.f3862k.addMovement(motionEvent);
        switch (motionEvent.getAction()) {
            case 0:
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                if (isEnabled() && m3440a(x, y)) {
                    this.f3858g = 1;
                    this.f3860i = x;
                    this.f3861j = y;
                    break;
                }
            case 1:
            case 3:
                if (this.f3858g != 2) {
                    this.f3858g = 0;
                    this.f3862k.clear();
                    break;
                } else {
                    m3441b(motionEvent);
                    return true;
                }
            case 2:
                switch (this.f3858g) {
                    case 1:
                        float x2 = motionEvent.getX();
                        float y2 = motionEvent.getY();
                        if (Math.abs(x2 - this.f3860i) > ((float) this.f3859h) || Math.abs(y2 - this.f3861j) > ((float) this.f3859h)) {
                            this.f3858g = 2;
                            getParent().requestDisallowInterceptTouchEvent(true);
                            this.f3860i = x2;
                            this.f3861j = y2;
                            return true;
                        }
                    case 2:
                        float x3 = motionEvent.getX();
                        float max = Math.max(0.0f, Math.min((x3 - this.f3860i) + this.f3864m, (float) getThumbScrollRange()));
                        if (max == this.f3864m) {
                            return true;
                        }
                        this.f3864m = max;
                        this.f3860i = x3;
                        invalidate();
                        return true;
                }
                break;
        }
        return super.onTouchEvent(motionEvent);
    }


    private void m3438a(MotionEvent motionEvent) {
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        obtain.setAction(3);
        super.onTouchEvent(obtain);
        obtain.recycle();
    }


    private void m3441b(MotionEvent motionEvent) {
        boolean z = true;
        this.f3858g = 0;
        boolean z2 = motionEvent.getAction() == 1 && isEnabled();
        m3438a(motionEvent);
        if (z2) {
            this.f3862k.computeCurrentVelocity(1000);
            float xVelocity = this.f3862k.getXVelocity();
            if (Math.abs(xVelocity) <= ((float) this.f3863l)) {
                z = getTargetCheckedState();
            } else if (xVelocity <= 0.0f) {
                z = false;
            }
            m3439a(z);
            return;
        }
        m3439a(isChecked());
    }


    private void m3439a(boolean z) {
        setChecked(z);
    }

    private boolean getTargetCheckedState() {
        return this.f3864m >= ((float) (getThumbScrollRange() / 2));
    }

    public void setChecked(boolean z) {
        super.setChecked(z);
        this.f3864m = (float) (z ? getThumbScrollRange() : 0);
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int height;
        int i5;
        super.onLayout(z, i, i2, i3, i4);
        this.f3864m = (float) (isChecked() ? getThumbScrollRange() : 0);
        int width = getWidth() - getPaddingRight();
        int i6 = width - this.f3865n;
        switch (getGravity() & 112) {
            case 16:
                i5 = (((getPaddingTop() + getHeight()) - getPaddingBottom()) / 2) - (this.f3866o / 2);
                height = this.f3866o + i5;
                break;
            case 80:
                height = getHeight() - getPaddingBottom();
                i5 = height - this.f3866o;
                break;
            default:
                i5 = getPaddingTop();
                height = this.f3866o + i5;
                break;
        }
        this.f3868q = i6;
        this.f3869r = i5;
        this.f3871t = height;
        this.f3870s = width;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int i = this.f3868q;
        int i2 = this.f3869r;
        int i3 = this.f3870s;
        int i4 = this.f3871t;
        int a = m3435a(8);
        int a2 = m3435a(12);
        this.f3853b.setBounds(i + a2, i2 + a, i3 - a2, i4 - a);
        this.f3853b.draw(canvas);
        canvas.save();
        this.f3853b.getPadding(this.f3876y);
        int i5 = i + this.f3876y.left;
        int i6 = this.f3876y.top + i2;
        int i7 = i3 - this.f3876y.right;
        int i8 = i4 - this.f3876y.bottom;
        this.f3852a.getPadding(this.f3876y);
        int i9 = (int) this.f3864m;
        int i10 = (i5 - this.f3876y.left) + i9;
        int i11 = this.f3876y.right + i5 + i9 + this.f3867p;
        this.f3852a.setBounds(i10, i2, i11, i4);
        this.f3852a.draw(canvas);
        if (this.f3873v != null) {
            this.f3872u.setColor(this.f3873v.getColorForState(getDrawableState(), this.f3873v.getDefaultColor()));
        }
        this.f3872u.drawableState = getDrawableState();
        Layout layout = getTargetCheckedState() ? this.f3874w : this.f3875x;
        canvas.translate((float) (((i10 + i11) / 2) - (layout.getWidth() / 2)), (float) (((i8 + i6) / 2) - (layout.getHeight() / 2)));
        canvas.restore();
    }

    public int getCompoundPaddingRight() {
        int compoundPaddingRight = super.getCompoundPaddingRight() + this.f3865n;
        if (!TextUtils.isEmpty(getText())) {
            return compoundPaddingRight + this.f3855d;
        }
        return compoundPaddingRight;
    }

    private int getThumbScrollRange() {
        if (this.f3853b == null) {
            return 0;
        }
        this.f3853b.getPadding(this.f3876y);
        return ((this.f3865n - this.f3867p) - this.f3876y.left) - this.f3876y.right;
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (isChecked()) {
            mergeDrawableStates(onCreateDrawableState, f3851z);
        }
        return onCreateDrawableState;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        if (this.f3852a != null) {
            this.f3852a.setState(drawableState);
        }
        if (this.f3853b != null) {
            this.f3853b.setState(drawableState);
        }
        invalidate();
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.f3852a || drawable == this.f3853b;
    }
}
