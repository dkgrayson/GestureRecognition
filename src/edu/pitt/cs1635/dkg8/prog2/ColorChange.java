package edu.pitt.cs1635.dkg8.prog2;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.widget.*;
import android.graphics.Color;


//start activity for result to send back color
public class ColorChange extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		// Show the Up button in the action bar.
		setupActionBar();
		Button blue = (Button)findViewById(R.id.button1);
		blue.setBackgroundColor(Color.parseColor("#06799F"));
		blue.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            returnBlue();
	        }
	    });
		
		Button green = (Button)findViewById(R.id.button2);
		green.setBackgroundColor(Color.parseColor("#00C12B"));
		green.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            returnGreen();
	        }
	    });
		
		Button purple = (Button)findViewById(R.id.button3);
		purple.setBackgroundColor(Color.parseColor("#CF5FD3"));
		purple.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            returnPurple();
	        }
	    });
	}

		public void returnBlue(){
			Intent output = new Intent();
			output.putExtra("COLOR", "blue");
			setResult(RESULT_OK, output);
			finish();
		}
		
		public void returnGreen(){
			Intent output = new Intent();
			output.putExtra("COLOR", "green");
			setResult(RESULT_OK, output);
			finish();
		}
		
		public void returnPurple(){
			Intent output = new Intent();
			output.putExtra("COLOR", "purple");
			setResult(RESULT_OK, output);
			finish();
		}
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
