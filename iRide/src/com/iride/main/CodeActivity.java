package com.iride.main;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.iride.data.Config;
import com.iride.data.UserInfo;
import com.iride.io.Response;
import com.iride.io.ServerResponseListener;
import com.iride.io.Serverconnector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CodeActivity extends Activity implements ServerResponseListener {


    Button btnSend;

    UserInfo userInfo = UserInfo.getInstance();

    private Handler handler;
    private ProgressDialog progDialog;
    boolean isInterrupted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        btnSend = (Button) findViewById(R.id.send);
        btnSend.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(readUserCode())
                {
                    //server call for registration
                    callServerforActivation();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.code, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
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
                showDialog(" Account activated successful. You can start collecting ZOOZ");
                userInfo.setActive(true);

                SharedPreferences sharedPref = getSharedPreferences("ZOOZ_PARAM", 0);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("ACTIVE", userInfo.getActive());
                // Commit the edits!
                editor.commit();
                startActivity(new Intent(getApplicationContext(), ZoozActivity.class));

            } catch (JSONException e) {
                showDialog("Could not activate. Try again.");
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else
        {
            showDialog(response.getMessage());
        }

    }
    public void showDialog(String msg)
    {
        final Dialog dialog  = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.custom_dialog);

        TextView tv = (TextView)dialog.findViewById(R.id.txtmsg);
        tv.setText(msg);

        Button btnOk = (Button)dialog.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //user data read for registration
    private boolean  readUserCode() {

        TextView tv = (TextView) findViewById(R.id.edtCodeNumber);
        //get username
        if (tv.getText().toString() == null || tv.getText().toString().equalsIgnoreCase("")) {
            showDialog("Please enter Your Activation Code ");
            return false;
        } else
            userInfo.setActivationCode(tv.getText().toString());
        return true;
    }
    //activation server call
    private void callServerforActivation() {

        handler = new Handler();
        handler.post(new Runnable() {
            public void run() {
                progDialog = ProgressDialog.show(CodeActivity.this, "Please Wait.", "Activation in progress...", true, true);
                isInterrupted = false;
                progDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog)
                    {
                        isInterrupted = true;
                    }
                });
            }
        });

        String url = Config.IRIDE_SERVER_URL + "activation?command=";
        String param = "";
        try {

            param = "{" + "\'mobilenumber\':" + "\'"
                    + URLEncoder.encode(userInfo.getMobileNumber(), "UTF-8")
                    + "\'," + "\'sessionkey\':" + "\'"
                    + URLEncoder.encode(userInfo.getSessionkey(), "UTF-8")
                    + "\'," + "\'activationcode\':" + "\'"
                    + URLEncoder.encode(userInfo.getActivationCode(), "UTF-8")
                    + "\'}";


        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        url+=param;
        final Serverconnector connector = new Serverconnector(CodeActivity.this, CodeActivity.this, url, null,
                Serverconnector.HTTP_GET_METHOD, Config.REQ_ACTIVATION, Serverconnector.HTTP_STRING);
        connector.start();
    }
}
