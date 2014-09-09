package com.simpumind.ribbit;

import android.app.Application;

import com.parse.Parse;

public class RibbitApplication extends Application{
	
	@Override
	public void onCreate() {
		super.onCreate();
		  Parse.initialize(this, "ecJdH9orHcCu5fqDlCvtX9VjCgJDlsh91gqQVx2f", "9JtBHwRrXi3fCjo0Twfjy41n9RlLRvoxf7NZJdUc");
		}
}
