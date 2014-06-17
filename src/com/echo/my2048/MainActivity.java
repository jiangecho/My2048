package com.echo.my2048;
import com.echo.my2048.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;


public class MainActivity extends Activity{

	private Button button;
	private LinearLayout linearLayout;
	private GameView gameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		gameView = (GameView) findViewById(R.id.gameView);
		gameView.initGame(4);
//		//GridLayout gridLayout = (GridLayout) findViewById(R.id.gridlayout);
//		button = (Button) findViewById(R.id.button1);
//		
//		linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
//		
//		final Button button2 = new Button(this);
//		button2.setText("button2");
//		LayoutParams lParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//		linearLayout.addView(button2,lParams);
//
//		button2.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				//TranslateAnimation translateAnimation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
//				TranslateAnimation translateAnimation = new TranslateAnimation(button.getX(), button.getX() + 100, 
//						button.getY(), button.getY() + 200);
//				translateAnimation.setDuration(500);
//				translateAnimation.setAnimationListener(new AnimationListener() {
//					
//					@Override
//					public void onAnimationStart(Animation animation) {
//						// TODO Auto-generated method stub
//						
//					}
//					
//					@Override
//					public void onAnimationRepeat(Animation animation) {
//						// TODO Auto-generated method stub
//						
//					}
//					
//					@Override
//					public void onAnimationEnd(Animation animation) {
//						// TODO Auto-generated method stub
//						//button2.setX(button2.getX() + 100);
//						//button2.setY(button2.getY() + 100);
//						button2.clearAnimation();
//						//button2.layout(button2.getLeft() + 100, button2.getTop() + 100, button2.getRight() - 100, button2.getBottom() - 100);
//						
//					}
//				});
//				button2.startAnimation(translateAnimation);
//			}
//		});
		
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
