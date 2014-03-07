package com.suiding.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import com.suiding.activity.R;

public class PullDownLinearLayout extends LinearLayout {
	// ===========================================================
	// Constants
	// ===========================================================

	// 公式1 y = H-A/(x+A/H)
	// 公式2 y = H-H*H/(x+H)
	private double H = 100; 

	// 状态
	private static final int PULL_TO_DOWN = 0x0;
	private static final int RELEASE_TO_UP = 0x1;

	// ===========================================================
	// Fields
	// ===========================================================

	private int touchSlop;

	private float initMotionY;
	private float lastMotionX;
	private float lastMotionY;
	private boolean isBeingDragged = false;

	private int state = PULL_TO_DOWN;

	private boolean isPullToRefreshEnabled = true;

	private SmoothRunnable smoothrunnable;

	private ReadyForPullDownable mPullDownable = null;
	
	public PullDownLinearLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.initialized(context, null);
	}

	public PullDownLinearLayout(Context context, int mode) {
		super(context);
		// TODO Auto-generated constructor stub
		this.initialized(context, null);
	}

	public PullDownLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.initialized(context, attrs);
	}

	private void initialized(Context context, AttributeSet attrs) {

		setOrientation(LinearLayout.VERTICAL);

		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

		// Styleables from XML
		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.PullToRefresh);
		array.recycle();

	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	/**
	 * Whether Pull-to-Refresh is enabled
	 * 
	 * @return enabled
	 */
	public final boolean isPullToRefreshEnabled() {
		return isPullToRefreshEnabled;
	}

	/**
	 * A mutator to enable/disable Pull-to-Refresh for the current View
	 * 
	 * @param enable
	 *            Whether Pull-To-Refresh should be used
	 */
	public final void setPullToRefreshEnabled(boolean enable) {
		this.isPullToRefreshEnabled = enable;
	}

	public void setPullDownable(ReadyForPullDownable mPullDownable) {
		this.mPullDownable = mPullDownable;
	}

	public void setMaxScorllHeight(int height) {
		this.H = height;
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================



	@Override
	public final boolean onTouchEvent(MotionEvent event) {

		if (!isPullToRefreshEnabled) {
			return false;
		}

		if (event.getAction() == MotionEvent.ACTION_DOWN
				&& event.getEdgeFlags() != 0) {
			return false;
		}

		switch (event.getAction()) {

		case MotionEvent.ACTION_MOVE: {
			if (isBeingDragged) {
				lastMotionY = event.getY();
				this.pullEvent();
				return true;
			}
			break;
		}

		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP: {
			if (isBeingDragged) {
				isBeingDragged = false;
				smoothScrollTo(0);
				return true;
			}
			break;
		}
		}

		return false;
	}

	@Override
	public final boolean onInterceptTouchEvent(MotionEvent event) {

		final int action = event.getAction();

		if (!isPullToRefreshEnabled || action == MotionEvent.ACTION_CANCEL
				|| action == MotionEvent.ACTION_UP) {
			isBeingDragged = false;
			return false;
		}

		if (action != MotionEvent.ACTION_DOWN && isBeingDragged) {
			return true;
		}

		switch (action) {
		case MotionEvent.ACTION_MOVE: {
			if (isReadyForPullDown()) {
				final float y = event.getY();
				final float dy = y - lastMotionY;
				final float yDiff = Math.abs(dy);
				final float xDiff = Math.abs(event.getX() - lastMotionX);

				if (yDiff > touchSlop && yDiff > xDiff && dy >= 0.0001f) {
					lastMotionY = y;
					isBeingDragged = true;
				}
			}
			break;
		}
		case MotionEvent.ACTION_DOWN: {
			if (isReadyForPullDown()) {
				lastMotionY = initMotionY = event.getY();
				lastMotionX = event.getX();
				isBeingDragged = false;
			}
			break;
		}
		}
		return isBeingDragged;
	}

	/**
	 * Actions a Pull Event
	 * 
	 * @return true if the Event has been handled, false if there has been no
	 *         change
	 */
	private boolean pullEvent() {

		int oldHeight = this.getScrollY();
		int newHeight = Math.round(Math.max(lastMotionY - initMotionY, 0));

		// 公式2 y = H-H*H/(x+H)
		int x = newHeight;
		int y = (int)(H-H*H/(x+H));

		setHeaderScroll(-y);

//		try {
//			String tip = String.format("x=%6d,y=%6d",x,y);
//			System.out.println(tip);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}

		if (newHeight != 0) {
			if (state == PULL_TO_DOWN) {
				state = RELEASE_TO_UP;
				return true;

			} else if (state == RELEASE_TO_UP) {
				// state = PULL_TO_DOWN;
				return true;
			}
		}
		return oldHeight != newHeight;
	}

	// ===========================================================
	// Interfaces
	// ===========================================================

	/**
	 * Implemented by derived class to return whether the View is in a state
	 * where the user can Pull to Refresh by scrolling down.
	 * 
	 * @return true if the View is currently the correct state (for example, top
	 *         of a ListView)
	 */
	protected boolean isReadyForPullDown() {
		if(this.mPullDownable != null){
			return mPullDownable.isReadyForPullDown();
		}
		return false;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	protected final void setHeaderScroll(int y) {
		scrollTo(0, y);
	}

	protected final void smoothScrollTo(int y) {
		if (null != smoothrunnable) {
			smoothrunnable.stop();
		}
		if (this.getScrollY() != y) {
			this.smoothrunnable = new SmoothRunnable(Looper.myLooper(),
					mSmooth, getScrollY(), y);
			this.smoothrunnable.start();
		}
	}

	// ===========================================================
	// Inner interface
	// ===========================================================

	public static interface ReadyForPullDownable {
		public boolean isReadyForPullDown();
	}
	
	private static interface Smoothable {
		public boolean onSmooth(int value, int form, int to);
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	private Smoothable mSmooth = new Smoothable() {
		@Override
		public boolean onSmooth(int value, int form, int to) {
			// TODO Auto-generated method stub
			scrollTo(0, value);
			return true;
		}
	};

	private final class SmoothRunnable implements Runnable {

		public static final int ANIMATION_FPS = 1000 / 60;
		public static final int ANIMATION_DURATION_MS = 190;

		private final int valueto;
		private final int valuefrom;
		private final Handler handler;
		private final Smoothable smoothable;
		private final Interpolator interpolator;

		private int value = -1;
		private long startTime = -1;
		private boolean running = true;

		public SmoothRunnable(Looper looper, Smoothable smoothable, int from,
				int to) {
			this.valueto = to;
			this.valuefrom = from;
			this.smoothable = smoothable;
			this.handler = new Handler(looper);
			this.interpolator = new AccelerateDecelerateInterpolator();
		}

		@Override
		public void run() {
			/**
			 * Only set startTime if this is the first time we're starting, else
			 * actually calculate the Y delta
			 */
			if (startTime == -1) {
				startTime = System.currentTimeMillis();
			} else {

				/**
				 * We do do all calculations in long to reduce software float
				 * calculations. We use 1000 as it gives us good accuracy and
				 * small rounding errors
				 */
				long normalizedTime = System.currentTimeMillis();
				normalizedTime = 1000 * (normalizedTime - startTime);
				normalizedTime = normalizedTime / ANIMATION_DURATION_MS;
				normalizedTime = Math.max(Math.min(normalizedTime, 1000), 0);

				float interpolation = interpolator
						.getInterpolation(normalizedTime / 1000f);
				final int delta = Math.round((valuefrom - valueto)
						* interpolation);
				this.value = valuefrom - delta;
				if (smoothable.onSmooth(value, valuefrom, valueto) == false) {
					running = false;
					this.handler.removeCallbacks(this);
				}
			}

			// If we're not at the target Y, keep going...
			if (running && valueto != value) {
				handler.postDelayed(this, ANIMATION_FPS);
			}
		}

		public void stop() {
			this.running = false;
			this.handler.removeCallbacks(this);
		}

		public void start() {
			this.running = true;
			this.handler.post(this);
		}
	};
}
