package com.simpumind.ribbit;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends ActionBarActivity {
		
	protected EditText mUsername;
	protected EditText mPassword;
	protected EditText mEmail;
	
	protected Button mSignUpButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		mUsername =(EditText)findViewById(R.id.usernameField);
		mPassword =(EditText)findViewById(R.id.passwordField);
		mEmail =(EditText)findViewById(R.id.emailField);
		mSignUpButton =(Button)findViewById(R.id.signupButton);
		
		mSignUpButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String username = mUsername.getText().toString();
				String password = mPassword.getText().toString();
				String email = mEmail.getText().toString();
				
				username = username.trim();
				password = password.trim();
				email = email.trim();
				
				if(username.isEmpty() || password.isEmpty() || email.isEmpty()){
					AlertDialog.Builder dialog = new AlertDialog.Builder(SignUpActivity.this);
					dialog.setTitle(R.string.signup_error_title);
					dialog.setMessage(R.string.signup_error_message);
					dialog.setPositiveButton(android.R.string.ok, null);
					AlertDialog builder = dialog.create();
					builder.show();
				}
				else{
					ParseUser newUser = new ParseUser();
					newUser.setUsername(username);
					newUser.setPassword(password);
					newUser.setEmail(email);
					newUser.signUpInBackground(new SignUpCallback() {
						
						@Override
						public void done(ParseException e) {
							// TODO Auto-generated method stub
							if(e == null){
								Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
								intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
								intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(intent);
							}
							else{
								AlertDialog.Builder dialog = new AlertDialog.Builder(SignUpActivity.this);
								dialog.setTitle(R.string.signup_error_title);
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
		getMenuInflater().inflate(R.menu.sign_up, menu);
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
}
