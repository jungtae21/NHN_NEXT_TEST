package com.example.nht_next_test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Dao dao = new Dao(getApplicationContext());
		
		String testJsonData = dao.getJsonTestData();
		
		dao.insertJsonData(testJsonData);
	}

}
