package com.beiing.xiaoxiongkanfang.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.beiing.xiaoxiongkanfang.R;
import com.beiing.xiaoxiongkanfang.asynctasks.ImageLoadAsyncTask;
import com.beiing.xiaoxiongkanfang.asynctasks.ImageLoadAsyncTask.LoadImageListner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ���ڲ��Ź����Զ���ؼ�-������ViewPager�Ŀؼ�
 */
public class TopView extends FrameLayout {

	public static final String KEY_URL = "url";

	public static final String KEY_TITLE = "title";

	ViewPager vPager;

	TextView titleTv;

	LinearLayout navLayout;

	private List<ImageView> imgViews;// viewpager����ʾ��

	private List<Map<String, String>> datas;

	Handler mHandler = new Handler();

	public TopView(Context context) {
		this(context, null, 0);
	}

	public TopView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TopView(Context context, AttributeSet attrs, int style) {
		super(context, attrs, style);
		// �ڶ���������ʾ��������Դ�и���ǩ�������Ĳ��ֲ����ο����ؼ�����
		// ������������Ϊtrueʱ������һ���������������ӿؼ��������ڶ������������У�false������
		LayoutInflater.from(context).inflate(R.layout.ac_main_ad_view_top,
				this, true);

		initView();
	}

	private void initView() {
		// TODO ������ص�UI�ؼ�
		vPager = (ViewPager) findViewById(R.id.viewPager);
		titleTv = (TextView) findViewById(R.id.titleId);
		navLayout = (LinearLayout) findViewById(R.id.navLayoutId);
	}

	public void setDatas(List<Map<String, String>> data) {
		this.datas = data;
		titleTv.setText(datas.get(0).get(KEY_TITLE));// Ĭ����ʾ��һ�����
		createViews();
	}

	private void createViews() {
		// TODO ��������Դ����Viewae
		imgViews = new ArrayList<ImageView>();
		ImageView img = null;
		for (Map<String, String> map : datas) {
			img = new ImageView(getContext());
			img.setScaleType(ScaleType.CENTER_CROP);
			img.setTag(map.get(KEY_URL));
			imgViews.add(img);
		}

		// ����viewpager������
		vPager.setAdapter(new ImageAdapter());

		loadImgs();
	}

	private void loadImgs() {
		// TODO ��������ͼƬ
		for (final Map<String, String> map : datas) {
			new ImageLoadAsyncTask(0, 0 ,new LoadImageListner() {
				@Override
				public void imageLoadSuccess(Bitmap bitmap) {
					for (ImageView img : imgViews) {
						if (img.getTag().equals(map.get(KEY_URL).toString()))
							img.setImageBitmap(bitmap);
					}
				}
			}).execute(map.get(KEY_URL).toString());
		}

		new PlayImgThread().start();
	}

	// @Override
	// public void responce(String url, byte[] bytes) {
	// // TODO ����������ɵĻص�����
	// Log.i("--", "�ص��ɹ�" + bytes.length);
	// Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	// for (ImageView img : imgViews) {
	// if (img.getTag().equals(url))
	// img.setImageBitmap(bitmap);
	// }
	// }

	class ImageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imgViews.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(imgViews.get(position));
			return imgViews.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView(imgViews.get(position));
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}
	}

	private int curPosition = 0;// ��ǰλ��

	class PlayImgThread extends Thread {
		@Override
		public void run() {
			super.run();
			try {
				while (TopView.this != null) {

					// if (curPosition == imgViews.size())
					// curPosition = 0;

					Thread.sleep(3000);
					mHandler.post(new Runnable() {

						@Override
						public void run() {
							titleTv.setText(datas.get(curPosition).get(
									KEY_TITLE));
							curPosition = ++curPosition % imgViews.size();
							vPager.setCurrentItem(curPosition);
						}
					});
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
