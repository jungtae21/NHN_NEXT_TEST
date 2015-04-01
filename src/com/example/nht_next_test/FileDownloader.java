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

		// 파일이 이미 있을 경우 다운로드 하지 않음
		if (!filePath.exists()) {
			try {

				URL url = new URL(fileUrl);

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();

				// 서버 접속시의 Time out(ms)
				conn.setConnectTimeout(10 * 1000);

				// Read시의 Time out(ms)
				conn.setReadTimeout(10 * 1000);

				// 요청 방식 선택
				conn.setRequestMethod("GET");

				// 연결을 지속하도록 함
				conn.setRequestProperty("Connetion", "Keep-Alive");

				// 캐릭터셋을 UTF-8로 요청
				conn.setRequestProperty("Accept-Charset", "UTF-8");

				// 캐시된 데이터를 사용하지 않고 매번 서버로부터 다시 받음
				conn.setRequestProperty("Cache-Control", "no-cache");

				// 서버로부터 아무 형식의 데이터나 다 요청
				conn.setRequestProperty("Accept", "*/*");

				// InputStream으로 서버로부터 응답을 받겠다는 옵션
				conn.setDoInput(true);

				conn.connect();
				int status = conn.getResponseCode();

				switch (status) {
				// 정상적으로 연결이 된 상태
				case 200:
				case 201:
					Log.i("test2", "정상 접속 여부 확인111111");
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
					Log.i("test2", "정상 접속 여부 확인2222222");
				}

			} catch (Exception e) {
				e.printStackTrace();
				Log.i("test2", "File download ERROR : " + e);
			}
		}
	}
}
