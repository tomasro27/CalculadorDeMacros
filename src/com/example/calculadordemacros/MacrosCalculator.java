package com.example.calculadordemacros;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Spinner;

public class MacrosCalculator extends Activity {

	
	EditText name;
	Spinner sex;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_macros_calculator);
		
		name = (EditText) findViewById(R.id.name);
		sex = (Spinner) findViewById(R.id.sex);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.macros_calculator, menu);
		return true;
	}

}
