package com.example.calculadordemacros;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MacrosCalculator extends Activity {

	
	EditText name, age, height, weight, bodyfat;
	Spinner sex, height_format, fitness_goal, exercise_level, weight_format;
	static String selected_sex;
	static String selected_weight_format;
	static String selected_height_format;
	static String selected_fitness_goal;
	static String selected_exercise_level;
	double weight_converted;
	double height_converted;
	double proteins;
	double carbs;
	double fats;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_macros_calculator);
		
		name = (EditText) findViewById(R.id.name);
		sex = (Spinner) findViewById(R.id.sex);
		age = (EditText) findViewById(R.id.age);
		height = (EditText) findViewById(R.id.height);
		height_format = (Spinner) findViewById(R.id.height_format);
		fitness_goal = (Spinner) findViewById (R.id.fitness_goal);
		exercise_level = (Spinner) findViewById (R.id.exercise_level);
		weight_format = (Spinner) findViewById (R.id.weight_format);
		weight = (EditText) findViewById (R.id.weight);
		bodyfat = (EditText) findViewById (R.id.bodyfat);
		
		ArrayAdapter<CharSequence> sex_adapter = ArrayAdapter.createFromResource(this,
		        R.array.sex, android.R.layout.simple_spinner_item);
		sex_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sex.setAdapter(sex_adapter);
		
		ArrayAdapter<CharSequence> height_format_adapter = ArrayAdapter.createFromResource(this,
		        R.array.cm_or_inches, android.R.layout.simple_spinner_item);
		height_format_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		height_format.setAdapter(height_format_adapter);
		
		ArrayAdapter<CharSequence> fitness_goal_adapter = ArrayAdapter.createFromResource(this,
		        R.array.fitness_goal, android.R.layout.simple_spinner_item);
		fitness_goal_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		fitness_goal.setAdapter(fitness_goal_adapter);
		
		ArrayAdapter<CharSequence> exercise_level_adapter = ArrayAdapter.createFromResource(this,
		        R.array.exercise_levels, android.R.layout.simple_spinner_item);
		exercise_level_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		exercise_level.setAdapter(exercise_level_adapter);
		
		ArrayAdapter<CharSequence> weight_format_adapter = ArrayAdapter.createFromResource(this,
		        R.array.kg_or_lbs, android.R.layout.simple_spinner_item);
		weight_format_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		weight_format.setAdapter(weight_format_adapter);
		
		sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        selected_sex = parent.getItemAtPosition(pos).toString();
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
		height_format.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        selected_height_format = parent.getItemAtPosition(pos).toString();
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
		
		fitness_goal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        selected_fitness_goal = parent.getItemAtPosition(pos).toString();
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
		exercise_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        selected_exercise_level = parent.getItemAtPosition(pos).toString();
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
		weight_format.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        selected_weight_format = parent.getItemAtPosition(pos).toString();
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
		
		weight_converted =  (Double.parseDouble(weight.getText().toString()));
		if(selected_weight_format == "Libras")
			weight_converted *= 0.453592;
		height_converted = (Double.parseDouble(height.getText().toString()));
		if(selected_height_format == "inches")
			height_converted *= 2.54;
		
			
	}
	
	public void calculateMacros(View view)
	{
		if( TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(age.getText()) 
			||	TextUtils.isEmpty(height.getText()) || TextUtils.isEmpty(weight.getText()) ){
			
			Toast.makeText(getApplicationContext(), "Por favor asegurate de completar todo el formulario",
					   Toast.LENGTH_LONG).show();
		}
		else
		{	
			double TDEE;
			double lean_body_mass;
			lean_body_mass = ((weight_converted *Double.parseDouble(bodyfat.getText().toString()))/100);
			if(TextUtils.isEmpty(bodyfat.getText()) || Double.parseDouble(bodyfat.getText().toString())==0)
			{
				if(selected_sex == "Hombre")
					TDEE = 10.0 * weight_converted + 6.25 *  
					height_converted - 5.0 *  Double.parseDouble(age.getText().toString()) +5.0;
				else
					TDEE = 10.0 * weight_converted + 6.25 *  
					height_converted - 5.0 *  Double.parseDouble(age.getText().toString()) -161.0;
			}
			else
			{
				TDEE = (21.6 * lean_body_mass) + 370;
			}
			
			if(selected_fitness_goal == "Perder Grasa")
			{
				TDEE = TDEE - (TDEE*0.2);
				proteins = 1.5*lean_body_mass;
				fats = 0.35 * lean_body_mass;
				carbs = (TDEE - (proteins*4) -(fats*9))/4;
			}
			else if(selected_fitness_goal == "Ganar Masa Muscular")
			{
				TDEE = TDEE + (TDEE*0.15);
				proteins = 1*lean_body_mass;
				fats = 0.40 * lean_body_mass;
				carbs = (TDEE - (proteins*4) -(fats*9))/4;
			}
			else
			{
				proteins = 1*lean_body_mass;
				fats = 0.4 * lean_body_mass;
				carbs = (TDEE - (proteins*4) - (fats*9))/4;
			}
			
			
			Toast.makeText(getApplicationContext(), "Tus calorias son " + TDEE +" Tus proteinas " 
			+ proteins + " Tus carbs " +carbs + " Tus fats " + fats , Toast.LENGTH_LONG).show();
			
			return;
			
		}
			
	}
	
	public class SpinnerActivity extends Activity implements OnItemSelectedListener {
	    
	    public void onItemSelected(AdapterView<?> parent, View view, 
	            int pos, long id) {
	        // An item was selected. You can retrieve the selected item using
	        parent.getItemAtPosition(pos);
	    }

	    public void onNothingSelected(AdapterView<?> parent) {
	        // Another interface callback
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.macros_calculator, menu);
		return true;
		
		
		
	}

}


