package com.beiing.xiaoxiongkanfang.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created Author:Beiing Email:1101587382@qq.com Date:2015/10/12
 */
// ����new ���ɱ��̳�
public final class HttpTools {

	private HttpTools() {

	}

	/**
	 * get�������
	 * 
	 * @param url
	 * @return
	 */
	public static byte[] doGet(String url) {
		byte[] ret = null;
		if (url != null) {
			HttpURLConnection conn = null;
			try {
				URL u = new URL(url);
				conn = (HttpURLConnection) u.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(5000);
				conn.setDoInput(true);
				conn.connect();
				if (conn.getResponseCode() == 200) {
					// TODO ��data��ֵ
					InputStream in = null;
					try {
						in = conn.getInputStream();
						ret = StreamUtil.readStream(in);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						StreamUtil.close(in);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				StreamUtil.close(conn);
			}
		}
		return ret;
	}

	/**
	 * post��ʽ�������
	 * @param path
	 * @param params
	 * @param encode
	 * @return
	 * @throws IOException
	 */
	public static byte[] doPost(String url, HashMap<String, String> params,
			String encode) throws IOException {
		byte[] ret = null;
		// ��Ҫ�ύ��������֯��username=kk&psw=111��ʽ
		if(url != null && params != null){
			if(encode == null)
				encode = "UTF-8";
			
			StringBuilder stringBuilder = new StringBuilder();
			for (Map.Entry<String, String> en : params.entrySet()) {
				stringBuilder.append(en.getKey()).append("=")
						.append(URLEncoder.encode(en.getValue(), encode))
						.append("&");
			}
			// ɾ������ &����
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(5000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			// �����ύ�����ݵ� ����
			conn.setRequestProperty("Content-Type",
					" application/x-www-form-urlencoded");
			// �����ύ�����ݵĳ���
			byte[] b = stringBuilder.toString().getBytes("UTF-8");
			conn.setRequestProperty("Content-Length", String.valueOf(b.length));
			// �ύ���� ---���������д������
			OutputStream outputStream = conn.getOutputStream();
			outputStream.write(b, 0, b.length);
			InputStream in = null;
			if (conn.getResponseCode() == 200) {
				in = conn.getInputStream();
				ret = StreamUtil.readStream(in);
			}
			
			StreamUtil.close(outputStream);
			StreamUtil.close(in);
		}
		return ret;
	}

}
