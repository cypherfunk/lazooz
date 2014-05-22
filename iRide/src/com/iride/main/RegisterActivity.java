package com.iride.main;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.json.JSONException;
import org.json.JSONObject;
import com.iride.data.Config;
import com.iride.data.UserInfo;
import com.iride.io.Response;
import com.iride.io.ServerResponseListener;
import com.iride.io.Serverconnector;
import com.iride.main.R;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends Activity implements ServerResponseListener{

	Button btnRegister;
	Dialog dialog;
	UserInfo userInfo = UserInfo.getInstance();
	 
	private Handler handler;
	private ProgressDialog progDialog;
	boolean isInterrupted = false;
	 
	@Override
	public Object onRetainNonConfigurationInstance() {
		if (progDialog != null)
		      return progDialog;
		    return null;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		btnRegister = (Button) findViewById(R.id.register1);
		
		btnRegister.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if(readUserInfo())
				{
					//server call for registration
					callServerforRegistration();
				}
			}
		});
	}
	//registration server call
	private void callServerforRegistration() {
		
		handler = new Handler();
		handler.post(new Runnable() {						
			public void run() {
				progDialog = ProgressDialog.show(RegisterActivity.this, "Please Wait.", "Registration in progress...", true, true);
				isInterrupted = false;
				progDialog.setOnCancelListener(new OnCancelListener() {
					
					@Override
					public void onCancel(DialogInterface dialog) 
					{
						isInterrupted = true;
					}
				});
			}
		});
		
		String url = Config.IRIDE_SERVER_URL + "registration?command=";
		String param = "";
		try {
            /*
			param = "{"+"\'age\':"+"\'"+URLEncoder.encode(""+userInfo.getAge(), "UTF-8")+"\',"+ "\'username\':" + "\'"+URLEncoder.encode(userInfo.getUserName(), "UTF-8")+"\',"
					+"\'hometown\':"+"\'"+URLEncoder.encode(userInfo.getHomeTown(), "UTF-8")+"\',"+"\'password\':"+"\'"+URLEncoder.encode(userInfo.getPassword(), "UTF-8")+"\',"
					+"\'fullname\':"+ "\'"+URLEncoder.encode(userInfo.getFullName(), "UTF-8")+"\',"+"\'mobilenumber\':"+"\'"+URLEncoder.encode(userInfo.getMobileNumber(), "UTF-8")+"\'}";
					*/
            param = "{" + "\'mobilenumber\':" + "\'"
                    + URLEncoder.encode(userInfo.getMobileNumber(), "UTF-8")
                    + "\'}";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		url+=param;
		final Serverconnector connector = new Serverconnector(RegisterActivity.this, RegisterActivity.this, url, null, 
				Serverconnector.HTTP_GET_METHOD, Config.REQ_REGISTRATION, Serverconnector.HTTP_STRING);
		connector.start();
	}
	
	public void showDialog(String msg)
	{
		final Dialog dialog  = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		 
	    dialog.setContentView(R.layout.custom_dialog);
	 
	    TextView tv = (TextView)dialog.findViewById(R.id.txtmsg);
	    tv.setText(msg);
	    
	    Button btnOk = (Button)dialog.findViewById(R.id.btnOk);
	 
	    btnOk.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
	       dialog.dismiss();
	    }
	    });
		dialog.show();
	}
	
	//user data read for registration
	private boolean  readUserInfo() {

        TextView tv = (TextView)findViewById(R.id.edtPhoneNumber);
        //get username
        if(tv.getText().toString()==null || tv.getText().toString().equalsIgnoreCase(""))
        {
            showDialog("Please enter Your Mobile Number");
            return false;
        }
        else
            userInfo.setMobileNumber(tv.getText().toString());
        /*
		TextView tv = (TextView)findViewById(R.id.edtUserName);
		//get username
		if(tv.getText().toString()==null || tv.getText().toString().equalsIgnoreCase(""))
		{
			showDialog("Please enter User Name.");
			return false;
		}
		else
			userInfo.setUserName(tv.getText().toString());
		
		//get password
		tv = (TextView)findViewById(R.id.edtPassword);
		String pass =null, repass = null;
		pass = tv.getText().toString();
		if(pass==null || tv.getText().toString().equalsIgnoreCase(""))
		{
			showDialog("Please enter Password.");
			return false;
		}
		
		//get retyped password
		tv = (TextView)findViewById(R.id.edtRetypePassword);
		repass = tv.getText().toString();
		if(repass==null || tv.getText().toString().equalsIgnoreCase(""))
		{
			showDialog("Please Retype Password.");
			return false;
		}
		else if(!pass.equalsIgnoreCase(repass))
		{
			showDialog("Passwords do not match. Please check.");
			return false;
		}
		userInfo.setPassword(pass);
		
		
		//get full name
		tv = (TextView)findViewById(R.id.edtFullName);
		if(tv.getText().toString()==null || tv.getText().toString().equalsIgnoreCase(""))
		{
			showDialog("Please enter Full Name.");
			return false;
		}
		else
			userInfo.setFullName(tv.getText().toString());
		
		//get home town
		tv = (TextView)findViewById(R.id.edtHomeTown);
		if(tv.getText().toString()==null || tv.getText().toString().equalsIgnoreCase(""))
		{
			showDialog("Please enter Home Town.");
			return false;
		}
		else
			userInfo.setHomeTown(tv.getText().toString());
		
		//get age
		tv = (TextView)findViewById(R.id.edtAge);
		if(tv.getText().toString()==null || tv.getText().toString().equalsIgnoreCase(""))
		{
			showDialog("Please enter Age.");
			return false;
		}
		else
			userInfo.setAge(Integer.parseInt(tv.getText().toString()));
		*/
		return true;
	}
	
	@Override
	public void finish() {
	    Intent intent = new Intent();
	    intent.putExtra("returnkey", "Registered");
	    setResult(RESULT_OK, intent);
	    super.finish();
	}
	
	protected Dialog onCreateDialog() {
	    Context context=RegisterActivity.this;
	    dialog=new Dialog(context);
	    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    dialog.setContentView(R.layout.custom_dialog);
	    Button btnOk = (Button)dialog.findViewById(R.id.btnOk);
	    btnOk.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) {
		       dialog.dismiss();
		    }
	    });
	    
	    return dialog;
	}

	//server response parsing
	@Override
	public void serverResponse(Response response) {
		
		handler.post(new Runnable() {			
			public void run() {
				progDialog.dismiss();
				progDialog = null;				
			}			
		});
		
		if (response.getStatus())
		{
			System.out.println(response.getData().toString());
			JSONObject jobj;
			try {
				jobj = new JSONObject(response.getData().toString());
			
				int resCode = jobj.getInt("code");
			
				if(resCode != 200)
				{
					showDialog(jobj.getString("message"));
					return;
				}
				showDialog("Registration successful. Please login.");
			
			} catch (JSONException e) {
				showDialog("Could not register. Try again.");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			showDialog(response.getMessage());
		}
		
	}
}
