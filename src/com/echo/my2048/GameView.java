package com.echo.my2048;

import java.util.Random;

import com.echo.my2048.SwipeDetector.SwipeListener;

import android.R.bool;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.GridLayout;
import android.widget.Toast;

public class GameView extends GridLayout implements SwipeListener{
	
	private Card[][] cardMatrix;
	private int columnCount = 4;
	private int cardWith = 100, cardHeight = 100;
	
	private boolean initialized = false;
	
	private SwipeDetector swipeDetector;

	public GameView(Context context) {
		super(context);
		init(Config.ROW_COUNT, Config.COLUMN_COUNT);
	}
	
	
	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(Config.ROW_COUNT, Config.COLUMN_COUNT);
	}


	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(Config.ROW_COUNT, Config.COLUMN_COUNT);
	}


	private void init(int rows, int columns){
//		setRowCount(rows);
//		setColumnCount(columns);
//		
//		cardMatrix = new Card[rows][columns];
		
	}
	
	public void initGame(int columnCount) {
		this.columnCount = columnCount;
		setColumnCount(columnCount);

		swipeDetector = new SwipeDetector(this);
		this.setOnTouchListener(swipeDetector);
		
	}
	
	private void initCards(int width, int height){
		
		cardMatrix = new Card[columnCount][columnCount];
		
//		cardMatrix[0][0] = new Card(getContext());
//		addView(cardMatrix[0][0], width, height);
//		cardMatrix[0][3] = new Card(getContext());
//		addView(cardMatrix[0][3], width, height);

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
			cardHeight = cardWith = (w - 50) / 4 ;
			initCards(cardHeight, cardHeight);
			start();
		}
	}
	
	private void applyMoveAnimation(final Card fromCard, final Card toCard){
		
		float x1, x2, y1, y2;
		x1 = fromCard.getX();
		x2 = toCard.getX();
		y1 = fromCard.getY();
		y2 = toCard.getY();
		
		
		//TranslateAnimation translateAnimation = new TranslateAnimation(fromCard.getX(), toCard.getX(), fromCard.getY(), toCard.getY());
		TranslateAnimation translateAnimation = new TranslateAnimation(x1, x2, y1, y2);
		translateAnimation.setDuration(500);
		translateAnimation.setFillAfter(true);
		translateAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				//fromCard.display();
				toCard.display();
			}
		});
		
		fromCard.startAnimation(translateAnimation);
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
					over = false;
					break;
				}
			}

		}
		
		return over;
	}
	
	private void addRondomNum(){
		Random random = new Random();
		int x, y;
		
		if (isGameOver()) {
			//TODO game over
			return;
		}
		
		x = random.nextInt(4);
		y = random.nextInt(4);
		
		while (cardMatrix[x][y].getNum() != 0) {
			x = random.nextInt(4);
			y = random.nextInt(4);
		}

		cardMatrix[x][y].setAndDisplayNum(random.nextBoolean() ? 2 : 4);
		
	}
	
	private void start(){
		Random random = new Random();
		int x, y;
		
		// TODO 
		// the (x, y) maybe the same
		for (int i = 0; i < 2; i++) {
			x = random.nextInt(3);
			y = random.nextInt(3);
			
			cardMatrix[x][y].setAndDisplayNum(randomNum());
		}
		
//			cardMatrix[1][0].setAndDisplayNum(2);
//			cardMatrix[1][1].setAndDisplayNum(2);

		
	}
	


	@Override
	public void swipeRight(View v) {
		Card fromCard, toCard = null;
		boolean addNewNum = false;
		
		//row
		for (int i = 0; i < columnCount; i++) {
			//column
			for (int j = columnCount - 2; j >= 0; j--) {
				fromCard = cardMatrix[i][j];
				if (fromCard.getNum() > 0) {
					toCard = null;
					for (int k = j + 1; k < columnCount; k++) {
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
						toCard.setNum(fromCard.getNum() + toCard.getNum());
						fromCard.setNum(0);
						applyMoveAnimation(fromCard, toCard);
						
//						toCard.setAndDisplayNum(fromCard.getNum() + toCard.getNum());
//						fromCard.setAndDisplayNum(0);

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
	}


	@Override
	public void swipeLeft(View v) {
		Card fromCard, toCard = null;
		boolean addNewNum = false;
		
		//row
		for (int i = 0; i < columnCount; i++) {
			//column
			//for (int j = columnCount - 2; j >= 0; j--) {
			for (int j = 1; j < columnCount; j++) {
				fromCard = cardMatrix[i][j];
				if (fromCard.getNum() > 0) {
					toCard = null;
					for (int k = j - 1; k >=0 ; k--) {
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
//						toCard.setNum(fromCard.getNum() + toCard.getNum());
//						fromCard.setNum(0);
//						applyMoveAnimation(fromCard, toCard);

						toCard.setAndDisplayNum(fromCard.getNum() + toCard.getNum());
						fromCard.setAndDisplayNum(0);

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
		
	}


	@Override
	public void swipeUp(View v) {
		Card fromCard, toCard = null;
		boolean addNewNum = false;
		
		//column
		for (int i = 0; i < columnCount; i++) {
			//row
			for (int j = 1; j < columnCount; j++) {
				fromCard = cardMatrix[j][i];
				if (fromCard.getNum() > 0) {
					toCard = null;
					for (int k = j - 1; k >=0 ; k--) {
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
//						toCard.setNum(fromCard.getNum() + toCard.getNum());
//						fromCard.setNum(0);
//						applyMoveAnimation(fromCard, toCard);

						toCard.setAndDisplayNum(fromCard.getNum() + toCard.getNum());
						fromCard.setAndDisplayNum(0);

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
		
	}


	@Override
	public void swipeDown(View v) {
		Card fromCard, toCard = null;
		boolean addNewNum = false;
		
		//column
		for (int i = 0; i < columnCount; i++) {
			//row
			for (int j = columnCount - 2; j >= 0; j--) {
				fromCard = cardMatrix[j][i];
				if (fromCard.getNum() > 0) {
					toCard = null;
					for (int k = j + 1; k < columnCount ; k++) {
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
//						toCard.setNum(fromCard.getNum() + toCard.getNum());
//						fromCard.setNum(0);
//						applyMoveAnimation(fromCard, toCard);

						toCard.setAndDisplayNum(fromCard.getNum() + toCard.getNum());
						fromCard.setAndDisplayNum(0);

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
		
	}

}
