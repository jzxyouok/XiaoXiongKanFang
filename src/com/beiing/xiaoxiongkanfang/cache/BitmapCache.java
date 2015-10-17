package com.beiing.xiaoxiongkanfang.cache;

import com.beiing.xiaoxiongkanfang.utils.BitmapTools;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.util.LruCache;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class BitmapCache {

	private static BitmapCache bitmapCache;

	public static void newInstance() {
		if (bitmapCache == null)
			bitmapCache = new BitmapCache();
	}

	public static BitmapCache getInstance() {
		if (bitmapCache == null) {
			throw new IllegalStateException(
					"The instance is null, please invoke newInstance() first");
		}
		return bitmapCache;
	}

	private LruCache<String, Bitmap> mCache;

	private BitmapCache() {
		int maxSize = 2 * 1024 * 1024;
		mCache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			}
		};
	}

	public Bitmap getBitmap(String url) {
		Bitmap bitmap = null;
		bitmap = mCache.get(url);
		if (bitmap == null) {
			bitmap = FileCache.getInstance().readBitmap(url);
			if (bitmap != null)
				mCache.put(url, bitmap);
		}
		return bitmap;
	}

	public void putBitmap(String url, Bitmap bitmap) {

		if (url != null && bitmap != null) {
			mCache.put(url, bitmap);
			// 将图片也存放在扩展卡
			try {
				FileCache.getInstance().saveBitmap(url, bitmap,
						CompressFormat.PNG);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}