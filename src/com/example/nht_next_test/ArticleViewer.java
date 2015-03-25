package com.example.nht_next_test;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
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

		// Dao √ ±‚»≠
		Dao dao = new Dao(getApplicationContext());

		Article article = dao.getArticleByArticleNumber(Integer
				.parseInt(articleNumber));
		
		tvTitle.setText(article.getTitle());
		tvWriter.setText(article.getWriter());
		tvContent.setText(article.getContent());
		tvWriteDate.setText(article.getWriteDate());
		
		try{
			InputStream ims = getApplicationContext().getAssets().open(article.getImgName());
			
			Drawable d = Drawable.createFromStream(ims, null);
			
			tvImage.setImageDrawable(d);
		}catch(IOException e){
			Log.e("ERROR", "ERROR : "+e);
		}
	}
}
