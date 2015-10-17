package com.beiing.xiaoxiongkanfang.asynctasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.beiing.xiaoxiongkanfang.cache.BitmapCache;
import com.beiing.xiaoxiongkanfang.cache.FileCache;
import com.beiing.xiaoxiongkanfang.utils.BitmapTools;
import com.beiing.xiaoxiongkanfang.utils.HttpTools;

public class ImageLoadAsyncTask extends AsyncTask<String, Void, Bitmap> {

	/**
	 * ͼƵ���ؽӿ�
	 */
	public interface LoadImageListner {
		public void imageLoadSuccess(Bitmap bitmap);
	}

	private LoadImageListner loadImageListner;

	/**
	 * ���ص�ͼƬ��������ʾ�Ŀ��
	 */
	private int requestWidth;
	// �߶�
	private int requestHeight;

	public ImageLoadAsyncTask(int requestWidth, int requestHeight,
			LoadImageListner loadImageListner) {
		this.loadImageListner = loadImageListner;
		this.requestWidth = requestWidth;
		this.requestHeight = requestHeight;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		Bitmap bitmap = null;
		if (params[0] != null) {
			String url = params[0];
			// ��ȡurl�ж�Ӧ���ļ�����
			byte[] data = FileCache.getInstance().load(url);
			if (data != null) {
				// TODO ���ļ����ݣ�����Ҫ����
			} else {
				// TODO ��������ͼƬ
				data = HttpTools.doGet(url);
			}
			if (data != null) {
				//���β���
				bitmap = BitmapTools.getDownDimensionBitmap(data, requestWidth,
						requestHeight);
				BitmapCache.getInstance().putBitmap(url, bitmap);
				// data��Ҫ��ʾ�ͷ�
				data = null;
			}
		}
		return bitmap;
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if (bitmap != null) {
			loadImageListner.imageLoadSuccess(bitmap);
		}
	}
}
