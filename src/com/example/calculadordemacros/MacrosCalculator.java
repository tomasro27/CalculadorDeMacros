package com.example.calculadordemacros;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MacrosCalculator extends Activity {

	
	EditText name, age, height, weight, bodyfat;
	Spinner sex, height_format, fitness_goal, exercise_level, weight_format;
	TextView result;
	
	static String selected_sex;
	static String selected_weight_format;
	static String selected_height_format;
	static String selected_fitness_goal;
	static String selected_exercise_level;
	double weight_converted;
	double height_converted;
	static double proteins;
	static double carbs;
	static double fats;
	boolean calculated;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_macros_calculator);
		
		calculated = false;
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
		result = (TextView) findViewById (R.id.result);
		
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
			
			
	}
	
	public void saveResults(View view){
		if(calculated == false)
		{
			Toast.makeText(getApplicationContext(), "Primero calcula el resultado antes de guardarlo",
					   Toast.LENGTH_LONG).show();
			return;
		}
		Intent data_back = new Intent();
	    data_back.putExtra("proteins" , proteins);
	    data_back.putExtra("carbs", carbs);
	    data_back.putExtra("fats", fats);
	    setResult(android.app.Activity.RESULT_OK, data_back);
	    
	    finish();
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
			
			try {
			    weight_converted = new Double(weight.getText().toString());
			} catch (NumberFormatException e) {
			    weight_converted = 0;
			}
			if(selected_weight_format.equals("Libras"))
				weight_converted *= 0.453592;
			
			try {
			    height_converted = new Double(height.getText().toString());
			} catch (NumberFormatException e) {
			    height_converted = 0; 
			}
			if(selected_height_format.equals("inches"))
				height_converted *= 2.54;
			
			double BMR;
			double TDEE;
			double lean_body_mass;
			double bodyfat_converted;
			try {
			   bodyfat_converted = new Double(bodyfat.getText().toString());
			} catch (NumberFormatException e) {
			    bodyfat_converted = 0; 
			}
			lean_body_mass = weight_converted * ((100 -bodyfat_converted)/100);
			
			double age_converted;
			try {
			    age_converted = new Double(age.getText().toString());
			} catch (NumberFormatException e) {
			    age_converted = 0;
			}
			
			if(TextUtils.isEmpty(bodyfat.getText()) || bodyfat_converted ==0)
			{
				if(selected_sex.equals("Hombre"))
				{
					BMR = (10.0 * weight_converted) + (6.25 *  height_converted) - (5.0 *  age_converted) +5.0;
					bodyfat_converted = (0.32810 * weight_converted) + (0.33929 * height_converted) - 29.5336;
				}
				else
				{
					BMR = (10.0 * weight_converted) + (6.25 *  height_converted) - (5.0 * age_converted) -161.0;
					bodyfat_converted = ( 0.29569 * weight_converted) + (0.41813 * height_converted) - 43.2933;
				}
			}
			else
			{
				BMR = (21.6 * lean_body_mass) + 370;
			}
			
			if(selected_exercise_level.equals("no hago ejercicio"))
				TDEE = BMR * 1.2;
			else if(selected_exercise_level.equals("entreno 3 veces por semana"))
				TDEE = BMR * 1.375;
			else if(selected_exercise_level.equals("entreno 4 veces por semana"))
				TDEE = BMR * 1.42;
			else if(selected_exercise_level.equals("entreno 5 veces por semana"))
				TDEE = BMR * 1.46;
			else if(selected_exercise_level.equals("entreno 6 veces por semana"))
				TDEE = BMR * 1.506;
			else if(selected_exercise_level.equals("entrenamiento intenso 5 veces por semana"))
				TDEE = BMR * 1.55;
			else if(selected_exercise_level.equals("entreno 7 veces por semana"))
				TDEE = BMR * 1.637;
			else if(selected_exercise_level.equals("dos veces por dia"))
				TDEE = BMR * 1.724;
			else
				TDEE = BMR;

			
			if(selected_fitness_goal.equals("Perder Grasa"))
			{
				 TDEE = TDEE - (TDEE*0.2);
				proteins = 1.5*lean_body_mass * 2.20462;
				fats = 0.35 * lean_body_mass * 2.20462;
				carbs = ((TDEE - (proteins*4)) -(fats*9))/4;
			}
			else if(selected_fitness_goal.equalsIgnoreCase("Ganar Masa Muscular"))
			{	
				TDEE = TDEE + (TDEE*0.15);
				proteins = 1*lean_body_mass * 2.20462;
				fats = 0.45 * lean_body_mass * 2.20462;
				carbs = (TDEE - (proteins*4) -(fats*9))/4;
			}
			else
			{
				proteins = 1 * lean_body_mass * 2.20462;
				fats = 0.4 * lean_body_mass * 2.20462;
				carbs = ((TDEE - (proteins*4)) - (fats*9))/4;
			}
			
			final TextView mTextView = (TextView) findViewById(R.id.result);
			mTextView.setText("CALORIAS: " + String.format("%d", (int)TDEE) + "\n" +"PROTEINAS: " 
					+ String.format("%d", (int)proteins) + "\n" + "CARBOHIDRATOS: " +String.format("%d", (int)carbs) + "\n" + "GRASAS " + String.format("%d", (int)fats));
			
			calculated = true;
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


