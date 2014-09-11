package com.simpumind.ribbit;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;

public class EditFriendsActivity extends ListActivity {
	public static final String TAG = EditFriendsActivity.class.getSimpleName();
	protected List<ParseUser> mUsers;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_edit_friends);
		//Show to up button in the action bar
		setupActionBar();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setProgressBarIndeterminateVisibility(true);
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.orderByAscending(ParseConstants.KEY_USERNAME);
		query.setLimit(1000);
		query.findInBackground(new FindCallback<ParseUser>() {
			
			@Override
			public void done(List<ParseUser> users, ParseException e) {
				// TODO Auto-generated method stub
				setProgressBarIndeterminateVisibility(false);
				if(e == null){
					mUsers = users;
					String[] usernames = new String[mUsers.size()];
					int i = 0;
					for(ParseUser user : mUsers){
						usernames[i] = user.getUsername();
						i++;
					}
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditFriendsActivity.this, 
							android.R.layout.simple_list_item_checked, usernames);
					setListAdapter(adapter);
				}
				else{
					Log.e(TAG, e.getMessage());
					AlertDialog.Builder dialog = new AlertDialog.Builder(EditFriendsActivity.this);
					dialog.setTitle(R.string.error_title);
					dialog.setMessage(e.getMessage());
					dialog.setPositiveButton(android.R.string.ok, null);
					AlertDialog builder = dialog.create();
					builder.show();
				}
			}
		});
	}

	public void setupActionBar(){
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_friends, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
		switch (item.getItemId()) {
		case android.R.id.home:
			/**
			 * This id represents the Home or Up button. In case of this
			 * activity, the Up button is shown. Use NavUtils to allow users
			 * to navigate up one level in the application structure.
			 */
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
