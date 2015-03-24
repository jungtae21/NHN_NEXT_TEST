package com.example.nht_next_test;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CustomListActivity extends Activity implements OnItemClickListener{
	
	private ArrayList<ListData> listDataArray = new ArrayList<ListData>();
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_list);
		
		ListData data1 = new ListData("1-첫번째 줄", "1-두번째 줄", "01.jpg");
		listDataArray.add(data1);
		
		ListData data2 = new ListData("2-첫번째 줄", "2-두번째 줄", "02.jpg");
		listDataArray.add(data2);
		
		ListData data3 = new ListData("3-첫번째 줄", "3-두번째 줄", "03.jpg");
		listDataArray.add(data3);
		
		ListData data4 = new ListData("4-첫번째 줄", "4-두번째 줄", "04.jpg");
		listDataArray.add(data4);
		
		ListData data5 = new ListData("5-첫번째 줄", "5-두번째 줄", "05.jpg");
		listDataArray.add(data5);
		
		ListView listView = (ListView)findViewById(R.id.custom_list_listView);
		
		CustomAdapter customAdapter = new CustomAdapter(this, R.layout.cutsom_list_row, listDataArray);
		
		listView.setAdapter(customAdapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Log.i("TEST", position +"번 리스트 선택됨");
		Log.i("TEST", "리스트 내용: "+listDataArray.get(position).getText1());
	}
}
