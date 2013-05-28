package com.pse.trainingappdroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TabsSettingsActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_settings);
		
		//Find buttons
		Button buttonRemove = (Button)findViewById(R.id.buttonRemove);
		Button buttonPersonalData = (Button)findViewById(R.id.buttonPersonal);
		
		final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
		
		final LoginDataSource db = new LoginDataSource(this);
		final Editor editor = pref.edit();
		
		buttonRemove.setOnClickListener(new View.OnClickListener()
		{public void onClick(View v) {
			
			String userRecovered = pref.getString("key_userName", "Not exist");
			Login login = new Login();
			
			db.open();
			login = db.getLogin(userRecovered);
			db.open();
			db.deleteLogin(login);
			Log.d("BUTTON_REMOVE", "user removed");
			Log.d("BUTTON_REMOVE", login._user);

			Intent intent = new Intent(TabsSettingsActivity.this , LoginActivity.class);
      	  	startActivity(intent);
            finish();
            
            editor.clear();
            editor.commit(); // commit changes
        		}
        });
		
		buttonPersonalData.setOnClickListener(new View.OnClickListener()
		{@SuppressWarnings("deprecation")
		public void onClick(View v) {
			
			AlertDialog alertDialog = new AlertDialog.Builder(TabsSettingsActivity.this).create();
			String userRecovered = pref.getString("key_userName", "error");
			String passwordRecovered = pref.getString("key_password", "error");
			
			// Setting Dialog Title
			alertDialog.setTitle("Personal data");
			
			// Setting Dialog Message
			alertDialog.setMessage("User name: " + userRecovered + "\n" + "Password: " + passwordRecovered);
			
			// Setting Icon to Dialog
			alertDialog.setIcon(R.drawable.logo_login);
			
			// Setting OK Button
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) {

			        }
			});
			
			// Showing Alert Message
			alertDialog.show();
			
			
			
			
        		}
        });

	}

}
