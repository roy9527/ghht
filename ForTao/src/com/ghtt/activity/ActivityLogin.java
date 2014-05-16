package com.ghtt.activity;

import android.os.Bundle;
import android.view.Window;

import com.ghtt.base.BaseActivity;
import com.ghtt.fortao.R;

public class ActivityLogin extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.a_login_layout);
	}

}
