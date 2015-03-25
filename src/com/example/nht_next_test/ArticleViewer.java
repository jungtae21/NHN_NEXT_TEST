package com.example.nht_next_test;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ArticleViewer extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_viewer);

		TextView tvTitle = (TextView) findViewById(R.id.view_article_textView_title);
		TextView tvWriter = (TextView) findViewById(R.id.view_article_textView_writer);
		TextView tvContent = (TextView) findViewById(R.id.view_article_textView_content);
		TextView tvWriteDate = (TextView) findViewById(R.id.view_article_textView_wrtie_time);
		ImageView tvImage = (ImageView) findViewById(R.id.view_article_imageView_photo);

		String articleNumber = getIntent().getExtras().getString(
				"ArticleNumber");

		// Dao �ʱ�ȭ
		Dao dao = new Dao(getApplicationContext());

		Article article = dao.getArticleByArticleNumber(Integer
				.parseInt(articleNumber));
		
		tvTitle.setText(article.getTitle());
		tvWriter.setText(article.getWriter());
		tvContent.setText(article.getContent());
		tvWriteDate.setText(article.getWriteDate());
		/*
		try{
			InputStream ims = getApplicationContext().getAssets().open(article.getImgName());
			
			Drawable d = Drawable.createFromStream(ims, null);
			
			tvImage.setImageDrawable(d);
		}catch(IOException e){
			Log.e("ERROR", "ERROR : "+e);
		}
		*/
		String img_path = getApplicationContext().getFilesDir().getPath()+"/"+article.getImgName();
		File img_load_path = new File(img_path);
		
		if(img_load_path.exists()){
			//이미지가 경로에 있으면 이미지를 비트맵으로 바꾸어서 이미지 뷰에 표시
			Bitmap bitmap = BitmapFactory.decodeFile(img_path);
			tvImage.setImageBitmap(bitmap);
		}
	}
}
