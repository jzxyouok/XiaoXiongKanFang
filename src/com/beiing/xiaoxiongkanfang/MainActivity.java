package com.beiing.xiaoxiongkanfang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beiing.xiaoxiongkanfang.adapters.InfoListAdapter;
import com.beiing.xiaoxiongkanfang.asynctasks.LoadAsyncTask;
import com.beiing.xiaoxiongkanfang.asynctasks.LoadAsyncTask.LoadListener;
import com.beiing.xiaoxiongkanfang.configs.XxKanFConfigs;
import com.beiing.xiaoxiongkanfang.configs.XxKanFUrls;
import com.beiing.xiaoxiongkanfang.entity.Advertisement;
import com.beiing.xiaoxiongkanfang.entity.Information;
import com.beiing.xiaoxiongkanfang.fragments.FirstPageFragment;
import com.beiing.xiaoxiongkanfang.widget.TopView;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class MainActivity extends Activity {

	FirstPageFragment firstPageFragment;

	FragmentManager fragmentManager;

	int containerViewId = R.id.ac_main_fm_layout;

	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().setWindowAnimations(R.style.activity_enter_exit_2);
		context = this;

		fragmentManager = getFragmentManager();

		firstPageFragment = new FirstPageFragment();
		fragmentManager.beginTransaction()
				.add(containerViewId, firstPageFragment).commit();
	}

}
