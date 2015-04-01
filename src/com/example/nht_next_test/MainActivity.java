package com.example.nht_next_test;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;



//OnItemClickListener - ����Ʈ���� ������ �ϳ� Ŭ������ �� ���
public class MainActivity extends Activity implements OnClickListener,
		OnItemClickListener {

	private ListView mainListView1;
	private ArrayList<Article> articleList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button buWrite = (Button) findViewById(R.id.main_button_write);
		Button buRefresh = (Button) findViewById(R.id.main_button_refresh);

		buWrite.setOnClickListener(this);
		buRefresh.setOnClickListener(this);

		mainListView1 = (ListView) findViewById(R.id.main_listView1);

		// ������ Dao���� �������� JSON���� ����
		// String testJsonData = dao.getJsonTestData();
		// dao.insertJsonData(testJsonData);
		//listView();
	}
	public void onResume(){
		super.onResume();
		
		refreshData();
		
		listView();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.main_button_write:
			Intent intent = new Intent(this, ArticleWriter.class);
			startActivity(intent);
			break;
		case R.id.main_button_refresh:
			refreshData();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, ArticleViewer.class);

		intent.putExtra("ArticleNumber", articleList.get(position)
				.getArticleNumber() + "");

		startActivity(intent);
	}

	private void listView() {
		// DB로부터 게시글 리스트를 받아옴
		Dao dao = new Dao(getApplicationContext());
		articleList = dao.getArticleList();

		// CustomAdapter를 적용함
		CustomAdapter customAdapter = new CustomAdapter(this,
				R.layout.cutsom_list_row, articleList);
		mainListView1.setAdapter(customAdapter);
		mainListView1.setOnItemClickListener(this);
	}

	private final Handler handler = new Handler();

	private void refreshData() {
		new Thread() {
			public void run() {
				// 서버로부터 JSON 데이터를 가져옴
				Proxy proxy = new Proxy();
				String jsonData = proxy.getJSON();

				// DB에 JSON데이터를 저장
				Dao dao = new Dao(getApplicationContext());
				dao.insertJsonData(jsonData);
			
				handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						listView();
					}
				});
			}
		}.start();
	}

}
