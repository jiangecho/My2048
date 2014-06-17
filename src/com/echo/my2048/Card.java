package com.echo.my2048;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


public class Card extends FrameLayout{
	private View background;
	private TextView label;
	private int num;

	public Card(Context context) {
		super(context);
		background = new View(context);
		background.setBackgroundColor(0x33ffffff);
		LayoutParams lp = new LayoutParams(-1, -1);
		lp.setMargins(10, 10, 0, 0);
		addView(background, lp);
		
		label = new TextView(context);
		label.setTextSize(28);
		label.setGravity(Gravity.CENTER);
		
		lp = new LayoutParams(-1, -1);
		lp.setMargins(10, 10, 0, 0);
		addView(label, lp);

		
		setAndDisplayNum(0);
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public void display() {
		
		if (this.num > 0) {
			label.setText("" + this.num);
		}else {
			label.setText("");
		}

		switch (this.num) {
		case 0:
			//label.setBackgroundColor(0x00000000);
			label.setBackgroundColor(0x33ffffff);
			break;
		case 2:
			label.setBackgroundColor(0xffeee4da);
			break;
		case 4:
			label.setBackgroundColor(0xffede0c8);
			break;
		case 8:
			label.setBackgroundColor(0xfff2b179);
			break;
		case 16:
			label.setBackgroundColor(0xfff59563);
			break;
		case 32:
			label.setBackgroundColor(0xfff67c5f);
			break;
		case 64:
			label.setBackgroundColor(0xfff65e3b);
			break;
		case 128:
			label.setBackgroundColor(0xffedcf72);
			break;
		case 256:
			label.setBackgroundColor(0xffedcc61);
			break;
		case 512:
			label.setBackgroundColor(0xffedc850);
			break;
		case 1024:
			label.setBackgroundColor(0xffedc53f);
			break;
		case 2048:
			label.setBackgroundColor(0xffedc22e);
			break;
		default:
			label.setBackgroundColor(0xff3c3a32);
			break;
		}
	}
	
	public void setAndDisplayNum(int num) {
		setNum(num);
		display();
		
	}
	
	

	public int getNum() {
		return this.num;
	}


	public boolean equals(Card card) {
		// TODO Auto-generated method stub
		return this.getNum() == card.getNum();
	}

	@Override
	public String toString() {
		return "" + this.num;
	}
	
	
	

}
