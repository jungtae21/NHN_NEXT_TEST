package com.example.nht_next_test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

//Data Access Objects
//SQLite관리 모듈
public class Dao {

	private Context context;
	private SQLiteDatabase database;

	public Dao(Context context) {
		this.context = context;

		// SQLite데이터베이스의 인스턴스를 만들고, openOrCreateDatabase()로 초기화
		database = context.openOrCreateDatabase("LocalDATA.db",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);

		// 테이블 생성
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
		// json으로 데이터를 파싱할 때 쓸 임시 변수
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
				
				//DB에 데이터 넣기
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
	 * JSON파싱을 위한 테스트 문자열입니다. 각 데이터는 다음과 같습니다. ArticleNumber - 글번호 중복X 숫자 Title
	 * - 글제목 문자열 Writer - 작성자 Id - 작성자ID Content - 글내용 WriteDate - 작성일 ImgName -
	 * 사진명
	 */
	public String getJsonTestData() {
		StringBuilder sb = new StringBuilder();
		sb.append("");
		sb.append("[");
		sb.append("      {");
		sb.append("         'ArticleNumber':'1',");
		sb.append("         'Title':'오늘도 좋은 하루',");
		sb.append("         'Writer':'학생1',");
		sb.append("         'Id':'6613d02f3e2153283f23bf621145f877',");
		sb.append("         'Content':'하지만 곧 기말고사지...',");
		sb.append("         'WriteDate':'2013-09-23-10-10',");
		sb.append("         'ImgName':'photo1.jpg'");
		sb.append("      },");
		sb.append("      {");

		sb.append("         'ArticleNumber':'2',");
		sb.append("         'Title':'대출 최고 3000만원',");
		sb.append("         'Writer':'김미영 팀장',");
		sb.append("         'Id':'6326d02f3e2153266f23bf621145f734',");
		sb.append("         'Content':'김미영팀장입니다. 고갱님께서는 최저이율로 최고 3000만원까지 30분 이내 통장입금가능합니다.',");
		sb.append("         'WriteDate':'2013-09-24-11-22',");
		sb.append("         'ImgName':'photo2.jpg'");
		sb.append("      },");
		sb.append("      {");
		sb.append("         'ArticleNumber':'3',");
		sb.append("         'Title':'MAC등록신청',");
		sb.append("         'Writer':'학생2',");
		sb.append("         'Id':'8426d02f3e2153283246bf6211454262',");
		sb.append("         'Content':'1a:2b:3c:4d:5e:6f',");
		sb.append("         'WriteDate':'2013-09-25-12-33',");
		sb.append("         'ImgName':'photo3.jpg'");
		sb.append("      }");
		sb.append("]");

		return sb.toString();
	}
}
