package com.echo.my2048;
import com.echo.my2048.GameView.GameEventListner;
import com.echo.my2048.R;

import android.R.string;
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
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements GameEventListner{

	private GameView gameView;
	private AnimationLayer animationLayer;
	private TextView scoreView;

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

	@Override
	public void onGameOver() {
		Toast.makeText(this, "game over", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onScoreUpdate(int score) {
		this.scoreView.setText("Score: " + score);
	}

}
