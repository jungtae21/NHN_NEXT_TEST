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

		// DB�� JSON�����͸� ����
		Dao dao = new Dao(getApplicationContext());
		String testJsonData = dao.getJsonTestData();
		dao.insertJsonData(testJsonData);

		// DB�κ��� �Խñ� ����Ʈ�� �޾ƿ�
		articleList = dao.getArticleList();

		// CustomAdapter�� ������
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
