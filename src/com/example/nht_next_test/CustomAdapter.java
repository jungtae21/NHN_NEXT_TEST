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
	 * Ŀ���� ����ʹ� ���ؽ�Ʈ����, ui���̾ƿ� id, ����Ʈ�� ǥ���� �����Ͱ� �ʿ�
	 * UI���̾ƿ�id�� ����Ʈ�� ĭ �ϳ��� ���̾ƿ��� �����ϴ� ��
	 * Ŀ���� ����ʹ� �ڽ��� ���� ���̾ƿ��� ���� �� ���� 
	 */
	public CustomAdapter(Context context, int layoutResourceId, ArrayList<ListData> listData){
		super(context, layoutResourceId, listData);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.listData = listData;
	}
	
	//getView�� �������̵��Ͽ� ����Ʈ�� ��� �������� ����
	public View getView(int position, View convertView, ViewGroup parent){
		
		//row�� ����Ʈ�� ������ ĭ�� ��
		View row = convertView;
		
		if(row==null){
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			//UI���̾ƿ��� �ҷ��� ��üȭ
			row = inflater.inflate(layoutResourceId, parent, false);
		}
		
		//row.findViewById�� row���� ���̾ƿ��� ����
		TextView  tvText1 = (TextView)row.findViewById(R.id.title);
		TextView  tvText2 = (TextView)row.findViewById(R.id.content);
		
		tvText1.setText(listData.get(position).getText1());
		tvText1.setText(listData.get(position).getText2());
		
		ImageView imageView = (ImageView)row.findViewById(R.id.imageView);
		
		//�̹����� �о�� ����Ʈ�� ǥ���� �ִ� ������ ���⿡���� asset������ ������ ����ְ� ������
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
