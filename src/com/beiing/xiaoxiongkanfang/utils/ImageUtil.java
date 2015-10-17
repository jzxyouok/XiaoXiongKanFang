package com.beiing.xiaoxiongkanfang.utils;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.TypedValue;
import android.widget.ImageView;

public class ImageUtil {

	public static int dpToPx(Resources res, int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				res.getDisplayMetrics());
	}

	/**
	 * 从ImageView中获取bitmap对象 1.如果imageview中有图片可以获取
	 * imageview设置为src,调用imageView.getDrawable()
	 * 如果是background,调用imageView.getBackground()
	 * 2.没哟图片可取，可能只有颜色，这样会抛ClassCastException: java.lang.ClassCastException:
	 * android.graphics.drawable.ColorDrawable cannot be cast to
	 * android.graphics.drawable.BitmapDrawable
	 * 
	 * @param imageView
	 * @return
	 */
	public static Bitmap getBmFromImageView(ImageView imageView) {
		if (imageView != null) {
			try {
				if (imageView.getDrawable() != null) {
					return ((BitmapDrawable) imageView.getDrawable())
							.getBitmap();
				}
				if (imageView.getBackground() != null) {
					return ((BitmapDrawable) imageView.getBackground())
							.getBitmap();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 从一个Bitmap中得到byte数组
	 * 
	 * @param bitmap
	 * @param format
	 *            压缩格式JPEG、PNG、WEBP
	 * @param quality
	 *            压缩质量1-100
	 * @return
	 */
	public static byte[] getBytesFromBitmap(Bitmap bitmap,
			CompressFormat format, int quality) {
		ByteArrayOutputStream bos = null;
		if (bitmap != null) {
			int bufSize = bitmap.getWidth() * bitmap.getHeight();
			bos = new ByteArrayOutputStream(bufSize);
			if (format == null)
				format = CompressFormat.PNG;// 默认PNG
			if (quality <= 0 || quality > 100)
				quality = 100;// 默认最高质量
			bitmap.compress(format, quality, bos);
		}
		return bos.toByteArray();
	}

	/**
	 * 通过bitmap对象得到drawable对象
	 * 
	 * @param context
	 * @param bitmap
	 * @return
	 */
	public static BitmapDrawable getBdFromBitmap(Context context, Bitmap bitmap) {
		BitmapDrawable bitmapDrawable = null;
		bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
		return bitmapDrawable;
	}

}
