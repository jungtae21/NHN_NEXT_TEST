package com.example.nht_next_test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<ListData> {
	private Context context;
	private int layoutResourceId;
	private ArrayList<ListData> listData;
	
	/*
	 * 커스텀 어댑터는 컨텍스트정보, ui레이아웃 id, 리스트에 표시할 데이터가 필요
	 * UI레이아웃id는 리스트의 칸 하나의 레이아웃을 구성하는 것
	 * 커스텀 어댑터는 자신이 직접 레이아웃을 만들 수 있음 
	 */
	public CustomAdapter(Context context, int layoutResourceId, ArrayList<ListData> listData){
		super(context, layoutResourceId, listData);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.listData = listData;
	}
	
	//getView를 오버라이딩하여 리스트가 어떻게 보여질지 정의
	public View getView(int position, View convertView, ViewGroup parent){
		
		//row는 리스트의 각각의 칸이 됨
		View row = convertView;
		
		if(row==null){
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			//UI레이아웃을 불러서 객체화
			row = inflater.inflate(layoutResourceId, parent, false);
		}
		
		//row.findViewById로 row안의 레이아웃을 설정
		TextView  tvText1 = (TextView)row.findViewById(R.id.title);
		TextView  tvText2 = (TextView)row.findViewById(R.id.content);
		
		tvText1.setText(listData.get(position).getText1());
		tvText1.setText(listData.get(position).getText2());
		
		ImageView imageView = (ImageView)row.findViewById(R.id.imageView);
		
		//이미지를 읽어와 리스트에 표시해 주는 것으로 에기에서는 asset폴더에 사진을 집어넣고 가져옴
		try{
			InputStream is = context.getAssets().open(listData.get(position).getImgName());
			
			Drawable d = Drawable.createFromStream(is, null);
			
			imageView.setImageDrawable(d);
		}catch(IOException e){
			Log.e("ERROR", "Error"+e);
		}
		
		return row;
		
	}
}
