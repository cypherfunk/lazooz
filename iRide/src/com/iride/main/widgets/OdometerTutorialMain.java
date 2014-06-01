package com.iride.main.widgets;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.iride.main.R;

public class OdometerTutorialMain extends Activity
{
	private static final String KEY_VALUE = "com.iride.main.widgets.OdometerValue";
	
	private Odometer mOdometer;
	private TextView mValueDisplay;
	
	private int mOdometerValue;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zooz);
		
		mOdometer = (Odometer) findViewById(R.id.odometer);
		mValueDisplay = (TextView) findViewById(R.id.main_valuedisplay);
		
		mOdometer.setOnValueChangeListener(new Odometer.OnValueChangeListener()
		{
			@Override
			public void onValueChange(Odometer sender, int newValue)
			{
				updateOdometerValue();
			}
		});
		
		if(savedInstanceState != null)
		{
			mOdometerValue = savedInstanceState.getInt(KEY_VALUE);
			mOdometer.setValue(mOdometerValue);
		}
		updateOdometerValue();
	}
	
	private void updateOdometerValue()
	{
		mOdometerValue = mOdometer.getValue();
		
		String text = String.format("%06d", mOdometerValue);
		mValueDisplay.setText(text);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		
		mOdometerValue = mOdometer.getValue();
		outState.putInt(KEY_VALUE, mOdometerValue);
	}
	
}