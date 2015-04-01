package com.example.nht_next_test;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Article> {
	private Context context;
	private int layoutResourceId;
	private ArrayList<Article> articleData;

	/*
	 * Ŀ���� ����ʹ� ���ؽ�Ʈ����, ui���̾ƿ� id, ����Ʈ�� ǥ���� �����Ͱ� �ʿ�
	 * UI���̾ƿ�id�� ����Ʈ�� ĭ �ϳ��� ���̾ƿ��� �����ϴ� �� Ŀ���� ����ʹ� �ڽ��� ����
	 * ���̾ƿ��� ���� �� ����
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

		// �̹����� �о�� ����Ʈ�� ǥ���� �ִ� ������ ���⿡���� asset������ ������
		// ����ְ� ������

		/*
		 * try { InputStream is = context.getAssets().open(
		 * articleData.get(position).getImgName());
		 * 
		 * Drawable d = Drawable.createFromStream(is, null);
		 * 
		 * imageView.setImageDrawable(d); } catch (IOException e) {
		 * Log.e("ERROR", "Error" + e); }
		 */

		String img_path = context.getFilesDir().getPath() + "/"
				+ articleData.get(position).getImgName();
		File img_load_path = new File(img_path);

		if (img_load_path.exists()) {
			// 읽어드릴 이미지의 사이즈를 구한다.
			BitmapFactory.Options options = null;
			try {
				// 이미지가 경로에 있으면 이미지를 비트맵으로 바꿔서 이미지 뷰 표시
				// Bitmap bitmap = BitmapFactory.decodeFile(img_path);
				Bitmap bitmap = BitmapFactory.decodeFile(img_path, options);
				imageView.setImageBitmap(bitmap);
			} catch (OutOfMemoryError e) {
				Log.i("Out", "out of memory !!");

				// 폰의 화면 사이즈를 구한다.
				Display display = ((WindowManager) context
						.getSystemService(Context.WINDOW_SERVICE))
						.getDefaultDisplay();
				int displayWidth = display.getWidth();
				int displayHeight = display.getHeight();

				options = new BitmapFactory.Options();
				options.inPreferredConfig = Config.RGB_565;
				options.inJustDecodeBounds = true;

				// 화면 사이즈에 가장 근접하는 이미지의 리스케일 사이즈를 구함
				// 리스케일의 사이즈는 짝수로 지정한다.(이미지 손실을 최소화하기 위함)
				float widthScale = options.outWidth / displayWidth;
				float heightScale = options.outHeight / displayHeight;
				float scale = widthScale > heightScale ? widthScale
						: heightScale;
				
				if (scale >= 8) {
					options.inSampleSize = 8;
				} else if (scale >= 6) {
					options.inSampleSize = 6;
				} else if (scale >= 4) {
					options.inSampleSize = 4;
				} else if (scale >= 2) {
					options.inSampleSize = 2;
				} else {
					options.inSampleSize = 1;
				}
				options.inJustDecodeBounds = false;
			}
		}
		return row;

	}
}
