package com.echo.my2048;

import java.util.Random;

import com.echo.my2048.SwipeDetector.SwipeListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.GridLayout;

public class GameView extends GridLayout implements SwipeListener{
	
	private Card[][] cardMatrix;
	private int columnCount = 4;
	private int cardWith = 100, cardHeight = 100;
	
	private boolean initialized = false;
	
	private int score = 0;
	
	private SwipeDetector swipeDetector;
	
	private AnimationLayer animationLayer;
	private GameEventListner gameEventListner;

	public GameView(Context context) {
		super(context);
	}
	
	
	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setGameOverListner(GameEventListner listner){
		this.gameEventListner = listner;
	}


	
	public void setAnimationLayer(AnimationLayer animationLayer){
		this.animationLayer = animationLayer;
	}
	
	public void initGame(int columnCount) {
		this.columnCount = columnCount;
		setColumnCount(columnCount);

		swipeDetector = new SwipeDetector(this);
		this.setOnTouchListener(swipeDetector);
		
	}
	
	private void initCards(int width, int height){
		
		cardMatrix = new Card[columnCount][columnCount];
		
		for (int i = 0; i < cardMatrix.length; i++) {
			for (int j = 0; j < cardMatrix[0].length; j++) {
				
				cardMatrix[i][j] = new Card(getContext());
				addView(cardMatrix[i][j], cardWith, cardHeight);
			}
			
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh); 
		
		if (!initialized) {
			initialized = true;
			cardHeight = cardWith = (w - 20) / 4 ;
			initCards(cardHeight, cardHeight);
			start();
		}
	}
	
	private int randomNum(){
		if (new Random().nextBoolean()) {
			return 2;
		}else {
			return 4;
		}
	}
	
	private boolean isGameOver(){
		boolean over = true;
		for (int i = 0; i < columnCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				if(cardMatrix[i][j].getNum() == 0){
					return false;
				}
			}
		}
		
		//row
		for (int i = 0; i < columnCount; i++) {
			for (int j = 0; j < columnCount - 1; j++) {
				if (cardMatrix[i][j].equals(cardMatrix[i][j + 1])) {
					return false;
				}
			}
		}
		
		//column
		for (int i = 0; i < columnCount; i++) {
			for (int j = 0; j < columnCount - 1; j++) {
				if (cardMatrix[j][i].equals(cardMatrix[j + 1][i])) {
					return false;
				}
			}
		}
		
