package com.beiing.xiaoxiongkanfang;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.MapView;
import com.beiing.xiaoxiongkanfang.asynctasks.ImageLoadAsyncTask;
import com.beiing.xiaoxiongkanfang.asynctasks.ImageLoadAsyncTask.LoadImageListner;
import com.beiing.xiaoxiongkanfang.configs.XxKanFKeys;
import com.beiing.xiaoxiongkanfang.fragments.XFDetailCommentsFragment;
import com.beiing.xiaoxiongkanfang.fragments.XFDetailCommentsFragment.FreshListener;
import com.beiing.xiaoxiongkanfang.fragments.XFDetailCotentFragment;
import com.beiing.xiaoxiongkanfang.utils.ImageUtil;
import com.beiing.xiaoxiongkanfang.utils.LogUtil;
import com.beiing.xiaoxiongkanfang.widget.ScrollBottomScrollView;
import com.beiing.xiaoxiongkanfang.widget.ScrollBottomScrollView.ScrollBottomListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

public class XinFangDetailActivity extends Activity implements FreshListener,
		View.OnClickListener {

	public static void startXinFangDetailActivity(Context context,
			String houseId, String cityPicUrl) {
		Intent intent = new Intent(context, XinFangDetailActivity.class);
		intent.putExtra(XxKanFKeys.HOUSE_ID, houseId);
		intent.putExtra(XxKanFKeys.CITY_PIC_URL, cityPicUrl);
		context.startActivity(intent);
	}

	String houseId;
	String cityPicUrl;

	private RelativeLayout topRl;
	private TextView numTv;

	private RadioGroup fmRg;

	private RadioButton rbComment;

	private ImageButton backTopIb;

	private PullToRefreshScrollView scrollView;

	private int containerId = R.id.ac_xf_detail_fm;

	FragmentManager fmManager;
	XFDetailCotentFragment xfDetailCotentFragment;
	XFDetailCommentsFragment xfDetailCommentsFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xinfang_detail);

		backTopIb = (ImageButton) findViewById(R.id.ac_xf_detail_back_top_ib);
		backTopIb.setOnClickListener(this);

		scrollView = (PullToRefreshScrollView) findViewById(R.id.ac_xf_detail_scrollview);

		houseId = getIntent().getStringExtra(XxKanFKeys.HOUSE_ID);
		cityPicUrl = getIntent().getStringExtra(XxKanFKeys.CITY_PIC_URL);
		fmManager = getFragmentManager();
		xfDetailCotentFragment = XFDetailCotentFragment.newInstance(houseId);
		xfDetailCommentsFragment = XFDetailCommentsFragment
				.newInstance(houseId);

		fmManager.beginTransaction().add(containerId, xfDetailCotentFragment)
				.add(containerId, xfDetailCommentsFragment)
				.hide(xfDetailCommentsFragment).commit();

		initViews();

		fmRgEvent();

		scrollViewEvent();

	}

	int i = 0;

	private void scrollViewEvent() {

		scrollView.setMode(Mode.DISABLED);

		scrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				xfDetailCommentsFragment.loadData();
			}
		});
	}

	private void fmRgEvent() {
		// TODO
		fmRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.ac_xf_deail_info_rb) {
					if (xfDetailCotentFragment.isHidden())
						fmManager.beginTransaction()
								.hide(xfDetailCommentsFragment)
								.show(xfDetailCotentFragment).commit();
					scrollView.setMode(Mode.DISABLED);
				} else if (checkedId == R.id.ac_xf_deail_comment_rb) {
					if (xfDetailCommentsFragment.isHidden())
						fmManager.beginTransaction()
								.hide(xfDetailCotentFragment)
								.show(xfDetailCommentsFragment).commit();
					scrollView.setMode(Mode.PULL_FROM_END);
				}
			}
		});
	}

	private void initViews() {
		topRl = (RelativeLayout) findViewById(R.id.ac_xf_detail_top_rl);
		fmRg = (RadioGroup) findViewById(R.id.ac_xf_detail_rg);

		rbComment = (RadioButton) findViewById(R.id.ac_xf_deail_comment_rb);

		numTv = (TextView) findViewById(R.id.ac_xf_detail_num_tv);
		new ImageLoadAsyncTask(0, 0, new LoadImageListner() {
			@Override
			public void imageLoadSuccess(Bitmap bitmap) {
				topRl.setBackground(ImageUtil.getBdFromBitmap(
						getApplicationContext(), bitmap));
			}
		}).execute(cityPicUrl);
	}

	public void onIbClick(View v) {
		switch (v.getId()) {
		case R.id.ac_xf_detail_back_ib:
			finish();
			break;

		case R.id.ac_xf_detail_favorate_ib:
			Toast.makeText(getApplicationContext(), "收藏", 0).show();
			break;

		case R.id.ac_xf_detail_share_ib:
			Toast.makeText(getApplicationContext(), "分享", 0).show();
			break;
		}
	}

	@Override
	public void onFreshComplete(int commentCount) {
		// TODO 评论fragment列表加载完成
		scrollView.onRefreshComplete();
		rbComment.setText("评论(" + commentCount + ")");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.ac_xf_detail_back_top_ib) {
			scrollView.getRefreshableView().fullScroll(
					PullToRefreshScrollView.FOCUS_UP);
		}
	}

}
