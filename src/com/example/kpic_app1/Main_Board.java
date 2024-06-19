package com.example.kpic_app1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class Main_Board extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.board);
		String account = this.getIntent().getStringExtra("getID");
		TextView myName = (TextView)findViewById(R.id.textView2);
		
		Intent intent = new Intent(Main_Board.this, Board_List.class);
		
		startActivity(intent);
		finish();
	}
}
