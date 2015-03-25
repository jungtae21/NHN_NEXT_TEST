package com.example.nht_next_test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;
import android.util.Log;



public class FileDownloader {
	private final Context context;

	public FileDownloader(Context context) {
		this.context = context;
	}

	public void downFile(String fileUrl, String fileName) {
		File filePath = new File(context.getFilesDir().getPath()+"/"+fileName);
		// ������ �̹� ���� ��� �ٿ�ε� ���� ����
		if (!filePath.exists()) {
			try {

				URL url = new URL(fileUrl);

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();

				// ���� ���ӽ��� Time out(ms)
				conn.setConnectTimeout(10 * 1000);

				// Read���� Time out(ms)
				conn.setReadTimeout(10 * 1000);

				// ��û��� ����
				conn.setRequestMethod("GET");

				// ������ �����ϵ��� ��
				conn.setRequestProperty("Connetion", "Keep-Alive");

				// ĳ���ͼ��� UTF-8�� ��û
				conn.setRequestProperty("Accept-Charset", "UTF-8");

				// ĳ�õ� �����͸� ������� �ʰ� �Ź� �����κ��� �ٽ� ����
				conn.setRequestProperty("Cache-Control", "no-cache");

				// �����κ��� �ƹ� ������ �����ͳ� �� ��û
				conn.setRequestProperty("Accept", "*/*");

				// InputStream���� ������ ���� ������ �ްڴٴ� �ɼ�
				conn.setDoInput(true);

				conn.connect();

				int status = conn.getResponseCode();

				switch (status) {
				// ���������� ������ �� ����(200,201�� ResponseCode)
				case 200:
				case 201:
					Log.i("test", "정상 접속 여부 확인");
					InputStream is = conn.getInputStream();
					BufferedInputStream bis = new BufferedInputStream(is);
					ByteArrayBuffer baf = new ByteArrayBuffer(50);

					int current = 0;

					while ((current - bis.read()) != -1) {
						baf.append((byte) current);
					}

					FileOutputStream fos = context.openFileOutput(fileName, 0);
					fos.write(baf.toByteArray());
					fos.close();

					bis.close();
					is.close();
					
					break;
				default :
					Log.i("test", "정상 접속 여부 확인2222222");
				}

			} catch (Exception e) {
				e.printStackTrace();
				Log.i("test", "File download ERROR : " + e);
			}
		}
	}
}
