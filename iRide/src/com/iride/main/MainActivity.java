package com.iride.main;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;

import com.google.bitcoin.core.Address;
import com.google.bitcoin.core.DumpedPrivateKey;
import com.google.bitcoin.core.ECKey;
import com.google.bitcoin.core.NetworkParameters;
import com.google.bitcoin.core.Utils;
import com.iride.data.Config;
import com.iride.data.UserInfo;
import com.iride.io.Response;
import com.iride.io.ServerResponseListener;
import com.iride.io.Serverconnector;

public class MainActivity extends Activity implements ServerResponseListener {

	//Button btnLogIn;
	Button btnRegister;

	private Handler handler;
	private ProgressDialog progDialog;
	boolean isInterrupted = false;

    UserInfo userInfo = UserInfo.getInstance();




    //System.out.println("Mobile-------------"+ MobileNumber);

    //FBUserInfo fbuserInfo = FBUserInfo.getInstance();

	@Override
	public Object onRetainNonConfigurationInstance() {
		if (progDialog != null)
			return progDialog;
		return null;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_sign_in);
		//btnLogIn = (Button) findViewById(R.id.login);
		btnRegister = (Button) findViewById(R.id.register);

        //CreateKeyPair1();
		initializeListener();



	//	btnFbLogin = (LoginButton) findViewById(R.id.authButton);

