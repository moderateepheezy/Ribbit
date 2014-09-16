package com.simpumind.ribbit;

import java.util.List;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

public class FriendsFragment extends ListFragment{
	
	protected static final String TAG = FriendsFragment.class.getSimpleName();;
	protected List<ParseUser> mFriends;
	protected ParseRelation<ParseUser> mfriendsRelation;
	protected ParseUser mCurrentUser;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
		return rootView;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		mCurrentUser = ParseUser.getCurrentUser();
		mfriendsRelation = mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);
		
		getActivity().setProgressBarIndeterminate(true);
		
		ParseQuery<ParseUser> query = mfriendsRelation.getQuery();
		query.addAscendingOrder(ParseConstants.KEY_USERNAME);
		query.findInBackground(new FindCallback<ParseUser>() {

			@Override
			public void done(List<ParseUser> friends, ParseException e) {
				
				getActivity().setProgressBarIndeterminate(false);
				
				if(e == null){
					mFriends = friends;
					String[] usernames = new String[mFriends.size()];
					int i = 0;
					for(ParseUser user : mFriends){
						usernames[i] = user.getUsername();
						i++;
					}
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), 
							android.R.layout.simple_list_item_1, usernames);
					setListAdapter(adapter);
				}
				else{
					Log.e(TAG, e.getMessage());
					AlertDialog.Builder dialog = new AlertDialog.Builder(getListView().getContext());
					dialog.setTitle(R.string.error_title);
					dialog.setMessage(e.getMessage());
					dialog.setPositiveButton(android.R.string.ok, null);
					AlertDialog builder = dialog.create();
					builder.show();
				}
			}
		});
	}
}
