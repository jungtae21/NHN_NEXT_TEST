package com.example.nht_next_test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

//Data Access Objects
//SQLite���� ���
public class Dao {

	private Context context;
	private SQLiteDatabase database;

	public Dao(Context context) {
		this.context = context;

		// SQLite�����ͺ��̽��� �ν��Ͻ��� �����, openOrCreateDatabase()�� �ʱ�ȭ
		database = context.openOrCreateDatabase("LocalDATA.db",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);

		// ���̺� ����
		try {
			String sql = "CREATE TABLE IF NOT EXISTS Articles(ID integer primary key autoincrement, "
					+ "													  ArticleNumber integer UNIQUE not null, "
					+ " 												  Title text not null, "
					+ " 												  WriterName text not null, "
					+ " 												  WriterID text not null, "
					+ " 												  Content text not null, "
					+ "	 												  WriteDate text not null, "
					+ " 												  ImgName text UNIQUE not null);";
			database.execSQL(sql);
		} catch (Exception e) {
			Log.e("test", "Create table failed ! - " + e);
			e.printStackTrace();
		}
	}

	public void insertJsonData(String jsonData){
		// json���� �����͸� �Ľ��� �� �� �ӽ� ����
		int articleNumber;
		String title;
		String writer;
		String id;
		String content;
		String writeDate;
		String imgName;
		try{
			JSONArray jArr = new JSONArray(jsonData);
			
			for(int i=0 ; i<jArr.length() ; i++){
				JSONObject jObj = jArr.getJSONObject(i);
				articleNumber = jObj.getInt("ArticleNumber");
				title=jObj.getString("Title");
				writer=jObj.getString("Writer");
				id=jObj.getString("Id");
				content=jObj.getString("Content");
				writeDate=jObj.getString("WriteDate");
				imgName=jObj.getString("ImgName");
				
				Log.i("test", "ArticleNumber : "+articleNumber + "Title : "+ title);
				
				//DB�� ������ �ֱ�
				String sql = "INSERT INTO Articles(ArticleNumber, Title, WriterName, WriterID, Content, WriteDate, ImgName)"
						+ " VALUES(" + articleNumber + ", '" + title + "', '" + writer + "', '" + id
						+ "', '" + content + "', '" + writeDate + "', '" + imgName + "');"; 
				
				try{
					database.execSQL(sql);
				}catch(Exception e){
					Log.e("test", "DB Error! - " + e );
					e.printStackTrace();
				}
			}
		}catch(JSONException e){
			Log.e("test", "JSON ERROR! - "+e);
			e.printStackTrace();
		}
	}
	/**
	 * JSON�Ľ��� ���� �׽�Ʈ ���ڿ��Դϴ�. �� �����ʹ� ������ �����ϴ�. ArticleNumber - �۹�ȣ �ߺ�X ���� Title
	 * - ������ ���ڿ� Writer - �ۼ��� Id - �ۼ���ID Content - �۳��� WriteDate - �ۼ��� ImgName -
	 * ������
	 */
	public String getJsonTestData() {
		StringBuilder sb = new StringBuilder();
		sb.append("");
		sb.append("[");
		sb.append("      {");
		sb.append("         'ArticleNumber':'1',");
		sb.append("         'Title':'���õ� ���� �Ϸ�',");
		sb.append("         'Writer':'�л�1',");
		sb.append("         'Id':'6613d02f3e2153283f23bf621145f877',");
		sb.append("         'Content':'������ �� �⸻�����...',");
		sb.append("         'WriteDate':'2013-09-23-10-10',");
		sb.append("         'ImgName':'photo1.jpg'");
		sb.append("      },");
		sb.append("      {");

		sb.append("         'ArticleNumber':'2',");
		sb.append("         'Title':'���� �ְ� 3000����',");
		sb.append("         'Writer':'��̿� ����',");
		sb.append("         'Id':'6326d02f3e2153266f23bf621145f734',");
		sb.append("         'Content':'��̿������Դϴ�. ���Բ����� ���������� �ְ� 3000�������� 30�� �̳� �����Աݰ����մϴ�.',");
		sb.append("         'WriteDate':'2013-09-24-11-22',");
		sb.append("         'ImgName':'photo2.jpg'");
		sb.append("      },");
		sb.append("      {");
		sb.append("         'ArticleNumber':'3',");
		sb.append("         'Title':'MAC��Ͻ�û',");
		sb.append("         'Writer':'�л�2',");
		sb.append("         'Id':'8426d02f3e2153283246bf6211454262',");
		sb.append("         'Content':'1a:2b:3c:4d:5e:6f',");
		sb.append("         'WriteDate':'2013-09-25-12-33',");
		sb.append("         'ImgName':'photo3.jpg'");
		sb.append("      }");
		sb.append("]");

		return sb.toString();
	}
}
