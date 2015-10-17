package com.beiing.xiaoxiongkanfang.fragments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.beiing.xiaoxiongkanfang.R;
import com.beiing.xiaoxiongkanfang.asynctasks.ImageLoadAsyncTask;
import com.beiing.xiaoxiongkanfang.asynctasks.ImageLoadAsyncTask.LoadImageListner;
import com.beiing.xiaoxiongkanfang.asynctasks.LoadAsyncTask;
import com.beiing.xiaoxiongkanfang.asynctasks.LoadAsyncTask.LoadListener;
import com.beiing.xiaoxiongkanfang.configs.XxKanFUrls;
import com.beiing.xiaoxiongkanfang.entity.InfoContent;
import com.beiing.xiaoxiongkanfang.entity.InfoDetail;

public class InfoDetailContentFragment_type0 extends Fragment {

	public interface OnLoadDataListener {
		public void loadComplete(String title, String sub);
	}

	OnLoadDataListener loadDataListener;

	public static InfoDetailContentFragment_type0 newInstance(String newsId) {
		InfoDetailContentFragment_type0 contentFragment = new InfoDetailContentFragment_type0();
		Bundle bundle = new Bundle();
		bundle.putString("newsid", newsId);
		contentFragment.setArguments(bundle);
		return contentFragment;
	}

	TextView titleTv;
	TextView subTv;

	LinearLayout contentLl;

	String newsId;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		loadDataListener = (OnLoadDataListener) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		newsId = getArguments().getString("newsid");
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View view = inflater
				.inflate(R.layout.fragment_info_detail_cotent, null);
		contentLl = (LinearLayout) view
				.findViewById(R.id.fm_info_detail_content_ll);

		titleTv = (TextView) view.findViewById(R.id.fm_info_detail_title);
		subTv = (TextView) view.findViewById(R.id.fm_info_detail_sub);

		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
//		map = new HashMap<String, Integer>();
		new LoadAsyncTask(getActivity(), new LoadListener() {
			@Override
			public void loadSeccess(Object result) {
				if (result != null) {
					InfoDetail detail = (InfoDetail) result;
					titleTv.setText(detail.getTitle());
					subTv.setText(detail.getSub());

					loadDataListener.loadComplete(detail.getTitle(),
							detail.getSub());

					List<InfoContent> cotents = detail.getContent();
					for (int i = 0; i < cotents.size(); i++) {
						InfoContent content = cotents.get(i);
						if (content.getType() == 2) {
							final ImageView imageView = new ImageView(
									getActivity());
							imageView.setTag(i + 1);
							new ImageLoadAsyncTask(0, 0, new LoadImageListner() {
								@Override
								public void imageLoadSuccess(Bitmap bitmap) {
									LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
											LayoutParams.MATCH_PARENT, bitmap
													.getHeight());
									params.setMargins(0, 20, 0, 15);
									imageView.setLayoutParams(params);
									imageView.setImageBitmap(bitmap);

									contentLl.addView(imageView,
											(Integer) imageView.getTag());
								}
							}).execute(content.getValue());
						}
					}

					for (int i = 0; i < cotents.size(); i++) {
						InfoContent content = cotents.get(i);
						if (content.getType() == 1) {
							TextView textView = new TextView(getActivity());
							LinearLayout.LayoutParams pps = new LayoutParams(
									LayoutParams.MATCH_PARENT,
									LayoutParams.WRAP_CONTENT);
							textView.setTextSize(16);
							textView.setTextColor(getResources().getColor(
									R.color.info_cotent_text_color));
							textView.setText(content.getValue());
							contentLl.addView(textView);
						}
					}
				}
			}
		}).execute(String.format(XxKanFUrls.NEWS_DETAIL, newsId),
				LoadAsyncTask.LOAD_INFO_DETAIL);
	}

}
