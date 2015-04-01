package com.example.nht_next_test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ArticleWriter extends Activity implements OnClickListener {

	private EditText etWriter;
	private EditText etTitle;
	private EditText etContent;
	private ImageButton ibphoto;
	private Button buUpload;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_writer);

		etWriter = (EditText) findViewById(R.id.write_name);
		etTitle = (EditText) findViewById(R.id.write_title);
		etContent = (EditText) findViewById(R.id.write_main);

		ibphoto = (ImageButton) findViewById(R.id.write_img_button);
		buUpload = (Button) findViewById(R.id.write_button);

		ibphoto.setOnClickListener(this);
		buUpload.setOnClickListener(this);
	}

	private String filePath;
	private String fileName;
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		try{
		if (requestCode == REQUEST_PHOTO_ALBUM) {	
			Uri uri = getRealPathUri(data.getData());
			filePath = uri.toString();
			fileName = uri.getLastPathSegment();
			
			Bitmap bitmap = BitmapFactory.decodeFile(filePath);
			ibphoto.setImageBitmap(bitmap);
		}
		}catch(Exception e){
			Log.e("test", "onActivityResult ERROR : "+e);
		}
	}

	private Uri getRealPathUri(Uri uri) {

		Uri filePathUri = uri;

		if (uri.getScheme().toString().compareTo("content") == 0) {

			Cursor cursor = getApplicationContext().getContentResolver().query(
					uri, null, null, null, null);

			if (cursor.moveToFirst()) {

				int column_index = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

				filePathUri = Uri.parse(cursor.getString(column_index));
			}
		}
		return filePathUri;
	}

	private static final int REQUEST_PHOTO_ALBUM = 1;

	private ProgressDialog progressDialog;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.write_img_button:
			Intent intent = new Intent(Intent.ACTION_PICK);

			intent.setType(Images.Media.CONTENT_TYPE);
			intent.setData(Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, REQUEST_PHOTO_ALBUM);
			break;

		case R.id.write_button:
			final Handler handler = new Handler();
			new Thread(){
				public void run(){
					handler.post(new Runnable(){
						public void run(){
							progressDialog = ProgressDialog.show(ArticleWriter.this, "","업로드 중입니다.");
						}
					});
					String ID = Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
					String DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA).format( new Date() );
					Article article = new Article(0, etTitle.getText().toString(), etWriter.getText().toString(), ID, etContent.getText().toString(), DATE, fileName);
				
					ProxyUP proxyUP = new ProxyUP();
					proxyUP.uploadArticle(article, filePath);
					
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							progressDialog.cancel();
							finish();
						}
					});
				}
			}.start();
			break;
		}
	}

}
