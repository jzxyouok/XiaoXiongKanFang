package com.beiing.xiaoxiongkanfang.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.beiing.xiaoxiongkanfang.utils.EncryptTools;
import com.beiing.xiaoxiongkanfang.utils.StreamUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;


/**
 * Created Author:Beiing Email:1101587382@qq.com Date:2015/10/12
 */
public class FileCache {
	private static FileCache ourInstance;

	public static FileCache newInstance(Context context) {
		if (context != null) {
			if (ourInstance == null) {
				ourInstance = new FileCache(context);
			}
		} else {
			throw new IllegalArgumentException("Context must be set");
		}
		return ourInstance;
	}

	public static FileCache getInstance() {
		if (ourInstance == null) {
			throw new IllegalStateException("ourInstance is null.");
		}
		return ourInstance;
	}

	private static Context context;

	private FileCache(Context context) {
		this.context = context;
	}

	/**
	 * ���ļ��洢�м��ض�Ӧ��ַ������
	 * 
	 * @param url
	 * @return
	 */
	public byte[] load(String url) {
		// TODO ͨ����ַ���ļ�
		byte[] ret = null;
		if (url != null) {
			// 1. �����ļ�����Ŀ¼
			File cacheDir = null;
			String state = Environment.getExternalStorageState();
			if (state.equals(Environment.MEDIA_MOUNTED)) {
				// ��ȡ�洢������Ӧ�ó���Ļ���Ŀ¼
				cacheDir = context.getExternalCacheDir();
			} else {
				// ��ȡ�ڲ��洢����Ŀ¼
				cacheDir = context.getCacheDir();
			}

			// 2. ӳ���ļ�����
			String fileName = EncryptTools.md5(url);
			File targetFile = new File(cacheDir, fileName);

			if (targetFile.exists()) {
				FileInputStream fin = null;
				try {
					fin = new FileInputStream(targetFile);
					ret = StreamUtil.readStream(fin);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					StreamUtil.close(fin);
				}
			}
		}

		return ret;
	}

	/**
	 * �����Ӧ��ַ�����ݵ��ļ���
	 * 
	 * @param url
	 * @param data
	 */
	public void save(String url, byte[] data) {
		// TODO ͨ����ַ���ļ�

		if (url != null && data != null) {
			// 1. �����ļ�����Ŀ¼
			File cacheDir = null;
			String state = Environment.getExternalStorageState();
			if (state.equals(Environment.MEDIA_MOUNTED)) {
				// ��ȡ�洢������Ӧ�ó���Ļ���Ŀ¼
				cacheDir = context.getExternalCacheDir();
			} else {
				// ��ȡ�ڲ��洢����Ŀ¼
				cacheDir = context.getCacheDir();
			}

			// 2. ӳ���ļ�����
			String fileName = EncryptTools.md5(url);
			File targetFile = new File(cacheDir, fileName);

			// 3. IO����
			FileOutputStream fout = null;
			try {
				fout = new FileOutputStream(targetFile);
				fout.write(data);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				StreamUtil.close(fout);
			}
		}
	}

	/**
	 * ��ȡһ��ͼƬ
	 * @param url
	 * @return Bitmap
	 */
	public Bitmap readBitmap(String url) {
		Bitmap bitmap = null;
		if (url != null) {
			// 1. �����ļ�����Ŀ¼
			File cacheDir = null;
			String state = Environment.getExternalStorageState();
			if (state.equals(Environment.MEDIA_MOUNTED)) {
				// ��ȡ�洢������Ӧ�ó���Ļ���Ŀ¼
				cacheDir = context.getExternalCacheDir();
			} else {
				// ��ȡ�ڲ��洢����Ŀ¼
				cacheDir = context.getCacheDir();
			}
			// 2. ӳ���ļ�����
			String fileName = EncryptTools.md5(url);
			File targetFile = new File(cacheDir, fileName);
			if (targetFile.exists()) {
				bitmap = BitmapFactory.decodeFile(targetFile.getAbsolutePath());
			}
		}
		return bitmap;
	}

	/**
	 * �洢ͼƬ
	 * @param url
	 * @param bitmap
	 * @param format
	 * @throws FileNotFoundException
	 */
	public void saveBitmap(String url, Bitmap bitmap, CompressFormat format)
			throws FileNotFoundException {
		if (url != null && bitmap != null) {
			// 1. �����ļ�����Ŀ¼
			File cacheDir = null;
			String state = Environment.getExternalStorageState();
			if (state.equals(Environment.MEDIA_MOUNTED)) {
				// ��ȡ�洢������Ӧ�ó���Ļ���Ŀ¼
				cacheDir = context.getExternalCacheDir();
			} else {
				// ��ȡ�ڲ��洢����Ŀ¼
				cacheDir = context.getCacheDir();
			}

			// 2. ӳ���ļ�����
			String fileName = EncryptTools.md5(url);
			File targetFile = new File(cacheDir, fileName);
			// ��ͼƬ����д�뵽һ��ͼƬ�ļ�
			FileOutputStream fos = new FileOutputStream(targetFile);
			// ͼƬ��ѹ�� CompressFormat.PNG:ѹ��֮��ĸ�ʽ
			bitmap.compress(format, 100, fos);
		}

	}

}
