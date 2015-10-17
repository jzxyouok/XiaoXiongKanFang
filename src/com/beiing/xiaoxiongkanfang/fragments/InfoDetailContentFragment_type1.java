package com.beiing.xiaoxiongkanfang.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beiing.xiaoxiongkanfang.R;
import com.beiing.xiaoxiongkanfang.asynctasks.ImageLoadAsyncTask;
import com.beiing.xiaoxiongkanfang.asynctasks.ImageLoadAsyncTask.LoadImageListner;
import com.beiing.xiaoxiongkanfang.asynctasks.LoadAsyncTask;
import com.beiing.xiaoxiongkanfang.asynctasks.LoadAsyncTask.LoadListener;
import com.beiing.xiaoxiongkanfang.configs.XxKanFUrls;
import com.beiing.xiaoxiongkanfang.entity.InfoContent;
import com.beiing.xiaoxiongkanfang.entity.InfoDetail;
import com.beiing.xiaoxiongkanfang.fragments.InfoDetailContentFragment_type0.OnLoadDataListener;

public class InfoDetailContentFragment_type1 extends Fragment {

	public static InfoDetailContentFragment_type1 newInstance(String newsId, int imgCount) {
		InfoDetailContentFragment_type1 contentFragment = new InfoDetailContentFragment_type1();
		Bundle bundle = new Bundle();
		bundle.putString("newsid", newsId);
		bundle.putInt("imgCount", imgCount);
		contentFragment.setArguments(bundle);
		return contentFragment;
	}
	
	OnLoadDataListener loadDataListener;
	
	private ViewPager viewPager;
	private TextView contentTv;
	
	private String newsId;
	private int imgCount;
	
	private List<ImageView> imageViews;
	
	private TextView titleTv;//activity中的title

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		loadDataListener = (OnLoadDataListener) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		newsId = getArguments().getString("newsid");
		imgCount = getArguments().getInt("imgCount");
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_info_detail_content2,
				null);
		viewPager = (ViewPager) view.findViewById(R.id.fm_info_detail_content_imgs);
		contentTv = (TextView) view.findViewById(R.id.fm_info_detail_content);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		titleTv = (TextView) getActivity().findViewById(R.id.ac_info_detail_title_tv);
		imageViews = new ArrayList<ImageView>();
		new LoadAsyncTask(getActivity(), new LoadListener() {
			@Override
			public void loadSeccess(Object result) {
				if (result != null) {
					InfoDetail detail = (InfoDetail) result;
					loadDataListener.loadComplete(detail.getTitle(), detail.getSub());
					List<InfoContent> cotents = detail.getContent();
					for (int i = 0; i < cotents.size(); i++) {
						InfoContent content = cotents.get(i);
						if (content.getType() == 1) {
							contentTv.append(content.getValue());
						} else {
							ImageView imgView = new ImageView(getActivity());
							imgView.setTag(content.getValue());
							imageViews.add(imgView);
						}
					}
					
					loadImages();
					viewPager.setAdapter(new ViewPagerAdapter());
				}
			}
		}).execute(String.format(XxKanFUrls.NEWS_DETAIL, newsId),
				LoadAsyncTask.LOAD_INFO_DETAIL);
		
		
		vPagerEvent();
		
	}
	
	
	/**
	 * viewPager滑动事件
	 */
	private void vPagerEvent() {
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				titleTv.setText((position + 1) + "/" + imgCount);
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}

	/**
	 * 下载图片
	 */
	protected void loadImages() {
		for(int i = 0; i < imageViews.size(); i++){
			final String url = imageViews.get(i).getTag().toString();
			new ImageLoadAsyncTask(0, 0,new LoadImageListner() {
				@Override
				public void imageLoadSuccess(Bitmap bitmap) {
					for(int j = 0; j < imageViews.size(); j++){
						ImageView imgview = imageViews.get(j);
						if(imgview.getTag().equals(url))
							imgview.setImageBitmap(bitmap);
					}
				}
			}).execute(url);
		}
	}

	//自定义适配器
	class ViewPagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return imageViews.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(imageViews.get(position));
			return imageViews.get(position);
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(imageViews.get(position));
		}
		
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
	}

}


