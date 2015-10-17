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
	 * 从文件存储中加载对应网址的内容
	 * 
	 * @param url
	 * @return
	 */
	public byte[] load(String url) {
		// TODO 通过网址找文件
		byte[] ret = null;
		if (url != null) {
			// 1. 最终文件缓存目录
			File cacheDir = null;
			String state = Environment.getExternalStorageState();
			if (state.equals(Environment.MEDIA_MOUNTED)) {
				// 获取存储卡上面应用程序的缓存目录
				cacheDir = context.getExternalCacheDir();
			} else {
				// 获取内部存储缓存目录
				cacheDir = context.getCacheDir();
			}

			// 2. 映射文件名称
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
	 * 保存对应网址的数据到文件中
	 * 
	 * @param url
	 * @param data
	 */
	public void save(String url, byte[] data) {
		// TODO 通过网址存文件

		if (url != null && data != null) {
			// 1. 最终文件缓存目录
			File cacheDir = null;
			String state = Environment.getExternalStorageState();
			if (state.equals(Environment.MEDIA_MOUNTED)) {
				// 获取存储卡上面应用程序的缓存目录
				cacheDir = context.getExternalCacheDir();
			} else {
				// 获取内部存储缓存目录
				cacheDir = context.getCacheDir();
			}

			// 2. 映射文件名称
			String fileName = EncryptTools.md5(url);
			File targetFile = new File(cacheDir, fileName);

			// 3. IO操作
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
	 * 读取一张图片
	 * @param url
	 * @return Bitmap
	 */
	public Bitmap readBitmap(String url) {
		Bitmap bitmap = null;
		if (url != null) {
			// 1. 最终文件缓存目录
			File cacheDir = null;
			String state = Environment.getExternalStorageState();
			if (state.equals(Environment.MEDIA_MOUNTED)) {
				// 获取存储卡上面应用程序的缓存目录
				cacheDir = context.getExternalCacheDir();
			} else {
				// 获取内部存储缓存目录
				cacheDir = context.getCacheDir();
			}
			// 2. 映射文件名称
			String fileName = EncryptTools.md5(url);
			File targetFile = new File(cacheDir, fileName);
			if (targetFile.exists()) {
				bitmap = BitmapFactory.decodeFile(targetFile.getAbsolutePath());
			}
		}
		return bitmap;
	}

	/**
	 * 存储图片
	 * @param url
	 * @param bitmap
	 * @param format
	 * @throws FileNotFoundException
	 */
	public void saveBitmap(String url, Bitmap bitmap, CompressFormat format)
			throws FileNotFoundException {
		if (url != null && bitmap != null) {
			// 1. 最终文件缓存目录
			File cacheDir = null;
			String state = Environment.getExternalStorageState();
			if (state.equals(Environment.MEDIA_MOUNTED)) {
				// 获取存储卡上面应用程序的缓存目录
				cacheDir = context.getExternalCacheDir();
			} else {
				// 获取内部存储缓存目录
				cacheDir = context.getCacheDir();
			}

			// 2. 映射文件名称
			String fileName = EncryptTools.md5(url);
			File targetFile = new File(cacheDir, fileName);
			// 把图片数据写入到一个图片文件
			FileOutputStream fos = new FileOutputStream(targetFile);
			// 图片的压缩 CompressFormat.PNG:压缩之后的格式
			bitmap.compress(format, 100, fos);
		}

	}

}
