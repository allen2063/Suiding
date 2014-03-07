package com.suiding.widget;

import com.suiding.activity.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

public class DashedLine extends View {

	private Paint mPaint = new Paint();
	private Path mPath = new Path();
	private PathEffect mPathEffect = new DashPathEffect(new float[] { 5, 5, 5,
			5 }, 1);

	private int color;

	public DashedLine(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint.setStyle(Paint.Style.STROKE);
		initialized(context, attrs);
		mPaint.setPathEffect(mPathEffect);
	}

	@SuppressLint("Recycle")
	private void initialized(Context context, AttributeSet attrs) {

		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.DashedLine);
		if (array.hasValue(R.styleable.DashedLine_lineColor)) {
			color = array
					.getColor(R.styleable.DashedLine_lineColor, Color.GRAY);
		}
		mPaint.setColor(color);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		mPath.moveTo(0, 1);
		mPath.lineTo(800, 1);
		canvas.drawPath(mPath, mPaint);
	}

}
