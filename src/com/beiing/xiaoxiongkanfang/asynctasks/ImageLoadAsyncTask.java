package com.beiing.xiaoxiongkanfang.asynctasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.beiing.xiaoxiongkanfang.cache.BitmapCache;
import com.beiing.xiaoxiongkanfang.cache.FileCache;
import com.beiing.xiaoxiongkanfang.utils.BitmapTools;
import com.beiing.xiaoxiongkanfang.utils.HttpTools;

public class ImageLoadAsyncTask extends AsyncTask<String, Void, Bitmap> {

	/**
	 * 图频下载接口
	 */
	public interface LoadImageListner {
		public void imageLoadSuccess(Bitmap bitmap);
	}

	private LoadImageListner loadImageListner;

	/**
	 * 加载的图片，最终显示的宽度
	 */
	private int requestWidth;
	// 高度
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
			// 获取url中对应的文件缓存
			byte[] data = FileCache.getInstance().load(url);
			if (data != null) {
				// TODO 有文件数据，不需要联网
			} else {
				// TODO 联网下载图片
				data = HttpTools.doGet(url);
			}
			if (data != null) {
				//二次采样
				bitmap = BitmapTools.getDownDimensionBitmap(data, requestWidth,
						requestHeight);
				BitmapCache.getInstance().putBitmap(url, bitmap);
				// data需要显示释放
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
