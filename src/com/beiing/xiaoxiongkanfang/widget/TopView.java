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
 * 用于播放广告的自定义控件-包含了ViewPager的控件
 */
public class TopView extends FrameLayout {

	public static final String KEY_URL = "url";

	public static final String KEY_TITLE = "title";

	ViewPager vPager;

	TextView titleTv;

	LinearLayout navLayout;

	private List<ImageView> imgViews;// viewpager中显示的

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
		// 第二个参数表示：布局资源中跟标签内声明的布局参数参考父控件类型
		// 第三个参数：为true时代表将第一个参数中声明的子控件归属到第二个参数对象中，false不归属
		LayoutInflater.from(context).inflate(R.layout.ac_main_ad_view_top,
				this, true);

		initView();
	}

	private void initView() {
		// TODO 查找相关的UI控件
		vPager = (ViewPager) findViewById(R.id.viewPager);
		titleTv = (TextView) findViewById(R.id.titleId);
		navLayout = (LinearLayout) findViewById(R.id.navLayoutId);
	}

	public void setDatas(List<Map<String, String>> data) {
		this.datas = data;
		titleTv.setText(datas.get(0).get(KEY_TITLE));// 默认显示第一个广告
		createViews();
	}

	private void createViews() {
		// TODO 根据数据源创建Viewae
		imgViews = new ArrayList<ImageView>();
		ImageView img = null;
		for (Map<String, String> map : datas) {
			img = new ImageView(getContext());
			img.setScaleType(ScaleType.CENTER_CROP);
			img.setTag(map.get(KEY_URL));
			imgViews.add(img);
		}

		// 设置viewpager适配器
		vPager.setAdapter(new ImageAdapter());

		loadImgs();
	}

	private void loadImgs() {
		// TODO 加载网络图片
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
	// // TODO 网络下载完成的回调方法
	// Log.i("--", "回调成功" + bytes.length);
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

	private int curPosition = 0;// 当前位置

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
