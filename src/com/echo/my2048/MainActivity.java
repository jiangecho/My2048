package com.echo.my2048;
import com.echo.my2048.GameView.GameEventListner;
import com.echo.my2048.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
	private static final String GAME_STATE = "GAME_STATE";
	
	private SharedPreferences sharedPreferences;

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
		gameView.initGameView(4);
		
		scoreView = (TextView) findViewById(R.id.scoreView);
		bestScoreView = (TextView) findViewById(R.id.bsetScoreView);

		sharedPreferences = getSharedPreferences("2048", Context.MODE_PRIVATE);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveGameState();
	}

	@Override
	protected void onResume() {
		super.onResume();

		int score = sharedPreferences.getInt(BEST_SCORE, 0);
		bestScoreView.setText("Best Score: " + score);
		
		String state = sharedPreferences.getString(GAME_STATE, null);
		if (state != null) {
			String[] values = state.split(" ");
			scoreView.setText("Score: " + values[1]);
			
			gameView.setGameState(state);
		}

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
	
	private void saveGameState(){

		int currentScore = gameView.getScore();
		int bestScore = sharedPreferences.getInt(BEST_SCORE, 0);

		Editor editor = sharedPreferences.edit();
		if (currentScore > bestScore) {
			editor.putInt(BEST_SCORE, currentScore);
		}
		
		if (gameView.isGameOver()) {
			editor.remove(GAME_STATE);
		}else {
			String state = gameView.getGameState();
			editor.putString(GAME_STATE, state);
		}
		
		editor.commit();
		
	}

	@Override
	public void onGameOver() {
		saveGameState();
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
