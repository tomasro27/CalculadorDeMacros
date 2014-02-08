package com.example.calculadordemacros;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	Button newMacros;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		newMacros = (Button) findViewById(R.id.newMacros);
		newMacros.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openMacrosCalculator(v);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	/* method to call the macrosCalculator */ 

	public void openMacrosCalculator(View view)
	{
		Intent intent = new Intent(this, MacrosCalculator.class);
		startActivity(intent);
	}
}







