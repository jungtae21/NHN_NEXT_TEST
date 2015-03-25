package com.example.nht_next_test;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomAdapter extends ArrayAdapter<Article> {
	private Context context;
	private int layoutResourceId;
	private ArrayList<Article> articleData;

	/*
	 * Ŀ���� ����ʹ� ���ؽ�Ʈ����, ui���̾ƿ� id, ����Ʈ�� ǥ���� �����Ͱ� �ʿ� UI���̾ƿ�id�� ����Ʈ�� ĭ �ϳ��� ���̾ƿ���
	 * �����ϴ� �� Ŀ���� ����ʹ� �ڽ��� ���� ���̾ƿ��� ���� �� ����
	 */
	public CustomAdapter(Context context, int layoutResourceId,
			ArrayList<Article> articleData) {
		super(context, layoutResourceId, articleData);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.articleData = articleData;
	}

	// getView�� �������̵��Ͽ� ����Ʈ�� ��� �������� ����
	public View getView(int position, View convertView, ViewGroup parent) {

		// row�� ����Ʈ�� ������ ĭ�� ��
		View row = convertView;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			// UI���̾ƿ��� �ҷ��� ��üȭ
			row = inflater.inflate(layoutResourceId, parent, false);
		}

		// row.findViewById�� row���� ���̾ƿ��� ����
		ImageView imageView = (ImageView) row.findViewById(R.id.imageView1);
		TextView tvTitle = (TextView) row.findViewById(R.id.textView1);
		TextView tvContent = (TextView) row.findViewById(R.id.textView2);

		tvTitle.setText(articleData.get(position).getTitle());
		tvContent.setText(articleData.get(position).getContent());

		// �̹����� �о�� ����Ʈ�� ǥ���� �ִ� ������ ���⿡���� asset������ ������ ����ְ� ������
		/*
		try {
			InputStream is = context.getAssets().open(
					articleData.get(position).getImgName());

			Drawable d = Drawable.createFromStream(is, null);

			imageView.setImageDrawable(d);
		} catch (IOException e) {
			Log.e("ERROR", "Error" + e);
		}
		 */
		
		String img_path = context.getFilesDir().getPath()+"/"+articleData.get(position).getImgName();
		File img_load_path = new File(img_path);
		
		if(img_load_path.exists()){
			//�̹����� ��ο� ������ �̹����� ��Ʈ������ �ٲپ �̹��� �信 ǥ��
			Bitmap bitmap = BitmapFactory.decodeFile(img_path);
			imageView.setImageBitmap(bitmap);
		}
		return row;

	}
}
