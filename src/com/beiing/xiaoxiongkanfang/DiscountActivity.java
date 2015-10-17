package com.beiing.xiaoxiongkanfang;

import java.util.List;

import com.beiing.xiaoxiongkanfang.adapters.DiscountAdapter;
import com.beiing.xiaoxiongkanfang.asynctasks.LoadAsyncTask;
import com.beiing.xiaoxiongkanfang.asynctasks.LoadAsyncTask.LoadListener;
import com.beiing.xiaoxiongkanfang.configs.XxKanFKeys;
import com.beiing.xiaoxiongkanfang.configs.XxKanFUrls;
import com.beiing.xiaoxiongkanfang.entity.Discount;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class DiscountActivity extends Activity {

	public static void startDiscountActivity(Context context, String cityId) {
		Intent intent = new Intent(context, DiscountActivity.class);
		intent.putExtra(XxKanFKeys.CITY_ID, cityId);
		context.startActivity(intent);
	}

	PullToRefreshListView ptrListView;

	DiscountAdapter adapter;

	int curPage = 0;

	String cityId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discount);
		cityId = getIntent().getStringExtra(XxKanFKeys.CITY_ID);
		ptrListView = (PullToRefreshListView) findViewById(R.id.ac_discount_lv);
		ptrListView.setMode(Mode.PULL_FROM_END);
		adapter = new DiscountAdapter(this);
		ptrListView.setAdapter(adapter);
		loadData();
		ptrListViewEvent();
	}

	private void ptrListViewEvent() {
		ptrListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				loadData();
			}
		});

		ptrListView.getRefreshableView().setOnItemClickListener(
				new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						XinFangDetailActivity.startXinFangDetailActivity(
								DiscountActivity.this, ((Discount) adapter
										.getItem(position - 1)).getHid(),
								((Discount) adapter.getItem(position - 1))
										.getCover());
					}
				});
	}

	private void loadData() {
		new LoadAsyncTask(this, new LoadListener() {
			@Override
			public void loadSeccess(Object result) {
				if (result != null) {
					adapter.addData((List<Discount>) result);
					ptrListView.onRefreshComplete();
				}
			}
		}).execute(getUrl(), LoadAsyncTask.LOAD_DISCOUNT);
	}

	private String getUrl() {
		return String.format(XxKanFUrls.DISCOUNT_SEAL, ++curPage, cityId);
	}
	
	
	public void onBackClick(View v){
		finish();
	}

}
