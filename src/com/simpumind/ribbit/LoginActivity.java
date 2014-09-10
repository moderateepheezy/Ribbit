 package com.simpumind.ribbit;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends ActionBarActivity {
	
	protected EditText mUsername;
	protected EditText mPassword;
	
	protected Button mLoginButon;
	
		protected TextView mSignUpTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		mSignUpTextView = (TextView)findViewById(R.id.signupText);
		
		mSignUpTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
				startActivity(intent);
			}
		});
		
		
		mUsername =(EditText)findViewById(R.id.usernameField);
		mPassword =(EditText)findViewById(R.id.passwordField);
		mLoginButon =(Button)findViewById(R.id.loginButton);
		
		mLoginButon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String username = mUsername.getText().toString();
				String password = mPassword.getText().toString();
				
				username = username.trim();
				password = password.trim();
				
				if(username.isEmpty() || password.isEmpty()){
					AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
					dialog.setTitle(R.string.login_error_title);
					dialog.setMessage(R.string.login_error_message);
					dialog.setPositiveButton(android.R.string.ok, null);
					AlertDialog builder = dialog.create();
					builder.show();
				}
				else{
					ParseUser.logInInBackground(username, password, new LogInCallback() {
						
						@Override
						public void done(ParseUser user, ParseException e) {
							// TODO Auto-generated method stub
							if(e == null){
								Intent intent = new Intent(LoginActivity.this, MainActivity.class);
								intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
								intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(intent);
							}
							else{
								AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
								dialog.setTitle(R.string.login_error_title);
								dialog.setMessage(e.getMessage());
								dialog.setPositiveButton(android.R.string.ok, null);
								AlertDialog builder = dialog.create();
								builder.show();
							}
						}
					});
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_bar) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
