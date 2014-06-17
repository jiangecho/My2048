package com.echo.my2048;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.GridLayout;

public class AnimationLayer extends GridLayout {
	
	private Card[][] cardMatrix;
	private int columnCount = 4;
	private int cardWith = 100, cardHeight = 100;
	
	private boolean initialized = false;
	private AnimationListener animationListener = null;
	

	public AnimationLayer(Context context) {
		super(context);
	}
	
	
	public AnimationLayer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	public AnimationLayer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	
	public void init(int columnCount) {
		this.columnCount = columnCount;
		setColumnCount(columnCount);
	}
	
	public void setAnimationListner(AnimationListener animationListener){
		this.animationListener = animationListener;
	}
	
	private void initCards(int width, int height){
		
		cardMatrix = new Card[columnCount][columnCount];

		for (int i = 0; i < cardMatrix.length; i++) {
			for (int j = 0; j < cardMatrix[0].length; j++) {
				
				cardMatrix[i][j] = new Card(getContext());
				cardMatrix[i][j].setVisibility(INVISIBLE);
				addView(cardMatrix[i][j], cardWith, cardHeight);
			}
			
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh); 
		
		if (!initialized) {
			initialized = true;
			cardHeight = cardWith = (w - 50) / 4 ;
			initCards(cardHeight, cardHeight);
		}
	}
	
	public void applyMoveAnimation(int fromX, int fromY, final Card fromCard, final Card toCard, int animationNum){

		final Card animationCard = cardMatrix[fromX][fromY];

		animationCard.setAndDisplayNum(animationNum);
		animationCard.setVisibility(VISIBLE);
		
		TranslateAnimation translateAnimation = new TranslateAnimation(0, toCard.getX() - fromCard.getX(), 0, toCard.getY() - fromCard.getY());
		translateAnimation.setDuration(200);
		//translateAnimation.setFillAfter(true);
		translateAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				fromCard.display();
				if (animationListener != null) {
					animationListener.onAnimationStart(animation);
				}
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				if (animationListener != null) {
					animationListener.onAnimationRepeat(animation);
				}
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				animationCard.clearAnimation();
				animationCard.setVisibility(INVISIBLE);
				toCard.display();

				if (animationListener != null) {
					animationListener.onAnimationEnd(animation);
				}
			}
		});
		
		animationCard.startAnimation(translateAnimation);
	}
	
}
