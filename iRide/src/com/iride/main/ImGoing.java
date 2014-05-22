package com.iride.main;



import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ImGoing extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
             System.out.println("first page");
		
         //    Boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        // 	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
             setContentView(R.layout.activity_splash);
	
		 // load title bar from Android layout
//		 View title = getWindow().findViewById(android.R.id.title);
//	        View titleBar = (View) title.getParent();
//	        titleBar.setBackgroundColor(Color.CYAN);

             int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
             if (actionBarTitleId > 0) {
                 TextView title = (TextView) findViewById(actionBarTitleId);
                 if (title != null) {
                     title.setTextColor(Color.YELLOW);
                    
                     setTitle("You have 1.002 $Mile"); 
                     getActionBar().setIcon(R.drawable.splash1);
                   
                 
                 }
             }
		 
	
		    //display the logo during 5 secondes,
		    new CountDownTimer(5000,1000){
		    	
		        @Override
		        public void onTick(long millisUntilFinished){} 

		        @Override
		        public void onFinish(){
		        
		               //set the new Content of your activity
		              // YourActivity.this.setContentView(R.layout.main);
		    		
		    		 Intent i = new Intent(ImGoing.this,MapActivity.class);
		               startActivity(i);
		             finish();
		    		
		        }
		   }.start();
		   

	}
		
	
		
		
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
