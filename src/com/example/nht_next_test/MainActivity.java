package com.example.nht_next_test;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

//OnItemClickListener - 리스트뷰의 아이템 하나 클릭했을 때 기능
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

		// DB에 JSON데이터를 저장
		Dao dao = new Dao(getApplicationContext());
		String testJsonData = dao.getJsonTestData();
		dao.insertJsonData(testJsonData);

		// DB로부터 게시글 리스트를 받아옴
		articleList = dao.getArticleList();

		// CustomAdapter를 적용함
		CustomAdapter customAdapter = new CustomAdapter(this,
				R.layout.cutsom_list_row, articleList);
		mainListView1.setAdapter(customAdapter);
		mainListView1.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, ArticleViewer.class);
		
		intent.putExtra("ArticleNumber", articleList.get(position).getArticleNumber()+"");
		
		startActivity(intent);
	}

}
