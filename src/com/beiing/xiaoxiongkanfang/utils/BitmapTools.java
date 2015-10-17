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
     * ��ȡ���β���ͼƬ
     * @param data
     * @param requestWidth
     * @param requestHeight
     * @return
     */
    public static Bitmap getDownDimensionBitmap(byte[] data, int requestWidth, int requestHeight){
        Bitmap bitmap = null;
        if(data != null){
            //����ԭʼ��ͼƬ�ߴ磬����Bitmap������
            //����Bitmap���ɣ��ǰ���ͼƬ��ԭʼ��ߣ��������ɣ�����ÿһ������ռ��4���ֽ� ARGB
//                ret = BitmapFactory.decodeByteArray(data, 0, data.length);

            //���ö��β�������СͼƬ�ߴ�ķ�ʽ��
            //1.����1 ��ȡԭʼͼƬ�Ŀ����Ϣ�����ڽ��в����ļ���

            //1.1����Options ����BitmapFactory���ڲ����������ò���
            BitmapFactory.Options options = new BitmapFactory.Options();
            //1.2����inJustDecodeBounds �����ƽ�������ֻ�����ͼƬ��ߵĻ�ȡ�������ȡͼƬ
            //��ռ���ڴ棬��ʹ���������������BitmapFactory.decodexxx()���᷵��bitmap
            options.inJustDecodeBounds = true;

            //���룬ʹ��options���� ���ý��뷽ʽ
            BitmapFactory.decodeByteArray(data, 0, data.length, options);

            //2.����2 ����ͼƬ����ʵ�ߴ磬�뵱ǰ��Ҫ��ʾ�ĳߴ磬���м��㣬���ɲ�����

            //2.1
            int picW = options.outWidth;
            int picH = options.outHeight;

            //2.2׼�� ��ʾ���ֻ��� 256x128 128x64
            //�ߴ��Ǹ��ݳ�����Ҫ�����õ�

//                int reqW = 256;
//                int reqH = 128;

            //2.3���� ���� ͼƬ������
            options.inSampleSize = calculateInSampleSize(options, requestWidth, requestHeight);//��ȵ�1/32  �߶ȵ�1/32

            //2.4���� ���룬ʵ������Bitmap
            options.inJustDecodeBounds = false;

            //2.4.1 Bitmap.Config��˵��
            //Ҫ�����������ÿһ�����������أ�ʹ��RGB_565 �洢��ʽ
            //һ�����أ�ռ�������ֽڣ���ARGB_8888Ц��һ��
            //�������������ʹ��ָ�����ã����Զ�ʹ��ARGB_8888
            options.inPreferredConfig = Bitmap.Config.RGB_565;

            //2.4.2��һ����ʱ������,���Լ�ʱ����ڴ�
            options.inPurgeable = true;

            //2.5ʹ�����ò����Ĳ����������� ���룬��ȡbitmap
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        }
        return bitmap;
    }


    /**
     * ����ͼƬ���β����Ĳ����ʣ�ʹ�û�ȡͼƬ���֮���Options��Ϊ��һ������
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

        //ֻ�е�����Ŀ�ȡ��߶� > 0ʱ����������
        //����ͼƬ����������
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
	 * ��ImageView�л�ȡbitmap���� 1.���imageview����ͼƬ���Ի�ȡ
	 * imageview����Ϊsrc,����imageView.getDrawable()
	 * �����background,����imageView.getBackground()
	 * 2.ûӴͼƬ��ȡ������ֻ����ɫ����������ClassCastException: java.lang.ClassCastException:
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
