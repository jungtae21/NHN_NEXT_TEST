package com.example.nht_next_test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;



public class Proxy {
	public String getJSON(){
		
		try{
			URL url = new URL("http://elisms.com/adminpage/test.json");	
			
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			
			//���� ���ӽ��� Time out(ms)
			conn.setConnectTimeout(10 * 1000);
			
			//Read���� Time out(ms)
			conn.setReadTimeout(10 * 1000);
			
			//��û��� ����
			conn.setRequestMethod("GET");
			
			//������ �����ϵ��� ��
			conn.setRequestProperty("Connetion", "Keep-Alive");
			
			//ĳ���ͼ��� UTF-8�� ��û
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			
			//ĳ�õ� �����͸� ������� �ʰ� �Ź� �����κ��� �ٽ� ����
			conn.setRequestProperty("Cache-Control", "no-cache");
			
			//�����κ��� JSON ������ Ÿ������ ������ ��û
			conn.setRequestProperty("Accept", "application/json");
			
			//InputStream���� ������ ���� ������ �ްڴٴ� �ɼ�
			conn.setDoInput(true);
			
			conn.connect();
			
			int status = conn.getResponseCode();
			Log.i("test", "ProxyResposnCode"+status);
			
			switch(status){
				//���������� ������ �� ����(200,201�� ResponseCode)
				case 200:
				case 201:
					//한글화 깨짐 해결(EUC-KR)
					//http://blog.daum.net/baramjin/16011210
					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "EUC-KR"));
					StringBuilder sb =new StringBuilder();
					String line;
					
					while((line = br.readLine()) != null){
						sb.append(line + "\n");
					}
					
					br.close();
					
					return sb.toString();
			}
			
		}catch(Exception e){
			e.printStackTrace();
			Log.i("test", "NETWORK ERROR : "+ e);
		}
		return null;
	}
}
