package com.beiing.xiaoxiongkanfang.utils;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

/**
 * Created
 * Author:Beiing
 * Email:1101587382@qq.com
 * Date:2015/10/12
 */
public final class BitmapTools {

    private BitmapTools(){}

    /**
     * 获取二次采样图片
     * @param data
     * @param requestWidth
     * @param requestHeight
     * @return
     */
    public static Bitmap getDownDimensionBitmap(byte[] data, int requestWidth, int requestHeight){
        Bitmap bitmap = null;
        if(data != null){
            //按照原始的图片尺寸，进行Bitmap的生存
            //按照Bitmap生成，是按照图片的原始宽高，进行生成，并且每一个像素占用4个字节 ARGB
//                ret = BitmapFactory.decodeByteArray(data, 0, data.length);

            //采用二次采样（缩小图片尺寸的方式）
            //1.步骤1 获取原始图片的宽高信息，用于进行采样的计算

            //1.1创建Options ，给BitmapFactory的内部解码器设置参数
            BitmapFactory.Options options = new BitmapFactory.Options();
            //1.2设置inJustDecodeBounds 来控制解码器，只会进行图片宽高的获取，不会获取图片
            //不占用内存，当使用这个参数，代表BitmapFactory.decodexxx()不会返回bitmap
            options.inJustDecodeBounds = true;

            //解码，使用options参数 设置解码方式
            BitmapFactory.decodeByteArray(data, 0, data.length, options);

            //2.步骤2 根据图片的真实尺寸，与当前需要显示的尺寸，进行计算，生成采样率

            //2.1
            int picW = options.outWidth;
            int picH = options.outHeight;

            //2.2准备 显示在手机上 256x128 128x64
            //尺寸是根据程序需要来设置的

//                int reqW = 256;
//                int reqH = 128;

            //2.3计算 设置 图片采样率
            options.inSampleSize = calculateInSampleSize(options, requestWidth, requestHeight);//宽度的1/32  高度的1/32

            //2.4开放 解码，实际生成Bitmap
            options.inJustDecodeBounds = false;

            //2.4.1 Bitmap.Config的说明
            //要求解码器对于每一个采样的像素，使用RGB_565 存储方式
            //一个像素，占用两个字节，比ARGB_8888笑了一半
            //如果解码器不能使用指定配置，就自动使用ARGB_8888
            options.inPreferredConfig = Bitmap.Config.RGB_565;

            //2.4.2是一个过时的设置,可以及时清除内存
            options.inPurgeable = true;

            //2.5使用设置采样的参数，来进行 解码，获取bitmap
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        }
        return bitmap;
    }


    /**
     * 计算图片二次采样的采样率，使用获取图片宽高之后的Options作为第一个参数
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     *
     * --by Google
     */
    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        //只有当请求的宽度、高度 > 0时，进行缩放
        //否则，图片不进行缩放
        if(reqHeight > 0 && reqWidth > 0){
            if (height > reqHeight || width > reqWidth) {

                final int halfHeight = height / 2;
                final int halfWidth = width / 2;

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) > reqHeight
                        && (halfWidth / inSampleSize) > reqWidth) {
                    inSampleSize *= 2;
                }
            }
        }
        return inSampleSize;
    }
    
    
    /**
	 * 从ImageView中获取bitmap对象 1.如果imageview中有图片可以获取
	 * imageview设置为src,调用imageView.getDrawable()
	 * 如果是background,调用imageView.getBackground()
	 * 2.没哟图片可取，可能只有颜色，这样会抛ClassCastException: java.lang.ClassCastException:
	 * android.graphics.drawable.ColorDrawable cannot be cast to
	 * android.graphics.drawable.BitmapDrawable
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
	 * @param context
	 * @param bitmap
	 * @return
	 */
	public BitmapDrawable getBdFromBitmap(Context context, Bitmap bitmap) {
		BitmapDrawable bitmapDrawable = null;
		bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
		return bitmapDrawable;
	}
    
    
}