		return over;
	}
	
	private void addRondomNum(){
		final Card card;
		Random random = new Random();
		int x, y, num;
		
		
		x = random.nextInt(4);
		y = random.nextInt(4);
		
		while (cardMatrix[x][y].getNum() != 0) {
			x = random.nextInt(4);
			y = random.nextInt(4);
		}
		
		if (random.nextInt(5) < 4) {
			num = 2;
		}else {
			num = 4;
		}
		
		cardMatrix[x][y].setNum(num);
		card = cardMatrix[x][y];
		
		ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1f, 0.5f, 1f);
		scaleAnimation.setDuration(200);
		scaleAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				card.display();
				
			}
		});
		card.startAnimation(scaleAnimation);
		
	}
	
	private void start(){
		Random random = new Random();
		int x, y;
		
		while(true){
			x = random.nextInt(columnCount * columnCount);
			y = random.nextInt(columnCount * columnCount);
			
			if (x != y) {
				cardMatrix[x / columnCount][x % columnCount].setAndDisplayNum(randomNum());
				cardMatrix[y / columnCount][y % columnCount].setAndDisplayNum(randomNum());
				break;
			}
		}
	}
	


	@Override
	public void swipeRight(View v) {
		Card fromCard, toCard = null;
		boolean addNewNum = false;
		int i, j, k, animationNum;
		
		//row
		for (i = 0; i < columnCount; i++) {
			//column
			for (j = columnCount - 2; j >= 0; j--) {
				fromCard = cardMatrix[i][j];
				if (fromCard.getNum() > 0) {
					toCard = null;
					for (k = j + 1; k < columnCount; k++) {
						if (cardMatrix[i][k].getNum() == 0) {
							toCard = cardMatrix[i][k];
							continue;
						}
						if (fromCard.equals(cardMatrix[i][k])) {
							toCard = cardMatrix[i][k];
							break;
						}else {
							break;
						}
					}
					if (toCard != null) {
						
						if (fromCard.equals(toCard)) {
							this.score += fromCard.getNum() * 2;
							if (this.gameEventListner != null) {
								gameEventListner.onScoreUpdate(this.score);
							}
						}
						
						toCard.setNum(fromCard.getNum() + toCard.getNum());
						animationNum = fromCard.getNum();
						fromCard.setNum(0);
						animationLayer.applyMoveAnimation(i, j, fromCard, toCard, animationNum);

						addNewNum = true;
					}
					
				}else {
					continue;
				}
				
			}
			
		}
		if (addNewNum) {
			addRondomNum();
		}

		if (isGameOver()) {
			gameEventListner.onGameOver();
		}
	}


	@Override
	public void swipeLeft(View v) {
		Card fromCard, toCard = null;
		boolean addNewNum = false;
		int i, j, k, animationNum;
		
		//row
		for (i = 0; i < columnCount; i++) {
			//column
			//for (j = columnCount - 2; j >= 0; j--) {
			for (j = 1; j < columnCount; j++) {
				fromCard = cardMatrix[i][j];
				if (fromCard.getNum() > 0) {
					toCard = null;
					for (k = j - 1; k >=0 ; k--) {
						if (cardMatrix[i][k].getNum() == 0) {
							toCard = cardMatrix[i][k];
							continue;
						}

						if (fromCard.equals(cardMatrix[i][k])) {
							toCard = cardMatrix[i][k];
							break;
						}else {
							break;
						}
					}
					if (toCard != null) {

						if (fromCard.equals(toCard)) {
							this.score += fromCard.getNum() * 2;
							if (this.gameEventListner != null) {
								gameEventListner.onScoreUpdate(this.score);
							}
						}

						toCard.setNum(fromCard.getNum() + toCard.getNum());
						animationNum = fromCard.getNum();
						fromCard.setNum(0);
						animationLayer.applyMoveAnimation(i, j, fromCard, toCard, animationNum);

						addNewNum = true;
					}
					
				}else {
					continue;
				}

				
			}
			
		}
		if (addNewNum) {
			addRondomNum();
		}
		
		if (isGameOver()) {
			gameEventListner.onGameOver();
		}
		
	}


	@Override
	public void swipeUp(View v) {
		Card fromCard, toCard = null;
		boolean addNewNum = false;
		int i, j, k, animationNum;
		
		//column
		for (i = 0; i < columnCount; i++) {
			//row
			for (j = 1; j < columnCount; j++) {
				fromCard = cardMatrix[j][i];
				if (fromCard.getNum() > 0) {
					toCard = null;
					for (k = j - 1; k >=0 ; k--) {
						if (cardMatrix[k][i].getNum() == 0) {
							toCard = cardMatrix[k][i];
							continue;
						}

						if (fromCard.equals(cardMatrix[k][i])) {
							toCard = cardMatrix[k][i];
							break;
						}else {
							break;
						}
					}
					if (toCard != null) {

						if (fromCard.equals(toCard)) {
							this.score += fromCard.getNum() * 2;
							if (this.gameEventListner != null) {
								gameEventListner.onScoreUpdate(this.score);
							}
						}

						toCard.setNum(fromCard.getNum() + toCard.getNum());
						animationNum = fromCard.getNum();
						fromCard.setNum(0);
						animationLayer.applyMoveAnimation(j, i, fromCard, toCard, animationNum);

						addNewNum = true;
					}
					
				}else {
					continue;
				}
			}
			
		}
		if (addNewNum) {
			addRondomNum();
		}

		if (isGameOver()) {
			gameEventListner.onGameOver();
		}
		
	}


	@Override
	public void swipeDown(View v) {
		Card fromCard, toCard = null;
		boolean addNewNum = false;
		int i, j, k, animationNum;
		
		//column
		for (i = 0; i < columnCount; i++) {
			//row
			for (j = columnCount - 2; j >= 0; j--) {
				fromCard = cardMatrix[j][i];
				if (fromCard.getNum() > 0) {
					toCard = null;
					for (k = j + 1; k < columnCount ; k++) {
						if (cardMatrix[k][i].getNum() == 0) {
							toCard = cardMatrix[k][i];
							continue;
						}

						if (fromCard.equals(cardMatrix[k][i])) {
							toCard = cardMatrix[k][i];
							break;
						}else {
							break;
						}
					}
					if (toCard != null) {

						if (fromCard.equals(toCard)) {
							this.score += fromCard.getNum() * 2;
							if (this.gameEventListner != null) {
								gameEventListner.onScoreUpdate(this.score);
							}
						}

						toCard.setNum(fromCard.getNum() + toCard.getNum());
						animationNum = fromCard.getNum();
						fromCard.setNum(0);
						animationLayer.applyMoveAnimation(j, i, fromCard, toCard, animationNum);

						addNewNum = true;
					}
					
				}else {
					continue;
				}
			}
			
		}
		if (addNewNum) {
			addRondomNum();
		}

		if (isGameOver()) {
			gameEventListner.onGameOver();
		}
	}
	
	public int getScore(){
		return this.score;
	}
	
	public interface GameEventListner{
		public void onGameOver();
		public void onScoreUpdate(int score);
	}

}