		// ((LoginButton)
		// btnFbLogin).setReadPermissions(Arrays.asList("user_birthday"));
        /*
		((LoginButton) btnFbLogin)
				.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
					@Override
					public void onUserInfoFetched(GraphUser user) {
						if (user != null) {
							// get user access_token
							Session session = Session.getActiveSession();
							String strToken = session.getAccessToken();
							fbuserInfo = new FBUserInfo(user, strToken);

							handler = new Handler();
							handler.post(new Runnable() {
								public void run() {
									progDialog = ProgressDialog.show(
											MainActivity.this, "Please Wait.",
											"Logging in...", true, true);
									isInterrupted = false;
									progDialog
											.setOnCancelListener(new OnCancelListener() {

												@Override
												public void onCancel(
														DialogInterface dialog) {
													isInterrupted = true;
												}
											});
								}
							});
							// call server for updating user's facebook info
							callServerforFBLogin(user);
							updateUI();
						} else {

							System.out.println("R,,..");

						}
					}
				});
*/	}

	// initializes the buttons listener
    private void CreateKeyPair1() {
        // generate key pair

        ECKey eck = new ECKey();
        Address PubKey = eck.toAddress(NetworkParameters.prodNet());
        System.out.println("Address :" +PubKey.toString());
        System.out.println("Private 1:" + eck.getPrivateKeyEncoded(NetworkParameters.prodNet()));
        userInfo.setPubKey(PubKey.toString());
        StoreParam("PUB_KEY", userInfo.getPubKey());
        StoreParam("PRV_KEY", eck.getPrivateKeyEncoded(NetworkParameters.prodNet()).toString());
    }


	private void initializeListener() {
/*
		btnLogIn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View view) {
				if (btnLogIn.getText().toString().equalsIgnoreCase("Login")) {
					if (readLoginInfo())
						callServerforLogin();
				} else {
					if (userInfo.getSessionkey() != null) {
						callServerforLogout(userInfo.getSessionkey());
						btnLogIn.setText("Login");
					}
				}
			}

		});
		*/

        userInfo.setActive(GetParamBoolean("ACTIVE"));
        userInfo.setSessionkey(GetParam("SESSION_KEY"));



        if (userInfo.getActive()==false)
        {
            CreateKeyPair1();
            btnRegister.setOnClickListener(new OnClickListener()
            {

                @Override
                public void onClick(View view) {
                    //if (btnRegister.getText().toString().equalsIgnoreCase("Register")) {
                        if (readRegistrationInfo())
                            callServerforRegistration();
//                    } else {
//                        if (userInfo.getSessionkey() != null) {
//                            callServerforLogout(userInfo.getSessionkey());
//                            //btnLogIn.setText("Login");
//                        }
//                    }
                /*
				Intent registerIntent = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(registerIntent);
				*/
                }
            });
        } else
        {
           //System.out.println("MobileNumber" + MobileNumber);
            startActivity(new Intent(getApplicationContext(),
                    ZoozActivity.class));
        }
    }
    //registration server call
    private void callServerforRegistration() {

        handler = new Handler();
        handler.post(new Runnable() {
            public void run() {
                progDialog = ProgressDialog.show(MainActivity.this, "Please Wait.", "Registration in progress...", true, true);
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
                    + "\'," + "\'publickey\':" + "\'"
                    + URLEncoder.encode(userInfo.getPubKey(), "UTF-8")
                    + "\'}";


        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        url+=param;
        final Serverconnector connector = new Serverconnector(MainActivity.this, MainActivity.this, url, null,
                Serverconnector.HTTP_GET_METHOD, Config.REQ_REGISTRATION, Serverconnector.HTTP_STRING);
        connector.start();
    }
	// login server call
	private void callServerforLogin() {

		handler = new Handler();
		handler.post(new Runnable() {
			public void run() {
				progDialog = ProgressDialog.show(MainActivity.this,
						"Please Wait.", "Logging in...", true, true);
				isInterrupted = false;
				progDialog.setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						isInterrupted = true;
					}
				});
			}
		});

		String url = Config.IRIDE_SERVER_URL + "login?command=";
		String param = "";
		try {
			param = "{" + "\'username\':" + "\'"
					+ URLEncoder.encode(userInfo.getUserName(), "UTF-8")
					+ "\'," + "\'password\':" + "\'"
					+ URLEncoder.encode(userInfo.getPassword(), "UTF-8")
					+ "\'}";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		url += param;
		final Serverconnector connector = new Serverconnector(
				MainActivity.this, MainActivity.this, url, null,
				Serverconnector.HTTP_GET_METHOD, Config.REQ_LOGIN,
				Serverconnector.HTTP_STRING);
		connector.start();
	}

	// logout server call
	private void callServerforLogout(String strSessionkey) {
		String url = Config.IRIDE_SERVER_URL + "logout?command=";
		String param = "";
		try {
			param = "{" + "\'sessionkey\':" + "\'"
					+ URLEncoder.encode(strSessionkey, "UTF-8") + "\'}";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		url += param;
		final Serverconnector connector = new Serverconnector(
				MainActivity.this, MainActivity.this, url, null,
				Serverconnector.HTTP_GET_METHOD, Config.REQ_LOGOUT,
				Serverconnector.HTTP_STRING);
		connector.start();
	}
/*
	// facebook login server call
	private void callServerforFBLogin(GraphUser user) {
		String url = Config.IRIDE_SERVER_URL + "facebookinfo?command=";
		String param = "";
		try {
			param = "{" + "\'access_token\':" + "\'"
					+ URLEncoder.encode(fbuserInfo.getFbAccessToken(), "UTF-8")
					+ "\'," + "\'fbuid\':" + "\'"
					+ URLEncoder.encode(fbuserInfo.getId(), "UTF-8") + "\',"
					+ "\'data\':{" + "\'username\':" + "\'"
					+ URLEncoder.encode(fbuserInfo.getUserName(), "UTF-8")
					+ "\'," + "\'fullname\':" + "\'"
					+ URLEncoder.encode(fbuserInfo.getFullName(), "UTF-8")
					+ "\'," + "\'hometown\':" + "\'"
					+ URLEncoder.encode(fbuserInfo.getHomeTown(), "UTF-8")
					+ "\'," + "\'age\':" + "\'"
					+ URLEncoder.encode("" + fbuserInfo.getAge(), "UTF-8")
					+ "\'}}";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		url += param;
		final Serverconnector connector = new Serverconnector(
				MainActivity.this, MainActivity.this, url, null,
				Serverconnector.HTTP_GET_METHOD, Config.REQ_FB_LOGIN,
				Serverconnector.HTTP_STRING);
		connector.start();
	}
*/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (data.hasExtra("returnkey")) {
				String result = data.getExtras().getString("returnkey");
				if (result != null && result.length() > 0) {
				}
			}
		}
	}


/*
	// login data read
	private boolean readLoginInfo() {
		TextView tv = (TextView) findViewById(R.id.edtLoginUserName);
		// get username
		if (tv.getText().toString() == null
				|| tv.getText().toString().equalsIgnoreCase("")) {
			showDialog("Please enter User Name.");
			return false;
		} else
			userInfo.setUserName(tv.getText().toString());

		// get password
		tv = (TextView) findViewById(R.id.edtLoginPassword);
		String pass = null;
		pass = tv.getText().toString();
		if (pass == null || tv.getText().toString().equalsIgnoreCase("")) {
			showDialog("Please enter Password.");
			return false;
		} else
			userInfo.setPassword(pass);
		return true;

	}
	*/
     private boolean StoreParam(String key,String param)
     {

         SharedPreferences sharedPref = getSharedPreferences("ZOOZ_PARAM", 0);
         SharedPreferences.Editor editor = sharedPref.edit();
         editor.putString(key, param);

         // Commit the edits!
         editor.commit();
         return true;
     }

    private String GetParam(String key)
    {
        SharedPreferences settings = getSharedPreferences("ZOOZ_PARAM", 0);
        return settings.getString(key, "");
    }

    private boolean GetParamBoolean(String key)
    {
        SharedPreferences settings = getSharedPreferences("ZOOZ_PARAM", 0);
        return settings.getBoolean(key, false);
    }

