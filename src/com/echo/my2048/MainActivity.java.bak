package com.echo.my2048;
import com.echo.my2048.GameView.GameEventListner;
import com.echo.my2048.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements GameEventListner{

	private GameView gameView;
	private AnimationLayer animationLayer;
	private TextView scoreView;
	private TextView bestScoreView;
	
	private long lastBackKeyPressTime = 0;

	private static final String BEST_SCORE = "BSET_SCORE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		animationLayer = (AnimationLayer) findViewById(R.id.animationLayer);
		animationLayer.init(4);

		gameView = (GameView) findViewById(R.id.gameView);
		gameView.setAnimationLayer(animationLayer);
		gameView.setGameOverListner(this);
		gameView.initGame(4);
		
		scoreView = (TextView) findViewById(R.id.scoreView);
		bestScoreView = (TextView) findViewById(R.id.bsetScoreView);

		SharedPreferences sharedPreferences = getSharedPreferences("2048", Context.MODE_PRIVATE);
		int score = sharedPreferences.getInt(BEST_SCORE, 0);
		bestScoreView.setText("Best Score: " + score);

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
		saveScore();
		
	}
	
	private void saveScore(){
		SharedPreferences sharedPreferences = getSharedPreferences("2048", Context.MODE_PRIVATE);

		int currentScore = gameView.getScore();
		int bestScore = sharedPreferences.getInt(BEST_SCORE, 0);

		if (currentScore > bestScore) {
			sharedPreferences.edit().putInt(BEST_SCORE, currentScore).commit();
		}
		
	}

	@Override
	public void onGameOver() {
		saveScore();
		AlertDialog dialog = new AlertDialog.Builder(this).create();
		dialog.setTitle("game over");
		dialog.setMessage("game over");
		dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "quit", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
				
			}
		});
		dialog.setButton(AlertDialog.BUTTON_POSITIVE, "restart", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				gameView.resetGame();
			}
		});
		dialog.show();
	}

	@Override
	public void onScoreUpdate(int score) {
		this.scoreView.setText("Score: " + score);
	}
	
	

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		long current = 0;
		long MAX_TIME = 500;
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			current = System.currentTimeMillis();
			if (current - this.lastBackKeyPressTime < MAX_TIME ) {
				finish();
			}else {
				this.lastBackKeyPressTime = current;
				Toast.makeText(this, "Press again to exit!", Toast.LENGTH_LONG).show();;
			}
		}
		
		return true;
	}

}
