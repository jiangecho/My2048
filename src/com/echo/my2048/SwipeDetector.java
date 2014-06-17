package com.echo.my2048;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class SwipeDetector implements OnTouchListener{
	
	private static final int MIN_DISTANCE = 100;
	private float downX, downY, upX, upY;
	
	private SwipeListener swipeListener;
	
	public SwipeDetector(SwipeListener listener){
		swipeListener = listener;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		float deltaX, deltaY;
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = event.getX();
			downY = event.getY();
			return true;
			
		case MotionEvent.ACTION_UP:
			upX = event.getX();
			upY = event.getY();
			
			deltaX = upX - downX;
			deltaY = upY - downY;
			
			//swipe horizontal
			if (Math.abs(deltaX) > MIN_DISTANCE) {
				if (deltaX > 0) {
					swipeListener.swipeRight(v);
					
				}else {
					swipeListener.swipeLeft(v);
				}
				
				return true;
			}
			
			//swipe vertical
			if (Math.abs(deltaY) > MIN_DISTANCE) {
				if (deltaY > 0) {
					swipeListener.swipeDown(v);
				}else {
					swipeListener.swipeUp(v);
				}
				
				return true;
			}
			
			return false;
			
			

		default:
			break;
		}
		return false;
	}
	
		
	public interface SwipeListener{
		public void swipeRight(View v);
		public void swipeLeft(View v);
		public void swipeUp(View v);
		public void swipeDown(View v);
	}

}
