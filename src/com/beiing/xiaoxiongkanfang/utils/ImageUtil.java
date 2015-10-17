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
	 * ��ImageView�л�ȡbitmap���� 1.���imageview����ͼƬ���Ի�ȡ
	 * imageview����Ϊsrc,����imageView.getDrawable()
	 * �����background,����imageView.getBackground()
	 * 2.ûӴͼƬ��ȡ������ֻ����ɫ����������ClassCastException: java.lang.ClassCastException:
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
	 * ��һ��Bitmap�еõ�byte����
	 * 
	 * @param bitmap
	 * @param format
	 *            ѹ����ʽJPEG��PNG��WEBP
	 * @param quality
	 *            ѹ������1-100
	 * @return
	 */
	public static byte[] getBytesFromBitmap(Bitmap bitmap,
			CompressFormat format, int quality) {
		ByteArrayOutputStream bos = null;
		if (bitmap != null) {
			int bufSize = bitmap.getWidth() * bitmap.getHeight();
			bos = new ByteArrayOutputStream(bufSize);
			if (format == null)
				format = CompressFormat.PNG;// Ĭ��PNG
			if (quality <= 0 || quality > 100)
				quality = 100;// Ĭ���������
			bitmap.compress(format, quality, bos);
		}
		return bos.toByteArray();
	}

	/**
	 * ͨ��bitmap����õ�drawable����
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
