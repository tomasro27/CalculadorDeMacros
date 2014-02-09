package com.example.calculadordemacros;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Button newMacros;
	double prots;
	double carbs;
	double fats;
	static int MY_REQUEST_CODE = 222;
	
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
		startActivityForResult(intent, MY_REQUEST_CODE);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    //TODO handle here.         
	    
	    if ( requestCode == MY_REQUEST_CODE ) 
        {
            if (resultCode == Activity.RESULT_OK ) 
            {       
			    this.prots = data.getExtras().getFloat("prots");
			    this.carbs = data.getExtras().getFloat("carbs");
			    this.fats = data.getExtras().getFloat("fats");
			    
			    Toast.makeText(getApplicationContext(), prots + " " + carbs + " " + fats,
						   Toast.LENGTH_LONG).show();
            }
        }
	    
	    
	}
	

	
	



	
	
}







