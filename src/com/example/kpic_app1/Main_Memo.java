package com.example.kpic_app1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class Main_Memo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.memo);
		String account = this.getIntent().getStringExtra("getID");
		TextView myName = (TextView)findViewById(R.id.textView2);
		
		
	}
}