private boolean HandleRegistrationResponse(Response response) {



    JSONObject jobj;

    if (response.getStatus()==false) {
        System.out.println("HandleRegistrationResponse false response");
        return false;
    }

    //System.out.println(response.getData().toString());
    try {
        jobj = new JSONObject(response.getData().toString());

        int resCode = jobj.getInt("code");

        if(resCode != 200)
        {
            showDialog(jobj.getString("message"));
            return false;
        }
       // showDialog("Registration successful.");

    } catch (JSONException e) {
        showDialog("Could not register. Try again.");
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return true;
}
private boolean readRegistrationInfo() {
    TextView tv = (TextView) findViewById(R.id.edtPhoneNumber);
    // get username
    if (tv.getText().toString() == null
            || tv.getText().toString().equalsIgnoreCase("")) {
        showDialog("Please enter your mobile Number.");
        return false;
    } else
        userInfo.setMobileNumber(tv.getText().toString());
    return true;
}
	public void showDialog(String msg) {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialog.setContentView(R.layout.custom_dialog);

		TextView tv = (TextView) dialog.findViewById(R.id.txtmsg);
		tv.setText(msg);

		Button btnOk = (Button) dialog.findViewById(R.id.btnOk);

		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	// server response parsing
	@Override
	public void serverResponse(Response response) {

		if (true)
        {
			if (false) /*response.getRequetType() == Config.REQ_LOGOUT) {*/
			{
                System.out.println("serverResponse fail!");
				//btnLogIn.setText("Login");
				return;
			}

			handler.post(new Runnable() {
				public void run() {
					progDialog.dismiss();
					progDialog = null;
				}
			});

            if (response.getRequetType() == Config.REQ_REGISTRATION)
            {
                if (HandleRegistrationResponse(response)==false)
                {
                    return;
                }

            }
            else if (response.getRequetType() == Config.REQ_FB_LOGIN ) {

				startActivity(new Intent(getApplicationContext(), ZoozActivity.class));
				return;
			}

			String strResponse = response.getData().toString();
			System.out.println(strResponse);
            /*
			startActivity(new Intent(getApplicationContext(),
					DestinationMockActivity.class));
					*/
		
			try {
				JSONObject jobj = new JSONObject(strResponse);
				JSONObject detailObj = jobj.getJSONObject("details");
				System.out.println(detailObj.toString());
                /*
				userInfo.setAge(Integer.parseInt(detailObj.getString("age")));
				userInfo.setFullName(detailObj.getString("fullname"));
				userInfo.setHomeTown(detailObj.getString("hometown"));
				*/
				userInfo.setSessionkey(detailObj.getString("sessionkey"));
                userInfo.setMobileNumber(detailObj.getString("mobilenumber"));


                StoreParam("PHONE_NUM",userInfo.getMobileNumber());
                StoreParam("SESSION_KEY",userInfo.getSessionkey());

                /*
				btnLogIn.setText("Logout");
				TextView tv = (TextView) findViewById(R.id.edtLoginUserName);
				tv.setText("");
				tv = (TextView) findViewById(R.id.edtLoginPassword);
				tv.setText("");
				*/
				startActivity(new Intent(getApplicationContext(),
						CodeActivity.class));
			} catch (Exception ex) {
				showDialog("Invalid mobile number.");
				ex.printStackTrace();
			}
		} else {
			if (response.getRequetType() == Config.REQ_FB_LOGIN ||response.getRequetType() == Config.REQ_REGISTRATION
					|| response.getRequetType() == Config.REQ_LOGIN)
            {
				handler.post(new Runnable() {
                    public void run() {
                        progDialog.dismiss();
                        progDialog = null;
                    }
                });
			}
			showDialog(response.getMessage());
		}
	}

	@Override
	public void finish() {
  /*
		if (!fbuserInfo.getId().equalsIgnoreCase("")) {
			callServerforLogout(fbuserInfo.getId());
			Session.getActiveSession().closeAndClearTokenInformation();
		}
    */
		if (userInfo.getSessionkey() != null) {
			callServerforLogout(userInfo.getSessionkey());
            System.out.println("userInfo.getSessionkey() != null");
			//btnLogIn.setText("Login");
		}
		super.finish();
	}
}
